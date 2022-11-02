package com.indocosmo.pms.web.reception.service;

import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.checkIn.model.CheckInDtl;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkIn.model.CheckInRate;
import com.indocosmo.pms.web.reservation_test.model.RoomRateEdited;

public interface ReceptionService {

	public boolean saveNewCheckin(List<CheckInHdr> checkinHd, List<CheckInDtl> checkinDtl,
			List<CheckInRate> checkinRate, JsonArray checkinDiscount) throws Exception;

	public JsonObject getCheckInNO(String roomNo) throws Exception;

	public boolean uploadCustomerFiles(JsonArray saveImageArray, JsonArray saveProofArray) throws Exception;

	public HashMap<String, Object> getCheckInDetails(int checkInNo) throws Exception;

	public boolean updateReceptionEdit(JsonObject jobj, String hotelDate) throws Exception;

	public JsonArray getInHouseList() throws Exception;

	public String mailCheckInSave(JsonArray jArray);

	public JsonArray customerDetailViaPhone(String phone) throws Exception;

	public int newRoomRateIds(List<RoomRateEdited> list) throws Exception;

	public JsonObject getListData(JsonObject jobject) throws Exception;

	public void changeRoomStatus(int roomId);

	public JsonArray getGrcFormData(int recpNo);

	

}
