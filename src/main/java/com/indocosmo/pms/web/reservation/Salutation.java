package com.indocosmo.pms.web.reservation;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


//@Entity
//@Table(name="salutation")
public class Salutation {

	@Column(name="checkin_no")
	private int checkin_no;
	
	@Column(name="resv_no")
	private int resv_no;
	
	@Column(name="folio_bind_no")
	private int folio_bind_no;//tyni
	
	@Column(name="status")
	private int status;//tyni
	
	@Column(name="type")
	private int type;//tyni
	
	@Column(name="corporate_id")
	private int corporate_id;
	
	@Column(name="corporate_name")
	private String corporate_name;
	
	@Column(name="corporate_address")
	private String corporate_address;
	
	@Column(name="room_type_id")
	private int room_type_id;
	
	@Column(name="room_type_code")
	private String room_type_code;
	
	@Column(name="room_number")
	private String room_number;
	
	@Column(name="arr_date")
	private Date arr_date;
	
	@Column(name="arr_time")
	private Date arr_time;//time
	
	@Column(name="exp_depart_date")
	private Date exp_depart_date;
	
	@Column(name="exp_depart_time")
	private Date exp_depart_time;//time
	
	@Column(name="act_depart_date")
	private Date act_depart_date;
	
	@Column(name="act_depart_time")
	private Date act_depart_time;//time
	
	@Column(name="rate_type")
	private int rate_type;//tiny
	
	@Column(name="rate_id")
	private int rate_id;
	
	@Column(name="rate_code")
	private String rate_code;
	
	@Column(name="rate_description")
	private String rate_description;
	
	@Column(name="occupancy")
	private int occupancy;//tiny
	
	@Column(name="disc_id")
	private int disc_id;
	
	@Column(name="arr_time")
	private String disc_code;
	
	@Column(name="disc_is_pc")
	private boolean disc_is_pc;
	
	@Column(name="disc_amount")
    private BigDecimal disc_amount; //decimal
	
	@Column(name="disc_pc")
    private BigDecimal disc_pc;
	
	@Column(name="disc_is_open")
    private boolean disc_is_open;
	
	@Column(name="billing_instruction")
    private String billing_instruction;
	
	@Column(name="checkin_by")
    private int checkin_by;
	
	@Column(name="checkout_by")
    private int checkout_by;
	
	@Column(name="checkout_at")
    private Date checkout_at;//datetime
	
}
