package com.indocosmo.pms.web.season.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.Month;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.season.model.Seasonhdr;
import com.indocosmo.pms.web.season.service.SeasonHdrService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping("/season")
public class SeasonController {

	public static final String SEASON_EDIT_PAGE_URL = "seasons/season_edit";
	public static final Logger logger = LoggerFactory.getLogger(SeasonController.class);

	@Autowired
	private SeasonHdrService seasonHdrService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	/**
	 * code Exist checking function
	 * 
	 * @param code
	 * @return true when code is exist/ false when is not exist
	 */
	@RequestMapping(value = "codeExist", method = RequestMethod.GET)
	public @ResponseBody boolean codeExist(@RequestParam(value = "code") String code) throws Exception {
		boolean isExist = false;

		try {
			isExist = seasonHdrService.codeExist(code);
		} catch (Exception e) {
			logger.error("Method : getRecord  , " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}

		return isExist;
	}

	/**
	 * Season module save and update function
	 * 
	 * @param Seasonhdr
	 *            seasonHdrModel
	 * @return if record added successfully page redirect to seasonList / if not
	 *         success it will redirect to edit page
	 * @throws Exception
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute("season") Seasonhdr seasonHdr, HttpSession session) throws Exception {
		seasonHdr.setLastUpdated(seasonHdr.getLastUpdated());
		boolean isSeasonSave = seasonHdrService.save(seasonHdr);

		if (isSeasonSave) {
			/**
			 * get count method is using to get total record count from table. record count
			 * session is using in jqGrid
			 */
			Map<String, String> temp1 = new HashMap<String, String>();
			Map<String, String> temp2 = new HashMap<String, String>();
			int count = seasonHdrService.getCount(temp1, temp2);

			session.setAttribute("recordCount", count);

			return "redirect:seasonsList"; // changed list
		}

