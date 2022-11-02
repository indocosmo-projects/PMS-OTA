package com.indocosmo.pms.web.agingAR.dao;

import java.util.List;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.agingAR.model.AgingAR;

public interface AgingARDAO {

	List<AgingAR> getAgingARList();

	List<AgingAR> getInvoiceDetails(JsonObject agingObj);
}
