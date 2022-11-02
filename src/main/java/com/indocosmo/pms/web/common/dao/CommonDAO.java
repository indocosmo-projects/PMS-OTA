package com.indocosmo.pms.web.common.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.common.model.CommunicationDetails;

public interface CommonDAO {

	Boolean getStatus(String table, String fieldName, int taxTypeId, String getFieldName);

	int getCount(String tableName, String fieldName, int value, int id);

	public boolean saveCommunicationDetails(CommunicationDetails commDtls) throws Exception;

	public List<CommunicationDetails> getCommunicationData(int id, String group) throws Exception;

	public boolean saveCommunicationDetailsMail(CommunicationDetails commDtlMail) throws Exception;

	public JsonArray getStateList(String nationality) throws Exception;

}
