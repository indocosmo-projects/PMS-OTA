package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CommunicationType {
	EMAIL(1, "EMAIL"), SMS(2, "SMS"), TELEPHONE(3, "TELEPHONE");
	private static final Map<Integer, CommunicationType> COMMUNICATIONTYPEMAP = new HashMap<Integer, CommunicationType>();
	static {
		for (CommunicationType communicationType : EnumSet.allOf(CommunicationType.class))
			COMMUNICATIONTYPEMAP.put(communicationType.getCode(), communicationType);
	}
	private int code;
	private String name;

	private CommunicationType(int code, String name) {
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

	public static CommunicationType get(int code) {
		return COMMUNICATIONTYPEMAP.get(code);
	}
}
