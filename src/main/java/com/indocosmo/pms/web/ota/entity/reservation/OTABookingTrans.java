package com.indocosmo.pms.web.ota.entity.reservation;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otabookingtrans")
public class OTABookingTrans {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	
	@Column(name = "subbookingid")
	private String subbookingid;
	
	@Column(name = "reservationid")
	private int reservationid;
	
	@Column(name = "transactionid")
	private String transactionid;
	
	@Column(name = "createdatetime")
	private String createdatetime;
	
	@Column(name = "modifydatetime")
	private String modifydatetime;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "isconfirmed")
	private String isconfirmed;
	
	@Column(name = "currentstatus")
	private String currentstatus;
	
	@Column(name = "voucherno")
	private String voucherno;
	
	@Column(name = "packagecode")
	private String packagecode;
	
	@Column(name = "packagename")
	private String packagename;
	
	@Column(name = "rateplancode")
	private String rateplancode;
	
	@Column(name = "rateplanname")
	private String rateplanname;
	
	@Column(name = "roomtypecode")
	private String roomtypecode;
	
	@Column(name = "roomtypename")
	private String roomtypename;
	
	@Column(name = "start")
	private String start;
	
	@Column(name = "end")
	private String end;
	
	@Column(name = "arrivaltime")
	private String arrivaltime;
	
	@Column(name = "departuretime")
	private String departuretime;
	
	@Column(name = "currencycode")
	private String currencycode;
	
	@Column(name = "totalamountaftertax")
	private String totalamountaftertax;

	@Column(name = "totalamountbeforetax")
	private String totalamountbeforetax;

	@Column(name = "totaltax")
	private String totaltax;
	
	@Column(name = "totaldiscount")
	private String totaldiscount;
	
	@Column(name = "totalextracharge")
	private String totalextracharge;
	
	@Column(name = "totalpayment")
	private String totalpayment;
	
	@Column(name = "tacommision")
	private String tacommision;
	
	@Column(name = "salutation")
	private String salutation;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "dateofbirth")
	private String dateofbirth;
	
	@Column(name = "spousedateofbirth")
	private String spousedateofbirth;
	
	@Column(name = "weddinganniversary")
	private String weddinganniversary;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "nationality")
	private String nationality;
	
	@Column(name = "zipcode")
	private String zipcode;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "fax")
	private String fax;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "registrationno")
	private String registrationno;
	
	@Column(name = "identiytype")
	private String identiytype;
	
	@Column(name = "identityno")
	private String identityno;
	
	@Column(name = "expirydate")
	private String expirydate;
	
	@Column(name = "transportationmode")
	private String transportationmode;
	
	@Column(name = "vehicle")
	private String vehicle;
	
	@Column(name = "pickupdate")
	private String pickupdate;
	
	@Column(name = "pickuptime")
	private String pickuptime;
	
	@Column(name = "source")
	private String source;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "affiliatename")
	private String affiliatename;
	
	@Column(name = "affiliatecode")
	private String affiliatecode;
	
	@Column(name = "cclink")
	private String cclink;
	
	@Column(name = "ccno")
	private String ccno;
	
	@Column(name = "cctype")
	private String cctype;
	
	@Column(name = "ccexpirydate")
	private String ccexpirydate;
	
	@Column(name = "cardholdersname")
	private String cardholdersname;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getSubbookingid() {
		return subbookingid;
	}

	public void setSubbookingid(String subbookingid) {
		this.subbookingid = subbookingid;
	}

	public int getReservationid() {
		return reservationid;
	}

	public void setReservationid(int reservationid) {
		this.reservationid = reservationid;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(String createdatetime) {
		this.createdatetime = createdatetime;
	}

	public String getModifydatetime() {
		return modifydatetime;
	}

	public void setModifydatetime(String modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsconfirmed() {
		return isconfirmed;
	}

	public void setIsconfirmed(String isconfirmed) {
		this.isconfirmed = isconfirmed;
	}

	public String getCurrentstatus() {
		return currentstatus;
	}

	public void setCurrentstatus(String currentstatus) {
		this.currentstatus = currentstatus;
	}

	public String getVoucherno() {
		return voucherno;
	}

	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}

	public String getPackagecode() {
		return packagecode;
	}

	public void setPackagecode(String packagecode) {
		this.packagecode = packagecode;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public String getRateplancode() {
		return rateplancode;
	}

	public void setRateplancode(String rateplancode) {
		this.rateplancode = rateplancode;
	}

	public String getRateplanname() {
		return rateplanname;
	}

	public void setRateplanname(String rateplanname) {
		this.rateplanname = rateplanname;
	}

	public String getRoomtypecode() {
		return roomtypecode;
	}

	public void setRoomtypecode(String roomtypecode) {
		this.roomtypecode = roomtypecode;
	}

	public String getRoomtypename() {
		return roomtypename;
	}

	public void setRoomtypename(String roomtypename) {
		this.roomtypename = roomtypename;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getArrivaltime() {
		return arrivaltime;
	}

	public void setArrivaltime(String arrivaltime) {
		this.arrivaltime = arrivaltime;
	}

	public String getDeparturetime() {
		return departuretime;
	}

	public void setDeparturetime(String departuretime) {
		this.departuretime = departuretime;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getTotalamountaftertax() {
		return totalamountaftertax;
	}

	public void setTotalamountaftertax(String totalamountaftertax) {
		this.totalamountaftertax = totalamountaftertax;
	}

	public String getTotalamountbeforetax() {
		return totalamountbeforetax;
	}

	public void setTotalamountbeforetax(String totalamountbeforetax) {
		this.totalamountbeforetax = totalamountbeforetax;
	}

	public String getTotaltax() {
		return totaltax;
	}

	public void setTotaltax(String totaltax) {
		this.totaltax = totaltax;
	}

	public String getTotaldiscount() {
		return totaldiscount;
	}

	public void setTotaldiscount(String totaldiscount) {
		this.totaldiscount = totaldiscount;
	}

	public String getTotalextracharge() {
		return totalextracharge;
	}

	public void setTotalextracharge(String totalextracharge) {
		this.totalextracharge = totalextracharge;
	}

	public String getTotalpayment() {
		return totalpayment;
	}

	public void setTotalpayment(String totalpayment) {
		this.totalpayment = totalpayment;
	}

	public String getTacommision() {
		return tacommision;
	}

	public void setTacommision(String tacommision) {
		this.tacommision = tacommision;
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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getSpousedateofbirth() {
		return spousedateofbirth;
	}

	public void setSpousedateofbirth(String spousedateofbirth) {
		this.spousedateofbirth = spousedateofbirth;
	}

	public String getWeddinganniversary() {
		return weddinganniversary;
	}

	public void setWeddinganniversary(String weddinganniversary) {
		this.weddinganniversary = weddinganniversary;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
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

	public String getIdentiytype() {
		return identiytype;
	}

	public void setIdentiytype(String identiytype) {
		this.identiytype = identiytype;
	}

	public String getIdentityno() {
		return identityno;
	}

	public void setIdentityno(String identityno) {
		this.identityno = identityno;
	}

	public String getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}

	public String getTransportationmode() {
		return transportationmode;
	}

	public void setTransportationmode(String transportationmode) {
		this.transportationmode = transportationmode;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getPickupdate() {
		return pickupdate;
	}

	public void setPickupdate(String pickupdate) {
		this.pickupdate = pickupdate;
	}

	public String getPickuptime() {
		return pickuptime;
	}

	public void setPickuptime(String pickuptime) {
		this.pickuptime = pickuptime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAffiliatename() {
		return affiliatename;
	}

	public void setAffiliatename(String affiliatename) {
		this.affiliatename = affiliatename;
	}

	public String getAffiliatecode() {
		return affiliatecode;
	}

	public void setAffiliatecode(String affiliatecode) {
		this.affiliatecode = affiliatecode;
	}

	public String getCclink() {
		return cclink;
	}

	public void setCclink(String cclink) {
		this.cclink = cclink;
	}

	public String getCcno() {
		return ccno;
	}

	public void setCcno(String ccno) {
		this.ccno = ccno;
	}

	public String getCctype() {
		return cctype;
	}

	public void setCctype(String cctype) {
		this.cctype = cctype;
	}

	public String getCcexpirydate() {
		return ccexpirydate;
	}

	public void setCcexpirydate(String ccexpirydate) {
		this.ccexpirydate = ccexpirydate;
	}

	public String getCardholdersname() {
		return cardholdersname;
	}

	public void setCardholdersname(String cardholdersname) {
		this.cardholdersname = cardholdersname;
	}
	
	                  
}
