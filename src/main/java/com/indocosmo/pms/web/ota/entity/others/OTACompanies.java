package com.indocosmo.pms.web.ota.entity.others;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otacompanies")
public class OTACompanies {
	
	@Id
	@GeneratedValue
	@Column(name = "sid")
	private int sid;
	
	@Column(name = "id")
	private String id;
	
	@Column(name = "accountname")
	private String accountname;
	
	@Column(name = "accountcode")
    private String accountcode;
	
	@Column(name = "contact_person")
    private String contact_person;
	
	@Column(name = "address")
    private String address ;
	
	@Column(name = "city")
    private String city;
	
	@Column(name = "postalcode")
    private String postalcode;
	
	@Column(name = "state")
    private String state;
	
	@Column(name = "country")
    private String country;
	
	@Column(name = "phone")
    private String phone;
	
	@Column(name = "mobile")
    private String mobile;
	
	@Column(name = "fax")
    private String fax;
	
	@Column(name = "email")
    private String email;
    
    @Column(name = "taxid")
    private String taxid;
    
    @Column(name = "registrationno")
    private String registrationno;
    
    @Column(name = "isactive")
    private String isactive;
    
    private String commissionplan;
    
    private String commissionvalue;
    
    private String discount;
    
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getAccountcode() {
		return accountcode;
	}

	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTaxid() {
		return taxid;
	}

	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}

	public String getRegistrationno() {
		return registrationno;
	}

	public void setRegistrationno(String registrationno) {
		this.registrationno = registrationno;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public String getCommissionplan() {
		return commissionplan;
	}

	public void setCommissionplan(String commissionplan) {
		this.commissionplan = commissionplan;
	}

	public String getCommissionvalue() {
		return commissionvalue;
	}

	public void setCommissionvalue(String commissionvalue) {
		this.commissionvalue = commissionvalue;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
    
    
    
}
