package com.indocosmo.pms.web.checkOut.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.CommunicationPurposes;
import com.indocosmo.pms.enumerator.CommunicationType;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.checkOut.dao.CheckOutDAO;
import com.indocosmo.pms.web.checkOut.dao.CheckOutDAOImp;
import com.indocosmo.pms.web.checkOut.model.CheckOutDetails;
import com.indocosmo.pms.web.checkOut.model.Invoice;
import com.indocosmo.pms.web.checkOut.model.TaxSummary;
import com.indocosmo.pms.web.common.model.CommunicationDetails;
import com.indocosmo.pms.web.common.service.CommonService;
import com.indocosmo.pms.web.common.setttings.Rounding;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.currency.service.CurrencyService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.payment.dao.PaymentDAO;
import com.indocosmo.pms.web.serviceTax.model.serviceTax;
import com.indocosmo.pms.web.systemSettings.dao.SystemSettingsDao;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.templates.dao.TemplateDao;
import com.indocosmo.pms.web.templates.dao.TemplateDaoImpl;
import com.indocosmo.pms.web.templates.model.EmailTemplate;
import com.indocosmo.pms.web.templates.model.SmsTemplate;
import com.indocosmo.pms.web.transaction.dao.TxnDao;
import com.indocosmo.pms.web.transaction.model.Transaction;

@Service
public class CheckOutServiceImp implements CheckOutService {
	@Autowired
	CheckOutDAO checkOutDAO;

	@Autowired
	SystemSettingsDao systemSettingsDao;

	@Autowired
	CurrencyService currencyService;

	@Autowired
	TemplateDao templateDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TxnDao txnDao;

	@Autowired
	PaymentDAO paymentDao;
	public static final Logger logger = LoggerFactory.getLogger(CheckOutServiceImp.class);

	private DbConnection dbConnection = new DbConnection();

	@Transactional
	public List<CheckOutDetails> getCheckInDetailsByFolioBindNO(int folioBindNO) throws Exception {
		return checkOutDAO.getCheckInDetailsByFolioBindNO(folioBindNO);
	}

	@Transactional
	public String doCheckOut(List<CheckOutDetails> checkOutDetailsList, JsonObject billAddressObj) throws Exception {
		return checkOutDAO.doCheckOut(checkOutDetailsList, billAddressObj);
	}

	@Transactional
	public List<Transaction> getInvoiceGroupDetails(int folioNo) {
		return checkOutDAO.getInvoiceGroupDetails(folioNo);

	}

