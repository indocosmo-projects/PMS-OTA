package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RoomInventoryStatus {
	OUTOFINVENTORY(1, "OUT OF INVENTORY"), INCLUDEDININVENTORY(2, "INCLUDED IN INVENTORY");

	private static final Map<Integer, RoomInventoryStatus> ROOMINVENTORYSTATUSMAP = new HashMap<Integer, RoomInventoryStatus>();

	static {
		for (RoomInventoryStatus roomInventoryStatus : EnumSet.allOf(RoomInventoryStatus.class))
			ROOMINVENTORYSTATUSMAP.put(roomInventoryStatus.getCode(), roomInventoryStatus);
	}

	private int code;
	private String roomInventoryStatus;

	private RoomInventoryStatus(int code, String name) {
		this.code = code;
		this.roomInventoryStatus = name;
	}

	public int getCode() {
		return this.code;
	}

	public String getRoomInventoryStatus() {
		return this.roomInventoryStatus;
	}

	public static RoomInventoryStatus get(int code) {
		return ROOMINVENTORYSTATUSMAP.get(code);
	}
}