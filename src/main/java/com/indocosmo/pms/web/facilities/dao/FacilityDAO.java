package com.indocosmo.pms.web.facilities.dao;

import java.util.List;

import com.indocosmo.pms.web.facilities.model.Facility;

public interface FacilityDAO {
	public List<Facility> getFacilityList();

	public boolean save(Facility fprov) throws Exception;

	public boolean delete(int id);

	public Facility getFacility(int id);
}
