package com.indocosmo.pms.web.reservation.model;

import java.math.BigDecimal;

public class RoomRateDetailsCheckArgs {

	String date;
	int rateId;
	int occupancy;
	int numRooms;
	int numNights;
	int discId;
	BigDecimal openDisount;
	int resvNo;
	int resvDtlNo;

	public int getResvDtlNo() {
		return resvDtlNo;
	}

	public void setResvDtlNo(int resvDtlNo) {
		this.resvDtlNo = resvDtlNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getRateId() {
		return rateId;
	}

	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	public int getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}

	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	public int getNumNights() {
		return numNights;
	}

	public void setNumNights(int numNights) {
		this.numNights = numNights;
	}

	public int getDiscId() {
		return discId;
	}

	public void setDiscId(int discId) {
		this.discId = discId;
	}

	public BigDecimal getOpenDisount() {
		return openDisount;
	}

	public void setOpenDisount(BigDecimal openDisount) {
		this.openDisount = openDisount;
	}

	public int getResvNo() {
		return resvNo;
	}

	public void setResvNo(int resvNo) {
		this.resvNo = resvNo;
	}

}
