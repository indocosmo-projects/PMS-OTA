package com.indocosmo.pms.web.reservation.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.CommunicationPurposes;
import com.indocosmo.pms.enumerator.CommunicationType;
import com.indocosmo.pms.web.common.model.CommunicationDetails;
import com.indocosmo.pms.web.common.service.CommonService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.reservation.dao.ReservationDAO;
import com.indocosmo.pms.web.reservation.model.Cancelreason;
import com.indocosmo.pms.web.reservation.model.ResvDtl;
import com.indocosmo.pms.web.reservation.model.ResvHdr;
import com.indocosmo.pms.web.reservation.model.RoomAvailability;
import com.indocosmo.pms.web.reservation.model.TxnHdr;
import com.indocosmo.pms.web.room.model.Room;
import com.indocosmo.pms.web.templates.dao.TemplateDao;
import com.indocosmo.pms.web.templates.model.EmailTemplate;

@Service
public class ReservationServiceImp implements ReservationService {

	@Autowired
	ReservationDAO reservationDao;

	@Autowired
	TemplateDao templateDao;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	CommonService commonService;

	public static final Logger logger = LoggerFactory.getLogger(ReservationServiceImp.class);

	public RoomAvailability getReservtionDetails(int id) {
		return reservationDao.getReservtionDetails(id);
	}

	@Transactional
	public boolean saveTxn(TxnHdr txnHdr) {
		return reservationDao.saveTxn(txnHdr);
	}

	@Transactional
	public HashMap<String, Object> getResvRecord(int resvNo) throws Exception {
		return reservationDao.getResvRecord(resvNo);

	}

	public List<Room> getAvailableRooms(Date arrivalDate, Date departDate, int roomType, int occupancy)
			throws Exception {
		return reservationDao.getAvailableRooms(arrivalDate, departDate, roomType, occupancy);
	}

	public ResvHdr getRecord(int reservationNo) {
		return reservationDao.getRecord(reservationNo);
	}

	public ResvHdr getPersonalDetails(int resrvId) {
		return reservationDao.getPersonalDetails(resrvId);
	}

	public RoomAvailability getRoomDetails(int resvId) {
		return reservationDao.getRoomDetails(resvId);
	}

	public boolean reasonSave(Cancelreason creason) {
		return reservationDao.reasonSave(creason);
	}

	public boolean cancellationSave(ResvHdr resvHdr) {
		boolean status = reservationDao.cancellationSave(resvHdr);
		if (status) {
			if (commonSettings.isMailNotifyConfirmation()) {
				EmailTemplate emailTmpl = null;
				ResvHdr resvHeader = null;
				String mailStatusMessage = "sent successfully";
				boolean mailStatus = true;
				try {
					emailTmpl = templateDao.getEmailTemplate(4);
					resvHeader = reservationDao.getRecord(resvHdr.getResvNo());
					MimeMessage message = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
					helper.setTo(resvHeader.getResvByMail());
					helper.setSubject(emailTmpl.getSubject());
					String mailContent = emailTmpl.getContent();
					mailContent = mailContent.replace("{resv_by_first_name}", resvHeader.getResvByFirstName());
					mailContent = mailContent.replace("{resv_by_last_name}", resvHeader.getResvByLastName());
					mailContent = mailContent.replace("{sum_num_rooms}", String.valueOf(resvHeader.getSumNumRooms()));
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
				commDtl.setStatus(mailStatus);
				commDtl.setDescription(mailStatusMessage);
				try {
					commonService.saveCommunicationDetails(commDtl);
				} catch (Exception e) {
					logger.error("Method : Save Email send status" + Throwables.getStackTraceAsString(e));
					e.printStackTrace();
				}
			}
		}
		return status;

	}

	public double getDepositAmount(int resrvId) {
		return reservationDao.getDepositAmount(resrvId);
	}

	public double getNetAmount(ResvDtl resvDtl) {
		return reservationDao.getNetAmount(resvDtl);
	}

	public boolean confirmation(ResvHdr resvHdr) {
		boolean status = reservationDao.confirmation(resvHdr);
		return status;
	}

	public String mailConfirmationSave(int resvNo) {
		String mailStatusCheck = "success";
		if (commonSettings.isMailNotifyConfirmation()) {
			EmailTemplate emailTmpl = null;
			ResvHdr resvHeader = null;
			String mailStatusMessage = "sent successfully";
			boolean mailStatus = true;
			String mailContent = null;
			try {
				emailTmpl = templateDao.getEmailTemplate(3);
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
			commDtl.setPurpose((byte) CommunicationPurposes.CONFIRMATION.getCode());
			commDtl.setSubject(emailTmpl.getSubject());
			commDtl.setEmailto(resvHeader.getResvByMail());
			commDtl.setContent(mailContent);
			commDtl.setStatus(mailStatus);
			commDtl.setPhone(resvHeader.getResvByPhone());
			commDtl.setDescription(mailStatusMessage);
			try {
				commonService.saveCommunicationDetails(commDtl);
			} catch (Exception e) {
				logger.error("Method : Save Email send status" + Throwables.getStackTraceAsString(e));
				e.printStackTrace();
			}
		}
		return mailStatusCheck;
	}

	public List<ResvDtl> getTotalPayable(int resrvId) {

		return reservationDao.getTotalPayable(resrvId);
	}

	public JsonObject getStatusRooms(Date arrivalDate, Date departDate, String roomType, int occupancy, String roomno) {

		return reservationDao.getStatusRooms(arrivalDate, departDate, roomType, occupancy, roomno);
	}

	public String UpdateNoshow(int resv_no, String[] slctdtlno, int selectstatus, ResvHdr resvhdr) {
		return reservationDao.UpdateNoshow(resv_no, slctdtlno, selectstatus, resvhdr);
	}

	public int updateCutOffDate(ResvHdr resvHdr) {
		// TODO Auto-generated method stub
		return reservationDao.updateCutOffDate(resvHdr);
	}

	@Override
	public int updatePickUpDetails(ResvHdr resvHdr) {
		return reservationDao.updatePickUpDetails(resvHdr);
	}
}
