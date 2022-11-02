package com.indocosmo.pms.web.reports.model;



public class Report {
	
	String ReportName;
	String CurrUserName;
	String CompanyName;
	String companyAddress;
	String dateFormat;
	Integer ReportType;
	
	public String getReportName() {
		return ReportName;
	}
	public void setReportName(String reportName) {
		ReportName = reportName;
	}
	public String getCurrUserName() {
		return CurrUserName;
	}
	public void setCurrUserName(String currUserName) {
		CurrUserName = currUserName;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public Integer getReportType() {
		return ReportType;
	}
	public void setReportType(Integer reportType) {
		ReportType = reportType;
	}
	
}