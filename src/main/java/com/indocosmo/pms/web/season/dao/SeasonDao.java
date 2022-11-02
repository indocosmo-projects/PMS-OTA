package com.indocosmo.pms.web.season.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.indocosmo.pms.web.general.master.dao.MasterDao;
import com.indocosmo.pms.web.season.model.Seasonhdr;

public interface SeasonDao extends MasterDao<Seasonhdr> {
	public boolean newSeason(Seasonhdr ism);
	public int totalRecord(Map<String,String>searchContent,Map<String,String>simpleSearchMap);
	public boolean getDates(String startDate,String endDate,int id);
	public ArrayList<ArrayList<String>> getSeasonNamesWithId();
	public List<Seasonhdr> seasonList() throws Exception;
	public List<Seasonhdr> getSeasonDtl(String filter_query);
}
