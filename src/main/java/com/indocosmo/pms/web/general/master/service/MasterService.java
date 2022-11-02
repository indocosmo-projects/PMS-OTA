package com.indocosmo.pms.web.general.master.service;

import java.util.Map;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;


public interface MasterService<T> {
	
	public boolean codeExist(String code)throws Exception;
	
	public T getRecord(int id)throws Exception;
	
	public boolean save(T object)throws Exception;
	
	public int getCount(Map<String,String> advanceSearchMap,Map<String,String> simpleSearchMap)throws Exception;
	
	public boolean delete(int id)throws Exception;
	
	public JqGridListWrapperDTO list(String currentPage, String rowLimit, String pagingStart,
			Map<String, String> advanceSearchMap, String sortVal,Map<String, String> simpleSearchMap)throws Exception;
	
}
