package com.indocosmo.pms.web.reservation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


//@Entity
//@Table(name="checkin_dtl")
public class CheckinDtl {

	@Column(name="checkin_dtl_no")
	private int checkinDtlNo;
	
	@Column(name="checkin_no")
	private int checkinNo;
	
	@Column(name="guest_id")
	private int guestId;
	
	@Column(name="is_sharer")
	private boolean isSharer;
	
	@Column(name="salutation")
	private String salutation;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="guest_name")
	private String guestName;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="address")
	private String address;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="nationality")
	private String nationality;
	
	@Column(name="state")
	private String state;
	
	@Column(name="passport_no")
	private String passportNo;
	
	@Column(name="passport_expiry_on")
	private Date passportExpiryOn;
	
	@Column(name="remarks")
	private String remarks;
	
	
	
}
