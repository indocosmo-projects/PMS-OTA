package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PettyAmount {

	PAYMENT(1,"payment"),CONTRA(2,"contra"),JOURNAL(3,"Journal");
	
	private static final Map<Integer, PettyAmount> PETTYPEMAP = new HashMap<Integer, PettyAmount>();
	
	static {
		for (PettyAmount rateType : EnumSet.allOf(PettyAmount.class))
			PETTYPEMAP.put(rateType.getCode(), rateType);
	}
	
	private int code;
	private String amountType;
	
	private PettyAmount(int code, String name) {
		this.code = code;
		this.amountType = name;
	}

	public int getCode() {
		return this.code;
	}

	public String PettyAmount() {
		return this.amountType;
	}

	public static PettyAmount get(int code) {
		return PETTYPEMAP.get(code);
	}
}
