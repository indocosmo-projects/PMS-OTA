package com.indocosmo.pms.web.reservation.model;

import java.util.Date;

public class RoomAvailability {

	private Date arrivalDate;
	private int rooms;
	private int nights;
	private int adults;
	private int childrens;
	private String corporate_ta;

	private Date availableDate;
	private int dlx;
	private int std;
	private int supr;
	private int suite;
	private int abcd;

	private String reservation_No;
	private Date bookingDate;
	/* private Date ArrivalDate; */
	private String status;
	private int folioBindNo;

	private double totalAmount;
	private double depositAmount;

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public int getAdults() {
		return adults;
	}

	public void setAdults(int adults) {
		this.adults = adults;
	}

	public int getChildrens() {
		return childrens;
	}

	public void setChildrens(int childrens) {
		this.childrens = childrens;
	}

	public String getCorporate_ta() {
		return corporate_ta;
	}

	public void setCorporate_ta(String corporate_ta) {
		this.corporate_ta = corporate_ta;
	}

	public Date getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public int getDlx() {
		return dlx;
	}

	public void setDlx(int dlx) {
		this.dlx = dlx;
	}

	public int getStd() {
		return std;
	}

	public void setStd(int std) {
		this.std = std;
	}

	public int getSupr() {
		return supr;
	}

	public void setSupr(int supr) {
		this.supr = supr;
	}

	public int getSuite() {
		return suite;
	}

	public void setSuite(int suite) {
		this.suite = suite;
	}

	public int getAbcd() {
		return abcd;
	}

	public void setAbcd(int abcd) {
		this.abcd = abcd;
	}

	public String getReservation_No() {
		return reservation_No;
	}

	public void setReservation_No(String reservation_No) {
		this.reservation_No = reservation_No;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getFolioBindNo() {
		return folioBindNo;
	}

	public void setFolioBindNo(int folioBindNo) {
		this.folioBindNo = folioBindNo;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

}
