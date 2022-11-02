package com.indocosmo.pms.web.reports.report_designs;

import java.text.DateFormat;
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

import com.indocosmo.pms.web.reports.model.ReceptionReport;
import com.indocosmo.pms.web.reports.model.ReportTemplate;

public class RoomsPlanReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<ReceptionReport> roomPlanList = reportTmpl.getRoomPlanListReport();

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

		int count = 1;
		int sheetCount = 1;
		int rowCount = 6;
		HSSFRow detailRow = null;

		HSSFSheet sheet = workbook.createSheet("Sheet" + (sheetCount));
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 16000);

		HSSFRow headnameRow = sheet.createRow(0);
		headnameRow.setHeightInPoints(15);
		HSSFCell companyCellname = headnameRow.createCell(0);
		companyCellname.setCellValue(reportTmpl.getCompanyname());
		companyCellname.setCellStyle(headNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

		HSSFRow buildingnameRow = sheet.createRow(1);
		buildingnameRow.setHeightInPoints(15);
		HSSFCell cellbuildingname = buildingnameRow.createCell(0);
		cellbuildingname.setCellValue(reportTmpl.getBuilding());
		cellbuildingname.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));

		HSSFRow DetailnameRow = sheet.createRow(2);
		DetailnameRow.setHeightInPoints(15);
		HSSFCell DetailnameCell = DetailnameRow.createCell(0);
		DetailnameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
		DetailnameCell.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));

		HSSFRow nameRow = sheet.createRow(3);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(reportTmpl.getReportname());
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 3));

		if (roomPlanList.size() != 0) {

			for (int i = 0; i < roomPlanList.size(); i++) {
				if (i == 0 || (!roomPlanList.get(i).getNightDate().equals(roomPlanList.get(i - 1).getNightDate()))) {

					String dateFormat = reportTmpl.getDateFormat();
					DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);

					HSSFRow subheaderRow = sheet.createRow(4);
					subheaderRow.setHeightInPoints(25);
					HSSFCell subheadercell = subheaderRow.createCell(0);
					subheadercell.setCellValue("Date: ".concat(
							String.valueOf(simpleDateFormatHotelDate.format(roomPlanList.get(i).getNightDate()))));
					subheadercell.setCellStyle(subheadCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 3));

					HSSFRow tableheading = sheet.createRow(5);
					tableheading.setHeightInPoints(25);

					HSSFCell cellSiNo = tableheading.createCell(0);
					cellSiNo.setCellStyle(headCellStyle);
					cellSiNo.setCellValue("Sl.No");

					HSSFCell cellRoomNo = tableheading.createCell(1);
					cellRoomNo.setCellStyle(headCellStyle);
					cellRoomNo.setCellValue("Room No");

					HSSFCell cellRoomType = tableheading.createCell(2);
					cellRoomType.setCellStyle(headCellStyle);
					cellRoomType.setCellValue("Pax");

					HSSFCell cellGustName = tableheading.createCell(3);
					cellGustName.setCellStyle(headCellStyle);
					cellGustName.setCellValue("Plan Description");
				}

				detailRow = sheet.createRow(rowCount);
				detailRow.setHeightInPoints(25);

				HSSFCell cellSiNoData = detailRow.createCell(0);
				cellSiNoData.setCellStyle(contentCellStyle);
				cellSiNoData.setCellValue(count);

				HSSFCell cellRoomNumberData = detailRow.createCell(1);
				cellRoomNumberData.setCellStyle(contentCellStyle);
				cellRoomNumberData.setCellValue(roomPlanList.get(i).getRoomNumber());

				HSSFCell cellInfantsData = detailRow.createCell(2);
				cellInfantsData.setCellStyle(contentCellStyle);
				cellInfantsData.setCellValue(roomPlanList.get(i).getPax() + roomPlanList.get(i).getChildren()
						+ roomPlanList.get(i).getInfants());

				HSSFCell cellRateDescriptionData = detailRow.createCell(3);
				cellRateDescriptionData.setCellStyle(contentCellStyle);
				cellRateDescriptionData.setCellValue(roomPlanList.get(i).getRateDescription());

				count = count + 1;
				rowCount = rowCount + 1;

				sheet.createFreezePane(0, 6);

				if ((i + 1) >= roomPlanList.size()) {
					break;
				}

				if (((!roomPlanList.get(i).getNightDate().equals(roomPlanList.get(i + 1).getNightDate())))) {
					sheetCount = sheetCount + 1;
					rowCount = 6;
					sheet = workbook.createSheet("Sheet" + (sheetCount));

					sheet.setColumnWidth(0, 3000);
					sheet.setColumnWidth(1, 5000);
					sheet.setColumnWidth(2, 5000);
					sheet.setColumnWidth(3, 16000);

					HSSFRow headNameRow = sheet.createRow(0);
					headNameRow.setHeightInPoints(15);
					HSSFCell companyCellName = headNameRow.createCell(0);
					companyCellName.setCellValue(reportTmpl.getCompanyname());
					companyCellName.setCellStyle(headNameCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

					HSSFRow buildingNameRow = sheet.createRow(1);
					buildingnameRow.setHeightInPoints(15);
					HSSFCell cellBuildingName = buildingNameRow.createCell(0);
					cellBuildingName.setCellValue(reportTmpl.getBuilding());
					cellBuildingName.setCellStyle(detailFontCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));

					HSSFRow DetailNameRow = sheet.createRow(2);
					DetailNameRow.setHeightInPoints(15);
					HSSFCell DetailNameCell = DetailNameRow.createCell(0);
					DetailNameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
					DetailNameCell.setCellStyle(detailFontCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));

					HSSFRow RepnameRow = sheet.createRow(3);
					RepnameRow.setHeightInPoints(35);
					HSSFCell cellReportname = RepnameRow.createCell(0);
					cellReportname.setCellValue(reportTmpl.getReportname());
					cellReportname.setCellStyle(rptNameCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 3));

					String dateFormat = reportTmpl.getDateFormat();
					DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);

					HSSFRow subheaderRow = sheet.createRow(4);
					subheaderRow.setHeightInPoints(25);
					HSSFCell subheadercell = subheaderRow.createCell(0);
					subheadercell.setCellValue("Date: ".concat(
							String.valueOf(simpleDateFormatHotelDate.format(roomPlanList.get(i).getNightDate()))));
					subheadercell.setCellStyle(subheadCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 3));

					HSSFRow tableheading = sheet.createRow(5);
					tableheading.setHeightInPoints(25);

					HSSFCell cellSiNo = tableheading.createCell(0);
					cellSiNo.setCellStyle(headCellStyle);
					cellSiNo.setCellValue("Sl.No");

					HSSFCell cellRoomNo = tableheading.createCell(1);
					cellRoomNo.setCellStyle(headCellStyle);
					cellRoomNo.setCellValue("Room No");

					HSSFCell cellRoomType = tableheading.createCell(2);
					cellRoomType.setCellStyle(headCellStyle);
					cellRoomType.setCellValue("Pax");

					HSSFCell cellGustName = tableheading.createCell(3);
					cellGustName.setCellStyle(headCellStyle);
					cellGustName.setCellValue("Plan Description");

					sheet.createFreezePane(0, 6);

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
