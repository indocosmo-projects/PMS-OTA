package com.indocosmo.pms.web.reservation.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.reservation.model.Cancelreason;
import com.indocosmo.pms.web.reservation.model.ResvDtl;
import com.indocosmo.pms.web.reservation.model.ResvHdr;
import com.indocosmo.pms.web.reservation.model.ResvRate;
import com.indocosmo.pms.web.reservation.model.ResvRoom;
import com.indocosmo.pms.web.reservation.model.RoomAvailability;
import com.indocosmo.pms.web.reservation.model.TxnHdr;
import com.indocosmo.pms.web.room.model.Room;
import com.indocosmo.pms.web.roomType.dao.RoomTypeDao;
import com.indocosmo.pms.web.transaction.dao.TxnDaoImpl;

@Repository
public class ReservationDAOImp implements ReservationDAO {

	@Autowired
	RoomTypeDao roomType;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = Logger.getLogger(ReservationDAOImp.class);

	DbConnection dbConnection = null;

	public ReservationDAOImp() {
		dbConnection = new DbConnection();
	}

	public RoomAvailability getReservtionDetails(int id) {

		RoomAvailability roomAvailability = new RoomAvailability();
		ResvDtl resvDtl;
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		List<ResvDtl> resvDtlList = new ArrayList<ResvDtl>();
		String sql = "select * from v_list_reservation where resv_no = " + id;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				roomAvailability.setArrivalDate(resultSet.getDate("arr_date"));
				roomAvailability.setBookingDate(resultSet.getDate("resv_date"));
				roomAvailability.setRooms(resultSet.getInt("num_rooms"));
				roomAvailability.setNights(resultSet.getInt("num_nights"));
				roomAvailability.setAdults(resultSet.getInt("num_adults"));
				roomAvailability.setChildrens(resultSet.getInt("num_children"));
				roomAvailability.setReservation_No(resultSet.getString("resv_no"));
				roomAvailability.setStatus(resultSet.getString("resv_status_xlt"));
				roomAvailability.setFolioBindNo(resultSet.getInt("folio_bind_no"));
			}

			String rateDtlSql = "select * from resv_dtl where resv_no = " + id;
			resultSet = statement.executeQuery(rateDtlSql);

			while (resultSet.next()) {
				resvDtl = new ResvDtl();
				resvDtl.setArrDate(resultSet.getDate("arr_date"));
				resvDtl.setNumNights(resultSet.getByte("num_nights"));
				resvDtl.setNumRooms(resultSet.getShort("num_rooms"));
				resvDtl.setRateId(resultSet.getInt("rate_id"));
				resvDtl.setOccupancy(resultSet.getByte("occupancy"));
				resvDtl.setDiscId(resultSet.getInt("disc_id"));
				resvDtl.setDiscIsOpen(resultSet.getBoolean("disc_is_open"));
				resvDtl.setDiscIsPc(resultSet.getBoolean("disc_is_pc"));
				boolean is_pc = resultSet.getBoolean("disc_is_pc");

				if (is_pc) {
					resvDtl.setDiscAmount(resultSet.getBigDecimal("disc_pc"));
				} else {
					resvDtl.setDiscAmount(resultSet.getBigDecimal("disc_amount"));
				}

				resvDtlList.add(resvDtl);
			}

			double total = 0.0;
			for (ResvDtl resvDtlL : resvDtlList) {
				total += getNetAmount(resvDtlL);
			}

