package com.indocosmo.pms.web.facilities.service;

import java.util.List;

import com.indocosmo.pms.web.facilities.model.Facility;

public interface FacilityService {

	public List<Facility> getFacilityList();

	public boolean save(Facility facility) throws Exception;

	public boolean delete(int id);
}
