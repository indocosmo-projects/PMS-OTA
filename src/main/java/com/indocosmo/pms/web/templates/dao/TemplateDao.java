package com.indocosmo.pms.web.templates.dao;

import java.util.List;

import com.indocosmo.pms.web.templates.model.EmailTemplate;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.indocosmo.pms.web.templates.model.SmsTemplate;

public interface TemplateDao {
	public InvoiceTemplate getInvoiceTemplateDtls() throws Exception;

	public boolean saveInvoice(InvoiceTemplate invoice);

	public List<EmailTemplate> getEmailTemplateDtls() throws Exception;

	public EmailTemplate getEmailTemplate(int id);

	public boolean saveEmail(EmailTemplate email);

	public boolean deleteEmailTempl(int id);

	public List<SmsTemplate> getSmsTemplateDtls() throws Exception;

	public boolean saveSms(SmsTemplate sms);

	public boolean deleteSmsTempl(int purpose);

}
