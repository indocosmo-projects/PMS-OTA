package com.indocosmo.pms.web.shiftManagement.service;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;

public interface ShiftManagementService {
	public String getPassWord(int userId);

	public boolean saveOpenShift(ShiftManagement shiftmanagement) throws Exception;

	public JsonArray getcurrentUserShift() throws Exception;

	public String shiftcountOndateRemain(String hotelDate) throws Exception;

	public JsonArray currentShiftDetails(String hotelDate) throws Exception;

	public String isshiftOPen(String hotelDate) throws Exception;

	public JsonArray getListShift(String hotelDate) throws Exception;

	public ShiftManagement getShiftManagementDetails(String hotelDate);

	public JsonArray allShiftDetails() throws Exception;;
}