	/*
	 * @Transactional public Invoice getDetailedTxnDetails(int folioNo,HttpSession
	 * session){ Invoice invoice = new Invoice(); serviceTax sTax= new serviceTax();
	 * List<Transaction> txnList = checkOutDAO.getTxnDetails(folioNo);
	 * List<Transaction> txnListInvoice =
	 * checkOutDAO.getInvoiceGroupDetails(folioNo); HashMap<Integer,Object> taxList
	 * = new HashMap(); TaxSummary taxsum=null; double
	 * disc=0.0,deposit=0.0,total=0.0,taxamt=0.0,tax1amt=0.0,tax2amt=0.0,tax3amt=0.0
	 * ,tax4amt=0.0,amount=0.0,taxTotal=0.0,serviceTax=0.0,serviceCharge=0.0,paidIn=
	 * 0.0,roundAdj=0.0,foodExpns=0.0,posRefundAmt=0.0,refundAmount=0.0; double
	 * tax1=0.0,tax2=0.0,tax3=0.0,tax4=0.0,tax1pc=0.0,tax2pc=0.0,tax3pc=0.0,tax4pc=0
	 * .0; int serviceTaxId=0; List<Transaction> posDetails = new ArrayList<>();
	 * Map<String, Object> refundDetails = new HashMap<String, Object>(); try {
	 * for(Transaction txn:txnList ){ taxsum=new TaxSummary();
	 * if(txn.getAcc_mst_id()==6){ disc+=txn.getNett_amount(); }else
	 * if(txn.getAcc_mst_id()==4){ deposit+=txn.getNett_amount(); }else
	 * if(txn.getAcc_mst_id()==8){ paidIn+=txn.getNett_amount(); }else
	 * if(txn.getAcc_mst_id()==9){ serviceTaxId=txn.getServiceTaxId(); }else
	 * if(txn.getAcc_mst_id()==10){ roundAdj+=txn.getNett_amount(); }else
	 * if(txn.getAcc_mst_id()==5){ refundAmount+=txn.getNett_amount(); }else
	 * if(txn.getAcc_mst_id()==12) { foodExpns += txn.getBase_amount();
	 * posDetails.add(txn); refundDetails = (Map<String, Object>)
	 * txnDao.getOrderDetails(txn.getTxn_no()); }else if(txn.getAcc_mst_id()==13) {
	 * posRefundAmt += txn.getNett_amount();
	 * txn.setBase_amount(-(txn.getNett_amount())); posDetails.add(txn); }else
	 * if(txn.getAcc_mst_id()==14) { txn.setBase_amount(0); }else{
	 * total+=txn.getBase_amount(); }
	 * 
	 * serviceCharge+=txn.getServiceCharge().doubleValue();
	 * 
	 * if(txn.getTax_id()!=1){ taxsum.setTaxId(txn.getTax_id());
	 * taxsum.setTaxCode(txn.getTax_code());
	 * taxsum.setTaxIndicator(txn.getTax_indicator()); if(txn.getAcc_mst_id()==6){
	 * taxsum.setAmount(-(txn.getBase_amount())); }else{
	 * taxsum.setAmount(txn.getBase_amount()); }
	 * taxsum.setTax1Amount(txn.getTax1_amount());
	 * taxsum.setTax2Amount(txn.getTax2_amount());
	 * taxsum.setTax3Amount(txn.getTax3_amount());
	 * taxsum.setTax4Amount(txn.getTax4_amount());
	 * taxsum.setTaxAmount(txn.getTax1_amount()+txn.getTax2_amount()+txn.
	 * getTax3_amount()+txn.getTax4_amount());
	 * taxsum.setTaxPc(txn.getTax1_pc()+txn.getTax2_pc()+txn.getTax3_pc()+txn.
	 * getTax4_pc()); taxTotal=taxTotal+taxsum.getTaxAmount();
	 * if(taxList.containsKey(txn.getTax_id())){ TaxSummary taxsum2 =(TaxSummary)
	 * taxList.get(txn.getTax_id()); taxamt =
	 * taxsum.getTaxAmount()+taxsum2.getTaxAmount(); tax1amt =
	 * taxsum.getTax1Amount()+taxsum2.getTax1Amount(); tax2amt =
	 * taxsum.getTax2Amount()+taxsum2.getTax2Amount(); tax3amt =
	 * taxsum.getTax3Amount()+taxsum2.getTax3Amount(); tax4amt =
	 * taxsum.getTax4Amount()+taxsum2.getTax4Amount();
	 * taxsum2.setTax1Amount(tax1amt); taxsum2.setTax2Amount(tax2amt);
	 * taxsum2.setTax3Amount(tax3amt); taxsum2.setTax4Amount(tax4amt);
	 * taxsum2.setTaxAmount(taxamt); amount =
	 * taxsum.getAmount()+taxsum2.getAmount(); taxsum2.setAmount(amount);
	 * taxList.put(txn.getTax_id(),taxsum2); }else{
	 * taxList.put(txn.getTax_id(),taxsum); } tax1+=txn.getTax1_amount();
	 * tax2+=txn.getTax2_amount(); tax3+=txn.getTax3_amount();
	 * tax4+=txn.getTax4_amount(); tax1pc+=txn.getTax1_pc();
	 * tax2pc+=txn.getTax2_pc(); tax3pc+=txn.getTax3_pc(); tax4pc+=txn.getTax4_pc();
	 * 
	 * }
	 * 
	 * }
	 * 
	 * SystemSettings systemSettings=systemSettingsDao.getSystemSettings(); Currency
	 * curr=(Currency)session.getAttribute("currencyObject");
	 * invoice.setTax1Code(systemSettings.getTax1Name());
	 * invoice.setTax2Code(systemSettings.getTax2Name());
	 * invoice.setTax3Code(systemSettings.getTax3Name());
	 * invoice.setTax4Code(systemSettings.getTax4Name()); invoice.setTax1((tax1));
	 * //invoice.setTax2(Rounding.nRound(tax2)); invoice.setTax2((tax2));
	 * invoice.setTax3((tax3)); invoice.setTax4((tax4)); invoice.setTax1_pc(tax1pc);
	 * invoice.setTax2_pc(tax2pc); invoice.setTax3(tax3pc);
	 * invoice.setTax4_pc(tax4pc); JsonObject
	 * jObj=checkOutDAO.getCustomerDetails(folioNo);
	 * invoice.setName(String.valueOf(jObj.get("name").getAsString()));
	 * if(jObj.get("guest_name") != null && !jObj.get("guest_name").isJsonNull()){
	 * invoice.setGuestName(String.valueOf(jObj.get("guest_name").getAsString())); }
	 * invoice.setPhone(String.valueOf(jObj.get("phone").getAsString()));
	 * invoice.setEmail(String.valueOf(jObj.get("email").getAsString()));
	 * invoice.setAddress(String.valueOf(jObj.get("address").getAsString()));
	 * invoice.setState(String.valueOf(jObj.get("state").getAsString()));
	 * invoice.setNationality(String.valueOf(jObj.get("nationality").getAsString()))
	 * ; invoice.setCheckInDate(String.valueOf(jObj.get("arrDate").getAsString()));
	 * invoice.setRoom_number(String.valueOf(jObj.get("roomNumber").getAsString()));
	 * if(!(jObj.get("rateDescription")).isJsonNull()) {
	 * invoice.setRate_description(String.valueOf(jObj.get("rateDescription").
	 * getAsString())); }else { invoice.setRate_description(""); }
	 * if(!jObj.get("depDate").isJsonNull()){
	 * invoice.setCheckOutDate(String.valueOf(jObj.get("depDate").getAsString())); }
	 * if(!jObj.get("gstno").isJsonNull()){
	 * invoice.setGstno(String.valueOf(jObj.get("gstno").getAsString())); }
	 * invoice.setArr_time(String.valueOf(jObj.get("arrTime").getAsString()));
	 * if(!jObj.get("act_depart_time").isJsonNull()){
	 * invoice.setAct_depart_time(String.valueOf(jObj.get("act_depart_time").
	 * getAsString())); } if(!jObj.get("invoiceNo").isJsonNull()){
	 * invoice.setInvoiceNo(jObj.get("invoiceNo").getAsString()); }
	 * invoice.setTxnList(txnList); invoice.setTxnListInvoice(txnListInvoice);
	 * System.out.println(txnListInvoice.get(0)); invoice.setTaxList(taxList);
	 * invoice.setDiscount((disc)); invoice.setServiceCharge((serviceCharge));
	 * invoice.setCurrencySymbol(curr.getSymbol());
	 * //invoice.setCurrencySymbol(commonSettings.currencySymbol);
	 * invoice.setTotal((total)); invoice.setTotalWithoutDisc((total-disc));
	 * if(serviceTaxId!=0){
	 * sTax=paymentDao.getserviceTax(serviceTaxId,total+serviceCharge-disc);
	 * serviceTax=sTax.getTotalServiceTax(); } invoice.setSeriveTax(sTax);
	 * invoice.setDeposit(Rounding.nRound(deposit));
	 * invoice.setPayments(Rounding.nRound(paidIn));
	 * invoice.setFoodExpense(foodExpns); invoice.setPosDetails(posDetails);
	 * invoice.setRefundDetails(refundDetails); invoice.setFoodRefund(posRefundAmt);
	 * invoice.setGrandTotal((invoice.getTotalWithoutDisc()+taxTotal+serviceCharge+
	 * serviceTax+foodExpns)-posRefundAmt); if(roundAdj<0) {
	 * invoice.setRoundAdjust(-roundAdj); } else{ invoice.setRoundAdjust(roundAdj);
	 * }
	 * invoice.setBalance(((total-disc)+taxTotal+serviceCharge+serviceTax-(deposit+
	 * paidIn+roundAdj)));
	 * 
	 * //invoice.setBalance(Rounding.nRound(invoice.getGrandTotal()-(deposit+paidIn+
	 * roundAdj))); } catch (Exception e) { e.printStackTrace();
	 * logger.error("Method : getTxnDetails " +
	 * Throwables.getStackTraceAsString(e)); throw new CustomException(); } return
	 * invoice; }
	 */

