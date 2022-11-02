package com.indocosmo.pms.web.reports.report_designs;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.xml.sax.InputSource;

import com.indocosmo.pms.enumerator.PaymentMode;
import com.indocosmo.pms.web.reports.model.ReportTemplate;

public class RevenueReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		double grandTotal = 0.00;
		double baseTaxTotal = 0.00;
		double tax1AmountTotal = 0.00;
		double tax2AmountTotal = 0.00;
		double tax3AmountTotal = 0.00;
		double totalTaxAmount = 0.00;
		double baseAmount = 0.00;
		double tax1Amount = 0.00;
		double tax2Amount = 0.00;
		double tax3Amount = 0.00;
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		String xmlReport = reportTmpl.getRevenueReport();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document documentxml = builder.parse(new InputSource(new StringReader(xmlReport)));
		org.w3c.dom.NodeList txnDateNodes = documentxml.getDocumentElement().getChildNodes();

		NumberFormat amountFormat = new DecimalFormat("#,##0.00");

		// Create a Font for styling header cells
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

		
		Font noDataFont = workbook.createFont();
		noDataFont.setFontName("Liberation Sans");

		noDataFont.setFontHeightInPoints((short) 12);
		noDataFont.setColor(IndexedColors.BLACK.getIndex());
		noDataFont.setBoldweight(noDataFont.BOLDWEIGHT_BOLD);

		CellStyle noDataCellStyle = workbook.createCellStyle();
		noDataCellStyle.setFont(noDataFont);
		noDataCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		
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

		subheadFont.setBoldweight(subheadFont.BOLDWEIGHT_BOLD);
		subheadFont.setFontHeightInPoints((short) 10);
		subheadFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle summaryCellStyle = workbook.createCellStyle();
		summaryCellStyle.setFont(subheadFont);
		summaryCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setFont(subheadFont);
		dateStyle.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle contentCellStyle2 = workbook.createCellStyle();
		contentCellStyle2.setFont(contentFont);
		contentCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_LEFT);

		Font headcontentFont = workbook.createFont();

		headcontentFont.setFontHeightInPoints((short) 9);
		headcontentFont.setColor(IndexedColors.BLACK.getIndex());
		headcontentFont.setBoldweight(headcontentFont.BOLDWEIGHT_BOLD);

		Font totalRowFont = workbook.createFont();

		totalRowFont.setFontHeightInPoints((short) 12);
		totalRowFont.setColor(IndexedColors.BLACK.getIndex());
		totalRowFont.setBoldweight(totalRowFont.BOLDWEIGHT_BOLD);

		CellStyle subheaderCellStyle = workbook.createCellStyle();
		subheaderCellStyle.setFont(headcontentFont);
		subheaderCellStyle.setWrapText(true);
		subheaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		subheaderCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

		CellStyle totalrowCellStyle = workbook.createCellStyle();
		totalrowCellStyle.setFont(totalRowFont);
		totalrowCellStyle.setWrapText(true);
		totalrowCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		totalrowCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		totalrowCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		totalrowCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		totalrowCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

		CellStyle numericCellStyle = workbook.createCellStyle();
		numericCellStyle.setFont(contentFont);
		numericCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		numericCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		int rowCount = 5;

		if (txnDateNodes.getLength() != 0) {

			HSSFSheet sheet = workbook.createSheet("DETAIL");
			sheet.getPrintSetup().setLandscape(true);
			sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

			sheet.setColumnWidth(0, 3500);
			sheet.setColumnWidth(1, 3500);
			sheet.setColumnWidth(2, 6500);
			sheet.setColumnWidth(3, 2000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 2800);
			sheet.setColumnWidth(6, 2800);
			sheet.setColumnWidth(7, 2800);
			sheet.setColumnWidth(8, 3000);

			Map<String, String> summaryMap = new LinkedHashMap<String, String>();
			for (int i = 0; i < txnDateNodes.getLength(); i++) {
				org.w3c.dom.Node txnDateNode = txnDateNodes.item(i);
				String txnDate = txnDateNode.getAttributes().getNamedItem("date").getNodeValue();
				String txnDateTotal = txnDateNode.getAttributes().getNamedItem("total").getNodeValue();
				String baseTotal = txnDateNode.getAttributes().getNamedItem("basetotal").getNodeValue();
				String tax1Total = txnDateNode.getAttributes().getNamedItem("tax1total").getNodeValue();
				String tax2Total = txnDateNode.getAttributes().getNamedItem("tax2total").getNodeValue();
				String tax3Total = txnDateNode.getAttributes().getNamedItem("tax3total").getNodeValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = sdf.parse(txnDate);

				String dateFormat = reportTmpl.getDateFormat();
				DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);
				
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
				cellname.setCellValue("DAILY REVENUE REPORT");
				cellname.setCellStyle(rptNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 8));

				HSSFRow tableheading = sheet.createRow(4);
				tableheading.setHeightInPoints(25);

				HSSFCell cellParticulars = tableheading.createCell(0);
				cellParticulars.setCellStyle(subheaderCellStyle);
				cellParticulars.setCellValue("Particulars");

				HSSFCell cellRoom = tableheading.createCell(1);
				cellRoom.setCellStyle(subheaderCellStyle);
				cellRoom.setCellValue("Room");

				HSSFCell cellGuest = tableheading.createCell(2);
				cellGuest.setCellStyle(subheaderCellStyle);
				cellGuest.setCellValue("Guest");

				HSSFCell cellPax = tableheading.createCell(3);
				cellPax.setCellStyle(subheaderCellStyle);
				cellPax.setCellValue("Pax");

				HSSFCell cellPosted = tableheading.createCell(4);
				cellPosted.setCellStyle(subheaderCellStyle);
				cellPosted.setCellValue("Base Amount");

				HSSFCell cellTax1 = tableheading.createCell(5);
				cellTax1.setCellStyle(subheaderCellStyle);
				cellTax1.setCellValue((String) request.getSession().getAttribute("tax1Name"));

				HSSFCell cellTax2 = tableheading.createCell(6);
				cellTax2.setCellStyle(subheaderCellStyle);
				cellTax2.setCellValue((String) request.getSession().getAttribute("tax2Name"));

				HSSFCell cellTax3 = tableheading.createCell(7);
				cellTax3.setCellStyle(subheaderCellStyle);
				cellTax3.setCellValue((String) request.getSession().getAttribute("tax3Name"));

				HSSFCell cellAmount = tableheading.createCell(8);
				cellAmount.setCellStyle(subheaderCellStyle);
				cellAmount.setCellValue("Amount");

				HSSFRow dateRow = sheet.createRow(rowCount);
				dateRow.setHeightInPoints(25);
				CellRangeAddress mergedCell1 = new CellRangeAddress(rowCount, rowCount, 0, 8);

				HSSFCell cellDateData = dateRow.createCell(0);
				cellDateData.setCellStyle(dateStyle);
				cellDateData.setCellValue("Date: ".concat(String.valueOf(simpleDateFormatHotelDate.format(date))));
				sheet.addMergedRegion(mergedCell1);

				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
				RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
				RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);
				RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell1, sheet, workbook);

				rowCount = rowCount + 1;

				baseTaxTotal += Double.valueOf(baseTotal);
				baseAmount = Math.round(baseTaxTotal * 100.00) / 100.00;

				tax1AmountTotal += Double.valueOf(tax1Total);
				tax1Amount = Math.round(tax1AmountTotal * 100.00) / 100.00;

				tax2AmountTotal += Double.valueOf(tax2Total);
				tax2Amount = Math.round(tax2AmountTotal * 100.00) / 100.00;

				tax3AmountTotal += Double.valueOf(tax3Total);
				tax3Amount = Math.round(tax3AmountTotal * 100.00) / 100.00;

				totalTaxAmount += Double.valueOf(txnDateTotal);
				grandTotal = Math.round(totalTaxAmount * 100.00) / 100.00;

				for (int txnPaymentIndex = 0; txnPaymentIndex < txnDateNode.getFirstChild().getChildNodes()
						.getLength(); txnPaymentIndex++) {

					org.w3c.dom.Node txnPaymentNode = txnDateNode.getFirstChild().getChildNodes().item(txnPaymentIndex);

					HSSFRow detailRow = sheet.createRow(rowCount);
					detailRow.setHeightInPoints(25);

					HSSFCell cellParticularsData = detailRow.createCell(0);
					cellParticularsData.setCellStyle(contentCellStyle2);
					cellParticularsData
							.setCellValue(txnPaymentNode.getAttributes().getNamedItem("type").getNodeValue());

					HSSFCell cellRoomdata = detailRow.createCell(1);
					cellRoomdata.setCellStyle(contentCellStyle2);
					cellRoomdata.setCellValue(txnPaymentNode.getAttributes().getNamedItem("room").getNodeValue());

					HSSFCell cellGuestData = detailRow.createCell(2);
					cellGuestData.setCellStyle(contentCellStyle2);
					cellGuestData.setCellValue(txnPaymentNode.getAttributes().getNamedItem("guest").getNodeValue());

					HSSFCell cellpax = detailRow.createCell(3);
					cellpax.setCellStyle(numericCellStyle);
					cellpax.setCellValue(txnPaymentNode.getAttributes().getNamedItem("source").getNodeValue());

					HSSFCell cellbase_amountData = detailRow.createCell(4);
					cellbase_amountData.setCellStyle(numericCellStyle);
					cellbase_amountData.setCellValue(amountFormat.format(
							Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("baseamount").getNodeValue())));

					HSSFCell celltax1Data = detailRow.createCell(5);
					celltax1Data.setCellStyle(numericCellStyle);
					celltax1Data.setCellValue(amountFormat.format(
							Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("tax1amount").getNodeValue())));

					HSSFCell celltax2Data = detailRow.createCell(6);
					celltax2Data.setCellStyle(numericCellStyle);
					celltax2Data.setCellValue(amountFormat.format(
							Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("tax2amount").getNodeValue())));

					HSSFCell celltax3Data = detailRow.createCell(7);
					celltax3Data.setCellStyle(numericCellStyle);
					celltax3Data.setCellValue(amountFormat.format(
							Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("tax3amount").getNodeValue())));

					HSSFCell cellAmountData = detailRow.createCell(8);
					cellAmountData.setCellStyle(numericCellStyle);
					cellAmountData.setCellValue(amountFormat.format(
							Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("amount").getNodeValue())));

					rowCount = rowCount + 1;

				}

				summaryMap.put(String.valueOf(simpleDateFormatHotelDate.format(date)), txnDateTotal);

				HSSFRow totalRow = sheet.createRow(rowCount);
				totalRow.setHeightInPoints(25);

				CellRangeAddress mergedCell = new CellRangeAddress(rowCount, rowCount, 0, 3);

				sheet.setColumnWidth(0, 3500);
				sheet.setColumnWidth(1, 3500);
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(3, 2000);
				sheet.setColumnWidth(4, 2800);
				sheet.setColumnWidth(5, 2800);
				sheet.setColumnWidth(6, 2800);
				sheet.setColumnWidth(7, 3000);

				HSSFCell cellTotalData = totalRow.createCell(0);
				cellTotalData.setCellStyle(totalrowCellStyle);
				cellTotalData.setCellValue("TOTAL:");
				sheet.addMergedRegion(mergedCell);

				HSSFCell cellTotalDat = totalRow.createCell(4);
				cellTotalDat.setCellStyle(totalrowCellStyle);

				cellTotalDat.setCellValue(amountFormat.format(Double.valueOf(baseTotal)));

				HSSFCell cellTotalDat1 = totalRow.createCell(5);
				cellTotalDat1.setCellStyle(totalrowCellStyle);
				cellTotalDat1.setCellValue(amountFormat.format(Double.valueOf(tax1Total)));

				HSSFCell cellTotalDat2 = totalRow.createCell(6);
				cellTotalDat2.setCellStyle(totalrowCellStyle);
				cellTotalDat2.setCellValue(amountFormat.format(Double.valueOf(tax2Total)));

				HSSFCell cellTotalDat3 = totalRow.createCell(7);
				cellTotalDat3.setCellStyle(totalrowCellStyle);
				cellTotalDat3.setCellValue(amountFormat.format(Double.valueOf(tax3Total)));

				HSSFCell cellTotalDat4 = totalRow.createCell(8);
				cellTotalDat4.setCellStyle(totalrowCellStyle);
				cellTotalDat4.setCellValue(amountFormat.format(Double.valueOf(txnDateTotal)));

				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);

				rowCount = rowCount + 1;

			}


			HSSFRow grandTotalRow = sheet.createRow(rowCount);
			grandTotalRow.setHeightInPoints(25);

			CellRangeAddress mergedGrandCell = new CellRangeAddress(rowCount, rowCount, 0, 3);

			sheet.setColumnWidth(0, 3500);
			sheet.setColumnWidth(4, 3500);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 4000);
			sheet.setColumnWidth(7, 4000);

			HSSFCell cellgrandData = grandTotalRow.createCell(0);
			cellgrandData.setCellStyle(totalrowCellStyle);
			cellgrandData.setCellValue("GRAND TOTAL:");
			sheet.addMergedRegion(mergedGrandCell);

			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedGrandCell, sheet, workbook);

			HSSFCell cellBaseData = grandTotalRow.createCell(4);
			cellBaseData.setCellStyle(totalrowCellStyle);
			cellBaseData.setCellValue(amountFormat.format(Double.valueOf(baseAmount)));

			HSSFCell cellTax1Data = grandTotalRow.createCell(5);
			cellTax1Data.setCellStyle(totalrowCellStyle);
			cellTax1Data.setCellValue(amountFormat.format(Double.valueOf(tax1Amount)));

			HSSFCell cellTax2Data = grandTotalRow.createCell(6);
			cellTax2Data.setCellStyle(totalrowCellStyle);
			cellTax2Data.setCellValue(amountFormat.format(Double.valueOf(tax2Amount)));

			HSSFCell celltax3Data = grandTotalRow.createCell(7);
			celltax3Data.setCellStyle(totalrowCellStyle);
			celltax3Data.setCellValue(amountFormat.format(Double.valueOf(tax3Amount)));

			HSSFCell cellGrandData = grandTotalRow.createCell(8);
			cellGrandData.setCellStyle(totalrowCellStyle);
			cellGrandData.setCellValue(amountFormat.format(Double.valueOf(grandTotal)));
			
			sheet.createFreezePane(0,5);

			HSSFSheet sheetn = workbook.createSheet("SUMMARY");
			sheetn.getPrintSetup().setLandscape(true);
			sheetn.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			
			sheetn.setColumnWidth(0, 9000);
			sheetn.setColumnWidth(1, 9000);
			
			HSSFRow headnameRow = sheet.createRow(0);
			headnameRow.setHeightInPoints(15);
			HSSFCell companyCellname = headnameRow.createCell(0);
			companyCellname.setCellValue(reportTmpl.getCompanyname());
			companyCellname.setCellStyle(headNameCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

			HSSFRow buildingnameRow = sheet.createRow(1);
			buildingnameRow.setHeightInPoints(15);
			HSSFCell cellbuildingname = buildingnameRow.createCell(0);
			cellbuildingname.setCellValue(reportTmpl.getBuilding());
			cellbuildingname.setCellStyle(detailFontCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));

			HSSFRow DetailnameRow = sheet.createRow(2);
			DetailnameRow.setHeightInPoints(15);
			HSSFCell DetailnameCell = DetailnameRow.createCell(0);
			DetailnameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
			DetailnameCell.setCellStyle(detailFontCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));

			HSSFRow nameReportRow = sheetn.createRow(3);
			nameReportRow.setHeightInPoints(35);
			HSSFCell cellname2 = nameReportRow.createCell(0);
			cellname2.setCellValue("DAILY REVENUE REPORT");
			cellname2.setCellStyle(rptNameCellStyle);
			sheetn.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));

			HSSFRow summaryRow = sheetn.createRow(4);
			summaryRow.setHeightInPoints(25);
			HSSFCell cellhead2 = summaryRow.createCell(0);
			cellhead2.setCellValue("SUMMARY");
			cellhead2.setCellStyle(summaryCellStyle);
			sheetn.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));

			HSSFRow tableheader = sheetn.createRow(5);
			tableheader.setHeightInPoints(25);

			HSSFCell cellDate = tableheader.createCell(0);
			cellDate.setCellStyle(subheaderCellStyle);
			cellDate.setCellValue("MODE OF PAYMENT");

			HSSFCell cellAmount = tableheader.createCell(1);
			cellAmount.setCellStyle(subheaderCellStyle);
			cellAmount.setCellValue("AMOUNT");

			rowCount = 3;
			for (int i = 0; i < txnDateNodes.getLength(); i++) {
				org.w3c.dom.Node txnDateNode = txnDateNodes.item(i);
				String txnDate = txnDateNode.getAttributes().getNamedItem("date").getNodeValue();
				
				Double txnDateTotal = 0.00;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = sdf.parse(txnDate);

				String dateFormat = reportTmpl.getDateFormat();
				DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);

				HSSFRow dateRow = sheetn.createRow(rowCount);

				CellRangeAddress mergedCellDate = new CellRangeAddress(rowCount, rowCount, 0, 1);

				HSSFCell cellDateData = dateRow.createCell(0);
				cellDateData.setCellStyle(dateStyle);
				cellDateData.setCellValue("Date: ".concat(String.valueOf(simpleDateFormatHotelDate1.format(date))));
				sheetn.addMergedRegion(mergedCellDate);

				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCellDate, sheetn, workbook);
				RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCellDate, sheetn, workbook);
				RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCellDate, sheetn, workbook);
				RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCellDate, sheetn, workbook);

				sheetn.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 1));
				rowCount++;

				for (int txnPaymentIndex = 0; txnPaymentIndex < txnDateNode.getLastChild().getChildNodes()
						.getLength(); txnPaymentIndex++) {

					org.w3c.dom.Node txnPaymentNode = txnDateNode.getLastChild().getChildNodes().item(txnPaymentIndex);

					HSSFRow detailRow = sheetn.createRow(rowCount);
					detailRow.setHeightInPoints(25);

					HSSFCell cellParticularsData = detailRow.createCell(0);
					cellParticularsData.setCellStyle(contentCellStyle2);
					cellParticularsData.setCellValue(PaymentMode
							.get(Integer.valueOf(
									txnPaymentNode.getAttributes().getNamedItem("payment_mode").getNodeValue()))
							.getPaymentMode());

					HSSFCell cellParticularsData1 = detailRow.createCell(1);
					cellParticularsData1.setCellStyle(numericCellStyle);
					cellParticularsData1
							.setCellValue(txnPaymentNode.getAttributes().getNamedItem("nettamount").getNodeValue());
					txnDateTotal += Double
							.valueOf(txnPaymentNode.getAttributes().getNamedItem("nettamount").getNodeValue());
					rowCount++;

				}

				HSSFRow rowTotal = sheetn.createRow(rowCount);
				rowTotal.setHeightInPoints(25);

				HSSFCell cellTotal = rowTotal.createCell(0);
				cellTotal.setCellStyle(totalrowCellStyle);
				cellTotal.setCellValue("TOTAL");

				HSSFCell cellTotalData = rowTotal.createCell(1);
				cellTotalData.setCellStyle(totalrowCellStyle);
				cellTotalData.setCellValue(amountFormat.format(txnDateTotal));
				rowCount++;
				
				sheet.createFreezePane(0,5);
				
			}

		} else {

			HSSFSheet sheet = workbook.createSheet("sheet");
			sheet.getPrintSetup().setLandscape(true);
			sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 6));
		}
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday = sdf.format(today);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat(("DAILY REVENUE REPORT").toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
