package com.indocosmo.pms.web.reports.report_designs;

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
import com.indocosmo.pms.web.reports.model.RoomBookingReport;

public class RoomBookingFrequencyReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String roomNumber = "";
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<RoomBookingReport> roomFrequency = reportTmpl.getRoomBookingFrequency();

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

		Font noDataFont = workbook.createFont();
		noDataFont.setFontName("Liberation Sans");

		noDataFont.setFontHeightInPoints((short) 12);
		noDataFont.setColor(IndexedColors.BLACK.getIndex());
		noDataFont.setBoldweight(noDataFont.BOLDWEIGHT_BOLD);

		CellStyle noDataCellStyle = workbook.createCellStyle();
		noDataCellStyle.setFont(noDataFont);
		noDataCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

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

		Font columnHeadFond = workbook.createFont();
		columnHeadFond.setFontName("Liberation Sans");

		columnHeadFond.setBoldweight(columnHeadFond.BOLDWEIGHT_BOLD);
		columnHeadFond.setFontHeightInPoints((short) 12);
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
		contentCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		contentCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		sheet.setColumnWidth(0, 1500);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 10000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);

		int i;
		int rowCount = 1;
		int count = 1;
		HSSFRow tableheading;
		HSSFRow detailRow;

		HSSFRow headnameRow = sheet.createRow(0);
		headnameRow.setHeightInPoints(15);
		HSSFCell companyCellname = headnameRow.createCell(0);
		companyCellname.setCellValue(reportTmpl.getCompanyname());
		companyCellname.setCellStyle(headNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		HSSFRow buildingnameRow = sheet.createRow(1);
		buildingnameRow.setHeightInPoints(15);
		HSSFCell cellbuildingname = buildingnameRow.createCell(0);
		cellbuildingname.setCellValue(reportTmpl.getBuilding());
		cellbuildingname.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

		HSSFRow DetailnameRow = sheet.createRow(2);
		DetailnameRow.setHeightInPoints(15);
		HSSFCell DetailnameCell = DetailnameRow.createCell(0);
		DetailnameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
		DetailnameCell.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));

		HSSFRow nameRow = sheet.createRow(3);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(reportTmpl.getReportname());
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));

		HSSFRow subheaderRow = sheet.createRow(4);
		subheaderRow.setHeightInPoints(25);
		HSSFCell subheadercell = subheaderRow.createCell(0);
		subheadercell.setCellValue(reportTmpl.getDateFilter());
		subheadercell.setCellStyle(subheadCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));

		if (roomFrequency.size() != 0) {

			for (i = 0; i < roomFrequency.size(); i++) {

				roomNumber = roomFrequency.get(i).getRoomNumber();

				if ((i == 0) || !roomNumber.equals(roomFrequency.get(i - 1).getRoomNumber())) {

					count = 1;
					sheet.addMergedRegion(new CellRangeAddress(5 + rowCount, 5 + rowCount, 0, 5));
					HSSFRow HeadRow = sheet.createRow(5 + rowCount);
					HeadRow.setHeightInPoints(25);
					rowCount++;

					HSSFCell roomCell = HeadRow.createCell(0);
					roomCell.setCellValue(roomNumber);
					roomCell.setCellStyle(subheadCellStyle);
					roomCell.setCellType(subheadCellStyle.ALIGN_LEFT);

					tableheading = sheet.createRow(5 + rowCount);
					tableheading.setHeightInPoints(25);
					rowCount++;

					HSSFCell cellSiNo = tableheading.createCell(0);
					cellSiNo.setCellStyle(headCellStyle);
					cellSiNo.setCellValue("#");

					HSSFCell cellArrival = tableheading.createCell(1);
					cellArrival.setCellStyle(headCellStyle);
					cellArrival.setCellValue("Arrival Date");

					HSSFCell cellGuest = tableheading.createCell(2);
					cellGuest.setCellStyle(headCellStyle);
					cellGuest.setCellValue("Guest Name");

					HSSFCell cellAdults = tableheading.createCell(3);
					cellAdults.setCellStyle(headCellStyle);
					cellAdults.setCellValue("Adults");

					HSSFCell cellChildren = tableheading.createCell(4);
					cellChildren.setCellStyle(headCellStyle);
					cellChildren.setCellValue("Children");

					HSSFCell cellInfants = tableheading.createCell(5);
					cellInfants.setCellStyle(headCellStyle);
					cellInfants.setCellValue("Infants");

				}

				detailRow = sheet.createRow(5 + rowCount);
				rowCount++;
				detailRow.setHeightInPoints(25);

				HSSFCell cellSiNoData = detailRow.createCell(0);
				cellSiNoData.setCellStyle(contentCellStyle);
				cellSiNoData.setCellValue(count);

				HSSFCell cellArrDate = detailRow.createCell(1);
				cellArrDate.setCellStyle(contentCellStyle);
				cellArrDate.setCellValue(String.valueOf(roomFrequency.get(i).getArrDate()));

				HSSFCell cellFirstName = detailRow.createCell(2);
				cellFirstName.setCellStyle(contentCellStyle);
				cellFirstName.setCellValue(roomFrequency.get(i).getFirstName());

				HSSFCell cellAdults = detailRow.createCell(3);
				cellAdults.setCellStyle(contentCellStyle);
				cellAdults.setCellValue(roomFrequency.get(i).getAdults());

				HSSFCell cellChildren = detailRow.createCell(4);
				cellChildren.setCellStyle(contentCellStyle);
				cellChildren.setCellValue(roomFrequency.get(i).getChildren());

				HSSFCell cellInfants = detailRow.createCell(5);
				cellInfants.setCellStyle(contentCellStyle);
				cellInfants.setCellValue(roomFrequency.get(i).getInfants());

				count = count + 1;

				sheet.createFreezePane(0, 6);

			}
		} else {

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));

		}
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy");
		String stringToday = sdf.format(today);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat((reportTmpl.getReportname()).toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);
	}

}
