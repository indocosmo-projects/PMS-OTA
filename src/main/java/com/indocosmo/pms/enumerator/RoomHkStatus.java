package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RoomHkStatus {
	DIRTY(1, "DIRTY"), CLEANING(2, "CLEANING"), CLEANINSPECTED(3, "CLEAN INSPECTED");

	private static final Map<Integer, RoomHkStatus> ROOMHKSTATUSMAP = new HashMap<Integer, RoomHkStatus>();

	static {
		for (RoomHkStatus roomHkStatus : EnumSet.allOf(RoomHkStatus.class))
			ROOMHKSTATUSMAP.put(roomHkStatus.getCode(), roomHkStatus);
	}

	private int code;
	private String roomHkStatus;

	private RoomHkStatus(int code, String name) {
		this.code = code;
		this.roomHkStatus = name;
	}

	public int getCode() {
		return this.code;
	}

	public String getRoomHkStatus() {
		return this.roomHkStatus;
	}

	public static RoomHkStatus get(int code) {
		return ROOMHKSTATUSMAP.get(code);
	}
}