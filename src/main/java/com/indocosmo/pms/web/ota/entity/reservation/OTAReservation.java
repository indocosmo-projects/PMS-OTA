package com.indocosmo.pms.web.ota.entity.reservation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otareservation")
public class OTAReservation {
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id ;
	
	@Column(name = "uniquereservationid")
	private int uniquereservationid ;
	
	@Column(name = "locationid")
	private String locationid ;
     
	@Column(name = "bookedby")
	private String bookedby ;
	
	@Column(name = "salutation")
	private String salutation ;
	
	@Column(name = "firstname")
	private String firstname ;
	
	@Column(name = "gender")
	private String gender ;
	
	@Column(name = "address")
	private String address ;
	
	@Column(name = "city")
	private String city ;
	
	@Column(name = "state")
	private String state ;
	
	@Column(name = "country")
	private String country ;
	
	@Column(name = "zipcode")
	private String zipcode ;
	
	@Column(name = "phone")
	private String phone ;
	
	@Column(name = "mobile")
	private String mobile ;
	
	@Column(name = "fax")
	private String fax ;

	@Column(name = "email")
	private String email ;
	
	@Column(name = "registrationno")
	private String registrationno ;
	
	@Column(name = "source")
	private String source ;
	
	@Column(name = "ischannelbooking")
	private String ischannelbooking ;

	@Column(name = "isdeleted")
	private int isdeleted ;

	public OTAReservation() {
		super();
	}

	public OTAReservation(int uniquereservationid, String bookedby) {
		super();
		this.uniquereservationid = uniquereservationid;
		this.bookedby = bookedby;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getUniquereservationid() {
		return uniquereservationid;
	}

	public void setUniquereservationid(int uniquereservationid) {
		this.uniquereservationid = uniquereservationid;
	}
	

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getBookedby() {
		return bookedby;
	}

	public void setBookedby(String bookedby) {
		this.bookedby = bookedby;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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

	public String getRegistrationno() {
		return registrationno;
	}

	public void setRegistrationno(String registrationno) {
		this.registrationno = registrationno;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIschannelbooking() {
		return ischannelbooking;
	}

	public void setIschannelbooking(String ischannelbooking) {
		this.ischannelbooking = ischannelbooking;
	}

	public int getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	

}
