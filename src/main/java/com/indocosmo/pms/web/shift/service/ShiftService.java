package com.indocosmo.pms.web.shift.service;

import java.util.List;

import com.indocosmo.pms.web.shift.model.Shift;

public interface ShiftService {

	public List<Shift> getShiftList();

	public boolean save(Shift shift) throws Exception;

	public boolean delete(int id);

}
