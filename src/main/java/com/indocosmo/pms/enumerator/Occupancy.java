package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Occupancy {
	SINGLE(1, "SINGLE"), DOUBLE(2, "DOUBLE"), TRIPLE(3, "TRIPLE"), QUAD(4, "QUAD");

	private static final Map<Integer, Occupancy> OCCUPANCYMAP = new HashMap<Integer, Occupancy>();

	static {
		for (Occupancy occupancy : EnumSet.allOf(Occupancy.class))
			OCCUPANCYMAP.put(occupancy.getCode(), occupancy);
	}

	private int code;
	private String occupancy;

	private Occupancy(int code, String name) {
		this.code = code;
		this.occupancy = name;
	}

	public int getCode() {
		return this.code;
	}

	public String occupancy() {
		return this.occupancy;
	}

	public static Occupancy get(int code) {
		return OCCUPANCYMAP.get(code);
	}
}
