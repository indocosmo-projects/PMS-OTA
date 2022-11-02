package com.indocosmo.pms.enumerator.corporate;

public enum CorporateClass {
	CORPORATE(1), TRAVEL_AGENT(2);

	private int value;

	private CorporateClass(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
