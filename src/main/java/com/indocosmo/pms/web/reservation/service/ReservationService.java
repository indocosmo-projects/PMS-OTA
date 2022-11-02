package com.indocosmo.pms.web.reservation.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonObject;
import com.indocosmo.pms.web.reservation.model.Cancelreason;
import com.indocosmo.pms.web.reservation.model.ResvDtl;
import com.indocosmo.pms.web.reservation.model.ResvHdr;
import com.indocosmo.pms.web.reservation.model.TxnHdr;
import com.indocosmo.pms.web.reservation.model.RoomAvailability;
import com.indocosmo.pms.web.room.model.Room;

public interface ReservationService {
	public RoomAvailability getReservtionDetails(int id);

	public boolean saveTxn(TxnHdr txnHdr);

	public HashMap<String, Object> getResvRecord(int resvNo) throws Exception;

	public List<Room> getAvailableRooms(Date arrivalDate, Date departDate, int roomType, int occupancy)
			throws Exception;

	public ResvHdr getRecord(int reservationNo);

	public RoomAvailability getRoomDetails(int resvId);

	public ResvHdr getPersonalDetails(int resrvId);

	public boolean reasonSave(Cancelreason creason);

	public boolean cancellationSave(ResvHdr resvHdr);

	public double getDepositAmount(int resrvId);

	public double getNetAmount(ResvDtl resvDtl);

	public boolean confirmation(ResvHdr resvHdr);

	public List<ResvDtl> getTotalPayable(int resrvId);

	public JsonObject getStatusRooms(Date arrivalDate, Date departDate, String roomType, int occupancy, String roomno);

	public String UpdateNoshow(int resv_no, String[] slctdtlno, int selectstatus, ResvHdr resvhdr);

	public int updateCutOffDate(ResvHdr resvHdr);

	public int updatePickUpDetails(ResvHdr resvHdr);

	public String mailConfirmationSave(int resvNo);
}
