package com.indocosmo.pms.web.serviceTax.service;

import com.indocosmo.pms.web.serviceTax.model.serviceTax;

public interface ServiceTaxService {

	public serviceTax getServiceTaxDetails()throws Exception;
	public boolean saveInvoice(serviceTax serviceTax) throws Exception;
	public int getActiveServiceTax();
}
