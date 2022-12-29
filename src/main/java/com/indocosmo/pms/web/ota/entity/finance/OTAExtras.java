package com.indocosmo.pms.web.ota.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otaextras")
public class OTAExtras {
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;
	
	@Column(name = "extrachargeid")
	private String extrachargeid;
	
	@Column(name = "shortcode")
	private String shortcode;
	
	@Column(name = "charge")
	private String charge;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "rate")
	private String rate;
	
	@Column(name = "chargerule")
	private String chargerule;
	
	@Column(name = "postingrule")
	private String postingrule;
	
	@Column(name = "validfrom")
	private String validfrom;
	
	@Column(name = "validto")
	private String validto;
	
	@Column(name = "ischargealways")
	private String ischargealways;
	
	@Column(name = "applyon_rateplan")
	private String applyon_rateplan;
	
	@Column(name = "applyon_special")
	private String applyon_special;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExtrachargeid() {
		return extrachargeid;
	}

	public void setExtrachargeid(String extrachargeid) {
		this.extrachargeid = extrachargeid;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getChargerule() {
		return chargerule;
	}

	public void setChargerule(String chargerule) {
		this.chargerule = chargerule;
	}

	public String getPostingrule() {
		return postingrule;
	}

	public void setPostingrule(String postingrule) {
		this.postingrule = postingrule;
	}

	public String getValidfrom() {
		return validfrom;
	}

	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}

	public String getValidto() {
		return validto;
	}

	public void setValidto(String validto) {
		this.validto = validto;
	}

	public String getIschargealways() {
		return ischargealways;
	}

	public void setIschargealways(String ischargealways) {
		this.ischargealways = ischargealways;
	}

	public String getApplyon_rateplan() {
		return applyon_rateplan;
	}

	public void setApplyon_rateplan(String applyon_rateplan) {
		this.applyon_rateplan = applyon_rateplan;
	}

	public String getApplyon_special() {
		return applyon_special;
	}

	public void setApplyon_special(String applyon_special) {
		this.applyon_special = applyon_special;
	}
		

}
