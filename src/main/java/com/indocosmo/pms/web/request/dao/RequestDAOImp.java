package com.indocosmo.pms.web.request.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.checkIn.model.CheckInRequest;
import com.indocosmo.pms.web.checkIn.model.CheckInRequestStatus;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.systemSettings.dao.SystemSettingsDao;



@Repository
public class RequestDAOImp implements RequestDAO{

	private DbConnection dbConnection=null;
	@Autowired
	SystemSettingsDao systemSettingsDao;

	@Autowired
	private SessionFactory sessionFactory;

	public RequestDAOImp(){
		dbConnection=new DbConnection();
	}

	public JsonArray getRoomLists() {
		Connection connection=dbConnection.getConnection();
		Statement statement=null;
		ResultSet resultset=null;
		JsonObject jsonObject;
		JsonArray JsonArray = new JsonArray();
		String sql = "select ch.checkin_no,ch.room_number,cd.first_name,cd.phone,ch.exp_depart_date from checkin_hdr ch inner join checkin_dtl cd on cd.checkin_no = ch.checkin_no where ch.`status` = 5 and cd.is_sharer=0";
		try{
			statement=connection.createStatement();
			resultset=statement.executeQuery(sql);
			while(resultset.next()){
				jsonObject=new JsonObject();
				jsonObject.addProperty("checkin_no", resultset.getInt("checkin_no"));
				jsonObject.addProperty("room_number", resultset.getString("room_number"));
				jsonObject.addProperty("first_name", resultset.getString("first_name"));
				jsonObject.addProperty("phone", resultset.getString("phone"));
				jsonObject.addProperty("exp_depart_date", resultset.getDate("exp_depart_date").toString());
				JsonArray.add(jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(resultset);
		}
		return JsonArray;
	}

	public JsonArray getFacilities() {
		Connection connection=dbConnection.getConnection();
		Statement statement=null;
		ResultSet resultset=null;
		JsonObject jsonObject;
		JsonArray JsonArray = new JsonArray();
		String sql = "SELECT facilities.id,facilities.`code`,facilities.`name`,facilities.description,facilities.facility_type FROM facilities WHERE facilities.is_deleted=0";
		try{
			statement=connection.createStatement();
			resultset=statement.executeQuery(sql);
			while(resultset.next()){
				jsonObject=new JsonObject();
				jsonObject.addProperty("id", resultset.getInt("id"));
				jsonObject.addProperty("code", resultset.getString("code"));
				jsonObject.addProperty("name", resultset.getString("name"));
				jsonObject.addProperty("description", resultset.getString("description"));
				jsonObject.addProperty("facility_type", resultset.getString("facility_type").toString());
				JsonArray.add(jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(resultset);
		}
		return JsonArray;
	}


	public boolean deleteAddOns(int id) throws Exception{
		boolean isDeleted = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			CheckInRequest chreq = (CheckInRequest) session.load(CheckInRequest.class, id);
			session.delete(chreq);
		} catch(Exception ex) {
			ex.printStackTrace();
			isDeleted = false;
			throw new CustomException();
		}
		return isDeleted;
	}

	public boolean saveAdd(List<CheckInRequest> chreqlist)throws Exception {
		boolean isSave = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			for(CheckInRequest chr : chreqlist){
				if(chr.getIsDeleted()!=true){
					if(chr.getId() != 0) {
						session.update(chr);
					} else { 
						String dateInact="";
						if(chr.getInactiveDateRequest()!=null&& chr.getInactiveDateRequest().size()>0){

							for(String inactveDate:chr.getInactiveDateRequest()){
								dateInact+="'"+inactveDate+"',";
							}
							dateInact=dateInact.substring(0, dateInact.length()-1);
						}
						java.util.Date Datere=chr.getReqDate();
						java.sql.Date sql_ReqDate=new java.sql.Date( Datere.getTime() );
						java.util.Date Dateretakn=chr.getReqTakenDate();
						java.sql.Date sql_Dateretakn=new java.sql.Date( Dateretakn.getTime() );
						java.util.Date DatereqTime=chr.getReqTime();
						Timestamp sql_DatereqTime=new java.sql.Timestamp( DatereqTime.getTime() );
						java.util.Date DatereqToDate=null;
						java.sql.Date sql_DatereqToDate=null;
						if(chr.getReqTodate()!=null){
							DatereqToDate=chr.getReqTodate();
							sql_DatereqToDate=new java.sql.Date( DatereqToDate.getTime() );
						}else{
							sql_DatereqToDate=sql_ReqDate;
						}
						Connection con = null;
						java.sql.CallableStatement st = null;
						ResultSet rs = null;
						con = dbConnection.getConnection();
						st = con.prepareCall("{call saveRequest(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						st.setInt(1,chr.getArrangementBy());
						st.setBoolean(2,chr.getCanceled());
						st.setInt(3,chr.getCheckInNo());
						st.setInt(4,chr.getFacilityId());
						st.setInt(5,chr.getId());
						st.setBoolean(6,chr.getIsDeleted());
						st.setBoolean(7,chr.isReqCompleted());
						st.setInt(8,chr.getOneTimeReq());
						st.setDate(9,sql_ReqDate);
						st.setString(10,chr.getReqRemarks());
						st.setInt(11,chr.getReqTakenBy());
						st.setDate(12,sql_Dateretakn);
						st.setTimestamp(13,sql_DatereqTime);
						st.setDate(14,sql_DatereqToDate);
						st.setInt(15,chr.getResvRoomNo());
						st.setInt(16,chr.getUserId());
						st.setInt(17, chr.getProvider());
						st.setString(18,dateInact);
						rs = st.executeQuery();

					}
				}else{
					isSave=deleteAddOns(chr.getId());
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			isSave = false;
			throw new CustomException();
		}
		return isSave;
	}


	public boolean delete(int id) {

		boolean canceled = true;
		try{
			Session session = sessionFactory.getCurrentSession();
			CheckInRequestStatus checkInRequestStatus=(CheckInRequestStatus)session.load(CheckInRequestStatus.class,id);
			checkInRequestStatus.setCanceled(true);
			session.update(checkInRequestStatus);

		}catch(Exception ex) {
			ex.printStackTrace();
			canceled = false;
			throw new CustomException();
		}
		return canceled;
	}
	@Override
	public boolean update(CheckInRequestStatus checkInRequestStatus)throws Exception {
		boolean isUpdate = true;
		try{
			Session session = sessionFactory.getCurrentSession();
			if(checkInRequestStatus.getId() != 0) {
				session.update(checkInRequestStatus);
			} 

		} catch(Exception ex) {
			ex.printStackTrace();
			isUpdate = false;
			throw new CustomException();
		}

		return isUpdate;
	}


	public JsonArray getJsonArrayFromRs(ResultSet rs) throws SQLException{
		JsonObject jsonObject;
		JsonArray jsonAry = new JsonArray();
		while(rs.next()){
			jsonObject=new JsonObject();
			jsonObject.addProperty("checkinRequest_id",rs.getInt("id"));
			jsonObject.addProperty("chkinrqst_is_one_time_request", rs.getInt("is_one_time_request"));
			jsonObject.addProperty("chkReqDate", rs.getString("req_date"));
			jsonObject.addProperty("chkReqTime",rs.getString("req_time"));
			jsonObject.addProperty("chkReqRemarks",rs.getString("req_remarks"));
			jsonObject.addProperty("chkReqFacId", rs.getString("facility_id"));
			jsonObject.addProperty("chkStatusProcessStatus",rs.getString("process_status"));
			jsonObject.addProperty("chkreqStatusDate",rs.getString("date"));
			jsonObject.addProperty("chkStatusRemarks", rs.getString("remarks"));
			jsonObject.addProperty("facilityCode",rs.getString("facility_code"));
			jsonObject.addProperty("facilityName",rs.getString("facility_name"));
			jsonObject.addProperty("facilityIsPayable",rs.getString("is_payable"));
			jsonObject.addProperty("facilityacc_mst_id",rs.getString("acc_mst_id"));
			jsonObject.addProperty("facilityAmount",rs.getString("amount"));
			jsonObject.addProperty("facilitytype",rs.getString("facility_type"));
			jsonObject.addProperty("chkhdrroom_number",rs.getString("room_number"));
			jsonObject.addProperty("chkHdrcheckin_no",rs.getString("checkin_no"));
			jsonObject.addProperty("chkHdrfolio_bind_no",rs.getString("folio_bind_no"));
			jsonObject.addProperty("chkReqis_req_completed",rs.getString("is_req_completed"));
			jsonObject.addProperty("chkReqis_canceled",rs.getString("is_canceled"));
			jsonObject.addProperty("first_name",rs.getString("guest_name"));
			jsonObject.addProperty("phone",rs.getString("phone"));
			jsonObject.addProperty("facProvId",rs.getInt("facprovid"));
			jsonObject.addProperty("facprovcode",rs.getString("facprovcode"));
			jsonObject.addProperty("facproname",rs.getString("facproname"));
			jsonObject.addProperty("requst_status_id",rs.getInt("requst_status_id"));
			jsonObject.addProperty("process_status", rs.getInt("process_status"));
			jsonAry.add(jsonObject);
		}
		return jsonAry;
	}
	@Override
	public JsonObject getSearchData(JsonObject jobj, Map<String, String> searchMap) throws Exception {

		Connection connection= null;
		Statement statement = null;
		Statement statementCount = null;
		ResultSet resultSet = null;
		
		JsonArray  jsonRequestArray ;
		JsonObject jsonFullContent = new JsonObject();
		
		int startRow = jobj.get("pagination").getAsJsonObject().get("offset").getAsInt();
		int limit=jobj.get("pagination").getAsJsonObject().get("limit").getAsInt();
		String selectedTab=jobj.get("pagination").getAsJsonObject().get("selectedTab").getAsString();
		int totalCount=0;
		
		String sort = jobj.get("sort").getAsJsonObject().get("sortColumn").getAsString();
		String sortValue=jobj.get("sort").getAsJsonObject().get("order").getAsString();
		String defaulCondition = " where '" + 5 + "' = `status` AND `statusCanceld` <> 1 ";
		if(jobj.get("searchType").getAsString().equals("advance")){
			defaulCondition+=getAdvanceSearchCondition(searchMap,selectedTab);
		}else if(jobj.get("searchType").getAsString().equals("simple")){
			String commonSearchValue = jobj.get("comnSearchValue").getAsString();
			defaulCondition+= "AND ("+getSimpleSearchCondition(commonSearchValue, selectedTab)+")";
		}
		if(selectedTab.equals("ALLREQ")){
			if(searchMap.containsKey("req_Date")){
				defaulCondition+= " AND  date ='"+searchMap.get("req_Date")+"'";
			}
		}else{
			defaulCondition+=" AND date<='"+new Date(commonSettings.getHotelDate().getTime())+"' AND process_status <> 1";
		}
		String countSql="SELECT COUNT(id) AS recordCount FROM v_checkin_request_details"+defaulCondition;
		String sql = "SELECT * FROM v_checkin_request_details"+ defaulCondition+"  order by "+ sort +" "+ sortValue+" limit "+limit+" offset "+startRow+"";
		try{
			connection=dbConnection.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(countSql);
			if(resultSet.next())
				totalCount =resultSet.getInt("recordCount"); 
			resultSet=statement.executeQuery(sql);

			jsonRequestArray=getJsonArrayFromRs(resultSet);
			jsonFullContent.add("requestData", jsonRequestArray);
			jsonFullContent.addProperty("totalRowCount", totalCount);

		} catch(Exception ex) {
			ex.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(resultSet);
		}
		return jsonFullContent;
	}
	
	private String getSimpleSearchCondition(String commonSearchValue,String selectedTab) {

		String searchCondition = "room_number like '%" + commonSearchValue + "%' OR "
					+ "guest_name like '%" + commonSearchValue + "%' OR "
					+ "phone like'%"+ commonSearchValue + "%' OR "
					+ "facility_code like '%" + commonSearchValue + "%' OR "
					+ "process_title = '" + commonSearchValue + "' OR "
					+ "addon_status ='" + commonSearchValue + "'";
					
		return searchCondition;
	}

	private String getAdvanceSearchCondition(Map<String, String> advanceSearchMap,String selectedTab) {

		String searchCondition="";
		if(advanceSearchMap.containsKey("room_no") ) {
			searchCondition += " AND room_number ='"+advanceSearchMap.get("room_no") +"'";
		}
		if(advanceSearchMap.containsKey("req_Status")) {
			searchCondition += " AND `addon_status` ='"+advanceSearchMap.get("req_Status")+"'";
		}
		if(advanceSearchMap.containsKey("req_Date")) {
			searchCondition += " AND `req_date` ='"+advanceSearchMap.get("req_Date")+"'";
		}
		return searchCondition;
	}

	public String isRequestExist(int checkinnumber, int facilityid, String requstDate, String reqTime) throws Exception {
		
		String count="0";
		Connection connection = null;
		PreparedStatement isRequest=null;
		ResultSet rs=null;
		Encryption encryption=new Encryption();
		String query="SELECT count(checkin_request_status.id) as count FROM checkin_request_status INNER JOIN checkin_request ON checkin_request.id = checkin_request_status.checkin_request_id INNER JOIN checkin_hdr ON checkin_hdr.checkin_no = checkin_request.checkin_no INNER JOIN facilities ON facilities.id = checkin_request.facility_id WHERE date=DATE('"+requstDate+"') AND checkin_request.checkin_no='"+checkinnumber+"' AND facilities.id='"+facilityid+"' AND TIME_FORMAT(checkin_request_status.req_time, \"%H:%i\") = TIME_FORMAT('"+reqTime+"', \"%H:%i\") and checkin_request_status.is_canceled <>1";
		try{
			connection=dbConnection.getConnection();
			isRequest=connection.prepareStatement(query);
			rs=isRequest.executeQuery();
			while (rs.next()) {
				count=rs.getString("count");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(isRequest);
			dbConnection.releaseResource(connection);
			dbConnection.releaseResource(rs);
		}
		

		return count;
	}

}