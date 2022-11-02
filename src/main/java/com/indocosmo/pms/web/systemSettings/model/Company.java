package com.indocosmo.pms.web.systemSettings.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "building_name")
	private String buildingName;

	@Column(name = "street_name")
	private String streetName;

	@Column(name = "city_name")
	private String cityName;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "gst_no")
	private String gstNo;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "logo_folder")
	private String logoFolder;
	
	@Column(name = "long_stay")
	private Integer longStay;
	
	
	@Column(name = "resv_confirm_head")
	private String resvConfirmHead;
	
	@Column(name = "resv_confirm_text1")
	private String resvConfirmText1;
	
	@Column(name = "resv_confirm_text2")
	private String resvConfirmText2;
	
	@Column(name = "resv_confirm_text3")
	private String resvConfirmText3;
	
	@Column(name = "resv_confirm_text4")
	private String resvConfirmText4;
	
	public String getResvConfirmHead() {
		return resvConfirmHead;
	}

	public void setResvConfirmHead(String resvConfirmHead) {
		this.resvConfirmHead = resvConfirmHead;
	}

	public String getResvConfirmText1() {
		return resvConfirmText1;
	}

	public void setResvConfirmText1(String resvConfirmText1) {
		this.resvConfirmText1 = resvConfirmText1;
	}

	public String getResvConfirmText2() {
		return resvConfirmText2;
	}

	public void setResvConfirmText2(String resvConfirmText2) {
		this.resvConfirmText2 = resvConfirmText2;
	}

	public String getResvConfirmText3() {
		return resvConfirmText3;
	}

	public void setResvConfirmText3(String resvConfirmText3) {
		this.resvConfirmText3 = resvConfirmText3;
	}

	public String getResvConfirmText4() {
		return resvConfirmText4;
	}

	public void setResvConfirmText4(String resvConfirmText4) {
		this.resvConfirmText4 = resvConfirmText4;
	}

	public String getResvConfirmText5() {
		return resvConfirmText5;
	}

	public void setResvConfirmText5(String resvConfirmText5) {
		this.resvConfirmText5 = resvConfirmText5;
	}

	@Column(name = "resv_confirm_text5")
	private String resvConfirmText5;
	
	
	
	
	public Integer getLongStay() {
		return longStay;
	}

	public void setLongStay(Integer longStay) {
		this.longStay = longStay;
	}

	public String getLogoFolder() {
		return logoFolder;
	}

	public void setLogoFolder(String logoFolder) {
		this.logoFolder = logoFolder;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "url")
	private String url;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

}
