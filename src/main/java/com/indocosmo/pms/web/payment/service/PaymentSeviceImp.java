package com.indocosmo.pms.web.payment.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkOut.dao.CheckOutDAO;
import com.indocosmo.pms.web.common.setttings.Rounding;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.payment.dao.PaymentDAO;
import com.indocosmo.pms.web.payment.model.Payable;
import com.indocosmo.pms.web.serviceTax.model.serviceTax;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.transaction.dao.TxnDao;
import com.indocosmo.pms.web.transaction.model.Transaction;

@Service
public class PaymentSeviceImp implements PaymentService {

	@Autowired
	PaymentDAO paymentDao;

	@Autowired
	TxnDao transactionDao;

	@Autowired
	CheckOutDAO checkoutDao;

	public static final Logger logger = LoggerFactory.getLogger(PaymentSeviceImp.class);

	@Transactional
	public Payable getPaymentDetails(int folioNo){
		Payable payable=new Payable();
		List<Transaction> txnList= checkoutDao.getTxnDetails(folioNo);
		int stId=0;
		double disc=0.0,deposits=0.0,roomCharge=0.0,total=0.0,taxTotal=0.0,serviceCharge=0.0,serviceTax=0.0,otherCharge=0.0,totalPayable=0.0,paidIn=0.0,roundAdj=0.0,foodExpns=0.0,posRefundAmt=0.0,complimentary=0.0;
		double totalAmt = 0.0;
		try{
			for(Transaction txn : txnList){
				//totalAmt+=txn.getNett_amount();
				if(txn.getAcc_mst_id()==6){
					disc+=txn.getBase_amount();
				}else if(txn.getAcc_mst_id()==4){
					deposits+=txn.getBase_amount();
				}else if(txn.getAcc_mst_id()==1){
					roomCharge+=txn.getBase_amount();
				}else if(txn.getAcc_mst_id()==8){
					paidIn+=txn.getBase_amount();	
				}else if(txn.getAcc_mst_id()==9){
					serviceTax+=txn.getBase_amount();	
				}else if(txn.getAcc_mst_id()==10){
					roundAdj+=txn.getNett_amount();	
				}else if(txn.getAcc_mst_id()==12) {
					foodExpns += txn.getBase_amount();
				}else if(txn.getAcc_mst_id()==13) {
					posRefundAmt += txn.getNett_amount();
				}else if(txn.getAcc_mst_id()==14) {
					complimentary+=txn.getBase_amount();
				}
				else{
					otherCharge+=txn.getBase_amount();
				}
				taxTotal +=(txn.getTax1_amount()+txn.getTax2_amount()+txn.getTax3_amount()+txn.getTax4_amount());
				serviceCharge+= txn.getServiceCharge().doubleValue();
			}
			total=roomCharge+otherCharge+serviceCharge+foodExpns-posRefundAmt-disc;
			if(serviceTax==0){
				if(commonSettings.isServiceTaxIncluded){
					serviceTax stObj=paymentDao.getserviceTax(commonSettings.getServiceTaxId(),total);
					serviceTax=stObj.getTotalServiceTax();
				}
			}
			total=(double)(total-deposits-paidIn-roundAdj-complimentary);
			
			totalPayable=total+taxTotal+serviceTax;
			//totalPayable=Math.abs(totalAmt);
			payable.setDeposits(String.valueOf(deposits));
			payable.setDiscounts(String.valueOf(disc));
			payable.setOtherCharges(String.valueOf(otherCharge));
			payable.setRoomCharges(String.valueOf(roomCharge));
			payable.setServiceCharge(String.valueOf(serviceCharge));
			payable.setTax(String.valueOf(Rounding.roundToTwo(taxTotal)));
			payable.setServiceTax(String.valueOf(serviceTax));
			payable.setPaidIn(String.valueOf(paidIn));
			payable.setRoundAdjust(String.valueOf(roundAdj));
			//payable.setTotal(totalPayable);
			payable.setTotal(String.valueOf(new BigDecimal(totalPayable).setScale(2, RoundingMode.HALF_UP).doubleValue()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getPaymentDetails " + Throwables.getStackTraceAsString(e));
		}

		return payable;
	}

	/*@Transactional
	public String save(Transaction txn)throws Exception{
		String stat="success";
		Payable pay=new Payable();
		pay=getPaymentDetails(txn.getFolio_no());
		double serviceTax=pay.getServiceTax();
		double total=pay.getTotal();
		double txnAmt=txn.getAmount();
		if(commonSettings.isServiceTaxIncluded){
			Transaction txnST= new Transaction();			
			try{				
				txnST.setFolio_no(txn.getFolio_no());
				txnST.setFolio_bind_no(txn.getFolio_bind_no());
				txnST.setAmount(serviceTax);
				txnST.setNett_amount(-serviceTax);
				txnST.setBase_amount(serviceTax);
				txnST.setTax1_pc(0.0);
				txnST.setTax2_pc(0.0);
				txnST.setTax3_pc(0.0);
				txnST.setTax4_pc(0.0);
				txnST.setTax1_amount(0.0);
				txnST.setTax2_amount(0.0);
				txnST.setTax3_amount(0.0);
				txnST.setTax4_amount(0.0);
				txnST.setServiceCharge(BigDecimal.valueOf(0.0));
				txnST.setServiceChargePc(BigDecimal.valueOf(0.0));
				txnST.setAcc_mst_id(9);
				AccountMaster accm =new AccountMaster();
				accm = transactionDao.getAccMstVal(9);			
				txnST.setAcc_mst_code(accm.getCode());
				txnST.setServiceTaxId(commonSettings.getServiceTaxId());
				txnST.setTxn_source(txn.getTxn_source());
				txnST.setTxn_date(txn.getTxn_date());
				txnST.setTxn_time(txn.getTxn_time());
				TaxHdr taxHdr=transactionDao.getTaxCode(accm.getTax_id());
				txnST.setTax_id(accm.getTax_id());
				txnST.setTax_code(taxHdr.getCode());
				
				txnST.setTxn_status(txn.getTxn_status());
				txnST.setRemarks("Service Tax");
				
				txnST.setUser_id(txn.getUser_id());
				txnST.setReceived_from(txn.getReceived_from());
				txnST.setShift_id(txn.getShift_id());
			}catch(Exception e){
				e.printStackTrace();
				logger.error("Method : save " + Throwables.getStackTraceAsString(e));
			}
			stat=paymentDao.save(txnST);	
		}

		if(stat=="success"){
			stat=paymentDao.save(txn);
		}

		if(stat=="success"){
			if(pay.getTotal()!=txn.getAmount()){
				Transaction txnRA= new Transaction();	
				double bAdj=0.0;
				try{
					total=Math.abs(total);
					txnAmt=Math.abs(txnAmt);
					
					if(txn.getAcc_mst_id()==5){
						bAdj=txnAmt-total;
					}else{
						bAdj=total-txnAmt;
					}
					txnRA.setFolio_no(txn.getFolio_no());
					txnRA.setFolio_bind_no(txn.getFolio_bind_no());
					txnRA.setAmount(bAdj);
					txnRA.setBase_amount(bAdj);
					txnRA.setNett_amount(bAdj);
					txnRA.setTax1_pc(0.0);
					txnRA.setTax2_pc(0.0);
					txnRA.setTax3_pc(0.0);
					txnRA.setTax4_pc(0.0);
					txnRA.setTax1_amount(0.0);
					txnRA.setTax2_amount(0.0);
					txnRA.setTax3_amount(0.0);
					txnRA.setTax4_amount(0.0);
					txnRA.setServiceCharge(BigDecimal.valueOf(0.0));
					txnRA.setServiceChargePc(BigDecimal.valueOf(0.0));
					txnRA.setAcc_mst_id(10);
					AccountMaster accm =new AccountMaster();
					accm = transactionDao.getAccMstVal(10);			
					txnRA.setAcc_mst_code(accm.getCode());
					txnRA.setTxn_source(txn.getTxn_source());
					txnRA.setTxn_date(txn.getTxn_date());
					txnRA.setTxn_time(txn.getTxn_time());
					TaxHdr taxHdr=transactionDao.getTaxCode(accm.getTax_id());
					txnRA.setTax_id(accm.getTax_id());
					txnRA.setTax_code(taxHdr.getCode());
					txnRA.setTxn_status(txn.getTxn_status());
					txnRA.setRemarks("Round Adjustment");
					txnRA.setUser_id(txn.getUser_id());
					txnRA.setReceived_from(txn.getReceived_from());

					txnRA.setShift_id(txn.getShift_id());

				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Method : save " + Throwables.getStackTraceAsString(e));
				}
				stat=paymentDao.save(txnRA);	
			}
		}
		if(stat=="success"){
			stat=paymentDao.setFinalSettleMent(txn.getFolio_no());
		}
		return stat;
	}*/
	
	
	
	
	@Transactional
	public String save(Transaction txn)throws Exception{
		String stat="success";
		Payable pay=new Payable();
		pay=getPaymentDetails(txn.getFolio_no());
		double serviceTax=Double.parseDouble(pay.getServiceTax());
		double total=Double.parseDouble(pay.getTotal());
		double txnAmt=txn.getAmount();
		if(commonSettings.isServiceTaxIncluded){
			Transaction txnST= new Transaction();			
			try{				
				txnST.setFolio_no(txn.getFolio_no());
				txnST.setFolio_bind_no(txn.getFolio_bind_no());
				txnST.setAmount(serviceTax);
				txnST.setNett_amount(-serviceTax);
				txnST.setBase_amount(serviceTax);
				txnST.setTax1_pc(0.0);
				txnST.setTax2_pc(0.0);
				txnST.setTax3_pc(0.0);
				txnST.setTax4_pc(0.0);
				txnST.setTax1_amount(0.0);
				txnST.setTax2_amount(0.0);
				txnST.setTax3_amount(0.0);
				txnST.setTax4_amount(0.0);
				txnST.setServiceCharge(BigDecimal.valueOf(0.0));
				txnST.setServiceChargePc(BigDecimal.valueOf(0.0));
				txnST.setAcc_mst_id(9);
				AccountMaster accm =new AccountMaster();
				accm = transactionDao.getAccMstVal(9);			
				txnST.setAcc_mst_code(accm.getCode());
				txnST.setServiceTaxId(commonSettings.getServiceTaxId());
				txnST.setTxn_source(txn.getTxn_source());
				txnST.setTxn_date(txn.getTxn_date());
				txnST.setTxn_time(txn.getTxn_time());
				TaxHdr taxHdr=transactionDao.getTaxCode(accm.getTax_id());
				txnST.setTax_id(accm.getTax_id());
				txnST.setTax_code(taxHdr.getCode());
				
				txnST.setTxn_status(txn.getTxn_status());
				txnST.setRemarks("Service Tax");
				
				txnST.setUser_id(txn.getUser_id());
				txnST.setReceived_from(txn.getReceived_from());
				txnST.setShift_id(txn.getShift_id());
			}catch(Exception e){
				e.printStackTrace();
				logger.error("Method : save " + Throwables.getStackTraceAsString(e));
			}
			stat=paymentDao.save(txnST);
		}

		if(stat=="success"){
			stat=paymentDao.save(txn);
		}

		
		return stat;
	}
	@Transactional
	public String setRoundAdj(Transaction txn) throws Exception{
		Payable pay=new Payable();
		pay=getPaymentDetails(txn.getFolio_no());

		double total=Double.parseDouble(pay.getTotal());
		double txnAmt=txn.getAmount();
		String stat = "success";
		if(!pay.getTotal().equals("0.0")){
				Transaction txnRA= new Transaction();
				double bAdj=0.0;
				try{
					total=Math.abs(total);
					txnAmt=Math.abs(txnAmt);
					/*
					if(txn.getAcc_mst_id()==5){
						bAdj=txnAmt-total;
					}else{
						bAdj=total-txnAmt;
					}*/
					bAdj=Double.parseDouble(pay.getTotal());
					txnRA.setFolio_no(txn.getFolio_no());
					txnRA.setFolio_bind_no(txn.getFolio_bind_no());
					txnRA.setAmount(bAdj);
					txnRA.setBase_amount(bAdj);
					txnRA.setNett_amount(bAdj);
					txnRA.setTax1_pc(0.0);
					txnRA.setTax2_pc(0.0);
					txnRA.setTax3_pc(0.0);
					txnRA.setTax4_pc(0.0);
					txnRA.setTax1_amount(0.0);
					txnRA.setTax2_amount(0.0);
					txnRA.setTax3_amount(0.0);
					txnRA.setTax4_amount(0.0);
					txnRA.setServiceCharge(BigDecimal.valueOf(0.0));
					txnRA.setServiceChargePc(BigDecimal.valueOf(0.0));
					txnRA.setAcc_mst_id(10);
					AccountMaster accm =new AccountMaster();
					accm = transactionDao.getAccMstVal(10);			
					txnRA.setAcc_mst_code(accm.getCode());
					txnRA.setTxn_source(txn.getTxn_source());
					txnRA.setTxn_date(txn.getTxn_date());
					txnRA.setTxn_time(txn.getTxn_time());
					TaxHdr taxHdr=transactionDao.getTaxCode(accm.getTax_id());
					txnRA.setTax_id(accm.getTax_id());
					txnRA.setTax_code(taxHdr.getCode());
					txnRA.setTxn_status(txn.getTxn_status());
					txnRA.setRemarks("Round Adjustment");
					txnRA.setUser_id(txn.getUser_id());
					txnRA.setReceived_from(txn.getReceived_from());

					txnRA.setShift_id(txn.getShift_id());

				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Method : save " + Throwables.getStackTraceAsString(e));
				}
				stat=paymentDao.save(txnRA);	
		}
		if(stat=="success"){
			stat=paymentDao.setFinalSettleMent(txn.getFolio_no());
		}
		return stat;
	}
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	public JsonArray bankCardTypes() throws Exception{
	return	paymentDao.bankCardTypes();
	}
	@Transactional
	public JsonArray getCompanyDetails() throws Exception{
		return paymentDao.getCompanyDetails();
	}
	@Transactional
	public JsonArray getBankDetails()throws Exception{
		return paymentDao.getBankDetails();
	}

	@Override
	public String getCorporateName(int corporateId) {
		
		return paymentDao.getCorporateName(corporateId);
	}

	@Override
	public JsonArray getDiscountDetails(int checkinNo,int folioNo) throws Exception {
		// TODO Auto-generated method stub
		return paymentDao.getDiscountDetails(checkinNo,folioNo);
	}

	@Override
	public int getDiscount(int folio_no) throws Exception {
		// TODO Auto-generated method stub
		return paymentDao.getDiscount(folio_no);
	}

	@Override
	public String upDateDiscounts(JsonArray discountDetails) throws Exception {
		// TODO Auto-generated method stub
		return paymentDao.upDateDiscounts(discountDetails);
	}
}
