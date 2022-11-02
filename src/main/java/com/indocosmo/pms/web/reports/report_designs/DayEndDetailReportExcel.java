package com.indocosmo.pms.web.reports.report_designs;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.pms.web.reports.model.DayEndRport;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;

public class DayEndDetailReportExcel extends AbstractExcelView {


	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<DayEndRport> dayEndReport = reportTmpl.getDayEndReport();
		

		NumberFormat amountFormat = new DecimalFormat("#,##0.00");

		// Create a Font for styling header cells
		Font headNameFont = workbook.createFont();
		headNameFont.setFontName("Liberation Sans");
		headNameFont.setFontHeightInPoints((short) 13);
		headNameFont.setColor(IndexedColors.BLACK.getIndex());
		headNameFont.setBoldweight(headNameFont.BOLDWEIGHT_BOLD);

		CellStyle headNameCellStyle = workbook.createCellStyle();
		headNameCellStyle.setFont(headNameFont);
		headNameCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font detailFont = workbook.createFont();
		detailFont.setFontName("Liberation Sans");
		detailFont.setFontHeightInPoints((short) 9);
		detailFont.setColor(IndexedColors.BLACK.getIndex());
		detailFont.setBoldweight(detailFont.BOLDWEIGHT_BOLD);

		CellStyle detailFontCellStyle = workbook.createCellStyle();
		detailFontCellStyle.setFont(detailFont);
		detailFontCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		
		Font noDataFont = workbook.createFont();
		noDataFont.setFontName("Liberation Sans");

		noDataFont.setFontHeightInPoints((short) 12);
		noDataFont.setColor(IndexedColors.BLACK.getIndex());
		noDataFont.setBoldweight(noDataFont.BOLDWEIGHT_BOLD);

		CellStyle noDataCellStyle = workbook.createCellStyle();
		noDataCellStyle.setFont(noDataFont);
		noDataCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		
		Font headerFont = workbook.createFont();

		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Font tophdr = workbook.createFont();
		tophdr.setFontHeightInPoints((short) 9);
		tophdr.setColor(IndexedColors.BLACK.getIndex());
		tophdr.setBoldweight(tophdr.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle = workbook.createCellStyle();
		tophdrCellStyle.setFont(tophdr);
		tophdrCellStyle.setWrapText(true);
		tophdrCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font reprName = workbook.createFont();
		reprName.setFontName("Liberation Sans");

		reprName.setBoldweight((short) 15);
		reprName.setFontHeightInPoints((short) 16);
		reprName.setBoldweight(Font.BOLDWEIGHT_BOLD);
		reprName.setColor(IndexedColors.BLACK.getIndex());
		reprName.setUnderline(Font.U_SINGLE);
		CellStyle rptNameCellStyle = workbook.createCellStyle();
		rptNameCellStyle.setFont(reprName);
		rptNameCellStyle.setAlignment(CellStyle.ALIGN_LEFT);

		Font contentFont = workbook.createFont();
		contentFont.setFontName("Liberation Sans");

		contentFont.setFontHeightInPoints((short) 9);
		contentFont.setColor(IndexedColors.BLACK.getIndex());

		Font subheadFont = workbook.createFont();
		subheadFont.setFontName("Liberation Sans");

		subheadFont.setBoldweight(subheadFont.BOLDWEIGHT_BOLD);
		subheadFont.setFontHeightInPoints((short) 10);
		subheadFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle summaryCellStyle = workbook.createCellStyle();
		summaryCellStyle.setFont(subheadFont);
		summaryCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setFont(subheadFont);
		dateStyle.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle contentCellStyle2 = workbook.createCellStyle();
		contentCellStyle2.setFont(contentFont);
		contentCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_LEFT);

		Font headcontentFont = workbook.createFont();

		headcontentFont.setFontHeightInPoints((short) 9);
		headcontentFont.setColor(IndexedColors.BLACK.getIndex());
		headcontentFont.setBoldweight(headcontentFont.BOLDWEIGHT_BOLD);

		Font totalRowFont = workbook.createFont();

		totalRowFont.setFontHeightInPoints((short) 12);
		totalRowFont.setColor(IndexedColors.BLACK.getIndex());
		totalRowFont.setBoldweight(totalRowFont.BOLDWEIGHT_BOLD);

