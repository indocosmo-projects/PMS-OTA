package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CommunicationPurposes {
	OTHER(0, "OTHER"), BOOKING(1, "BOOKING"), CUTOFFDATE(2, "CUTOFFDATE"), CONFIRMATION(3,
			"CONFIRMATION"), CANCELLATION(4,
					"CANCELLATION"), NOSHOW(5, "NOSHOW"), WELCOME(6, "WELCOME"), THANKS(7, "THANKS"),
	INVOICE(8,"INVOICE");

	private static final Map<Integer, CommunicationPurposes> COMMUNICATIONPURPOSEMAP = new HashMap<Integer, CommunicationPurposes>();

	static {
		for (CommunicationPurposes communicationPurpose : EnumSet.allOf(CommunicationPurposes.class))
			COMMUNICATIONPURPOSEMAP.put(communicationPurpose.getCode(), communicationPurpose);
	}
	private int code;
	private String name;

	private CommunicationPurposes(int code, String name) {
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

	public static CommunicationPurposes get(int code) {
		return COMMUNICATIONPURPOSEMAP.get(code);
	}
}
