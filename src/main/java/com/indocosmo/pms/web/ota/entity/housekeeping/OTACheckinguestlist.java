package com.indocosmo.pms.web.ota.entity.housekeeping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otacheckinguestlist")
public class OTACheckinguestlist {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "hotel_code")
	private String hotel_code;
	
	@Column(name = "reservationno")
	private String reservationno;
	
	@Column(name = "guestname")
	private String guestname;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "room")
	private String room;
	
	@Column(name = "roomtype")
	private String roomtype;
	
	@Column(name = "ratetype")
	private String ratetype;
	
	@Column(name = "bookingdate")
	private String bookingdate;
	
	@Column(name = "checkindate")
	private String checkindate;
	
	@Column(name = "checkoutdate")
	private String checkoutdate;
	
	@Column(name = "businesssource")
	private String businesssource;
	
	@Column(name = "market")
	private String market;
	
	@Column(name = "travelagent")
	private String travelagent;
	
	@Column(name = "company")
	private String company;
	
	@Column(name = "tavoucherno")
	private String tavoucherno;
	
	@Column(name = "adult")
	private String adult;
	
	@Column(name = "child")
	private String child;
	
	@Column(name = "housekeepingremarks")
	private String housekeepingremarks;
	
	@Column(name = "bookingstatus")
	private String bookingstatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHotel_code() {
		return hotel_code;
	}

	public void setHotel_code(String hotel_code) {
		this.hotel_code = hotel_code;
	}

	public String getReservationno() {
		return reservationno;
	}

	public void setReservationno(String reservationno) {
		this.reservationno = reservationno;
	}

	public String getGuestname() {
		return guestname;
	}

	public void setGuestname(String guestname) {
		this.guestname = guestname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	public String getRatetype() {
		return ratetype;
	}

	public void setRatetype(String ratetype) {
		this.ratetype = ratetype;
	}

	public String getBookingdate() {
		return bookingdate;
	}

	public void setBookingdate(String bookingdate) {
		this.bookingdate = bookingdate;
	}

	public String getCheckindate() {
		return checkindate;
	}

	public void setCheckindate(String checkindate) {
		this.checkindate = checkindate;
	}

	public String getCheckoutdate() {
		return checkoutdate;
	}

	public void setCheckoutdate(String checkoutdate) {
		this.checkoutdate = checkoutdate;
	}

	public String getBusinesssource() {
		return businesssource;
	}

	public void setBusinesssource(String businesssource) {
		this.businesssource = businesssource;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getTravelagent() {
		return travelagent;
	}

	public void setTravelagent(String travelagent) {
		this.travelagent = travelagent;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTavoucherno() {
		return tavoucherno;
	}

	public void setTavoucherno(String tavoucherno) {
		this.tavoucherno = tavoucherno;
	}

	public String getAdult() {
		return adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getHousekeepingremarks() {
		return housekeepingremarks;
	}

	public void setHousekeepingremarks(String housekeepingremarks) {
		this.housekeepingremarks = housekeepingremarks;
	}

	public String getBookingstatus() {
		return bookingstatus;
	}

	public void setBookingstatus(String bookingstatus) {
		this.bookingstatus = bookingstatus;
	}
	
	
	
	
}
