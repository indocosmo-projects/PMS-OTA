package com.indocosmo.pms.web.reports.report_designs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.indocosmo.pms.web.reports.model.CustomerReport;
import com.indocosmo.pms.web.reports.model.ReportTemplate;

public class CustomerHistoryReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<CustomerReport> customerReport = reportTmpl.getCustomerReport();
		Map<String, List<CustomerReport>> mapValues = customerReport.parallelStream()
				.collect(Collectors.groupingBy(pl -> pl.getFirst_name() + " " + pl.getLast_name()));

		
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

		sheet.setColumnWidth(0, 1200);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 5500);
		sheet.setColumnWidth(4, 3900);
		sheet.setColumnWidth(5, 3900);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 2400);
		sheet.setColumnWidth(8, 4900);

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

		if (customerReport.size() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellSiNo = tableheading.createCell(0);
			cellSiNo.setCellStyle(headCellStyle);
			cellSiNo.setCellValue("#");

			HSSFCell cellCustomerName = tableheading.createCell(1);
			cellCustomerName.setCellStyle(headCellStyle);
			cellCustomerName.setCellValue("Customer Name");

			HSSFCell cellPhone = tableheading.createCell(2);
			cellPhone.setCellStyle(headCellStyle);
			cellPhone.setCellValue("Phone");

			HSSFCell cellEmail = tableheading.createCell(3);
			cellEmail.setCellStyle(headCellStyle);
			cellEmail.setCellValue("Email");

			HSSFCell cellArrivalDate = tableheading.createCell(4);
			cellArrivalDate.setCellStyle(headCellStyle);
			cellArrivalDate.setCellValue("Arrival Date");

			HSSFCell cellRoomType = tableheading.createCell(5);
			cellRoomType.setCellStyle(headCellStyle);
			cellRoomType.setCellValue("Room Type");

			HSSFCell cellRoomNumber = tableheading.createCell(6);
			cellRoomNumber.setCellStyle(headCellStyle);
			cellRoomNumber.setCellValue("Room No");

			HSSFCell cellNights = tableheading.createCell(7);
			cellNights.setCellStyle(headCellStyle);
			cellNights.setCellValue("Nights");

			HSSFCell cellDepartureDate = tableheading.createCell(8);
			cellDepartureDate.setCellStyle(headCellStyle);
			cellDepartureDate.setCellValue("Departure Date");

			int count = 1;
			int s = 0;
			int countValue = 0;

			for (Map.Entry<String, List<CustomerReport>> entry : mapValues.entrySet()) {

				s++;
				String customerKey = entry.getKey();

				List<CustomerReport> customerData = entry.getValue();

				String ckey = "";
				for (CustomerReport customerListNew : customerData) {

					HSSFRow detailRow = sheet.createRow(6 + countValue);
					detailRow.setHeightInPoints(25);

					if (ckey != customerKey) {
						HSSFCell cellSiNoData = detailRow.createCell(0);
						cellSiNoData.setCellStyle(contentCellStyle);
						cellSiNoData.setCellValue(s + 0);

						HSSFCell cellFirstName = detailRow.createCell(1);
						cellFirstName.setCellStyle(contentCellStyle);
						cellFirstName.setCellValue(customerKey);

						HSSFCell cellMobile = detailRow.createCell(2);
						cellMobile.setCellStyle(contentCellStyle);
						cellMobile.setCellValue(customerListNew.getPhone());

						HSSFCell cellCusEmail = detailRow.createCell(3);
						cellCusEmail.setCellStyle(contentCellStyle);
						cellCusEmail.setCellValue(customerListNew.getEmail());

						ckey = customerKey;
						count = count + 1;

					} else {

						HSSFCell cellSiNoDa = detailRow.createCell(0);
						cellSiNoDa.setCellStyle(contentCellStyle);
						cellSiNoDa.setCellValue("");

						HSSFCell cellFirst = detailRow.createCell(1);
						cellFirst.setCellStyle(contentCellStyle);
						cellFirst.setCellValue("");

						HSSFCell cellMob = detailRow.createCell(2);
						cellMob.setCellStyle(contentCellStyle);
						cellMob.setCellValue("");

						HSSFCell cellCusEm = detailRow.createCell(3);
						cellCusEm.setCellStyle(contentCellStyle);
						cellCusEm.setCellValue("");

					}

					HSSFCell cellArrDate = detailRow.createCell(4);
					cellArrDate.setCellStyle(contentCellStyle);
					cellArrDate.setCellValue(customerListNew.getArr_date());

					HSSFCell cellRoomTypeCode = detailRow.createCell(5);
					cellRoomTypeCode.setCellStyle(contentCellStyle);
					cellRoomTypeCode.setCellValue(customerListNew.getRoom_type_code());

					HSSFCell cellRoomNo = detailRow.createCell(6);
					cellRoomNo.setCellStyle(contentCellStyle);
					cellRoomNo.setCellValue(customerListNew.getRoom_number());

					HSSFCell cellNigh = detailRow.createCell(7);
					cellNigh.setCellStyle(contentCellStyle);
					cellNigh.setCellValue(customerListNew.getNum_nights());

					HSSFCell cellDeptDate = detailRow.createCell(8);
					cellDeptDate.setCellStyle(contentCellStyle);
					cellDeptDate.setCellValue(customerListNew.getAct_depart_date());

					countValue++;
					
					sheet.createFreezePane(0,6);
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
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy");
		String stringToday = sdf.format(today);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat((reportTmpl.getReportname()).toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);
	}

}
