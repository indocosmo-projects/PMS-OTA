package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CheckinTypes {
	FIT(1, "FIT"), CORPORATE(2, "CORPORATE"), TA(3, "TA"), FAMILIES(4, "NEIGHBOURS"), FRIENDS(5, "FRIENDS");

	private static final Map<Integer, CheckinTypes> RESERVATIONTYPEMAP = new HashMap<Integer, CheckinTypes>();

	static {
		for (CheckinTypes reservationType : EnumSet.allOf(CheckinTypes.class))
			RESERVATIONTYPEMAP.put(reservationType.getId(), reservationType);
	}

	private int id;
	private String code;

	private CheckinTypes(int id, String code) {
		this.id = id;
		this.code = code;
	}

	public int getId() {
		return this.id;
	}

	public String reservationType() {
		return this.code;
	}

	public static CheckinTypes get(int id) {
		return RESERVATIONTYPEMAP.get(id);
	}
}