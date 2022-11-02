package com.indocosmo.pms.web.facilities.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.facilities.model.Facility;

@Repository
public class FacilityDAOImpl implements FacilityDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(FacilityDAOImpl.class);

	@Override
	public List<Facility> getFacilityList() {
		List<Facility> facilityList = null;
		try {
			facilityList = sessionFactory.getCurrentSession().createQuery("from Facility where is_deleted=0").list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getFacilityList ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return facilityList;
	}

	public Facility getFacility(int id) {
		Facility facility = null;
		try {
			facility = (Facility) sessionFactory.getCurrentSession().get(Facility.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getFacilityList ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return facility;
	}

	public boolean save(Facility facility) throws Exception {
		boolean isSave = true;

		try {
			Session session = sessionFactory.getCurrentSession();

			if (facility.getId() != 0) {
				session.update(facility);
			} else {
				session.save(facility);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : save, " + Throwables.getStackTraceAsString(ex));
			isSave = false;
			throw new CustomException();
		}

		return isSave;
	}

	@Override
	public boolean delete(int id) {
		boolean isDeleted = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			Facility facility = (Facility) session.load(Facility.class, id);
			facility.setDeleted(true);
			session.update(facility);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(ex));
			isDeleted = false;
			throw new CustomException();
		}
		return isDeleted;
	}
}
