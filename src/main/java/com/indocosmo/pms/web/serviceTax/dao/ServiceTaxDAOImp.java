package com.indocosmo.pms.web.serviceTax.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.serviceTax.model.serviceTax;
import com.indocosmo.pms.web.templates.dao.TemplateDaoImpl;

@Repository
@Transactional
public class ServiceTaxDAOImp implements ServiceTaxDAO{
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(TemplateDaoImpl.class);

	public serviceTax getServiceTaxDetails()throws Exception{
		serviceTax serviceTax = new serviceTax();
		try {
			List<serviceTax> serviceTaxList = sessionFactory.getCurrentSession().createQuery("from serviceTax where isDeleted=0").list();

			if(serviceTaxList.size() > 0) {
				serviceTax = serviceTaxList.get(0);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("Method : getInvoiceTemplateDtls ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return serviceTax;
	}

	public boolean saveInvoice(serviceTax serviceTax) throws Exception{
		boolean isSave = true;
		try {
			boolean dtl=delete(serviceTax.getId());
			if(dtl){
				sessionFactory.getCurrentSession().save(serviceTax);
			}
		} catch(Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return isSave;
	}

	public boolean delete(int id) throws Exception {
		boolean isDeleted = true;
		Session session = null;
		serviceTax sTax = null;

		try {
			session = sessionFactory.getCurrentSession();
			sTax = (serviceTax) session.load(serviceTax.class,id);
			sTax.setDeleted(true);
			session.update(sTax);
		} catch (Exception ex) {
			logger.error("Method : delete " + Throwables.getStackTraceAsString(ex));
			ex.printStackTrace();
			isDeleted = false;
			throw new CustomException();
		}

		return isDeleted;
	}

	public int getActiveServiceTax(){
		int id=0;
		Session session = null;
	try{
		session=sessionFactory.getCurrentSession();
	id = (Integer) session.createQuery("select serviceTax.id from serviceTax where isDeleted=0").uniqueResult();
	} catch (Exception ex) {
		logger.error("Method : getActive Service Tax " + Throwables.getStackTraceAsString(ex));
		ex.printStackTrace();
 		throw new CustomException();
	}
		return id;
	}
}
