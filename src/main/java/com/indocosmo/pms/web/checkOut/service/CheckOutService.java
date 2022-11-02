package com.indocosmo.pms.web.checkOut.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.checkOut.model.CheckOutDetails;
import com.indocosmo.pms.web.checkOut.model.Invoice;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.itextpdf.text.Document;

public interface CheckOutService {
	public List<CheckOutDetails> getCheckInDetailsByFolioBindNO(int folioBindNO) throws Exception;

	public String doCheckOut(List<CheckOutDetails> checkOutDetailsList, JsonObject billAddressObj) throws Exception;

	public Invoice getTxnDetails(int folioNo, HttpSession session, int billDtls);

	public List<Transaction> getTxnSummary(int folioNo, HttpSession session, int billDtls);

	public JsonArray getCheckInCheckOutData(String wherepart) throws Exception;

	public String getmailCheckOut(JsonArray jArray) throws Exception;

	public List<Transaction> getInvoiceGroupDetails(int folioNo);

	public String sendSms(JsonArray jArray);

	public JsonObject loadAddressWithCheckinNo(int checkinNo);

	public HttpServletResponse sendInvoiceMail(Integer folioNo,ByteArrayOutputStream doc, SystemSettings systemSettings) throws Exception;

	public Invoice getTxnDetailsSeparate(int folioNo, HttpSession session, int billDtls, int i);

	public List<Transaction> getTxnSummarySeparate(int folioNo, HttpSession session, int billDtls, int sysAccType);

	public String getReportName(int sysAccType) throws Exception;

	public Invoice getTxnDetailsCombined(int folioNo, HttpSession session, int billDtls);

	


	

	

}
