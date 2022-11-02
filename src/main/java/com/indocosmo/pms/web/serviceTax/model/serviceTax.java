package com.indocosmo.pms.web.serviceTax.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="service_tax")
public class serviceTax {
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "service_tax")	
	private double serviceTax;
	
	@Column(name = "abatement")
	private double abatement;
	
	@Column(name = "cess1_name")
	private String cess1Name;
	
	@Column(name = "cess2_name")
	private String cess2Name;
	
	@Column(name = "cess3_name")
	private String cess3Name;
	
	@Column(name = "cess4_name")
	private String cess4Name;
	
	@Column(name = "cess5_name")
	private String cess5Name;
	
	@Column(name = "cess1_pc")
	private double cess1_pc;
	
	@Column(name = "cess2_pc")
	private double cess2_pc;
	
	@Column(name = "cess3_pc")
	private double cess3_pc;
	
	@Column(name = "cess4_pc")
	private double cess4_pc;
	
	@Column(name = "cess5_pc")
	private double cess5_pc;
	
	@Column(name = "is_deleted")
	private boolean isDeleted;
	
	@Column(name = "user_id")
	private int userId;

	@Transient
	private double cess1_amt;

	@Transient
	private double cess2_amt;

	@Transient
	private double cess3_amt;

	@Transient
	private double cess4_amt;

	@Transient
	private double cess5_amt;

	@Transient
	private double serviceTax_amt;

	@Transient
	private double totalServiceTax;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}
	public double getAbatement() {
		return abatement;
	}
	public void setAbatement(double abatement) {
		this.abatement = abatement;
	}
	public String getCess1Name() {
		return cess1Name;
	}
	public void setCess1Name(String cess1Name) {
		this.cess1Name = cess1Name;
	}
	public String getCess2Name() {
		return cess2Name;
	}
	public void setCess2Name(String cess2Name) {
		this.cess2Name = cess2Name;
	}
	public String getCess3Name() {
		return cess3Name;
	}
	public void setCess3Name(String cess3Name) {
		this.cess3Name = cess3Name;
	}
	public String getCess4Name() {
		return cess4Name;
	}
	public void setCess4Name(String cess4Name) {
		this.cess4Name = cess4Name;
	}
	public String getCess5Name() {
		return cess5Name;
	}
	public void setCess5Name(String cess5Name) {
		this.cess5Name = cess5Name;
	}
	public double getCess1_pc() {
		return cess1_pc;
	}
	public void setCess1_pc(double cess1_pc) {
		this.cess1_pc = cess1_pc;
	}
	public double getCess2_pc() {
		return cess2_pc;
	}
	public void setCess2_pc(double cess2_pc) {
		this.cess2_pc = cess2_pc;
	}
	public double getCess3_pc() {
		return cess3_pc;
	}
	public void setCess3_pc(double cess3_pc) {
		this.cess3_pc = cess3_pc;
	}
	public double getCess4_pc() {
		return cess4_pc;
	}
	public void setCess4_pc(double cess4_pc) {
		this.cess4_pc = cess4_pc;
	}
	public double getCess5_pc() {
		return cess5_pc;
	}
	public void setCess5_pc(double cess5_pc) {
		this.cess5_pc = cess5_pc;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getCess1_amt() {
		return cess1_amt;
	}
	public void setCess1_amt(double cess1_amt) {
		this.cess1_amt = cess1_amt;
	}
	public double getCess2_amt() {
		return cess2_amt;
	}
	public void setCess2_amt(double cess2_amt) {
		this.cess2_amt = cess2_amt;
	}
	public double getCess3_amt() {
		return cess3_amt;
	}
	public void setCess3_amt(double cess3_amt) {
		this.cess3_amt = cess3_amt;
	}
	public double getCess4_amt() {
		return cess4_amt;
	}
	public void setCess4_amt(double cess4_amt) {
		this.cess4_amt = cess4_amt;
	}
	public double getCess5_amt() {
		return cess5_amt;
	}
	public void setCess5_amt(double cess5_amt) {
		this.cess5_amt = cess5_amt;
	}
	public double getServiceTax_amt() {
		return serviceTax_amt;
	}
	public void setServiceTax_amt(double serviceTax_amt) {
		this.serviceTax_amt = serviceTax_amt;
	}
	public double getTotalServiceTax() {
		return totalServiceTax;
	}
	public void setTotalServiceTax(double totalServiceTax) {
		this.totalServiceTax = totalServiceTax;
	}

}
