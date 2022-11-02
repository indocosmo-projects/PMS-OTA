package com.indocosmo.pms.web.reservation_test.model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Transient;

public class ResvHdr {

	private int resvNo;
	private int folioBindNO;
	private int FolioNo;
	private byte status;
	private boolean pickupNeeded;
	private Date pickupDate;
	private String pickupTime;
	private String pickupLocation;
	private int pickupSeats;
	private String pickupRemarks;
	private int pickupProvider;
	private Date cutOffDate;
	private Date minArrDate;
	private Date maxDepartDate;
	private int numRooms;
	private byte resvType;
	private int corporateId;
	private String corporateName;
	private String corporateAddress;
	private byte discType;
	private int guestId;
	private String selectedSalutation;
	private String resvByFirstName;
	private String resvByLastName;
	private String resvByAddress;
	private String resvByMail;
	private String resvByPhone;
	private String resvFor;
	private int numAdults;
	private int numChildren;
	private int numInfants;
	private String remarks;
	private String billingInstruction;
	private String specialRequests;
	private Date resvDate;
	private Date resvTime;
	private int resvTakenBy;
	private Date confDate;
	private Date confTime;
	private int confBy;
	private String confRefNo;
	private String cancelDate;
	private Date cancelTime;
	private int cancelBy;
	private String cancelReason;
	private int payment_source;
	private String resvByCompany;
    private String resvByDesignation;
    private String nationality;
    private String state;
    private String resvByArriving;
    private String resvByProceeding;
    private int mealPlan;
    private Date dob;
    private String gender;
    private Time minArrTime;
    private Time maxDepTime;
    private String guestName;
	public int getPayment_source() {
		return payment_source;
	}

	public void setPayment_source(int payment_source) {
		this.payment_source = payment_source;
	}

	@Transient
	private int packs;

	@Transient
	private short sumNumRooms;

	@Transient
	private String dateFormat;

	@Transient
	private int numNights;

	@Transient
	private double depositAmount;

	@Transient
	private String roomTypeCode;
	@Transient
	private String folioBalance;

	@Transient
	private int romnum;
	@Transient
	private double amount;
	/**
	 * @return the resvNo
	 */
	public int getResvNo() {
		return resvNo;
	}

	/**
	 * @param resvNo the resvNo to set
	 */
	public void setResvNo(int resvNo) {
		this.resvNo = resvNo;
	}

	/**
	 * @return the folioBindNO
	 */
	public int getFolioBindNO() {
		return folioBindNO;
	}

	/**
	 * @param folioBindNO the folioBindNO to set
	 */
	public void setFolioBindNO(int folioBindNO) {
		this.folioBindNO = folioBindNO;
	}

	/**
	 * @return the folioNo
	 */
	public int getFolioNo() {
		return FolioNo;
	}

	/**
	 * @param folioNo the folioNo to set
	 */
	public void setFolioNo(int folioNo) {
		FolioNo = folioNo;
	}

	/**
	 * @return the status
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @return the pickupNeeded
	 */
	public boolean isPickupNeeded() {
		return pickupNeeded;
	}

	/**
	 * @param pickupNeeded the pickupNeeded to set
	 */
	public void setPickupNeeded(boolean pickupNeeded) {
		this.pickupNeeded = pickupNeeded;
	}

	/**
	 * @return the pickupDate
	 */
	public Date getPickupDate() {
		return pickupDate;
	}

	/**
	 * @param pickupDate the pickupDate to set
	 */
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	/**
	 * @return the pickupTime
	 */
	public String getPickupTime() {
		return pickupTime;
	}

	/**
	 * @param pickupTime the pickupTime to set
	 */
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	/**
	 * @return the pickupLocation
	 */
	public String getPickupLocation() {
		return pickupLocation;
	}

	/**
	 * @param pickupLocation the pickupLocation to set
	 */
	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	/**
	 * @return the pickupSeats
	 */
	public int getPickupSeats() {
		return pickupSeats;
	}

	/**
	 * @param pickupSeats the pickupSeats to set
	 */
	public void setPickupSeats(int pickupSeats) {
		this.pickupSeats = pickupSeats;
	}

	/**
	 * @return the pickupRemarks
	 */
	public String getPickupRemarks() {
		return pickupRemarks;
	}

	/**
	 * @param pickupRemarks the pickupRemarks to set
	 */
	public void setPickupRemarks(String pickupRemarks) {
		this.pickupRemarks = pickupRemarks;
	}

	/**
	 * @return the pickupProvider
	 */
	public int getPickupProvider() {
		return pickupProvider;
	}

	/**
	 * @param pickupProvider the pickupProvider to set
	 */
	public void setPickupProvider(int pickupProvider) {
		this.pickupProvider = pickupProvider;
	}

	/**
	 * @return the cutOffDate
	 */
	public Date getCutOffDate() {
		return cutOffDate;
	}

	/**
	 * @param cutOffDate the cutOffDate to set
	 */
	public void setCutOffDate(Date cutOffDate) {
		this.cutOffDate = cutOffDate;
	}

