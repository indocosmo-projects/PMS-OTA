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
import com.indocosmo.pms.web.reports.model.CustomerGrading;
import com.indocosmo.pms.web.reports.model.ReportTemplate;

public class CustomerGradingReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<CustomerGrading> customerGradingList = reportTmpl.getCustomerGradingList();

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

		CellStyle amtCellStyle = workbook.createCellStyle();
		amtCellStyle.setFont(contentFont);
		amtCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		amtCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		amtCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		amtCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		amtCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 7000);
		sheet.setColumnWidth(2, 9000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);

		HSSFRow headnameRow = sheet.createRow(0);
		headnameRow.setHeightInPoints(15);
		HSSFCell companyCellname = headnameRow.createCell(0);
		companyCellname.setCellValue(reportTmpl.getCompanyname());
		companyCellname.setCellStyle(headNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

		HSSFRow buildingnameRow = sheet.createRow(1);
		buildingnameRow.setHeightInPoints(15);
		HSSFCell cellbuildingname = buildingnameRow.createCell(0);
		cellbuildingname.setCellValue(reportTmpl.getBuilding());
		cellbuildingname.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));

		HSSFRow DetailnameRow = sheet.createRow(2);
		DetailnameRow.setHeightInPoints(15);
		HSSFCell DetailnameCell = DetailnameRow.createCell(0);
		DetailnameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
		DetailnameCell.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));

		HSSFRow MainHeaderRow = sheet.createRow(3);
		MainHeaderRow.setHeightInPoints(35);
		HSSFCell MainHeaderCell = MainHeaderRow.createCell(0);
		MainHeaderCell.setCellValue("CUSTOMER GRADING REPORT");
		MainHeaderCell.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));

		HSSFRow subheaderDateRow = sheet.createRow(4);
		subheaderDateRow.setHeightInPoints(35);
		HSSFCell subheaderDatecell = subheaderDateRow.createCell(0);
		subheaderDatecell.setCellValue(reportTmpl.getDateFilter());
		subheaderDatecell.setCellStyle(subheadCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 4));

		if (customerGradingList.size() != 0) {
			int i = 0;

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellSiNo = tableheading.createCell(0);
			cellSiNo.setCellStyle(headCellStyle);
			cellSiNo.setCellValue("Guest Name");

			HSSFCell cellGustName = tableheading.createCell(1);
			cellGustName.setCellStyle(headCellStyle);
			cellGustName.setCellValue("Email");

			HSSFCell cellPax = tableheading.createCell(2);
			cellPax.setCellStyle(headCellStyle);
			cellPax.setCellValue("Address");

			HSSFCell cellRoomNo = tableheading.createCell(3);
			cellRoomNo.setCellStyle(headCellStyle);
			cellRoomNo.setCellValue("Phone");

			HSSFCell cellRoomType = tableheading.createCell(4);
			cellRoomType.setCellStyle(headCellStyle);
			cellRoomType.setCellValue("Total Amount");

			for (CustomerGrading obj : customerGradingList) {

				HSSFRow detailRow = sheet.createRow(6 + i);
				detailRow.setHeightInPoints(25);

				HSSFCell cellName = detailRow.createCell(0);
				cellName.setCellStyle(contentCellStyle);
				cellName.setCellValue(obj.getGuestName());

				HSSFCell cellemail = detailRow.createCell(1);
				cellemail.setCellStyle(contentCellStyle);
				cellemail.setCellValue(obj.getEmail());

				HSSFCell cellAddress = detailRow.createCell(2);
				cellAddress.setCellStyle(contentCellStyle);
				cellAddress.setCellValue(obj.getAddress());

				HSSFCell cellPhone = detailRow.createCell(3);
				cellPhone.setCellStyle(contentCellStyle);
				cellPhone.setCellValue(obj.getPhone().toString());

				HSSFCell cellTotal = detailRow.createCell(4);
				cellTotal.setCellStyle(amtCellStyle);
				cellTotal.setCellValue(obj.getTotalAmount().toString());

				i++;

				sheet.createFreezePane(0,6);
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
		headerResponse = headerResponse
				.concat((reportTmpl.getReportname()).toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}
}
