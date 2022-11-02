package com.indocosmo.pms.web.reservation_test.service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.CommunicationPurposes;
import com.indocosmo.pms.enumerator.CommunicationType;
import com.indocosmo.pms.web.checkinRequest.dao.CheckinRequestDAO;
import com.indocosmo.pms.web.common.CustomizedException;
import com.indocosmo.pms.web.common.model.CommunicationDetails;
import com.indocosmo.pms.web.common.service.CommonService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.dashboard.dao.DashboardDAO;
import com.indocosmo.pms.web.reservation.controller.ReservationController;
import com.indocosmo.pms.web.reservation.model.Cancelreason;
import com.indocosmo.pms.web.reservation_test.dao.ReservationDaoTest;
import com.indocosmo.pms.web.reservation_test.model.AvailableRoomTypes;
import com.indocosmo.pms.web.reservation_test.model.ResvDtl;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.reservation_test.model.ResvRoom;
import com.indocosmo.pms.web.reservation_test.model.ResvRoomRatePlans;
import com.indocosmo.pms.web.reservation_test.model.RoomRateDetailsCheck;
import com.indocosmo.pms.web.reservation_test.model.RoomRateEdited;
import com.indocosmo.pms.web.room.model.Room;
import com.indocosmo.pms.web.templates.dao.TemplateDao;
import com.indocosmo.pms.web.templates.model.EmailTemplate;

@Service
public class ReservationServiceTestImpl implements ReservationServiceTest {

	@Autowired
	ReservationDaoTest reservationDao;

	@Autowired
	DashboardDAO dashboardDao;

	@Autowired
	CheckinRequestDAO checkinRequestDAO;

	@Autowired
	TemplateDao templateDao;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private CommonService commonService;

	public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

	public List<AvailableRoomTypes> getRoomAvailability(Date chInDate, int night, int rooms) {

		return reservationDao.getRoomAvailability(chInDate, night, rooms);
	}

	public List<ResvRoomRatePlans> getRoomRates(Date arrDate, int nights, int corpId, String roomType, int rate_id) {
		return reservationDao.getRoomRates(arrDate, nights, corpId, roomType, rate_id);
	}

	public JsonObject getRoomRateDetails(RoomRateDetailsCheck rateDtls) {
		return reservationDao.getRoomRateDetails(rateDtls);
	}

	@Transactional
	public boolean save(String resvHdr, List<ResvDtl> resvDtlList,JsonArray checkinDiscount) throws Exception {
		boolean isSave = reservationDao.save(resvHdr, resvDtlList,checkinDiscount);
		return isSave;
	}

	@Transactional
	public ResvRoom getRoomData(int resvRoomNo) {
		return reservationDao.getRoomData(resvRoomNo);
	}

	@Transactional
	public JsonArray getAvailableRoomData(Date minArrDate, Date maxDepartDate, int roomTypeId, int occupancy)
			throws Exception {
		Gson gson = new Gson();
		List<Room> roomList = reservationDao.getAvailableRoomData(minArrDate, maxDepartDate, roomTypeId, occupancy);
		return (JsonArray) gson.toJsonTree(roomList);
	}
	

