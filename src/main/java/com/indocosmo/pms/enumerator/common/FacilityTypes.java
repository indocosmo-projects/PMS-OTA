package com.indocosmo.pms.enumerator.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FacilityTypes {
	TRAVEL(1, "TRAVEL"), THEATRE(2, "THEATRE"), TREATMENT(3, "TREATMENT"), STUDY(4, "STUDY"), INHOUSE(5, "INHOUSE");
	private static final Map<Integer, FacilityTypes> FACILITYTYPESMAP = new HashMap<Integer, FacilityTypes>();
	static {
		for (FacilityTypes facilityType : EnumSet.allOf(FacilityTypes.class))
			FACILITYTYPESMAP.put(facilityType.getCode(), facilityType);
	}
	private int code;
	private String name;

	private FacilityTypes(int code, String name) {
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

	public static FacilityTypes get(int code) {
		return FACILITYTYPESMAP.get(code);
	}
}