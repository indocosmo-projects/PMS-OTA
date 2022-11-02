package com.indocosmo.pms.web.facilityProvider.service;

import java.util.List;

import com.indocosmo.pms.web.facilityProvider.model.FacilityProvider;

public interface FacilityProviderService {
	public List<FacilityProvider> getFacilityProviderList();

	public boolean save(FacilityProvider fprov) throws Exception;

	public boolean delete(int id);
}
