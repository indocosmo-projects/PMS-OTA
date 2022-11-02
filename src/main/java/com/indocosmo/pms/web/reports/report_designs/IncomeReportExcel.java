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
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.pms.web.reports.model.IncomeReport;
import com.indocosmo.pms.web.reports.model.ReportTemplate;

public class IncomeReportExcel extends AbstractExcelView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String dateFormat;
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<com.indocosmo.pms.web.reports.model.IncomeReport> incomeReportList = reportTmpl.getIncomeReport();
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

		CellStyle floatStyle = workbook.createCellStyle();
		floatStyle.setFont(contentFont);
		floatStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		floatStyle.setBorderTop(CellStyle.BORDER_THIN);
		floatStyle.setBorderBottom(CellStyle.BORDER_THIN);
		floatStyle.setBorderLeft(CellStyle.BORDER_THIN);
		floatStyle.setBorderRight(CellStyle.BORDER_THIN);

		CellStyle totalStyle = workbook.createCellStyle();
		totalStyle.setFont(columnHeadFond);
		totalStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		totalStyle.setBorderTop(CellStyle.BORDER_THIN);
		totalStyle.setBorderBottom(CellStyle.BORDER_THIN);
		totalStyle.setBorderLeft(CellStyle.BORDER_THIN);
		totalStyle.setBorderRight(CellStyle.BORDER_THIN);

		sheet.setColumnWidth(0, 1300);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 7000);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 6000);

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

		HSSFRow nameRow = sheet.createRow(3);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(reportTmpl.getReportname());
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));

		HSSFRow subheaderRow = sheet.createRow(4);
		subheaderRow.setHeightInPoints(25);
		HSSFCell subheadercell = subheaderRow.createCell(0);
		subheadercell.setCellValue(reportTmpl.getDateFilter());
		subheadercell.setCellStyle(subheadCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 4));

		if (incomeReportList.size() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellSiNo = tableheading.createCell(0);
			cellSiNo.setCellStyle(headCellStyle);
			cellSiNo.setCellValue("#");

			HSSFCell cellTransactionDate = tableheading.createCell(1);
			cellTransactionDate.setCellStyle(headCellStyle);
			cellTransactionDate.setCellValue("Transaction Date");

			HSSFCell cellReceiptNO = tableheading.createCell(2);
			cellReceiptNO.setCellStyle(headCellStyle);
			cellReceiptNO.setCellValue("Receipt/Invoice No");

			HSSFCell cellMode = tableheading.createCell(3);
			cellMode.setCellStyle(headCellStyle);
			cellMode.setCellValue("Mode of Payment");

			HSSFCell cellAmount = tableheading.createCell(4);
			cellAmount.setCellStyle(headCellStyle);
			cellAmount.setCellValue("Amount");

			int count = 0;
			int rawCount = 6;
			float total = 0.0f;
			float grandTotal = 0.0f;
			for (IncomeReport incomeReport : incomeReportList) {
				dateFormat = incomeReport.getDateFormat();
				DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);
				if (count == 0 || incomeReport.getDate().equals(incomeReportList.get(count - 1).getDate())) {
					total += incomeReport.getAmount();
				} else {
					HSSFRow totalRw = sheet.createRow(rawCount);

					totalRw.setHeightInPoints(25);
					HSSFCell totalCell = totalRw.createCell(0);
					totalCell.setCellValue("Total");
					totalCell.setCellStyle(totalStyle);
					CellRangeAddress mergedCell = new CellRangeAddress(rawCount, rawCount, 0, 3);
					sheet.addMergedRegion(mergedCell);
					RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);

					HSSFCell totalAmnt = totalRw.createCell(4);
					totalAmnt.setCellValue(String.format("%.2f", total));
					totalAmnt.setCellStyle(totalStyle);
					rawCount++;
					total = incomeReport.getAmount();
				}

				HSSFRow detailRow = sheet.createRow(rawCount);
				detailRow.setHeightInPoints(25);

				HSSFCell cellSiNoData = detailRow.createCell(0);
				cellSiNoData.setCellStyle(contentCellStyle);
				cellSiNoData.setCellValue(count + 1);

				HSSFCell cellTxnDate = detailRow.createCell(1);
				cellTxnDate.setCellStyle(contentCellStyle);
				cellTxnDate.setCellValue(simpleDateFormatHotelDate1.format(incomeReport.getDate()));

				HSSFCell cellRcptNo = detailRow.createCell(2);
				cellRcptNo.setCellStyle(contentCellStyle);
				if (incomeReport.getReceiptNo() != null) {
					cellRcptNo.setCellValue(incomeReport.getReceiptNo());
				} else {
					cellRcptNo.setCellValue(incomeReport.getInvoiceNo());
				}

				HSSFCell cellMd = detailRow.createCell(3);
				cellMd.setCellStyle(contentCellStyle);
				cellMd.setCellValue(incomeReport.getMode());

				HSSFCell cellAmnt = detailRow.createCell(4);
				cellAmnt.setCellStyle(floatStyle);
				cellAmnt.setCellValue(String.format("%.2f", incomeReport.getAmount()));

				grandTotal += incomeReport.getAmount();
				rawCount++;
				count++;
			}

			HSSFRow totalRw = sheet.createRow(rawCount);

			totalRw.setHeightInPoints(25);
			HSSFCell totalCell = totalRw.createCell(0);
			totalCell.setCellValue("Total");
			totalCell.setCellStyle(totalStyle);
			CellRangeAddress cellRangeAddress1 = new CellRangeAddress(rawCount, rawCount, 0, 3);
			sheet.addMergedRegion(cellRangeAddress1);

			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress1, sheet, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress1, sheet, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress1, sheet, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress1, sheet, workbook);

			HSSFCell totalAmnt = totalRw.createCell(4);
			totalAmnt.setCellValue(String.format("%.2f", total));
			totalAmnt.setCellStyle(totalStyle);

			HSSFRow gTotalRw = sheet.createRow(rawCount + 1);

			gTotalRw.setHeightInPoints(25);
			HSSFCell gTotalCell = gTotalRw.createCell(0);
			gTotalCell.setCellValue(" Grand Total");
			gTotalCell.setCellStyle(totalStyle);
			CellRangeAddress cellRangeAddress2 = new CellRangeAddress(rawCount + 1, rawCount + 1, 0, 3);

			sheet.addMergedRegion(cellRangeAddress2);

			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress2, sheet, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress2, sheet, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress2, sheet, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress2, sheet, workbook);

			HSSFCell gTotalAmnt = gTotalRw.createCell(4);
			gTotalAmnt.setCellValue(String.format("%.2f", grandTotal));
			gTotalAmnt.setCellStyle(totalStyle);
			
			sheet.createFreezePane(0,6);

		} else {

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 4));

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
