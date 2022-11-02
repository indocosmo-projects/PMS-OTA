package com.indocosmo.pms.web.systemSettings.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.common.Month;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.common.model.FinancialYear;
import com.indocosmo.pms.web.common.model.RatePeriod;
import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.currency.service.CurrencyService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.shiftManagement.service.ShiftManagementService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.systemSettings.model.Company;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

@Controller
@RequestMapping("/systemSettings")
public class SystemSettingsController {

	@Autowired
	private SystemSettingsService systemSettingsService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	@Autowired
	private ShiftManagementService shiftManagementService;

	private SysPermissions permissionObj;

	private static final Logger logger = LoggerFactory.getLogger(SystemSettingsController.class);

	/**
	 * Get record from system_setting, rate_periods and currency tables. Then
	 * redirect to system settings edit page.
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String getRecord(Model model, HttpSession session, HttpServletResponse response) throws Exception {

		String pageUrl = "/system_settings/system_settings_edit";

		try {
			permissionObj = pageAccessPermissionService.getPermission(session, "MST_SYST_SETNG");
			model.addAttribute("curPagePerObj", permissionObj);
			SysPermissions permissionObjRate = pageAccessPermissionService.getPermission(session, "MST_RATE_PERD");
			model.addAttribute("ratePagePerObj", permissionObjRate);

			if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);

				ObjectMapper mapper = new ObjectMapper();
				Map<String, String> weeksMap = new LinkedHashMap<String, String>();
				Map<Integer, String> billRoundMap = new LinkedHashMap<Integer, String>();
				billRoundMap = systemSettingsService.getBillRoundings();

				SystemSettings systemSettings = systemSettingsService.getSystemSettings();
				Company company = systemSettingsService.getcompany();
				List<Currency> currencyList = currencyService.getCurrencyList();
				List<RatePeriod> ratePeriodList = systemSettingsService.getRatePeriods();
				List<FinancialYear> financialYears = systemSettingsService.getFinancialYears();
				StringBuilder currencyListJson = new StringBuilder();
				currencyListJson.append("{\"currencies\" : ");
				currencyListJson.append(mapper.writeValueAsString(currencyList));
				currencyListJson.append("}");

				weeksMap.put("SUN", "Sunday");
				weeksMap.put("MON", "Monday");
				weeksMap.put("TUE", "Tuesday");
				weeksMap.put("WED", "Wednesday");
				weeksMap.put("THU", "Thursday");
				weeksMap.put("FRY", "Friday");
				weeksMap.put("SAT", "Saturday");

				model.addAttribute("weeks", weeksMap);
				model.addAttribute("months", new Month().month());
				model.addAttribute("settings", systemSettings);
				model.addAttribute("company", company);
				model.addAttribute("currencyListJson", currencyListJson.toString());
				model.addAttribute("ratePeriods", ratePeriodList);
				model.addAttribute("finYears", financialYears);
				model.addAttribute("billRoundingList", billRoundMap);
				model.addAttribute("dateFormat", session.getAttribute("dateFormat").toString());

			} else {

				pageUrl = "access_denied/access_denied";
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getRecord ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return pageUrl;
	}

	/**
	 * update the system_settings table row. save/update the rate periods. Then
	 * redirect to system settings edit page.
	 * 
	 * @param model
	 * @param settings
	 * @param ratePeriodAsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute SystemSettings settings,
			@RequestParam(value = "companyAsJson", required = true, defaultValue = "") String companyAsJson,
			@RequestParam(value = "finYearAsJson", required = true, defaultValue = "") String finYearAsJson)
			throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			FinancialYear financialYear = null;
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			if (finYearAsJson != null && !new JsonParser().parse(finYearAsJson).isJsonNull()) {
				financialYear = gson.fromJson(new JsonParser().parse(finYearAsJson).getAsJsonObject(),
						FinancialYear.class);
			}
			// List<FinancialYear> financialYear = mapper.readValue(finYearAsJson,
			// TypeFactory.defaultInstance().constructCollectionType(List.class,
			// FinancialYear.class));
			List<Company> company = mapper.readValue(companyAsJson,
					TypeFactory.defaultInstance().constructCollectionType(List.class, Company.class));

			settings.setLastUpdTs(new Date());
			SystemSettings systemSettings1 = systemSettingsService.getSystemSettings();
			settings.setHotelDate(systemSettings1.getHotelDate());

			systemSettingsService.save(settings, financialYear, company);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : save ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return "redirect:/systemSettings/settings";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String delete(@RequestParam(value = "code") String code) {
		int id = 0;
		int count = 0;
		try {
			id = systemSettingsService.selectid(code);
			count = systemSettingsService.getCount(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String deleteStatus = "success";
		Gson g = new Gson();
		if (count == 0) {
			boolean isDelete = systemSettingsService.delete(id);
			if (isDelete) {
				deleteStatus = "status:" + deleteStatus;
			} else {
				deleteStatus = "status:error";
			}
		} else {
			deleteStatus = "status:error";
		}
		return g.toJson(deleteStatus).toString();
	}

	@RequestMapping(value = "/checkUser", method = RequestMethod.POST)
	public @ResponseBody void checkUser(@RequestParam(value = "passWord") String passWord, HttpSession session,
			HttpServletResponse response) throws IOException {
		int userId = ((User) session.getAttribute("userForm")).getId();
		String status = "failed";
		if (passWord.equals(shiftManagementService.getPassWord(userId))) {
			status = "success";

		}
		response.getWriter().print(status);

	}
}