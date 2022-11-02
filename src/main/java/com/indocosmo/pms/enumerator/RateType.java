package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RateType {
	RACKRATE(1, "RACKRATE"), OTHERROOMRATE(2, "OTHER ROOM RATE"), CORPORATERATE(3, "CORPORATE RATE"), TRAVELAGENTRATE(4,
			"TRAVEL AGENT RATE");

	private static final Map<Integer, RateType> RATETYPEMAP = new HashMap<Integer, RateType>();

	static {
		for (RateType rateType : EnumSet.allOf(RateType.class))
			RATETYPEMAP.put(rateType.getCode(), rateType);
	}

	private int code;
	private String rateType;

	private RateType(int code, String name) {
		this.code = code;
		this.rateType = name;
	}

	public int getCode() {
		return this.code;
	}

	public String reservationType() {
		return this.rateType;
	}

	public static RateType get(int code) {
		return RATETYPEMAP.get(code);
	}
}