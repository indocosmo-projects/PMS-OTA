package com.indocosmo.pms.web.pettycash.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.indocosmo.pms.web.pettycash.dao.PettyCashDao;
import com.indocosmo.pms.web.pettycash.model.PettyCash;

@Service
public class PettyCashServiceImp implements PettyCashService{

	@Autowired
	PettyCashDao pettyCashDao;
	
	@Override
	public Double openingBalance(String hotelDate) throws ParseException, SQLException {		
		return pettyCashDao.openingBalance(hotelDate);
	}

	@Override
	public String saveCategory(String saveJson) {
		return pettyCashDao.saveCategory(saveJson);
	}

	@Override
	public JsonArray loadCategory() {
		return pettyCashDao.loadCategory();
	}

	@Override
	public JsonArray loadHead() {
		return pettyCashDao.loadHead();
	}

	@Override
	public List<PettyCash> saveExpense(String saveExpense) throws Exception {
		return pettyCashDao.saveExpense(saveExpense);
	}

	@Override
	public List<PettyCash> loadExpense() throws SQLException {
		return pettyCashDao.loadExpense();
	}

	@Override
	public String deleteExpense(String deletePetty) throws SQLException {
		return pettyCashDao.deleteExpense(deletePetty);
	}

	@Override
	public String pettyEdit(int id) {
		return pettyCashDao.pettyEdit(id);
	}

	@Override
	public JsonArray pettyHeadDetails() {
		return pettyCashDao.pettyHeadDetails();
	}

	@Override
	public JsonArray loadHeadList(int id) {
		return pettyCashDao.loadHeadList(id);
	}

	@Override
	public String deletePettyHead(int id) throws SQLException {
		return pettyCashDao.deletePettyHead(id);
	}

	@Override
	public JsonArray editExpense(String dateValue) {
		return pettyCashDao.editExpense(dateValue);
	}

	@Override
	public List<PettyCash>  searchExpense(String dateValue) {
		return pettyCashDao.searchExpense(dateValue);
	}
	
	
	@Override
	public JsonArray searchCategory(String searchCritrea) {
		return pettyCashDao.searchCategory(searchCritrea);
	}

	@Override
	public String updateOpening(double opening) throws SQLException {
		return pettyCashDao.updateOpening(opening);
	}

	@Override
	public String deleteExpenseRow(String id) throws SQLException {
		return pettyCashDao.deleteExpenseRow(id);
	}

}
