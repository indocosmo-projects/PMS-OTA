package com.indocosmo.pms.web.reservation_test.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.common.CustomizedException;
import com.indocosmo.pms.web.reservation.model.Cancelreason;
import com.indocosmo.pms.web.reservation_test.model.AvailableRoomTypes;
import com.indocosmo.pms.web.reservation_test.model.ResvDtl;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.reservation_test.model.ResvRoom;
import com.indocosmo.pms.web.reservation_test.model.ResvRoomRatePlans;
import com.indocosmo.pms.web.reservation_test.model.RoomRateDetailsCheck;
import com.indocosmo.pms.web.reservation_test.model.RoomRateEdited;
import com.indocosmo.pms.web.room.model.Room;

public interface ReservationDaoTest {

	public List<AvailableRoomTypes> getRoomAvailability(Date chInDate, int night, int rooms);

	public List<ResvRoomRatePlans> getRoomRates(Date arrDate, int nights, int corpId, String roomType, int rate_id);

	public JsonObject getRoomRateDetails(RoomRateDetailsCheck rateDtls);

	public boolean save(String resvHdr, List<ResvDtl> resvDtlList, JsonArray checkinDiscount) throws Exception;

	public ResvRoom getRoomData(int resvRoomNo);

	public List<Room> getAvailableRoomData(Date minArrDate, Date maxDepartDate, int roomTypeId, int occupancy);

	public boolean updateResvRoom(ResvRoom resvRoom) throws Exception;

	public HashMap<String, Object> getResvRecord(int resvNo) throws Exception;

	public ResvHdr getResvHdr(int resvNo) throws Exception;

	public boolean cancellationSave(ResvHdr resv, List<ResvRoom> resvRoomList, int noCancelCount, int checkStatus)
			throws CustomizedException;

	public ResvHdr getRecord(int reservationNo);

	public JsonArray getCalendarData(Date checkinDate, int nights) throws Exception;

	public boolean saveReservationEdit(ResvHdr resvHdrNew, List<RoomRateDetailsCheck> resvDtlListNew,
			List<ResvRoom> resvRoomListNew, JsonObject changesObj, JsonArray checkinDiscount) throws Exception;

	// public JsonObject getListData(JsonObject
	// jbj,Map<String,String>advanceSearchMap,Map<String,String>simpleSearchMap)
	// throws Exception;
	public JsonObject getResvDtlList(int resvId) throws Exception;

	public JsonObject getNewArrivalDateData(int resvNo, java.sql.Date sqlDate, int discType, int noNight, int resvType);

	public boolean saveChangeArrivalDate(int resvNo, java.util.Date arrDate,
			List<RoomRateDetailsCheck> roomRateDetailsList, JsonArray disc) throws Exception;

	public JsonArray getReservationData(String wherePart) throws Exception;

	public JsonObject getResvRecordDetails(int resvNo);

	public boolean saveNoShow(int resvNo, List<ResvRoom> resvRoomList, int noShowCount, int checkStatus)
			throws Exception;

	public boolean reasonSave(Cancelreason creason);

	public JsonArray getCountryList();

	public JsonArray detailViaPhonenumResv(String phonenum) throws Exception;

	public JsonArray getResvStatus();

	public JsonArray updateNewRoomRates(List<RoomRateEdited> list) throws Exception;

	public List<RoomRateDetailsCheck> getRoomRateForAllOccupancy(Date arrDate, int night, int rateid, String roomType);

	public JsonArray fetchCustomerData(String salutation, String firstName, String lastName) throws SQLException;

	public JsonObject getListData(JsonObject jbj) throws Exception;

	public void deletePickUp(int resvNo);

	public JsonArray getGrcData(int resvNo);

	public JsonArray getBookingData(int resvNo)throws Exception;

	
}
