package com.indocosmo.pms.web.reports.model;

import java.sql.Date;

import javax.persistence.Transient;

public class ReceptionReport {
	private int checkinNo;
	private Date checkinDate;
	private Date expectedCheckoutDate;
	private Date actualCheckoutDate;
	private String roomNumber;
	private String firstName;
	private String lastName;
	private String phone;
	private String folioBalance;
	private String nationality;
	private float roomCharge;
	private Date nightDate;
	private String rateDescription;
	private int children;
	private int infants;
	private int adults;
	
	@Transient
	private String dateFormat;
	
	@Transient
	private String reqTime;
	
	@Transient
	private Date reqDate;
	
	@Transient
	private boolean oneTimeReq;
	
	@Transient
	private String remarks;
	
	@Transient
	private int facilityId;
	
	@Transient
	private String code;
	
	@Transient
	private String name;
	
	@Transient
	private String rateCode;
	
	@Transient
	private String roomTypeCode;
	
	@Transient
	private int folioBindNo;
	
	@Transient
	 private double amount;
	
	@Transient
	private int numNights;
	
	@Transient
	private int resvNo;
	
	@Transient
	private byte Roomsnumbers;
	
	@Transient
	private Date arrDate;
	
	@Transient
	private String arrtime;
	
	@Transient
	private Date expDepartTime;// datetime
	@Transient
	private Date actDepartDate;
	@Transient
	private String actDepartTime;// datetime
	
	@Transient
	private int totalRooms;
	
	@Transient
	private int pax;
	
	/**
	 * @return the checkinNo
	 */
	public int getCheckinNo() {
		return checkinNo;
	}
	/**
	 * @param checkinNo the checkinNo to set
	 */
	public void setCheckinNo(int checkinNo) {
		this.checkinNo = checkinNo;
	}
	/**
	 * @return the checkinDate
	 */
	public Date getCheckinDate() {
		return checkinDate;
	}
	/**
	 * @param checkinDate the checkinDate to set
	 */
	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	/**
	 * @return the expectedCheckoutDate
	 */
	public Date getExpectedCheckoutDate() {
		return expectedCheckoutDate;
	}
	/**
	 * @param expectedCheckoutDate the expectedCheckoutDate to set
	 */
	public void setExpectedCheckoutDate(Date expectedCheckoutDate) {
		this.expectedCheckoutDate = expectedCheckoutDate;
	}
	/**
	 * @return the actualCheckoutDate
	 */
	public Date getActualCheckoutDate() {
		return actualCheckoutDate;
	}
	/**
	 * @param actualCheckoutDate the actualCheckoutDate to set
	 */
	public void setActualCheckoutDate(Date actualCheckoutDate) {
		this.actualCheckoutDate = actualCheckoutDate;
	}
	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}
	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the folioBalance
	 */
	public String getFolioBalance() {
		return folioBalance;
	}
	/**
	 * @param folioBalance the folioBalance to set
	 */
	public void setFolioBalance(String folioBalance) {
		this.folioBalance = folioBalance;
	}
	
	
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public Date getReqDate() {
		return reqDate;
	}
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}
	public boolean isOneTimeReq() {
		return oneTimeReq;
	}
	public void setOneTimeReq(boolean oneTimeReq) {
		this.oneTimeReq = oneTimeReq;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	public String getRoomTypeCode() {
		return roomTypeCode;
	}
	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}
	public int getFolioBindNo() {
		return folioBindNo;
	}
	public void setFolioBindNo(int folioBindNo) {
		this.folioBindNo = folioBindNo;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getNumNights() {
		return numNights;
	}
	public void setNumNights(int numNights) {
		this.numNights = numNights;
	}
	public int getResvNo() {
		return resvNo;
	}
	public void setResvNo(int resvNo) {
		this.resvNo = resvNo;
	}
	public byte getRoomsnumbers() {
		return Roomsnumbers;
	}
	public void setRoomsnumbers(byte roomsnumbers) {
		Roomsnumbers = roomsnumbers;
	}
	public Date getArrDate() {
		return arrDate;
	}
	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}
	public Date getExpDepartTime() {
		return expDepartTime;
	}
	public void setExpDepartTime(Date expDepartTime) {
		this.expDepartTime = expDepartTime;
	}
	public Date getActDepartDate() {
		return actDepartDate;
	}
	public void setActDepartDate(Date actDepartDate) {
		this.actDepartDate = actDepartDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArrtime() {
		return arrtime;
	}
	public void setArrtime(String arrtime) {
		this.arrtime = arrtime;
	}
	public String getActDepartTime() {
		return actDepartTime;
	}
	public void setActDepartTime(String actDepartTime) {
		this.actDepartTime = actDepartTime;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public float getRoomCharge() {
		return roomCharge;
	}
	public void setRoomCharge(float f) {
		this.roomCharge = f;
	}
	public int getTotalRooms() {
		return totalRooms;
	}
	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}
	public int getPax() {
		return pax;
	}
	public void setPax(int pax) {
		this.pax = pax;
	}
	public Date getNightDate() {
		return nightDate;
	}
	public void setNightDate(Date nightDate) {
		this.nightDate = nightDate;
	}
	public String getRateDescription() {
		return rateDescription;
	}
	public void setRateDescription(String rateDescription) {
		this.rateDescription = rateDescription;
	}
	public int getChildren() {
		return children;
	}
	public void setChildren(int children) {
		this.children = children;
	}
	public int getInfants() {
		return infants;
	}
	public void setInfants(int infants) {
		this.infants = infants;
	}
	
	/**
	 * @return the adults
	 */
	public int getAdults() {
		return adults;
	}
	/**
	 * @param adults the adults to set
	 */
	public void setAdults(int adults) {
		this.adults = adults;
	}
	
}
