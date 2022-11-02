package com.indocosmo.pms.web.templates.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "invoice_template")
public class InvoiceTemplate {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "hdr_line1")
	private String hdrLine1;

	@Column(name = "hdr_line2")
	private String hdrLine2;

	@Column(name = "hdr_line3")
	private String hdrLine3;

	@Column(name = "hdr_line4")
	private String hdrLine4;

	@Column(name = "ftr_col1_line1")
	private String ftrCol1Line1;

	@Column(name = "ftr_col1_line2")
	private String ftrCol1Line2;

	@Column(name = "ftr_col1_line3")
	private String ftrCol1Line3;

	@Column(name = "ftr_col1_line4")
	private String ftrCol1Line4;

	@Column(name = "ftr_col2_line1")
	private String ftrCol2Line1;

	@Column(name = "ftr_col2_line2")
	private String ftrCol2Line2;

	@Column(name = "ftr_col2_line3")
	private String ftrCol2Line3;

	@Column(name = "ftr_col2_line4")
	private String ftrCol2Line4;

	@Column(name = "ftr_col3_line1")
	private String ftrCol3Line1;

	@Column(name = "ftr_col3_line2")
	private String ftrCol3Line2;

	@Column(name = "ftr_col3_line3")
	private String ftrCol3Line3;

	@Column(name = "ftr_col3_line4")
	private String ftrCol3Line4;

	@Column(name = "sign_line1")
	private String signLine1;

	@Column(name = "sign_line2")
	private String signLine2;

	@Column(name = "is_hf_incl")
	private boolean isHFIncl;

	@Column(name = "is_taxsumry_incl")
	private boolean isTaxsumryincl;

	@Transient
	private String hdrLogoUrl;

	@Transient
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHdrLine1() {
		return hdrLine1;
	}

	public void setHdrLine1(String hdrLine1) {
		this.hdrLine1 = hdrLine1;
	}

	public String getHdrLine2() {
		return hdrLine2;
	}

	public void setHdrLine2(String hdrLine2) {
		this.hdrLine2 = hdrLine2;
	}

	public String getHdrLine3() {
		return hdrLine3;
	}

	public void setHdrLine3(String hdrLine3) {
		this.hdrLine3 = hdrLine3;
	}

	public String getHdrLine4() {
		return hdrLine4;
	}

	public void setHdrLine4(String hdrLine4) {
		this.hdrLine4 = hdrLine4;
	}

	public String getFtrCol1Line1() {
		return ftrCol1Line1;
	}

	public void setFtrCol1Line1(String ftrCol1Line1) {
		this.ftrCol1Line1 = ftrCol1Line1;
	}

	public String getFtrCol1Line2() {
		return ftrCol1Line2;
	}

	public void setFtrCol1Line2(String ftrCol1Line2) {
		this.ftrCol1Line2 = ftrCol1Line2;
	}

	public String getFtrCol1Line3() {
		return ftrCol1Line3;
	}

	public void setFtrCol1Line3(String ftrCol1Line3) {
		this.ftrCol1Line3 = ftrCol1Line3;
	}

	public String getFtrCol1Line4() {
		return ftrCol1Line4;
	}

	public void setFtrCol1Line4(String ftrCol1Line4) {
		this.ftrCol1Line4 = ftrCol1Line4;
	}

	public String getFtrCol2Line1() {
		return ftrCol2Line1;
	}

	public void setFtrCol2Line1(String ftrCol2Line1) {
		this.ftrCol2Line1 = ftrCol2Line1;
	}

	public String getFtrCol2Line2() {
		return ftrCol2Line2;
	}

	public void setFtrCol2Line2(String ftrCol2Line2) {
		this.ftrCol2Line2 = ftrCol2Line2;
	}

	public String getFtrCol2Line3() {
		return ftrCol2Line3;
	}

	public void setFtrCol2Line3(String ftrCol2Line3) {
		this.ftrCol2Line3 = ftrCol2Line3;
	}

	public String getFtrCol2Line4() {
		return ftrCol2Line4;
	}

	public void setFtrCol2Line4(String ftrCol2Line4) {
		this.ftrCol2Line4 = ftrCol2Line4;
	}

	public String getFtrCol3Line1() {
		return ftrCol3Line1;
	}

	public void setFtrCol3Line1(String ftrCol3Line1) {
		this.ftrCol3Line1 = ftrCol3Line1;
	}

	public String getFtrCol3Line2() {
		return ftrCol3Line2;
	}

	public void setFtrCol3Line2(String ftrCol3Line2) {
		this.ftrCol3Line2 = ftrCol3Line2;
	}

	public String getFtrCol3Line3() {
		return ftrCol3Line3;
	}

	public void setFtrCol3Line3(String ftrCol3Line3) {
		this.ftrCol3Line3 = ftrCol3Line3;
	}

	public String getFtrCol3Line4() {
		return ftrCol3Line4;
	}

	public void setFtrCol3Line4(String ftrCol3Line4) {
		this.ftrCol3Line4 = ftrCol3Line4;
	}

	public String getSignLine1() {
		return signLine1;
	}

	public void setSignLine1(String signLine1) {
		this.signLine1 = signLine1;
	}

	public String getSignLine2() {
		return signLine2;
	}

	public void setSignLine2(String signLine2) {
		this.signLine2 = signLine2;
	}

	public boolean isHFIncl() {
		return isHFIncl;
	}

	public void setHFIncl(boolean isHFIncl) {
		this.isHFIncl = isHFIncl;
	}

	public String getHdrLogoUrl() {
		return hdrLogoUrl;
	}

	public void setHdrLogoUrl(String hdrLogoUrl) {
		this.hdrLogoUrl = hdrLogoUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isTaxsumryincl() {
		return isTaxsumryincl;
	}

	public void setTaxsumryincl(boolean isTaxsumryincl) {
		this.isTaxsumryincl = isTaxsumryincl;
	}

}
