package com.indocosmo.pms.web.checkIn.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.checkIn.dao.CheckInDAO;
import com.indocosmo.pms.web.checkIn.model.CheckInDtl;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkIn.model.CheckInRate;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckinType;
import com.indocosmo.pms.web.checkIn.model.PaymentType;
import com.indocosmo.pms.web.checkinRequest.service.CheckinRequestService;
import com.indocosmo.pms.web.reservation_test.model.ResvRoom;
import com.indocosmo.pms.web.reservation_test.service.ReservationServiceTest;

@Service
public class CheckInServiceImp implements CheckInService {
	@Autowired
	CheckInDAO checkInDAO;

	@Autowired
	ReservationServiceTest reservationService;

	@Autowired
	CheckinRequestService checkinRequestService;

	@Transactional
	public int getTotalCheckInRooms(int checkInId) throws Exception {
		return checkInDAO.getTotalCheckInRooms(checkInId);
	}

	@Transactional
	public String save(CheckInHdr checkInHdr, CheckInDtl checkInDtl, List<CheckInRate> CheckInRateArray)
			throws Exception {
		return checkInDAO.save(checkInHdr, checkInDtl, CheckInRateArray);
	}

	@Transactional
	public JsonArray getCheckInData(int resvNo) throws Exception {
		return checkInDAO.getCheckInData(resvNo);
	}

	@Transactional
	public JsonObject getCheckInDetails(int resvRoomNo, String minArrDateString, String maxDepartDateString,
			int roomTypeId, int occupancy) throws Exception {
		JsonObject jobj = new JsonObject();
		Gson gson = new Gson();
		ResvRoom resvRoom = reservationService.getRoomData(resvRoomNo);
		List<CheckInRequest> chrqList = checkinRequestService.getCheckInRequestList(resvRoomNo, "reservation");
		resvRoom.setCheckinRequest(chrqList);
		jobj.addProperty("roomData", gson.toJson(resvRoom));
		Date minArrDate = new Date(Long.parseLong(minArrDateString));
		Date maxDepartDate = new Date(Long.parseLong(maxDepartDateString));
		jobj.add("availableRooms",
				reservationService.getAvailableRoomData(minArrDate, maxDepartDate, roomTypeId, occupancy));
		return jobj;
	}
	
	@Override
	public void changeRoomStatus(int roomId) {
		// TODO Auto-generated method stub
		checkInDAO.changeRoomStatus(roomId);
	}
	@Override
	public void updateGroupCheckin(int roomId,String roomnumber) throws Exception {
		 checkInDAO.updateGroupCheckin(roomId,roomnumber);
	}
	

	@Transactional
	public JsonObject getReservationRateDetails(int resvRoomNo) throws Exception {
		return checkInDAO.getReservationRateDetails(resvRoomNo);
	}

	@Override
	public List<CheckInRate> getRoomCharge(int checkinNo) {
		// TODO Auto-generated method stub
		return checkInDAO.getRoomCharge(checkinNo);
	}

	@Override
	public List<CheckinType> getCheckinTypes() throws Exception {
		// TODO Auto-generated method stub
		return checkInDAO.getCheckinTypes();
	}

	@Override
	public JsonArray getCustomers() {
		// TODO Auto-generated method stub
		return checkInDAO.getCustomers();
	}

	@Override
	public JsonArray loadData(String customer) {
		// TODO Auto-generated method stub
		return checkInDAO.loadData(customer);
	}

	@Override
	public JsonObject loadDataByMail(String mail) {
		// TODO Auto-generated method stub
		return checkInDAO.loadDataByMail(mail);
	}

	@Override
	public List<PaymentType> getPaymentTypes() {
		// TODO Auto-generated method stub
		return checkInDAO.getPaymentTypes();
	}
}