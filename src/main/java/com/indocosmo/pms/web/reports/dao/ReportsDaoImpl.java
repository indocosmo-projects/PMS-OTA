package com.indocosmo.pms.web.reports.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.PaymentMode;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.agingAR.model.AgingAR;
import com.indocosmo.pms.web.corporate.model.Corporate;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.pettycash.model.PettyCash;
import com.indocosmo.pms.web.reports.model.CustomerGrading;
import com.indocosmo.pms.web.reports.model.CustomerReport;
import com.indocosmo.pms.web.reports.model.DayEndRport;
import com.indocosmo.pms.web.reports.model.GeneralReport;
import com.indocosmo.pms.web.reports.model.IncomeReport;
import com.indocosmo.pms.web.reports.model.NationalityReport;
import com.indocosmo.pms.web.reports.model.ReceptionReport;
import com.indocosmo.pms.web.reports.model.RoomBookingReport;
import com.indocosmo.pms.web.reports.model.TemplateReport;
import com.indocosmo.pms.web.reservation.controller.ReservationController;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.indocosmo.pms.web.transaction.model.Transaction;

@Repository
public class ReportsDaoImpl implements ReportsDao {

	@Autowired
	SystemSettingsService systemSettingsService;

	DbConnection dbConnection = null;

	public ReportsDaoImpl() {
		dbConnection = new DbConnection();
	}

	public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	public static final int EXPECTED_ARRIVAL = 0;
	public static final int ACTUAL_ARRIVAL = 1;
	public static final int INHOUSE = 2;
	public static final int EXPECTED_DEPATURE = 3;
	public static final int ACTUAL_DEPATURE = 4;

	public List<ReceptionReport> getFolioBalanceData(String wherePart) throws Exception {
		String sql = "select checkin_hdr.checkin_no,checkin_hdr.room_number,checkin_hdr.arr_date,checkin_hdr.exp_depart_date,checkin_hdr.act_depart_date,checkin_dtl.first_name,checkin_dtl.last_name,checkin_dtl.phone,v_list_folio_balance.folio_balance from checkin_hdr inner join checkin_dtl on checkin_hdr.checkin_no = checkin_dtl.checkin_no inner join folio on checkin_hdr.checkin_no = folio.checkin_no left join v_list_folio_balance on v_list_folio_balance.folio_no = folio.folio_no where  "
				+ wherePart;
		Statement statement = null;
		List<ReceptionReport> receptionList = new ArrayList<ReceptionReport>();
		ReceptionReport receptionReport = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
	    connection = dbConnection.getConnection();
		statement = connection.createStatement();
		rs = statement.executeQuery(sql);
		double fbalance = 0.0;
		while (rs.next()) {
			receptionReport = new ReceptionReport();
			receptionReport.setCheckinNo(rs.getInt("checkin_no"));
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			String dateFormat = systemSettings.getDateFormat();

			receptionReport.setDateFormat(String.valueOf(dateFormat));
			receptionReport.setCheckinDate(rs.getDate("arr_date"));
			receptionReport.setRoomNumber(rs.getString("room_number"));
			receptionReport.setExpectedCheckoutDate(rs.getDate("exp_depart_date"));
			receptionReport.setActualCheckoutDate(rs.getDate("act_depart_date"));
			receptionReport.setFirstName(rs.getString("first_name"));
			receptionReport.setLastName(rs.getString("last_name"));
			receptionReport.setPhone(rs.getString("phone"));
			fbalance = rs.getDouble("folio_balance");
			receptionReport.setFolioBalance(String.valueOf(fbalance));
			receptionList.add(receptionReport);

		}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getFolioBalanceData, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
		}
		
