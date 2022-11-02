package com.indocosmo.pms.web.checkinRequest.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.TxnSource;
import com.indocosmo.pms.web.accountMaster.model.AccountMaster;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckInRequestStatus;
import com.indocosmo.pms.web.checkinRequest.dao.CheckinRequestDAO;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.tax.model.TaxHdr;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.indocosmo.pms.web.transaction.service.TxnService;

@Service
public class CheckinRequestServiceImpl implements CheckinRequestService {

	@Autowired
	CheckinRequestDAO checkinRequestDAO;

	@Autowired
	TxnService transactionService;

	@Transactional
	public JsonArray getAddOnDetails(int id, int checkin_no) throws Exception {
		return checkinRequestDAO.getAddOnDetails(id, checkin_no);
	}

	@Transactional
	public JsonArray getListRoomRequest() throws Exception {
		return checkinRequestDAO.getListRoomRequest();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String processAddon(CheckInRequestStatus reqStatus) throws Exception {
		String status = "success";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			JsonArray jsonArray = checkinRequestDAO.getAddOnDetails(reqStatus.getCheckInReequestId(), 0);
			JsonObject jobject = new JsonObject();
			if (jsonArray.size() != 0) {
				jobject = jsonArray.get(0).getAsJsonObject();
				if (jobject.get("is_req_completed").getAsBoolean()) {
					status = "Addon Already completed";
				}
				if (!jobject.get("process_date").getAsString().equals("")) {
					Date prdate = formatter.parse(jobject.get("process_date").getAsString());
					if (prdate == commonSettings.getHotelDate()) {
						status = "Addon Already processed";
					}
				}
			}
			if (status.equals("success")) {
				status = "error";
				boolean isSave = checkinRequestDAO.processAddon(reqStatus);
				if (isSave) {
					CheckInRequest chreq = checkinRequestDAO.getCheckInRequest(reqStatus.getCheckInReequestId());
					jsonArray = checkinRequestDAO.getAddOnDetails(reqStatus.getCheckInReequestId(), 0);
					jobject = jsonArray.get(0).getAsJsonObject();
					if (jobject.get("facility_is_payable").getAsBoolean()) {

						java.sql.Date hotelDate = new java.sql.Date(commonSettings.getHotelDate().getTime());
						Transaction txn = null;
						AccountMaster accm = transactionService.accMstId(jobject.get("acc_mst_id").getAsInt());
						txn = transactionService.getCharges(hotelDate, jobject.get("facility_amount").getAsDouble(),
								jobject.get("acc_mst_id").getAsInt(), !accm.isIs_tax_included(), 0);
						txn.setAmount(jobject.get("facility_amount").getAsDouble());
						txn.setNett_amount(-(txn.getNett_amount()));
						txn.setAcc_mst_id(jobject.get("acc_mst_id").getAsInt());
						txn.setAcc_mst_code(accm.getCode());
						txn.setTxn_source(TxnSource.FRONTOFFICEPOSTING.getCode());
						txn.setFolio_no(jobject.get("checkin_no").getAsInt());
						txn.setFolio_bind_no(jobject.get("folio_bind_no").getAsInt());
						Calendar cal = Calendar.getInstance();
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						String payTime = dateFormat.format(cal.getTime());
						txn.setTxn_date(hotelDate.toString());
						txn.setTxn_time(payTime);
						TaxHdr taxHdr = transactionService.getTaxCode(accm.getTax_id());
						txn.setTax_id(accm.getTax_id());
						txn.setTax_code(taxHdr.getCode());
						txn.setInclude_tax(accm.isIs_tax_included());
						txn.setTxn_status(1);
						txn.setRemarks("addon posting");
						txn.setUser_id(reqStatus.getUserId());
						status = transactionService.save(txn);
					}
					if (status.equals("success")) {
						if (jobject.get("chreq_is_one_time_request").getAsBoolean()) {
							chreq.setReqCompleted(true);
							chreq.setIsDeleted(false);
							List<CheckInRequest> chreqList = new ArrayList<CheckInRequest>();
							chreqList.add(chreq);
							isSave = checkinRequestDAO.saveAddOns(chreqList);
						}
					}
				}
				if (isSave)
					status = "success";
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return status;
	}

	@Transactional
	public List<CheckInRequest> getCheckInRequestList(int checkno, String source) {
		return checkinRequestDAO.getCheckInRequestList(checkno, source);
	}

	public JsonArray getRoomList() throws Exception {
		return checkinRequestDAO.getRoomList();
	}

	@Transactional
	public List<CheckInRequestStatus> getCheckInRequestSatusList(int requestId) throws Exception {
		return checkinRequestDAO.getCheckInRequestStatusList(requestId);
	}

	@Transactional
	public CheckInRequest getCheckInRequest(int id) {
		return checkinRequestDAO.getCheckInRequest(id);
	}

	@Transactional
	public boolean saveAddOns(List<CheckInRequest> requestList) throws Exception {
		return checkinRequestDAO.saveAddOns(requestList);
	}

	@Transactional
	public boolean saveCheckinRequestStatus(CheckInRequestStatus checkinreqStatus) throws Exception {
		return checkinRequestDAO.processAddon(checkinreqStatus);
	}

	@Transactional
	public boolean deleteAddOnStatus(int id) throws Exception {
		return checkinRequestDAO.deleteAddOnStatus(id);
	}

	@Transactional
	public boolean cancelAddOns(int id) throws Exception {
		return checkinRequestDAO.cancelAddOns(id);
	}
}
