package com.indocosmo.pms.web.agingAR.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.agingAR.dao.AgingARDAO;
import com.indocosmo.pms.web.agingAR.model.AgingAR;

@Service
@Transactional
public class AgingARServiceImp implements AgingARService {

	@Autowired
	AgingARDAO agingARDAO;

	@Override
	public List<AgingAR> getAgingARList() {
		return agingARDAO.getAgingARList();
	}

	@Override
	public List<AgingAR> getInvoiceDetails(JsonObject agingObj) {

		return agingARDAO.getInvoiceDetails(agingObj);
	}

}