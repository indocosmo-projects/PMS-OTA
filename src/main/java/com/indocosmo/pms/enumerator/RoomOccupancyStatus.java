package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RoomOccupancyStatus {
	OCCUPIED(1, "OCCUPIED"), VACCANT(2, "VACANT");

	private static final Map<Integer, RoomOccupancyStatus> ROOMOCCUPANCYSTATUSMAP = new HashMap<Integer, RoomOccupancyStatus>();

	static {
		for (RoomOccupancyStatus roomOccupancyStatus : EnumSet.allOf(RoomOccupancyStatus.class))
			ROOMOCCUPANCYSTATUSMAP.put(roomOccupancyStatus.getCode(), roomOccupancyStatus);
	}

	private int code;
	private String roomOccupancyStatus;

	private RoomOccupancyStatus(int code, String name) {
		this.code = code;
		this.roomOccupancyStatus = name;
	}

	public int getCode() {
		return this.code;
	}

	public String roomOccupancyStatus() {
		return this.roomOccupancyStatus;
	}

	public static RoomOccupancyStatus get(int code) {
		return ROOMOCCUPANCYSTATUSMAP.get(code);
	}
}