		return receptionList;
	}

	public List<ResvHdr> getReservationCancelReportData(String wherePart) throws Exception {
		String sql = "select resv_hdr.resv_no,resv_hdr.resv_date,resv_hdr.min_arr_date,resv_hdr.max_depart_date,resv_hdr.resv_by_first_name,resv_hdr.resv_by_last_name,resv_hdr.resv_for,resv_hdr.resv_by_phone,resv_hdr.sum_num_rooms,resv_hdr.`status` from resv_hdr where  "
				+ wherePart;
		Statement statement = null;
		List<ResvHdr> resvHdrList = new ArrayList<ResvHdr>();
		ResvHdr resvHdr = null;
		ResultSet rs = null;
		Connection connection = null ;
		try {
	    connection = dbConnection.getConnection();
		statement = connection.createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			resvHdr = new ResvHdr();
			resvHdr.setResvNo(rs.getInt("resv_no"));
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			String dateFormat = systemSettings.getDateFormat();

			resvHdr.setDateFormat(String.valueOf(dateFormat));
			resvHdr.setResvDate(rs.getDate("resv_date"));
			resvHdr.setMinArrDate(rs.getDate("min_arr_date"));
			resvHdr.setMaxDepartDate(rs.getDate("max_depart_date"));
			resvHdr.setResvByFirstName(rs.getString("resv_by_first_name"));
			resvHdr.setResvByLastName(rs.getString("resv_by_last_name"));
			resvHdr.setResvFor(rs.getString("resv_for"));
			resvHdr.setResvByPhone(rs.getString("resv_by_phone"));
			resvHdr.setNumRooms(rs.getShort("sum_num_rooms"));
			resvHdr.setStatus(rs.getByte("status"));
			resvHdrList.add(resvHdr);
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getReservationCancelReportData, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
		}
		return resvHdrList;
	}

	public List<ResvHdr> getReservationListData(String wherePart) throws Exception {
		String sql = "SELECT resv_hdr.resv_no,resv_hdr.folio_bind_no,resv_hdr.resv_date,resv_hdr.min_arr_date,resv_hdr.max_depart_date,resv_hdr.resv_by_first_name,resv_hdr.resv_by_last_name,resv_hdr.resv_for,resv_hdr.resv_by_phone,resv_hdr.sum_num_rooms,resv_hdr.`status`,v_list_folio_balance.folio_balance,resv_hdr.folio_bind_no,v_list_folio_balance.folio_balance,resv_dtl.num_nights,resv_dtl.num_rooms FROM resv_hdr INNER JOIN folio ON resv_hdr.resv_no = folio.resv_no LEFT JOIN v_list_folio_balance ON v_list_folio_balance.folio_no = folio.folio_no INNER JOIN resv_dtl ON resv_hdr.resv_no = resv_dtl.resv_no where "
				+ wherePart + "ORDER BY resv_no ASC";
		Statement statement = null;
		List<ResvHdr> resvHdrList = new ArrayList<ResvHdr>();
		ResvHdr resvHdr = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
		connection = dbConnection.getConnection();
		statement = connection.createStatement();
		rs = statement.executeQuery(sql);
		double fbalance = 0.0;
		while (rs.next()) {
			resvHdr = new ResvHdr();
			resvHdr.setResvNo(rs.getInt("resv_no"));
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			String dateFormat = systemSettings.getDateFormat();
			resvHdr.setDateFormat(String.valueOf(dateFormat));
			resvHdr.setResvDate(rs.getDate("resv_date"));
			resvHdr.setMinArrDate(rs.getDate("min_arr_date"));
			resvHdr.setMaxDepartDate(rs.getDate("max_depart_date"));
			resvHdr.setResvByFirstName(rs.getString("resv_by_first_name"));
			resvHdr.setResvByLastName(rs.getString("resv_by_last_name"));
			resvHdr.setResvFor(rs.getString("resv_for"));
			resvHdr.setResvByPhone(rs.getString("resv_by_phone"));
			resvHdr.setNumRooms(rs.getShort("sum_num_rooms"));
			resvHdr.setStatus(rs.getByte("status"));
			fbalance = rs.getDouble("folio_balance");
			resvHdr.setFolioBalance(String.valueOf(fbalance));
			resvHdr.setNumNights(rs.getInt("num_nights"));
			resvHdr.setNumRooms(rs.getByte("num_rooms"));

			resvHdrList.add(resvHdr);
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getReservationListData, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
		}
		
		return resvHdrList;
	}

	public List<ReceptionReport> getRequestData(String wherePart) throws Exception {
		String sql = "SELECT checkin_request.checkin_no,checkin_request.is_one_time_request, DATE_FORMAT(checkin_request.req_time,'%d-%m-%Y') AS datereq,TIME_FORMAT(checkin_request.req_time, '%H:%i:%s') AS timereq,checkin_request.req_date,checkin_request.req_time,checkin_request.facility_id,checkin_request.id,checkin_request.resv_room_no,checkin_request.arrangement_by,checkin_request.req_taken_by,checkin_request.req_taken_date,checkin_request.req_remarks,checkin_request.is_req_completed,checkin_request.is_canceled,checkin_request.user_id,checkin_request.last_updated_ts,facilities.`name`,checkin_hdr.room_number,checkin_hdr.room_type_code,checkin_hdr.num_nights,checkin_hdr.arr_date,checkin_hdr.act_depart_date,checkin_hdr.act_depart_time,checkin_hdr.arr_time,checkin_hdr.num_adults,checkin_hdr.num_children,checkin_hdr.num_infants,checkin_hdr.resv_no,checkin_dtl.phone,checkin_dtl.first_name,checkin_dtl.last_name,checkin_dtl.address,checkin_dtl.email,checkin_hdr.exp_depart_date,checkin_hdr.exp_depart_time FROM checkin_request INNER JOIN checkin_hdr ON checkin_request.checkin_no = checkin_hdr.checkin_no INNER JOIN facilities ON facilities.id = checkin_request.facility_id INNER JOIN checkin_dtl ON checkin_hdr.checkin_no = checkin_dtl.checkin_no where "
				+ wherePart + " ORDER BY req_time ASC";

		Statement statement = null;
		List<ReceptionReport> requestList = new ArrayList<ReceptionReport>();
		ReceptionReport receptionReport = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
		connection = dbConnection.getConnection();
		statement = connection.createStatement();
		rs = statement.executeQuery(sql);
		//double fbalance = 0.0;
		while (rs.next()) {
			receptionReport = new ReceptionReport();
			receptionReport.setReqTime(rs.getString("req_time"));
			receptionReport.setOneTimeReq(rs.getBoolean("is_one_time_request"));
			receptionReport.setReqDate(rs.getDate("req_date"));
			receptionReport.setFacilityId(rs.getInt("facility_id"));
			receptionReport.setName(rs.getString("name"));
			receptionReport.setRoomTypeCode(rs.getString("room_type_code"));
			receptionReport.setNumNights(rs.getInt("num_nights"));
			receptionReport.setArrDate(rs.getDate("arr_date"));
			receptionReport.setActualCheckoutDate(rs.getDate("act_depart_date"));
			receptionReport.setRoomNumber(rs.getString("room_number"));
			receptionReport.setFirstName(rs.getString("first_name"));
			receptionReport.setLastName(rs.getString("last_name"));
			receptionReport.setPhone(rs.getString("phone"));
			requestList.add(receptionReport);

		}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getReservationListData, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
		}
		return requestList;
	}

	// digna
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pms.web.reports.dao.ReportsDao#getReservationReport(java.lang.
	 * String)
	 */
	@Override
	public List<GeneralReport> getGeneralReportList(Date fromDate, Date toDate, int type) {
		List<GeneralReport> generalReportList = new ArrayList<GeneralReport>();
		GeneralReport generalReport = new GeneralReport();
		Connection con = null;
		CallableStatement st = null;
		ResultSet rs = null;
		String storedProcedure = null;
		switch (type) {
		case EXPECTED_ARRIVAL:
			storedProcedure = "{call getReservationReport(?,?)}";
			break;
		case ACTUAL_ARRIVAL:
			storedProcedure = "{call getArrivalReport(?,?)}";
			break;
		case INHOUSE:
			storedProcedure = "{call getInHouseReport(?,?)}";
			break;
		case EXPECTED_DEPATURE:
			storedProcedure = "{call getExpectedDepatureReport(?,?)}";
			break;
		case ACTUAL_DEPATURE:
			storedProcedure = "{call getDepatureReport(?,?)}";
			break;

		}

		try {
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			String dateFormat = systemSettings.getDateFormat();
			DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);
			con = dbConnection.getConnection();
			st = con.prepareCall(storedProcedure);
			st.setDate(1, fromDate);
			st.setDate(2, toDate);
			st.execute();
			rs = st.getResultSet();
			GeneralReport.setType(type);
			while (rs.next()) {
				generalReport = new GeneralReport();
				generalReport.setReport_no(rs.getInt("report_no"));
				generalReport.setRoom_no(rs.getString("room_no"));
				generalReport.setRoom_type(rs.getString("room_type"));
				generalReport.setCheckin_date(simpleDateFormatHotelDate1.format(rs.getDate("checkin_date")));
				if (null != rs.getDate("checkout_date")) {
					generalReport.setCheckout_date(simpleDateFormatHotelDate1.format(rs.getDate("checkout_date")));
				}
				if (null != rs.getDate("exp_checkout_date")) {
					generalReport
							.setExpCheckout_date(simpleDateFormatHotelDate1.format(rs.getDate("exp_checkout_date")));
				}
				generalReport.setGust_name(rs.getString("gust_name"));
				generalReport.setPax_resv(rs.getString("pax_resv"));
				generalReport.setTarif(rs.getString("tarif"));
				generalReport.setMeal_plan(rs.getString("meal_plan"));
				generalReport.setDeposit(rs.getString("deposit"));
				generalReportList.add(generalReport);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(rs);
		}
		return generalReportList;
	}
	// digna

	public String getCashRegistersClosureReportList(Date fromDate, Date toDate, int cashRegistersClosure)
			throws Exception {
		Connection con = dbConnection.getConnection();
		CallableStatement preparedStatement = null;
	//	ResultSet resultSet = null;
		String xmlString = "";
	//	List<Transaction> transactnList = new ArrayList<Transaction>();
	//	Transaction txn = new Transaction();
		String procedure = "{CALL getCashRegistersClosureReport(?,?,?)}";
		try {
			preparedStatement = con.prepareCall(procedure);
			preparedStatement.setDate(1, fromDate);
			preparedStatement.setDate(2, toDate);
			preparedStatement.registerOutParameter(3, java.sql.Types.VARCHAR);

			preparedStatement.executeUpdate();
			xmlString = preparedStatement.getString(3);
			// DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// DocumentBuilder builder;
			// try {
			// builder = factory.newDocumentBuilder();
			// Document document = builder.parse(new InputSource(new
			// StringReader(xmlString)));
			// //System.out.println("Root element :" +
			// document.getDocumentElement().getNodeName());
			// System.out.println("Root element :" +
			// document.getDocumentElement().getChildNodes());
			//
			// } catch (Exception e) {
			// e.printStackTrace();
			// }

		} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(preparedStatement);
			dbConnection.releaseResource(con);
		}

		return xmlString;
	}

	public List<Transaction> getShiftWiseTxnTransferData(String wherePart) throws Exception {
		String sql = "select txn.txn_no,txn.shift_id,shift.`code` AS shiftcode,txn.user_id,users.login_id AS username,txn.txn_date,txn.txn_status,txn.acc_mst_code,txn.tax_code,txn.base_amount,(txn.tax1_amount +txn.tax2_amount+txn.tax3_amount +txn.tax4_amount) as tax,txn.service_charge,txn.nett_amount,txn.remarks,checkin_hdr.room_number as source_room,checkin_dtl.first_name As source_name, tbl.destination_room,tbl.destination_name, tbltrfr.txn_date as transfered_date from txn inner join folio on folio.folio_no = txn.folio_no INNER JOIN shift ON txn.shift_id=shift.id INNER JOIN users ON txn.user_id =users.id left join checkin_hdr on checkin_hdr.checkin_no = folio.checkin_no inner join checkin_dtl on checkin_hdr.checkin_no = checkin_dtl.checkin_no left join (select folio.folio_no as folio_no,checkin_hdr.room_number as destination_room, checkin_dtl.first_name as destination_name from checkin_hdr inner join folio on folio.checkin_no = checkin_hdr.checkin_no inner join checkin_dtl on checkin_hdr.checkin_no = checkin_dtl.checkin_no) tbl on txn.dest_folio_no = tbl.folio_no left join (select txn.txn_date,txn.source_txn_no from txn) tbltrfr on txn.txn_no = tbltrfr.source_txn_no where "
				+ wherePart;
		Statement statement = null;
		List<Transaction> transactionTransfrList = new ArrayList<Transaction>();
		Transaction txn = null;
		ResultSet rs = null;
		Connection con = null;
		try {
		con = new DbConnection().getConnection();
		statement = con.createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			txn = new Transaction();
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			String dateFormat = systemSettings.getDateFormat();

			txn.setDateFormat(String.valueOf(dateFormat));
			txn.setTxn_source(rs.getInt("txn_no"));
			txn.setTxn_date(rs.getString("txn_date"));
			txn.setShiftCode(rs.getString("shiftcode"));
			txn.setUsername(rs.getString("username"));
			txn.setAcc_mst_code(rs.getString("acc_mst_code"));
			txn.setTax_code(rs.getString("tax_code"));
			txn.setTax1_pc(rs.getDouble("tax"));
			txn.setBase_amount(rs.getDouble("base_amount"));
			txn.setServiceCharge(rs.getBigDecimal("service_charge"));
			txn.setNett_amount(rs.getDouble("nett_amount"));
			txn.setRemarks(rs.getString("remarks"));
			txn.setTax(rs.getDouble("tax"));
			txn.setTxn_status(rs.getShort("txn_status"));
			txn.setSourceRoom(rs.getString("source_room"));
			txn.setSourceName(rs.getString("source_name"));

			if (txn.getTxn_status() == 2) {
				txn.setDestinationName(rs.getString("destination_name"));
				txn.setDestinationRoom(rs.getString("destination_room"));
				txn.setTransferedDate(rs.getString("transfered_date"));
			}
			transactionTransfrList.add(txn);
		}
		} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);
		}
		return transactionTransfrList;
	}

	public List<Transaction> getTransactionReportData(String wherePart) throws Exception {
		String sql = "SELECT txn.txn_no,txn.txn_date,txn.include_tax,txn.txn_status,txn.acc_mst_code,txn.tax_code,txn.base_amount,(txn.tax1_amount +txn.tax2_amount+txn.tax3_amount +txn.tax4_amount) AS tax,txn.service_charge,txn.nett_amount,txn.remarks,checkin_hdr.room_number AS source_room,tbl.destination_room,tbltrfr.txn_date AS transfered_date,shift.`code`,users.login_id FROM txn INNER JOIN shift ON shift.id = txn.shift_id INNER JOIN folio ON folio.folio_no = txn.folio_no LEFT JOIN checkin_hdr ON checkin_hdr.checkin_no = folio.checkin_no LEFT JOIN (select folio.folio_no as folio_no,checkin_hdr.room_number as destination_room from checkin_hdr inner join folio on folio.checkin_no = checkin_hdr.checkin_no) AS tbl ON txn.dest_folio_no = tbl.folio_no LEFT JOIN (select txn.txn_date,txn.source_txn_no from txn ) AS tbltrfr ON txn.txn_no = tbltrfr.source_txn_no INNER JOIN users ON users.id = txn.user_id where "
				+ wherePart;

		Statement statement = null;
		List<Transaction> transactionList = new ArrayList<Transaction>();
		Transaction txn = null;
		ResultSet rs = null;
		Connection con = null;
		try {
		con = new DbConnection().getConnection();
		statement = con.createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			txn = new Transaction();
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			String dateFormat = systemSettings.getDateFormat();

			txn.setDateFormat(String.valueOf(dateFormat));
			txn.setTxn_source(rs.getInt("txn_no"));
			txn.setTxn_date(rs.getString("txn_date"));
			txn.setAcc_mst_code(rs.getString("acc_mst_code"));
			txn.setTax_code(rs.getString("tax_code"));
			txn.setTax1_pc(rs.getDouble("tax"));
			txn.setBase_amount(rs.getDouble("base_amount"));
			txn.setServiceCharge(rs.getBigDecimal("service_charge"));
			txn.setNett_amount(rs.getDouble("nett_amount"));
			txn.setRemarks(rs.getString("remarks"));
			txn.setTax(rs.getDouble("tax"));
			txn.setInclude_tax(rs.getBoolean("include_tax"));
			txn.setTxn_status(rs.getShort("txn_status"));
			txn.setSourceRoom(rs.getString("source_room"));
			txn.setShiftCode(rs.getString("code"));
			txn.setUsername(rs.getString("login_id"));
			if (txn.getTxn_status() == 2) {
				txn.setDestinationRoom(rs.getString("destination_room"));
				txn.setTransferedDate(rs.getString("transfered_date"));
			}
			transactionList.add(txn);
		}
		} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);
		}
		return transactionList;
	}

	public List<ShiftManagement> getShiftManagementReportData(String wherePart) throws Exception {
		String sql = "select ush.*,us.`login_id` as loginusers,sht.`code` AS CodeShift from user_shift  ush INNER JOIN users us ON ush.user_id = us.id INNER JOIN shift sht ON ush.shift_id=sht.id WHERE "
				+ wherePart;
		Statement statement = null;
		List<ShiftManagement> shiftManagementList = new ArrayList<ShiftManagement>();
		ShiftManagement shft = null;
		ResultSet rs = null;
		Connection con = null;
		try {
		con = new DbConnection().getConnection();
		statement = con.createStatement();
		rs = statement.executeQuery(sql);

		while (rs.next()) {
			shft = new ShiftManagement();
			shft.setLoginusers(rs.getString("loginusers"));
			shft.setCodeShift(rs.getString("CodeShift"));

			shft.setOpeningFloat(rs.getBigDecimal("opening_float"));
			shft.setOpeningDate(rs.getString("opening_date"));
			shft.setOpeningTime(rs.getString("opening_time"));
			shft.setClosingDate(rs.getString("closing_date"));
			shft.setClosingTime(rs.getString("closing_time"));

			shiftManagementList.add(shft);

		}
		} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);
		}

		return shiftManagementList;

	}

	public List<Transaction> getShiftWiseTransactionReportData(String wherePart) throws Exception {
		String sql = "select txn.txn_no,txn.shift_id,shift.code AS ShiftCode,txn.user_id,users.login_id AS username,txn.txn_date,txn.txn_status,txn.acc_mst_code,txn.tax_code,txn.base_amount,(txn.tax1_amount +txn.tax2_amount+txn.tax3_amount +txn.tax4_amount) as tax,txn.service_charge,txn.nett_amount,txn.remarks,checkin_hdr.room_number as source_room,tbl.destination_room,tbltrfr.txn_date as transfered_date from txn inner join folio on folio.folio_no = txn.folio_no INNER JOIN users ON txn.user_id =users.id INNER JOIN shift ON txn.shift_id=shift.id left join checkin_hdr on checkin_hdr.checkin_no = folio.checkin_no left join (select folio.folio_no as folio_no,checkin_hdr.room_number as destination_room from checkin_hdr inner join folio on folio.checkin_no = checkin_hdr.checkin_no) tbl on txn.dest_folio_no = tbl.folio_no left join (select txn.txn_date,txn.source_txn_no from txn ) tbltrfr on txn.txn_no = tbltrfr.source_txn_no  where "
				+ wherePart;
		Statement statement = null;
		List<Transaction> shiftWiseTransactionList = new ArrayList<Transaction>();
		Transaction txn = null;
		ResultSet rs = null;
		Connection con = null;
		try {
		con = new DbConnection().getConnection();
		statement = con.createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			txn = new Transaction();
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			String dateFormat = systemSettings.getDateFormat();

			txn.setDateFormat(String.valueOf(dateFormat));
			txn.setTxn_source(rs.getInt("txn_no"));
			txn.setTxn_date(rs.getString("txn_date"));
			txn.setShift_id(rs.getInt("shift_id"));
			txn.setAcc_mst_code(rs.getString("acc_mst_code"));
			txn.setShiftCode(rs.getString("ShiftCode"));
			txn.setUsername(rs.getString("username"));
			txn.setTax_code(rs.getString("tax_code"));
			txn.setTax1_pc(rs.getDouble("tax"));
			txn.setBase_amount(rs.getDouble("base_amount"));
			txn.setServiceCharge(rs.getBigDecimal("service_charge"));
			txn.setNett_amount(rs.getDouble("nett_amount"));
			txn.setRemarks(rs.getString("remarks"));
			txn.setTax(rs.getDouble("tax"));
			txn.setTxn_status(rs.getShort("txn_status"));
			txn.setSourceRoom(rs.getString("source_room"));

			if (txn.getTxn_status() == 2) {
				txn.setDestinationRoom(rs.getString("destination_room"));
				txn.setTransferedDate(rs.getString("transfered_date"));
			}
			shiftWiseTransactionList.add(txn);
		}
		} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);
		}
		return shiftWiseTransactionList;

	}

	@Override
	public List<ReceptionReport> getOccupancyDetails(Date fromDate, Date toDate) throws Exception {
		String sql = "SELECT tbl1.room_number,tbl1.room_type_code,tbl1.first_name,tbl1.last_name,tbl1.nationality,"
				+ "tbl2.room_charge,tbl1.arr_date,tbl1.act_depart_date" + " FROM"
				+ " (SELECT checkin_hdr.checkin_no,checkin_hdr.room_number,checkin_dtl.first_name,checkin_dtl.last_name,"
				+ "checkin_dtl.nationality,checkin_hdr.room_type_code,folio.folio_no,checkin_hdr.arr_date,checkin_hdr.act_depart_date"
				+ " FROM checkin_dtl INNER JOIN checkin_hdr ON checkin_dtl.checkin_no = checkin_hdr.checkin_no"
				+ " INNER JOIN folio ON folio.checkin_no = checkin_hdr.checkin_no"
				+ " WHERE(checkin_hdr.arr_date BETWEEN'" + fromDate + "' AND'" + toDate + "') "
				+ "or (checkin_hdr.act_depart_date BETWEEN'" + fromDate + "' AND'" + toDate + "') "
				+ "or(checkin_hdr.arr_date<= '" + fromDate + "' and checkin_hdr.act_depart_date is null) "
				+ "or(checkin_hdr.arr_date<= '" + fromDate + "' and checkin_hdr.act_depart_date >= '" + toDate + "' ) "
				+ " and checkin_dtl.is_sharer = 0 ) tbl1" + " INNER JOIN ("
				+ " SELECT SUM(nett_amount) AS room_charge,folio_no FROM txn WHERE txn_date <= '" + toDate
				+ "' AND acc_mst_id NOT IN (5, 8, 4) GROUP BY folio_no) tbl2 " + " ON tbl1.folio_no = tbl2.folio_no;";
		PreparedStatement getOccupancy = null;
		ReceptionReport receptionReport = null;
		List<ReceptionReport> occupancyList = new ArrayList<ReceptionReport>();
		ResultSet rs = null;
		Connection connection = null;
		try {
		connection = dbConnection.getConnection();
		getOccupancy = connection.prepareStatement(sql);
		rs = getOccupancy.executeQuery();
		while (rs.next()) {
			receptionReport = new ReceptionReport();
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			String dateFormat = systemSettings.getDateFormat();
			receptionReport.setDateFormat(String.valueOf(dateFormat));
			receptionReport.setRoomNumber(rs.getString("room_number"));
			receptionReport.setRoomTypeCode(rs.getString("room_type_code"));
			receptionReport.setFirstName(rs.getString("first_name"));
			receptionReport.setLastName(rs.getString("last_name"));
			receptionReport.setNationality(rs.getString("nationality"));
			receptionReport.setRoomCharge(0 - rs.getFloat("room_charge"));
			receptionReport.setArrDate(rs.getDate("arr_date"));
			receptionReport.setActDepartDate(rs.getDate("act_depart_date"));
			occupancyList.add(receptionReport);
		}
		} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(getOccupancy);
		}
		return occupancyList;
	}

	public List<Transaction> getTotalDailyRevenueList(Date fromDate, Date toDate) throws Exception {
		String totalSql = "SELECT SUM(txn.amount) as amount,txn.txn_date from txn WHERE txn.txn_date BETWEEN '"
				+ fromDate + "' AND '" + toDate + "' GROUP BY txn.txn_date";
		PreparedStatement getTotalDailyRevenueList = null;
		ResultSet rs = null;
		Connection connection = null;
		List<Transaction> totalDailyRevenueList = new ArrayList<Transaction>();
	try {
		connection = dbConnection.getConnection();
		getTotalDailyRevenueList = connection.prepareStatement(totalSql);
		rs = getTotalDailyRevenueList.executeQuery();
		while (rs.next()) {
			Transaction txn = new Transaction();
			txn.setAmount(rs.getInt("amount"));
			txn.setTxn_date(rs.getString("txn_date"));
			totalDailyRevenueList.add(txn);
		}
	} catch (Exception ex) {
		ex.printStackTrace();

		throw new CustomException();
	}
	finally {
		dbConnection.releaseResource(connection);
		dbConnection.releaseResource(rs);
		dbConnection.releaseResource(rs);
	}
		return totalDailyRevenueList;

	}

	@Override
	public String getDailyRevenueList(Date fromDate, Date toDate) throws Exception {
		Connection connection = dbConnection.getConnection();
		CallableStatement preparedStatement = null;
		String xmlString = "";
		String procedure = "{CALL getTxnDetails(?,?,?)}";

		try {
			preparedStatement = connection.prepareCall(procedure);
			preparedStatement.setDate(1, fromDate); 
			preparedStatement.setDate(2, toDate);
			preparedStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			preparedStatement.executeUpdate();
			xmlString = escapeMetaCharacters(preparedStatement.getString(3));
		} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(preparedStatement);
			
		}

		return xmlString;
	}

	public String escapeMetaCharacters(String inputString) {
		// final String[] metaCharacters =
		// {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%"};
		final String[] metaCharacters = { "&", "'" };

		for (int i = 0; i < metaCharacters.length; i++) {
			if (inputString.contains(metaCharacters[i])) {
				inputString = inputString.replace(metaCharacters[i], " ");
			}
		}
		return inputString;
	}

	@Override
	public List<Transaction> getMonthlyClosure(int getMonth, String year) {
		Connection connection = dbConnection.getConnection();
		String monthlyResult = "SELECT " + "	v_get_invoice_details.act_depart_date, "
				+ "	v_get_invoice_details.txn_date, " + "	v_get_invoice_details.invoice_no, "
				+ "	v_get_invoice_details.room_number, " + "	v_get_invoice_details.guestName, "
				+ "	checkin_hdr.arr_date, " + "  v_get_invoice_details.numnights,  " + "	v_get_invoice_details.pax, "
				+ "	SUM( " + "		v_get_invoice_details.base_amount " + "	) AS base_amount, " + "	SUM( "
				+ "		v_get_invoice_details.tax1_amount " + "	) AS tax1_amount, " + "	SUM( "
				+ "		v_get_invoice_details.tax2_amount " + "	) AS tax2_amount, " + "	SUM( "
				+ "		v_get_invoice_details.tax3_amount " + "	) AS tax3_amount, " + "	SUM( "
				+ "		v_get_invoice_details.tax4_amount " + "	) AS tax4_amount, " + "	SUM( "
				+ "		v_get_invoice_details.totalTax " + "	) AS totalTax, " + "	SUM( "
				+ "		v_get_invoice_details.base_amount + v_get_invoice_details.totalTax " + "	) AS amount "
				+ "FROM " + "	v_get_invoice_details "
				+ "INNER JOIN checkin_hdr ON v_get_invoice_details.checkin_no = checkin_hdr.checkin_no " + "WHERE "
				+ "	v_get_invoice_details.act_depart_date BETWEEN '" + year + "-" + getMonth + "-01' " + "AND '" + year
				+ "-" + getMonth + "-31' " + "GROUP BY " + "	invoice_no " + "ORDER BY "
				+ "	v_get_invoice_details.act_depart_date,v_get_invoice_details.invoice_no  ASC";

		ResultSet result = null;
		List<Transaction> monthlyClosureList = new ArrayList<Transaction>();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(monthlyResult);
			// System.out.println(monthlyResult);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				Transaction txn = new Transaction();
				SystemSettings systemSettings = systemSettingsService.getSystemSettings();
				String dateFormat = systemSettings.getDateFormat();
				txn.setDateFormat(String.valueOf(dateFormat));
				txn.setInvoiceDate(result.getDate("act_depart_date"));
				txn.setInvoiceNo(result.getString("invoice_no"));
				txn.setRoomNumber(result.getString("room_number"));
				txn.setFirstName(result.getString("guestName"));
				txn.setBase_amount(result.getDouble("base_amount"));
				txn.setTax1_amount(result.getDouble("tax1_amount"));
				txn.setTax2_amount(result.getDouble("tax2_amount"));
				txn.setTax3_amount(result.getDouble("tax3_amount"));
				txn.setTax4_amount(result.getDouble("tax4_amount"));
				txn.setTax(result.getDouble("totalTax"));
				txn.setAmount(result.getDouble("amount"));
				txn.setNumNights(result.getString("numnights"));
				txn.setPax(result.getInt("pax"));
				monthlyClosureList.add(txn);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(result);
			dbConnection.releaseResource(preparedStatement);
			
		}
		return monthlyClosureList;
	}

	@Override
	public List<ReceptionReport> getRoomPerDayList(Date fromDate, Date toDate) {
		// String sql = "SELECT checkin_hdr.arr_date,COUNT(checkin_hdr.checkin_no) AS
		// roomCount,(SUM(checkin_hdr.num_adults) + SUM(checkin_hdr.num_children) +
		// SUM(checkin_hdr.num_infants)) AS pax FROM checkin_hdr WHERE `status` = 5 AND
		// arr_date BETWEEN '"+fromDate+"' AND '"+toDate+"' GROUP BY arr_date";
		// String sql = "SELECT checkin_rate.night_date,COUNT(checkin_hdr.checkin_no) AS
		// roomCount,(SUM(checkin_hdr.num_adults) + SUM(checkin_hdr.num_children) +
		// SUM(checkin_hdr.num_infants)) AS pax FROM checkin_rate INNER JOIN checkin_dtl
		// on checkin_rate.checkin_dtl_no=checkin_dtl.checkin_dtl_no INNER JOIN
		// checkin_hdr ON checkin_hdr.checkin_no=checkin_hdr.checkin_no WHERE
		// checkin_hdr.`status`=5 AND checkin_rate.night_date BETWEEN '"+fromDate+"' AND
		// '"+toDate+"' GROUP BY night_date";
		// String sql = "SELECT checkin_rate.night_date,count(id) AS
		// room,GROUP_CONCAT(checkin_hdr.checkin_no SEPARATOR ' , ') AS
		// ch,GROUP_CONCAT(checkin_hdr.num_adults SEPARATOR ' , ') AS pax FROM
		// checkin_rate LEFT JOIN checkin_dtl on
		// checkin_rate.checkin_dtl_no=checkin_dtl.checkin_dtl_no LEFT JOIN checkin_hdr
		// on checkin_dtl.checkin_no=checkin_hdr.checkin_no WHERE
		// checkin_rate.night_date BETWEEN '"+fromDate+"' AND '"+toDate+"' GROUP BY
		// checkin_rate.night_date";
		// String sql = "SELECT checkin_rate.night_date,count(id) AS
		// room,SUM(num_adults)+SUM(num_children)+SUM(num_infants) AS pax FROM
		// checkin_rate LEFT JOIN checkin_dtl ON checkin_rate.checkin_dtl_no =
		// checkin_dtl.checkin_dtl_no LEFT JOIN checkin_hdr ON checkin_dtl.checkin_no =
		// checkin_hdr.checkin_no WHERE checkin_rate.night_date BETWEEN '"+fromDate+"'
		// AND '"+toDate+"' AND `status`=5 GROUP BY checkin_rate.night_date";
		String sql = "SELECT checkin_rate.night_date, count(room_number) AS room, SUM(num_adults) + SUM(num_children) + SUM(num_infants) AS pax FROM	checkin_rate LEFT JOIN checkin_dtl ON checkin_rate.checkin_dtl_no = checkin_dtl.checkin_dtl_no LEFT JOIN checkin_hdr ON checkin_dtl.checkin_no = checkin_hdr.checkin_no WHERE	checkin_rate.night_date BETWEEN '"
				+ fromDate + "' AND '" + toDate + "' GROUP BY	checkin_rate.night_date";
		Connection con = dbConnection.getConnection();
		PreparedStatement roomPerDay = null;
		ResultSet result = null;
		ReceptionReport receptionReport = null;
		List<ReceptionReport> roomPerDayList = new ArrayList<ReceptionReport>();
		try {
			roomPerDay = con.prepareStatement(sql);
			result = roomPerDay.executeQuery();
			while (result.next()) {
				receptionReport = new ReceptionReport();
				receptionReport.setNightDate(result.getDate("night_date"));
				receptionReport.setTotalRooms(result.getInt("room"));
				receptionReport.setPax(result.getInt("pax"));
				roomPerDayList.add(receptionReport);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(result);
			dbConnection.releaseResource(roomPerDay);
			
		}

		return roomPerDayList;
	}

	@Override
	public List<ReceptionReport> getRoomPlanList(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		Connection con = dbConnection.getConnection();
		// String sql="SELECT
		// checkin_rate.night_date,checkin_hdr.room_number,(SUM(checkin_hdr.num_adults)
		// + SUM(checkin_hdr.num_children) + SUM(checkin_hdr.num_infants)) AS
		// pax,checkin_hdr.rate_description FROM checkin_rate INNER JOIN checkin_dtl on
		// checkin_rate.checkin_dtl_no=checkin_dtl.checkin_dtl_no INNER JOIN checkin_hdr
		// ON checkin_hdr.checkin_no=checkin_hdr.checkin_no WHERE checkin_hdr.`status`=5
		// AND checkin_rate.night_date BETWEEN '"+fromDate+"' AND '"+toDate+"' GROUP BY
		// night_date";
		// String sql="SELECT
		// checkin_rate.night_date,checkin_hdr.room_number,(SUM(checkin_hdr.num_adults)
		// + SUM(checkin_hdr.num_children) + SUM(checkin_hdr.num_infants)) AS
		// num_adults,checkin_hdr.rate_description FROM checkin_rate LEFT JOIN
		// checkin_dtl on checkin_dtl.checkin_dtl_no=checkin_rate.checkin_dtl_no LEFT
		// JOIN checkin_hdr ON checkin_hdr.checkin_no=checkin_dtl.checkin_no WHERE
		// night_date BETWEEN '"+fromDate+"' AND '"+toDate+"'GROUP BY room_number";
		String sql = "SELECT checkin_rate.night_date,checkin_hdr.room_number,	checkin_hdr.num_adults,checkin_hdr.num_children,checkin_hdr.num_infants,checkin_hdr.rate_description	FROM checkin_rate LEFT JOIN checkin_dtl ON checkin_dtl.checkin_dtl_no = checkin_rate.checkin_dtl_no	LEFT JOIN checkin_hdr ON checkin_hdr.checkin_no = checkin_dtl.checkin_no WHERE	night_date BETWEEN '"
				+ fromDate + "'AND '" + toDate + "' ORDER BY checkin_rate.night_date ASC ";
		PreparedStatement room = null;
		ReceptionReport receptionReport = null;
		ResultSet result = null;
		List<ReceptionReport> roomList = new ArrayList<ReceptionReport>();
		try {
			room = con.prepareStatement(sql);
			result = room.executeQuery();
			while (result.next()) {
				receptionReport = new ReceptionReport();
				receptionReport.setNightDate(result.getDate("night_date"));
				receptionReport.setRoomNumber(result.getString("room_number"));
				receptionReport.setPax(result.getInt("num_adults"));
				receptionReport.setRateDescription(result.getString("rate_description"));
				receptionReport.setChildren(result.getInt("num_children"));
				receptionReport.setInfants(result.getInt("num_infants"));
				roomList.add(receptionReport);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(result);
			dbConnection.releaseResource(room);
			
		}


		return roomList;
	}

	@Override
	public List<NationalityReport> getNationalityReportList(String getMonth, String getYear) {
		int montly_room_total = 0;
		int monthly_person_total = 0;
		List<NationalityReport> nationalityReportList = new ArrayList<NationalityReport>();
		NationalityReport nationalityReport = null;
		Connection con = null;
		CallableStatement st = null;
		ResultSet rs = null;
		String storedProcedure = "{call getNationalityReport(?,?)}";
		int lastDday = getLastDayOfMonth(getMonth + "/" + getYear);

		try {
			con = dbConnection.getConnection();
			st = con.prepareCall(storedProcedure);
			st.setString(1, getYear + "-" + getMonth + "-01");
			st.setString(2, getYear + "-" + getMonth + "-" + lastDday);
			st.execute();
			rs = st.getResultSet();
			while (rs.next()) {
				nationalityReport = new NationalityReport();

				nationalityReport.setCountry_name(rs.getString("country_name"));
				nationalityReport.setState_name(rs.getString("state_name"));
				nationalityReport.setRoom(rs.getInt("monthly_room"));
				nationalityReport.setPersons(rs.getInt("monthly_persons"));
				nationalityReport.setYearly_room(rs.getInt("yearly_room"));
				nationalityReport.setYearly_person(rs.getInt("yearly_persons"));
				montly_room_total += nationalityReport.getRoom();
				monthly_person_total += nationalityReport.getPersons();
				nationalityReportList.add(nationalityReport);

			}
			if (montly_room_total != 0) {
				for (NationalityReport nr : nationalityReportList) {
					nr.setRoom_percentage(String.format("%.2f", (float) nr.getRoom() * 100 / montly_room_total) + "%");
					nr.setPerson_percentage(
							String.format("%.2f", (float) nr.getPersons() * 100 / monthly_person_total) + "%");
				}
			} else {
				for (NationalityReport nr : nationalityReportList) {
					nr.setRoom_percentage("0%");
					nr.setPerson_percentage("0%");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(st);
			
		}



		return nationalityReportList;
	}

	private int getLastDayOfMonth(String dateString) {
		String DATE_PATTERN = "MM/yyyy";
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern(DATE_PATTERN);
		YearMonth yearMonth = YearMonth.parse(dateString, pattern);
		LocalDate date = yearMonth.atEndOfMonth();
		return date.lengthOfMonth();
	}

	@Override
	public List<CustomerReport> getCustomerReportList(String jsonCustSearch, Date fromDate, Date toDate)
			throws Exception {
		// TODO Auto-generated method stub

		List<CustomerReport> customerReportList = new ArrayList<CustomerReport>();
		CustomerReport customerReport = null;
		ResultSet rs = null;
		Statement statement = null;
		Connection connection = null;
		try {
		connection = dbConnection.getConnection();
		statement = connection.createStatement();
		String selectSql = "select checkin_dtl.first_name,checkin_dtl.last_name,checkin_dtl.email,checkin_dtl.phone,checkin_hdr.num_nights,checkin_hdr.arr_date,checkin_hdr.room_type_code,checkin_hdr.room_number,checkin_hdr.act_depart_date from  "
				+ "checkin_dtl INNER JOIN checkin_hdr ON checkin_dtl.checkin_no=checkin_hdr.checkin_no where checkin_dtl.first_name LIKE '"
				+ jsonCustSearch + "%' and " + " checkin_hdr.arr_date >= '" + fromDate
				+ "' and checkin_hdr.arr_date <= '" + toDate + "' ORDER BY checkin_dtl.first_name,checkin_hdr.arr_date";
		rs = statement.executeQuery(selectSql);
		while (rs.next()) {

			customerReport = new CustomerReport();
			SystemSettings systemSettings = systemSettingsService.getSystemSettings();
			String dateFormat = systemSettings.getDateFormat();
			customerReport.setDateFormat(String.valueOf(dateFormat));
			customerReport.setFirst_name(rs.getString("first_name"));
			customerReport.setLast_name(rs.getString("last_name"));
			customerReport.setEmail(rs.getString("email"));
			customerReport.setPhone(rs.getString("phone"));
			customerReport.setArr_date(rs.getString("arr_date"));
			customerReport.setRoom_type_code(rs.getString("room_type_code"));
			customerReport.setRoom_number(rs.getString("room_number"));
			customerReport.setNum_nights(rs.getString("num_nights"));
			customerReport.setAct_depart_date(rs.getString("act_depart_date"));
			customerReportList.add(customerReport);
		}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			
		}

		return customerReportList;
	}

	@Override
	public List<IncomeReport> getIncomeReportList(Date fromDate, Date toDate) {
		String sql = "SELECT txn.txn_date, receipt_gen.id AS receipt_no, txn.nett_amount, txn.payment_mode, checkin_hdr.invoice_no "
				+ "FROM txn LEFT JOIN receipt_gen ON txn.txn_no = receipt_gen.txn_no "
				+ "INNER JOIN folio ON folio.folio_no = txn.folio_no "
				+ "LEFT JOIN checkin_hdr ON folio.checkin_no = checkin_hdr.checkin_no "
				+ "WHERE acc_mst_id IN (4, 8) AND txn.txn_date BETWEEN '" + fromDate + "' AND '" + toDate
				+ "' ORDER BY txn.txn_date, receipt_gen.id";
		Connection connection = dbConnection.getConnection();
		PreparedStatement preparedStatement = null;
		IncomeReport incomeReport = null;
		ResultSet result = null;
		List<IncomeReport> incomeReportList = new ArrayList<IncomeReport>();
		try {
			preparedStatement = connection.prepareStatement(sql);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				incomeReport = new IncomeReport();
				SystemSettings systemSettings = systemSettingsService.getSystemSettings();
				String dateFormat = systemSettings.getDateFormat();
				incomeReport.setDateFormat(String.valueOf(dateFormat));
				incomeReport.setDate(result.getDate("txn_date"));
				incomeReport.setReceiptNo(result.getString("receipt_no"));
				incomeReport.setInvoiceNo(result.getString("invoice_no"));
				incomeReport.setAmount(result.getFloat("nett_amount"));
				incomeReport.setMode(PaymentMode.get(result.getInt("payment_mode")).getPaymentMode());
				incomeReportList.add(incomeReport);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(result);
			dbConnection.releaseResource(preparedStatement);
			dbConnection.releaseResource(connection);
			
		}
		return incomeReportList;
	}

	@Override
	public List<RoomBookingReport> getRoomFrequencyList(Date fromDate, Date toDate) throws SQLException {
		// TODO Auto-generated method stub

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		RoomBookingReport roomBookingReport = null;
		List<RoomBookingReport> receptionReportList = new ArrayList<RoomBookingReport>();
		try {
		connection = dbConnection.getConnection();
		String selectSql = "SELECT " + "	r.booking, " + "	r.room_number, " + "	CONCAT( "
				+ "		c.first_name, " + "		\" \", " + "		c.last_name " + "	) AS customer, "
				+ "	c.arr_date, " + "	c.num_adults, " + "	c.num_children, " + "	c.num_infants " + "FROM " + "	( "
				+ "		( " + "			SELECT " + "				count(*) AS booking, "
				+ "				room_number " + "			FROM " + "				checkin_hdr "
				+ "			INNER JOIN checkin_dtl ON checkin_dtl.checkin_no = checkin_hdr.checkin_no "
				+ "			WHERE " + "				is_sharer = 0 " + "			AND arr_date BETWEEN '" + fromDate
				+ "' " + "			AND '" + toDate + "' " + "			GROUP BY " + "				room_number "
				+ "			ORDER BY " + "				booking DESC " + "			LIMIT 5 " + "		) r "
				+ "		INNER JOIN ( " + "			SELECT " + "				checkin_hdr.checkin_no, "
				+ "				room_number, " + "				first_name, " + "				last_name, "
				+ "				arr_date, " + "				num_adults, " + "				num_children, "
				+ "				num_infants " + "			FROM " + "				checkin_hdr "
				+ "			INNER JOIN checkin_dtl ON checkin_dtl.checkin_no = checkin_hdr.checkin_no "
				+ "			WHERE " + "				is_sharer = 0 " + "			AND checkin_hdr.arr_date BETWEEN '"
				+ fromDate + "' " + "			AND '" + toDate + "' " + "		) c ON r.room_number = c.room_number "
				+ "	) " + "ORDER BY " + "	r.booking DESC, " + "	r.room_number, " + "	c.arr_date ";
		statement = connection.createStatement();
		resultSet = statement.executeQuery(selectSql);
		while (resultSet.next()) {

			roomBookingReport = new RoomBookingReport();
			roomBookingReport.setArrDate(resultSet.getDate("arr_date"));
			roomBookingReport.setFirstName(resultSet.getString("customer"));
			roomBookingReport.setRoomNumber(resultSet.getString("room_number"));
			roomBookingReport.setAdults(resultSet.getInt("num_adults"));
			roomBookingReport.setChildren(resultSet.getInt("num_children"));
			roomBookingReport.setInfants(resultSet.getInt("num_infants"));
			receptionReportList.add(roomBookingReport);
		}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
			
		}
		return receptionReportList;
	}

	@Override
	public List<AgingAR> getCustomerOutstandingDetails(Date fromDate, Date toDate, int id) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet10 = null;
		List<AgingAR> customerOutstandingList = new ArrayList<AgingAR>();
		try {
		connection = dbConnection.getConnection();
		String query2 = "";
		if (id != 0) {
			query2 = "' AND corporate_id= " + id;
		} else {
			query2 = "'";
		}
		String query1 = "SELECT * FROM ( SELECT " + " vw.corporate_id,vw.corporate_name,IFNULL (( SELECT "
				+ " sum(payable_amount) - sum(paid_amount) AS opening_amount FROM "
				+ " v_creditors_list WHERE invoice_date < '" + fromDate
				+ "' AND corporate_id = vw.corporate_id ),0) AS opening_amount, "
				+ " vw.invoice_date,vw.invoice_no,vw.days, "
				+ " sum(vw.payable_amount) - sum(vw.paid_amount) AS payable_amount FROM "
				+ " v_creditors_list vw WHERE " + " vw.invoice_date BETWEEN '" + fromDate + "' AND '" + toDate + query2
				+ " GROUP BY vw.corporate_id , vw.invoice_no) TBL"
				+ " WHERE TBL.opening_amount > 0 OR TBL.payable_amount > 0 ";

		statement = connection.createStatement();
		resultSet10 = statement.executeQuery(query1);
		while (resultSet10.next()) {
			// resultSet10.next();
			AgingAR agingAR = new AgingAR();
			agingAR.setInvoice_date(resultSet10.getString("invoice_date"));
			agingAR.setCorporate_id(resultSet10.getInt("corporate_id"));
			agingAR.setCorporate_name(resultSet10.getString("corporate_name"));
			agingAR.setInvoice_no(resultSet10.getString("invoice_no"));
			agingAR.setDays(resultSet10.getInt("days"));
			agingAR.setAmount(resultSet10.getFloat("payable_amount"));
			agingAR.setOpening_amount(resultSet10.getFloat("opening_amount"));
			customerOutstandingList.add(agingAR);
		}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet10);
			dbConnection.releaseResource(connection);
			
		}

		return customerOutstandingList;
	}

	@Override
	public List<TemplateReport> getCompanyDetails() throws Exception {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet11 = null;
		List<TemplateReport> templateReportsList = new ArrayList<TemplateReport>();
		try {
		connection = dbConnection.getConnection();
		String query1 = "SELECT * FROM company";
		statement = connection.createStatement();
		resultSet11 = statement.executeQuery(query1);
		if (resultSet11.next()) {
			TemplateReport templateReport = new TemplateReport();
			templateReport.setName(resultSet11.getString("company_name"));
			templateReport.setBuilding(resultSet11.getString("building_name"));
			templateReport.setStreet(resultSet11.getString("street_name"));
			templateReport.setCity(resultSet11.getString("city_name"));
			templateReport.setState(resultSet11.getString("state_name"));
			templateReport.setCountry(resultSet11.getString("country_name"));
			templateReport.setGst(resultSet11.getString("gst_no"));
			templateReportsList.add(templateReport);

		}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet11);
			dbConnection.releaseResource(connection);
			
		}
		return templateReportsList;
	}

	@Override
	public List<CustomerGrading> getCustomerGradingDetails(Date fromDate, Date toDate) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		List<CustomerGrading> customerGradingList = new ArrayList<CustomerGrading>();
		try {
		connection = dbConnection.getConnection();
		String query = "SELECT " + "	guest_name, " + "	address, " + "	email, " + "	phone, "
				+ "	total_amount " + "	FROM " + "	( " + "		SELECT " + "			CONCAT( "
				+ "				checkin_dtl.first_name, " + "				' ', "
				+ "				checkin_dtl.last_name " + "			) AS guest_name, "
				+ "			checkin_dtl.address, " + "			checkin_dtl.email, " + "			checkin_dtl.phone, "
				+ "			SUM(txn.amount) AS total_amount " + "		FROM " + "			checkin_hdr "
				+ "		INNER JOIN checkin_dtl ON checkin_hdr.checkin_no = checkin_dtl.checkin_no "
				+ "		INNER JOIN folio ON checkin_hdr.checkin_no = folio.checkin_no "
				+ "		INNER JOIN txn ON folio.folio_no = txn.folio_no " + "		WHERE "
				+ "			checkin_hdr.arr_date BETWEEN '" + fromDate + "'		AND '" + toDate
				+ "'		AND txn.acc_mst_id IN (1, 2, 7) " + "		AND (txn.is_deleted = 0) "
				+ "		AND (txn.txn_status = 1) " + "		GROUP BY " + "			CONCAT( "
				+ "				checkin_dtl.first_name, " + "				' ', "
				+ "				checkin_dtl.last_name " + "			) " + "	) TBL " + "	ORDER BY "
				+ "	total_amount DESC";
		statement = connection.createStatement();
		rs = statement.executeQuery(query);
		while (rs.next()) {
			CustomerGrading customerGrading = new CustomerGrading();
			customerGrading.setGuestName(rs.getString("guest_name"));
			customerGrading.setAddress(rs.getString("address"));
			customerGrading.setEmail(rs.getString("email"));
			customerGrading.setPhone(rs.getLong("phone"));
			customerGrading.setTotalAmount(rs.getDouble("total_amount"));

			customerGradingList.add(customerGrading);
		}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			
		}

		return customerGradingList;
	}

	@Override
	public JsonArray getTallyData(Date fromDate, Date toDate) {
		Connection connection = null;
	//	PreparedStatement statement = null;
		ResultSet rs = null;
		JsonArray tallyDataList = new JsonArray();
		connection = dbConnection.getConnection();
		CallableStatement statement=null;

		// String query="SELECT * FROM v_payment_tally WHERE act_depart_date BETWEEN ?
		// AND ? ORDER BY invoice_no, payment_sort";
		/*String query = "SELECT " + "invoice_no, " + "act_depart_date, " + "room_number, " + "room_type_code, "
				+ "corporate_name, " + "state, " + "nationality, " + "folio_no, " + "tax1_pc, " + "tax2_pc, "
				+ "tax3_pc, " + "acc_mst_code, " + "sum(amount) AS amount, " + "sum(base_amount) AS base_amount, "
				+ "sum(tax1_amount) AS tax1_amount, " + "sum(tax2_amount) AS tax2_amount, "
				+ "sum(tax3_amount) AS tax3_amount, " + "payment_mode, " + "payment_sort, " + "corporate_name,gstno,invoice  "
				+ " FROM " + "v_payment_tally " + "WHERE " + "act_depart_date BETWEEN ? " + "AND ? " + "GROUP BY "
				+ "invoice_no, " + "tax1_pc, " + "tax2_pc, " + "tax3_pc, " + "payment_mode " + "ORDER BY "
				+ "invoice_no, " + "payment_sort";*/
		
		

		try {

			statement=connection.prepareCall("{CALL getPaymentTally(?,?)}");
			statement.setDate(1, fromDate);
			statement.setDate(2, toDate);
			rs=statement.executeQuery();
			System.out.println(rs.getFetchSize());
			while (rs.next()) {
				
				JsonObject obj = new JsonObject();
				obj.addProperty("actualDepartDate", rs.getString("act_depart_date"));
				obj.addProperty("roomNumber", rs.getString("room_number"));
				obj.addProperty("roomTypeCode", rs.getString("room_type_code"));
				obj.addProperty("corporateName", rs.getString("corporate_name"));
				obj.addProperty("state", rs.getString("state"));
				obj.addProperty("nationality", rs.getString("nationality"));
				obj.addProperty("folioNo", rs.getString("folio_no"));
				obj.addProperty("amount", rs.getString("amount"));
				obj.addProperty("baseAmount", rs.getString("base_amount"));
				obj.addProperty("tax1pc", rs.getString("tax1_pc"));
				obj.addProperty("tax2pc", rs.getString("tax2_pc"));
				obj.addProperty("tax3pc", rs.getString("tax3_pc"));
				obj.addProperty("tax1Amount", rs.getString("tax1_amount"));
				obj.addProperty("tax2Amount", rs.getString("tax2_amount"));
				obj.addProperty("tax3Amount", rs.getString("tax3_amount"));
				obj.addProperty("paymentMode", rs.getString("payment_mode"));
				obj.addProperty("invoiceNo", rs.getString("invoice_no"));
				obj.addProperty("accMstCode", rs.getString("acc_mst_code"));
				obj.addProperty("gstNo", rs.getString("gstno"));
				obj.addProperty("invoice", rs.getString("invoice"));

				tallyDataList.add(obj);
				
			}
			System.out.println(tallyDataList.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			
		}
	
		return tallyDataList;
	}

	@Override
	public List<Corporate> getCorporateNames(Date fromDate, Date toDate) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Corporate> tallyDataList = new ArrayList<>();
		

		String query = "SELECT code, name FROM corporate WHERE is_deleted=0 AND DATE_FORMAT(last_upd_ts, '%Y-%m-%d') BETWEEN ? AND ?";

		try {
			connection = dbConnection.getConnection();

			statement = connection.prepareStatement(query);
			statement.setDate(1, fromDate);
			statement.setDate(2, toDate);
			rs = statement.executeQuery();
			while (rs.next()) {
				Corporate corp = new Corporate();
				corp.setName(rs.getString("name"));
				corp.setCode(rs.getString("code"));
				tallyDataList.add(corp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			
		}
		return tallyDataList;
	}

	
	@Override
	public JsonObject getCompany() {
		
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		JsonArray companyList = new JsonArray();
		
		
		try {
			connection=dbConnection.getConnection();
			String query = "select company_name,building_name,street_name,city_name from company";
			statement = connection.prepareStatement(query);
			rs=statement.executeQuery();
			while(rs.next()) {
				JsonObject obj = new JsonObject();
				obj.addProperty("company_name", rs.getString("company_name"));
				obj.addProperty("building_name", rs.getString("building_name"));
				obj.addProperty("street_name", rs.getString("street_name"));
				obj.addProperty("city_name", rs.getString("city_name"));
				
				companyList.add(obj);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			
		}
		JsonObject companyObj = new JsonObject();
		companyObj.add("companyData", companyList);
		//companyList = new JsonArray();
		//companyList.add(companyObj);
		
		return companyObj;
	}
	
	
	@Override
	public JsonArray getBookingFrequencyList(Date fromDate, Date toDate) {
		Connection connection=null;
		CallableStatement statement=null;
		ResultSet rs=null;
		JsonArray bookingDataList = new JsonArray();
		
		try {
			connection=dbConnection.getConnection();
			
			statement=connection.prepareCall("{CALL GuestAnalysis(?,?)}");
			statement.setDate(1, fromDate);
			statement.setDate(2, toDate);
			rs=statement.executeQuery();
			while(rs.next()) {
				JsonObject obj = new JsonObject();
				obj.addProperty("company", rs.getString("company_name"));
				obj.addProperty("phone", rs.getString("phone"));
				obj.addProperty("email", rs.getString("email"));
				obj.addProperty("noOfNights", rs.getString("nights"));
				obj.addProperty("nationality", rs.getString("nationality"));
				obj.addProperty("state", rs.getString("state"));
				obj.addProperty("amount", rs.getString("total_amount"));
				obj.addProperty("frequency", rs.getString("frequency"));
				bookingDataList.add(obj);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			
		}
		JsonObject dataObj = new JsonObject();
		JsonObject dateRange = new JsonObject();
		dateRange.addProperty("fromDate", fromDate.toString());
		dateRange.addProperty("toDate", toDate.toString());
		dataObj.add("bookingData", bookingDataList);
		dataObj.add("dateRange", dateRange);
		bookingDataList = new JsonArray();
		bookingDataList.add(dataObj);
		
		return bookingDataList;
	}
	
	
	@Override
	public JsonArray getLedgerData(Date fromDate, Date toDate) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		JsonArray LedgerDataList = new JsonArray();
		

		String query = "SELECT * FROM v_ledger_list WHERE act_depart_date BETWEEN ? AND ?";

		try {
			connection = dbConnection.getConnection();

			statement = connection.prepareStatement(query);
			statement.setDate(1, fromDate);
			statement.setDate(2, toDate);
			rs = statement.executeQuery();
			while (rs.next()) {
				JsonObject obj = new JsonObject();
				obj.addProperty("ledgerName", rs.getString("ledger_name"));
				obj.addProperty("gstNo", rs.getString("gst_no"));
				obj.addProperty("country", rs.getString("country"));
				LedgerDataList.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			
		}

		return LedgerDataList;
	}

	@Override
	public List<PettyCash> getPettyCashList(Date fromDate, Date toDate) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<PettyCash> pettyCashList=new ArrayList<>();
		
		String selSql="SELECT cashexp.*,cash.name from petty_cash_expense cashexp INNER JOIN petty_cash_category cash ON " + 
								" cash.id=cashexp.category_id WHERE entry_date BETWEEN ? AND ? AND cashexp.is_deleted=0"
								+ " AND VOUCHER_NAME IN('PAYMENT','CONTRA') order by cashexp.entry_date ASC";
		 
		try {
			connection = dbConnection.getConnection();
			statement = connection.prepareStatement(selSql);
			statement.setDate(1, fromDate);
			statement.setDate(2, toDate);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PettyCash pettyCash=new PettyCash();
				pettyCash.setId(resultSet.getInt("id"));
				pettyCash.setAmount(resultSet.getDouble("amount"));
				pettyCash.setCategoryName(resultSet.getString("name"));
				pettyCash.setEntryDate(resultSet.getDate("entry_date"));
				pettyCash.setVoucherType(resultSet.getString("voucher_name"));
				pettyCash.setVoucherNo(resultSet.getInt("voucher_no"));
				pettyCash.setNarration(resultSet.getString("narration"));
				pettyCashList.add(pettyCash);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
			
		}

	
		return pettyCashList;
	}
	
	
	
	@Override
	public List<PettyCash> getPettyCashExportList(Date fromDate, Date toDate) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<PettyCash> pettyCashList=new ArrayList<>();
		
//		String selSql="SELECT cashexp.*,cash.name from petty_cash_expense cashexp INNER JOIN petty_cash_category cash ON " + 
//								" cash.id=cashexp.category_id WHERE entry_date BETWEEN ? AND ? AND cashexp.is_deleted=0 order by cashexp.entry_date ASC";
		String selSql = "SELECT * FROM (SELECT '"+fromDate+"' AS entry_date , 0 As  category_id ,\"\" AS voucher_no,\"\" AS voucher_name, getOpening('"+fromDate+"') AS  amount,\"Opening Balance\" as name\r\n , \"\" AS narration " + 
				"UNION\r\n" + 
				"SELECT cashexp.entry_date,cashexp.category_id,cashexp.voucher_no,cashexp.voucher_name,cashexp.amount,cash.name,cashexp.narration from petty_cash_expense cashexp INNER JOIN petty_cash_category cash ON  cash.id=cashexp.category_id WHERE entry_date BETWEEN '"+fromDate+"' AND '"+toDate+"' AND voucher_name IN('PAYMENT','CONTRA') AND cashexp.is_deleted=0 )T\r\n" + 
				"order by entry_date,category_id ASC";
		
		try {
			connection = dbConnection.getConnection();
			statement = connection.prepareStatement(selSql);
//			statement.setDate(1, fromDate);
//			statement.setDate(2, toDate);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PettyCash pettyCash=new PettyCash();
//				pettyCash.setId(resultSet.getInt("id"));
				pettyCash.setAmount(resultSet.getDouble("amount"));
				pettyCash.setCategoryName(resultSet.getString("name"));
				pettyCash.setEntryDate(resultSet.getDate("entry_date"));
				pettyCash.setVoucherType(resultSet.getString("voucher_name"));
				pettyCash.setVoucherNo(resultSet.getInt("voucher_no"));
				pettyCash.setNarration(resultSet.getString("narration"));
				pettyCashList.add(pettyCash);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
			
		}
		return pettyCashList;
	}

	@Override
	public JsonArray getContraData(Date fromDate, Date toDate,int isContra) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String selSql="";
		JsonArray contraDataList = new JsonArray();
		
		if(isContra==1) {
		selSql="SELECT " + 
				"	cashexp.*, cash. name AS head_name " + 
				"FROM " + 
				"	petty_cash_expense cashexp " + 
				"INNER JOIN petty_cash_category cash ON cash.id = cashexp.category_id WHERE cashexp.voucher_name='CONTRA' "
				+ "AND cashexp.entry_date BETWEEN ? AND ? AND cashexp.is_deleted=0";
		}else {
			selSql="SELECT " + 
					"	cashexp.*, cash. name AS head_name " + 
					"FROM " + 
					"	petty_cash_expense cashexp " + 
					"INNER JOIN petty_cash_category cash ON cash.id = cashexp.category_id WHERE cashexp.voucher_name='PAYMENT' "
					+ "AND cashexp.entry_date BETWEEN ? AND ? AND cashexp.is_deleted=0";
		}
		try {
			connection = dbConnection.getConnection();
			statement = connection.prepareStatement(selSql);
			statement.setDate(1, fromDate);
			statement.setDate(2, toDate);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				JsonObject obj = new JsonObject();
				obj.addProperty("name", resultSet.getString("head_name"));
				//obj.addProperty("total", resultSet.getString("total"));
				obj.addProperty("amount", resultSet.getDouble("amount"));
				obj.addProperty("date", String.valueOf(resultSet.getDate("entry_date")));
				obj.addProperty("voucherNo", resultSet.getInt("voucher_no"));
				obj.addProperty("narration", resultSet.getString("narration"));
				contraDataList.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
			
		}
		return contraDataList;
	}

	@Override
	public JsonArray getPettyLedgerData(Date fromDate, Date toDate) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String selSql="";
		JsonArray pettyLedList = new JsonArray();
	
		
		selSql="SELECT * FROM (SELECT " + 
				"	cashexp.*, cash. name AS head_name " + 
				"FROM " + 
				"	petty_cash_expense cashexp " + 
				"INNER JOIN petty_cash_category cash ON cash.id = cashexp.category_id WHERE cashexp.voucher_name='CONTRA' "
				+ "AND cashexp.entry_date BETWEEN ? AND ? AND cashexp.is_deleted=0 ) A UNION ALL"
		        + " SELECT * FROM (SELECT " + 
				"	cashexp.*, cash. name AS head_name " + 
				"FROM " + 
				"	petty_cash_expense cashexp " + 
				"INNER JOIN petty_cash_category cash ON cash.id = cashexp.category_id WHERE cashexp.voucher_name='PAYMENT' "
				+ "AND cashexp.entry_date BETWEEN ? AND ? AND cashexp.is_deleted=0 )B ORDER BY entry_date";
			try {
				connection = dbConnection.getConnection();
				statement = connection.prepareStatement(selSql);
				statement.setDate(1, fromDate);
				statement.setDate(2, toDate);
				statement.setDate(3, fromDate);
				statement.setDate(4, toDate);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					JsonObject obj = new JsonObject();
					obj.addProperty("name", resultSet.getString("head_name"));
					obj.addProperty("voucher_name", resultSet.getString("voucher_name"));
					obj.addProperty("amount", resultSet.getDouble("amount"));
					obj.addProperty("date", String.valueOf(resultSet.getDate("entry_date")));
					obj.addProperty("voucherNo", resultSet.getInt("voucher_no"));
					obj.addProperty("narration", resultSet.getString("narration"));
					pettyLedList.add(obj);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new CustomException();
			}
			finally {
				dbConnection.releaseResource(statement);
				dbConnection.releaseResource(resultSet);
				dbConnection.releaseResource(connection);
				
			}
			return pettyLedList;
		
	}
	
	
	
	@Override
	public JsonArray getCreditCardData(Date fromDate, Date toDate) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String selSql="";
		JsonArray pettyLedList = new JsonArray();
		
		
		selSql="SELECT * FROM (SELECT " + 
				"	cashexp.*, cash. name AS head_name " + 
				"FROM " + 
				"	petty_cash_expense cashexp " + 
				"INNER JOIN petty_cash_category cash ON cash.id = cashexp.category_id WHERE cashexp.voucher_name='JOURNAL' "
				+ "AND cashexp.entry_date BETWEEN ? AND ? AND cashexp.is_deleted=0 )A ORDER BY entry_date";
			try {
				connection = dbConnection.getConnection();
				statement = connection.prepareStatement(selSql);
				statement.setDate(1, fromDate);
				statement.setDate(2, toDate);
				
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					JsonObject obj = new JsonObject();
					obj.addProperty("name", resultSet.getString("head_name"));
					obj.addProperty("voucher_name", resultSet.getString("voucher_name"));
					obj.addProperty("amount", resultSet.getDouble("amount"));
					obj.addProperty("date", String.valueOf(resultSet.getDate("entry_date")));
					obj.addProperty("voucherNo", resultSet.getInt("voucher_no"));
					obj.addProperty("narration", resultSet.getString("narration"));
					pettyLedList.add(obj);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new CustomException();
			}
			finally {
				dbConnection.releaseResource(statement);
				dbConnection.releaseResource(resultSet);
				dbConnection.releaseResource(connection);
				
			}
			return pettyLedList;
		
	}


	@Override
	public List<PettyCash> getCreditCardDataList(Date fromDate, Date toDate) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String selSql="";
		List<PettyCash> pettyCashList=new ArrayList<>();
		
		 selSql="SELECT * FROM (SELECT " + 
				"	cashexp.*, cash. name " + 
				"FROM " + 
				"	petty_cash_expense cashexp " + 
				"INNER JOIN petty_cash_category cash ON cash.id = cashexp.category_id WHERE cashexp.voucher_name='JOURNAL' "
				+ "AND cashexp.entry_date BETWEEN ? AND ? AND cashexp.is_deleted=0 )A ORDER BY entry_date";
			try {
				connection = dbConnection.getConnection();
				statement = connection.prepareStatement(selSql);
				statement.setDate(1, fromDate);
				statement.setDate(2, toDate);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PettyCash pettyCash=new PettyCash();
				pettyCash.setAmount(resultSet.getDouble("amount"));
				pettyCash.setCategoryName(resultSet.getString("name"));
				pettyCash.setEntryDate(resultSet.getDate("entry_date"));
				pettyCash.setVoucherType(resultSet.getString("voucher_name"));
				pettyCash.setVoucherNo(resultSet.getInt("voucher_no"));
				pettyCash.setNarration(resultSet.getString("narration"));
				pettyCashList.add(pettyCash);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException();
		}
			finally {
				dbConnection.releaseResource(statement);
				dbConnection.releaseResource(resultSet);
				dbConnection.releaseResource(connection);
				
			}
	
		return pettyCashList;
	}

	@Override
	public List<Transaction> getRevenueReportDetail(Date fromDate, Date toDate,int reportSelected) throws SQLException {
		Connection connection = dbConnection.getConnection();
		CallableStatement preparedStatement = null;
		String selectQuery = "";
		if(reportSelected == 0) {
			 selectQuery = "select txn_no,particulars,room_number,invoice_no,first_name,last_name,pax,txn_date,acc_mst_id,tax_id,\r\n" + 
			 		"CASE WHEN acc_mst_id IN(13,5) THEN -1* base_amount ELSE base_amount END AS base_amount,\r\n" + 
			 		"CASE WHEN acc_mst_id IN(13,5) THEN -1* tax1_amount ELSE tax1_amount END AS tax1_amount,\r\n" + 
			 		"CASE WHEN acc_mst_id IN(13,5) THEN -1* tax2_amount ELSE tax1_amount END AS tax2_amount,\r\n" + 
			 		"CASE WHEN acc_mst_id IN(13,5) THEN -1* amount ELSE amount END AS amount,\r\n" + 
			 		"tax1_pc,tax2_pc,nett_amount,sysdef_acc_type_id from v_revenue_detail where txn_date between '" +fromDate+ "' and '" +toDate+"' ORDER BY txn_date,invoice_no,room_number ";
		}
		else {
			
			 selectQuery = "select txn_no,particulars,room_number,invoice_no,first_name,last_name,pax,txn_date,acc_mst_id,tax_id,\r\n" + 
			 		"CASE WHEN acc_mst_id IN(13,5) THEN -1* base_amount ELSE base_amount END AS base_amount,\r\n" + 
			 		"CASE WHEN acc_mst_id IN(13,5) THEN -1* tax1_amount ELSE tax1_amount END AS tax1_amount,\r\n" + 
			 		"CASE WHEN acc_mst_id IN(13,5) THEN -1* tax2_amount ELSE tax1_amount END AS tax2_amount,\r\n" + 
			 		"CASE WHEN acc_mst_id IN(13,5) THEN -1* amount ELSE amount END AS amount,\r\n" + 
			 		"tax1_pc,tax2_pc,nett_amount,sysdef_acc_type_id from v_revenue_detail where txn_date between '" +fromDate+ "' and '" +toDate+"' and sysdef_acc_type_id = "+reportSelected+" ORDER BY txn_date,invoice_no,room_number ";
			
		}
		
		PreparedStatement prpS = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction txn=null;
		List<Transaction> txnList=new ArrayList<Transaction>();
		try {
			con = dbConnection.getConnection();
			prpS = con.prepareStatement(selectQuery);
			rs = prpS.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setAccMstName(rs.getString("particulars"));
				txn.setTxn_no(rs.getInt("txn_no"));
				txn.setRoomNumber(rs.getString("room_number"));
				txn.setInvoiceNo(rs.getString("invoice_no"));
				txn.setTxn_date(rs.getString("txn_date"));
				
				//txn.setAcc_mst_id(rs.getInt("acc_mst_id"));
				//txn.setAcc_mst_code(rs.getString("acc_mst_code"));
				//txn.setServiceTaxId(rs.getInt("service_tax_id"));
				txn.setTax_id(rs.getInt("tax_id"));
				//txn.setTax_code(rs.getString("tax_code"));
				//txn.setTax_indicator(rs.getString("tax_indicator"));
				//txn.setInclude_tax(rs.getBoolean("include_tax"));
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				//txn.setTax3_amount(rs.getDouble("tax3_amount"));
				//txn.setTax4_amount(rs.getDouble("tax4_amount"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				//txn.setTax3_pc(rs.getDouble("tax3_pc"));
				//txn.setTax4_pc(rs.getDouble("tax4_pc"));
				txn.setFirstName(rs.getString("first_name"));
				txn.setLastName(rs.getString("last_name"));
				
				txn.setPax(rs.getInt("pax"));
				
				//txn.setServiceCharge(rs.getBigDecimal("service_charge"));
				txn.setAmount(rs.getDouble("amount"));
				txn.setBase_amount(rs.getDouble("base_amount"));
				txn.setNett_amount(rs.getDouble("nett_amount"));
				
				//txn.setReceived_from(rs.getString("received_from"));
				txnList.add(txn);
			}
			} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(prpS);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);

		}
		

		return txnList;
	}

	@Override
	public List<Transaction> getFdRevenueReportDetail(Date fromDate, Date toDate, int reportSelected)
			throws SQLException {
		Connection connection = dbConnection.getConnection();
		CallableStatement preparedStatement = null;
		String selectQuery = "";
		
		if(reportSelected == 12) {
			selectQuery = "select txn_no,particulars,room_number,invoice_no,first_name,last_name,pax,txn_date,acc_mst_id,tax_id,\r\n" + 
					"CASE WHEN acc_mst_id IN(13,5) THEN -1* base_amount ELSE base_amount END AS base_amount,\r\n" + 
					"CASE WHEN acc_mst_id IN(13,5) THEN -1* tax1_amount ELSE tax1_amount END AS tax1_amount,\r\n" + 
					"CASE WHEN acc_mst_id IN(13,5) THEN -1* tax2_amount ELSE tax1_amount END AS tax2_amount,\r\n" + 
					"CASE WHEN acc_mst_id IN(13,5) THEN -1* amount ELSE amount END AS amount,\r\n" + 
					"tax1_pc,tax2_pc,nett_amount,sysdef_acc_type_id from v_revenue_detail where txn_date between '" +fromDate+ "' and '" +toDate+"' and acc_mst_id IN (12,13)  ORDER BY txn_date,invoice_no,room_number ";
		}
		else {
			
			selectQuery = "select txn_no,particulars,room_number,invoice_no,first_name,last_name,pax,txn_date,acc_mst_id,tax_id,\r\n" + 
					"CASE WHEN acc_mst_id IN(13,5) THEN -1* base_amount ELSE base_amount END AS base_amount,\r\n" + 
					"CASE WHEN acc_mst_id IN(13,5) THEN -1* tax1_amount ELSE tax1_amount END AS tax1_amount,\r\n" + 
					"CASE WHEN acc_mst_id IN(13,5) THEN -1* tax2_amount ELSE tax1_amount END AS tax2_amount,\r\n" + 
					"CASE WHEN acc_mst_id IN(13,5) THEN -1* amount ELSE amount END AS amount,\r\n" + 
					"tax1_pc,tax2_pc,nett_amount,sysdef_acc_type_id from v_revenue_detail where txn_date between '" +fromDate+ "' and '" +toDate+"' and acc_mst_id = "+reportSelected+" ORDER BY txn_date,invoice_no,room_number ";
			
		}
	
		
		PreparedStatement prpS = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction txn=null;
		List<Transaction> txnList=new ArrayList<Transaction>();
		try {
			con = dbConnection.getConnection();
			prpS = con.prepareStatement(selectQuery);
			rs = prpS.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setAccMstName(rs.getString("particulars"));
				txn.setTxn_no(rs.getInt("txn_no"));
				txn.setRoomNumber(rs.getString("room_number"));
				txn.setInvoiceNo(rs.getString("invoice_no"));
				txn.setTxn_date(rs.getString("txn_date"));
				
				//txn.setAcc_mst_id(rs.getInt("acc_mst_id"));
				//txn.setAcc_mst_code(rs.getString("acc_mst_code"));
				//txn.setServiceTaxId(rs.getInt("service_tax_id"));
				txn.setTax_id(rs.getInt("tax_id"));
				//txn.setTax_code(rs.getString("tax_code"));
				//txn.setTax_indicator(rs.getString("tax_indicator"));
				//txn.setInclude_tax(rs.getBoolean("include_tax"));
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				//txn.setTax3_amount(rs.getDouble("tax3_amount"));
				//txn.setTax4_amount(rs.getDouble("tax4_amount"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				//txn.setTax3_pc(rs.getDouble("tax3_pc"));
				//txn.setTax4_pc(rs.getDouble("tax4_pc"));
				txn.setFirstName(rs.getString("first_name"));
				txn.setLastName(rs.getString("last_name"));
				
				txn.setPax(rs.getInt("pax"));
				
				//txn.setServiceCharge(rs.getBigDecimal("service_charge"));
				txn.setAmount(rs.getDouble("amount"));
				txn.setBase_amount(rs.getDouble("base_amount"));
				txn.setNett_amount(rs.getDouble("nett_amount"));
				
				//txn.setReceived_from(rs.getString("received_from"));
				txnList.add(txn);
			}
			} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(prpS);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);

		}
		

		return txnList;
	}

	@Override
	public List<Transaction> getPosRevenueReport(Date fromDate, Date toDate) throws SQLException {
		Connection connection = dbConnection.getConnection();
		CallableStatement preparedStatement = null;
		String selectQuery = "";

		
		selectQuery = " SELECT * FROM v_revenue_detail tx inner join txn_dtl dtl on tx.txn_no = dtl.txn_no where acc_mst_id in('13', '12') AND txn_date between '" +fromDate+ "' and '" +toDate+"'";
		
		PreparedStatement prpS = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction txn=null;
		List<Transaction> txnList=new ArrayList<Transaction>();
		try {
			con = dbConnection.getConnection();
			prpS = con.prepareStatement(selectQuery);
			rs = prpS.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setAccMstName(rs.getString("particulars"));
				txn.setTxn_no(rs.getInt("txn_no"));
				txn.setRoomNumber(rs.getString("room_number"));
				txn.setInvoiceNo(rs.getString("invoice_no"));
				txn.setTxn_date(rs.getString("txn_date"));
				txn.setTax_id(rs.getInt("tax_id"));
				txn.setTax1_amount(rs.getDouble("tax1_amount"));
				txn.setTax2_amount(rs.getDouble("tax2_amount"));
				txn.setTax3_amount(rs.getDouble("tax3_amount"));
				txn.setTax1_pc(rs.getDouble("tax1_pc"));
				txn.setTax2_pc(rs.getDouble("tax2_pc"));
				txn.setTax3_pc(rs.getDouble("tax3_pc"));
				txn.setFirstName(rs.getString("first_name"));
				txn.setLastName(rs.getString("last_name"));
				txn.setPax(rs.getInt("pax"));
				
				//txn.setServiceCharge(rs.getBigDecimal("service_charge"));
				txn.setAmount(rs.getDouble("amount"));
				txn.setBase_amount(rs.getDouble("base_amount"));
				txn.setNett_amount(rs.getDouble("nett_amount"));
				txn.setAcc_mst_id(rs.getInt("acc_mst_id"));
				
				//txn.setReceived_from(rs.getString("received_from"));
				txn.setBillDetails(rs.getObject("bill_details"));
				txnList.add(txn);
			}
			} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(prpS);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);

		}
		

		return txnList;
	}

	@Override
	public List<Transaction> getB2BReport(Date fromDate, Date toDate) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = dbConnection.getConnection();
		CallableStatement preparedStatement = null;
		String selectQuery = "";

		
		selectQuery = "SELECT (SELECT COUNT( DISTINCT gstno) FROM v_tax_report WHERE  tax_id!=1 AND invoice_date BETWEEN  '" +fromDate+ "' and '" +toDate+"'  AND gstno!=\"\") as countGst,"
				+ "(SELECT COUNT( DISTINCT invoice_no) FROM v_tax_report WHERE tax_id!=1 AND  invoice_date BETWEEN  '" +fromDate+ "' and '" +toDate+"' AND gstno!=\"\") as countInvoice,"
				+"(SELECT SUM(amount) FROM v_tax_report WHERE  tax_id!=1 AND invoice_date BETWEEN  '" +fromDate+ "' and '" +toDate+"' AND gstno!=\"\") as totalInvoiceAmt,"
				+"(SELECT SUM(base_amount) FROM v_tax_report WHERE tax_id!=1 AND  invoice_date BETWEEN  '" +fromDate+ "' and '" +toDate+"' AND gstno!=\"\") as totalTaxableVal,"
				+ "tax_id,invoice_no,invoice_date,gstno,amount,receiver_name,total_tax_pc,SUM(base_amount) AS base_amount \r\n" + 
				"FROM v_tax_report WHERE  tax_id!=1 AND invoice_date BETWEEN  '" +fromDate+ "' and '" +toDate+"' AND gstno!=\"\"\r\n" + 
				" GROUP BY invoice_no,total_tax_pc ORDER BY invoice_no,gstno";
		
		PreparedStatement prpS = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction txn=null;
		List<Transaction> txnList=new ArrayList<Transaction>();
		try {
			con = dbConnection.getConnection();
			prpS = con.prepareStatement(selectQuery);
			rs = prpS.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setCountInvoice(rs.getInt("countInvoice"));
				txn.setCountGst(rs.getInt("countGst"));
				txn.setTotalInvoiceAmount(rs.getDouble("totalInvoiceAmt"));
				txn.setInvoiceAmount(rs.getDouble("amount"));
				txn.setTotalTaxableVal(rs.getDouble("totalTaxableVal"));
				txn.setInvoiceBaseAmount(rs.getDouble("base_amount"));
				txn.setInvoiceNo(rs.getString("invoice_no"));
				txn.setGstNo(rs.getString("gstno"));
				txn.setInvoiceDateField(rs.getString("invoice_date"));
				txn.setReceiver_name(rs.getString("receiver_name"));
				txn.setTotalTaxPc(rs.getDouble("total_tax_pc"));
				txnList.add(txn);
			}
			} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(prpS);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);

		}
		

		return txnList;
	}

	@Override
	public List<Transaction> getB2CReport(Date fromDate, Date toDate) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = dbConnection.getConnection();
		CallableStatement preparedStatement = null;
		String selectQuery = "";

		
		selectQuery = "SELECT total_tax_pc,SUM(base_amount) AS base_amount ,\r\n" + 
				"(SELECT SUM(base_amount) FROM v_tax_report WHERE tax_id!=1 AND  invoice_date BETWEEN  '" +fromDate+ "' and '" +toDate+"' AND gstno=\"\") as totalTaxableVal "+
				"FROM v_tax_report WHERE tax_id!=1 AND invoice_date BETWEEN  '" +fromDate+ "' and '" +toDate+"' AND gstno =\"\"\r\n" + 
				" GROUP BY total_tax_pc";//tax_id
		PreparedStatement prpS = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction txn=null;
		List<Transaction> txnList=new ArrayList<Transaction>();
		try {
			con = dbConnection.getConnection();
			prpS = con.prepareStatement(selectQuery);
			rs = prpS.executeQuery();
			while(rs.next()){
				txn=new Transaction();
				txn.setTotalTaxableVal(rs.getDouble("totalTaxableVal"));
				txn.setInvoiceBaseAmount(rs.getDouble("base_amount"));
				txn.setTotalTaxPc(rs.getDouble("total_tax_pc"));
				txnList.add(txn);
				
			}
			} catch (Exception ex) {
			ex.printStackTrace();

			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(prpS);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);

		}
		

		return txnList;
	}
		@Override
	public List<DayEndRport> getDayEndDetials(Date currentDate) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String selSql="";
		List<DayEndRport> pettyCashList=new ArrayList<>();

		
		connection = dbConnection.getConnection();
		selSql = "SELECT  hdr.invoice_no,tx.txn_date ,room_type.code AS room_type,'' AS BookMethod ,fo.folio_no,concat(`dtl`.`salutation`,`dtl`.`first_name`) AS `first_name`,`dtl`.`last_name` AS `last_name`, hdr.room_number AS room_no, hdr.arr_date as checkin_date,\r\n" + 
				" hdr.arr_time as checkin_time,((`hdr`.`num_adults` + `hdr`.`num_children`) + `hdr`.`num_infants`) AS pax ,hdr.act_depart_date AS \r\n" + 
				" checkout_date,hdr.act_depart_time AS checkout_time ,tx.acc_mst_code ,CASE WHEN acc.code = 'DEPOSIT' THEN 'Advance' ELSE acc.name END AS perticulars, \r\n" + 
				"  CASE WHEN tx.payment_mode = 1 THEN 'CASH' WHEN tx.payment_mode = 2 THEN 'CARD' WHEN tx.payment_mode = 3 THEN 'ONLINE TRANSFER' \r\n" + 
				"  WHEN tx.payment_mode = 4 THEN 'DD'  WHEN tx.payment_mode = 5 THEN 'COMPANY'  WHEN tx.payment_mode = 6 THEN 'COMPLEMENTARY' END AS payment_mode,\r\n" + 
				" CASE WHEN acc_mst_code = 'R-C' THEN 'A' WHEN  acc_mst_code ='DEPOSIT' THEN 'C' WHEN acc_mst_code ='PAID-IN' THEN 'D' \r\n" + 
				"  ELSE 'B' END AS  acc_group,\r\n" + 
				"`tx`.`is_disc_applied` AS `is_disc_applied`,`tx`.`disc_id` AS `disc_id`,`tx`.`disc_amount` AS `disc_amount`,\r\n" + 
				"(case when (`tx`.`is_disc_applied` = 1) then `tx`.`new_base_amount` else `tx`.`base_amount` end) AS `base_amount`,\r\n" + 
				"(case when (`tx`.`is_disc_applied` = 1) then ABS(`tx`.`new_nett_amount`) else ABS(`tx`.`nett_amount`) end) AS `amount`,\r\n" + 
				"(case when (`tx`.`is_disc_applied` = 1) then `tx`.`new_nett_amount` else `tx`.`nett_amount` end) AS `nett_amount`\r\n" + 
				" FROM checkin_hdr hdr LEFT  JOIN checkin_dtl dtl ON hdr.checkin_no=dtl.checkin_no \r\n" + 
				" INNER  JOIN folio fo ON  hdr.checkin_no=fo.checkin_no \r\n" + 
				" JOIN txn tx ON fo.folio_no=tx.folio_no  LEFT JOIN acc_mst acc ON acc.id = tx.acc_mst_id "
				+ " LEFT JOIN  room  ON (`hdr`.`room_number`  = room.number AND room.is_deleted =0)\r\n" + 
				" LEFT JOIN room_type ON room_type.id = room.room_type_id AND room_type.is_deleted =0 "
				+ " WHERE (tx.txn_status =1 AND  (( hdr.act_depart_date='" +currentDate+ "' ) OR  (hdr.arr_date <='"+currentDate+"' AND hdr.status = 5 ))) ORDER BY invoice_no,folio_no,acc_group,txn_date";
		//+ " WHERE ( hdr.act_depart_date='" +currentDate+ "' OR  (hdr.arr_date <='"+currentDate+"' AND hdr.status = 5 ) ) ORDER BY invoice_no,folio_no,acc_group";
		

			try {
				statement = connection.prepareStatement(selSql);
				resultSet = statement.executeQuery();
			while (resultSet.next()) {
				DayEndRport pettyCash=new DayEndRport();
			//	pettyCash.setRptDate();
				pettyCash.setTxn_date(resultSet.getDate("txn_date"));
				pettyCash.setRoom_tpye(resultSet.getString("room_type"));
				pettyCash.setInvoiceNo(resultSet.getString("invoice_no"));
				pettyCash.setPerticulars(resultSet.getString("perticulars"));
				pettyCash.setAccMstCode(resultSet.getString("acc_mst_code"));
				pettyCash.setPaymentMode(resultSet.getString("payment_mode"));
				pettyCash.setFirstName(resultSet.getString("first_name"));
				pettyCash.setLastName(resultSet.getString("last_name"));
				pettyCash.setRoomNumber(resultSet.getString("room_no"));
				pettyCash.setCheckinDate(resultSet.getString("checkin_date"));
				pettyCash.setCheckinTime(resultSet.getString("checkin_time"));
				pettyCash.setCheckOutDate(resultSet.getString("checkout_date"));
				pettyCash.setCheckOutTime(resultSet.getString("checkout_time"));
				pettyCash.setPax(resultSet.getInt("pax"));
				pettyCash.setPaymentMode(resultSet.getString("payment_mode"));
				pettyCash.setBookingMethod(resultSet.getString("BookMethod"));
				pettyCash.setAmount(resultSet.getDouble("amount"));
				pettyCash.setBaseAmount(resultSet.getDouble("base_amount"));
				pettyCash.setNettAmount(resultSet.getDouble("nett_amount"));
				pettyCash.setFolioNo(resultSet.getInt("folio_no"));
				pettyCash.setRptDate(currentDate);
				pettyCashList.add(pettyCash);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException();
		}
			finally {
				dbConnection.releaseResource(connection);
				dbConnection.releaseResource(statement);
				dbConnection.releaseResource(resultSet);

			}
			
		return pettyCashList;
	}

	@Override
	public String getTotalCashPayment(Date currentDate) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
	Double totalCashPayment=0.00;
	connection = dbConnection.getConnection();

		String query = "SELECT * FROM petty_cash_expense WHERE category_id NOT IN(2,41) AND entry_date=?";

		try {

			statement = connection.prepareStatement(query);
			statement.setDate(1, currentDate);
			
			rs = statement.executeQuery();
			while (rs.next()) {
				totalCashPayment=totalCashPayment+rs.getDouble("amount");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return totalCashPayment.toString();
	}

	@Override
	public String getOfficeExpenses(Date currentDate) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
	Double totalOfficeExpenses=0.00;
	connection = dbConnection.getConnection();

		String query = "SELECT * FROM petty_cash_expense WHERE category_id=41 AND entry_date=?";

		try {

			statement = connection.prepareStatement(query);
			statement.setDate(1, currentDate);
			
			rs = statement.executeQuery();
			while (rs.next()) {
				totalOfficeExpenses=totalOfficeExpenses+rs.getDouble("amount");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return totalOfficeExpenses.toString();
	}

	@Override
	public String getTotalAdvance(Date currentDate) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
	Double totalTotalAdvance=0.00;
	connection = dbConnection.getConnection();

		String query = "SELECT * FROM petty_cash_expense WHERE category_id=2 AND entry_date=?";

		try {

			statement = connection.prepareStatement(query);
			statement.setDate(1, currentDate);
			
			rs = statement.executeQuery();
			while (rs.next()) {
				totalTotalAdvance=totalTotalAdvance+rs.getDouble("amount");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return totalTotalAdvance.toString();
	}

	@Override
	public JsonArray getDayEndSummary(Date currentDate) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		JsonArray dayEndSummaryList = new JsonArray();
		
		long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
		long MILLIS_IN_2_DAY = 1000 * 60 * 60 * 24*2;
		Date previousDay = new Date(currentDate.getTime() -MILLIS_IN_A_DAY );
		Date backTopreviousDay =  new Date(currentDate.getTime() -MILLIS_IN_2_DAY );
		try {
			connection=dbConnection.getConnection();
			String query = "SELECT "
					+ "IFNULL((SELECT getOpening('"+currentDate+"')),0) AS petty_opening_balance " + 
					",IFNULL((select SUM(nett_amount) FROM v_dayEndReport WHERE txn_date< '"+currentDate+"' AND payment_mode='CASH' ),0)AS txn_opening_balance" +
					" ,IFNULL((select SUM(nett_amount) FROM v_dayEndReport WHERE txn_date= '"+currentDate+"' AND payment_mode='COMPLEMENTARY' ),0)AS complementary_total"+
					" ,IFNULL((select ABS(SUM(nett_amount)) FROM v_dayEndReport WHERE txn_date= '"+currentDate+"' AND acc_mst_code='REFUND' ),0)AS refund_total"+
					"\r\n" +
					",IFNULL((select SUM(nett_amount) FROM v_dayEndReport WHERE txn_date='"+currentDate+"' AND payment_mode='CASH'),0)AS 'cash_payment'  \r\n" + 
					"\r\n" + 
					",IFNULL((select SUM(nett_amount) FROM v_dayEndReport WHERE txn_date='"+currentDate+"' AND payment_mode NOT IN ('CASH','COMPLEMENTARY','' )),0)AS 'non_cash_payment'\r\n" + 
					"\r\n" + 
					"\r\n" + 
					",IFNULL((SELECT SUM(TABLE1.nett_amount) \r\n" + 
					"FROM(\r\n" + 
					"SELECT tx.nett_amount,(case when (`tx`.`payment_mode` = 1) then 'CASH' when (`tx`.`payment_mode` = 2) then 'CARD' when (`tx`.`payment_mode` = 3) then 'ONLINE TRANSFER' when (`tx`.`payment_mode` = 4) then 'DD' when (`tx`.`payment_mode` = 5) then 'COMPANY' when (`tx`.`payment_mode` = 6) then 'COMPLEMENTARY' end) AS `payment_mode`\r\n" + 
					" FROM resv_hdr  rsv INNER JOIN txn tx ON rsv.folio_bind_no = tx.folio_bind_no AND  rsv.status IN('0','1' ) \r\n" + 
					"AND tx.acc_mst_code ='DEPOSIT' AND  tx.txn_status = 1 AND  tx.txn_date = '"+currentDate+"')AS  TABLE1 WHERE TABLE1.payment_mode = 'CASH'),0) AS 'cash_booking'\r\n" + 
					"\r\n" + 
					",IFNULL((SELECT SUM(TABLE1.nett_amount) \r\n" + 
					"FROM(\r\n" + 
					"SELECT tx.nett_amount,(case when (`tx`.`payment_mode` = 1) then 'CASH' when (`tx`.`payment_mode` = 2) then 'CARD' when (`tx`.`payment_mode` = 3) then 'ONLINE TRANSFER' when (`tx`.`payment_mode` = 4) then 'DD' when (`tx`.`payment_mode` = 5) then 'COMPANY' when (`tx`.`payment_mode` = 6) then 'COMPLEMENTARY' end) AS `payment_mode`\r\n" + 
					" FROM resv_hdr  rsv INNER JOIN txn tx ON rsv.folio_bind_no = tx.folio_bind_no AND  rsv.status IN('0','1' ) \r\n" + 
					"AND tx.acc_mst_code ='DEPOSIT' AND  tx.txn_status = 1 AND tx.txn_date = '"+currentDate+"')AS  TABLE1 WHERE TABLE1.payment_mode NOT IN ('CASH','' )),0) AS 'non_cash_booking'\r\n" + 
					"\r\n" + 
					",IFNULL((select SUM(amount) FROM(\r\n" + 
					"SELECT T.entry_date,T.voucher_name,cat2.name ,T.amount FROM(SELECT exp.entry_date,  exp.voucher_name, cat.parent_id,\r\n" + 
					"exp.amount FROM petty_cash_expense exp\r\n" + 
					"INNER JOIN petty_cash_category cat ON  exp.category_id =cat.id AND exp.is_deleted = 0 )T\r\n" + 
					"INNER JOIN petty_cash_category cat2 ON T.parent_id = cat2.id)T2 WHERE T2.voucher_name ='PAYMENT' \r\n" + 
					"AND T2.entry_date='"+currentDate+"' AND T2.name !='Cash_To_Office' ),0) AS 'total_expense'\r\n" + 
					"\r\n" + 
					",IFNULL((select SUM(amount) FROM(\r\n" + 
					"SELECT T.entry_date,T.voucher_name,cat2.name ,T.amount FROM(SELECT exp.entry_date,  exp.voucher_name, cat.parent_id,\r\n" + 
					"exp.amount FROM petty_cash_expense exp\r\n" + 
					"INNER JOIN petty_cash_category cat ON  exp.category_id =cat.id AND exp.is_deleted = 0 )T\r\n" + 
					"INNER JOIN petty_cash_category cat2 ON T.parent_id = cat2.id)T2 WHERE T2.voucher_name ='CONTRA' \r\n" + 
					"AND T2.entry_date='"+currentDate+"' ),0) AS 'total_contra'\r\n" + 
					"\r\n" +
					",IFNULL((select SUM(amount) FROM(\r\n" + 
					"SELECT T.entry_date,T.voucher_name,cat2.name ,T.amount FROM(SELECT exp.entry_date,  exp.voucher_name, cat.parent_id,\r\n" + 
					"exp.amount FROM petty_cash_expense exp\r\n" + 
					"INNER JOIN petty_cash_category cat ON  exp.category_id =cat.id AND exp.is_deleted = 0  )T\r\n" + 
					"INNER JOIN petty_cash_category cat2 ON T.parent_id = cat2.id)T2 WHERE T2.voucher_name ='PAYMENT' \r\n" + 
					"AND T2.entry_date='"+currentDate+"' AND T2.name ='Cash_To_Office' ),0) AS 'Cash_To_Office'\r\n" + 
					"\r\n" + 
					",IFNULL((SELECT resv_deposit + checkin_deposit  FROM(\r\n" + 
					"SELECT IFNULL((SELECT SUM(TABLE1.nett_amount) \r\n" + 
					"FROM(\r\n" + 
					"SELECT tx.nett_amount,(case when (`tx`.`payment_mode` = 1) then 'CASH' when (`tx`.`payment_mode` = 2) then 'CARD' when (`tx`.`payment_mode` = 3) then 'ONLINE TRANSFER' when (`tx`.`payment_mode` = 4) then 'DD' when (`tx`.`payment_mode` = 5) then 'COMPANY' when (`tx`.`payment_mode` = 6) then 'COMPLEMENTARY' end) AS `payment_mode`\r\n" + 
					" FROM resv_hdr  rsv INNER JOIN txn tx ON rsv.folio_bind_no = tx.folio_bind_no AND  rsv.status IN('0','1' ) \r\n" + 
					"AND tx.acc_mst_code ='DEPOSIT' AND  tx.txn_status = 1 )AS  TABLE1),0) AS 'resv_deposit'\r\n" + 
					",IFNULL((select SUM(nett_amount) FROM v_dayEndReport WHERE txn_date='"+currentDate+"' AND acc_mst_code = 'DEPOSIT'),0)AS 'checkin_deposit')T),0) AS 'Total_deposit' \r\n" + 
					",IFNULL((SELECT SUM(amount)   FROM v_dayEndReport vtxn INNER JOIN acc_mst acc ON\r\n" + 
					"    vtxn.acc_mst_id = acc.id\r\n" + 
					"WHERE txn_date='"+currentDate+"' AND acc.sysdef_acc_type_id = 3),0)AS 'total_food_cost' "
					+ ",IFNULL((SELECT SUM(CONTRA_AMOUNT)-SUM(PAYMENT_AMOUNT) \r\n" + 
					"FROM(\r\n" + 
					"SELECT  entry_date,CASE WHEN voucher_name = 'PAYMENT' THEN AMOUNT ELSE 0 END AS PAYMENT_AMOUNT,\r\n" + 
					"CASE WHEN voucher_name = 'CONTRA' THEN AMOUNT ELSE 0 END AS CONTRA_AMOUNT \r\n" + 
					" FROM petty_cash_expense\r\n" + 
					" WHERE entry_date = '"+currentDate+"' AND  is_deleted = 0 AND voucher_name IN('PAYMENT','CONTRA') )T\r\n" + 
					" GROUP BY entry_date),0) AS  petty_closing_amount";
			statement = connection.prepareStatement(query);
			rs=statement.executeQuery();
			while(rs.next()) {
				JsonObject obj = new JsonObject();
				//opening balance  = petty opening + cashPayment in previous date + booking payment in cash Previous date
				// Double cash_opening_bal = rs.getDouble("petty_opening_balance") +  rs.getDouble("cash_payment_prev")+ rs.getDouble("cash_booking_prev")+rs.getDouble("cash_payment_prevToBack")+rs.getDouble("cash_booking_prevToBack");
				Double cash_opening_bal = rs.getDouble("petty_opening_balance") +  rs.getDouble("txn_opening_balance");
				 obj.addProperty("cash_opening_balance", cash_opening_bal);
				obj.addProperty("total_cash_payment",  rs.getDouble("cash_payment"));
				obj.addProperty("total_non_cash_payment",  rs.getDouble("non_cash_payment"));
				obj.addProperty("total_cash_booking",  rs.getDouble("cash_booking"));
				obj.addProperty("total_non_cash_booking",  rs.getDouble("non_cash_booking"));
				obj.addProperty("total_petty_expense",  rs.getDouble("total_expense"));
				obj.addProperty("total_cash_to_office",  rs.getDouble("Cash_To_Office"));
				obj.addProperty("total_deposit",  rs.getDouble("Total_deposit"));
				obj.addProperty("total_food_cost",  rs.getDouble("total_food_cost"));
				obj.addProperty("petty_closing_amount",  rs.getDouble("petty_closing_amount"));
				obj.addProperty("refund_total",  rs.getDouble("refund_total"));
				obj.addProperty("complementary_total",  rs.getDouble("complementary_total"));
				obj.addProperty("total_contra",  rs.getDouble("total_contra"));
				
				
				
				
				
				dayEndSummaryList.add(obj);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(connection);
			
		}
	
	
		//companyList = new JsonArray();
		//companyList.add(companyObj);
		
		return dayEndSummaryList;
	}

	@Override
	public String getFinancialYear(Date fromDate) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String fin_code = "";
		connection = dbConnection.getConnection();

		String query = "SELECT fin_Code FROM financial_year WHERE '"+fromDate+"' BETWEEN  from_date and to_date";

		try {

			statement = connection.prepareStatement(query);
			
			
			rs = statement.executeQuery();
			while (rs.next()) {
				fin_code=rs.getString("fin_code");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fin_code;
	}

	@Override
	public Date getFinancialYearFrom(Date fromDate) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Date finYFrom = null;
		connection = dbConnection.getConnection();

		String query = "SELECT from_date FROM financial_year WHERE '"+fromDate+"' BETWEEN  from_date and to_date";

		try {

			statement = connection.prepareStatement(query);
			
			
			rs = statement.executeQuery();
			while (rs.next()) {
				finYFrom=rs.getDate("from_date");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return finYFrom;
	}

}
