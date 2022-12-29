package com.indocosmo.pms.web.ota.controller.finance;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indocosmo.pms.web.ota.dto.finance.OTABillsDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTAFinancialAccountsDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTAHotelExpensesDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTARevenuesDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTAoutinwDTO;
import com.indocosmo.pms.web.ota.entity.finance.OTAExtras;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.service.finance.OTAFinanceServiceImpl;

@RestController
@RequestMapping(value = "/otafinance")
public class OTAFinanceController {
	
	@Autowired
	private OTAFinanceServiceImpl otafinanceserviceimpl;

	
	
	@RequestMapping(value = "/otaretrieveextras", method = RequestMethod.GET)
	public List<OTAExtras> getRetrieveExtras(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAExtras> extras = otafinanceserviceimpl.getRetrieveExtras(hotel);
		
		return extras;
	}
	
	
	@RequestMapping(value = "/otaretrieveextrasFromDB", method = RequestMethod.GET)
	public List<OTAExtras> getRetrieveExtrasFromDB(HttpSession session) throws Exception{
		
		List<OTAExtras> extras = otafinanceserviceimpl.getRetrieveExtrasFromDB();
		return extras;
		
	}

	
	@RequestMapping(value = "/otaretrievehotelexpenses", method = RequestMethod.POST)
	public List<OTAHotelExpensesDTO> getRetrievehotelexpenses(
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAHotelExpensesDTO> hotelexpenses = otafinanceserviceimpl.getRetrievehotelexpenses(hotel,fromdate,todate);
		
		return hotelexpenses;
		
	}
	
	
	@RequestMapping(value = "/otaretrievebills", method = RequestMethod.POST)
	public List<OTABillsDTO> getRetrievebills(
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTABillsDTO> bills = otafinanceserviceimpl.getRetrievebills(hotel,fromdate,todate);
		return bills;
		
	}
	
	
	
	@RequestMapping(value = "/otaretrievefinancialaccounts", method = RequestMethod.GET)
	public List<OTAFinancialAccountsDTO> getRetrievefinancialaccounts(HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAFinancialAccountsDTO> otafinancialaccountsDTO = otafinanceserviceimpl.getRetrievefinancialaccounts(hotel);
		
		return otafinancialaccountsDTO;
		
	}
	
	
	@RequestMapping(value = "/otaretrieverevenues", method = RequestMethod.POST)
	public List<OTARevenuesDTO> getRetrieverevenues(
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTARevenuesDTO> otarevenuesdto = otafinanceserviceimpl.getRetrieverevenues(hotel,fromdate,todate);
		
		return otarevenuesdto;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveoutwardspayments", method = RequestMethod.POST)
	public List<OTAoutinwDTO> getRetrieveoutwardspayments(
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAoutinwDTO> otaoutinwdto = otafinanceserviceimpl.getRetrieveoutwardspayments(hotel,fromdate,todate);
		
		return otaoutinwdto;
		
	}
	
	
	@RequestMapping(value = "/otaretrieveinwardspayments", method = RequestMethod.POST)
	public List<OTAoutinwDTO> getRetrieveinwardspayments(
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAoutinwDTO> otaoutinwdto = otafinanceserviceimpl.getRetrieveinwardspayments(hotel,fromdate,todate);
		
		return otaoutinwdto;
		
	}
	
	

	@RequestMapping(value = "/otaretrievejournals", method = RequestMethod.POST)
	public List<OTAoutinwDTO> getRetrievejournals(
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAoutinwDTO> otaoutinwdto = otafinanceserviceimpl.getRetrievejournals(hotel,fromdate,todate);
		
		return otaoutinwdto;
		
	}
	
	
	
	@RequestMapping(value = "/otaretrieveincidentalinvoices", method = RequestMethod.POST)
	public List<OTAoutinwDTO> getRetrieveincidentalinvoices(
			@RequestParam String fromdate,@RequestParam String todate,
			HttpSession session) throws Exception{
		
		HotelInfo hotel = (HotelInfo) session.getAttribute("hotel");
		List<OTAoutinwDTO> otaoutinwdto = otafinanceserviceimpl.getRetrieveincidentalinvoices(hotel,fromdate,todate);
		
		return otaoutinwdto;
		
	}
	
	


	
}
