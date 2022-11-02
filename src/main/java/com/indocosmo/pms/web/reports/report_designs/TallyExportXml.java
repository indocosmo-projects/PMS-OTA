package com.indocosmo.pms.web.reports.report_designs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

@Repository
public class TallyExportXml {

	@Autowired
	SystemSettingsService systemSettingsService;


	public String tallyExport(JsonArray tallyData, Properties prop,String titleHead) {
		StringBuilder content = new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String filePath = prop.getProperty("exportTallyPath");
		String path = filePath+"\\tally"+dateFormat.format(today).toString()+".txt";
		String invNO = "";
		String xmlString = "";
		String xmlStringHead = "";
		String xmlStringEnd = "";
		String xmlBody="";
		String items = "";
		String summaryItems = "";
		String xmlTax="";
		
		
		try {

			for(Object obj : tallyData) {
				JsonObject jobj = (JsonObject) obj;
				String[] dateString = (jobj.get("actualDepartDate").getAsString()).split("-");
				String date = dateString[0]+dateString[1]+dateString[2];

				String tax1Pc = jobj.get("tax1pc").getAsString();
				String tax2Pc = jobj.get("tax2pc").getAsString();
				String tax3Pc = jobj.get("tax3pc").getAsString();
				String[] tax1_pc = (jobj.get("tax1pc").getAsString()).split("\\.");
				String[] tax2_pc = (jobj.get("tax2pc").getAsString()).split("\\.");
				String[] tax3_pc = (jobj.get("tax3pc").getAsString()).split("\\.");
				if(tax1_pc[1].equals("00")) {
					tax1Pc = tax1_pc[0];
				}else if((tax1_pc[1].substring(tax1_pc[1].length()-1)).equals("0")) {
					tax1Pc = tax1_pc[0] +"."+ tax1_pc[1].replace("0", "");
				}

				if(tax2_pc[1].equals("00")) {
					tax2Pc = tax2_pc[0];
				}else if((tax2_pc[1].substring(tax2_pc[1].length()-1)).equals("0")) {
					tax2Pc = tax2_pc[0] +"."+ tax2_pc[1].replace("0", "");
				}

				if(tax3_pc[1].equals("00")) {
					tax3Pc = tax3_pc[0];
				}else if((tax3_pc[1].substring(tax3_pc[1].length()-1)).equals("0")) {
					tax3Pc = tax3_pc[0] +"."+ tax3_pc[1].replace("0", "");
				}

				String corporateName = jobj.get("corporateName").getAsString();
				corporateName = corporateName.replaceAll("&(?!amp;)", " AND ");
				String gstNo=jobj.get("gstNo").getAsString();
				/*String paymentMode = jobj.get("paymentMode").getAsString();
				String pmtMode = paymentMode;
				if(paymentMode.equals("CARD")) {
					pmtMode = prop.getProperty("CARD");
				}else if(paymentMode.equals("DD")) {
					pmtMode = prop.getProperty("BANK");
				}else if(paymentMode.equals("ONLINE TRANSFER")) {
					pmtMode = guestName;
				}*/

				/*if(paymentMode.equals("COMPANY")) {
					pmtMode = jobj.get("corporateName").getAsString();*/
				xmlStringHead = "<ENVELOPE>" + 
						"\n\t<HEADER>" + 
						"\n\t\t<TALLYREQUEST>Import Data</TALLYREQUEST>" + 
						"\n\t</HEADER>" + 
						"\n\t<BODY>" + 
						"\n\t\t<IMPORTDATA>" + 
						"\n\t\t\t<REQUESTDESC>" + 
						"\n\t\t\t\t<REPORTNAME>Vouchers</REPORTNAME>" + 
						"\n\t\t\t\t<STATICVARIABLES>" + 
						"\n\t\t\t\t<SVCURRENTCOMPANY>"+titleHead+"</SVCURRENTCOMPANY>" + 
						"\n\t\t\t\t</STATICVARIABLES>" + 
						"\n\t\t\t</REQUESTDESC>" + 
						"\n\t\t\t<REQUESTDATA>" + 
						"\n\t\t\t\t<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">" + 
						"\n\t\t\t\t\t<VOUCHER VCHTYPE=\"Sales\" ACTION=\"Create\" OBJVIEW=\"Invoice Voucher View\">" +
						"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
						"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
						"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
						"\n\t\t\t\t\t\t<DATE>"+date+"</DATE>" + 
						"\n\t\t\t\t\t\t<NARRATION>Being sales Accounted</NARRATION>" + 
						"\n\t\t\t\t\t\t<COUNTRYOFRESIDENCE>India</COUNTRYOFRESIDENCE>";
				if(!gstNo.isEmpty()) {
					xmlBody="\n\t\t\t\t\t\t<PARTYGSTIN>"+gstNo+"</PARTYGSTIN>" +
							"\n\t\t\t\t\t\t<PARTYNAME>"+corporateName+"</PARTYNAME>" +
							"\n\t\t\t\t\t\t<PARTYLEDGERNAME>"+corporateName+"</PARTYLEDGERNAME>" +
							"\n\t\t\t\t\t\t<VOUCHERTYPENAME>Sales</VOUCHERTYPENAME>" +
							"\n\t\t\t\t\t\t<REFERENCE>"+jobj.get("invoiceNo").getAsString()+"</REFERENCE>" +
							"\n\t\t\t\t\t\t<BASICBASEPARTYNAME>"+corporateName+"</BASICBASEPARTYNAME>"+
							"\n\t\t\t\t\t\t <CSTFORMISSUETYPE/>" +
							"\n\t\t\t\t\t\t <CSTFORMRECVTYPE/>" +
							"\n\t\t\t\t\t\t<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>" +
							"\n\t\t\t\t\t\t<PERSISTEDVIEW>Invoice Voucher View</PERSISTEDVIEW>" +
							"\n\t\t\t\t\t\t<CONSIGNEEGSTIN>"+gstNo+"</CONSIGNEEGSTIN>" +
							"\n\t\t\t\t\t\t<BASICBUYERNAME>"+corporateName+"</BASICBUYERNAME>    " +						
							"\n\t\t\t\t\t\t<VCHGSTCLASS/>" +
							"\n\t\t\t\t\t\t<DIFFACTUALQTY>No</DIFFACTUALQTY>" +
							"\n\t\t\t\t\t\t<ISMSTFROMSYNC>No</ISMSTFROMSYNC>" +
							"\n\t\t\t\t\t\t<ASORIGINAL>No</ASORIGINAL>" +
							"\n\t\t\t\t\t\t<AUDITED>No</AUDITED>" +
							"\n\t\t\t\t\t\t<FORJOBCOSTING>No</FORJOBCOSTING>" +
							"\n\t\t\t\t\t\t <ISOPTIONAL>No</ISOPTIONAL>" +
							"\n\t\t\t\t\t\t <EFFECTIVEDATE>"+date+"</EFFECTIVEDATE>" +
							"\n\t\t\t\t\t\t<USEFOREXCISE>No</USEFOREXCISE>" +
							"\n\t\t\t\t\t\t <ISFORJOBWORKIN>No</ISFORJOBWORKIN>" +
							"\n\t\t\t\t\t\t <ALLOWCONSUMPTION>No</ALLOWCONSUMPTION>" +
							"\n\t\t\t\t\t\t<USEFORINTEREST>No</USEFORINTEREST>" +
							"\n\t\t\t\t\t\t<USEFORGAINLOSS>No</USEFORGAINLOSS>" +
							"\n\t\t\t\t\t\t<USEFORGODOWNTRANSFER>No</USEFORGODOWNTRANSFER>" +
							"\n\t\t\t\t\t\t<USEFORCOMPOUND>No</USEFORCOMPOUND>" +
							"\n\t\t\t\t\t\t<USEFORSERVICETAX>No</USEFORSERVICETAX>" +
							"\n\t\t\t\t\t\t<ISDELETED>No</ISDELETED>" +
							"\n\t\t\t\t\t\t<ISONHOLD>No</ISONHOLD>" +
							"\n\t\t\t\t\t\t<ISBOENOTAPPLICABLE>No</ISBOENOTAPPLICABLE>" +
							"\n\t\t\t\t\t\t<ISEXCISEVOUCHER>No</ISEXCISEVOUCHER>" +
							"\n\t\t\t\t\t\t<EXCISETAXOVERRIDE>No</EXCISETAXOVERRIDE>" +
							"\n\t\t\t\t\t\t<USEFORTAXUNITTRANSFER>No</USEFORTAXUNITTRANSFER>" +
							"\n\t\t\t\t\t\t<IGNOREPOSVALIDATION>No</IGNOREPOSVALIDATION>" +
							"\n\t\t\t\t\t\t<EXCISEOPENING>No</EXCISEOPENING>" +
							"\n\t\t\t\t\t\t<USEFORFINALPRODUCTION>No</USEFORFINALPRODUCTION>" +
							"\n\t\t\t\t\t\t <ISTDSOVERRIDDEN>No</ISTDSOVERRIDDEN>" +
							"\n\t\t\t\t\t\t<ISTCSOVERRIDDEN>No</ISTCSOVERRIDDEN>" +
							"\n\t\t\t\t\t\t <ISTDSTCSCASHVCH>No</ISTDSTCSCASHVCH>" +
							"\n\t\t\t\t\t\t <INCLUDEADVPYMTVCH>No</INCLUDEADVPYMTVCH>" +
							"\n\t\t\t\t\t\t <ISSUBWORKSCONTRACT>No</ISSUBWORKSCONTRACT>" +
							"\n\t\t\t\t\t\t<ISVATOVERRIDDEN>No</ISVATOVERRIDDEN>" +
							"\n\t\t\t\t\t\t<IGNOREORIGVCHDATE>No</IGNOREORIGVCHDATE>" +
							"\n\t\t\t\t\t\t<ISVATPAIDATCUSTOMS>No</ISVATPAIDATCUSTOMS>" +
							"\n\t\t\t\t\t\t<ISDECLAREDTOCUSTOMS>No</ISDECLAREDTOCUSTOMS>" +
							"\n\t\t\t\t\t\t<ISSERVICETAXOVERRIDDEN>No</ISSERVICETAXOVERRIDDEN>" +
							"\n\t\t\t\t\t\t<ISISDVOUCHER>No</ISISDVOUCHER>" +
							"\n\t\t\t\t\t\t<ISEXCISEOVERRIDDEN>No</ISEXCISEOVERRIDDEN>" +
							"\n\t\t\t\t\t\t<ISEXCISESUPPLYVCH>No</ISEXCISESUPPLYVCH>" +
							"\n\t\t\t\t\t\t <ISGSTOVERRIDDEN>No</ISGSTOVERRIDDEN>" +
							"\n\t\t\t\t\t\t<GSTNOTEXPORTED>No</GSTNOTEXPORTED>" +
							"\n\t\t\t\t\t\t<IGNOREGSTINVALIDATION>No</IGNOREGSTINVALIDATION>" + 
							"\n\t\t\t\t\t\t <ISGSTREFUND>No</ISGSTREFUND>" + 
							"\n\t\t\t\t\t\t<ISGSTSECSEVENAPPLICABLE>No</ISGSTSECSEVENAPPLICABLE>" + 
							"\n\t\t\t\t\t\t<ISVATPRINCIPALACCOUNT>No</ISVATPRINCIPALACCOUNT>" + 
							"\n\t\t\t\t\t\t<ISSHIPPINGWITHINSTATE>No</ISSHIPPINGWITHINSTATE>" + 
							"\n\t\t\t\t\t\t<ISOVERSEASTOURISTTRANS>No</ISOVERSEASTOURISTTRANS>" + 
							"\n\t\t\t\t\t\t<ISDESIGNATEDZONEPARTY>No</ISDESIGNATEDZONEPARTY>" + 
							"\n\t\t\t\t\t\t<ISCANCELLED>No</ISCANCELLED>" + 
							"\n\t\t\t\t\t\t<HASCASHFLOW>No</HASCASHFLOW>" + 
							"\n\t\t\t\t\t\t<ISPOSTDATED>No</ISPOSTDATED>" + 
							"\n\t\t\t\t\t\t<USETRACKINGNUMBER>No</USETRACKINGNUMBER>" + 
							"\n\t\t\t\t\t\t<ISINVOICE>Yes</ISINVOICE>" + 
							"\n\t\t\t\t\t\t<MFGJOURNAL>No</MFGJOURNAL>" + 
							"\n\t\t\t\t\t\t<HASDISCOUNTS>No</HASDISCOUNTS>" + 
							"\n\t\t\t\t\t\t<ASPAYSLIP>No</ASPAYSLIP>" + 
							"\n\t\t\t\t\t\t<ISCOSTCENTRE>No</ISCOSTCENTRE>" + 
							"\n\t\t\t\t\t\t<ISSTXNONREALIZEDVCH>No</ISSTXNONREALIZEDVCH>" + 
							"\n\t\t\t\t\t\t<ISEXCISEMANUFACTURERON>No</ISEXCISEMANUFACTURERON>" + 
							"\n\t\t\t\t\t\t<ISBLANKCHEQUE>No</ISBLANKCHEQUE>" + 
							"\n\t\t\t\t\t\t<ISVOID>No</ISVOID>" + 
							"\n\t\t\t\t\t\t<ORDERLINESTATUS>No</ORDERLINESTATUS>" + 
							"\n\t\t\t\t\t\t<VATISAGNSTCANCSALES>No</VATISAGNSTCANCSALES>" + 
							"\n\t\t\t\t\t\t<VATISPURCEXEMPTED>No</VATISPURCEXEMPTED>" + 
							"\n\t\t\t\t\t\t<ISVATRESTAXINVOICE>No</ISVATRESTAXINVOICE>" + 
							"\n\t\t\t\t\t\t<VATISASSESABLECALCVCH>No</VATISASSESABLECALCVCH>" + 
							"\n\t\t\t\t\t\t<ISVATDUTYPAID>Yes</ISVATDUTYPAID>" + 
							"\n\t\t\t\t\t\t<ISDELIVERYSAMEASCONSIGNEE>No</ISDELIVERYSAMEASCONSIGNEE>" + 
							"\n\t\t\t\t\t\t<ISDISPATCHSAMEASCONSIGNOR>No</ISDISPATCHSAMEASCONSIGNOR>" + 
							"\n\t\t\t\t\t\t<CHANGEVCHMODE>No</CHANGEVCHMODE>" +
							"\n\t\t\t\t\t\t<EWAYBILLDETAILS.LIST>      </EWAYBILLDETAILS.LIST>" + 
							"\n\t\t\t\t\t\t<EXCLUDEDTAXATIONS.LIST>      </EXCLUDEDTAXATIONS.LIST>" + 
							"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>      </OLDAUDITENTRIES.LIST>" + 
							"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>      </ACCOUNTAUDITENTRIES.LIST>" + 
							"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>      </AUDITENTRIES.LIST>" + 
							"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>      </DUTYHEADDETAILS.LIST>" + 
							"\n\t\t\t\t\t\t<INVENTORYENTRIES.LIST>      </INVENTORYENTRIES.LIST>" + 
							"\n\t\t\t\t\t\t<SUPPLEMENTARYDUTYHEADDETAILS.LIST>      </SUPPLEMENTARYDUTYHEADDETAILS.LIST>" + 
							"\n\t\t\t\t\t\t<INVOICEDELNOTES.LIST>      </INVOICEDELNOTES.LIST>" + 
							"\n\t\t\t\t\t\t<INVOICEORDERLIST.LIST>      </INVOICEORDERLIST.LIST>" + 
							"\n\t\t\t\t\t\t<INVOICEINDENTLIST.LIST>      </INVOICEINDENTLIST.LIST>" + 
							"\n\t\t\t\t\t\t<ATTENDANCEENTRIES.LIST>      </ATTENDANCEENTRIES.LIST>" + 
							"\n\t\t\t\t\t\t<ORIGINVOICEDETAILS.LIST>      </ORIGINVOICEDETAILS.LIST>" + 
							"\n\t\t\t\t\t\t<INVOICEEXPORTLIST.LIST>      </INVOICEEXPORTLIST.LIST>";


				}else {
					xmlBody=
							"\n\t\t\t\t\t\t<PARTYNAME>"+corporateName+"</PARTYNAME>" +
									"\n\t\t\t\t\t\t<PARTYLEDGERNAME>"+corporateName+"</PARTYLEDGERNAME>" +
									"\n\t\t\t\t\t\t<VOUCHERTYPENAME>Sales</VOUCHERTYPENAME>" +
									"\n\t\t\t\t\t\t<REFERENCE>"+jobj.get("invoiceNo").getAsString()+"</REFERENCE>" +
									"\n\t\t\t\t\t\t<BASICBASEPARTYNAME>"+corporateName+"</BASICBASEPARTYNAME>"+
									"\n\t\t\t\t\t\t <CSTFORMISSUETYPE/>" +
									"\n\t\t\t\t\t\t <CSTFORMRECVTYPE/>" +
									"\n\t\t\t\t\t\t<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>" +
									"\n\t\t\t\t\t\t<PERSISTEDVIEW>Invoice Voucher View</PERSISTEDVIEW>" +
									"\n\t\t\t\t\t\t<BASICBUYERNAME>"+corporateName+"</BASICBUYERNAME>    " +						
									"\n\t\t\t\t\t\t<VCHGSTCLASS/>" +
									"\n\t\t\t\t\t\t<DIFFACTUALQTY>No</DIFFACTUALQTY>" +
									"\n\t\t\t\t\t\t<ISMSTFROMSYNC>No</ISMSTFROMSYNC>" +
									"\n\t\t\t\t\t\t<ASORIGINAL>No</ASORIGINAL>" +
									"\n\t\t\t\t\t\t<AUDITED>No</AUDITED>" +
									"\n\t\t\t\t\t\t<FORJOBCOSTING>No</FORJOBCOSTING>" +
									"\n\t\t\t\t\t\t <ISOPTIONAL>No</ISOPTIONAL>" +
									"\n\t\t\t\t\t\t <EFFECTIVEDATE>"+date+"</EFFECTIVEDATE>" +
									"\n\t\t\t\t\t\t<USEFOREXCISE>No</USEFOREXCISE>" +
									"\n\t\t\t\t\t\t <ISFORJOBWORKIN>No</ISFORJOBWORKIN>" +
									"\n\t\t\t\t\t\t <ALLOWCONSUMPTION>No</ALLOWCONSUMPTION>" +
									"\n\t\t\t\t\t\t<USEFORINTEREST>No</USEFORINTEREST>" +
									"\n\t\t\t\t\t\t<USEFORGAINLOSS>No</USEFORGAINLOSS>" +
									"\n\t\t\t\t\t\t<USEFORGODOWNTRANSFER>No</USEFORGODOWNTRANSFER>" +
									"\n\t\t\t\t\t\t<USEFORCOMPOUND>No</USEFORCOMPOUND>" +
									"\n\t\t\t\t\t\t<USEFORSERVICETAX>No</USEFORSERVICETAX>" +
									"\n\t\t\t\t\t\t<ISDELETED>No</ISDELETED>" +
									"\n\t\t\t\t\t\t<ISONHOLD>No</ISONHOLD>" +
									"\n\t\t\t\t\t\t<ISBOENOTAPPLICABLE>No</ISBOENOTAPPLICABLE>" +
									"\n\t\t\t\t\t\t<ISEXCISEVOUCHER>No</ISEXCISEVOUCHER>" +
									"\n\t\t\t\t\t\t<EXCISETAXOVERRIDE>No</EXCISETAXOVERRIDE>" +
									"\n\t\t\t\t\t\t<USEFORTAXUNITTRANSFER>No</USEFORTAXUNITTRANSFER>" +
									"\n\t\t\t\t\t\t<IGNOREPOSVALIDATION>No</IGNOREPOSVALIDATION>" +
									"\n\t\t\t\t\t\t<EXCISEOPENING>No</EXCISEOPENING>" +
									"\n\t\t\t\t\t\t<USEFORFINALPRODUCTION>No</USEFORFINALPRODUCTION>" +
									"\n\t\t\t\t\t\t <ISTDSOVERRIDDEN>No</ISTDSOVERRIDDEN>" +
									"\n\t\t\t\t\t\t<ISTCSOVERRIDDEN>No</ISTCSOVERRIDDEN>" +
									"\n\t\t\t\t\t\t <ISTDSTCSCASHVCH>No</ISTDSTCSCASHVCH>" +
									"\n\t\t\t\t\t\t <INCLUDEADVPYMTVCH>No</INCLUDEADVPYMTVCH>" +
									"\n\t\t\t\t\t\t <ISSUBWORKSCONTRACT>No</ISSUBWORKSCONTRACT>" +
									"\n\t\t\t\t\t\t<ISVATOVERRIDDEN>No</ISVATOVERRIDDEN>" +
									"\n\t\t\t\t\t\t<IGNOREORIGVCHDATE>No</IGNOREORIGVCHDATE>" +
									"\n\t\t\t\t\t\t<ISVATPAIDATCUSTOMS>No</ISVATPAIDATCUSTOMS>" +
									"\n\t\t\t\t\t\t<ISDECLAREDTOCUSTOMS>No</ISDECLAREDTOCUSTOMS>" +
									"\n\t\t\t\t\t\t<ISSERVICETAXOVERRIDDEN>No</ISSERVICETAXOVERRIDDEN>" +
									"\n\t\t\t\t\t\t<ISISDVOUCHER>No</ISISDVOUCHER>" +
									"\n\t\t\t\t\t\t<ISEXCISEOVERRIDDEN>No</ISEXCISEOVERRIDDEN>" +
									"\n\t\t\t\t\t\t<ISEXCISESUPPLYVCH>No</ISEXCISESUPPLYVCH>" +
									"\n\t\t\t\t\t\t <ISGSTOVERRIDDEN>No</ISGSTOVERRIDDEN>" +
									"\n\t\t\t\t\t\t<GSTNOTEXPORTED>No</GSTNOTEXPORTED>" +
									"\n\t\t\t\t\t\t<IGNOREGSTINVALIDATION>No</IGNOREGSTINVALIDATION>" + 
									"\n\t\t\t\t\t\t <ISGSTREFUND>No</ISGSTREFUND>" + 
									"\n\t\t\t\t\t\t<ISGSTSECSEVENAPPLICABLE>No</ISGSTSECSEVENAPPLICABLE>" + 
									"\n\t\t\t\t\t\t<ISVATPRINCIPALACCOUNT>No</ISVATPRINCIPALACCOUNT>" + 
									"\n\t\t\t\t\t\t<ISBOENOTAPPLICABLE>No</ISBOENOTAPPLICABLE>" + 
									"\n\t\t\t\t\t\t<ISSHIPPINGWITHINSTATE>No</ISSHIPPINGWITHINSTATE>" + 
									"\n\t\t\t\t\t\t<ISOVERSEASTOURISTTRANS>No</ISOVERSEASTOURISTTRANS>" + 
									"\n\t\t\t\t\t\t<ISDESIGNATEDZONEPARTY>No</ISDESIGNATEDZONEPARTY>" + 
									"\n\t\t\t\t\t\t<ISCANCELLED>No</ISCANCELLED>" + 
									"\n\t\t\t\t\t\t<HASCASHFLOW>No</HASCASHFLOW>" + 
									"\n\t\t\t\t\t\t<ISPOSTDATED>No</ISPOSTDATED>" + 
									"\n\t\t\t\t\t\t<USETRACKINGNUMBER>No</USETRACKINGNUMBER>" + 
									"\n\t\t\t\t\t\t<ISINVOICE>Yes</ISINVOICE>" + 
									"\n\t\t\t\t\t\t<MFGJOURNAL>No</MFGJOURNAL>" + 
									"\n\t\t\t\t\t\t<HASDISCOUNTS>No</HASDISCOUNTS>" + 
									"\n\t\t\t\t\t\t<ASPAYSLIP>No</ASPAYSLIP>" + 
									"\n\t\t\t\t\t\t<ISCOSTCENTRE>No</ISCOSTCENTRE>" + 
									"\n\t\t\t\t\t\t<ISSTXNONREALIZEDVCH>No</ISSTXNONREALIZEDVCH>" + 
									"\n\t\t\t\t\t\t<ISEXCISEMANUFACTURERON>No</ISEXCISEMANUFACTURERON>" + 
									"\n\t\t\t\t\t\t<ISBLANKCHEQUE>No</ISBLANKCHEQUE>" + 
									"\n\t\t\t\t\t\t<ISVOID>No</ISVOID>" + 
									"\n\t\t\t\t\t\t<ISONHOLD>No</ISONHOLD>" + 
									"\n\t\t\t\t\t\t<ORDERLINESTATUS>No</ORDERLINESTATUS>" + 
									"\n\t\t\t\t\t\t<VATISAGNSTCANCSALES>No</VATISAGNSTCANCSALES>" + 
									"\n\t\t\t\t\t\t<VATISPURCEXEMPTED>No</VATISPURCEXEMPTED>" + 
									"\n\t\t\t\t\t\t<ISVATRESTAXINVOICE>No</ISVATRESTAXINVOICE>" + 
									"\n\t\t\t\t\t\t<VATISASSESABLECALCVCH>No</VATISASSESABLECALCVCH>" + 
									"\n\t\t\t\t\t\t<ISVATDUTYPAID>Yes</ISVATDUTYPAID>" + 
									"\n\t\t\t\t\t\t<ISDELIVERYSAMEASCONSIGNEE>No</ISDELIVERYSAMEASCONSIGNEE>" + 
									"\n\t\t\t\t\t\t<ISDISPATCHSAMEASCONSIGNOR>No</ISDISPATCHSAMEASCONSIGNOR>" + 
									"\n\t\t\t\t\t\t<ISDELETED>No</ISDELETED>" + 
									"\n\t\t\t\t\t\t<CHANGEVCHMODE>No</CHANGEVCHMODE>" +
									"\n\t\t\t\t\t\t<EWAYBILLDETAILS.LIST>      </EWAYBILLDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<EXCLUDEDTAXATIONS.LIST>      </EXCLUDEDTAXATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>      </OLDAUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>      </ACCOUNTAUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>      </AUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>      </DUTYHEADDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<INVENTORYENTRIES.LIST>      </INVENTORYENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<SUPPLEMENTARYDUTYHEADDETAILS.LIST>      </SUPPLEMENTARYDUTYHEADDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<INVOICEDELNOTES.LIST>      </INVOICEDELNOTES.LIST>" + 
									"\n\t\t\t\t\t\t<INVOICEORDERLIST.LIST>      </INVOICEORDERLIST.LIST>" + 
									"\n\t\t\t\t\t\t<INVOICEINDENTLIST.LIST>      </INVOICEINDENTLIST.LIST>" + 
									"\n\t\t\t\t\t\t<ATTENDANCEENTRIES.LIST>      </ATTENDANCEENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<ORIGINVOICEDETAILS.LIST>      </ORIGINVOICEDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<INVOICEEXPORTLIST.LIST>      </INVOICEEXPORTLIST.LIST>";
				}

				xmlStringEnd = "\n\t\t\t\t\t<PAYROLLMODEOFPAYMENT.LIST>      </PAYROLLMODEOFPAYMENT.LIST>" + 
						"\n\t\t\t\t\t<ATTDRECORDS.LIST>      </ATTDRECORDS.LIST>" + 
						"\n\t\t\t\t\t<GSTEWAYCONSIGNORADDRESS.LIST>      </GSTEWAYCONSIGNORADDRESS.LIST>" + 
						"\n\t\t\t\t\t<GSTEWAYCONSIGNEEADDRESS.LIST>      </GSTEWAYCONSIGNEEADDRESS.LIST>" + 
						"\n\t\t\t\t\t<TEMPGSTRATEDETAILS.LIST>      </TEMPGSTRATEDETAILS.LIST>"+
						"\n\t\t\t\t\t</VOUCHER>" + 
						"\n\t\t\t\t</TALLYMESSAGE>" + 
						"\n\t\t\t</REQUESTDATA>" + 
						"\n\t\t</IMPORTDATA>" + 
						"\n\t</BODY>" + 
						"\n</ENVELOPE>\n";

				String itemType = "";
				if((jobj.get("accMstCode").getAsString()).equals("R-C") || (jobj.get("accMstCode").getAsString()).equals("E-B")) {
					itemType = prop.getProperty("ROOMRENT");
				}else {
					itemType = prop.getProperty("OTHER");
				}

				if(invNO.equals(jobj.get("invoiceNo").getAsString())) {

					content = new StringBuilder(content.toString().replace(xmlString, ""));
					if(jobj.get("accMstCode").getAsString().equals("PAID-IN")  || jobj.get("accMstCode").getAsString().equals("DEPOSIT")) {

						summaryItems = summaryItems +"\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
								"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
								"\n\t\t\t\t\t\t<LEDGERNAME>"+corporateName+"</LEDGERNAME>" + 
								"\n\t\t\t\t\t\t<GSTCLASS/>" + 
								"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>" + 
								"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
								"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
								"\n\t\t\t\t\t\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>" + 
								"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>" + 
								"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
								"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
								"\n\t\t\t\t\t\t<AMOUNT>-"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
								"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<NAME>"+jobj.get("invoice").getAsString()+"</NAME>" + 
								"\n\t\t\t\t\t\t<BILLTYPE>New Ref</BILLTYPE>" + 
								"\n\t\t\t\t\t\t<TDSDEDUCTEEISSPECIALRATE>No</TDSDEDUCTEEISSPECIALRATE>" + 
								"\n\t\t\t\t\t\t<AMOUNT>-"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
								"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>        </INTERESTCOLLECTION.LIST>" + 
								"\n\t\t\t\t\t\t<STBILLCATEGORIES.LIST>        </STBILLCATEGORIES.LIST>" + 
								"\n\t\t\t\t\t\t</BILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
								"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
								"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";

					}
					else if(jobj.get("accMstCode").getAsString().equals("DISCOUNT")) {
						
						items = items + "\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
								"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
								"\n\t\t\t\t\t\t<LEDGERNAME>"+itemType+"</LEDGERNAME>" + 
								"\n\t\t\t\t\t\t<GSTCLASS/>" + 
								"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
								"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
								"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
								"\n\t\t\t\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
								"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
								"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
								"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
								"\n\t\t\t\t\t\t<AMOUNT>-"+jobj.get("baseAmount").getAsString()+"</AMOUNT>" + 
								"\n\t\t\t\t\t\t<VATEXPAMOUNT>-"+jobj.get("baseAmount").getAsString()+"</VATEXPAMOUNT>" + 
								"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
								"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
								"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";
						
					}
					else {

						/*items = items + "\n\t\t\t\t\t\t<ALLLEDGERENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t\t<LEDGERNAME>"+itemType+"</LEDGERNAME>" + 
									"\n\t\t\t\t\t\t\t<GSTCLASS/>" + 
									"\n\t\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
									"\n\t\t\t\t\t\t\t<AMOUNT>"+jobj.get("baseAmount").getAsString()+"</AMOUNT>" + 
									"\n\t\t\t\t\t\t</ALLLEDGERENTRIES.LIST>";*/
						items = items + "\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
								"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
								"\n\t\t\t\t\t\t<LEDGERNAME>"+itemType+"</LEDGERNAME>" + 
								"\n\t\t\t\t\t\t<GSTCLASS/>" + 
								"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
								"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
								"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
								"\n\t\t\t\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
								"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
								"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
								"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
								"\n\t\t\t\t\t\t<AMOUNT>"+jobj.get("baseAmount").getAsString()+"</AMOUNT>" + 
								"\n\t\t\t\t\t\t<VATEXPAMOUNT>"+jobj.get("baseAmount").getAsString()+"</VATEXPAMOUNT>" + 
								"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
								"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
								"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";
						if(jobj.get("tax1Amount").getAsDouble() != 0) {

							items=items+"\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
									"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
									"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX.LIST TYPE=\"Number\">" + 
									"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX>"+tax1Pc+"</BASICRATEOFINVOICETAX>" + 
									"\n\t\t\t\t\t\t</BASICRATEOFINVOICETAX.LIST>" + 
									"\n\t\t\t\t\t\t<ROUNDTYPE/>" + 
									"\n\t\t\t\t\t\t<LEDGERNAME>OUTPUT CGST "+tax1Pc+"%</LEDGERNAME>" + 
									"\n\t\t\t\t\t\t<GSTCLASS/>" + 
									"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
									"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
									"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
									"\n\t\t\t\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
									"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
									"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
									"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
									"\n\t\t\t\t\t\t<AMOUNT>"+jobj.get("tax1Amount").getAsString()+"</AMOUNT>" + 
									"\n\t\t\t\t\t\t<VATEXPAMOUNT>"+jobj.get("tax1Amount").getAsString()+"</VATEXPAMOUNT>" + 
									"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
									"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
									"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";
						}
						if(jobj.get("tax2Amount").getAsDouble() != 0) {

							items = items + "\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
									"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
									"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX.LIST TYPE=\"Number\">" + 
									"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX>"+tax2Pc+"</BASICRATEOFINVOICETAX>" + 
									"\n\t\t\t\t\t\t</BASICRATEOFINVOICETAX.LIST>" + 
									"\n\t\t\t\t\t\t<ROUNDTYPE/>" + 
									"\n\t\t\t\t\t\t<LEDGERNAME>OUTPUT SGST "+tax2Pc+"%</LEDGERNAME>" + 
									"\n\t\t\t\t\t\t<GSTCLASS/>" + 
									"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
									"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
									"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
									"\n\t\t\t\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
									"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
									"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
									"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
									"\n\t\t\t\t\t\t<AMOUNT>"+jobj.get("tax2Amount").getAsString()+"</AMOUNT>" + 
									"\n\t\t\t\t\t\t<VATEXPAMOUNT>"+jobj.get("tax2Amount").getAsString()+"</VATEXPAMOUNT>" + 
									"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
									"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
									"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";
						}
						if(gstNo.isEmpty()) {
							if(jobj.get("tax3Amount").getAsDouble() != 0) {

								items = items + "\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
										"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
										"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
										"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX.LIST TYPE=\"Number\">" + 
										"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX>"+tax3Pc+"</BASICRATEOFINVOICETAX>" + 
										"\n\t\t\t\t\t\t</BASICRATEOFINVOICETAX.LIST>" + 
										"\n\t\t\t\t\t\t<ROUNDTYPE/>" + 
										"\n\t\t\t\t\t\t<LEDGERNAME>OUTPUT KFC "+tax3Pc+"%</LEDGERNAME>" + 
										"\n\t\t\t\t\t\t<GSTCLASS/>" + 
										"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
										"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
										"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
										"\n\t\t\t\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
										"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
										"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
										"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
										"\n\t\t\t\t\t\t<AMOUNT>"+jobj.get("tax3Amount").getAsString()+"</AMOUNT>" + 
										"\n\t\t\t\t\t\t<VATEXPAMOUNT>"+jobj.get("tax3Amount").getAsString()+"</VATEXPAMOUNT>" + 
										"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
										"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
										"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
										"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";
							}
						}

					}

					//if(paymentMode.equals("COMPANY")) {
					xmlString = xmlStringHead + xmlBody+summaryItems + items + xmlStringEnd;
					/*}else {
							xmlString = xmlStringHead + items + summaryItems + xmlStringEnd;
						}*/

				}else {
					items = "";
					summaryItems = "";
					if(jobj.get("accMstCode").getAsString().equals("PAID-IN") || jobj.get("accMstCode").getAsString().equals("DEPOSIT")) {

						/*summaryItems = "\n\t\t\t\t\t\t<ALLLEDGERENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t\t<LEDGERNAME>"+corporateName+"</LEDGERNAME>" + 
									"\n\t\t\t\t\t\t\t<GSTCLASS/>" + 
									"\n\t\t\t\t\t\t\t<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>" + 
									"\n\t\t\t\t\t\t\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>" + 
									"\n\t\t\t\t\t\t\t<AMOUNT>-"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
									"\n\t\t\t\t\t\t</ALLLEDGERENTRIES.LIST>";*/
						summaryItems ="\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
								"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
								"\n\t\t\t\t\t\t<LEDGERNAME>"+corporateName+"</LEDGERNAME>" + 
								"\n\t\t\t\t\t\t<GSTCLASS/>" + 
								"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>" + 
								"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
								"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
								"\n\t\t\t\t\t\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>" + 
								"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>" + 
								"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
								"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
								"\n\t\t\t\t\t\t<AMOUNT>-"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
								"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<NAME>"+jobj.get("invoice").getAsString()+"</NAME>" + 
								"\n\t\t\t\t\t\t<BILLTYPE>New Ref</BILLTYPE>" + 
								"\n\t\t\t\t\t\t<TDSDEDUCTEEISSPECIALRATE>No</TDSDEDUCTEEISSPECIALRATE>" + 
								"\n\t\t\t\t\t\t<AMOUNT>-"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
								"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>        </INTERESTCOLLECTION.LIST>" + 
								"\n\t\t\t\t\t\t<STBILLCATEGORIES.LIST>        </STBILLCATEGORIES.LIST>" + 
								"\n\t\t\t\t\t\t</BILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
								"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
								"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";

					}else {

						//							items = items + "\n\t\t\t\t\t\t<ALLLEDGERENTRIES.LIST>" + 
						//									"\n\t\t\t\t\t\t\t<LEDGERNAME>"+itemType+"</LEDGERNAME>" +
						//									"\n\t\t\t\t\t\t\t<GSTCLASS/>" + 
						//									"\n\t\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
						//									"\n\t\t\t\t\t\t\t<AMOUNT>"+jobj.get("baseAmount").getAsString()+"</AMOUNT>" + 
						//									"\n\t\t\t\t\t\t</ALLLEDGERENTRIES.LIST>"; 
						items =  items+"\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
								"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
								"\n\t\t\t\t\t\t<LEDGERNAME>"+itemType+"</LEDGERNAME>" + 
								"\n\t\t\t\t\t\t<GSTCLASS/>" + 
								"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
								"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
								"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
								"\n\t\t\t\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
								"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
								"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
								"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
								"\n\t\t\t\t\t\t<AMOUNT>"+jobj.get("baseAmount").getAsString()+"</AMOUNT>" + 
								"\n\t\t\t\t\t\t<VATEXPAMOUNT>"+jobj.get("baseAmount").getAsString()+"</VATEXPAMOUNT>" + 
								"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
								"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
								"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
								"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
								"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
								"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
								"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";
						if(jobj.get("tax1Amount").getAsDouble() != 0) {
							/*	items = items + "\n\t\t\t\t\t\t<ALLLEDGERENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t\t<LEDGERNAME>"+prop.getProperty("CGST")+" "+tax1Pc+"%</LEDGERNAME>" + 
										"\n\t\t\t\t\t\t\t<GSTCLASS/>" + 
										"\n\t\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
										"\n\t\t\t\t\t\t\t<AMOUNT>"+jobj.get("tax1Amount").getAsString()+"</AMOUNT>" + 
										"\n\t\t\t\t\t\t</ALLLEDGERENTRIES.LIST>"; */
							items=items+"\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
									"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
									"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX.LIST TYPE=\"Number\">" + 
									"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX>"+tax1Pc+"</BASICRATEOFINVOICETAX>" + 
									"\n\t\t\t\t\t\t</BASICRATEOFINVOICETAX.LIST>" + 
									"\n\t\t\t\t\t\t<ROUNDTYPE/>" + 
									"\n\t\t\t\t\t\t<LEDGERNAME>OUTPUT CGST "+tax1Pc+"%</LEDGERNAME>" + 
									"\n\t\t\t\t\t\t<GSTCLASS/>" + 
									"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
									"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
									"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
									"\n\t\t\t\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
									"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
									"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
									"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
									"\n\t\t\t\t\t\t<AMOUNT>"+jobj.get("tax1Amount").getAsString()+"</AMOUNT>" + 
									"\n\t\t\t\t\t\t<VATEXPAMOUNT>"+jobj.get("tax1Amount").getAsString()+"</VATEXPAMOUNT>" + 
									"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
									"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
									"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";
						}
						if(jobj.get("tax2Amount").getAsDouble() != 0) {
							/*items = items + "\n\t\t\t\t\t\t<ALLLEDGERENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t\t<LEDGERNAME>"+prop.getProperty("SGST")+" "+tax2Pc+"%</LEDGERNAME>" + 
										"\n\t\t\t\t\t\t\t<GSTCLASS/>" + 
										"\n\t\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
										"\n\t\t\t\t\t\t\t<AMOUNT>"+jobj.get("tax2Amount").getAsString()+"</AMOUNT>" + 
										"\n\t\t\t\t\t\t</ALLLEDGERENTRIES.LIST>";*/
							items = items+"\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
									"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
									"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX.LIST TYPE=\"Number\">" + 
									"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX>"+tax2Pc+"</BASICRATEOFINVOICETAX>" + 
									"\n\t\t\t\t\t\t</BASICRATEOFINVOICETAX.LIST>" + 
									"\n\t\t\t\t\t\t<ROUNDTYPE/>" + 
									"\n\t\t\t\t\t\t<LEDGERNAME>OUTPUT SGST "+tax2Pc+"%</LEDGERNAME>" + 
									"\n\t\t\t\t\t\t<GSTCLASS/>" + 
									"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
									"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
									"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
									"\n\t\t\t\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
									"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
									"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
									"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
									"\n\t\t\t\t\t\t<AMOUNT>"+jobj.get("tax2Amount").getAsString()+"</AMOUNT>" + 
									"\n\t\t\t\t\t\t<VATEXPAMOUNT>"+jobj.get("tax2Amount").getAsString()+"</VATEXPAMOUNT>" + 
									"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
									"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
									"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
									"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
									"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
									"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
									"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";
						}
						if(gstNo.isEmpty()) {
							if(jobj.get("tax3Amount").getAsDouble() != 0) {
								/*items = items + "\n\t\t\t\t\t\t<ALLLEDGERENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t\t<LEDGERNAME>"+prop.getProperty("KFC")+""+tax3Pc+"%</LEDGERNAME>" + 
										"\n\t\t\t\t\t\t\t<GSTCLASS/>" + 
										"\n\t\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
										"\n\t\t\t\t\t\t\t<AMOUNT>"+jobj.get("tax3Amount").getAsString()+"</AMOUNT>" + 
										"\n\t\t\t\t\t\t</ALLLEDGERENTRIES.LIST>";*/
								items =  items+"\n\t\t\t\t\t\t<LEDGERENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
										"\n\t\t\t\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
										"\n\t\t\t\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
										"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX.LIST TYPE=\"Number\">" + 
										"\n\t\t\t\t\t\t<BASICRATEOFINVOICETAX>"+tax3Pc+"</BASICRATEOFINVOICETAX>" + 
										"\n\t\t\t\t\t\t</BASICRATEOFINVOICETAX.LIST>" + 
										"\n\t\t\t\t\t\t<ROUNDTYPE/>" + 
										"\n\t\t\t\t\t\t<LEDGERNAME>OUTPUT KFC "+tax3Pc+"%</LEDGERNAME>" + 
										"\n\t\t\t\t\t\t<GSTCLASS/>" + 
										"\n\t\t\t\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
										"\n\t\t\t\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
										"\n\t\t\t\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
										"\n\t\t\t\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
										"\n\t\t\t\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
										"\n\t\t\t\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
										"\n\t\t\t\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
										"\n\t\t\t\t\t\t<AMOUNT>"+jobj.get("tax3Amount").getAsString()+"</AMOUNT>" + 
										"\n\t\t\t\t\t\t<VATEXPAMOUNT>"+jobj.get("tax3Amount").getAsString()+"</VATEXPAMOUNT>" + 
										"\n\t\t\t\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
										"\n\t\t\t\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
										"\n\t\t\t\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
										"\n\t\t\t\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
										"\n\t\t\t\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
										"\n\t\t\t\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
										"\n\t\t\t\t\t\t</LEDGERENTRIES.LIST>";
							}

						}
					}

					//if(paymentMode.equals("COMPANY")) {
					//xmlString = xmlStringHead + xmlBody+summaryItems + items + xmlStringEnd;
					/*}else {
							xmlString = xmlStringHead + items + summaryItems + xmlStringEnd;
						}*/
					xmlString = xmlStringHead + xmlBody+summaryItems+ items + xmlStringEnd;
				}

				invNO = jobj.get("invoiceNo").getAsString();
				content = content.append(xmlString);
			}			

			Files.write(Paths.get(path), content.toString().getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

	@SuppressWarnings("unlikely-arg-type")
	public String ledgerExport(JsonArray ledgerData, Properties prop) {
		StringBuilder content = new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String filePath = prop.getProperty("exportTallyPath");
		String path = filePath+"\\tally"+dateFormat.format(today).toString()+".txt";
		String xmlString = "";
		String gstNo="";
		try {

			for(Object obj : ledgerData) {
				JsonObject jobj = (JsonObject) obj;
				/*xmlString ="<TALLYMESSAGE>" + 
						//	"\n\t<LEDGER NAME=\"Debtor Name1\">" + 
							"\n\t<LEDGER NAME = \""+jobj.get("ledgerName").getAsString() + "\"> " +
							"\n\t\t<NAME.LIST>" + 
							"\n\t\t\t<NAME>"+jobj.get("ledgerName").getAsString()+"</NAME>" + 
							"\n\t\t</NAME.LIST>" + 
							"\n\t\t<PARENT>Sundry Debtors</PARENT>" + 
							"\n\t</LEDGER>" + 
							"\n</TALLYMESSAGE>";*/
				if(jobj.get("gstNo").isJsonNull()) {
					gstNo="";
				}else {
					gstNo=jobj.get("gstNo").getAsString();
				}
				String ledgerName=jobj.get("ledgerName").getAsString();
				ledgerName = ledgerName.replaceAll("&(?!amp;)", " AND ");
				if(!gstNo.isEmpty()) {
					xmlString="\n<ENVELOPE>"+
							"\n\t<HEADER>"+
							"\n\t\t<TALLYREQUEST>Import Data</TALLYREQUEST>"+
							"\n\t</HEADER>"+
							"\n\t<BODY>"+
							"\n\t\t<IMPORTDATA>"+
							"\n\t\t\t<REQUESTDESC>"+
							"\n\t\t\t<REPORTNAME>Ledgers</REPORTNAME>"+
							"\n\t\t\t<STATICVARIABLES>"+
							"\n\t\t\t <SVCURRENTCOMPANY>Hikari Hotels Pvt.Ltd.</SVCURRENTCOMPANY>"+
							"\n\t\t\t</STATICVARIABLES>"+
							"\n\t\t\t</REQUESTDESC>"+
							"\n\t\t<REQUESTDATA>"+
							"\n\t\t\t<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"+
							"\n\t\t\t <LEDGER NAME=\""+ledgerName+"\" RESERVEDNAME=\"\">"+
							"\n\t\t\t <PARENT>Sundry Debtors</PARENT>"+	
							"\n\t\t\t <COUNTRYNAME>"+jobj.get("country").getAsString()+"</COUNTRYNAME>"+	
							"\n\t\t\t <PARTYGSTIN>"+jobj.get("gstNo").getAsString()+"</PARTYGSTIN>"+						  
							"\n\t\t\t<LANGUAGENAME.LIST>"+
							"\n\t\t\t<NAME.LIST TYPE=\"String\">"+
							"\n\t\t\t<NAME>"+ledgerName+"</NAME>"+
							"\n\t\t\t</NAME.LIST>"+
							"\n\t\t\t</LANGUAGENAME.LIST>"+
							"\n\t\t</LEDGER>"+
							"\n\t\t</TALLYMESSAGE>"+
							"\n\t\t</REQUESTDATA>"+
							"\n\t\t</IMPORTDATA>"+
							"\n\t</BODY>"+
							"\n</ENVELOPE>";
				}else {
					xmlString="\n<ENVELOPE>"+
							"\n\t<HEADER>"+
							"\n\t\t<TALLYREQUEST>Import Data</TALLYREQUEST>"+
							"\n\t</HEADER>"+
							"\n\t<BODY>"+
							"\n\t\t<IMPORTDATA>"+
							"\n\t\t\t<REQUESTDESC>"+
							"\n\t\t\t<REPORTNAME>Ledgers</REPORTNAME>"+
							"\n\t\t\t<STATICVARIABLES>"+
							"\n\t\t\t <SVCURRENTCOMPANY>Hikari Hotels Pvt.Ltd.</SVCURRENTCOMPANY>"+
							"\n\t\t\t</STATICVARIABLES>"+
							"\n\t\t\t</REQUESTDESC>"+
							"\n\t\t<REQUESTDATA>"+
							"\n\t\t\t<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"+
							"\n\t\t\t <LEDGER NAME=\""+ledgerName+"\" RESERVEDNAME=\"\">"+
							"\n\t\t\t <PARENT>Sundry Debtors</PARENT>"+	
							"\n\t\t\t <COUNTRYNAME>"+jobj.get("country").getAsString()+"</COUNTRYNAME>"+
							"\n\t\t\t<LANGUAGENAME.LIST>"+
							"\n\t\t\t<NAME.LIST TYPE=\"String\">"+
							"\n\t\t\t<NAME>"+ledgerName+"</NAME>"+
							"\n\t\t\t</NAME.LIST>"+
							"\n\t\t\t</LANGUAGENAME.LIST>"+
							"\n\t\t</LEDGER>"+
							"\n\t\t</TALLYMESSAGE>"+
							"\n\t\t</REQUESTDATA>"+
							"\n\t\t</IMPORTDATA>"+
							"\n\t</BODY>"+
							"\n</ENVELOPE>";
				}
				content = content.append(xmlString);
			}			

			Files.write(Paths.get(path), content.toString().getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

	public String contraExport(JsonArray contraData, Properties prop) throws IOException {
		StringBuilder content = new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String filePath = prop.getProperty("exportTallyPath");
		String path = filePath+"\\tally"+dateFormat.format(today).toString()+".txt";
		String xmlHead="";
		String xmlBody="";
		String xmlString="";

		for(Object obj : contraData) {
			JsonObject jobj = (JsonObject) obj;
			String[] dateString = (jobj.get("date").getAsString()).split("-");
			String date = dateString[0]+dateString[1]+dateString[2];
			xmlHead="\n<ENVELOPE>"+
					"\n\t<HEADER>"+
					"\n\t\t<TALLYREQUEST>Import Data</TALLYREQUEST>"+
					"\n\t</HEADER>"+
					"\n\t<BODY>"+
					"\n\t\t<IMPORTDATA>"+
					"\n\t\t<REQUESTDESC>"+
					"\n\t\t\t<REPORTNAME>Vouchers</REPORTNAME>"+
					"\n\t\t\t<STATICVARIABLES>"+
					"\n\t\t\t<SVCURRENTCOMPANY>HIKARI HOTELS PVT LTD  F.Y 2019-20- (from 1-Apr-2019)</SVCURRENTCOMPANY>"+
					"\n\t\t\t</STATICVARIABLES>"+
					"\n\t\t</REQUESTDESC>"+
					"\n\t\t<REQUESTDATA>"+
					"\n\t\t\t<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"+
					"\n\t\t\t<VOUCHER  VCHTYPE=\"Contra\" ACTION=\"Create\" OBJVIEW=\"Accounting Voucher View\">"+
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"+	
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"+	
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>"+						  
					"\n\t\t\t<DATE>"+date+"</DATE>"+
					"\n\t\t\t<NARRATION>"+jobj.get("narration").getAsString()+"</NARRATION>"+
					"\n\t\t\t<PARTYLEDGERNAME>"+prop.getProperty("BANK")+"</PARTYLEDGERNAME>"+
					"\n\t\t\t<VOUCHERTYPENAME>Contra</VOUCHERTYPENAME>"+
					"\n\t\t\t<VOUCHERNUMBER>"+jobj.get("voucherNo").getAsString()+"</VOUCHERNUMBER>"+
					"\n\t\t\t<CSTFORMISSUETYPE/>"+
					"\n\t\t\t<CSTFORMRECVTYPE/>"+
					"\n\t\t\t<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>" + 
					"\n\t\t\t<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"+
					"\n\t\t\t<VCHGSTCLASS/>" + 
					"\n\t\t\t<DIFFACTUALQTY>No</DIFFACTUALQTY>" + 
					"\n\t\t\t<ISMSTFROMSYNC>No</ISMSTFROMSYNC>" + 
					"\n\t\t\t<ASORIGINAL>No</ASORIGINAL>" + 
					"\n\t\t\t<AUDITED>No</AUDITED>" + 
					"\n\t\t\t<FORJOBCOSTING>No</FORJOBCOSTING>" + 
					"\n\t\t\t<ISOPTIONAL>No</ISOPTIONAL>"+
					"\n\t\t\t<EFFECTIVEDATE>"+date+"</EFFECTIVEDATE>" + 
					"\n\t\t\t<USEFOREXCISE>No</USEFOREXCISE>"+
					"\n\t\t\t<ISFORJOBWORKIN>No</ISFORJOBWORKIN>"+
					"\n\t\t\t<ALLOWCONSUMPTION>No</ALLOWCONSUMPTION>" + 
					"\n\t\t\t<USEFORINTEREST>No</USEFORINTEREST>" + 
					"\n\t\t\t<USEFORGAINLOSS>No</USEFORGAINLOSS>" + 
					"\n\t\t\t<USEFORGODOWNTRANSFER>No</USEFORGODOWNTRANSFER>" + 
					"\n\t\t\t<USEFORCOMPOUND>No</USEFORCOMPOUND>" + 
					"\n\t\t\t<USEFORSERVICETAX>No</USEFORSERVICETAX>" + 
					"\n\t\t\t<ISDELETED>No</ISDELETED>" + 
					"\n\t\t\t<ISONHOLD>No</ISONHOLD>" + 
					"\n\t\t\t<ISBOENOTAPPLICABLE>No</ISBOENOTAPPLICABLE>" + 
					"\n\t\t\t<ISEXCISEVOUCHER>No</ISEXCISEVOUCHER>" + 
					"\n\t\t\t<EXCISETAXOVERRIDE>No</EXCISETAXOVERRIDE>" + 
					"\n\t\t\t<USEFORTAXUNITTRANSFER>No</USEFORTAXUNITTRANSFER>" + 
					"\n\t\t\t<IGNOREPOSVALIDATION>No</IGNOREPOSVALIDATION>" + 
					"\n\t\t\t<EXCISEOPENING>No</EXCISEOPENING>" + 
					"\n\t\t\t<USEFORFINALPRODUCTION>No</USEFORFINALPRODUCTION>" + 
					"\n\t\t\t<ISTDSOVERRIDDEN>No</ISTDSOVERRIDDEN>" + 
					"\n\t\t\t<ISTCSOVERRIDDEN>No</ISTCSOVERRIDDEN>" + 
					"\n\t\t\t<ISTDSTCSCASHVCH>No</ISTDSTCSCASHVCH>" + 
					"\n\t\t\t<INCLUDEADVPYMTVCH>No</INCLUDEADVPYMTVCH>" + 
					"\n\t\t\t<ISSUBWORKSCONTRACT>No</ISSUBWORKSCONTRACT>" + 
					"\n\t\t\t<ISVATOVERRIDDEN>No</ISVATOVERRIDDEN>" + 
					"\n\t\t\t<IGNOREORIGVCHDATE>No</IGNOREORIGVCHDATE>" + 
					"\n\t\t\t<ISVATPAIDATCUSTOMS>No</ISVATPAIDATCUSTOMS>" + 
					"\n\t\t\t <ISDECLAREDTOCUSTOMS>No</ISDECLAREDTOCUSTOMS>" + 
					"\n\t\t\t<ISSERVICETAXOVERRIDDEN>No</ISSERVICETAXOVERRIDDEN>" + 
					"\n\t\t\t<ISISDVOUCHER>No</ISISDVOUCHER>" + 
					"\n\t\t\t<ISEXCISEOVERRIDDEN>No</ISEXCISEOVERRIDDEN>" + 
					"\n\t\t\t<ISEXCISESUPPLYVCH>No</ISEXCISESUPPLYVCH>" + 
					"\n\t\t\t<ISGSTOVERRIDDEN>No</ISGSTOVERRIDDEN>" + 
					"\n\t\t\t<GSTNOTEXPORTED>No</GSTNOTEXPORTED>" + 
					"\n\t\t\t<IGNOREGSTINVALIDATION>No</IGNOREGSTINVALIDATION>" + 
					"\n\t\t\t<ISGSTREFUND>No</ISGSTREFUND>" + 
					"\n\t\t\t<ISGSTSECSEVENAPPLICABLE>No</ISGSTSECSEVENAPPLICABLE>" + 
					"\n\t\t\t<ISVATPRINCIPALACCOUNT>No</ISVATPRINCIPALACCOUNT>" + 
					"\n\t\t\t<ISSHIPPINGWITHINSTATE>No</ISSHIPPINGWITHINSTATE>" + 
					"\n\t\t\t<ISOVERSEASTOURISTTRANS>No</ISOVERSEASTOURISTTRANS>" + 
					"\n\t\t\t<ISDESIGNATEDZONEPARTY>No</ISDESIGNATEDZONEPARTY>" + 
					"\n\t\t\t<ISCANCELLED>No</ISCANCELLED>" + 
					"\n\t\t\t<HASCASHFLOW>Yes</HASCASHFLOW>" + 
					"\n\t\t\t<ISPOSTDATED>No</ISPOSTDATED>" + 
					"\n\t\t\t<USETRACKINGNUMBER>No</USETRACKINGNUMBER>" + 
					"\n\t\t\t<ISINVOICE>No</ISINVOICE>" + 
					"\n\t\t\t<MFGJOURNAL>No</MFGJOURNAL>" + 
					"\n\t\t\t<HASDISCOUNTS>No</HASDISCOUNTS>" + 
					"\n\t\t\t<ASPAYSLIP>No</ASPAYSLIP>" + 
					"\n\t\t\t<ISCOSTCENTRE>No</ISCOSTCENTRE>" + 
					"\n\t\t\t<ISSTXNONREALIZEDVCH>No</ISSTXNONREALIZEDVCH>" + 
					"\n\t\t\t<ISEXCISEMANUFACTURERON>No</ISEXCISEMANUFACTURERON>" + 
					"\n\t\t\t<ISBLANKCHEQUE>No</ISBLANKCHEQUE>" + 
					"\n\t\t\t<ISVOID>No</ISVOID>" + 
					"\n\t\t\t<ORDERLINESTATUS>No</ORDERLINESTATUS>" + 
					"\n\t\t\t<VATISAGNSTCANCSALES>No</VATISAGNSTCANCSALES>" + 
					"\n\t\t\t<VATISPURCEXEMPTED>No</VATISPURCEXEMPTED>" + 
					"\n\t\t\t<ISVATRESTAXINVOICE>No</ISVATRESTAXINVOICE>" + 
					"\n\t\t\t<VATISASSESABLECALCVCH>No</VATISASSESABLECALCVCH>" + 
					"\n\t\t\t<ISVATDUTYPAID>Yes</ISVATDUTYPAID>" + 
					"\n\t\t\t<ISDELIVERYSAMEASCONSIGNEE>No</ISDELIVERYSAMEASCONSIGNEE>" + 
					"\n\t\t\t<ISDISPATCHSAMEASCONSIGNOR>No</ISDISPATCHSAMEASCONSIGNOR>" + 
					"\n\t\t\t<CHANGEVCHMODE>No</CHANGEVCHMODE>"+
					"\n\t\t\t<EWAYBILLDETAILS.LIST>      </EWAYBILLDETAILS.LIST>" + 
					"\n\t\t\t<EXCLUDEDTAXATIONS.LIST>      </EXCLUDEDTAXATIONS.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>      </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>      </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>      </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>      </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<SUPPLEMENTARYDUTYHEADDETAILS.LIST>      </SUPPLEMENTARYDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEDELNOTES.LIST>      </INVOICEDELNOTES.LIST>" + 
					"\n\t\t\t<INVOICEORDERLIST.LIST>      </INVOICEORDERLIST.LIST>" + 
					"\n\t\t\t<INVOICEINDENTLIST.LIST>      </INVOICEINDENTLIST.LIST>" + 
					"\n\t\t\t<ATTENDANCEENTRIES.LIST>      </ATTENDANCEENTRIES.LIST>" + 
					"\n\t\t\t<ORIGINVOICEDETAILS.LIST>      </ORIGINVOICEDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEEXPORTLIST.LIST>      </INVOICEEXPORTLIST.LIST>" + 
					"\n\t\t\t<ALLLEDGERENTRIES.LIST>";
			xmlBody="\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>"+
					"\n\t\t\t<LEDGERNAME>"+prop.getProperty("BANK")+"</LEDGERNAME>" + 
					"\n\t\t\t<GSTCLASS/>"+
					"\n\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
					"\n\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
					"\n\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
					"\n\t\t\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>" + 
					"\n\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
					"\n\t\t\t<AMOUNT>"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
					"\n\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>"+
					"\n\t\t\t<BANKALLOCATIONS.LIST>"+
					"\n\t\t\t<DATE>"+date+"</DATE>"+
					"\n\t\t\t<INSTRUMENTDATE>"+date+"</INSTRUMENTDATE>"+
					"\n\t\t\t<TRANSACTIONTYPE>Cheque</TRANSACTIONTYPE>" + 
					"\n\t\t\t<PAYMENTFAVOURING>Self</PAYMENTFAVOURING>"+
					"\n\t\t\t<STATUS>No</STATUS>" + 
					"\n\t\t\t<PAYMENTMODE>Transacted</PAYMENTMODE>" + 
					"\n\t\t\t<SECONDARYSTATUS/>" + 
					"\n\t\t\t<ISCONNECTEDPAYMENT>No</ISCONNECTEDPAYMENT>" + 
					"\n\t\t\t<ISSPLIT>No</ISSPLIT>" + 
					"\n\t\t\t<ISCONTRACTUSED>No</ISCONTRACTUSED>" + 
					"\n\t\t\t<ISACCEPTEDWITHWARNING>No</ISACCEPTEDWITHWARNING>" + 
					"\n\t\t\t<ISTRANSFORCED>No</ISTRANSFORCED>"+
					"\n\t\t\t<AMOUNT>"+jobj.get("amount").getAsString()+"</AMOUNT>"+
					"\n\t\t\t<CONTRACTDETAILS.LIST>        </CONTRACTDETAILS.LIST>" + 
					"\n\t\t\t<BANKSTATUSINFO.LIST>        </BANKSTATUSINFO.LIST>" + 
					"\n\t\t\t</BANKALLOCATIONS.LIST>" + 
					"\n\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
					"\n\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
					"\n\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
					"\n\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
					"\n\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
					"\n\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
					"\n\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
					"\n\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
					"\n\t\t\t</ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>"+
					"\n\t\t\t<LEDGERNAME>"+prop.getProperty("PETTYHEAD")+"</LEDGERNAME>"+
					"\n\t\t\t<GSTCLASS/>" + 
					"\n\t\t\t<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>" + 
					"\n\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
					"\n\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
					"\n\t\t\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>" + 
					"\n\t\t\t<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>" + 
					"\n\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
					"\n\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>"+
					"\n\t\t\t<AMOUNT>-"+jobj.get("amount").getAsString()+"</AMOUNT>"+
					"\n\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
					"\n\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
					"\n\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
					"\n\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
					"\n\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
					"\n\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
					"\n\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
					"\n\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
					"\n\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
					"\n\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
					"\n\t\t\t</ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<PAYROLLMODEOFPAYMENT.LIST>      </PAYROLLMODEOFPAYMENT.LIST>" + 
					"\n\t\t\t<ATTDRECORDS.LIST>      </ATTDRECORDS.LIST>" + 
					"\n\t\t\t<GSTEWAYCONSIGNORADDRESS.LIST>      </GSTEWAYCONSIGNORADDRESS.LIST>" + 
					"\n\t\t\t<GSTEWAYCONSIGNEEADDRESS.LIST>      </GSTEWAYCONSIGNEEADDRESS.LIST>" + 
					"\n\t\t\t<TEMPGSTRATEDETAILS.LIST>      </TEMPGSTRATEDETAILS.LIST>"+
					"\n\t\t</VOUCHER>" + 
					"\n\t\t</TALLYMESSAGE>" + 
					"\n\t</REQUESTDATA>" + 
					"\n\t</IMPORTDATA>"+
					"\n\t</BODY>"+
					"\n</ENVELOPE>";
			xmlString = xmlHead + xmlBody;
			content = content.append(xmlString);	
		}


		Files.write(Paths.get(path), content.toString().getBytes());
		return path;
	}

	public String PaymentExport(JsonArray contraData, Properties prop) throws IOException {
		StringBuilder content = new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String filePath = prop.getProperty("exportTallyPath");
		String path = filePath+"\\tally"+dateFormat.format(today).toString()+".txt";
		String xmlHead="";
		String xmlBody="";
		String xmlString="";
		for(Object obj : contraData) {
			JsonObject jobj = (JsonObject) obj;
			String[] dateString = (jobj.get("date").getAsString()).split("-");
			String date = dateString[0]+dateString[1]+dateString[2];
			xmlHead="\n<ENVELOPE>"+
					"\n\t<HEADER>"+
					"\n\t\t<TALLYREQUEST>Import Data</TALLYREQUEST>"+
					"\n\t</HEADER>"+
					"\n\t<BODY>"+
					"\n\t\t<IMPORTDATA>"+
					"\n\t\t<REQUESTDESC>"+
					"\n\t\t\t<REPORTNAME>Vouchers</REPORTNAME>"+
					"\n\t\t\t<STATICVARIABLES>"+
					"\n\t\t\t<SVCURRENTCOMPANY>HIKARI HOTELS PVT LTD  F.Y 2019-20- (from 1-Apr-2019)</SVCURRENTCOMPANY>"+
					"\n\t\t\t</STATICVARIABLES>"+
					"\n\t\t</REQUESTDESC>"+
					"\n\t\t<REQUESTDATA>"+
					"\n\t\t\t<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"+
					"\n\t\t\t<VOUCHER  VCHTYPE=\"Payment\" ACTION=\"Create\" OBJVIEW=\"Accounting Voucher View\">"+
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"+	
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"+	
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>"+						  
					"\n\t\t\t<DATE>"+date+"</DATE>"+
					"\n\t\t\t<NARRATION>"+jobj.get("narration").getAsString()+"</NARRATION>"+
					"\n\t\t\t<PARTYLEDGERNAME>"+prop.getProperty("PETTYHEAD")+"</PARTYLEDGERNAME>"+
					"\n\t\t\t<VOUCHERTYPENAME>Payment</VOUCHERTYPENAME>"+
					"\n\t\t\t<VOUCHERNUMBER>"+jobj.get("voucherNo").getAsString()+"</VOUCHERNUMBER>"+
					"\n\t\t\t<CSTFORMISSUETYPE/>"+
					"\n\t\t\t<CSTFORMRECVTYPE/>"+
					"\n\t\t\t<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>" + 
					"\n\t\t\t<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"+
					"\n\t\t\t<VCHGSTCLASS/>" + 
					"\n\t\t\t<DIFFACTUALQTY>No</DIFFACTUALQTY>" + 
					"\n\t\t\t<ISMSTFROMSYNC>No</ISMSTFROMSYNC>" + 
					"\n\t\t\t<ASORIGINAL>No</ASORIGINAL>" + 
					"\n\t\t\t<AUDITED>No</AUDITED>" + 
					"\n\t\t\t<FORJOBCOSTING>No</FORJOBCOSTING>" + 
					"\n\t\t\t<ISOPTIONAL>No</ISOPTIONAL>"+
					"\n\t\t\t<EFFECTIVEDATE>"+date+"</EFFECTIVEDATE>" + 
					"\n\t\t\t<USEFOREXCISE>No</USEFOREXCISE>"+
					"\n\t\t\t<ISFORJOBWORKIN>No</ISFORJOBWORKIN>" + 
					"\n\t\t\t<ALLOWCONSUMPTION>No</ALLOWCONSUMPTION>" + 
					"\n\t\t\t<USEFORINTEREST>No</USEFORINTEREST>" + 
					"\n\t\t\t<USEFORGAINLOSS>No</USEFORGAINLOSS>" + 
					"\n\t\t\t<USEFORGODOWNTRANSFER>No</USEFORGODOWNTRANSFER>" + 
					"\n\t\t\t<USEFORCOMPOUND>No</USEFORCOMPOUND>" + 
					"\n\t\t\t<USEFORSERVICETAX>No</USEFORSERVICETAX>" + 
					"\n\t\t\t<ISDELETED>No</ISDELETED>" + 
					"\n\t\t\t<ISONHOLD>No</ISONHOLD>" + 
					"\n\t\t\t<ISBOENOTAPPLICABLE>No</ISBOENOTAPPLICABLE>" + 
					"\n\t\t\t<ISEXCISEVOUCHER>No</ISEXCISEVOUCHER>" + 
					"\n\t\t\t<EXCISETAXOVERRIDE>No</EXCISETAXOVERRIDE>" + 
					"\n\t\t\t<USEFORTAXUNITTRANSFER>No</USEFORTAXUNITTRANSFER>" + 
					"\n\t\t\t<IGNOREPOSVALIDATION>No</IGNOREPOSVALIDATION>" + 
					"\n\t\t\t<EXCISEOPENING>No</EXCISEOPENING>" + 
					"\n\t\t\t<USEFORFINALPRODUCTION>No</USEFORFINALPRODUCTION>" + 
					"\n\t\t\t<ISTDSOVERRIDDEN>No</ISTDSOVERRIDDEN>" + 
					"\n\t\t\t<ISTCSOVERRIDDEN>No</ISTCSOVERRIDDEN>" + 
					"\n\t\t\t<ISTDSTCSCASHVCH>No</ISTDSTCSCASHVCH>" + 
					"\n\t\t\t<INCLUDEADVPYMTVCH>No</INCLUDEADVPYMTVCH>" + 
					"\n\t\t\t<ISSUBWORKSCONTRACT>No</ISSUBWORKSCONTRACT>" + 
					"\n\t\t\t<ISVATOVERRIDDEN>No</ISVATOVERRIDDEN>" + 
					"\n\t\t\t<IGNOREORIGVCHDATE>No</IGNOREORIGVCHDATE>" + 
					"\n\t\t\t<ISVATPAIDATCUSTOMS>No</ISVATPAIDATCUSTOMS>" + 
					"\n\t\t\t<ISDECLAREDTOCUSTOMS>No</ISDECLAREDTOCUSTOMS>" + 
					"\n\t\t\t<ISSERVICETAXOVERRIDDEN>No</ISSERVICETAXOVERRIDDEN>" + 
					"\n\t\t\t<ISISDVOUCHER>No</ISISDVOUCHER>" + 
					"\n\t\t\t<ISEXCISEOVERRIDDEN>No</ISEXCISEOVERRIDDEN>" + 
					"\n\t\t\t<ISEXCISESUPPLYVCH>No</ISEXCISESUPPLYVCH>" + 
					"\n\t\t\t<ISGSTOVERRIDDEN>No</ISGSTOVERRIDDEN>" + 
					"\n\t\t\t<GSTNOTEXPORTED>No</GSTNOTEXPORTED>" + 
					"\n\t\t\t<IGNOREGSTINVALIDATION>No</IGNOREGSTINVALIDATION>" + 
					"\n\t\t\t<ISGSTREFUND>No</ISGSTREFUND>" + 
					"\n\t\t\t<ISGSTSECSEVENAPPLICABLE>No</ISGSTSECSEVENAPPLICABLE>" + 
					"\n\t\t\t<ISVATPRINCIPALACCOUNT>No</ISVATPRINCIPALACCOUNT>" + 
					"\n\t\t\t<ISSHIPPINGWITHINSTATE>No</ISSHIPPINGWITHINSTATE>" + 
					"\n\t\t\t<ISOVERSEASTOURISTTRANS>No</ISOVERSEASTOURISTTRANS>" + 
					"\n\t\t\t<ISDESIGNATEDZONEPARTY>No</ISDESIGNATEDZONEPARTY>" + 
					"\n\t\t\t<ISCANCELLED>No</ISCANCELLED>" + 
					"\n\t\t\t<HASCASHFLOW>Yes</HASCASHFLOW>" + 
					"\n\t\t\t<ISPOSTDATED>No</ISPOSTDATED>" + 
					"\n\t\t\t<USETRACKINGNUMBER>No</USETRACKINGNUMBER>" + 
					"\n\t\t\t<ISINVOICE>No</ISINVOICE>" + 
					"\n\t\t\t<MFGJOURNAL>No</MFGJOURNAL>" + 
					"\n\t\t\t<HASDISCOUNTS>No</HASDISCOUNTS>" + 
					"\n\t\t\t<ASPAYSLIP>No</ASPAYSLIP>" + 
					"\n\t\t\t<ISCOSTCENTRE>No</ISCOSTCENTRE>" + 
					"\n\t\t\t<ISSTXNONREALIZEDVCH>No</ISSTXNONREALIZEDVCH>" + 
					"\n\t\t\t<ISEXCISEMANUFACTURERON>No</ISEXCISEMANUFACTURERON>" + 
					"\n\t\t\t<ISBLANKCHEQUE>No</ISBLANKCHEQUE>" + 
					"\n\t\t\t<ISVOID>No</ISVOID>" + 
					"\n\t\t\t<ORDERLINESTATUS>No</ORDERLINESTATUS>" + 
					"\n\t\t\t<VATISAGNSTCANCSALES>No</VATISAGNSTCANCSALES>" + 
					"\n\t\t\t<VATISPURCEXEMPTED>No</VATISPURCEXEMPTED>" + 
					"\n\t\t\t<ISVATRESTAXINVOICE>No</ISVATRESTAXINVOICE>" + 
					"\n\t\t\t<VATISASSESABLECALCVCH>No</VATISASSESABLECALCVCH>" + 
					"\n\t\t\t<ISVATDUTYPAID>Yes</ISVATDUTYPAID>" + 
					"\n\t\t\t<ISDELIVERYSAMEASCONSIGNEE>No</ISDELIVERYSAMEASCONSIGNEE>" + 
					"\n\t\t\t<ISDISPATCHSAMEASCONSIGNOR>No</ISDISPATCHSAMEASCONSIGNOR>" + 
					"\n\t\t\t<CHANGEVCHMODE>No</CHANGEVCHMODE>"+
					"\n\t\t\t<EWAYBILLDETAILS.LIST>      </EWAYBILLDETAILS.LIST>" + 
					"\n\t\t\t<EXCLUDEDTAXATIONS.LIST>      </EXCLUDEDTAXATIONS.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>      </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>      </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>      </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>      </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<SUPPLEMENTARYDUTYHEADDETAILS.LIST>      </SUPPLEMENTARYDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEDELNOTES.LIST>      </INVOICEDELNOTES.LIST>" + 
					"\n\t\t\t<INVOICEORDERLIST.LIST>      </INVOICEORDERLIST.LIST>" + 
					"\n\t\t\t<INVOICEINDENTLIST.LIST>      </INVOICEINDENTLIST.LIST>" + 
					"\n\t\t\t<ATTENDANCEENTRIES.LIST>      </ATTENDANCEENTRIES.LIST>" + 
					"\n\t\t\t<ORIGINVOICEDETAILS.LIST>      </ORIGINVOICEDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEEXPORTLIST.LIST>      </INVOICEEXPORTLIST.LIST>" + 
					"\n\t\t\t<ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>";
			xmlBody="\n\t\t\t<LEDGERNAME>"+jobj.get("name").getAsString()+"</LEDGERNAME>" + 
					"\n\t\t\t<GSTCLASS/>" + 
					"\n\t\t\t<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>" + 
					"\n\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
					"\n\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
					"\n\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
					"\n\t\t\t<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>" + 
					"\n\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
					"\n\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
					"\n\t\t\t<AMOUNT>-"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
					"\n\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
					"\n\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
					"\n\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
					"\n\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
					"\n\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
					"\n\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
					"\n\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
					"\n\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
					"\n\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
					"\n\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
					"\n\t\t\t</ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
					"\n\t\t\t<LEDGERNAME>"+prop.getProperty("PETTYHEAD")+"</LEDGERNAME>" + 
					"\n\t\t\t<GSTCLASS/>" + 
					"\n\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
					"\n\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
					"\n\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
					"\n\t\t\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>" + 
					"\n\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
					"\n\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
					"\n\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
					"\n\t\t\t<AMOUNT>"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
					"\n\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
					"\n\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
					"\n\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
					"\n\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
					"\n\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
					"\n\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
					"\n\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
					"\n\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
					"\n\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
					"\n\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
					"\n\t\t\t</ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<PAYROLLMODEOFPAYMENT.LIST>      </PAYROLLMODEOFPAYMENT.LIST>" + 
					"\n\t\t\t<ATTDRECORDS.LIST>      </ATTDRECORDS.LIST>" + 
					"\n\t\t\t<GSTEWAYCONSIGNORADDRESS.LIST>      </GSTEWAYCONSIGNORADDRESS.LIST>" + 
					"\n\t\t\t<GSTEWAYCONSIGNEEADDRESS.LIST>      </GSTEWAYCONSIGNEEADDRESS.LIST>" + 
					"\n\t\t\t<TEMPGSTRATEDETAILS.LIST>      </TEMPGSTRATEDETAILS.LIST>" + 
					"\n\t\t\t</VOUCHER>" + 
					"\n\t\t</TALLYMESSAGE>" + 
					"\n\t\t</REQUESTDATA>" + 
					"\n\t\t</IMPORTDATA>" + 
					"\n\t\t</BODY>" + 
					"\n</ENVELOPE>";
			xmlString = xmlHead + xmlBody;
			content = content.append(xmlString);
		}
		Files.write(Paths.get(path), content.toString().getBytes());
		return path;
	}

	public String PettyLedgerExport(JsonArray contraData, Properties prop) throws IOException {
		StringBuilder content = new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String filePath = prop.getProperty("exportTallyPath");
		String path = filePath+"\\tally"+dateFormat.format(today).toString()+".txt";
		String xmlHead="";
		String xmlBody="";
		String xmlString="";
		for(Object obj : contraData) {
			JsonObject jobj = (JsonObject) obj;
			String[] dateString = (jobj.get("date").getAsString()).split("-");
			String date = dateString[0]+dateString[1]+dateString[2];
			
			String voucheNname = jobj.get("voucher_name").getAsString() ;
			String name = xmlEscapeText(jobj.get("name").getAsString());
			String narration =xmlEscapeText( jobj.get("narration").getAsString());
			if(voucheNname.equals("PAYMENT")) {
				System.out.println(voucheNname);
			xmlHead="\n<ENVELOPE>"+
					"\n\t<HEADER>"+
					"\n\t\t<TALLYREQUEST>Import Data</TALLYREQUEST>"+
					"\n\t</HEADER>"+
					"\n\t<BODY>"+
					"\n\t\t<IMPORTDATA>"+
					"\n\t\t<REQUESTDESC>"+
					"\n\t\t\t<REPORTNAME>Vouchers</REPORTNAME>"+
					"\n\t\t\t<STATICVARIABLES>"+
					"\n\t\t\t<SVCURRENTCOMPANY>HIKARI HOTELS PVT LTD  F.Y 2019-20- (from 1-Apr-2019)</SVCURRENTCOMPANY>"+
					"\n\t\t\t</STATICVARIABLES>"+
					"\n\t\t</REQUESTDESC>"+
					"\n\t\t<REQUESTDATA>"+
					"\n\t\t\t<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"+
					"\n\t\t\t<VOUCHER  VCHTYPE=\"Payment\" ACTION=\"Create\" OBJVIEW=\"Accounting Voucher View\">"+
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"+	
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"+	
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>"+						  
					"\n\t\t\t<DATE>"+date+"</DATE>"+
					"\n\t\t\t<NARRATION>"+narration+"</NARRATION>"+
					"\n\t\t\t<PARTYLEDGERNAME>"+prop.getProperty("PETTYHEAD")+"</PARTYLEDGERNAME>"+
					"\n\t\t\t<VOUCHERTYPENAME>Payment</VOUCHERTYPENAME>"+
					"\n\t\t\t<VOUCHERNUMBER>"+jobj.get("voucherNo").getAsString()+"</VOUCHERNUMBER>"+
					"\n\t\t\t<CSTFORMISSUETYPE/>"+
					"\n\t\t\t<CSTFORMRECVTYPE/>"+
					"\n\t\t\t<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>" + 
					"\n\t\t\t<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"+
					"\n\t\t\t<VCHGSTCLASS/>" + 
					"\n\t\t\t<DIFFACTUALQTY>No</DIFFACTUALQTY>" + 
					"\n\t\t\t<ISMSTFROMSYNC>No</ISMSTFROMSYNC>" + 
					"\n\t\t\t<ASORIGINAL>No</ASORIGINAL>" + 
					"\n\t\t\t<AUDITED>No</AUDITED>" + 
					"\n\t\t\t<FORJOBCOSTING>No</FORJOBCOSTING>" + 
					"\n\t\t\t<ISOPTIONAL>No</ISOPTIONAL>"+
					"\n\t\t\t<EFFECTIVEDATE>"+date+"</EFFECTIVEDATE>" + 
					"\n\t\t\t<USEFOREXCISE>No</USEFOREXCISE>"+
					"\n\t\t\t<ISFORJOBWORKIN>No</ISFORJOBWORKIN>" + 
					"\n\t\t\t<ALLOWCONSUMPTION>No</ALLOWCONSUMPTION>" + 
					"\n\t\t\t<USEFORINTEREST>No</USEFORINTEREST>" + 
					"\n\t\t\t<USEFORGAINLOSS>No</USEFORGAINLOSS>" + 
					"\n\t\t\t<USEFORGODOWNTRANSFER>No</USEFORGODOWNTRANSFER>" + 
					"\n\t\t\t<USEFORCOMPOUND>No</USEFORCOMPOUND>" + 
					"\n\t\t\t<USEFORSERVICETAX>No</USEFORSERVICETAX>" + 
					"\n\t\t\t<ISDELETED>No</ISDELETED>" + 
					"\n\t\t\t<ISONHOLD>No</ISONHOLD>" + 
					"\n\t\t\t<ISBOENOTAPPLICABLE>No</ISBOENOTAPPLICABLE>" + 
					"\n\t\t\t<ISEXCISEVOUCHER>No</ISEXCISEVOUCHER>" + 
					"\n\t\t\t<EXCISETAXOVERRIDE>No</EXCISETAXOVERRIDE>" + 
					"\n\t\t\t<USEFORTAXUNITTRANSFER>No</USEFORTAXUNITTRANSFER>" + 
					"\n\t\t\t<IGNOREPOSVALIDATION>No</IGNOREPOSVALIDATION>" + 
					"\n\t\t\t<EXCISEOPENING>No</EXCISEOPENING>" + 
					"\n\t\t\t<USEFORFINALPRODUCTION>No</USEFORFINALPRODUCTION>" + 
					"\n\t\t\t<ISTDSOVERRIDDEN>No</ISTDSOVERRIDDEN>" + 
					"\n\t\t\t<ISTCSOVERRIDDEN>No</ISTCSOVERRIDDEN>" + 
					"\n\t\t\t<ISTDSTCSCASHVCH>No</ISTDSTCSCASHVCH>" + 
					"\n\t\t\t<INCLUDEADVPYMTVCH>No</INCLUDEADVPYMTVCH>" + 
					"\n\t\t\t<ISSUBWORKSCONTRACT>No</ISSUBWORKSCONTRACT>" + 
					"\n\t\t\t<ISVATOVERRIDDEN>No</ISVATOVERRIDDEN>" + 
					"\n\t\t\t<IGNOREORIGVCHDATE>No</IGNOREORIGVCHDATE>" + 
					"\n\t\t\t<ISVATPAIDATCUSTOMS>No</ISVATPAIDATCUSTOMS>" + 
					"\n\t\t\t<ISDECLAREDTOCUSTOMS>No</ISDECLAREDTOCUSTOMS>" + 
					"\n\t\t\t<ISSERVICETAXOVERRIDDEN>No</ISSERVICETAXOVERRIDDEN>" + 
					"\n\t\t\t<ISISDVOUCHER>No</ISISDVOUCHER>" + 
					"\n\t\t\t<ISEXCISEOVERRIDDEN>No</ISEXCISEOVERRIDDEN>" + 
					"\n\t\t\t<ISEXCISESUPPLYVCH>No</ISEXCISESUPPLYVCH>" + 
					"\n\t\t\t<ISGSTOVERRIDDEN>No</ISGSTOVERRIDDEN>" + 
					"\n\t\t\t<GSTNOTEXPORTED>No</GSTNOTEXPORTED>" + 
					"\n\t\t\t<IGNOREGSTINVALIDATION>No</IGNOREGSTINVALIDATION>" + 
					"\n\t\t\t<ISGSTREFUND>No</ISGSTREFUND>" + 
					"\n\t\t\t<ISGSTSECSEVENAPPLICABLE>No</ISGSTSECSEVENAPPLICABLE>" + 
					"\n\t\t\t<ISVATPRINCIPALACCOUNT>No</ISVATPRINCIPALACCOUNT>" + 
					"\n\t\t\t<ISSHIPPINGWITHINSTATE>No</ISSHIPPINGWITHINSTATE>" + 
					"\n\t\t\t<ISOVERSEASTOURISTTRANS>No</ISOVERSEASTOURISTTRANS>" + 
					"\n\t\t\t<ISDESIGNATEDZONEPARTY>No</ISDESIGNATEDZONEPARTY>" + 
					"\n\t\t\t<ISCANCELLED>No</ISCANCELLED>" + 
					"\n\t\t\t<HASCASHFLOW>Yes</HASCASHFLOW>" + 
					"\n\t\t\t<ISPOSTDATED>No</ISPOSTDATED>" + 
					"\n\t\t\t<USETRACKINGNUMBER>No</USETRACKINGNUMBER>" + 
					"\n\t\t\t<ISINVOICE>No</ISINVOICE>" + 
					"\n\t\t\t<MFGJOURNAL>No</MFGJOURNAL>" + 
					"\n\t\t\t<HASDISCOUNTS>No</HASDISCOUNTS>" + 
					"\n\t\t\t<ASPAYSLIP>No</ASPAYSLIP>" + 
					"\n\t\t\t<ISCOSTCENTRE>No</ISCOSTCENTRE>" + 
					"\n\t\t\t<ISSTXNONREALIZEDVCH>No</ISSTXNONREALIZEDVCH>" + 
					"\n\t\t\t<ISEXCISEMANUFACTURERON>No</ISEXCISEMANUFACTURERON>" + 
					"\n\t\t\t<ISBLANKCHEQUE>No</ISBLANKCHEQUE>" + 
					"\n\t\t\t<ISVOID>No</ISVOID>" + 
					"\n\t\t\t<ORDERLINESTATUS>No</ORDERLINESTATUS>" + 
					"\n\t\t\t<VATISAGNSTCANCSALES>No</VATISAGNSTCANCSALES>" + 
					"\n\t\t\t<VATISPURCEXEMPTED>No</VATISPURCEXEMPTED>" + 
					"\n\t\t\t<ISVATRESTAXINVOICE>No</ISVATRESTAXINVOICE>" + 
					"\n\t\t\t<VATISASSESABLECALCVCH>No</VATISASSESABLECALCVCH>" + 
					"\n\t\t\t<ISVATDUTYPAID>Yes</ISVATDUTYPAID>" + 
					"\n\t\t\t<ISDELIVERYSAMEASCONSIGNEE>No</ISDELIVERYSAMEASCONSIGNEE>" + 
					"\n\t\t\t<ISDISPATCHSAMEASCONSIGNOR>No</ISDISPATCHSAMEASCONSIGNOR>" + 
					"\n\t\t\t<CHANGEVCHMODE>No</CHANGEVCHMODE>"+
					"\n\t\t\t<EWAYBILLDETAILS.LIST>      </EWAYBILLDETAILS.LIST>" + 
					"\n\t\t\t<EXCLUDEDTAXATIONS.LIST>      </EXCLUDEDTAXATIONS.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>      </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>      </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>      </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>      </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<SUPPLEMENTARYDUTYHEADDETAILS.LIST>      </SUPPLEMENTARYDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEDELNOTES.LIST>      </INVOICEDELNOTES.LIST>" + 
					"\n\t\t\t<INVOICEORDERLIST.LIST>      </INVOICEORDERLIST.LIST>" + 
					"\n\t\t\t<INVOICEINDENTLIST.LIST>      </INVOICEINDENTLIST.LIST>" + 
					"\n\t\t\t<ATTENDANCEENTRIES.LIST>      </ATTENDANCEENTRIES.LIST>" + 
					"\n\t\t\t<ORIGINVOICEDETAILS.LIST>      </ORIGINVOICEDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEEXPORTLIST.LIST>      </INVOICEEXPORTLIST.LIST>" + 
					"\n\t\t\t<ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>";
			xmlBody="\n\t\t\t<LEDGERNAME>"+name+"</LEDGERNAME>" + 
					"\n\t\t\t<GSTCLASS/>" + 
					"\n\t\t\t<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>" + 
					"\n\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
					"\n\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
					"\n\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
					"\n\t\t\t<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>" + 
					"\n\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
					"\n\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
					"\n\t\t\t<AMOUNT>-"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
					"\n\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
					"\n\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
					"\n\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
					"\n\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
					"\n\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
					"\n\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
					"\n\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
					"\n\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
					"\n\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
					"\n\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
					"\n\t\t\t</ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
					"\n\t\t\t<LEDGERNAME>"+prop.getProperty("PETTYHEAD")+"</LEDGERNAME>" + 
					"\n\t\t\t<GSTCLASS/>" + 
					"\n\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
					"\n\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
					"\n\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
					"\n\t\t\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>" + 
					"\n\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
					"\n\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
					"\n\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
					"\n\t\t\t<AMOUNT>"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
					"\n\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
					"\n\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
					"\n\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
					"\n\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
					"\n\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
					"\n\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
					"\n\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
					"\n\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
					"\n\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
					"\n\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
					"\n\t\t\t</ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<PAYROLLMODEOFPAYMENT.LIST>      </PAYROLLMODEOFPAYMENT.LIST>" + 
					"\n\t\t\t<ATTDRECORDS.LIST>      </ATTDRECORDS.LIST>" + 
					"\n\t\t\t<GSTEWAYCONSIGNORADDRESS.LIST>      </GSTEWAYCONSIGNORADDRESS.LIST>" + 
					"\n\t\t\t<GSTEWAYCONSIGNEEADDRESS.LIST>      </GSTEWAYCONSIGNEEADDRESS.LIST>" + 
					"\n\t\t\t<TEMPGSTRATEDETAILS.LIST>      </TEMPGSTRATEDETAILS.LIST>" + 
					"\n\t\t\t</VOUCHER>" + 
					"\n\t\t</TALLYMESSAGE>" + 
					"\n\t\t</REQUESTDATA>" + 
					"\n\t\t</IMPORTDATA>" + 
					"\n\t\t</BODY>" + 
					"\n</ENVELOPE>";
			
			}
			else {
				
				xmlHead="\n<ENVELOPE>"+
						"\n\t<HEADER>"+
						"\n\t\t<TALLYREQUEST>Import Data</TALLYREQUEST>"+
						"\n\t</HEADER>"+
						"\n\t<BODY>"+
						"\n\t\t<IMPORTDATA>"+
						"\n\t\t<REQUESTDESC>"+
						"\n\t\t\t<REPORTNAME>Vouchers</REPORTNAME>"+
						"\n\t\t\t<STATICVARIABLES>"+
						"\n\t\t\t<SVCURRENTCOMPANY>HIKARI HOTELS PVT LTD  F.Y 2019-20- (from 1-Apr-2019)</SVCURRENTCOMPANY>"+
						"\n\t\t\t</STATICVARIABLES>"+
						"\n\t\t</REQUESTDESC>"+
						"\n\t\t<REQUESTDATA>"+
						"\n\t\t\t<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"+
						"\n\t\t\t<VOUCHER  VCHTYPE=\"Contra\" ACTION=\"Create\" OBJVIEW=\"Accounting Voucher View\">"+
						"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"+	
						"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"+	
						"\n\t\t\t</OLDAUDITENTRYIDS.LIST>"+						  
						"\n\t\t\t<DATE>"+date+"</DATE>"+
						"\n\t\t\t<NARRATION>"+narration+"</NARRATION>"+
						"\n\t\t\t<PARTYLEDGERNAME>"+prop.getProperty("BANK")+"</PARTYLEDGERNAME>"+
						"\n\t\t\t<VOUCHERTYPENAME>Contra</VOUCHERTYPENAME>"+
						"\n\t\t\t<VOUCHERNUMBER>"+jobj.get("voucherNo").getAsString()+"</VOUCHERNUMBER>"+
						"\n\t\t\t<CSTFORMISSUETYPE/>"+
						"\n\t\t\t<CSTFORMRECVTYPE/>"+
						"\n\t\t\t<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>" + 
						"\n\t\t\t<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"+
						"\n\t\t\t<VCHGSTCLASS/>" + 
						"\n\t\t\t<DIFFACTUALQTY>No</DIFFACTUALQTY>" + 
						"\n\t\t\t<ISMSTFROMSYNC>No</ISMSTFROMSYNC>" + 
						"\n\t\t\t<ASORIGINAL>No</ASORIGINAL>" + 
						"\n\t\t\t<AUDITED>No</AUDITED>" + 
						"\n\t\t\t<FORJOBCOSTING>No</FORJOBCOSTING>" + 
						"\n\t\t\t<ISOPTIONAL>No</ISOPTIONAL>"+
						"\n\t\t\t<EFFECTIVEDATE>"+date+"</EFFECTIVEDATE>" + 
						"\n\t\t\t<USEFOREXCISE>No</USEFOREXCISE>"+
						"\n\t\t\t<ISFORJOBWORKIN>No</ISFORJOBWORKIN>"+
						"\n\t\t\t<ALLOWCONSUMPTION>No</ALLOWCONSUMPTION>" + 
						"\n\t\t\t<USEFORINTEREST>No</USEFORINTEREST>" + 
						"\n\t\t\t<USEFORGAINLOSS>No</USEFORGAINLOSS>" + 
						"\n\t\t\t<USEFORGODOWNTRANSFER>No</USEFORGODOWNTRANSFER>" + 
						"\n\t\t\t<USEFORCOMPOUND>No</USEFORCOMPOUND>" + 
						"\n\t\t\t<USEFORSERVICETAX>No</USEFORSERVICETAX>" + 
						"\n\t\t\t<ISDELETED>No</ISDELETED>" + 
						"\n\t\t\t<ISONHOLD>No</ISONHOLD>" + 
						"\n\t\t\t<ISBOENOTAPPLICABLE>No</ISBOENOTAPPLICABLE>" + 
						"\n\t\t\t<ISEXCISEVOUCHER>No</ISEXCISEVOUCHER>" + 
						"\n\t\t\t<EXCISETAXOVERRIDE>No</EXCISETAXOVERRIDE>" + 
						"\n\t\t\t<USEFORTAXUNITTRANSFER>No</USEFORTAXUNITTRANSFER>" + 
						"\n\t\t\t<IGNOREPOSVALIDATION>No</IGNOREPOSVALIDATION>" + 
						"\n\t\t\t<EXCISEOPENING>No</EXCISEOPENING>" + 
						"\n\t\t\t<USEFORFINALPRODUCTION>No</USEFORFINALPRODUCTION>" + 
						"\n\t\t\t<ISTDSOVERRIDDEN>No</ISTDSOVERRIDDEN>" + 
						"\n\t\t\t<ISTCSOVERRIDDEN>No</ISTCSOVERRIDDEN>" + 
						"\n\t\t\t<ISTDSTCSCASHVCH>No</ISTDSTCSCASHVCH>" + 
						"\n\t\t\t<INCLUDEADVPYMTVCH>No</INCLUDEADVPYMTVCH>" + 
						"\n\t\t\t<ISSUBWORKSCONTRACT>No</ISSUBWORKSCONTRACT>" + 
						"\n\t\t\t<ISVATOVERRIDDEN>No</ISVATOVERRIDDEN>" + 
						"\n\t\t\t<IGNOREORIGVCHDATE>No</IGNOREORIGVCHDATE>" + 
						"\n\t\t\t<ISVATPAIDATCUSTOMS>No</ISVATPAIDATCUSTOMS>" + 
						"\n\t\t\t <ISDECLAREDTOCUSTOMS>No</ISDECLAREDTOCUSTOMS>" + 
						"\n\t\t\t<ISSERVICETAXOVERRIDDEN>No</ISSERVICETAXOVERRIDDEN>" + 
						"\n\t\t\t<ISISDVOUCHER>No</ISISDVOUCHER>" + 
						"\n\t\t\t<ISEXCISEOVERRIDDEN>No</ISEXCISEOVERRIDDEN>" + 
						"\n\t\t\t<ISEXCISESUPPLYVCH>No</ISEXCISESUPPLYVCH>" + 
						"\n\t\t\t<ISGSTOVERRIDDEN>No</ISGSTOVERRIDDEN>" + 
						"\n\t\t\t<GSTNOTEXPORTED>No</GSTNOTEXPORTED>" + 
						"\n\t\t\t<IGNOREGSTINVALIDATION>No</IGNOREGSTINVALIDATION>" + 
						"\n\t\t\t<ISGSTREFUND>No</ISGSTREFUND>" + 
						"\n\t\t\t<ISGSTSECSEVENAPPLICABLE>No</ISGSTSECSEVENAPPLICABLE>" + 
						"\n\t\t\t<ISVATPRINCIPALACCOUNT>No</ISVATPRINCIPALACCOUNT>" + 
						"\n\t\t\t<ISSHIPPINGWITHINSTATE>No</ISSHIPPINGWITHINSTATE>" + 
						"\n\t\t\t<ISOVERSEASTOURISTTRANS>No</ISOVERSEASTOURISTTRANS>" + 
						"\n\t\t\t<ISDESIGNATEDZONEPARTY>No</ISDESIGNATEDZONEPARTY>" + 
						"\n\t\t\t<ISCANCELLED>No</ISCANCELLED>" + 
						"\n\t\t\t<HASCASHFLOW>Yes</HASCASHFLOW>" + 
						"\n\t\t\t<ISPOSTDATED>No</ISPOSTDATED>" + 
						"\n\t\t\t<USETRACKINGNUMBER>No</USETRACKINGNUMBER>" + 
						"\n\t\t\t<ISINVOICE>No</ISINVOICE>" + 
						"\n\t\t\t<MFGJOURNAL>No</MFGJOURNAL>" + 
						"\n\t\t\t<HASDISCOUNTS>No</HASDISCOUNTS>" + 
						"\n\t\t\t<ASPAYSLIP>No</ASPAYSLIP>" + 
						"\n\t\t\t<ISCOSTCENTRE>No</ISCOSTCENTRE>" + 
						"\n\t\t\t<ISSTXNONREALIZEDVCH>No</ISSTXNONREALIZEDVCH>" + 
						"\n\t\t\t<ISEXCISEMANUFACTURERON>No</ISEXCISEMANUFACTURERON>" + 
						"\n\t\t\t<ISBLANKCHEQUE>No</ISBLANKCHEQUE>" + 
						"\n\t\t\t<ISVOID>No</ISVOID>" + 
						"\n\t\t\t<ORDERLINESTATUS>No</ORDERLINESTATUS>" + 
						"\n\t\t\t<VATISAGNSTCANCSALES>No</VATISAGNSTCANCSALES>" + 
						"\n\t\t\t<VATISPURCEXEMPTED>No</VATISPURCEXEMPTED>" + 
						"\n\t\t\t<ISVATRESTAXINVOICE>No</ISVATRESTAXINVOICE>" + 
						"\n\t\t\t<VATISASSESABLECALCVCH>No</VATISASSESABLECALCVCH>" + 
						"\n\t\t\t<ISVATDUTYPAID>Yes</ISVATDUTYPAID>" + 
						"\n\t\t\t<ISDELIVERYSAMEASCONSIGNEE>No</ISDELIVERYSAMEASCONSIGNEE>" + 
						"\n\t\t\t<ISDISPATCHSAMEASCONSIGNOR>No</ISDISPATCHSAMEASCONSIGNOR>" + 
						"\n\t\t\t<CHANGEVCHMODE>No</CHANGEVCHMODE>"+
						"\n\t\t\t<EWAYBILLDETAILS.LIST>      </EWAYBILLDETAILS.LIST>" + 
						"\n\t\t\t<EXCLUDEDTAXATIONS.LIST>      </EXCLUDEDTAXATIONS.LIST>" + 
						"\n\t\t\t<OLDAUDITENTRIES.LIST>      </OLDAUDITENTRIES.LIST>" + 
						"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>      </ACCOUNTAUDITENTRIES.LIST>" + 
						"\n\t\t\t<AUDITENTRIES.LIST>      </AUDITENTRIES.LIST>" + 
						"\n\t\t\t<DUTYHEADDETAILS.LIST>      </DUTYHEADDETAILS.LIST>" + 
						"\n\t\t\t<SUPPLEMENTARYDUTYHEADDETAILS.LIST>      </SUPPLEMENTARYDUTYHEADDETAILS.LIST>" + 
						"\n\t\t\t<INVOICEDELNOTES.LIST>      </INVOICEDELNOTES.LIST>" + 
						"\n\t\t\t<INVOICEORDERLIST.LIST>      </INVOICEORDERLIST.LIST>" + 
						"\n\t\t\t<INVOICEINDENTLIST.LIST>      </INVOICEINDENTLIST.LIST>" + 
						"\n\t\t\t<ATTENDANCEENTRIES.LIST>      </ATTENDANCEENTRIES.LIST>" + 
						"\n\t\t\t<ORIGINVOICEDETAILS.LIST>      </ORIGINVOICEDETAILS.LIST>" + 
						"\n\t\t\t<INVOICEEXPORTLIST.LIST>      </INVOICEEXPORTLIST.LIST>" + 
						"\n\t\t\t<ALLLEDGERENTRIES.LIST>";
				xmlBody="\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
						"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
						"\n\t\t\t</OLDAUDITENTRYIDS.LIST>"+
						"\n\t\t\t<LEDGERNAME>"+name+"</LEDGERNAME>" + 
						"\n\t\t\t<GSTCLASS/>"+
						"\n\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
						"\n\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
						"\n\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
						"\n\t\t\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>" + 
						"\n\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
						"\n\t\t\t<AMOUNT>"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
						"\n\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>"+
						"\n\t\t\t<BANKALLOCATIONS.LIST>"+
						"\n\t\t\t<DATE>"+date+"</DATE>"+
						"\n\t\t\t<INSTRUMENTDATE>"+date+"</INSTRUMENTDATE>"+
						"\n\t\t\t<TRANSACTIONTYPE>Cheque</TRANSACTIONTYPE>" + 
						"\n\t\t\t<PAYMENTFAVOURING>Self</PAYMENTFAVOURING>"+
						"\n\t\t\t<STATUS>No</STATUS>" + 
						"\n\t\t\t<PAYMENTMODE>Transacted</PAYMENTMODE>" + 
						"\n\t\t\t<SECONDARYSTATUS/>" + 
						"\n\t\t\t<ISCONNECTEDPAYMENT>No</ISCONNECTEDPAYMENT>" + 
						"\n\t\t\t<ISSPLIT>No</ISSPLIT>" + 
						"\n\t\t\t<ISCONTRACTUSED>No</ISCONTRACTUSED>" + 
						"\n\t\t\t<ISACCEPTEDWITHWARNING>No</ISACCEPTEDWITHWARNING>" + 
						"\n\t\t\t<ISTRANSFORCED>No</ISTRANSFORCED>"+
						"\n\t\t\t<AMOUNT>"+jobj.get("amount").getAsString()+"</AMOUNT>"+
						"\n\t\t\t<CONTRACTDETAILS.LIST>        </CONTRACTDETAILS.LIST>" + 
						"\n\t\t\t<BANKSTATUSINFO.LIST>        </BANKSTATUSINFO.LIST>" + 
						"\n\t\t\t</BANKALLOCATIONS.LIST>" + 
						"\n\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
						"\n\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
						"\n\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
						"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
						"\n\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
						"\n\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
						"\n\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
						"\n\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
						"\n\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
						"\n\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
						"\n\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
						"\n\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
						"\n\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
						"\n\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
						"\n\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
						"\n\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
						"\n\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
						"\n\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
						"\n\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
						"\n\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
						"\n\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
						"\n\t\t\t</ALLLEDGERENTRIES.LIST>" + 
						"\n\t\t\t<ALLLEDGERENTRIES.LIST>" + 
						"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
						"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
						"\n\t\t\t</OLDAUDITENTRYIDS.LIST>"+
						"\n\t\t\t<LEDGERNAME>"+prop.getProperty("PETTYHEAD")+"</LEDGERNAME>"+
						"\n\t\t\t<GSTCLASS/>" + 
						"\n\t\t\t<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>" + 
						"\n\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
						"\n\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
						"\n\t\t\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>" + 
						"\n\t\t\t<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>" + 
						"\n\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
						"\n\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>"+
						"\n\t\t\t<AMOUNT>-"+jobj.get("amount").getAsString()+"</AMOUNT>"+
						"\n\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
						"\n\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
						"\n\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
						"\n\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
						"\n\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
						"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
						"\n\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
						"\n\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
						"\n\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
						"\n\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
						"\n\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
						"\n\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
						"\n\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
						"\n\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
						"\n\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
						"\n\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
						"\n\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
						"\n\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
						"\n\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
						"\n\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
						"\n\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
						"\n\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
						"\n\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
						"\n\t\t\t</ALLLEDGERENTRIES.LIST>" + 
						"\n\t\t\t<PAYROLLMODEOFPAYMENT.LIST>      </PAYROLLMODEOFPAYMENT.LIST>" + 
						"\n\t\t\t<ATTDRECORDS.LIST>      </ATTDRECORDS.LIST>" + 
						"\n\t\t\t<GSTEWAYCONSIGNORADDRESS.LIST>      </GSTEWAYCONSIGNORADDRESS.LIST>" + 
						"\n\t\t\t<GSTEWAYCONSIGNEEADDRESS.LIST>      </GSTEWAYCONSIGNEEADDRESS.LIST>" + 
						"\n\t\t\t<TEMPGSTRATEDETAILS.LIST>      </TEMPGSTRATEDETAILS.LIST>"+
						"\n\t\t</VOUCHER>" + 
						"\n\t\t</TALLYMESSAGE>" + 
						"\n\t</REQUESTDATA>" + 
						"\n\t</IMPORTDATA>"+
						"\n\t</BODY>"+
						"\n</ENVELOPE>";
				
			}
			xmlString = xmlHead + xmlBody;
			content = content.append(xmlString);
		}
		Files.write(Paths.get(path), content.toString().getBytes());
		return path;
	}
	
	
	public String CreditCardExport(JsonArray contraData, Properties prop) throws IOException {
		StringBuilder content = new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String filePath = prop.getProperty("exportTallyPath");
		String path = filePath+"\\tally"+dateFormat.format(today).toString()+".txt";
		String xmlHead="";
		String xmlBody="";
		String xmlString="";
		for(Object obj : contraData) {
			JsonObject jobj = (JsonObject) obj;
			String[] dateString = (jobj.get("date").getAsString()).split("-");
			String date = dateString[0]+dateString[1]+dateString[2];
			String name = xmlEscapeText(jobj.get("name").getAsString());
			String narration =xmlEscapeText( jobj.get("narration").getAsString());
			xmlHead="\n<ENVELOPE>"+
					"\n\t<HEADER>"+
					"\n\t\t<TALLYREQUEST>Import Data</TALLYREQUEST>"+
					"\n\t</HEADER>"+
					"\n\t<BODY>"+
					"\n\t\t<IMPORTDATA>"+
					"\n\t\t<REQUESTDESC>"+
					"\n\t\t\t<REPORTNAME>Vouchers</REPORTNAME>"+
					"\n\t\t\t<STATICVARIABLES>"+
					"\n\t\t\t<SVCURRENTCOMPANY>HIKARI HOTELS PVT LTD  F.Y 2019-20- (from 1-Apr-2019)</SVCURRENTCOMPANY>"+
					"\n\t\t\t</STATICVARIABLES>"+
					"\n\t\t</REQUESTDESC>"+
					"\n\t\t<REQUESTDATA>"+
					"\n\t\t\t<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"+
					"\n\t\t\t<VOUCHER  VCHTYPE=\"Journal\" ACTION=\"Create\" OBJVIEW=\"Accounting Voucher View\">"+
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">"+	
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>"+	
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>"+						  
					"\n\t\t\t<DATE>"+date+"</DATE>"+
					"\n\t\t\t<NARRATION>"+narration+"</NARRATION>"+
					"\n\t\t\t<PARTYLEDGERNAME>"+prop.getProperty("PETTYHEADCARD")+"</PARTYLEDGERNAME>"+
					"\n\t\t\t<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME>"+
					"\n\t\t\t<VOUCHERNUMBER>"+jobj.get("voucherNo").getAsString()+"</VOUCHERNUMBER>"+
					"\n\t\t\t<CSTFORMISSUETYPE/>"+
					"\n\t\t\t<CSTFORMRECVTYPE/>"+
					"\n\t\t\t<FBTPAYMENTTYPE>Default</FBTPAYMENTTYPE>" + 
					"\n\t\t\t<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"+
					"\n\t\t\t<VCHGSTCLASS/>" + 
					"\n\t\t\t<DIFFACTUALQTY>No</DIFFACTUALQTY>" + 
					"\n\t\t\t<ISMSTFROMSYNC>No</ISMSTFROMSYNC>" + 
					"\n\t\t\t<ASORIGINAL>No</ASORIGINAL>" + 
					"\n\t\t\t<AUDITED>No</AUDITED>" + 
					"\n\t\t\t<FORJOBCOSTING>No</FORJOBCOSTING>" + 
					"\n\t\t\t<ISOPTIONAL>No</ISOPTIONAL>"+
					"\n\t\t\t<EFFECTIVEDATE>"+date+"</EFFECTIVEDATE>" + 
					"\n\t\t\t<USEFOREXCISE>No</USEFOREXCISE>"+
					"\n\t\t\t<ISFORJOBWORKIN>No</ISFORJOBWORKIN>" + 
					"\n\t\t\t<ALLOWCONSUMPTION>No</ALLOWCONSUMPTION>" + 
					"\n\t\t\t<USEFORINTEREST>No</USEFORINTEREST>" + 
					"\n\t\t\t<USEFORGAINLOSS>No</USEFORGAINLOSS>" + 
					"\n\t\t\t<USEFORGODOWNTRANSFER>No</USEFORGODOWNTRANSFER>" + 
					"\n\t\t\t<USEFORCOMPOUND>No</USEFORCOMPOUND>" + 
					"\n\t\t\t<USEFORSERVICETAX>No</USEFORSERVICETAX>" + 
					"\n\t\t\t<ISDELETED>No</ISDELETED>" + 
					"\n\t\t\t<ISONHOLD>No</ISONHOLD>" + 
					"\n\t\t\t<ISBOENOTAPPLICABLE>No</ISBOENOTAPPLICABLE>" + 
					"\n\t\t\t<ISEXCISEVOUCHER>No</ISEXCISEVOUCHER>" + 
					"\n\t\t\t<EXCISETAXOVERRIDE>No</EXCISETAXOVERRIDE>" + 
					"\n\t\t\t<USEFORTAXUNITTRANSFER>No</USEFORTAXUNITTRANSFER>" + 
					"\n\t\t\t<IGNOREPOSVALIDATION>No</IGNOREPOSVALIDATION>" + 
					"\n\t\t\t<EXCISEOPENING>No</EXCISEOPENING>" + 
					"\n\t\t\t<USEFORFINALPRODUCTION>No</USEFORFINALPRODUCTION>" + 
					"\n\t\t\t<ISTDSOVERRIDDEN>No</ISTDSOVERRIDDEN>" + 
					"\n\t\t\t<ISTCSOVERRIDDEN>No</ISTCSOVERRIDDEN>" + 
					"\n\t\t\t<ISTDSTCSCASHVCH>No</ISTDSTCSCASHVCH>" + 
					"\n\t\t\t<INCLUDEADVPYMTVCH>No</INCLUDEADVPYMTVCH>" + 
					"\n\t\t\t<ISSUBWORKSCONTRACT>No</ISSUBWORKSCONTRACT>" + 
					"\n\t\t\t<ISVATOVERRIDDEN>No</ISVATOVERRIDDEN>" + 
					"\n\t\t\t<IGNOREORIGVCHDATE>No</IGNOREORIGVCHDATE>" + 
					"\n\t\t\t<ISVATPAIDATCUSTOMS>No</ISVATPAIDATCUSTOMS>" + 
					"\n\t\t\t<ISDECLAREDTOCUSTOMS>No</ISDECLAREDTOCUSTOMS>" + 
					"\n\t\t\t<ISSERVICETAXOVERRIDDEN>No</ISSERVICETAXOVERRIDDEN>" + 
					"\n\t\t\t<ISISDVOUCHER>No</ISISDVOUCHER>" + 
					"\n\t\t\t<ISEXCISEOVERRIDDEN>No</ISEXCISEOVERRIDDEN>" + 
					"\n\t\t\t<ISEXCISESUPPLYVCH>No</ISEXCISESUPPLYVCH>" + 
					"\n\t\t\t<ISGSTOVERRIDDEN>No</ISGSTOVERRIDDEN>" + 
					"\n\t\t\t<GSTNOTEXPORTED>No</GSTNOTEXPORTED>" + 
					"\n\t\t\t<IGNOREGSTINVALIDATION>No</IGNOREGSTINVALIDATION>" + 
					"\n\t\t\t<ISGSTREFUND>No</ISGSTREFUND>" + 
					"\n\t\t\t<ISGSTSECSEVENAPPLICABLE>No</ISGSTSECSEVENAPPLICABLE>" + 
					"\n\t\t\t<ISVATPRINCIPALACCOUNT>No</ISVATPRINCIPALACCOUNT>" + 
					"\n\t\t\t<ISSHIPPINGWITHINSTATE>No</ISSHIPPINGWITHINSTATE>" + 
					"\n\t\t\t<ISOVERSEASTOURISTTRANS>No</ISOVERSEASTOURISTTRANS>" + 
					"\n\t\t\t<ISDESIGNATEDZONEPARTY>No</ISDESIGNATEDZONEPARTY>" + 
					"\n\t\t\t<ISCANCELLED>No</ISCANCELLED>" + 
					"\n\t\t\t<HASCASHFLOW>No</HASCASHFLOW>" + 
					"\n\t\t\t<ISPOSTDATED>No</ISPOSTDATED>" + 
					"\n\t\t\t<USETRACKINGNUMBER>No</USETRACKINGNUMBER>" + 
					"\n\t\t\t<ISINVOICE>No</ISINVOICE>" + 
					"\n\t\t\t<MFGJOURNAL>No</MFGJOURNAL>" + 
					"\n\t\t\t<HASDISCOUNTS>No</HASDISCOUNTS>" + 
					"\n\t\t\t<ASPAYSLIP>No</ASPAYSLIP>" + 
					"\n\t\t\t<ISCOSTCENTRE>No</ISCOSTCENTRE>" + 
					"\n\t\t\t<ISSTXNONREALIZEDVCH>No</ISSTXNONREALIZEDVCH>" + 
					"\n\t\t\t<ISEXCISEMANUFACTURERON>No</ISEXCISEMANUFACTURERON>" + 
					"\n\t\t\t<ISBLANKCHEQUE>No</ISBLANKCHEQUE>" + 
					"\n\t\t\t<ISVOID>No</ISVOID>" + 
					"\n\t\t\t<ORDERLINESTATUS>No</ORDERLINESTATUS>" + 
					"\n\t\t\t<VATISAGNSTCANCSALES>No</VATISAGNSTCANCSALES>" + 
					"\n\t\t\t<VATISPURCEXEMPTED>No</VATISPURCEXEMPTED>" + 
					"\n\t\t\t<ISVATRESTAXINVOICE>No</ISVATRESTAXINVOICE>" + 
					"\n\t\t\t<VATISASSESABLECALCVCH>No</VATISASSESABLECALCVCH>" + 
					"\n\t\t\t<ISVATDUTYPAID>Yes</ISVATDUTYPAID>" + 
					"\n\t\t\t<ISDELIVERYSAMEASCONSIGNEE>No</ISDELIVERYSAMEASCONSIGNEE>" + 
					"\n\t\t\t<ISDISPATCHSAMEASCONSIGNOR>No</ISDISPATCHSAMEASCONSIGNOR>" + 
					"\n\t\t\t<CHANGEVCHMODE>No</CHANGEVCHMODE>"+
					"\n\t\t\t<EWAYBILLDETAILS.LIST>      </EWAYBILLDETAILS.LIST>" + 
					"\n\t\t\t<EXCLUDEDTAXATIONS.LIST>      </EXCLUDEDTAXATIONS.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>      </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>      </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>      </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>      </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<SUPPLEMENTARYDUTYHEADDETAILS.LIST>      </SUPPLEMENTARYDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEDELNOTES.LIST>      </INVOICEDELNOTES.LIST>" + 
					"\n\t\t\t<INVOICEORDERLIST.LIST>      </INVOICEORDERLIST.LIST>" + 
					"\n\t\t\t<INVOICEINDENTLIST.LIST>      </INVOICEINDENTLIST.LIST>" + 
					"\n\t\t\t<ATTENDANCEENTRIES.LIST>      </ATTENDANCEENTRIES.LIST>" + 
					"\n\t\t\t<ORIGINVOICEDETAILS.LIST>      </ORIGINVOICEDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEEXPORTLIST.LIST>      </INVOICEEXPORTLIST.LIST>" + 
					"\n\t\t\t<ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>";
			xmlBody="\n\t\t\t<LEDGERNAME>"+name+"</LEDGERNAME>" + 
					"\n\t\t\t<GSTCLASS/>" + 
					"\n\t\t\t<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>" + 
					"\n\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
					"\n\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
					"\n\t\t\t<ISPARTYLEDGER>No</ISPARTYLEDGER>" + 
					"\n\t\t\t<ISLASTDEEMEDPOSITIVE>Yes</ISLASTDEEMEDPOSITIVE>" + 
					"\n\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
					"\n\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
					"\n\t\t\t<AMOUNT>-"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
					"\n\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
					"\n\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
					"\n\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
					"\n\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
					"\n\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
					"\n\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
					"\n\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
					"\n\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
					"\n\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
					"\n\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
					"\n\t\t\t</ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRYIDS.LIST TYPE=\"Number\">" + 
					"\n\t\t\t<OLDAUDITENTRYIDS>-1</OLDAUDITENTRYIDS>" + 
					"\n\t\t\t</OLDAUDITENTRYIDS.LIST>" + 
					"\n\t\t\t<LEDGERNAME>"+prop.getProperty("PETTYHEADCARD")+"</LEDGERNAME>" + 
					"\n\t\t\t<GSTCLASS/>" + 
					"\n\t\t\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>" + 
					"\n\t\t\t<LEDGERFROMITEM>No</LEDGERFROMITEM>" + 
					"\n\t\t\t<REMOVEZEROENTRIES>No</REMOVEZEROENTRIES>" + 
					"\n\t\t\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>" + 
					"\n\t\t\t<ISLASTDEEMEDPOSITIVE>No</ISLASTDEEMEDPOSITIVE>" + 
					"\n\t\t\t<ISCAPVATTAXALTERED>No</ISCAPVATTAXALTERED>" + 
					"\n\t\t\t<ISCAPVATNOTCLAIMED>No</ISCAPVATNOTCLAIMED>" + 
					"\n\t\t\t<AMOUNT>"+jobj.get("amount").getAsString()+"</AMOUNT>" + 
					"\n\t\t\t<SERVICETAXDETAILS.LIST>       </SERVICETAXDETAILS.LIST>" + 
					"\n\t\t\t<BANKALLOCATIONS.LIST>       </BANKALLOCATIONS.LIST>" + 
					"\n\t\t\t<BILLALLOCATIONS.LIST>       </BILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<INTERESTCOLLECTION.LIST>       </INTERESTCOLLECTION.LIST>" + 
					"\n\t\t\t<OLDAUDITENTRIES.LIST>       </OLDAUDITENTRIES.LIST>" + 
					"\n\t\t\t<ACCOUNTAUDITENTRIES.LIST>       </ACCOUNTAUDITENTRIES.LIST>" + 
					"\n\t\t\t<AUDITENTRIES.LIST>       </AUDITENTRIES.LIST>" + 
					"\n\t\t\t<INPUTCRALLOCS.LIST>       </INPUTCRALLOCS.LIST>" + 
					"\n\t\t\t<DUTYHEADDETAILS.LIST>       </DUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEDUTYHEADDETAILS.LIST>       </EXCISEDUTYHEADDETAILS.LIST>" + 
					"\n\t\t\t<RATEDETAILS.LIST>       </RATEDETAILS.LIST>" + 
					"\n\t\t\t<SUMMARYALLOCS.LIST>       </SUMMARYALLOCS.LIST>" + 
					"\n\t\t\t<STPYMTDETAILS.LIST>       </STPYMTDETAILS.LIST>" + 
					"\n\t\t\t<EXCISEPAYMENTALLOCATIONS.LIST>       </EXCISEPAYMENTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXBILLALLOCATIONS.LIST>       </TAXBILLALLOCATIONS.LIST>" + 
					"\n\t\t\t<TAXOBJECTALLOCATIONS.LIST>       </TAXOBJECTALLOCATIONS.LIST>" + 
					"\n\t\t\t<TDSEXPENSEALLOCATIONS.LIST>       </TDSEXPENSEALLOCATIONS.LIST>" + 
					"\n\t\t\t<VATSTATUTORYDETAILS.LIST>       </VATSTATUTORYDETAILS.LIST>" + 
					"\n\t\t\t<COSTTRACKALLOCATIONS.LIST>       </COSTTRACKALLOCATIONS.LIST>" + 
					"\n\t\t\t<REFVOUCHERDETAILS.LIST>       </REFVOUCHERDETAILS.LIST>" + 
					"\n\t\t\t<INVOICEWISEDETAILS.LIST>       </INVOICEWISEDETAILS.LIST>" + 
					"\n\t\t\t<VATITCDETAILS.LIST>       </VATITCDETAILS.LIST>" + 
					"\n\t\t\t<ADVANCETAXDETAILS.LIST>       </ADVANCETAXDETAILS.LIST>" + 
					"\n\t\t\t</ALLLEDGERENTRIES.LIST>" + 
					"\n\t\t\t<PAYROLLMODEOFPAYMENT.LIST>      </PAYROLLMODEOFPAYMENT.LIST>" + 
					"\n\t\t\t<ATTDRECORDS.LIST>      </ATTDRECORDS.LIST>" + 
					"\n\t\t\t<GSTEWAYCONSIGNORADDRESS.LIST>      </GSTEWAYCONSIGNORADDRESS.LIST>" + 
					"\n\t\t\t<GSTEWAYCONSIGNEEADDRESS.LIST>      </GSTEWAYCONSIGNEEADDRESS.LIST>" + 
					"\n\t\t\t<TEMPGSTRATEDETAILS.LIST>      </TEMPGSTRATEDETAILS.LIST>" + 
					"\n\t\t\t</VOUCHER>" + 
					"\n\t\t</TALLYMESSAGE>" + 
					"\n\t\t</REQUESTDATA>" + 
					"\n\t\t</IMPORTDATA>" + 
					"\n\t\t</BODY>" + 
					"\n</ENVELOPE>";
			
			
			xmlString = xmlHead + xmlBody;
			content = content.append(xmlString);
		}
		Files.write(Paths.get(path), content.toString().getBytes());
		return path;
	}
	
	public static String xmlEscapeText(String t) {
		   StringBuilder sb = new StringBuilder();
		   for(int i = 0; i < t.length(); i++){
		      char c = t.charAt(i);
		      switch(c){
		      case '<': sb.append("&lt;"); break;
		      case '>': sb.append("&gt;"); break;
		      case '\"': sb.append("&quot;"); break;
		      case '&': sb.append("&amp;"); break;
		      case '\'': sb.append("&apos;"); break;
		      default:
		         if(c>0x7e) {
		            sb.append("&#"+((int)c)+";");
		         }else
		            sb.append(c);
		      }
		   }
		   return sb.toString();
		}

}
