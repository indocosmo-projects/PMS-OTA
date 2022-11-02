package com.indocosmo.pms.web.reception.dao;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.indocosmo.pms.enumerator.RoomHkStatus;
import com.indocosmo.pms.enumerator.RoomInventoryStatus;
import com.indocosmo.pms.enumerator.RoomOccupancyStatus;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.checkIn.model.CheckInDtl;
import com.indocosmo.pms.web.checkIn.model.CheckInHdr;
import com.indocosmo.pms.web.checkIn.model.CheckInRate;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.discount.dao.DiscountDAO;
import com.indocosmo.pms.web.discount.model.Discount;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.reservation_test.model.CheckinDiscount;
import com.indocosmo.pms.web.reservation_test.model.RoomRateDetailsCheck;
import com.indocosmo.pms.web.reservation_test.model.RoomRateEdited;
import com.indocosmo.pms.web.reservation_test.service.ReservationServiceTest;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.indocosmo.pms.web.transaction.service.TxnService;
import com.thoughtworks.xstream.XStream;

@Repository
public class ReceptionDAOImpl implements ReceptionDAO {
	@Autowired
	private ReservationServiceTest reservationService;

	@Autowired
	private DiscountDAO discountDao;

	@Autowired
	TxnService transactionService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static final Logger logger = LoggerFactory.getLogger(ReceptionDAOImpl.class);

	private DbConnection dbConnection = null;

	public ReceptionDAOImpl() {
		dbConnection = new DbConnection();
	}

