package com.indocosmo.pms.web.agingAR.service;

import java.util.List;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.agingAR.model.AgingAR;

public interface AgingARService {

	List<AgingAR> getAgingARList();

	List<AgingAR> getInvoiceDetails(JsonObject agingObj);

}
