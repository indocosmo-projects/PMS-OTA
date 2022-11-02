package com.indocosmo.pms.web.reports.report_designs;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
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

public class TransactionTransferReportExcel extends AbstractExcelView {

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

		Font noDataFont = workbook.createFont();
		noDataFont.setFontName("Liberation Sans");

		noDataFont.setFontHeightInPoints((short) 12);
		noDataFont.setColor(IndexedColors.BLACK.getIndex());
		noDataFont.setBoldweight(noDataFont.BOLDWEIGHT_BOLD);

		CellStyle noDataCellStyle = workbook.createCellStyle();
		noDataCellStyle.setFont(noDataFont);
		noDataCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font subheadFont = workbook.createFont();
		subheadFont.setFontName("Liberation Sans");

		subheadFont.setFontHeightInPoints((short) 12);
		subheadFont.setColor(IndexedColors.BLACK.getIndex());
		subheadFont.setBoldweight(subheadFont.BOLDWEIGHT_BOLD);

		CellStyle subheadCellStyle = workbook.createCellStyle();
		subheadCellStyle.setFont(subheadFont);
		subheadCellStyle.setAlignment(CellStyle.ALIGN_LEFT);

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

		Font dateRowFont = workbook.createFont();
		dateRowFont.setFontHeightInPoints((short) 12);
		dateRowFont.setColor(IndexedColors.BLACK.getIndex());
		dateRowFont.setBoldweight(dateRowFont.BOLDWEIGHT_BOLD);

		CellStyle daterowCellStyle = workbook.createCellStyle();
		daterowCellStyle.setFont(dateRowFont);
		daterowCellStyle.setWrapText(true);
		daterowCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		daterowCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		daterowCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		daterowCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		daterowCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

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

		CellStyle numericCellStyle = workbook.createCellStyle();
		numericCellStyle.setFont(contentFont);
		numericCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		numericCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		HSSFSheet sheet = workbook.createSheet("Sheet1");

		sheet.setColumnWidth(0, 1000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 5000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);
		sheet.setColumnWidth(8, 3000);
		sheet.setColumnWidth(9, 5000);
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
		cellname.setCellValue("TRANSACTION LIST");
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
			cellRoom.setCellValue("SRC ROOM");

			HSSFCell cellTxnCode = tableheading.createCell(2);
			cellTxnCode.setCellStyle(headCellStyle);
			cellTxnCode.setCellValue("DEST ROOM");

			HSSFCell cellAmount = tableheading.createCell(3);
			cellAmount.setCellStyle(headCellStyle);
			cellAmount.setCellValue("TXN CODE");

			HSSFCell cellTaxCode = tableheading.createCell(4);
			cellTaxCode.setCellStyle(headCellStyle);
			cellTaxCode.setCellValue("AMOUNT");

			HSSFCell cellTax = tableheading.createCell(5);
			cellTax.setCellStyle(headCellStyle);
			cellTax.setCellValue("TAX CODE");

			HSSFCell cellServiceCharge = tableheading.createCell(6);
			cellServiceCharge.setCellStyle(headCellStyle);
			cellServiceCharge.setCellValue("TAX");

			HSSFCell cellTotal = tableheading.createCell(7);
			cellTotal.setCellStyle(headCellStyle);
			cellTotal.setCellValue("SERVICE CHARGE");

			HSSFCell cellPShift = tableheading.createCell(8);
			cellPShift.setCellStyle(headCellStyle);
			cellPShift.setCellValue("NETT");

			HSSFCell cellUser = tableheading.createCell(9);
			cellUser.setCellStyle(headCellStyle);
			cellUser.setCellValue("TXN");

			HSSFCell cellRemarks = tableheading.createCell(10);
			cellRemarks.setCellStyle(headCellStyle);
			cellRemarks.setCellValue("REMARKS");

			HSSFRow dateRow = sheet.createRow(6);
			dateRow.setHeightInPoints(25);

			CellRangeAddress mergedCell = new CellRangeAddress(6, 6, 0, 10);

