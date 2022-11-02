package com.indocosmo.pms.web.common.service;

import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.CommunicationGroups;
import com.indocosmo.pms.web.checkOut.dao.CheckOutDAO;
import com.indocosmo.pms.web.common.dao.CommonDAO;
import com.indocosmo.pms.web.common.model.CommunicationDetails;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.reception.service.ReceptionService;
import com.indocosmo.pms.web.reservation_test.service.ReservationServiceTest;
import com.indocosmo.pms.web.systemSettings.dao.SystemSettingsDao;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.templates.dao.TemplateDao;

@Service
public class CommonServiceImp implements CommonService {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private CheckOutDAO checkOutDAO;

	@Autowired
	TemplateDao templateDao;

	@Autowired
	private ReceptionService receptionService;

	@Autowired
	private ReservationServiceTest reservationService;

	@Autowired
	SystemSettingsDao systemSettingsDao;

	@Autowired
	private JavaMailSender mailSender;

	public static final Logger logger = LoggerFactory.getLogger(CommonServiceImp.class);

	@Transactional
	public Boolean getStatus(String table, String fieldName, int taxTypeId, String getFieldName) {
		// TODO Auto-generated method stub
		return commonDAO.getStatus(table, fieldName, taxTypeId, getFieldName);
	}

	@Transactional
	public int getCount(String tableName, String fieldName, int value, int id) {
		// TODO Auto-generated method stub
		return commonDAO.getCount(tableName, fieldName, value, id);
	}

	@Transactional
	public boolean saveCommunicationDetails(CommunicationDetails commDtls) throws Exception {
		return commonDAO.saveCommunicationDetails(commDtls);
	}

	@Transactional
	public boolean saveCommunicationDetailsMail(CommunicationDetails commDtlMail) throws Exception {

		Properties prop = new Properties();
		InputStream input = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		SystemSettings systemSettings = systemSettingsDao.getSystemSettings();
		String mailContent = null;
		boolean mailStatus = true;

		try {
			input = loader.getResourceAsStream("/sms.properties");
			prop.load(input);

			final String username = systemSettings.getSmtpSUserId();
			final String password = systemSettings.getSmtpPassword();

			Properties properties = System.getProperties();
			properties.put("mail.smtp.host", systemSettings.getSmtpServer());
			properties.put("mail.transport.protocol.", "smtp");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.", "true");
			properties.put("mail.smtp.port", systemSettings.getSmtpPort());
			properties.put("mail.debug", "true");
			properties.put("mail.smtp.socketFactory.port", systemSettings.getSmtpPort());
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.ssl.trust", systemSettings.getSmtpServer());

			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			MimeMessage message = new MimeMessage(session);

			// header field of the header.
			message.setFrom(new InternetAddress(systemSettings.getSmtpSUserId(), "Niko Hotels"));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(commDtlMail.getEmailto()));
			if (prop.getProperty("EMAILCC") != null) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(commDtlMail.getEmailcc()));
			}
			message.setReplyTo(
					new javax.mail.Address[] { new javax.mail.internet.InternetAddress("no-reply@niko-inn.com") });
			message.setSubject("Niko Hotels");
			mailContent = commDtlMail.getContent();

			message.setContent(mailContent, "text/html");

			// Send message
			Transport.send(message);

		} catch (Exception e) {
			logger.error("Method : Checkout Email " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			mailStatus = false;
		}

		commonDAO.saveCommunicationDetailsMail(commDtlMail);

		return mailStatus;
	}

	public JsonArray getCommunicationGuestDetails(JsonObject jObj) throws Exception {
		JsonArray jsonArray = new JsonArray();
		int group = jObj.get("group").getAsInt();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
		simpleDateformat.setTimeZone(TimeZone.getTimeZone("IST"));
		String wherePart = "";
		try {
			if (group == CommunicationGroups.INHOUSE.getCode()) {
				jsonArray = receptionService.getInHouseList();
			} else if (group == CommunicationGroups.CHECKOUT.getCode()) {
				String typeOfSearch = jObj.get("checkOut").getAsString();
				if (typeOfSearch.equals("on")) {
					Date d = new Date(Long.parseLong(jObj.get("checkOutOn").getAsString()));
					String chOut = simpleDateformat.format(d);
					wherePart = "ch.`status`=7  and ch.act_depart_date= '" + chOut + "'";
				} else {
					Date dbfr1 = new Date(Long.parseLong(jObj.get("checkOutBefore1").getAsString()));
					String chOutBfr1 = simpleDateformat.format(dbfr1);
					Date dbfr2 = new Date(Long.parseLong(jObj.get("checkOutBefore2").getAsString()));
					String chOutBfr2 = simpleDateformat.format(dbfr2);
					wherePart = "ch.`status`=7 and ch.act_depart_date between '" + chOutBfr1 + "' and '" + chOutBfr2
							+ "'";
				}
				jsonArray = checkOutDAO.getCheckInCheckOutData(wherePart);

			} else {

				String typeOfSearch = jObj.get("resvtn").getAsString();
				if (typeOfSearch.equals("cutoff")) {
					int cuoff = jObj.get("cutoffBefore").getAsInt();
					wherePart = " vr.cut_off_date between '" + simpleDateformat.format(commonSettings.getHotelDate())
							+ "' and DATE_ADD('" + simpleDateformat.format(commonSettings.getHotelDate())
							+ "',INTERVAL " + cuoff + " DAY)";
				} else {
					Date d = new Date(Long.parseLong(jObj.get("resvArrvlOn").getAsString()));
					String arrOn = simpleDateformat.format(d);
					wherePart = "vr.arr_date= '" + arrOn + "'";
				}
				jsonArray = reservationService.getReservationData(wherePart);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		return jsonArray;
	}

	public JsonObject getCheckInguestData(int checkInNo) throws Exception {
		String wherePart = "ch.checkin_no=" + checkInNo;
		JsonArray jArray = checkOutDAO.getCheckInCheckOutData(wherePart);
		JsonObject jObject = (JsonObject) jArray.get(0);
		return jObject;
	}

	public JsonObject getReservationGuestData(int resvNo) throws Exception {
		String wherePart = "vr.resv_no=" + resvNo;
		JsonArray jArray = reservationService.getReservationData(wherePart);
		JsonObject jObject = (JsonObject) jArray.get(0);
		return jObject;
	}

	@Transactional
	public List<CommunicationDetails> getCommunicationData(int id, String group) throws Exception {

		return commonDAO.getCommunicationData(id, group);
	}

	public JsonArray getStateList(String nationality) throws Exception {
		return commonDAO.getStateList(nationality);
	}
}