	@Transactional
	public Invoice getTxnDetails(int folioNo, HttpSession session, int billDtls) {
		Invoice invoice = new Invoice();
		serviceTax sTax = new serviceTax();
		List<Transaction> txnList = checkOutDAO.getTxnDetails(folioNo);
		List<Transaction> txnListInvoice;
		List<Transaction> paymentList = new ArrayList<>();
		if (billDtls == 0) {
			txnListInvoice = checkOutDAO.getInvoiceDetails(folioNo);
		} else {
			txnListInvoice = checkOutDAO.getInvoiceGroupDetails(folioNo);
		}
		paymentList = checkOutDAO.getPaymentList(folioNo);
		HashMap<Integer, Object> taxList = new HashMap();
		TaxSummary taxsum = null;
		double disc = 0.0, deposit = 0.0, total = 0.0, taxamt = 0.0, tax1amt = 0.0, tax2amt = 0.0, tax3amt = 0.0,
				tax4amt = 0.0, amount = 0.0, taxTotal = 0.0, serviceTax = 0.0, serviceCharge = 0.0, paidIn = 0.0,
				roundAdj = 0.0, foodExpns = 0.0, posRefundAmt = 0.0, refundAmount = 0.0;
		double tax1 = 0.0, tax2 = 0.0, tax3 = 0.0, tax4 = 0.0, tax1pc = 0.0, tax2pc = 0.0, tax3pc = 0.0, tax4pc = 0.0,
				taxKfc = 0.0;
		int serviceTaxId = 0;
		List<Transaction> posDetails = new ArrayList<>();
		Map<String, Object> refundDetails = new HashMap<String, Object>();
		try {
			for (Transaction txn : txnList) {
				taxsum = new TaxSummary();
				if (txn.getAcc_mst_id() == 6) {
					disc += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 4) {
					deposit += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 8) {
					paidIn += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 9) {
					serviceTaxId = txn.getServiceTaxId();
				} else if (txn.getAcc_mst_id() == 10) {
					roundAdj += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 5) {
					refundAmount += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 12) {
					foodExpns += txn.getBase_amount();
					posDetails.add(txn);
					refundDetails = (Map<String, Object>) txnDao.getOrderDetails(txn.getTxn_no());
				} else if (txn.getAcc_mst_id() == 13) {
					posRefundAmt += txn.getNett_amount();
					txn.setBase_amount(-(txn.getNett_amount()));
					posDetails.add(txn);
				} else if (txn.getAcc_mst_id() == 14) {
					txn.setBase_amount(0);
				} else {
					total += txn.getBase_amount();
				}

				serviceCharge += txn.getServiceCharge().doubleValue();

				if (txn.getTax_id() != 1) {
					taxsum.setTaxId(txn.getTax_id());
					taxsum.setTaxCode(txn.getTax_code());
					taxsum.setTaxIndicator(txn.getTax_indicator());
					if (txn.getAcc_mst_id() == 6) {
						taxsum.setAmount(-(txn.getBase_amount()));
					} else {
						taxsum.setAmount(txn.getBase_amount());
					}
					taxsum.setTax1Amount(txn.getTax1_amount());
					taxsum.setTax2Amount(txn.getTax2_amount());
					taxsum.setTax3Amount(txn.getTax3_amount());
					taxsum.setTax4Amount(txn.getTax4_amount());
					taxsum.setTaxAmount(
							txn.getTax1_amount() + txn.getTax2_amount() + txn.getTax3_amount() + txn.getTax4_amount());
					taxsum.setTaxPc(txn.getTax1_pc() + txn.getTax2_pc() + txn.getTax3_pc() + txn.getTax4_pc());
					taxTotal = taxTotal + taxsum.getTaxAmount();
					if (taxList.containsKey(txn.getTax_id())) {
						TaxSummary taxsum2 = (TaxSummary) taxList.get(txn.getTax_id());
						taxamt = taxsum.getTaxAmount() + taxsum2.getTaxAmount();
						tax1amt = taxsum.getTax1Amount() + taxsum2.getTax1Amount();
						tax2amt = taxsum.getTax2Amount() + taxsum2.getTax2Amount();
						tax3amt = taxsum.getTax3Amount() + taxsum2.getTax3Amount();
						tax4amt = taxsum.getTax4Amount() + taxsum2.getTax4Amount();
						taxsum2.setTax1Amount(tax1amt);
						taxsum2.setTax2Amount(tax2amt);
						taxsum2.setTax3Amount(tax3amt);
						taxsum2.setTax4Amount(tax4amt);
						taxsum2.setTaxAmount(taxamt);
						amount = taxsum.getAmount() + taxsum2.getAmount();
						taxsum2.setAmount(amount);
						taxList.put(txn.getTax_id(), taxsum2);
					} else {
						taxList.put(txn.getTax_id(), taxsum);
					}
					tax1 += txn.getTax1_amount();
					tax2 += txn.getTax2_amount();
					tax3 += txn.getTax3_amount();
					tax4 += txn.getTax4_amount();
					tax1pc += txn.getTax1_pc();
					tax2pc += txn.getTax2_pc();
					tax3pc += txn.getTax3_pc();
					tax4pc += txn.getTax4_pc();

					if (txn.getTax3_pc() > 0) {
						taxKfc = txn.getTax3_pc();
					}
				}

			}

			SystemSettings systemSettings = systemSettingsDao.getSystemSettings();
			Currency curr = (Currency) session.getAttribute("currencyObject");
			invoice.setTax3_pc_kfc(taxKfc);
			invoice.setTax1Code(systemSettings.getTax1Name());
			invoice.setTax2Code(systemSettings.getTax2Name());
			invoice.setTax3Code(systemSettings.getTax3Name());
			invoice.setTax4Code(systemSettings.getTax4Name());
			invoice.setTax1((tax1));
			// invoice.setTax2(Rounding.nRound(tax2));
			invoice.setTax2((tax2));
			invoice.setTax3((tax3));
			invoice.setTax4((tax4));
			invoice.setTax1_pc(tax1pc);
			invoice.setTax2_pc(tax2pc);
			invoice.setTax3_pc(tax3pc);
			invoice.setTax4_pc(tax4pc);
			JsonObject jObj = checkOutDAO.getCustomerDetails(folioNo);
			if (jObj != null) {
				invoice.setName(String.valueOf(jObj.get("name").getAsString()));
				if (jObj.get("guest_name") != null && !jObj.get("guest_name").isJsonNull()) {
					invoice.setGuestName(String.valueOf(jObj.get("guest_name").getAsString()));
				}
				invoice.setPhone(String.valueOf(jObj.get("phone").getAsString()));
				invoice.setEmail(String.valueOf(jObj.get("email").getAsString()));
				invoice.setAddress(String.valueOf(jObj.get("address").getAsString()));
				invoice.setState(String.valueOf(jObj.get("state").getAsString()));
				invoice.setNationality(String.valueOf(jObj.get("nationality").getAsString()));
				invoice.setCheckInDate(String.valueOf(jObj.get("arrDate").getAsString()));
				invoice.setRoom_number(String.valueOf(jObj.get("roomNumber").getAsString()));
				if (!(jObj.get("rateDescription")).isJsonNull()) {
					invoice.setRate_description(String.valueOf(jObj.get("rateDescription").getAsString()));
				} else {
					invoice.setRate_description("");
				}
				if (!jObj.get("depDate").isJsonNull()) {
					invoice.setCheckOutDate(String.valueOf(jObj.get("depDate").getAsString()));
				}
				if (!jObj.get("gstno").isJsonNull()) {
					invoice.setGstno(String.valueOf(jObj.get("gstno").getAsString()));
				}
				invoice.setArr_time(String.valueOf(jObj.get("arrTime").getAsString()));
				if (!jObj.get("act_depart_time").isJsonNull()) {
					invoice.setAct_depart_time(String.valueOf(jObj.get("act_depart_time").getAsString()));
				}
				if (!jObj.get("invoiceNo").isJsonNull()) {
					invoice.setInvoiceNo(jObj.get("invoiceNo").getAsString());
				}
			}
			invoice.setTxnList(txnList);
			invoice.setTxnListInvoice(txnListInvoice);
			invoice.setPaymentList(paymentList);
			invoice.setTaxList(taxList);
			invoice.setDiscount((disc));
			invoice.setServiceCharge((serviceCharge));
			invoice.setCurrencySymbol(curr.getSymbol());
			// invoice.setCurrencySymbol(commonSettings.currencySymbol);
			invoice.setTotal((total));
			invoice.setTotalWithoutDisc((total - disc));
			if (serviceTaxId != 0) {
				sTax = paymentDao.getserviceTax(serviceTaxId, total + serviceCharge - disc);
				serviceTax = sTax.getTotalServiceTax();
			}
			invoice.setSeriveTax(sTax);
			invoice.setDeposit(Rounding.nRound(deposit));
			invoice.setPayments(Rounding.nRound(paidIn));
			invoice.setFoodExpense(foodExpns);
			invoice.setPosDetails(posDetails);
			invoice.setRefundDetails(refundDetails);
			invoice.setFoodRefund(posRefundAmt);

		double grandTotal = (invoice.getTotalWithoutDisc() + taxTotal + serviceCharge + serviceTax + foodExpns)
					- posRefundAmt;
//			double grandTotal = (invoice.getTotalWithoutDisc() + taxTotal + serviceCharge + serviceTax + foodExpns)
//					+ posRefundAmt;
			
			grandTotal = Rounding.roundOff(new BigDecimal(grandTotal), 2, true);

			invoice.setGrandTotal(grandTotal);
			if (roundAdj < 0) {
				invoice.setRoundAdjust(-roundAdj);
			} else {
				invoice.setRoundAdjust(roundAdj);
			}
			invoice.setBalance(
					((total - disc) + taxTotal + serviceCharge + serviceTax - (deposit + paidIn + roundAdj)));

			// invoice.setBalance(Rounding.nRound(invoice.getGrandTotal()-(deposit+paidIn+roundAdj)));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getTxnDetails " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return invoice;
	}
	
	@Transactional
	public Invoice getTxnDetailsCombined(int folioNo, HttpSession session, int billDtls) {
		Invoice invoice = new Invoice();
		serviceTax sTax = new serviceTax();
		List<Transaction> txnList = checkOutDAO.getTxnDetails(folioNo);
		List<Transaction> txnListInvoice;
		List<Transaction> paymentList = new ArrayList<>();
		if (billDtls == 0) {
			txnListInvoice = checkOutDAO.getInvoiceDetails(folioNo);
		} else {
			txnListInvoice = checkOutDAO.getInvoiceGroupDetailsCombined(folioNo);
		}
		paymentList = checkOutDAO.getPaymentList(folioNo);
		HashMap<Integer, Object> taxList = new HashMap();
		TaxSummary taxsum = null;
		double disc = 0.0, deposit = 0.0, total = 0.0, taxamt = 0.0, tax1amt = 0.0, tax2amt = 0.0, tax3amt = 0.0,
				tax4amt = 0.0, amount = 0.0, taxTotal = 0.0, serviceTax = 0.0, serviceCharge = 0.0, paidIn = 0.0,
				roundAdj = 0.0, foodExpns = 0.0, posRefundAmt = 0.0, refundAmount = 0.0;
		double tax1 = 0.0, tax2 = 0.0, tax3 = 0.0, tax4 = 0.0, tax1pc = 0.0, tax2pc = 0.0, tax3pc = 0.0, tax4pc = 0.0,
				taxKfc = 0.0;
		int serviceTaxId = 0;
		List<Transaction> posDetails = new ArrayList<>();
		Map<String, Object> refundDetails = new HashMap<String, Object>();
		try {
			for (Transaction txn : txnList) {
				taxsum = new TaxSummary();
				if (txn.getAcc_mst_id() == 6) {
					disc += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 4) {
					deposit += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 8) {
					paidIn += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 9) {
					serviceTaxId = txn.getServiceTaxId();
				} else if (txn.getAcc_mst_id() == 10) {
					roundAdj += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 5) {
					refundAmount += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 12) {
					foodExpns += txn.getBase_amount();
					posDetails.add(txn);
					refundDetails = (Map<String, Object>) txnDao.getOrderDetails(txn.getTxn_no());
				} else if (txn.getAcc_mst_id() == 13) {
					posRefundAmt += txn.getNett_amount();
					txn.setBase_amount(-(txn.getNett_amount()));
					posDetails.add(txn);
				} else if (txn.getAcc_mst_id() == 14) {
					txn.setBase_amount(0);
				} else {
					total += txn.getBase_amount();
				}

				serviceCharge += txn.getServiceCharge().doubleValue();

				if (txn.getTax_id() != 1) {
					taxsum.setTaxId(txn.getTax_id());
					taxsum.setTaxCode(txn.getTax_code());
					taxsum.setTaxIndicator(txn.getTax_indicator());
					if (txn.getAcc_mst_id() == 6) {
						taxsum.setAmount(-(txn.getBase_amount()));
					} else {
						taxsum.setAmount(txn.getBase_amount());
					}
					taxsum.setTax1Amount(txn.getTax1_amount());
					taxsum.setTax2Amount(txn.getTax2_amount());
					taxsum.setTax3Amount(txn.getTax3_amount());
					taxsum.setTax4Amount(txn.getTax4_amount());
					taxsum.setTaxAmount(
							txn.getTax1_amount() + txn.getTax2_amount() + txn.getTax3_amount() + txn.getTax4_amount());
					taxsum.setTaxPc(txn.getTax1_pc() + txn.getTax2_pc() + txn.getTax3_pc() + txn.getTax4_pc());
					taxTotal = taxTotal + taxsum.getTaxAmount();
					if (taxList.containsKey(txn.getTax_id())) {
						TaxSummary taxsum2 = (TaxSummary) taxList.get(txn.getTax_id());
						taxamt = taxsum.getTaxAmount() + taxsum2.getTaxAmount();
						tax1amt = taxsum.getTax1Amount() + taxsum2.getTax1Amount();
						tax2amt = taxsum.getTax2Amount() + taxsum2.getTax2Amount();
						tax3amt = taxsum.getTax3Amount() + taxsum2.getTax3Amount();
						tax4amt = taxsum.getTax4Amount() + taxsum2.getTax4Amount();
						taxsum2.setTax1Amount(tax1amt);
						taxsum2.setTax2Amount(tax2amt);
						taxsum2.setTax3Amount(tax3amt);
						taxsum2.setTax4Amount(tax4amt);
						taxsum2.setTaxAmount(taxamt);
						amount = taxsum.getAmount() + taxsum2.getAmount();
						taxsum2.setAmount(amount);
						taxList.put(txn.getTax_id(), taxsum2);
					} else {
						taxList.put(txn.getTax_id(), taxsum);
					}
					tax1 += txn.getTax1_amount();
					tax2 += txn.getTax2_amount();
					tax3 += txn.getTax3_amount();
					tax4 += txn.getTax4_amount();
					tax1pc += txn.getTax1_pc();
					tax2pc += txn.getTax2_pc();
					tax3pc += txn.getTax3_pc();
					tax4pc += txn.getTax4_pc();

					if (txn.getTax3_pc() > 0) {
						taxKfc = txn.getTax3_pc();
					}
				}

			}

			SystemSettings systemSettings = systemSettingsDao.getSystemSettings();
			Currency curr = (Currency) session.getAttribute("currencyObject");
			invoice.setTax3_pc_kfc(taxKfc);
			invoice.setTax1Code(systemSettings.getTax1Name());
			invoice.setTax2Code(systemSettings.getTax2Name());
			invoice.setTax3Code(systemSettings.getTax3Name());
			invoice.setTax4Code(systemSettings.getTax4Name());
			invoice.setTax1((tax1));
			// invoice.setTax2(Rounding.nRound(tax2));
			invoice.setTax2((tax2));
			invoice.setTax3((tax3));
			invoice.setTax4((tax4));
			invoice.setTax1_pc(tax1pc);
			invoice.setTax2_pc(tax2pc);
			invoice.setTax3_pc(tax3pc);
			invoice.setTax4_pc(tax4pc);
			JsonObject jObj = checkOutDAO.getCustomerDetails(folioNo);
			if (jObj != null) {
				invoice.setName(String.valueOf(jObj.get("name").getAsString()));
				if (jObj.get("guest_name") != null && !jObj.get("guest_name").isJsonNull()) {
					invoice.setGuestName(String.valueOf(jObj.get("guest_name").getAsString()));
				}
				invoice.setPhone(String.valueOf(jObj.get("phone").getAsString()));
				invoice.setEmail(String.valueOf(jObj.get("email").getAsString()));
				invoice.setAddress(String.valueOf(jObj.get("address").getAsString()));
				invoice.setState(String.valueOf(jObj.get("state").getAsString()));
				invoice.setNationality(String.valueOf(jObj.get("nationality").getAsString()));
				invoice.setCheckInDate(String.valueOf(jObj.get("arrDate").getAsString()));
				invoice.setRoom_number(String.valueOf(jObj.get("roomNumber").getAsString()));
				if (!(jObj.get("rateDescription")).isJsonNull()) {
					invoice.setRate_description(String.valueOf(jObj.get("rateDescription").getAsString()));
				} else {
					invoice.setRate_description("");
				}
				if (!jObj.get("depDate").isJsonNull()) {
					invoice.setCheckOutDate(String.valueOf(jObj.get("depDate").getAsString()));
				}
				if (!jObj.get("gstno").isJsonNull()) {
					invoice.setGstno(String.valueOf(jObj.get("gstno").getAsString()));
				}
				invoice.setArr_time(String.valueOf(jObj.get("arrTime").getAsString()));
				if (!jObj.get("act_depart_time").isJsonNull()) {
					invoice.setAct_depart_time(String.valueOf(jObj.get("act_depart_time").getAsString()));
				}
				if (!jObj.get("invoiceNo").isJsonNull()) {
					invoice.setInvoiceNo(jObj.get("invoiceNo").getAsString());
				}
				if (!jObj.get("checkinnum").isJsonNull()) {
					invoice.setGrcNo(jObj.get("checkinnum").getAsString());
				}
			}
			invoice.setTxnList(txnList);
			invoice.setTxnListInvoice(txnListInvoice);
			invoice.setPaymentList(paymentList);
			invoice.setTaxList(taxList);
			invoice.setDiscount((disc));
			invoice.setServiceCharge((serviceCharge));
			invoice.setCurrencySymbol(curr.getSymbol());
			// invoice.setCurrencySymbol(commonSettings.currencySymbol);
			invoice.setTotal((total));
			invoice.setTotalWithoutDisc((total - disc));
			if (serviceTaxId != 0) {
				sTax = paymentDao.getserviceTax(serviceTaxId, total + serviceCharge - disc);
				serviceTax = sTax.getTotalServiceTax();
			}
			invoice.setSeriveTax(sTax);
			invoice.setDeposit(Rounding.nRound(deposit));
			invoice.setPayments(Rounding.nRound(paidIn));
			invoice.setFoodExpense(foodExpns);
			invoice.setPosDetails(posDetails);
			invoice.setRefundDetails(refundDetails);
			invoice.setFoodRefund(posRefundAmt);

			double grandTotal = (invoice.getTotalWithoutDisc() + taxTotal + serviceCharge + serviceTax + foodExpns)
					- posRefundAmt;
		
			
			grandTotal = Rounding.roundOff(new BigDecimal(grandTotal), 2, true);

			invoice.setGrandTotal(grandTotal);
			if (roundAdj < 0) {
				invoice.setRoundAdjust(-roundAdj);
			} else {
				invoice.setRoundAdjust(roundAdj);
			}
			invoice.setBalance(
					((total - disc) + taxTotal + serviceCharge + serviceTax - (deposit + paidIn + roundAdj)));

			// invoice.setBalance(Rounding.nRound(invoice.getGrandTotal()-(deposit+paidIn+roundAdj)));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getTxnDetails " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return invoice;
	}
	
	@Transactional
	public Invoice getTxnDetailsSeparate(int folioNo, HttpSession session, int billDtls,int systemAccType) {
		Invoice invoice = new Invoice();
		serviceTax sTax = new serviceTax();
		List<Transaction> txnList = checkOutDAO.getTxnDetailsSeparate(folioNo,systemAccType);
		List<Transaction> txnListInvoice;
		List<Transaction> paymentList = new ArrayList<>();
		if (billDtls == 0) {
			txnListInvoice = checkOutDAO.getInvoiceDetails(folioNo);
		} else {
			txnListInvoice = checkOutDAO.getTxnDetailsSeparateGrop(folioNo,systemAccType);
		}
		paymentList = checkOutDAO.getPaymentList(folioNo);
		HashMap<Integer, Object> taxList = new HashMap();
		TaxSummary taxsum = null;
		double disc = 0.0, deposit = 0.0, total = 0.0, taxamt = 0.0, tax1amt = 0.0, tax2amt = 0.0, tax3amt = 0.0,
				tax4amt = 0.0, amount = 0.0, taxTotal = 0.0, serviceTax = 0.0, serviceCharge = 0.0, paidIn = 0.0,
				roundAdj = 0.0, foodExpns = 0.0, posRefundAmt = 0.0, refundAmount = 0.0;
		double tax1 = 0.0, tax2 = 0.0, tax3 = 0.0, tax4 = 0.0, tax1pc = 0.0, tax2pc = 0.0, tax3pc = 0.0, tax4pc = 0.0,
				taxKfc = 0.0;
		int serviceTaxId = 0;
		List<Transaction> posDetails = new ArrayList<>();
		Map<String, Object> refundDetails = new HashMap<String, Object>();
		try {
			for (Transaction txn : txnList) {
				taxsum = new TaxSummary();
				if (txn.getAcc_mst_id() == 6) {
					disc += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 4) {
					deposit += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 8) {
					paidIn += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 9) {
					serviceTaxId = txn.getServiceTaxId();
				} else if (txn.getAcc_mst_id() == 10) {
					roundAdj += txn.getNett_amount();
				} else if (txn.getAcc_mst_id() == 5) {
					refundAmount += txn.getNett_amount();
				}
			/*	else if (txn.getAcc_mst_id() == 12) {
					foodExpns += txn.getBase_amount();
					posDetails.add(txn);
					refundDetails = (Map<String, Object>) txnDao.getOrderDetails(txn.getTxn_no());
				}*/
				else if (txn.getAcc_mst_id() == 13) {
					posRefundAmt += txn.getNett_amount();
					txn.setBase_amount(-(txn.getNett_amount()));
					posDetails.add(txn);
				} else if (txn.getAcc_mst_id() == 14) {
					txn.setBase_amount(0);
				} else {
					total += txn.getBase_amount();
				}

				serviceCharge += txn.getServiceCharge().doubleValue();

				if (txn.getTax_id() != 1) {
					taxsum.setTaxId(txn.getTax_id());
					taxsum.setTaxCode(txn.getTax_code());
					taxsum.setTaxIndicator(txn.getTax_indicator());
					if (txn.getAcc_mst_id() == 6) {
						taxsum.setAmount(-(txn.getBase_amount()));
					} else {
						taxsum.setAmount(txn.getBase_amount());
					}
					taxsum.setTax1Amount(txn.getTax1_amount());
					taxsum.setTax2Amount(txn.getTax2_amount());
					taxsum.setTax3Amount(txn.getTax3_amount());
					taxsum.setTax4Amount(txn.getTax4_amount());
					taxsum.setTaxAmount(
							txn.getTax1_amount() + txn.getTax2_amount() + txn.getTax3_amount() + txn.getTax4_amount());
					taxsum.setTaxPc(txn.getTax1_pc() + txn.getTax2_pc() + txn.getTax3_pc() + txn.getTax4_pc());
					taxTotal = taxTotal + taxsum.getTaxAmount();
					if (taxList.containsKey(txn.getTax_id())) {
						TaxSummary taxsum2 = (TaxSummary) taxList.get(txn.getTax_id());
						taxamt = taxsum.getTaxAmount() + taxsum2.getTaxAmount();
						tax1amt = taxsum.getTax1Amount() + taxsum2.getTax1Amount();
						tax2amt = taxsum.getTax2Amount() + taxsum2.getTax2Amount();
						tax3amt = taxsum.getTax3Amount() + taxsum2.getTax3Amount();
						tax4amt = taxsum.getTax4Amount() + taxsum2.getTax4Amount();
						taxsum2.setTax1Amount(tax1amt);
						taxsum2.setTax2Amount(tax2amt);
						taxsum2.setTax3Amount(tax3amt);
						taxsum2.setTax4Amount(tax4amt);
						taxsum2.setTaxAmount(taxamt);
						amount = taxsum.getAmount() + taxsum2.getAmount();
						taxsum2.setAmount(amount);
						taxList.put(txn.getTax_id(), taxsum2);
					} else {
						taxList.put(txn.getTax_id(), taxsum);
					}
					tax1 += txn.getTax1_amount();
					tax2 += txn.getTax2_amount();
					tax3 += txn.getTax3_amount();
					tax4 += txn.getTax4_amount();
					tax1pc += txn.getTax1_pc();
					tax2pc += txn.getTax2_pc();
					tax3pc += txn.getTax3_pc();
					tax4pc += txn.getTax4_pc();

					if (txn.getTax3_pc() > 0) {
						taxKfc = txn.getTax3_pc();
					}
				}

			}

			SystemSettings systemSettings = systemSettingsDao.getSystemSettings();
			Currency curr = (Currency) session.getAttribute("currencyObject");
			invoice.setTax3_pc_kfc(taxKfc);
			invoice.setTax1Code(systemSettings.getTax1Name());
			invoice.setTax2Code(systemSettings.getTax2Name());
			invoice.setTax3Code(systemSettings.getTax3Name());
			invoice.setTax4Code(systemSettings.getTax4Name());
			invoice.setTax1((tax1));
			// invoice.setTax2(Rounding.nRound(tax2));
			invoice.setTax2((tax2));
			invoice.setTax3((tax3));
			invoice.setTax4((tax4));
			invoice.setTax1_pc(tax1pc);
			invoice.setTax2_pc(tax2pc);
			invoice.setTax3_pc(tax3pc);
			invoice.setTax4_pc(tax4pc);
			JsonObject jObj = checkOutDAO.getCustomerDetails(folioNo);
			if (jObj != null) {
				invoice.setName(String.valueOf(jObj.get("name").getAsString()));
				if (jObj.get("guest_name") != null && !jObj.get("guest_name").isJsonNull()) {
					invoice.setGuestName(String.valueOf(jObj.get("guest_name").getAsString()));
				}
				invoice.setPhone(String.valueOf(jObj.get("phone").getAsString()));
				invoice.setEmail(String.valueOf(jObj.get("email").getAsString()));
				invoice.setAddress(String.valueOf(jObj.get("address").getAsString()));
				invoice.setState(String.valueOf(jObj.get("state").getAsString()));
				invoice.setNationality(String.valueOf(jObj.get("nationality").getAsString()));
				invoice.setCheckInDate(String.valueOf(jObj.get("arrDate").getAsString()));
				invoice.setRoom_number(String.valueOf(jObj.get("roomNumber").getAsString()));
				if (!(jObj.get("rateDescription")).isJsonNull()) {
					invoice.setRate_description(String.valueOf(jObj.get("rateDescription").getAsString()));
				} else {
					invoice.setRate_description("");
				}
				if (!jObj.get("depDate").isJsonNull()) {
					invoice.setCheckOutDate(String.valueOf(jObj.get("depDate").getAsString()));
				}
				if (!jObj.get("gstno").isJsonNull()) {
					invoice.setGstno(String.valueOf(jObj.get("gstno").getAsString()));
				}
				invoice.setArr_time(String.valueOf(jObj.get("arrTime").getAsString()));
				if (!jObj.get("act_depart_time").isJsonNull()) {
					invoice.setAct_depart_time(String.valueOf(jObj.get("act_depart_time").getAsString()));
				}
				if (!jObj.get("invoiceNo").isJsonNull()) {
					invoice.setInvoiceNo(jObj.get("invoiceNo").getAsString());
				}
			}
			invoice.setTxnList(txnList);
			invoice.setTxnListInvoice(txnListInvoice);
			invoice.setPaymentList(paymentList);
			invoice.setTaxList(taxList);
			invoice.setDiscount((disc));
			invoice.setServiceCharge((serviceCharge));
			invoice.setCurrencySymbol(curr.getSymbol());
			// invoice.setCurrencySymbol(commonSettings.currencySymbol);
			invoice.setTotal((total));
			invoice.setTotalWithoutDisc((total - disc));
			if (serviceTaxId != 0) {
				sTax = paymentDao.getserviceTax(serviceTaxId, total + serviceCharge - disc);
				serviceTax = sTax.getTotalServiceTax();
			}
			invoice.setSeriveTax(sTax);
			invoice.setDeposit(Rounding.nRound(deposit));
			invoice.setPayments(Rounding.nRound(paidIn));
			invoice.setFoodExpense(foodExpns);
			invoice.setPosDetails(posDetails);
			invoice.setRefundDetails(refundDetails);
			invoice.setFoodRefund(posRefundAmt);

			double grandTotal = (invoice.getTotalWithoutDisc() + taxTotal + serviceCharge + serviceTax + foodExpns)
					-  posRefundAmt;
			
			grandTotal = Rounding.roundOff(new BigDecimal(grandTotal), 2, true);

			invoice.setGrandTotal(grandTotal);
			if (roundAdj < 0) {
				invoice.setRoundAdjust(-roundAdj);
			} else {
				invoice.setRoundAdjust(roundAdj);
			}
			invoice.setBalance(
					((total - disc) + taxTotal + serviceCharge + serviceTax - (deposit + paidIn + roundAdj)));

			// invoice.setBalance(Rounding.nRound(invoice.getGrandTotal()-(deposit+paidIn+roundAdj)));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getTxnDetails " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return invoice;
	}

	@Transactional
	public JsonArray getCheckInCheckOutData(String wherepart) throws Exception {
		return checkOutDAO.getCheckInCheckOutData(wherepart);
	}
	
	@Transactional
	public String getReportName(int  sysAccType) throws Exception {
		return checkOutDAO.getReportName(sysAccType);
	}

	@Transactional
	public String getmailCheckOut(JsonArray jArray) throws Exception {
		String isSucess = "success";
		Properties prop = new Properties();
		InputStream input = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		SystemSettings systemSettings = systemSettingsDao.getSystemSettings();

		if (commonSettings.isMailNotifyCheckOut()) {
			EmailTemplate emailTmpl = null;
			JsonObject checkOutData = null;
			String mailContent = null;
			String mailStatusMessage = "Mail sent successfully";
			boolean mailStatus = true;

			try {
				emailTmpl = templateDao.getEmailTemplate(7);
				for (int i = 0; i < jArray.size(); i++) {
					input = loader.getResourceAsStream("/sms.properties");
					prop.load(input);
					checkOutData = checkOutDAO.getCustomerDetails(jArray.get(i).getAsInt());

					final String username = systemSettings.getSmtpSUserId();
					final String password = systemSettings.getSmtpPassword();

					/*
					 * Properties properties = System.getProperties();
					 * properties.setProperty("mail.smtp.host", systemSettings.getSmtpServer());
					 * 
					 * // SSL Port properties.put("mail.smtp.port", systemSettings.getSmtpPort());
					 * // enable authentication properties.put("mail.smtp.auth", "true"); // SSL
					 * Factory properties.put("mail.smtp.socketFactory.class",
					 * "javax.net.ssl.SSLSocketFactory");
					 */

					Properties properties = System.getProperties();
					properties.put("mail.smtp.host", systemSettings.getSmtpServer());
					properties.put("mail.transport.protocol.", "smtp");
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.", "true");
					properties.put("mail.smtp.port", systemSettings.getSmtpPort());
					properties.put("mail.debug", "true");
					properties.put("mail.smtp.socketFactory.port", systemSettings.getSmtpPort());
					properties.put("mail.smtp.starttls.enable", "true");
					properties.put("mail.smtp.ssl.trust", systemSettings.getSmtpServer());

					Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {

						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					});

					MimeMessage message = new MimeMessage(session);

					// header field of the header.
					message.setFrom(new InternetAddress(systemSettings.getSmtpSUserId(), "Niko Hotels"));

					message.addRecipient(Message.RecipientType.TO,
							new InternetAddress(checkOutData.get("email").getAsString()));
					if (prop.getProperty("EMAILCC") != null) {
						message.addRecipient(Message.RecipientType.CC,
								new InternetAddress(prop.getProperty("EMAILCC")));
					}
					message.setReplyTo(new javax.mail.Address[] {
							new javax.mail.internet.InternetAddress("no-reply@niko-inn.com") });
					message.setSubject("Niko Hotels");
					mailContent = emailTmpl.getContent();
					mailContent = mailContent.replace("{checkin_by_name}", checkOutData.get("name").getAsString());
					mailContent = mailContent.replace("{checkinNo}", checkOutData.get("checkinnum").getAsString());
					mailContent = mailContent.replace("{max_depart_date}", checkOutData.get("depDate").getAsString());
					mailContent = mailContent.replace("{checkin_arr_date}", checkOutData.get("arrDate").getAsString());
					mailContent = mailContent.replace("{checkin_nights}", checkOutData.get("numnights").getAsString());
					mailContent = mailContent.replace("{checkin_roomNumber}",
							checkOutData.get("roomNumber").getAsString());
					URL url = new URL(prop.getProperty("FDBKURL"));
					URLConnection con = url.openConnection();
					StringBuilder contentBuilder = new StringBuilder();
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String str;
					while ((str = in.readLine()) != null) {
						contentBuilder.append(str);
					}
					in.close();
					String content = contentBuilder.toString();
					message.setContent(mailContent + content, "text/html");

					// Send message
					Transport.send(message);
				}

			} catch (Exception e) {
				logger.error("Method : Checkout Email " + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
				mailStatusMessage = e.getMessage();
				mailStatus = false;

			}

			CommunicationDetails commDtl = new CommunicationDetails();
			commDtl.setCommunicationType((byte) CommunicationType.EMAIL.getCode());
			commDtl.setResvNum(checkOutData.get("checkinnum").getAsInt());
			commDtl.setPurpose((byte) CommunicationPurposes.CANCELLATION.getCode());
			commDtl.setStatus(mailStatus);
			commDtl.setDescription(mailStatusMessage);
			commDtl.setPhone(checkOutData.get("phone").getAsString());
			commDtl.setEmailto(checkOutData.get("email").getAsString());
			commDtl.setEmailcc(prop.getProperty("EMAILCC"));
			commDtl.setSubject(emailTmpl.getSubject());
			commDtl.setContent(mailContent);
			try {
				commonService.saveCommunicationDetails(commDtl);
			} catch (Exception e) {
				logger.error("Method : Save Email send status" + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
			}

		}

		return isSucess;
	}

	@Override
	public String sendSms(JsonArray jArray) {

		boolean status = false;
		String smsStatusMessage = "SMS sent successfully";
		List<SmsTemplate> smsTemplate = null;
		JsonObject checkOutData = null;
		String smsContent = null;
		Integer isdCode = 0;
		Properties prop = new Properties();
		InputStream input = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String name = "";
		if (commonSettings.isSmsNotifyCheckOut()) {
			try {

				checkOutData = checkOutDAO.getCustomerDetails(jArray.get(0).getAsInt());
				name = checkOutData.get("name").getAsString();
				smsTemplate = templateDao.getSmsTemplateDtls();
				smsContent = smsTemplate.get(0).getContent();
				isdCode = getIsdCode(checkOutData.get("nationality").getAsString());

				input = loader.getResourceAsStream("/sms.properties");
				prop.load(input);

				// Construct data
				String apiKey = "apikey=" + prop.getProperty("SMSAPIKEY");
				String message = "&message=" + smsContent.replace("{name}", name);
				// String numbers = "&numbers=" + isdCode +
				// checkOutData.get("phone").getAsString();
				String numbers = "";
				if (prop.getProperty("SMSAPICC") != null) {
					numbers = "&numbers=" + prop.getProperty("SMSAPICC") + isdCode
							+ checkOutData.get("phone").getAsString();
				} else {
					numbers = "&numbers=" + isdCode + checkOutData.get("phone").getAsString();
				}
				String sender = "&sender=" + prop.getProperty("SMSAPISENDERID");
				// Send data
				HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?")
						.openConnection();
				String data = apiKey + numbers + message + sender;
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
				conn.getOutputStream().write(data.getBytes("UTF-8"));
				final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				final StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					stringBuffer.append(line);
				}
				rd.close();

				JsonParser parser = new JsonParser();
				JsonObject responseObject = parser.parse(stringBuffer.toString()).getAsJsonObject();
				if ((responseObject.get("status").getAsString()).equals("success")) {
					status = true;
				}
				smsStatusMessage = stringBuffer.toString();

			} catch (Exception e) {
				smsStatusMessage = e.toString();
			}

			CommunicationDetails commDtl = new CommunicationDetails();
			commDtl.setCommunicationType((byte) CommunicationType.SMS.getCode());
			commDtl.setResvNum(checkOutData.get("checkinnum").getAsInt());
			commDtl.setPurpose((byte) CommunicationPurposes.THANKS.getCode());
			commDtl.setStatus(status);
			commDtl.setDescription(smsStatusMessage);
			commDtl.setPhone(checkOutData.get("phone").getAsString());
			commDtl.setEmailto(checkOutData.get("email").getAsString());
			commDtl.setEmailcc(prop.getProperty("SMSAPICC"));
			commDtl.setContent(smsContent.replace("{name}", name));
			try {
				commonService.saveCommunicationDetails(commDtl);
			} catch (Exception e) {
				logger.error("Method : Save Email send status" + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
			}
		}

		return smsStatusMessage;
	}

	public Integer getIsdCode(String country) {
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		String sql = "select isdcode from  countries where name=" + "'" + country + "'";
		Integer isdCode = 0;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {

				isdCode = rs.getInt("isdcode");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
		}

		return isdCode;
	}

	@Override
	public JsonObject loadAddressWithCheckinNo(int checkinNo) {

		return checkOutDAO.loadAddressWithCheckinNo(checkinNo);
	}

	@Transactional
	public List<Transaction> getTxnSummary(int folioNo, HttpSession session, int billDtls) {
		// Invoice txnSummary = new Invoice();
		// serviceTax sTax = new serviceTax();
		List<Transaction> txnListSummary = checkOutDAO.getTxnSummary(folioNo);
		/*
		 * if (billDtls == 0) { txnListSummary = checkOutDAO.getInvoiceDetails(folioNo);
		 * } else { txnListSummary = checkOutDAO.getInvoiceGroupDetails(folioNo); }
		 */

		return txnListSummary;
	}
	@Transactional
	public List<Transaction> getTxnSummarySeparate(int folioNo, HttpSession session, int billDtls,int sysAccType) {
		// Invoice txnSummary = new Invoice();
		// serviceTax sTax = new serviceTax();
		List<Transaction> txnListSummary = checkOutDAO.getTxnSummarySeparate(folioNo,sysAccType);
		/*
		 * if (billDtls == 0) { txnListSummary = checkOutDAO.getInvoiceDetails(folioNo);
		 * } else { txnListSummary = checkOutDAO.getInvoiceGroupDetails(folioNo); }
		 */

		return txnListSummary;
	}

	@SuppressWarnings({ "unlikely-arg-type", "null" })
	@Override
	public HttpServletResponse sendInvoiceMail(Integer folioNo,ByteArrayOutputStream baos,SystemSettings systemSettings) throws Exception {
		templateDao=new TemplateDaoImpl();
		checkOutDAO=new CheckOutDAOImp();
		HttpServletResponse response = null;
		String status=null;
		Properties prop = new Properties();
		InputStream input = null;
		InputStream inputPath = null;
		String absolutePath=null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			if (commonSettings.isMailNotifyCheckOut()) {
				
				EmailTemplate emailTmpl = new EmailTemplate();
				JsonObject checkOutData = null;
				String mailContent = null;
				String mailStatusMessage = "Mail sent successfully";
				boolean mailStatus = true;
				emailTmpl = templateDao.getEmailTemplate(8);
				input = loader.getResourceAsStream("/sms.properties");
				prop.load(input);
				checkOutData = checkOutDAO.getCustomerDetails(folioNo);
				JsonElement mail=checkOutData.get("email");
               if(mail.getAsString().equals("")) {
				final String username = systemSettings.getSmtpSUserId();
				final String password = systemSettings.getSmtpPassword();
				Properties properties = System.getProperties();
				properties.put("mail.smtp.host", systemSettings.getSmtpServer());
				properties.put("mail.transport.protocol.", "smtp");
				properties.put("mail.smtp.auth","true");
				properties.put("mail.smtp.","true");
				properties.put("mail.smtp.port",systemSettings.getSmtpPort());
				properties.put("mail.debug","true");
				properties.put("mail.smtp.socketFactory.port",systemSettings.getSmtpPort());
				properties.put("mail.smtp.ssl.trust",systemSettings.getSmtpServer());
				properties.put("mail.smtp.user",systemSettings.getSmtpSUserId());
				properties.put("mail.smtp.ssl.enable", "true");
				properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

				Session session1 = Session.getInstance(properties, new javax.mail.Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication(username, password);
				    }
				});
				MimeMessage message = new MimeMessage(session1);
				MimeBodyPart messageBodyPart = new MimeBodyPart();

				// header field of the header.
				message.setFrom(new InternetAddress(systemSettings.getSmtpSUserId(), "Niko Hotels"));

				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(checkOutData.get("email").getAsString()));
				if (prop.getProperty("EMAILCC") != null) {
					message.addRecipient(Message.RecipientType.CC,
							new InternetAddress(prop.getProperty("EMAILCC")));
				}
				message.setReplyTo(new javax.mail.Address[] {
						new javax.mail.internet.InternetAddress("no-reply@niko-inn.com") });
				message.setSubject("Niko Hotels");
				mailContent = emailTmpl.getContent();
				mailContent = mailContent.replace("{checkin_by_name}", checkOutData.get("name").getAsString());
				mailContent = mailContent.replace("{checkinNo}", checkOutData.get("checkinnum").getAsString());
				mailContent = mailContent.replace("{max_depart_date}", checkOutData.get("depDate").getAsString());
				mailContent = mailContent.replace("{checkin_arr_date}", checkOutData.get("arrDate").getAsString());
				mailContent = mailContent.replace("{checkin_nights}", checkOutData.get("numnights").getAsString());
				mailContent = mailContent.replace("{checkin_roomNumber}",
						checkOutData.get("roomNumber").getAsString());
				MimeMultipart multipart = new MimeMultipart();
				byte[] bytes = baos.toByteArray();
				ByteArrayDataSource source = new ByteArrayDataSource(bytes, "application/pdf");
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName("Invoice.pdf");
				multipart.addBodyPart(messageBodyPart);
				MimeBodyPart htmlBodyPart = new MimeBodyPart();    

				URL url = new URL(prop.getProperty("FDBKURL"));
				URLConnection con = url.openConnection();
				StringBuilder contentBuilder = new StringBuilder();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String str;
				while ((str = in.readLine()) != null) {
					contentBuilder.append(str);
				}
				in.close();
				String content = contentBuilder.toString();
				htmlBodyPart.setContent(mailContent + content , "text/html"); 
				multipart.addBodyPart(htmlBodyPart);
				message.setContent(multipart);

				// Send message
				Transport.send(message);
				status="success";
			}else {
				status="Invalid Email id";
			}
			}
		} catch (Exception e) {
			status="failed";
			e.printStackTrace();
		}
		response.getWriter().print(status);
		return response;



	}

	/*private ModelAndView getInvoice(int folioNo) {
		int billDtls=1;
		List<Transaction> invoice = null;
		invoice = checkOutDAO.getTxnDetails(folioNo);
		InvoiceTemplate invTmpl = new InvoiceTemplate();
		invTmpl = templateDao.getInvoiceTemplateDtls();
		invTmpl.setHdrLogoUrl(baseUrl + "pms/resources/common/images/logos/invoicelogo.png");
		SystemSettings systemSettings = systemSettingsService.getSystemSettings();
		// Currency currency =
		// currencyService.getRecord(systemSettings.getBaseCurrencyId());
		invoice.setHeaderFooter(invTmpl);
		invoice.setSystemseting(systemSettings);
		invoice.setPrintMode(printMode);
		invoice.setBillDetails(billDtls);
		invoice.setConstNationality("India");
		invoice.setConstState("Kerala");
		invoice.setTxnListSummary(checkOutService.getTxnSummary(folioNo, session, billDtls));

		if (billDtls == 0) {
			pdfName = "pdfView";
		} else {
			pdfName = "pdfViewDetailed";
		}
		return null;

	}*/

}
