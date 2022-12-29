package com.indocosmo.pms.web.ota.service.finance;

import java.util.List;

import com.indocosmo.pms.web.ota.dto.finance.OTABillsDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTAFinancialAccountsDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTAHotelExpensesDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTARevenuesDTO;
import com.indocosmo.pms.web.ota.dto.finance.OTAoutinwDTO;
import com.indocosmo.pms.web.ota.entity.finance.OTAExtras;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;

public interface OTAFinanceServiceInterface {
	
	public List<OTAExtras> getRetrieveExtras(HotelInfo hotel);
	
	public List<OTAExtras> getRetrieveExtrasFromDB();
	
	public List<OTAHotelExpensesDTO> getRetrievehotelexpenses(HotelInfo hotel, String fromdate, String todate);
	
	public List<OTABillsDTO> getRetrievebills(HotelInfo hotel, String fromdate, String todate);
	
	public List<OTAFinancialAccountsDTO> getRetrievefinancialaccounts(HotelInfo hotel);
	
	public List<OTARevenuesDTO> getRetrieverevenues(HotelInfo hotel, String fromdate, String todate);
	
	public List<OTAoutinwDTO> getRetrieveoutwardspayments(HotelInfo hotel, String fromdate, String todate) ;
	
	public List<OTAoutinwDTO> getRetrieveinwardspayments(HotelInfo hotel,String fromdate, String todate);
	
	public List<OTAoutinwDTO> getRetrievejournals(HotelInfo hotel,String fromdate, String todate);
	
	public List<OTAoutinwDTO> getRetrieveincidentalinvoices(HotelInfo hotel,String fromdate, String todate) ;
	
}
