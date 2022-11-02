package com.indocosmo.pms.enumerator.discount;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DiscountCalculationMethod {
	PERCENTAGE(true, "PERCENTAGE"), ABSOLUTEAMOUNT(false, "ABSOLUTE AMOUNT");

	private Boolean code;
	private String discountCalculation;

	private static final Map<Boolean, String> DISCOUNTCALCULATIONMAP = new HashMap<Boolean, String>();

	static {
		for (DiscountCalculationMethod DiscountCalculationMethod : EnumSet.allOf(DiscountCalculationMethod.class))
			DISCOUNTCALCULATIONMAP.put(DiscountCalculationMethod.getCode(),
					DiscountCalculationMethod.discountCalculationMethod());
	}

	private DiscountCalculationMethod(Boolean code, String name) {
		this.code = code;
		this.discountCalculation = name;
	}

	public Boolean getCode() {
		return this.code;
	}

	public String discountCalculationMethod() {
		return this.discountCalculation;
	}

	public static Map<Boolean, String> getDiscountcalculationmap() {
		return DISCOUNTCALCULATIONMAP;
	}
}
