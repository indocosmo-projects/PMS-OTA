package com.indocosmo.pms.web.checkIn.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.checkIn.model.CheckInDtl;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkIn.model.CheckInRate;
import com.indocosmo.pms.web.checkIn.model.CheckinType;
import com.indocosmo.pms.web.checkIn.model.PaymentType;

public interface CheckInService {
	public int getTotalCheckInRooms(int checkInId) throws Exception;

	public String save(CheckInHdr checkInHdr, CheckInDtl checkInDtl, List<CheckInRate> CheckInRateArray)
			throws Exception;

	public JsonArray getCheckInData(int resvNo) throws Exception;

	public JsonObject getCheckInDetails(int resvRoomNo, String minArrDate, String maxDepartDate, int roomTypeId,
			int occupancy) throws Exception;

	public JsonObject getReservationRateDetails(int resvRoomNo) throws Exception;

	public List<CheckInRate> getRoomCharge(int checkinNo);

	public List<CheckinType> getCheckinTypes() throws Exception;

	public JsonArray getCustomers();

	public JsonArray loadData(String customer);

	public JsonObject loadDataByMail(String mail);

	public List<PaymentType> getPaymentTypes();

	public void changeRoomStatus(int id);

	public void updateGroupCheckin(int roomId, String roomnumber) throws Exception;

	

}
