package com.indocosmo.pms.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.indocosmo.pms.web.checkOut.controller.CheckOutController;
import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.currency.service.CurrencyServiceImp;

public final class MoneyToWordsConvertor {

	@Autowired
	CheckOutController checkOutController;

	public MoneyToWordsConvertor() {
		// TODO Auto-generated constructor stub
	}

	public static  String toWords(double money) throws Exception {

		return toWords(String.valueOf(money));

	}

	public static  String toWords(String value) throws Exception {

		String[] moneyParts = value.split("[.]");

		final long moneyPart = Long.valueOf(moneyParts[0]);

		final long moneyFraction = (moneyParts.length == 2) ? Long.valueOf(moneyParts[1]) : 0;

		
		 String moneyInWord = NumberToWordsConverter.toWords(moneyPart);
		final String moneyFractInWord = (moneyFraction > 0) ? NumberToWordsConverter.toWords(moneyFraction) : "";
		final String moneySybol = "Rupees";// PosEnvSettings.getInstance().getCurrency().getName();
		moneyInWord  = moneyInWord.replace(" and "," ");
		
		//final String moneySybol = "Bahraini Dinar";
		//final String moneySybol=checkOutController.getCurrency();
		final String moneyFractSybol = "paisa";// PosEnvSettings.getInstance().getCurrency().getFractionName();

		//return moneyInWord
				//+ ((moneyFraction > 0 && !moneyFractInWord.equals("")) ? moneyFractInWord + " " + moneyFractSybol  
				//		: ""+" "+moneySybol+" "+" Only")
		return moneySybol+" "+moneyInWord
				+ ((moneyFraction > 0 && !moneyFractInWord.equals("")) ? " and"+moneyFractInWord+" "+ moneyFractSybol+" Only"  
							: ""+" "+" Only");
	}



}
