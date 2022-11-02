package com.indocosmo.pms.web.reports.report_designs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
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

import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.reports.model.ReportTemplate;

public class NationalityReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Date date = commonSettings.hotelDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");

		List<com.indocosmo.pms.web.reports.model.NationalityReport> nationalityReportlist = reportTmpl
				.getNationalityReportList();

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
		contentCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
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

		CellStyle numericCellStyle1 = workbook.createCellStyle();
		numericCellStyle1.setFont(columnHeadFont);
		numericCellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
		numericCellStyle1.setBorderTop(CellStyle.BORDER_THIN);
		numericCellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
		numericCellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
		numericCellStyle1.setBorderRight(CellStyle.BORDER_THIN);

		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 3500);
		sheet.setColumnWidth(4, 3500);
		sheet.setColumnWidth(5, 3500);
		sheet.setColumnWidth(6, 3500);
		sheet.setColumnWidth(7, 3500);

		HSSFRow headnameRow = sheet.createRow(0);
		headnameRow.setHeightInPoints(15);
		HSSFCell companyCellname = headnameRow.createCell(0);
		companyCellname.setCellValue(reportTmpl.getCompanyname());
		companyCellname.setCellStyle(headNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

		HSSFRow buildingnameRow = sheet.createRow(1);
		buildingnameRow.setHeightInPoints(15);
		HSSFCell cellbuildingname = buildingnameRow.createCell(0);
		cellbuildingname.setCellValue(reportTmpl.getBuilding());
		cellbuildingname.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

		HSSFRow DetailnameRow = sheet.createRow(2);
		DetailnameRow.setHeightInPoints(15);
		HSSFCell DetailnameCell = DetailnameRow.createCell(0);
		DetailnameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
		DetailnameCell.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));

		HSSFRow nameRow = sheet.createRow(3);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(reportTmpl.getReportname());
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));

		HSSFRow subheaderRow = sheet.createRow(4);
		subheaderRow.setHeightInPoints(25);
		HSSFCell subheadercell = subheaderRow.createCell(0);
		subheadercell.setCellValue(reportTmpl.getDateFilter());
		subheadercell.setCellStyle(subheadCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 7));

		if (nationalityReportlist.size() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellCountry = tableheading.createCell(0);
			cellCountry.setCellStyle(headCellStyle);
			cellCountry.setCellValue("Country");

			HSSFCell cellState = tableheading.createCell(1);
			cellState.setCellStyle(headCellStyle);
			cellState.setCellValue("State");

			HSSFCell cellRooms = tableheading.createCell(2);
			cellRooms.setCellStyle(headCellStyle);
			cellRooms.setCellValue("Rooms");

			HSSFCell cellRoomsPercent = tableheading.createCell(3);
			cellRoomsPercent.setCellStyle(headCellStyle);
			cellRoomsPercent.setCellValue("Room %");

			HSSFCell cellGustName = tableheading.createCell(4);
			cellGustName.setCellStyle(headCellStyle);
			cellGustName.setCellValue("Persons");

			HSSFCell cellPersonsPercent = tableheading.createCell(5);
			cellPersonsPercent.setCellStyle(headCellStyle);
			cellPersonsPercent.setCellValue("Persons %");

			HSSFCell cellYearRooms = tableheading.createCell(6);
			cellYearRooms.setCellStyle(headCellStyle);
			cellYearRooms.setCellValue(year + "rooms");

			HSSFCell cellYearPersons = tableheading.createCell(7);
			cellYearPersons.setCellStyle(headCellStyle);
			cellYearPersons.setCellValue(year + "Persons");

			Collections.sort(nationalityReportlist);
			int room = 0;
			float room_perc = 0.0f;
			int person = 0;
			float person_perc = 0.0f;
			int yearly_room = 0;
			int yearly_person = 0;
			int rowCount = 0;
			int adtnlRow = 0;
			int rowNum = 0;
			int initRow = 6;
			for (int i = 0; i < nationalityReportlist.size(); i++) {

				com.indocosmo.pms.web.reports.model.NationalityReport nationalityReport = nationalityReportlist.get(i);

				rowNum = initRow + rowCount + adtnlRow;
				HSSFRow detailRow1 = sheet.createRow(rowNum);
				detailRow1.setHeightInPoints(25);

				if (i == 0 || !nationalityReport.getCountry_name()
						.equals(nationalityReportlist.get(i - 1).getCountry_name())) {
					HSSFCell cellCountry_nameData = detailRow1.createCell(0);
					cellCountry_nameData.setCellStyle(contentCellStyle);
					cellCountry_nameData.setCellValue(nationalityReport.getCountry_name());

				} else {

					HSSFCell cellCountry_nameData = detailRow1.createCell(0);
					cellCountry_nameData.setCellStyle(contentCellStyle);
					cellCountry_nameData.setCellValue("");
				}

				HSSFCell cellState_nameData = detailRow1.createCell(1);
				cellState_nameData.setCellStyle(contentCellStyle);
				cellState_nameData.setCellValue(nationalityReport.getState_name());

				HSSFCell cellRoomData = detailRow1.createCell(2);
				cellRoomData.setCellStyle(contentCellStyle);
				cellRoomData.setCellValue(nationalityReport.getRoom());

				HSSFCell cellRoom_percentageData = detailRow1.createCell(3);
				cellRoom_percentageData.setCellStyle(numericCellStyle1);
				cellRoom_percentageData.setCellValue(nationalityReport.getRoom_percentage());

				HSSFCell cellPersonsData = detailRow1.createCell(4);
				cellPersonsData.setCellStyle(contentCellStyle);
				cellPersonsData.setCellValue(nationalityReport.getPersons());

				HSSFCell cellPerson_percentageData = detailRow1.createCell(5);
				cellPerson_percentageData.setCellStyle(numericCellStyle1);
				cellPerson_percentageData.setCellValue(nationalityReport.getPerson_percentage());

				HSSFCell cellYearly_roomData = detailRow1.createCell(6);
				cellYearly_roomData.setCellStyle(contentCellStyle);
				cellYearly_roomData.setCellValue(nationalityReport.getYearly_room());

				HSSFCell cellYearly_personData = detailRow1.createCell(7);
				cellYearly_personData.setCellStyle(contentCellStyle);
				cellYearly_personData.setCellValue(nationalityReport.getYearly_person());

				room += nationalityReport.getRoom();
				person += nationalityReport.getPersons();
				room_perc += Float.valueOf(nationalityReport.getRoom_percentage().replace("%", "").trim());
				person_perc += Float.valueOf(nationalityReport.getPerson_percentage().replace("%", "").trim());
				yearly_room += nationalityReport.getYearly_room();
				yearly_person += nationalityReport.getYearly_person();

				rowCount = rowCount + 1;

				if ((nationalityReport.getCountry_name() == null
						&& nationalityReportlist.get(i + 1).getCountry_name() != null)
						|| (nationalityReportlist.size() == i + 1 || !nationalityReport.getCountry_name()
								.equals(nationalityReportlist.get(i + 1).getCountry_name()))) {

					adtnlRow = adtnlRow + 1;
					rowNum = rowNum + 1;

					HSSFRow contentRow = sheet.createRow(rowNum);
					contentRow.setHeightInPoints(25);

					HSSFCell cellEmptyData1 = contentRow.createCell(0);
					cellEmptyData1.setCellStyle(headCellStyle);
					cellEmptyData1.setCellValue("");

					HSSFCell cellEmptyData2 = contentRow.createCell(1);
					cellEmptyData2.setCellStyle(headCellStyle);
					cellEmptyData2.setCellValue("");

					HSSFCell cellRoomsData = contentRow.createCell(2);
					cellRoomsData.setCellStyle(contentCellStyle);
					cellRoomsData.setCellValue(room);

					HSSFCell cellRoomPercData = contentRow.createCell(3);
					cellRoomPercData.setCellStyle(numericCellStyle1);
					cellRoomPercData.setCellValue(String.format("%.2f", (room_perc)) + "%");

					HSSFCell cellPersonData = contentRow.createCell(4);
					cellPersonData.setCellStyle(contentCellStyle);
					cellPersonData.setCellValue(person);

					HSSFCell cellPersonPercData = contentRow.createCell(5);
					cellPersonPercData.setCellStyle(numericCellStyle1);
					cellPersonPercData.setCellValue(String.format("%.2f", (person_perc)) + "%");

					HSSFCell cellYearlyRoomData = contentRow.createCell(6);
					cellYearlyRoomData.setCellStyle(contentCellStyle);
					cellYearlyRoomData.setCellValue(yearly_room);

					HSSFCell cellYearlyPersonData = contentRow.createCell(7);
					cellYearlyPersonData.setCellStyle(contentCellStyle);
					cellYearlyPersonData.setCellValue(yearly_person);
					
					room = 0;
					room_perc = 0.0f;
					person = 0;
					person_perc = 0.0f;
					yearly_person = 0;
					yearly_room = 0;
					
					sheet.createFreezePane(0,6);
				}

			}

		} else {

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 3));

		}

		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday = sdf.format(today);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat((reportTmpl.getReportname()).toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
