package com.indocosmo.pms.web.request.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckInRequestStatus;
import com.indocosmo.pms.web.request.dao.RequestDAO;

@Service
public class RequestServiceImp implements RequestService{
	@Autowired
	RequestDAO requestDAO;

	public static final Logger logger=LoggerFactory.getLogger(RequestServiceImp.class);

	@Transactional
	public JsonArray getRoomLists() {
		return requestDAO.getRoomLists();
	}

	@Transactional
	public JsonArray getFacilities() {
		return requestDAO.getFacilities();
	}

	@Transactional
	public boolean saveAdd(List<CheckInRequest> chreqlist)throws Exception {
		return requestDAO.saveAdd(chreqlist);
	}


	@Transactional
	public boolean delete(int id) {
		return requestDAO.delete(id);
	}


	@Transactional(readOnly = false, rollbackFor=Exception.class)
	public boolean update(CheckInRequestStatus checkInRequestStatus)throws Exception {
		return requestDAO.update(checkInRequestStatus);
	}


	@Transactional
	public JsonObject getSearchData(JsonObject jobj, Map<String, String> searchMap)throws Exception {
		return requestDAO.getSearchData(jobj,searchMap);
	}



	public String isRequestExist(int checkinnumber, int facilityid, String requstDate, String reqTime) throws Exception {
		return requestDAO.isRequestExist(checkinnumber,facilityid,requstDate,reqTime);
	}

}
