package com.indocosmo.pms.web.ota.dto.reservation;

import java.util.List;

import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRatePlans;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRateTypes;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRoomTypes;

public class OTARoomInfoDTO {
	
	private List<OTARoomRatePlans> otaroomrateplans;
	
	private List<OTARoomRateTypes> otaroomratetypes;
	
	private List<OTARoomRoomTypes> otaroomroomtypes;

	public List<OTARoomRatePlans> getOtaroomrateplans() {
		return otaroomrateplans;
	}

	public void setOtaroomrateplans(List<OTARoomRatePlans> otaroomrateplans) {
		this.otaroomrateplans = otaroomrateplans;
	}

	public List<OTARoomRateTypes> getOtaroomratetypes() {
		return otaroomratetypes;
	}

	public void setOtaroomratetypes(List<OTARoomRateTypes> otaroomratetypes) {
		this.otaroomratetypes = otaroomratetypes;
	}

	public List<OTARoomRoomTypes> getOtaroomroomtypes() {
		return otaroomroomtypes;
	}

	public void setOtaroomroomtypes(List<OTARoomRoomTypes> otaroomroomtypes) {
		this.otaroomroomtypes = otaroomroomtypes;
	}
	
	
	
	
}
