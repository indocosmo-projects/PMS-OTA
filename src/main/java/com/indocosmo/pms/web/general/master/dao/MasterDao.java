package com.indocosmo.pms.web.general.master.dao;

import java.util.List;
import java.util.Map;

public interface MasterDao<T> {

	public boolean codeExist(String code)throws Exception;
	
	public T getRecord(int id)throws Exception;
	
	public int getCount(Map<String,String>advanceSearchMap,Map<String,String>simpleSearchMap)throws Exception;
	
	public boolean save(T object)throws Exception;
	
	public boolean delete(int id)throws Exception;
	
	public List<T> list(int startLimit,int endLimit,Map<String,String> advanceSearch,String sortVal,Map<String, String>simpleSearchMap)throws Exception;
	
}
