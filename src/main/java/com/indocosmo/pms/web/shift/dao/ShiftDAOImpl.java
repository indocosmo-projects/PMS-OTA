package com.indocosmo.pms.web.shift.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.exception.CustomException;

import com.indocosmo.pms.web.shift.model.Shift;

@Repository
public class ShiftDAOImpl implements ShiftDAO {

	@Autowired
	private SessionFactory sessionFactory;

	DbConnection dbConnection = null;

	public ShiftDAOImpl() {
		dbConnection = new DbConnection();
	}

	private static final Logger logger = LoggerFactory.getLogger(ShiftDAOImpl.class);

	@Override
	public List<Shift> getShiftList() {
		List<Shift> shiftList = null;
		// TODO Auto-generated method stub
		try {
			shiftList = sessionFactory.getCurrentSession().createQuery("from Shift where is_deleted=0").list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Method : getShiftList ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return shiftList;
	}

	public Shift getShift(int id) {
		Shift shift = null;
		try {
			shift = (Shift) sessionFactory.getCurrentSession().get(Shift.class, id);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Method : getShiftList ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return shift;

	}

	@Override
	public boolean save(Shift shift) throws Exception {
		// TODO Auto-generated method stub
		boolean isSave = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			if (shift.getId() != 0) {
				session.update(shift);
			} else {
				session.save(shift);

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : save, " + Throwables.getStackTraceAsString(e));
			isSave = false;
			throw new CustomException();
		}

		return isSave;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		boolean isDeleted = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			Shift shift = (Shift) session.load(Shift.class, id);
			shift.setDeleted(true);
			session.update(shift);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(e));
			isDeleted = false;
			throw new CustomException();
		}
		return isDeleted;
	}

}
