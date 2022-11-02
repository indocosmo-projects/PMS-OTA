package com.indocosmo.pms.web.room.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.pms.enumerator.RoomHkStatus;
import com.indocosmo.pms.enumerator.RoomInventoryStatus;
import com.indocosmo.pms.enumerator.RoomOccupancyStatus;
import com.indocosmo.pms.web.roomType.model.RoomType;

@Entity
@Table(name = "room")
public class Room {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	// @Id
	@Column(name = "number")
	private String number;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "room_type_id")
	private Integer roomTypeId;

	@ManyToOne
	@JoinColumn(name = "room_type_id", insertable = false, updatable = false)
	private RoomType roomType;

	@Column(name = "floor_id")
	private Integer floorId;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "floor_id", insertable = false, updatable = false) private
	 * Floor floor;
	 */

	@Column(name = "inv_status")
	private byte invStatus = (byte) RoomInventoryStatus.OUTOFINVENTORY.getCode();

	@Column(name = "hk_status")
	private byte hkStatus = 1;

	@Column(name = "occ_status")
	private byte occStatus = 2;

	@Column(name = "room_features_ids")
	private String roomFeaturesIds;

	@Column(name = "is_system")
	private boolean system;

	@Column(name = "is_deleted")
	private boolean deleted;

	@Column(name = "last_upd_ts", updatable = false)
	private Date lastUpdTs;

	@Transient
	private String encryption;

	@Transient
	private RoomInventoryStatus invStatusEnum;

	@Transient
	RoomHkStatus roomHkStatusEnum;

	@Transient
	RoomOccupancyStatus roomOccupancyStatusEnum;

	@Transient
	private String[] roomFeatures;

	@Transient
	private String room_status;

	@Transient
	private String hk1_status;

	@Transient
	private List<RoomFeature> roomFeatureList;

	public Integer getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public RoomInventoryStatus getInvStatusEnum() {
		invStatusEnum = RoomInventoryStatus.get(invStatus);
		return invStatusEnum;
	}

	public void setInvStatusEnum(RoomInventoryStatus invStatusEnum) {
		this.invStatusEnum = invStatusEnum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public RoomHkStatus getRoomHkStatusEnum() {
		roomHkStatusEnum = RoomHkStatus.get(hkStatus);
		return roomHkStatusEnum;
	}

	public void setRoomHkStatusEnum(RoomHkStatus roomHkStatusEnum) {
		this.roomHkStatusEnum = roomHkStatusEnum;
	}

	public RoomOccupancyStatus getRoomOccupancyStatusEnum() {
		roomOccupancyStatusEnum = RoomOccupancyStatus.get(occStatus);
		return roomOccupancyStatusEnum;
	}

	public void setRoomOccupancyStatusEnum(RoomOccupancyStatus roomOccupancyStatusEnum) {
		this.roomOccupancyStatusEnum = roomOccupancyStatusEnum;
	}

	public String[] getRoomFeatures() {
		if (roomFeaturesIds != null)
			return roomFeaturesIds.split(",");
		else
			return null;
	}

	public void setRoomFeatures(String[] roomFeatures) {
		this.roomFeatures = roomFeatures;
		roomFeaturesIds = "";

		for (String roomFeature : roomFeatures) {
			roomFeaturesIds += roomFeature + ",";
		}

		if (!roomFeaturesIds.equals(""))
			roomFeaturesIds = roomFeaturesIds.substring(0, roomFeaturesIds.length() - 1);
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(byte invStatus) {
		this.invStatus = invStatus;
	}

	public byte getOccStatus() {
		return occStatus;
	}

	public void setOccStatus(byte occStatus) {
		this.occStatus = occStatus;
	}

	public byte getHkStatus() {
		return hkStatus;
	}

	public void setHkStatus(byte hkStatus) {
		this.hkStatus = hkStatus;
	}

	public String getRoomFeaturesIds() {
		return roomFeaturesIds;
	}

	public void setRoomFeaturesIds(String roomFeaturesIds) {
		this.roomFeaturesIds = roomFeaturesIds;
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

	/**
	 * @return the roomFeatureList
	 */
	public List<RoomFeature> getRoomFeatureList() {
		return roomFeatureList;
	}

	/**
	 * @param roomFeatureList
	 *            the roomFeatureList to set
	 */
	public void setRoomFeatureList(List<RoomFeature> roomFeatureList) {
		this.roomFeatureList = roomFeatureList;
	}

	public String getRoom_status() {
		return room_status;
	}

	public void setRoom_status(String room_status) {
		this.room_status = room_status;
	}

	public String getHk1_status() {
		return hk1_status;
	}

	public void setHk1_status(String hk1_status) {
		this.hk1_status = hk1_status;
	}

	public Integer getFloorId() {
		return floorId;
	}

	public void setFloorId(Integer floorId) {
		this.floorId = floorId;
	}

	/*
	 * public Floor getFloor() { return floor; }
	 * 
	 * public void setFloor(Floor floor) { this.floor = floor; }
	 */

}
