package com.indocosmo.pms.web.checkinRequest.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckInRequestStatus;

public interface CheckinRequestDAO {
	public JsonArray getAddOnDetails(int id, int checkin_no) throws Exception;

	public JsonArray getListRoomRequest() throws Exception;

	public boolean processAddon(CheckInRequestStatus reqStatus) throws Exception;

	public boolean saveAddOns(List<CheckInRequest> requestList) throws Exception;

	public List<CheckInRequest> getCheckInRequestList(int checkno, String source);

	public CheckInRequest getCheckInRequest(int id);

	public JsonArray getRoomList() throws Exception;

	public List<CheckInRequestStatus> getCheckInRequestStatusList(int requestId) throws Exception;

	public boolean deleteAddOnStatus(int id) throws Exception;

	public boolean cancelAddOns(int id) throws Exception;
}
