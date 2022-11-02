package com.indocosmo.pms.web.ota.dto.reservation;

import java.util.List;

import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.entity.reservation.OTACancelReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTARentalInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;

public class OTAReservationDTO {
	
	private List<OTAReservation> otareservation;
	
	private List<OTABookingTrans> otabookingtrans;
	
	private List<OTARentalInfo> otarentalinfo;
	
	private List<OTATaxDeatil> otataxdeatil;
	
	private List<OTACancelReservation> otacancelreservation;

	public List<OTAReservation> getOtareservation() {
		return otareservation;
	}

	public void setOtareservation(List<OTAReservation> otareservation) {
		this.otareservation = otareservation;
	}

	public List<OTABookingTrans> getOtabookingtrans() {
		return otabookingtrans;
	}

	public void setOtabookingtrans(List<OTABookingTrans> otabookingtrans) {
		this.otabookingtrans = otabookingtrans;
	}

	public List<OTARentalInfo> getOtarentalinfo() {
		return otarentalinfo;
	}

	public void setOtarentalinfo(List<OTARentalInfo> otarentalinfo) {
		this.otarentalinfo = otarentalinfo;
	}

	public List<OTATaxDeatil> getOtataxdeatil() {
		return otataxdeatil;
	}

	public void setOtataxdeatil(List<OTATaxDeatil> otataxdeatil) {
		this.otataxdeatil = otataxdeatil;
	}

	public List<OTACancelReservation> getOtacancelreservation() {
		return otacancelreservation;
	}

	public void setOtacancelreservation(List<OTACancelReservation> otacancelreservation) {
		this.otacancelreservation = otacancelreservation;
	}
	
	
}
