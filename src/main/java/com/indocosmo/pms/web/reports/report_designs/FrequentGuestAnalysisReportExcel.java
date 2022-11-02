package com.indocosmo.pms.web.reports.report_designs;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FrequentGuestAnalysisReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JsonArray bookingDataList = (JsonArray) model.get("bookingData");
		JsonObject data = bookingDataList.get(0).getAsJsonObject();
		JsonObject dateRange = data.get("dateRange").getAsJsonObject();

		HSSFSheet sheet = workbook.createSheet("sheet");
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

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

		CellStyle subheadCellStyle = workbook.createCellStyle();
		subheadCellStyle.setFont(subheadFont);
		subheadCellStyle.setAlignment(CellStyle.ALIGN_LEFT);

		Font noDataFont = workbook.createFont();
		noDataFont.setFontName("Liberation Sans");

		noDataFont.setFontHeightInPoints((short) 12);
		noDataFont.setColor(IndexedColors.BLACK.getIndex());
		noDataFont.setBoldweight(noDataFont.BOLDWEIGHT_BOLD);

		CellStyle noDataCellStyle = workbook.createCellStyle();
		noDataCellStyle.setFont(noDataFont);
		noDataCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font columnHeadFond = workbook.createFont();
		columnHeadFond.setFontName("Liberation Sans");

		columnHeadFond.setBoldweight(columnHeadFond.BOLDWEIGHT_BOLD);
		columnHeadFond.setFontHeightInPoints((short) 10);
		columnHeadFond.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headCellStyle = workbook.createCellStyle();
		headCellStyle.setFont(columnHeadFond);
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

		CellStyle amountCellStyle = workbook.createCellStyle();
		amountCellStyle.setFont(contentFont);
		amountCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		amountCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		amountCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		amountCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		amountCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		sheet.setColumnWidth(0, 1000);
		sheet.setColumnWidth(1, 8500);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 8500);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(5, 3100);
		sheet.setColumnWidth(6, 3100);
		sheet.setColumnWidth(7, 3000);

		JsonObject headerDataList = (JsonObject) model.get("companyData");
		JsonArray companyData = headerDataList.get("companyData").getAsJsonArray();
		JsonObject companyDataList = (JsonObject) companyData.get(0);

		HSSFRow headnameRow = sheet.createRow(0);
		headnameRow.setHeightInPoints(15);
		HSSFCell companyCellname = headnameRow.createCell(0);
		companyCellname.setCellValue(companyDataList.get("company_name").getAsString());
		companyCellname.setCellStyle(headNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

		HSSFRow buildingnameRow = sheet.createRow(1);
		buildingnameRow.setHeightInPoints(15);
		HSSFCell cellbuildingname = buildingnameRow.createCell(0);
		cellbuildingname.setCellValue(companyDataList.get("building_name").getAsString());
		cellbuildingname.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

		HSSFRow DetailnameRow = sheet.createRow(2);
		DetailnameRow.setHeightInPoints(15);
		HSSFCell DetailnameCell = DetailnameRow.createCell(0);
		DetailnameCell.setCellValue(companyDataList.get("street_name").getAsString() + ","
				+ companyDataList.get("city_name").getAsString());
		DetailnameCell.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));

		HSSFRow nameRow = sheet.createRow(3);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue("FREQUENT GUEST ANALYSIS REPORT");
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));

		HSSFRow subheaderRow = sheet.createRow(4);
		subheaderRow.setHeightInPoints(25);
		HSSFCell subheadercell = subheaderRow.createCell(0);
		subheadercell.setCellValue(
				"Between " + dateRange.get("fromDate").getAsString() + " And " + dateRange.get("toDate").getAsString());
		subheadercell.setCellStyle(subheadCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 7));

		if (bookingDataList.size() > 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellSiNo = tableheading.createCell(0);
			cellSiNo.setCellStyle(headCellStyle);
			cellSiNo.setCellValue("#");

			HSSFCell cellGustName = tableheading.createCell(1);
			cellGustName.setCellStyle(headCellStyle);
			cellGustName.setCellValue("Company Name");

			HSSFCell cellPax = tableheading.createCell(2);
			cellPax.setCellStyle(headCellStyle);
			cellPax.setCellValue("Phone");

			HSSFCell cellRoomNo = tableheading.createCell(3);
			cellRoomNo.setCellStyle(headCellStyle);
			cellRoomNo.setCellValue("Email");

			HSSFCell cellRoomType = tableheading.createCell(4);
			cellRoomType.setCellStyle(headCellStyle);
			cellRoomType.setCellValue("Nights");

			HSSFCell cellMealPlan = tableheading.createCell(5);
			cellMealPlan.setCellStyle(headCellStyle);
			cellMealPlan.setCellValue("Country");

			HSSFCell cellCheckinDate = tableheading.createCell(6);
			cellCheckinDate.setCellStyle(headCellStyle);
			cellCheckinDate.setCellValue("State");

			HSSFCell cellCheckoutDate = tableheading.createCell(7);
			cellCheckoutDate.setCellStyle(headCellStyle);
			cellCheckoutDate.setCellValue("Amount");

			int count = 0;

			int i = 0;
			JsonArray bookingData = data.get("bookingData").getAsJsonArray();
			if (bookingData.size() > 0) {
				for (Object obj : bookingData) {
					count++;
					JsonObject bookingObj = (JsonObject) obj;

					HSSFRow detailRow = sheet.createRow(6 + i);
					detailRow.setHeightInPoints(25);

					HSSFCell cellSiNoData = detailRow.createCell(0);
					cellSiNoData.setCellStyle(contentCellStyle);
					cellSiNoData.setCellValue(count);

					HSSFCell cellcompany = detailRow.createCell(1);
					cellcompany.setCellStyle(contentCellStyle);
					cellcompany.setCellValue(bookingObj.get("company").getAsString());

					HSSFCell cellphone = detailRow.createCell(2);
					cellphone.setCellStyle(contentCellStyle);
					cellphone.setCellValue(bookingObj.get("phone").getAsString());

					HSSFCell cellemail = detailRow.createCell(3);
					cellemail.setCellStyle(contentCellStyle);
					cellemail.setCellValue(bookingObj.get("email").getAsString());

					HSSFCell cellnoOfNights = detailRow.createCell(4);
					cellnoOfNights.setCellStyle(amountCellStyle);
					cellnoOfNights.setCellValue(bookingObj.get("noOfNights").getAsString());

					HSSFCell cellnationality = detailRow.createCell(5);
					cellnationality.setCellStyle(contentCellStyle);
					cellnationality.setCellValue(bookingObj.get("nationality").getAsString());

					HSSFCell cellstate = detailRow.createCell(6);
					cellstate.setCellStyle(contentCellStyle);
					cellstate.setCellValue(bookingObj.get("state").getAsString());

					HSSFCell cellamount = detailRow.createCell(7);
					cellamount.setCellStyle(amountCellStyle);
					cellamount.setCellValue(bookingObj.get("amount").getAsString());

					sheet.createFreezePane(0, 6);

					i++;
				}

			}
		} else {

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 7));
		}

		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday = sdf.format(today);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse.concat("frequentguestanalysis_" + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}
}
