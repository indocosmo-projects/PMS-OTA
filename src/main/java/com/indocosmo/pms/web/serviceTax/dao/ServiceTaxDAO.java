package com.indocosmo.pms.web.serviceTax.dao;

import com.indocosmo.pms.web.serviceTax.model.serviceTax;

public interface ServiceTaxDAO {
	public serviceTax getServiceTaxDetails()throws Exception;
	public boolean saveInvoice(serviceTax serviceTax) throws Exception;
	public int getActiveServiceTax();
}
