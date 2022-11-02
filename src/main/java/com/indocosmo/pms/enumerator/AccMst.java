package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AccMst {
	ROOMREVENUE(1, "ROOM REVENUE"), OTHERREVENUE(2, "OTHER REVENUE"), OUTLETREVENUE(3, "OUTLET REVENUE"), DEPOSIT(4,
			"DEPOSIT"), REFUND(5, "REFUND"), DISCOUNT(6, "DISCOUNT"), FORFEIT(7,
					"FORFEIT"), PAIDIN(8, "PAID IN"), SERVICE_TAX(9, "SERVICE TAX"), ROUNDADJUST(10, "ROUND ADJUST");

	private static final Map<Integer, AccMst> ACCMSTMAP = new HashMap<Integer, AccMst>();

	private int sysdef_acc_type_id;
	private String name;

	private AccMst(int systemDefId, String name) {
		this.sysdef_acc_type_id = systemDefId;
		this.name = name;
	}

	public int getSysdef_acc_type_id() {
		return sysdef_acc_type_id;
	}

	public String getName() {
		return name;
	}

	public static Map<Integer, AccMst> getAccmstmap() {
		return ACCMSTMAP;
	}

	static {
		for (AccMst accMst : EnumSet.allOf(AccMst.class))
			ACCMSTMAP.put(accMst.getSysdef_acc_type_id(), accMst);
	}
}
