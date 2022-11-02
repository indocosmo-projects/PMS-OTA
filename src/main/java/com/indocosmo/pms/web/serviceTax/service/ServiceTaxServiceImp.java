package com.indocosmo.pms.web.serviceTax.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indocosmo.pms.web.serviceTax.dao.ServiceTaxDAO;
import com.indocosmo.pms.web.serviceTax.model.serviceTax;

@Service
public class ServiceTaxServiceImp implements ServiceTaxService {

	@Autowired
	ServiceTaxDAO serviceTaxDAO; 
	
	@Transactional
	public serviceTax getServiceTaxDetails()throws Exception{
		return serviceTaxDAO.getServiceTaxDetails();
	}
	
	@Transactional
	public boolean saveInvoice(serviceTax serviceTax) throws Exception{
		return serviceTaxDAO.saveInvoice(serviceTax);
	}
	
	@Transactional
	public int getActiveServiceTax(){
		return serviceTaxDAO.getActiveServiceTax();
	}
}
