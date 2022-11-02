package com.indocosmo.pms.web.floor.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.floor.model.Floor;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.floor.dao.FloorDAO;

@Service
@Transactional
public class FloorServiceImp implements FloorService {

	@Autowired
	private FloorDAO floorDAO;
	
	public static final Logger logger = LoggerFactory.getLogger(FloorServiceImp.class);

	/**
	 * Floor save function
	 * @param currency model
	 * @return   
	 */
	@Transactional
	public boolean save(Floor floor) throws Exception {
		boolean isSave = floorDAO.save(floor);

		return  isSave;
	}

	/**
	 * Floor delete function
	 * @param id 
	 * @return
	 */
	@Transactional
	public boolean delete(int id) throws Exception {
		boolean isDeleted = floorDAO.delete(id);

		return isDeleted;
	}

	/**
	 * Single record access function
	 * @param id 
	 * return currency model
	 */
	@Transactional
	public Floor getRecord(int id) throws Exception {
		return floorDAO.getRecord(id);
	}

	/**
	 * FloorList function
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal      
	 * @param simpleSearch
	 * @return  jsonData
	 **/ 
	@Transactional
	public JqGridListWrapperDTO list(String currentPage, String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal, Map<String, String> simpleSearchMap)throws Exception {
		int startRow = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowLimit);
		JqGridListWrapperDTO jqGridListWrapper = new JqGridListWrapperDTO();
		jqGridListWrapper.setPage(Integer.parseInt(currentPage));
		List<Floor> floorList = floorDAO.list(startRow, Integer.parseInt(rowLimit), advanceSearchMap, sortVal, simpleSearchMap);
		Encryption encryption;
		
		try {
			encryption = new Encryption();
			
			for(Floor floor : floorList) {
				floor.setEncryption(encryption.encrypt(Integer.toString(floor.getId())));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : list " + Throwables.getStackTraceAsString(ex));
			throw new CustomException();
		}

		jqGridListWrapper.setRows(floorList);
		jqGridListWrapper.setTotal(this.getCount(advanceSearchMap, simpleSearchMap));
		jqGridListWrapper.setStartLimit(startRow);
		jqGridListWrapper.setEndLimit(Integer.parseInt(rowLimit));
		jqGridListWrapper.setPagingStart(pagingStart);

		return jqGridListWrapper;
	}

	/**
	 * total count function
	 * @param simpleSearchMap
	 * @param advanceSearchMap
	 * @return total count
	 */
	@Transactional
	public int getCount(Map<String, String> searchContent, Map<String, String> simpleSearchMap) throws Exception {
		int record = floorDAO.getCount(searchContent, simpleSearchMap);

		return record;
	}

	/**
	 * Code exist checking function
	 * @param code
	 * @return boolean 
	 */

	public boolean codeExist(String code) throws Exception {
		return floorDAO.codeExist(code);
	}

	/**
	 * returns a map as <id, name>
	 */
	@Transactional
	public Map<Integer, String> getNameIdMap() throws Exception {
		return floorDAO.getNameIdMap();
	}

	@Override
	public Floor canDelete(int id) {
		return floorDAO.canDelete(id);
	}

	@Override
	public int getCountOfFloor(int id) {
		return floorDAO.getCountOfFloor(id);
	}
}