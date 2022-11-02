package com.indocosmo.pms.web.season.service;

import java.util.ArrayList;
import java.util.List;
import com.indocosmo.pms.web.general.master.service.MasterService;
import com.indocosmo.pms.web.season.model.Seasonhdr;

public interface SeasonHdrService extends MasterService<Seasonhdr> {
	public boolean getDates(String startDate, String endDate, int id);

	public ArrayList<ArrayList<String>> getSeasonNamesWithId();

	public List<Seasonhdr> seasonList() throws Exception;

	public List<Seasonhdr> getSeasonDtl(String filter_query);
}