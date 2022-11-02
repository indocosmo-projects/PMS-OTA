package com.indocosmo.pms.web.shift.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indocosmo.pms.web.shift.dao.ShiftDAO;
import com.indocosmo.pms.web.shift.model.Shift;

@Service
public class ShiftServiceImpl implements ShiftService {

	@Autowired
	ShiftDAO shiftDAO;

	@Transactional
	public List<Shift> getShiftList() {
		return shiftDAO.getShiftList();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean save(Shift shift) throws Exception {
		return shiftDAO.save(shift);
	}

	@Transactional
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return shiftDAO.delete(id);
	}

}
