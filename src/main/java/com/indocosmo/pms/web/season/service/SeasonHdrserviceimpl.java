package com.indocosmo.pms.web.season.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.season.dao.SeasonDao;
import com.indocosmo.pms.web.season.model.Seasondtl;
import com.indocosmo.pms.web.season.model.Seasonhdr;

@Service
public class SeasonHdrserviceimpl implements SeasonHdrService {

	@Autowired
	private SeasonDao seasonHdrDao;

	public static final Logger logger = LoggerFactory.getLogger(SeasonHdrserviceimpl.class);

	/**
	 * CurrecyList function
	 * 
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal
	 * @param simpleSearch
	 * @return jsonData
	 * @throws Exception
	 * @throws NumberFormatException
	 **/

	@Transactional
	public JqGridListWrapperDTO list(String currentPage, String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal, Map<String, String> simpleSearchMap)
			throws NumberFormatException, Exception {
		int startRow = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowLimit);
		JqGridListWrapperDTO jqGridListWrapper = new JqGridListWrapperDTO();
		jqGridListWrapper.setPage(Integer.parseInt(currentPage));
		List<Seasonhdr> listOfSeason = seasonHdrDao.list(startRow, Integer.parseInt(rowLimit), advanceSearchMap,
				sortVal, simpleSearchMap);
		Encryption encryption;