	/**
	 * @return the minArrDate
	 */
	public Date getMinArrDate() {
		return minArrDate;
	}

	/**
	 * @param minArrDate the minArrDate to set
	 */
	public void setMinArrDate(Date minArrDate) {
		this.minArrDate = minArrDate;
	}

	/**
	 * @return the maxDepartDate
	 */
	public Date getMaxDepartDate() {
		return maxDepartDate;
	}

	/**
	 * @param maxDepartDate the maxDepartDate to set
	 */
	public void setMaxDepartDate(Date maxDepartDate) {
		this.maxDepartDate = maxDepartDate;
	}

	/**
	 * @return the numRooms
	 */
	public int getNumRooms() {
		return numRooms;
	}

	/**
	 * @param numRooms the numRooms to set
	 */
	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	/**
	 * @return the resvType
	 */
	public byte getResvType() {
		return resvType;
	}

	/**
	 * @param resvType the resvType to set
	 */
	public void setResvType(byte resvType) {
		this.resvType = resvType;
	}

	/**
	 * @return the corporateId
	 */
	public int getCorporateId() {
		return corporateId;
	}

	/**
	 * @param corporateId the corporateId to set
	 */
	public void setCorporateId(int corporateId) {
		this.corporateId = corporateId;
	}

	/**
	 * @return the corporateName
	 */
	public String getCorporateName() {
		return corporateName;
	}

	/**
	 * @param corporateName the corporateName to set
	 */
	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	/**
	 * @return the corporateAddress
	 */
	public String getCorporateAddress() {
		return corporateAddress;
	}

	/**
	 * @param corporateAddress the corporateAddress to set
	 */
	public void setCorporateAddress(String corporateAddress) {
		this.corporateAddress = corporateAddress;
	}

	/**
	 * @return the discType
	 */
	public byte getDiscType() {
		return discType;
	}

	/**
	 * @param discType the discType to set
	 */
	public void setDiscType(byte discType) {
		this.discType = discType;
	}

	/**
	 * @return the guestId
	 */
	public int getGuestId() {
		return guestId;
	}

	/**
	 * @param guestId the guestId to set
	 */
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	/**
	 * @return the resvByFirstName
	 */
	public String getResvByFirstName() {
		return resvByFirstName;
	}

	/**
	 * @param resvByFirstName the resvByFirstName to set
	 */
	public void setResvByFirstName(String resvByFirstName) {
		this.resvByFirstName = resvByFirstName;
	}

	/**
	 * @return the resvByLastName
	 */
	public String getResvByLastName() {
		return resvByLastName;
	}

	/**
	 * @param resvByLastName the resvByLastName to set
	 */
	public void setResvByLastName(String resvByLastName) {
		this.resvByLastName = resvByLastName;
	}

	/**
	 * @return the resvByAddress
	 */
	public String getResvByAddress() {
		return resvByAddress;
	}

	/**
	 * @param resvByAddress the resvByAddress to set
	 */
	public void setResvByAddress(String resvByAddress) {
		this.resvByAddress = resvByAddress;
	}

	/**
	 * @return the resvByMail
	 */
	public String getResvByMail() {
		return resvByMail;
	}

	/**
	 * @param resvByMail the resvByMail to set
	 */
	public void setResvByMail(String resvByMail) {
		this.resvByMail = resvByMail;
	}

	/**
	 * @return the resvByLastPhone
	 */
	public String getResvByPhone() {
		return resvByPhone;
	}

	/**
	 * @param resvByLastPhone the resvByLastPhone to set
	 */
	public void setResvByPhone(String resvByPhone) {
		this.resvByPhone = resvByPhone;
	}

	/**
	 * @return the resvFor
	 */
	public String getResvFor() {
		return resvFor;
	}

	/**
	 * @param resvFor the resvFor to set
	 */
	public void setResvFor(String resvFor) {
		this.resvFor = resvFor;
	}

	/**
	 * @return the numAdults
	 */
	public int getNumAdults() {
		return numAdults;
	}

	/**
	 * @param numAdults the numAdults to set
	 */
	public void setNumAdults(int numAdults) {
		this.numAdults = numAdults;
	}

	/**
	 * @return the numChildren
	 */
	public int getNumChildren() {
		return numChildren;
	}

	/**
	 * @param numChildren the numChildren to set
	 */
	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

	/**
	 * @return the numInfants
	 */
	public int getNumInfants() {
		return numInfants;
	}

