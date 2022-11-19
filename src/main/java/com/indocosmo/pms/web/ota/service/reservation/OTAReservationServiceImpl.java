package com.indocosmo.pms.web.ota.service.reservation;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.ota.dao.reservation.OTABookingTransDaoImpl;
import com.indocosmo.pms.web.ota.dao.reservation.OTACancelReservationDaoImpl;
import com.indocosmo.pms.web.ota.dao.reservation.OTARentalInfoDaoImpl;
import com.indocosmo.pms.web.ota.dao.reservation.OTAReservationDaoImpl;
import com.indocosmo.pms.web.ota.dao.reservation.OTATaxDeatilDaoImpl;
import com.indocosmo.pms.web.ota.dao.room.OTARoomRatePlansDao;
import com.indocosmo.pms.web.ota.dao.room.OTARoomRateTypesDao;
import com.indocosmo.pms.web.ota.dao.room.OTARoomRoomTypesDao;
import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTAReservationDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomDetailsDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInfoDTO;
import com.indocosmo.pms.web.ota.dto.reservation.OTARoomInventoryDTO;
import com.indocosmo.pms.web.ota.entity.hotel.HotelInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTABookingTrans;
import com.indocosmo.pms.web.ota.entity.reservation.OTACancelReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTARentalInfo;
import com.indocosmo.pms.web.ota.entity.reservation.OTAReservation;
import com.indocosmo.pms.web.ota.entity.reservation.OTATaxDeatil;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRatePlans;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRateTypes;
import com.indocosmo.pms.web.ota.entity.room.OTARoomRoomTypes;
import com.indocosmo.pms.web.ota.service.common.OnlineTravelAgentServiceImpl;

@Service
public class OTAReservationServiceImpl implements OTAReservationServiceInterface{
	
	@Autowired
	private OnlineTravelAgentServiceImpl onlineTravelAgentServiceImpl;
	
	@Autowired
	private OTAReservationDaoImpl otareservationDaoImpl;
	
	@Autowired
	private OTARentalInfoDaoImpl otarentalInfoDaoImpl;
	
	@Autowired
	private OTATaxDeatilDaoImpl otataxdeatilDaoImpl;
	
	@Autowired
	private OTABookingTransDaoImpl otabookingtransDaoImpl;
	
	@Autowired
	private OTACancelReservationDaoImpl otacancelreservationDaoImpl;
	
	@Autowired
	private OTARoomRatePlansDao otaroomrateplansdao;
	
	@Autowired
	private OTARoomRateTypesDao otaroomratetypesDao;
	
	@Autowired
	private OTARoomRoomTypesDao otaroomroomtypesdao;

	@Override
	public OTAReservationDTO getRetrieveAll(HotelInfo hotel) throws Exception {
		
		List<OTAReservation> resrvationList = otareservationDaoImpl.getAllRecords();
		List<OTABookingTrans> bookingtransList = otabookingtransDaoImpl.getAllRecords();
		List<OTARentalInfo> rentalinfoList = otarentalInfoDaoImpl.getAllRecords();
		List<OTATaxDeatil> taxdetailList = otataxdeatilDaoImpl.getAllRecords();
		List<OTACancelReservation> cancelreservationlist = otacancelreservationDaoImpl.getAllRecords();
		
		int o;
			
		o = otareservationDaoImpl.deleteAll(resrvationList.size());
		o = otacancelreservationDaoImpl.deleteAll(cancelreservationlist.size());
		o = otabookingtransDaoImpl.deleteAll(bookingtransList.size());
		o = otarentalInfoDaoImpl.deleteAll(rentalinfoList.size());
		o = otataxdeatilDaoImpl.deleteAll(taxdetailList.size());
		
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "Bookings");
		payload.put("Authentication", auth);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		
		resrvationList = new ArrayList<OTAReservation>();
		bookingtransList = new ArrayList<OTABookingTrans>();
		rentalinfoList = new ArrayList<OTARentalInfo>();
		taxdetailList = new ArrayList<OTATaxDeatil>();
		cancelreservationlist = new ArrayList<OTACancelReservation>();
		
		JsonObject jobj = jsonobject.get("Reservations").getAsJsonObject();
		JsonArray jarr = jobj.get("Reservation").getAsJsonArray();
		
		JsonArray cancelreservationarr = jobj.get("CancelReservation").getAsJsonArray();
		cancelreservationlist = tolistCancelReservation(cancelreservationarr);  
		
