package com.indocosmo.pms.web.ota.dto.finance;

public class OTAoutinwDTO {
	
	private int id;
	
	private String type;
	
	private String transid;
	
	private String tran_datetime;
	
	private String name;
	
	private String email;
	
	private String location;
	
	private String grossamount ;
    
	private String remark ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTransid() {
		return transid;
	}

	public void setTransid(String transid) {
		this.transid = transid;
	}

	public String getTran_datetime() {
		return tran_datetime;
	}

	public void setTran_datetime(String tran_datetime) {
		this.tran_datetime = tran_datetime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGrossamount() {
		return grossamount;
	}

	public void setGrossamount(String grossamount) {
		this.grossamount = grossamount;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
