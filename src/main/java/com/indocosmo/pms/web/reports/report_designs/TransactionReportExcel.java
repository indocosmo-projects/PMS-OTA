package com.indocosmo.pms.web.reports.report_designs;

import java.text.DateFormat;
import java.text.DecimalFormat;
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

import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.transaction.model.Transaction;

public class TransactionReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String dateFormat;
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<Transaction> transactionList = reportTmpl.getTransactionList();

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

		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Font tophdr = workbook.createFont();
		tophdr.setFontHeightInPoints((short) 9);
		tophdr.setColor(IndexedColors.BLACK.getIndex());
		tophdr.setBoldweight(tophdr.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle = workbook.createCellStyle();
		tophdrCellStyle.setFont(tophdr);
		tophdrCellStyle.setWrapText(true);
		tophdrCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

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

		Font contentFont = workbook.createFont();
		contentFont.setFontName("Liberation Sans");

		contentFont.setFontHeightInPoints((short) 9);
		contentFont.setColor(IndexedColors.BLACK.getIndex());

		Font subheadFont = workbook.createFont();
		subheadFont.setFontName("Liberation Sans");

		subheadFont.setFontHeightInPoints((short) 12);
		subheadFont.setColor(IndexedColors.BLACK.getIndex());
		subheadFont.setBoldweight(subheadFont.BOLDWEIGHT_BOLD);

		CellStyle subheadCellStyle = workbook.createCellStyle();
		subheadCellStyle.setFont(subheadFont);
		subheadCellStyle.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setFont(contentFont);
		contentCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		contentCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderRight(CellStyle.BORDER_THIN);

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

		Font headcontentFont = workbook.createFont();
		headcontentFont.setFontHeightInPoints((short) 12);
		headcontentFont.setColor(IndexedColors.BLACK.getIndex());
		headcontentFont.setBoldweight(headcontentFont.BOLDWEIGHT_BOLD);

		Font dateRowFont = workbook.createFont();
		dateRowFont.setFontHeightInPoints((short) 12);
		dateRowFont.setColor(IndexedColors.BLACK.getIndex());
		dateRowFont.setBoldweight(dateRowFont.BOLDWEIGHT_BOLD);

		CellStyle subheaderCellStyle = workbook.createCellStyle();
		subheaderCellStyle.setFont(headcontentFont);
		subheaderCellStyle.setWrapText(true);
		subheaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		subheaderCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

		CellStyle daterowCellStyle = workbook.createCellStyle();
		daterowCellStyle.setFont(dateRowFont);
		daterowCellStyle.setWrapText(true);
		daterowCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		daterowCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		daterowCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		daterowCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		daterowCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

		CellStyle numericCellStyle = workbook.createCellStyle();
		numericCellStyle.setFont(contentFont);
		numericCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		numericCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		HSSFSheet sheet = workbook.createSheet("Sheet1");
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

		sheet.setColumnWidth(0, 1300);
		sheet.setColumnWidth(1, 2000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 3500);
		sheet.setColumnWidth(6, 4500);
		sheet.setColumnWidth(7, 2500);
		sheet.setColumnWidth(8, 2000);
		sheet.setColumnWidth(9, 2500);
		sheet.setColumnWidth(10, 5000);

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

		if (transactionList.size() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellSino = tableheading.createCell(0);
			cellSino.setCellStyle(headCellStyle);
			cellSino.setCellValue("#");

			HSSFCell cellRoom = tableheading.createCell(1);
			cellRoom.setCellStyle(headCellStyle);
			cellRoom.setCellValue("ROOM");

			HSSFCell cellTxnCode = tableheading.createCell(2);
			cellTxnCode.setCellStyle(headCellStyle);
			cellTxnCode.setCellValue("TXN CODE");

			HSSFCell cellAmount = tableheading.createCell(3);
			cellAmount.setCellStyle(headCellStyle);
			cellAmount.setCellValue("AMOUNT");

			HSSFCell cellTaxCode = tableheading.createCell(4);
			cellTaxCode.setCellStyle(headCellStyle);
			cellTaxCode.setCellValue("TAX CODE");

			HSSFCell cellTax = tableheading.createCell(5);
			cellTax.setCellStyle(headCellStyle);
			cellTax.setCellValue("TAX");

			HSSFCell cellServiceCharge = tableheading.createCell(6);
			cellServiceCharge.setCellStyle(headCellStyle);
			cellServiceCharge.setCellValue("SERVICE CHARGE");

			HSSFCell cellTotal = tableheading.createCell(7);
			cellTotal.setCellStyle(headCellStyle);
			cellTotal.setCellValue("TOTAL");

			HSSFCell cellPShift = tableheading.createCell(8);
			cellPShift.setCellStyle(headCellStyle);
			cellPShift.setCellValue("SHIFT");

			HSSFCell cellUser = tableheading.createCell(9);
			cellUser.setCellStyle(headCellStyle);
			cellUser.setCellValue("USER");

			HSSFCell cellRemarks = tableheading.createCell(10);
			cellRemarks.setCellStyle(headCellStyle);
			cellRemarks.setCellValue("REMARKS");

			int rowCount = 6;
			int count = 1;
			int i;
			for (i = 0; i < transactionList.size(); i++) {

				DecimalFormat df = new DecimalFormat("#0.00");
				Transaction transaction = transactionList.get(i);
				dateFormat = transactionList.get(i).getDateFormat();
				DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				java.util.Date date = sdf.parse(transaction.getTxn_date());
				Date sqlDate = new Date(date.getTime());

				if (i == 0
						|| (!transactionList.get(i).getTxn_date().equals(transactionList.get(i - 1).getTxn_date()))) {

					HSSFRow dateRow = sheet.createRow(rowCount);
					dateRow.setHeightInPoints(25);

					CellRangeAddress mergedCell = new CellRangeAddress(rowCount, rowCount, 0, 10);

					HSSFCell cellDate = dateRow.createCell(0);
					cellDate.setCellStyle(daterowCellStyle);
					sheet.addMergedRegion(mergedCell);
					cellDate.setCellValue(simpleDateFormatHotelDate1.format(sqlDate));

					RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);

					rowCount = rowCount + 1;

					HSSFRow detailRow = sheet.createRow(rowCount);
					detailRow.setHeightInPoints(25);

					if (i > 0) {

						count = 1;
						HSSFCell cellsinodata = detailRow.createCell(0);
						cellsinodata.setCellStyle(contentCellStyle);
						cellsinodata.setCellValue(count);

						HSSFCell cellSourceroomdata = detailRow.createCell(1);
						cellSourceroomdata.setCellStyle(contentCellStyle);
						cellSourceroomdata.setCellValue(transaction.getSourceRoom());

					}
				}

				HSSFRow detailTempRow = null;

				if (sheet.getRow(rowCount) == null) {
					detailTempRow = sheet.createRow(rowCount);
					detailTempRow.setHeightInPoints(25);
				} else {
					detailTempRow = sheet.getRow(rowCount);
				}

				if (transaction.getSourceRoom() == null) {
					HSSFCell cellsinodata = detailTempRow.createCell(0);
					cellsinodata.setCellStyle(contentCellStyle);
					cellsinodata.setCellValue(count);

					HSSFCell cellSourceroomdata = detailTempRow.createCell(1);
					cellSourceroomdata.setCellStyle(contentCellStyle);
					cellSourceroomdata.setCellValue("");
				} else {

					if (i == 0) {
						HSSFCell cellsinodata = detailTempRow.createCell(0);
						cellsinodata.setCellStyle(contentCellStyle);
						cellsinodata.setCellValue(count);

						HSSFCell cellSourceroomdata = detailTempRow.createCell(1);
						cellSourceroomdata.setCellStyle(contentCellStyle);
						cellSourceroomdata.setCellValue(transaction.getSourceRoom());
					}

					else if (transactionList.get(i).getSourceRoom().equals((transactionList.get(i - 1).getSourceRoom()))
							&& (transactionList.get(i).getTxn_date()
									.equals(transactionList.get(i - 1).getTxn_date()))) {
						count = count - 1;
						HSSFCell cellsinodata = detailTempRow.createCell(0);
						cellsinodata.setCellStyle(contentCellStyle);
						cellsinodata.setCellValue("");

						HSSFCell cellSourceroomdata = detailTempRow.createCell(1);
						cellSourceroomdata.setCellStyle(contentCellStyle);
						cellSourceroomdata.setCellValue("");
					}

					else if (!((transactionList.get(i).getSourceRoom())
							.equals((transactionList.get(i - 1).getSourceRoom())))
							&& (transactionList.get(i).getTxn_date()
									.equals(transactionList.get(i - 1).getTxn_date()))) {
						HSSFCell cellsinodata = detailTempRow.createCell(0);
						cellsinodata.setCellStyle(contentCellStyle);
						cellsinodata.setCellValue(count);

						HSSFCell cellSourceroomdata = detailTempRow.createCell(1);
						cellSourceroomdata.setCellStyle(contentCellStyle);
						cellSourceroomdata.setCellValue(transaction.getSourceRoom());
					}

				}

				HSSFCell cellAccmstcodedata = detailTempRow.createCell(2);
				cellAccmstcodedata.setCellStyle(contentCellStyle);
				cellAccmstcodedata.setCellValue(transaction.getAcc_mst_code());

				HSSFCell cellBaseamountdata = detailTempRow.createCell(3);
				cellBaseamountdata.setCellStyle(numericCellStyle);
				cellBaseamountdata.setCellValue(df.format(transaction.getBase_amount()));

				HSSFCell cellTaxcodedata = detailTempRow.createCell(4);
				cellTaxcodedata.setCellStyle(contentCellStyle);
				cellTaxcodedata.setCellValue(transaction.getTax_code());

				if (transaction.getTax() == 0.00) {

					HSSFCell cellTaxdata = detailTempRow.createCell(5);
					cellTaxdata.setCellStyle(contentCellStyle);
					cellTaxcodedata.setCellValue("");

				} else {
					if (transaction.isInclude_tax() == true) {

						HSSFCell cellTaxdata = detailTempRow.createCell(5);
						cellTaxdata.setCellStyle(numericCellStyle);
						cellTaxdata.setCellValue(String.valueOf(df.format(transaction.getTax())).concat("(Incl.)"));
					} else {

						HSSFCell cellTaxdata = detailTempRow.createCell(5);
						cellTaxdata.setCellStyle(numericCellStyle);
						cellTaxdata.setCellValue(String.valueOf(df.format(transaction.getTax())).concat("(Excl.)"));
					}
				}

				HSSFCell cellServiceChargedata = detailTempRow.createCell(6);
				cellServiceChargedata.setCellStyle(numericCellStyle);
				cellServiceChargedata.setCellValue(String.valueOf(transaction.getServiceCharge()));

				if (transaction.getNett_amount() < 0) {
					Double nettamunt = transaction.getNett_amount() * (-1);

					HSSFCell cellnettamuntdata = detailTempRow.createCell(7);
					cellnettamuntdata.setCellStyle(numericCellStyle);
					cellnettamuntdata.setCellValue(String.valueOf(df.format(nettamunt)));
				} else {

					HSSFCell cellnettamuntdata = detailTempRow.createCell(7);
					cellnettamuntdata.setCellStyle(numericCellStyle);
					cellnettamuntdata.setCellValue(String.valueOf(df.format(transaction.getNett_amount())));
				}

				HSSFCell cellShiftCodedata = detailTempRow.createCell(8);
				cellShiftCodedata.setCellStyle(contentCellStyle);
				cellShiftCodedata.setCellValue(String.valueOf(transaction.getShiftCode()));

				HSSFCell cellUsernamedata = detailTempRow.createCell(9);
				cellUsernamedata.setCellStyle(contentCellStyle);
				cellUsernamedata.setCellValue(String.valueOf(transaction.getUsername()));

				if (transaction.getRemarks() == null) {
					HSSFCell cellRemarksdata = detailTempRow.createCell(10);
					cellRemarksdata.setCellStyle(contentCellStyle);
					cellRemarksdata.setCellValue("");
				} else {
					HSSFCell cellRemarksdata = detailTempRow.createCell(10);
					cellRemarksdata.setCellStyle(contentCellStyle);
					cellRemarksdata.setCellValue(String.valueOf(transaction.getRemarks()));

				}
				count = count + 1;

				rowCount = rowCount + 1;

				sheet.createFreezePane(0, 6);

			}
		} else {

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 10));

		}

		Date today1 = new Date();
		SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday3 = sdf3.format(today1);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat(reportTmpl.getReportname().toLowerCase().replaceAll("\\s", "") + stringToday3 + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
