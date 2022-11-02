package com.indocosmo.pms.web.pettycash.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.pettycash.model.PettyCash;

public interface PettyCashService {

	Double openingBalance(String hotelDate) throws ParseException, SQLException;

	String saveCategory(String saveJson);

	JsonArray loadCategory();

	JsonArray loadHead();

	List<PettyCash> saveExpense(String saveExpense) throws Exception;

	List<PettyCash> loadExpense() throws SQLException;

	String deleteExpense(String deletePetty) throws SQLException;

	String pettyEdit(int id);

	JsonArray pettyHeadDetails();

	JsonArray loadHeadList(int id);

	String deletePettyHead(int id) throws SQLException;

	JsonArray editExpense(String dateValue);
	
	List<PettyCash> searchExpense(String dateValue);

	JsonArray searchCategory(String searchCritrea);

	String updateOpening(double opening) throws SQLException;

	String deleteExpenseRow(String id) throws SQLException;

}
