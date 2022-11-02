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
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.transaction.model.Transaction;

public class B2BReportExcel extends AbstractExcelView {


	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<Transaction> b2bReport = reportTmpl.getB2BReport();
		

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

		noDataFont.setFontHeightInPoints((short) 10);
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
		subheadFont.setFontHeightInPoints((short) 9);
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
		//headcontentFont.setFontName("Liberation Sans");
		headcontentFont.setFontHeightInPoints((short) 11);
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

		int rowCount = 7;

		if (b2bReport.size()!=0) {
			
			Transaction b2bData0=b2bReport.get(0);

			HSSFSheet sheet = workbook.createSheet("B2B");
			sheet.getPrintSetup().setLandscape(true);
			sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 4000);
			sheet.setColumnWidth(4, 4000);
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 4000);
			sheet.setColumnWidth(7, 4000);
			sheet.setColumnWidth(8, 4000);
			
		
				HSSFRow headnameRow = sheet.createRow(0);
				headnameRow.setHeightInPoints(15);
				HSSFCell companyCellname = headnameRow.createCell(0);
				companyCellname.setCellValue(reportTmpl.getCompanyname());
				companyCellname.setCellStyle(headNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

				HSSFRow buildingnameRow = sheet.createRow(1);
				buildingnameRow.setHeightInPoints(15);
				HSSFCell cellbuildingname = buildingnameRow.createCell(0);
				cellbuildingname.setCellValue(reportTmpl.getBuilding());
				cellbuildingname.setCellStyle(detailFontCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));

				HSSFRow DetailnameRow = sheet.createRow(2);
				DetailnameRow.setHeightInPoints(15);
				HSSFCell DetailnameCell = DetailnameRow.createCell(0);
				DetailnameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
				DetailnameCell.setCellStyle(detailFontCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 8));


				HSSFRow nameRow = sheet.createRow(3);
				nameRow.setHeightInPoints(35);
				HSSFCell cellname = nameRow.createCell(0);
				cellname.setCellValue("B2B REPORT");
				cellname.setCellStyle(rptNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 8));

				HSSFRow tableheading = sheet.createRow(4);
				tableheading.setHeightInPoints(25);

				HSSFCell cellParticulars = tableheading.createCell(0);
				cellParticulars.setCellStyle(subheaderCellStyle);
				cellParticulars.setCellValue("No. of Recipients");

				HSSFCell cellRoom = tableheading.createCell(1);
				cellRoom.setCellStyle(subheaderCellStyle);
				cellRoom.setCellValue("No. of Invoices");

				HSSFCell cellGuest = tableheading.createCell(2);
				cellGuest.setCellStyle(subheaderCellStyle);
				cellGuest.setCellValue(" ");

				HSSFCell cellPax = tableheading.createCell(3);
				cellPax.setCellStyle(subheaderCellStyle);
				cellPax.setCellValue("Total Invoice Value");

				HSSFCell cellPosted = tableheading.createCell(4);
				cellPosted.setCellStyle(subheaderCellStyle);
				cellPosted.setCellValue(" ");
				
				HSSFCell cellBaseAm= tableheading.createCell(5);
				cellBaseAm.setCellStyle(subheaderCellStyle);
				cellBaseAm.setCellValue(" ");

				HSSFCell cellTax1 = tableheading.createCell(6);
				cellTax1.setCellStyle(subheaderCellStyle);
				cellTax1.setCellValue(" ");
				
				
				HSSFCell cellTax2 = tableheading.createCell(7);
				cellTax2.setCellStyle(subheaderCellStyle);
				cellTax2.setCellValue("");
				

				HSSFCell cellAmount = tableheading.createCell(8);
				cellAmount.setCellStyle(subheaderCellStyle);
				cellAmount.setCellValue("Total Taxable Value");
				

				HSSFRow tableheading1 = sheet.createRow(5);
				tableheading1.setHeightInPoints(30);

				HSSFCell cellParticulars1 = tableheading1.createCell(0);
				cellParticulars1.setCellStyle(contentCellStyle2);
				cellParticulars1.setCellValue(b2bData0.getCountGst());

				HSSFCell cellRoom1 = tableheading1.createCell(1);
				cellRoom1.setCellStyle(contentCellStyle2);
				cellRoom1.setCellValue(b2bData0.getCountInvoice());

				HSSFCell cellGuest1 = tableheading1.createCell(2);
				cellGuest1.setCellStyle(contentCellStyle2);
				cellGuest1.setCellValue(" ");

				HSSFCell cellPax1 = tableheading1.createCell(3);
				cellPax1.setCellStyle(contentCellStyle2);
				cellPax1.setCellValue(b2bData0.getTotalInvoiceAmount());

				HSSFCell cellPosted1 = tableheading1.createCell(4);
				cellPosted1.setCellStyle(contentCellStyle2);
				cellPosted1.setCellValue(" ");
				
				HSSFCell cellBaseAm1= tableheading1.createCell(5);
				cellBaseAm1.setCellStyle(contentCellStyle2);
				cellBaseAm1.setCellValue(" ");

				HSSFCell cellTax1Pc = tableheading1.createCell(6);
				cellTax1Pc.setCellStyle(contentCellStyle2);
				cellTax1Pc.setCellValue(" ");
				
				HSSFCell cellTax1Amt = tableheading1.createCell(7);
				cellTax1Amt.setCellStyle(contentCellStyle2);
				cellTax1Amt.setCellValue(" ");
				

				HSSFCell cellTax2Pc = tableheading1.createCell(8);
				cellTax2Pc.setCellStyle(contentCellStyle2);
				cellTax2Pc.setCellValue(amountFormat.format(Double.valueOf(b2bData0.getTotalTaxableVal())));
			

				HSSFRow tableheading2 = sheet.createRow(6);
				tableheading2.setHeightInPoints(35);
			

				HSSFCell cellParticulars2 = tableheading2.createCell(0);
				cellParticulars2.setCellStyle(subheaderCellStyle);
				cellParticulars2.setCellValue("GSTIN/UIN of Recipient");

				HSSFCell cellRoom2 = tableheading2.createCell(1);
				cellRoom2.setCellStyle(subheaderCellStyle);
				cellRoom2.setCellValue("Invoice Number");

				HSSFCell cellGuest2 = tableheading2.createCell(2);
				cellGuest2.setCellStyle(subheaderCellStyle);
				cellGuest2.setCellValue("Invoice date");

				HSSFCell cellPax2 = tableheading2.createCell(3);
				cellPax2.setCellStyle(subheaderCellStyle);
				cellPax2.setCellValue("Invoice Value");

				HSSFCell cellPosted2 = tableheading2.createCell(4);
				cellPosted2.setCellStyle(subheaderCellStyle);
				cellPosted2.setCellValue("Place Of Supply");
				
				HSSFCell cellBaseAm2= tableheading2.createCell(5);
				cellBaseAm2.setCellStyle(subheaderCellStyle);
				cellBaseAm2.setCellValue("Reverse Charge");

				HSSFCell cellTax3 = tableheading2.createCell(6);
				cellTax3.setCellStyle(subheaderCellStyle);
				cellTax3.setCellValue("Invoice Type");
				
				
				HSSFCell cellTax4 = tableheading2.createCell(7);
				cellTax4.setCellStyle(subheaderCellStyle);
				cellTax4.setCellValue("Rate");
				

				HSSFCell cellAmount2 = tableheading2.createCell(8);
				cellAmount2.setCellStyle(subheaderCellStyle);
				cellAmount2.setCellValue("Taxable Value");
				
				
				

				for (int i = 0; i < b2bReport.size(); i++) {
					
					Transaction b2bData=b2bReport.get(i);
					//for date display	
					//String txnDate = b2bData.getTxn_date();
					//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					//java.util.Date date = sdf.parse(txnDate);

					String dateFormat = reportTmpl.getDateFormat();
					DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);

				
					//for data dispaly
					HSSFRow detailRow = sheet.createRow(rowCount);
					detailRow.setHeightInPoints(25);

					HSSFCell cellParticularsData = detailRow.createCell(0);
					cellParticularsData.setCellStyle(contentCellStyle2);
					cellParticularsData
							.setCellValue(b2bData.getGstNo());

					HSSFCell cellRoomdata = detailRow.createCell(1);
					cellRoomdata.setCellStyle(contentCellStyle2);
					cellRoomdata.setCellValue(b2bData.getInvoiceNo());

					HSSFCell cellGuestData = detailRow.createCell(2);
					cellGuestData.setCellStyle(contentCellStyle2);
					cellGuestData.setCellValue(b2bData.getInvoiceDateField());

					HSSFCell cellpax = detailRow.createCell(3);
					cellpax.setCellStyle(contentCellStyle2);
					cellpax.setCellValue(amountFormat.format(Double.valueOf(b2bData.getInvoiceAmount())));
				
					HSSFCell cellpax1 = detailRow.createCell(4);
					cellpax1.setCellStyle(numericCellStyle);
					cellpax1.setCellValue("32-Kerala");
					
					HSSFCell cellbase_amountData = detailRow.createCell(5);
					cellbase_amountData.setCellStyle(numericCellStyle);
					cellbase_amountData.setCellValue("N");

					HSSFCell celltax1Pc = detailRow.createCell(6);
					celltax1Pc.setCellStyle(numericCellStyle);
					celltax1Pc.setCellValue("Regular");

					HSSFCell celltax1Data = detailRow.createCell(7);
					celltax1Data.setCellStyle(numericCellStyle);
					celltax1Data.setCellValue(amountFormat.format(Double.valueOf(b2bData.getTotalTaxPc())));

					HSSFCell celltax2Pc = detailRow.createCell(8);
					celltax2Pc.setCellStyle(numericCellStyle);
					celltax2Pc.setCellValue(amountFormat.format(Double.valueOf(b2bData.getInvoiceBaseAmount())));

					rowCount = rowCount + 1;
					
				}
				
			
			sheet.createFreezePane(0,7);
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
				.concat(("B2B REPORT").toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
