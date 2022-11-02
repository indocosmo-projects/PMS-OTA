package com.indocosmo.pms.web.corporate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.pms.enumerator.corporate.CorporateClass;

@Entity
@Table(name = "corporate")
public class Corporate {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "back_office_account_code")
	private String backOfficeAC;

	@Column(name = "contact_person")
	private String contactPerson;

	@Column(name = "contact_email")
	private String contactEmail;

	@Column(name = "contact_mobile")
	private String contactMobile;

	@Column(name = "contact_office")
	private String contactOffice;

	@Column(name = "contact_fax")
	private String contactFax;

	@Column(name = "corporate_class")
	private int corporateClass;

	@Column(name = "corporate_rating")
	private char corporateRating='A';

	@Column(name = "status")
	private short status=1;

	@Column(name = "is_system")
	private boolean isSystem;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "last_upd_ts",updatable = false)
	private Date lastUpdTs;

	@Transient
	private String encryption;

	@Transient
	private int rowCount; 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBackOfficeAC() {
		return backOfficeAC;
	}

	public void setBackOfficeAC(String backOfficeAC) {
		this.backOfficeAC = backOfficeAC;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactOffice() {
		return contactOffice;
	}

	public void setContactOffice(String contactOffice) {
		this.contactOffice = contactOffice;
	}

	public String getContactFax() {
		return contactFax;
	}

	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	public CorporateClass getCorporateClass() {
		for(CorporateClass e : CorporateClass.values()){
			if( corporateClass== e.getValue())
				return e;
		}
		
		return null;
	}

	public void setCorporateClass(CorporateClass corporateClass) {
		this.corporateClass = corporateClass.getValue();
	}

	public char getCorporateRating() {
		return corporateRating;
	}

	public void setCorporateRating(char corporateRating) {
		this.corporateRating = corporateRating;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Date getLastUpdTs() {
		return lastUpdTs;
	}

	public void setLastUpdTs(Date lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}

	public boolean isSystem() {
		return isSystem;
	}

	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

	public boolean setIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

}


