package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TxnStatus {
	DELETED(0, "DELETED"), ACTIVE(1, "ACTIVE"), TANSFERED(2, "TANSFERED"), VALID(3, "VALID"), VOID(4, "VOID");

	private static final Map<Integer, TxnStatus> TXNSTATUSMAP = new HashMap<Integer, TxnStatus>();

	static {
		for (TxnStatus txnStatus : EnumSet.allOf(TxnStatus.class))
			TXNSTATUSMAP.put(txnStatus.getCode(), txnStatus);
	}

	private int code;
	private String txnStatus;

	private TxnStatus(int code, String name) {
		this.code = code;
		this.txnStatus = name;
	}

	public int getCode() {
		return this.code;
	}

	public String txnStatus() {
		return this.txnStatus;
	}

	public static TxnStatus get(int code) {
		return TXNSTATUSMAP.get(code);
	}
}