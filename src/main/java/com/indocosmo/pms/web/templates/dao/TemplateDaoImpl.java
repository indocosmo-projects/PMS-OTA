package com.indocosmo.pms.web.templates.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.templates.model.EmailTemplate;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.indocosmo.pms.web.templates.model.SmsTemplate;


@Repository
@Transactional
public class TemplateDaoImpl implements TemplateDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	private DbConnection dbConnection = null;

	private static final Logger logger = LoggerFactory.getLogger(TemplateDaoImpl.class);

	public InvoiceTemplate getInvoiceTemplateDtls() {
		InvoiceTemplate invoiceTempl = new InvoiceTemplate();
		try {
			List<InvoiceTemplate> invoiceList = sessionFactory.getCurrentSession().createQuery("from InvoiceTemplate")
					.list();

			if (invoiceList.size() > 0) {
				invoiceTempl = invoiceList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getInvoiceTemplateDtls ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return invoiceTempl;
	}

	public boolean saveInvoice(InvoiceTemplate invoice) {
		boolean isSave = true;
		try {
			sessionFactory.getCurrentSession().update(invoice);
		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return isSave;
	}

	/* ======E-Mail Template==== */

	public List<EmailTemplate> getEmailTemplateDtls() {
		List<EmailTemplate> emailTemplList;
		try {
			emailTemplList = sessionFactory.getCurrentSession().createQuery("from EmailTemplate where is_deleted=0")
					.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getEmailTemplateDtls ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return emailTemplList;
	}

	public EmailTemplate getEmailTemplate(int purpose) {
		EmailTemplate emailTempl = new EmailTemplate();
		dbConnection = new DbConnection();
		Connection connection = dbConnection.getConnection();
		ResultSet rs=null;
		Statement statement=null;
		try {
		/*	List<EmailTemplate> emailTemplList = sessionFactory.getCurrentSession()
					.createQuery("from EmailTemplate where is_deleted=0 and purpose=" + purpose).list();*/
			String sql="select * from email_templates where is_deleted=0 and purpose=" + purpose;
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
			/*if (emailTemplList.size() > 0) {
				//emailTempl = emailTemplList.get(0);
			}*/
				emailTempl.setCode(rs.getString("code"));
				emailTempl.setContent(rs.getString("content"));
				emailTempl.setGroup(rs.getInt("templ_group"));
				emailTempl.setSubject(rs.getString("subject"));
				emailTempl.setUser_id(rs.getInt("user_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getEmailTemplate ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
		}
		return emailTempl;
	}

	public boolean saveEmail(EmailTemplate email) {
		boolean isSave = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			if (email.getId() != 0) {
				session.update(email);
			} else {
				session.save(email);
			}
		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return isSave;
	}

	public boolean deleteEmailTempl(int id) {
		boolean isDeleted = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			EmailTemplate emailTmpl = (EmailTemplate) session.load(EmailTemplate.class, id);
			emailTmpl.setIs_deleted(true);
			session.update(emailTmpl);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(ex));
			isDeleted = false;
			throw new CustomException();
		}
		return isDeleted;
	}

	/* ======SMS Template==== */

	public List<SmsTemplate> getSmsTemplateDtls() {
		List<SmsTemplate> smsTemplList;
		try {
			smsTemplList = sessionFactory.getCurrentSession().createQuery("from SmsTemplate where is_deleted=0").list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getSmsTemplateDtls ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return smsTemplList;
	}

	public boolean saveSms(SmsTemplate sms) {
		boolean isSave = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			if (sms.getId() != 0) {
				session.update(sms);
			} else {
				session.save(sms);
			}
		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			logger.error("Method : save ," + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return isSave;
	}

	public boolean deleteSmsTempl(int id) {
		boolean isDeleted = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			SmsTemplate smsTmpl = (SmsTemplate) session.load(SmsTemplate.class, id);
			smsTmpl.setIs_deleted(true);
			session.update(smsTmpl);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : delete  , " + Throwables.getStackTraceAsString(ex));
			isDeleted = false;
			throw new CustomException();
		}
		return isDeleted;
	}
}
