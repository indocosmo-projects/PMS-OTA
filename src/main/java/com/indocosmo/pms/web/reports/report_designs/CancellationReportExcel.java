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

import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;

public class CancellationReportExcel extends AbstractExcelView {

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

		subheadFont.setFontHeightInPoints((short) 13);
		subheadFont.setColor(IndexedColors.BLACK.getIndex());
		subheadFont.setBoldweight(subheadFont.BOLDWEIGHT_BOLD);

		CellStyle subheadCellStyle = workbook.createCellStyle();
		subheadCellStyle.setFont(subheadFont);
		subheadCellStyle.setAlignment(CellStyle.ALIGN_LEFT);

		Font noDataCellFont = workbook.createFont();
		noDataCellFont.setFontName("Liberation Sans");

		noDataCellFont.setFontHeightInPoints((short) 12);
		noDataCellFont.setColor(IndexedColors.BLACK.getIndex());
		noDataCellFont.setBoldweight(noDataCellFont.BOLDWEIGHT_BOLD);

		CellStyle noDataCellStyle = workbook.createCellStyle();
		noDataCellStyle.setFont(noDataCellFont);
		noDataCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

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
		sheet.setColumnWidth(1, 2600);
		sheet.setColumnWidth(2, 3300);
		sheet.setColumnWidth(3, 4900);
		sheet.setColumnWidth(4, 4900);
		sheet.setColumnWidth(5, 5500);
		sheet.setColumnWidth(6, 3300);
		sheet.setColumnWidth(7, 5500);
		sheet.setColumnWidth(8, 2300);

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
		cellname.setCellValue(reportTmpl.getReportname());
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 8));

		HSSFRow subheaderRow = sheet.createRow(4);
		subheaderRow.setHeightInPoints(25);
		HSSFCell subheadercell = subheaderRow.createCell(0);
		subheadercell.setCellValue(reportTmpl.getDateFilter());
		subheadercell.setCellStyle(subheadCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));

		if (resvHdrList.size() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellSiNo = tableheading.createCell(0);
			cellSiNo.setCellStyle(headCellStyle);
			cellSiNo.setCellValue("#");

			HSSFCell cellResvNo = tableheading.createCell(1);
			cellResvNo.setCellStyle(headCellStyle);
			cellResvNo.setCellValue("Resv No");

			HSSFCell cellResvDate = tableheading.createCell(2);
			cellResvDate.setCellStyle(headCellStyle);
			cellResvDate.setCellValue("Resv Date");

			HSSFCell cellExpArrival = tableheading.createCell(3);
			cellExpArrival.setCellStyle(headCellStyle);
			cellExpArrival.setCellValue("Exp Arrival Date");

			HSSFCell cellExpDepart = tableheading.createCell(4);
			cellExpDepart.setCellStyle(headCellStyle);
			cellExpDepart.setCellValue("Exp Depart Date");

			HSSFCell cellResvdBy = tableheading.createCell(5);
			cellResvdBy.setCellStyle(headCellStyle);
			cellResvdBy.setCellValue("Resvd By");

			HSSFCell cellPhone = tableheading.createCell(6);
			cellPhone.setCellStyle(headCellStyle);
			cellPhone.setCellValue("Phone");

			HSSFCell cellResvdFor = tableheading.createCell(7);
			cellResvdFor.setCellStyle(headCellStyle);
			cellResvdFor.setCellValue("Resvd For");

			HSSFCell cellRooms = tableheading.createCell(8);
			cellRooms.setCellStyle(headCellStyle);
			cellRooms.setCellValue("Rooms");

			int i;
			for (i = 0; i < resvHdrList.size(); i++) {

				ResvHdr resvhdr = resvHdrList.get(i);
				dateFormat = resvHdrList.get(i).getDateFormat();
				DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);

				HSSFRow detailRow = sheet.createRow(6 + i);
				detailRow.setHeightInPoints(25);

				HSSFCell cellSiNoData = detailRow.createCell(0);
				cellSiNoData.setCellStyle(contentCellStyle);
				cellSiNoData.setCellValue(i + 1);

				HSSFCell cellResvData = detailRow.createCell(1);
				cellResvData.setCellStyle(contentCellStyle);
				cellResvData.setCellValue(resvhdr.getResvNo());

				HSSFCell cellRsvDate = detailRow.createCell(2);
				cellRsvDate.setCellStyle(contentCellStyle);
				cellRsvDate.setCellValue(simpleDateFormatHotelDate1.format(resvhdr.getResvDate()));

				HSSFCell cellArrival = detailRow.createCell(3);
				cellArrival.setCellStyle(contentCellStyle);
				cellArrival.setCellValue(simpleDateFormatHotelDate1.format(resvhdr.getMinArrDate()));

				HSSFCell cellDeparture = detailRow.createCell(4);
				cellDeparture.setCellStyle(contentCellStyle);
				cellDeparture.setCellValue(simpleDateFormatHotelDate1.format(resvhdr.getMaxDepartDate()));

				HSSFCell cellReserBy = detailRow.createCell(5);
				cellReserBy.setCellStyle(contentCellStyle);
				cellReserBy.setCellValue(
						resvhdr.getResvByFirstName().concat("").concat(String.valueOf(resvhdr.getResvByLastName())));

				HSSFCell cellPhoneNo = detailRow.createCell(6);
				cellPhoneNo.setCellStyle(contentCellStyle);
				cellPhoneNo.setCellValue(resvhdr.getResvByPhone());

				HSSFCell cellResvFor = detailRow.createCell(7);
				cellResvFor.setCellStyle(contentCellStyle);
				cellResvFor.setCellValue(resvhdr.getResvFor());

				HSSFCell cellNoRooms = detailRow.createCell(8);
				cellNoRooms.setCellStyle(contentCellStyle);
				cellNoRooms.setCellValue(resvhdr.getNumRooms());

				sheet.createFreezePane(0, 6);
			}

		} else {

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 8));
		}

		Date today1 = new Date();
		SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday3 = sdf3.format(today1);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat((reportTmpl.getReportname()).toLowerCase().replaceAll("\\s", "") + stringToday3 + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
