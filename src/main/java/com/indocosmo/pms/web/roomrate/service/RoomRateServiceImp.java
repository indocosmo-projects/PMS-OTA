package com.indocosmo.pms.web.roomrate.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.model.RateDist;
import com.indocosmo.pms.web.common.model.RateDtl;
import com.indocosmo.pms.web.common.model.RateHdr;
import com.indocosmo.pms.web.roomType.model.RoomType;
import com.indocosmo.pms.web.roomrate.dao.RoomRateDAO;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;

@Service
public class RoomRateServiceImp implements RoomRateService {

	@Autowired
	private RoomRateDAO roomRateDAO;

	public static final Logger logger = LoggerFactory.getLogger(RoomRateServiceImp.class);

	/**
	 * Room rate List function
	 * 
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal
	 * @param simpleSearch
	 * @return jsonData
	 * @throws Exception
	 **/
	@Transactional
	public JqGridListWrapperDTO list(String currentPage, String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal, Map<String, String> simpleSearchMap)
			throws Exception {
		int startRow = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowLimit);
		JqGridListWrapperDTO jqGridListWrapper = new JqGridListWrapperDTO();
		jqGridListWrapper.setPage(Integer.parseInt(currentPage));

		List<RateHdr> listOfRateHdr = roomRateDAO.list(startRow, Integer.parseInt(rowLimit), advanceSearchMap, sortVal,
				simpleSearchMap);
		Encryption encryption;

		try {
			encryption = new Encryption();

			for (RateHdr rateHdrObj : listOfRateHdr) {
				rateHdrObj.setEncryption(encryption.encrypt(Integer.toString(rateHdrObj.getId())));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		jqGridListWrapper.setRows(listOfRateHdr);

		try {
			jqGridListWrapper.setTotal(this.getCount(advanceSearchMap, simpleSearchMap));
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		jqGridListWrapper.setStartLimit(startRow);
		jqGridListWrapper.setEndLimit(Integer.parseInt(rowLimit));
		jqGridListWrapper.setPagingStart(pagingStart);

		return jqGridListWrapper;
	}

	/**
	 * Room rate save function
	 * 
	 * @param rateHdr
	 *            model
	 * @return true when saving is success/ false when saving is not success
	 */
	@Transactional
	public boolean save(RateHdr rateHdr) throws Exception {
		List<RateDtl> editRecordList = rateHdr.getRateDetails();

		for (int i = 0; i < editRecordList.size(); i++) {
			editRecordList.get(i).setRateHdr(rateHdr);
		}

		List<RateDist> editDistRecordList = rateHdr.getRateDistribution();

		for (int i = 0; i < editDistRecordList.size(); i++) {
			editDistRecordList.get(i).setRateHdr(rateHdr);
		}

		boolean isRateHdrSave = roomRateDAO.save(rateHdr);

		return isRateHdrSave;
	}

	/**
	 * Single record access function
	 * 
	 * @param rateHdrId
	 * @return RateHdr model
	 * @throws Exception
	 */
	@Transactional
	public RateHdr getRecord(int rateHdrId) throws Exception {
		RateHdr rateHdr = roomRateDAO.getRecord(rateHdrId);
		List<RateDist> editDistRecordList = rateHdr.getRateDistribution();

		for (int i = 0; i < editDistRecordList.size(); i++) {
			if (editDistRecordList.get(i).getIsDeleted() == true) {
				editDistRecordList.remove(i);
				i--;
			}
		}

		List<RateDtl> editDltsRecordList = rateHdr.getRateDetails();

		for (int i = 0; i < editDltsRecordList.size(); i++) {
			if (editDltsRecordList.get(i).getIsDeleted() == true) {
				editDltsRecordList.remove(i);
				i--;
			}
		}

		return rateHdr;
	}

	/**
	 * Room rate delete function
	 * 
	 * @param rateHdrIds
	 * @return boolean
	 * @throws Exception
	 */
	@Transactional
	public boolean delete(int rateHdrIds) throws Exception {
		boolean isDeleted = roomRateDAO.delete(rateHdrIds);

		return isDeleted;
	}

	/**
	 * Code exist checking function
	 * 
	 * @param code
	 * @return boolean
	 */
	@Transactional
	public boolean codeExist(String code) throws Exception {
		return roomRateDAO.codeExist(code);
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
		int count = roomRateDAO.getCount(searchContent, simpleSearchMap);

		return count;
	}

	/**
	 * Room rate list function
	 * 
	 * @return map containing room rates
	 * @throws Exception
	 */
	@Transactional
	public Map<Integer, String> list() throws Exception {
		Map<Integer, String> listOfRateHdr = roomRateDAO.list();

		return listOfRateHdr;
	}

	/**
	 * Room type list function
	 * 
	 * @return map containing room types
	 * @throws Exception
	 */
	@Transactional
	public Map<Integer, String> roomTypeListMap() throws Exception {
		Map<Integer, String> listOfRoomType = roomRateDAO.roomTypeListMap();

		return listOfRoomType;
	}

	/**
	 * room details function
	 * 
	 * @param roomId
	 * @return single room type record
	 * @throws Exception
	 */
	@Transactional
	public RoomType roomDetails(int roomId) throws Exception {
		return roomRateDAO.roomDetails(roomId);
	}

	/**
	 * department list function
	 * 
	 * @return map containing department
	 * @throws Exception
	 */
	@Transactional
	public Map<Integer, String> departmentTypeListMap() throws Exception {
		Map<Integer, String> listOfdepartmentType = roomRateDAO.departmentTypeListMap();

		return listOfdepartmentType;
	}

}