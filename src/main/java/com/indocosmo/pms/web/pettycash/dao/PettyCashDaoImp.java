package com.indocosmo.pms.web.pettycash.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.PettyAmount;
import com.indocosmo.pms.util.DbConnection;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.pettycash.controller.PettyCashController;
import com.indocosmo.pms.web.pettycash.model.PettyCash;
import com.indocosmo.pms.web.reception.dao.ReceptionDAO;
import com.indocosmo.pms.web.reception.dao.ReceptionDAOImpl;
import com.indocosmo.pms.web.reservation.controller.ReservationController;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

@Repository
public class PettyCashDaoImp implements PettyCashDao{
	private static final String PAYMENTPETTY="PAYMENT";
	private static final String PAYMENTPETTYCONTRA="CONTRA";
	private static final int UNIQUE_ID=1;
	
	public static final Logger logger = LoggerFactory.getLogger(PettyCashDaoImp.class);

	@Autowired
	SystemSettingsService systemSettingsService;

	DbConnection dbConnection = null;
	Connection connection=null;
	Statement statement = null;
	ResultSet resultSet = null;
	PreparedStatement psPettyCatInsert = null;
	PreparedStatement psPettyHdInsert=null;

	private PettyCashDaoImp() {
		dbConnection = new DbConnection();

	}


