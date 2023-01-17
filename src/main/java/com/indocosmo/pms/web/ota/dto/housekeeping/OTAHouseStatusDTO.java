package com.indocosmo.pms.web.ota.dto.housekeeping;

import java.util.List;

import com.indocosmo.pms.web.ota.entity.housekeeping.OTACheckinguestlist;
import com.indocosmo.pms.web.ota.entity.housekeeping.OTARoomList;

public class OTAHouseStatusDTO {
	
	private List<OTARoomList> roomlist;
	
	private List<OTACheckinguestlist> checkinguestList;

	public List<OTARoomList> getRoomlist() {
		return roomlist;
	}

	public void setRoomlist(List<OTARoomList> roomlist) {
		this.roomlist = roomlist;
	}

	public List<OTACheckinguestlist> getCheckinguestList() {
		return checkinguestList;
	}

	public void setCheckinguestList(List<OTACheckinguestlist> checkinguestList) {
		this.checkinguestList = checkinguestList;
	}
	
	
	
	
}
