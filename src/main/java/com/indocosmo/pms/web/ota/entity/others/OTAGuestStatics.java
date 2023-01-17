package com.indocosmo.pms.web.ota.entity.others;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otagueststatics")
public class OTAGuestStatics {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "guestname")
	private String guestname;
	
	@Column(name = "guestemail")
	private String guestemail;
	
	@Column(name = "totalnumberofstays")
	private String totalnumberofstays;
	
	@Column(name = "firststay")
	private String firststay;
	
	@Column(name = "firstreservationno")
	private String firstreservationno;
	
	@Column(name = "firstfoliono")
	private String firstfoliono;
	
	@Column(name = "laststay")
	private String laststay;
	
	@Column(name = "lastreservationno")
	private String lastreservationno;
	
	@Column(name = "lastfoliono")
	private String lastfoliono;
	
	@Column(name = "nextstay")
	private String nextstay;

	@Column(name = "nextreservationno")
	private String nextreservationno;
	
	@Column(name = "nextfoliono")
	private String nextfoliono;
	
	@Column(name = "lifetimespending")
	private String lifetimespending;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuestname() {
		return guestname;
	}

	public void setGuestname(String guestname) {
		this.guestname = guestname;
	}

	public String getGuestemail() {
		return guestemail;
	}

	public void setGuestemail(String guestemail) {
		this.guestemail = guestemail;
	}

	public String getTotalnumberofstays() {
		return totalnumberofstays;
	}

	public void setTotalnumberofstays(String totalnumberofstays) {
		this.totalnumberofstays = totalnumberofstays;
	}

	public String getFirststay() {
		return firststay;
	}

	public void setFirststay(String firststay) {
		this.firststay = firststay;
	}

	public String getFirstreservationno() {
		return firstreservationno;
	}

	public void setFirstreservationno(String firstreservationno) {
		this.firstreservationno = firstreservationno;
	}

	public String getFirstfoliono() {
		return firstfoliono;
	}

	public void setFirstfoliono(String firstfoliono) {
		this.firstfoliono = firstfoliono;
	}

	public String getLaststay() {
		return laststay;
	}

	public void setLaststay(String laststay) {
		this.laststay = laststay;
	}

	public String getLastreservationno() {
		return lastreservationno;
	}

	public void setLastreservationno(String lastreservationno) {
		this.lastreservationno = lastreservationno;
	}

	public String getLastfoliono() {
		return lastfoliono;
	}

	public void setLastfoliono(String lastfoliono) {
		this.lastfoliono = lastfoliono;
	}

	public String getNextstay() {
		return nextstay;
	}

	public void setNextstay(String nextstay) {
		this.nextstay = nextstay;
	}

	public String getNextreservationno() {
		return nextreservationno;
	}

	public void setNextreservationno(String nextreservationno) {
		this.nextreservationno = nextreservationno;
	}

	public String getNextfoliono() {
		return nextfoliono;
	}

	public void setNextfoliono(String nextfoliono) {
		this.nextfoliono = nextfoliono;
	}

	public String getLifetimespending() {
		return lifetimespending;
	}

	public void setLifetimespending(String lifetimespending) {
		this.lifetimespending = lifetimespending;
	}

	
	
	
}