	public boolean saveNewCheckin(List<CheckInHdr> checkinHdrList, List<CheckInDtl> checkinDtlList,
			List<CheckInRate> checkinRateList,JsonArray checkinDiscount) throws Exception {
		boolean isSave = true;
		Connection con = dbConnection.getConnection();
		PreparedStatement hdrPst = null;
		PreparedStatement dtlPst = null;
		PreparedStatement ratePst = null;
		PreparedStatement folioPst = null;
		CallableStatement clSt = null;
		PreparedStatement prsCheckRoomStatus = null;
		PreparedStatement prsRoomStatusUpdate = null;
		PreparedStatement prsSaveBillingAddress = null;
		PreparedStatement pscheckinDiscount = null;
		ResultSet rs = null;

		int occStatus = RoomOccupancyStatus.OCCUPIED.getCode();
		int hkStatus = RoomHkStatus.DIRTY.getCode();

		String counterSql = "{? = call counter(?)}";
		String checkInHdrSql = "INSERT INTO checkin_hdr VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String checkInDtlSql = "INSERT INTO checkin_dtl VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		String checkInRateSql = "INSERT INTO checkin_rate"
				+ "(checkin_dtl_no,night_date,night,room_charge,include_tax,tax1_pc,tax2_pc,tax3_pc,tax4_pc,service_charge_pc,tax1,tax2,tax3,tax4,service_charge,rate_id,disc_id,disc_amount,disc_pc,disc_tax,room_type,occupancy) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String folioSql = "INSERT INTO folio(folio_no, folio_bind_no, checkin_no) VALUES(?,?,?)";
		String sqlCheckRoomStatus = "SELECT occ_status, hk_status FROM room WHERE inv_status !=? and number=?";
		String sqlRoomStatusUpdate = "update room set occ_status=? where number=?";
		String sqlSaveBillingAddress = "INSERT INTO billing_address(checkin_no, folio_bind_no, salutation, first_name, last_name, guest_name, address,"
				+ " email, phone, nationality, state, gstno) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlcheckinDiscntInsert = "insert into checkin_discounts(id,checkin_no,disc_type,disc_id,disc_code,disc_is_pc,disc_amount,disc_pc,is_open,discount_for) values(?,?,?,?,?,?,?,?,?,?)";
		
		try {
			con.setAutoCommit(false);

			clSt = con.prepareCall(counterSql);
			hdrPst = con.prepareStatement(checkInHdrSql);
			dtlPst = con.prepareStatement(checkInDtlSql);
			ratePst = con.prepareStatement(checkInRateSql);
			folioPst = con.prepareStatement(folioSql);
			prsCheckRoomStatus = con.prepareStatement(sqlCheckRoomStatus);
			prsRoomStatusUpdate = con.prepareStatement(sqlRoomStatusUpdate);
			prsSaveBillingAddress = con.prepareStatement(sqlSaveBillingAddress);
			
			
			if(checkinDiscount.size()!=0) {
				pscheckinDiscount = con.prepareStatement(sqlcheckinDiscntInsert);
			}
			int folioBindNo = getCounterNo(clSt, "folio_bind_no", rs);
			CheckInDtl checkInDtl = null;
			int i = 0;
			for (CheckInHdr checkInHdr : checkinHdrList) {
				prsCheckRoomStatus.setInt(1, RoomInventoryStatus.OUTOFINVENTORY.getCode());
				prsCheckRoomStatus.setString(2, checkInHdr.getRoomNumber());
				rs = prsCheckRoomStatus.executeQuery();
				/** end- check status of room */

				while (rs.next()) {
					occStatus = rs.getInt("occ_status");
					hkStatus = rs.getInt("hk_status");
				}

				if (occStatus == RoomOccupancyStatus.VACCANT.getCode() && hkStatus != RoomHkStatus.DIRTY.getCode()) {
					// Update occ_status of room in room table
					prsRoomStatusUpdate.setInt(1, RoomOccupancyStatus.OCCUPIED.getCode());
					prsRoomStatusUpdate.setString(2, checkInHdr.getRoomNumber());
					prsRoomStatusUpdate.executeUpdate();

					// rs = null;
					checkInHdr.setCheckInNo(getCounterNo(clSt, "checkin_no", rs));
					checkInHdr.setFolioBindNo(folioBindNo);

					// checkin_hdr insertion
					hdrPst.setInt(1, checkInHdr.getCheckInNo());
					hdrPst.setObject(2, null);
					hdrPst.setInt(3, checkInHdr.getFolioBindNo());
					hdrPst.setByte(4, checkInHdr.getStatus());
					hdrPst.setBoolean(5, checkInHdr.isFullySettled());
					hdrPst.setByte(6, checkInHdr.getResvType());
					hdrPst.setInt(7, checkInHdr.getPayment_source());
					hdrPst.setInt(8, checkInHdr.getCorporateId());
					hdrPst.setString(9, checkInHdr.getCorporateName());
					hdrPst.setString(10, checkInHdr.getCorporateAddress());
					hdrPst.setInt(11, checkInHdr.getRoomTypeId());
					hdrPst.setString(12, checkInHdr.getRoomTypeCode());
					hdrPst.setString(13, checkInHdr.getRoomNumber());
					hdrPst.setInt(14, checkInHdr.getNumNights());
					hdrPst.setDate(15, new java.sql.Date(checkInHdr.getArrDate().getTime()));
					hdrPst.setTime(16, new Time(checkInHdr.getArrTime().getTime()));
					hdrPst.setDate(17, new java.sql.Date(checkInHdr.getExpDepartDate().getTime()));
					hdrPst.setTime(18, new Time(checkInHdr.getExpDepartTime().getTime()));
					hdrPst.setDate(19, /* new java.sql.Date(checkInHdr.getActDepartDate().getTime()) */null);
					hdrPst.setTime(20, /* new Time(checkInHdr.getActDepartTime().getTime()) */ null);
					hdrPst.setByte(21, checkInHdr.getRateType());
					hdrPst.setInt(22, checkInHdr.getRateId());
					hdrPst.setString(23, checkInHdr.getRateCode());
					hdrPst.setString(24, checkInHdr.getRateDescription());
					hdrPst.setByte(25, checkInHdr.getOccupancy());
					hdrPst.setByte(26, checkInHdr.getNumAdults());
					hdrPst.setByte(27, checkInHdr.getNumChildren());
					hdrPst.setByte(28, checkInHdr.getNumInfants());
					hdrPst.setInt(29, checkInHdr.getDiscType());
					hdrPst.setInt(30, checkInHdr.getDiscId());
					hdrPst.setString(31, checkInHdr.getDiscCode());
					hdrPst.setBoolean(32, checkInHdr.getDiscIsPc());
					hdrPst.setBigDecimal(33, checkInHdr.getDiscAmount());
					hdrPst.setBigDecimal(34, checkInHdr.getDiscPc());
					hdrPst.setBoolean(35, checkInHdr.getDiscIsOpen());
					hdrPst.setString(36, checkInHdr.getBillingInstruction());
					hdrPst.setString(37, checkInHdr.getSpecialRequests());
					hdrPst.setInt(38, checkInHdr.getCheckInBy());
					hdrPst.setInt(39, checkInHdr.getCheckOutBy());
					hdrPst.setDate(40, null);
					hdrPst.setObject(41, null);

					hdrPst.addBatch();
					// End if checkin_hdr insertion

					// checkin_dtl insertion
					checkInDtl = checkinDtlList.get(i);
					if (checkInHdr.getRoomNumber().equals(checkInDtl.getRoomNumber())) {
						checkInDtl.setCheckinDtlNo(getCounterNo(clSt, "checkin_dtl_no", rs));
						dtlPst.setInt(1, checkInDtl.getCheckinDtlNo());
						dtlPst.setInt(2, checkInHdr.getCheckInNo());
						dtlPst.setInt(3, checkInDtl.getGuestId());
						dtlPst.setBoolean(4, checkInDtl.getIsSharer());
						dtlPst.setString(5, checkInDtl.getSalutation());
						dtlPst.setString(6, checkInDtl.getFirstName());
						dtlPst.setString(7, checkInDtl.getLastName());
						dtlPst.setString(8, checkInDtl.getGuestName());
						dtlPst.setString(9, checkInDtl.getGender());
						dtlPst.setString(10, checkInDtl.getAddress());
						dtlPst.setString(11, checkInDtl.getEmail());
						dtlPst.setString(12, checkInDtl.getPhone());
						dtlPst.setString(13, checkInDtl.getNationality());
						dtlPst.setString(14, checkInDtl.getState());
						dtlPst.setString(15, checkInDtl.getPassportNo());
						dtlPst.setDate(16,
								checkInDtl.getPassportExpiryOn() != null
										? new java.sql.Date(checkInDtl.getPassportExpiryOn().getTime())
										: null);
						dtlPst.setString(17, checkInDtl.getGstno());
						dtlPst.setString(18, checkInDtl.getCustomerImage());
						dtlPst.setString(19, checkInDtl.getCustomerIdProof());
						dtlPst.setString(20, checkInDtl.getRemarks());

						dtlPst.addBatch();
						// End of checkin_dtl insertion

						/** Insert into billing_address table */

						prsSaveBillingAddress.setInt(1, checkInHdr.getCheckInNo());
						prsSaveBillingAddress.setInt(2, checkInHdr.getFolioBindNo());
						prsSaveBillingAddress.setString(3, checkInDtl.getSalutation());
						prsSaveBillingAddress.setString(4, checkInDtl.getFirstName());
						prsSaveBillingAddress.setString(5, checkInDtl.getLastName());
						prsSaveBillingAddress.setString(6, checkInDtl.getGuestName());
						prsSaveBillingAddress.setString(7, checkInDtl.getAddress());
						prsSaveBillingAddress.setString(8, checkInDtl.getEmail());
						prsSaveBillingAddress.setString(9, checkInDtl.getPhone());
						prsSaveBillingAddress.setString(10, checkInDtl.getNationality());
						prsSaveBillingAddress.setString(11, checkInDtl.getState());
						prsSaveBillingAddress.setString(12, checkInDtl.getGstno());

						prsSaveBillingAddress.addBatch();

						// checkin_rate insertion
						for (CheckInRate checkInRate : checkinRateList) {

							if (checkInHdr.getRoomNumber().equals(checkInRate.getRoomNumber())) {
								java.sql.Date tDate = new java.sql.Date(
										checkInHdr.getArrDate().getTime() + ((checkInRate.getNight() - 1)) * 86400000);
								BigDecimal taxPc = BigDecimal.valueOf(100);

								ratePst.setInt(1, checkInDtl.getCheckinDtlNo());
								ratePst.setDate(2, tDate);
								ratePst.setByte(3, checkInRate.getNight());
								ratePst.setBigDecimal(4, checkInRate.getRoomCharge());
								ratePst.setBoolean(5, checkInRate.getIncludeTax());
								ratePst.setBigDecimal(6, checkInRate.getTax1Pc());
								ratePst.setBigDecimal(7, checkInRate.getTax2Pc());
								ratePst.setBigDecimal(8, checkInRate.getTax3Pc());
								ratePst.setBigDecimal(9, checkInRate.getTax4Pc());
								ratePst.setBigDecimal(10, checkInRate.getServiceChargePc());
								ratePst.setBigDecimal(11, checkInRate.getTax1());
								ratePst.setBigDecimal(12, checkInRate.getTax2());
								ratePst.setBigDecimal(13, checkInRate.getTax3());
								ratePst.setBigDecimal(14, checkInRate.getTax4());
								ratePst.setBigDecimal(15, checkInRate.getServiceCharge());
								ratePst.setInt(16, checkInHdr.getRateId());
								ratePst.setInt(17, checkInHdr.getDiscId());
								ratePst.setBigDecimal(18, checkInHdr.getDiscAmount());
								ratePst.setBigDecimal(19, checkInHdr.getDiscPc());
								if (checkInHdr.getDiscAmount() != null) {
									ratePst.setBigDecimal(20,
											(((checkInRate.getTax1Pc().add(checkInRate.getTax2Pc())
													.add(checkInRate.getTax3Pc()).add(checkInRate.getTax4Pc()))
															.divide(taxPc)).multiply(checkInHdr.getDiscAmount())));
								}
								if (checkInHdr.getDiscPc() != null) {
									ratePst.setBigDecimal(20,
											(((checkInRate.getTax1Pc().add(checkInRate.getTax2Pc())
													.add(checkInRate.getTax3Pc()).add(checkInRate.getTax4Pc()))
															.divide(taxPc))
																	.multiply((checkInHdr.getDiscPc()).divide(taxPc))
																	.multiply(checkInRate.getRoomCharge())));
								}
								if (checkInHdr.getDiscAmount() == null && checkInHdr.getDiscPc() == null) {
									ratePst.setBigDecimal(20, null);
								}
								ratePst.setInt(21, checkInHdr.getRoomTypeId());
								ratePst.setInt(22, checkInHdr.getOccupancy());

								ratePst.addBatch();
							}
						}
					}
					// End of checkin_rate insertion

					// folio insertion
					folioPst.setInt(1, getCounterNo(clSt, "folio_no", rs));
					folioPst.setInt(2, checkInHdr.getFolioBindNo());
					folioPst.setInt(3, checkInHdr.getCheckInNo());
					folioPst.addBatch();
					// End of folio insertion
					
					//start checkin_discounts insertion
					
					if(checkinDiscount.size()!=0) {
					//	int roomCount = 1; //resvdtl.getNumRooms();
						//while (roomCount > 0) {
						for(int c = 0; c<checkinDiscount.size() ;c++) {
							JsonObject discnt = (JsonObject) checkinDiscount.get(c);
							int discId = discnt.get("discId").getAsInt();
								pscheckinDiscount.setInt(1,getCounterNo(clSt, "checkin_discounts", rs));
							pscheckinDiscount.setInt(2, checkInHdr.getCheckInNo());
							//pscheckinDiscount.setInt(3, resvdtl.getResvDtlNo());
							
								Discount disc = discountDao.getRecord(discId);
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
					//	roomCount = roomCount -1;
					//}
				}
					
					
					
					
					
					
					
					i++;
				} else {
					return false;
				}
			}
			if(checkinDiscount.size()!=0) {
				pscheckinDiscount.executeBatch();
			}
		
			hdrPst.executeBatch();
			dtlPst.executeBatch();
			ratePst.executeBatch();
			folioPst.executeBatch();
			prsSaveBillingAddress.executeBatch();

			con.commit();

		} catch (Exception e) {
			con.rollback();
			isSave = false;
			e.printStackTrace();
			logger.error("Method: save, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();

		} finally {
			if (rs != null) {
				rs.close();
			}
			if (clSt != null) {
				clSt.close();
			}
			if (hdrPst != null) {
				hdrPst.close();
			}
			if (dtlPst != null) {
				dtlPst.close();
			}
			if (ratePst != null) {
				ratePst.close();
			}
			if (folioPst != null) {
				folioPst.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return isSave;
	}

	public int getCounterNo(CallableStatement clSt, String keyName, ResultSet rs) throws SQLException {
		int counterNo = 0;

		try {
			clSt.registerOutParameter(1, java.sql.Types.INTEGER);
			clSt.setString(2, keyName);
			rs = clSt.executeQuery();

			if (rs.next()) {
				counterNo = rs.getInt(1);
			}

			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getCounterNo, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return counterNo;
	}

	public JsonObject getCheckInNO(String roomNo) throws Exception {
		String sql = "select ch.checkin_no,cd.checkin_dtl_no from checkin_hdr ch inner join checkin_dtl cd on ch.checkin_no=cd.checkin_no where ch.room_number="
				+ roomNo;
		Connection connection = dbConnection.getConnection();
		ResultSet resultSet = null;
		Statement statement = null;
		JsonObject recpRow = new JsonObject();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				recpRow.addProperty("checkin_no", resultSet.getInt("checkin_no"));
				recpRow.addProperty("checkin_dtl_no", resultSet.getInt("checkin_dtl_no"));

			}
		} catch (Exception e) {
			logger.error("Method: getRatePlan, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return recpRow;
	}

	public boolean uploadCustomerFiles(JsonArray saveImageArray, JsonArray saveProofArray) throws Exception {
		String imgsql = "update checkin_dtl set customer_image=? where checkin_dtl_no=?";
		PreparedStatement dtlUpdatePstImg = null;
		String proofsql = "update checkin_dtl set customer_id_proof=? where checkin_dtl_no=?";
		PreparedStatement dtlUpdatePstProof = null;
		Connection connection = dbConnection.getConnection();
		boolean isSave = true;
		try {
			connection.setAutoCommit(false);
			for (JsonElement imgJE : saveImageArray) {
				dtlUpdatePstImg = connection.prepareStatement(imgsql);
				dtlUpdatePstImg.setString(1, imgJE.getAsJsonObject().get("file").getAsString());
				dtlUpdatePstImg.setInt(2, imgJE.getAsJsonObject().get("checkin_dtl_no").getAsInt());
				dtlUpdatePstImg.addBatch();
			}
			for (JsonElement proofJE : saveProofArray) {
				dtlUpdatePstProof = connection.prepareStatement(proofsql);
				dtlUpdatePstProof.setString(1, proofJE.getAsJsonObject().get("file").getAsString());
				dtlUpdatePstProof.setInt(2, proofJE.getAsJsonObject().get("checkin_dtl_no").getAsInt());
				dtlUpdatePstProof.addBatch();
			}
			if (saveImageArray.size() != 0)
				dtlUpdatePstImg.executeBatch();

			if (saveProofArray.size() != 0)
				dtlUpdatePstProof.executeBatch();

			connection.commit();

		} catch (Exception e) {
			connection.rollback();
			isSave = false;
			logger.error("Method: uploadCustomerFiles, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(dtlUpdatePstProof);
			dbConnection.releaseResource(dtlUpdatePstImg);
		}
		
		return isSave;
	}

	public HashMap<String, Object> getCheckInDetails(int checkInNo) throws Exception {
		ResultSet resultSet = null;
		CheckInHdr checkInHdr = null;
		CheckInDtl checkInDtl = null;
		double f_balance = 0;
		int disAplied = 0;
		List<CheckInDtl> checkInDtlList = new ArrayList<CheckInDtl>();
		HashMap<String, Object> resultData = new HashMap<String, Object>();
		List<CheckinDiscount> checkinDiscountList = new ArrayList<CheckinDiscount>();
		PreparedStatement psDis = null ;
		CheckinDiscount checkdis = null;
		Connection connection = dbConnection.getConnection();
		try {
			
			String DiscountQuery = "select  * from checkin_discounts where checkin_no=? GROUP BY disc_id";
			psDis =  connection.prepareStatement(DiscountQuery);
			psDis.setInt(1, checkInNo);
			resultSet = psDis.executeQuery();
			while (resultSet.next()) {
				checkdis = new CheckinDiscount();
				checkdis.setId(resultSet.getInt("id"));
				checkdis.setCheckinNo(resultSet.getInt("checkin_no"));
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
			
			

			
			
			String rootPath = System.getProperty("catalina.home");
//			String imageRootDirectory = rootPath + "/webapps" ;
//			String proofRootDirectory = rootPath + "/webapps" ;
			String path = "../..";
			checkInHdr = getCheckinHdr(checkInNo);
			String selectCheckInDtlQuery = "select * from checkin_dtl where checkin_no=?";
			PreparedStatement checkInDtlPs = connection.prepareStatement(selectCheckInDtlQuery);
			checkInDtlPs.setInt(1, checkInHdr.getCheckInNo());
			resultSet = checkInDtlPs.executeQuery();
			while (resultSet.next()) {
				checkInDtl = new CheckInDtl();
				checkInDtl.setCheckinDtlNo(resultSet.getInt("checkin_dtl_no"));
				checkInDtl.setIsSharer(resultSet.getBoolean("is_sharer"));
				checkInDtl.setSalutation(resultSet.getString("salutation"));
				checkInDtl.setFirstName(resultSet.getString("first_name"));
				checkInDtl.setLastName(resultSet.getString("last_name"));
				checkInDtl.setGuestName(resultSet.getString("guest_name"));
				checkInDtl.setGender(resultSet.getString("gender"));
				checkInDtl.setAddress(resultSet.getString("address"));
				checkInDtl.setEmail(resultSet.getString("email"));
				checkInDtl.setPhone(resultSet.getString("phone"));
				checkInDtl.setNationality(resultSet.getString("nationality"));
				checkInDtl.setState(resultSet.getString("state"));
				checkInDtl.setPassportNo(resultSet.getString("passport_no"));
				checkInDtl.setPassportExpiryOn(resultSet.getDate("passport_expiry_on"));
				checkInDtl.setGstno(resultSet.getString("gstno"));
				checkInDtl.setRemarks(resultSet.getString("remarks"));
				if(resultSet.getString("customer_image")!="" && resultSet.getString("customer_image")!=null) {
					checkInDtl.setCustomerImage(path+resultSet.getString("customer_image"));
				}
				else {
					checkInDtl.setCustomerImage("");
				}
				if(resultSet.getString("customer_id_proof")!="" && resultSet.getString("customer_id_proof")!=null) {
					checkInDtl.setCustomerIdProof(path+resultSet.getString("customer_id_proof"));
				}
				else {
					checkInDtl.setCustomerIdProof("");
				}
				
				checkInDtlList.add(checkInDtl);
			}
			resultSet = null;
			String selectFolioBalanceQuery = "select ifnull(`v_list_folio_balance`.`folio_balance`,0) AS `folio_balance` from v_list_folio_balance inner join folio on folio.folio_no =v_list_folio_balance.folio_no where folio.checkin_no=?";
			PreparedStatement folioBalanceRatePs = connection.prepareStatement(selectFolioBalanceQuery);
			folioBalanceRatePs.setInt(1, checkInHdr.getCheckInNo());
			resultSet = folioBalanceRatePs.executeQuery();
			while (resultSet.next()) {
				f_balance = resultSet.getDouble("folio_balance");
			}
			resultSet = null;
			
			String disAppliedQuery = " SELECT (select COUNT(*) from txn where txn.acc_mst_id = 6 and txn.folio_no = folio.folio_no AND txn.folio_bind_no = checkin_hdr.folio_bind_no ) AS disApplied from checkin_hdr inner join folio on (checkin_hdr.checkin_no=folio.checkin_no)  and checkin_hdr.checkin_no=?  ";
			PreparedStatement psDisAplied =  connection.prepareStatement(disAppliedQuery);
			psDisAplied.setInt(1, checkInNo);
			resultSet = psDisAplied.executeQuery();
			while (resultSet.next()) {
					disAplied =  resultSet.getInt("disApplied");
				
			}
			
			resultData.put("checkInHdr", checkInHdr);
			resultData.put("checkInDtlList", checkInDtlList);
			resultData.put("checkInDtl", checkInDtl);
			resultData.put("folioBal", f_balance);
			resultData.put("disAplied", disAplied);
			resultData.put("checkDiscList", checkinDiscountList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getCheckInDetails, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return resultData;
	}

	public CheckInHdr getCheckinHdr(int checkInNo) {
		CheckInHdr checkInHdr = null;
		ResultSet resultSet = null;
		PreparedStatement checkInPs = null ;
		Connection connection = dbConnection.getConnection();
		String sql = "select *,discount.disc_type as discType from checkin_hdr left join discount on checkin_hdr.disc_id=discount.id where checkin_no=?";
		try {
		    checkInPs = connection.prepareStatement(sql);
			checkInPs.setInt(1, checkInNo);
			resultSet = checkInPs.executeQuery();
			while (resultSet.next()) {
				checkInHdr = new CheckInHdr();
				checkInHdr.setCheckInNo(checkInNo);
				checkInHdr.setResvNo(resultSet.getInt("resv_no"));
				checkInHdr.setFolioBindNo(resultSet.getInt("folio_bind_no"));
				checkInHdr.setResvStatus(resultSet.getByte("status"));
				checkInHdr.setRoomTypeId(resultSet.getInt("room_type_id"));
				checkInHdr.setRoomTypeCode(resultSet.getString("room_type_code"));
				checkInHdr.setRoomNumber(resultSet.getString("room_number"));
				checkInHdr.setArrDate(resultSet.getDate("arr_date"));
				checkInHdr.setArrTime(resultSet.getDate("arr_time"));
				checkInHdr.setExpDepartDate(resultSet.getDate("exp_depart_date"));
				checkInHdr.setRateType(resultSet.getByte("rate_type"));
				checkInHdr.setRateId(resultSet.getInt("rate_id"));
				checkInHdr.setRateCode(resultSet.getString("rate_code"));
				checkInHdr.setOccupancy(resultSet.getByte("occupancy"));
				checkInHdr.setDiscId(resultSet.getInt("disc_id"));
				checkInHdr.setDiscAmount(resultSet.getBigDecimal("disc_amount"));
				checkInHdr.setDiscCode(resultSet.getString("disc_code"));
				checkInHdr.setDiscPc(resultSet.getBigDecimal("disc_pc"));
				checkInHdr.setDiscIsPc(resultSet.getBoolean("disc_is_pc"));
				checkInHdr.setDiscIsOpen(resultSet.getBoolean("disc_is_open"));
				checkInHdr.setNumAdults(resultSet.getByte("num_adults"));
				checkInHdr.setNumChildren(resultSet.getByte("num_children"));
				/* <2318 digna 20180621 begin */
				checkInHdr.setNumInfants(resultSet.getByte("num_infants"));
				/* 2318 digna 20180621 end > */
				checkInHdr.setNumNights(resultSet.getByte("num_nights"));
				checkInHdr.setDiscType(resultSet.getInt("discType"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(checkInPs);
		}
		return checkInHdr;
	}

	public int newRoomRateIds(List<RoomRateEdited> list) throws Exception {

		Connection con = dbConnection.getConnection();

		con.setAutoCommit(false);
		CallableStatement st = null;
		int newRateId = 0;
		try {
			st = con.prepareCall("CALL newRoomRateIds(?,?)");
			XStream xstrem = new XStream();
			xstrem.alias("newRoomRates", RoomRateEdited.class);
			st.setString(1, xstrem.toXML(list).replaceAll("\\s*[\\r\\n]+\\s*", "").trim());
			st.registerOutParameter(2, java.sql.Types.INTEGER);
			st.executeUpdate();
			con.commit();
			newRateId = st.getInt(2);
			// System.out.println(newRateId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(con);
			dbConnection.releaseResource(st);
			
		}

		return newRateId;

	}

	public boolean updateReceptionEdit(JsonObject jobj, String hotelDate) throws Exception {
		JsonObject dtlsObj = jobj.get("roomDtls").getAsJsonObject();
		JsonObject hdrObj = jobj.get("checkinHdr").getAsJsonObject();
		JsonArray checkinDiscount = jobj.get("checkinDiscnt").getAsJsonArray();
	//	JsonArray checkinDiscount = jobj.get("checkinDiscount").getAsJsonArray();
		JsonObject discObj = new JsonObject();
		JsonObject rateObj = new JsonObject();
		JsonArray txnJson = new JsonArray();
		ObjectMapper mapper = new ObjectMapper();
		CheckInDtl checkInDtl = null;
		int hdrPsCount = 0;
		boolean isSave = true;

		Connection con = dbConnection.getConnection();

		// con.setAutoCommit(false);
		// CallableStatement st=null;
		// int a=0;
		// try{
		// st=con.prepareCall("CALL newRoomRateIds(?,?)");
		// XStream xstrem=new XStream();
		// xstrem.alias("newRoomRates", RoomRateEdited.class);
		// st.setString(1, xstrem.toXML(list).replaceAll("\\s*[\\r\\n]+\\s*",
		// "").trim());
		// st.registerOutParameter(2, java.sql.Types.INTEGER);
		// st.executeUpdate();
		// con.commit();
		// a=st.getInt(2);
		// System.out.println(a);

		// } catch (Exception e) {
		// e.printStackTrace();
		// throw new CustomException();
		// }
		
		PreparedStatement pscheckinDiscount = null;
		PreparedStatement psDlt = null;
		CallableStatement countFunct = null;
		
		PreparedStatement dtlUpdatePst = null;
		PreparedStatement roomStatusUpdatePs = null;
		PreparedStatement oldRoomStatusUpdatePs = null;
		PreparedStatement nightUpdatePst = null;
		PreparedStatement rateUpdatePst = null;
		PreparedStatement hdrUpdatePst = null;
		PreparedStatement addSharePs = null;
		PreparedStatement updateSharePs = null;
		PreparedStatement delSharePs = null;
		PreparedStatement discUpdatePst = null;
		PreparedStatement prsSaveBillingAddress = null;
		PreparedStatement getTxnDataPst = null;
		PreparedStatement updateTxnpst = null;
		CallableStatement clSt = null;
		ResultSet rs = null;
		String checkInDtlupdateSql = "UPDATE checkin_dtl SET salutation=?,first_name=?,last_name=?,guest_name=?,gender=?,address=?,email=?,phone=?,nationality=?,state=?,passport_no=?,passport_expiry_on=?,gstno=?,remarks=? WHERE checkin_dtl_no=?";
		String roomStatusUpdateSql = "update room set occ_status=? where number=?";
		String oldRoomStatusUpdateSql = "UPDATE room set occ_status=?,hk_status=? where number=?";
		String changeNightSql = "delete from checkin_rate where checkin_dtl_no=? and night in(";
		String checkInRateSql = "update checkin_rate set room_charge=?,include_tax=?,tax1_pc=?,tax2_pc=?,tax3_pc=?,tax4_pc=?,service_charge_pc=?,tax1=?,tax2=?,tax3=?,tax4=?,service_charge=?,rate_id=?,room_type=?,occupancy=?,disc_id=?,disc_amount=?,disc_pc=? where night=? and checkin_dtl_no=?";
		String HdrUpdateSql = "";
		String counterSql = "{? = call counter(?)}";
		String checkInDtlnewSharerSql = "INSERT INTO checkin_dtl VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqldel = "delete from checkin_dtl where checkin_dtl_no=?";
		String discUpdateSql = "update checkin_rate set disc_id=?,disc_amount=?,disc_pc=?,disc_tax=? where night=? and checkin_dtl_no=?";
		String sqlSaveBillingAddress = "UPDATE billing_address SET	salutation = ?,	first_name = ?, last_name = ?, guest_name = ?, address = ?, email = ?, phone = ?, nationality = ?, state = ?,	gstno = ? WHERE checkin_no = ?";

		String delDiscount = "delete from checkin_discounts where checkin_no=?";
		String sqlcheckinDiscntInsert = "insert into checkin_discounts(id,checkin_no,disc_type,disc_id,disc_code,disc_is_pc,disc_amount,disc_pc,is_open,discount_for) values(?,?,?,?,?,?,?,?,?,?)";
		
		// to update txn with respect to gstno.

		String getTxnData = "SELECT * FROM txn INNER JOIN folio ON txn.folio_no = folio.folio_no WHERE folio.checkin_no = ?";
		String updateTxn = "UPDATE txn SET base_amount=?,tax1_amount=?,tax2_amount=?,tax3_amount=?,tax4_amount=?,"
				+ "nett_amount=? WHERE txn_no=?";
		
		String countFunction = "{? = call counter(?)}";

		try {
			con.setAutoCommit(false);
			
			CheckInHdr checkinHdr = getCheckinHdr(hdrObj.get("checkInNo").getAsInt());
			//checkin discount section start
			ResultSet rs1 = null;
			psDlt= con.prepareStatement(delDiscount);
			psDlt.setInt(1, checkinHdr.getCheckInNo());
			if(checkinDiscount.size()!=0) {
				pscheckinDiscount = con.prepareStatement(sqlcheckinDiscntInsert);
			}
			
			countFunct = con.prepareCall(countFunction);
			countFunct.registerOutParameter(1, java.sql.Types.INTEGER);
			
			psDlt.executeUpdate();
			
			if(checkinDiscount.size()!=0) {
				
				for(int i = 0; i<checkinDiscount.size() ;i++) {
					JsonObject discnt = (JsonObject) checkinDiscount.get(i);
					int discId = discnt.get("discId").getAsInt();
				
					countFunct.setString(2, "checkin_discounts");
					rs1 = countFunct.executeQuery();
					while (rs1.next()) {
						pscheckinDiscount.setInt(1,rs1.getInt(1));
					}
					pscheckinDiscount.setInt(2, checkinHdr.getCheckInNo());
					//pscheckinDiscount.setInt(3, resvdtl.getResvDtlNo());
					
						Discount disc = discountDao.getRecord(discId);
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
				
				pscheckinDiscount.executeBatch();
				

			  }
			
			
			
			
			//checkin discount section end
			
			int dateDiff = (int) (commonSettings.getHotelDate().getTime() - checkinHdr.getArrDate().getTime())
					/ (1000 * 60 * 60 * 24);
			if (!jobj.get("checkinDtl").isJsonNull()) {
				checkInDtl = mapper.readValue(jobj.get("checkinDtl").getAsJsonObject().toString(), CheckInDtl.class);
			}
			if (!jobj.get("sharer").isJsonNull()) {
				boolean updateSharer = false, deleteSharer = false, addSharer = false;
				List<CheckInDtl> sharerEditList = mapper.readValue(jobj.get("sharer").getAsJsonArray().toString(),
						TypeFactory.defaultInstance().constructCollectionType(List.class, CheckInDtl.class));
				updateSharePs = con.prepareStatement(checkInDtlupdateSql);
				addSharePs = con.prepareStatement(checkInDtlnewSharerSql);
				delSharePs = con.prepareStatement(sqldel);
				// sharer update
				for (CheckInDtl sharerList : sharerEditList) {
					if (sharerList.getCheckinDtlNo() != 0 && !sharerList.getIsDeleted()) {
						updateSharePs.setString(1, sharerList.getSalutation());
						updateSharePs.setString(2, sharerList.getFirstName());
						updateSharePs.setString(3, sharerList.getLastName());
						updateSharePs.setString(4, null);
						updateSharePs.setString(5, sharerList.getGender());
						updateSharePs.setString(6, sharerList.getAddress());
						updateSharePs.setString(7, sharerList.getEmail());
						updateSharePs.setString(8, sharerList.getPhone());
						updateSharePs.setString(9, sharerList.getNationality());
						updateSharePs.setString(10, sharerList.getState());
						updateSharePs.setString(11, sharerList.getPassportNo());
						updateSharePs.setString(12, sharerList.getGstno());
						updateSharePs.setDate(13,
								sharerList.getPassportExpiryOn() != null
										? new java.sql.Date(sharerList.getPassportExpiryOn().getTime())
										: null);
						updateSharePs.setString(14, sharerList.getRemarks());
						updateSharePs.setInt(15, sharerList.getCheckinDtlNo());
						updateSharePs.addBatch();
						updateSharer = true;
					} else if (sharerList.getIsDeleted()) {
						delSharePs.setInt(1, sharerList.getCheckinDtlNo());
						delSharePs.addBatch();
						deleteSharer = true;
					}
					if (sharerList.getCheckinDtlNo() == 0) {
						clSt = con.prepareCall(counterSql);
						clSt.registerOutParameter(1, java.sql.Types.INTEGER);
						clSt.setString(2, "checkin_dtl_no");
						int checkInDtlCount = 0;
						rs = clSt.executeQuery();
						while (rs.next()) {
							checkInDtlCount = rs.getInt(1);
						}
						addSharePs.setInt(1, checkInDtlCount);
						addSharePs.setInt(2, checkinHdr.getCheckInNo());
						addSharePs.setInt(3, 0);
						addSharePs.setBoolean(4, true);
						addSharePs.setString(5, sharerList.getSalutation());
						addSharePs.setString(6, sharerList.getFirstName());
						addSharePs.setString(7, sharerList.getLastName());
						addSharePs.setString(8, null);
						addSharePs.setString(9, sharerList.getGender());
						addSharePs.setString(10, sharerList.getAddress());
						addSharePs.setString(11, sharerList.getEmail());
						addSharePs.setString(12, sharerList.getPhone());
						addSharePs.setString(13, sharerList.getNationality());
						addSharePs.setString(14, sharerList.getState());
						addSharePs.setString(15, sharerList.getPassportNo());
						Date PassportExpiry = null;
						if (sharerList.getPassportExpiryOn() != null) {
							PassportExpiry = new java.sql.Date(sharerList.getPassportExpiryOn().getTime());
						}
						addSharePs.setDate(16, PassportExpiry);
						addSharePs.setString(17, null);
						addSharePs.setString(18, null);
						addSharePs.setString(19, null);
						addSharePs.setString(20, sharerList.getRemarks());
						addSharePs.addBatch();
						addSharer = true;
						rs.close();
					}
				}

				if (updateSharer)
					updateSharePs.executeBatch();
				if (deleteSharer)
					delSharePs.executeBatch();
				if (addSharer)
					addSharePs.executeBatch();
			}
			if (!jobj.get("discount").isJsonNull()) {
				discObj = jobj.get("discount").getAsJsonObject();
			}

			if (checkInDtl != null && checkInDtl.getCheckinDtlNo() != 0) {
				dtlUpdatePst = con.prepareStatement(checkInDtlupdateSql);
				dtlUpdatePst.setString(1, checkInDtl.getSalutation());
				dtlUpdatePst.setString(2, checkInDtl.getFirstName());
				dtlUpdatePst.setString(3, checkInDtl.getLastName());
				dtlUpdatePst.setString(4, checkInDtl.getGuestName());
				dtlUpdatePst.setString(5, checkInDtl.getGender());
				dtlUpdatePst.setString(6, checkInDtl.getAddress());
				dtlUpdatePst.setString(7, checkInDtl.getEmail());
				dtlUpdatePst.setString(8, checkInDtl.getPhone());
				dtlUpdatePst.setString(9, checkInDtl.getNationality());
				dtlUpdatePst.setString(10, checkInDtl.getState());
				dtlUpdatePst.setString(11, checkInDtl.getPassportNo());
				dtlUpdatePst.setDate(12,
						checkInDtl.getPassportExpiryOn() != null
								? new java.sql.Date(checkInDtl.getPassportExpiryOn().getTime())
								: null);
				dtlUpdatePst.setString(13, checkInDtl.getGstno());
				dtlUpdatePst.setString(14, checkInDtl.getRemarks());
				dtlUpdatePst.setInt(15, checkInDtl.getCheckinDtlNo());
				dtlUpdatePst.executeUpdate();

				prsSaveBillingAddress = con.prepareStatement(sqlSaveBillingAddress);
				/** Insert into billing_address table */

				prsSaveBillingAddress.setString(1, checkInDtl.getSalutation());
				prsSaveBillingAddress.setString(2, checkInDtl.getFirstName());
				prsSaveBillingAddress.setString(3, checkInDtl.getLastName());
				prsSaveBillingAddress.setString(4, checkInDtl.getGuestName());
				prsSaveBillingAddress.setString(5, checkInDtl.getAddress());
				prsSaveBillingAddress.setString(6, checkInDtl.getEmail());
				prsSaveBillingAddress.setString(7, checkInDtl.getPhone());
				prsSaveBillingAddress.setString(8, checkInDtl.getNationality());
				prsSaveBillingAddress.setString(9, checkInDtl.getState());
				prsSaveBillingAddress.setString(10, checkInDtl.getGstno());
				prsSaveBillingAddress.setInt(11, checkinHdr.getCheckInNo());

				prsSaveBillingAddress.executeUpdate();
			}

			if (dtlsObj.get("isRoomNumberDirty").getAsBoolean()) {
				if ((checkinHdr.getRoomNumber() != hdrObj.get("roomNumber").getAsString())) {
					HdrUpdateSql = HdrUpdateSql + ",room_number=?";
					/* up1 */ hdrPsCount = hdrPsCount + 1;
					roomStatusUpdatePs = con.prepareStatement(roomStatusUpdateSql);
					roomStatusUpdatePs.setInt(1, RoomOccupancyStatus.OCCUPIED.getCode());
					roomStatusUpdatePs.setString(2, hdrObj.get("roomNumber").getAsString());
					roomStatusUpdatePs.executeUpdate();
					oldRoomStatusUpdatePs = con.prepareStatement(oldRoomStatusUpdateSql);
					oldRoomStatusUpdatePs.setInt(1, RoomOccupancyStatus.VACCANT.getCode());
					oldRoomStatusUpdatePs.setInt(2, RoomHkStatus.CLEANING.getCode());
					oldRoomStatusUpdatePs.setString(3, checkinHdr.getRoomNumber());
					checkinHdr.setRoomNumber(hdrObj.get("roomNumber").getAsString());
					oldRoomStatusUpdatePs.executeUpdate();
				}
			}

			if (dtlsObj.get("isNightDirty").getAsBoolean()) {
				if (checkinHdr.getNumNights() != hdrObj.get("numNights").getAsByte()) {
					for (int i = checkinHdr.getNumNights(); i > hdrObj.get("numNights").getAsByte(); i--) {
						changeNightSql = changeNightSql + '?';
						if (i != hdrObj.get("numNights").getAsByte() + 1)
							changeNightSql = changeNightSql + ',';
					}
					changeNightSql = changeNightSql + ')';
					nightUpdatePst = con.prepareStatement(changeNightSql);
					nightUpdatePst.setInt(1, dtlsObj.get("checkinDtlNo").getAsInt());
					int c = 2;
					for (int i = hdrObj.get("numNights").getAsByte() + 1; i <= checkinHdr.getNumNights(); i++) {
						nightUpdatePst.setInt(c, i);
						c = c + 1;
					}
					Calendar cal = Calendar.getInstance();
					cal.setTime(checkinHdr.getExpDepartDate());
					cal.add(Calendar.DATE, (hdrObj.get("numNights").getAsByte() - checkinHdr.getNumNights()));
					checkinHdr.setNumNights(hdrObj.get("numNights").getAsByte());
					checkinHdr.setExpDepartDate(cal.getTime());
					nightUpdatePst.executeUpdate();

					HdrUpdateSql = HdrUpdateSql + ",num_nights=?,exp_depart_date=?";
					/* up2 */ hdrPsCount = hdrPsCount + 2;
				}
			}

			if (dtlsObj.get("isDiscountDirty").getAsBoolean()) {
				if (!jobj.get("discount").isJsonNull()) {
					Discount disc = discountDao
							.getRecord((discObj.get("value").getAsJsonObject()).get("discId").getAsInt());
					if (disc.getIsPc()) {
						if (disc.getIsOpen()) {
							checkinHdr.setDiscPc(
									(discObj.get("value").getAsJsonObject()).get("openValue").getAsBigDecimal());
							checkinHdr.setDiscAmount(null);
						} else {
							checkinHdr.setDiscPc(disc.getDiscPc());
							checkinHdr.setDiscAmount(null);
						}
					} else {
						if (disc.getIsOpen()) {
							checkinHdr.setDiscAmount(
									(discObj.get("value").getAsJsonObject()).get("openValue").getAsBigDecimal());
							checkinHdr.setDiscPc(null);
						} else {
							checkinHdr.setDiscAmount(disc.getDiscAmount());
							checkinHdr.setDiscPc(null);
						}
					}
					checkinHdr.setDiscId(disc.getId());
					checkinHdr.setDiscCode(disc.getCode());
					checkinHdr.setDiscIsPc(disc.getIsPc());
					checkinHdr.setDiscIsOpen(disc.getIsOpen());
					checkinHdr.setDiscType(disc.getDiscType());

					HdrUpdateSql = HdrUpdateSql
							+ ",disc_type=?,disc_id=?,disc_code=?,disc_is_pc=?,disc_amount=?,disc_pc=?,disc_is_open=?";
					/* up3 */ hdrPsCount = hdrPsCount + 7;
					discUpdatePst = con.prepareStatement(discUpdateSql);
					for (int i = dateDiff + 1; i <= checkinHdr.getNumNights(); i++) {
						discUpdatePst.setInt(1, checkinHdr.getDiscId());
						discUpdatePst.setBigDecimal(2, checkinHdr.getDiscAmount());
						discUpdatePst.setBigDecimal(3, checkinHdr.getDiscPc());
						discUpdatePst.setBigDecimal(4, null);
						discUpdatePst.setInt(5, i);
						discUpdatePst.setInt(6, dtlsObj.get("checkinDtlNo").getAsInt());
						discUpdatePst.addBatch();
					}
					discUpdatePst.executeBatch();
				}
			}

			if (dtlsObj.get("isRatePlanDirty").getAsBoolean()) {
				if ((checkinHdr.getRateId() != hdrObj.get("rateId").getAsByte())) {
					RoomRateDetailsCheck roomRateCheck = new RoomRateDetailsCheck();
					roomRateCheck.setArrDate(checkinHdr.getArrDate());
					if (dtlsObj.get("isDiscountDirty").getAsBoolean()) {
						if (!jobj.get("discount").isJsonNull()) {
							roomRateCheck.setDiscId((discObj.get("value").getAsJsonObject()).get("discId").getAsInt());
							if (discObj.get("group").getAsString().equals("general")) {
								if (discObj.get("value").getAsJsonObject().get("isOpen").getAsBoolean()) {
									roomRateCheck.setOpenDiscount((discObj.get("value").getAsJsonObject())
											.get("openValue").getAsBigDecimal());
								}
							}
						}
					}
					if (dtlsObj.get("isNightDirty").getAsBoolean()) {
						roomRateCheck.setNumNights(hdrObj.get("numNights").getAsByte());
					} else {
						roomRateCheck.setNumNights(checkinHdr.getNumNights());
					}
					roomRateCheck.setNumRooms(1);
					roomRateCheck.setOccupancy(hdrObj.get("occupancy").getAsInt());
					roomRateCheck.setRateId(hdrObj.get("rateId").getAsInt());
					rateObj = reservationService.getRoomRateDetails(roomRateCheck);
					JsonArray ratePerOcc = rateObj.get("ratePerOcc").getAsJsonArray();
					rateUpdatePst = con.prepareStatement(checkInRateSql);
					for (JsonElement rateJE : ratePerOcc) {
						for (int i = dateDiff + 1; i <= checkinHdr.getNumNights(); i++) {
							if (i == rateJE.getAsJsonObject().get("night").getAsInt()) {
								rateUpdatePst.setBigDecimal(1,
										rateJE.getAsJsonObject().get("charges").getAsBigDecimal());
								rateUpdatePst.setBoolean(2,
										rateJE.getAsJsonObject().get("is_tax_included").getAsBoolean());
								rateUpdatePst.setBigDecimal(3,
										rateJE.getAsJsonObject().get("tax1_pc").getAsBigDecimal());
								rateUpdatePst.setBigDecimal(4,
										rateJE.getAsJsonObject().get("tax2_pc").getAsBigDecimal());
								rateUpdatePst.setBigDecimal(5,
										rateJE.getAsJsonObject().get("tax3_pc").getAsBigDecimal());
								rateUpdatePst.setBigDecimal(6,
										rateJE.getAsJsonObject().get("tax4_pc").getAsBigDecimal());
								rateUpdatePst.setBigDecimal(7,
										rateJE.getAsJsonObject().get("service_charge_pc").getAsBigDecimal());
								rateUpdatePst.setBigDecimal(8,
										rateJE.getAsJsonObject().get("tax1_amount").getAsBigDecimal());
								rateUpdatePst.setBigDecimal(9,
										rateJE.getAsJsonObject().get("tax2_amount").getAsBigDecimal());
								rateUpdatePst.setBigDecimal(10,
										rateJE.getAsJsonObject().get("tax3_amount").getAsBigDecimal());
								rateUpdatePst.setBigDecimal(11,
										rateJE.getAsJsonObject().get("tax4_amount").getAsBigDecimal());
								rateUpdatePst.setBigDecimal(12,
										rateJE.getAsJsonObject().get("service_charge_amount").getAsBigDecimal());
								rateUpdatePst.setInt(13, rateJE.getAsJsonObject().get("rate_id").getAsInt());
								rateUpdatePst.setInt(14, rateObj.get("roomTypeId").getAsInt());
								rateUpdatePst.setInt(15, roomRateCheck.getOccupancy());
								rateUpdatePst.setInt(16, checkinHdr.getDiscId());
								rateUpdatePst.setBigDecimal(17, checkinHdr.getDiscAmount());
								rateUpdatePst.setBigDecimal(18, checkinHdr.getDiscPc());
								rateUpdatePst.setInt(19, rateJE.getAsJsonObject().get("night").getAsInt());
								rateUpdatePst.setInt(20, dtlsObj.get("checkinDtlNo").getAsInt());
								rateUpdatePst.addBatch();
							}
						}
					}
					HdrUpdateSql = HdrUpdateSql + ",rate_type=?,rate_id=?,rate_code=?,rate_description=?,occupancy=?";
					/* up4 */ hdrPsCount = hdrPsCount + 5;
					checkinHdr.setRateType(ratePerOcc.get(0).getAsJsonObject().get("rate_type").getAsByte());
					checkinHdr.setRateCode(ratePerOcc.get(0).getAsJsonObject().get("rate_code").getAsString());
					checkinHdr.setRateId(roomRateCheck.getRateId());
					checkinHdr.setOccupancy((byte) roomRateCheck.getOccupancy());
					if (dtlsObj.get("isRoomTypeDirty").getAsBoolean()) {
						if ((checkinHdr.getRoomTypeId() != hdrObj.get("roomTypeId").getAsByte())) {
							HdrUpdateSql = HdrUpdateSql + ",room_type_id=?,room_type_code=?";
							/* up5 */hdrPsCount = hdrPsCount + 2;
						}
					}
					checkinHdr.setRoomTypeId(rateObj.get("roomTypeId").getAsInt());
					checkinHdr.setRoomTypeCode(rateObj.get("roomTypeCode").getAsString());
					if (ratePerOcc.get(0).getAsJsonObject().get("rate_description").isJsonNull()) {
						checkinHdr.setRateDescription(null);
					} else {
						checkinHdr.setRateDescription(
								ratePerOcc.get(0).getAsJsonObject().get("rate_description").getAsString());
					}
					rateUpdatePst.executeBatch();
				}

			}
			/* <2244 digna 20180622 begin */
			HdrUpdateSql = HdrUpdateSql + ",num_adults=?,num_children=?,num_infants=?";
			checkinHdr.setNumAdults(hdrObj.get("numAdults").getAsByte());
			checkinHdr.setNumChildren(hdrObj.get("numChildren").getAsByte());
			checkinHdr.setNumInfants(hdrObj.get("numInfants").getAsByte());
			hdrPsCount = hdrPsCount + 3;
			/* 2244 digna 20180622 end > */
			if (hdrPsCount != 0) {
				hdrPsCount = 1;
				HdrUpdateSql = "update checkin_hdr set " + (HdrUpdateSql.substring(1, HdrUpdateSql.length()))
						+ " where checkin_no=?";
				hdrUpdatePst = con.prepareStatement(HdrUpdateSql);
				if (dtlsObj.get("isRoomNumberDirty").getAsBoolean()) {
					hdrUpdatePst.setString(hdrPsCount, checkinHdr.getRoomNumber());
					hdrPsCount += 1;
				}
				if (dtlsObj.get("isNightDirty").getAsBoolean()) {
					hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getNumNights());
					hdrPsCount += 1;
					hdrUpdatePst.setDate(hdrPsCount, new Date(checkinHdr.getExpDepartDate().getTime()));
					hdrPsCount += 1;
				}
				if (dtlsObj.get("isDiscountDirty").getAsBoolean()) {
					hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getDiscType());
					hdrPsCount += 1;
					hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getDiscId());
					hdrPsCount += 1;
					hdrUpdatePst.setString(hdrPsCount, checkinHdr.getDiscCode());
					hdrPsCount += 1;
					hdrUpdatePst.setBoolean(hdrPsCount, checkinHdr.getDiscIsPc());
					hdrPsCount += 1;
					hdrUpdatePst.setBigDecimal(hdrPsCount, checkinHdr.getDiscAmount());
					hdrPsCount += 1;
					hdrUpdatePst.setBigDecimal(hdrPsCount, checkinHdr.getDiscPc());
					hdrPsCount += 1;
					hdrUpdatePst.setBoolean(hdrPsCount, checkinHdr.getDiscIsOpen());
					hdrPsCount += 1;
				}
				if (dtlsObj.get("isRatePlanDirty").getAsBoolean()) {
					hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getRateType());
					hdrPsCount += 1;
					hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getRateId());
					hdrPsCount += 1;
					hdrUpdatePst.setString(hdrPsCount, checkinHdr.getRateCode());
					hdrPsCount += 1;
					hdrUpdatePst.setString(hdrPsCount, checkinHdr.getRateDescription());
					hdrPsCount += 1;
					hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getOccupancy());
					hdrPsCount += 1;
					if (dtlsObj.get("isRoomTypeDirty").getAsBoolean()) {
						hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getRoomTypeId());
						hdrPsCount += 1;
						hdrUpdatePst.setString(hdrPsCount, checkinHdr.getRoomTypeCode());
						hdrPsCount += 1;
					}
				}
				/* <2244 digna 20180622 begin */
				hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getNumAdults());
				hdrPsCount += 1;
				hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getNumChildren());
				hdrPsCount += 1;
				hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getNumInfants());
				hdrPsCount += 1;
				/* 2244 digna 20180622 end > */
				hdrUpdatePst.setInt(hdrPsCount, checkinHdr.getCheckInNo());
				hdrUpdatePst.executeUpdate();
			}
			extendStay(hdrObj, con);

			con.commit();

			// txnJson
			getTxnDataPst = con.prepareStatement(getTxnData);
			getTxnDataPst.setInt(1, checkinHdr.getCheckInNo());
			ResultSet rsTxnData = getTxnDataPst.executeQuery();
			boolean isBase = false;
			while (rsTxnData.next()) {
				JsonObject txnObj = new JsonObject();
				if (rsTxnData.getInt("include_tax") == 1) {
					isBase = false;
				} else {
					isBase = true;
				}
				txnObj.addProperty("txnNo", rsTxnData.getInt("txn_no"));
				txnObj.addProperty("isBase", isBase);
				txnObj.addProperty("accMstId", rsTxnData.getInt("acc_mst_id"));
				txnObj.addProperty("amount", rsTxnData.getDouble("amount"));
				txnObj.addProperty("checkinNo", rsTxnData.getInt("checkin_no"));
				txnObj.addProperty("paymentMode", rsTxnData.getInt("payment_mode"));

				txnJson.add(txnObj);
			}

			if (txnJson.size() != 0) {
				for (Object obj : txnJson) {
					JsonObject dataObject = (JsonObject) obj;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.sql.Date hotel_date = new java.sql.Date(sdf.parse(hotelDate).getTime());

					Transaction txn = null;
					txn = transactionService.getCharges(hotel_date, dataObject.get("amount").getAsDouble(),
							dataObject.get("accMstId").getAsInt(), dataObject.get("isBase").getAsBoolean(),
							dataObject.get("checkinNo").getAsInt());
					updateTxnpst = con.prepareStatement(updateTxn);

					updateTxnpst.setDouble(1, txn.getBase_amount());
					updateTxnpst.setDouble(2, txn.getTax1_amount());
					updateTxnpst.setDouble(3, txn.getTax2_amount());
					updateTxnpst.setDouble(4, txn.getTax3_amount());
					updateTxnpst.setDouble(5, txn.getTax4_amount());
					if (dataObject.get("paymentMode").getAsInt() == 0) {
						updateTxnpst.setDouble(6, (-(txn.getNett_amount())));
					} else {
						updateTxnpst.setDouble(6, txn.getNett_amount());
					}
					updateTxnpst.setInt(7, dataObject.get("txnNo").getAsInt());

					updateTxnpst.executeUpdate();
				}

				con.commit();
			}

		} catch (Exception e) {
			logger.error("Method : updateReceptionEdit()" + Throwables.getStackTraceAsString(e));
			isSave = false;
			con.rollback();
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(psDlt );
			dbConnection.releaseResource(pscheckinDiscount );
			
			dbConnection.releaseResource(dtlUpdatePst );
			dbConnection.releaseResource(roomStatusUpdatePs );
			dbConnection.releaseResource(oldRoomStatusUpdatePs  );
			dbConnection.releaseResource(nightUpdatePst  );
			dbConnection.releaseResource(rateUpdatePst  );
			dbConnection.releaseResource(hdrUpdatePst  );
			dbConnection.releaseResource(addSharePs  );
			dbConnection.releaseResource(updateSharePs  );
			dbConnection.releaseResource(delSharePs  );
			dbConnection.releaseResource(discUpdatePst   );
			dbConnection.releaseResource(prsSaveBillingAddress   );
			dbConnection.releaseResource(getTxnDataPst   );
			dbConnection.releaseResource(updateTxnpst    );
			dbConnection.releaseResource(clSt    );
			 
			dbConnection.releaseResource(con);
		}
		
		return isSave;
	}

	private void extendStay(JsonObject hdrObj, Connection con) throws Exception {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();

		// rootListElement elements
		Element rootListElement = doc.createElement("list");
		doc.appendChild(rootListElement);

		Element checkinItemElement = doc.createElement("checkinItem");
		rootListElement.appendChild(checkinItemElement);

		// checkinnum elements
		Element checkinnum = doc.createElement("checkInNo");
		checkinnum.appendChild(doc.createTextNode(hdrObj.get("checkInNo").getAsString()));
		checkinItemElement.appendChild(checkinnum);

		// daynum elements
		Element daynum = doc.createElement("dayNum");
		daynum.appendChild(doc.createTextNode(hdrObj.get("dayNum").getAsString()));
		checkinItemElement.appendChild(daynum);

		// convert xml document to string
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString();

		CallableStatement st = con.prepareCall("{call ExtendStay(?)}");
		st.setString(1, output.replaceAll("\\s*[\\r\\n]+\\s*", "").trim());
		st.executeUpdate();

	}

	public JsonArray getInHouseList() throws Exception {
		String sql = "select * from v_reception_in_house_list";
		Connection connection = dbConnection.getConnection();
		ResultSet resultSet = null;
		Statement statement = null;
		JsonArray jsonRecptnArray = new JsonArray();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			JsonObject recpRow = null;
			while (resultSet.next()) {
				recpRow = new JsonObject();
				recpRow.addProperty("checkin_no", resultSet.getInt("checkin_no"));
				recpRow.addProperty("arr_date", resultSet.getString("arr_date").toString());
				recpRow.addProperty("arr_time", resultSet.getString("arr_time").toString());
				recpRow.addProperty("exp_depart_date", resultSet.getString("depart_date"));
				recpRow.addProperty("room_type_code", resultSet.getString("room_type_code"));
				recpRow.addProperty("room_number", resultSet.getString("room_number"));
				recpRow.addProperty("phone", resultSet.getString("phone"));
				recpRow.addProperty("name", resultSet.getString("customer_name"));
				recpRow.addProperty("folio_bind_no", resultSet.getInt("folio_bind_no"));
				jsonRecptnArray.add(recpRow);
			}
		} catch (Exception e) {
			logger.error("Method: getRatePlan, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		} finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return jsonRecptnArray;
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

			search += "(room_number like '%" + searchObj.get("comnSearchValue").getAsString() + "%' OR "
					+ "customer_name like '%" + searchObj.get("comnSearchValue").getAsString() + "%') "
					+ "AND (act_depart_date IS NULL OR act_depart_date = (select hotel_date from system_setting))";

		} else if (searchObj.get("advSearch").getAsBoolean()) {
			int count = 0;
			if (searchObj.get("roomNumber").getAsJsonObject().get("searchable").getAsBoolean()) {
				search += " room_number = '"
						+ searchObj.get("roomNumber").getAsJsonObject().get("searchValue").getAsString() + "'";
				count++;
			}
			if (searchObj.get("customerName").getAsJsonObject().get("searchable").getAsBoolean()) {
				if (count != 0) {
					search += " AND ";
				}
				search += "customer_name like '%"
						+ searchObj.get("customerName").getAsJsonObject().get("searchValue").getAsString() + "%'";
				count++;

			}
			if (null != searchObj.get("checkoutDate")
					&& null != searchObj.get("checkoutDate").getAsJsonObject().get("searchValue")
					&& searchObj.get("checkoutDate").getAsJsonObject().get("searchable").getAsBoolean()
					&& !("ALL".equals(
							searchObj.get("checkoutDate").getAsJsonObject().get("searchValue").getAsString()))) {
				if (count != 0) {
					search += " AND ";
				}
				Date tempDate = new Date(Long
						.parseLong(searchObj.get("checkoutDate").getAsJsonObject().get("searchValue").getAsString()));
				search += "act_depart_date  = '" + tempDate + "'";

			}
		} else {
			search += " (act_depart_date IS NULL OR act_depart_date >= (select hotel_date from system_setting))";
		}
		String sql = "select * from v_reception_in_house_list " + search + "  order by " + sort + " " + sortValue
				+ " limit " + limit + " offset " + startRow + "";
		String countSql = "select count(checkin_no) from v_reception_in_house_list " + search;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			JsonObject recpRow = null;
			while (resultSet.next()) {
				recpRow = new JsonObject();
				recpRow.addProperty("checkinStatus", resultSet.getInt("checkin_status"));
				recpRow.addProperty("allowTxns", resultSet.getInt("allow_txns"));
				recpRow.addProperty("arrDate", resultSet.getString("arr_date").toString());
				recpRow.addProperty("arrTime", resultSet.getString("arr_time").toString());
				recpRow.addProperty("departDate", resultSet.getString("depart_date"));
				recpRow.addProperty("roomTypeId", resultSet.getInt("room_type_id"));
				recpRow.addProperty("occupancy", resultSet.getInt("occupancy"));
				recpRow.addProperty("checkinNo", resultSet.getInt("checkin_no"));
				recpRow.addProperty("checkinStatusXlt", resultSet.getString("checkin_status_xlt"));
				recpRow.addProperty("roomNumber", resultSet.getString("room_number"));
				recpRow.addProperty("customerName", resultSet.getString("customer_name"));
				recpRow.addProperty("corporateName", resultSet.getString("corporate_name"));
				recpRow.addProperty("folioBalance", resultSet.getString("folio_balance"));
				recpRow.addProperty("folioNo", resultSet.getInt("folio_no"));
				recpRow.addProperty("folioBindNo", resultSet.getInt("folio_bind_no"));
				recpRow.addProperty("actDepartDate", resultSet.getString("act_depart_date"));
				recpRow.addProperty("stayedDays", 0);
				jsonCardArray.add(recpRow);
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

	public JsonArray customerDetailViaPhone(String phone) throws Exception {
		JsonArray jArray = new JsonArray();
		JsonObject jObj = null;
		ResultSet rs = null;
		PreparedStatement customerDetail = null;
		// Encryption encryption = new Encryption();
		Connection connection = dbConnection.getConnection();
		String query = "SELECT count(checkin_hdr.arr_date) AS count, max(checkin_hdr.arr_date) as arr_date, checkin_dtl.salutation, checkin_dtl.first_name,checkin_dtl.last_name,checkin_dtl.gender,checkin_dtl.address,checkin_dtl.email,checkin_dtl.phone,checkin_dtl.nationality,checkin_dtl.state,checkin_dtl.passport_no,checkin_dtl.passport_expiry_on,checkin_dtl.gstno,checkin_dtl.remarks FROM checkin_dtl INNER JOIN checkin_hdr ON checkin_hdr.checkin_no = checkin_dtl.checkin_no WHERE phone='"
				+ phone + "'";
		try {
			customerDetail = connection.prepareStatement(query);
			rs = customerDetail.executeQuery();
			while (rs.next()) {
				jObj = new JsonObject();
				jObj.addProperty("salutation", rs.getString("salutation"));
				jObj.addProperty("first_name", rs.getString("first_name"));
				jObj.addProperty("last_name", rs.getString("last_name"));
				jObj.addProperty("gender", rs.getString("gender"));
				jObj.addProperty("address", rs.getString("address"));
				jObj.addProperty("email", rs.getString("email"));
				jObj.addProperty("nationality", rs.getString("nationality"));
				jObj.addProperty("state", rs.getString("state"));
				jObj.addProperty("passport_no", rs.getString("passport_no"));
				jObj.addProperty("passport_expiry_on", rs.getString("passport_expiry_on"));
				jObj.addProperty("gstno", rs.getString("gstno"));
				jObj.addProperty("remarks", rs.getString("remarks"));
				jObj.addProperty("no_visit", rs.getString("count"));
				jObj.addProperty("last_visit", rs.getString("arr_date"));
				jArray.add(jObj);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(customerDetail);
			dbConnection.releaseResource(rs);
		}
		return jArray;

	}

	@Override
	public void changeRoomStatus(int roomId) {
		try {

			final String query = "UPDATE room SET hk_status=3 WHERE id=?";
			jdbcTemplate.update(query, roomId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}

	}

	@SuppressWarnings("resource")
	@Override
	public JsonArray getGrcFormData(int recpNo) {
		// to print grc form data

		JsonObject grcFormData = new JsonObject();
		JsonArray grcFormArray = new JsonArray();
		Connection connection = dbConnection.getConnection();
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1=null;
		ResultSet resultSet = null;

		String deposit_count_query = "select COUNT(txn.acc_mst_code) AS depositCount from txn INNER JOIN folio on folio.folio_no = txn.folio_no\n"
				+ "INNER JOIN checkin_hdr hdr on folio.checkin_no= hdr.checkin_no && txn.acc_mst_code='DEPOSIT'"
				+ "WHERE hdr.checkin_no='" + recpNo + "'";

		String grcQuery1 = "select dtl.*,hdr.arr_date,hdr.room_number,"
				+ "hdr.arr_date,hdr.arr_time,hdr.exp_depart_date,hdr.exp_depart_time,hdr.room_type_code,"
				+ "hdr.num_nights,txn.acc_mst_code,txn.nett_amount,SUM(nett_amount) AS totalAdvance "
				+ "FROM checkin_dtl dtl INNER JOIN checkin_hdr hdr ON hdr.checkin_no=dtl.checkin_no "
				+ "INNER JOIN folio ON folio.checkin_no = hdr.checkin_no "
				+ "INNER JOIN txn ON txn.folio_no = folio.folio_no && txn.acc_mst_code='DEPOSIT'"
				+ "WHERE dtl.checkin_no='" + recpNo + "'";

		String grcQuery = "select checkin_dtl.*,checkin_hdr.arr_date,checkin_hdr.room_number,checkin_hdr.arr_date,"
				+ "checkin_hdr.arr_time,checkin_hdr.exp_depart_date,checkin_hdr.exp_depart_time,"
				+"checkin_hdr.room_type_code,checkin_hdr.num_nights FROM checkin_dtl "
				+"INNER JOIN checkin_hdr ON checkin_hdr.checkin_no=checkin_dtl.checkin_no "
				+ "WHERE checkin_dtl.checkin_no='" + recpNo + "'";

		try {

			preparedStatement = connection.prepareStatement(deposit_count_query);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int deposit_count = resultSet.getInt("depositCount");
				if (deposit_count == 0) {

					preparedStatement1 = connection.prepareStatement(grcQuery);
					resultSet = preparedStatement1.executeQuery();

					while (resultSet.next()) {

						grcFormData = new JsonObject();
						grcFormData.addProperty("checkin_no", resultSet.getString("checkin_no"));
						grcFormData.addProperty("arr_date", resultSet.getString("arr_date"));
						grcFormData.addProperty("salutation", resultSet.getString("salutation"));
						grcFormData.addProperty("first_name", resultSet.getString("first_name"));
						grcFormData.addProperty("last_name", resultSet.getString("last_name"));
						grcFormData.addProperty("address", resultSet.getString("address"));
						grcFormData.addProperty("passport_no", resultSet.getString("passport_no"));
						grcFormData.addProperty("nationality", resultSet.getString("nationality"));
						grcFormData.addProperty("email", resultSet.getString("email"));
						grcFormData.addProperty("phone", resultSet.getString("phone"));
						grcFormData.addProperty("state", resultSet.getString("state"));
						grcFormData.addProperty("room_number", resultSet.getString("room_number"));
						grcFormData.addProperty("arr_date", resultSet.getString("arr_date"));
						grcFormData.addProperty("arr_time", resultSet.getString("arr_time"));
						grcFormData.addProperty("exp_depart_date", resultSet.getString("exp_depart_date"));
						grcFormData.addProperty("exp_depart_time", resultSet.getString("exp_depart_time"));
						grcFormData.addProperty("room_type_code", resultSet.getString("room_type_code"));
						grcFormData.addProperty("num_nights", resultSet.getString("num_nights"));
						

						grcFormArray.add(grcFormData);
					}
				} else {

					preparedStatement = connection.prepareStatement(grcQuery1);
					resultSet = preparedStatement.executeQuery();

					while (resultSet.next()) {

						grcFormData = new JsonObject();
						grcFormData.addProperty("checkin_no", resultSet.getString("checkin_no"));
						grcFormData.addProperty("arr_date", resultSet.getString("arr_date"));
						grcFormData.addProperty("salutation", resultSet.getString("salutation"));
						grcFormData.addProperty("first_name", resultSet.getString("first_name"));
						grcFormData.addProperty("last_name", resultSet.getString("last_name"));
						grcFormData.addProperty("address", resultSet.getString("address"));
						grcFormData.addProperty("passport_no", resultSet.getString("passport_no"));
						grcFormData.addProperty("nationality", resultSet.getString("nationality"));
						grcFormData.addProperty("email", resultSet.getString("email"));
						grcFormData.addProperty("phone", resultSet.getString("phone"));
						grcFormData.addProperty("state", resultSet.getString("state"));
						grcFormData.addProperty("room_number", resultSet.getString("room_number"));
						grcFormData.addProperty("arr_date", resultSet.getString("arr_date"));
						grcFormData.addProperty("arr_time", resultSet.getString("arr_time"));
						grcFormData.addProperty("exp_depart_date", resultSet.getString("exp_depart_date"));
						grcFormData.addProperty("exp_depart_time", resultSet.getString("exp_depart_time"));
						grcFormData.addProperty("room_type_code", resultSet.getString("room_type_code"));
						grcFormData.addProperty("num_nights", resultSet.getString("num_nights"));
						grcFormData.addProperty("acc_mst_code", resultSet.getString("acc_mst_code"));
						grcFormData.addProperty("totalAdvance", resultSet.getString("totalAdvance"));

						grcFormArray.add(grcFormData);

					}

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(preparedStatement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(preparedStatement1);
			dbConnection.releaseResource(connection);
			
		}
		return grcFormArray;

	}
}
