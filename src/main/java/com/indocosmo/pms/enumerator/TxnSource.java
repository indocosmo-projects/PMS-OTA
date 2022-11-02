package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TxnSource {
	FRONTOFFICEPOSTING(1, "FRONT OFFICE POSTING", "FO"), NIGHTAUDITAUTOPOSTING(2, "NIGHT AUDIT AUTO POSTING",
			"FO"), POSTINGFROMPOS(3, "POSTING FROM POS",
					"F&B"), POSTINGFROMPABXANDSIMILAR(4, "POSTING FROM PABX AND SIMILAR", "PABX");

	private static final Map<Integer, TxnSource> TXNSTATUSMAP = new HashMap<Integer, TxnSource>();

	static {
		for (TxnSource txnStatus : EnumSet.allOf(TxnSource.class))
			TXNSTATUSMAP.put(txnStatus.getCode(), txnStatus);
	}

	private int code;
	private String txnStatus;
	private String sourceName;

	private TxnSource(int code, String name, String sourceName) {
		this.code = code;
		this.txnStatus = name;
		this.sourceName = sourceName;
	}

	public int getCode() {
		return this.code;
	}

	public String txnStatus() {
		return this.txnStatus;
	}

	public String getSourceName() {
		return this.sourceName;
	}

	public static TxnSource get(int code) {
		return TXNSTATUSMAP.get(code);
	}
}