package com.indocosmo.pms.enumerator.discount;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DiscountFor {
	ROOM(1, "ROOM"), FOOD(2, "FOOD");

	private static final Map<Integer, String> DISCOUNTFORMAP = new HashMap<Integer, String>();

	static {
		for (DiscountFor DiscountFor : EnumSet.allOf(DiscountFor.class))
			DISCOUNTFORMAP.put(DiscountFor.getCode(), DiscountFor.discountFor());
	}

	private int code;
	private String discountFor;

	private DiscountFor(int code, String name) {
		this.code = code;
		this.discountFor = name;
	}

	public int getCode() {
		return this.code;
	}

	public String discountFor() {
		return this.discountFor;
	}

	public static Map<Integer, String> getDiscountformap() {
		return DISCOUNTFORMAP;
	}
}