			HSSFCell cellDate = dateRow.createCell(0);
			cellDate.setCellStyle(daterowCellStyle);
			sheet.addMergedRegion(mergedCell);

			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);

			int rowCount = 7;
			int count = 1;
			int i;
			for (i = 0; i < transactionList.size(); i++) {
				DecimalFormat df = new DecimalFormat("#0.00");
				Transaction transaction = transactionList.get(i);
				dateFormat = transactionList.get(i).getDateFormat();
				DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				java.util.Date date = sdf.parse(transaction.getTransferedDate());
				java.sql.Date sqlDate = new Date(date.getTime());

				HSSFRow detailRow = sheet.createRow(rowCount);
				detailRow.setHeightInPoints(25);

				if (i == 0 || (!transactionList.get(i).getTransferedDate()
						.equals(transactionList.get(i - 1).getTransferedDate()))) {

					cellDate.setCellValue("Transferred : " + simpleDateFormatHotelDate1.format(sqlDate));

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
				if (i == 0) {

					HSSFCell cellsinodata = detailRow.createCell(0);
					cellsinodata.setCellStyle(contentCellStyle);
					cellsinodata.setCellValue(count);

					HSSFCell cellSourceroomdata = detailRow.createCell(1);
					cellSourceroomdata.setCellStyle(contentCellStyle);
					cellSourceroomdata.setCellValue(transaction.getSourceRoom());

				} else if (transactionList.get(i).getSourceRoom().equals(transactionList.get(i - 1).getSourceRoom())
						&& (transactionList.get(i).getTransferedDate()
								.equals(transactionList.get(i - 1).getTransferedDate()))) {
					count = count - 1;

					HSSFCell cellsinodata = detailRow.createCell(0);
					cellsinodata.setCellStyle(contentCellStyle);
					cellsinodata.setCellValue("");

					HSSFCell cellSourceroomdata = detailRow.createCell(1);
					cellSourceroomdata.setCellStyle(contentCellStyle);
					cellSourceroomdata.setCellValue("");

				}

				else if (!(transactionList.get(i).getSourceRoom().equals((transactionList.get(i - 1).getSourceRoom())))
						&& (transactionList.get(i).getTransferedDate()
								.equals(transactionList.get(i - 1).getTransferedDate()))) {

					HSSFCell cellsinodata = detailRow.createCell(0);
					cellsinodata.setCellStyle(contentCellStyle);
					cellsinodata.setCellValue(count);

					HSSFCell cellSourceroomdata = detailRow.createCell(1);
					cellSourceroomdata.setCellStyle(contentCellStyle);
					cellSourceroomdata.setCellValue(transaction.getSourceRoom());

				}

				HSSFCell cellDestinationRoomdata = detailRow.createCell(2);
				cellDestinationRoomdata.setCellStyle(contentCellStyle);
				cellDestinationRoomdata.setCellValue(transaction.getDestinationRoom());

				HSSFCell cellAccmstcodedata = detailRow.createCell(3);
				cellAccmstcodedata.setCellStyle(contentCellStyle);
				cellAccmstcodedata.setCellValue(transaction.getAcc_mst_code());

				HSSFCell cellBaseAmountdata = detailRow.createCell(4);
				cellBaseAmountdata.setCellStyle(numericCellStyle);
				cellBaseAmountdata.setCellValue(transaction.getBase_amount());

				HSSFCell cellTaxCodedata = detailRow.createCell(5);
				cellTaxCodedata.setCellStyle(contentCellStyle);
				cellTaxCodedata.setCellValue(transaction.getTax_code());

				HSSFCell cellTaxdata = detailRow.createCell(6);
				cellTaxdata.setCellStyle(numericCellStyle);
				cellTaxdata.setCellValue(transaction.getTax());

				HSSFCell cellServiceChargedata = detailRow.createCell(7);
				cellServiceChargedata.setCellStyle(numericCellStyle);
				cellServiceChargedata.setCellValue(String.valueOf(transaction.getServiceCharge()));

				HSSFCell cellNettAmountdata = detailRow.createCell(8);
				cellNettAmountdata.setCellStyle(numericCellStyle);
				cellNettAmountdata.setCellValue(transaction.getNett_amount());

				HSSFCell cellTxnDatedata = detailRow.createCell(9);
				cellTxnDatedata.setCellStyle(contentCellStyle);
				cellTxnDatedata.setCellValue(transaction.getTxn_date());

				HSSFCell cellRemarksdata = detailRow.createCell(10);
				cellRemarksdata.setCellStyle(contentCellStyle);
				cellRemarksdata.setCellValue(transaction.getRemarks());

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

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse.concat(reportTmpl.getReportname().toLowerCase().replaceAll("\\s", "") + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
