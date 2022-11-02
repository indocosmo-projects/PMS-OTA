package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReasonTypes {
	REASON1(1, "Reason1"), REASON2(2, "Reason2"), REASON3(3, "Reason3"), REASON4(4, "Reason4");

	private static final Map<Integer, ReasonTypes> REASONTYPEMAP = new HashMap<Integer, ReasonTypes>();

	static {
		for (ReasonTypes reasonType : EnumSet.allOf(ReasonTypes.class))
			REASONTYPEMAP.put(reasonType.getCode(), reasonType);
	}

	private int code;
	private String name;

	private ReasonTypes(int code, String name) {
		this.setCode(code);
		this.setName(name);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ReasonTypes get(int code) {
		return REASONTYPEMAP.get(code);
	}

	public String reasonType(String reasonCode) {
		return this.name;
	}
}