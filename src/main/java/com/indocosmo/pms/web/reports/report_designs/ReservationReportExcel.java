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

import com.indocosmo.pms.enumerator.ReservationStatus;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;

public class ReservationReportExcel extends AbstractExcelView {

	String dateFormat;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");

		List<ResvHdr> resvHdrList = reportTmpl.getResvHdrList();

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

		subheadFont.setFontHeightInPoints((short) 14);
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

		CellStyle noHeadCellStyle = workbook.createCellStyle();
		noHeadCellStyle.setFont(noDataFont);
		noHeadCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

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

		sheet.setColumnWidth(0, 1300);
		sheet.setColumnWidth(1, 2000);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 6400);
		sheet.setColumnWidth(5, 3300);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 2500);
		sheet.setColumnWidth(8, 2400);
		sheet.setColumnWidth(9, 3700);

		HSSFRow headnameRow = sheet.createRow(0);
		headnameRow.setHeightInPoints(15);
		HSSFCell companyCellname = headnameRow.createCell(0);
		companyCellname.setCellValue(reportTmpl.getCompanyname());
		companyCellname.setCellStyle(headNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

		HSSFRow buildingnameRow = sheet.createRow(1);
		buildingnameRow.setHeightInPoints(15);
		HSSFCell cellbuildingname = buildingnameRow.createCell(0);
		cellbuildingname.setCellValue(reportTmpl.getBuilding());
		cellbuildingname.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));

		HSSFRow DetailnameRow = sheet.createRow(2);
		DetailnameRow.setHeightInPoints(15);
		HSSFCell DetailnameCell = DetailnameRow.createCell(0);
		DetailnameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
		DetailnameCell.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 9));

		HSSFRow nameRow = sheet.createRow(3);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(reportTmpl.getReportname());
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 9));

		HSSFRow subheaderRow = sheet.createRow(4);
		subheaderRow.setHeightInPoints(25);
		HSSFCell subheadercell = subheaderRow.createCell(0);
		subheadercell.setCellValue(reportTmpl.getDateFilter());
		subheadercell.setCellStyle(subheadCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 9));

		if (resvHdrList.size() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellSiNo = tableheading.createCell(0);
			cellSiNo.setCellStyle(headCellStyle);
			cellSiNo.setCellValue("#");

			HSSFCell cellResv = tableheading.createCell(1);
			cellResv.setCellStyle(headCellStyle);
			cellResv.setCellValue("Resv#");

			HSSFCell cellArrivalDate = tableheading.createCell(2);
			cellArrivalDate.setCellStyle(headCellStyle);
			cellArrivalDate.setCellValue("Arrival Date");

			HSSFCell cellDeparture = tableheading.createCell(3);
			cellDeparture.setCellStyle(headCellStyle);
			cellDeparture.setCellValue("Departure");

			HSSFCell cellResvBy = tableheading.createCell(4);
			cellResvBy.setCellStyle(headCellStyle);
			cellResvBy.setCellValue("Resv By");

			HSSFCell cellMealPlan = tableheading.createCell(5);
			cellMealPlan.setCellStyle(headCellStyle);
			cellMealPlan.setCellValue("Phone");

			HSSFCell cellCheckinDate = tableheading.createCell(6);
			cellCheckinDate.setCellStyle(headCellStyle);
			cellCheckinDate.setCellValue("Resv For");

			HSSFCell cellCheckoutDate = tableheading.createCell(7);
			cellCheckoutDate.setCellStyle(headCellStyle);
			cellCheckoutDate.setCellValue("Rooms");

			HSSFCell cellTariff = tableheading.createCell(8);
			cellTariff.setCellStyle(headCellStyle);
			cellTariff.setCellValue("Nights");

			HSSFCell cellStatus = tableheading.createCell(9);
			cellStatus.setCellStyle(headCellStyle);
			cellStatus.setCellValue("Status");

			int i;
			for (i = 0; i < resvHdrList.size(); i++) {

				ResvHdr resvhdr = resvHdrList.get(i);
				dateFormat = resvHdrList.get(i).getDateFormat();
				DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);

				HSSFRow dateRow = sheet.createRow(6);
				dateRow.setHeightInPoints(25);
				HSSFCell dateCell = dateRow.createCell(0);
				dateCell.setCellValue("Reservation Date : " + simpleDateFormatHotelDate.format(resvhdr.getResvDate()));
				cellname.setCellStyle(subheadCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 9));

				HSSFRow detailRow = sheet.createRow(7 + i);
				detailRow.setHeightInPoints(25);

				HSSFCell cellSiNoData = detailRow.createCell(0);
				cellSiNoData.setCellStyle(contentCellStyle);
				cellSiNoData.setCellValue(i + 1);

				HSSFCell cellResvData = detailRow.createCell(1);
				cellResvData.setCellStyle(contentCellStyle);
				cellResvData.setCellValue(resvhdr.getResvNo());

				HSSFCell cellArrival = detailRow.createCell(2);
				cellArrival.setCellStyle(contentCellStyle);
				cellArrival.setCellValue(simpleDateFormatHotelDate.format(resvhdr.getMinArrDate()));

				HSSFCell cellDepartureDate = detailRow.createCell(3);
				cellDepartureDate.setCellStyle(contentCellStyle);
				cellDepartureDate.setCellValue(simpleDateFormatHotelDate.format(resvhdr.getMaxDepartDate()));

				HSSFCell cellReserBy = detailRow.createCell(4);
				cellReserBy.setCellStyle(contentCellStyle);
				cellReserBy.setCellValue(
						resvhdr.getResvByFirstName().concat("").concat(String.valueOf(resvhdr.getResvByLastName())));

				HSSFCell cellPhone = detailRow.createCell(5);
				cellPhone.setCellStyle(contentCellStyle);
				cellPhone.setCellValue(resvhdr.getResvByPhone());

				HSSFCell cellResvFor = detailRow.createCell(6);
				cellResvFor.setCellStyle(contentCellStyle);
				cellResvFor.setCellValue(resvhdr.getResvFor());

				HSSFCell cellRooms = detailRow.createCell(7);
				cellRooms.setCellStyle(contentCellStyle);
				cellRooms.setCellValue(resvhdr.getNumRooms());

				HSSFCell cellNights = detailRow.createCell(8);
				cellNights.setCellStyle(contentCellStyle);
				cellNights.setCellValue(resvhdr.getNumNights());

				HSSFCell cellStatusDisplay = detailRow.createCell(9);
				cellStatusDisplay.setCellStyle(contentCellStyle);
				cellStatusDisplay.setCellValue(ReservationStatus.get(resvhdr.getStatus()).name());

				sheet.createFreezePane(0, 6);
			}
		} else {

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noHeadCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 9));
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