	@Transactional
	public String updateResvRoomAndRequests(ResvRoom resvRoom) {
		String returnStatus = "error";
		boolean isSave = false;
		try {
			isSave = reservationDao.updateResvRoom(resvRoom);
			if (isSave) {
				isSave = checkinRequestDAO.saveAddOns(resvRoom.getCheckinRequest());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isSave)
			returnStatus = "success";

		return returnStatus;
	}

	public HashMap<String, Object> getResvRecord(int resvNo) throws Exception {
		return reservationDao.getResvRecord(resvNo);
	}

	public ResvHdr getResvHdr(int resvNo) throws Exception {
		ResvHdr rhdr = null;
		try {
			rhdr = reservationDao.getResvHdr(resvNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rhdr;
	}

	public boolean cancellationSave(ResvHdr resvHdr, List<ResvRoom> resvRoomList, int noCancelCount, int checkStatus)
			throws CustomizedException {
		boolean isSave = reservationDao.cancellationSave(resvHdr, resvRoomList, noCancelCount, checkStatus);
		return isSave;

	}

	public String getMailCancellation(int resvNo, int noCancelCount, int nightCount) {

		String isSucess = "success";
		if (commonSettings.isMailNotifyCancellation()) {
			EmailTemplate emailTmpl = null;
			HashMap<String, Object> resvData = null;
			ResvHdr resvHeader = null;
			String mailStatusMessage = "sent successfully";
			boolean mailStatus = true;
			String mailContent = null;
			try {
				emailTmpl = templateDao.getEmailTemplate(4);
				resvData = reservationDao.getResvRecord(resvNo);
				resvHeader = (ResvHdr) resvData.get("resvHdr");
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(resvHeader.getResvByMail());
				helper.setSubject(emailTmpl.getSubject());
				mailContent = emailTmpl.getContent();
				mailContent = mailContent.replace("{resv_by_first_name}", resvHeader.getResvByFirstName());
				mailContent = mailContent.replace("{resv_by_last_name}", resvHeader.getResvByLastName());
				mailContent = mailContent.replace("{sum_num_rooms}", String.valueOf(resvHeader.getNumRooms()));
				mailContent = mailContent.replace("{nights}", String.valueOf(nightCount));
				if (noCancelCount != 0) {
					mailContent = mailContent.replace("{cancellation_template}",
							"Your ".concat(String.valueOf(resvHeader.getNumRooms() - noCancelCount))
									.concat(" room(s) out of ").concat(String.valueOf(resvHeader.getNumRooms())));
				} else {
					mailContent = mailContent.replace("{cancellation_template}",
							"You were booked for ".concat(String.valueOf(resvHeader.getNumRooms())));
				}
				mailContent = mailContent.replace("{resv_no}", String.valueOf(resvHeader.getResvNo()));
				mailContent = mailContent.replace("{min_arr_date}", resvHeader.getMinArrDate().toString());
				mailContent = mailContent.replace("{max_depart_date}", resvHeader.getMaxDepartDate().toString());
				mailContent = mailContent.replace("{num_nights}", String.valueOf(resvHeader.getNumNights()));
				mailContent = mailContent.replace("{num_adults}", String.valueOf(resvHeader.getNumAdults()));
				helper.setText(mailContent, true);
				mailSender.send(message);
			} catch (Exception e) {
				logger.error("Method : Reservation confirm Email " + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
				mailStatusMessage = (e.getMessage().substring(0, e.getMessage().indexOf(";")));
				mailStatus = false;
			}
			CommunicationDetails commDtl = new CommunicationDetails();
			commDtl.setCommunicationType((byte) CommunicationType.EMAIL.getCode());
			commDtl.setResvNum(resvHeader.getResvNo());
			commDtl.setPurpose((byte) CommunicationPurposes.CANCELLATION.getCode());
			commDtl.setEmailto(resvHeader.getResvByMail());
			commDtl.setContent(mailContent);
			commDtl.setStatus(mailStatus);
			commDtl.setSubject(emailTmpl.getSubject());
			commDtl.setDescription(mailStatusMessage);
			try {
				commonService.saveCommunicationDetails(commDtl);
			} catch (Exception e) {
				logger.error("Method : Save Email send status" + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
			}
		}
		return isSucess;

	}

	public JsonArray getCalendarData(Date checkinDate, int nights) throws Exception {
		return reservationDao.getCalendarData(checkinDate, nights);
	}

	public boolean saveReservationEdit(ResvHdr resvHdrNew, List<RoomRateDetailsCheck> resvDtlListNew,
			List<ResvRoom> resvRoomListNew, JsonObject changesObj,JsonArray checkinDiscount) throws Exception {

		return reservationDao.saveReservationEdit(resvHdrNew, resvDtlListNew, resvRoomListNew, changesObj,checkinDiscount);
	}
	/*
	 * public JsonObject getListData(JsonObject
	 * jbj,Map<String,String>advanceSearchMap,Map<String,String>simpleSearchMap)
	 * throws Exception{ return
	 * reservationDao.getListData(jbj,advanceSearchMap,simpleSearchMap); }
	 */

	public JsonObject getListData(JsonObject jbj) throws Exception {
		return reservationDao.getListData(jbj);
	}

	public JsonObject getResvDtlList(int resvId) throws Exception {
		return reservationDao.getResvDtlList(resvId);
	}

	public JsonObject getNewArrivalDateData(int resvNo, java.sql.Date sqlDate, int discType, int noNight,
			int resvType) {
		return reservationDao.getNewArrivalDateData(resvNo, sqlDate, discType, noNight, resvType);
	}

	public boolean saveChangeArrivalDate(int resvNo, java.util.Date arrDate,
			List<RoomRateDetailsCheck> roomRateDetailsList, JsonArray disc) throws Exception {
		return reservationDao.saveChangeArrivalDate(resvNo, arrDate, roomRateDetailsList, disc);
	}

	public JsonArray getReservationData(String wherePart) throws Exception {
		return reservationDao.getReservationData(wherePart);
	}

	public JsonObject getResvRecordDetails(int resvNo) {
		return reservationDao.getResvRecordDetails(resvNo);
	}

	public String mailReservationSave(int resvNo) {
		String mailStatusCheck = "success";
		if (commonSettings.isMailNotifyReservation()) {
			EmailTemplate emailTmpl = null;
			ResvHdr resvHeader = null;
			String mailStatusMessage = "sent successfully";
			boolean mailStatus = true;
			String mailContent = null;
			try {
				emailTmpl = templateDao.getEmailTemplate(1);
				resvHeader = reservationDao.getRecord(resvNo);
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(resvHeader.getResvByMail());
				helper.setSubject(emailTmpl.getSubject());
				mailContent = emailTmpl.getContent();
				mailContent = mailContent.replace("{resv_by_first_name}", resvHeader.getResvByFirstName());
				mailContent = mailContent.replace("{resv_by_last_name}", resvHeader.getResvByLastName());
				mailContent = mailContent.replace("{sum_num_rooms}", String.valueOf(resvHeader.getSumNumRooms()));
				mailContent = mailContent.replace("{resv_no}", String.valueOf(resvHeader.getResvNo()));
				mailContent = mailContent.replace("{min_arr_date}", resvHeader.getMinArrDate().toString());
				mailContent = mailContent.replace("{max_depart_date}", resvHeader.getMaxDepartDate().toString());
				mailContent = mailContent.replace("{num_nights}", String.valueOf(resvHeader.getNumNights()));
				mailContent = mailContent.replace("{num_adults}", String.valueOf(resvHeader.getNumAdults()));
				mailContent = mailContent.replace("{cut_off_date}", String.valueOf(resvHeader.getCutOffDate()));
				helper.setText(mailContent, true);
				mailSender.send(message);
			} catch (Exception e) {
				logger.error("Method : Reservation confirm Email " + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
				mailStatusMessage = (e.getMessage().substring(0, e.getMessage().indexOf(";")));
				mailStatus = false;
			}
			CommunicationDetails commDtl = new CommunicationDetails();
			commDtl.setCommunicationType((byte) CommunicationType.EMAIL.getCode());
			commDtl.setResvNum(resvHeader.getResvNo());
			commDtl.setPurpose((byte) CommunicationPurposes.BOOKING.getCode());
			commDtl.setStatus(mailStatus);
			commDtl.setDescription(mailStatusMessage);
			commDtl.setContent(mailContent);
			commDtl.setEmailto(resvHeader.getResvByMail());
			commDtl.setPhone(resvHeader.getResvByPhone());
			commDtl.setSubject(emailTmpl.getSubject());
			try {
				commonService.saveCommunicationDetails(commDtl);
			} catch (Exception e) {
				logger.error("Method : Save Email send status" + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
			}
		}

		return mailStatusCheck;

	}

	public boolean saveNoShow(int resvNo, List<ResvRoom> resvRoomList, int noShowCount, int checkStatus)
			throws Exception {
		boolean isSave = reservationDao.saveNoShow(resvNo, resvRoomList, noShowCount, checkStatus);

		return isSave;
	}

	@Override
	public boolean reasonSave(Cancelreason creason) {
		return reservationDao.reasonSave(creason);
	}

	@Override
	public String mailNoshowSave(int resvNo) {
		String mailStatusCheck = "success";
		if (commonSettings.isMailNotifyNoshow()) {
			EmailTemplate emailTmpl = null;
			HashMap<String, Object> resvData = null;
			ResvHdr resvHeader = null;
			String mailStatusMessage = "sent successfully";
			boolean mailStatus = true;
			String mailContent = null;
			try {
				emailTmpl = templateDao.getEmailTemplate(5);
				resvData = reservationDao.getResvRecord(resvNo);
				resvHeader = (ResvHdr) resvData.get("resvHdr");
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(resvHeader.getResvByMail());
				helper.setSubject(emailTmpl.getSubject());
				mailContent = emailTmpl.getContent();
				mailContent = mailContent.replace("{resvNo}", resvData.toString());
				mailContent = mailContent.replace("{resv_by_first_name}", resvHeader.getResvByFirstName());
				mailContent = mailContent.replace("{resv_by_last_name}", resvHeader.getResvByLastName());
				mailContent = mailContent.replace("{sum_num_rooms}", String.valueOf(resvHeader.getNumRooms()));
				mailContent = mailContent.replace("{resv_no}", String.valueOf(resvHeader.getResvNo()));
				mailContent = mailContent.replace("{min_arr_date}", resvHeader.getMinArrDate().toString());
				mailContent = mailContent.replace("{max_depart_date}", resvHeader.getMaxDepartDate().toString());
				mailContent = mailContent.replace("{num_nights}", String.valueOf(resvHeader.getNumNights()));
				mailContent = mailContent.replace("{num_adults}", String.valueOf(resvHeader.getNumAdults()));
				helper.setText(mailContent, true);
				mailSender.send(message);
			} catch (Exception e) {
				logger.error("Method : Reservation confirm Email " + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
				mailStatusMessage = (e.getMessage().substring(0, e.getMessage().indexOf(";")));
				mailStatus = false;
			}
			CommunicationDetails commDtl = new CommunicationDetails();
			commDtl.setCommunicationType((byte) CommunicationType.EMAIL.getCode());
			commDtl.setResvNum(resvHeader.getResvNo());
			commDtl.setPurpose((byte) CommunicationPurposes.NOSHOW.getCode());
			commDtl.setStatus(mailStatus);
			commDtl.setDescription(mailStatusMessage);
			commDtl.setSubject(emailTmpl.getSubject());
			commDtl.setEmailto(resvHeader.getResvByMail());
			commDtl.setContent(mailContent);
			commDtl.setPhone(resvHeader.getResvByPhone());
			try {
				commonService.saveCommunicationDetails(commDtl);
			} catch (Exception e) {
				logger.error("Method : Save Email send status" + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
			}
		}
		return mailStatusCheck;
	}

	@Override
	public JsonArray getCountriesList() {
		return reservationDao.getCountryList();
	}

	public JsonArray detailViaPhonenumResv(String phonenum) throws Exception {
		return reservationDao.detailViaPhonenumResv(phonenum);
	}

	public JsonArray getResvStatus() {
		return reservationDao.getResvStatus();
	}

	@Override
	public JsonArray updateNewRoomRates(List<RoomRateEdited> list) throws Exception {

		return reservationDao.updateNewRoomRates(list);
	}

	@Override
	public List<RoomRateDetailsCheck> getRoomRateForAllOccupancy(Date arrDate, int night, int rateid, String roomType) {
		return reservationDao.getRoomRateForAllOccupancy(arrDate, night, rateid, roomType);
	}

	@Override
	public JsonArray getCustomerData(String salutation, String firstName, String lastName) throws SQLException {
		// TODO Auto-generated method stub
		return reservationDao.fetchCustomerData(salutation, firstName, lastName);
	}

	@Override
	public void deletePickUp(int resvNo) {
		// TODO Auto-generated method stub
		reservationDao.deletePickUp(resvNo);
	}

	@Override
	public JsonArray getGrcData(int resvNo) {

		return reservationDao.getGrcData(resvNo);
	}

	@Override
	public JsonArray getBookingData(int resvNo) throws Exception {
		// TODO Auto-generated method stub
		return reservationDao.getBookingData(resvNo);
	}

	

}
