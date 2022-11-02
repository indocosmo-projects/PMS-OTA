package com.indocosmo.pms.web.facilities.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indocosmo.pms.web.facilities.dao.FacilityDAO;
import com.indocosmo.pms.web.facilities.model.Facility;

@Service
public class FacilityServiceImpl implements FacilityService {

	@Autowired
	FacilityDAO facilityDAO;

	@Transactional
	public List<Facility> getFacilityList() {
		return facilityDAO.getFacilityList();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean save(Facility facility) throws Exception {
		return facilityDAO.save(facility);
	}

	@Transactional
	public boolean delete(int id) {
		return facilityDAO.delete(id);
	}

}