		CellStyle subheaderCellStyle = workbook.createCellStyle();
		subheaderCellStyle.setFont(headcontentFont);
		subheaderCellStyle.setWrapText(true);
		subheaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		subheaderCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		
		CellStyle subheaderCellStyle1 = workbook.createCellStyle();
		subheaderCellStyle1.setFont(headcontentFont);
		subheaderCellStyle1.setWrapText(true);
		subheaderCellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
		subheaderCellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
		subheaderCellStyle1.setBorderTop(CellStyle.BORDER_NONE);
		subheaderCellStyle1.setBorderRight(CellStyle.BORDER_THIN);
		subheaderCellStyle1.setBorderLeft(CellStyle.BORDER_THIN);

		CellStyle totalrowCellStyle = workbook.createCellStyle();
		totalrowCellStyle.setFont(totalRowFont);
		totalrowCellStyle.setWrapText(true);
		totalrowCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		totalrowCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		totalrowCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		totalrowCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		totalrowCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

		CellStyle numericCellStyle = workbook.createCellStyle();
		numericCellStyle.setFont(contentFont);
		numericCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		numericCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		int rowCount = 5;
		String dateFormatrt = reportTmpl.getDateFormat();
		DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormatrt);

		if (dayEndReport.size()!=0) {

			HSSFSheet sheet = workbook.createSheet("DETAIL");
			sheet.getPrintSetup().setLandscape(true);
			sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

			sheet.setColumnWidth(0, 1500);
			sheet.setColumnWidth(1, 4000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 4500);
			sheet.setColumnWidth(4, 5500);
			sheet.setColumnWidth(5, 5500);
			sheet.setColumnWidth(6, 1500);
			sheet.setColumnWidth(7, 7000);
			sheet.setColumnWidth(8, 3000);
			sheet.setColumnWidth(9, 3000);
			sheet.setColumnWidth(10, 3000);
			sheet.setColumnWidth(11, 3000);
			sheet.setColumnWidth(12, 3200);

				HSSFRow headnameRow = sheet.createRow(0);
				headnameRow.setHeightInPoints(15);
				HSSFCell companyCellname = headnameRow.createCell(0);
				companyCellname.setCellValue(reportTmpl.getCompanyname());
				companyCellname.setCellStyle(headNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

				HSSFRow buildingnameRow = sheet.createRow(1);
				buildingnameRow.setHeightInPoints(15);
				HSSFCell cellbuildingname = buildingnameRow.createCell(0);
				cellbuildingname.setCellValue(reportTmpl.getBuilding());
				cellbuildingname.setCellStyle(detailFontCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

				HSSFRow DetailnameRow = sheet.createRow(2);
				DetailnameRow.setHeightInPoints(15);
				HSSFCell DetailnameCell = DetailnameRow.createCell(0);
				DetailnameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
				DetailnameCell.setCellStyle(detailFontCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));


				HSSFRow nameRow = sheet.createRow(3);
				nameRow.setHeightInPoints(35);
				HSSFCell cellname = nameRow.createCell(0);
				cellname.setCellValue("DAY END REPORT");
				cellname.setCellStyle(rptNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 10));

				HSSFRow tableheading = sheet.createRow(4);
				tableheading.setHeightInPoints(25);

				HSSFCell cellParticulars = tableheading.createCell(0);
				cellParticulars.setCellStyle(subheaderCellStyle);
				cellParticulars.setCellValue("Sl No");

				HSSFCell cellRoom = tableheading.createCell(1);
				cellRoom.setCellStyle(subheaderCellStyle);
				cellRoom.setCellValue("Invoice No");

				HSSFCell cellGuest = tableheading.createCell(2);
				cellGuest.setCellStyle(subheaderCellStyle);
				cellGuest.setCellValue("Guest Name");

				HSSFCell cellPax = tableheading.createCell(3);
				cellPax.setCellStyle(subheaderCellStyle);
				cellPax.setCellValue("Room No");

				HSSFCell cellPosted = tableheading.createCell(4);
				cellPosted.setCellStyle(subheaderCellStyle);
				cellPosted.setCellValue("Check In");
				
				HSSFCell cellBaseAm= tableheading.createCell(5);
				cellBaseAm.setCellStyle(subheaderCellStyle);
				cellBaseAm.setCellValue("Check Out");

				HSSFCell cellTax1 = tableheading.createCell(6);
				cellTax1.setCellStyle(subheaderCellStyle);
				cellTax1.setCellValue("Pax");
				
				
				HSSFCell cellTax2 = tableheading.createCell(7);
				cellTax2.setCellStyle(subheaderCellStyle);
				cellTax2.setCellValue("Particulars");
				//sheet.addMergedRegion(new CellRangeAddress(4,4, 8, 9));

				HSSFCell pAmount = tableheading.createCell(8);
				pAmount.setCellStyle(subheaderCellStyle);
				pAmount.setCellValue("Payable Amount");
				

				HSSFCell Amountp = tableheading.createCell(9);
				Amountp.setCellStyle(subheaderCellStyle);
				Amountp.setCellValue("Amount Paid");
				
				
				HSSFCell Refund = tableheading.createCell(10);
				Refund.setCellStyle(subheaderCellStyle);
				Refund.setCellValue("Refund");
				

				HSSFCell Balace = tableheading.createCell(11);
				Balace.setCellStyle(subheaderCellStyle);
				Balace.setCellValue("Balace");
				

				HSSFCell cellPayM= tableheading.createCell(12);
				cellPayM.setCellStyle(subheaderCellStyle);
				cellPayM.setCellValue("Payment Mode");
				
				
				int count=1;
				
				Double payableAmt=0.00;
				Double amountPaid=0.00;
				Double balance=0.00;
				Double refundAmt=0.00;
				
				Double grandAmountPaid=0.00;
				Double grandPayableAmt=0.00;
				Double grandBalance=0.00;
				Double grandRefundAmt=0.00;
				
				String invoiceNoPrev = "";
				String folioNoPrev = "";
				String formattedcheckin = "";
				String formattedcheckout = "";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh.mm aa");
				
				for (int i = 0; i < dayEndReport.size(); i++) {
					
					DayEndRport dayEndRpt=dayEndReport.get(i);
					
					
					//for date display	
					String invoiceNo = dayEndRpt.getInvoiceNo();
					String folioNo = String.valueOf(dayEndRpt.getFolioNo());
					if(i != 0 && !folioNoPrev.equals(folioNo)) {
						
						count = count + 1;
						
					  }
					
					
					if(count > 1 && !folioNoPrev.equals(folioNo)) {
						
						
						
						
						
						grandAmountPaid=grandAmountPaid + amountPaid ;
						grandPayableAmt=grandPayableAmt + payableAmt;
						grandRefundAmt = grandRefundAmt +refundAmt;
						
							//for total ddispplay
						HSSFRow totalRow = sheet.createRow(rowCount);
						totalRow.setHeightInPoints(25);

						CellRangeAddress mergedCell = new CellRangeAddress(rowCount, rowCount, 0, 7);
						
					 
						HSSFCell cellTotalData = totalRow.createCell(0);
						cellTotalData.setCellStyle(totalrowCellStyle);
						cellTotalData.setCellValue("TOTAL:");
						sheet.addMergedRegion(mergedCell);
						
						HSSFCell cellTotalPax= totalRow.createCell(8);
						cellTotalPax.setCellStyle(totalrowCellStyle);
						cellTotalPax.setCellValue(amountFormat.format(Double.valueOf(payableAmt)));							

						HSSFCell  HSSFCeltalDat = totalRow.createCell(9);
						HSSFCeltalDat.setCellStyle(totalrowCellStyle);
						HSSFCeltalDat.setCellValue(amountFormat.format(Double.valueOf(amountPaid)));
						
						HSSFCell  HSSFCeltalrefundAmt = totalRow.createCell(10);
						HSSFCeltalrefundAmt.setCellStyle(totalrowCellStyle);
						HSSFCeltalrefundAmt.setCellValue(amountFormat.format(Double.valueOf(refundAmt)));

						HSSFCell cellTotalDat1 = totalRow.createCell(11);
						cellTotalDat1.setCellStyle(totalrowCellStyle);
						cellTotalDat1.setCellValue(amountFormat.format(Double.valueOf(payableAmt-(amountPaid-refundAmt))));
					
						
						HSSFCell cellTotalDat2 = totalRow.createCell(12);
						cellTotalDat2.setCellStyle(totalrowCellStyle);
						cellTotalDat2.setCellValue("");
					
						RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
						RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
						RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
						RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
						
						
						rowCount = rowCount + 1;
							
							
							amountPaid=0.00;
							payableAmt=0.00;
							balance=0.00;
							refundAmt = 0.00;
							
						 }
				
					
					//for data dispaly
					
					HSSFRow detailRow = sheet.createRow(rowCount);
					detailRow.setHeightInPoints(25);
					
					if(!folioNoPrev.equals(folioNo))
					{					
					

					HSSFCell cellParticularsData = detailRow.createCell(0);
					cellParticularsData.setCellStyle(contentCellStyle2);
					cellParticularsData.setCellValue(String.valueOf(count));

					HSSFCell cellRoomdata = detailRow.createCell(1);
					cellRoomdata.setCellStyle(contentCellStyle2);
					cellRoomdata.setCellValue(dayEndRpt.getInvoiceNo());

					HSSFCell cellGuestData = detailRow.createCell(2);
					cellGuestData.setCellStyle(contentCellStyle2);
					cellGuestData.setCellValue(dayEndRpt.getFirstName()+" "+dayEndRpt.getLastName());

					HSSFCell cellpax = detailRow.createCell(3);
					cellpax.setCellStyle(contentCellStyle2);
					cellpax.setCellValue(dayEndRpt.getRoomNumber() +" "+dayEndRpt.getRoom_tpye());
					
					/*java.util.Date dateCheckin = sdf1.parse(dayEndRpt.getCheckinDate());
					Date sqlDateCheckin =  new Date(dateCheckin.getTime());
					java.sql.Timestamp tstmpDeptDate = java.sql.Timestamp
							.valueOf(sqlDateCheckin + " " + (dayEndRpt.getCheckinTime()));
					formattedcheckin = dateFormat.format(tstmpDeptDate).toString();
					
					
*/
					HSSFCell cellbase_amountData = detailRow.createCell(4);
					cellbase_amountData.setCellStyle(numericCellStyle);
					cellbase_amountData.setCellValue(dayEndRpt.getCheckinDate()+" "+dayEndRpt.getCheckinTime());
					/*
					java.util.Date dateCheckout = sdf1.parse(dayEndRpt.getCheckOutDate());
					Date sqlDateCheckout = new Date(dateCheckout.getTime());
					java.sql.Timestamp tstmpcheckout = java.sql.Timestamp
							.valueOf(sqlDateCheckout + " " + (dayEndRpt.getCheckOutTime()));
					formattedcheckout = dateFormat.format(tstmpcheckout).toString();
					*/

					if(dayEndRpt.getCheckOutDate() == null) {
						
						HSSFCell celltax1Pc = detailRow.createCell(5);
						celltax1Pc.setCellStyle(numericCellStyle);
						celltax1Pc.setCellValue("");
						
					}
					else {
						
						HSSFCell celltax1Pc = detailRow.createCell(5);
						celltax1Pc.setCellStyle(numericCellStyle);
						celltax1Pc.setCellValue(dayEndRpt.getCheckOutDate()+" "+dayEndRpt.getCheckOutTime());
						
					}
					

					HSSFCell celltax1Data = detailRow.createCell(6);
					celltax1Data.setCellStyle(numericCellStyle);
					celltax1Data.setCellValue(dayEndRpt.getPax());
					
					}
					else {
						
						HSSFCell cellParticularsData = detailRow.createCell(0);
						cellParticularsData.setCellStyle(contentCellStyle2);
						cellParticularsData.setCellValue("");

						HSSFCell cellRoomdata = detailRow.createCell(1);
						cellRoomdata.setCellStyle(contentCellStyle2);
						cellRoomdata.setCellValue("");

						HSSFCell cellGuestData = detailRow.createCell(2);
						cellGuestData.setCellStyle(contentCellStyle2);
						cellGuestData.setCellValue("");

						HSSFCell cellpax = detailRow.createCell(3);
						cellpax.setCellStyle(contentCellStyle2);
						cellpax.setCellValue("");
						
					
						HSSFCell cellbase_amountData = detailRow.createCell(4);
						cellbase_amountData.setCellStyle(numericCellStyle);
						cellbase_amountData.setCellValue("");
					

						HSSFCell celltax1Pc = detailRow.createCell(5);
						celltax1Pc.setCellStyle(numericCellStyle);
						celltax1Pc.setCellValue("");

						HSSFCell celltax1Data = detailRow.createCell(6);
						celltax1Data.setCellStyle(numericCellStyle);
						celltax1Data.setCellValue("");
					}

					HSSFCell celltax2Pc = detailRow.createCell(7);
					celltax2Pc.setCellStyle(numericCellStyle);
					celltax2Pc.setCellValue(dayEndRpt.getPerticulars()+"("+String.valueOf(simpleDateFormatHotelDate.format(dayEndRpt.getTxn_date()))+")");
					
					
					if(dayEndRpt.getNettAmount()<0 && !dayEndRpt.getAccMstCode().equals("REFUND")) {
						
						HSSFCell cell2AmountData = detailRow.createCell(8);
						cell2AmountData.setCellStyle(numericCellStyle);
						cell2AmountData.setCellValue(amountFormat.format(Double.valueOf(dayEndRpt.getAmount())));

						
						HSSFCell celltax2Data = detailRow.createCell(9);
						celltax2Data.setCellStyle(numericCellStyle);
						celltax2Data.setCellValue("");

						
						HSSFCell cellAmountR = detailRow.createCell(10);
						cellAmountR.setCellStyle(numericCellStyle);
						cellAmountR.setCellValue("");
						
						HSSFCell cellAmountData = detailRow.createCell(11);
						cellAmountData.setCellStyle(numericCellStyle);
						cellAmountData.setCellValue("");

						
						HSSFCell celltax2Data1 = detailRow.createCell(12);
						celltax2Data1.setCellStyle(numericCellStyle);
						celltax2Data1.setCellValue("");

						payableAmt=payableAmt + dayEndRpt.getAmount();

						
					}
					
					else if(dayEndRpt.getAccMstCode().equals("REFUND")) {
						
						HSSFCell cell2AmountData = detailRow.createCell(8);
						cell2AmountData.setCellStyle(numericCellStyle);
						cell2AmountData.setCellValue("");

						
						HSSFCell celltax2Data = detailRow.createCell(9);
						celltax2Data.setCellStyle(numericCellStyle);
						celltax2Data.setCellValue("");

						
						HSSFCell cellAmountData = detailRow.createCell(10);
						cellAmountData.setCellStyle(numericCellStyle);
						cellAmountData.setCellValue((amountFormat.format(Double.valueOf(dayEndRpt.getAmount()))));
						
						HSSFCell cellAmountR = detailRow.createCell(11);
						cellAmountR.setCellStyle(numericCellStyle);
						cellAmountR.setCellValue("");

						
						HSSFCell celltax2Data1 = detailRow.createCell(12);
						celltax2Data1.setCellStyle(numericCellStyle);
						celltax2Data1.setCellValue(dayEndRpt.getPaymentMode());
						
						refundAmt=refundAmt + dayEndRpt.getAmount();
					}
					else {
						
						HSSFCell cell2AmountData = detailRow.createCell(8);
						cell2AmountData.setCellStyle(numericCellStyle);
						cell2AmountData.setCellValue("");

						
						HSSFCell celltax2Data = detailRow.createCell(9);
						celltax2Data.setCellStyle(numericCellStyle);
						celltax2Data.setCellValue(amountFormat.format(Double.valueOf(dayEndRpt.getAmount())));

						
						HSSFCell cellAmountData = detailRow.createCell(10);
						cellAmountData.setCellStyle(numericCellStyle);
						cellAmountData.setCellValue("");
						
						HSSFCell cellAmountR = detailRow.createCell(11);
						cellAmountR.setCellStyle(numericCellStyle);
						cellAmountR.setCellValue("");

						
						HSSFCell celltax2Data1 = detailRow.createCell(12);
						celltax2Data1.setCellStyle(numericCellStyle);
						celltax2Data1.setCellValue(dayEndRpt.getPaymentMode());

						amountPaid=amountPaid + dayEndRpt.getAmount();
					}
					
					
					rowCount = rowCount + 1;
					
				//	balance = payableAmt-amountPaid;
					
					
					//invoiceNoPrev = invoiceNo;
					folioNoPrev = folioNo;

				}
				
				//for last total
				
				grandAmountPaid=grandAmountPaid + amountPaid ;
				grandPayableAmt=grandPayableAmt + payableAmt;
				grandRefundAmt = grandRefundAmt +refundAmt;

				
				HSSFRow totalRow = sheet.createRow(rowCount);
				totalRow.setHeightInPoints(25);
				
				
				CellRangeAddress mergedCell = new CellRangeAddress(rowCount, rowCount, 0, 7);
				
				 
				HSSFCell cellTotalData = totalRow.createCell(0);
				cellTotalData.setCellStyle(totalrowCellStyle);
				cellTotalData.setCellValue("TOTAL:");
				sheet.addMergedRegion(mergedCell);
				
				HSSFCell cellTotalPax= totalRow.createCell(8);
				cellTotalPax.setCellStyle(totalrowCellStyle);
				cellTotalPax.setCellValue(amountFormat.format(Double.valueOf(payableAmt)));							

				HSSFCell  HSSFCeltalDat = totalRow.createCell(9);
				HSSFCeltalDat.setCellStyle(totalrowCellStyle);
				HSSFCeltalDat.setCellValue(amountFormat.format(Double.valueOf(amountPaid)));
				
				HSSFCell  HSSFCeltalRt = totalRow.createCell(10);
				HSSFCeltalRt.setCellStyle(totalrowCellStyle);
				HSSFCeltalRt.setCellValue(amountFormat.format(Double.valueOf(refundAmt)));

				HSSFCell cellTotalDat1 = totalRow.createCell(11);
				cellTotalDat1.setCellStyle(totalrowCellStyle);
				cellTotalDat1.setCellValue(amountFormat.format(Double.valueOf(payableAmt-(amountPaid-refundAmt))));
			
				
				HSSFCell cellTotalDat2 = totalRow.createCell(12);
				cellTotalDat2.setCellStyle(totalrowCellStyle);
				cellTotalDat2.setCellValue("");
			
				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				
				rowCount  = rowCount + 1;

			
			HSSFRow grandTotalRow = sheet.createRow(rowCount);
			grandTotalRow.setHeightInPoints(25);

			CellRangeAddress mergedGrandCell = new CellRangeAddress(rowCount, rowCount, 0, 7);

		
			
			HSSFCell cellgrandData = grandTotalRow.createCell(0);
			cellgrandData.setCellStyle(totalrowCellStyle);
			cellgrandData.setCellValue("GRAND TOTAL:");
			sheet.addMergedRegion(mergedGrandCell);

			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			
			
			HSSFCell cellTotalGrandPax= grandTotalRow.createCell(8);
			cellTotalGrandPax.setCellStyle(totalrowCellStyle);
			cellTotalGrandPax.setCellValue(amountFormat.format(Double.valueOf(grandPayableAmt)));							

			
			HSSFCell cellBaseData = grandTotalRow.createCell(9);
			cellBaseData.setCellStyle(totalrowCellStyle);
			cellBaseData.setCellValue(amountFormat.format(Double.valueOf(grandAmountPaid)));
			
			HSSFCell cellBaseDataR = grandTotalRow.createCell(10);
			cellBaseDataR.setCellStyle(totalrowCellStyle);
			cellBaseDataR.setCellValue(amountFormat.format(Double.valueOf(grandRefundAmt)));
			
			
			HSSFCell cellTax1Data = grandTotalRow.createCell(11);
			cellTax1Data.setCellStyle(totalrowCellStyle);
			cellTax1Data.setCellValue(amountFormat.format(Double.valueOf(grandPayableAmt-(grandAmountPaid-grandRefundAmt))));
		
			
			HSSFCell cellTax2Data = grandTotalRow.createCell(12);
			cellTax2Data.setCellStyle(totalrowCellStyle);
			cellTax2Data.setCellValue("");
		

		
			
			sheet.createFreezePane(0,5);
			
			
			//for summary
			
			HSSFSheet sheetn = workbook.createSheet("SUMMARY");
			sheetn.getPrintSetup().setLandscape(true);
			sheetn.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			
			sheetn.setColumnWidth(0, 9000);
			sheetn.setColumnWidth(1, 9000);
			
			
			HSSFRow headnameRowSUM= sheetn.createRow(0);
			headnameRowSUM.setHeightInPoints(15);
			HSSFCell companyCellnameSUM = headnameRowSUM.createCell(0);
			companyCellnameSUM.setCellValue(reportTmpl.getCompanyname());
			companyCellnameSUM.setCellStyle(headNameCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

			HSSFRow buildingnameRowSUM = sheetn.createRow(1);
			buildingnameRowSUM.setHeightInPoints(15);
			HSSFCell cellbuildingnameSUM = buildingnameRowSUM.createCell(0);
			cellbuildingnameSUM.setCellValue(reportTmpl.getBuilding());
			cellbuildingnameSUM.setCellStyle(detailFontCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));

			HSSFRow DetailnameRowSUM = sheetn.createRow(2);
			DetailnameRowSUM.setHeightInPoints(15);
			HSSFCell DetailnameCellSUM = DetailnameRowSUM.createCell(0);
			DetailnameCellSUM.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
			DetailnameCellSUM.setCellStyle(detailFontCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));

			HSSFRow nameReportRowSUM = sheetn.createRow(3);
			nameReportRowSUM.setHeightInPoints(35);
			HSSFCell cellname2 = nameReportRowSUM.createCell(0);
			cellname2.setCellValue("DAY END REPORT");
			cellname2.setCellStyle(rptNameCellStyle);
			sheetn.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));

			

			HSSFRow summaryRow = sheetn.createRow(4);
			summaryRow.setHeightInPoints(25);
			HSSFCell cellhead2 = summaryRow.createCell(0);
			cellhead2.setCellValue("SUMMARY");
			cellhead2.setCellStyle(summaryCellStyle);
			sheetn.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));

			HSSFRow tableheader = sheetn.createRow(5);
			tableheader.setHeightInPoints(25);

			HSSFCell cellDate = tableheader.createCell(0);
			cellDate.setCellStyle(subheaderCellStyle);
			cellDate.setCellValue("Total Cash Payment");

			HSSFCell cellAmount = tableheader.createCell(1);
			cellAmount.setCellStyle(numericCellStyle);
			cellAmount.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getCashPaymentTotal())));
			
			HSSFRow tableheader1 = sheetn.createRow(6);
			tableheader1.setHeightInPoints(25);

			HSSFCell cellDate1 = tableheader1.createCell(0);
			cellDate1.setCellStyle(subheaderCellStyle);
			cellDate1.setCellValue("Total Non Cash Payment");

			HSSFCell cellAmount1 = tableheader1.createCell(1);
			cellAmount1.setCellStyle(numericCellStyle);
			cellAmount1.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getNonCashPaymentTotal())));
			
			HSSFRow tableheader2 = sheetn.createRow(7);
			tableheader2.setHeightInPoints(25);

			HSSFCell cellDate2 = tableheader2.createCell(0);
			cellDate2.setCellStyle(subheaderCellStyle);
			cellDate2.setCellValue("Booking Cash Payment");

			HSSFCell cellAmount2 = tableheader2.createCell(1);
			cellAmount2.setCellStyle(numericCellStyle);
			cellAmount2.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getBookingCashPaymentTotal())));
			
			HSSFRow tableheader3 = sheetn.createRow(8);
			tableheader3.setHeightInPoints(25);

			HSSFCell cellDate3 = tableheader3.createCell(0);
			cellDate3.setCellStyle(subheaderCellStyle);
			cellDate3.setCellValue("Booking Non-Cash Payment");

			HSSFCell cellAmount3 = tableheader3.createCell(1);
			cellAmount3.setCellStyle(numericCellStyle);
			cellAmount3.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getNonCashPaymentTotal())));
			
			HSSFRow tableheader4 = sheetn.createRow(9);
			tableheader4.setHeightInPoints(25);

			HSSFCell cellDate4 = tableheader4.createCell(0);
			cellDate4.setCellStyle(subheaderCellStyle);
			cellDate4.setCellValue("Total Non-Cash Payment");

			HSSFCell cellAmount4 = tableheader4.createCell(1);
			cellAmount4.setCellStyle(numericCellStyle);
			cellAmount4.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getBookingNonCashPaymentTotal())));

		
			HSSFRow tableheader5 = sheetn.createRow(10);
			tableheader5.setHeightInPoints(25);

			HSSFCell cellDate5 = tableheader5.createCell(0);
			cellDate5.setCellStyle(subheaderCellStyle);
			cellDate5.setCellValue("Expense Total");

			HSSFCell cellAmount5 = tableheader5.createCell(1);
			cellAmount5.setCellStyle(numericCellStyle);
			cellAmount5.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getPettyExpenseTotal())));


			
			HSSFRow tableheader6 = sheetn.createRow(11);
			tableheader6.setHeightInPoints(25);

			HSSFCell cellDate6 = tableheader6.createCell(0);
			cellDate6.setCellStyle(subheaderCellStyle);
			cellDate6.setCellValue("Cash Opening Balance");

			HSSFCell cellAmount6 = tableheader6.createCell(1);
			cellAmount6.setCellStyle(numericCellStyle);
			cellAmount6.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getOpeningBal())));
			
			HSSFRow tableheader7 = sheetn.createRow(12);
			tableheader7.setHeightInPoints(25);

			HSSFCell cellDate7 = tableheader7.createCell(0);
			cellDate7.setCellStyle(subheaderCellStyle);
			cellDate7.setCellValue("Cash to Office");

			HSSFCell cellAmount7 = tableheader7.createCell(1);
			cellAmount7.setCellStyle(numericCellStyle);
			cellAmount7.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getCashToOfficeTotal())));
			
			HSSFRow tableheader8 = sheetn.createRow(13);
			tableheader8.setHeightInPoints(25);

			HSSFCell cellDate8 = tableheader8.createCell(0);
			cellDate8.setCellStyle(subheaderCellStyle);
			cellDate8.setCellValue("Total of Advance");

			HSSFCell cellAmount8 = tableheader8.createCell(1);
			cellAmount8.setCellStyle(numericCellStyle);
			cellAmount8.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getDepositTotal())));

			HSSFRow tableheader9 = sheetn.createRow(14);
			tableheader9.setHeightInPoints(25);

			HSSFCell cellDate9 = tableheader9.createCell(0);
			cellDate9.setCellStyle(subheaderCellStyle);
			cellDate9.setCellValue("Total Food Cost");

			HSSFCell cellAmount9 = tableheader9.createCell(1);
			cellAmount9.setCellStyle(numericCellStyle);
			cellAmount9.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getFoodCostTotal())));
			
			/*HSSFRow tableheader10 = sheetn.createRow(15);
			tableheader10.setHeightInPoints(25);

			HSSFCell cellDate10 = tableheader10.createCell(0);
			cellDate10.setCellStyle(subheaderCellStyle);
			cellDate10.setCellValue("Total Refund Amount");

			HSSFCell cellAmount10 = tableheader10.createCell(1);
			cellAmount10.setCellStyle(subheaderCellStyle);
			cellAmount10.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getRefundTotal())));*/
			
			HSSFRow tableheader11 = sheetn.createRow(15);
			tableheader11.setHeightInPoints(25);

			HSSFCell cellDate11 = tableheader11.createCell(0);
			cellDate11.setCellStyle(subheaderCellStyle);
			cellDate11.setCellValue("Total Complementary Amount");

			HSSFCell cellAmount11 = tableheader11.createCell(1);
			cellAmount11.setCellStyle(numericCellStyle);
			cellAmount11.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getComplementaryTotal())));
			
			HSSFRow tableheader12 = sheetn.createRow(16);
			tableheader12.setHeightInPoints(25);

			HSSFCell cellDate12 = tableheader12.createCell(0);
			cellDate12.setCellStyle(subheaderCellStyle);
			cellDate12.setCellValue("Petty Contra Amount");

			HSSFCell cellAmount12 = tableheader12.createCell(1);
			cellAmount12.setCellStyle(numericCellStyle);
			cellAmount12.setCellValue(amountFormat.format(Double.valueOf(reportTmpl.getContraTotal())));




		
			HSSFRow tableheader13 = sheetn.createRow(17);
			tableheader13.setHeightInPoints(25);

			HSSFCell cellDate13 = tableheader13.createCell(0);
			cellDate13.setCellStyle(subheaderCellStyle);
			cellDate13.setCellValue("Balance cash in hand");
			Double balanceinhand = reportTmpl.getOpeningBal() + reportTmpl.getCashPaymentTotal()+reportTmpl.getBookingCashPaymentTotal()+reportTmpl.getPettyclosingAmount();
			

			HSSFCell cellAmount13 = tableheader13.createCell(1);
			cellAmount13.setCellStyle(subheaderCellStyle);
			cellAmount13.setCellValue(amountFormat.format(Double.valueOf(balanceinhand)));

		
		} 
		//no data available
		else {

			HSSFSheet sheet = workbook.createSheet("sheet");
			sheet.getPrintSetup().setLandscape(true);
			sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 6));
		}
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday = sdf.format(today);
		
		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat(("DAY END REPORT").toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
