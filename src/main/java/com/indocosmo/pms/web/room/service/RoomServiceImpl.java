package com.indocosmo.pms.web.room.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.floor.model.Floor;
import com.indocosmo.pms.web.room.dao.RoomDAO;
import com.indocosmo.pms.web.room.model.Room;
import com.indocosmo.pms.web.room.model.RoomFeature;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomDAO roomDAO;

	public static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

	/**
	 * Room save/update method
	 * 
	 * @param room
	 *            Room model
	 * @param newRecord
	 *            boolean value to identify whether the record is new or not
	 * @return true if saved successfully. Else false
	 */

	public boolean save(Room room, boolean newRecord) throws Exception {
		return roomDAO.save(room, newRecord);
	}

	/**
	 * Delete a record form room table(soft deletion)
	 * 
	 * @param id
	 *            id of record to be delete.
	 * @return true if successful. Else false
	 */
	@Transactional
	public boolean delete(int id) throws Exception {
		boolean isDeleted = roomDAO.delete(id);
		return isDeleted;
	}

	/**
	 * Fetch Room record form room table
	 * 
	 * @param id
	 *            the id of record to fetch
	 * @return Room object corresponding to the id
	 */
	/*@Transactional
	public Room getRecord(int id) throws Exception {
		return roomDAO.getRecord(id);
	}*/
	
	@Transactional
	public Room getRecord(String id) throws Exception {
		return roomDAO.getRecord(id);
	}

	/**
	 * DepartmentList function
	 * 
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal
	 * @param simpleSearch
	 * @return jsonData
	 **/
	@Transactional
	public JqGridListWrapperDTO list(String currentPage, String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal, Map<String, String> simpleSearchMap)
			throws Exception {
		int startRow = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowLimit);
		JqGridListWrapperDTO jqGridListWrapper = new JqGridListWrapperDTO();
		jqGridListWrapper.setPage(Integer.parseInt(currentPage));
		List<Room> roomList = roomDAO.list(startRow, Integer.parseInt(rowLimit), advanceSearchMap, sortVal,
				simpleSearchMap);
		Encryption encryption;

		try {
			encryption = new Encryption();
			for (Room room : roomList) {
				room.setEncryption(encryption.encrypt(room.getNumber()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : list, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		jqGridListWrapper.setRows(roomList);
		jqGridListWrapper.setTotal(this.getCount(advanceSearchMap, simpleSearchMap));
		jqGridListWrapper.setStartLimit(startRow);
		jqGridListWrapper.setEndLimit(Integer.parseInt(rowLimit));
		jqGridListWrapper.setPagingStart(pagingStart);

		return jqGridListWrapper;
	}

	/**
	 * Get total count of records according to the search params
	 * 
	 * @param searchContent
	 * @param simpleSearchMap
	 * @return total count according to the search params
	 */
	public int getCount(Map<String, String> searchContent, Map<String, String> simpleSearchMap) throws Exception {
		return roomDAO.getCount(searchContent, simpleSearchMap);
	}

	/**
	 * Check whether the room number exist or not
	 * 
	 * @param roomNo
	 * @return false if the roomNo not exist. Else true
	 */
	public boolean roomNumExist(String roomNo) throws Exception {
		return roomDAO.roomNumExist(roomNo);
	}

	/**
	 * Fetch all the room feature from room_feature table
	 * 
	 * @return a list containing RoomFeature
	 */
	public List<RoomFeature> getRoomFeatures() throws Exception {
		return roomDAO.getRoomFeatures();
	}

	/**
	 * Check whether the feature exist or not.
	 * 
	 * @param feature
	 * @return true if the feature exist. Else false.
	 */
	public boolean featureExist(RoomFeature roomFeature) throws Exception {
		return roomDAO.featureExist(roomFeature);
	}

	/**
	 * RoomFeature save
	 * 
	 * @param roomFeature
	 * @return true if the feature saved successfully.
	 */
	public boolean addFeature(RoomFeature roomFeature) throws Exception {
		return roomDAO.addFeature(roomFeature);
	}

	public boolean deleteFeature(String id) throws Exception {
		return roomDAO.deleteFeature(id);
	}

	@Override
	public List<Floor> getFloor() throws Exception {
		return roomDAO.getFloor();
	}
}