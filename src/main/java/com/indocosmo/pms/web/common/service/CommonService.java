
package com.indocosmo.pms.web.common.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.common.model.CommunicationDetails;

public interface CommonService {

	Boolean getStatus(String table, String fieldName, int taxTypeId, String getFieldName);

	int getCount(String table, String fieldName, int taxTypeId, int acmId);

	public boolean saveCommunicationDetails(CommunicationDetails commDtls) throws Exception;

	public JsonArray getCommunicationGuestDetails(JsonObject jObj) throws Exception;

	public JsonObject getCheckInguestData(int checkInNo) throws Exception;

	public JsonObject getReservationGuestData(int resvNo) throws Exception;

	public List<CommunicationDetails> getCommunicationData(int id, String group) throws Exception;

	public boolean saveCommunicationDetailsMail(CommunicationDetails commDtlMail) throws Exception;

	public JsonArray getStateList(String nationality) throws Exception;
}
