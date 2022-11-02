package com.indocosmo.pms.web.reservation_test.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="checkin_discounts")
public class CheckinDiscount {
	
	
	
	@Id
	@Column(name = "id")
	private int Id;

	@Column(name = "resv_no")
	private int resvNo;

	@Column(name = "checkin_no")
	private int checkinNo;

	@Column(name = "disc_type")
	private int discType;

	@Column(name = "disc_id")
	private int discId;

	@Column(name = "disc_code")
	private String discCode;

	@Column(name = "disc_is_pc")
	private boolean discIsPc;

	@Column(name = "disc_amount")
	private BigDecimal discAmount;

	@Column(name = "disc_pc")
	private BigDecimal discPc;

	@Column(name = "is_open")
	private boolean isOpen;
	
	@Column(name = "discount_for")
	private int discountFor;
	
	
	public int getResvNo() {
		return resvNo;
	}

	public void setResvNo(int resvNo) {
		this.resvNo = resvNo;
	}

	public int getCheckinNo() {
		return checkinNo;
	}

	public void setCheckinNo(int checkinNo) {
		this.checkinNo = checkinNo;
	}

	public int getDiscType() {
		return discType;
	}

	public void setDiscType(int discType) {
		this.discType = discType;
	}

	public int getDiscId() {
		return discId;
	}

	public void setDiscId(int discId) {
		this.discId = discId;
	}

	public String getDiscCode() {
		return discCode;
	}

	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}

	public boolean isDiscIsPc() {
		return discIsPc;
	}

	public void setDiscIsPc(boolean discIsPc) {
		this.discIsPc = discIsPc;
	}

	public BigDecimal getDiscAmount() {
		return discAmount;
	}

	public void setDiscAmount(BigDecimal discAmount) {
		this.discAmount = discAmount;
	}

	public BigDecimal getDiscPc() {
		return discPc;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public void setDiscPc(BigDecimal discPc) {
		this.discPc = discPc;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public int getDiscountFor() {
		return discountFor;
	}

	public void setDiscountFor(int discountFor) {
		this.discountFor = discountFor;
	}

	
	
	
}
