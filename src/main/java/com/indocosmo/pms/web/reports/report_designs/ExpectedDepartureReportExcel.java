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

public class ExpectedDepartureReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");

		List<com.indocosmo.pms.web.reports.model.GeneralReport> generalReportList = reportTmpl.getGeneralReportList();

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
		contentCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		contentCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		Font contentFont1 = workbook.createFont();
		contentFont1.setFontName("Liberation Sans");

		contentFont1.setFontHeightInPoints((short) 9);
		contentFont1.setColor(IndexedColors.BLACK.getIndex());

		CellStyle amountCellStyle = workbook.createCellStyle();
		amountCellStyle.setFont(contentFont1);
		amountCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		amountCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		amountCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		amountCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		amountCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		sheet.setColumnWidth(0, 1300);
		sheet.setColumnWidth(1, 7000);
		sheet.setColumnWidth(2, 1500);
		sheet.setColumnWidth(3, 2500);
		sheet.setColumnWidth(4, 2700);
		sheet.setColumnWidth(5, 2500);
		sheet.setColumnWidth(6, 3300);
		sheet.setColumnWidth(7, 3500);
		sheet.setColumnWidth(8, 2500);
		sheet.setColumnWidth(9, 3000);
		sheet.setColumnWidth(10, 2500);

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

		if (generalReportList.size() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellSiNo = tableheading.createCell(0);
			cellSiNo.setCellStyle(headCellStyle);
			cellSiNo.setCellValue("#");

			HSSFCell cellGustName = tableheading.createCell(1);
			cellGustName.setCellStyle(headCellStyle);
			cellGustName.setCellValue("Gust Name");

			HSSFCell cellPax = tableheading.createCell(2);
			cellPax.setCellStyle(headCellStyle);
			cellPax.setCellValue("Pax");

			HSSFCell cellRoomNo = tableheading.createCell(3);
			cellRoomNo.setCellStyle(headCellStyle);
			cellRoomNo.setCellValue("Room No");

			HSSFCell cellRoomType = tableheading.createCell(4);
			cellRoomType.setCellStyle(headCellStyle);
			cellRoomType.setCellValue("Room Type");

			HSSFCell cellMealPlan = tableheading.createCell(5);
			cellMealPlan.setCellStyle(headCellStyle);
			cellMealPlan.setCellValue("Meal Plan");

			HSSFCell cellCheckinDate = tableheading.createCell(6);
			cellCheckinDate.setCellStyle(headCellStyle);
			cellCheckinDate.setCellValue("Checkin Date");

			HSSFCell cellCheckoutDate = tableheading.createCell(7);
			cellCheckoutDate.setCellStyle(headCellStyle);
			cellCheckoutDate.setCellValue("Checkout Date");

			HSSFCell cellStatus = tableheading.createCell(8);
			cellStatus.setCellStyle(headCellStyle);
			cellStatus.setCellValue("Status");

			HSSFCell cellTariff = tableheading.createCell(9);
			cellTariff.setCellStyle(headCellStyle);
			cellTariff.setCellValue("Tariff");

			HSSFCell cellFolioBalance = tableheading.createCell(10);
			cellFolioBalance.setCellStyle(headCellStyle);
			cellFolioBalance.setCellValue("Folio Bal");

			for (int i = 0; i < generalReportList.size(); i++) {

				com.indocosmo.pms.web.reports.model.GeneralReport generalReport = generalReportList.get(i);

				HSSFRow detailRow = sheet.createRow(6 + i);
				detailRow.setHeightInPoints(25);

				HSSFCell cellSiNoData = detailRow.createCell(0);
				cellSiNoData.setCellStyle(contentCellStyle);
				cellSiNoData.setCellValue(i + 1);

				HSSFCell cellGustNameData = detailRow.createCell(1);
				cellGustNameData.setCellStyle(contentCellStyle);
				cellGustNameData.setCellValue(generalReport.getGust_name());

				HSSFCell cellPaxData = detailRow.createCell(2);
				cellPaxData.setCellStyle(contentCellStyle);
				cellPaxData.setCellValue(generalReport.getPax_resv());

				HSSFCell cellRoomNoData = detailRow.createCell(3);
				cellRoomNoData.setCellStyle(contentCellStyle);
				cellRoomNoData.setCellValue(generalReport.getRoom_no());

				HSSFCell cellRoomTypeData = detailRow.createCell(4);
				cellRoomTypeData.setCellStyle(contentCellStyle);
				cellRoomTypeData.setCellValue(generalReport.getRoom_type());

				HSSFCell cellMealPlanData = detailRow.createCell(5);
				cellMealPlanData.setCellStyle(contentCellStyle);
				cellMealPlanData.setCellValue(generalReport.getMeal_plan());

				HSSFCell cellCheckinDateData = detailRow.createCell(6);
				cellCheckinDateData.setCellStyle(contentCellStyle);
				cellCheckinDateData.setCellValue(generalReport.getCheckin_date());

				HSSFCell cellExpCheckoutDateData = detailRow.createCell(7);
				cellExpCheckoutDateData.setCellStyle(contentCellStyle);
				cellExpCheckoutDateData.setCellValue(generalReport.getExpCheckout_date());

				HSSFCell cellStatusData = detailRow.createCell(8);
				cellStatusData.setCellStyle(contentCellStyle);

				if (null != generalReport.getCheckout_date()) {

					cellStatusData.setCellValue("Departed");

				} else {

					cellStatusData.setCellValue("pending");

				}

				HSSFCell cellTarifData = detailRow.createCell(9);
				cellTarifData.setCellStyle(amountCellStyle);
				cellTarifData.setCellValue(generalReport.getTarif());

				HSSFCell cellDepositData = detailRow.createCell(10);
				cellDepositData.setCellStyle(amountCellStyle);
				cellDepositData.setCellValue(generalReport.getDeposit());

				sheet.createFreezePane(0,6);
			}
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
				.concat((reportTmpl.getReportname()).toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
