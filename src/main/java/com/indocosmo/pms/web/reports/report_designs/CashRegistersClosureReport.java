package com.indocosmo.pms.web.reports.report_designs;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import com.indocosmo.pms.enumerator.PaymentMode;
import com.indocosmo.pms.enumerator.TxnSource;
import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.CashRegistersClosureReportTemplate;
import com.indocosmo.pms.web.reports.model.TemplateReport;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CashRegistersClosureReport extends AbstractPdfViewReports {

	Font font;
	Font currencyFont;
	CashRegistersClosureReportTemplate reportTmpl;
	private float heightHeader = 45.0f;
	TemplateReport hdftReport;
	Font SUBFONT;
	private double base_amount_date = 0.00;
	private double tax_date = 0.00;
	private double discount_date = 0.00;

	public static final String FONT_CURRENCY = "../../../../../../../../resources/common/fonts/PlayfairDisplay-Regular.ttf";

	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(), 20, 20, heightHeader, 30);
	}

	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		reportTmpl = (CashRegistersClosureReportTemplate) model.get("reportTemplate");
		hdftReport = reportTmpl.getReportHeaderFooter();
		String xmlSt = reportTmpl.getXmlDocument();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document documentxml = builder.parse(new InputSource(new StringReader(xmlSt)));
		org.w3c.dom.NodeList txnDateNodes = documentxml.getDocumentElement().getChildNodes();
		SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 16, Font.BOLD);
		font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		currencyFont = FontFactory.getFont(this.getClass().getResource(FONT_CURRENCY).getPath(), BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED, 12, java.awt.Font.BOLD);
		if (txnDateNodes.getLength() != 0) {
			Map<String, String> summaryMap = new LinkedHashMap<String, String>();
			Map<String, Double> taxSummaryMap = new LinkedHashMap<String, Double>();
			double total = 0.00, taxTotal = 0.00;
			for (int i = 0; i < txnDateNodes.getLength(); i++) {

				PdfPTable table = new PdfPTable(9);
				table.setWidthPercentage(100.0f);
				table.setWidths(new float[] { 3.0f, 2.0f, 3.5f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f });
				table.setSpacingBefore(10);

				org.w3c.dom.Node txnDateNode = txnDateNodes.item(i);
				String txnDate = txnDateNode.getAttributes().getNamedItem("date").getNodeValue();
				String txnDateTotal = txnDateNode.getAttributes().getNamedItem("total").getNodeValue();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = sdf.parse(txnDate);

				reportHeaderName(table);

				PdfPCell cellspan = new PdfPCell();
				cellspan.setColspan(9);
				cellspan.setPadding(5);
				String dateFormat = reportTmpl.getDateFormat();
				DateFormat dateFormatHotelDate = new SimpleDateFormat(dateFormat);
				cellspan.setPhrase(new Phrase("Date: " + dateFormatHotelDate.format(date), font));
				cellspan.setBorder(Rectangle.NO_BORDER);
				table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
				summaryMap.put(String.valueOf(dateFormatHotelDate.format(date)), txnDateTotal);
				printHeaderColumnTitles(table);

				table.getDefaultCell().setPadding(5.0f);
				printTxnPaymentDetails(table, txnDateNode);

				PdfPCell cellDateTotal = new PdfPCell();
				netTotalAmount(cellDateTotal, table, base_amount_date, tax_date, discount_date, txnDateTotal);
				taxSummaryMap.put(String.valueOf(dateFormatHotelDate.format(date)), tax_date);
				base_amount_date = 0.00;
				tax_date = 0.00;
				discount_date = 0.00;

				document.add(table);
				document.newPage();// to get new date report on new page
				document.setMargins(20, 20, 0, 30);

			}
			PdfPTable summaryTable = new PdfPTable(9);
			summaryTable.setWidthPercentage(100.0f);
			summaryTable.setWidths(new float[] { 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f });
			summaryTable.setSpacingBefore(10);
			reportHeaderName(summaryTable);
			document.add(summaryTable);

			summaryTable = new PdfPTable(3);
			summaryTable.setWidthPercentage(40.0f);
			summaryTable.setWidths(new float[] { 3.0f, 3.0f, 3.0f });
			summaryTable.setSpacingBefore(10);
			summaryTable.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell();
			cell.setColspan(3);
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("SUMMARY", font));
			cell.setBorder(Rectangle.NO_BORDER);
			summaryTable.addCell(cell);

			cell.setBorder(Rectangle.BOX);
			cell.setColspan(1);
			cell.setPadding(2);
			cell.setPhrase(new Phrase("DATE", font));
			summaryTable.addCell(cell);
			cell.setPhrase(new Phrase("TAX", font));
			summaryTable.addCell(cell);
			cell.setPhrase(new Phrase("AMOUNT", font));
			summaryTable.addCell(cell);
			for (String key : summaryMap.keySet()) {
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPhrase(new Phrase(key));
				summaryTable.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase(String.format("%.2f", taxSummaryMap.get(key))));
				summaryTable.addCell(cell);
				cell.setPhrase(new Phrase(summaryMap.get(key)));
				summaryTable.addCell(cell);
				total += Double.parseDouble(summaryMap.get(key));
				taxTotal += taxSummaryMap.get(key);
			}
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("TOTAL", font));
			summaryTable.addCell(cell);

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(String.format("%.2f", taxTotal), font));
			summaryTable.addCell(cell);

			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(String.format("%.2f", total), font));
			summaryTable.addCell(cell);
			document.add(summaryTable);

		} else {
			PdfPTable table = new PdfPTable(6);
			noDataAvailable(table, document);
			document.add(table);

		}

	}

	/**
	 * @param table
	 * @param txnDateNode
	 */
	private void printTxnPaymentDetails(PdfPTable table, Node txnDateNode) {

		for (int txnPaymentIndex = 0; txnPaymentIndex < txnDateNode.getChildNodes().getLength(); txnPaymentIndex++) {

			org.w3c.dom.Node txnPaymentNode = txnDateNode.getChildNodes().item(txnPaymentIndex);
			int txnPaymentMode = Integer.parseInt(txnPaymentNode.getAttributes().getNamedItem("mode").getNodeValue());
			PaymentMode paymentMode = PaymentMode.get(txnPaymentMode);
			PdfPCell cellPaymentMode = new PdfPCell();
			cellPaymentMode.setColspan(9);
			cellPaymentMode.setPadding(5);
			cellPaymentMode.setPhrase((new Phrase(paymentMode.getPaymentMode(), font)));// from enumerator
			table.addCell(cellPaymentMode);

			printTxnDetails(table, txnPaymentNode);

		}
	}

	/**
	 * @param table
	 * @param txnPaymentNode
	 */
	private void printTxnDetails(PdfPTable table, Node txnPaymentNode) {
		String txnPaymentTotal = txnPaymentNode.getAttributes().getNamedItem("total").getNodeValue();
		double base_amount = 0.00;
		double tax = 0.00;
		double discount = 0.00;

		for (int txnRoomIndex = 0; txnRoomIndex < txnPaymentNode.getChildNodes().getLength(); txnRoomIndex++) {
			org.w3c.dom.Node txnRoomNode = txnPaymentNode.getChildNodes().item(txnRoomIndex);
			table.addCell(txnRoomNode.getAttributes().getNamedItem("type").getNodeValue());
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(txnRoomNode.getAttributes().getNamedItem("room").getNodeValue());
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(txnRoomNode.getAttributes().getNamedItem("guest").getNodeValue());
			TxnSource txnSource = TxnSource
					.get(Integer.parseInt(txnRoomNode.getAttributes().getNamedItem("source").getNodeValue()));
			table.addCell(txnSource.getSourceName());
			table.addCell(txnRoomNode.getAttributes().getNamedItem("user").getNodeValue());
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(txnRoomNode.getAttributes().getNamedItem("base_amount").getNodeValue());
			table.addCell(txnRoomNode.getAttributes().getNamedItem("tax").getNodeValue());
			table.addCell(txnRoomNode.getAttributes().getNamedItem("discount").getNodeValue());
			table.addCell(txnRoomNode.getAttributes().getNamedItem("amount").getNodeValue());
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			base_amount += Double.valueOf(txnRoomNode.getAttributes().getNamedItem("base_amount").getNodeValue());
			tax += Double.valueOf(txnRoomNode.getAttributes().getNamedItem("tax").getNodeValue());
			discount += Double.valueOf(txnRoomNode.getAttributes().getNamedItem("discount").getNodeValue());
		}
		PdfPCell cellPaymentTotal = new PdfPCell();
		totalAmount(cellPaymentTotal, table, base_amount, tax, discount, txnPaymentTotal);
		base_amount_date += base_amount;
		tax_date += tax;
		discount_date += discount;

	}

	/**
	 * @param cellPaymentTotal
	 * @param table
	 * @param discount
	 * @param tax
	 * @param base_amount
	 * @param txnPaymentTotal
	 */
	private void totalAmount(PdfPCell cellPaymentTotal, PdfPTable table, double base_amount, double tax,
			double discount, String txnPaymentTotal) {

		cellPaymentTotal.setColspan(5);
		cellPaymentTotal.setPadding(5);
		cellPaymentTotal.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.TOP);
		cellPaymentTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellPaymentTotal.setPhrase(new Phrase("Total:", font));
		table.addCell(cellPaymentTotal);

		PdfPCell cellPaymentTotalAmount = new PdfPCell();
		cellPaymentTotalAmount.setColspan(1);
		cellPaymentTotalAmount.setPadding(5);
		cellPaymentTotalAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellPaymentTotalAmount.setPhrase(new Phrase(
				reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", base_amount)), currencyFont));
		table.addCell(cellPaymentTotalAmount);
		cellPaymentTotalAmount.setPhrase(new Phrase(
				reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", tax)), currencyFont));
		table.addCell(cellPaymentTotalAmount);
		cellPaymentTotalAmount.setPhrase(new Phrase(
				reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", discount)), currencyFont));
		table.addCell(cellPaymentTotalAmount);
		cellPaymentTotalAmount.setPhrase(new Phrase(
				reportTmpl.getCurrencySymbol().concat(" ").concat(String.valueOf(txnPaymentTotal)), currencyFont));
		table.addCell(cellPaymentTotalAmount);

	}

	/**
	 * @param cellDateTotal
	 * @param table
	 * @param discount_date2
	 * @param tax_date2
	 * @param base_amount_date2
	 * @param txnDateTotal
	 */
	private void netTotalAmount(PdfPCell cellDateTotal, PdfPTable table, double base_amount, double tax,
			double discount, String txnDateTotal) {

		cellDateTotal.setColspan(5);
		cellDateTotal.setPadding(5);
		cellDateTotal.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.TOP);
		cellDateTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellDateTotal.setPhrase(new Phrase("Net Total:", font));
		table.addCell(cellDateTotal);

		PdfPCell cellDateTotalAmount = new PdfPCell();
		cellDateTotalAmount.setColspan(1);
		cellDateTotalAmount.setPadding(5);
		cellDateTotalAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellDateTotalAmount.setPhrase(new Phrase(
				reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", base_amount)), currencyFont));
		table.addCell(cellDateTotalAmount);
		cellDateTotalAmount.setPhrase(new Phrase(
				reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", tax)), currencyFont));
		table.addCell(cellDateTotalAmount);
		cellDateTotalAmount.setPhrase(new Phrase(
				reportTmpl.getCurrencySymbol().concat(" ").concat(String.format("%.2f", discount)), currencyFont));
		table.addCell(cellDateTotalAmount);
		cellDateTotalAmount.setPhrase(new Phrase(
				reportTmpl.getCurrencySymbol().concat(" ").concat(String.valueOf(txnDateTotal)), currencyFont));
		table.addCell(cellDateTotalAmount);

	}

	/**
	 * @param table
	 */
	private void printHeaderColumnTitles(PdfPTable table) {

		PdfPCell cell = new PdfPCell();
		cell.setPadding(5);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		cell.setPhrase(new Phrase("Particulars", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Room", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Guest", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Posted", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("User", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Base Amount", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Tax", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Discount", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Total", font));
		table.addCell(cell);

	}

	/**
	 * @param table
	 * @param document
	 */
	private void noDataAvailable(PdfPTable table, Document document) {

		table.setWidthPercentage(100.0f);
		try {
			table.setWidths(new float[] { 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f });
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		table.setSpacingBefore(10);

		reportHeaderName(table);

		PdfPCell noCellspan = new PdfPCell();
		noCellspan.setColspan(9);
		noCellspan.setPadding(5);
		noCellspan.setBorderWidthBottom(.5f);
		noCellspan.setBorder(Rectangle.NO_BORDER);
		noCellspan.setPhrase(new Phrase(" ", font));
		table.addCell(noCellspan).setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell noDataCellspan = new PdfPCell();
		noDataCellspan.setColspan(9);
		noDataCellspan.setPadding(5);
		noDataCellspan.setBorderWidthBottom(.5f);
		noDataCellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
		table.addCell(noDataCellspan).setHorizontalAlignment(Element.ALIGN_CENTER);

	}

	private void reportHeaderName(PdfPTable table) {

		PdfPCell reportHeadr = new PdfPCell();
		reportHeadr.setColspan(9);
		reportHeadr.setPadding(5);
		reportHeadr.setPhrase(new Phrase("CASH REGISTERS CLOSURE REPORT", SUBFONT));
		reportHeadr.setBorder(Rectangle.NO_BORDER);
		table.addCell(reportHeadr).setHorizontalAlignment(Element.ALIGN_CENTER);

	}

}
