package com.indocosmo.pms.web.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "communication_dtl")
public class CommunicationDetails {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "comm_type")
	private byte communicationType;

	@Column(name = "resv_no")
	private int resvNum;

	@Column(name = "checkIn_no")
	private int chkInNum;

	@Column(name = "purpose")
	private byte purpose;

	@Column(name = "status")
	private boolean status;

	@Column(name = "description")
	private String description;

	@Column(name = "content")
	private String content;

	@Column(name = "email_to")
	private String emailto;

	@Column(name = "email_cc")
	private String emailcc;

	@Column(name = "phone")
	private String phone;

	@Column(name = "subject")
	private String subject;

	@Column(name = "last_update_ts")
	private String lastUpdatedTS;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the communicationType
	 */
	public byte getCommunicationType() {
		return communicationType;
	}

	/**
	 * @param communicationType
	 *            the communicationType to set
	 */
	public void setCommunicationType(byte communicationType) {
		this.communicationType = communicationType;
	}

	/**
	 * @return the folioNUm
	 */
	public int getResvNum() {
		return resvNum;
	}

	/**
	 * @param folioNUm
	 *            the folioNUm to set
	 */
	public void setResvNum(int resvNum) {
		this.resvNum = resvNum;
	}

	/**
	 * @return the chkInNum
	 */
	public int getChkInNum() {
		return chkInNum;
	}

	/**
	 * @param chkInNum
	 *            the chkInNum to set
	 */
	public void setChkInNum(int chkInNum) {
		this.chkInNum = chkInNum;
	}

	/**
	 * @return the purpose
	 */
	public byte getPurpose() {
		return purpose;
	}

	/**
	 * @param purpose
	 *            the purpose to set
	 */
	public void setPurpose(byte purpose) {
		this.purpose = purpose;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmailto() {
		return emailto;
	}

	public void setEmailto(String emailto) {
		this.emailto = emailto;
	}

	public String getEmailcc() {
		return emailcc;
	}

	public void setEmailcc(String emailcc) {
		this.emailcc = emailcc;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLastUpdatedTS() {
		return lastUpdatedTS;
	}

	public void setLastUpdatedTS(String lastUpdatedTS) {
		this.lastUpdatedTS = lastUpdatedTS;
	}
}