		return SEASON_EDIT_PAGE_URL;
	}

	/**
	 * List redirect function
	 * 
	 * @return page redirect to season list
	 */
	@RequestMapping(value = "seasonsList", method = RequestMethod.GET)
	public String listRedirect(HttpServletRequest request, Model model, HttpSession session) throws Exception {

		permissionObj = pageAccessPermissionService.getPermission(session, "MST_SEASON");
		model.addAttribute("curPagePerObj", permissionObj);

		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? "seasons/season_list"
				: "access_denied/access_denied");

	}

	/**
	 * Season delete function
	 * 
	 * @param id
	 * @param session
	 * @return true when deletion is success / false when deletion is not success
	 * @throws Exception
	 */
	@RequestMapping(value = "Delete", method = RequestMethod.POST)
	public @ResponseBody boolean delete(@RequestParam(value = "ids") int id, HttpSession session) throws Exception {
		boolean isDeleted = seasonHdrService.delete(id);
		session.removeAttribute("recordCount");

		/**
		 * get count method is using to get total record count from table. record count
		 * session is using in jqGrid
		 */
		Map<String, String> temp1 = new HashMap<String, String>();
		Map<String, String> temp2 = new HashMap<String, String>();

		int count = seasonHdrService.getCount(temp1, temp2);
		session.setAttribute("recordCount", count);

		return isDeleted;
	}

	/**
	 * Season module Single record access function
	 * 
	 * @param season
	 *            Seasonhdrmodel
	 * @param model
	 * @param id
	 *            primary key of table (encrypted)
	 * @return if the record is get the page redirect to edit page
	 */
	@RequestMapping(value = "getSeason", method = RequestMethod.GET)
	public String getRecord(@ModelAttribute Seasonhdr season, HttpSession session, Model model,
			@RequestParam(value = "ids", required = true) String ids) {
		try {
			permissionObj = pageAccessPermissionService.getPermission(session, "MST_SEASON");
			model.addAttribute("curPagePerObj", permissionObj);

			Encryption encryption = new Encryption();
			int seasonId = Integer.parseInt(encryption.decrypt(ids));
			season = seasonHdrService.getRecord(seasonId);
			String encryptedId = encryption.encrypt(Integer.toString(season.getId()));
			season.setEncryption(encryptedId);
		} catch (Exception e) {
			logger.error("Method : getRecord  , " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}

		model.addAttribute("season", season);

		return SEASON_EDIT_PAGE_URL;
	}

	/**
	 * Add season function
	 * 
	 * @param season
	 * @param model
	 * @return page redirect to edit page
	 */
	@RequestMapping(value = "editRedirect", method = RequestMethod.GET)
	public String editRedirect(@ModelAttribute Seasonhdr season, Model model) {
		model.addAttribute("season", season);

		return SEASON_EDIT_PAGE_URL;
	}

	/**
	 * List redirect function
	 * 
	 * @return page redirect to season list
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showPage(HttpServletRequest request, Model model, HttpSession session) throws Exception {

		permissionObj = pageAccessPermissionService.getPermission(session, "MST_SEASON");
		model.addAttribute("curPagePerObj", permissionObj);

		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? "seasons/season_list"
				: "access_denied/access_denied");

	}

	/**
	 * 
	 * @param seasonlist
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal
	 * @param simpleSearch
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JqGridListWrapperDTO list(@ModelAttribute("seasonlist") Seasonhdr seasonlist,
			@RequestParam(value = "currentPage", required = false) String currentPage,
			@RequestParam(value = "rowLimit", required = false) String rowLimit,
			@RequestParam(value = "pagingStart", required = false) String pagingStart,
			@RequestParam(value = "advanceSearch", required = false) String advanceSearch,
			@RequestParam(value = "sortVal", required = false) String sortVal,
			@RequestParam(value = "simpleSearh", required = false) String simpleSearch, HttpSession session)
			throws Exception {
		Map<String, String> simpleSearchMap = new HashMap<String, String>();

		if (simpleSearch != null && !simpleSearch.equals("")) {
			simpleSearchMap.put("code", simpleSearch);
			simpleSearchMap.put("name", simpleSearch);
		}

		Map<String, String> advanceSearchMap = new HashMap<String, String>();

		if (advanceSearch != null && !advanceSearch.equals("null") && !advanceSearch.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				advanceSearchMap = objectMapper.readValue(advanceSearch, new TypeReference<HashMap<String, String>>() {
				});
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Method : getCount " + Throwables.getStackTraceAsString(ex));
				throw new CustomException();
			}
		}

		if (currentPage.equals("")) {
			currentPage = "1";
		}

		if (pagingStart.equals("NaN")) {
			pagingStart = "1";
		}

		JqGridListWrapperDTO jqGridListWrapperDTO = null;
		jqGridListWrapperDTO = seasonHdrService.list(currentPage, rowLimit, pagingStart, advanceSearchMap, sortVal,
				simpleSearchMap);
		session.removeAttribute("recordCount");

		return jqGridListWrapperDTO;
	}

	/**
	 * function for get total count
	 * 
	 * @param simpleSearchData
	 * @param advanceSearch
	 * @return total count of records
	 */
	@RequestMapping(value = "getCount", method = RequestMethod.POST)
	public @ResponseBody String getCount(@RequestParam(value = "simpleSearch") String simpleSearchData,
			@RequestParam(value = "advanceSearch") String advanceSearch) throws Exception {
		Map<String, String> simpleSearchMap = new HashMap<String, String>();

		if (simpleSearchData != null && !simpleSearchData.equals("")) {
			simpleSearchMap.put("code", simpleSearchData);
			simpleSearchMap.put("name", simpleSearchData);
		}

		Map<String, String> advanceSearchMap = new HashMap<String, String>();

		if (advanceSearch != null && !advanceSearch.equals("null") && !advanceSearch.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				advanceSearchMap = objectMapper.readValue(advanceSearch, new TypeReference<HashMap<String, String>>() {
				});
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Method : getCount " + Throwables.getStackTraceAsString(ex));
				throw new CustomException();
			}
		}

		int count = seasonHdrService.getCount(advanceSearchMap, simpleSearchMap);

		return Integer.toString(count);
	}

	/**
	 * function of dateVerification
	 * 
	 * @param jsonDate
	 * @param session
	 * @return json
	 */
	@RequestMapping(value = "/dateVerification", method = RequestMethod.POST)
	public @ResponseBody String dateVerification(@RequestParam(value = "jsonDate") String jsonDate, HttpSession session)
			throws Exception {
		String dateStatus = "";

		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonDate);
			String sm = (String) jsonObject.get("startmonth");
			String sd = (String) jsonObject.get("startday");
			String em = (String) jsonObject.get("endmonth");
			String ed = (String) jsonObject.get("endday");
			int id = Integer.parseInt(jsonObject.get("id").toString());

			int year = Calendar.getInstance().get(Calendar.YEAR);
			String sYear = Integer.toString(year);
			String startDate = sYear + "-" + sm + "-" + sd;
			String eYear = Integer.toString(year);
			String endDate = eYear + "-" + em + "-" + ed;

			boolean returnDate = seasonHdrService.getDates(startDate, endDate, id);

			if (returnDate == true) {
				dateStatus = "SUCCESS";
			} else {
				dateStatus = "FAILURE";
			}
		} catch (Exception ex) {
			logger.error("Method : dateVerification  , " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
		}

		return dateStatus;
	}

	/**
	 * function of color
	 * 
	 * @return map
	 */
	@ModelAttribute("color")
	public Map<String, String> colors() {
		Map<String, String> map = new LinkedHashMap<String, String>();

		map.put("Green", "#00BF99");
		map.put("FireBrick", "#800517");
		map.put("Blue", "#4D4DFF");
		map.put("Aqua", "#DCDCDC");
		map.put("Red", "#df053e");
		map.put("Violet", "#8D38C9");

		return map;
	}

	/**
	 * function of month
	 * 
	 * @return map
	 */
	@ModelAttribute("month")
	public Map<Integer, String> month() {
		Month month = new Month();
		Map<Integer, String> monthmap = month.month();

		return monthmap;
	}

	/**
	 * function of no of days
	 * 
	 * @return map
	 */
	@ModelAttribute("days")
	public Map<Integer, Integer> days() {
		// read month
		Map<Integer, Integer> daymap = new LinkedHashMap<Integer, Integer>();

		for (int i = 1; i < 32; i++) {
			daymap.put(i, i);
		}

		return daymap;
	}

	/**
	 * function of endMonth
	 * 
	 * @return map
	 */
	@ModelAttribute("endmonth")
	public Map<Integer, String> endmonth() {
		Month month = new Month();
		Map<Integer, String> monthmap = month.month();

		return monthmap;
	}

	/**
	 * function for Days
	 * 
	 * @return map
	 */
	@ModelAttribute("enddays")
	public Map<Integer, Integer> enddays() {
		Map<Integer, Integer> endday = new LinkedHashMap<Integer, Integer>();

		for (int i = 1; i < 32; i++) {
			endday.put(i, i);
		}

		return endday;
	}

	@RequestMapping(value = "/getSeasonCalendar", method = RequestMethod.GET)
	public @ResponseBody String getSeasonCalendar(HttpSession session) {
		String data = "[{0={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green, 31=green}, 1={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=red}, 2={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green, 31=green}, 3={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green}, 4={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green, 31=green}, 5={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green}, 6={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green, 31=green}, 7={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green, 31=green}, 8={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green}, 9={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green, 31=green}, 10={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green}, 11={1=green, 2=green, 3=green, 4=green, 5=green, 6=green, 7=green, 8=green, 9=green, 10=green, 11=green, 12=green, 13=green, 14=green, 15=green, 16=green, 17=green, 18=green, 19=green, 20=green, 21=green, 22=green, 23=green, 24=green, 25=green, 26=green, 27=green, 28=green, 29=green, 30=green, 31=green}}]";

		return data;
	}

	@RequestMapping(value = "/seasonCalendar", method = RequestMethod.GET)
	public String seasonCalendar(Model model, HttpSession session) throws Exception {

		// year itration start
		final int ThirtyOne = 31;
		final int Thirty = 30;
	//	final int FebNorm = 28;
		final int FebLeap = 29;
	//	int numOfSundays = 0;
		int calendar[][] = new int[12][];

		calendar[0] = new int[ThirtyOne];
		calendar[1] = new int[FebLeap];
		calendar[2] = new int[ThirtyOne];
		calendar[3] = new int[Thirty];
		calendar[4] = new int[ThirtyOne];
		calendar[5] = new int[Thirty];
		calendar[6] = new int[ThirtyOne];
		calendar[7] = new int[ThirtyOne];
		calendar[8] = new int[Thirty];
		calendar[9] = new int[ThirtyOne];
		calendar[10] = new int[Thirty];
		calendar[11] = new int[ThirtyOne];

		// int dayOfWeek = 1;

		List calenderObj = new ArrayList<HashMap>();
		HashMap<Integer, HashMap> monthObj = new HashMap<Integer, HashMap>();
		HashMap<Integer, String> dayObj = null;

		for (int year = 2015; year < 2016; year++) {
			for (int month = 0; month < calendar.length; month++) {
				int dayOfMonth = 1;
				int daysInMonth;
				if (month == 1) {
					daysInMonth = calendar[month].length;
				} else {
					daysInMonth = calendar[month].length;
				}
				dayObj = new HashMap<Integer, String>();
				while (dayOfMonth <= daysInMonth) {
					dayObj.put(dayOfMonth, "black");
					dayOfMonth++;
				}
				monthObj.put(month, dayObj);
			}
		}

		calenderObj.add(monthObj);

		List<Seasonhdr> seasonList = null;

		List<Seasonhdr> seasonhdrList = null;

		String filter_query = "GROUP BY season_dtl.season_hdr_id";
		seasonhdrList = seasonHdrService.getSeasonDtl(filter_query);

		filter_query = "";
		seasonList = seasonHdrService.getSeasonDtl(filter_query);

		for (Seasonhdr seasonhdr : seasonList) {

			// System.out.println(new Gson().toJson(seasonhdr));

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2016);
			cal.set(Calendar.MONTH, (seasonhdr.getStartMonth() - 1));
			cal.set(Calendar.DAY_OF_MONTH, (seasonhdr.getStartDay()));

			Date start = cal.getTime();

			// set date to last day of 2014
			cal.set(Calendar.YEAR, 2016);
			cal.set(Calendar.MONTH, (seasonhdr.getStartMonth() - 1)); // 11 = december
			cal.set(Calendar.DAY_OF_MONTH, (seasonhdr.getEndDay() + 1)); // new years eve

			Date end = cal.getTime();

			// Iterate through the two dates
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTime(start);

			while (gcal.getTime().before(end)) {
				int map_month = gcal.getTime().getMonth();
				int map_day = gcal.get(Calendar.DAY_OF_MONTH);
				if (monthObj.containsKey(map_month)) {
					dayObj = monthObj.get(map_month);
					dayObj.put(map_day, seasonhdr.getColorCode());
				}

				gcal.add(Calendar.DAY_OF_YEAR, 1);

			}

		}

		model.addAttribute("monthObj", monthObj);

		model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());

		model.addAttribute("seasonhdr", seasonhdrList);
		// year itration close

		return "seasons/season_calendar";
	}

}