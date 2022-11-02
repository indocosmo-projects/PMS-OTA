package com.indocosmo.pms.web.cform.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.cform.dao.CFormDAO;


@Service
public class CFormServiceImp implements CFormService {

	@Autowired
	CFormDAO cformDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pms.web.cform.service.CformService#getDetailsCustomer(int)
	 */
	@Transactional
	public JsonObject getDetailsCustomer(int folioNo) {
		return cformDAO.getDetailsCustomer(folioNo);
	}
}
