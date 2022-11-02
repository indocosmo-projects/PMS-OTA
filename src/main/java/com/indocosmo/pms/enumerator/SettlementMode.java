package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SettlementMode {
	CASH(1, "CASH"), CARD(2, "CARD"), ONLINETRANSFER(3, "ONLINE TRANSFER"), DD(4, "DD");

	private static final Map<Integer, String> SETTLEMENTMODEMAP = new HashMap<Integer, String>();
	private static final Map<Integer, SettlementMode> ENUMLIST = new HashMap<Integer, SettlementMode>(); // NEW
																											// CORRECTED
																											// ABOVE

	static {
		for (SettlementMode settlementMode : EnumSet.allOf(SettlementMode.class)) {
			SETTLEMENTMODEMAP.put(settlementMode.getCode(), settlementMode.getSettlementMode());
			ENUMLIST.put(settlementMode.getCode(), settlementMode);
		}
	}

	private int code;
	private String settlementMode;

	private SettlementMode(int code, String name) {
		this.code = code;
		this.settlementMode = name;
	}

	public int getCode() {
		return this.code;
	}

	public String getSettlementMode() {
		return this.settlementMode;
	}

	public static SettlementMode get(int code) {
		return ENUMLIST.get(code);
	}

	public static Map<Integer, String> getSettlementModemap() {
		return SETTLEMENTMODEMAP;
	}
}
