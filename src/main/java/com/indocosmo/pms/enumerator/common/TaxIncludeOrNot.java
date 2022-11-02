package com.indocosmo.pms.enumerator.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TaxIncludeOrNot {
	YES(true, "YES"), NO(false, "NO");

	private Boolean code;
	private String option;

	private TaxIncludeOrNot(Boolean code, String name) {
		this.code = code;
		this.option = name;
	}

	private static final Map<Boolean, String> YESORNOMAP = new HashMap<Boolean, String>();

	static {
		for (TaxIncludeOrNot yesOrNoOptions : EnumSet.allOf(TaxIncludeOrNot.class))
			YESORNOMAP.put(yesOrNoOptions.getCode(), yesOrNoOptions.getOption());
	}

	public Boolean getCode() {
		return code;
	}

	public void setCode(Boolean code) {
		this.code = code;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public static Map<Boolean, String> getYesornomap() {
		return YESORNOMAP;
	}
}