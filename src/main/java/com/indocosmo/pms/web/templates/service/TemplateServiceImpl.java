package com.indocosmo.pms.web.templates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indocosmo.pms.web.templates.dao.TemplateDao;
import com.indocosmo.pms.web.templates.model.EmailTemplate;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.indocosmo.pms.web.templates.model.SmsTemplate;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	TemplateDao templateDao;

	@Transactional
	public InvoiceTemplate getInvoiceTemplateDtls() throws Exception {
		return templateDao.getInvoiceTemplateDtls();
	}

	@Transactional
	public boolean saveInvoice(InvoiceTemplate invoice) {
		return templateDao.saveInvoice(invoice);
	}

	@Transactional
	public List<EmailTemplate> getEmailTemplateDtls() throws Exception {
		return templateDao.getEmailTemplateDtls();
	}

	@Transactional
	public boolean saveEmail(EmailTemplate email) {
		return templateDao.saveEmail(email);
	}

	@Transactional
	public boolean deleteEmailTempl(int id) {
		return templateDao.deleteEmailTempl(id);
	}

	@Transactional
	public List<SmsTemplate> getSmsTemplateDtls() throws Exception {
		return templateDao.getSmsTemplateDtls();
	}

	@Transactional
	public boolean saveSms(SmsTemplate sms) {
		return templateDao.saveSms(sms);
	}

	@Transactional
	public boolean deleteSmsTempl(int id) {
		return templateDao.deleteSmsTempl(id);
	}

}
