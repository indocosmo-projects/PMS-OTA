package com.indocosmo.pms.enumerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReservationStatus {
	UNCONFIRMED((byte) 0, "UNCONFIRMED"), CONFIRMED((byte) 1, "CONFIRMED"), CANCELLED((byte) 2, "CANCELLED"), NOSHOW(
			(byte) 3, "NO-SHOW"), PARTCHECKIN((byte) 4, "PART CHECK-IN"), CHECKIN((byte) 5, "CHECK-IN"), PARTCHECKOUT(
					(byte) 6, "PART CHECK-OUT"), CHECKOUT((byte) 7, "CHECK-OUT");

	private static final Map<Byte, ReservationStatus> RESERVATIONSTATUSMAP = new HashMap<Byte, ReservationStatus>();

	static {
		for (ReservationStatus reservationStatus : EnumSet.allOf(ReservationStatus.class))
			RESERVATIONSTATUSMAP.put(reservationStatus.getCode(), reservationStatus);
	}

	private byte code;
	private String reservationStatus;

	private ReservationStatus(byte code, String name) {
		this.reservationStatus = name;
		this.code = code;
	}

	public byte getCode() {
		return this.code;
	}

	public String reservationStatus() {
		return this.reservationStatus;
	}

	public static ReservationStatus get(byte code) {
		return RESERVATIONSTATUSMAP.get(code);
	}

}