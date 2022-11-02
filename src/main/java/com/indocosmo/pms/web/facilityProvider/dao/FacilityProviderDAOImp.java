package com.indocosmo.pms.web.facilityProvider.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.facilityProvider.model.FacilityProvider;

@Repository
public class FacilityProviderDAOImp implements FacilityProviderDAO {
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(FacilityProviderDAOImp.class);

	public List<FacilityProvider> getFacilityProviderList() {
		List<FacilityProvider> providerList = null;
		try {
			providerList = sessionFactory.getCurrentSession().createQuery("from FacilityProvider where is_deleted=0")
					.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getFacilityProviderList ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return providerList;
	}

	public boolean save(FacilityProvider fprov) throws Exception {
		boolean isSave = true;

		try {
			Session session = sessionFactory.getCurrentSession();

			if (fprov.getId() != 0) {
				session.update(fprov);
			} else {
				session.save(fprov);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : save, " + Throwables.getStackTraceAsString(ex));
			isSave = false;
			throw new CustomException();
		}

		return isSave;
	}

	public boolean delete(int id) {
		boolean isDeleted = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			FacilityProvider fprov = (FacilityProvider) session.load(FacilityProvider.class, id);
			fprov.setDeleted(true);
			session.update(fprov);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(ex));
			isDeleted = false;
			throw new CustomException();
		}
		return isDeleted;
	}
}
