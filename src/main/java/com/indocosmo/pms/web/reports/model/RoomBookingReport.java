package com.indocosmo.pms.web.reports.model;

import java.util.Date;

import javax.persistence.Transient;

public class RoomBookingReport {

	private String roomNumber;
	private String firstName;
	private String lastName;
	private int children;
	private int infants;
	private int adults;

	@Transient
	private String dateFormat;

	/**
	 * @return the arrDate
	 */
	public Date getArrDate() {
		return arrDate;
	}
	/**
	 * @param arrDate the arrDate to set
	 */
	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}
	private Date arrDate;


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
	 * @return the children
	 */
	public int getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(int children) {
		this.children = children;
	}
	/**
	 * @return the infants
	 */
	public int getInfants() {
		return infants;
	}
	/**
	 * @param infants the infants to set
	 */
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

	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}
