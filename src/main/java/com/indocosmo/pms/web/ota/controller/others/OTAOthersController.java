package com.indocosmo.pms.web.ota.controller.others;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indocosmo.pms.web.ota.dto.housekeeping.OTAHouseStatusDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.others.OTACompanies;
import com.indocosmo.pms.web.ota.entity.others.OTAGuestStatics;
import com.indocosmo.pms.web.ota.service.others.OTAOthersServiceImpl;

@RestController
@RequestMapping(value = "/otaothers")
public class OTAOthersController {
	
	@Autowired
	private OTAOthersServiceImpl otaothersserviceimpl;
	
	
	@RequestMapping(value = "/otaretrievegueststatics", method = RequestMethod.GET)
	public List<OTAGuestStatics> getRetrievegueststatics(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAGuestStatics> otagueststatics = otaothersserviceimpl.getRetrievegueststatics(hotel);
		
		return otagueststatics;
		
	}
	
	
	@RequestMapping(value = "/otaretrievegueststaticsFromDB", method = RequestMethod.GET)
	public List<OTAGuestStatics> getRetrievegueststaticsFromDB(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAGuestStatics> otagueststatics = otaothersserviceimpl.getRetrievegueststaticsFromDB();
		
		return otagueststatics;
		
	}
	
	
	@RequestMapping(value = "/otaretrievecompany", method = RequestMethod.POST)
	public List<OTACompanies> getretrievecompany(@RequestParam String[] ids,@RequestParam String[] names,
			@RequestParam String createdfromdate,@RequestParam String createdtodate,@RequestParam String updatedfromdate,
			@RequestParam String updatedtodate,@RequestParam String isactive,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTACompanies> otacompanies = otaothersserviceimpl.getretrievecompany(hotel, ids, names, createdfromdate, createdtodate, updatedfromdate, updatedtodate, isactive);		
		return otacompanies;
		
	}
	

}