		try {
			encryption = new Encryption();

			for (Seasonhdr sea : listOfSeason) {
				sea.setEncryption(encryption.encrypt(Integer.toString(sea.getId())));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		jqGridListWrapper.setRows(listOfSeason);
		jqGridListWrapper.setTotal(this.totalRecord(advanceSearchMap, simpleSearchMap));
		jqGridListWrapper.setStartLimit(startRow);
		jqGridListWrapper.setEndLimit(Integer.parseInt(rowLimit));
		jqGridListWrapper.setPagingStart(currentPage);

		return jqGridListWrapper;
	}

	/**
	 * total count function
	 * 
	 * @param simpleSearchMap
	 * @param advanceSearchMap
	 * @return total count
	 */
	@Transactional
	public int totalRecord(Map<String, String> advanceSearchMap, Map<String, String> simpleSearchMap) {
		int count = seasonHdrDao.totalRecord(advanceSearchMap, simpleSearchMap);

		return count;
	}

	/**
	 * Season save function
	 * 
	 * @param Seasonhdr
	 *            model
	 * @return true when saving is success/ false when saving is not success
	 */
	@Transactional
	public boolean save(Seasonhdr seasonHdrForm) throws Exception {
		/*
		 * List < Seasondtl > editSeasonList = seasonHdrForm.getSeasondetails(); int
		 * startMonth = seasonHdrForm.getStartMonth(); int startDay =
		 * seasonHdrForm.getStartDay();
		 * 
		 * int endMonth = seasonHdrForm.getEndMonth(); int endDay =
		 * seasonHdrForm.getEndDay(); int eom = 31; Seasondtl seasondtl = null;
		 * 
		 * for(int i = startMonth; i <= endMonth; i++) { seasondtl = new Seasondtl();
		 * seasondtl.setSeason_hdr(seasonHdrForm); seasondtl.setCalmonth(i);
		 * 
		 * if(i == startMonth) { seasondtl.setStartday(startDay); } else {
		 * seasondtl.setStartday(1); }
		 * 
		 * if(i == endMonth) { seasondtl.setEndday(endDay); } else {
		 * seasondtl.setEndday(eom); }
		 * 
		 * editSeasonList.add(seasondtl); }
		 */

		// System.out.println("seasonHdrForm");
		List<Seasondtl> editSeasonList = seasonHdrForm.getSeasondetails();
		Seasondtl seasondtl = null;

		int startMonth = seasonHdrForm.getStartMonth();
		int startDay = seasonHdrForm.getStartDay();

		int endMonth = seasonHdrForm.getEndMonth();
		endMonth = endMonth + 1;
		int endDay = seasonHdrForm.getEndDay();

		int startYear = 2016;
		int endYear = 2016;
		if (startMonth > endMonth || endMonth == startMonth) {
			endYear = 2017;
		}

		String date1 = "" + startMonth + " " + startYear;
		String date2 = "" + endMonth + " " + endYear;

		DateFormat formater = new SimpleDateFormat("MM yyyy");

		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();
		Calendar finishCalendarTemp = Calendar.getInstance();

		try {
			beginCalendar.setTime(formater.parse(date1));
			finishCalendar.setTime(formater.parse(date2));
			finishCalendarTemp.setTime(formater.parse(date2));
			finishCalendarTemp.add(Calendar.MONTH, -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String finishCalendarTempStr = formater.format(finishCalendarTemp.getTime()).toUpperCase();
		int cnt = 0;
		int month;
		while (beginCalendar.before(finishCalendar)) {

			month = beginCalendar.get(Calendar.MONTH);
			month++;

			seasondtl = new Seasondtl();
			seasondtl.setSeason_hdr(seasonHdrForm);
			seasondtl.setCalmonth(month);

			if (cnt == 0) {
				seasondtl.setStartday(startDay);
			} else {
				seasondtl.setStartday(1);
			}

			String date = formater.format(beginCalendar.getTime()).toUpperCase();
			// System.out.println(date+"-dates--"+finishCalendarTempStr);

			if (date.endsWith(finishCalendarTempStr)) {
				seasondtl.setEndday(endDay);
			} else {

				int mend = beginCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				if (mend == 28) {
					mend = 29;
				}
				seasondtl.setEndday(mend);
			}

			editSeasonList.add(seasondtl);

			beginCalendar.add(Calendar.MONTH, 1);
			cnt++;
		}

		// System.out.println("seasonHdrForm 2");

		boolean isseasonSave = seasonHdrDao.save(seasonHdrForm);

		return isseasonSave;
	}

	/**
	 * Single record access function
	 * 
	 * @param seasonId
	 *            return Seasonhdr model
	 */
	@Transactional
	public Seasonhdr getRecord(int seasonId) throws Exception {
		return seasonHdrDao.getRecord(seasonId);
	}

	/**
	 * delete function
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean delete(int seasonIds) throws Exception {
		boolean isDeleted = seasonHdrDao.delete(seasonIds);

		return isDeleted;
	}

	/**
	 * total count function
	 * 
	 * @param simpleSearchMap
	 * @param advanceSearchMap
	 * @return total count
	 * @throws Exception
	 */
	@Transactional
	public int getCount(Map<String, String> searchContent, Map<String, String> simpleSearchMap) throws Exception {
		int count = seasonHdrDao.getCount(searchContent, simpleSearchMap);

		return count;
	}

	/**
	 * Validation with dates function
	 * 
	 * @param startDate
	 *            endDate
	 * @return boolean
	 */
	@Transactional
	public boolean getDates(String startDate, String endDate, int id) {
		boolean dates = seasonHdrDao.getDates(startDate, endDate, id);

		return dates;
	}

	/**
	 * return season names and ids as a list
	 */
	@Transactional
	public ArrayList<ArrayList<String>> getSeasonNamesWithId() {
		return seasonHdrDao.getSeasonNamesWithId();
	}

	/**
	 * season list function
	 * 
	 * @return map containing seasons
	 * @throws Exception
	 */
	@Transactional
	public List<Seasonhdr> seasonList() throws Exception {
		List<Seasonhdr> listOfSeason = seasonHdrDao.seasonList();

		return listOfSeason;
	}

	/**
	 * Code exist checking function
	 * 
	 * @param code
	 * @return boolean
	 */
	@Transactional
	public boolean codeExist(String code) throws Exception {
		return seasonHdrDao.codeExist(code);
	}

	@Transactional
	public List<Seasonhdr> getSeasonDtl(String filter_query) {
		// TODO Auto-generated method stub
		return seasonHdrDao.getSeasonDtl(filter_query);
	}

}