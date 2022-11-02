package com.indocosmo.pms.web.reservation_test.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.common.setttings.Rounding;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.discount.model.Discount;
import com.indocosmo.pms.web.discount.service.DiscountService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.reservation.controller.ReservationController;
import com.indocosmo.pms.web.reservation.dao.ReservationDAOImp;
import com.indocosmo.pms.web.reservation.model.Cancelreason;
import com.indocosmo.pms.web.reservation_test.model.AvailableRoomTypes;
import com.indocosmo.pms.web.reservation_test.model.CheckinDiscount;
import com.indocosmo.pms.web.reservation_test.model.ResvDtl;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
import com.indocosmo.pms.web.reservation_test.model.ResvRate;
import com.indocosmo.pms.web.reservation_test.model.ResvRoom;
import com.indocosmo.pms.web.reservation_test.model.ResvRoomRatePlans;
import com.indocosmo.pms.web.reservation_test.model.RoomRateDetailsCheck;
import com.indocosmo.pms.web.reservation_test.model.RoomRateEdited;
import com.indocosmo.pms.web.room.dao.RoomDAO;
import com.indocosmo.pms.web.room.model.Room;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;
import com.thoughtworks.xstream.XStream;

@Repository
public class ReservationDaoTestImpl implements ReservationDaoTest {
	@Autowired
	DiscountService discountService;

	@Autowired
	SystemSettingsService systemSettingsService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	RoomDAO roomDao;

	@Autowired
	JdbcTemplate jdbcTemplate;

	DbConnection dbConnection = null;

	public ReservationDaoTestImpl() {
		dbConnection = new DbConnection();
	}

	public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

