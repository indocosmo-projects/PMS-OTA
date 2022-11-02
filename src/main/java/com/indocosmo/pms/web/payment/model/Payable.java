package com.indocosmo.pms.web.payment.model;

public class Payable {
	/*double roomCharges;
	double otherCharges;
	double discounts;
	double deposits;
	double tax;
	double serviceCharge;
	double serviceTax;
	double total;
	double paidIn;
	double roundAdjust;*/
	
	String roomCharges;
	String otherCharges;
	String discounts;
	String deposits;
	String tax;
	String serviceCharge;
	String serviceTax;
	String total;
	String paidIn;
	String roundAdjust;
	boolean canPay;

	public boolean isCanPay() {
		return canPay;
	}

	public void setCanPay(boolean canPay) {
		this.canPay = canPay;
	}

	public String getRoomCharges() {
		return roomCharges;
	}

	public void setRoomCharges(String roomCharges) {
		this.roomCharges = roomCharges;
	}

	public String getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(String otherCharges) {
		this.otherCharges = otherCharges;
	}

	public String getDiscounts() {
		return discounts;
	}

	public void setDiscounts(String discounts) {
		this.discounts = discounts;
	}

	public String getDeposits() {
		return deposits;
	}

	public void setDeposits(String deposits) {
		this.deposits = deposits;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}

	public String getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPaidIn() {
		return paidIn;
	}

	public void setPaidIn(String paidIn) {
		this.paidIn = paidIn;
	}

	public String getRoundAdjust() {
		return roundAdjust;
	}

	public void setRoundAdjust(String roundAdjust) {
		this.roundAdjust = roundAdjust;
	}
}
