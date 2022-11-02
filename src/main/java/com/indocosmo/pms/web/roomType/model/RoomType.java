package com.indocosmo.pms.web.roomType.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "room_type")
public class RoomType {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "overbooking_pc")
	private byte overBookingPrcntg;

	@Column(name = "support_single_occ")
	private boolean supportSingleOcc;

	@Column(name = "support_double_occ")
	private boolean supportDoubleOcc;

	@Column(name = "support_triple_occ")
	private boolean supportTripleOcc;

	@Column(name = "support_quad_occ")
	private boolean supportQuadOcc;

	@Column(name = "display_order")
	private int displayOrder;

	@Column(name = "is_system")
	private boolean system;

	@Column(name = "is_deleted")
	private boolean deleted;

	@Column(name = "last_upd_ts", updatable = false)
	private Date lastUpdTs;

	@Column(name = "image1")
	private String image1;

	@Column(name = "image2")
	private String image2;

	@Column(name = "image3")
	private String image3;

	@Column(name = "image4")
	private String image4;

	@Transient
	private String encryption;

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	@Transient
	private String single = "", dble = "", triple = "", quad = "";

	@Transient
	private Long noOfRooms;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getOverBookingPrcntg() {
		return overBookingPrcntg;
	}

	public void setOverBookingPrcntg(byte overBookingPrcntg) {
		this.overBookingPrcntg = overBookingPrcntg;
	}

	public boolean isSupportSingleOcc() {
		return supportSingleOcc;
	}

	public void setSupportSingleOcc(boolean supportSingleOcc) {
		this.supportSingleOcc = supportSingleOcc;
	}

	public boolean isSupportDoubleOcc() {
		return supportDoubleOcc;
	}

	public void setSupportDoubleOcc(boolean supportDoubleOcc) {
		this.supportDoubleOcc = supportDoubleOcc;
	}

	public boolean isSupportTripleOcc() {
		return supportTripleOcc;
	}

	public void setSupportTripleOcc(boolean supportTripleOcc) {
		this.supportTripleOcc = supportTripleOcc;
	}

	public boolean isSupportQuadOcc() {
		return supportQuadOcc;
	}

	public void setSupportQuadOcc(boolean supportQuadOcc) {
		this.supportQuadOcc = supportQuadOcc;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public boolean isSystem() {
		return system;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getLastUpdTs() {
		return lastUpdTs;
	}

	public void setLastUpdTs(Date lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}

	public String getSingle() {
		if (supportSingleOcc)
			single = "Yes";
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public String getDble() {
		if (supportDoubleOcc)
			dble = "Yes";
		return dble;
	}

	public void setDble(String dble) {
		this.dble = dble;
	}

	public String getTriple() {
		if (supportTripleOcc)
			triple = "Yes";
		return triple;
	}

	public void setTriple(String triple) {
		this.triple = triple;
	}

	public String getQuad() {
		if (supportQuadOcc)
			quad = "Yes";
		return quad;
	}

	public void setQuad(String quad) {
		this.quad = quad;
	}

	public Long getNoOfRooms() {
		return noOfRooms;
	}

	public void setNoOfRooms(Long noOfRooms) {
		this.noOfRooms = noOfRooms;
	}

}