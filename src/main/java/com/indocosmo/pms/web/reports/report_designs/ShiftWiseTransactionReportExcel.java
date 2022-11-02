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
import com.indocosmo.pms.web.transaction.model.Transaction;

public class ShiftWiseTransactionReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String dateFormat;
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<Transaction> shiftWiseTransactionList = reportTmpl.getShiftWiseTransactionList();
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

		CellStyle numericCellStyle = workbook.createCellStyle();
		numericCellStyle.setFont(contentFont);
		numericCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		numericCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		sheet.setColumnWidth(0, 1300);
		sheet.setColumnWidth(1, 2300);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 3300);
		sheet.setColumnWidth(6, 5500);
		sheet.setColumnWidth(7, 2500);
		sheet.setColumnWidth(8, 2500);
		sheet.setColumnWidth(9, 3000);
		sheet.setColumnWidth(10, 4000);

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

		if (shiftWiseTransactionList.size() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellSiNo = tableheading.createCell(0);
			cellSiNo.setCellStyle(headCellStyle);
			cellSiNo.setCellValue("#");

			HSSFCell cellResv = tableheading.createCell(1);
			cellResv.setCellStyle(headCellStyle);
			cellResv.setCellValue("ROOM");

			HSSFCell cellArrivalDate = tableheading.createCell(2);
			cellArrivalDate.setCellStyle(headCellStyle);
			cellArrivalDate.setCellValue("TXN CODE");

			HSSFCell cellDeparture = tableheading.createCell(3);
			cellDeparture.setCellStyle(headCellStyle);
			cellDeparture.setCellValue("AMOUNT");

			HSSFCell cellResvBy = tableheading.createCell(4);
			cellResvBy.setCellStyle(headCellStyle);
			cellResvBy.setCellValue("TAX CODE");

			HSSFCell cellMealPlan = tableheading.createCell(5);
			cellMealPlan.setCellStyle(headCellStyle);
			cellMealPlan.setCellValue("TAX");

			HSSFCell cellCheckinDate = tableheading.createCell(6);
			cellCheckinDate.setCellStyle(headCellStyle);
			cellCheckinDate.setCellValue("SERVICE CHARGE");

			HSSFCell cellCheckoutDate = tableheading.createCell(7);
			cellCheckoutDate.setCellStyle(headCellStyle);
			cellCheckoutDate.setCellValue("TOTAL");

			HSSFCell cellTariff = tableheading.createCell(8);
			cellTariff.setCellStyle(headCellStyle);
			cellTariff.setCellValue("SHIFT");

			HSSFCell cellStatus = tableheading.createCell(9);
			cellStatus.setCellStyle(headCellStyle);
			cellStatus.setCellValue("USER");

			HSSFCell cellRemarks = tableheading.createCell(10);
			cellRemarks.setCellStyle(headCellStyle);
			cellRemarks.setCellValue("REMARKS");

			int count = 1;
			int rowCount = 7;

			int i;
			for (i = 0; i < shiftWiseTransactionList.size(); i++) {

				DecimalFormat df = new DecimalFormat("#0.00");
				Transaction transaction = shiftWiseTransactionList.get(i);
				dateFormat = shiftWiseTransactionList.get(i).getDateFormat();
				DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = sdf.parse(transaction.getTxn_date());
				java.sql.Date sqlDate = new Date(date.getTime());

				HSSFRow detailRow = sheet.createRow(rowCount);
				detailRow.setHeightInPoints(25);

				if (i == 0 || (!shiftWiseTransactionList.get(i).getTxn_date()
						.equals(shiftWiseTransactionList.get(i - 1).getTxn_date()))) {

					HSSFRow dateRow = sheet.createRow(6);
					dateRow.setHeightInPoints(25);
					HSSFCell dateCell = dateRow.createCell(0);
					dateCell.setCellValue(simpleDateFormatHotelDate1.format(sqlDate));
					cellname.setCellStyle(subheadCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 10));

					if (i > 0) {

						count = 1;

						HSSFCell cellSiNoData = detailRow.createCell(0);
						cellSiNoData.setCellStyle(contentCellStyle);
						cellSiNoData.setCellValue(count);

						HSSFCell cellResvData = detailRow.createCell(1);
						cellResvData.setCellStyle(contentCellStyle);
						cellResvData.setCellValue(transaction.getSourceRoom());
					}
				}
				if (transaction.getSourceRoom() == null) {

					HSSFCell cellSiNoData = detailRow.createCell(0);
					cellSiNoData.setCellStyle(contentCellStyle);
					cellSiNoData.setCellValue(count);

					HSSFCell cellResvData = detailRow.createCell(1);
					cellResvData.setCellStyle(contentCellStyle);
					cellResvData.setCellValue("");
				} else {

					if (i == 0) {

						HSSFCell cellSiNoData = detailRow.createCell(0);
						cellSiNoData.setCellStyle(contentCellStyle);
						cellSiNoData.setCellValue(count);

						HSSFCell cellResvData = detailRow.createCell(1);
						cellResvData.setCellStyle(contentCellStyle);
						cellResvData.setCellValue(transaction.getSourceRoom());
					} else if (Integer.parseInt(shiftWiseTransactionList.get(i).getSourceRoom()) == Integer
							.parseInt(shiftWiseTransactionList.get(i - 1).getSourceRoom())
							&& (shiftWiseTransactionList.get(i).getTxn_date()
									.equals(shiftWiseTransactionList.get(i - 1).getTxn_date()))) {
						count = count - 1;
						HSSFCell cellSiNoData = detailRow.createCell(0);
						cellSiNoData.setCellStyle(contentCellStyle);
						cellSiNoData.setCellValue("");

						HSSFCell cellResvData = detailRow.createCell(1);
						cellResvData.setCellStyle(contentCellStyle);
						cellResvData.setCellValue("");
					} else if (Integer.parseInt(shiftWiseTransactionList.get(i).getSourceRoom()) != Integer
							.parseInt(shiftWiseTransactionList.get(i - 1).getSourceRoom())
							&& (shiftWiseTransactionList.get(i).getTxn_date()
									.equals(shiftWiseTransactionList.get(i - 1).getTxn_date()))) {

						HSSFCell cellSiNoData = detailRow.createCell(0);
						cellSiNoData.setCellStyle(contentCellStyle);
						cellSiNoData.setCellValue(count);

						HSSFCell cellResvData = detailRow.createCell(1);
						cellResvData.setCellStyle(contentCellStyle);
						cellResvData.setCellValue(transaction.getSourceRoom());
					}
				}

				HSSFCell cellAccMstCode = detailRow.createCell(2);
				cellAccMstCode.setCellStyle(contentCellStyle);
				cellAccMstCode.setCellValue(transaction.getAcc_mst_code());

				HSSFCell cellBaseAmount = detailRow.createCell(3);
				cellBaseAmount.setCellStyle(numericCellStyle);
				cellBaseAmount.setCellValue(df.format(transaction.getBase_amount()));

				HSSFCell cellTaxCode = detailRow.createCell(4);
				cellTaxCode.setCellStyle(contentCellStyle);
				cellTaxCode.setCellValue(transaction.getTax_code());

				if (transaction.getTax() == 0.00) {

					HSSFCell cellTaxdata = detailRow.createCell(5);
					cellTaxdata.setCellStyle(contentCellStyle);
					cellTaxdata.setCellValue("");
				} else {

					if (transaction.isInclude_tax() == true) {

						HSSFCell cellTaxdata = detailRow.createCell(5);
						cellTaxdata.setCellStyle(numericCellStyle);
						cellTaxdata.setCellValue(String.valueOf(df.format(transaction.getTax())).concat("(Incl.)"));
					} else {

						HSSFCell cellTaxdata = detailRow.createCell(5);
						cellTaxdata.setCellStyle(numericCellStyle);
						cellTaxdata.setCellValue(String.valueOf(df.format(transaction.getTax())).concat("(Excl.)"));
					}
				}
				HSSFCell cellServiceChargedata = detailRow.createCell(6);
				cellServiceChargedata.setCellStyle(numericCellStyle);
				cellServiceChargedata.setCellValue(String.valueOf(transaction.getServiceCharge()));

				if (transaction.getNett_amount() < 0) {

					Double nettamunt = transaction.getNett_amount() * (-1);
					HSSFCell cellnettamuntdata = detailRow.createCell(7);
					cellnettamuntdata.setCellStyle(numericCellStyle);
					cellnettamuntdata.setCellValue(String.valueOf(df.format(nettamunt)));
				} else {

					HSSFCell cellnettamuntdata = detailRow.createCell(7);
					cellnettamuntdata.setCellStyle(numericCellStyle);
					cellnettamuntdata.setCellValue(String.valueOf(df.format(transaction.getNett_amount())));
				}

				HSSFCell cellShiftCodedata = detailRow.createCell(8);
				cellShiftCodedata.setCellStyle(contentCellStyle);
				cellShiftCodedata.setCellValue(String.valueOf(transaction.getShiftCode()));

				HSSFCell cellUsernamedata = detailRow.createCell(9);
				cellUsernamedata.setCellStyle(contentCellStyle);
				cellUsernamedata.setCellValue(String.valueOf(transaction.getUsername()));

				if (transaction.getRemarks() == null) {

					HSSFCell cellRemarksdata = detailRow.createCell(10);
					cellRemarksdata.setCellStyle(contentCellStyle);
					cellRemarksdata.setCellValue("");
				} else {

					HSSFCell cellRemarksdata = detailRow.createCell(10);
					cellRemarksdata.setCellStyle(contentCellStyle);
					cellRemarksdata.setCellValue(String.valueOf(transaction.getRemarks()));
				}
				count = count + 1;
				rowCount = rowCount + 1;

			}
			sheet.createFreezePane(0, 6);
		} else {

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 10));

		}
		Date today = new Date(0);
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy");
		String stringToday = sdf.format(today);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat((reportTmpl.getReportname()).toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);
	}
}
