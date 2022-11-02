package com.indocosmo.pms.web.reservation_test.model;

import javax.persistence.Transient;

public class AvailableRoomTypes {
	private int roomTypeId;
	private String roomTypeCode;
	private String roomTypeName;
	private boolean occ1;
	private boolean occ2;
	private boolean occ3;
	private boolean occ4;
	private int totalRoom;
	private int availRoom;
	private int displayOrder;
	
	@Transient
	private String image;

	public int getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getRoomTypeCode() {
		return roomTypeCode;
	}
	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public boolean isOcc1() {
		return occ1;
	}
	public void setOcc1(boolean occ1) {
		this.occ1 = occ1;
	}
	public boolean isOcc2() {
		return occ2;
	}
	public void setOcc2(boolean occ2) {
		this.occ2 = occ2;
	}
	public boolean isOcc3() {
		return occ3;
	}
	public void setOcc3(boolean occ3) {
		this.occ3 = occ3;
	}
	public boolean isOcc4() {
		return occ4;
	}
	public void setOcc4(boolean occ4) {
		this.occ4 = occ4;
	}
	public int getTotalRoom() {
		return totalRoom;
	}
	public void setTotalRoom(int totalRoom) {
		this.totalRoom = totalRoom;
	}
	public int getAvailRoom() {
		return availRoom;
	}
	public void setAvailRoom(int availRoom) {
		this.availRoom = availRoom;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

}
