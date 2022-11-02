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

import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;

public class RevenueReportDetailExcel extends AbstractExcelView {


	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<Transaction> revenueDetailReport = reportTmpl.getRevenueReportDetail();
		

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

		int rowCount = 6;

		if (revenueDetailReport.size()!=0) {

			HSSFSheet sheet = workbook.createSheet("DETAIL");
			sheet.getPrintSetup().setLandscape(true);
			sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 3500);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 2800);
			sheet.setColumnWidth(6, 2000);
			sheet.setColumnWidth(7, 3000);
			sheet.setColumnWidth(8, 2000);
			sheet.setColumnWidth(9, 3000);
			sheet.setColumnWidth(10, 3000);

			Double baseTotal=0.00;
			Double tax1Total=0.00;
			Double tax2Total=0.00;
			
			Double TotalAmount=0.00;
			
			Double grandBaseTotal=0.00;
			Double grandTax1Total=0.00;
			Double grandTax2Total=0.00;
			Double grandTotalAmount=0.00;
		
			
		
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
				cellname.setCellValue("DETAIL REVENUE REPORT");
				cellname.setCellStyle(rptNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 10));

				HSSFRow tableheading = sheet.createRow(4);
				tableheading.setHeightInPoints(25);

				HSSFCell cellParticulars = tableheading.createCell(0);
				cellParticulars.setCellStyle(subheaderCellStyle);
				cellParticulars.setCellValue(" ");

				HSSFCell cellRoom = tableheading.createCell(1);
				cellRoom.setCellStyle(subheaderCellStyle);
				cellRoom.setCellValue(" ");

				HSSFCell cellGuest = tableheading.createCell(2);
				cellGuest.setCellStyle(subheaderCellStyle);
				cellGuest.setCellValue(" ");

				HSSFCell cellPax = tableheading.createCell(3);
				cellPax.setCellStyle(subheaderCellStyle);
				cellPax.setCellValue(" ");

				HSSFCell cellPosted = tableheading.createCell(4);
				cellPosted.setCellStyle(subheaderCellStyle);
				cellPosted.setCellValue(" ");
				
				HSSFCell cellBaseAm= tableheading.createCell(5);
				cellBaseAm.setCellStyle(subheaderCellStyle);
				cellBaseAm.setCellValue("Base ");

				HSSFCell cellTax1 = tableheading.createCell(6);
				cellTax1.setCellStyle(subheaderCellStyle);
				cellTax1.setCellValue((String) request.getSession().getAttribute("tax1Name"));
				sheet.addMergedRegion(new CellRangeAddress(4,4, 6, 7));
				
				HSSFCell cellTax2 = tableheading.createCell(8);
				cellTax2.setCellStyle(subheaderCellStyle);
				cellTax2.setCellValue((String) request.getSession().getAttribute("tax2Name"));
				sheet.addMergedRegion(new CellRangeAddress(4,4, 8, 9));

				HSSFCell cellAmount = tableheading.createCell(10);
				cellAmount.setCellStyle(subheaderCellStyle);
				cellAmount.setCellValue("Total");
				

				HSSFRow tableheading1 = sheet.createRow(5);
				tableheading1.setHeightInPoints(25);

				HSSFCell cellParticulars1 = tableheading1.createCell(0);
				cellParticulars1.setCellStyle(subheaderCellStyle1);
				cellParticulars1.setCellValue("Particulars");

				HSSFCell cellRoom1 = tableheading1.createCell(1);
				cellRoom1.setCellStyle(subheaderCellStyle1);
				cellRoom1.setCellValue("Room ");

				HSSFCell cellGuest1 = tableheading1.createCell(2);
				cellGuest1.setCellStyle(subheaderCellStyle1);
				cellGuest1.setCellValue("Invoice No.");

				HSSFCell cellPax1 = tableheading1.createCell(3);
				cellPax1.setCellStyle(subheaderCellStyle1);
				cellPax1.setCellValue("Guest");

				HSSFCell cellPosted1 = tableheading1.createCell(4);
				cellPosted1.setCellStyle(subheaderCellStyle1);
				cellPosted1.setCellValue("Pax ");
				
				HSSFCell cellBaseAm1= tableheading1.createCell(5);
				cellBaseAm1.setCellStyle(subheaderCellStyle1);
				cellBaseAm1.setCellValue("Amount");

				HSSFCell cellTax1Pc = tableheading1.createCell(6);
				cellTax1Pc.setCellStyle(subheaderCellStyle);
				cellTax1Pc.setCellValue("%");
				
				HSSFCell cellTax1Amt = tableheading1.createCell(7);
				cellTax1Amt.setCellStyle(subheaderCellStyle);
				cellTax1Amt.setCellValue("Amount");
				
				
				
				HSSFCell cellTax2Pc = tableheading1.createCell(8);
				cellTax2Pc.setCellStyle(subheaderCellStyle);
				cellTax2Pc.setCellValue("%");
			

				HSSFCell cellTax2Amt = tableheading1.createCell(9);
				cellTax2Amt.setCellStyle(subheaderCellStyle);
				cellTax2Amt.setCellValue("Amount");
				
				HSSFCell amt1 = tableheading1.createCell(10);
				amt1.setCellStyle(subheaderCellStyle1);
				amt1.setCellValue("Amount");
				
				
				
				
				String txnDatePrev = "";
				String txnDateCurr = "";
				
				int  paxTotal = 0;
				int paxGrandTotal = 0;
				String invoiceNoPrev = "";

				for (int i = 0; i < revenueDetailReport.size(); i++) {
					
					Transaction dailyRevenue=revenueDetailReport.get(i);
					//for date display	
					String txnDate = dailyRevenue.getTxn_date();
					String invoiceNo = dailyRevenue.getInvoiceNo();
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = sdf.parse(txnDate);

					String dateFormat = reportTmpl.getDateFormat();
					DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);

					
					
					if(i != 0 && !txnDatePrev.equals(txnDate)) {
						
						
						 
						 grandBaseTotal = grandBaseTotal + baseTotal;
						 grandTax1Total = grandTax1Total + tax1Total;
						 grandTax2Total = grandTax2Total + tax2Total;
						
						 grandTotalAmount = grandTotalAmount + TotalAmount;
						 
						 paxGrandTotal = paxGrandTotal + paxTotal;
						 
						 //add column for total
						 
							HSSFRow totalRow = sheet.createRow(rowCount);
							totalRow.setHeightInPoints(25);

							CellRangeAddress mergedCell = new CellRangeAddress(rowCount, rowCount, 0, 3);
							CellRangeAddress mergedCell1 = new CellRangeAddress(rowCount, rowCount, 6, 7);
							CellRangeAddress mergedCell2 = new CellRangeAddress(rowCount, rowCount, 8, 9);

							
							
						 
							HSSFCell cellTotalData = totalRow.createCell(0);
							cellTotalData.setCellStyle(totalrowCellStyle);
							cellTotalData.setCellValue("TOTAL:");
							sheet.addMergedRegion(mergedCell);
							
							HSSFCell cellTotalPax= totalRow.createCell(4);
							cellTotalPax.setCellStyle(totalrowCellStyle);
							cellTotalPax.setCellValue(paxTotal);							

							HSSFCell  HSSFCeltalDat = totalRow.createCell(5);
							HSSFCeltalDat.setCellStyle(totalrowCellStyle);
							HSSFCeltalDat.setCellValue(amountFormat.format(Double.valueOf(baseTotal)));

							HSSFCell cellTotalDat1 = totalRow.createCell(6);
							cellTotalDat1.setCellStyle(totalrowCellStyle);
							cellTotalDat1.setCellValue(amountFormat.format(Double.valueOf(tax1Total)));
							sheet.addMergedRegion(mergedCell1);
							
							HSSFCell cellTotalDat2 = totalRow.createCell(8);
							cellTotalDat2.setCellStyle(totalrowCellStyle);
							cellTotalDat2.setCellValue(amountFormat.format(Double.valueOf(tax2Total)));
							sheet.addMergedRegion(mergedCell2);
							

							HSSFCell cellTotalDat4 = totalRow.createCell(10);
							cellTotalDat4.setCellStyle(totalrowCellStyle);
							cellTotalDat4.setCellValue(amountFormat.format(Double.valueOf(Math.abs(TotalAmount))));

							RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
							RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
							RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
							RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
							
							RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
							RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
							RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
							RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
							
							RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell2, sheet, workbook);
							RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell2, sheet, workbook);
							RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell2, sheet, workbook);
							RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell2, sheet, workbook);

							
							rowCount = rowCount + 1;
						 
							baseTotal=0.00;
							tax1Total=0.00;
							tax2Total=0.00;
							
							TotalAmount=0.00;
							  paxTotal = 0;
					}
					
					
					if(!txnDatePrev.equals(txnDate))
					{
						
						txnDatePrev = txnDate;
						HSSFRow dateRow = sheet.createRow(rowCount);
						dateRow.setHeightInPoints(25);
						CellRangeAddress mergedCell1 = new CellRangeAddress(rowCount, rowCount, 0, 10);

						HSSFCell cellDateData = dateRow.createCell(0);
						cellDateData.setCellStyle(dateStyle);
						cellDateData.setCellValue("Date: ".concat(String.valueOf(simpleDateFormatHotelDate.format(date))));
						sheet.addMergedRegion(mergedCell1);

						RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
						RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
						RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
						RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);

						 rowCount = rowCount + 1;
						
					}
					
					
					//for data dispaly
					HSSFRow detailRow = sheet.createRow(rowCount);
					detailRow.setHeightInPoints(25);

					HSSFCell cellParticularsData = detailRow.createCell(0);
					cellParticularsData.setCellStyle(contentCellStyle2);
					cellParticularsData
							.setCellValue(dailyRevenue.getAccMstName());

					HSSFCell cellRoomdata = detailRow.createCell(1);
					cellRoomdata.setCellStyle(contentCellStyle2);
					cellRoomdata.setCellValue(dailyRevenue.getRoomNumber());

					HSSFCell cellGuestData = detailRow.createCell(2);
					cellGuestData.setCellStyle(contentCellStyle2);
					cellGuestData.setCellValue(dailyRevenue.getInvoiceNo());

					HSSFCell cellpax = detailRow.createCell(3);
					cellpax.setCellStyle(contentCellStyle2);
					cellpax.setCellValue(dailyRevenue.getFirstName() + " "+ dailyRevenue.getLastName());
					
					
					if(!invoiceNoPrev.equals(invoiceNo))
					{
						HSSFCell cellpax1 = detailRow.createCell(4);
						cellpax1.setCellStyle(numericCellStyle);
						cellpax1.setCellValue(dailyRevenue.getPax());
						paxTotal = paxTotal + dailyRevenue.getPax() ;
						
					}
					else {
						
						HSSFCell cellpax1 = detailRow.createCell(4);
						cellpax1.setCellStyle(numericCellStyle);
						cellpax1.setCellValue("");
						
						
					}
					
					

					HSSFCell cellbase_amountData = detailRow.createCell(5);
					cellbase_amountData.setCellStyle(numericCellStyle);
					cellbase_amountData.setCellValue(amountFormat.format(
							Double.valueOf(dailyRevenue.getBase_amount())));

					HSSFCell celltax1Pc = detailRow.createCell(6);
					celltax1Pc.setCellStyle(numericCellStyle);
					celltax1Pc.setCellValue(amountFormat.format(
							Double.valueOf(dailyRevenue.getTax1_pc())));

					HSSFCell celltax1Data = detailRow.createCell(7);
					celltax1Data.setCellStyle(numericCellStyle);
					celltax1Data.setCellValue(amountFormat.format(
							Double.valueOf(dailyRevenue.getTax1_amount())));

					HSSFCell celltax2Pc = detailRow.createCell(8);
					celltax2Pc.setCellStyle(numericCellStyle);
					celltax2Pc.setCellValue(amountFormat.format(
							Double.valueOf(dailyRevenue.getTax2_pc())));

					
					HSSFCell celltax2Data = detailRow.createCell(9);
					celltax2Data.setCellStyle(numericCellStyle);
					celltax2Data.setCellValue(amountFormat.format(
							Double.valueOf(dailyRevenue.getTax2_amount())));

					
					HSSFCell cellAmountData = detailRow.createCell(10);
					cellAmountData.setCellStyle(numericCellStyle);
					cellAmountData.setCellValue(amountFormat.format(
							Double.valueOf(dailyRevenue.getAmount())));

					
					baseTotal= baseTotal + dailyRevenue.getBase_amount();
					tax1Total= tax1Total + dailyRevenue.getTax1_amount();
					tax2Total= tax2Total + dailyRevenue.getTax2_amount();
					TotalAmount= TotalAmount + dailyRevenue.getAmount();
					
					
					rowCount = rowCount + 1;
					
					invoiceNoPrev = invoiceNo;

				}
				
				//for last total
				
				 grandBaseTotal = grandBaseTotal + baseTotal;
				 grandTax1Total = grandTax1Total + tax1Total;
				 grandTax2Total = grandTax2Total + tax2Total;
				
				 grandTotalAmount = grandTotalAmount + TotalAmount;
				 
				 paxGrandTotal = paxGrandTotal +paxTotal;

				
				HSSFRow totalRow = sheet.createRow(rowCount);
				totalRow.setHeightInPoints(25);

				CellRangeAddress mergedCell = new CellRangeAddress(rowCount, rowCount, 0, 3);

				CellRangeAddress mergedCell1 = new CellRangeAddress(rowCount, rowCount, 6, 7);

				CellRangeAddress mergedCell2 = new CellRangeAddress(rowCount, rowCount, 8, 9);

				HSSFCell cellTotalData = totalRow.createCell(0);
				cellTotalData.setCellStyle(totalrowCellStyle);
				cellTotalData.setCellValue("TOTAL:");
				sheet.addMergedRegion(mergedCell);
				
				HSSFCell cellTotalPax= totalRow.createCell(4);
				cellTotalPax.setCellStyle(totalrowCellStyle);
				cellTotalPax.setCellValue(paxTotal);							


				HSSFCell cellTotalDat = totalRow.createCell(5);
				cellTotalDat.setCellStyle(totalrowCellStyle);

				cellTotalDat.setCellValue(amountFormat.format(Double.valueOf(baseTotal)));

				HSSFCell cellTotalDat1 = totalRow.createCell(6);
				cellTotalDat1.setCellStyle(totalrowCellStyle);
				cellTotalDat1.setCellValue(amountFormat.format(Double.valueOf(tax1Total)));
				sheet.addMergedRegion(mergedCell1);
				HSSFCell cellTotalDat2 = totalRow.createCell(8);
				cellTotalDat2.setCellStyle(totalrowCellStyle);
				cellTotalDat2.setCellValue(amountFormat.format(Double.valueOf(tax2Total)));
				sheet.addMergedRegion(mergedCell2);
				

				HSSFCell cellTotalDat4 = totalRow.createCell(10);
				cellTotalDat4.setCellStyle(totalrowCellStyle);
				cellTotalDat4.setCellValue(amountFormat.format(Double.valueOf(Math.abs(TotalAmount))));

				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);

				
				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
				RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
				RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
				RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
				
				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell2, sheet, workbook);
				RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell2, sheet, workbook);
				RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell2, sheet, workbook);
				RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell2, sheet, workbook);
				
				rowCount = rowCount + 1;

			


			HSSFRow grandTotalRow = sheet.createRow(rowCount);
			grandTotalRow.setHeightInPoints(25);

			CellRangeAddress mergedGrandCell = new CellRangeAddress(rowCount, rowCount, 0, 3);

			CellRangeAddress mergedGrandCell1 = new CellRangeAddress(rowCount, rowCount, 6, 7);

			CellRangeAddress mergedGrandCell2 = new CellRangeAddress(rowCount, rowCount, 8, 9);
			
			HSSFCell cellgrandData = grandTotalRow.createCell(0);
			cellgrandData.setCellStyle(totalrowCellStyle);
			cellgrandData.setCellValue("GRAND TOTAL:");
			sheet.addMergedRegion(mergedGrandCell);

			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			
			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedGrandCell1, sheet, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedGrandCell1, sheet, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedGrandCell1, sheet, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedGrandCell1, sheet, workbook);
			
			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedGrandCell2, sheet, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedGrandCell2, sheet, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedGrandCell2, sheet, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedGrandCell2, sheet, workbook);

			
			
			HSSFCell cellTotalGrandPax= grandTotalRow.createCell(4);
			cellTotalGrandPax.setCellStyle(totalrowCellStyle);
			cellTotalGrandPax.setCellValue(paxGrandTotal);							

			
			HSSFCell cellBaseData = grandTotalRow.createCell(5);
			cellBaseData.setCellStyle(totalrowCellStyle);
			cellBaseData.setCellValue(amountFormat.format(Double.valueOf(grandBaseTotal)));
			
			
			HSSFCell cellTax1Data = grandTotalRow.createCell(6);
			cellTax1Data.setCellStyle(totalrowCellStyle);
			cellTax1Data.setCellValue(amountFormat.format(Double.valueOf(grandTax1Total)));
			sheet.addMergedRegion(mergedGrandCell1);
			
			HSSFCell cellTax2Data = grandTotalRow.createCell(8);
			cellTax2Data.setCellStyle(totalrowCellStyle);
			cellTax2Data.setCellValue(amountFormat.format(Double.valueOf(grandTax2Total)));
			sheet.addMergedRegion(mergedGrandCell2);

			HSSFCell cellGrandData = grandTotalRow.createCell(10);
			cellGrandData.setCellStyle(totalrowCellStyle);
			cellGrandData.setCellValue(amountFormat.format(Double.valueOf(Math.abs(grandTotalAmount))));
			
			sheet.createFreezePane(0,6);

			


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
				.concat(("DETAIL REVENUE REPORT").toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
