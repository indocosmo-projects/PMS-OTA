package com.indocosmo.pms.web.reception.service;

import java.util.HashMap;
import java.util.List;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.checkIn.model.CheckInDtl;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkIn.model.CheckInRate;
import com.indocosmo.pms.web.reception.dao.ReceptionDAO;
import com.indocosmo.pms.web.reservation.controller.ReservationController;
import com.indocosmo.pms.web.reservation_test.model.RoomRateEdited;
import com.indocosmo.pms.web.common.service.CommonService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.common.model.CommunicationDetails;
import com.indocosmo.pms.enumerator.CommunicationPurposes;
import com.indocosmo.pms.enumerator.CommunicationType;
import com.indocosmo.pms.web.templates.dao.TemplateDao;
import com.indocosmo.pms.web.templates.model.EmailTemplate;

@Service
public class ReceptionServiceImpl implements ReceptionService {

	@Autowired
	ReceptionDAO receptionDao;

	@Autowired
	TemplateDao templateDao;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private CommonService commonService;

	public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

	@Transactional
	public boolean saveNewCheckin(List<CheckInHdr> checkinHd, List<CheckInDtl> checkinDtl,
			List<CheckInRate> checkinRate,JsonArray checkinDiscount) throws Exception {
		return receptionDao.saveNewCheckin(checkinHd, checkinDtl, checkinRate,checkinDiscount);
	}

	@Transactional
	public JsonObject getCheckInNO(String roomNo) throws Exception {
		return receptionDao.getCheckInNO(roomNo);
	}

	@Transactional
	public boolean uploadCustomerFiles(JsonArray saveImageArray, JsonArray saveProofArray) throws Exception {
		return receptionDao.uploadCustomerFiles(saveImageArray, saveProofArray);
	}

	@Transactional
	public HashMap<String, Object> getCheckInDetails(int checkInNo) throws Exception {
		return receptionDao.getCheckInDetails(checkInNo);
	}

	@Transactional
	public boolean updateReceptionEdit(JsonObject jobj, String hotelDate) throws Exception {
		return receptionDao.updateReceptionEdit(jobj, hotelDate);
	}

	@Transactional
	public JsonObject getListData(JsonObject jbj) throws Exception {
		return receptionDao.getListData(jbj);
	}

	@Transactional
	public JsonArray getInHouseList() throws Exception {
		return receptionDao.getInHouseList();
	}

	public String mailCheckInSave(JsonArray jArray) {
		String mailStatusCheck = "success";
		if (commonSettings.isMailNotifyCheckIn()) {
			EmailTemplate emailTmpl = null;
			CheckInHdr checkInHeader = null;
			CheckInDtl checkInDetails = null;
			HashMap<String, Object> checkInData = null;
			String mailContent = null;
			String mailStatusMessage = "sent successfully";
			boolean mailStatus = true;
			try {
				emailTmpl = templateDao.getEmailTemplate(6);

				for (int i = 0; i < jArray.size(); i++) {

					checkInData = receptionDao.getCheckInDetails(jArray.get(i).getAsInt());
					checkInHeader = (CheckInHdr) checkInData.get("checkInHdr");
					checkInDetails = (CheckInDtl) checkInData.get("checkInDtl");
					MimeMessage message = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
					helper.setTo(checkInDetails.getEmail());
					helper.setSubject(emailTmpl.getSubject());
					mailContent = emailTmpl.getContent();
					mailContent = mailContent.replace("{checkin_by_first_name}", checkInDetails.getFirstName());
					mailContent = mailContent.replace("{checkin_by_last_name}",
							String.valueOf(checkInDetails.getLastName()));
					mailContent = mailContent.replace("{resv_no}", String.valueOf(checkInHeader.getCheckInNo()));
					mailContent = mailContent.replace("{min_arr_date}", String.valueOf(checkInHeader.getArrDate()));
					mailContent = mailContent.replace("{max_depart_date}",
							String.valueOf(checkInHeader.getExpDepartDate()));
					mailContent = mailContent.replace("{num_adults}", String.valueOf(checkInHeader.getNumAdults()));
					mailContent = mailContent.replace("{num_nights}", String.valueOf(checkInHeader.getNumNights()));
					helper.setText(mailContent, true);
					mailSender.send(message);
					/* } */
				}
			} catch (Exception e) {
				logger.error("Method : Reservation confirm Email " + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
				mailStatusMessage = (e.getMessage().substring(0, e.getMessage().indexOf(";")));
				mailStatus = false;
			}
			CommunicationDetails commDtl = new CommunicationDetails();
			commDtl.setCommunicationType((byte) CommunicationType.EMAIL.getCode());
			commDtl.setPurpose((byte) CommunicationPurposes.WELCOME.getCode());
			commDtl.setStatus(mailStatus);
			commDtl.setEmailto(checkInDetails.getEmail());
			commDtl.setContent(mailContent);
			commDtl.setPhone(checkInHeader.getPhone());
			commDtl.setSubject(emailTmpl.getSubject());
			commDtl.setChkInNum(checkInHeader.getCheckInNo());

			commDtl.setDescription(mailStatusMessage);
		}
		return mailStatusCheck;
	}

	public JsonArray customerDetailViaPhone(String phone) throws Exception {
		return receptionDao.customerDetailViaPhone(phone);
	}

	@Override
	public int newRoomRateIds(List<RoomRateEdited> list) throws Exception {
		// TODO Auto-generated method stub
		return receptionDao.newRoomRateIds(list);
	}

	@Override
	public void changeRoomStatus(int roomId) {
		// TODO Auto-generated method stub
		receptionDao.changeRoomStatus(roomId);
	}

	@Override
	public JsonArray getGrcFormData(int recpNo) {
		return receptionDao.getGrcFormData(recpNo);
	}

	

}