package com.indocosmo.pms.web.reservation_test.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

public class ResvRoomRatePlans {
	private int roomTypeId;
	private String roomTypeCode;
	private boolean occ1;
	private boolean occ2;
	private boolean occ3;
	private boolean occ4;
	private int rateId;
	private String rateCode;
	private String rateName;
	private String mealPlan;
	private boolean taxIncl;
	private int rateType;
	private double totalOcc1Rate;
	private double totalOcc2Rate;
	private double totalOcc3Rate;
	private double totalOcc4Rate;
	private boolean isOpen;
	private int source_rate_hdr_id;

//	private String validity_from;
//	private  String validity_to;
	
	
	@Transient
	private List<String> images;
	
	public int getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getRoomTypeCode() {
		return roomTypeCode;
	}
	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}
	public boolean isOcc1() {
		return occ1;
	}
	public void setOcc1(boolean occ1) {
		this.occ1 = occ1;
	}
	public boolean isOcc2() {
		return occ2;
	}
	public void setOcc2(boolean occ2) {
		this.occ2 = occ2;
	}
	public boolean isOcc3() {
		return occ3;
	}
	public void setOcc3(boolean occ3) {
		this.occ3 = occ3;
	}
	public boolean isOcc4() {
		return occ4;
	}
	public void setOcc4(boolean occ4) {
		this.occ4 = occ4;
	}
	public int getRateId() {
		return rateId;
	}
	public void setRateId(int rateId) {
		this.rateId = rateId;
	}
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	public String getMealPlan() {
		return mealPlan;
	}
	public void setMealPlan(String mealPlan) {
		this.mealPlan = mealPlan;
	}
	public boolean isTaxIncl() {
		return taxIncl;
	}
	public void setTaxIncl(boolean taxIncl) {
		this.taxIncl = taxIncl;
	}
	public int getRateType() {
		return rateType;
	}
	public void setRateType(int rateType) {
		this.rateType = rateType;
	}
	public double getTotalOcc1Rate() {
		return totalOcc1Rate;
	}
	public void setTotalOcc1Rate(double totalOcc1Rate) {
		this.totalOcc1Rate = totalOcc1Rate;
	}
	public double getTotalOcc2Rate() {
		return totalOcc2Rate;
	}
	public void setTotalOcc2Rate(double totalOcc2Rate) {
		this.totalOcc2Rate = totalOcc2Rate;
	}
	public double getTotalOcc3Rate() {
		return totalOcc3Rate;
	}
	public void setTotalOcc3Rate(double totalOcc3Rate) {
		this.totalOcc3Rate = totalOcc3Rate;
	}
	public double getTotalOcc4Rate() {
		return totalOcc4Rate;
	}
	public void setTotalOcc4Rate(double totalOcc4Rate) {
		this.totalOcc4Rate = totalOcc4Rate;
	}
	/**
	 * @return the images
	 */
	public List<String> getImages() {
		return images;
	}
	/**
	 * @param images the images to set
	 */
	public void setImages(List<String> images) {
		this.images = images;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public int getSource_rate_hdr_id() {
		return source_rate_hdr_id;
	}
	public void setSource_rate_hdr_id(int source_rate_hdr_id) {
		this.source_rate_hdr_id = source_rate_hdr_id;
	}

	
}
