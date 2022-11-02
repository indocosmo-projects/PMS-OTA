package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PaymentMode {
	CASH(1, "CASH"), CARD(2, "CARD"), ONLINETRANSFER(3, "ONLINE TRANSFER"), DD(4, "DD"), COMPANY(5,
			"COMPANY"), COMPLEMENTARY(6, "COMPLEMENTARY");

	private static final Map<Integer, String> PAYMENTMODEMAP = new HashMap<Integer, String>();
	private static final Map<Integer, PaymentMode> ENUMLIST = new HashMap<Integer, PaymentMode>(); // NEW CORRECTED
																									// ABOVE

	static {
		for (PaymentMode paymentMode : EnumSet.allOf(PaymentMode.class)) {
			PAYMENTMODEMAP.put(paymentMode.getCode(), paymentMode.getPaymentMode());
			ENUMLIST.put(paymentMode.getCode(), paymentMode);
		}
	}

	private int code;
	private String paymentMode;

	private PaymentMode(int code, String name) {
		this.code = code;
		this.paymentMode = name;
	}

	public int getCode() {
		return this.code;
	}

	public String getPaymentMode() {
		return this.paymentMode;
	}

	public static PaymentMode get(int code) {
		return ENUMLIST.get(code);
	}

	public static Map<Integer, String> getPaymentModemap() {
		return PAYMENTMODEMAP;
	}
}