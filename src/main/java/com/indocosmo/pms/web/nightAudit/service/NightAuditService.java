package com.indocosmo.pms.web.nightAudit.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.transaction.model.Transaction;

public interface NightAuditService {
public JsonArray getArrivalList();
public JsonArray getDepartList();
public JsonArray getInHouseList(String hotelDate);
public JsonObject getRoomRateForCurrentDate(String hotelDate,int checkInNo);
public void setNightAuditStage(int stage);
public boolean changeHotelDate(String newHotDate);
public String postNightAudit(Transaction txn);
public JsonArray getNightAuditTransactions(String hotelDate);
public JsonObject getReceptionCount(String hotelDate);
public List<ResvHdr> getRecordToCancel();
public boolean cancelNonNoShowRooms(int userid) throws Exception;
public String confirmTransactions(String hotelDate,int userId);
public int getlastShiftDetail(String prevHotelDate);
public boolean extendStayOneNight(List<CheckInHdr> chkinHdrs) throws Exception;
}
