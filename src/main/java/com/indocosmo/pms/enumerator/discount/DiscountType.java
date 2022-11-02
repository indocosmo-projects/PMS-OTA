package com.indocosmo.pms.enumerator.discount;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DiscountType {
	PLANBASEDDISCOUNT(1, "PLAN BASED DISCOUNT"), GENERALDISCOUNT(2, "GENERAL DISCOUNT");

	private static final Map<Integer, String> DISCOUNTTYPEMAP = new HashMap<Integer, String>();

	static {
		for (DiscountType DiscountType : EnumSet.allOf(DiscountType.class))
			DISCOUNTTYPEMAP.put(DiscountType.getCode(), DiscountType.discountType());
	}

	private int code;
	private String discountType;

	private DiscountType(int code, String name) {
		this.code = code;
		this.discountType = name;
	}

	public int getCode() {
		return this.code;
	}

	public String discountType() {
		return this.discountType;
	}

	public static Map<Integer, String> getDiscounttypemap() {
		return DISCOUNTTYPEMAP;
	}
}
