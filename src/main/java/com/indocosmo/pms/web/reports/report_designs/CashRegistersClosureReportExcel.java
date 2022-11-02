package com.indocosmo.pms.web.reports.report_designs;

import java.io.StringReader;
import java.text.DateFormat;
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
import com.indocosmo.pms.enumerator.TxnSource;
import com.indocosmo.pms.web.reports.model.CashRegistersClosureReportTemplate;

public class CashRegistersClosureReportExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CashRegistersClosureReportTemplate reportTmpl;
		double base_amount_date = 0.00;
		double tax_date = 0.00;
		double discount_date = 0.00;
		reportTmpl = (CashRegistersClosureReportTemplate) model.get("reportTemplate");
		String xmlSt = reportTmpl.getXmlDocument();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		xmlSt = xmlSt.replaceAll("&(?!amp;)", " AND ");
		org.w3c.dom.Document documentxml = builder.parse(new InputSource(new StringReader(xmlSt)));

		org.w3c.dom.NodeList txnDateNodes = documentxml.getDocumentElement().getChildNodes();

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

		CellStyle amtCellStyle = workbook.createCellStyle();
		amtCellStyle.setFont(contentFont);
		amtCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		amtCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		amtCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		amtCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		amtCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		CellStyle numericCellStyle = workbook.createCellStyle();
		numericCellStyle.setFont(contentFont);
		numericCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		numericCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		Font summaryHeadFont = workbook.createFont();
		summaryHeadFont.setFontName("Liberation Sans");
		summaryHeadFont.setFontHeightInPoints((short) 12);
		summaryHeadFont.setColor(IndexedColors.BLACK.getIndex());
		summaryHeadFont.setBoldweight(summaryHeadFont.BOLDWEIGHT_BOLD);

		CellStyle summaryHeadCellStyle = workbook.createCellStyle();
		summaryHeadCellStyle.setFont(summaryHeadFont);
		summaryHeadCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		summaryHeadCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		summaryHeadCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		summaryHeadCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		summaryHeadCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		Font summaryTotalFont = workbook.createFont();
		summaryTotalFont.setFontName("Liberation Sans");
		summaryTotalFont.setFontHeightInPoints((short) 12);
		summaryTotalFont.setColor(IndexedColors.BLACK.getIndex());
		summaryTotalFont.setBoldweight(summaryTotalFont.BOLDWEIGHT_BOLD);

		CellStyle summaryTotalCellStyle = workbook.createCellStyle();
		summaryTotalCellStyle.setFont(summaryTotalFont);
		summaryTotalCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		summaryTotalCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		summaryTotalCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		summaryTotalCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		summaryTotalCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		Font paymentModeFont = workbook.createFont();
		paymentModeFont.setFontName("Liberation Sans");
		paymentModeFont.setFontHeightInPoints((short) 12);
		paymentModeFont.setColor(IndexedColors.BLACK.getIndex());
		paymentModeFont.setBoldweight(summaryTotalFont.BOLDWEIGHT_BOLD);

		CellStyle paymentModeCellStyle = workbook.createCellStyle();
		paymentModeCellStyle.setFont(paymentModeFont);
		paymentModeCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		paymentModeCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		paymentModeCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		paymentModeCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		paymentModeCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		HSSFSheet sheet = workbook.createSheet("DETAILS");
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 2800);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 2500);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 3800);
		sheet.setColumnWidth(6, 3800);
		sheet.setColumnWidth(7, 3800);
		sheet.setColumnWidth(8, 3800);

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
		subheadercell.setCellStyle(subheadCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));

		if (txnDateNodes.getLength() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellParticulars = tableheading.createCell(0);
			cellParticulars.setCellStyle(headCellStyle);
			cellParticulars.setCellValue("Particulars");

			HSSFCell cellRoom = tableheading.createCell(1);
			cellRoom.setCellStyle(headCellStyle);
			cellRoom.setCellValue("Room");

			HSSFCell cellGuest = tableheading.createCell(2);
			cellGuest.setCellStyle(headCellStyle);
			cellGuest.setCellValue("Guest");

			HSSFCell cellPosted = tableheading.createCell(3);
			cellPosted.setCellStyle(headCellStyle);
			cellPosted.setCellValue("Posted");

			HSSFCell cellUser = tableheading.createCell(4);
			cellUser.setCellStyle(headCellStyle);
			cellUser.setCellValue("User");

			HSSFCell cellBaseAmount = tableheading.createCell(5);
			cellBaseAmount.setCellStyle(headCellStyle);
			cellBaseAmount.setCellValue("Base Amount");

			HSSFCell cellTax = tableheading.createCell(6);
			cellTax.setCellStyle(headCellStyle);
			cellTax.setCellValue("Tax");

			HSSFCell cellDiscount = tableheading.createCell(7);
			cellDiscount.setCellStyle(headCellStyle);
			cellDiscount.setCellValue("Discount");

			HSSFCell cellTotal = tableheading.createCell(8);
			cellTotal.setCellStyle(headCellStyle);
			cellTotal.setCellValue("Total");

			Map<String, String> summaryMap = new LinkedHashMap<String, String>();
			Map<String, Double> taxSummaryMap = new LinkedHashMap<String, Double>();
			double total = 0.00, taxTotal = 0.00;

			int rowCount = 6;
			for (int i = 0; i < txnDateNodes.getLength(); i++) {

				org.w3c.dom.Node txnDateNode = txnDateNodes.item(i);
				String txnDate = txnDateNode.getAttributes().getNamedItem("date").getNodeValue();
				String txnDateTotal = txnDateNode.getAttributes().getNamedItem("total").getNodeValue();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = sdf.parse(txnDate);

				String dateFormat = reportTmpl.getDateFormat();
				DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);

				subheadercell.setCellValue("Date: " + simpleDateFormatHotelDate1.format(date));

				summaryMap.put(String.valueOf(simpleDateFormatHotelDate1.format(date)), txnDateTotal);

				for (int txnPaymentIndex = 0; txnPaymentIndex < txnDateNode.getChildNodes()
						.getLength(); txnPaymentIndex++) {

					HSSFRow detailRow = sheet.createRow(rowCount);
					detailRow.setHeightInPoints(25);

					org.w3c.dom.Node txnPaymentNode = txnDateNode.getChildNodes().item(txnPaymentIndex);
					int txnPaymentMode = Integer
							.parseInt(txnPaymentNode.getAttributes().getNamedItem("mode").getNodeValue());
					PaymentMode paymentMode = PaymentMode.get(txnPaymentMode);

					CellRangeAddress mergedCell = new CellRangeAddress(rowCount, rowCount, 0, 8);

					HSSFCell cellpaymentModedata = detailRow.createCell(0);
					cellpaymentModedata.setCellStyle(paymentModeCellStyle);
					cellpaymentModedata.setCellValue(paymentMode.getPaymentMode());
					sheet.addMergedRegion(mergedCell);

					RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);

					String txnPaymentTotal = txnPaymentNode.getAttributes().getNamedItem("total").getNodeValue();
					double base_amount = 0.00;
					double tax = 0.00;
					double discount = 0.00;

					rowCount = rowCount + 1;

					for (int txnRoomIndex = 0; txnRoomIndex < txnPaymentNode.getChildNodes()
							.getLength(); txnRoomIndex++) {
						org.w3c.dom.Node txnRoomNode = txnPaymentNode.getChildNodes().item(txnRoomIndex);

						HSSFRow detailRow1 = sheet.createRow(rowCount);
						detailRow1.setHeightInPoints(25);

						HSSFCell celltypedata = detailRow1.createCell(0);
						celltypedata.setCellStyle(contentCellStyle);
						celltypedata.setCellValue(txnRoomNode.getAttributes().getNamedItem("type").getNodeValue());

						HSSFCell cellroomdata = detailRow1.createCell(1);
						cellroomdata.setCellStyle(contentCellStyle);
						cellroomdata.setCellValue(txnRoomNode.getAttributes().getNamedItem("room").getNodeValue());

						HSSFCell cellguestdata = detailRow1.createCell(2);
						cellguestdata.setCellStyle(contentCellStyle);

						String guest = txnRoomNode.getAttributes().getNamedItem("guest").getNodeValue();
						guest = guest.replaceAll("&(?!amp;)", " AND ");
						cellguestdata.setCellValue((guest));

						TxnSource txnSource = TxnSource.get(
								Integer.parseInt(txnRoomNode.getAttributes().getNamedItem("source").getNodeValue()));

						HSSFCell cellSourceNamedata = detailRow1.createCell(3);
						cellSourceNamedata.setCellStyle(contentCellStyle);
						cellSourceNamedata.setCellValue(txnSource.getSourceName());

						HSSFCell celluserdata = detailRow1.createCell(4);
						celluserdata.setCellStyle(contentCellStyle);
						celluserdata.setCellValue(txnRoomNode.getAttributes().getNamedItem("user").getNodeValue());

						HSSFCell cellbase_amountdata = detailRow1.createCell(5);
						cellbase_amountdata.setCellStyle(amtCellStyle);
						cellbase_amountdata
								.setCellValue(txnRoomNode.getAttributes().getNamedItem("base_amount").getNodeValue());

						HSSFCell celltaxdata = detailRow1.createCell(6);
						celltaxdata.setCellStyle(amtCellStyle);
						celltaxdata.setCellValue(txnRoomNode.getAttributes().getNamedItem("tax").getNodeValue());

						HSSFCell celldiscountdata = detailRow1.createCell(7);
						celldiscountdata.setCellStyle(amtCellStyle);
						celldiscountdata
								.setCellValue(txnRoomNode.getAttributes().getNamedItem("discount").getNodeValue());

						HSSFCell cellamountdata = detailRow1.createCell(8);
						cellamountdata.setCellStyle(amtCellStyle);
						cellamountdata.setCellValue(txnRoomNode.getAttributes().getNamedItem("amount").getNodeValue());

						base_amount += Double
								.valueOf(txnRoomNode.getAttributes().getNamedItem("base_amount").getNodeValue());
						tax += Double.valueOf(txnRoomNode.getAttributes().getNamedItem("tax").getNodeValue());
						discount += Double.valueOf(txnRoomNode.getAttributes().getNamedItem("discount").getNodeValue());

						rowCount = rowCount + 1;
					}

					HSSFRow detailRowTotal = sheet.createRow(rowCount);
					detailRowTotal.setHeightInPoints(25);

					mergedCell = new CellRangeAddress(rowCount, rowCount, 0, 4);

					HSSFCell cellTotaldata = detailRowTotal.createCell(0);
					cellTotaldata.setCellStyle(summaryTotalCellStyle);
					cellTotaldata.setCellValue("Total:");
					sheet.addMergedRegion(mergedCell);

					RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
					RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);

					HSSFCell cellbase_amountTotal = detailRowTotal.createCell(5);
					cellbase_amountTotal.setCellStyle(summaryTotalCellStyle);
					cellbase_amountTotal.setCellValue(
							reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", base_amount)));

					HSSFCell celltaxTotal = detailRowTotal.createCell(6);
					celltaxTotal.setCellStyle(summaryTotalCellStyle);
					celltaxTotal.setCellValue(
							reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", tax)));

					HSSFCell celldiscountTotal = detailRowTotal.createCell(7);
					celldiscountTotal.setCellStyle(summaryTotalCellStyle);
					celldiscountTotal.setCellValue(
							reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", discount)));

					HSSFCell celltxnPaymentTotal = detailRowTotal.createCell(8);
					celltxnPaymentTotal.setCellStyle(summaryTotalCellStyle);
					celltxnPaymentTotal.setCellValue(
							reportTmpl.getCurrencySymbol().concat(" ").concat(String.valueOf(txnPaymentTotal)));

					base_amount_date += base_amount;
					tax_date += tax;
					discount_date += discount;

					rowCount = rowCount + 1;
				}

				HSSFRow detailRow = sheet.createRow(rowCount);
				detailRow.setHeightInPoints(25);

				CellRangeAddress mergedCell = new CellRangeAddress(rowCount, rowCount, 0, 4);

				HSSFCell cellNetTotaldata = detailRow.createCell(0);
				cellNetTotaldata.setCellStyle(summaryTotalCellStyle);
				cellNetTotaldata.setCellValue("Net Total:");
				sheet.addMergedRegion(mergedCell);

				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderTop(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);
				RegionUtil.setBorderRight(CellStyle.BORDER_THIN, mergedCell, sheet, workbook);

				HSSFCell cellNetbase_amountdata = detailRow.createCell(5);
				cellNetbase_amountdata.setCellStyle(summaryTotalCellStyle);
				cellNetbase_amountdata.setCellValue(
						reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", base_amount_date)));

				HSSFCell cellNettaxdata = detailRow.createCell(6);
				cellNettaxdata.setCellStyle(summaryTotalCellStyle);
				cellNettaxdata.setCellValue(
						reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", tax_date)));

				HSSFCell cellNetdiscountdata = detailRow.createCell(7);
				cellNetdiscountdata.setCellStyle(summaryTotalCellStyle);
				cellNetdiscountdata.setCellValue(
						reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", discount_date)));

				HSSFCell cellNettxnDateTotal = detailRow.createCell(8);
				cellNettxnDateTotal.setCellStyle(summaryTotalCellStyle);
				cellNettxnDateTotal
						.setCellValue(reportTmpl.getCurrencySymbol().concat(" ").concat(String.valueOf(txnDateTotal)));

				taxSummaryMap.put(String.valueOf(simpleDateFormatHotelDate1.format(date)), tax_date);
				base_amount_date = 0.00;
				tax_date = 0.00;
				discount_date = 0.00;
			}

			HSSFSheet sheetSummary = workbook.createSheet("SUMMARY");
			sheetSummary.getPrintSetup().setLandscape(true);
			sheetSummary.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			sheetSummary.setColumnWidth(0, 9000);
			sheetSummary.setColumnWidth(1, 9000);
			sheetSummary.setColumnWidth(2, 9000);

			HSSFRow companyNameRow = sheet.createRow(0);
			companyNameRow.setHeightInPoints(15);
			HSSFCell companyCellDataname = companyNameRow.createCell(0);
			companyCellDataname.setCellValue(reportTmpl.getCompanyname());
			companyCellDataname.setCellStyle(headNameCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

			HSSFRow buildingNameRow = sheet.createRow(1);
			buildingNameRow.setHeightInPoints(15);
			HSSFCell cellBuildingName = buildingNameRow.createCell(0);
			cellBuildingName.setCellValue(reportTmpl.getBuilding());
			cellBuildingName.setCellStyle(detailFontCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));

			HSSFRow addressNameRow = sheet.createRow(2);
			addressNameRow.setHeightInPoints(15);
			HSSFCell addressNameCell = addressNameRow.createCell(0);
			addressNameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
			addressNameCell.setCellStyle(detailFontCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));

			HSSFRow reportNameRow = sheetSummary.createRow(0);
			reportNameRow.setHeightInPoints(35);
			HSSFCell reportCellname = reportNameRow.createCell(0);
			reportCellname.setCellValue(reportTmpl.getReportname());
			reportCellname.setCellStyle(rptNameCellStyle);
			sheetSummary.addMergedRegion(new CellRangeAddress(3, 3, 0, 2));

			HSSFRow subHeadRow = sheetSummary.createRow(1);
			subHeadRow.setHeightInPoints(25);
			HSSFCell subHeadCell = subHeadRow.createCell(0);
			subHeadCell.setCellValue("SUMMARY");
			subHeadCell.setCellStyle(subheadCellStyle);
			sheetSummary.addMergedRegion(new CellRangeAddress(4, 4, 0, 2));

			HSSFRow tableHeaderRow = sheetSummary.createRow(5);
			tableHeaderRow.setHeightInPoints(25);

			HSSFCell cellDate = tableHeaderRow.createCell(0);
			cellDate.setCellStyle(summaryHeadCellStyle);
			cellDate.setCellValue("DATE");

			HSSFCell cellsummeryTax = tableHeaderRow.createCell(1);
			cellsummeryTax.setCellStyle(summaryHeadCellStyle);
			cellsummeryTax.setCellValue("TAX");

			HSSFCell cellAmount = tableHeaderRow.createCell(2);
			cellAmount.setCellStyle(summaryHeadCellStyle);
			cellAmount.setCellValue("AMOUNT");

			int count = 6;
			for (String key : summaryMap.keySet()) {

				HSSFRow dataRow = sheetSummary.createRow(count);
				dataRow.setHeightInPoints(25);

				HSSFCell celldate = dataRow.createCell(0);
				celldate.setCellStyle(contentCellStyle);
				celldate.setCellValue(key);

				HSSFCell celltax = dataRow.createCell(1);
				celltax.setCellStyle(numericCellStyle);
				celltax.setCellValue(String.format("%.2f", taxSummaryMap.get(key)));

				HSSFCell cellamount = dataRow.createCell(2);
				cellamount.setCellStyle(numericCellStyle);
				cellamount.setCellValue(summaryMap.get(key));

				total += Double.parseDouble(summaryMap.get(key));
				taxTotal += taxSummaryMap.get(key);

				count = count + 1;
			}

			HSSFRow rowTotal = sheetSummary.createRow(count);
			rowTotal.setHeightInPoints(25);

			HSSFCell cellsummarytotal = rowTotal.createCell(0);
			cellsummarytotal.setCellStyle(summaryHeadCellStyle);
			cellsummarytotal.setCellValue("TOTAL");

			HSSFCell cellTaxData = rowTotal.createCell(1);
			cellTaxData.setCellStyle(headCellStyle);
			cellTaxData.setCellStyle(summaryTotalCellStyle);
			cellTaxData.setCellValue(String.format("%.2f", taxTotal));

			HSSFCell cellTotalData = rowTotal.createCell(2);
			cellTotalData.setCellStyle(summaryTotalCellStyle);
			cellTotalData.setCellValue(String.format("%.2f", total));

			sheet.createFreezePane(0, 6);

		} else {

			HSSFSheet sheetSummary = workbook.createSheet("Sheet1");

			HSSFRow tableData = sheetSummary.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheetSummary.addMergedRegion(new CellRangeAddress(5, 5, 0, 10));

		}

		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday = sdf.format(today);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat(reportTmpl.getReportname().toLowerCase().replaceAll("\\s", "") + stringToday + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
