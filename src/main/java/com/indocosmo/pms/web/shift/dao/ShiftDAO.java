package com.indocosmo.pms.web.shift.dao;

import java.util.List;

import com.indocosmo.pms.web.shift.model.Shift;

public interface ShiftDAO {

	public List<Shift> getShiftList();

	public Shift getShift(int id);

	public boolean save(Shift shift) throws Exception;

	public boolean delete(int id);

}
