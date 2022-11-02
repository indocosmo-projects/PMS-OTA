package com.indocosmo.pms.web.shiftManagement.dao;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;

public interface ShiftManagementDAO {

	String getPassWord(int userId);

	boolean saveOpenShift(ShiftManagement shiftmanagement) throws Exception;

	JsonArray getcurrentUserShift() throws Exception;

	JsonArray currentShiftDetails(String hotelDate) throws Exception;

	String isshiftOPen(String hotelDate) throws Exception;

	JsonArray getListShift(String hotelDate) throws Exception;

	ShiftManagement getShiftManagementDetails(String hotelDate);

	String shiftcountOndateRemain(String hotelDate) throws Exception;

	JsonArray allShiftDetails() throws Exception;
}
