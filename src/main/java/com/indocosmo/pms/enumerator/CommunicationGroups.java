package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CommunicationGroups {
	RESERVATION(1, "RESERVATION"), INHOUSE(2, "INHOUSE"), CHECKOUT(3, "CHECKOUT");

	private static final Map<Integer, CommunicationGroups> COMMUNICATIONGROUPMAP = new HashMap<Integer, CommunicationGroups>();

	static {
		for (CommunicationGroups communicationGroup : EnumSet.allOf(CommunicationGroups.class))
			COMMUNICATIONGROUPMAP.put(communicationGroup.getCode(), communicationGroup);
	}
	private int code;
	private String name;

	private CommunicationGroups(int code, String name) {
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

	public static CommunicationGroups get(int code) {
		return COMMUNICATIONGROUPMAP.get(code);
	}
}
