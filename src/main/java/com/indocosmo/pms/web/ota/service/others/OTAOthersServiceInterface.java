package com.indocosmo.pms.web.ota.service.others;

import java.util.List;

import com.indocosmo.pms.web.ota.dto.housekeeping.OTAHouseStatusDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.others.OTACompanies;
import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;

public interface OTAOthersServiceInterface {

	public List<OTAGuestStatics> getRetrievegueststatics(HotelInfo hotel);
	
	public List<OTACompanies> getretrievecompany(HotelInfo hotel,String[] ids,String[] names,String createdfromdate,
			String createdtodate,String updatedfromdate,String updatedtodate,String isactive);
	
	public List<OTAGuestStatics> getRetrievegueststaticsFromDB();
	
	public List<OTACompanies> getotaretrievetravelagent(HotelInfo hotel,String[] id,String[] name,String createdfromdate,
			String createdtodate,String updatedfromdate,String updatedtodate,String isactive);
	
	
	public String getCreatetravelagent(HotelInfo hotel,String user,String businessname,
			String country,String email,String percentdiscount);
	
	public List<OTACompanies> getGuestList(HotelInfo hotel);
}
