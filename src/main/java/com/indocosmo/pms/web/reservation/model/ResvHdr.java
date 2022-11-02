package com.indocosmo.pms.web.reservation.model;

import java.util.Date;
import java.util.List;

import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.enumerator.ReservationType;

public class ResvHdr {

	/* DB independent */
	private int numNights;
	private List<SelectedRoomType> selectedRoomTypeList;
	private List<ResvDtl> resvDtlList;
	private List<ResvRoom> resvRoomList;
	private List<ResvRate> resvRateList;
	/* End */

	/* UI information fields for Wizard 2 */
	private boolean taCorpOnly;
	private String discountType;
	private String discCode;
	private String disc;
	/* End */

	private byte reservationStatus;

	private String reservedFor;

	private int resvNo;

	private int folioBindNo;

	private byte status; // tinyint

	private Date changeArrDate;

	private Date cutOffDate;

	private boolean pickupNeeded;
	private Date pickupDate;
	private String pickupTime;
	private String pickupLocation;
	private int pickupSeats;
	private String pickupRemarks;

	public boolean isPickupNeeded() {
		return pickupNeeded;
	}

	public void setPickupNeeded(boolean pickupNeeded) {
		this.pickupNeeded = pickupNeeded;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public String getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public int getPickupSeats() {
		return pickupSeats;
	}

	public void setPickupSeats(int pickupSeats) {
		this.pickupSeats = pickupSeats;
	}

	public String getPickupRemarks() {
		return pickupRemarks;
	}

	public void setPickupRemarks(String pickupRemarks) {
		this.pickupRemarks = pickupRemarks;
	}

	public Date getCutOffDate() {
		return cutOffDate;
	}

	public void setCutOffDate(Date cutOffDate) {
		this.cutOffDate = cutOffDate;
	}

	private Date minArrDate;

	private Date maxDepartDate;

	private short sumNumRooms;// smallint

	private byte resvType;// tiny

	private ReservationType resvTypeEnum;

	private int corporateId;

	private String corporateName;

	private String corporateAddress;

	private byte discType; // tinyint

	private byte discTypes;

	private int guestId;

	private String resvBySalutation;

	private String resvByFirstName;

	private String resvByLastName;

	private String resvByAddress;

	private String resvByMail;

	private String resvByPhone;

	private byte numAdults;

	private byte numChildren;

	private String remarks;

	private String billingInstruction;

	private Date resvDate;

	private Date resvTime;// datetime

	private int resvTakenBy;

	private Date confDate;

	private String confTime;// datetime

	private int confBy;

	private String confRefNo;

	private Date cancel_date;

	private String cancelTime;// datetime

	private int cancelledBy;

	private String cancelledReason;

	private Date lastUpdates;

	private int folioNo;

	private String dateFormat;

	private int isDirty;

	public int getIsDirty() {
		return isDirty;
	}

	public void setIsDirty(int isDirty) {
		this.isDirty = isDirty;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public byte getDiscTypes() {
		return discTypes;
	}

	public void setDiscTypes(byte discTypes) {
		this.discTypes = discTypes;
	}

	public byte getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(byte reservationStatus) {
		this.reservationStatus = reservationStatus;
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

	public int getFolioBindNo() {
		return folioBindNo;
	}

	public void setFolioBindNo(int folioBindNo) {
		this.folioBindNo = folioBindNo;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status.getCode();
	}

	public Date getMinArrDate() {
		return minArrDate;
	}

	public void setMinArrDate(Date minArrDate) {
		this.minArrDate = minArrDate;
	}

	public Date getMaxDepartDate() {
		return maxDepartDate;
	}

	public void setMaxDepartDate(Date maxDepartDate) {
		this.maxDepartDate = maxDepartDate;
	}

	public short getSumNumRooms() {
		return sumNumRooms;
	}

	public void setSumNumRooms(short sumNumRooms) {
		this.sumNumRooms = sumNumRooms;
	}

	public byte getResvType() {
		return resvType;
	}

	public void setResvType(byte resvType) {
		this.resvType = resvType;
	}

	public int getCorporateId() {
		return corporateId;
	}

	public void setCorporateId(int corporateId) {
		this.corporateId = corporateId;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getCorporateAddress() {
		return corporateAddress;
	}

	public void setCorporateAddress(String corporateAddress) {
		this.corporateAddress = corporateAddress;
	}

	public byte getDiscType() {
		return discType;
	}

	public void setDiscType(byte discType) {
		this.discType = discType;
	}

	public int getGuestId() {
		return guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public String getResvBySalutation() {
		return resvBySalutation;
	}

	public void setResvBySalutation(String resvBySalutation) {
		this.resvBySalutation = resvBySalutation;
	}

	public String getResvByFirstName() {
		return resvByFirstName;
	}

	public void setResvByFirstName(String resvByFirstName) {
		this.resvByFirstName = resvByFirstName;
	}

	public String getResvByLastName() {
		return resvByLastName;
	}

	public void setResvByLastName(String resvByLastName) {
		this.resvByLastName = resvByLastName;
	}

	public String getResvByAddress() {
		return resvByAddress;
	}

	public void setResvByAddress(String resvByAddress) {
		this.resvByAddress = resvByAddress;
	}

	public String getResvByMail() {
		return resvByMail;
	}

	public void setResvByMail(String resvByMail) {
		this.resvByMail = resvByMail;
	}

	public String getResvByPhone() {
		return resvByPhone;
	}

	public void setResvByPhone(String resvByPhone) {
		this.resvByPhone = resvByPhone;
	}

	public byte getNumAdults() {
		return numAdults;
	}

	public void setNumAdults(byte numAdults) {
		this.numAdults = numAdults;
	}

	public byte getNumChildren() {
		return numChildren;
	}

	public void setNumChildren(byte numChildren) {
		this.numChildren = numChildren;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBillingInstruction() {
		return billingInstruction;
	}

	public void setBillingInstruction(String billingInstruction) {
		this.billingInstruction = billingInstruction;
	}

	public Date getResvDate() {
		return resvDate;
	}

	public void setResvDate(Date resvDate) {
		this.resvDate = resvDate;
	}

	public int getResvTakenBy() {
		return resvTakenBy;
	}

	public void setResvTakenBy(int resvTakenBy) {
		this.resvTakenBy = resvTakenBy;
	}

	public int getConfBy() {
		return confBy;
	}

	public void setConfBy(int confBy) {
		this.confBy = confBy;
	}

	public String getConfRefNo() {
		return confRefNo;
	}

	public void setConfRefNo(String confRefNo) {
		this.confRefNo = confRefNo;
	}

	public int getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(int cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	public String getCancelledReason() {
		return cancelledReason;
	}

	public void setCancelledReason(String cancelledReason) {
		this.cancelledReason = cancelledReason;
	}

	public Date getLastUpdates() {
		return lastUpdates;
	}

	public void setLastUpdates(Date lastUpdates) {
		this.lastUpdates = lastUpdates;
	}

	public List<SelectedRoomType> getSelectedRoomTypeList() {
		return selectedRoomTypeList;
	}

	public void setSelectedRoomTypeList(List<SelectedRoomType> selectedRoomTypeList) {
		this.selectedRoomTypeList = selectedRoomTypeList;
	}

	public List<ResvDtl> getResvDtlList() {
		return resvDtlList;
	}

	public void setResvDtlList(List<ResvDtl> resvDtlList2) {
		this.resvDtlList = resvDtlList2;
	}

	public List<ResvRoom> getResvRoomList() {
		return resvRoomList;
	}

	public void setResvRoomList(List<ResvRoom> resvRoomList) {
		this.resvRoomList = resvRoomList;
	}

	public ReservationType getResvTypeEnum() {
		return resvTypeEnum;
	}

	public void setResvTypeEnum(ReservationType resvTypeEnum) {
		resvType = (byte) resvTypeEnum.getId();
		this.resvTypeEnum = resvTypeEnum;
	}

	public List<ResvRate> getResvRateList() {
		return resvRateList;
	}

	public void setResvRateList(List<ResvRate> resvRateList) {
		this.resvRateList = resvRateList;
	}

	public boolean getTaCorpOnly() {
		return taCorpOnly;
	}

	public void setTaCorpOnly(boolean taCorpOnly) {
		this.taCorpOnly = taCorpOnly;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscCode() {
		return discCode;
	}

	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}

	public String getDisc() {
		return disc;
	}

	public void setDisc(String disc) {
		this.disc = disc;
	}

	public String getReservedFor() {
		return reservedFor;
	}

	public void setReservedFor(String reservedFor) {
		this.reservedFor = reservedFor;
	}

	public Date getResvTime() {
		return resvTime;
	}

	public void setResvTime(Date resvTime) {
		this.resvTime = resvTime;
	}

	public Date getConfDate() {
		return confDate;
	}

	public void setConfDate(Date confDate) {
		this.confDate = confDate;
	}

	public String getConfTime() {
		return confTime;
	}

	public void setConfTime(String confTime) {
		this.confTime = confTime;
	}

	public Date getCancel_date() {
		return cancel_date;
	}

	public void setCancel_date(Date cancel_date) {
		this.cancel_date = cancel_date;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public int getFolioNo() {
		return folioNo;
	}

	public void setFolioNo(int folioNo) {
		this.folioNo = folioNo;
	}

	public Date getChangeArrDate() {
		return changeArrDate;
	}

	public void setChangeArrDate(Date changeArrDate) {
		this.changeArrDate = changeArrDate;
	}

}
