package com.indocosmo.pms.web.ota.dao.reservation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.entity.reservation.OTARentalInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;

@Repository
public class OTADifferentTableSaveDaoImpl {

	DbConnection dbConnection = null;

	public OTADifferentTableSaveDaoImpl() {
		dbConnection = new DbConnection();
	}

	public static Connection getConnection() {
		Connection con = null;
		ResourceBundle dataBaseProperty = ResourceBundle.getBundle("database");
		String url = dataBaseProperty.getString("jdbc.url");
		String userName = dataBaseProperty.getString("jdbc.username");
		String passWord = dataBaseProperty.getString("jdbc.password");
		String driverClass = dataBaseProperty.getString("jdbc.driverClassName");
		try {
			Class.forName(driverClass);
			con = DriverManager.getConnection(url, userName, passWord);
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}

	public int saveResvHdr(OTABookingTrans reservation, OTARentalInfo rental, OTATaxDeatil taxdetail,
			OTAReservation reselist) {

		Statement smt = null;
		ResultSet rs = null;
		String sqlResvHdrInsert = "insert into resv_hdr(resv_no,ota_id,folio_bind_no,status,min_arr_time,min_arr_date,max_depart_time,max_depart_date,sum_num_rooms,resv_type,payment_source,salutation,resv_by_first_name,resv_by_last_name,resv_by_address,resv_by_mail,resv_by_phone,num_adults,num_children,resv_date,resv_taken_by,cut_off_date,country,state,meal_plan,gender)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String sqlResvDtlInsert = "insert into resv_dtl(resv_dtl_no, resv_no, room_type_id, room_type_code, arr_date, depart_date, num_nights, num_rooms, rate_type, rate_id, rate_code, occupancy) values(?,?,?,?,?,?,?,?,?,?,?,?)";

		String sqlResvRoomInsert = "insert into resv_room(resv_room_no, resv_dtl_no,is_noshow,room_status, first_name, last_name, email, phone, address, num_adults, num_children,gender,nationality,state) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String sqlResvRateInsert = "insert into resv_rate(resv_room_no, night, room_charge, include_tax, tax1_pc, tax2_pc, tax3_pc, tax4_pc,service_charge_pc, tax1, tax2, tax3, tax4,service_charge) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String sqlFolioInsert = "insert into folio(folio_no, folio_bind_no, resv_no) values(?,?,?)";

		int status = 0, room_id;
		String a = null, b = null, c = null, ota = null;
		try {

			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(sqlResvHdrInsert);
			PreparedStatement rd = con.prepareStatement(sqlResvDtlInsert);
			PreparedStatement rr = con.prepareStatement(sqlResvRoomInsert);
			PreparedStatement rt = con.prepareStatement(sqlResvRateInsert);
			PreparedStatement ft = con.prepareStatement(sqlFolioInsert);
			CallableStatement countFunct = null;

			/////////////////////
			int UID = reselist.getUniquereservationid();
			System.out.println("UIDDDDDDDDDDDDDD" + UID);
			String sql1 = ("SELECT ota_id FROM resv_hdr WHERE ota_id=" + UID);
			con = dbConnection.getConnection();
			Statement stmtota = con.createStatement();
			rs = stmtota.executeQuery(sql1);

			while (rs.next()) {
				ota = rs.getString("ota_id");
			}
			rs.close();

			System.out.println(ota);
			////////////////////////////////

			if (ota == null) {

				String countFunction = "{? = call counter(?)}";
				countFunct = con.prepareCall(countFunction);
				countFunct.registerOutParameter(1, java.sql.Types.INTEGER);

				// rs = countFunct.executeQuery();
				int resvno = 0;
				countFunct.setString(2, "resv_no");
				rs = countFunct.executeQuery();
				while (rs.next()) {
					resvno = rs.getInt(1);
				}
				rs.close();

				int foliobindno = 0;
				countFunct.setString(2, "folio_bind_no");
				rs = countFunct.executeQuery();
				while (rs.next()) {
					foliobindno = rs.getInt(1);
				}
				rs.close();

				ps.setInt(1, resvno);
				ps.setInt(2, reselist.getUniquereservationid());
				ps.setInt(3, foliobindno);
				// ps.setInt(1,reservation.getId()); ///------> needed parameter value can be
				// set

				ps.setInt(4, 0);

				/////////// string to time conversion
				String s = reservation.getArrivaltime();
				DateFormat formatter = new SimpleDateFormat("HH:mm:SS");
				java.sql.Time timeValue = new java.sql.Time(formatter.parse(s).getTime());
				/////////////////

				ps.setTime(5, timeValue);
				////////////// String to date
				String startDate = reservation.getStart();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = sdf.parse(startDate);

				java.sql.Date sqlDate = new Date(date.getTime());
				//// Combine date and time

				LocalDate datearr = sqlDate.toLocalDate();

				LocalTime timearr = timeValue.toLocalTime();
				LocalDateTime dateTime = datearr.atTime(timearr);
				Timestamp timestamp_object = Timestamp.valueOf(dateTime);
				ps.setTimestamp(6, timestamp_object);

				String dep_time = reservation.getDeparturetime();
				DateFormat formatterdep = new SimpleDateFormat("HH:mm:SS");
				java.sql.Time timeValuedep = new java.sql.Time(formatterdep.parse(s).getTime());
				ps.setTime(7, timeValuedep);

				//////////////////////////////
				String dep_date = reservation.getEnd();

				SimpleDateFormat sdfdep = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date datedep = sdfdep.parse(dep_date);

				java.sql.Date sqlDatedep = new Date(datedep.getTime());

				LocalDate dateend = sqlDatedep.toLocalDate();
				LocalTime timeend = timeValuedep.toLocalTime();
				LocalDateTime dateTimedep = dateend.atTime(timeend);
				Timestamp timestamp_objectdep = Timestamp.valueOf(dateTimedep);

				ps.setTimestamp(8, timestamp_objectdep);

				// ps.setTimestamp(7,dtd);
				// ps.setString(7,reservation.getEnd().concat(reservation.getDeparturetime()));
				ps.setString(9, "1"); // sum_num_rooms
				ps.setString(10, "3"); // resv_type
				ps.setString(11, "1"); // payment source
				ps.setString(12, reservation.getSalutation());
				ps.setString(13, reservation.getFirstname());
				ps.setString(14, reservation.getLastname());
				ps.setString(15, reservation.getAddress());
				ps.setString(16, reservation.getEmail());
				ps.setString(17, reservation.getPhone());
				ps.setString(18, rental.getAdult());
				ps.setString(19, rental.getChild());
				ps.setString(20, reservation.getCreatedatetime());
				ps.setInt(21, 0);// resv taken by
				ps.setString(22, reservation.getModifydatetime());
				ps.setString(23, reservation.getCountry());
				ps.setString(24, reservation.getState());
				ps.setString(25, "1"); // meal plan plz refer OTA BOOKING TRANS package name;
				ps.setString(26, reservation.getGender());
				ps.addBatch();

				////////////////// resvdtl insertion//////////////////////////////////////

				int resvdtlno = 0;

				countFunct.setString(2, "resv_dtl_no");
				rs = countFunct.executeQuery();
				while (rs.next()) {
					resvdtlno = rs.getInt(1);
				}
				rs.close();

				rd.setInt(1, resvdtlno);

				rd.setInt(2, resvno);

				String roomtype = reservation.getRoomtypecode();
				System.out.println("roomtypeeeeee" + roomtype);

				char roomtype_id = roomtype.charAt(roomtype.length() - 1);
				room_id = Integer.parseInt(String.valueOf(roomtype_id));
				System.out.println("roomm typeid  " + roomtype_id);
				System.out.println("roomm typeid  " + room_id);
				rd.setInt(3, room_id);
				String roomcode = ("SELECT code FROM room_type WHERE id=" + roomtype_id);

				con = dbConnection.getConnection();
				Statement stmt = con.createStatement();

				rs = stmt.executeQuery(roomcode);

				while (rs.next()) {
					a = rs.getString("code");
				}
				rs.close();
				rd.setString(4, a);
				rd.setString(5, reservation.getStart());
				rd.setString(6, reservation.getEnd());
				// rd.setString(7,reservation.getEnd());
				String date1 = (reservation.getStart());
				String date2 = (reservation.getEnd());

				LocalDate dateBefore = LocalDate.parse(date1);
				LocalDate dateAfter = LocalDate.parse(date2);

				// Approach 1
				// long daysDiff = ChronoUnit.DAYS.between(dateBefore, dateAfter);
				// System.out.println("The number of days between dates: " + daysDiff);

				// Approach 2
				long daysDiff = dateBefore.until(dateAfter, ChronoUnit.DAYS);

				///////////////////////////////////////////////////////////////////////
				// Calculate the number of days between dates

				rd.setLong(7, daysDiff);
				rd.setInt(8, 1);// num rooms
				rd.setInt(9, 2); /// rate type other room type in in enumerator

				String rateid = ("SELECT id FROM rate_hdr WHERE room_type_id=" + roomtype_id);
				// con = dbConnection.getConnection();
				Statement stm = con.createStatement();
				rs = stm.executeQuery(rateid);

				while (rs.next()) {
					b = rs.getString("id");
				}
				rs.close();

				rd.setString(10, b);

				String ratecode = ("SELECT code FROM rate_hdr WHERE id=" + b);

				con = dbConnection.getConnection();

				Statement st = con.createStatement();

				rs = st.executeQuery(ratecode);

				while (rs.next()) {

					c = rs.getString("code");
				}
				rs.close();
				rd.setString(11, c);
				rd.setInt(12, 1);///// occupany
				rd.addBatch();

				//////////////// reserv_room insertion//////////////

				int resvroomno = 0;
				countFunct.setString(2, "resv_room_no");
				rs = countFunct.executeQuery();
				while (rs.next()) {
					resvroomno = rs.getInt(1);
				}
				rs.close();
				rr.setInt(1, resvroomno);
				rr.setInt(2, resvdtlno);
				rr.setInt(3, 0);// no no show is set at the time of reservation.
				rr.setInt(4, 0);// room status no room is allotted at the time of reservation
				rr.setString(5, reservation.getFirstname());
				rr.setString(6, reservation.getLastname());
				rr.setString(7, reservation.getEmail());
				rr.setString(8, reservation.getPhone());
				rr.setString(9, reservation.getAddress());
				rr.setString(10, rental.getAdult());
				rr.setString(11, rental.getChild());
				rr.setString(12, reservation.getGender());
				rr.setString(13, reservation.getCountry());
				rr.setString(14, reservation.getState());
				rr.addBatch();
				///////////// ///reserv rate insertion//////////////////

				for (int n = 1; n <= daysDiff; n++) {
					rt.setInt(1, resvroomno);
					rt.setInt(2, n);
					rt.setString(3, reservation.getTotalamountbeforetax());
					rt.setInt(4, 0);
					rt.setInt(5, 6);///// tax 1 rate// get cgst
					rt.setInt(6, 6);//// tax 2 rate/sgst
					rt.setString(7, "0.00");
					rt.setString(8, "0.00");
					rt.setString(9, "0.00");
					rt.setInt(10, 6);// tax1 amount
					rt.setInt(11, 6);// tax2 amont
					rt.setString(12, "0.00");
					rt.setString(13, "0.00");
					rt.setString(14, "0.00");
					rt.addBatch();
				}
				ft.setInt(1, getcounter(1, "folio_no"));
				ft.setInt(2, foliobindno);
				ft.setInt(3, resvno);

				ft.addBatch();

			
				ps.executeUpdate();
				rd.executeBatch();
				rr.executeBatch();
				rt.executeBatch();
				ft.executeUpdate();
				
				 

				dbConnection.releaseResource(con);
				dbConnection.releaseResource(ps);
				dbConnection.releaseResource(rd);
				dbConnection.releaseResource(rr);
				dbConnection.releaseResource(rt);
				dbConnection.releaseResource(ft);
			} else {
				System.out.println("Reservation Already Added");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
	}

	public int getcounter(int count, String keyName) throws Exception {
		CallableStatement countFunct = null;
		Connection connection = null;
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
		} catch (Exception e) {
			System.out.println("Method : getcounter()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(countFunct);
			dbConnection.releaseResource(rs);
		}
		return newCount;
	}

}
