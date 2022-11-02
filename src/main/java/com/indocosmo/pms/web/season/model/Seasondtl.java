package com.indocosmo.pms.web.season.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "season_dtl")
public class Seasondtl {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "season_hdr_id")
	private Seasonhdr season_hdr;

	@Column(name = "cal_month")
	private int calmonth;

	@Column(name = "start_day")
	private int startday;

	@Column(name = "end_day")
	private int endday;

	@Transient
	int seasonHdrID = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonBackReference
	public Seasonhdr getSeason_hdr() {
		return season_hdr;
	}

	public void setSeason_hdr(Seasonhdr season_hdr) {
		this.season_hdr = season_hdr;
	}

	public int getCalmonth() {
		return calmonth;
	}

	public void setCalmonth(int calmonth) {
		this.calmonth = calmonth;
	}

	public int getStartday() {
		return startday;
	}

	public void setStartday(int startday) {
		this.startday = startday;
	}

	public int getEndday() {
		return endday;
	}

	public void setEndday(int endday) {
		this.endday = endday;
	}

	public int getSeasonHdrID() {
		return seasonHdrID;
	}

	public void setSeasonHdrID(int seasonHdrID) {
		this.seasonHdrID = seasonHdrID;
	}
}