package com.indocosmo.pms.enumerator.common;

public enum RateTypes {
	RACKRATE(1), OTHERROOMRATES(2), CORPORATE(3), TRAVELAGENT(4);

	private RateTypes(int rateType) {
		this.setRateType(rateType);
	}

	public int getRateType() {
		return rateType;
	}

	public void setRateType(int rateType) {
		this.rateType = rateType;
	}

	private int rateType;
}