		int count = resrvationList.size();
		for (int i = 0; i < jarr.size(); i++) { 
			JsonObject  obj = jarr.get(i).getAsJsonObject();
			
			try {
			
			count = count + 1;
			OTAReservation otareservation = new OTAReservation();

			otareservation.setLocationid(obj.get("LocationId").getAsString());
			otareservation.setUniquereservationid(obj.get("UniqueID").getAsInt());
			otareservation.setBookedby(obj.get("BookedBy").getAsString());
			otareservation.setSalutation(obj.get("Salutation").getAsString());
			otareservation.setFirstname(obj.get("FirstName").getAsString()+obj.get("LastName").getAsString());
			otareservation.setGender(obj.get("Address").getAsString());
			otareservation.setCity(obj.get("City").getAsString());
			otareservation.setState(obj.get("State").getAsString());
			otareservation.setCountry(obj.get("Country").getAsString());
			otareservation.setZipcode(obj.get("Zipcode").getAsString());
			otareservation.setPhone(obj.get("Phone").getAsString());
			otareservation.setMobile(obj.get("Mobile").getAsString());
			otareservation.setFax(obj.get("Fax").getAsString());
			otareservation.setEmail(obj.get("Email").getAsString());
			otareservation.setRegistrationno(obj.get("RegistrationNo").getAsString());
			otareservation.setSource(obj.get("Source").getAsString());
			otareservation.setIschannelbooking(obj.get("IsChannelBooking").getAsString());
			otareservation.setIsdeleted(0);
			
			JsonArray  BookingTransarr = obj.get("BookingTran").getAsJsonArray();
			OTAReservationDTO otaeservationDTO= tolistBookingTrans(BookingTransarr, otareservation.getUniquereservationid());	
			
			resrvationList.add(otareservation);
			bookingtransList.addAll(otaeservationDTO.getOtabookingtrans());
			rentalinfoList.addAll(otaeservationDTO.getOtarentalinfo());
			taxdetailList.addAll(otaeservationDTO.getOtataxdeatil());
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	
		}
		
		try {
		
		int c = 0;
		for(OTAReservation reselist : resrvationList) {
			c++;
			reselist.setId(c);
			o = otareservationDaoImpl.save(reselist);
		}
		
		c = 0;
		for(OTABookingTrans booktrans : bookingtransList) {
			c = c + 1;
			booktrans.setId(c);
			o = otabookingtransDaoImpl.save(booktrans);
		}
		
		c = 0;
		for(OTARentalInfo rentalinfo : rentalinfoList) {
			c = c + 1;
			rentalinfo.setId(c);
			o = otarentalInfoDaoImpl.save(rentalinfo);
		}
		
		c = 0;
		for(OTATaxDeatil txndetail : taxdetailList) {
			c = c + 1;
			txndetail.setId(c);
			o = otataxdeatilDaoImpl.save(txndetail);
		}
		
		c = 0;
		for(OTACancelReservation cancelrese : cancelreservationlist) {
			c = c + 1;
			cancelrese.setId(c);
			o = otacancelreservationDaoImpl.save(cancelrese);
		}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		OTAReservationDTO ota = new OTAReservationDTO();
		ota.setOtabookingtrans(bookingtransList);
		ota.setOtacancelreservation(cancelreservationlist);
		ota.setOtarentalinfo(rentalinfoList);
		ota.setOtareservation(resrvationList);
		ota.setOtataxdeatil(taxdetailList);
		
		return ota;
	}
	
	
	
	@Override
	public OTAReservationDTO getRetrieveAllNewReservation(HotelInfo hotel) throws Exception {
		
		List<OTAReservation> resrvationList = otareservationDaoImpl.getAllRecords();
		List<OTABookingTrans> bookingtransList = otabookingtransDaoImpl.getAllRecords();
		List<OTARentalInfo> rentalinfoList = otarentalInfoDaoImpl.getAllRecords();
		List<OTATaxDeatil> taxdetailList = otataxdeatilDaoImpl.getAllRecords();
		List<OTACancelReservation> cancelreservationlist = otacancelreservationDaoImpl.getAllRecords();
		
		
		List<OTAReservation> resrvationListDB = otareservationDaoImpl.getAllRecords();
		List<OTABookingTrans> bookingtransListDB = otabookingtransDaoImpl.getAllRecords();
		List<OTARentalInfo> rentalinfoListDB = otarentalInfoDaoImpl.getAllRecords();
		List<OTATaxDeatil> taxdetailListDB = otataxdeatilDaoImpl.getAllRecords();
		List<OTACancelReservation> cancelreservationlistDB = otacancelreservationDaoImpl.getAllRecords();
		
		int o;
			
		o = otareservationDaoImpl.deleteAll(resrvationList.size());
		o = otacancelreservationDaoImpl.deleteAll(cancelreservationlist.size());
		o = otabookingtransDaoImpl.deleteAll(bookingtransList.size());
		o = otarentalInfoDaoImpl.deleteAll(rentalinfoList.size());
		o = otataxdeatilDaoImpl.deleteAll(taxdetailList.size());
		
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "Bookings");
		payload.put("Authentication", auth);
		request.put("RES_Request", payload);
		String json =  request.toString();
		
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
		JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
		
		resrvationList = new ArrayList<OTAReservation>();
		bookingtransList = new ArrayList<OTABookingTrans>();
		rentalinfoList = new ArrayList<OTARentalInfo>();
		taxdetailList = new ArrayList<OTATaxDeatil>();
		cancelreservationlist = new ArrayList<OTACancelReservation>();
		
		JsonObject jobj = jsonobject.get("Reservations").getAsJsonObject();
		JsonArray jarr = jobj.get("Reservation").getAsJsonArray();
		
		JsonArray cancelreservationarr = jobj.get("CancelReservation").getAsJsonArray();
		cancelreservationlist = tolistCancelReservation(cancelreservationarr);  
		
		int count = resrvationList.size();
		for (int i = 0; i < jarr.size(); i++) { 
			JsonObject  obj = jarr.get(i).getAsJsonObject();
			
			try {
			
			count = count + 1;
			OTAReservation otareservation = new OTAReservation();

			otareservation.setLocationid(obj.get("LocationId").getAsString());
			otareservation.setUniquereservationid(obj.get("UniqueID").getAsInt());
			otareservation.setBookedby(obj.get("BookedBy").getAsString());
			otareservation.setSalutation(obj.get("Salutation").getAsString());
			otareservation.setFirstname(obj.get("FirstName").getAsString()+obj.get("LastName").getAsString());
			otareservation.setGender(obj.get("Address").getAsString());
			otareservation.setCity(obj.get("City").getAsString());
			otareservation.setState(obj.get("State").getAsString());
			otareservation.setCountry(obj.get("Country").getAsString());
			otareservation.setZipcode(obj.get("Zipcode").getAsString());
			otareservation.setPhone(obj.get("Phone").getAsString());
			otareservation.setMobile(obj.get("Mobile").getAsString());
			otareservation.setFax(obj.get("Fax").getAsString());
			otareservation.setEmail(obj.get("Email").getAsString());
			otareservation.setRegistrationno(obj.get("RegistrationNo").getAsString());
			otareservation.setSource(obj.get("Source").getAsString());
			otareservation.setIschannelbooking(obj.get("IsChannelBooking").getAsString());
			otareservation.setIsdeleted(0);
			
			JsonArray  BookingTransarr = obj.get("BookingTran").getAsJsonArray();
			OTAReservationDTO otaeservationDTO= tolistBookingTrans(BookingTransarr, otareservation.getUniquereservationid());	
			
			resrvationList.add(otareservation);
			bookingtransList.addAll(otaeservationDTO.getOtabookingtrans());
			rentalinfoList.addAll(otaeservationDTO.getOtarentalinfo());
			taxdetailList.addAll(otaeservationDTO.getOtataxdeatil());
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	
		}
		
		try {
		
		int c = 0;
		for(OTAReservation reselist : resrvationList) {
			c++;
			reselist.setId(c);
			o = otareservationDaoImpl.save(reselist);
		}
		
		c = 0;
		for(OTABookingTrans booktrans : bookingtransList) {
			c = c + 1;
			booktrans.setId(c);
			o = otabookingtransDaoImpl.save(booktrans);
		}
		
		c = 0;
		for(OTARentalInfo rentalinfo : rentalinfoList) {
			c = c + 1;
			rentalinfo.setId(c);
			o = otarentalInfoDaoImpl.save(rentalinfo);
		}
		
		c = 0;
		for(OTATaxDeatil txndetail : taxdetailList) {
			c = c + 1;
			txndetail.setId(c);
			o = otataxdeatilDaoImpl.save(txndetail);
		}
		
		c = 0;
		for(OTACancelReservation cancelrese : cancelreservationlist) {
			c = c + 1;
			cancelrese.setId(c);
			o = otacancelreservationDaoImpl.save(cancelrese);
		}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resrvationList.removeAll(resrvationListDB);
		bookingtransList.removeAll(bookingtransListDB);
		cancelreservationlist.removeAll(cancelreservationlistDB);
		rentalinfoList.removeAll(rentalinfoListDB);
		taxdetailList.removeAll(taxdetailListDB);
		
		OTAReservationDTO ota = new OTAReservationDTO();
		ota.setOtabookingtrans(bookingtransList);
		ota.setOtacancelreservation(cancelreservationlist);
		ota.setOtarentalinfo(rentalinfoList);
		ota.setOtareservation(resrvationList);
		ota.setOtataxdeatil(taxdetailList);
		
		return ota;
	}
	
	
	@Override
	public OTAReservationDTO tolistBookingTrans(JsonArray BookingTransarr, int reservationId) {
		
		OTAReservationDTO otareservationdto = new OTAReservationDTO();
		List<OTABookingTrans> otabookingTransList = new ArrayList<OTABookingTrans>();
		List<OTARentalInfo> otarental = new ArrayList<OTARentalInfo>();
		List<OTATaxDeatil> otataxdeatil = new ArrayList<OTATaxDeatil>();
		
		JsonObject  otabooking = null;
		JsonArray  otabookingarr = null;
		for (int i = 0; i < BookingTransarr.size(); i++) { 
		
		try {
			otabooking = BookingTransarr.get(i).getAsJsonObject();
	
		OTABookingTrans otabookingtrans = new OTABookingTrans();
		
		otabookingtrans.setSubbookingid(otabooking.get("SubBookingId").getAsString());
		otabookingtrans.setReservationid(reservationId);
		otabookingtrans.setTransactionid(otabooking.get("TransactionId").getAsString());
		otabookingtrans.setCreatedatetime(otabooking.get("Createdatetime").getAsString());
		otabookingtrans.setModifydatetime(otabooking.get("Modifydatetime").getAsString());
		otabookingtrans.setStatus(otabooking.get("Status").getAsString());
		otabookingtrans.setCurrentstatus(otabooking.get("CurrentStatus").getAsString());
		otabookingtrans.setVoucherno(otabooking.get("VoucherNo").getAsString());
		otabookingtrans.setPackagecode(otabooking.get("PackageCode").getAsString());
		otabookingtrans.setPackagename(otabooking.get("PackageName").getAsString());
		otabookingtrans.setRateplancode(otabooking.get("RateplanCode").getAsString());
		otabookingtrans.setRateplanname(otabooking.get("RateplanName").getAsString());
		otabookingtrans.setRoomtypecode(otabooking.get("RoomTypeCode").getAsString());
		otabookingtrans.setRoomtypename(otabooking.get("RoomTypeName").getAsString());
		otabookingtrans.setStart(otabooking.get("Start").getAsString());
		otabookingtrans.setEnd(otabooking.get("End").getAsString());
		otabookingtrans.setArrivaltime(otabooking.get("ArrivalTime").getAsString());
		otabookingtrans.setDeparturetime(otabooking.get("DepartureTime").getAsString());
		
		otabookingtrans.setCurrencycode(otabooking.get("CurrencyCode").getAsString());
		otabookingtrans.setTotalamountaftertax(otabooking.get("TotalAmountAfterTax").getAsString());
		otabookingtrans.setTotalamountbeforetax(otabooking.get("TotalAmountBeforeTax").getAsString());
		otabookingtrans.setTotaltax(otabooking.get("TotalTax").getAsString());
		otabookingtrans.setStatus(otabooking.get("Status").getAsString());
		otabookingtrans.setTotaldiscount(otabooking.get("TotalDiscount").getAsString());
		otabookingtrans.setTotalextracharge(otabooking.get("TotalExtraCharge").getAsString());
		otabookingtrans.setTotalpayment(otabooking.get("TotalPayment").getAsString());
		otabookingtrans.setTacommision(otabooking.get("TACommision").getAsString());
		otabookingtrans.setSalutation(otabooking.get("Salutation").getAsString());
		otabookingtrans.setFirstname(otabooking.get("FirstName").getAsString());
		otabookingtrans.setLastname(otabooking.get("LastName").getAsString());
		otabookingtrans.setGender(otabooking.get("Gender").getAsString());
		otabookingtrans.setDateofbirth(otabooking.get("DateOfBirth").getAsString());
		otabookingtrans.setSpousedateofbirth(otabooking.get("SpouseDateOfBirth").getAsString());
		otabookingtrans.setWeddinganniversary(otabooking.get("WeddingAnniversary").getAsString());
		otabookingtrans.setAddress(otabooking.get("Address").getAsString());
		
		otabookingtrans.setCity(otabooking.get("City").getAsString());
		otabookingtrans.setState(otabooking.get("State").getAsString());
		otabookingtrans.setCountry(otabooking.get("Country").getAsString());
		otabookingtrans.setNationality(otabooking.get("Nationality").getAsString());
		otabookingtrans.setZipcode(otabooking.get("Zipcode").getAsString());
		otabookingtrans.setPhone(otabooking.get("Phone").getAsString());
		otabookingtrans.setMobile(otabooking.get("Mobile").getAsString());
		otabookingtrans.setFax(otabooking.get("Fax").getAsString());
		otabookingtrans.setEmail(otabooking.get("Email").getAsString());
		otabookingtrans.setRegistrationno(otabooking.get("RegistrationNo").getAsString());
		otabookingtrans.setIdentiytype(otabooking.get("IdentiyType").getAsString());
		otabookingtrans.setIdentityno(otabooking.get("IdentityNo").getAsString());
		otabookingtrans.setExpirydate(otabooking.get("ExpiryDate").getAsString());
		otabookingtrans.setTransportationmode(otabooking.get("TransportationMode").getAsString());
		otabookingtrans.setVehicle(otabooking.get("Vehicle").getAsString());
		otabookingtrans.setPickupdate(otabooking.get("PickupDate").getAsString());
		otabookingtrans.setPickuptime(otabooking.get("PickupTime").getAsString());
		
		otabookingtrans.setSource(otabooking.get("Source").getAsString());
		otabookingtrans.setComment(otabooking.get("Comment").getAsString());
		otabookingtrans.setAffiliatename(otabooking.get("AffiliateName").getAsString());
		otabookingtrans.setAffiliatecode(otabooking.get("AffiliateCode").getAsString());
		otabookingtrans.setCclink(otabooking.get("CCLink").getAsString());
		otabookingtrans.setCcno(otabooking.get("CCNo").getAsString());
		otabookingtrans.setCctype(otabooking.get("CCType").getAsString());
		otabookingtrans.setCcexpirydate(otabooking.get("CCExpiryDate").getAsString());
		otabookingtrans.setCardholdersname(otabooking.get("CardHoldersName").getAsString());
		
		JsonArray  RentalInfoobj = otabooking.get("RentalInfo").getAsJsonArray();
		otarental.addAll(tolistRentalInfo(RentalInfoobj, reservationId));	
		
		JsonArray TaxDeatilobj = otabooking.get("TaxDeatil").getAsJsonArray();
		otataxdeatil.addAll(tolistTaxDeatil(TaxDeatilobj, reservationId));
		
		otabookingTransList.add(otabookingtrans);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		otareservationdto.setOtabookingtrans(otabookingTransList);
		otareservationdto.setOtarentalinfo(otarental); 
		otareservationdto.setOtataxdeatil(otataxdeatil); 
		
		return otareservationdto;	
	}
	
	
	@Override
	public List<OTARentalInfo> tolistRentalInfo(JsonArray otarentalinfoarr, int reservationId) {
		List<OTARentalInfo> otarentallist = new ArrayList<OTARentalInfo>();
		OTARentalInfo otarental = new OTARentalInfo();
		
		for (int i = 0; i < otarentalinfoarr.size(); i++) { 
			
		JsonObject  otarentalinfo = otarentalinfoarr.get(i).getAsJsonObject();
		otarental.setEffectivedate(otarentalinfo.get("EffectiveDate").getAsString());
		otarental.setReservationid(reservationId);
		otarental.setPackagecode(otarentalinfo.get("PackageCode").getAsString());
		otarental.setPackagename(otarentalinfo.get("PackageName").getAsString());
		otarental.setRoomtypecode(otarentalinfo.get("RoomTypeCode").getAsString());
		otarental.setRoomtypename(otarentalinfo.get("RoomTypeName").getAsString());
		otarental.setAdult(otarentalinfo.get("Adult").getAsString());
		otarental.setChild(otarentalinfo.get("Child").getAsString());
		otarental.setRentpretax(otarentalinfo.get("RentPreTax").getAsString());
		otarental.setRent(otarentalinfo.get("Rent").getAsString());
		otarental.setDiscount(otarentalinfo.get("Discount").getAsString());
		
		otarentallist.add(otarental);
		}
		
		return otarentallist;
	}


	@Override
	public List<OTATaxDeatil> tolistTaxDeatil(JsonArray TaxDeatilobj, int reservationId) {
		
		List<OTATaxDeatil> OtaTaxdeatilList = new ArrayList<OTATaxDeatil>();
		
		for (int i = 0; i < TaxDeatilobj.size(); i++) { 
			
		JsonObject  jobj = TaxDeatilobj.get(i).getAsJsonObject();
		OTATaxDeatil otataxdeatil = new OTATaxDeatil();
		
		otataxdeatil.setTaxcode(jobj.get("TaxCode").getAsString());
		otataxdeatil.setReservationid(reservationId);
		otataxdeatil.setTaxname(jobj.get("TaxName").getAsString());
		otataxdeatil.setTaxamount(jobj.get("TaxAmount").getAsString());
		
		OtaTaxdeatilList.add(otataxdeatil);
		}
		
		return OtaTaxdeatilList;
	}


	@Override
	public List<OTACancelReservation> tolistCancelReservation(JsonArray cancelReservation) {

		List<OTACancelReservation> otacancelreservationlist = new ArrayList<OTACancelReservation>();
		
		for (int i = 0; i < cancelReservation.size(); i++) { 
		JsonObject  jobjcancelReservation = cancelReservation.get(i).getAsJsonObject();
		OTACancelReservation otacancelreservation = new OTACancelReservation();
		otacancelreservation.setLocationid(jobjcancelReservation.get("LocationId").getAsString());
		otacancelreservation.setReservationid(jobjcancelReservation.get("UniqueID").getAsInt());
		otacancelreservation.setStatus(jobjcancelReservation.get("Status").getAsString());
		otacancelreservation.setCanceldatetime(jobjcancelReservation.get("Canceldatetime").getAsString());
		otacancelreservation.setRemark(jobjcancelReservation.get("Remark").getAsString());
		otacancelreservation.setVoucherno(jobjcancelReservation.get("VoucherNo").getAsString());
		otacancelreservationlist.add(otacancelreservation);
		}
		return otacancelreservationlist;
	}
	
	@Override
	public OTAReservationDTO getRetrieveAllFromDB(HotelInfo hotel) {
		
		OTAReservationDTO otareservationDTO = new OTAReservationDTO();
		
		List<OTAReservation> resrvationList = otareservationDaoImpl.getAllRecords();
		List<OTABookingTrans> bookingtransList = otabookingtransDaoImpl.getAllRecords();
		List<OTARentalInfo> rentalinfoList = otarentalInfoDaoImpl.getAllRecords();
		List<OTATaxDeatil> taxdetailList = otataxdeatilDaoImpl.getAllRecords();
		List<OTACancelReservation> cancelreservationlist = otacancelreservationDaoImpl.getAllRecords();
		
		otareservationDTO.setOtareservation(resrvationList);
		otareservationDTO.setOtabookingtrans(bookingtransList);
		otareservationDTO.setOtarentalinfo(rentalinfoList);
		otareservationDTO.setOtataxdeatil(taxdetailList);
		otareservationDTO.setOtacancelreservation(cancelreservationlist);
		
		return otareservationDTO;
	}
	
	@Override
	public HotelInfoDTO getBookingReceived(HotelInfo hotel,String BookingId,String PMS_BookingId,String Status) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject booking = new JSONObject();
		JSONObject booking1 = new JSONObject();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "BookingRecdNotification");
		payload.put("Authentication", auth);
		booking1.put("BookingId", BookingId);
		booking1.put("PMS_BookingId", PMS_BookingId);
		booking1.put("Status",Status);
		booking.put("Booking", booking1);
		payload.put("Bookings", booking);
		
		request.put("RES_Request", payload); 	
		String json = request.toString();
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
			
	    JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
	    HotelInfoDTO hotelInfo = new HotelInfoDTO();
	    
	    try {
	    JsonObject jobjsuccess = jsonobject.get("Success").getAsJsonObject();
	    String successmsg = jobjsuccess.get("SuccessMsg").getAsString();
	    String errormsg ;
	    String errorcode ;
	    if(successmsg.equals("")) {
	    JsonObject jobjerror = jsonobject.get("Errors").getAsJsonObject();
	    errormsg = jobjsuccess.get("ErrorMessage").getAsString();
	    errorcode = jobjsuccess.get("ErrorCode").getAsString();
	    hotelInfo.setErrormsg(errormsg);
	    hotelInfo.setErrorcode(errorcode);
	    }
	    
	    hotelInfo.setSuccessmsg(successmsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	    return hotelInfo;
	}
	
	
	@Override
	public OTAReservationDTO getBookingId() {
		OTAReservationDTO dto = new OTAReservationDTO();
		List<OTAReservation> reservation = otareservationDaoImpl.getAllRecords();
		List<OTABookingTrans> bookingtrans = otabookingtransDaoImpl.getAllRecords();
		List<OTACancelReservation> cancelreservation = otacancelreservationDaoImpl.getAllRecords();
		
		dto.setOtareservation(reservation);
		dto.setOtabookingtrans(bookingtrans);
		dto.setOtacancelreservation(cancelreservation);
		
		return dto;
	}
	
	
	@Override
	public OTARoomInfoDTO getRoomInformation(HotelInfo hotel,int roomrequired) {
		
		int o = 0;
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTARoomRoomTypes> otaroomtypeslist =  otaroomroomtypesdao.getAllRecords();
		List<OTARoomRateTypes> otaratetypeslist =  otaroomratetypesDao.getAllRecords();
		List<OTARoomRatePlans> otarateplanslist = otaroomrateplansdao.getAllRecords();
		
		o = otaroomroomtypesdao.deleteAll(otaroomtypeslist.size());
		o = otaroomratetypesDao.deleteAll(otaratetypeslist.size());
		o = otaroomrateplansdao.deleteAll(otarateplanslist.size());
		
		JSONObject request = new JSONObject();
		JSONObject payload = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject roomneeds = new JSONObject();
		
		auth.put("HotelCode", hotelcode);
		auth.put("AuthCode", hotelauthkey);
		payload.put("Request_Type", "RoomInfo");
		payload.put("NeedPhysicalRooms", roomrequired);
		payload.put("Authentication", auth);
		request.put("RES_Request", payload); 
		
		String json = request.toString();
		String url = "https://live.ipms247.com/pmsinterface/pms_connectivity.php";
		
	    JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON(url, json);
	    
	    JsonObject jobj = jsonobject.get("RoomInfo").getAsJsonObject();
	    JsonObject jobjroomtypes = jobj.get("RoomTypes").getAsJsonObject();
	    JsonObject jobjratetypes = jobj.get("RateTypes").getAsJsonObject();
	    JsonObject jobjrateplans = jobj.get("RatePlans").getAsJsonObject();
	    
	    List<OTARoomRoomTypes> otaroomtypes = toListRoomTypes(jobjroomtypes);
	    List<OTARoomRateTypes> otaratetypes = toListRateTypes(jobjratetypes);
	    List<OTARoomRatePlans> otarateplans = toListRatePlans(jobjrateplans);
	   
	    try {
	
		int c = 0;
		for (OTARoomRoomTypes roomtype : otaroomtypes) {
			c++;
			roomtype.setId(c);
			o = otaroomroomtypesdao.save(roomtype);
		}

		c = 0;
		for (OTARoomRateTypes otaratetype : otaratetypes) {
			c++;
			otaratetype.setId(c);
			o = otaroomratetypesDao.save(otaratetype);
		}

		c = 0;
		for (OTARoomRatePlans otarateplan : otarateplans) {
			c++;
			otarateplan.setId(c);
			o = otaroomrateplansdao.save(otarateplan);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    OTARoomInfoDTO otaroominfodto = new OTARoomInfoDTO();
	    otaroominfodto.setOtaroomroomtypes(otaroomtypes);
	    otaroominfodto.setOtaroomratetypes(otaratetypes);
	    otaroominfodto.setOtaroomrateplans(otarateplans);
	    
	    return otaroominfodto;
	}
	
	
	@Override
	public OTARoomInfoDTO getRoomInformationFromDB() {
		
		List<OTARoomRoomTypes> otaroomtypes =  otaroomroomtypesdao.getAllRecords();
		List<OTARoomRateTypes> otaratetypes =  otaroomratetypesDao.getAllRecords();
		List<OTARoomRatePlans> otarateplans = otaroomrateplansdao.getAllRecords();
		
		OTARoomInfoDTO otaroominfodto = new OTARoomInfoDTO();
		otaroominfodto.setOtaroomroomtypes(otaroomtypes);
		otaroominfodto.setOtaroomratetypes(otaratetypes);
		otaroominfodto.setOtaroomrateplans(otarateplans);
		    
		return otaroominfodto;
	}
	

	@Override
	public List<OTARoomRoomTypes> toListRoomTypes(JsonObject jobjroomtypes) {
		
		List<OTARoomRoomTypes> OTARoomTypes = new ArrayList<OTARoomRoomTypes>();
		JsonArray roomtype = jobjroomtypes.get("RoomType").getAsJsonArray();
		
		for (int i = 0; i < roomtype.size(); i++) { 
			try {
				JsonObject jobjotaroomtypes = roomtype.get(i).getAsJsonObject();
				String roomtypesid = jobjotaroomtypes.get("ID").getAsString();
				String roomtypesname = jobjotaroomtypes.get("Name").getAsString();
				
				boolean isexistrooms = false;
				String roomexists = jobjotaroomtypes.toString();
				if(roomexists.contains("Rooms")) {
					isexistrooms = true;
				}
				
				if(isexistrooms == true) {
				JsonArray rooms = jobjotaroomtypes.get("Rooms").getAsJsonArray();
				for (int j = 0; j < rooms.size(); j++) { 
					OTARoomRoomTypes otaroom = new OTARoomRoomTypes();
					
					JsonObject otarooms = rooms.get(j).getAsJsonObject();
					otaroom.setRoomtypesid(roomtypesid);
					otaroom.setRoomtypename(roomtypesname);
					otaroom.setRoomid(otarooms.get("RoomID").getAsString());
					otaroom.setRoomname(otarooms.get("RoomName").getAsString());	
					
					OTARoomTypes.add(otaroom);
				}
				}else {
					OTARoomRoomTypes otaroom = new OTARoomRoomTypes();
					
					otaroom.setRoomtypesid(roomtypesid);
					otaroom.setRoomtypename(roomtypesname);
					otaroom.setRoomid("No Rooms Available");
					otaroom.setRoomname("No Rooms Available");
					
					OTARoomTypes.add(otaroom);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return OTARoomTypes;
	}
	
	
	@Override
	public List<OTARoomRateTypes> toListRateTypes(JsonObject jobjratetypes) {
		
		List<OTARoomRateTypes>  otaratetypes = new ArrayList<OTARoomRateTypes>();
		JsonArray jsonobjratetype = jobjratetypes.get("RateType").getAsJsonArray();
		
		for (int i = 0; i < jsonobjratetype.size(); i++) { 
			try {
				
				OTARoomRateTypes otaratetype = new OTARoomRateTypes();
				JsonObject ratetype = jsonobjratetype.get(i).getAsJsonObject();
				otaratetype.setRoomtypesid(ratetype.get("ID").getAsString());
				otaratetype.setRoomtypesname(ratetype.get("Name").getAsString());
				
				otaratetypes.add(otaratetype);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return otaratetypes;
	}
	
	@Override
	public List<OTARoomRatePlans> toListRatePlans(JsonObject jobjrateplans) {
	
		List<OTARoomRatePlans> otarateplans = new ArrayList<OTARoomRatePlans>();
		JsonArray jarrrateplans = jobjrateplans.get("RatePlan").getAsJsonArray();
		
		for (int i = 0; i < jarrrateplans.size(); i++) { 
			try {
				OTARoomRatePlans otarate = new OTARoomRatePlans();
				JsonObject ratetype = jarrrateplans.get(i).getAsJsonObject();
				
				otarate.setRateplanid(ratetype.get("RatePlanID").getAsString());
				otarate.setRoomname(ratetype.get("Name").getAsString());
				otarate.setRoomtypeid(ratetype.get("RoomTypeID").getAsString());
				otarate.setRoomtype(ratetype.get("RoomType").getAsString());
				otarate.setRatetypeid(ratetype.get("RateTypeID").getAsString());
				otarate.setRatetype(ratetype.get("RateType").getAsString());
			
				otarateplans.add(otarate);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return otarateplans;
	}
	
	
	@Override
	public List<OTARoomDetailsDTO> getOtareservationSingleroom(HotelInfo hotel, int reservationid) {
		
		int o = 0;
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		List<OTARoomDetailsDTO> otaroomdetailslist =  new ArrayList<OTARoomDetailsDTO>();
		
		try {
		JSONObject request = new JSONObject();
		JSONObject auth = new JSONObject();
		
		auth.put("hotel_id", hotelcode);
		auth.put("reservation_id", reservationid);
		request.put("request_type", "get_reservation"); 
		request.put("body", auth);
		String json = request.toString();
		String url = "https://live.ipms247.com/channelbookings/vacation_rental.php";
		
	    JsonObject jsonobject = onlineTravelAgentServiceImpl.Post_JSON_Header(url, json, hotel);
	    
	    String status = jsonobject.get("status").getAsString();
	    JsonArray jarr = jsonobject.get("data").getAsJsonArray();
	  
	    if(status.equals("success")) {
	    	for(int i = 0; i< jarr.size();i++) {
	    		JsonObject otaroom = jarr.get(i).getAsJsonObject();
	    		OTARoomDetailsDTO otaroomdetails = new OTARoomDetailsDTO();
	    		
	    		otaroomdetails.setId(i+1);
	    		otaroomdetails.setHotel_id(otaroom.get("hotel_id").getAsString());
	    		otaroomdetails.setHotel_name(otaroom.get("hotel_name").getAsString());
	    		otaroomdetails.setRoom_id(otaroom.get("room_id").getAsString());
	    		otaroomdetails.setRoom_name(otaroom.get("room_name").getAsString());
	    		otaroomdetails.setRoom_code(otaroom.get("room_code").getAsString());
	    		otaroomdetails.setReservation_id(otaroom.get("reservation_id").getAsString());
	    		otaroomdetails.setBooking_status(otaroom.get("booking_status").getAsString());
	    		otaroomdetails.setGuest_name(otaroom.get("guest_name").getAsString());
	    		otaroomdetails.setCheck_in(otaroom.get("check_in").getAsString());
	    		otaroomdetails.setCheck_out(otaroom.get("check_out").getAsString());
	    		otaroomdetails.setRemark(otaroom.get("remark").getAsString());
	    		otaroomdetails.setTotal_amount(otaroom.get("total_amount").getAsString());
	    		otaroomdetails.setCurrency(otaroom.get("currency").getAsString());
	    		otaroomdetails.setChannel(otaroom.get("channel").getAsString());
	    		otaroomdetails.setPayment_type(otaroom.get("payment_type").getAsString());
	    		
	    		otaroomdetailslist.add(otaroomdetails);
	    	}
	    }
	    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return otaroomdetailslist;
	}
	

	
	@Override
	public List<OTARoomInventoryDTO> getRetrieveRoomInventory(HotelInfo hotel) {
		
		List<OTARoomInventoryDTO> otaroominvList  = new ArrayList<OTARoomInventoryDTO>();
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		String xmldata = "<?xml version=\"1.0\" standalone=\"yes\"?><RES_Request><Request_Type>Inventory</Request_Type><Authentication><HotelCode>"+hotelcode+"</HotelCode><AuthCode>"+hotelauthkey+"</AuthCode></Authentication><FromDate>2020-10-05</FromDate><ToDate>2020-11-18</ToDate></RES_Request>";
		String url = "https://live.ipms247.com/pmsinterface/getdataAPI.php";
		
		JSONObject jobj = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String data = jobj.toString();
		
		try {
			
			JSONObject jobjresresponse = (JSONObject) jobj.get("RES_Response");
			JSONObject jobjroominfo = (JSONObject) jobjresresponse.get("RoomInfo");
			JSONObject jobjsource = (JSONObject) jobjroominfo.get("Source");
			JSONObject jobjroomtypes = (JSONObject) jobjsource.get("RoomTypes");
			JSONArray jobjrooms =  (JSONArray) jobjroomtypes.get("RoomType");
			
			for(int i=0; i< jobjrooms.length() ;i++) {
				JSONObject jobjeachroom = (JSONObject) jobjrooms.get(i);
				
				OTARoomInventoryDTO otaroominv  = new OTARoomInventoryDTO();
				
				otaroominv.setId(i+1);
				otaroominv.setRoomtypeid(jobjeachroom.get("RoomTypeID").toString()); 
				otaroominv.setAvailability(jobjeachroom.get("Availability").toString()); 
				otaroominv.setFromdate(jobjeachroom.get("FromDate").toString());
				otaroominv.setTodate(jobjeachroom.get("ToDate").toString());
				
				otaroominvList.add(otaroominv);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		return otaroominvList;
	}
	
	
	
	@Override
	public OTAReservationDTO getInventory(HotelInfo hotel) {
		
		String hotelcode = hotel.getHotelcode();
		String hotelauthkey = hotel.getAuthkey();
		
		String xmldata = "<?xml version=\"1.0\" standalone=\"yes\"?><Request_Type>Inventory</Request_Type><Authentication><HotelCode>hotelcode</HotelCode><AuthCode>hotelauthkey</AuthCode></Authentication><FromDate>2020-03-05</FromDate><ToDate>2020-03-18</ToDate></RES_Request>";
		String url = "https://live.ipms247.com/pmsinterface/getdataAPI.php";
		
		org.json.JSONObject response = onlineTravelAgentServiceImpl.getUrlContents(url, xmldata);
		String data = response.toString();
		
		 
	    System.out.println("getInventory=====>"+data);
	    
		return null;
	}

	


	


	
}
