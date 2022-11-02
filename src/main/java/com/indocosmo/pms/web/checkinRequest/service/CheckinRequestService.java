package com.indocosmo.pms.web.checkinRequest.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckInRequestStatus;

public interface CheckinRequestService {
	public JsonArray getAddOnDetails(int id, int checkin_no) throws Exception;

	public String processAddon(CheckInRequestStatus reqStatus) throws Exception;

	public List<CheckInRequest> getCheckInRequestList(int checkno, String source);

	public JsonArray getRoomList() throws Exception;

	public List<CheckInRequestStatus> getCheckInRequestSatusList(int requestId) throws Exception;

	public CheckInRequest getCheckInRequest(int id);

	public boolean saveAddOns(List<CheckInRequest> requestList) throws Exception;

	public boolean saveCheckinRequestStatus(CheckInRequestStatus checkinreqStatus) throws Exception;

	public boolean deleteAddOnStatus(int id) throws Exception;

	public boolean cancelAddOns(int id) throws Exception;

	public JsonArray getListRoomRequest() throws Exception;
}
