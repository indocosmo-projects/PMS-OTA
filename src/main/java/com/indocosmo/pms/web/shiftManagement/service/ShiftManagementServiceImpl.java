package com.indocosmo.pms.web.shiftManagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.shiftManagement.dao.ShiftManagementDAO;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;

@Service
public class ShiftManagementServiceImpl implements ShiftManagementService {

	@Autowired
	ShiftManagementDAO shiftManagementDAO;
	public static final Logger logger = LoggerFactory.getLogger(ShiftManagementServiceImpl.class);

	public String getPassWord(int userId) {
		// TODO Auto-generated method stub
		return shiftManagementDAO.getPassWord(userId);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean saveOpenShift(ShiftManagement shiftmanagement) throws Exception {

		// TODO Auto-generated method stub
		return shiftManagementDAO.saveOpenShift(shiftmanagement);
	}

	@Transactional
	public JsonArray getcurrentUserShift() throws Exception {
		// TODO Auto-generated method stub
		return shiftManagementDAO.getcurrentUserShift();
	}

	@Transactional
	public JsonArray currentShiftDetails(String hotelDate) throws Exception {
		// TODO Auto-generated method stub
		return shiftManagementDAO.currentShiftDetails(hotelDate);
	}

	@Transactional
	public String isshiftOPen(String hotelDate) throws Exception {
		// TODO Auto-generated method stub
		return shiftManagementDAO.isshiftOPen(hotelDate);
	}

	@Transactional
	public JsonArray getListShift(String hotelDate) throws Exception {
		// TODO Auto-generated method stub
		return shiftManagementDAO.getListShift(hotelDate);
	}

	@Transactional
	public ShiftManagement getShiftManagementDetails(String hotelDate) {
		// TODO Auto-generated method stub
		return shiftManagementDAO.getShiftManagementDetails(hotelDate);
	}

	@Transactional
	public String shiftcountOndateRemain(String hotelDate) throws Exception {
		// TODO Auto-generated method stub
		return shiftManagementDAO.shiftcountOndateRemain(hotelDate);
	}

	@Transactional
	public JsonArray allShiftDetails() throws Exception {
		// TODO Auto-generated method stub
		return shiftManagementDAO.allShiftDetails();
	}

}