	public List<AvailableRoomTypes> getRoomAvailability(Date chInDate, int night, int rooms) {
		List<AvailableRoomTypes> availableRoomList = new ArrayList<AvailableRoomTypes>();
		AvailableRoomTypes available = new AvailableRoomTypes();
		Connection con = null;
		CallableStatement st = null;
		ResultSet rs = null;
		try {
			con = dbConnection.getConnection();
			st = con.prepareCall("{call GetRoomAvailability(?,?)}");
			st.setDate(1, chInDate);
			st.setInt(2, night);
			rs = st.executeQuery();
			String code = "";
			int id = 0, availableRooms = 0, curRooms = 0;
			Map<Integer, ArrayList<String>> roomTypes = new HashMap<Integer, ArrayList<String>>();
			ArrayList<String> roomTypeRow = null;
			HashMap hm = new HashMap();
			while (rs.next()) {
				availableRooms = rs.getInt("num_available");
				code = rs.getString("room_type_code");
				if (availableRooms == 0) {
					hm.put(code, availableRooms);
				}
			}
	//		rs.beforeFirst();
			while (rs.next()) {
				code = rs.getString("room_type_code");
				availableRooms = rs.getInt("num_available");
				if (code != null && availableRooms > 0 && !hm.containsKey(code)) {
					id = rs.getInt("room_type_id");
					roomTypeRow = roomTypes.get(id);
					if (roomTypeRow != null) {
						curRooms = Integer.parseInt(roomTypeRow.get(8));
						if (availableRooms < curRooms) {
							roomTypeRow.set(8, Integer.toString(availableRooms));
						}
					} else {
						roomTypeRow = new ArrayList<String>();
						roomTypeRow.add(Integer.toString(id));
						roomTypeRow.add(code);
						roomTypeRow.add(rs.getString("room_type_name"));
						roomTypeRow.add(rs.getString("is_support_single_occ"));
						roomTypeRow.add(rs.getString("is_support_double_occ"));
						roomTypeRow.add(rs.getString("is_support_triple_occ"));
						roomTypeRow.add(rs.getString("is_support_quad_occ"));
						roomTypeRow.add(rs.getString("num_rooms"));
						roomTypeRow.add(Integer.toString(availableRooms));
						roomTypeRow.add(rs.getString("image"));
						roomTypeRow.add(rs.getString("display_order"));
						roomTypes.put(id, roomTypeRow);
					}
				}
			}
			Iterator itr = roomTypes.entrySet().iterator();
			Map.Entry<Integer, ArrayList<String>> pair = null;
			while (itr.hasNext()) {
				pair = (Entry<Integer, ArrayList<String>>) itr.next();
				roomTypeRow = pair.getValue();
				available = new AvailableRoomTypes();
				available.setRoomTypeId(Integer.parseInt(roomTypeRow.get(0)));
				available.setRoomTypeCode(roomTypeRow.get(1));
				available.setRoomTypeName(roomTypeRow.get(2));
				available.setOcc1(Integer.parseInt(roomTypeRow.get(3)) != 0);
				available.setOcc2(Integer.parseInt(roomTypeRow.get(4)) != 0);
				available.setOcc3(Integer.parseInt(roomTypeRow.get(5)) != 0);
				available.setOcc4(Integer.parseInt(roomTypeRow.get(6)) != 0);
				available.setTotalRoom(Integer.parseInt(roomTypeRow.get(7)));
				available.setAvailRoom(Integer.parseInt(roomTypeRow.get(8)));
				if (roomTypeRow.get(9) != null && roomTypeRow.get(9) != "")
					available.setImage(roomTypeRow.get(9));
				available.setDisplayOrder(Integer.parseInt(roomTypeRow.get(10)));
				availableRoomList.add(available);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getRoomAvailability()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
		finally {
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);
		}
		return availableRoomList;
	}

	public List<ResvRoomRatePlans> getRoomRates(Date arrDate, int nights, int corpId, String roomType, int rate_id) {
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		List<ResvRoomRatePlans> ratePlanList = new ArrayList<ResvRoomRatePlans>();
		ResvRoomRatePlans ratePlans = new ResvRoomRatePlans();
		try {
			String procedure = "{call GetRoomRates(?,?,?,?,?)}";
			con = dbConnection.getConnection();
			stmt = con.prepareCall(procedure);
			stmt.setDate(1, arrDate);
			stmt.setInt(2, nights);
			stmt.setInt(3, corpId);
			stmt.setString(4, roomType);
			stmt.setInt(5, rate_id);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				ratePlans = new ResvRoomRatePlans();
				ratePlans.setRoomTypeId(resultSet.getInt("room_type_id"));
				ratePlans.setRoomTypeCode(resultSet.getString("room_type_code"));
				ratePlans.setOcc1(resultSet.getBoolean("singleOcc"));
				ratePlans.setOcc2(resultSet.getBoolean("doubleOcc"));
				ratePlans.setOcc3(resultSet.getBoolean("tripleOcc"));
				ratePlans.setOcc4(resultSet.getBoolean("quadOcc"));
				ratePlans.setRateId(resultSet.getInt("rate_id"));
				ratePlans.setRateCode(resultSet.getString("rate_code"));
				ratePlans.setRateName(resultSet.getString("rate_name"));
				ratePlans.setRateType(resultSet.getInt("rate_type"));
				ratePlans.setMealPlan(resultSet.getString("meal_plans"));
				ratePlans.setTaxIncl(resultSet.getBoolean("is_tax_included"));
				ratePlans.setOpen(resultSet.getBoolean("isOpen"));
				ratePlans.setSource_rate_hdr_id(resultSet.getInt("source_rate_hdr_id"));
				ratePlans.setTotalOcc1Rate(resultSet.getDouble("total_single_rate"));
				ratePlans.setTotalOcc2Rate(resultSet.getDouble("total_double_rate"));
				ratePlans.setTotalOcc3Rate(resultSet.getDouble("total_triple_rate"));
				ratePlans.setTotalOcc4Rate(resultSet.getDouble("total_quad_rate"));
				// ratePlans.setValidity_from(resultSet.getString("validity_from"));
				// ratePlans.setValidity_to(resultSet.getString("validity_to"));
				ratePlanList.add(ratePlans);
			}
		} catch (Exception e) {
			logger.error("Method : getRoomRates()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(stmt);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(con);
		}
		return ratePlanList;
	}

	public JsonObject getRoomRateDetails(RoomRateDetailsCheck rateDtls) {
		Connection connection = dbConnection.getConnection();
		CallableStatement preparedStatement;
		CallableStatement preparedStatementStax;
		ResultSet rs = null, rsSt = null;
		String procedureRoomRate = "{call GetRoomRateDetails(?,?,?,?,?,?,?,?)}";
		String procedureservTax = "CALL GetServiceTaxAmounts(?,?)";
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
		JsonObject jsonContentOfRateDetails = null;
		JsonArray jsonContentPerOccupncy = new JsonArray();
		double totalRate = 0.0, totalSTax = 0.0, totalTax = 0.0, totalDisc = 0.0, totalSCharge = 0.0;
		int roomTypeId = 0;
		boolean taxIncl = false;
		String roomType = "";
		JsonObject jObject = new JsonObject();
		try {
			preparedStatement = connection.prepareCall(procedureRoomRate);
			preparedStatement.setString(1, simpleDateformat.format(rateDtls.getArrDate()));
			preparedStatement.setInt(2, rateDtls.getNumRooms());
			preparedStatement.setInt(3, rateDtls.getNumNights());
			preparedStatement.setInt(4, rateDtls.getRateId());
			preparedStatement.setInt(5, rateDtls.getOccupancy());
			preparedStatement.setInt(6, rateDtls.getDiscId());
			preparedStatement.setBigDecimal(7, rateDtls.getOpenDiscount());
			preparedStatement.setBoolean(8, commonSettings.isServiceChargeApplicable);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				jsonContentOfRateDetails = new JsonObject();
				jsonContentOfRateDetails.addProperty("resvStatus", 0);
				jsonContentOfRateDetails.addProperty("narration", rs.getString("narration"));
				jsonContentOfRateDetails.addProperty("total", rs.getString("total"));
				jsonContentOfRateDetails.addProperty("tax1_pc", rs.getBigDecimal("tax1_percentage"));
				jsonContentOfRateDetails.addProperty("tax2_pc", rs.getBigDecimal("tax2_percentage"));
				jsonContentOfRateDetails.addProperty("tax3_pc", rs.getBigDecimal("tax3_percentage"));
				jsonContentOfRateDetails.addProperty("tax4_pc", rs.getBigDecimal("tax4_percentage"));
				jsonContentOfRateDetails.addProperty("tax1_amount", rs.getBigDecimal("tax1_amount"));
				jsonContentOfRateDetails.addProperty("tax2_amount", rs.getBigDecimal("tax2_amount"));
				jsonContentOfRateDetails.addProperty("tax3_amount", rs.getBigDecimal("tax3_amount"));
				jsonContentOfRateDetails.addProperty("tax4_amount", rs.getBigDecimal("tax4_amount"));
				jsonContentOfRateDetails.addProperty("service_charge_pc",
						rs.getBigDecimal("service_charge_percentage"));
				jsonContentOfRateDetails.addProperty("service_charge_amount",
						rs.getBigDecimal("service_charge_amount"));
				jsonContentOfRateDetails.addProperty("tax", rs.getBigDecimal("tax"));
				jsonContentOfRateDetails.addProperty("night", rs.getInt("night"));
				jsonContentOfRateDetails.addProperty("date", rs.getString("date"));
				jsonContentOfRateDetails.addProperty("room_type_id", rs.getInt("room_type_id"));
				jsonContentOfRateDetails.addProperty("room_type_code", rs.getString("room_type_code"));
				jsonContentOfRateDetails.addProperty("rate_id", rs.getInt("rate_id"));
				jsonContentOfRateDetails.addProperty("rate_type", rs.getInt("rate_type"));
				jsonContentOfRateDetails.addProperty("rate_code", rs.getString("rate_code"));
				jsonContentOfRateDetails.addProperty("rate_name", rs.getString("rate_name"));
				jsonContentOfRateDetails.addProperty("rate_description", rs.getString("rate_description"));
				jsonContentOfRateDetails.addProperty("occupancy", rs.getInt("occupancy"));
				jsonContentOfRateDetails.addProperty("num_rooms", rs.getInt("num_rooms"));
				jsonContentOfRateDetails.addProperty("charges", rs.getBigDecimal("charges"));
				jsonContentOfRateDetails.addProperty("is_tax_included", rs.getBoolean("is_tax_included"));
				jsonContentOfRateDetails.addProperty("narration", rs.getString("narration"));
				jsonContentOfRateDetails.addProperty("disc_amount", rs.getBigDecimal("disc_amount"));
				BigDecimal disc = BigDecimal.valueOf(0);
				if (rs.getBigDecimal("disc_amount") != null) {
					disc = rs.getBigDecimal("disc_amount");
				}
				roomTypeId = rs.getInt("room_type_id");
				roomType = rs.getString("room_type_code");
				totalDisc += disc.doubleValue();
				totalRate += rs.getDouble("total");
				totalTax += rs.getDouble("tax");
				totalSCharge += rs.getDouble("service_charge_amount");
				taxIncl = rs.getBoolean("is_tax_included");
				if (commonSettings.isServiceTaxIncluded) {
					BigDecimal total = rs.getBigDecimal("total").subtract(rs.getBigDecimal("tax")).subtract(disc);
					preparedStatementStax = connection.prepareCall(procedureservTax);
					preparedStatementStax.setInt(1, commonSettings.serviceTaxId);
					preparedStatementStax.setBigDecimal(2, total);
					rsSt = preparedStatementStax.executeQuery();
					if (rsSt.next()) {
						jsonContentOfRateDetails.addProperty("service_tax", rsSt.getBigDecimal("tp_total_service_tax"));
					}
					totalSTax += rsSt.getDouble("tp_total_service_tax");
				} else {
					jsonContentOfRateDetails.addProperty("service_tax", 0);
				}
				jsonContentOfRateDetails.addProperty("service_tax_incl", commonSettings.isServiceTaxIncluded);
				jsonContentPerOccupncy.add(jsonContentOfRateDetails);

			}
			jObject.addProperty("roomTypeId", roomTypeId);
			jObject.addProperty("roomTypeCode", roomType);
			jObject.addProperty("numRooms", rateDtls.getNumRooms());
			jObject.addProperty("numNights", rateDtls.getNumNights());
			jObject.addProperty("totalRate", Rounding.nRound(totalRate));
			jObject.addProperty("totalTax", Rounding.nRound(totalTax));
			jObject.addProperty("isTaxIncl", taxIncl);
			jObject.addProperty("totalSTax", Rounding.nRound(totalSTax));
			jObject.addProperty("totalDisc", Rounding.nRound(totalDisc));
			jObject.addProperty("totalSCharge", Rounding.nRound(totalSCharge));
			jObject.add("ratePerOcc", jsonContentPerOccupncy);
		} catch (Exception e) {
			logger.error("Method : getRoomRateDetails()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
		}
		return jObject;
	}

	public int getcounter(int count, String keyName) throws Exception {
		CallableStatement countFunct = null;
		Connection connection = null ;
		ResultSet rs = null;
		int newCount = 0;
		try {
			connection = dbConnection.getConnection();
			String countFunction = "{? = call counter(?)}";
			countFunct = connection.prepareCall(countFunction);
			countFunct.registerOutParameter(count, java.sql.Types.INTEGER);
			countFunct.setString(2, keyName);
			rs = countFunct.executeQuery();
			while (rs.next()) {
				newCount = rs.getInt(1);
			}
		}
		catch (Exception e) {
			logger.error("Method : getcounter()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(countFunct);
			dbConnection.releaseResource(rs);
		}
		return newCount;
	}

	public boolean save(String reserHdr, List<ResvDtl> resvDtlList,JsonArray 
			checkinDiscount) throws Exception {
		Gson gson = new Gson();
		// ResvHdr resvHdr=gson.fromJson(reserHdr, ResvHdr.class);
		ResvHdr resvHdr = new ObjectMapper().readValue(reserHdr, ResvHdr.class);
		// resvdetails=new
		// ObjectMapper().setDateFormat(dateFormatFrom).writeValueAsString(resvHdr);
		RoomRateDetailsCheck roomRateChk = null;
		boolean isSave = true;
		
		String sqlResvHdrInsert = "insert into resv_hdr(resv_no,folio_bind_no,status,min_arr_time,min_arr_date,max_depart_time,max_depart_date,sum_num_rooms,resv_type,payment_source,corporate_id,corporate_name,corporate_address,disc_type,guest_id,salutation,resv_by_first_name,resv_by_last_name,resv_by_address,resv_by_mail,resv_by_phone,num_adults,num_children,remarks,billing_instruction,resv_date,resv_taken_by,resv_for,cut_off_date,pickup_needed,special_requests,num_infants,company,designation,country,state,arriving_from,proceeding_to,meal_plan,dob,gender,guest_name)values"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlResvHdrInsert_pickup = "insert into resv_hdr(resv_no,folio_bind_no,status,min_arr_date,max_depart_date,sum_num_rooms,resv_type,payment_source,corporate_id,corporate_name,corporate_address,disc_type,guest_id,salutation,resv_by_first_name,resv_by_last_name,resv_by_address,resv_by_mail,resv_by_phone,num_adults,num_children,remarks,billing_instruction,resv_date,resv_taken_by,resv_for,cut_off_date,pickup_needed,special_requests,num_infants,pickup_date,pickupTime,pickupLocation,pickupSeats,pickupRemarks,pickup_provider\n"
				+ ")values" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlResvDtlInsert = "insert into resv_dtl(resv_dtl_no, resv_no, room_type_id, room_type_code, arr_date, depart_date, num_nights, num_rooms, rate_type, rate_id, rate_code, rate_description, occupancy, disc_id, disc_code, disc_is_pc, disc_is_open, disc_amount,disc_pc) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlResvRoomInsert = "insert into resv_room(resv_room_no, resv_dtl_no,is_noshow,room_status, first_name, last_name, email, phone, address, num_adults, num_children, num_infants, remarks,gender,nationality,state) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlResvRateInsert = "insert into resv_rate(resv_room_no, night,night_date, room_charge, include_tax, tax1_pc, tax2_pc, tax3_pc, tax4_pc,service_charge_pc, tax1, tax2, tax3, tax4,service_charge) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlFolioInsert = "insert into folio(folio_no, folio_bind_no, resv_no) values(?,?,?)";
		
		String sqlcheckinDiscntInsert = "insert into checkin_discounts(id,resv_no,disc_type,disc_id,disc_code,disc_is_pc,disc_amount,disc_pc,is_open,discount_for) values(?,?,?,?,?,?,?,?,?,?)";
		String countFunction = "{? = call counter(?)}";
		Connection connection = null;
		PreparedStatement psResvHdrInsert = null;
		PreparedStatement psResvDtlInsert = null;
		PreparedStatement psResvRoomInsert = null;
		PreparedStatement psResvRateInsert = null;
		PreparedStatement psFolioInsert = null;
		PreparedStatement pscheckinDiscount = null;
		
		CallableStatement countFunct = null;
		ResultSet rs = null;

		try {
			connection = dbConnection.getConnection();
			connection.setAutoCommit(false);
			countFunct = connection.prepareCall(countFunction);
			countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
			if (resvHdr.isPickupNeeded()) {
				psResvHdrInsert = connection.prepareStatement(sqlResvHdrInsert_pickup);
			} else {
				psResvHdrInsert = connection.prepareStatement(sqlResvHdrInsert);
			}
			
			psResvDtlInsert = connection.prepareStatement(sqlResvDtlInsert);
			psResvRoomInsert = connection.prepareStatement(sqlResvRoomInsert);
			psResvRateInsert = connection.prepareStatement(sqlResvRateInsert);
			psFolioInsert = connection.prepareStatement(sqlFolioInsert);
			if(checkinDiscount.size()!=0) {
				pscheckinDiscount = connection.prepareStatement(sqlcheckinDiscntInsert);
			}
			
			countFunct.setString(2, "folio_bind_no");
			rs = countFunct.executeQuery();
			while (rs.next()) {
				resvHdr.setFolioBindNO(rs.getInt(1));
			}
			countFunct.setString(2, "resv_no");
			rs = countFunct.executeQuery();
			while (rs.next()) {
				resvHdr.setResvNo(rs.getInt(1));
			}
			psResvHdrInsert.setInt(1, resvHdr.getResvNo());
			psResvHdrInsert.setInt(2, resvHdr.getFolioBindNO());
			psResvHdrInsert.setByte(3, resvHdr.getStatus());
			psResvHdrInsert.setTime(4, resvHdr.getMinArrTime());
			//psResvHdrInsert.setDate(5, new java.sql.Date(resvHdr.getMinArrDate().getTime()));
			Timestamp DatetimeArr = new Timestamp(resvHdr.getMinArrDate().getTime()+resvHdr.getMinArrTime().getTime());
			psResvHdrInsert.setTimestamp(5, DatetimeArr);
			psResvHdrInsert.setTime(6, resvHdr.getMaxDepTime());
			Timestamp DatetimeDpt = new Timestamp(resvHdr.getMaxDepartDate().getTime()+resvHdr.getMaxDepTime().getTime());
			psResvHdrInsert.setTimestamp(7, DatetimeDpt);
			//psResvHdrInsert.setDate(7, new java.sql.Date(resvHdr.getMaxDepartDate().getTime()));
			psResvHdrInsert.setInt(8, resvHdr.getNumRooms());
			psResvHdrInsert.setByte(9, resvHdr.getResvType());
			psResvHdrInsert.setInt(10, resvHdr.getPayment_source());
			psResvHdrInsert.setInt(11, resvHdr.getCorporateId());
			psResvHdrInsert.setString(12, resvHdr.getCorporateName());
			psResvHdrInsert.setString(13, resvHdr.getCorporateAddress());
			psResvHdrInsert.setByte(14, resvHdr.getDiscType());
			psResvHdrInsert.setInt(15, resvHdr.getGuestId());
			// String saluation = resvHdr.getSelectedSalutation().toString();
			psResvHdrInsert.setString(16, resvHdr.getSelectedSalutation());
			psResvHdrInsert.setString(17, resvHdr.getResvByFirstName());
			psResvHdrInsert.setString(18, resvHdr.getResvByLastName());
			psResvHdrInsert.setString(19, resvHdr.getResvByAddress());
			psResvHdrInsert.setString(20, resvHdr.getResvByMail());
			psResvHdrInsert.setString(21, resvHdr.getResvByPhone());
			psResvHdrInsert.setByte(22, (byte) resvHdr.getNumAdults());
			psResvHdrInsert.setByte(23, (byte) resvHdr.getNumChildren());
			psResvHdrInsert.setString(24, resvHdr.getRemarks());
			psResvHdrInsert.setString(25, resvHdr.getBillingInstruction());
			psResvHdrInsert.setDate(26, new Date(commonSettings.getHotelDate().getTime()));
			psResvHdrInsert.setInt(27, resvHdr.getResvTakenBy());
			psResvHdrInsert.setString(28, resvHdr.getResvFor());
			psResvHdrInsert.setDate(29, new java.sql.Date(resvHdr.getCutOffDate().getTime()));
			psResvHdrInsert.setBoolean(30, resvHdr.isPickupNeeded());
			psResvHdrInsert.setString(31, resvHdr.getSpecialRequests());
			psResvHdrInsert.setInt(32, resvHdr.getNumInfants());
			psResvHdrInsert.setString(33, resvHdr.getResvByCompany());
			psResvHdrInsert.setString(34, resvHdr.getResvByDesignation());
			psResvHdrInsert.setString(35, resvHdr.getNationality());
			psResvHdrInsert.setString(36, resvHdr.getState());
			psResvHdrInsert.setString(37, resvHdr.getResvByArriving());
			psResvHdrInsert.setString(38, resvHdr.getResvByProceeding());
			psResvHdrInsert.setInt(39, resvHdr.getMealPlan());
			 if(resvHdr.getDob()==null) {
			 psResvHdrInsert.setNull(40, Types.DATE);
			}else {
			psResvHdrInsert.setDate(40, new java.sql.Date(resvHdr.getDob().getTime()));
			}
			psResvHdrInsert.setString(41, resvHdr.getGender());
			if (resvHdr.isPickupNeeded()) {
				psResvHdrInsert.setDate(31, new java.sql.Date(resvHdr.getPickupDate().getTime()));
				psResvHdrInsert.setString(32, resvHdr.getPickupTime());
				psResvHdrInsert.setString(33, resvHdr.getPickupLocation());
				psResvHdrInsert.setInt(34, resvHdr.getPickupSeats());
				psResvHdrInsert.setString(35, resvHdr.getPickupRemarks());
				psResvHdrInsert.setInt(36, resvHdr.getPickupProvider());
			}
			psResvHdrInsert.setString(42, resvHdr.getGuestName());
			for (ResvDtl resvdtl : resvDtlList) {
				if (resvdtl != null) {
					resvdtl.setResvNo(resvHdr.getResvNo());
					countFunct.setString(2, "resv_dtl_no");
					rs = countFunct.executeQuery();
					while (rs.next()) {
						resvdtl.setResvDtlNo(rs.getInt(1));
					}
					resvdtl.setDepartDate(resvHdr.getMaxDepartDate());
					roomRateChk = new RoomRateDetailsCheck();
					// roomRateChk.setArrDate(resvdtl.getArrDate());
					roomRateChk.setArrDate(resvHdr.getMinArrDate());
					roomRateChk.setDiscId(resvdtl.getDiscId());
					roomRateChk.setNumNights(resvdtl.getNumNights());
					roomRateChk.setNumRooms(1);// resvdtl.getNumRooms() room rate to be calculated for individual rooms
					roomRateChk.setOccupancy(resvdtl.getOccupancy());
					roomRateChk.setRateId(resvdtl.getRateId());
					roomRateChk.setOpenDiscount(resvdtl.getOpenDiscount());
					JsonObject rateObj = getRoomRateDetails(roomRateChk);
					JsonArray ratePerOcc = rateObj.get("ratePerOcc").getAsJsonArray();
					psResvDtlInsert.setInt(1, resvdtl.getResvDtlNo());
					psResvDtlInsert.setInt(2, resvHdr.getResvNo());
					psResvDtlInsert.setInt(3, rateObj.get("roomTypeId").getAsInt());
					psResvDtlInsert.setString(4, rateObj.get("roomTypeCode").getAsString());
					//psResvDtlInsert.setDate(5, new java.sql.Date(resvdtl.getArrDate().getTime()));
					//psResvDtlInsert.setDate(6, new java.sql.Date(resvdtl.getDepartDate().getTime()));
					psResvDtlInsert.setDate(5, new java.sql.Date(resvHdr.getMinArrDate().getTime()));
					psResvDtlInsert.setDate(6, new java.sql.Date(resvHdr.getMaxDepartDate().getTime()));
					psResvDtlInsert.setByte(7, resvdtl.getNumNights());
					psResvDtlInsert.setShort(8, resvdtl.getNumRooms());
					psResvDtlInsert.setByte(9, ratePerOcc.get(0).getAsJsonObject().get("rate_type").getAsByte());
					psResvDtlInsert.setInt(10, resvdtl.getRateId());
					psResvDtlInsert.setString(11, ratePerOcc.get(0).getAsJsonObject().get("rate_code").getAsString());
					if (ratePerOcc.get(0).getAsJsonObject().get("rate_description").isJsonNull()) {
						psResvDtlInsert.setString(12, null);
					} else {
						psResvDtlInsert.setString(12,
								ratePerOcc.get(0).getAsJsonObject().get("rate_description").getAsString());
					}

					psResvDtlInsert.setByte(13, resvdtl.getOccupancy());
					psResvDtlInsert.setInt(14, resvdtl.getDiscId());
					if (resvdtl.getDiscId() != 0) {
						Discount disc = discountService.getRecord(resvdtl.getDiscId());
						if (disc.getIsPc()) {
							if (disc.getIsOpen()) {
								resvdtl.setDiscPc(resvdtl.getOpenDiscount().doubleValue());
							} else {
								resvdtl.setDiscPc(disc.getDiscPc().doubleValue());
							}
						} else {
							if (disc.getIsOpen()) {
								resvdtl.setDiscAmount(resvdtl.getOpenDiscount().doubleValue());
							} else {
								resvdtl.setDiscAmount(disc.getDiscAmount().doubleValue());
							}
						}
						resvdtl.setDiscCode(disc.getCode());
						resvdtl.setDiscIsPc(disc.getIsPc());
						resvdtl.setDiscIsOpen(disc.getIsOpen());
					}
					psResvDtlInsert.setString(15, resvdtl.getDiscCode());
					psResvDtlInsert.setBoolean(16, resvdtl.isDiscIsPc());
					psResvDtlInsert.setBoolean(17, resvdtl.isDiscIsOpen());
					psResvDtlInsert.setBigDecimal(18, BigDecimal.valueOf(resvdtl.getDiscAmount()));
					psResvDtlInsert.setBigDecimal(19, BigDecimal.valueOf(resvdtl.getDiscPc()));
					psResvDtlInsert.addBatch();
					int roomCount = resvdtl.getNumRooms();
					while (roomCount > 0) {
						int rsvRoomNo = 0;
						countFunct.setString(2, "resv_room_no");
						rs = countFunct.executeQuery();
						while (rs.next()) {
							rsvRoomNo = rs.getInt(1);
						}

						System.out.println("room number " + rsvRoomNo);

						psResvRoomInsert.setInt(1, rsvRoomNo);
						psResvRoomInsert.setInt(2, resvdtl.getResvDtlNo());
						psResvRoomInsert.setBoolean(3, false);
						psResvRoomInsert.setInt(4, ReservationStatus.UNCONFIRMED.getCode());
						psResvRoomInsert.setString(5, resvHdr.getResvByFirstName());
						psResvRoomInsert.setString(6, resvHdr.getResvByLastName());
						psResvRoomInsert.setString(7, resvHdr.getResvByMail());
						psResvRoomInsert.setString(8, resvHdr.getResvByPhone());
						psResvRoomInsert.setString(9, resvHdr.getResvByAddress());
						psResvRoomInsert.setInt(10, resvHdr.getNumAdults());
						psResvRoomInsert.setInt(11, resvHdr.getNumChildren());
						psResvRoomInsert.setInt(12, resvHdr.getNumInfants());
						psResvRoomInsert.setString(13, resvHdr.getRemarks());
						psResvRoomInsert.setString(14, resvHdr.getGender());
						psResvRoomInsert.setString(15, resvHdr.getNationality());
						psResvRoomInsert.setString(16, resvHdr.getState());
						psResvRoomInsert.addBatch();
						for (JsonElement rateJE : ratePerOcc) {
							psResvRateInsert.setInt(1, rsvRoomNo);
							psResvRateInsert.setByte(2, rateJE.getAsJsonObject().get("night").getAsByte());
							psResvRateInsert.setDate(3, new Date(resvHdr.getMinArrDate().getTime()
									+ (((rateJE.getAsJsonObject().get("night").getAsByte()) - 1)) * 86400000));
							psResvRateInsert.setBigDecimal(4,
									rateJE.getAsJsonObject().get("charges").getAsBigDecimal());
							psResvRateInsert.setBoolean(5,
									rateJE.getAsJsonObject().get("is_tax_included").getAsBoolean());
							psResvRateInsert.setBigDecimal(6,
									rateJE.getAsJsonObject().get("tax1_pc").getAsBigDecimal());
							psResvRateInsert.setBigDecimal(7,
									rateJE.getAsJsonObject().get("tax2_pc").getAsBigDecimal());
							psResvRateInsert.setBigDecimal(8,
									rateJE.getAsJsonObject().get("tax3_pc").getAsBigDecimal());
							psResvRateInsert.setBigDecimal(9,
									rateJE.getAsJsonObject().get("tax4_pc").getAsBigDecimal());
							psResvRateInsert.setBigDecimal(10,
									rateJE.getAsJsonObject().get("service_charge_pc").getAsBigDecimal());
							psResvRateInsert.setBigDecimal(11,
									rateJE.getAsJsonObject().get("tax1_amount").getAsBigDecimal());
							psResvRateInsert.setBigDecimal(12,
									rateJE.getAsJsonObject().get("tax2_amount").getAsBigDecimal());
							psResvRateInsert.setBigDecimal(13,
									rateJE.getAsJsonObject().get("tax3_amount").getAsBigDecimal());
							psResvRateInsert.setBigDecimal(14,
									rateJE.getAsJsonObject().get("tax4_amount").getAsBigDecimal());
							psResvRateInsert.setBigDecimal(15,
									rateJE.getAsJsonObject().get("service_charge_amount").getAsBigDecimal());
							psResvRateInsert.addBatch();
						}
						roomCount = roomCount - 1;
					}
				}
				
				
				//for checkin_discounts  set values
				
				if(checkinDiscount.size()!=0) {
					int roomCount = resvdtl.getNumRooms();
					while (roomCount > 0) {
					for(int i = 0; i<checkinDiscount.size() ;i++) {
						JsonObject discnt = (JsonObject) checkinDiscount.get(i);
						int discId = discnt.get("discId").getAsInt();
					
						countFunct.setString(2, "checkin_discounts");
						rs = countFunct.executeQuery();
						while (rs.next()) {
							pscheckinDiscount.setInt(1,rs.getInt(1));
						}
						pscheckinDiscount.setInt(2, resvHdr.getResvNo());
						//pscheckinDiscount.setInt(3, resvdtl.getResvDtlNo());
						
							Discount disc = discountService.getRecord(discId);
							if (disc.getIsPc()) {
								if (disc.getIsOpen()) {
									
									pscheckinDiscount.setBigDecimal(8, BigDecimal.valueOf(discnt.get("openDiscount").getAsDouble()));
								} else {
									pscheckinDiscount.setBigDecimal(8, BigDecimal.valueOf(disc.getDiscPc().doubleValue()));
									
								}
								pscheckinDiscount.setBigDecimal(7, BigDecimal.valueOf(0));
							} else {
								pscheckinDiscount.setBigDecimal(8, BigDecimal.valueOf(0));
								if (disc.getIsOpen()) {
									pscheckinDiscount.setBigDecimal(7, discnt.get("openDiscount").getAsBigDecimal());
									
								} else {
									pscheckinDiscount.setBigDecimal(7, BigDecimal.valueOf(disc.getDiscAmount().doubleValue()));
								}
							}
							
							pscheckinDiscount.setInt(3, disc.getDiscType());
							pscheckinDiscount.setInt(4, disc.getId());
							pscheckinDiscount.setString(5, disc.getCode());
							pscheckinDiscount.setBoolean(6, disc.getIsPc());
							
							
							
							pscheckinDiscount.setBoolean(9, disc.getIsOpen());
							pscheckinDiscount.setInt(10, disc.getDiscountFor());
							//pscheckinDiscount.setInt(12, disc.getDiscType());
							pscheckinDiscount.addBatch();
					}
					roomCount = roomCount -1;
				}
			}
				
			}
			psFolioInsert.setInt(1, getcounter(1, "folio_no"));
			psFolioInsert.setInt(2, resvHdr.getFolioBindNO());
			psFolioInsert.setInt(3, resvHdr.getResvNo());
			
			

			psResvHdrInsert.executeUpdate();
			psResvDtlInsert.executeBatch();
			psResvRoomInsert.executeBatch();
			psResvRateInsert.executeBatch();
			psFolioInsert.executeUpdate();
			if(checkinDiscount.size()!=0) {
				pscheckinDiscount.executeBatch();
			}
			//pscheckinDiscount.executeBatch();
			connection.commit();
		} catch (Exception e) {
			logger.error("Method : save()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			connection.rollback();
			isSave = false;
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(psFolioInsert);
			dbConnection.releaseResource(psResvHdrInsert);
			dbConnection.releaseResource(sqlResvHdrInsert_pickup);
			dbConnection.releaseResource(psResvDtlInsert);
			dbConnection.releaseResource(psResvRoomInsert);
			dbConnection.releaseResource(psResvRateInsert);
			dbConnection.releaseResource(countFunct);
			dbConnection.releaseResource(pscheckinDiscount);
			dbConnection.releaseResource(rs);
		}
		return isSave;
	}

	public ResvRoom getRoomData(int resvRoomNo) {
		ResultSet rs = null;
		ResvRoom resvRoom = new ResvRoom();
		PreparedStatement resvRoomPs = null;
		Connection connection =  null;
		String selectQuery = "select `resv_room_no`,`room_number`,`room_status`,salutation,`first_name`,`last_name`,`guest_name`,`email`,`phone`,`address`,`nationality`,state,`passport_no`,`passport_expiry_on`,`gender`,gstno,`remarks`,`num_adults`,`num_children`,`num_infants`,`resv_dtl_no`,customer_image,customer_id_proof from resv_room where `resv_room_no` =?";
		try {
		    connection = dbConnection.getConnection();
			resvRoomPs = connection.prepareStatement(selectQuery);
			resvRoomPs.setInt(1, resvRoomNo);
			rs = resvRoomPs.executeQuery();
			while (rs.next()) {
				resvRoom.setSalutation(rs.getString("salutation"));
				resvRoom.setState(rs.getString("state"));
				resvRoom.setResvRoomNo(rs.getInt("resv_room_no"));
				resvRoom.setRoomNumber(rs.getString("room_number"));
				resvRoom.setRoomStatus(rs.getByte("room_status"));
				resvRoom.setFirstName(rs.getString("first_name"));
				resvRoom.setLastName(rs.getString("last_name"));
				resvRoom.setGuestName(rs.getString("guest_name"));
				resvRoom.setEmail(rs.getString("email"));
				resvRoom.setPhone(rs.getString("phone"));
				resvRoom.setAddress(rs.getString("address"));
				resvRoom.setNationality(rs.getString("nationality"));
				resvRoom.setPassportNo(rs.getString("passport_no"));
				resvRoom.setPassportExpiryOn(rs.getDate("passport_expiry_on"));
				resvRoom.setGender(rs.getString("gender"));
				resvRoom.setGstno(rs.getString("gstno"));
				resvRoom.setRemarks(rs.getString("remarks"));
				resvRoom.setNumAdults(rs.getInt("num_adults"));
				resvRoom.setNumChildren(rs.getInt("num_children"));
				resvRoom.setNumInfants(rs.getInt("num_infants"));
				resvRoom.setResvDtlNo(rs.getInt("resv_dtl_no"));
				resvRoom.setCustomerImage(rs.getString("customer_image"));
				resvRoom.setCustomerIdProof(rs.getString("customer_id_proof"));
			}
		} catch (Exception e) {
			logger.error("Method : getRoomData()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(resvRoomPs);
			dbConnection.releaseResource(rs);
		}
		return resvRoom;
	}
	
	
	
	
	public List<Room> getAvailableRoomData(Date minArrDate, Date maxDepartDate, int roomTypeId, int occupancy) {

		List<Room> availableRooms = new ArrayList<Room>();
		Connection con = null ;
		ResultSet rs = null;
		CallableStatement st = null;
		Room room;
		try {
			con = dbConnection.getConnection();
			// st = con.prepareCall("{call GetVacantRooms(?,?,?,?)}");
			st = con.prepareCall("{call GetvacantandexpectedRooms(?,?,?,?)}");
			st.setDate(1, minArrDate);
			st.setDate(2, maxDepartDate);
			st.setInt(3, roomTypeId);
			st.setInt(4, occupancy);
			rs = st.executeQuery();
			while (rs.next()) {
				// room = roomDao.getRecord(Integer.parseInt(rs.getString("number")));
				room = roomDao.getRecord(rs.getString("number"));
				// room = roomDao.getRecord(rs.getInt("id"));
				room.setHk1_status(rs.getString("hk1_status"));
				room.setRoom_status(rs.getString("room_status"));
				room.setRoomFeatureList(roomDao.getRoomFeatures(room.getRoomFeaturesIds()));
				availableRooms.add(room);
			}
		} catch (Exception e) {
			logger.error("Method : getAvailableRooms()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);
		}
		return availableRooms;
	}

	public boolean updateResvRoom(ResvRoom resvRoom) throws Exception {
		Connection connection = null;
		String sql = "";
		boolean isSave = true;
		PreparedStatement resvRoomPs = null;

		sql = "UPDATE resv_room SET salutation=?, first_name=?,last_name=?,guest_name=?,gender=?,address=?,email=?,phone=?,nationality=?,state=?,passport_no=?,passport_expiry_on=?,remarks=?,room_number=?,customer_image=?,customer_id_proof=?,gstno=?,`num_adults`=?,`num_children`=?,`num_infants`=? WHERE resv_room_no=? and room_status !=?";

		try {
			connection = dbConnection.getConnection();
			connection.setAutoCommit(false);
			resvRoomPs = connection.prepareStatement(sql);
			resvRoomPs.setString(1, resvRoom.getSalutation());
			resvRoomPs.setString(2, resvRoom.getFirstName());
			resvRoomPs.setString(3, resvRoom.getLastName());
			resvRoomPs.setString(4, resvRoom.getGuestName());
			resvRoomPs.setString(5, resvRoom.getGender());
			resvRoomPs.setString(6, resvRoom.getAddress());
			if (resvRoom.getEmail() != null) {
				resvRoomPs.setString(7, resvRoom.getEmail());
			} else {
				resvRoomPs.setString(7, " ");

			}
			if (resvRoom.getPhone() != null) {
				resvRoomPs.setString(8, resvRoom.getPhone());
			} else {
				resvRoomPs.setString(8, " ");

			}
			resvRoomPs.setString(9, resvRoom.getNationality());
			resvRoomPs.setString(10, resvRoom.getState());
			resvRoomPs.setString(11, resvRoom.getPassportNo());
			Date PassportExpiry = null;
			if (resvRoom.getPassportExpiryOn() != null) {
				PassportExpiry = new java.sql.Date(resvRoom.getPassportExpiryOn().getTime());
			}
			resvRoomPs.setDate(12, PassportExpiry);
			resvRoomPs.setString(13, resvRoom.getRemarks());
			if (resvRoom.getRoomNumber() != null) {
				resvRoomPs.setString(14, resvRoom.getRoomNumber());
			} else {
				resvRoomPs.setString(14, null);
			}
			resvRoomPs.setString(15, resvRoom.getCustomerImage());
			resvRoomPs.setString(16, resvRoom.getCustomerIdProof());
			resvRoomPs.setString(17, resvRoom.getGstno());
			resvRoomPs.setInt(18, resvRoom.getNumAdults());
			resvRoomPs.setInt(19, resvRoom.getNumChildren());
			resvRoomPs.setInt(20, resvRoom.getNumInfants());
			resvRoomPs.setInt(21, resvRoom.getResvRoomNo());
			resvRoomPs.setByte(22, ReservationStatus.CHECKIN.getCode());

			if (resvRoomPs.executeUpdate() == 1) {
				isSave = true;

			} else {
				isSave = false;
			}
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			isSave = false;
			connection.rollback();
			logger.error("Method : updateResvRoom()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
		}
		return isSave;
	}

	public HashMap<String, Object> getResvRecord(int resvNo) throws Exception {

		Connection connection = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		ResvHdr resvHdr = new ResvHdr();
		List<CheckinDiscount> checkinDiscountList = new ArrayList<CheckinDiscount>();
		List<ResvDtl> resvDtlList = new ArrayList<ResvDtl>();
		List<ResvRoom> resvRoomList = new ArrayList<ResvRoom>();
		List<ResvRate> resvRateList = new ArrayList<ResvRate>();
		HashMap<String, Object> resultData = new HashMap<String, Object>();
		PreparedStatement psDis = null ;
		try {
			connection = dbConnection.getConnection();
			resvHdr = getResvHdr(resvNo);
			/** resv_dtl section */
			resultSet = null;
			resultSet1 = null;
			ResultSet roomResultSet = null;
			ResultSet rateResultSet = null;
			ResvDtl resvDtl = null;
			ResvRoom resvRoom = null;
			ResvRate resvRate = null;
			CheckinDiscount checkdis = null;
		
			String DiscountQuery = "select  * from checkin_discounts where resv_no=? GROUP BY disc_id";
			psDis =  connection.prepareStatement(DiscountQuery);
			psDis.setInt(1, resvHdr.getResvNo());
			resultSet = psDis.executeQuery();
			while (resultSet.next()) {
				checkdis = new CheckinDiscount();
				checkdis.setId(resultSet.getInt("id"));
				checkdis.setResvNo(resultSet.getInt("resv_no"));
				checkdis.setDiscType(resultSet.getInt("disc_type"));
				checkdis.setDiscId(resultSet.getInt("disc_id"));
				checkdis.setDiscCode(resultSet.getString("disc_code"));
				checkdis.setDiscIsPc(resultSet.getBoolean("disc_is_pc"));
				checkdis.setDiscAmount(resultSet.getBigDecimal("disc_amount"));
				checkdis.setDiscPc(resultSet.getBigDecimal("disc_pc"));
				checkdis.setOpen(resultSet.getBoolean("is_open"));
				checkdis.setDiscountFor(resultSet.getInt("discount_for"));
				checkinDiscountList.add(checkdis);
			}
			
			
			String selectResvDtlQuery = "select * from resv_dtl where resv_no=?";
			PreparedStatement resvDtlPs = connection.prepareStatement(selectResvDtlQuery);
			resvDtlPs.setInt(1, resvHdr.getResvNo());
			resultSet = resvDtlPs.executeQuery();
			while (resultSet.next()) {
				resvDtl = new ResvDtl();
				resvDtl.setResvDtlNo(resultSet.getInt("resv_dtl_no"));
				resvDtl.setResvNo(resultSet.getInt("resv_no"));
				resvDtl.setRoomTypeId(resultSet.getInt("room_type_id"));
				resvDtl.setRoomTypeCode(resultSet.getString("room_type_code"));
				resvDtl.setArrDate(resultSet.getDate("arr_date"));
				resvDtl.setDepartDate(resultSet.getDate("depart_date"));
				resvDtl.setNumNights(resultSet.getByte("num_nights"));
				resvDtl.setNumRooms(resultSet.getByte("num_rooms"));
				resvDtl.setRateType(resultSet.getByte("rate_type"));
				resvDtl.setRateId(resultSet.getInt("rate_id"));
				resvDtl.setRateCode(resultSet.getString("rate_code"));
				resvDtl.setRateDescription(resultSet.getString("rate_description"));
				resvDtl.setOccupancy(resultSet.getByte("occupancy"));
				resvDtl.setDiscId(resultSet.getInt("disc_id"));
				resvDtl.setDiscCode(resultSet.getString("disc_code"));
				resvDtl.setDiscIsPc(resultSet.getBoolean("disc_is_pc"));
				resvDtl.setDiscIsOpen(resultSet.getBoolean("disc_is_open"));
				resvDtl.setDiscAmount(resultSet.getDouble("disc_amount"));
				resvDtl.setDiscPc(resultSet.getDouble("disc_pc"));
				resvDtlList.add(resvDtl);

				/** resv_room section */
				roomResultSet = null;
				String selectResvRoomQuery = "select * from resv_room where resv_dtl_no=?";
				PreparedStatement resvRoomPs = connection.prepareStatement(selectResvRoomQuery);
				resvRoomPs.setInt(1, resvDtl.getResvDtlNo());
				roomResultSet = resvRoomPs.executeQuery();
				while (roomResultSet.next()) {
					resvRoom = new ResvRoom();
					resvRoom.setResvRoomNo(roomResultSet.getInt("resv_room_no"));
					resvRoom.setResvDtlNo(roomResultSet.getInt("resv_dtl_no"));
					resvRoom.setRoomNumber(roomResultSet.getString("room_number"));
					resvRoom.setRoomStatus(roomResultSet.getByte("room_status"));
					resvRoom.setNoShow(roomResultSet.getBoolean("is_noshow"));
					if (resvRoom.isNoShow()) {
						resvRoom.setNoshowDate(roomResultSet.getDate("noshow_date"));
						resvRoom.setNoshowTime(roomResultSet.getDate("noshow_time"));
						resvRoom.setNoshowBy(roomResultSet.getInt("noshow_by"));
						resvRoom.setNoshowReason(roomResultSet.getString("noshow_reason"));
					}
					if (resvRoom.getRoomStatus() == 2) {
						resvRoom.setCancelReason(roomResultSet.getString("cancel_reason"));
					}
					if (roomResultSet.getString("salutation") != null) {
						resvRoom.setSalutation(roomResultSet.getString("salutation"));
					} else {
						resvRoom.setSalutation("");
					}
					resvRoom.setFirstName(roomResultSet.getString("first_name"));
					resvRoom.setLastName(roomResultSet.getString("last_name"));
					if (roomResultSet.getString("guest_name") != null) {
						resvRoom.setGuestName(roomResultSet.getString("guest_name"));
					} else {
						resvRoom.setGuestName("");
					}
					resvRoom.setGender(roomResultSet.getString("gender"));
					resvRoom.setAddress(roomResultSet.getString("address"));
					resvRoom.setEmail(roomResultSet.getString("email"));
					resvRoom.setPhone(roomResultSet.getString("phone"));
					resvRoom.setNationality(roomResultSet.getString("nationality"));
					resvRoom.setState(roomResultSet.getString("state"));
					resvRoom.setPassportNo(roomResultSet.getString("passport_no"));
					resvRoom.setPassportExpiryOn(roomResultSet.getDate("passport_expiry_on"));
					resvRoom.setGstno(roomResultSet.getString("gstno"));
					resvRoom.setRemarks(roomResultSet.getString("remarks"));
					resvRoom.setNumAdults(roomResultSet.getByte("num_adults"));
					resvRoom.setNumChildren(roomResultSet.getByte("num_children"));
					resvRoom.setNumInfants(roomResultSet.getByte("num_infants"));
					resvRoom.setCustomerImage(roomResultSet.getString("customer_image"));
					resvRoom.setCustomerIdProof(roomResultSet.getString("customer_id_proof"));
					resvRoomList.add(resvRoom);

					/** resv_rate section */
					rateResultSet = null;
					String selectResvRateQuery = "select * from resv_rate where resv_room_no=?";
					PreparedStatement resvRatePs = connection.prepareStatement(selectResvRateQuery);
					resvRatePs.setInt(1, resvRoom.getResvRoomNo());
					rateResultSet = resvRatePs.executeQuery();
					while (rateResultSet.next()) {
						resvRate = new ResvRate();
						resvRate.setId(rateResultSet.getInt("id"));
						resvRate.setResvRoomNo(rateResultSet.getInt("resv_room_no"));
						resvRate.setNightDate(rateResultSet.getDate("night_date"));
						resvRate.setNight(rateResultSet.getByte("night"));
						resvRate.setRoomCharge(rateResultSet.getBigDecimal("room_charge"));
						resvRate.setIncludeTax(rateResultSet.getBoolean("include_tax"));
						resvRate.setTax1Pc(rateResultSet.getBigDecimal("tax1_pc"));
						resvRate.setTax2Pc(rateResultSet.getBigDecimal("tax2_pc"));
						resvRate.setTax3Pc(rateResultSet.getBigDecimal("tax3_pc"));
						resvRate.setTax4Pc(rateResultSet.getBigDecimal("tax4_pc"));
						resvRate.setServiceChargePc(rateResultSet.getBigDecimal("service_charge_pc"));
						resvRate.setTax1(rateResultSet.getBigDecimal("tax1"));
						resvRate.setTax2(rateResultSet.getBigDecimal("tax2"));
						resvRate.setTax3(rateResultSet.getBigDecimal("tax3"));
						resvRate.setTax4(rateResultSet.getBigDecimal("tax4"));
						resvRate.setServiceCharge(rateResultSet.getBigDecimal("service_charge"));
						resvRateList.add(resvRate);
					}
				}
			}
			resultData.put("resvHdr", resvHdr);
			resultData.put("resvDtlList", resvDtlList);
			resultData.put("resvRoomList", resvRoomList);
			resultData.put("resvRateList", resvRateList);
			resultData.put("checkDiscList", checkinDiscountList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getResvRecord()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();

		} finally {
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(psDis);
			dbConnection.releaseResource(resultSet1);
			
		}
		return resultData;
	}

	public ResvHdr getResvHdr(int resvNo) throws Exception {
		Connection connection = null;
		PreparedStatement resvHdrPs = null;
		PreparedStatement depositPs = null;
		ResvHdr resvHdr = new ResvHdr();
		
		try {
		connection = dbConnection.getConnection();
		String selectResvHdrQuery = "SELECT folio.folio_no, resv_hdr.* FROM resv_hdr INNER JOIN folio ON folio.resv_no = resv_hdr.resv_no WHERE resv_hdr.resv_no=?";
		String depositSql = "select sum(nett_amount) from txn where folio_bind_no=?";
		resvHdrPs = connection.prepareStatement(selectResvHdrQuery);
		depositPs = connection.prepareStatement(depositSql);
		
		resvHdrPs.setInt(1, resvNo);
		ResultSet resultSet = resvHdrPs.executeQuery();
		while (resultSet.next()) {
			resvHdr.setResvNo(resultSet.getInt("resv_No"));
			resvHdr.setFolioBindNO(resultSet.getInt("folio_bind_no"));
			resvHdr.setStatus(resultSet.getByte("status"));
			resvHdr.setPickupNeeded(resultSet.getBoolean("status"));
			if (resvHdr.isPickupNeeded()) {
				resvHdr.setPickupDate(resultSet.getDate("pickup_date"));
				resvHdr.setPickupTime(resultSet.getString("pickupTime"));
				resvHdr.setPickupLocation(resultSet.getString("pickupLocation"));
				resvHdr.setPickupSeats(resultSet.getInt("pickupSeats"));
				resvHdr.setPickupRemarks(resultSet.getString("pickupRemarks"));
				resvHdr.setPickupProvider(resultSet.getInt("pickup_provider"));
			}
			resvHdr.setCutOffDate(resultSet.getDate("cut_off_date"));
			resvHdr.setMinArrDate(resultSet.getDate("min_arr_date"));
			resvHdr.setMaxDepartDate(resultSet.getDate("max_depart_date"));
			resvHdr.setNumRooms(resultSet.getShort("sum_num_rooms"));
			resvHdr.setResvType(resultSet.getByte("resv_type"));
			resvHdr.setCorporateId(resultSet.getInt("corporate_id"));
			resvHdr.setCorporateName(resultSet.getString("corporate_name"));
			resvHdr.setCorporateAddress(resultSet.getString("corporate_address"));
			resvHdr.setDiscType(resultSet.getByte("disc_type"));
			resvHdr.setGuestId(resultSet.getInt("guest_id"));
			resvHdr.setSelectedSalutation(resultSet.getString("salutation"));
			resvHdr.setResvByFirstName(resultSet.getString("resv_by_first_name"));
			resvHdr.setResvByLastName(resultSet.getString("resv_by_last_name"));
			resvHdr.setResvByAddress(resultSet.getString("resv_by_address"));
			resvHdr.setResvByMail(resultSet.getString("resv_by_mail"));
			resvHdr.setResvByPhone(resultSet.getString("resv_by_phone"));
			resvHdr.setResvFor(resultSet.getString("resv_for"));
			resvHdr.setNumAdults(resultSet.getByte("num_adults"));
			resvHdr.setNumChildren(resultSet.getByte("num_children"));
			resvHdr.setNumInfants(resultSet.getByte("num_infants"));
			resvHdr.setRemarks(resultSet.getString("remarks"));
			resvHdr.setBillingInstruction(resultSet.getString("billing_instruction"));
			resvHdr.setSpecialRequests(resultSet.getString("special_requests"));
			resvHdr.setResvDate(resultSet.getDate("resv_date"));
			resvHdr.setResvTime(resultSet.getDate("resv_time"));
			resvHdr.setResvTakenBy(resultSet.getInt("resv_taken_by"));
			resvHdr.setConfDate(resultSet.getDate("conf_date"));
			resvHdr.setConfTime(resultSet.getDate("conf_time"));
			resvHdr.setConfBy(resultSet.getInt("conf_by"));
			resvHdr.setConfRefNo(resultSet.getString("conf_ref_no"));
			resvHdr.setCancelDate(resultSet.getString("cancel_date"));
			resvHdr.setCancelTime(resultSet.getDate("cancel_time"));
			resvHdr.setCancelBy(resultSet.getInt("cancel_by"));
			resvHdr.setCancelReason(resultSet.getString("cancel_reason"));
			resvHdr.setFolioNo(resultSet.getInt("folio_no"));
		}
		resultSet.close();
		depositPs.setInt(1, resvHdr.getFolioBindNO());
		resultSet = depositPs.executeQuery();
		resvHdr.setDepositAmount(0.0);
		while (resultSet.next()) {
			resvHdr.setDepositAmount(resultSet.getDouble(1));
		}
		}
		catch (Exception e) {
			logger.error("Method : getResvHdr()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
			
		}
		return resvHdr;
	}

	public boolean reasonSave(Cancelreason creason) {
		boolean isSave = true;
		Connection connection = null;
		String sql = "insert into reason(reason_type,reason)" + "values(?,?)";
		try {
			connection = dbConnection.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, creason.getReason_type());
			prepareStatement.setString(2, creason.getReason());

			prepareStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("Method : reasonSave()" + Throwables.getStackTraceAsString(e));
		} finally {

			dbConnection.releaseResource(connection);
		}
		return isSave;
	}

	public boolean cancellationSave(ResvHdr resvHdr, List<ResvRoom> resvRoomList, int noCancelCount, int checkStatus) {
		boolean isSave = true;
		Connection con = dbConnection.getConnection();
		PreparedStatement resvRoomUpdatePs = null;
		PreparedStatement resvHdrUpdatePs = null;
		Statement statement = null;
		ResultSet rs = null;

		// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String resvRoomUpdate = "UPDATE resv_room SET room_status=?,cancel_reason=? WHERE resv_room_no=? AND room_status!=?";
		String resvHdrUpdate = "UPDATE resv_hdr SET status=?,cancel_by=?,cancel_date=?,cancel_reason=?,cancel_time=? WHERE resv_no=? AND status!=?";
		String numRooms = "select sum_num_rooms from resv_hdr where resv_no=" + resvHdr.getResvNo();
		int sumNumRooms = 0, notNoCancel = 0;

		try {
			con.setAutoCommit(false);
			resvRoomUpdatePs = con.prepareStatement(resvRoomUpdate);
			for (ResvRoom roomDtl : resvRoomList) {
				if (roomDtl.getRoomStatus() == 2) {
					resvRoomUpdatePs.setByte(1, roomDtl.getRoomStatus());
					resvRoomUpdatePs.setString(2, roomDtl.getCancelReason());
					resvRoomUpdatePs.setInt(3, roomDtl.getResvRoomNo());
					resvRoomUpdatePs.setInt(4, ReservationStatus.NOSHOW.getCode());
					resvRoomUpdatePs.addBatch();
					resvRoomUpdatePs.executeBatch();
				}
			}
			statement = con.createStatement();
			rs = statement.executeQuery(numRooms);
			while (rs.next()) {
				sumNumRooms = rs.getInt(1);
			}
			resvHdrUpdatePs = con.prepareStatement(resvHdrUpdate);
			resvHdrUpdatePs.setByte(1, resvHdr.getStatus());
			resvHdrUpdatePs.setInt(2, resvHdr.getCancelBy());
			resvHdrUpdatePs.setString(3, resvHdr.getCancelDate());
			resvHdrUpdatePs.setString(4, resvHdr.getCancelReason());
			resvHdrUpdatePs.setTime(5, new Time(resvHdr.getCancelTime().getTime()));
			resvHdrUpdatePs.setInt(6, resvHdr.getResvNo());
			resvHdrUpdatePs.setInt(7, ReservationStatus.PARTCHECKIN.getCode());
			if (noCancelCount == 0 || checkStatus == 2) {
				resvHdrUpdatePs.executeUpdate();
			}
			// resvRoomUpdatePs.executeBatch();
			con.commit();
		} catch (SQLException e) {
			logger.error("Method : cancellationSave()" + Throwables.getStackTraceAsString(e));
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(resvRoomUpdatePs);
			dbConnection.releaseResource(resvHdrUpdatePs);
			dbConnection.releaseResource(con);
		}
		return isSave;
	}

	public JsonArray getCalendarData(Date checkinDate, int nights) throws Exception {
		Connection con = null;
		JsonArray jArray = new JsonArray();
		CallableStatement st = null;
		ResultSet rs = null;
		try {
		con = dbConnection.getConnection();
		st = con.prepareCall("{call GetRoomAvailability(?,?)}");
		st.setDate(1, checkinDate);
		st.setInt(2, nights);
		rs = st.executeQuery();
		while (rs.next()) {
			JsonObject jobj = new JsonObject();
			jobj.addProperty("stay_date", rs.getString("stay_date"));
			jobj.addProperty("roomTypeId", rs.getInt("room_type_id"));
			jobj.addProperty("roomTypeCode", rs.getString("room_type_code"));
			jobj.addProperty("roomTypeName", rs.getString("room_type_name"));
			jobj.addProperty("occ1", rs.getBoolean("is_support_single_occ"));
			jobj.addProperty("occ2", rs.getBoolean("is_support_double_occ"));
			jobj.addProperty("occ3", rs.getBoolean("is_support_triple_occ"));
			jobj.addProperty("occ4", rs.getBoolean("is_support_quad_occ"));
			jobj.addProperty("totalRoom", rs.getInt("num_rooms"));
			jobj.addProperty("availRoom", rs.getInt("num_available"));
			jArray.add(jobj);
		}
		}
		catch (Exception e) {
			logger.error("Method : getCalendarData()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(rs);
		}
		return jArray;
	}

	public boolean saveReservationEdit(ResvHdr resvHdrNew, List<RoomRateDetailsCheck> resvDtlListNew,
			List<ResvRoom> resvRoomListNew, JsonObject changesObj,JsonArray checkinDiscount) throws Exception {
		Connection con = dbConnection.getConnection();
		boolean isSave = true;
		XStream xstr = new XStream();
		con.setAutoCommit(false);
		CallableStatement st = null;
		PreparedStatement pscheckinDiscount = null;
		PreparedStatement psDlt = null;
		CallableStatement countFunct = null;
		try {
			String delDiscount = "delete from checkin_discounts where resv_no=?";
			String sqlcheckinDiscntInsert = "insert into checkin_discounts(id,resv_no,disc_type,disc_id,disc_code,disc_is_pc,disc_amount,disc_pc,is_open,discount_for) values(?,?,?,?,?,?,?,?,?,?)";
			String countFunction = "{? = call counter(?)}";
			
			ResultSet rs = null;
			psDlt= con.prepareStatement(delDiscount);
			psDlt.setInt(1, resvHdrNew.getResvNo());
			psDlt.executeUpdate();
			
			if(checkinDiscount.size()!=0) {
				pscheckinDiscount = con.prepareStatement(sqlcheckinDiscntInsert);
			}
			
			countFunct = con.prepareCall(countFunction);
			countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
			//for checkin_discounts  set values
			
			if(checkinDiscount.size()!=0) {
			for (RoomRateDetailsCheck resvdtl : resvDtlListNew) {
			
				int roomCount = resvdtl.getNumRooms();
				while (roomCount > 0) {
				for(int i = 0; i<checkinDiscount.size() ;i++) {
					JsonObject discnt = (JsonObject) checkinDiscount.get(i);
					int discId = discnt.get("discId").getAsInt();
				
					countFunct.setString(2, "checkin_discounts");
					rs = countFunct.executeQuery();
					while (rs.next()) {
						pscheckinDiscount.setInt(1,rs.getInt(1));
					}
					pscheckinDiscount.setInt(2, resvHdrNew.getResvNo());
					//pscheckinDiscount.setInt(3, resvdtl.getResvDtlNo());
					
						Discount disc = discountService.getRecord(discId);
						if (disc.getIsPc()) {
							if (disc.getIsOpen()) {
								
								pscheckinDiscount.setBigDecimal(8, BigDecimal.valueOf(discnt.get("openDiscount").getAsDouble()));
							} else {
								pscheckinDiscount.setBigDecimal(8, BigDecimal.valueOf(disc.getDiscPc().doubleValue()));
								
							}
							pscheckinDiscount.setBigDecimal(7, BigDecimal.valueOf(0));
						} else {
							pscheckinDiscount.setBigDecimal(8, BigDecimal.valueOf(0));
							if (disc.getIsOpen()) {
								pscheckinDiscount.setBigDecimal(7, discnt.get("openDiscount").getAsBigDecimal());
								
							} else {
								pscheckinDiscount.setBigDecimal(7, BigDecimal.valueOf(disc.getDiscAmount().doubleValue()));
							}
						}
						
						pscheckinDiscount.setInt(3, disc.getDiscType());
						pscheckinDiscount.setInt(4, disc.getId());
						pscheckinDiscount.setString(5, disc.getCode());
						pscheckinDiscount.setBoolean(6, disc.getIsPc());
						
						
						
						pscheckinDiscount.setBoolean(9, disc.getIsOpen());
						pscheckinDiscount.setInt(10, disc.getDiscountFor());
						//pscheckinDiscount.setInt(12, disc.getDiscType());
						pscheckinDiscount.addBatch();
				}
				roomCount = roomCount -1;
				}

			  
			}
			pscheckinDiscount.executeBatch();
			}
		
			st = con.prepareCall("{call updateReservation(?,?,?,?)}");
			xstr = new XStream();
			xstr.alias("resvhdr", ResvHdr.class);
			st.setString(1, xstr.toXML(resvHdrNew).replaceAll("\\s*[\\r\\n]+\\s*", "").trim());
			xstr = new XStream();
			xstr.alias("resvdtlArgs", RoomRateDetailsCheck.class);
			st.setString(2, xstr.toXML(resvDtlListNew).replaceAll("\\s*[\\r\\n]+\\s*", "").trim());
			xstr = new XStream();
			xstr.alias("resvroom", ResvRoom.class);
			st.setString(3, xstr.toXML(resvRoomListNew).replaceAll("\\s*[\\r\\n]+\\s*", "").trim());
			String changesString = "<changes><hdrIsDirty>" + changesObj.get("hdrIsDirty").getAsBoolean()
					+ "</hdrIsDirty>" + "<ratePlanIsDirty>" + changesObj.get("ratePlanIsDirty").getAsBoolean()
					+ "</ratePlanIsDirty><roomTypeIsDirty>" + changesObj.get("roomTypeIsDirty").getAsBoolean()
					+ "</roomTypeIsDirty>" + "<discountIsDirty>" + changesObj.get("discountIsDirty").getAsBoolean()
					+ "</discountIsDirty><roomIsDirty>" + changesObj.get("roomIsDirty").getAsBoolean()
					+ "</roomIsDirty><roomDataIsDirty>" + changesObj.get("roomDataIsDirty").getAsBoolean()
					+ "</roomDataIsDirty></changes>";
			st.setString(4, changesString);
			st.executeUpdate();
			con.commit();
		} catch (Exception e) {
			isSave = false;
			con.rollback();
			e.printStackTrace();
			logger.error("Method : saveReservationEdit()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(st);
			
		}
		return isSave;
	}

	public JsonObject getListData(JsonObject jobj) throws Exception {
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		Statement statementCount = null;
		ResultSet resultSet = null;
		JsonArray jsonCardArray = new JsonArray();
		JsonObject jsonFullContent = new JsonObject();

		JsonObject pagination = jobj.get("pagination").getAsJsonObject();
		JsonObject sortObj = jobj.get("sort").getAsJsonObject();
		JsonObject searchObj = jobj.get("search").getAsJsonObject();

		int startRow = pagination.get("offset").getAsInt(), limit = pagination.get("limit").getAsInt(), totalCount = 0;
		String sort = sortObj.get("sortColumn").getAsString(), sortValue = sortObj.get("order").getAsString();
		String search = " WHERE ";
		if (searchObj.get("comnSearch").getAsBoolean()) {
			search += "'" + new Date(commonSettings.getHotelDate().getTime()) + "' <= arr_date" + " AND "
					+ "(resv_status_xlt like '%" + searchObj.get("comnSearchValue").getAsString() + "%' OR "
					+ "corporate_name like '%" + searchObj.get("comnSearchValue").getAsString() + "%' OR "
					+ "reserved_by like '%" + searchObj.get("comnSearchValue").getAsString() + "%' OR "
					+ "resv_for like '%" + searchObj.get("comnSearchValue").getAsString() + "%')";
		} else if (searchObj.get("advSearch").getAsBoolean()) {
			int count = 0;
			if (searchObj.get("resvNo").getAsJsonObject().get("searchable").getAsBoolean()) {
				search += "resv_no like '%" + searchObj.get("resvNo").getAsJsonObject().get("searchValue").getAsString()
						+ "%' ";
				count++;
			}
			if (searchObj.get("resvBy").getAsJsonObject().get("searchable").getAsBoolean()) {
				if (count != 0) {
					search += " AND ";
				}
				search += "resv_for like '%"
						+ searchObj.get("resvBy").getAsJsonObject().get("searchValue").getAsString() + "%' ";
				count++;
			}
			if (searchObj.get("resvDate").getAsJsonObject().get("searchable").getAsBoolean()) {
				if (count != 0) {
					search += " AND ";
				}
				Date tempDate = new Date(
						Long.parseLong(searchObj.get("resvDate").getAsJsonObject().get("searchValue").getAsString()));
				search += "resv_date = '" + tempDate + "' ";
				count++;
			}
			if (searchObj.get("arrDate").getAsJsonObject().get("searchable").getAsBoolean()) {
				if (count != 0) {
					search += " AND ";
				}
				Date tempDate = new Date(
						Long.parseLong(searchObj.get("arrDate").getAsJsonObject().get("searchValue").getAsString()));
				search += "arr_date = '" + tempDate + "' ";
				count++;
			}
			if (searchObj.get("resvStatus").getAsJsonObject().get("searchable").getAsBoolean()) {
				if (count != 0) {
					search += " AND ";
				}
				search += "resv_status_xlt like '%"
						+ searchObj.get("resvStatus").getAsJsonObject().get("searchValue").getAsString() + "%' ";
			//	search += "resv_status= '"+ searchObj.get("resvStatus").getAsJsonObject().get("searchValue").getAsString() +"' ";
			}

		} else {
			if (pagination.has("selectedTab")) {
				search += " arr_date='" + new Date(commonSettings.getHotelDate().getTime()) + "' and resv_status!='"
						+ ReservationStatus.CHECKIN.getCode() + "'";
			} else {
				search += "'" + new Date(commonSettings.getHotelDate().getTime()) + "' <= arr_date";

			}
		}
		String sql = "select * from v_list_reservation " + search + "  order by " + sort + " " + sortValue + " limit "
				+ limit + " offset " + startRow + "";
		String countSql = "select count(resv_no) from v_list_reservation" + search;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			JsonObject resvRow = null;
			while (resultSet.next()) {
				resvRow = new JsonObject();
				resvRow.addProperty("resvNo", resultSet.getInt("resv_no"));
				resvRow.addProperty("resvStatus", resultSet.getInt("resv_status"));
				resvRow.addProperty("resvStatusXlt", resultSet.getString("resv_status_xlt"));
				resvRow.addProperty("corporateName", resultSet.getString("corporate_name"));
				resvRow.addProperty("resvDate", resultSet.getString("resv_date"));
				resvRow.addProperty("reservedBy", resultSet.getString("reserved_by"));
				resvRow.addProperty("canConfirm", resultSet.getString("can_confirm"));
				resvRow.addProperty("canCancel", resultSet.getString("can_cancel"));
				resvRow.addProperty("canDeposit", resultSet.getString("can_deposit"));
				resvRow.addProperty("canCheckin", resultSet.getString("can_check_in"));
				resvRow.addProperty("canEdit", resultSet.getString("can_edit"));
				resvRow.addProperty("canNoshow", resultSet.getString("can_Noshow"));
				resvRow.addProperty("folioBalance", resultSet.getString("folio_balance"));
				resvRow.addProperty("arrivalDate", resultSet.getString("arr_date"));
				resvRow.addProperty("numRooms", resultSet.getString("num_rooms"));
				resvRow.addProperty("numNights", resultSet.getString("num_nights"));
				resvRow.addProperty("resvFor", resultSet.getString("resv_for"));
				resvRow.addProperty("stayedDays", 0);
				resvRow.addProperty("arrivalTime", resultSet.getString("arr_time"));
				resvRow.addProperty("departTime", resultSet.getString("depart_time"));
				jsonCardArray.add(resvRow);
			}
			resultSet.close();
			statementCount = connection.createStatement();
			resultSet = statementCount.executeQuery(countSql);
			while (resultSet.next()) {
				totalCount = resultSet.getInt(1);
			}
			jsonFullContent.addProperty("total", totalCount);
			jsonFullContent.add("card", jsonCardArray);
		} catch (Exception e) {
			logger.error("Method : getListData()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
		}
		return jsonFullContent;
	}

	public JsonObject getResvDtlList(int resvId) throws Exception {
		ResvDtl resvDtl;
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		List<ResvDtl> resvDtlList = new ArrayList<ResvDtl>();
		JsonObject resultData = new JsonObject();
		ResvHdr resvHdr = new ResvHdr();
		int numNights = 0;
		Gson gson = new Gson();
		try {
			statement = connection.createStatement();
			String rateDtlSql = "select resv_dtl_no,arr_date,num_nights,num_rooms,room_type_id,room_type_code,rate_code,rate_id,occupancy,disc_id,disc_code,disc_is_open,disc_is_pc,disc_pc,disc_amount,num_nights from resv_dtl where resv_no = "
					+ resvId;
			resultSet = statement.executeQuery(rateDtlSql);
			while (resultSet.next()) {
				resvDtl = new ResvDtl();
				resvDtl.setResvDtlNo(resultSet.getInt("resv_dtl_no"));
				resvDtl.setArrDate(resultSet.getDate("arr_date"));
				resvDtl.setNumNights(resultSet.getByte("num_nights"));
				resvDtl.setNumRooms(resultSet.getByte("num_rooms"));
				resvDtl.setRoomTypeId(resultSet.getInt("room_type_id"));
				resvDtl.setRoomTypeCode(resultSet.getString("room_type_code"));
				resvDtl.setRateCode(resultSet.getString("rate_code"));
				resvDtl.setRateId(resultSet.getInt("rate_id"));
				resvDtl.setOccupancy(resultSet.getByte("occupancy"));
				resvDtl.setDiscId(resultSet.getInt("disc_id"));
				resvDtl.setDiscCode(resultSet.getString("disc_code"));
				resvDtl.setDiscIsOpen(resultSet.getBoolean("disc_is_open"));
				resvDtl.setDiscIsPc(resultSet.getBoolean("disc_is_pc"));
				resvDtl.setDiscPc(resultSet.getDouble("disc_pc"));
				resvDtl.setDiscAmount(resultSet.getDouble("disc_amount"));
				numNights = resultSet.getInt("num_nights");
				resvDtlList.add(resvDtl);
			}
			// resv_Hdr details
			String selectResvHdrQuery = "SELECT folio.folio_no,resv_hdr.resv_No,resv_hdr.folio_bind_no,resv_hdr.status,resv_hdr.resv_type,resv_hdr.corporate_id,resv_hdr.corporate_name,resv_hdr.resv_date,resv_hdr.resv_by_first_name,resv_hdr.resv_by_last_name,resv_hdr.resv_for,resv_hdr.disc_type,resv_hdr.min_arr_date,resv_hdr.max_depart_date,resv_hdr.sum_num_rooms,resv_hdr.special_requests,resv_hdr.remarks,resv_hdr.cut_off_date FROM resv_hdr INNER JOIN folio ON folio.resv_no = resv_hdr.resv_no WHERE resv_hdr.resv_no=?";
			PreparedStatement resvHdrPs = connection.prepareStatement(selectResvHdrQuery);
			resvHdrPs.setInt(1, resvId);
			resultSet = resvHdrPs.executeQuery();
			while (resultSet.next()) {
				resvHdr.setResvNo(resultSet.getInt("resv_No"));
				resvHdr.setFolioBindNO(resultSet.getInt("folio_bind_no"));
				resvHdr.setStatus(resultSet.getByte("status"));
				resvHdr.setResvType(resultSet.getByte("resv_type"));
				resvHdr.setCorporateId(resultSet.getInt("corporate_id"));
				resvHdr.setCorporateName(resultSet.getString("corporate_name"));
				resvHdr.setResvDate(resultSet.getDate("resv_date"));
				resvHdr.setResvByFirstName(resultSet.getString("resv_by_first_name"));
				resvHdr.setResvByLastName(resultSet.getString("resv_by_last_name"));
				resvHdr.setFolioNo(resultSet.getInt("folio_no"));
				resvHdr.setResvFor(resultSet.getString("resv_for"));
				resvHdr.setDiscType(resultSet.getByte("disc_type"));
				resvHdr.setMinArrDate(resultSet.getDate("min_arr_date"));
				resvHdr.setMaxDepartDate(resultSet.getDate("max_depart_date"));
				resvHdr.setCutOffDate(resultSet.getDate("cut_off_date"));
				resvHdr.setNumRooms(resultSet.getShort("sum_num_rooms"));
				resvHdr.setSpecialRequests(resultSet.getString("special_requests"));
				resvHdr.setRemarks(resultSet.getString("remarks"));
				resvHdr.setNumNights(numNights);
			}
		} catch (SQLException e) {
			logger.error("Method : getResvDtlList()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
		}
		resultData.addProperty("resvHdr", gson.toJson(resvHdr));
		resultData.add("resvDtl", gson.toJsonTree(resvDtlList));
		dbConnection.releaseResource(resultSet);
		dbConnection.releaseResource(connection);
		return resultData;
	}

	public JsonObject getNewArrivalDateData(int resvNo, Date arrivalDate, int discType, int noNight, int corporateId) {
		// ResvDtl resvDtl;
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null, resultSet2 = null;
		// List<ResvDtl> resvDtlList = new ArrayList<ResvDtl>();
		// ResvHdr resvHdr = new ResvHdr();
		JsonObject jsonObject = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		JsonObject row = null, rowRates = null;
		CallableStatement st = null, st2 = null;
		ResultSet rs = null;
		// HashMap reducedTypeHmap = new HashMap();
		try {
			statement = connection.createStatement();
			st = connection.prepareCall("{call GetRoomAvailabilityiWithResvNo(?,?,?)}");
			st.setDate(1, arrivalDate);
			st.setInt(2, noNight);
			st.setInt(3, resvNo);
			rs = st.executeQuery();
			String code = "";
			// int id = 0;
			int availableRooms = 0;
			// int curRooms = 0;
			// Map<Integer, ArrayList<String>> roomTypes = new HashMap<Integer,
			// ArrayList<String>>();
			// ArrayList<String> roomTypeRow = null;
			HashMap hm = new HashMap();
			HashMap hmZeroRooms = new HashMap();
			while (rs.next()) {
				availableRooms = rs.getInt("num_available");
				code = rs.getString("room_type_code");
				if (availableRooms > 0) {
					hm.put(code, availableRooms);
				} else {
					hmZeroRooms.put(code, availableRooms);
				}
			}
			Iterator it = hm.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				if (hmZeroRooms.containsKey(pair.getKey())) {
					it.remove();
				}
			}
			rs.beforeFirst();
			String rateDtlSql = "select * from resv_dtl where resv_no = " + resvNo;
			resultSet = statement.executeQuery(rateDtlSql);
			int rowStatus = 00;
			String procedure = "{call GetRoomRates(?,?,?,?,?)}";
			st2 = connection.prepareCall(procedure);
			st2.setDate(1, arrivalDate);
			st2.setInt(2, noNight);
			st2.setInt(3, corporateId);
			while (resultSet.next()) {
				row = new JsonObject();
				row.addProperty("resv_dtl_no", resultSet.getInt("resv_dtl_no"));
				row.addProperty("arr_date", resultSet.getDate("arr_date").toString());
				row.addProperty("num_nights", resultSet.getByte("num_nights"));
				row.addProperty("num_rooms", resultSet.getShort("num_rooms"));
				row.addProperty("room_type_code", resultSet.getString("room_type_code"));
				row.addProperty("rate_id", resultSet.getInt("rate_id"));
				row.addProperty("rate_code", resultSet.getString("rate_code"));
				row.addProperty("occupancy", resultSet.getByte("occupancy"));
				row.addProperty("disc_id", resultSet.getInt("disc_id"));
				row.addProperty("disc_code", resultSet.getString("disc_code"));
				row.addProperty("disc_is_open", resultSet.getBoolean("disc_is_open"));
				row.addProperty("disc_is_pc", resultSet.getBoolean("disc_is_pc"));
				row.addProperty("disc_pc", resultSet.getDouble("disc_pc"));
				row.addProperty("disc_amount", resultSet.getDouble("disc_amount"));
				row.addProperty("crate_plan", resultSet.getString("rate_code"));// rate plan is same as rate code
				// row.addProperty("status",rowStatus); // 0 no change , 1 rate plan change , 2
				// row delete or edit
				if (hm.containsKey(resultSet.getString("room_type_code"))) { // room_type_code available
					// room_type_code eg: STD has rooms
					int avlNoRoomFromSp = (Integer) hm.get(resultSet.getString("room_type_code"));
					int resvedNoRoom = resultSet.getShort("num_rooms");
					// to check No of rooms are enough
					if (avlNoRoomFromSp >= resvedNoRoom) { // number of rooms are enough
						// to check plan is available (rate code)
						st2.setString(4, resultSet.getString("room_type_code"));
						st2.setInt(5, resultSet.getInt("rate_id"));
						// System.out.println(""+st2.toString());
						resultSet2 = st2.executeQuery();
						if (!resultSet2.next()) {
							rowStatus = 2;
						} else {
							resultSet2.beforeFirst();
							// int planAvlStatus = 0,
							// int count = 0;
							JsonArray roomRateArray = new JsonArray();
							while (resultSet2.next()) {
								rowRates = new JsonObject();
								if (resultSet2.getString("rate_code").equals(resultSet.getString("rate_code"))) {
									rowStatus = 0;
									// planAvlStatus = 1;
									break;
								} else {
									rowStatus = 1;
									rowRates.addProperty("roomTypeId", resultSet2.getInt("room_type_id"));
									rowRates.addProperty("roomTypeCode", resultSet2.getString("room_type_code"));
									rowRates.addProperty("roomTypeName", resultSet2.getString("room_type_name"));
									rowRates.addProperty("single", resultSet2.getString("singleOcc"));
									rowRates.addProperty("double", resultSet2.getString("doubleOcc"));
									rowRates.addProperty("triple", resultSet2.getString("tripleOcc"));
									rowRates.addProperty("quad", resultSet2.getString("quadOcc"));
									rowRates.addProperty("rateId", resultSet2.getInt("rate_id"));
									rowRates.addProperty("rateCode", resultSet2.getString("rate_code"));
									rowRates.addProperty("rateType", resultSet2.getInt("rate_type"));
									rowRates.addProperty("rateDescription", resultSet2.getString("rate_description"));
									rowRates.addProperty("rateName", resultSet2.getString("rate_name"));
									rowRates.addProperty("ratePeriodId", resultSet2.getInt("rate_period_id"));
									rowRates.addProperty("mealPlan", resultSet2.getString("meal_plans"));
									rowRates.addProperty("isTaxInclude", resultSet2.getBoolean("is_tax_included"));
									rowRates.addProperty("singleRate", resultSet2.getDouble("total_single_rate"));
									rowRates.addProperty("doubleRate", resultSet2.getDouble("total_double_rate"));
									rowRates.addProperty("tripleRate", resultSet2.getDouble("total_triple_rate"));
									rowRates.addProperty("quadRate", resultSet2.getDouble("total_quad_rate"));
									roomRateArray.add(rowRates);
									// count++;
								}
							}
							if (rowStatus == 1) {
								row.add("availableRooms", roomRateArray);
							}
						}
						if (rowStatus == 1 || rowStatus == 0) {
							hm.put(resultSet.getString("room_type_code"), (avlNoRoomFromSp - resvedNoRoom));
						}
					} else { // number of rooms are not enough
						rowStatus = 2; // 2 => row delete or edit
					}
				} else { // room_type_code not available
					rowStatus = 2; // row.addProperty("status",2); // 0 no change , 1 rate plan change , 2 row
					// delete or edit
				}
				if (discType != 0) {
					if (rowStatus == 2 || rowStatus == 1) { // discount changed
						row.addProperty("disStatus", 1); // (disStatus = 0 or 1 ) => 0 means no change , 1 means
						// discount changed so delete this discount
					} else {
						row.addProperty("disStatus", 0);
					}
				}
				row.addProperty("status", rowStatus);
				jsonArray.add(row);
			}
			jsonObject.add("availableRooms", jsonArray);
		} catch (SQLException e) {
			logger.error("Method : getNewArrivalDateData()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
		}
		return jsonObject;
	}

	public boolean saveChangeArrivalDate(int resvNo, java.util.Date arrDate,
			List<RoomRateDetailsCheck> roomRateDetailsList, JsonArray discList) throws Exception {
		boolean isSave = true;
		Connection connection = dbConnection.getConnection();
		SimpleDateFormat dateFormatFrom = new SimpleDateFormat("MMM dd,yyyy");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(dateFormatFrom);
		PreparedStatement selresvHdrPs = null;
		PreparedStatement updateResvDtlRatePs = null;
		PreparedStatement updateResvDtlNumRoomPs = null;
		PreparedStatement updateDiscPs = null;
		PreparedStatement updateResvRoomPs = null;
		PreparedStatement deleteRatePs = null;
		PreparedStatement insertRatePs = null;
		PreparedStatement getResvRoomNoPs = null;
		PreparedStatement resvRoomInsertPs = null;
		PreparedStatement resvDtlInsertPs = null;
		PreparedStatement updateHdrPs = null;
		PreparedStatement updateResvDtlPs = null;
		PreparedStatement deleteResvDtlPs = null;
		ResultSet rs = null;
		ResultSet resultSetSel = null;
		ResvHdr rsvHdr = null;
		
		PreparedStatement deletePickupPs = null;
		CallableStatement countFunct = null;
		Statement statementSl = null;
		
		String countFunction = "{? = call counter(?)}";
		String selHdr = "select min_arr_time,max_depart_time from resv_hdr where resv_no='"+resvNo+"'";
		String updateHdr = "update resv_hdr set cut_off_date=?,min_arr_date=?,max_depart_date=?  where resv_no=?";
		String updateResvDtlRate = "update resv_dtl set arr_date=?, depart_date=?,rate_type=?, rate_id=?, rate_code=?, rate_description=?,num_rooms=? where resv_dtl_no=?";
		String updateResvDtlNumRoom = "update resv_dtl set arr_date=?, depart_date=?, num_rooms=? where resv_dtl_no=?";
		String updateResvDtl = "update resv_dtl set arr_date=?, depart_date=? where resv_no=?";
		String updateDisc = "update resv_dtl set disc_id=?, disc_code=?, disc_is_pc=?, disc_is_open=?, disc_amount=?, disc_pc=? where resv_no=? and rate_id=?";
		String updateResvRoom = "update resv_room set resv_dtl_no=? where resv_dtl_no =?";
		String getResvRoomNo = "select resv_room_no from resv_room where resv_dtl_no=?";
		String deleteResvDtl = "delete from resv_dtl where resv_no=? and resv_dtl_no not in ";
		String deleteRate = "delete resv_rate.* from resv_rate inner join resv_room on resv_room.resv_room_no=resv_rate.resv_room_no where resv_room.resv_dtl_no=?";
		String insertRate = "insert into resv_rate(resv_room_no, night,night_date, room_charge, include_tax, tax1_pc, tax2_pc, tax3_pc, tax4_pc,service_charge_pc, tax1, tax2, tax3, tax4,service_charge) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String resvRoomInsert = "insert into resv_room(resv_room_no, resv_dtl_no,is_noshow,room_status) values(?,?,?,?)";
		String resvDtlInsert = "insert into resv_dtl(resv_dtl_no, resv_no, room_type_id, room_type_code, arr_date, depart_date, num_nights, num_rooms, rate_type, rate_id, rate_code, rate_description, occupancy, disc_id, disc_code, disc_is_pc, disc_is_open, disc_amount, disc_pc) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String deletePickup = "update resv_hdr set pickup_needed=?, pickup_date=?, pickupTime=?, pickupLocation=?, pickupSeats=? ,pickupRemarks=?, pickup_provider=? where resv_no=?";
		boolean rateChange = false, roomTypeChange = false, discChange = false, numRoomChange = false,
				roomInsert = false;
		String dtlIds = "";
		int numRooms = 0;
		java.util.Date oldArrDate = null, departDate = null, hdate = null;
		// java.util.Date cuttoffDate = null;
		List<Integer> dtlIdList = new ArrayList<Integer>();
		JsonParser parser = new JsonParser();
		connection.setAutoCommit(false);
		try {
			JsonObject resvDtlObj = getResvDtlList(resvNo);
			selresvHdrPs = connection.prepareStatement(selHdr);
			
			statementSl = connection.createStatement();
			resultSetSel = statementSl.executeQuery(selHdr);
			while (resultSetSel.next()) {
				rsvHdr = new ResvHdr();
				 rsvHdr.setMinArrTime(resultSetSel.getTime("min_arr_time"));
				 rsvHdr.setMaxDepTime(resultSetSel.getTime("max_depart_time"));
				
			}
			JsonObject hdrObj = parser.parse(resvDtlObj.get("resvHdr").getAsString()).getAsJsonObject();
			hdrObj.remove("FolioNo");
			ResvHdr resvHdr = mapper.readValue(hdrObj.toString(), ResvHdr.class);
			int numNights = resvHdr.getNumNights();
			Calendar c = Calendar.getInstance();
			oldArrDate = resvHdr.getMinArrDate();
			if (oldArrDate != arrDate) {
				c.setTime(arrDate);
				c.add(Calendar.DATE, numNights);
				departDate = c.getTime();
				SystemSettings systemSettings = systemSettingsService.getSystemSettings();
				int confirmBefore = systemSettings.getConfirmBefore();
				java.util.Date dt = new Date(arrDate.getTime());
				Calendar ccottoff = Calendar.getInstance();
				ccottoff.setTime(new Date(arrDate.getTime()));
				ccottoff.add(Calendar.DATE, -(confirmBefore));
				dt = ccottoff.getTime();
				hdate = systemSettings.getHotelDate();
				java.util.Date cdateHotl = (new Date(hdate.getTime()));
				Calendar chdate = Calendar.getInstance();
				chdate.setTime(new Date(hdate.getTime()));
				cdateHotl = chdate.getTime();

				updateHdrPs = connection.prepareStatement(updateHdr);
				
				
				if (dt.before(cdateHotl)) {
					updateHdrPs.setDate(1, new Date(cdateHotl.getTime()));
				} else {
					updateHdrPs.setDate(1, new Date(dt.getTime()));
				}
				SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
				String stringDateArr = DateFor.format(arrDate);
				LocalTime timeArr = LocalTime.parse(rsvHdr.getMinArrTime().toString());
				LocalDate dateArr = LocalDate.parse(stringDateArr);
				LocalDateTime datetimeArr = timeArr.atDate(dateArr);
				
				String stringDateDpt = DateFor.format(departDate);
				LocalTime timeDpt = LocalTime.parse(rsvHdr.getMaxDepTime().toString());
				LocalDate dateDpt = LocalDate.parse(stringDateDpt);
				LocalDateTime datetimeDpt = timeDpt.atDate(dateDpt);
				
				updateHdrPs.setTimestamp(2, Timestamp.valueOf(datetimeArr));
				updateHdrPs.setTimestamp(3, Timestamp.valueOf(datetimeDpt));

				//updateHdrPs.setDate(2, new Date(arrDate.getTime()));
				//updateHdrPs.setDate(3, new Date(departDate.getTime()));
				updateHdrPs.setInt(4, resvNo);
				updateHdrPs.executeUpdate();
				updateResvDtlPs = connection.prepareStatement(updateResvDtl);
				updateResvDtlPs.setDate(1, new Date(arrDate.getTime()));
				updateResvDtlPs.setDate(2, new Date(departDate.getTime()));
				updateResvDtlPs.setInt(3, resvNo);
				updateResvDtlPs.executeUpdate();

				updateResvDtlRatePs = connection.prepareStatement(updateResvDtlRate);
				updateDiscPs = connection.prepareStatement(updateDisc);
				deleteRatePs = connection.prepareStatement(deleteRate);
				insertRatePs = connection.prepareStatement(insertRate);
				resvRoomInsertPs = connection.prepareStatement(resvRoomInsert);
				updateResvDtlNumRoomPs = connection.prepareStatement(updateResvDtlNumRoom);
				resvDtlInsertPs = connection.prepareStatement(resvDtlInsert);
				countFunct = connection.prepareCall(countFunction);
				List<ResvDtl> resvDtlList = mapper.readValue(resvDtlObj.get("resvDtl").toString(),
						TypeFactory.defaultInstance().constructCollectionType(List.class, ResvDtl.class));

				for (RoomRateDetailsCheck roomRateDtl : roomRateDetailsList) {
					if (roomRateDtl.getResvDtlNo() != 0) {
						/* change in rate plan or change in numRooms */
						for (ResvDtl resvDtl : resvDtlList) {
							if (resvDtl.getResvDtlNo() == roomRateDtl.getResvDtlNo()) {
								int resvDtlNo = roomRateDtl.getResvDtlNo();
								RoomRateDetailsCheck rateCheckObj = new RoomRateDetailsCheck();
								BeanUtils.copyProperties(roomRateDtl, rateCheckObj);
								rateCheckObj.setNumRooms(1);
								JsonObject rateObj = getRoomRateDetails(rateCheckObj);
								JsonArray ratePerOcc = rateObj.get("ratePerOcc").getAsJsonArray();
								dtlIdList.add(resvDtl.getResvDtlNo());
								/* rate t o be updated for date change even there is no other changes */
								if (roomRateDtl.getNumRooms() > resvDtl.getNumRooms()) {
									numRoomChange = true;
									resvDtlNo = roomRateDtl.getResvDtlNo();
									updateResvDtlNumRoomPs.setDate(1, new Date(arrDate.getTime()));
									updateResvDtlNumRoomPs.setDate(2, new Date(departDate.getTime()));
									updateResvDtlNumRoomPs.setInt(3, roomRateDtl.getNumRooms());
									updateResvDtlNumRoomPs.setInt(4, roomRateDtl.getResvDtlNo());
									updateResvDtlNumRoomPs.addBatch();
									updateResvRoomPs = connection.prepareStatement(updateResvRoom);
									updateResvRoomPs.setInt(1, roomRateDtl.getResvDtlNo());
									updateResvRoomPs.setInt(2, resvDtl.getResvDtlNo());
									updateResvRoomPs.executeUpdate();
								}

								/* rate plan change */
								if (resvDtl.getRateId() != roomRateDtl.getRateId()) {
									boolean isExist = false;
									numRooms = 0;
									resvDtlNo = 0;
									for (ResvDtl resvDtlCheck : resvDtlList) {
										if (resvDtlCheck.getResvDtlNo() != roomRateDtl.getResvDtlNo()) {
											/* if resv_dtl row with same rateid and occupancy exists */
											if (resvDtlCheck.getRateId() == roomRateDtl.getRateId()
													&& resvDtlCheck.getOccupancy() == roomRateDtl.getOccupancy()) {
												isExist = true;
												numRoomChange = true;
												resvDtlNo = resvDtlCheck.getResvDtlNo();
												numRooms = resvDtlCheck.getNumRooms() + roomRateDtl.getNumRooms();
												roomRateDtl.setNumRooms(numRooms);
												updateResvDtlNumRoomPs.setDate(1, new Date(arrDate.getTime()));
												updateResvDtlNumRoomPs.setDate(2, new Date(departDate.getTime()));
												updateResvDtlNumRoomPs.setInt(3, numRooms);
												updateResvDtlNumRoomPs.setInt(4, resvDtlCheck.getResvDtlNo());
												updateResvDtlNumRoomPs.addBatch();
												updateResvRoomPs = connection.prepareStatement(updateResvRoom);
												updateResvRoomPs.setInt(1, resvDtlCheck.getResvDtlNo());
												updateResvRoomPs.setInt(2, roomRateDtl.getResvDtlNo());
												if (dtlIdList.contains(resvDtl.getResvDtlNo())) {
													dtlIdList.remove(new Integer(resvDtl.getResvDtlNo()));
												}
												updateResvRoomPs.executeUpdate();
											}
										}
									}

									if (!isExist) {
										rateChange = true;
										resvDtlNo = roomRateDtl.getResvDtlNo();
										updateResvDtlRatePs.setDate(1, new Date(arrDate.getTime()));
										updateResvDtlRatePs.setDate(2, new Date(departDate.getTime()));
										updateResvDtlRatePs.setByte(3,
												ratePerOcc.get(0).getAsJsonObject().get("rate_type").getAsByte());
										updateResvDtlRatePs.setInt(4, roomRateDtl.getRateId());
										updateResvDtlRatePs.setString(5,
												ratePerOcc.get(0).getAsJsonObject().get("rate_code").getAsString());
										if (ratePerOcc.get(0).getAsJsonObject().get("rate_description").isJsonNull()) {
											updateResvDtlRatePs.setString(6, null);
										} else {
											updateResvDtlRatePs.setString(6, ratePerOcc.get(0).getAsJsonObject()
													.get("rate_description").getAsString());
										}
										updateResvDtlRatePs.setInt(7, roomRateDtl.getNumRooms());
										updateResvDtlRatePs.setInt(8, roomRateDtl.getResvDtlNo());
										updateResvDtlRatePs.addBatch();
									}
								}
								deleteRatePs.setInt(1, resvDtlNo);
								deleteRatePs.addBatch();
								int roomCount = 0;
								getResvRoomNoPs = connection.prepareStatement(getResvRoomNo);
								getResvRoomNoPs.setInt(1, resvDtlNo);
								rs = getResvRoomNoPs.executeQuery();
								while (rs.next()) {
									for (JsonElement rateJE : ratePerOcc) {
										insertRatePs.setInt(1, rs.getInt(1));
										insertRatePs.setByte(2, rateJE.getAsJsonObject().get("night").getAsByte());
										insertRatePs.setDate(3,
												new Date(arrDate.getTime()
														+ (((rateJE.getAsJsonObject().get("night").getAsByte()) - 1))
																* 86400000));
										insertRatePs.setBigDecimal(4,
												rateJE.getAsJsonObject().get("charges").getAsBigDecimal());
										insertRatePs.setBoolean(5,
												rateJE.getAsJsonObject().get("is_tax_included").getAsBoolean());
										insertRatePs.setBigDecimal(6,
												rateJE.getAsJsonObject().get("tax1_pc").getAsBigDecimal());
										insertRatePs.setBigDecimal(7,
												rateJE.getAsJsonObject().get("tax2_pc").getAsBigDecimal());
										insertRatePs.setBigDecimal(8,
												rateJE.getAsJsonObject().get("tax3_pc").getAsBigDecimal());
										insertRatePs.setBigDecimal(9,
												rateJE.getAsJsonObject().get("tax4_pc").getAsBigDecimal());
										insertRatePs.setBigDecimal(10,
												rateJE.getAsJsonObject().get("service_charge_pc").getAsBigDecimal());
										insertRatePs.setBigDecimal(11,
												rateJE.getAsJsonObject().get("tax1_amount").getAsBigDecimal());
										insertRatePs.setBigDecimal(12,
												rateJE.getAsJsonObject().get("tax2_amount").getAsBigDecimal());
										insertRatePs.setBigDecimal(13,
												rateJE.getAsJsonObject().get("tax3_amount").getAsBigDecimal());
										insertRatePs.setBigDecimal(14,
												rateJE.getAsJsonObject().get("tax4_amount").getAsBigDecimal());
										insertRatePs.setBigDecimal(15, rateJE.getAsJsonObject()
												.get("service_charge_amount").getAsBigDecimal());
										insertRatePs.addBatch();
									}
									roomCount += 1;
								}
								rs.close();
								if (roomCount < roomRateDtl.getNumRooms()) {

									while (roomCount < roomRateDtl.getNumRooms()) {
										int rsvRoomNo = 0;
										countFunct.setString(2, "resv_room_no");
										rs = countFunct.executeQuery();
										while (rs.next()) {
											rsvRoomNo = rs.getInt(1);
										}
										rs.close();
										resvRoomInsertPs.setInt(1, rsvRoomNo);
										resvRoomInsertPs.setInt(2, resvDtlNo);
										resvRoomInsertPs.setBoolean(3, false);
										resvRoomInsertPs.setInt(4, ReservationStatus.UNCONFIRMED.getCode());
										resvRoomInsertPs.addBatch();
										roomInsert = true;
										for (JsonElement rateJE : ratePerOcc) {
											insertRatePs.setInt(1, rsvRoomNo);
											insertRatePs.setByte(2, rateJE.getAsJsonObject().get("night").getAsByte());
											insertRatePs.setDate(3, new Date(arrDate.getTime()
													+ (((rateJE.getAsJsonObject().get("night").getAsByte()) - 1))
															* 86400000));
											insertRatePs.setBigDecimal(4,
													rateJE.getAsJsonObject().get("charges").getAsBigDecimal());
											insertRatePs.setBoolean(5,
													rateJE.getAsJsonObject().get("is_tax_included").getAsBoolean());
											insertRatePs.setBigDecimal(6,
													rateJE.getAsJsonObject().get("tax1_pc").getAsBigDecimal());
											insertRatePs.setBigDecimal(7,
													rateJE.getAsJsonObject().get("tax2_pc").getAsBigDecimal());
											insertRatePs.setBigDecimal(8,
													rateJE.getAsJsonObject().get("tax3_pc").getAsBigDecimal());
											insertRatePs.setBigDecimal(9,
													rateJE.getAsJsonObject().get("tax4_pc").getAsBigDecimal());
											insertRatePs.setBigDecimal(10, rateJE.getAsJsonObject()
													.get("service_charge_pc").getAsBigDecimal());
											insertRatePs.setBigDecimal(11,
													rateJE.getAsJsonObject().get("tax1_amount").getAsBigDecimal());
											insertRatePs.setBigDecimal(12,
													rateJE.getAsJsonObject().get("tax2_amount").getAsBigDecimal());
											insertRatePs.setBigDecimal(13,
													rateJE.getAsJsonObject().get("tax3_amount").getAsBigDecimal());
											insertRatePs.setBigDecimal(14,
													rateJE.getAsJsonObject().get("tax4_amount").getAsBigDecimal());
											insertRatePs.setBigDecimal(15, rateJE.getAsJsonObject()
													.get("service_charge_amount").getAsBigDecimal());
											insertRatePs.addBatch();
										}
										roomCount += 1;
									}
								}
							}
						}
					} else {
						/* change room not available - change in room type */
						roomTypeChange = true;
						countFunct.setString(2, "resv_dtl_no");
						rs = countFunct.executeQuery();
						while (rs.next()) {
							roomRateDtl.setResvDtlNo(rs.getInt(1));
						}
						rs.close();
						dtlIdList.add(roomRateDtl.getResvDtlNo());
						RoomRateDetailsCheck rateCheckObj = new RoomRateDetailsCheck();
						BeanUtils.copyProperties(roomRateDtl, rateCheckObj);
						rateCheckObj.setNumRooms(1);
						JsonObject rateObj = getRoomRateDetails(rateCheckObj);
						JsonArray ratePerOcc = rateObj.get("ratePerOcc").getAsJsonArray();
						resvDtlInsertPs.setInt(1, roomRateDtl.getResvDtlNo());
						resvDtlInsertPs.setInt(2, resvNo);
						resvDtlInsertPs.setInt(3, rateObj.get("roomTypeId").getAsInt());
						resvDtlInsertPs.setString(4, rateObj.get("roomTypeCode").getAsString());
						resvDtlInsertPs.setDate(5, new Date(arrDate.getTime()));
						resvDtlInsertPs.setDate(6, new Date(departDate.getTime()));
						resvDtlInsertPs.setInt(7, roomRateDtl.getNumNights());
						resvDtlInsertPs.setInt(8, roomRateDtl.getNumRooms());
						resvDtlInsertPs.setByte(9, ratePerOcc.get(0).getAsJsonObject().get("rate_type").getAsByte());
						resvDtlInsertPs.setInt(10, roomRateDtl.getRateId());
						resvDtlInsertPs.setString(11,
								ratePerOcc.get(0).getAsJsonObject().get("rate_code").getAsString());
						if (ratePerOcc.get(0).getAsJsonObject().get("rate_description").isJsonNull()) {
							resvDtlInsertPs.setString(12, null);
						} else {
							resvDtlInsertPs.setString(12,
									ratePerOcc.get(0).getAsJsonObject().get("rate_description").getAsString());
						}

						resvDtlInsertPs.setInt(13, roomRateDtl.getOccupancy());
						resvDtlInsertPs.setInt(14, roomRateDtl.getDiscId());
						ResvDtl resvdtl = new ResvDtl();
						if (roomRateDtl.getDiscId() != 0) {
							Discount disc = discountService.getRecord(roomRateDtl.getDiscId());
							if (disc.getIsPc()) {
								if (disc.getIsOpen()) {
									resvdtl.setDiscPc(resvdtl.getOpenDiscount().doubleValue());
								} else {
									resvdtl.setDiscPc(disc.getDiscPc().doubleValue());
								}
							} else {
								if (disc.getIsOpen()) {
									resvdtl.setDiscAmount(resvdtl.getOpenDiscount().doubleValue());
								} else {
									resvdtl.setDiscAmount(disc.getDiscAmount().doubleValue());
								}
							}
							resvdtl.setDiscCode(disc.getCode());
							resvdtl.setDiscIsPc(disc.getIsPc());
							resvdtl.setDiscIsOpen(disc.getIsOpen());
						}
						resvDtlInsertPs.setString(15, resvdtl.getDiscCode());
						resvDtlInsertPs.setBoolean(16, resvdtl.isDiscIsPc());
						resvDtlInsertPs.setBoolean(17, resvdtl.isDiscIsOpen());
						resvDtlInsertPs.setBigDecimal(18, BigDecimal.valueOf(resvdtl.getDiscAmount()));
						resvDtlInsertPs.setBigDecimal(19, BigDecimal.valueOf(resvdtl.getDiscPc()));
						resvDtlInsertPs.addBatch();
						roomInsert = true;
						int roomCount = roomRateDtl.getNumRooms();
						while (roomCount > 0) {
							int rsvRoomNo = 0;
							countFunct.setString(2, "resv_room_no");
							rs = countFunct.executeQuery();
							while (rs.next()) {
								rsvRoomNo = rs.getInt(1);
							}
							rs.close();
							resvRoomInsertPs.setInt(1, rsvRoomNo);
							resvRoomInsertPs.setInt(2, roomRateDtl.getResvDtlNo());
							resvRoomInsertPs.setBoolean(3, false);
							resvRoomInsertPs.setInt(4, ReservationStatus.UNCONFIRMED.getCode());
							resvRoomInsertPs.addBatch();
							for (JsonElement rateJE : ratePerOcc) {
								insertRatePs.setInt(1, rsvRoomNo);
								insertRatePs.setByte(2, rateJE.getAsJsonObject().get("night").getAsByte());
								insertRatePs.setDate(3, new Date(arrDate.getTime()
										+ (((rateJE.getAsJsonObject().get("night").getAsByte()) - 1)) * 86400000));
								insertRatePs.setBigDecimal(4,
										rateJE.getAsJsonObject().get("charges").getAsBigDecimal());
								insertRatePs.setBoolean(5,
										rateJE.getAsJsonObject().get("is_tax_included").getAsBoolean());
								insertRatePs.setBigDecimal(6,
										rateJE.getAsJsonObject().get("tax1_pc").getAsBigDecimal());
								insertRatePs.setBigDecimal(7,
										rateJE.getAsJsonObject().get("tax2_pc").getAsBigDecimal());
								insertRatePs.setBigDecimal(8,
										rateJE.getAsJsonObject().get("tax3_pc").getAsBigDecimal());
								insertRatePs.setBigDecimal(9,
										rateJE.getAsJsonObject().get("tax4_pc").getAsBigDecimal());
								insertRatePs.setBigDecimal(10,
										rateJE.getAsJsonObject().get("service_charge_pc").getAsBigDecimal());
								insertRatePs.setBigDecimal(11,
										rateJE.getAsJsonObject().get("tax1_amount").getAsBigDecimal());
								insertRatePs.setBigDecimal(12,
										rateJE.getAsJsonObject().get("tax2_amount").getAsBigDecimal());
								insertRatePs.setBigDecimal(13,
										rateJE.getAsJsonObject().get("tax3_amount").getAsBigDecimal());
								insertRatePs.setBigDecimal(14,
										rateJE.getAsJsonObject().get("tax4_amount").getAsBigDecimal());
								insertRatePs.setBigDecimal(15,
										rateJE.getAsJsonObject().get("service_charge_amount").getAsBigDecimal());
								insertRatePs.addBatch();
							}
							roomCount = roomCount - 1;
						}

					}

				}
				if (resvHdr.getDiscType() == 1) {
					if (discList.size() != 0) {
						discChange = true;
						for (JsonElement discJE : discList) {
							updateDiscPs.setInt(1, discJE.getAsJsonObject().get("id").getAsInt());
							Discount disc = discountService.getRecord(discJE.getAsJsonObject().get("id").getAsInt());
							updateDiscPs.setString(2, disc.getCode());
							updateDiscPs.setBoolean(3, disc.getIsPc());
							updateDiscPs.setBoolean(4, disc.getIsOpen());
							updateDiscPs.setBigDecimal(5, disc.getDiscAmount());
							updateDiscPs.setBigDecimal(6, disc.getDiscPc());
							updateDiscPs.setInt(7, resvNo);
							updateDiscPs.setInt(8, disc.getRateId());
							updateDiscPs.addBatch();
						}
					}
				}
				if (numRoomChange) {
					updateResvDtlNumRoomPs.executeBatch();
				}
				if (rateChange) {
					updateResvDtlRatePs.executeBatch();
				}
				if (roomTypeChange) {
					resvDtlInsertPs.executeBatch();
				}
				if (roomInsert) {
					resvRoomInsertPs.executeBatch();
				}
				if (!dtlIdList.isEmpty()) {
					for (int ids : dtlIdList) {
						if (dtlIds.equals("")) {
							dtlIds = String.valueOf(ids);
						} else {
							dtlIds = dtlIds.concat("," + String.valueOf(ids));
						}
					}
					deleteResvDtl = deleteResvDtl + "(" + dtlIds + ")";
					deleteResvDtlPs = connection.prepareStatement(deleteResvDtl);
					deleteResvDtlPs.setInt(1, resvNo);
					deleteResvDtlPs.executeUpdate();
				}
				deleteRatePs.executeBatch();
				insertRatePs.executeBatch();
				if (discChange) {
					updateDiscPs.executeBatch();
				}
				connection.commit();
			}
		} catch (Exception e) {
			isSave = false;
			connection.rollback();
			logger.error("Method : saveChangeArrivalDate()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			
			
		}
		if (isSave) {
			deletePickupPs = connection.prepareStatement(deletePickup);
			deletePickupPs.setInt(1, 0);
			deletePickupPs.setDate(2, null);
			deletePickupPs.setTime(3, null);
			deletePickupPs.setString(4, null);
			deletePickupPs.setInt(5, 0);
			deletePickupPs.setString(6, null);
			deletePickupPs.setInt(7, 0);
			deletePickupPs.setInt(8, resvNo);
			deletePickupPs.executeUpdate();
			connection.commit();
		}

		return isSave;
	}

	public JsonArray getReservationData(String wherePart) throws Exception {
		JsonArray jsonArr = new JsonArray();
		JsonObject jobject = null;
		String sql = "select vr.resv_no,vr.reserved_by,vr.resv_by_phone,vr.resv_by_mail,vr.resv_by_address,vr.resv_date,vr.resv_for,vr.cut_off_date,vr.arr_date,vr.depart_date,vr.folio_bind_no,vr.num_nights,vr.num_rooms,vr.resv_type_xlt,vr.resv_status_xlt from v_list_reservation vr where  vr.resv_status not in(5,6,7) and "
				+ wherePart;
		Statement statement = null;
		ResultSet rs = null;
		Connection connection = dbConnection.getConnection();
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				jobject = new JsonObject();
				jobject.addProperty("resv_no", rs.getInt("resv_no"));
				jobject.addProperty("reserved_by", rs.getString("reserved_by"));
				jobject.addProperty("resv_by_phone", rs.getString("resv_by_phone"));
				jobject.addProperty("resv_by_mail", rs.getString("resv_by_mail"));
				jobject.addProperty("resv_by_address", rs.getString("resv_by_address"));
				jobject.addProperty("resv_date", rs.getString("resv_date"));
				jobject.addProperty("resv_for", rs.getString("resv_for"));
				jobject.addProperty("cut_off_date", rs.getString("cut_off_date"));
				jobject.addProperty("arr_date", rs.getDate("arr_date").toString());
				jobject.addProperty("depart_date", rs.getDate("depart_date").toString());
				jobject.addProperty("folio_bind_no", rs.getInt("folio_bind_no"));
				jobject.addProperty("num_nights", rs.getInt("num_nights"));
				jobject.addProperty("num_rooms", rs.getInt("num_rooms"));
				jobject.addProperty("resv_type", rs.getString("resv_type_xlt"));
				jobject.addProperty("resv_status", rs.getString("resv_status_xlt"));
				jsonArr.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getResvRecordDetails()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(rs);
		}
		return jsonArr;
	}

	public JsonObject getResvRecordDetails(int resvNo) {
		JsonObject resvRow = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = dbConnection.getConnection();
		String sql = "select * from v_list_reservation where resv_no=" + resvNo;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				resvRow = new JsonObject();
				resvRow.addProperty("resvNo", resultSet.getInt("resv_no"));
				resvRow.addProperty("resvStatus", resultSet.getInt("resv_status"));
				resvRow.addProperty("resvStatusXlt", resultSet.getString("resv_status_xlt"));
				resvRow.addProperty("corporateName", resultSet.getString("corporate_name"));
				resvRow.addProperty("resvDate", resultSet.getString("resv_date"));
				resvRow.addProperty("arrDate", resultSet.getString("arr_date"));
				resvRow.addProperty("resvByName", resultSet.getString("reserved_by"));
				resvRow.addProperty("resvByEmail", resultSet.getString("resv_by_mail"));
				resvRow.addProperty("resvByPhone", resultSet.getString("resv_by_phone"));
				resvRow.addProperty("resvByAddres", resultSet.getString("resv_by_address"));
				resvRow.addProperty("canConfirm", resultSet.getString("can_confirm"));
				resvRow.addProperty("canCancel", resultSet.getString("can_cancel"));
				resvRow.addProperty("canDeposit", resultSet.getString("can_deposit"));
				resvRow.addProperty("canCheckin", resultSet.getString("can_check_in"));
				resvRow.addProperty("canEdit", resultSet.getString("can_edit"));
				resvRow.addProperty("canNoshow", resultSet.getString("can_Noshow"));
				resvRow.addProperty("folioBalance", resultSet.getString("folio_balance"));
				resvRow.addProperty("arrivalDate", resultSet.getString("arr_date"));
				resvRow.addProperty("numRooms", resultSet.getString("num_rooms"));
				resvRow.addProperty("numNights", resultSet.getString("num_nights"));
				resvRow.addProperty("resvFor", resultSet.getString("resv_for"));
				resvRow.addProperty("cutOffDate", resultSet.getString("cut_off_date"));
				resvRow.addProperty("pickupNeeded", resultSet.getString("pickup_needed"));
				resvRow.addProperty("pickupDate", resultSet.getString("pickup_date"));
				resvRow.addProperty("pickupTime", resultSet.getString("pickupTime"));
				resvRow.addProperty("pickupLocation", resultSet.getString("pickupLocation"));
				resvRow.addProperty("pickupSeats", resultSet.getInt("pickupSeats"));
				resvRow.addProperty("pickupRemarks", resultSet.getString("pickupRemarks"));
				resvRow.addProperty("departDate", resultSet.getString("depart_date"));
				resvRow.addProperty("specialrequest", resultSet.getString("special_requests"));
			}
		} catch (Exception e) {
			logger.error("Method : getResvRecordDetails()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
		}
		return resvRow;
	}

	public boolean saveNoShow(int resvNo, List<ResvRoom> resvRoomList, int noShowCount, int checkStatus)
			throws Exception {
		boolean isSave = true;
		Connection con = dbConnection.getConnection();
		PreparedStatement roomUpdatePs = null;
		PreparedStatement hdrUpdatePs = null;
		Statement statement = null;
		ResultSet rs = null;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String roomUpdate = "update resv_room SET  is_noshow =?,noshow_by =? , noshow_reason =? ,noshow_date=?, noshow_time=? ,room_status=?   WHERE resv_room_no=? AND room_status!=?";
		String hdrUpdate = "update resv_hdr set status=? where resv_no = ? and status!=?";
		String numRooms = "select sum_num_rooms from resv_hdr where resv_no = " + resvNo;
		int sumNumRooms = 0, notNoShowCount = 0;
		try {
			con.setAutoCommit(false);
			roomUpdatePs = con.prepareStatement(roomUpdate);
			for (ResvRoom room : resvRoomList) {
				if (room.isNoShow()) {
					roomUpdatePs.setBoolean(1, room.isNoShow());
					roomUpdatePs.setInt(2, room.getNoshowBy());
					roomUpdatePs.setString(3, room.getNoshowReason());
					roomUpdatePs.setDate(4, new Date(room.getNoshowDate().getTime()));
					roomUpdatePs.setString(5, dateFormat.format(room.getNoshowTime()));
					roomUpdatePs.setInt(6, ReservationStatus.NOSHOW.getCode());
					roomUpdatePs.setInt(7, room.getResvRoomNo());
					roomUpdatePs.setInt(8, ReservationStatus.CANCELLED.getCode());
					roomUpdatePs.addBatch();
					roomUpdatePs.executeBatch();
				}
			}
			statement = con.createStatement();
			rs = statement.executeQuery(numRooms);
			while (rs.next()) {
				sumNumRooms = rs.getInt(1);
			}
			hdrUpdatePs = con.prepareStatement(hdrUpdate);
			if (sumNumRooms == notNoShowCount) {
				hdrUpdatePs.setInt(1, ReservationStatus.CONFIRMED.getCode());
				hdrUpdatePs.setInt(2, resvNo);
				hdrUpdatePs.setInt(3, ReservationStatus.PARTCHECKIN.getCode());
			} else {
				hdrUpdatePs.setInt(1, ReservationStatus.NOSHOW.getCode());
				hdrUpdatePs.setInt(2, resvNo);
				hdrUpdatePs.setInt(3, ReservationStatus.PARTCHECKIN.getCode());
			}

			if (noShowCount == 0 || checkStatus == 3) {
				hdrUpdatePs.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			isSave = false;
			con.rollback();
			e.printStackTrace();
			logger.error("Method : saveNoShow()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(con);
			
		}
		return isSave;
	}

	@Override
	public ResvHdr getRecord(int reservationNo) {
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		ResvHdr resvHdr = new ResvHdr();
		ResvDtl resvDtl = null;
		// ResvRate resvRate = null;
		ResvRoom resvRoom = null;

		List<ResvDtl> resvDtlList = new ArrayList<ResvDtl>();
		// List<ResvRate> resvRateList = new ArrayList<ResvRate>();
		List<ResvRoom> resvRoomList = new ArrayList<ResvRoom>();

		try {
			String sql = "select * from v_resevation_info where resv_no=" + reservationNo;
			statement = connection.createStatement(); //
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				resvDtl = new ResvDtl();
				resvRoom = new ResvRoom();
				resvRoom.setResvRoomNo(resultSet.getInt("resv_room_no"));
				resvRoom.setFirstName(resultSet.getString("first_name"));
				resvRoom.setLastName(resultSet.getString("last_name"));
				resvRoom.setGender(resultSet.getString("gender"));
				resvRoom.setAddress(resultSet.getString("address"));
				resvRoom.setEmail(resultSet.getString("email"));
				resvRoom.setNationality(resultSet.getString("phone"));
				resvRoom.setResvDtlNo(resultSet.getInt("resv_room_dtl_no"));

				resvHdr.setResvByFirstName(resultSet.getString("resv_by_first_name"));
				resvHdr.setResvByLastName(resultSet.getString("resv_by_last_name"));
				// resvHdr.setNumNights(numNights);
				resvHdr.setSumNumRooms(resultSet.getShort("sum_num_rooms"));
				resvHdr.setResvNo(resultSet.getInt("resv_no"));
				resvHdr.setMinArrDate(resultSet.getDate("min_arr_date"));
				resvHdr.setCutOffDate(resultSet.getDate("cut_of_date"));
				resvHdr.setMaxDepartDate(resultSet.getDate("max_depart_date"));
				resvHdr.setResvType(resultSet.getByte("resv_type"));
				resvHdr.setCorporateId(resultSet.getInt("corporate_id"));
				resvHdr.setCorporateName(resultSet.getString("corporate_name"));
				// resvHdr.setCorporateAddress(resultSet.getString(columnLabel));
				resvHdr.setResvByMail(resultSet.getString("resv_by_mail"));
				resvHdr.setResvByPhone(resultSet.getString("resv_by_phone"));
				resvHdr.setResvByAddress(resultSet.getString("resv_by_address"));
				resvHdr.setNumNights(resultSet.getByte("num_nights"));

				resvDtl.setRoomTypeId(resultSet.getInt("room_type_id"));
				resvDtl.setRoomTypeCode(resultSet.getString("room_type_code"));
				resvDtl.setDiscCode(resultSet.getString("disc_code"));
				resvDtl.setDiscIsOpen(resultSet.getBoolean("disc_is_open"));
				resvDtl.setDiscIsPc(resultSet.getBoolean("disc_is_pc"));
				resvDtl.setResvDtlNo(resultSet.getInt("resv_dtl_no"));
				resvDtl.setDiscId(resultSet.getInt("disc_id"));
				resvDtl.setRateId(resultSet.getInt("rate_id"));
				resvDtl.setRateCode(resultSet.getString("rate_code"));
				resvDtl.setNumNights(resultSet.getByte("num_nights"));
				resvDtl.setRateType(resultSet.getByte("rate_type"));
				resvHdr.setResvDate(resultSet.getDate("resv_date"));
				resvHdr.setNumAdults(resultSet.getByte("num_adults"));

				resvDtlList.add(resvDtl);
				resvRoomList.add(resvRoom);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord()" + Throwables.getStackTraceAsString(ex));
		} finally {
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return resvHdr;
	}

	public JsonArray getCountryList() {

		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement AllShift = null;
		Connection connection = dbConnection.getConnection();
		String query = "select * from countries";

		try {
			AllShift = connection.prepareStatement(query);
			rs = AllShift.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("id", rs.getInt("id"));
				jObj.addProperty("name", rs.getString("name"));
				jObj.addProperty("sortname", rs.getString("sortname"));

				jArray.add(jObj);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(AllShift);
		}
		return jArray;
	}

	public JsonArray detailViaPhonenumResv(String phone) throws Exception {
		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement customerDetail = null;
		// Encryption encryption = new Encryption();
		Connection connection = dbConnection.getConnection();
		String query = "SELECT resv_hdr.resv_by_first_name,resv_hdr.resv_by_last_name,resv_hdr.resv_by_address,resv_hdr.resv_by_mail,resv_hdr.resv_by_phone,resv_hdr.resv_for,resv_hdr.remarks,resv_hdr.special_requests FROM resv_hdr where resv_by_phone='"
				+ phone + "'";
		try {
			customerDetail = connection.prepareStatement(query);
			rs = customerDetail.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("resv_by_first_name", rs.getString("resv_by_first_name"));
				jObj.addProperty("resv_by_last_name", rs.getString("resv_by_last_name"));
				jObj.addProperty("resv_by_phone", rs.getString("resv_by_phone"));
				jObj.addProperty("resv_by_address", rs.getString("resv_by_address"));
				jObj.addProperty("resv_by_mail", rs.getString("resv_by_mail"));
				jObj.addProperty("resv_for", rs.getString("resv_for"));
				jObj.addProperty("remarks", rs.getString("remarks"));
				jObj.addProperty("special_requests", rs.getString("special_requests"));

				jArray.add(jObj);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(customerDetail);
		}
		return jArray;

	}

	public JsonArray getResvStatus() {
		// int resvNum = 0;
		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		Connection connection = dbConnection.getConnection();
		PreparedStatement getStarusCount = null;
		// String sql = "SELECT resv_dtl_no FROM resv_room WHERE resv_dtl_no IN (SELECT
		// resv_dtl_no FROM resv_room WHERE room_status = 3)AND room_status=2";
		String sql = "SELECT resv_hdr.resv_no AS resvStatusNo FROM resv_hdr INNER JOIN resv_dtl ON resv_hdr.resv_no = resv_dtl.resv_no "
				+ "INNER JOIN resv_room ON resv_dtl.resv_dtl_no = resv_room.resv_dtl_no WHERE resv_hdr.resv_no IN"
				+ "(SELECT resv_hdr.resv_no FROM resv_hdr INNER JOIN resv_dtl ON resv_hdr.resv_no = resv_dtl.resv_no "
				+ "INNER JOIN resv_room ON resv_dtl.resv_dtl_no = resv_room.resv_dtl_no WHERE resv_room.room_status = 3)AND resv_room.room_status = 2";
		try {
			getStarusCount = connection.prepareStatement(sql);
			rs = getStarusCount.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("resvStatusNo", rs.getInt("resvStatusNo"));
				jArray.add(jObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(getStarusCount);
		}
		return jArray;
	}

	@Override
	public JsonArray updateNewRoomRates(List<RoomRateEdited> list) throws Exception {
		Connection con = dbConnection.getConnection();
		con.setAutoCommit(false);
		CallableStatement st = null;
		JsonArray jarry = null;
		try {
			JsonParser parsor = new JsonParser();
			st = con.prepareCall("CALL updateNewRoomRates(?,?)");
			XStream xstrem = new XStream();
			xstrem.alias("newRoomRates", RoomRateEdited.class);
			st.setString(1, xstrem.toXML(list).replaceAll("\\s*[\\r\\n]+\\s*", "").trim());
			st.registerOutParameter(2, java.sql.Types.LONGNVARCHAR);
			st.executeUpdate();
			con.commit();
			JsonElement jsonElemnt = parsor.parse(st.getString(2));
			jarry = jsonElemnt.getAsJsonArray();

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(st);
			dbConnection.releaseResource(con);
		}
		return jarry;
	}

	@Override
	public List<RoomRateDetailsCheck> getRoomRateForAllOccupancy(Date arrDate, int night, int rateid, String roomType) {
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		List<RoomRateDetailsCheck> occupancyRateList = new ArrayList<RoomRateDetailsCheck>();
		RoomRateDetailsCheck occupancyRate = new RoomRateDetailsCheck();
		try {
			// String procedure = "{call GetRoomRatesForAllOccupancy(?,?,?,?,?,?,?)}";
			// con = dbConnection.getConnection();
			// stmt = con.prepareCall(procedure);
			// stmt.setDate(1, arrDate);
			// stmt.setInt(2, 1);
			// stmt.setInt(3, night);
			// stmt.setInt(4, rateid);
			// stmt.setInt(5,0);
			// stmt.setInt(6,0);
			// stmt.setBoolean(7,commonSettings.isServiceChargeApplicable);
			String procedure = "{call getRoomRatesForNightsDetail(?,?,?,?,?,?,?,?,?,?)}";
			con = dbConnection.getConnection();
			stmt = con.prepareCall(procedure);
			stmt.setDate(1, arrDate);
			stmt.setInt(2, night);
			stmt.setInt(3, 0);
			stmt.setString(4, roomType);
			stmt.setInt(5, 0);
			stmt.setInt(6, rateid);
			stmt.setInt(7, 1);
			stmt.setBoolean(8, commonSettings.isServiceChargeApplicable);
			stmt.setInt(9, 0);
			stmt.setInt(10, 0);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				occupancyRate = new RoomRateDetailsCheck();
				occupancyRate.setOccupancyDate(resultSet.getDate("date"));
				occupancyRate.setNumNights(resultSet.getInt("night"));
				occupancyRate.setOcc1Rate(resultSet.getDouble("single"));
				occupancyRate.setOcc2Rate(resultSet.getDouble("double"));
				occupancyRate.setOcc3Rate(resultSet.getDouble("triple"));
				occupancyRate.setOcc4Rate(resultSet.getDouble("quad"));
				occupancyRate.setOpen(resultSet.getBoolean("isOpen"));
				occupancyRate.setRateCode(resultSet.getString("rate_code"));
				occupancyRate.setRoomTypeCode(resultSet.getString("room_type_code"));
				occupancyRate.setOcc1(resultSet.getBoolean("singleOcc"));
				occupancyRate.setOcc2(resultSet.getBoolean("doubleOcc"));
				occupancyRate.setOcc3(resultSet.getBoolean("tripleOcc"));
				occupancyRate.setOcc4(resultSet.getBoolean("quadOcc"));
				occupancyRate.setRoomTypeId(resultSet.getInt("room_type_id"));
				occupancyRate.setRateId(resultSet.getInt("rate_id"));
				occupancyRateList.add(occupancyRate);
			}
		} catch (Exception e) {
			logger.error("Method : getRoomRates()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(stmt);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(con);
		}
		return occupancyRateList;
	}

	@Override
	public JsonArray fetchCustomerData(String salutation, String firstName, String lastName) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = null;
		try {
		connection = dbConnection.getConnection();
		statement = connection.createStatement();
		String selectCustomer = "select checkin_dtl.salutation,checkin_dtl.first_name,checkin_dtl.last_name,checkin_dtl.email,"
				+ "checkin_dtl.phone,checkin_hdr.num_nights,checkin_hdr.arr_date,checkin_hdr.room_type_code,"
				+ "checkin_hdr.room_number,checkin_hdr.act_depart_date from \n"
				+ "checkin_dtl INNER JOIN checkin_hdr ON checkin_dtl.checkin_no=checkin_hdr.checkin_no "
				+ "where checkin_dtl.first_name ='" + firstName + "' and checkin_dtl.last_name='" + lastName
				+ "' ORDER BY checkin_dtl.first_name,checkin_hdr.arr_date";

		resultSet = statement.executeQuery(selectCustomer);
		while (resultSet.next()) {

			jsonObject = new JsonObject();
			jsonObject.addProperty("salutation", resultSet.getString("salutation"));
			jsonObject.addProperty("firstName", resultSet.getString("first_name"));
			jsonObject.addProperty("lastName", resultSet.getString("last_name"));
			jsonObject.addProperty("email", resultSet.getString("email"));
			jsonObject.addProperty("phoneNumber", resultSet.getString("phone"));
			jsonObject.addProperty("arrivalDate", resultSet.getString("arr_date"));
			jsonObject.addProperty("roomType", resultSet.getString("room_type_code"));
			jsonObject.addProperty("roomNumber", resultSet.getString("room_number"));
			jsonObject.addProperty("numNights", resultSet.getString("num_nights"));
			jsonObject.addProperty("deptDate", resultSet.getString("act_depart_date"));
			jsonArray.add(jsonObject);
		}
		}
		catch (Exception e) {
			logger.error("Method : getRoomRates()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
		}
		return jsonArray;
	}

	@Override
	public void deletePickUp(int resvNo) {
		try {

			final String query = "UPDATE resv_hdr SET pickup_needed=0,pickup_date=null,"
					+ "pickupTime=null,pickupLocation='',pickupSeats=0,"
					+ "pickupRemarks='',pickup_provider=0 WHERE resv_no=?";
			jdbcTemplate.update(query, resvNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}

	}

	@SuppressWarnings("resource")
	@Override
	public JsonArray getGrcData(int resvNo) {
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statement1 = null;
		ResultSet resultSet = null;

		JsonArray resvnJson = new JsonArray();
		connection = dbConnection.getConnection();
		int foliobindno = 0;

		String queryfolio = "select resv_hdr.folio_bind_no from resv_hdr WHERE resv_no='" + resvNo + "'";

		try {
			statement = connection.prepareStatement(queryfolio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				foliobindno = resultSet.getInt("folio_bind_no");
				System.out.println(foliobindno);
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		String deposit_resv_count = "select COUNT(txn.acc_mst_code) AS depositCount from txn "
				+ "INNER JOIN resv_hdr on resv_hdr.folio_bind_no = txn.folio_bind_no"
				+ " WHERE txn.acc_mst_code='DEPOSIT' AND resv_hdr.resv_no='" + resvNo + "'";

		String query = "SELECT " + " IFNULL(rroom.first_name, hdr.resv_by_first_name) AS first_name, "
				+ " IFNULL(rroom.last_name,hdr.resv_by_last_name) AS last_name, "
				+ " (CASE hdr.resv_type WHEN 2 THEN hdr.corporate_name ELSE '' END) as corporate_name, "
				+ " rroom.nationality, " + " rroom.passport_no, rroom.nationality, "
				+ "	rroom.passport_no, hdr.arriving_from,hdr.proceeding_to,hdr.country,hdr.state,hdr.gender, "
				+ "hdr.dob,hdr.designation,hdr.company,DATE_FORMAT(hdr.min_arr_date, \"%Y-%m-%d\") AS min_arr_date,  "
				+ " IFNULL(rroom.address ,hdr.resv_by_address) AS address, "
				+ " IFNULL(rroom.phone,hdr.resv_by_phone) AS phone, "
				+ " IFNULL(rroom.email,hdr.resv_by_mail) AS email,         " + " dtl.arr_date As arrival_date, "
				+ " dtl.depart_date AS depart_date, " + " hdr.payment_source, "
				+ " IFNULL((CASE WHEN hdr.resv_type <> 2 THEN hdr.corporate_name ELSE hdr.resv_by_first_name END), '') AS resv_by, "
				+ " rroom.room_number, IFNULL(" + "		(" + "			CASE "
				+ "			WHEN hdr.meal_plan = 1 THEN " + "				'EP' " + "			WHEN "
				+ "				hdr.meal_plan = 2 THEN " + "					'CP' " + "			WHEN "
				+ "					hdr.meal_plan = 3 THEN " + "						'MAP' "
				+ "					ELSE " + "							'AP' " + "						END "
				+ "		),'' " + "	) AS meal_plan,"
				+ " IFNULL((rroom.num_adults + rroom.num_children + rroom.num_infants),0) AS pax,SUM(txn.amount)/hdr.sum_num_rooms AS advance,hdr.resv_no"
				+ " FROM " + " resv_hdr hdr " + " INNER JOIN resv_dtl dtl ON hdr.resv_no = dtl.resv_no "
				+ "INNER JOIN txn ON hdr.folio_bind_no = txn.folio_bind_no "
				+ " LEFT JOIN resv_room rroom ON dtl.resv_dtl_no = rroom.resv_dtl_no WHERE hdr.resv_no = " + resvNo
				+ " AND txn.folio_bind_no=" + foliobindno
				+ " AND txn.acc_mst_code='DEPOSIT' GROUP BY txn.folio_bind_no";

		String query1 = "SELECT " + " IFNULL(rroom.first_name, hdr.resv_by_first_name) AS first_name, "
				+ " IFNULL(rroom.last_name,hdr.resv_by_last_name) AS last_name, "
				+ " (CASE hdr.resv_type WHEN 2 THEN hdr.corporate_name ELSE '' END) as corporate_name, "
				+ " rroom.nationality, " + " rroom.passport_no, rroom.nationality, "
				+ "	rroom.passport_no, hdr.arriving_from,hdr.proceeding_to,hdr.country,hdr.state,hdr.gender, "
				+ "hdr.dob,hdr.designation,hdr.company,DATE_FORMAT(hdr.min_arr_date, \"%Y-%m-%d\") AS min_arr_date,  "
				+ " IFNULL(rroom.address ,hdr.resv_by_address) AS address, "
				+ " IFNULL(rroom.phone,hdr.resv_by_phone) AS phone, "
				+ " IFNULL(rroom.email,hdr.resv_by_mail) AS email,         " + " dtl.arr_date As arrival_date, "
				+ " dtl.depart_date AS depart_date, " + " hdr.payment_source, "
				+ " IFNULL((CASE WHEN hdr.resv_type <> 2 THEN hdr.corporate_name ELSE hdr.resv_by_first_name END), '') AS resv_by, "
				+ " rroom.room_number, IFNULL(" + "		(" + "			CASE "
				+ "			WHEN hdr.meal_plan = 1 THEN " + "				'EP' " + "			WHEN "
				+ "				hdr.meal_plan = 2 THEN " + "					'CP' " + "			WHEN "
				+ "					hdr.meal_plan = 3 THEN " + "						'MAP' "
				+ "					ELSE " + "							'AP' " + "						END "
				+ "		),'' " + "	) AS meal_plan,"
				+ " IFNULL((rroom.num_adults + rroom.num_children + rroom.num_infants),0) AS pax,hdr.resv_no" + " FROM "
				+ " resv_hdr hdr " + " INNER JOIN resv_dtl dtl ON hdr.resv_no = dtl.resv_no "
				+ " LEFT JOIN resv_room rroom ON dtl.resv_dtl_no = rroom.resv_dtl_no WHERE hdr.resv_no = " + resvNo;

		try {
			// statement = connection.createStatement();

			statement = connection.prepareStatement(deposit_resv_count);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int deposit_count = resultSet.getInt("depositCount");
				if (deposit_count == 0) {

					statement = connection.prepareStatement(query1);
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
					//	System.out.println(resultSet.getString("first_name"));
						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("first_name", resultSet.getString("first_name"));
						jsonObject.addProperty("last_name", resultSet.getString("last_name"));
						jsonObject.addProperty("corporate_name", resultSet.getString("corporate_name"));
						jsonObject.addProperty("nationality", resultSet.getString("nationality"));
						jsonObject.addProperty("passport_no", resultSet.getString("passport_no"));
						jsonObject.addProperty("address", resultSet.getString("address"));
						jsonObject.addProperty("email", resultSet.getString("email"));
						jsonObject.addProperty("phone", resultSet.getString("phone"));
						jsonObject.addProperty("arr_date", resultSet.getString("min_arr_date"));
						jsonObject.addProperty("depart_date", resultSet.getString("depart_date"));
						jsonObject.addProperty("payment_source", resultSet.getString("payment_source"));
						jsonObject.addProperty("resv_by", resultSet.getString("resv_by"));
						jsonObject.addProperty("room_number", resultSet.getString("room_number"));
						jsonObject.addProperty("pax", resultSet.getString("pax"));
						jsonObject.addProperty("arriving_from", resultSet.getString("arriving_from"));
						jsonObject.addProperty("proceeding_to", resultSet.getString("proceeding_to"));
						jsonObject.addProperty("country", resultSet.getString("country"));
						jsonObject.addProperty("state", resultSet.getString("state"));
						jsonObject.addProperty("gender", resultSet.getString("gender"));
						jsonObject.addProperty("dob", resultSet.getString("dob"));
						jsonObject.addProperty("designation", resultSet.getString("designation"));
						jsonObject.addProperty("company", resultSet.getString("company"));
						jsonObject.addProperty("meal_plan", resultSet.getString("meal_plan"));
						jsonObject.addProperty("resv_no", resultSet.getDouble("resv_no"));
						resvnJson.add(jsonObject);

					}
				} else {

					statement1 = connection.prepareStatement(query);
					resultSet = statement1.executeQuery();
					while (resultSet.next()) {
						
						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("first_name", resultSet.getString("first_name"));
						jsonObject.addProperty("last_name", resultSet.getString("last_name"));
						jsonObject.addProperty("corporate_name", resultSet.getString("corporate_name"));
						jsonObject.addProperty("nationality", resultSet.getString("nationality"));
						jsonObject.addProperty("passport_no", resultSet.getString("passport_no"));
						jsonObject.addProperty("address", resultSet.getString("address"));
						jsonObject.addProperty("email", resultSet.getString("email"));
						jsonObject.addProperty("phone", resultSet.getString("phone"));
						jsonObject.addProperty("arr_date", resultSet.getString("min_arr_date"));
						jsonObject.addProperty("depart_date", resultSet.getString("depart_date"));
						jsonObject.addProperty("payment_source", resultSet.getString("payment_source"));
						jsonObject.addProperty("resv_by", resultSet.getString("resv_by"));
						jsonObject.addProperty("room_number", resultSet.getString("room_number"));
						jsonObject.addProperty("pax", resultSet.getString("pax"));
						jsonObject.addProperty("arriving_from", resultSet.getString("arriving_from"));
						jsonObject.addProperty("proceeding_to", resultSet.getString("proceeding_to"));
						jsonObject.addProperty("country", resultSet.getString("country"));
						jsonObject.addProperty("state", resultSet.getString("state"));
						jsonObject.addProperty("gender", resultSet.getString("gender"));
						jsonObject.addProperty("dob", resultSet.getString("dob"));
						jsonObject.addProperty("designation", resultSet.getString("designation"));
						jsonObject.addProperty("company", resultSet.getString("company"));
						jsonObject.addProperty("meal_plan", resultSet.getString("meal_plan"));
						jsonObject.addProperty("advance", resultSet.getDouble("advance"));
						jsonObject.addProperty("resv_no", resultSet.getDouble("resv_no"));
						// resvnJson.add("resvnJson", jsonObject);
						resvnJson.add(jsonObject);
					}

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
		}
		return resvnJson;
	}

	@Override
	public JsonArray getBookingData(int resvNo) throws Exception {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statement1 = null;
		ResultSet resultSet = null;

		JsonArray resvnJson = new JsonArray();
		connection = dbConnection.getConnection();
		
		ReservationDAOImp reservtndaoimp = new ReservationDAOImp();
		ResvDtl resvDtlnew = new ResvDtl();
		

		String query = "SELECT hdr.resv_no AS resv_no,IFNULL(txn.amount,0) AS advance,CASE WHEN txn.payment_mode = 1 THEN 'CASH' WHEN txn.payment_mode = 2 THEN 'CARD' WHEN txn.payment_mode = 3 THEN 'ONLINE TRANSFER'  WHEN txn.payment_mode = 4 THEN 'DD' \r\n" + 
				" WHEN txn.payment_mode = 5 THEN 'COMPANY' WHEN txn.payment_mode = 6 THEN 'COMPLEMENTARY' ELSE NULL END AS payment_mode, DATE_FORMAT(hdr.pickup_date,\"%d/%m/%Y\") AS  pickup_date,hdr.pickupTime,hdr.pickup_needed,hdr.pickupLocation,hdr.pickupSeats,\r\n" + 
				"CONCAT(hdr.resv_by_first_name,\" \",hdr.resv_by_last_name)  AS reserved_for,DATE_FORMAT(hdr.conf_time, \"%Y-%m-%d\") AS conf_date \r\n" + 
				" ,(CASE hdr.resv_type WHEN 2 THEN hdr.corporate_name ELSE '' END) as corporate_name,hdr.company AS company\r\n" + 
				" , DATE_FORMAT(hdr.min_arr_date, \"%Y-%m-%d\") AS min_arr_date ,DATE_FORMAT(hdr.max_depart_date, \"%Y-%m-%d\") AS      max_depart_date,hdr.min_arr_time ,hdr.max_depart_time,IFNULL((CASE WHEN hdr.meal_plan = 1 THEN 'EP' WHEN 	hdr.meal_plan = 2 THEN 'CP' WHEN hdr.meal_plan = 3 THEN 'MAP' ELSE 'AP' END 		),'' 	) AS meal_plan\r\n" + 
				" ,dtl.room_type_code AS room_type_code,IFNULL((rroom.num_adults + rroom.num_children + rroom.num_infants),0) AS pax\r\n" + 
				" ,dtl.arr_date,dtl.num_rooms,dtl.num_nights,dtl.occupancy,dtl.disc_id,dtl.disc_amount,dtl.rate_id \r\n" + 
				"  FROM  resv_hdr hdr  \r\n" + 
				"INNER JOIN resv_dtl dtl ON hdr.resv_no = dtl.resv_no \r\n" + 
				"LEFT JOIN resv_room rroom ON dtl.resv_dtl_no = rroom.resv_dtl_no \r\n" + 
				"LEFT JOIN rate_hdr ON dtl.rate_id = rate_hdr.id\r\n" + 
				"LEFT JOIN rate_dtl ON rate_hdr.id = rate_dtl.rate_hdr_id \r\n" + 
				"LEFT JOIN txn ON (hdr.folio_bind_no = txn.folio_bind_no AND txn.acc_mst_code='DEPOSIT')\r\n" + 
				"WHERE hdr.resv_no =" + resvNo;

		 ;

		try {
			// statement = connection.createStatement();

					statement = connection.prepareStatement(query);
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
					//	System.out.println(resultSet.getString("first_name"));
						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("reserved_for", resultSet.getString("reserved_for"));
						jsonObject.addProperty("corporate_name", resultSet.getString("corporate_name"));
						jsonObject.addProperty("min_arr_date", resultSet.getString("min_arr_date"));
						jsonObject.addProperty("min_arr_time", resultSet.getString("min_arr_time"));
						jsonObject.addProperty("max_depart_date", resultSet.getString("max_depart_date"));
						jsonObject.addProperty("max_depart_time", resultSet.getString("max_depart_time"));
						jsonObject.addProperty("payment_mode", resultSet.getString("payment_mode"));
						jsonObject.addProperty("pax", resultSet.getInt("pax"));
						jsonObject.addProperty("company", resultSet.getString("company"));
						jsonObject.addProperty("meal_plan", resultSet.getString("meal_plan"));
						jsonObject.addProperty("resv_no", resultSet.getInt("resv_no"));
						jsonObject.addProperty("room_type_code", resultSet.getString("room_type_code"));
						jsonObject.addProperty("num_rooms", resultSet.getInt("num_rooms"));
						jsonObject.addProperty("num_nights", resultSet.getInt("num_nights"));
						jsonObject.addProperty("pickup_needed", resultSet.getInt("pickup_needed"));
						jsonObject.addProperty("pickup_date", resultSet.getString("pickup_date"));
						jsonObject.addProperty("pickupTime", resultSet.getString("pickupTime"));
						jsonObject.addProperty("pickupSeats", resultSet.getInt("pickupSeats"));
						jsonObject.addProperty("pickupLocation", resultSet.getString("pickupLocation"));
						jsonObject.addProperty("conf_date", resultSet.getString("conf_date"));
						
						
						//rate find
						CallableStatement preparedStatement;
						ResultSet resultSet1 = null;
						double discTaxTotal = 0;
						double discTax = 0;
						double taxPc = 0;
						double totalAmount = 0;
						double discountAmount = 0;
						String amount = "";
						String procedure = "{call GetRoomRateDetails(?,?,?,?,?,?,?,?)}";
						
						double totalRate = 0;
				
						preparedStatement = connection.prepareCall(procedure);
						preparedStatement.setString(1, resultSet.getString("arr_date"));
						preparedStatement.setInt(2, resultSet.getShort("num_rooms"));
						preparedStatement.setInt(3, resultSet.getByte("num_nights"));
						preparedStatement.setInt(4, resultSet.getInt("rate_id"));
						preparedStatement.setInt(5, resultSet.getByte("occupancy"));
						preparedStatement.setInt(6, resultSet.getInt("disc_id"));
						preparedStatement.setBigDecimal(7, resultSet.getBigDecimal("disc_amount"));
						preparedStatement.setBoolean(8, commonSettings.isServiceChargeApplicable);

						resultSet1 = preparedStatement.executeQuery();

						while (resultSet1.next()) {
							amount = resultSet1.getString("total");

							/*	if (resultSet1.getBigDecimal("disc_amount") != null) {

								taxPc = resultSet1.getBigDecimal("tax1_percentage").doubleValue()
										+ (resultSet1.getBigDecimal("tax2_percentage").doubleValue())
										+ (resultSet1.getBigDecimal("tax3_percentage").doubleValue())
										+ (resultSet1.getBigDecimal("tax4_percentage").doubleValue());
								discountAmount = resultSet1.getBigDecimal("disc_amount").doubleValue();
								discTax = ((taxPc / 100) * discountAmount);
								discTaxTotal = discTaxTotal + discTax;

								amount = resultSet1.getString("total");
							} else {
								discountAmount = 0.0;
								amount = resultSet.getString("total");
							}*/
							if(amount == "" || amount == null) {
								amount = "0.0";
							}
							//totalAmount += Double.parseDouble(amount) - discountAmount - discTaxTotal;
							//totalRate =totalAmount;
							totalRate = Double.parseDouble(amount);
							
						}
						jsonObject.addProperty("Rate", totalRate);
						//jsonObject.addProperty("Rate", 0);
						resvnJson.add(jsonObject);
						
						
						
						

					}
				}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
		}
		return resvnJson;

	}


}
