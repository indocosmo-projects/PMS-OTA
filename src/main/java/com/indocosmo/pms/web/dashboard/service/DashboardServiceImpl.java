package com.indocosmo.pms.web.dashboard.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.dashboard.dao.DashboardDAO;
import com.indocosmo.pms.web.dashboard.model.RoomDetails;
import com.indocosmo.pms.web.nightAudit.service.NightAuditService;
import com.indocosmo.pms.web.reservation_test.dao.ReservationDaoTest;
import com.indocosmo.pms.web.reservation_test.model.AvailableRoomTypes;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private DashboardDAO dashboardDAO;

	@Autowired
	private ReservationDaoTest reservationDAO;

	@Autowired
	private NightAuditService nightAuditService;

	public JsonObject getDetails() throws Exception {
		Gson gson = new Gson();
		JsonObject dashBoard = dashboardDAO.getDataForDashBoard();
		JsonArray lastResv = dashboardDAO.getLastReservationData("order by vr.resv_no DESC limit 10");
		List<AvailableRoomTypes> availRoom = reservationDAO
				.getRoomAvailability(new java.sql.Date(commonSettings.getHotelDate().getTime()), 1, 1);
		dashBoard.add("avail_room_list", gson.toJsonTree(availRoom));
		JsonObject jObject = new JsonObject();
		jObject.add("dashboard_data", dashBoard);
		jObject.add("lastReservations", lastResv);
		return jObject;
	}

	public JsonObject getListData() throws Exception {
		JsonObject jobj = new JsonObject();
		Calendar c = Calendar.getInstance();
		c.setTime(commonSettings.getHotelDate());
		c.add(Calendar.DATE, 5);
		Date newDate = c.getTime();
		String wherepart = "where vr.cut_off_date<= '" + new java.sql.Date(newDate.getTime())
				+ "' and vr.resv_status in(0)";
		JsonArray cutoffwithin5day = dashboardDAO.getLastReservationData(wherepart);
		c.setTime(commonSettings.getHotelDate());
		// c.add(Calendar.DATE, 2);
		newDate = c.getTime();
		// wherepart="where vr.arr_date<= '"+new java.sql.Date(newDate.getTime())+"' and
		// vr.resv_status in(0,1,3)";
		wherepart = "where vr.arr_date BETWEEN '" + new java.sql.Date(newDate.getTime()) + "' AND DATE_ADD('"
				+ new java.sql.Date(newDate.getTime()) + "', INTERVAL 2 DAY) and vr.resv_status in(0,1,3)";
		JsonArray checkinwithin2days = dashboardDAO.getLastReservationData(wherepart);
		JsonArray departList = nightAuditService.getDepartList();
		jobj.add("departToday", departList);
		jobj.add("cutoffwithin5day", cutoffwithin5day);
		jobj.add("checkinwithin2days", checkinwithin2days);
		return jobj;
	}

	public List<RoomDetails> getRoomDetails(int roomType, int floor) throws Exception {
		return dashboardDAO.getRoomDetails(roomType, floor);
	}
	
	
}
