package com.indocosmo.pms.web.facilityProvider.dao;

import java.util.List;

import com.indocosmo.pms.web.facilityProvider.model.FacilityProvider;

public interface FacilityProviderDAO {
	public List<FacilityProvider> getFacilityProviderList();

	public boolean save(FacilityProvider fprov) throws Exception;

	public boolean delete(int id);
}
