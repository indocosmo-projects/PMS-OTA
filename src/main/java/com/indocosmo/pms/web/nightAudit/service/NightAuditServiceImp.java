package com.indocosmo.pms.web.nightAudit.service;

import java.util.List;

import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.CommunicationPurposes;
import com.indocosmo.pms.enumerator.CommunicationType;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.common.model.CommunicationDetails;
import com.indocosmo.pms.web.common.service.CommonService;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.nightAudit.dao.NightAuditDAO;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.templates.dao.TemplateDao;
import com.indocosmo.pms.web.templates.model.EmailTemplate;
import com.indocosmo.pms.web.transaction.model.Transaction;

@Service
public class NightAuditServiceImp implements NightAuditService {

	@Autowired NightAuditDAO nightAuditDAO;

	@Autowired TemplateDao templateDao;

	@Autowired JavaMailSender mailSender;

	@Autowired CommonService commonService;
	

	public static final Logger logger=LoggerFactory.getLogger(NightAuditServiceImp.class);

	@Transactional
	public JsonArray getArrivalList(){
		return nightAuditDAO.getArrivalList();
	}

	@Transactional
	public JsonArray getDepartList(){
		return nightAuditDAO.getDepartList();
	}

	@Transactional
	public JsonArray getInHouseList(String hotelDate){
		return nightAuditDAO.getInHouseList(hotelDate);
	}

	@Transactional
	public JsonObject getRoomRateForCurrentDate(String hotelDate,int checkInNo){
		return nightAuditDAO.getRoomRateForCurrentDate(hotelDate,checkInNo);
	}

	@Transactional
	public void setNightAuditStage(int stage){
		nightAuditDAO.setNightAuditStage(stage);
	}

	@Transactional
	public boolean changeHotelDate(String newHotDate){
		boolean isSave=nightAuditDAO.changeHotelDate(newHotDate);

		if(isSave){
			List<ResvHdr> resvHdr = nightAuditDAO.getCutOffDateRecord();
			for(ResvHdr resvHeader:resvHdr){
				if(commonSettings.isMailNotifyCutoffDate()){
					EmailTemplate emailTmpl=null;
					String mailStatusMessage="sent successfully";
					boolean mailStatus=true;
					String mailContent=null;
					try{											
						emailTmpl = templateDao.getEmailTemplate(2);
						MimeMessage message = mailSender.createMimeMessage();
						MimeMessageHelper helper = new MimeMessageHelper(message, true);
						helper.setTo(resvHeader.getResvByMail());
						helper.setSubject(emailTmpl.getSubject());
						mailContent=emailTmpl.getContent();
						mailContent=mailContent.replace("{resv_by_first_name}",resvHeader.getResvByFirstName());
						mailContent=mailContent.replace("{resv_by_last_name}",resvHeader.getResvByLastName());
						mailContent=mailContent.replace("{sum_num_rooms}",String.valueOf(resvHeader.getNumRooms()));
						mailContent=mailContent.replace("{resv_no}",String.valueOf(resvHeader.getResvNo()));
						mailContent=mailContent.replace("{min_arr_date}",resvHeader.getMinArrDate().toString());
						mailContent=mailContent.replace("{max_depart_date}",resvHeader.getMaxDepartDate().toString());
						mailContent=mailContent.replace("{num_nights}",String.valueOf(resvHeader.getNumNights()));
						mailContent=mailContent.replace("{num_adults}",String.valueOf(resvHeader.getNumAdults()));
						helper.setText(mailContent, true);
						mailSender.send(message);

					}catch(Exception e){
						logger.error("Method : Reservation confirm Email " + Throwables.getStackTraceAsString(e));		
						e.printStackTrace();
						mailStatusMessage=(e.getMessage().substring(0,e.getMessage().indexOf(";")));
						mailStatus=false;
					}
					CommunicationDetails commDtl=new CommunicationDetails();
					commDtl.setCommunicationType((byte) CommunicationType.EMAIL.getCode());
					commDtl.setResvNum(resvHeader.getResvNo());
					commDtl.setPurpose((byte) CommunicationPurposes.CUTOFFDATE.getCode());
					commDtl.setStatus(mailStatus);
					commDtl.setDescription(mailStatusMessage);
					commDtl.setContent(mailContent);
					commDtl.setEmailto(resvHeader.getResvByMail());
					commDtl.setSubject(emailTmpl.getSubject());
					commDtl.setPhone(resvHeader.getResvByPhone());
					
					
					try {
						commonService.saveCommunicationDetails(commDtl);
					} catch (Exception e) {
						logger.error("Method : Save Email send status" + Throwables.getStackTraceAsString(e));	
						e.printStackTrace();
					}
				}
			}
		}
		return  isSave;
	}

	@Transactional
	public String postNightAudit(Transaction txn){
		return nightAuditDAO.postNightAudit(txn);		
	}

	@Transactional
	public JsonArray getNightAuditTransactions(String hotelDate){
		return nightAuditDAO.getNightAuditTransactions(hotelDate);
	}

	@Transactional
	public JsonObject getReceptionCount(String hotelDate){
		return nightAuditDAO.getReceptionCount(hotelDate);
	}
	
	@Transactional
	public List<ResvHdr> getRecordToCancel(){
		return nightAuditDAO.getRecordToCancel();
	}
	@Transactional
	public boolean cancelNonNoShowRooms(int userid) throws Exception{
		return nightAuditDAO.cancelNonNoShowRooms(userid);
	}
	
	@Transactional
	public String confirmTransactions(String hotelDate,int userId){
		return nightAuditDAO.confirmTransactions(hotelDate, userId);
	}

	@Transactional
	public int getlastShiftDetail(String prevHotelDate) {
		return nightAuditDAO.getlastShiftDetail(prevHotelDate);
	}
     
	@Transactional
	public boolean extendStayOneNight(List<CheckInHdr> chkinHdrs)throws Exception {
		return nightAuditDAO.extendStayOneNight(chkinHdrs);
	}

}
