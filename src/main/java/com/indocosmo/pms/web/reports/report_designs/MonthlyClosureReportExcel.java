package com.indocosmo.pms.web.reports.report_designs;

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

public class MonthlyClosureReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String dateFormat;
		int paxTotal = 0;
		int paxValue = 0;
		int nightTotal = 0;
		int nightValue = 0;
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<Transaction> monthlyClosureReport = reportTmpl.getMonthlyClosureReport();
		NumberFormat amountFormat = new DecimalFormat("#,##0.00");

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

		Font subheadFont = workbook.createFont();
		subheadFont.setFontName("Liberation Sans");

		subheadFont.setFontHeightInPoints((short) 12);
		subheadFont.setColor(IndexedColors.BLACK.getIndex());
		subheadFont.setBoldweight(subheadFont.BOLDWEIGHT_BOLD);

		Font noDataFont = workbook.createFont();
		noDataFont.setFontName("Liberation Sans");

		noDataFont.setFontHeightInPoints((short) 12);
		noDataFont.setColor(IndexedColors.BLACK.getIndex());
		noDataFont.setBoldweight(noDataFont.BOLDWEIGHT_BOLD);

		CellStyle noDataCellStyle = workbook.createCellStyle();
		noDataCellStyle.setFont(noDataFont);
		noDataCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font totalRowFont = workbook.createFont();

		totalRowFont.setFontHeightInPoints((short) 12);
		totalRowFont.setColor(IndexedColors.BLACK.getIndex());
		totalRowFont.setBoldweight(totalRowFont.BOLDWEIGHT_BOLD);

		CellStyle subheadCellStyle = workbook.createCellStyle();
		subheadCellStyle.setFont(subheadFont);
		subheadCellStyle.setAlignment(CellStyle.ALIGN_LEFT);

		Font columnHeadFont = workbook.createFont();
		columnHeadFont.setFontName("Liberation Sans");

		columnHeadFont.setBoldweight(columnHeadFont.BOLDWEIGHT_BOLD);
		columnHeadFont.setFontHeightInPoints((short) 10);
		columnHeadFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headCellStyle = workbook.createCellStyle();
		headCellStyle.setFont(columnHeadFont);
		headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		headCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		Font contentFont = workbook.createFont();
		contentFont.setFontName("Liberation Sans");
		contentFont.setFontHeightInPoints((short) 9);
		contentFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setFont(contentFont);
		contentCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		contentCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		CellStyle numericCellStyle = workbook.createCellStyle();
		numericCellStyle.setFont(contentFont);
		numericCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		numericCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		CellStyle totalrowCellStyle = workbook.createCellStyle();
		totalrowCellStyle.setFont(totalRowFont);
		totalrowCellStyle.setWrapText(true);
		totalrowCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		totalrowCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		totalrowCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		totalrowCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		totalrowCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

		int rowCount = 6;

		double baseAmount = 0.0;
		double tax1 = 0.0;
		double tax2 = 0.0;
		//double tax3 = 0.0;
		double total = 0.0;

		HSSFSheet sheet = workbook.createSheet("Sheet1");
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

		sheet.setColumnWidth(0, 2800);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 1800);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 1800);
		sheet.setColumnWidth(5, 1600);
		sheet.setColumnWidth(6, 3500);
		sheet.setColumnWidth(7, 3500);
		sheet.setColumnWidth(8, 3500);
		sheet.setColumnWidth(9, 3500);
		///sheet.setColumnWidth(10, 3500);

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
		cellname.setCellValue(reportTmpl.getReportname());
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 10));

		HSSFRow subheaderRow = sheet.createRow(4);
		subheaderRow.setHeightInPoints(25);
		HSSFCell subheadercell = subheaderRow.createCell(0);
		subheadercell.setCellValue(reportTmpl.getDateFilter());
		subheadercell.setCellStyle(subheadCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 10));

		if (monthlyClosureReport.size() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellDate = tableheading.createCell(0);
			cellDate.setCellStyle(headCellStyle);
			cellDate.setCellValue("Date");

			HSSFCell cellInvoice = tableheading.createCell(1);
			cellInvoice.setCellStyle(headCellStyle);
			cellInvoice.setCellValue("Invoice");

			HSSFCell cellRoom = tableheading.createCell(2);
			cellRoom.setCellStyle(headCellStyle);
			cellRoom.setCellValue("Room");

			HSSFCell cellGuest = tableheading.createCell(3);
			cellGuest.setCellStyle(headCellStyle);
			cellGuest.setCellValue("Guest");

			HSSFCell cellNights = tableheading.createCell(4);
			cellNights.setCellStyle(headCellStyle);
			cellNights.setCellValue("Nights");

			HSSFCell cellPax = tableheading.createCell(5);
			cellPax.setCellStyle(headCellStyle);
			cellPax.setCellValue("Pax");

			HSSFCell cellBaseAmount = tableheading.createCell(6);
			cellBaseAmount.setCellStyle(headCellStyle);
			cellBaseAmount.setCellValue("Base Amount");

			HSSFCell cellTax1 = tableheading.createCell(7);
			cellTax1.setCellStyle(headCellStyle);
			cellTax1.setCellValue((String) request.getSession().getAttribute("tax1Name"));

			HSSFCell cellTax2 = tableheading.createCell(8);
			cellTax2.setCellStyle(headCellStyle);
			cellTax2.setCellValue((String) request.getSession().getAttribute("tax2Name"));

			//HSSFCell cellTax3 = tableheading.createCell(9);
			//cellTax3.setCellStyle(headCellStyle);
			//cellTax3.setCellValue((String) request.getSession().getAttribute("tax3Name"));

			HSSFCell cellTotal = tableheading.createCell(9);
			cellTotal.setCellStyle(headCellStyle);
			cellTotal.setCellValue("Total");

			for (int i = 0; i < monthlyClosureReport.size(); i++) {

				dateFormat = monthlyClosureReport.get(i).getDateFormat();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

				HSSFRow detailRow = sheet.createRow(rowCount);
				detailRow.setHeightInPoints(25);

				HSSFCell cellInvoiceDatedata = detailRow.createCell(0);
				cellInvoiceDatedata.setCellStyle(contentCellStyle);
				cellInvoiceDatedata.setCellValue(
						String.valueOf(simpleDateFormat.format(monthlyClosureReport.get(i).getInvoiceDate())));

				HSSFCell cellInvoiceNodata = detailRow.createCell(1);
				cellInvoiceNodata.setCellStyle(contentCellStyle);
				cellInvoiceNodata.setCellValue(String.valueOf(monthlyClosureReport.get(i).getInvoiceNo()));

				HSSFCell cellRoomNumberdata = detailRow.createCell(2);
				cellRoomNumberdata.setCellStyle(contentCellStyle);
				cellRoomNumberdata.setCellValue(String.valueOf(monthlyClosureReport.get(i).getRoomNumber()));

				HSSFCell cellFirstNamedata = detailRow.createCell(3);
				cellFirstNamedata.setCellStyle(contentCellStyle);
				cellFirstNamedata.setCellValue(monthlyClosureReport.get(i).getFirstName());

				HSSFCell cellNoNights = detailRow.createCell(4);
				cellNoNights.setCellStyle(numericCellStyle);
				cellNoNights.setCellValue(Integer.valueOf(monthlyClosureReport.get(i).getNumNights()));
				nightValue = Integer.parseInt(monthlyClosureReport.get(i).getNumNights());

				HSSFCell cellPaxdata = detailRow.createCell(5);
				cellPaxdata.setCellStyle(numericCellStyle);
				cellPaxdata.setCellValue(Integer.valueOf(monthlyClosureReport.get(i).getPax()));
				paxValue = monthlyClosureReport.get(i).getPax();

				HSSFCell cellBaseAmountdata = detailRow.createCell(6);
				cellBaseAmountdata.setCellStyle(numericCellStyle);
				cellBaseAmountdata.setCellValue((amountFormat.format(monthlyClosureReport.get(i).getBase_amount())));

				HSSFCell cellTax1data = detailRow.createCell(7);
				cellTax1data.setCellStyle(numericCellStyle);
				cellTax1data.setCellValue((amountFormat.format(monthlyClosureReport.get(i).getTax1_amount())));

				HSSFCell cellTax2data = detailRow.createCell(8);
				cellTax2data.setCellStyle(numericCellStyle);
				cellTax2data.setCellValue((amountFormat.format(monthlyClosureReport.get(i).getTax2_amount())));

				//HSSFCell cellTax3data = detailRow.createCell(9);
				//cellTax3data.setCellStyle(numericCellStyle);
				//cellTax3data.setCellValue((amountFormat.format(monthlyClosureReport.get(i).getTax3_amount())));

				HSSFCell cellAmountdata = detailRow.createCell(9);
				cellAmountdata.setCellStyle(numericCellStyle);
				cellAmountdata.setCellValue((amountFormat.format(monthlyClosureReport.get(i).getAmount())));

				baseAmount += monthlyClosureReport.get(i).getBase_amount();
				tax1 += monthlyClosureReport.get(i).getTax1_amount();
				tax2 += monthlyClosureReport.get(i).getTax2_amount();
				//tax3 += monthlyClosureReport.get(i).getTax3_amount();
				total += monthlyClosureReport.get(i).getAmount();
				paxTotal += paxValue;
				nightTotal += nightValue;

				rowCount = rowCount + 1;
			}

			HSSFRow detailRow = sheet.createRow(rowCount);
			detailRow.setHeightInPoints(25);

			CellRangeAddress mergedCell = new CellRangeAddress(rowCount, rowCount, 0, 3);

			HSSFCell cellEmptydata = detailRow.createCell(0);
			cellEmptydata.setCellStyle(totalrowCellStyle);
			cellEmptydata.setCellValue("TOTAL:");
			sheet.addMergedRegion(mergedCell);

			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);

			HSSFCell cellNightTotal = detailRow.createCell(4);
			cellNightTotal.setCellStyle(totalrowCellStyle);
			cellNightTotal.setCellValue((nightTotal));
			
			HSSFCell cellPaxTotal = detailRow.createCell(5);
			cellPaxTotal.setCellStyle(totalrowCellStyle);
			cellPaxTotal.setCellValue((paxTotal));

			HSSFCell baseAmountCelldata = detailRow.createCell(6);
			baseAmountCelldata.setCellStyle(totalrowCellStyle);
			baseAmountCelldata.setCellValue(amountFormat.format(baseAmount));

			HSSFCell celltax1data = detailRow.createCell(7);
			celltax1data.setCellStyle(totalrowCellStyle);
			celltax1data.setCellValue(amountFormat.format(tax1));

			HSSFCell celltax2data = detailRow.createCell(8);
			celltax2data.setCellStyle(totalrowCellStyle);
			celltax2data.setCellValue(amountFormat.format(tax2));

			//HSSFCell celltax3data = detailRow.createCell(9);
			//celltax3data.setCellStyle(totalrowCellStyle);
			//celltax3data.setCellValue(amountFormat.format(tax3));

			HSSFCell celltotaldata = detailRow.createCell(9);
			celltotaldata.setCellStyle(totalrowCellStyle);
			celltotaldata.setCellValue(amountFormat.format(total));
			
			sheet.createFreezePane(0,6);

		} else {

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 10));

		}

		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday = sdf.format(today);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat(reportTmpl.getReportname().toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