	@Override
	public double openingBalance(String hotelDate) throws ParseException, SQLException {
		
		Connection connection=null;
		
		double openingBalance=0.00;
		//String selectSql ="SELECT SUM(opening_float) AS opening_float FROM user_shift WHERE user_shift.opening_date='"+hotelDate+"'";
		/*String selectSql ="SELECT getOpening(?)";
		psPettyCatInsert=connection.prepareStatement(selectSql);
		psPettyCatInsert.setString(1,hotelDate);
		resultSet=psPettyCatInsert.executeQuery();*/
		

		try {
			
			connection = dbConnection.getConnection();
			CallableStatement callStmt = connection.prepareCall("{? = call getOpening(?)}");
			callStmt.registerOutParameter(1, java.sql.Types.DECIMAL);
			callStmt.setString(2,hotelDate);
			resultSet=callStmt.executeQuery();
			while (resultSet.next()) {
				openingBalance= resultSet.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return openingBalance;
	}


	@Override
	public String saveCategory(String saveJson) {
		Connection connection=null;
		
		String insertSql="";
		String status="";
		String updateSql="";
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonSave = jsonParser.parse(saveJson).getAsJsonObject();
		int parentId=jsonSave.get("parentId").getAsInt();

		try {
			connection = dbConnection.getConnection();
			if(jsonSave.get("id")==null) {
				insertSql="insert into petty_cash_category(name,parent_id,description,is_deleted) values(?,?,?,?)";
				if(parentId==0) {				
					psPettyCatInsert=connection.prepareStatement(insertSql);
					psPettyCatInsert.setString(1,jsonSave.get("head").getAsString());
					psPettyCatInsert.setInt(2,parentId);
					psPettyCatInsert.setString(3,jsonSave.get("description").getAsString());
					psPettyCatInsert.setInt(4,0);
					psPettyCatInsert.executeUpdate();
					status="success";
				}else {
					psPettyCatInsert=connection.prepareStatement(insertSql);
					psPettyCatInsert.setString(1,jsonSave.get("head").getAsString());
					psPettyCatInsert.setInt(2,jsonSave.get("categoryId").getAsInt());
					if(jsonSave.get("description").getAsString()!=null) {
						psPettyCatInsert.setString(3,jsonSave.get("description").getAsString());
					}else {
						psPettyCatInsert.setString(3,"");
					}
					psPettyCatInsert.setInt(4,0);
					psPettyCatInsert.executeUpdate();
					status="success";
				}
			}else {
				updateSql="update petty_cash_category SET name=?,parent_id=?,description=?,is_deleted=? WHERE id=?";
				psPettyCatInsert=connection.prepareStatement(updateSql);
				psPettyCatInsert.setString(1,jsonSave.get("head").getAsString());
				psPettyCatInsert.setInt(2,jsonSave.get("categoryId").getAsInt());
				if(jsonSave.get("description").getAsString()!=null || jsonSave.get("description").getAsString()!="") {
					psPettyCatInsert.setString(3,jsonSave.get("description").getAsString());
				}else {
					psPettyCatInsert.setString(3,"");
				}
				psPettyCatInsert.setInt(4,0);
				psPettyCatInsert.setInt(5,jsonSave.get("id").getAsInt());
				psPettyCatInsert.executeUpdate();
				status="success";
			}
		} catch (SQLException e) {
			status="failed";
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return status;

	}


	@Override
	public JsonArray loadCategory() {
		Connection connection=null;
		
		JsonArray categoryArray=new JsonArray();
		String selSql="SELECT id,name FROM petty_cash_category WHERE petty_cash_category.parent_id=0 AND petty_cash_category.is_deleted=0";
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			resultSet=statement.executeQuery(selSql);
			while(resultSet.next()) {
				JsonObject categoryObj=new JsonObject();
				categoryObj.addProperty("id",resultSet.getInt("id"));
				categoryObj.addProperty("name",resultSet.getString("name"));
				categoryArray.add(categoryObj);
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
		return categoryArray;
	}


	@Override
	public JsonArray loadHead() {
		Connection connection=null;
		
		JsonArray categoryArray=new JsonArray();
		String selSql="SELECT id,name FROM petty_cash_category WHERE petty_cash_category.parent_id<>0 AND petty_cash_category.is_deleted=0 "
				+ "GROUP BY `name`,parent_id";
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			resultSet=statement.executeQuery(selSql);
			while(resultSet.next()) {
				JsonObject categoryObj=new JsonObject();
				categoryObj.addProperty("id",resultSet.getInt("id"));
				categoryObj.addProperty("name",resultSet.getString("name"));
				categoryArray.add(categoryObj);
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
		return categoryArray;
	}


	@SuppressWarnings("unlikely-arg-type")
	@Override
	public List<PettyCash> saveExpense(String saveExpense) throws Exception {
		Connection connection=null;
		Statement statement = null;
		PettyCash pettyCash = new PettyCash();
		PreparedStatement psPettyCatInsert = null;
		PreparedStatement psPettyHdInsert=null;
		
		String insertSql="";
		String status="";
		String selSql="";
		String updateSql="";
		int[] count;
		double amount=0.00;
		double opening_bal=0;
		SystemSettings systemSettings = null;
		CallableStatement clSt = null;
		ResultSet rs = null;
		PreparedStatement psSelStatement=null;
		List<PettyCash> pettyCashList=new ArrayList<>();
		//PettyCash pettyCash=null;
		ReceptionDAOImpl receptionDao=new ReceptionDAOImpl();
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObj = jsonParser.parse(saveExpense).getAsJsonObject();
		String jsoneXP=jsonObj.get("saveExpense").getAsString();
		/*	String entryDate=jsonObj.get("entryDate").getAsString();*/
		Date entrydate = simpleDateformat.parse(jsonObj.get("entryDate").getAsString());
		String nextDate = simpleDateformat.format(entrydate.getTime()+MILLIS_IN_DAY);
		System.out.println("nextDate:"+nextDate);
		//simpleDateformat.parse(nextDate);
	//	pettyCash.setEntryDate(simpleDateformat.parse(nextDate));
		pettyCash.setEntryDate(entrydate);
		//entrydate.after(entrydate.);
		Date NewEntryDate = pettyCash.getEntryDate();
		double opening =jsonObj.get("openingb").getAsDouble();
		JsonArray jsonSave = (JsonArray) jsonParser.parse(jsoneXP);
		try {
			connection = dbConnection.getConnection();
			systemSettings = systemSettingsService.getSystemSettings();		
			String counterSql = "{? = call counter(?)}";
			double dayOpng=getOpening(simpleDateformat.format(NewEntryDate));
			
			System.out.println("dayOpng:"+dayOpng);
			if(dayOpng==0) {
				opening_bal=opening;
			}else {
				opening_bal=0;
			}

			insertSql="insert into petty_cash_expense(entry_date,category_id,voucher_name,amount,"
					+ "voucher_no,narration,opening_balance) values(?,?,?,?,?,?,?)";
			updateSql="update petty_cash_expense SET entry_date=?,category_id=?,voucher_name=?,amount=?,"
					+ "voucher_no=?,narration=?  where id=?";
			clSt = connection.prepareCall(counterSql);

			for(int i=0;i<jsonSave.size();i++) {
				JsonObject saveExpArray=(JsonObject) jsonSave.get(i);
				pettyCash=new PettyCash();
				if(saveExpArray.get("id").getAsString()!=null && saveExpArray.get("id").getAsString().length()>0) {
					pettyCash.setId(saveExpArray.get("id").getAsInt());
					psPettyCatInsert=connection.prepareStatement(updateSql);
					psPettyCatInsert.setInt(7,pettyCash.getId());
				}else {

					psPettyCatInsert=connection.prepareStatement(insertSql,psPettyCatInsert.RETURN_GENERATED_KEYS);
					psPettyCatInsert.setDouble(7,opening_bal);

				}
				pettyCash.setVoucherNo(receptionDao.getCounterNo(clSt, "voucher_no", rs));

				String entrydatestr = simpleDateformat.format(NewEntryDate);
				psPettyCatInsert.setString(1,entrydatestr);
				psPettyCatInsert.setInt(2,saveExpArray.get("categoryId").getAsInt());

				psPettyCatInsert.setString(3,saveExpArray.get("voucherType").getAsString());


				if(saveExpArray.get("voucherType").getAsString().equals(PAYMENTPETTY)){
					amount=saveExpArray.get("creditAmount").getAsDouble();

				}else if(saveExpArray.get("voucherType").getAsString().equals(PAYMENTPETTYCONTRA)){
					amount=saveExpArray.get("debitAmount").getAsDouble();
				}
		
				else {
					amount=saveExpArray.get("creditCardAmount").getAsDouble();

				}

				psPettyCatInsert.setDouble(4,amount);
				psPettyCatInsert.setInt(5,pettyCash.getVoucherNo());
				psPettyCatInsert.setString(6,saveExpArray.get("narration").getAsString());
				//psPettyCatInsert.setDouble(7,opening_bal);

				psPettyCatInsert.addBatch();
				if(saveExpArray.get("id")!=null && saveExpArray.get("id").getAsString().length()>0) {

					pettyCash.setCategoryId(saveExpArray.get("categoryId").getAsInt());
					pettyCash.setNarration(saveExpArray.get("narration").getAsString());
					pettyCash.setVoucherType(saveExpArray.get("voucherType").getAsString());
					pettyCash.setAmount(amount);
					pettyCashList.add(pettyCash);
					psPettyCatInsert.executeUpdate();

				}else {
					count=psPettyCatInsert.executeBatch();
					ResultSet extractRecord= psPettyCatInsert.getGeneratedKeys();
					while(extractRecord.next()) {
						PettyCash pettDetails=new PettyCash();
						pettDetails.setId(extractRecord.getInt(1));
						selSql="SELECT cashexp.*,cash.name from petty_cash_expense cashexp INNER JOIN petty_cash_category cash ON " + 
								" cash.id=cashexp.category_id where cashexp.id='"+pettDetails.getId()+"' and cashexp.is_deleted=0";
						statement = connection.createStatement();
						ResultSet resPetty=statement.executeQuery(selSql);
						while(resPetty.next()) {
							pettDetails.setAmount(resPetty.getDouble("amount"));
							pettDetails.setCategoryId(resPetty.getInt("category_id"));
							pettDetails.setCategoryName(resPetty.getString("name"));
							pettDetails.setEntryDate(resPetty.getDate("entry_date"));
							pettDetails.setNarration(resPetty.getString("narration"));
							pettDetails.setVoucherType(resPetty.getString("voucher_name"));
							pettyCashList.add(pettDetails);
						}

					}


				}


				opening_bal=0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : pettyCashSave()" + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}

		return pettyCashList;
	}


	private double getOpening(String entryDate) throws ParseException {
		Connection connection=null;
		
		double opening=0.00;
		/*SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date entrydate = simpleDateformat.parse(entryDate);*/
		String selSql="select opening_balance from petty_cash_expense where entry_date='"+entryDate+"' AND is_deleted=0";
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			resultSet=statement.executeQuery(selSql);
			while(resultSet.next()) {
				opening=resultSet.getDouble("opening_balance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}

		return opening;
	}


	@Override
	public List<PettyCash> loadExpense() throws SQLException {
		Connection connection=null;
		
		/*String seleSql="SELECT cashexp.*,cash.name from petty_cash_expense cashexp INNER JOIN petty_cash_category cash ON " + 
				" cash.id=cashexp.category_id WHERE cashexp.is_deleted=0 GROUP BY cashexp.entry_date";*/
		/*String seleSql="SELECT cashexp.*,Max(cashexp.entry_date),cash.name from petty_cash_expense cashexp INNER JOIN petty_cash_category cash ON " + 
				" cash.id=cashexp.category_id WHERE cashexp.is_deleted=0  GROUP BY cashexp.entry_date ";*/

		String seleSql="SELECT " + 
				"    entry_date, " + 
				"    opening, " + 
				"		receipt," + 
				"    payment, " + 
				"   (SUM(IFNULL(opening,0)) + SUM(IFNULL(receipt,0))) - SUM(IFNULL(payment,0)) AS balance " + 
				"FROM " + 
				"(" + 
				"SELECT " + 
				"		entry_date, " + 
				"    getOpening(entry_date) AS opening, " + 
				"		( " + 
				"			SELECT " + 
				"				SUM(amount) " + 
				"			FROM " + 
				"				petty_cash_expense " + 
				"			WHERE " + 
				"				voucher_name = 'CONTRA' " + 
				"			AND entry_date = PCE.entry_date AND is_deleted = 0  " +   
				"		) AS receipt, " + 
				"    ( " + 
				"			SELECT " + 
				"				SUM(amount) " + 
				"			FROM " + 
				"				petty_cash_expense " + 
				"			WHERE " + 
				"				voucher_name = 'PAYMENT' " + 
				"			AND entry_date = PCE.entry_date AND is_deleted = 0 " + 
				"		) AS payment " + 
				"	FROM " + 
				"		petty_cash_expense PCE WHERE is_deleted = 0 " + 
				"GROUP BY entry_date) TBL " + 
				"GROUP BY entry_date"; 

		List<PettyCash> pettyList=new ArrayList<>();
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			resultSet=statement.executeQuery(seleSql);
			while(resultSet.next()) {
				PettyCash pettyCash=new PettyCash();
				pettyCash.setEntryDate(resultSet.getDate("entry_date"));
				pettyCash.setOpeningBalance(resultSet.getDouble("opening"));
				pettyCash.setReciept(resultSet.getDouble("receipt"));
				pettyCash.setPayment(resultSet.getDouble("payment"));
				pettyCash.setClosingBalance(resultSet.getDouble("balance"));
				/*pettyCash.setId(resultSet.getInt("id"));
				pettyCash.setAmount(resultSet.getDouble("amount"));
				pettyCash.setCategoryName(resultSet.getString("name"));
				pettyCash.setCategoryId(resultSet.getInt("category_id"));
				pettyCash.setEntryDate(resultSet.getDate("entry_date"));
				pettyCash.setVoucherType(resultSet.getString("voucher_name"));
				pettyCash.setNarration(resultSet.getString("narration"));*/
				pettyList.add(pettyCash);
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
		return pettyList;
	}


	@Override
	public String deleteExpense(String deletePetty) throws SQLException {
		Connection connection = null;
		
		
		
		String delSql="";
		String status="";
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObj = jsonParser.parse(deletePetty).getAsJsonObject();
		String jsoneDel=jsonObj.get("deletePetty").getAsString();
		JsonArray jsonDelete =(JsonArray) jsonParser.parse(jsoneDel);
		for(int i=0;i<jsonDelete.size();i++) {
			JsonObject jsonDelObj=(JsonObject) jsonDelete.get(i);
			delSql="update petty_cash_expense set is_deleted=1 where id='"+jsonDelObj.get("id").getAsInt()+"'";
			statement.executeUpdate(delSql);
			status="success";
		}
		}
		catch (SQLException e) {
				e.printStackTrace();
				throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}

		return status;
	}


	@Override
	public String pettyEdit(int id) {

		/*String updSql="";
		String status="";

			delSql="update petty_cash_expense set is_deleted=1 where id='"+jsonDelObj.get("id").getAsInt()+"'";
			statement.executeUpdate(delSql);
			status="success";
		}

		return status
		 */
		return null;
	}


	@Override
	public JsonArray pettyHeadDetails() {
		
		Connection connection = null;
		
		JsonArray categoryArray=new JsonArray();
		String selSql="SELECT  b.name AS parent_name, b.id as parent_id," + 
				"    a.id, a.name,a.description  " + 
				" FROM " + 
				"    petty_cash_category a" + 
				"    INNER JOIN petty_cash_category b" + 
				"        ON a.parent_id = b.id " + 
				" WHERE" + 
				"    b.parent_id =0 AND a.is_deleted=0";
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			resultSet=statement.executeQuery(selSql);
			while(resultSet.next()) {
				JsonObject categoryObj=new JsonObject();
				categoryObj.addProperty("id",resultSet.getInt("id"));
				categoryObj.addProperty("name",resultSet.getString("name"));
				categoryObj.addProperty("parent_name",resultSet.getString("parent_name"));
				categoryObj.addProperty("description",resultSet.getString("description"));
				categoryObj.addProperty("parentId",resultSet.getString("parent_id"));
				categoryArray.add(categoryObj);
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

		return categoryArray;
	}


	@Override
	public JsonArray loadHeadList(int id) {
		Connection connection = null;
		JsonArray categoryArray=new JsonArray();
		String selSql="SELECT  b.name AS parent_name, b.id as parent_id," + 
				"    a.id, a.name,a.description  " + 
				" FROM " + 
				"    petty_cash_category a" + 
				"    INNER JOIN petty_cash_category b" + 
				"        ON a.parent_id = b.id " + 
				" WHERE" + 
				"    b.parent_id =0 AND b.is_deleted=0 AND a.id="+id;
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			resultSet=statement.executeQuery(selSql);
			while(resultSet.next()) {
				JsonObject categoryObj=new JsonObject();
				categoryObj.addProperty("id",resultSet.getInt("id"));
				categoryObj.addProperty("name",resultSet.getString("name"));
				categoryObj.addProperty("parent_name",resultSet.getString("parent_name"));
				categoryObj.addProperty("description",resultSet.getString("description"));
				categoryObj.addProperty("parentId",resultSet.getString("parent_id"));
				categoryArray.add(categoryObj);
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

		return categoryArray;

	}


	@Override
	public String deletePettyHead(int id) throws SQLException {
		Connection connection = null;
		String delSql="";
		String status="";
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			delSql="update petty_cash_category set is_deleted=1 where id="+id;
			statement.executeUpdate(delSql);
			status="success";
		} catch (SQLException e) {
			status="failed";
			e.printStackTrace();
		}	
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
		}
		return status;
	}

	@Override
	public List<PettyCash>  searchExpense(String dateValue) {
		
		Connection connection = null;
		
		
		String seleSql="SELECT * from(SELECT " + 
				"    entry_date, " + 
				"    opening, " + 
				"		receipt," + 
				"    payment, " + 
				"    (SUM(IFNULL(opening,0)) + SUM(IFNULL(receipt,0))) - SUM(IFNULL(payment,0)) AS balance " + 
				"FROM " + 
				"(" + 
				"SELECT " + 
				"		entry_date, " + 
				"    getOpening(entry_date) AS opening, " + 
				"		( " + 
				"			SELECT " + 
				"				SUM(amount) " + 
				"			FROM " + 
				"				petty_cash_expense " + 
				"			WHERE " + 
				"				voucher_name = 'CONTRA' " + 
				"			AND entry_date = PCE.entry_date AND is_deleted = 0 " +
//				    "AND opening_balance = 0 " +
				"		) AS receipt, " + 
				"    ( " + 
				"			SELECT " + 
				"				SUM(amount) " + 
				"			FROM " + 
				"				petty_cash_expense " + 
				"			WHERE " + 
				"				voucher_name = 'PAYMENT' " + 
				"			AND entry_date = PCE.entry_date AND is_deleted = 0 " + 
				"		) AS payment " + 
				"	FROM " + 
				"		petty_cash_expense PCE WHERE is_deleted = 0 " + 
				"GROUP BY entry_date) TBL " + 
				"GROUP BY entry_date)as t2 where entry_date = '"+dateValue+"'"; 

		List<PettyCash> pettyList=new ArrayList<>();
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			resultSet=statement.executeQuery(seleSql);
			while(resultSet.next()) {
				PettyCash pettyCash=new PettyCash();
				pettyCash.setEntryDate(resultSet.getDate("entry_date"));
				pettyCash.setOpeningBalance(resultSet.getDouble("opening"));
				pettyCash.setReciept(resultSet.getDouble("receipt"));
				pettyCash.setPayment(resultSet.getDouble("payment"));
				pettyCash.setClosingBalance(resultSet.getDouble("balance"));
				/*pettyCash.setId(resultSet.getInt("id"));
				pettyCash.setAmount(resultSet.getDouble("amount"));
				pettyCash.setCategoryName(resultSet.getString("name"));
				pettyCash.setCategoryId(resultSet.getInt("category_id"));
				pettyCash.setEntryDate(resultSet.getDate("entry_date"));
				pettyCash.setVoucherType(resultSet.getString("voucher_name"));
				pettyCash.setNarration(resultSet.getString("narration"));*/
				pettyList.add(pettyCash);
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
		return pettyList;
	}

	@Override
	public JsonArray editExpense(String dateValue) {
		Connection connection = null;
		JsonArray expenseArray=new JsonArray();
		String selSql="SELECT cashexp.*,cash.name from petty_cash_expense cashexp INNER JOIN petty_cash_category cash ON " + 
				" cash.id=cashexp.category_id WHERE cashexp.is_deleted=0 AND entry_date='"+dateValue+"'";
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			resultSet=statement.executeQuery(selSql);
			while(resultSet.next()) {
				JsonObject categoryObj=new JsonObject();
				categoryObj.addProperty("id",resultSet.getInt("id"));
				/*categoryObj.addProperty("openingBalance", resultSet.getBigDecimal("opening_balance"));*/
				categoryObj.addProperty("categoryId",resultSet.getInt("category_id"));
				categoryObj.addProperty("categoryName",resultSet.getString("name"));
				categoryObj.addProperty("amount",resultSet.getString("amount"));
				categoryObj.addProperty("entryDate",resultSet.getString("entry_date"));
				categoryObj.addProperty("voucherType",resultSet.getString("voucher_name"));
				categoryObj.addProperty("narration",resultSet.getString("narration"));
				expenseArray.add(categoryObj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}

		return expenseArray;
	}


	@Override
	public JsonArray searchCategory(String searchCritrea) {
		Connection connection = null;
		
		JsonArray categoryArray=new JsonArray();
		String selSql="SELECT  b.name AS parent_name, b.id as parent_id," + 
				"    a.id, a.name,a.description  " + 
				" FROM " + 
				"    petty_cash_category a" + 
				"    INNER JOIN petty_cash_category b" + 
				"        ON a.parent_id = b.id " + 
				" WHERE" + 
				"    b.parent_id =0 AND a.is_deleted=0 AND a.`name` LIKE '"+searchCritrea+"%'";
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			resultSet=statement.executeQuery(selSql);
			while(resultSet.next()) {
				JsonObject categoryObj=new JsonObject();
				categoryObj.addProperty("id",resultSet.getInt("id"));
				categoryObj.addProperty("name",resultSet.getString("name"));
				categoryObj.addProperty("parent_name",resultSet.getString("parent_name"));
				categoryObj.addProperty("description",resultSet.getString("description"));
				categoryObj.addProperty("parentId",resultSet.getString("parent_id"));
				categoryArray.add(categoryObj);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(resultSet);
			dbConnection.releaseResource(connection);
		}
		return categoryArray;
	}


	@Override
	public String updateOpening(double opening) throws SQLException {
		Connection connection = null;
		String status="";
		try {
			connection = dbConnection.getConnection();
			statement = connection.createStatement();
			String updSql="update petty_cash_expense SET opening_balance='"+opening+"' where id="+UNIQUE_ID;
			statement.executeUpdate(updSql);
			status="success";
		}catch(Exception e) {
			e.printStackTrace();
			throw new CustomException();
		}
		finally {
			dbConnection.releaseResource(statement);
			dbConnection.releaseResource(connection);
		}

		return status;
	}


	@Override
	public String deleteExpenseRow(String id) throws SQLException {
		Connection connection = null;
			
			String delSql="";
			String status="";
			/*JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = jsonParser.parse(deletePetty).getAsJsonObject();
			String jsoneDel=jsonObj.get("deletePetty").getAsString();
			JsonArray jsonDelete =(JsonArray) jsonParser.parse(jsoneDel);
			for(int i=0;i<jsonDelete.size();i++) {*/
				//JsonObject jsonDelObj=(JsonObject) jsonDelete.get(i);
			try {
				connection = dbConnection.getConnection();
				statement = connection.createStatement();
				delSql="update petty_cash_expense set is_deleted=1 where id="+id;
				statement.executeUpdate(delSql);
				status="success";
			//}
			}
			catch(Exception e) {
				e.printStackTrace();
				throw new CustomException();
			}
			finally {
					dbConnection.releaseResource(statement);
					dbConnection.releaseResource(connection);
				}
			return status;
		}
	

}