	/**
	 * @param numInfants the numInfants to set
	 */
	public void setNumInfants(int numInfants) {
		this.numInfants = numInfants;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the billingInstruction
	 */
	public String getBillingInstruction() {
		return billingInstruction;
	}

	/**
	 * @param billingInstruction the billingInstruction to set
	 */
	public void setBillingInstruction(String billingInstruction) {
		this.billingInstruction = billingInstruction;
	}

	/**
	 * @return the specialRequests
	 */
	public String getSpecialRequests() {
		return specialRequests;
	}

	/**
	 * @param specialRequests the specialRequests to set
	 */
	public void setSpecialRequests(String specialRequests) {
		this.specialRequests = specialRequests;
	}

	/**
	 * @return the resvDate
	 */
	public Date getResvDate() {
		return resvDate;
	}

	/**
	 * @param resvDate the resvDate to set
	 */
	public void setResvDate(Date resvDate) {
		this.resvDate = resvDate;
	}

	/**
	 * @return the resvTime
	 */
	public Date getResvTime() {
		return resvTime;
	}

	/**
	 * @param resvTime the resvTime to set
	 */
	public void setResvTime(Date resvTime) {
		this.resvTime = resvTime;
	}

	/**
	 * @return the resvTakenBy
	 */
	public int getResvTakenBy() {
		return resvTakenBy;
	}

	/**
	 * @param resvTakenBy the resvTakenBy to set
	 */
	public void setResvTakenBy(int resvTakenBy) {
		this.resvTakenBy = resvTakenBy;
	}

	/**
	 * @return the confDate
	 */
	public Date getConfDate() {
		return confDate;
	}

	/**
	 * @param confDate the confDate to set
	 */
	public void setConfDate(Date confDate) {
		this.confDate = confDate;
	}

	/**
	 * @return the confTime
	 */
	public Date getConfTime() {
		return confTime;
	}

	/**
	 * @param confTime the confTime to set
	 */
	public void setConfTime(Date confTime) {
		this.confTime = confTime;
	}

	/**
	 * @return the confBy
	 */
	public int getConfBy() {
		return confBy;
	}

	/**
	 * @param confBy the confBy to set
	 */
	public void setConfBy(int confBy) {
		this.confBy = confBy;
	}

	/**
	 * @return the confRefNo
	 */
	public String getConfRefNo() {
		return confRefNo;
	}

	/**
	 * @param confRefNo the confRefNo to set
	 */
	public void setConfRefNo(String confRefNo) {
		this.confRefNo = confRefNo;
	}

	/**
	 * @return the cancelDate
	 */

	/**
	 * @return the cancelTime
	 */
	public Date getCancelTime() {
		return cancelTime;
	}

	/**
	 * @param cancelTime the cancelTime to set
	 */
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	/**
	 * @return the cancelBy
	 */
	public int getCancelBy() {
		return cancelBy;
	}

	/**
	 * @param cancelBy the cancelBy to set
	 */
	public void setCancelBy(int cancelBy) {
		this.cancelBy = cancelBy;
	}

	/**
	 * @return the cancelReason
	 */
	public String getCancelReason() {
		return cancelReason;
	}

	/**
	 * @param cancelReason the cancelReason to set
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	/**
	 * @return the numNights
	 */
	public int getNumNights() {
		return numNights;
	}

	/**
	 * @param numNights the numNights to set
	 */
	public void setNumNights(int numNights) {
		this.numNights = numNights;
	}

	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getRoomTypeCode() {
		return roomTypeCode;
	}

	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}



	public double getAmount() {
		return amount;
	}



	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getRomnum() {
		return romnum;
	}

	public void setRomnum(int romnum) {
		this.romnum = romnum;
	}

	public String getFolioBalance() {
		return folioBalance;
	}

	public void setFolioBalance(String folioBalance) {
		this.folioBalance = folioBalance;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
/*
	public int getpacks() {
		return packs;
	}

	public void setpacks(int packs) {
		this.packs = packs;
	}*/

	public short getSumNumRooms() {
		return sumNumRooms;
	}

	public void setSumNumRooms(short sumNumRooms) {
		this.sumNumRooms = sumNumRooms;
	}
	/**
	 * @return the selectedSalutation
	 */
	public String getSelectedSalutation() {
		return selectedSalutation;
	}

	/**
	 * @param selectedSalutation the selectedSalutation to set
	 */
	public void setSelectedSalutation(String selectedSalutation) {
		this.selectedSalutation = selectedSalutation;
	}

	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPacks() {
		return packs;
	}

	public void setPacks(int packs) {
		this.packs = packs;
	}

	public int getMealPlan() {
		return mealPlan;
	}

	public void setMealPlan(int mealPlan) {
		this.mealPlan = mealPlan;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getResvByCompany() {
		return resvByCompany;
	}

	public void setResvByCompany(String resvByCompany) {
		this.resvByCompany = resvByCompany;
	}

	public String getResvByDesignation() {
		return resvByDesignation;
	}

	public void setResvByDesignation(String resvByDesignation) {
		this.resvByDesignation = resvByDesignation;
	}

	public String getResvByArriving() {
		return resvByArriving;
	}

	public void setResvByArriving(String resvByArriving) {
		this.resvByArriving = resvByArriving;
	}

	public String getResvByProceeding() {
		return resvByProceeding;
	}

	public void setResvByProceeding(String resvByProceeding) {
		this.resvByProceeding = resvByProceeding;
	}

	public Time getMinArrTime() {
		return minArrTime;
	}

	public void setMinArrTime(Time minArrTime) {
		this.minArrTime = minArrTime;
	}

	public Time getMaxDepTime() {
		return maxDepTime;
	}

	public void setMaxDepTime(Time maxDepTime) {
		this.maxDepTime = maxDepTime;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	

}