			roomAvailability.setTotalAmount(total);
			roomAvailability.setDepositAmount(getDepositAmount(roomAvailability.getFolioBindNo()));

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method : getReservtionDetails()" + Throwables.getStackTraceAsString(e));
		}

		finally {
			dbConnection.releaseResource(connection);
		}

		return roomAvailability;
	}

	public int counter(String keyName) {

		Connection connection = dbConnection.getConnection();
		ResultSet resultSet = null;
		int keyValue = 0;
		try {

			String countFunction = "{? = call counter(?)}";
			CallableStatement countFunct = connection.prepareCall(countFunction);
			countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
			countFunct.setString(2, keyName);
			resultSet = countFunct.executeQuery();

			while (resultSet.next()) {
				keyValue = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return keyValue;
	}

	public double getNetAmount(ResvDtl resvDtl) {

		Connection connection = dbConnection.getConnection();
		CallableStatement preparedStatement;
		ResultSet resultSet = null;
		double discTaxTotal = 0;
		double discTax = 0;
		double taxPc = 0;
		double totalAmount = 0;
		double discountAmount = 0;
		String amount = "";

		try {
			String procedure = "{call GetRoomRateDetails(?,?,?,?,?,?,?,?)}";

			preparedStatement = connection.prepareCall(procedure);
			preparedStatement.setString(1, resvDtl.getArrDate().toString());
			preparedStatement.setInt(2, resvDtl.getNumRooms());
			preparedStatement.setInt(3, resvDtl.getNumNights());
			preparedStatement.setInt(4, resvDtl.getRateId());
			preparedStatement.setInt(5, resvDtl.getOccupancy());
			preparedStatement.setInt(6, resvDtl.getDiscId());
			preparedStatement.setBigDecimal(7, resvDtl.getDiscAmount());
			preparedStatement.setBoolean(8, commonSettings.isServiceChargeApplicable);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				if (resultSet.getBigDecimal("disc_amount") != null) {

					taxPc = resultSet.getBigDecimal("tax1_percentage").doubleValue()
							+ (resultSet.getBigDecimal("tax2_percentage").doubleValue())
							+ (resultSet.getBigDecimal("tax3_percentage").doubleValue())
							+ (resultSet.getBigDecimal("tax4_percentage").doubleValue());
					discountAmount = resultSet.getBigDecimal("disc_amount").doubleValue();
					discTax = ((taxPc / 100) * discountAmount);
					discTaxTotal = discTaxTotal + discTax;

					amount = resultSet.getString("total");
					// Double tAmount = Double.parseDouble(amount);
				} else {
					discountAmount = 0.0;
					amount = resultSet.getString("total");
				}

				if(amount == "" || amount == null) {
					amount = "0.0";
				}
				totalAmount += Double.parseDouble(amount) - discountAmount - discTaxTotal;

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getNetAmount()" + Throwables.getStackTraceAsString(e));
		} finally {
			dbConnection.releaseResource(connection);
		}
		return totalAmount;

	}

	public double getDepositAmount(int id) {
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		double amount = 0;

		try {// acc_mst_id=other revenue
			String txnDetals = "select * from txn where acc_mst_id=3 and folio_bind_no=" + id;

			statement = connection.createStatement(); //
			resultSet = statement.executeQuery(txnDetals);

			while (resultSet.next()) {
				amount += resultSet.getBigDecimal("nett_amount").doubleValue();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getDepositAmount()" + Throwables.getStackTraceAsString(e));
		} finally {
			dbConnection.releaseResource(connection);
		}

		return amount;

	}

	public boolean saveTxn(TxnHdr txnHdr) {

		Connection connection = null;
		boolean isSave = true;
		TxnDaoImpl txnDao = new TxnDaoImpl();
		try {
			String keyName = "txn_no";
			connection = dbConnection.getConnection();
			connection.setAutoCommit(false);
			String countFunction = "{? = call counter(?)}";
			CallableStatement countFunct = connection.prepareCall(countFunction);
			countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
			countFunct.setString(2, keyName);
			ResultSet resultSet = countFunct.executeQuery();

			while (resultSet.next()) {
				txnHdr.setTxnNo(resultSet.getInt(1));
			}

			String sql = "insert into txn(txn_no,folio_no,folio_bind_no,txn_source,txn_date,txn_time,acc_mst_id,acc_mst_code,tax_id,tax_code,include_tax,tax1_pc,tax2_pc,tax3_pc,tax4_pc,amount,tax1_amount,tax2_amount,tax3_amount,tax4_amount,nett_amount,payment_mode,txn_status,source_folio_no,dest_folio_no,remarks,user_id,last_upd_ts)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement prepareStatement = connection.prepareStatement(sql);

			prepareStatement.setInt(1, txnHdr.getTxnNo());
			prepareStatement.setInt(2, 1);// folio no
			prepareStatement.setInt(3, txnHdr.getFolioBindNo());
			prepareStatement.setInt(4, 1);// tinyint txn source
			prepareStatement.setDate(5, new Date(txnHdr.getTxnDate().getTime()));// date
			prepareStatement.setTimestamp(6, new Timestamp(txnHdr.getLastUpdates().getTime()));
			prepareStatement.setInt(7, txnHdr.getAccMstId());
			prepareStatement.setString(8, txnHdr.getAccMstCode());
			prepareStatement.setInt(9, txnHdr.getTaxId());
			prepareStatement.setString(10, txnHdr.getTaxCode());
			prepareStatement.setInt(11, 0);// bit //need to add variables
			prepareStatement.setBigDecimal(12, txnHdr.getTax1Pc());
			prepareStatement.setBigDecimal(13, txnHdr.getTax2Pc());
			prepareStatement.setBigDecimal(14, txnHdr.getTax3Pc());
			prepareStatement.setBigDecimal(15, txnHdr.getTax4Pc());
			prepareStatement.setBigDecimal(16, txnHdr.getAmount());
			prepareStatement.setBigDecimal(17, txnHdr.getTax1Amount());
			prepareStatement.setBigDecimal(18, txnHdr.getTax2Amount());
			prepareStatement.setBigDecimal(19, txnHdr.getTax3Amount());
			prepareStatement.setBigDecimal(20, txnHdr.getTax4Amount());
			prepareStatement.setBigDecimal(21, txnHdr.getNetAmount());
			prepareStatement.setInt(22, txnHdr.getPaymentMode());// tinyint
			prepareStatement.setInt(23, 1);// tinyint
			prepareStatement.setInt(24, 1);
			prepareStatement.setInt(25, 1);
			prepareStatement.setString(26, txnHdr.getRemarks());// string
			prepareStatement.setInt(27, txnHdr.getUserId());
			prepareStatement.setDate(28, new Date(txnHdr.getLastUpdates().getTime()));// date
			prepareStatement.executeUpdate();
			txnDao.updateTxnLog(connection, txnHdr.getTxnNo(), "INSERT");
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method : saveTxn()" + Throwables.getStackTraceAsString(e));
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			isSave = false;
		} finally {
			dbConnection.releaseResource(connection);
		}
		return isSave;

	}

	public HashMap<String, Object> getResvRecord(int resvNo) throws Exception {

		Connection connection = dbConnection.getConnection();
		ResultSet resultSet = null;

		ResvHdr resvHdr = new ResvHdr();
		List<ResvDtl> resvDtlList = new ArrayList<ResvDtl>();
		List<ResvRoom> resvRoomList = new ArrayList<ResvRoom>();
		List<ResvRate> resvRateList = new ArrayList<ResvRate>();
		HashMap<String, Object> resultData = new HashMap<String, Object>();
		String selectResvHdrQuery = "SELECT folio.folio_no, resv_hdr.* FROM resv_hdr INNER JOIN folio ON folio.resv_no = resv_hdr.resv_no WHERE resv_hdr.resv_no=?";
		try {
			PreparedStatement resvHdrPs = connection.prepareStatement(selectResvHdrQuery);
			resvHdrPs.setInt(1, resvNo);
			resultSet = resvHdrPs.executeQuery();

			while (resultSet.next()) {

				resvHdr.setResvNo(resultSet.getInt("resv_No"));
				resvHdr.setFolioBindNo(resultSet.getInt("folio_bind_no"));
				resvHdr.setStatus(getStatusName(resultSet.getByte("status")));
				resvHdr.setResvType(resultSet.getByte("resv_type"));
				resvHdr.setCorporateId(resultSet.getInt("corporate_id"));
				resvHdr.setCorporateName(resultSet.getString("corporate_name"));
				resvHdr.setCorporateAddress(resultSet.getString("corporate_address"));
				resvHdr.setBillingInstruction(resultSet.getString("billing_instruction"));
				resvHdr.setRemarks(resultSet.getString("remarks"));
				resvHdr.setResvDate(resultSet.getDate("resv_date"));
				resvHdr.setResvByFirstName(resultSet.getString("resv_by_first_name"));
				resvHdr.setResvByLastName(resultSet.getString("resv_by_last_name"));
				resvHdr.setResvByAddress(resultSet.getString("resv_by_address"));
				resvHdr.setResvByMail(resultSet.getString("resv_by_mail"));
				resvHdr.setResvByPhone(resultSet.getString("resv_by_phone"));
				resvHdr.setGuestId(resultSet.getInt("guest_id"));
				resvHdr.setFolioNo(resultSet.getInt("folio_no"));
				resvHdr.setReservedFor(resultSet.getString("resv_for"));
				resvHdr.setNumAdults(resultSet.getByte("num_adults"));
				resvHdr.setNumChildren(resultSet.getByte("num_children"));
				resvHdr.setMinArrDate(resultSet.getDate("min_arr_date"));
				resvHdr.setSumNumRooms(resultSet.getShort("sum_num_rooms"));
				resvHdr.setDiscType(resultSet.getByte("disc_type"));
			}
			/** resv_dtl section */
			resultSet = null;
			ResultSet roomResultSet = null;
			ResultSet rateResultSet = null;
			ResvDtl resvDtl = null;
			ResvRoom resvRoom = null;
			ResvRate resvRate = null;
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
				resvDtl.setRateType(resultSet.getByte("rate_type"));
				resvDtl.setRateId(resultSet.getInt("rate_id"));
				resvDtl.setRateCode(resultSet.getString("rate_code"));
				resvDtl.setRateDescription(resultSet.getString("rate_description"));
				resvDtl.setOccupancy(resultSet.getByte("occupancy"));
				resvDtl.setDiscId(resultSet.getInt("disc_id"));
				resvDtl.setDiscCode(resultSet.getString("disc_code"));
				resvDtl.setDiscIsPc(resultSet.getBoolean("disc_is_pc"));
				resvDtl.setDiscIsOpen(resultSet.getBoolean("disc_is_open"));
				resvDtl.setDiscAmount(resultSet.getBigDecimal("disc_amount"));
				resvDtl.setDiscPc(resultSet.getBigDecimal("disc_pc"));
				resvDtl.setNumRooms(resultSet.getShort("num_rooms"));
				resvDtl.setNumNights(resultSet.getByte("num_nights"));
				resvDtlList.add(resvDtl);

				/** resv_room section */

				roomResultSet = null;

				String selectResvRoomQuery = "select * from resv_room where resv_dtl_no=?";
				PreparedStatement resvRoomPs = connection.prepareStatement(selectResvRoomQuery);
				resvRoomPs.setInt(1, resvDtl.getResvDtlNo());
				roomResultSet = resvRoomPs.executeQuery();

				// ReservationStatus reservationStatus= null;

				while (roomResultSet.next()) {
					resvRoom = new ResvRoom();
					resvRoom.setResvRoomNo(roomResultSet.getInt("resv_room_no"));
					resvRoom.setResvDtlNo(roomResultSet.getInt("resv_dtl_no"));
					resvRoom.setRoomNumber(roomResultSet.getString("room_number"));
					resvRoom.setFirstName(roomResultSet.getString("first_name"));
					resvRoom.setLastName(roomResultSet.getString("last_name"));
					resvRoom.setGender(roomResultSet.getString("gender"));
					resvRoom.setAddress(roomResultSet.getString("address"));
					resvRoom.setEmail(roomResultSet.getString("email"));
					resvRoom.setPhone(roomResultSet.getString("phone"));
					resvRoom.setNationality(roomResultSet.getString("nationality"));
					resvRoom.setPassportNo(roomResultSet.getString("passport_no"));
					resvRoom.setPassportExpiryOn(roomResultSet.getDate("passport_expiry_on"));
					resvRoom.setRemarks(roomResultSet.getString("remarks"));
					resvRoom.setRoomStatus(getStatusName(roomResultSet.getByte("room_status")));

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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getResvRecord()" + Throwables.getStackTraceAsString(e));
		} finally {
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return resultData;
	}

	public List<Room> getAvailableRooms(Date arrivalDate, Date departDate, int roomType, int occupancy)
			throws Exception {

		List<Room> availableRooms = new ArrayList<Room>();
		Connection con = dbConnection.getConnection();
		ResultSet rs = null;
		CallableStatement st = null;
		Room room;
		try {
			st = con.prepareCall("{call GetVacantRooms(?,?,?,?)}");
			st.setDate(1, arrivalDate);
			st.setDate(2, departDate);
			st.setInt(3, roomType);
			st.setInt(4, occupancy);

			rs = st.executeQuery();

			while (rs.next()) {

				room = new Room();

				room.setNumber(rs.getString("number"));
				room.setName(rs.getString("name"));
				// TODO

				availableRooms.add(room);
			}

		} catch (Exception e) {
			logger.error("Method : getAvailableRooms()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
		} finally {

			dbConnection.releaseResource(st);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);
		}

		return availableRooms;
	}

	public ReservationStatus getStatusName(byte value) {
		ReservationStatus name = null;
		switch (value) {

		case 0:
			name = ReservationStatus.UNCONFIRMED;
			break;
		case 1:
			name = ReservationStatus.CONFIRMED;
			break;
		case 2:
			name = ReservationStatus.CANCELLED;
			break;
		case 3:
			name = ReservationStatus.NOSHOW;
			break;
		case 4:
			name = ReservationStatus.PARTCHECKIN;
			break;
		case 5:
			name = ReservationStatus.CHECKIN;
			break;
		case 6:
			name = ReservationStatus.PARTCHECKOUT;
			break;
		case 7:
			name = ReservationStatus.CHECKOUT;
			break;

		}
		return name;
	}

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
				resvRoom.setRoomNumber(resultSet.getString("room_number"));
				resvRoom.setFirstName(resultSet.getString("first_name"));
				resvRoom.setLastName(resultSet.getString("last_name"));
				resvRoom.setGender(resultSet.getString("gender"));
				resvRoom.setAddress(resultSet.getString("address"));
				resvRoom.setEmail(resultSet.getString("email"));
				resvRoom.setNationality(resultSet.getString("phone"));
				resvRoom.setResvDtlNo(resultSet.getInt("resv_room_dtl_no"));

				resvHdr.setDiscTypes(resultSet.getByte("disc_type"));
				resvHdr.setResvByFirstName(resultSet.getString("resv_by_first_name"));
				resvHdr.setResvByLastName(resultSet.getString("resv_by_last_name"));
				// resvHdr.setNumNights(numNights);
				resvHdr.setSumNumRooms(resultSet.getShort("sum_num_rooms"));
				resvHdr.setResvNo(resultSet.getInt("resv_no"));
				resvHdr.setFolioBindNo(resultSet.getInt("folio_bind_no"));
				resvHdr.setReservationStatus(resultSet.getByte("status"));
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
				resvDtl.setDiscAmount(resultSet.getBigDecimal("disc_amount"));
				resvDtl.setDiscCode(resultSet.getString("disc_code"));
				resvDtl.setDiscIsOpen(resultSet.getBoolean("disc_is_open"));
				resvDtl.setDiscIsPc(resultSet.getBoolean("disc_is_pc"));
				resvDtl.setDiscPc(resultSet.getBigDecimal("disc_pc"));
				resvDtl.setResvDtlNo(resultSet.getInt("resv_dtl_no"));
				resvDtl.setDiscId(resultSet.getInt("disc_id"));
				resvDtl.setRateId(resultSet.getInt("rate_id"));
				resvDtl.setRateCode(resultSet.getString("rate_code"));
				resvDtl.setNumNights(resultSet.getByte("num_nights"));
				resvDtl.setNumRooms(resultSet.getShort("num_rooms"));
				resvDtl.setRateType(resultSet.getByte("rate_type"));
				resvHdr.setReservedFor(resultSet.getString("resv_for"));
				resvHdr.setResvDate(resultSet.getDate("resv_date"));
				resvHdr.setNumAdults(resultSet.getByte("num_adults"));

				resvDtlList.add(resvDtl);
				resvRoomList.add(resvRoom);
			}
			resvHdr.setResvDtlList(resvDtlList);
			resvHdr.setResvRoomList(resvRoomList);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Method : getRecord()" + Throwables.getStackTraceAsString(ex));
		} finally {
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return resvHdr;
	}

	public RoomAvailability getRoomDetails(int resvId) {
		RoomAvailability roomAvailability = new RoomAvailability();
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "select * from v_list_reservation where resv_no = " + resvId;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				roomAvailability.setArrivalDate(resultSet.getDate("arr_date"));
				roomAvailability.setReservation_No(resultSet.getString("resv_no"));
				roomAvailability.setBookingDate(resultSet.getDate("resv_date"));
				roomAvailability.setStatus(resultSet.getString("resv_status_xlt"));
				roomAvailability.setCorporate_ta(resultSet.getString("corporate_name"));
			}

		} catch (SQLException e) {
			logger.error("Method : getRoomDetails()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
		} finally {

			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return roomAvailability;

	}

	public ResvHdr getPersonalDetails(int resrvId) {

		ResvHdr rerHdr = new ResvHdr();
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "select * from resv_hdr where resv_no = " + resrvId;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				rerHdr.setResvByFirstName(resultSet.getString("resv_by_first_name"));
				rerHdr.setResvByLastName(resultSet.getString("resv_by_last_name"));
				rerHdr.setResvByMail(resultSet.getString("resv_by_mail"));
				rerHdr.setResvByPhone(resultSet.getString("resv_by_phone"));
				rerHdr.setFolioBindNo(resultSet.getInt("folio_bind_no"));
				rerHdr.setReservedFor(resultSet.getString("resv_for"));
				rerHdr.setResvByAddress(resultSet.getString("resv_by_address"));
				rerHdr.setNumAdults(resultSet.getByte("num_adults"));
			}

		} catch (SQLException e) {
			logger.error("Method : getPersonalDetails()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
		} finally {
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return rerHdr;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pms.web.reservation.dao.ReservationDAO#cancellationSave(com.
	 * indocosmo.pms.web.reservation.model.ResvHdr)
	 */
	public boolean cancellationSave(ResvHdr resvHdr) {
		boolean isSave = true;
		Connection connection = null;

		String sql = "UPDATE resv_hdr SET status=?,cancel_by=?,cancel_date=?,cancel_reason=?,cancel_time=? WHERE resv_no=?";
		PreparedStatement prepareStatement = null;
		try {
			connection = dbConnection.getConnection();
			connection.setAutoCommit(false);
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setByte(1, resvHdr.getStatus());
			/* prepareStatement.setDate(2,(Date)resvHdr.getCancelledOn()); */
			prepareStatement.setInt(2, resvHdr.getCancelledBy());
			prepareStatement.setDate(3, (Date) resvHdr.getCancel_date());
			prepareStatement.setString(4, resvHdr.getCancelledReason());
			prepareStatement.setString(5, resvHdr.getCancelTime());
			prepareStatement.setInt(6, resvHdr.getResvNo());

			prepareStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			logger.error("Method : cancellationSave()" + Throwables.getStackTraceAsString(e));
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			dbConnection.releaseResource(prepareStatement);
			dbConnection.releaseResource(connection);
		}

		return isSave;
	}

	/*
	 * Confirmation By Vishnu Mohan
	 */
	public boolean confirmation(ResvHdr resvHdr) {

		boolean isSave = false;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement updateRoom = null;
		String sql = "UPDATE resv_hdr SET status=?,conf_date=?,conf_time=?,conf_by=?,conf_ref_no=? WHERE resv_no=?";
		String sqlUpdate = "UPDATE resv_room SET room_status = ? WHERE resv_room_no IN" 
				+"( SELECT resv_room_no FROM (SELECT * FROM resv_room) AS newResvRoom WHERE resv_dtl_no IN "
				+"(SELECT resv_dtl_no FROM resv_dtl WHERE resv_no = "
				+ resvHdr.getResvNo() + ")) AND room_status NOT IN(?,?)";
		try {
			connection = dbConnection.getConnection();
			connection.setAutoCommit(false);
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setByte(1, resvHdr.getStatus());
			prepareStatement.setDate(2, (Date) resvHdr.getConfDate());
			prepareStatement.setString(3, resvHdr.getConfTime());
			prepareStatement.setInt(4, resvHdr.getConfBy());
			prepareStatement.setString(5, resvHdr.getConfRefNo());
			prepareStatement.setInt(6, resvHdr.getResvNo());
			if (prepareStatement.executeUpdate() == 1) {
				// connection=dbConnection.getConnection();
				connection.setAutoCommit(false);
				updateRoom = connection.prepareStatement(sqlUpdate);
				updateRoom.setInt(1, resvHdr.getStatus());
				updateRoom.setInt(2, ReservationStatus.NOSHOW.getCode());
				updateRoom.setInt(3, ReservationStatus.CANCELLED.getCode());
				if (updateRoom.executeUpdate() == 1) {
					isSave = true;
				}
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method : confirmation()" + Throwables.getStackTraceAsString(e));
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			dbConnection.releaseResource(connection);
		}
		return isSave;
	}

	public List<ResvDtl> getTotalPayable(int resrvId) {
		ResvDtl resvdtl = null;
		Connection connection = dbConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		List<ResvDtl> resvDtlList = new ArrayList<ResvDtl>();
		String sql = "SELECT * from resv_dtl where resv_no=" + resrvId;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				resvdtl = new ResvDtl();
				resvdtl.setArrDate(resultSet.getDate("arr_date"));
				resvdtl.setNumRooms(resultSet.getShort("num_rooms"));
				resvdtl.setNumNights(resultSet.getByte("num_nights"));
				resvdtl.setRateId(resultSet.getInt("rate_id"));
				resvdtl.setOccupancy(resultSet.getByte("occupancy"));
				resvdtl.setDiscId(resultSet.getInt("disc_id"));
				resvdtl.setDiscAmount(resultSet.getBigDecimal("disc_amount"));
				resvDtlList.add(resvdtl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Method : getTotalPayable()" + Throwables.getStackTraceAsString(e));
		} finally {

			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return resvDtlList;
	}

	public JsonObject getStatusRooms(Date arrivalDate, Date departDate, String roomType, int occupancy, String roomno) {
		Connection con = dbConnection.getConnection();
		ResultSet rs = null;
		ResultSet rs1 = null;
		CallableStatement st = null;
		JsonObject resvRow = null;
		JsonObject jsonObject = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		Statement statement = null;
		int roomTypeid;
		int roomstatus = 0;

		try {

			String statusroom = "select depart_date from resv_dtl WHERE resv_dtl_no in(SELECT  resv_dtl_no from resv_room where room_number='"
					+ roomno + "')";
			statement = con.createStatement();
			rs1 = statement.executeQuery(statusroom);
			rs1.next();

			String sql = "SELECT id from room_type where code='" + roomType + "' ";

			statement = con.createStatement();
			rs1 = statement.executeQuery(sql);
			rs1.next();
			roomTypeid = rs1.getInt("id");

			st = con.prepareCall("{call GetVacantRooms(?,?,?,?)}");
			st.setDate(1, arrivalDate);
			st.setDate(2, departDate);
			st.setInt(3, roomTypeid);
			st.setInt(4, occupancy);

			rs = st.executeQuery();

			while (rs.next()) {

				resvRow = new JsonObject();

				resvRow.addProperty("room_no", rs.getString("number"));
				jsonArray.add(resvRow);

			}
			roomstatus = 1;

			resvRow = new JsonObject();

			resvRow.addProperty("roomstatus", roomstatus);
			jsonArray.add(resvRow);
			jsonObject.add("roomassign", jsonArray);

		} catch (Exception e) {
			logger.error("Method : getStatusRooms()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
		} finally {

			dbConnection.releaseResource(st);
			dbConnection.releaseResource(rs);
			dbConnection.releaseResource(con);
		}
		return jsonObject;
	}

	/// noshow

	public String UpdateNoshow(int resv_no, String[] slctdtlno, int selectstatus, ResvHdr resvhdr) {

		String sql = "update  resv_hdr SET status=3 , cancel_date =?, cancel_by =? , cancel_reason =? , cancel_time =? WHERE resv_no=?";
		String sqlroom = "update resv_room SET  noshow_by =? , noshow_reason =? ,noshow_date=?, noshow_time=?,is_noshow =1 ,room_status=3   WHERE resv_room_no=?";

		try {
			if (selectstatus == 1) {
				jdbcTemplate.update(sql, resvhdr.getCancel_date(), resvhdr.getCancelledBy(),
						resvhdr.getCancelledReason(), resvhdr.getCancelTime(), resv_no);
			}

			for (int i = 0; i <= slctdtlno.length / 2; i = i + 2) {

				jdbcTemplate.update(sqlroom, resvhdr.getCancelledBy(), slctdtlno[i + 1], resvhdr.getCancel_date(),
						resvhdr.getCancelTime(), slctdtlno[i]);

			}
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Method : UpdateNoshow()" + Throwables.getStackTraceAsString(e));
		}

		return "success";

	}

	public int updateCutOffDate(ResvHdr resvHdr) {
		Connection connection = null;
		// ResultSet resultSet = null;
		PreparedStatement psResvHdrUpdate = null;
		int result = 0;
		String resvHdrcutOffDateUpdate = "UPDATE resv_hdr SET cut_off_date = ? WHERE resv_no = ?";
		try {
			connection = dbConnection.getConnection();
			connection.setAutoCommit(false);
			psResvHdrUpdate = connection.prepareStatement(resvHdrcutOffDateUpdate);
			psResvHdrUpdate.setDate(1, new java.sql.Date(resvHdr.getCutOffDate().getTime()));
			psResvHdrUpdate.setInt(2, resvHdr.getResvNo());
			result = psResvHdrUpdate.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			logger.error("Method : updateCutOffDate()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			result = 0;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(psResvHdrUpdate);

		}

		return result;
	}

	@Override
	public int updatePickUpDetails(ResvHdr resvHdr) {
		Connection connection = null;
		// ResultSet resultSet = null;
		PreparedStatement psResvHdrUpdate = null;
		int result = 0;
		String resvHdrcutOffDateUpdate = "";
		try {
			connection = dbConnection.getConnection();
			connection.setAutoCommit(false);
			if (resvHdr.isPickupNeeded()) {
				resvHdrcutOffDateUpdate = "UPDATE resv_hdr SET pickup_needed = ?," + "pickup_date = ?,"
						+ "pickupTime = ?," + "pickupLocation = ?," + "pickupSeats = ?," + "pickupRemarks = ?"
						+ " WHERE resv_no = ?";

				psResvHdrUpdate = connection.prepareStatement(resvHdrcutOffDateUpdate);
				psResvHdrUpdate.setBoolean(1, resvHdr.isPickupNeeded());
				psResvHdrUpdate.setDate(2, new java.sql.Date(resvHdr.getPickupDate().getTime()));
				psResvHdrUpdate.setString(3, resvHdr.getPickupTime());
				psResvHdrUpdate.setString(4, resvHdr.getPickupLocation());
				psResvHdrUpdate.setInt(5, resvHdr.getPickupSeats());
				psResvHdrUpdate.setString(6, resvHdr.getPickupRemarks());
				psResvHdrUpdate.setInt(7, resvHdr.getResvNo());

			} else {
				resvHdrcutOffDateUpdate = "UPDATE resv_hdr SET pickup_needed = ?" + " WHERE resv_no = ?";
				psResvHdrUpdate = connection.prepareStatement(resvHdrcutOffDateUpdate);
				psResvHdrUpdate.setBoolean(1, resvHdr.isPickupNeeded());
				psResvHdrUpdate.setInt(2, resvHdr.getResvNo());

			}

			result = psResvHdrUpdate.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			logger.error("Method : updatePickUpDetails()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			result = 0;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(psResvHdrUpdate);

		}

		return result;
	}

}