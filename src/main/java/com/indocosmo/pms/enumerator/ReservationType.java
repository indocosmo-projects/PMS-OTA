package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReservationType {
	FIT(1, "FIT"), OTA(2, "OTA"), CORPORATE(3, "CORPORATE"), TA(4, "TA"), FAMILIES(5, "FAMILIES"), FRIENDS(6,
			"FRIENDS");

	private static final Map<Integer, ReservationType> RESERVATIONTYPEMAP = new HashMap<Integer, ReservationType>();

	static {
		for (ReservationType reservationType : EnumSet.allOf(ReservationType.class))
			RESERVATIONTYPEMAP.put(reservationType.getId(), reservationType);
	}

	private int id;
	private String code;

	private ReservationType(int id, String code) {
		this.id = id;
		this.code = code;
	}

	public int getId() {
		return this.id;
	}

	public String reservationType() {
		return this.code;
	}

	public static ReservationType get(int id) {
		return RESERVATIONTYPEMAP.get(id);
	}
}