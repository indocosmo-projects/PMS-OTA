package com.indocosmo.pms.web.reservation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity

//@Table(name="")
public class RoomAllocation {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="seller_id")
	private int seller_id;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="room_type_id")
	private int roomTypeId;
	
	@Column(name="room_type_code")
	private String roomTypeCode;
	
	@Column(name="num_proposed")
	private int numProposed;
	
	@Column(name="num_released")
	private int numReleased;
	
	@Column(name="num_actualized")
	private int numActualized;


}
