package com.indocosmo.pms.web.facilityProvider.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indocosmo.pms.web.facilityProvider.dao.FacilityProviderDAO;
import com.indocosmo.pms.web.facilityProvider.model.FacilityProvider;

@Service
public class FacilityProviderServiceImp implements FacilityProviderService {

	@Autowired
	FacilityProviderDAO facilityProviderDao;

	public static final Logger logger = LoggerFactory.getLogger(FacilityProviderServiceImp.class);

	@Transactional
	public List<FacilityProvider> getFacilityProviderList() {
		return facilityProviderDao.getFacilityProviderList();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean save(FacilityProvider fprov) throws Exception {
		return facilityProviderDao.save(fprov);
	}

	@Transactional
	public boolean delete(int id) {
		return facilityProviderDao.delete(id);
	}
}
