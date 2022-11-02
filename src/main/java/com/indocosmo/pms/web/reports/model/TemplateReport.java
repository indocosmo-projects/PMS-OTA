package com.indocosmo.pms.web.reports.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Transient;

public class TemplateReport {
private String header;
private String filterDetails;
private String hotelName;
private Date printDate;
private String printBy;
private int reportType;
private float headerHeight=30.0f;
private String name;
private String building;
private String street;
private String city;
private String state;
private String country;
private String gst;





public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getBuilding() {
	return building;
}
public void setBuilding(String building) {
	this.building = building;
}
public String getStreet() {
	return street;
}
public void setStreet(String street) {
	this.street = street;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getGst() {
	return gst;
}
public void setGst(String gst) {
	this.gst = gst;
}



/**
 * @return the header
 */
public String getHeader() {
	return header;
}
/**
 * @param header the header to set
 */
public void setHeader(String header) {
	this.header = header;
}




/**
 * @return the filterDetails
 */
public String getFilterDetails() {
	return filterDetails;
}
/**
 * @param filterDetails the filterDetails to set
 */
public void setFilterDetails(String filterDetails) {
	this.filterDetails = filterDetails;
}
/**
 * @return the hotelName
 */
public String getHotelName() {
	return hotelName;
}
/**
 * @param hotelName the hotelName to set
 */
public void setHotelName(String hotelName) {
	this.hotelName = hotelName;
}
/**
 * @return the printDate
 */
public Date getPrintDate() {
	return printDate;
}
/**
 * @param printDate the printDate to set
 */
public void setPrintDate(Date printDate) {
	this.printDate = printDate;
}
/**
 * @return the printBy
 */
public String getPrintBy() {
	return printBy;
}
/**
 * @param printBy the printBy to set
 */
public void setPrintBy(String printBy) {
	this.printBy = printBy;
}
/**
 * @return the reportType
 */
public int getReportType() {
	return reportType;
}
/**
 * @param reportType the reportType to set
 */
public void setReportType(int reportType) {
	this.reportType = reportType;
}
public float getHeaderHeight() {
	return headerHeight;
}
public void setHeaderHeight(float headerHeight) {
	this.headerHeight = headerHeight;
}


}
