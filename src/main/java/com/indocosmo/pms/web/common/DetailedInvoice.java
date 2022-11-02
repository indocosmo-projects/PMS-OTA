package com.indocosmo.pms.web.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.enumerator.PaymentMode;
import com.indocosmo.pms.util.MoneyToWordsConvertor;
import com.indocosmo.pms.web.checkOut.model.Invoice;
import com.indocosmo.pms.web.checkOut.model.TaxSummary;
import com.indocosmo.pms.web.checkOut.service.CheckOutServiceImp;
import com.indocosmo.pms.web.common.setttings.Rounding;
import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.serviceTax.model.serviceTax;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DetailedInvoice extends AbstractITextPdfView {

	/*@Autowired
	private CheckOutServiceImp checkoutService;*/

	private final static int INVOICE_LENGTH=7;

	@SuppressWarnings("unlikely-arg-type")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		InputStream inputPath = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties propPdf = new Properties();
		inputPath = loader.getResourceAsStream("/export.properties");
		propPdf.load(inputPath);
		Transaction txn = (Transaction) model.get("deposit");
		NumberFormat formatter = new DecimalFormat("#,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 9);
		font.setColor(BaseColor.BLACK);
		Font fontchk = FontFactory.getFont(FontFactory.HELVETICA, 9);
		fontchk.setColor(BaseColor.BLACK);
		Font fonttx = FontFactory.getFont(FontFactory.HELVETICA, 9);
		Font fonttxhdr = FontFactory.getFont(FontFactory.HELVETICA, 11);
		Font fontAmt = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
		Font fontFoodExpnse = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
		Font fontFoodExpnseHead = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);

		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 9);
		Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);

		int lineCount = 0;
		int pageMinRows = 25;
		int pageMaxRows = 38;
		double rate=0.0;
		double rateCalc=0.0;
		String invNo=null;
		int invoiceLength=0;
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		ByteArrayOutputStream baos=null;
		
		baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(doc, baos);
		doc.open();
		//ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// PdfWriter.getInstance(doc, baos);
		// doc.open();
		/*response.setContentType("application/pdf");
		response.setHeader("Content-disposition","attachment;filename="+ "Invoice.pdf");
		File file = new File("D://HMS-V1//Invoice.pdf");
		FileOutputStream pdfFileout = new FileOutputStream(file);
	    PdfWriter.getInstance(doc, pdfFileout);
	    doc.open();*/
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();*/
		if (txn != null) {
			SystemSettings settingsSystem = txn.getSystemSettings();
			String dateform = settingsSystem.getDateFormat();
			DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateform);
			SimpleDateFormat tf = new SimpleDateFormat(settingsSystem.getTimeFormat());

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] { 2.0f, 2.0f });
			table.setSpacingBefore(10);
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setPhrase(new Phrase("Receipt No: " + txn.getReceipt_no(), font));
			table.addCell(cell);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			java.util.Date rDate = sdf.parse(txn.getTxn_date());
			java.sql.Date recpDate = new Date(rDate.getTime());
			cell.setPhrase(new Phrase("Receipt Date: " + simpleDateFormatHotelDate.format(recpDate), font));
			table.addCell(cell);
			cell.setColspan(2);
			cell.setPhrase(new Phrase("Receipt Time:     "
					+ tf.format(new SimpleDateFormat("yy-MM-dd hh:mm:ss").parse(txn.getTxn_time())), font));
			table.addCell(cell);
			cell.setPhrase(new Phrase(" ", font));
			table.addCell(cell);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setColspan(1);
			cell.setPhrase(new Phrase("Name of Guest     : " + txn.getFirstName() + " " + txn.getLastName(), font));
			table.addCell(cell);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			if (txn.getRoomNumber() != null && txn.getRoomNumber() != "") {
				cell.setPhrase(new Phrase("Room No :      " + txn.getRoomNumber(), font));
			} else {
				cell.setPhrase(new Phrase("Room No :      Not Assigned", font));
			}
			table.addCell(cell);

			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setPhrase(new Phrase("Description           : Room Advance", font));
			table.addCell(cell);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setPhrase(new Phrase("Room Type : " + txn.getRoom_type(), font));
			table.addCell(cell);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setPhrase(new Phrase("Amount Received : Rs." + formatter.format(txn.getNett_amount()), font));
			table.addCell(cell);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			if (txn.getPayment_mode() != 0) {
				cell.setPhrase(new Phrase(
						"Mode of Payment : " + PaymentMode.get(txn.getPayment_mode()).getPaymentMode(), font));
			}
			table.addCell(cell);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setColspan(2);
			cell.setPhrase(new Phrase(" ", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase(
					"In Words               : " + MoneyToWordsConvertor.toWords(String.valueOf((txn.getNett_amount()))),
					font));
			table.addCell(cell);
			doc.add(table);

			PdfPTable bottomtable = new PdfPTable(2);
			bottomtable.setWidthPercentage(100.0f);
			bottomtable.setWidths(new float[] { 2.0f, 2.0f });
			bottomtable.setTotalWidth(doc.right(doc.rightMargin()) - doc.left(doc.leftMargin()));
			cell.setColspan(1);
			cell.setPhrase(new Phrase(
					"Prepared by: " + ((User) request.getSession().getAttribute("userForm")).getName(), font));
			bottomtable.addCell(cell);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setPhrase(new Phrase("Signature of Guest: ......................"));
			bottomtable.addCell(cell);
			bottomtable.writeSelectedRows(0, -1, doc.left(doc.leftMargin()),
					bottomtable.getTotalHeight() + doc.bottom(doc.bottomMargin()), writer.getDirectContent());

		} else {
			
			Invoice listInvoice = (Invoice) model.get("listInvoice");

			InvoiceTemplate invTmp = listInvoice.getHeaderFooter();
			SystemSettings settingsSystem = listInvoice.getSystemseting();

			String dateform = settingsSystem.getDateFormat();
			DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateform);
			SimpleDateFormat tf = new SimpleDateFormat(settingsSystem.getTimeFormat());
			List<Transaction> txnListInvoice = (List<Transaction>) listInvoice.getTxnListInvoice();
			@SuppressWarnings("unchecked")
			List<Transaction> paymentList = (List<Transaction>) listInvoice.getPaymentList();

			serviceTax sTax = listInvoice.getSeriveTax();

			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(100.0f);
			tableHead.setWidths(new float[] { 2.0f, 1.3f });
			PdfPCell cellDetails = new PdfPCell();
			cellDetails.setBorder(Rectangle.NO_BORDER);
			cellDetails.setVerticalAlignment(Element.ALIGN_TOP);

			cellDetails.addElement(new Paragraph(listInvoice.getName()));
			cellDetails.addElement(new Paragraph(listInvoice.getAddress()));

			if (listInvoice.getGstno() != null && !listInvoice.getGstno().equals("")) {
				cellDetails.addElement(new Paragraph("GSTIN:" + listInvoice.getGstno()));
			}

			PdfPCell cellTemp = new PdfPCell();
			cellTemp.setBorder(Rectangle.NO_BORDER);

			Font fontHead = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
			PdfPTable tablechk = new PdfPTable(2);
			tablechk.setWidthPercentage(100.0f);
			tablechk.setWidths(new float[] { 1.3f, 2.0f });

			PdfPCell cellchk = new PdfPCell();
			cellchk.setBorder(Rectangle.NO_BORDER);
			cellchk.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (listInvoice.getPrintMode() != 1) {
				cellchk.setPhrase(new Phrase(" "));

			}

			cellchk.setPhrase(new Phrase("Invoice No : ", fontHead));
			tablechk.addCell(cellchk);
			lineCount++;
			if (listInvoice.getInvoiceNo() != "" && listInvoice.getInvoiceNo() != null) {
				cellchk.setPhrase(new Phrase(listInvoice.getInvoiceNo(), fontHead));

			} else {
				cellchk.setPhrase(new Phrase(""));
			}
			tablechk.addCell(cellchk);

			cellchk.setPhrase(new Phrase("Invoice Dt. : ", fontHead));
			tablechk.addCell(cellchk);
			lineCount++;
			if (listInvoice.getCheckOutDate() != null) {
				java.util.Date invoiceDate = sdf.parse(listInvoice.getCheckOutDate());
				cellchk.setPhrase(new Phrase(simpleDateFormatHotelDate.format(invoiceDate), fontHead));
			} else {
				cellchk.setPhrase(new Phrase(""));
			}
			tablechk.addCell(cellchk);

			cellchk.setPhrase(new Phrase("Room         : ", fontHead));
			tablechk.addCell(cellchk);
			lineCount++;
			cellchk.setPhrase(new Phrase(listInvoice.getRoom_number(), fontHead));
			tablechk.addCell(cellchk);

			cellchk.setPhrase(new Phrase("Check In       : ", fontchk));
			tablechk.addCell(cellchk);
			lineCount++;
			String formattedDeptDate = "";
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh.mm aa");
			java.util.Date dateCheckin = sdf.parse(listInvoice.getCheckInDate());
			java.sql.Date sqlDateCheckin = new Date(dateCheckin.getTime());
			if (listInvoice.getCheckOutDate() != null) {
				java.util.Date dateCheckOut = sdf.parse(listInvoice.getCheckOutDate());
				java.sql.Date sqlDateCheckOut = new Date(dateCheckOut.getTime());
				java.sql.Timestamp tstmpDeptDate = java.sql.Timestamp
						.valueOf(sqlDateCheckOut + " " + (listInvoice.getAct_depart_time()));
				formattedDeptDate = dateFormat.format(tstmpDeptDate).toString();
			}

			java.sql.Timestamp tstmpArrDate = java.sql.Timestamp
					.valueOf(sqlDateCheckin + " " + (listInvoice.getArr_time()));
			String formattedArrDate = dateFormat.format(tstmpArrDate).toString();

			cellchk.setPhrase(new Phrase(formattedArrDate, fontchk));
			tablechk.addCell(cellchk);

			cellchk.setPhrase(new Phrase("Check Out    : ", fontchk));
			tablechk.addCell(cellchk);
			lineCount++;
			if (listInvoice.getCheckOutDate() != null) {
				//	java.util.Date dateCheckout = sdf.parse(listInvoice.getCheckOutDate());
				//	java.sql.Date sqlDateCheckout = new Date(dateCheckout.getTime());
				cellchk.setPhrase(new Phrase(formattedDeptDate, fontchk));
			} else {
				cellchk.setPhrase(new Phrase(""));
			}
			tablechk.addCell(cellchk);

			cellchk.setPhrase(new Phrase("Guest Name : ", fontchk));
			tablechk.addCell(cellchk);
			lineCount++;
			cellchk.setPhrase(new Phrase(listInvoice.getGuestName(), fontchk));
			tablechk.addCell(cellchk);

			cellTemp.addElement(tablechk);
			tableHead.addCell(cellDetails);
			tableHead.addCell(cellTemp);
			doc.add(tableHead);

			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] { 1.1f, 3.0f, 1.2f, 1.05f, 1.5f, 1.5f, 1.0f });
			table.setSpacingBefore(10);
			PdfPCell cell = new PdfPCell();
			lineCount++;
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(4);
			cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
			cell.setPhrase(new Phrase("", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("HSN/SAC", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Base ", font));
			table.addCell(cell);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
			cell.setPhrase(new Phrase(listInvoice.getTax1Code(), font));
			table.addCell(cell);
			cell.setPhrase(new Phrase(listInvoice.getTax2Code(), font));
			table.addCell(cell);
			cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
			cell.setPhrase(new Phrase("Total", font));
			table.addCell(cell);
			doc.add(table);

			PdfPTable tableNew = new PdfPTable(9);
			tableNew.setWidthPercentage(100.0f);
			tableNew.setWidths(new float[] { 1.1f, 3.0f, 1.2f, 1.05f, 0.6f, 0.9f, 0.6f, 0.9f, 1.0f });
			PdfPCell cellNew = new PdfPCell();
			lineCount++;
			cellNew.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellNew.setPadding(4);
			cellNew.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
			cellNew.setPhrase(new Phrase("Date", font));
			tableNew.addCell(cellNew);
			cellNew.setPhrase(new Phrase("Description", font));
			tableNew.addCell(cellNew);
			cellNew.setPhrase(new Phrase("Code", font));
			tableNew.addCell(cellNew);
			cellNew.setPhrase(new Phrase("Amount", font));
			tableNew.addCell(cellNew);
			cellNew.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
			cellNew.setPhrase(new Phrase("%", font));
			tableNew.addCell(cellNew);
			cellNew.setPhrase(new Phrase("Amount", font));
			tableNew.addCell(cellNew);
			cellNew.setPhrase(new Phrase("%", font));
			tableNew.addCell(cellNew);
			cellNew.setPhrase(new Phrase("Amount", font));
			tableNew.addCell(cellNew);
			cellNew.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
			cell.setPhrase(new Phrase("Amount", font));
			tableNew.addCell(cellNew);
			tableNew.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			boolean isComplementary = false;
			String transactionDate = "";
			double subTotal = 0.0;
			double baseTotal = 0.0;
			double tax1Total = 0.0;
			double tax2Total = 0.0;
			double grandTotal = 0.0;
			double grandBaseTotal = 0.0;
			double grandTax1Total = 0.0;
			double grandTax2Total = 0.0;

			PdfPTable grandTotalTable = new PdfPTable(5);
			grandTotalTable.setWidthPercentage(100.0f);
			grandTotalTable.setWidths(new float[] { 5.3f, 1.05f, 1.5f, 1.5f, 1.0f });

			int rowCount = 0;

			for (Transaction txnInvoice : txnListInvoice) {
				rowCount++;

				PdfPCell cellDayTotal = new PdfPCell();
				cellDayTotal.setPadding(5);
				cellDayTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellDayTotal.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

				if (txnInvoice.getAcc_mst_id() != 4 && txnInvoice.getAcc_mst_id() != 5
						&& txnInvoice.getAcc_mst_id() != 6 && txnInvoice.getAcc_mst_id() != 8
						&& txnInvoice.getAcc_mst_id() != 9 && txnInvoice.getAcc_mst_id() != 10
						&& txnInvoice.getAcc_mst_id() != 12 && txnInvoice.getAcc_mst_id() != 13
						&& txnInvoice.getAcc_mst_id() != 14) {
					tableNew.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					tableNew.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					if (!transactionDate.equals(txnInvoice.getTxn_date())) {
						if (transactionDate != "") {
							cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
							cellDayTotal.setPhrase(new Phrase(""));
							tableNew.addCell(cellDayTotal);
							cellDayTotal.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
							cellDayTotal.setPhrase(new Phrase(""));
							tableNew.addCell(cellDayTotal);
							cellDayTotal.setPhrase(new Phrase("Day Total", font));
							tableNew.addCell(cellDayTotal);
							cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
							cellDayTotal.setPhrase(new Phrase(formatter.format(baseTotal), font));
							tableNew.addCell(cellDayTotal);
							cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
							cellDayTotal.setPhrase(new Phrase(""));
							tableNew.addCell(cellDayTotal);
							cellDayTotal.setBorder(Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
							cellDayTotal.setPhrase(new Phrase(formatter.format(tax1Total), font));
							tableNew.addCell(cellDayTotal);
							cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
							cellDayTotal.setPhrase(new Phrase(""));
							tableNew.addCell(cellDayTotal);
							cellDayTotal.setBorder(Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
							cellDayTotal.setPhrase(new Phrase(formatter.format(tax2Total), font));
							tableNew.addCell(cellDayTotal);
							cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
							cellDayTotal.setPhrase(new Phrase(formatter.format(subTotal), font));
							tableNew.addCell(cellDayTotal);
							cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
							lineCount++;

						}

						baseTotal = 0.0;
						tax1Total = 0.0;
						tax2Total = 0.0;
						subTotal = 0.0;

						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
						cell.setPhrase(new Phrase(txnInvoice.getTxn_date(), font2));
						tableNew.addCell(cell);
					} else {
						cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
						cell.setPhrase(new Phrase(""));
						tableNew.addCell(cell);
					}

					baseTotal += txnInvoice.getInvoiceBaseAmount();
					tax1Total += txnInvoice.getTax1_amount();
					tax2Total += txnInvoice.getTax2_amount();
					subTotal = baseTotal + tax1Total + tax2Total;

					grandBaseTotal += txnInvoice.getInvoiceBaseAmount();
					grandTax1Total += txnInvoice.getTax1_amount();
					grandTax2Total += txnInvoice.getTax2_amount();

					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(txnInvoice.getAccMstName(), font2));
					tableNew.addCell(cell);

					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPhrase(new Phrase(String.valueOf(txnInvoice.getHsnCode()), font2));
					tableNew.addCell(cell);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(formatter.format(txnInvoice.getInvoiceBaseAmount()), font2));
					tableNew.addCell(cell);

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(String.valueOf(txnInvoice.getTax1_pc()), font2));
					tableNew.addCell(cell);

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(formatter.format(txnInvoice.getTax1_amount()), font2));
					tableNew.addCell(cell);

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(String.valueOf(txnInvoice.getTax2_pc()), font2));
					tableNew.addCell(cell);

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(formatter.format(txnInvoice.getTax2_amount()), font2));
					tableNew.addCell(cell);

					double totalAmount = txnInvoice.getInvoiceBaseAmount() + txnInvoice.getTax1_amount()
					+ txnInvoice.getTax2_amount();

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(formatter.format(totalAmount), font2));
					tableNew.addCell(cell);

					transactionDate = txnInvoice.getTxn_date().toString();
					lineCount++;
				}

				if (rowCount == txnListInvoice.size()) {
					cellDayTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
					cellDayTotal.setPhrase(new Phrase(""));
					tableNew.addCell(cellDayTotal);
					cellDayTotal.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
					cellDayTotal.setPhrase(new Phrase("", font));
					tableNew.addCell(cellDayTotal);
					cellDayTotal.setPhrase(new Phrase("Day Total", font));
					tableNew.addCell(cellDayTotal);
					cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
					cellDayTotal.setPhrase(new Phrase(String.valueOf(formatter.format(baseTotal)), font));
					tableNew.addCell(cellDayTotal);
					cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
					cellDayTotal.setPhrase(new Phrase(""));
					tableNew.addCell(cellDayTotal);
					cellDayTotal.setBorder(Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
					cellDayTotal.setPhrase(new Phrase(String.valueOf(formatter.format(tax1Total)), font));
					tableNew.addCell(cellDayTotal);
					cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
					cellDayTotal.setPhrase(new Phrase(""));
					tableNew.addCell(cellDayTotal);
					cellDayTotal.setBorder(Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
					cellDayTotal.setPhrase(new Phrase(String.valueOf(formatter.format(tax2Total)), font));
					tableNew.addCell(cellDayTotal);
					cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
					cellDayTotal.setPhrase(new Phrase(formatter.format(subTotal), font));
					tableNew.addCell(cellDayTotal);
					lineCount++;

					for (int i = rowCount; i <= 12; i++) {
						PdfPCell emptyCell = new PdfPCell();
						emptyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						emptyCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);

						emptyCell.setPhrase(new Phrase(" "));
						grandTotalTable.addCell(emptyCell);

						emptyCell.setPhrase(new Phrase(""));
						grandTotalTable.addCell(emptyCell);

						emptyCell.setPhrase(new Phrase(""));
						grandTotalTable.addCell(emptyCell);

						emptyCell.setPhrase(new Phrase(""));
						grandTotalTable.addCell(emptyCell);

						emptyCell.setPhrase(new Phrase(""));
						grandTotalTable.addCell(emptyCell);
						lineCount++;
					}

					for (int i = rowCount; i == 15; i++) {
						PdfPCell emptyCell = new PdfPCell();
						emptyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						emptyCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);

						emptyCell.setPhrase(new Phrase(" "));
						grandTotalTable.addCell(emptyCell);

						emptyCell.setPhrase(new Phrase(""));
						grandTotalTable.addCell(emptyCell);

						emptyCell.setPhrase(new Phrase(""));
						grandTotalTable.addCell(emptyCell);

						emptyCell.setPhrase(new Phrase(""));
						grandTotalTable.addCell(emptyCell);

						emptyCell.setPhrase(new Phrase(""));
						grandTotalTable.addCell(emptyCell);
						lineCount++;
					}

					// grand total

					grandTotal = grandBaseTotal + grandTax1Total + grandTax2Total;

					PdfPCell grandTotalcell = new PdfPCell();
					grandTotalcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					grandTotalcell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);

					grandTotalcell.setPhrase(new Phrase("Grand Total", font));
					grandTotalTable.addCell(grandTotalcell);

					grandTotalcell.setPhrase(new Phrase(String.valueOf(formatter.format(grandBaseTotal)), font));
					grandTotalTable.addCell(grandTotalcell);

					grandTotalcell.setPhrase(new Phrase(String.valueOf(formatter.format(grandTax1Total)), font));
					grandTotalTable.addCell(grandTotalcell);

					grandTotalcell.setPhrase(new Phrase(String.valueOf(formatter.format(grandTax2Total)), font));
					grandTotalTable.addCell(grandTotalcell);

					grandTotalcell.setPhrase(new Phrase(String.valueOf(formatter.format(grandTotal)), font));
					grandTotalTable.addCell(grandTotalcell);
					lineCount++;
				}

				if (txnInvoice.getAcc_mst_id() == 14) {
					isComplementary = true;
				}

				if (lineCount > pageMaxRows) {
					lineCount = 0;
				}

			}

			tableNew.getDefaultCell().setColspan(6);
			tableNew.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tableNew.addCell("");
			doc.add(tableNew);

			doc.add(grandTotalTable);
			if (lineCount > pageMinRows) {
				doc.newPage();
			}

			PdfPTable table_summary = new PdfPTable(2);
			table_summary.setWidthPercentage(100.0f);
			table_summary.setWidths(new float[] { 7.0f, 2.22f });

			PdfPCell tableSummaryCell = new PdfPCell();
			tableSummaryCell.setBackgroundColor(new BaseColor(255, 255, 255));
			tableSummaryCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			tableSummaryCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
			table_summary.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableSummaryCell.setPhrase(new Phrase("Sub Total ", font));
			table_summary.addCell(tableSummaryCell);
			table_summary.getDefaultCell().setColspan(1);
			tableSummaryCell.setPhrase(new Phrase(String.valueOf(formatter.format(listInvoice.getTotal())), font));
			table_summary.addCell(tableSummaryCell);

			tableSummaryCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			tableSummaryCell.setPhrase(new Phrase("(-) Discounts", font));
			table_summary.addCell(tableSummaryCell);
			table_summary.getDefaultCell().setColspan(1);
			tableSummaryCell.setPhrase(new Phrase(formatter.format(listInvoice.getDiscount()), font));
			table_summary.addCell(tableSummaryCell);

			tableSummaryCell.setPhrase(new Phrase("Service Charge", font));
			table_summary.addCell(tableSummaryCell);

			table_summary.getDefaultCell().setColspan(1);
			tableSummaryCell.setPhrase(new Phrase(formatter.format(listInvoice.getServiceCharge()), font));
			table_summary.addCell(tableSummaryCell);
			// if(listInvoice.getNationality().equals(listInvoice.getConstNationality()) &&
			// listInvoice.getState().equals(listInvoice.getConstState())) {
			/*
			 * if(listInvoice.getTax1()!=0){ tableSummaryCell.setPhrase(new
			 * Phrase(listInvoice.getTax1Code(), font));
			 * table_summary.addCell(tableSummaryCell);
			 * 
			 * //table_summary.addCell(listInvoice.getTax1Code());
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getTax1()), font));
			 * table_summary.addCell(tableSummaryCell);
			 * //table_summary.addCell(formatter.format(listInvoice.getTax1()));
			 * 
			 * }
			 */

			/*
			 * if(listInvoice.getTax2()!=0){ tableSummaryCell.setPhrase(new
			 * Phrase(listInvoice.getTax2Code(), font));
			 * table_summary.addCell(tableSummaryCell);
			 * //table_summary.addCell(listInvoice.getTax2Code());
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getTax2()), font));
			 * table_summary.addCell(tableSummaryCell);
			 * //table_summary.addCell(formatter.format(listInvoice.getTax2())); }
			 */

			/*
			 * if(listInvoice.getTax3()!=0){ tableSummaryCell.setPhrase(new
			 * Phrase(listInvoice.getTax3Code() + "@" +listInvoice.getTax3_pc_kfc() + "%",
			 * font)); table_summary.addCell(tableSummaryCell);
			 * //table_summary.addCell(listInvoice.getTax3Code());
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getTax3()), font));
			 * table_summary.addCell(tableSummaryCell);
			 * //table_summary.addCell(formatter.format(listInvoice.getTax3())); }
			 */

			/*
			 * if(listInvoice.getTax4()!=0){ tableSummaryCell.setPhrase(new
			 * Phrase(listInvoice.getTax4Code(), font));
			 * table_summary.addCell(tableSummaryCell);
			 * //table_summary.addCell(listInvoice.getTax4Code());
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getTax4()), font));
			 * table_summary.addCell(tableSummaryCell);
			 * //table_summary.addCell(formatter.format(listInvoice.getTax4())); }
			 */
			/*
			 * }else {
			 * 
			 * tableSummaryCell.setPhrase(new Phrase("IGST", font));
			 * table_summary.addCell(tableSummaryCell); //table_summary.addCell("IGST");
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getTax1() + listInvoice.getTax2() +
			 * listInvoice.getTax3() + listInvoice.getTax4()), font));
			 * table_summary.addCell(tableSummaryCell);
			 * //table_summary.addCell(formatter.format(listInvoice.getTax1() +
			 * listInvoice.getTax2() + listInvoice.getTax3() + listInvoice.getTax4()));
			 * 
			 * }
			 */

			if (sTax.getTotalServiceTax() != 0) {
				tableSummaryCell.setPhrase(new Phrase("Service Tax", font));
				table_summary.addCell(tableSummaryCell);
				table_summary.getDefaultCell().setColspan(1);
				tableSummaryCell.setPhrase(new Phrase(formatter.format(sTax.getTotalServiceTax()), font));
				table_summary.addCell(tableSummaryCell);
			}
			if (listInvoice.getFoodExpense() != 0) {
				tableSummaryCell.setPhrase(new Phrase("Food Expense", font));
				table_summary.addCell(tableSummaryCell);
				table_summary.getDefaultCell().setColspan(1);
				tableSummaryCell.setPhrase(new Phrase(formatter.format(listInvoice.getFoodExpense()), font));
				table_summary.addCell(tableSummaryCell);
			}
			if (listInvoice.getRoundAdjust() != 0) {
				tableSummaryCell.setPhrase(new Phrase("Round Adjust", font));
				table_summary.addCell(tableSummaryCell);
				table_summary.getDefaultCell().setColspan(1);
				tableSummaryCell.setPhrase(new Phrase(formatter.format(listInvoice.getRoundAdjust()), font));
				table_summary.addCell(tableSummaryCell);
			}

			if (listInvoice.getFoodRefund() != 0) {
				tableSummaryCell.setPhrase(new Phrase("(-) POS Refund", font));
				table_summary.addCell(tableSummaryCell);
				table_summary.getDefaultCell().setColspan(1);
				tableSummaryCell.setPhrase(new Phrase(formatter.format(listInvoice.getFoodRefund()), font2));
				table_summary.addCell(tableSummaryCell);
			}

			PdfPTable tableSettlementHead = new PdfPTable(1);
			tableSettlementHead.setWidthPercentage(100.0f);
			tableSettlementHead.setWidths(new float[] { 1.0f });
			tableSettlementHead.getDefaultCell()
			.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
			tableSettlementHead.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

			// Tax Summary table

			Invoice txnSummary = (Invoice) model.get("listInvoice");
			List<Transaction> txnListSummary = txnSummary.getTxnListSummary();

			PdfPTable tableSummaryTax = new PdfPTable(9);
			tableSummaryTax.setWidthPercentage(100.0f);
			tableSummaryTax.setWidths(new float[] { 2.0f, 1.5f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f, 1.9f });
			PdfPCell tableSummaryTaxCell;

			tableSummaryTaxCell = new PdfPCell(new Phrase("HSN/SAC", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTaxCell.setRowspan(2);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			tableSummaryTaxCell = new PdfPCell(new Phrase("Taxable Value", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTaxCell.setRowspan(2);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			tableSummaryTaxCell = new PdfPCell(new Phrase("Central Tax", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTaxCell.setColspan(2);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			tableSummaryTaxCell = new PdfPCell(new Phrase("State tax", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTaxCell.setColspan(2);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			// for niko
			String KFCName = "";
			java.util.Date checkin = sdf.parse(listInvoice.getCheckInDate());
			java.util.Date aug1 =   sdf.parse("2021-07-31");
			 if(checkin.before(aug1)){
				 KFCName = "KFC";
	            }
			tableSummaryTaxCell = new PdfPCell(new Phrase(KFCName, font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTaxCell.setColspan(2);
			tableSummaryTax.addCell(tableSummaryTaxCell);


			//for Behrain
			/*tableSummaryTaxCell = new PdfPCell(new Phrase("Other Tax", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTaxCell.setColspan(2);
			tableSummaryTax.addCell(tableSummaryTaxCell);*/

			tableSummaryTaxCell = new PdfPCell(new Phrase("Total Tax Amount", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTaxCell.setRowspan(2);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			tableSummaryTaxCell = new PdfPCell(new Phrase("Rate", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			tableSummaryTaxCell = new PdfPCell(new Phrase("Amount", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			tableSummaryTaxCell = new PdfPCell(new Phrase("Rate", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			tableSummaryTaxCell = new PdfPCell(new Phrase("Amount", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTax.addCell(tableSummaryTaxCell);
			
			//for kfc
			String KFCRate = "";
			String KFCAmount = "";
			 if(checkin.before(aug1)){
				 KFCRate = "Rate";
				 KFCAmount = "Amount";
	            }

			tableSummaryTaxCell = new PdfPCell(new Phrase(KFCRate, font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			tableSummaryTaxCell = new PdfPCell(new Phrase(KFCAmount, font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			double sum_base_amount = 0, sum_tax1_amount = 0, sum_tax2_amount = 0, sum_tax3_amount = 0, total_amount = 0;
			for (int i = 0; i < txnListSummary.size(); i++) {
				tableSummaryTax.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableSummaryTax.addCell(new Phrase(String.valueOf(((txnListSummary.get(i).getHsnCode()))), font2));
				tableSummaryTax.addCell(
						new Phrase(String.valueOf((formatter.format(txnListSummary.get(i).getBase_amount()))), font2));
				tableSummaryTax.addCell(new Phrase(String.valueOf(((txnListSummary.get(i).getTax1_pc()))), font2));
				tableSummaryTax.addCell(
						new Phrase(String.valueOf((formatter.format(txnListSummary.get(i).getTax1_amount()))), font2));
				tableSummaryTax.addCell(new Phrase(String.valueOf(((txnListSummary.get(i).getTax2_pc()))), font2));
				tableSummaryTax.addCell(
						new Phrase(String.valueOf((formatter.format(txnListSummary.get(i).getTax2_amount()))), font2));
				if(checkin.before(aug1)){
					tableSummaryTax.addCell(new Phrase(String.valueOf(((txnListSummary.get(i).getTax3_pc()))), font2));
					tableSummaryTax.addCell(
								new Phrase(String.valueOf((formatter.format(txnListSummary.get(i).getTax3_amount()))), font2));
		            }
				 else {
					tableSummaryTax.addCell(new Phrase("", font2));
					tableSummaryTax.addCell(new Phrase("", font2));
				 }
				tableSummaryTax.addCell(
						new Phrase(String.valueOf((formatter.format(txnListSummary.get(i).getTax_total()))), font2));

				sum_base_amount = sum_base_amount + txnListSummary.get(i).getBase_amount();
				sum_tax1_amount = sum_tax1_amount + txnListSummary.get(i).getTax1_amount();
				sum_tax2_amount = sum_tax2_amount + txnListSummary.get(i).getTax2_amount();
				sum_tax3_amount = sum_tax3_amount + txnListSummary.get(i).getTax3_amount();
				total_amount = total_amount + txnListSummary.get(i).getTax_total();
			}

			tableSummaryTaxCell = new PdfPCell(new Phrase("Total", font3));
			tableSummaryTaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSummaryTax.addCell(tableSummaryTaxCell);

			tableSummaryTax.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableSummaryTax.addCell(new Phrase(String.valueOf(formatter.format(sum_base_amount)), font2));
			tableSummaryTax.addCell("");
			tableSummaryTax.addCell(new Phrase(String.valueOf(formatter.format(sum_tax1_amount)), font2));
			tableSummaryTax.addCell("");
			tableSummaryTax.addCell(new Phrase(String.valueOf(formatter.format(sum_tax2_amount)), font2));
			tableSummaryTax.addCell("");
			if(checkin.before(aug1)){
				
				tableSummaryTax.addCell(new Phrase(String.valueOf(formatter.format(sum_tax3_amount)), font2));
				
				}
				else {
					tableSummaryTax.addCell(new Phrase("", font2));	
				}
			tableSummaryTax.addCell(new Phrase(String.valueOf(formatter.format(total_amount)), font2));

			// settlement table

			PdfPTable tableSettlementHeader = new PdfPTable(1);
			tableSettlementHeader.setWidthPercentage(100.0f);
			tableSettlementHeader.setWidths(new float[] { 1.0f });
			tableSettlementHeader.getDefaultCell()
			.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
			tableSettlementHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPTable tableSettlement = new PdfPTable(2);
			tableSettlement.setWidthPercentage(100.0f);
			tableSettlement.setWidths(new float[] { 7.0f, 2.22f });
			PdfPCell cellSettlement = new PdfPCell();
			cellSettlement.setHorizontalAlignment(Element.ALIGN_RIGHT);

			cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
			cellSettlement.setPhrase(new Phrase("Net Amount", fontAmt));
			tableSettlement.addCell(cellSettlement);
			cellSettlement.setPhrase(new Phrase(listInvoice.getCurrencySymbol().concat(" ")
					.concat(String.valueOf(formatter.format(listInvoice.getGrandTotal()))), fontAmt));
			tableSettlement.addCell(cellSettlement);

			cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
			cellSettlement.setColspan(2);
			cellSettlement.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (listInvoice.getGrandTotal() == 0.0) {
				cellSettlement.setPhrase(new Phrase(""));
			} else {
				cellSettlement.setPhrase(new Phrase("Amount in words: " + MoneyToWordsConvertor.toWords(
						new BigDecimal(listInvoice.getGrandTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue()),
						fonttxhdr));
			}
			tableSettlement.addCell(cellSettlement);
			cellSettlement.setPhrase(new Phrase("", fontAmt));
			tableSettlement.addCell(cellSettlement);

			int paymentCount = 0;
			double paidAmount = 0.0;
			cellSettlement.setColspan(1);
			cellSettlement.setHorizontalAlignment(Element.ALIGN_RIGHT);
			if (listInvoice.getDeposit() != 0) {
				cellSettlement.setBorder(Rectangle.RIGHT | Rectangle.TOP | Rectangle.LEFT);
				cellSettlement.setPhrase(new Phrase("By Advance", font));
				tableSettlement.addCell(cellSettlement);
				cellSettlement.setPhrase(new Phrase(String.valueOf(formatter.format(listInvoice.getDeposit())), font));
				tableSettlement.addCell(cellSettlement);
			}

			for (Transaction trans : paymentList) {
				paymentCount++;
				if (paymentCount == 1) {
					if (listInvoice.getDeposit() == 0) {

						cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					} else {

						cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					}
				} else {

					cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				}

				cellSettlement
				.setPhrase(new Phrase("By " + String.valueOf(PaymentMode.get(trans.getPayment_mode())), font));
				tableSettlement.addCell(cellSettlement);
				cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				cellSettlement.setPhrase(new Phrase(String.valueOf(formatter.format(trans.getAmount())), font));
				tableSettlement.addCell(cellSettlement);

				paidAmount += trans.getAmount();
			}

			/*
			 * cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM
			 * | Rectangle.TOP); cellSettlement.setPhrase(new Phrase("Paid Amount",
			 * fontAmt)); tableSettlement.addCell(cellSettlement);
			 * cellSettlement.setPhrase(new
			 * Phrase(String.valueOf(formatter.format(paidAmount +
			 * listInvoice.getDeposit())), fontAmt));
			 * tableSettlement.addCell(cellSettlement);
			 */

			cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
			cellSettlement.setPhrase(new Phrase("Balance Amount", fontAmt));
			tableSettlement.addCell(cellSettlement);
			cellSettlement.setPhrase(new Phrase(
					String.valueOf(
							formatter.format(paidAmount + listInvoice.getDeposit() + listInvoice.getRoundAdjust() - listInvoice.getGrandTotal())),
					fontAmt));
			//cellSettlement.setPhrase(new Phrase(String.valueOf(0.00),fontAmt));
			tableSettlement.addCell(cellSettlement);
			
			doc.add(table_summary);
			doc.add(tableSettlementHead);
			doc.add(tableSummaryTax);
			doc.add(tableSettlementHeader);
			doc.add(tableSettlement);

			PdfPTable tableBottom = new PdfPTable(1);
			tableBottom.setWidthPercentage(117.0f);
			PdfPCell cellBottomAmount = new PdfPCell();
			cellBottomAmount.setBorder(Rectangle.NO_BORDER);

			tableBottom.addCell(cellBottomAmount);
			PdfPCell cellBottom = new PdfPCell();
			if (sTax.getTotalServiceTax() != 0) {

				PdfPTable tableSTax = new PdfPTable(3);
				tableSTax.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableSTax.setWidthPercentage(117.0f);
				PdfPCell celltxtSTax = new PdfPCell();
				celltxtSTax.setColspan(3);
				celltxtSTax.setBorder(Rectangle.NO_BORDER);
				celltxtSTax.setPhrase(new Phrase("Service Tax Details"));
				celltxtSTax.setPaddingBottom(10.0f);
				tableSTax.addCell(celltxtSTax);
				PdfPCell cellSTax = new PdfPCell();
				cellSTax.setPhrase(new Phrase("Description", fonttxhdr));
				tableSTax.addCell(cellSTax);
				cellSTax.setPhrase(new Phrase("Tax Rate", fonttxhdr));
				tableSTax.addCell(cellSTax);
				cellSTax.setPhrase(new Phrase("Amount", fonttxhdr));
				tableSTax.addCell(cellSTax);
				tableSTax.addCell(new Phrase("Service Tax", fonttx));
				Phrase stax = new Phrase(String.valueOf(sTax.getServiceTax() + " %"), fonttx);
				Phrase staxcess1 = new Phrase(String.valueOf(sTax.getCess1_pc() + " %"), fonttx);
				Phrase staxcess2 = new Phrase(String.valueOf(sTax.getCess2_pc() + " %"), fonttx);
				Phrase staxcess3 = new Phrase(String.valueOf(sTax.getCess3_pc() + " %"), fonttx);
				Phrase staxcess4 = new Phrase(String.valueOf(sTax.getCess4_pc() + " %"), fonttx);
				Phrase staxcess5 = new Phrase(String.valueOf(sTax.getCess5_pc() + " %"), fonttx);
				if (sTax.getAbatement() != 0) {

					stax = new Phrase(String.valueOf(sTax.getAbatement() + " % on" + sTax.getServiceTax() + " %"),
							fonttx);
					staxcess1 = new Phrase(String.valueOf(sTax.getAbatement() + " % on" + sTax.getCess1_pc() + " %"),
							fonttx);
					staxcess2 = new Phrase(String.valueOf(sTax.getAbatement() + " % on" + sTax.getCess2_pc() + " %"),
							fonttx);
					staxcess3 = new Phrase(String.valueOf(sTax.getAbatement() + " % on" + sTax.getCess3_pc() + " %"),
							fonttx);
					staxcess4 = new Phrase(String.valueOf(sTax.getAbatement() + " % on" + sTax.getCess4_pc() + " %"),
							fonttx);
					staxcess5 = new Phrase(String.valueOf(sTax.getAbatement() + " % on" + sTax.getCess5_pc() + " %"),
							fonttx);

				}

				tableSTax.addCell(stax);
				tableSTax.addCell(new Phrase(formatter.format(Rounding.nRound(sTax.getServiceTax_amt())), fonttx));
				if (sTax.getCess1_pc() != 0) {

					tableSTax.addCell(new Phrase(sTax.getCess1Name(), fonttx));
					tableSTax.addCell(staxcess1);
					tableSTax.addCell(new Phrase(formatter.format(Rounding.nRound(sTax.getCess1_amt())), fonttx));

				}
				if (sTax.getCess2_pc() != 0) {

					tableSTax.addCell(new Phrase(sTax.getCess2Name(), fonttx));
					tableSTax.addCell(staxcess2);
					tableSTax.addCell(new Phrase(formatter.format(Rounding.nRound(sTax.getCess2_amt())), fonttx));

				}
				if (sTax.getCess3_pc() != 0) {

					tableSTax.addCell(new Phrase(sTax.getCess3Name(), fonttx));
					tableSTax.addCell(staxcess3);
					tableSTax.addCell(new Phrase(formatter.format(Rounding.nRound(sTax.getCess3_amt())), fonttx));

				}
				if (sTax.getCess4_pc() != 0) {

					tableSTax.addCell(new Phrase(sTax.getCess4Name(), fonttx));
					tableSTax.addCell(staxcess4);
					tableSTax.addCell(new Phrase(formatter.format(Rounding.nRound(sTax.getCess4_amt())), fonttx));

				}
				if (sTax.getCess5_pc() != 0) {

					tableSTax.addCell(new Phrase(sTax.getCess5Name(), fonttx));
					tableSTax.addCell(staxcess5);
					tableSTax.addCell(new Phrase(formatter.format(Rounding.nRound(sTax.getCess5_amt())), fonttx));

				}
				cellBottom.addElement(tableSTax);
			}

			if (invTmp.isTaxsumryincl()) {
				if (listInvoice.getTax1() != 0 || listInvoice.getTax2() != 0 || listInvoice.getTax3() != 0
						|| listInvoice.getTax4() != 0) {
					int columnCount = 0;
					if (listInvoice.getTax1() != 0) {
						columnCount++;
					}
					if (listInvoice.getTax2() != 0) {
						columnCount++;
					}
					if (listInvoice.getTax3() != 0) {
						columnCount++;
					}
					if (listInvoice.getTax4() != 0) {
						columnCount++;
					}
					PdfPTable table2 = new PdfPTable(3 + columnCount);
					table2.setWidthPercentage(117.0f);
					PdfPCell celltxt = new PdfPCell();
					celltxt.setColspan(3 + columnCount);
					celltxt.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
					celltxt.setPhrase(new Phrase("Tax Summary", fontAmt));
					celltxt.setPaddingBottom(10.0f);
					table2.addCell(celltxt);
					PdfPCell cell3 = new PdfPCell();
					cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell3.setPhrase(new Phrase("Rate %", font));
					table2.addCell(cell3);
					cell3.setPhrase(new Phrase("Amount", font));
					table2.addCell(cell3);
					if (listInvoice.getTax1() != 0) {
						cell3.setPhrase(new Phrase(listInvoice.getTax1Code(), font));
						table2.addCell(cell3);
					}
					if (listInvoice.getTax2() != 0) {
						cell3.setPhrase(new Phrase(listInvoice.getTax2Code(), font));
						table2.addCell(cell3);
					}
					if (listInvoice.getTax3() != 0) {
						cell3.setPhrase(new Phrase(listInvoice.getTax3Code(), font));
						table2.addCell(cell3);
					}
					if (listInvoice.getTax4() != 0) {
						cell3.setPhrase(new Phrase(listInvoice.getTax4Code(), font));
						table2.addCell(cell3);
					}
					cell3.setPhrase(new Phrase("Tax Amount", font));
					table2.addCell(cell3);
					Map<Integer, Object> taxMap = listInvoice.getTaxList();
					for (Map.Entry<Integer, Object> entry : taxMap.entrySet()) {
						TaxSummary tax = (TaxSummary) entry.getValue();
						PdfPCell celldes = new PdfPCell();

						celldes.setHorizontalAlignment(Element.ALIGN_RIGHT);
						celldes.setPhrase(new Phrase(formatter.format(tax.getTaxPc()), fonttx));
						table2.addCell(celldes);
						celldes.setPhrase(new Phrase(formatter.format(tax.getAmount()), fonttx));
						table2.addCell(celldes);
						if (listInvoice.getTax1() != 0) {
							celldes.setPhrase(new Phrase(formatter.format(tax.getTax1Amount()), fonttx));
							table2.addCell(celldes);
						}
						if (listInvoice.getTax2() != 0) {
							celldes.setPhrase(new Phrase(formatter.format(tax.getTax2Amount()), fonttx));
							table2.addCell(celldes);
						}
						if (listInvoice.getTax3() != 0) {
							celldes.setPhrase(new Phrase(formatter.format(tax.getTax3Amount()), fonttx));
							table2.addCell(celldes);
						}
						if (listInvoice.getTax4() != 0) {
							celldes.setPhrase(new Phrase(formatter.format(tax.getTax4Amount()), fonttx));
							table2.addCell(celldes);
						}
						celldes.setPhrase(new Phrase(formatter.format(tax.getTaxAmount()), fonttx));
						table2.addCell(celldes);
					}
					table2.setKeepTogether(true);
					cellBottom.setBorder(Rectangle.NO_BORDER);
					cellBottom.addElement(table2);
				}
			}

			if (isComplementary) {
				PdfPTable tblComplementary = new PdfPTable(1);
				tblComplementary.setWidthPercentage(117.0f);
				tblComplementary.setSpacingBefore(4f);
				PdfPCell cellComplementary = new PdfPCell();
				cellComplementary.setColspan(1);
				cellComplementary.setBorder(Rectangle.NO_BORDER);
				cellComplementary.setPhrase(new Phrase("*This is a complementary Invoice", fontAmt));
				tblComplementary.addCell(cellComplementary);
				cellBottom.addElement(tblComplementary);
			}
			tableBottom.addCell(cellBottom);
			

			
			PdfPTable tblsign = new PdfPTable(2);
			tblsign.setSpacingBefore(30.0f);
			tblsign.setWidthPercentage(100.0f);
			InvoiceTemplate invTemp = listInvoice.getHeaderFooter();
			if (invTemp.getSignLine1() != null) {
				Paragraph psignaur = new Paragraph(invTemp.getSignLine1());
				psignaur.setAlignment(Element.ALIGN_LEFT);
				PdfPCell cellsign = new PdfPCell();
				cellsign.setBorder(Rectangle.NO_BORDER);
				cellsign.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellsign.addElement(psignaur);
				tblsign.addCell(cellsign);
			}
			if (invTemp.getSignLine2() != null) {
				Paragraph pSignature = new Paragraph(invTemp.getSignLine2());
				pSignature.setAlignment(Element.ALIGN_RIGHT);
				PdfPCell cellsign2 = new PdfPCell();
				cellsign2.setBorder(Rectangle.NO_BORDER);
				cellsign2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellsign2.addElement(pSignature);

				tblsign.addCell(cellsign2);
			}

			doc.add(tblsign);

//			@SuppressWarnings("unchecked")
//			List<Transaction> list = (List<Transaction>) listInvoice.getPosDetails();
//
//			if (list.size() != 0) {
//
//				doc.newPage();
//
//				PdfPTable tableFoodExpns = new PdfPTable(3);
//				tableFoodExpns.setWidthPercentage(100.0f);
//				tableFoodExpns.setWidths(new float[] { 1.5f, 2f, 1f });
//
//				PdfPCell foodExpenseCell = new PdfPCell();
//				foodExpenseCell.setPadding(8);
//				foodExpenseCell.setBorder(Rectangle.NO_BORDER);
//				foodExpenseCell.setColspan(3);
//				foodExpenseCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				foodExpenseCell.setPhrase(new Phrase("Food Expense", fontFoodExpnseHead));
//				tableFoodExpns.addCell(foodExpenseCell);
//				foodExpenseCell.setColspan(1);
//				foodExpenseCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
//				foodExpenseCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				foodExpenseCell.setPhrase(new Phrase("Date", font));
//				tableFoodExpns.addCell(foodExpenseCell);
//				foodExpenseCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				foodExpenseCell.setPhrase(new Phrase("Invoice No", font));
//				tableFoodExpns.addCell(foodExpenseCell);
//				foodExpenseCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				foodExpenseCell.setPhrase(new Phrase("Amount", font));
//				tableFoodExpns.addCell(foodExpenseCell);
//
//				Double foodExpnsTotal = 0.00;
//
//				for (int i = 0; i < list.size(); i++) {
//
//					foodExpenseCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
//					foodExpenseCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//					foodExpenseCell.setPhrase(new Phrase(String.valueOf(list.get(i).getTxn_date()), font));
//					tableFoodExpns.addCell(foodExpenseCell);
//					foodExpenseCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//					//check length of invoice no
//					/*invNo=list.get(i).getReceived_from();
//					invoiceLength=invNo.length();
//				    char replaceChar='0';
//					String invoiceNumber=paddLeft(invNo,invoiceLength,replaceChar);
//					String invoicePrefix=list.get(i).getInvoicePrefix();
//					if(invoicePrefix!=null) {
//						foodExpenseCell.setPhrase(new Phrase(String.valueOf(invoicePrefix+""+invoiceNumber), font));
//					}else {
//					foodExpenseCell.setPhrase(new Phrase(String.valueOf(invoiceNumber), font));
//					}*/
//					foodExpenseCell.setPhrase(new Phrase(String.valueOf(list.get(i).getReceived_from()), font));
//					tableFoodExpns.addCell(foodExpenseCell);
//					foodExpenseCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					foodExpenseCell.setPhrase(
//							new Phrase(String.valueOf(numberFormat.format(list.get(i).getBase_amount())), font));
//					tableFoodExpns.addCell(foodExpenseCell);
//
//					foodExpnsTotal += list.get(i).getBase_amount();
//
//				}
//
//				PdfPCell cellTotal = new PdfPCell();
//				cellTotal.setColspan(2);
//				cellTotal.setPhrase(new Phrase("Total", font));
//				cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				tableFoodExpns.addCell(cellTotal);
//
//				cellTotal.setColspan(1);
//				cellTotal.setPhrase(new Phrase(numberFormat.format(foodExpnsTotal), fontFoodExpnse));
//				cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				tableFoodExpns.addCell(cellTotal);
//
//				doc.add(tableFoodExpns);
//
//				if (listInvoice.getBillDetails() == 1) {
//
//					Map<String, Object> orderData = (Map<String, Object>) listInvoice.getRefundDetails();
//
//					if (orderData.size() != 0) {
//						Object billDetails = orderData.get("bill_details");
//						JsonParser parser = new JsonParser();
//						if (billDetails != null) {
//							JsonObject orderHdrsArrayObj = parser.parse(billDetails.toString()).getAsJsonObject();
//							JsonArray orderHdrsArray = orderHdrsArrayObj.get("order_hdrs").getAsJsonArray();
//							JsonObject orderHdrsObject = orderHdrsArray.get(0).getAsJsonObject();
//							JsonArray orderDtlArray = orderHdrsObject.get("order_dtls").getAsJsonArray();
//
//							PdfPTable tabletop = new PdfPTable(2);
//							tabletop.setWidths(new float[] { 2.0f, 3.0f });
//							tabletop.setPaddingTop(30.0f);
//							tabletop.setSpacingBefore(3f);
//							tabletop.getDefaultCell().setPaddingBottom(30f);
//
//							PdfPHeaderCell cellspan = new PdfPHeaderCell();
//							cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
//							cellspan.setPadding(6);
//							cellspan.setBorder(Rectangle.NO_BORDER);
//							cellspan.setPhrase(new Phrase("RESTUARANT", font1));
//							cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
//							tabletop.addCell(cellspan);
//
//							PdfPHeaderCell cellspan1 = new PdfPHeaderCell();
//							cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
//							cellspan1.setPadding(6);
//							cellspan1.setBorder(Rectangle.NO_BORDER);
//							cellspan1.setPhrase(new Phrase("BILL", font1));
//							cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
//							tabletop.addCell(cellspan1);
//
//							doc.add(tabletop);
//
//							PdfPTable tableHdr1 = new PdfPTable(2);
//							tableHdr1.setPaddingTop(30.0f);
//							tableHdr1.setWidths(new float[] { 2.0f, 2.0f });
//
//							PdfPCell cellPos = new PdfPCell();
//							cellPos.setBackgroundColor(new BaseColor(255, 255, 255));
//							cellPos.setPadding(3);
//
//							cellPos.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
//
//							String[] orderTime = (orderHdrsObject.get("order_time").toString()).split(" ");
//							cellPos.setPhrase(new Phrase(
//									"Order Date: "
//											+ ((orderHdrsObject.get("order_date")) + orderTime[1]).replace('"', ' '),
//											font3));
//							cellPos.setHorizontalAlignment(Element.ALIGN_LEFT);
//							tableHdr1.addCell(cellPos);
//
//							cellPos.setHorizontalAlignment(Element.ALIGN_LEFT);
//							cellPos.setPhrase(new Phrase(
//									"Order Id: " + (orderHdrsObject.get("order_id").toString()).replace('"', ' '),
//									font3));
//							tableHdr1.addCell(cellPos);
//
//							doc.add(tableHdr1);
//
//							PdfPTable tableHdr2 = new PdfPTable(3);
//							tableHdr2.setWidths(new float[] { 2.0f, 1.0f, 1.0f });
//
//							PdfPCell cellHdr2 = new PdfPCell();
//							cellHdr2.setBackgroundColor(new BaseColor(255, 255, 255));
//							cellHdr2.setPadding(3);
//							tableHdr2.getDefaultCell().setPaddingBottom(10.0f);
//							tableHdr2.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
//							tableHdr2.getDefaultCell().setBorder(Rectangle.BOTTOM);
//							tableHdr2.getDefaultCell().setBorderWidthBottom(2.0f);
//
//							cellHdr2.setPhrase(new Phrase(
//									"Bill No: " + (orderHdrsObject.get("invoice_no").toString()).replace('"', ' '),
//									font3));
//							cellHdr2.setHorizontalAlignment(Element.ALIGN_LEFT);
//							tableHdr2.addCell(cellHdr2);
//
//							cellHdr2.setPhrase(new Phrase(
//									"Room No: " + (orderHdrsArrayObj.get("room_no").toString()).replace('"', ' '),
//									font3));
//							tableHdr2.addCell(cellHdr2);
//
//							cellHdr2.setPhrase(new Phrase(
//									"Shop Code: " + (orderHdrsObject.get("shop_code").toString()).replace('"', ' '),
//									font2));
//							tableHdr2.addCell(cellHdr2);
//
//							doc.add(tableHdr2);
//
//							PdfPTable tablePos = new PdfPTable(6);
//							tablePos.setWidths(new float[] { .8f, 1.0f, 2.5f, 1.5f, 1.5f,1.5f });
//
//							PdfPCell cellPosTblHead = new PdfPCell();
//							cellPosTblHead.setBackgroundColor(new BaseColor(255, 255, 255));
//							cellPosTblHead.setPadding(5);
//							tablePos.getDefaultCell().setPaddingBottom(10.0f);
//							tablePos.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
//							tablePos.getDefaultCell().setBorder(Rectangle.BOTTOM);
//							tablePos.getDefaultCell().setBorderWidthBottom(2.0f);
//							cellPosTblHead.setPhrase(new Phrase("SI#", font));
//							cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
//							tablePos.addCell(cellPosTblHead);
//							cellPosTblHead.setPhrase(new Phrase("Qty", font));
//							cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
//							tablePos.addCell(cellPosTblHead);
//							cellPosTblHead.setPhrase(new Phrase("Item", font));
//							cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
//							tablePos.addCell(cellPosTblHead);
//							cellPosTblHead.setPhrase(new Phrase("Rate", font));
//							cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
//							tablePos.addCell(cellPosTblHead);
//							cellPosTblHead.setPhrase(new Phrase("Discount", font));
//							cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
//							tablePos.addCell(cellPosTblHead);
//							cellPosTblHead.setPhrase(new Phrase("Total", font));
//							cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
//							tablePos.addCell(cellPosTblHead);
//
//							int count = 0;
//							for (int i = 0; i < orderDtlArray.size(); i++) {
//								count++;
//								String sino = "" + count;
//
//								JsonObject obj = (JsonObject) orderDtlArray.get(i);
//
//								PdfPCell cell3 = new PdfPCell();
//								cell3.setBackgroundColor(new BaseColor(255, 255, 255));
//								cell3.setPadding(5);
//								tablePos.getDefaultCell().setPaddingBottom(10.0f);
//								tablePos.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
//								cell3.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
//								tablePos.getDefaultCell().setBorderWidthBottom(2.0f);
//
//								cell3.setPhrase(new Phrase(sino, font2));
//								cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
//								tablePos.addCell(cell3);
//
//								cell3.setPhrase(new Phrase(numberFormat.format(obj.get("qty").getAsDouble()), font2));
//								cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
//								tablePos.addCell(cell3);
//
//								cell3.setPhrase(new Phrase(obj.get("name").getAsString(), font2));
//								cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
//								tablePos.addCell(cell3);
//
//								if(obj.get("tax_calculation_method").equals("0")) {
//
//									cell3.setPhrase(
//											new Phrase(numberFormat.format(obj.get("fixed_price").getAsDouble()), font2));
//									cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//									tablePos.addCell(cell3);
//								}else {
//									rate=obj.get("fixed_price").getAsDouble();
//									double tax1Amount=obj.get("tax1_pc").getAsDouble();
//									double tax2Amount=obj.get("tax2_pc").getAsDouble();
//									double tax3Amount=obj.get("tax3_pc").getAsDouble();
//									rateCalc=rate+(rate*tax1Amount/100)+(rate*tax2Amount/100)+(rate*tax3Amount/100);
//									cell3.setPhrase(
//											new Phrase(numberFormat.format(rateCalc), font2));
//									cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//									tablePos.addCell(cell3);
//								}
//
//
//								cell3.setPhrase(
//										new Phrase(numberFormat.format(obj.get("qty").getAsDouble()*rateCalc-obj.get("item_total").getAsDouble()), font2));
//								cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//								tablePos.addCell(cell3);
//
//								cell3.setPhrase(
//										new Phrase(numberFormat.format(obj.get("item_total").getAsDouble()), font2));
//								cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//								tablePos.addCell(cell3);
//
//							}
//
//							doc.add(tablePos);
//
//							PdfPTable totalTable = new PdfPTable(2);
//							totalTable.setWidths(new float[] { 4.9f, 1f });
//
//							PdfPCell cellPosTotal = new PdfPCell();
//							cellPosTotal.setBackgroundColor(new BaseColor(255, 255, 255));
//							cellPosTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
//							cellPosTotal.setPadding(6);
//							//	cellPosTotal.setColspan(4);
//
//							cellPosTotal.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
//							cellPosTotal.setPhrase(new Phrase("Sub Total ", font));
//							totalTable.addCell(cellPosTotal);
//
//							cellPosTotal.setPhrase(new Phrase(
//									numberFormat.format(orderHdrsObject.get("total_amount").getAsDouble()), font2));
//							totalTable.addCell(cellPosTotal);
//
//							cellPosTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
//
//							cellPosTotal.setPhrase(new Phrase("Discount Amount ", font));
//							totalTable.addCell(cellPosTotal);
//
//							cellPosTotal.setPhrase(new Phrase(
//									numberFormat.format(orderHdrsObject.get("bill_discount_amount").getAsDouble()),
//									font2));
//							totalTable.addCell(cellPosTotal);
//
//							List<Object> refundHeaderList = new ArrayList<>();
//							refundHeaderList = (List<Object>) orderData.get("refund_details");
//
//							double totalrefundamount = 0.0;
//							if (!refundHeaderList.isEmpty()) {
//								Object refundDetailObject = null;
//								for (int i = 0; i < refundHeaderList.size(); i++) {
//									refundDetailObject = refundHeaderList.get(i);
//									JsonObject refundDetailJsonObject = parser.parse(refundDetailObject.toString())
//											.getAsJsonObject();
//									JsonArray refundHdrsJsonArray = refundDetailJsonObject.get("order_hdrs")
//											.getAsJsonArray();
//									JsonObject refundHdrsJsonObject = refundHdrsJsonArray.get(0).getAsJsonObject();
//									JsonArray refundDetailJsonArray = refundHdrsJsonObject.get("order_refund")
//											.getAsJsonArray();
//
//									for (int j = 0; j < refundDetailJsonArray.size(); j++) {
//										JsonObject obj = (JsonObject) refundDetailJsonArray.get(j);
//										totalrefundamount += obj.get("refund_amount").getAsDouble();
//									}
//								}
//
//								cellPosTotal.setPhrase(new Phrase("Refund ", font));
//								totalTable.addCell(cellPosTotal);
//
//								cellPosTotal.setPhrase(new Phrase(numberFormat.format(totalrefundamount), font2));
//								totalTable.addCell(cellPosTotal);
//							}
//							cellPosTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
//							cellPosTotal.setPhrase(new Phrase("Net Amount ", font));
//							cellPosTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
//							totalTable.addCell(cellPosTotal);
//
//							double totalAmount = 0.0;
//							double billDiscount = 0.0;
//							double finalRoundAmount = 0.0;
//							double net = 0.0;
//
//							totalAmount = orderHdrsObject.get("total_amount").getAsDouble();
//							billDiscount = orderHdrsObject.get("bill_discount_amount").getAsDouble();
//							finalRoundAmount = orderHdrsObject.get("final_round_amount").getAsDouble();
//
//							net = totalAmount - billDiscount + finalRoundAmount;
//							cellPosTotal.setPhrase(new Phrase(numberFormat.format(net - totalrefundamount), font2));
//							cellPosTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
//							totalTable.addCell(cellPosTotal);
//
//							cellPosTotal.setBorder(Rectangle.NO_BORDER);
//							cellPosTotal.setPhrase(new Phrase("* Includes tax of Rs.", font2));
//							totalTable.addCell(cellPosTotal);
//
//							Double taxTotal = orderHdrsObject.get("total_tax1").getAsDouble()
//									+ orderHdrsObject.get("total_tax2").getAsDouble()
//									+ orderHdrsObject.get("total_tax3").getAsDouble()
//									+ orderHdrsObject.get("total_gst").getAsDouble()
//									+ orderHdrsObject.get("total_sc").getAsDouble();
//
//							cellPosTotal.setPhrase(new Phrase(numberFormat.format(taxTotal), font2));
//							cellPosTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
//							totalTable.addCell(cellPosTotal);
//
//							//
//
//							doc.add(totalTable);
//
//							double totalRefundTaxAmount = 0.0;
//
//							if (!refundHeaderList.isEmpty()) {
//								Object refundDetailObject = null;
//
//								PdfPTable tableRefundHead = new PdfPTable(1);
//								tableRefundHead.setWidths(new float[] { 3.0f });
//								tableRefundHead.setPaddingTop(30.0f);
//								tableRefundHead.setSpacingBefore(10f);
//								tableRefundHead.getDefaultCell().setPaddingBottom(30f);
//
//								PdfPHeaderCell cellspanRefundHead = new PdfPHeaderCell();
//								cellspanRefundHead.setBackgroundColor(new BaseColor(255, 255, 255));
//								cellspanRefundHead.setPadding(6);
//								cellspanRefundHead.setBorder(Rectangle.NO_BORDER);
//								cellspanRefundHead.setPhrase(new Phrase("REFUND", font1));
//								cellspanRefundHead.setHorizontalAlignment(Element.ALIGN_CENTER);
//								tableRefundHead.addCell(cellspanRefundHead);
//
//								doc.add(tableRefundHead);
//
//								for (int i = 0; i < refundHeaderList.size(); i++) {
//									refundDetailObject = refundHeaderList.get(i);
//									JsonObject refundDetailJsonObject = parser.parse(refundDetailObject.toString())
//											.getAsJsonObject();
//									JsonArray refundHdrsJsonArray = refundDetailJsonObject.get("order_hdrs")
//											.getAsJsonArray();
//									JsonObject refundHdrsJsonObject = refundHdrsJsonArray.get(0).getAsJsonObject();
//									JsonArray refundDetailJsonArray = refundHdrsJsonObject.get("order_refund")
//											.getAsJsonArray();
//
//									PdfPTable tableRefundHdr = new PdfPTable(2);
//									tableRefundHdr.setPaddingTop(30.0f);
//									tableRefundHdr.setWidths(new float[] { 2.0f, 2.0f });
//
//									PdfPCell cellRefundHdr = new PdfPCell();
//									cellRefundHdr.setBackgroundColor(new BaseColor(255, 255, 255));
//									cellRefundHdr.setPadding(3);
//									tableRefundHdr.getDefaultCell().setPaddingBottom(10.0f);
//									tableRefundHdr.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
//									tableRefundHdr.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//									tableRefundHdr.getDefaultCell().setBorderWidthBottom(2.0f);
//
//									cellRefundHdr.setPhrase(new Phrase("Refund Date: "
//											+ (refundHdrsJsonObject.get("refund_date").toString()).replace('"', ' '),
//											font2));
//									cellRefundHdr.setHorizontalAlignment(Element.ALIGN_LEFT);
//									tableRefundHdr.addCell(cellRefundHdr);
//
//									String[] orderRefundTime = (refundHdrsJsonObject.get("refund_time").toString())
//											.split(" ");
//									cellRefundHdr.setPhrase(new Phrase(
//											"Refund Time: " + (orderRefundTime[1].toString()).replace('"', ' '),
//											font2));
//									cellRefundHdr.setHorizontalAlignment(Element.ALIGN_LEFT);
//									tableRefundHdr.addCell(cellRefundHdr);
//
//									doc.add(tableRefundHdr);
//
//									PdfPTable tableRefund = new PdfPTable(4);
//									tableRefund.setWidths(new float[] { .8f, 1.0f, 3.5f, 1.5f, });
//									tableRefund.setKeepTogether(true);
//									tableRefund.setSpacingAfter(10f);
//
//									PdfPCell cellRefund = new PdfPCell();
//									cellRefund.setBackgroundColor(new BaseColor(255, 255, 255));
//									cellRefund.setPadding(5);
//									tableRefund.getDefaultCell().setPaddingBottom(10.0f);
//									tableRefund.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
//									cellRefund.setBorder(
//											Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
//									tableRefund.getDefaultCell().setBorderWidthBottom(2.0f);
//									cellRefund.setPhrase(new Phrase("Si#", font));
//									cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
//									tableRefund.addCell(cellRefund);
//									cellRefund.setPhrase(new Phrase("Qty", font));
//									cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
//									tableRefund.addCell(cellRefund);
//									cellRefund.setPhrase(new Phrase("Item", font));
//									cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
//									tableRefund.addCell(cellRefund);
//									cellRefund.setPhrase(new Phrase("Total", font));
//									cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
//									tableRefund.addCell(cellRefund);
//
//									int number = 0;
//									double refundItemTotal = 0.0;
//									for (int j = 0; j < refundDetailJsonArray.size(); j++) {
//
//										number++;
//										String sino = "" + number;
//
//										JsonObject obj = (JsonObject) refundDetailJsonArray.get(j);
//
//										PdfPCell cellRefundDtl = new PdfPCell();
//										cellRefundDtl.setBackgroundColor(new BaseColor(255, 255, 255));
//										cellRefundDtl.setPadding(5);
//
//										cellRefundDtl.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
//
//										cellRefundDtl.setPhrase(new Phrase(sino, font2));
//										cellRefundDtl.setHorizontalAlignment(Element.ALIGN_CENTER);
//										tableRefund.addCell(cellRefundDtl);
//
//										cellRefundDtl.setPhrase(
//												new Phrase(numberFormat.format(obj.get("qty").getAsDouble()), font2));
//										cellRefundDtl.setHorizontalAlignment(Element.ALIGN_LEFT);
//										tableRefund.addCell(cellRefundDtl);
//
//										cellRefundDtl.setPhrase(new Phrase(obj.get("name").getAsString(), font2));
//										cellRefundDtl.setHorizontalAlignment(Element.ALIGN_LEFT);
//										tableRefund.addCell(cellRefundDtl);
//
//										cellRefundDtl.setPhrase(new Phrase(
//												numberFormat.format(obj.get("refund_amount").getAsDouble()), font2));
//										cellRefundDtl.setHorizontalAlignment(Element.ALIGN_RIGHT);
//										tableRefund.addCell(cellRefundDtl);
//
//										refundItemTotal += obj.get("refund_amount").getAsDouble();
//										totalRefundTaxAmount += obj.get("tax1_amount").getAsDouble()
//												+ obj.get("tax2_amount").getAsDouble()
//												+ obj.get("tax3_amount").getAsDouble();
//									}
//
//									PdfPCell refundTotalCell = new PdfPCell();
//									refundTotalCell.setBackgroundColor(new BaseColor(255, 255, 255));
//									refundTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//									refundTotalCell.setPadding(5);
//
//									refundTotalCell.setColspan(3);
//									refundTotalCell.setPhrase(new Phrase("Refund Total", font));
//									tableRefund.addCell(refundTotalCell);
//
//									refundTotalCell.setColspan(1);
//									refundTotalCell.setPhrase(new Phrase(numberFormat.format(refundItemTotal), font2));
//									tableRefund.addCell(refundTotalCell);
//
//									refundTotalCell.setColspan(3);
//									refundTotalCell.setBorder(Rectangle.NO_BORDER);
//									refundTotalCell.setPhrase(new Phrase("* Refund ncludes tax of Rs.", font2));
//									tableRefund.addCell(refundTotalCell);
//
//									refundTotalCell.setColspan(1);
//									refundTotalCell
//									.setPhrase(new Phrase(numberFormat.format(totalRefundTaxAmount), font2));
//									refundTotalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//									tableRefund.addCell(refundTotalCell);
//
//									doc.add(tableRefund);
//									
//
//								}
//							}
//						}
//					}
//				}
//
//			}
	//mail		
			

		//	doc.close();
			/*if(listInvoice.getMailType()==1) {
			checkoutService.sendInvoiceMail(listInvoice.getFolioNo(),baos,settingsSystem);
		}*/
			/*
			 * }else {
			 * 
			 * 
			 * PdfPTable table = new PdfPTable(6); table.setWidthPercentage(100.0f);
			 * table.setWidths(new float[] {1.1f,3.0f,1.2f,1.05f,1.5f,1.0f});
			 * table.setSpacingBefore(10); PdfPCell cell = new PdfPCell();
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); cell.setPadding(4);
			 * cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
			 * cell.setPhrase(new Phrase("", font)); table.addCell(cell); cell.setPhrase(new
			 * Phrase("", font)); table.addCell(cell); cell.setPhrase(new
			 * Phrase("HSN/SAC",font)); table.addCell(cell); cell.setPhrase(new
			 * Phrase("Base ", font)); table.addCell(cell); cell.setBorder(Rectangle.TOP |
			 * Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT); cell.setPhrase(new
			 * Phrase("IGST", font)); table.addCell(cell); cell.setBorder(Rectangle.TOP |
			 * Rectangle.LEFT | Rectangle.RIGHT); cell.setPhrase(new Phrase("Total", font));
			 * table.addCell(cell); doc.add(table);
			 * 
			 * PdfPTable tableNew = new PdfPTable(7); tableNew.setWidthPercentage(100.0f);
			 * tableNew.setWidths(new float[] {1.1f,3.0f,1.2f,1.05f,0.5f,1.0f,1.0f});
			 * 
			 * PdfPCell cellNew = new PdfPCell();
			 * cellNew.setHorizontalAlignment(Element.ALIGN_CENTER); cellNew.setPadding(4);
			 * cellNew.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
			 * cellNew.setPhrase(new Phrase("Date", font)); tableNew.addCell(cellNew);
			 * cellNew.setPhrase(new Phrase("Description", font));
			 * tableNew.addCell(cellNew); cellNew.setPhrase(new Phrase("Code",font));
			 * tableNew.addCell(cellNew); cellNew.setPhrase(new Phrase("Amount", font));
			 * tableNew.addCell(cellNew); cellNew.setBorder(Rectangle.BOTTOM |
			 * Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP); cellNew.setPhrase(new
			 * Phrase("%", font)); tableNew.addCell(cellNew); cellNew.setPhrase(new
			 * Phrase("Amount", font)); tableNew.addCell(cellNew);
			 * cellNew.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
			 * cell.setPhrase(new Phrase("Amount", font)); tableNew.addCell(cellNew);
			 * tableNew.getDefaultCell().setBorder(Rectangle.NO_BORDER); boolean
			 * isComplementary = false; String transactionDate = ""; double subTotal = 0.0;
			 * double baseTotal = 0.0; double tax1Total = 0.0; double tax2Total = 0.0;
			 * double grandTotal = 0.0; double grandBaseTotal = 0.0; double grandTax1Total =
			 * 0.0; double grandTax2Total = 0.0;
			 * 
			 * PdfPTable grandTotalTable = new PdfPTable(4);
			 * grandTotalTable.setWidthPercentage(100.0f); grandTotalTable.setWidths(new
			 * float[] {5.3f,1.05f,1.5f,1.0f});
			 * 
			 * int rowCount = 0; for (Transaction txnInvoice : txnListInvoice) { rowCount++;
			 * 
			 * PdfPCell cellDayTotal = new PdfPCell();
			 * cellDayTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * cellDayTotal.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
			 * 
			 * if(txnInvoice.getAcc_mst_id()!=4 && txnInvoice.getAcc_mst_id()!=5 &&
			 * txnInvoice.getAcc_mst_id()!=6 && txnInvoice.getAcc_mst_id()!=8&&
			 * txnInvoice.getAcc_mst_id()!=9 && txnInvoice.getAcc_mst_id()!=10 &&
			 * txnInvoice.getAcc_mst_id()!=12 && txnInvoice.getAcc_mst_id()!=13 &&
			 * txnInvoice.getAcc_mst_id()!=14){
			 * tableNew.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tableNew.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			 * 
			 * if(!transactionDate.equals(txnInvoice.getTxn_date())) { if(transactionDate !=
			 * "") { cellDayTotal.setPadding(4); cellDayTotal.setBorder(Rectangle.LEFT |
			 * Rectangle.TOP | Rectangle.BOTTOM); cellDayTotal.setColspan(3);
			 * cellDayTotal.setPhrase(new Phrase("Day Total", font));
			 * tableNew.addCell(cellDayTotal); cellDayTotal.setColspan(1);
			 * cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP |
			 * Rectangle.BOTTOM); cellDayTotal.setPhrase(new
			 * Phrase(formatter.format(baseTotal), font)); tableNew.addCell(cellDayTotal);
			 * cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
			 * cellDayTotal.setPhrase(new Phrase("")); tableNew.addCell(cellDayTotal);
			 * cellDayTotal.setBorder(Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
			 * cellDayTotal.setPhrase(new Phrase(formatter.format(tax1Total + tax1Total),
			 * font)); tableNew.addCell(cellDayTotal);
			 * 
			 * cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP |
			 * Rectangle.BOTTOM); cellDayTotal.setPhrase(new
			 * Phrase(formatter.format(subTotal), font)); tableNew.addCell(cellDayTotal);
			 * cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP); }
			 * 
			 * baseTotal = 0.0; tax1Total = 0.0; tax2Total = 0.0; subTotal = 0.0;
			 * 
			 * cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
			 * cell.setPhrase(new Phrase(txnInvoice.getTxn_date(),font2));
			 * tableNew.addCell(cell); }else { cell.setBorder(Rectangle.LEFT |
			 * Rectangle.RIGHT); cell.setPhrase(new Phrase("")); tableNew.addCell(cell); }
			 * 
			 * baseTotal += txnInvoice.getInvoiceBaseAmount(); tax1Total +=
			 * txnInvoice.getTax1_amount(); tax2Total += txnInvoice.getTax2_amount();
			 * subTotal = baseTotal + tax1Total + tax2Total;
			 * 
			 * grandBaseTotal += txnInvoice.getInvoiceBaseAmount(); grandTax1Total +=
			 * txnInvoice.getTax1_amount(); grandTax2Total += txnInvoice.getTax2_amount();
			 * 
			 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); cell.setPhrase(new
			 * Phrase(txnInvoice.getAccMstName(),font2)); tableNew.addCell(cell);
			 * 
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); cell.setPhrase(new
			 * Phrase(String.valueOf(txnInvoice.getHsnCode()),font2));
			 * tableNew.addCell(cell); cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * cell.setPhrase(new
			 * Phrase(formatter.format(txnInvoice.getInvoiceBaseAmount()),font2));
			 * tableNew.addCell(cell);
			 * 
			 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); cell.setPhrase(new
			 * Phrase(String.valueOf(txnInvoice.getTax1_pc() +
			 * txnInvoice.getTax1_pc()),font2)); tableNew.addCell(cell);
			 * 
			 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); cell.setPhrase(new
			 * Phrase(formatter.format(txnInvoice.getTax1_amount() +
			 * txnInvoice.getTax2_amount()),font2)); tableNew.addCell(cell);
			 * 
			 * double totalAmount = txnInvoice.getInvoiceBaseAmount() +
			 * txnInvoice.getTax1_amount() + txnInvoice.getTax2_amount();
			 * 
			 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); cell.setPhrase(new
			 * Phrase(formatter.format(totalAmount),font2)); tableNew.addCell(cell);
			 * 
			 * transactionDate = txnInvoice.getTxn_date().toString();
			 * 
			 * }
			 * 
			 * if(rowCount == txnListInvoice.size()) {
			 * cellDayTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
			 * 
			 * cellDayTotal.setColspan(3); cellDayTotal.setPhrase(new Phrase("Day Total",
			 * font)); tableNew.addCell(cellDayTotal); cellDayTotal.setColspan(1);
			 * cellDayTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP |
			 * Rectangle.BOTTOM); cellDayTotal.setPhrase(new
			 * Phrase(String.valueOf(formatter.format(baseTotal)), font));
			 * tableNew.addCell(cellDayTotal); cellDayTotal.setBorder(Rectangle.LEFT
			 * |Rectangle.TOP | Rectangle.BOTTOM); cellDayTotal.setPhrase(new Phrase(""));
			 * tableNew.addCell(cellDayTotal); cellDayTotal.setBorder(Rectangle.RIGHT |
			 * Rectangle.TOP | Rectangle.BOTTOM); cellDayTotal.setPhrase(new
			 * Phrase(String.valueOf(formatter.format(tax1Total + tax2Total)), font));
			 * tableNew.addCell(cellDayTotal); cellDayTotal.setBorder(Rectangle.LEFT |
			 * Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
			 * cellDayTotal.setPhrase(new Phrase(formatter.format(subTotal), font));
			 * tableNew.addCell(cellDayTotal);
			 * 
			 * 
			 * for(int i=lineCount; i<=12; i++) {
			 * 
			 * PdfPCell emptyCell = new PdfPCell();
			 * emptyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * emptyCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			 * 
			 * emptyCell.setPhrase(new Phrase(" ")); grandTotalTable.addCell(emptyCell);
			 * 
			 * emptyCell.setPhrase(new Phrase("")); grandTotalTable.addCell(emptyCell);
			 * 
			 * emptyCell.setPhrase(new Phrase("")); grandTotalTable.addCell(emptyCell);
			 * 
			 * emptyCell.setPhrase(new Phrase("")); grandTotalTable.addCell(emptyCell);
			 * 
			 * }
			 * 
			 * //grand total
			 * 
			 * grandTotal = grandBaseTotal + grandTax1Total + grandTax2Total;
			 * 
			 * PdfPCell grandTotalcell = new PdfPCell();
			 * grandTotalcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * grandTotalcell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM
			 * | Rectangle.TOP);
			 * 
			 * grandTotalcell.setPhrase(new Phrase("Grand Total", font));
			 * grandTotalTable.addCell(grandTotalcell);
			 * 
			 * grandTotalcell.setPhrase(new
			 * Phrase(String.valueOf(formatter.format(grandBaseTotal)), font));
			 * grandTotalTable.addCell(grandTotalcell);
			 * 
			 * grandTotalcell.setPhrase(new
			 * Phrase(String.valueOf(formatter.format(grandTax1Total + grandTax2Total)),
			 * font)); grandTotalTable.addCell(grandTotalcell);
			 * 
			 * grandTotalcell.setPhrase(new
			 * Phrase(String.valueOf(formatter.format(grandTotal)), font));
			 * grandTotalTable.addCell(grandTotalcell);
			 * 
			 * }
			 * 
			 * if(txnInvoice.getAcc_mst_id() == 14) { isComplementary = true; }
			 * 
			 * lineCount++; }
			 * 
			 * tableNew.getDefaultCell().setColspan(6);
			 * tableNew.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			 * tableNew.addCell(""); doc.add(tableNew);
			 * 
			 * doc.add(grandTotalTable);
			 * 
			 * PdfPTable table_summary = new PdfPTable(2);
			 * table_summary.setWidthPercentage(100.0f); table_summary.setWidths(new float[]
			 * {7.0f,2.76f});
			 * 
			 * PdfPCell tableSummaryCell = new PdfPCell();
			 * tableSummaryCell.setBackgroundColor(new BaseColor(255, 255, 255));
			 * tableSummaryCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * 
			 * tableSummaryCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
			 * table_summary.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * tableSummaryCell.setPhrase(new Phrase("Sub Total", font));
			 * table_summary.addCell(tableSummaryCell);
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(String.valueOf(formatter.format(listInvoice.getTotal())), font));
			 * table_summary.addCell(tableSummaryCell);
			 * 
			 * tableSummaryCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			 * tableSummaryCell.setPhrase(new Phrase("(-) Discounts", font));
			 * table_summary.addCell(tableSummaryCell);
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getDiscount()), font));
			 * table_summary.addCell(tableSummaryCell);
			 * 
			 * tableSummaryCell.setPhrase(new Phrase("Service Charge", font));
			 * table_summary.addCell(tableSummaryCell);
			 * 
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getServiceCharge()), font));
			 * table_summary.addCell(tableSummaryCell);
			 * 
			 * tableSummaryCell.setPhrase(new Phrase("IGST", font));
			 * table_summary.addCell(tableSummaryCell);
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getTax1() + listInvoice.getTax2() +
			 * listInvoice.getTax3() + listInvoice.getTax4()), font));
			 * table_summary.addCell(tableSummaryCell);
			 * 
			 * if(sTax.getTotalServiceTax()!=0){ tableSummaryCell.setPhrase(new
			 * Phrase("Service Tax", font)); table_summary.addCell(tableSummaryCell);
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(sTax.getTotalServiceTax()), font));
			 * table_summary.addCell(tableSummaryCell); } if(listInvoice.getFoodExpense() !=
			 * 0) { tableSummaryCell.setPhrase(new Phrase("Food Expense", font));
			 * table_summary.addCell(tableSummaryCell);
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getFoodExpense()), font));
			 * table_summary.addCell(tableSummaryCell); }
			 * if(listInvoice.getRoundAdjust()!=0){ tableSummaryCell.setPhrase(new
			 * Phrase("Round Adjust", font)); table_summary.addCell(tableSummaryCell);
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getRoundAdjust()), font));
			 * table_summary.addCell(tableSummaryCell); }
			 * 
			 * if(listInvoice.getFoodRefund() != 0) { tableSummaryCell.setPhrase(new
			 * Phrase("(-) POS Refund", font)); table_summary.addCell(tableSummaryCell);
			 * table_summary.getDefaultCell().setColspan(1); tableSummaryCell.setPhrase(new
			 * Phrase(formatter.format(listInvoice.getFoodRefund()), font2));
			 * table_summary.addCell(tableSummaryCell); }
			 * 
			 * tableSummaryCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT |
			 * Rectangle.BOTTOM); tableSummaryCell.setPhrase(new Phrase("Net Amount",
			 * fontAmt)); table_summary.addCell(tableSummaryCell);
			 * 
			 * tableSummaryCell.setPhrase(new
			 * Phrase(listInvoice.getCurrencySymbol().concat(" ").concat(String.valueOf(
			 * formatter.format(listInvoice.getGrandTotal()))), fontAmt));
			 * table_summary.addCell(tableSummaryCell);
			 * 
			 * // settlement table
			 * 
			 * PdfPTable tableSettlementHead = new PdfPTable(1);
			 * tableSettlementHead.setWidthPercentage(100.0f);
			 * tableSettlementHead.setWidths(new float[] {1.0f});
			 * tableSettlementHead.getDefaultCell().setBorder(Rectangle.LEFT |
			 * Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
			 * tableSettlementHead.getDefaultCell().setHorizontalAlignment(Element.
			 * ALIGN_CENTER);
			 * 
			 * PdfPCell cellAmountTitle=new PdfPCell(); cellAmountTitle.setColspan(1);
			 * cellAmountTitle.setFixedHeight(20f); cellAmountTitle.setBorder(Rectangle.LEFT
			 * | Rectangle.RIGHT);
			 * 
			 * if(listInvoice.getGrandTotal()==0.0){ cellAmountTitle.setPhrase(new
			 * Phrase("")); } else{ cellAmountTitle.setPhrase(new
			 * Phrase("Amount in words: "+ MoneyToWordsConvertor.toWords(new
			 * BigDecimal(listInvoice.getGrandTotal()).setScale(2,
			 * RoundingMode.HALF_UP).doubleValue()),fonttxhdr)); }
			 * 
			 * tableSettlementHead.addCell(cellAmountTitle);
			 * tableSettlementHead.addCell("Settlement Details");
			 * 
			 * PdfPTable tableSettlement = new PdfPTable(2);
			 * tableSettlement.setWidthPercentage(100.0f); tableSettlement.setWidths(new
			 * float[] {7.0f,2.76f}); PdfPCell cellSettlement = new PdfPCell();
			 * cellSettlement.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * 
			 * int paymentCount = 0; double paidAmount = 0.0; if(listInvoice.getDeposit() !=
			 * 0) { cellSettlement.setBorder(Rectangle.RIGHT | Rectangle.TOP |
			 * Rectangle.LEFT); cellSettlement.setPhrase(new Phrase("By Advance", font));
			 * tableSettlement.addCell(cellSettlement); cellSettlement.setPhrase(new
			 * Phrase(String.valueOf(formatter.format(listInvoice.getDeposit())), font));
			 * tableSettlement.addCell(cellSettlement); }
			 * 
			 * for(Transaction trans : paymentList) { paymentCount++; if(paymentCount == 1)
			 * { if(listInvoice.getDeposit() == 0) {
			 * 
			 * cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
			 * }else {
			 * 
			 * cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT); } }else{
			 * 
			 * cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT); }
			 * 
			 * cellSettlement.setPhrase(new
			 * Phrase("By "+String.valueOf(PaymentMode.get(trans.getPayment_mode())),
			 * font)); tableSettlement.addCell(cellSettlement);
			 * cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			 * cellSettlement.setPhrase(new
			 * Phrase(String.valueOf(formatter.format(trans.getAmount())), font));
			 * tableSettlement.addCell(cellSettlement);
			 * 
			 * paidAmount += trans.getAmount(); }
			 * 
			 * cellSettlement.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM
			 * | Rectangle.TOP); cellSettlement.setPhrase(new Phrase("Paid Amount",
			 * fontAmt)); tableSettlement.addCell(cellSettlement);
			 * cellSettlement.setPhrase(new
			 * Phrase(String.valueOf(formatter.format(paidAmount +
			 * listInvoice.getDeposit())), fontAmt));
			 * tableSettlement.addCell(cellSettlement);
			 * 
			 * doc.add(table_summary); doc.add(tableSettlementHead);
			 * doc.add(tableSettlement);
			 * 
			 * PdfPTable tableBottom = new PdfPTable(1);
			 * tableBottom.setWidthPercentage(117.0f); PdfPCell cellBottomAmount = new
			 * PdfPCell(); cellBottomAmount.setBorder(Rectangle.NO_BORDER);
			 * 
			 * tableBottom.addCell(cellBottomAmount); PdfPCell cellBottom = new PdfPCell();
			 * if(sTax.getTotalServiceTax()!=0){
			 * 
			 * PdfPTable tableSTax = new PdfPTable(3);
			 * tableSTax.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tableSTax.setWidthPercentage(117.0f); PdfPCell celltxtSTax = new PdfPCell();
			 * celltxtSTax.setColspan(3); celltxtSTax.setBorder(Rectangle.NO_BORDER);
			 * celltxtSTax.setPhrase(new Phrase("Service Tax Details"));
			 * celltxtSTax.setPaddingBottom(10.0f); tableSTax.addCell(celltxtSTax); PdfPCell
			 * cellSTax = new PdfPCell(); cellSTax.setPhrase(new
			 * Phrase("Description",fonttxhdr)); tableSTax.addCell(cellSTax);
			 * cellSTax.setPhrase(new Phrase("Tax Rate",fonttxhdr));
			 * tableSTax.addCell(cellSTax); cellSTax.setPhrase(new
			 * Phrase("Amount",fonttxhdr)); tableSTax.addCell(cellSTax);
			 * tableSTax.addCell(new Phrase("Service Tax",fonttx)); Phrase stax=new
			 * Phrase(String.valueOf(sTax.getServiceTax()+" %"),fonttx); Phrase
			 * staxcess1=new Phrase(String.valueOf(sTax.getCess1_pc()+" %"),fonttx); Phrase
			 * staxcess2=new Phrase(String.valueOf(sTax.getCess2_pc()+" %"),fonttx); Phrase
			 * staxcess3=new Phrase(String.valueOf(sTax.getCess3_pc()+" %"),fonttx); Phrase
			 * staxcess4=new Phrase(String.valueOf(sTax.getCess4_pc()+" %"),fonttx); Phrase
			 * staxcess5=new Phrase(String.valueOf(sTax.getCess5_pc()+" %"),fonttx);
			 * if(sTax.getAbatement()!=0){
			 * 
			 * stax=new Phrase(String.valueOf(sTax.getAbatement()+" % on"+
			 * sTax.getServiceTax()+" %"),fonttx); staxcess1=new
			 * Phrase(String.valueOf(sTax.getAbatement()+" % on"+
			 * sTax.getCess1_pc()+" %"),fonttx); staxcess2=new
			 * Phrase(String.valueOf(sTax.getAbatement()+" % on"+
			 * sTax.getCess2_pc()+" %"),fonttx); staxcess3=new
			 * Phrase(String.valueOf(sTax.getAbatement()+" % on"+
			 * sTax.getCess3_pc()+" %"),fonttx); staxcess4=new
			 * Phrase(String.valueOf(sTax.getAbatement()+" % on"+
			 * sTax.getCess4_pc()+" %"),fonttx); staxcess5=new
			 * Phrase(String.valueOf(sTax.getAbatement()+" % on"+
			 * sTax.getCess5_pc()+" %"),fonttx);
			 * 
			 * }
			 * 
			 * tableSTax.addCell(stax); tableSTax.addCell(new
			 * Phrase(formatter.format(Rounding.nRound(sTax.getServiceTax_amt())),fonttx));
			 * if(sTax.getCess1_pc()!=0){
			 * 
			 * tableSTax.addCell(new Phrase(sTax.getCess1Name(),fonttx));
			 * tableSTax.addCell(staxcess1); tableSTax.addCell(new
			 * Phrase(formatter.format(Rounding.nRound(sTax.getCess1_amt())),fonttx));
			 * 
			 * } if(sTax.getCess2_pc()!=0){
			 * 
			 * tableSTax.addCell(new Phrase(sTax.getCess2Name(),fonttx));
			 * tableSTax.addCell(staxcess2); tableSTax.addCell(new
			 * Phrase(formatter.format(Rounding.nRound(sTax.getCess2_amt())),fonttx));
			 * 
			 * } if(sTax.getCess3_pc()!=0){
			 * 
			 * tableSTax.addCell(new Phrase(sTax.getCess3Name(),fonttx));
			 * tableSTax.addCell(staxcess3); tableSTax.addCell(new
			 * Phrase(formatter.format(Rounding.nRound(sTax.getCess3_amt())),fonttx));
			 * 
			 * } if(sTax.getCess4_pc()!=0){
			 * 
			 * tableSTax.addCell(new Phrase(sTax.getCess4Name(),fonttx));
			 * tableSTax.addCell(staxcess4); tableSTax.addCell(new
			 * Phrase(formatter.format(Rounding.nRound(sTax.getCess4_amt())),fonttx));
			 * 
			 * } if(sTax.getCess5_pc()!=0){
			 * 
			 * tableSTax.addCell(new Phrase(sTax.getCess5Name(),fonttx));
			 * tableSTax.addCell(staxcess5); tableSTax.addCell(new
			 * Phrase(formatter.format(Rounding.nRound(sTax.getCess5_amt())),fonttx));
			 * 
			 * } cellBottom.addElement(tableSTax); }
			 * 
			 * 
			 * if(invTmp.isTaxsumryincl()){ if(listInvoice.getTax1()!=0 ||
			 * listInvoice.getTax2()!=0 || listInvoice.getTax3()!=0 ||
			 * listInvoice.getTax4()!=0){ int columnCount=0; if(listInvoice.getTax1()!=0){
			 * columnCount++; } if(listInvoice.getTax2()!=0){ columnCount++; }
			 * if(listInvoice.getTax3()!=0){ columnCount++; } if(listInvoice.getTax4()!=0){
			 * columnCount++; } PdfPTable table2 = new PdfPTable(3+columnCount);
			 * table2.setWidthPercentage(117.0f); PdfPCell celltxt = new PdfPCell();
			 * celltxt.setColspan(3+columnCount);
			 * celltxt.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.TOP);
			 * celltxt.setPhrase(new Phrase("Tax Summary",fontAmt));
			 * celltxt.setPaddingBottom(10.0f); table2.addCell(celltxt); PdfPCell cell3 =
			 * new PdfPCell(); cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell3.setPhrase(new Phrase("Rate %",font)); table2.addCell(cell3);
			 * cell3.setPhrase(new Phrase("Amount",font)); table2.addCell(cell3);
			 * if(listInvoice.getTax1()!=0){ cell3.setPhrase(new
			 * Phrase(listInvoice.getTax1Code(),font)); table2.addCell(cell3); }
			 * if(listInvoice.getTax2()!=0){ cell3.setPhrase(new
			 * Phrase(listInvoice.getTax2Code(),font)); table2.addCell(cell3); }
			 * if(listInvoice.getTax3()!=0){ cell3.setPhrase(new
			 * Phrase(listInvoice.getTax3Code(),font)); table2.addCell(cell3); }
			 * if(listInvoice.getTax4()!=0){ cell3.setPhrase(new
			 * Phrase(listInvoice.getTax4Code(),font)); table2.addCell(cell3); }
			 * cell3.setPhrase(new Phrase("Tax Amount",font)); table2.addCell(cell3);
			 * Map<Integer,Object> taxMap = listInvoice.getTaxList(); for
			 * (Map.Entry<Integer, Object> entry : taxMap.entrySet()) { TaxSummary tax=
			 * (TaxSummary) entry.getValue(); PdfPCell celldes=new PdfPCell();
			 * 
			 * celldes.setHorizontalAlignment(Element.ALIGN_RIGHT); celldes.setPhrase(new
			 * Phrase(formatter.format(tax.getTaxPc()),fonttx)); table2.addCell(celldes);
			 * celldes.setPhrase(new Phrase(formatter.format(tax.getAmount()),fonttx));
			 * table2.addCell(celldes); if(listInvoice.getTax1()!=0){ celldes.setPhrase(new
			 * Phrase(formatter.format(tax.getTax1Amount()),fonttx));
			 * table2.addCell(celldes); } if(listInvoice.getTax2()!=0){
			 * celldes.setPhrase(new Phrase(formatter.format(tax.getTax2Amount()),fonttx));
			 * table2.addCell(celldes); } if(listInvoice.getTax3()!=0){
			 * celldes.setPhrase(new Phrase(formatter.format(tax.getTax3Amount()),fonttx));
			 * table2.addCell(celldes); } if(listInvoice.getTax4()!=0){
			 * celldes.setPhrase(new Phrase(formatter.format(tax.getTax4Amount()),fonttx));
			 * table2.addCell(celldes); } celldes.setPhrase(new
			 * Phrase(formatter.format(tax.getTaxAmount()),fonttx));
			 * table2.addCell(celldes); } table2.setKeepTogether(true);
			 * cellBottom.setBorder(Rectangle.NO_BORDER); cellBottom.addElement(table2); } }
			 * 
			 * if(isComplementary) { PdfPTable tblComplementary=new PdfPTable(1);
			 * tblComplementary.setWidthPercentage(117.0f);
			 * tblComplementary.setSpacingBefore(4f); PdfPCell cellComplementary=new
			 * PdfPCell(); cellComplementary.setColspan(1);
			 * cellComplementary.setBorder(Rectangle.NO_BORDER);
			 * cellComplementary.setPhrase(new
			 * Phrase("*This is a complementary Invoice",fontAmt));
			 * tblComplementary.addCell(cellComplementary);
			 * cellBottom.addElement(tblComplementary); } tableBottom.addCell(cellBottom);
			 * 
			 * PdfPTable tblsign = new PdfPTable(2); tblsign.setSpacingBefore(30.0f);
			 * tblsign.setWidthPercentage(100.0f); InvoiceTemplate invTemp=
			 * listInvoice.getHeaderFooter(); if(invTemp.getSignLine1()!=null){ Paragraph
			 * psignaur=new Paragraph(invTemp.getSignLine1());
			 * psignaur.setAlignment(Element.ALIGN_LEFT); PdfPCell cellsign = new
			 * PdfPCell(); cellsign.setBorder(Rectangle.NO_BORDER);
			 * cellsign.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * cellsign.addElement(psignaur); tblsign.addCell(cellsign); }
			 * if(invTemp.getSignLine2()!=null){ Paragraph pSignature=new
			 * Paragraph(invTemp.getSignLine2());
			 * pSignature.setAlignment(Element.ALIGN_RIGHT); PdfPCell cellsign2 = new
			 * PdfPCell(); cellsign2.setBorder(Rectangle.NO_BORDER);
			 * cellsign2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * cellsign2.addElement(pSignature);
			 * 
			 * tblsign.addCell(cellsign2); }
			 * 
			 * doc.add(tblsign);
			 * 
			 * @SuppressWarnings("unchecked") List<Transaction> list = (List<Transaction>)
			 * listInvoice.getPosDetails();
			 * 
			 * if(list.size() !=0) {
			 * 
			 * doc.newPage();
			 * 
			 * PdfPTable tableFoodExpns = new PdfPTable(3);
			 * tableFoodExpns.setWidthPercentage(100.0f); tableFoodExpns.setWidths(new
			 * float[] {1.5f,2f,1f});
			 * 
			 * PdfPCell foodExpenseCell = new PdfPCell(); foodExpenseCell.setPadding(8);
			 * foodExpenseCell.setBorder(Rectangle.NO_BORDER);
			 * foodExpenseCell.setColspan(3);
			 * foodExpenseCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * foodExpenseCell.setPhrase(new Phrase("Food Expense", fontFoodExpnseHead));
			 * tableFoodExpns.addCell(foodExpenseCell); foodExpenseCell.setColspan(1);
			 * foodExpenseCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP |
			 * Rectangle.BOTTOM);
			 * foodExpenseCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * foodExpenseCell.setPhrase(new Phrase("Date", font));
			 * tableFoodExpns.addCell(foodExpenseCell);
			 * foodExpenseCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * foodExpenseCell.setPhrase(new Phrase("Invoice No", font));
			 * tableFoodExpns.addCell(foodExpenseCell);
			 * foodExpenseCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * foodExpenseCell.setPhrase(new Phrase("Amount",font));
			 * tableFoodExpns.addCell(foodExpenseCell);
			 * 
			 * Double foodExpnsTotal = 0.00;
			 * 
			 * for(int i=0; i < list.size(); i++) {
			 * 
			 * foodExpenseCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			 * foodExpenseCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * foodExpenseCell.setPhrase(new
			 * Phrase(String.valueOf(list.get(i).getTxn_date()), font));
			 * tableFoodExpns.addCell(foodExpenseCell);
			 * foodExpenseCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * foodExpenseCell.setPhrase(new
			 * Phrase(String.valueOf(list.get(i).getReceived_from()), font));
			 * tableFoodExpns.addCell(foodExpenseCell);
			 * foodExpenseCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * foodExpenseCell.setPhrase(new
			 * Phrase(String.valueOf(numberFormat.format(list.get(i).getBase_amount())),
			 * font)); tableFoodExpns.addCell(foodExpenseCell);
			 * 
			 * foodExpnsTotal += list.get(i).getBase_amount();
			 * 
			 * }
			 * 
			 * PdfPCell cellTotal = new PdfPCell(); cellTotal.setColspan(2);
			 * cellTotal.setPhrase(new Phrase("Total",font));
			 * cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * tableFoodExpns.addCell(cellTotal);
			 * 
			 * cellTotal.setColspan(1); cellTotal.setPhrase(new
			 * Phrase(numberFormat.format(foodExpnsTotal),fontFoodExpnse));
			 * cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * tableFoodExpns.addCell(cellTotal);
			 * 
			 * doc.add(tableFoodExpns);
			 * 
			 * if(listInvoice.getBillDetails() == 1) {
			 * 
			 * Map<String, Object> orderData = (Map<String, Object>)
			 * listInvoice.getRefundDetails();
			 * 
			 * if(orderData.size() !=0) { Object billDetails =
			 * orderData.get("bill_details"); JsonParser parser = new JsonParser();
			 * JsonObject orderHdrsArrayObj =
			 * parser.parse(billDetails.toString()).getAsJsonObject(); JsonArray
			 * orderHdrsArray = orderHdrsArrayObj.get("order_hdrs").getAsJsonArray();
			 * JsonObject orderHdrsObject = orderHdrsArray.get(0).getAsJsonObject();
			 * JsonArray orderDtlArray = orderHdrsObject.get("order_dtls").getAsJsonArray();
			 * 
			 * PdfPTable tabletop = new PdfPTable(2); tabletop.setWidths(new float[] { 2.0f,
			 * 3.0f }); tabletop.setPaddingTop(30.0f); tabletop.setSpacingBefore(3f);
			 * tabletop.getDefaultCell().setPaddingBottom(30f);
			 * 
			 * PdfPHeaderCell cellspan = new PdfPHeaderCell();
			 * cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			 * cellspan.setPadding(6); cellspan.setBorder(Rectangle.NO_BORDER);
			 * cellspan.setPhrase(new Phrase("RESTUARANT", font1));
			 * cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tabletop.addCell(cellspan);
			 * 
			 * PdfPHeaderCell cellspan1 = new PdfPHeaderCell();
			 * cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
			 * cellspan1.setPadding(6); cellspan1.setBorder(Rectangle.NO_BORDER);
			 * cellspan1.setPhrase(new Phrase("BILL", font1));
			 * cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tabletop.addCell(cellspan1);
			 * 
			 * doc.add(tabletop);
			 * 
			 * PdfPTable tableHdr1 = new PdfPTable(2); tableHdr1.setPaddingTop(30.0f);
			 * tableHdr1.setWidths(new float[] { 2.0f, 2.0f });
			 * 
			 * PdfPCell cellPos = new PdfPCell(); cellPos.setBackgroundColor(new
			 * BaseColor(255, 255, 255)); cellPos.setPadding(3);
			 * 
			 * cellPos.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP |
			 * Rectangle.BOTTOM);
			 * 
			 * String[] orderTime =
			 * (orderHdrsObject.get("order_time").toString()).split(" ");
			 * cellPos.setPhrase(new Phrase( "Order Date: " +
			 * ((orderHdrsObject.get("order_date")) + orderTime[1]).replace('"', ' '),
			 * font3)); cellPos.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tableHdr1.addCell(cellPos);
			 * 
			 * cellPos.setHorizontalAlignment(Element.ALIGN_LEFT); cellPos.setPhrase( new
			 * Phrase("Order Id: " +
			 * (orderHdrsObject.get("order_id").toString()).replace('"', ' '), font3));
			 * tableHdr1.addCell(cellPos);
			 * 
			 * doc.add(tableHdr1);
			 * 
			 * PdfPTable tableHdr2 = new PdfPTable(3); tableHdr2.setWidths(new float[] {
			 * 2.0f, 1.0f, 1.0f });
			 * 
			 * PdfPCell cellHdr2 = new PdfPCell(); cellHdr2.setBackgroundColor(new
			 * BaseColor(255, 255, 255)); cellHdr2.setPadding(3);
			 * tableHdr2.getDefaultCell().setPaddingBottom(10.0f);
			 * tableHdr2.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255,
			 * 255)); tableHdr2.getDefaultCell().setBorder(Rectangle.BOTTOM);
			 * tableHdr2.getDefaultCell().setBorderWidthBottom(2.0f);
			 * 
			 * cellHdr2.setPhrase(new Phrase( "Bill No: " +
			 * (orderHdrsObject.get("invoice_no").toString()).replace('"', ' '), font3));
			 * cellHdr2.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tableHdr2.addCell(cellHdr2);
			 * 
			 * cellHdr2.setPhrase( new Phrase("Room No: " +
			 * (orderHdrsArrayObj.get("room_no").toString()).replace('"', ' '), font3));
			 * tableHdr2.addCell(cellHdr2);
			 * 
			 * cellHdr2.setPhrase( new Phrase("Shop Code: " +
			 * (orderHdrsObject.get("shop_code").toString()).replace('"', ' '), font2));
			 * tableHdr2.addCell(cellHdr2);
			 * 
			 * doc.add(tableHdr2);
			 * 
			 * PdfPTable tablePos = new PdfPTable(5); tablePos.setWidths(new float[] { .8f,
			 * 1.0f, 2.5f, 1.5f, 1.5f });
			 * 
			 * PdfPCell cellPosTblHead = new PdfPCell();
			 * cellPosTblHead.setBackgroundColor(new BaseColor(255, 255, 255));
			 * cellPosTblHead.setPadding(5);
			 * tablePos.getDefaultCell().setPaddingBottom(10.0f);
			 * tablePos.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			 * tablePos.getDefaultCell().setBorder(Rectangle.BOTTOM);
			 * tablePos.getDefaultCell().setBorderWidthBottom(2.0f);
			 * cellPosTblHead.setPhrase(new Phrase("Si#", font));
			 * cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tablePos.addCell(cellPosTblHead); cellPosTblHead.setPhrase(new Phrase("Qty",
			 * font)); cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tablePos.addCell(cellPosTblHead); cellPosTblHead.setPhrase(new Phrase("Item",
			 * font)); cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tablePos.addCell(cellPosTblHead); cellPosTblHead.setPhrase(new Phrase("Rate",
			 * font)); cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tablePos.addCell(cellPosTblHead); cellPosTblHead.setPhrase(new
			 * Phrase("Total", font));
			 * cellPosTblHead.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tablePos.addCell(cellPosTblHead);
			 * 
			 * int count = 0; for (int i = 0; i < orderDtlArray.size(); i++) { count++;
			 * String sino = "" + count;
			 * 
			 * JsonObject obj = (JsonObject) orderDtlArray.get(i);
			 * 
			 * PdfPCell cell3 = new PdfPCell(); cell3.setBackgroundColor(new BaseColor(255,
			 * 255, 255)); cell3.setPadding(5);
			 * tablePos.getDefaultCell().setPaddingBottom(10.0f);
			 * tablePos.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			 * cell3.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			 * tablePos.getDefaultCell().setBorderWidthBottom(2.0f);
			 * 
			 * cell3.setPhrase(new Phrase(sino, font2));
			 * cell3.setHorizontalAlignment(Element.ALIGN_CENTER); tablePos.addCell(cell3);
			 * 
			 * cell3.setPhrase(new Phrase(numberFormat.format(obj.get("qty").getAsDouble()),
			 * font2)); cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tablePos.addCell(cell3);
			 * 
			 * cell3.setPhrase(new Phrase(obj.get("name").getAsString(), font2));
			 * cell3.setHorizontalAlignment(Element.ALIGN_LEFT); tablePos.addCell(cell3);
			 * 
			 * cell3.setPhrase(new
			 * Phrase(numberFormat.format(obj.get("fixed_price").getAsDouble()), font2));
			 * cell3.setHorizontalAlignment(Element.ALIGN_RIGHT); tablePos.addCell(cell3);
			 * 
			 * cell3.setPhrase(new
			 * Phrase(numberFormat.format(obj.get("item_total").getAsDouble()), font2));
			 * cell3.setHorizontalAlignment(Element.ALIGN_RIGHT); tablePos.addCell(cell3);
			 * 
			 * }
			 * 
			 * doc.add(tablePos);
			 * 
			 * PdfPTable totalTable = new PdfPTable(2); totalTable.setWidths(new float[] {
			 * 3.87f, 1f });
			 * 
			 * PdfPCell cellPosTotal = new PdfPCell(); cellPosTotal.setBackgroundColor(new
			 * BaseColor(255, 255, 255));
			 * cellPosTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * cellPosTotal.setPadding(5);
			 * 
			 * cellPosTotal.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
			 * cellPosTotal.setPhrase(new Phrase("Sub Total ", font));
			 * totalTable.addCell(cellPosTotal);
			 * 
			 * cellPosTotal.setPhrase( new
			 * Phrase(numberFormat.format(orderHdrsObject.get("detail_total").getAsDouble())
			 * , font2)); totalTable.addCell(cellPosTotal);
			 * 
			 * cellPosTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			 * 
			 * cellPosTotal.setPhrase(new Phrase("Discount Amount ", font));
			 * totalTable.addCell(cellPosTotal);
			 * 
			 * cellPosTotal.setPhrase( new
			 * Phrase(numberFormat.format(orderHdrsObject.get("bill_discount_amount").
			 * getAsDouble()), font2)); totalTable.addCell(cellPosTotal);
			 * 
			 * List<Object> refundHeaderList = new ArrayList<>(); refundHeaderList =
			 * (List<Object>) orderData.get("refund_details");
			 * 
			 * double totalrefundamount = 0.0; if (!refundHeaderList.isEmpty()) { Object
			 * refundDetailObject = null; for (int i = 0; i < refundHeaderList.size(); i++)
			 * { refundDetailObject = refundHeaderList.get(i); JsonObject
			 * refundDetailJsonObject =
			 * parser.parse(refundDetailObject.toString()).getAsJsonObject(); JsonArray
			 * refundHdrsJsonArray =
			 * refundDetailJsonObject.get("order_hdrs").getAsJsonArray(); JsonObject
			 * refundHdrsJsonObject = refundHdrsJsonArray.get(0).getAsJsonObject();
			 * JsonArray refundDetailJsonArray =
			 * refundHdrsJsonObject.get("order_refund").getAsJsonArray();
			 * 
			 * for (int j = 0; j < refundDetailJsonArray.size(); j++) { JsonObject obj =
			 * (JsonObject) refundDetailJsonArray.get(j); totalrefundamount +=
			 * obj.get("refund_amount").getAsDouble(); } }
			 * 
			 * cellPosTotal.setPhrase(new Phrase("Refund ", font));
			 * totalTable.addCell(cellPosTotal);
			 * 
			 * cellPosTotal.setPhrase(new Phrase(numberFormat.format(totalrefundamount),
			 * font2)); totalTable.addCell(cellPosTotal); }
			 * cellPosTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
			 * cellPosTotal.setPhrase(new Phrase("Net Amount ", font));
			 * cellPosTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * totalTable.addCell(cellPosTotal);
			 * 
			 * double totalAmount = 0.0; double billDiscount = 0.0; double finalRoundAmount
			 * = 0.0; double net = 0.0;
			 * 
			 * totalAmount = orderHdrsObject.get("total_amount").getAsDouble(); billDiscount
			 * = orderHdrsObject.get("bill_discount_amount").getAsDouble(); finalRoundAmount
			 * = orderHdrsObject.get("final_round_amount").getAsDouble();
			 * 
			 * net = totalAmount - billDiscount + finalRoundAmount; cellPosTotal.setPhrase(
			 * new Phrase(numberFormat.format(net-totalrefundamount), font2));
			 * cellPosTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * totalTable.addCell(cellPosTotal);
			 * 
			 * cellPosTotal.setBorder(Rectangle.NO_BORDER); cellPosTotal.setPhrase(new
			 * Phrase("* Includes tax of Rs.", font2)); totalTable.addCell(cellPosTotal);
			 * 
			 * Double taxTotal = orderHdrsObject.get("total_tax1").getAsDouble() +
			 * orderHdrsObject.get("total_tax2").getAsDouble() +
			 * orderHdrsObject.get("total_tax3").getAsDouble() +
			 * orderHdrsObject.get("total_gst").getAsDouble() +
			 * orderHdrsObject.get("total_sc").getAsDouble();
			 * 
			 * cellPosTotal.setPhrase(new Phrase(numberFormat.format(taxTotal), font2));
			 * cellPosTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * totalTable.addCell(cellPosTotal);
			 * 
			 * //
			 * 
			 * doc.add(totalTable);
			 * 
			 * double totalRefundTaxAmount = 0.0;
			 * 
			 * if (!refundHeaderList.isEmpty()) { Object refundDetailObject = null;
			 * 
			 * PdfPTable tableRefundHead = new PdfPTable(1); tableRefundHead.setWidths(new
			 * float[] { 3.0f }); tableRefundHead.setPaddingTop(30.0f);
			 * tableRefundHead.setSpacingBefore(10f);
			 * tableRefundHead.getDefaultCell().setPaddingBottom(30f);
			 * 
			 * PdfPHeaderCell cellspanRefundHead = new PdfPHeaderCell();
			 * cellspanRefundHead.setBackgroundColor(new BaseColor(255, 255, 255));
			 * cellspanRefundHead.setPadding(6);
			 * cellspanRefundHead.setBorder(Rectangle.NO_BORDER);
			 * cellspanRefundHead.setPhrase(new Phrase("REFUND", font1));
			 * cellspanRefundHead.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tableRefundHead.addCell(cellspanRefundHead);
			 * 
			 * doc.add(tableRefundHead);
			 * 
			 * for (int i = 0; i < refundHeaderList.size(); i++) { refundDetailObject =
			 * refundHeaderList.get(i); JsonObject refundDetailJsonObject =
			 * parser.parse(refundDetailObject.toString()).getAsJsonObject(); JsonArray
			 * refundHdrsJsonArray =
			 * refundDetailJsonObject.get("order_hdrs").getAsJsonArray(); JsonObject
			 * refundHdrsJsonObject = refundHdrsJsonArray.get(0).getAsJsonObject();
			 * JsonArray refundDetailJsonArray =
			 * refundHdrsJsonObject.get("order_refund").getAsJsonArray();
			 * 
			 * PdfPTable tableRefundHdr = new PdfPTable(2);
			 * tableRefundHdr.setPaddingTop(30.0f); tableRefundHdr.setWidths(new float[] {
			 * 2.0f, 2.0f });
			 * 
			 * PdfPCell cellRefundHdr = new PdfPCell(); cellRefundHdr.setBackgroundColor(new
			 * BaseColor(255, 255, 255)); cellRefundHdr.setPadding(3);
			 * tableRefundHdr.getDefaultCell().setPaddingBottom(10.0f);
			 * tableRefundHdr.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255,
			 * 255)); tableRefundHdr.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			 * tableRefundHdr.getDefaultCell().setBorderWidthBottom(2.0f);
			 * 
			 * cellRefundHdr.setPhrase(new Phrase( "Refund Date: " +
			 * (refundHdrsJsonObject.get("refund_date").toString()).replace('"', ' '),
			 * font2)); cellRefundHdr.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tableRefundHdr.addCell(cellRefundHdr);
			 * 
			 * String[] orderRefundTime =
			 * (refundHdrsJsonObject.get("refund_time").toString()).split(" ");
			 * cellRefundHdr.setPhrase( new Phrase("Refund Time: " +
			 * (orderRefundTime[1].toString()).replace('"', ' '), font2));
			 * cellRefundHdr.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tableRefundHdr.addCell(cellRefundHdr);
			 * 
			 * doc.add(tableRefundHdr);
			 * 
			 * PdfPTable tableRefund = new PdfPTable(4); tableRefund.setWidths(new float[] {
			 * .8f, 1.0f, 3.5f, 1.5f, }); tableRefund.setKeepTogether(true);
			 * tableRefund.setSpacingAfter(10f);
			 * 
			 * PdfPCell cellRefund = new PdfPCell(); cellRefund.setBackgroundColor(new
			 * BaseColor(255, 255, 255)); cellRefund.setPadding(5);
			 * tableRefund.getDefaultCell().setPaddingBottom(10.0f);
			 * tableRefund.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255,
			 * 255)); cellRefund.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP
			 * | Rectangle.BOTTOM); tableRefund.getDefaultCell().setBorderWidthBottom(2.0f);
			 * cellRefund.setPhrase(new Phrase("Si#", font));
			 * cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tableRefund.addCell(cellRefund); cellRefund.setPhrase(new Phrase("Qty",
			 * font)); cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tableRefund.addCell(cellRefund); cellRefund.setPhrase(new Phrase("Item",
			 * font)); cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tableRefund.addCell(cellRefund); cellRefund.setPhrase(new Phrase("Total",
			 * font)); cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tableRefund.addCell(cellRefund);
			 * 
			 * int number = 0; double refundItemTotal = 0.0; for (int j = 0; j <
			 * refundDetailJsonArray.size(); j++) {
			 * 
			 * number++; String sino = "" + number;
			 * 
			 * JsonObject obj = (JsonObject) refundDetailJsonArray.get(j);
			 * 
			 * PdfPCell cellRefundDtl = new PdfPCell(); cellRefundDtl.setBackgroundColor(new
			 * BaseColor(255, 255, 255)); cellRefundDtl.setPadding(5);
			 * 
			 * cellRefundDtl.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			 * 
			 * cellRefundDtl.setPhrase(new Phrase(sino, font2));
			 * cellRefundDtl.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tableRefund.addCell(cellRefundDtl);
			 * 
			 * cellRefundDtl.setPhrase(new
			 * Phrase(numberFormat.format(obj.get("qty").getAsDouble()), font2));
			 * cellRefundDtl.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tableRefund.addCell(cellRefundDtl);
			 * 
			 * cellRefundDtl.setPhrase(new Phrase(obj.get("name").getAsString(), font2));
			 * cellRefundDtl.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tableRefund.addCell(cellRefundDtl);
			 * 
			 * cellRefundDtl.setPhrase( new
			 * Phrase(numberFormat.format(obj.get("refund_amount").getAsDouble()), font2));
			 * cellRefundDtl.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * tableRefund.addCell(cellRefundDtl);
			 * 
			 * refundItemTotal += obj.get("refund_amount").getAsDouble();
			 * totalRefundTaxAmount += obj.get("tax1_amount").getAsDouble() +
			 * obj.get("tax2_amount").getAsDouble() + obj.get("tax3_amount").getAsDouble();
			 * }
			 * 
			 * PdfPCell refundTotalCell = new PdfPCell();
			 * refundTotalCell.setBackgroundColor(new BaseColor(255, 255, 255));
			 * refundTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * refundTotalCell.setPadding(5);
			 * 
			 * refundTotalCell.setColspan(3); refundTotalCell.setPhrase(new
			 * Phrase("Refund Total", font)); tableRefund.addCell(refundTotalCell);
			 * 
			 * refundTotalCell.setColspan(1); refundTotalCell.setPhrase(new
			 * Phrase(numberFormat.format(refundItemTotal), font2));
			 * tableRefund.addCell(refundTotalCell);
			 * 
			 * refundTotalCell.setColspan(3);
			 * refundTotalCell.setBorder(Rectangle.NO_BORDER); refundTotalCell.setPhrase(new
			 * Phrase("* Refund ncludes tax of Rs.", font2));
			 * tableRefund.addCell(refundTotalCell);
			 * 
			 * refundTotalCell.setColspan(1); refundTotalCell.setPhrase(new
			 * Phrase(numberFormat.format(totalRefundTaxAmount), font2));
			 * refundTotalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tableRefund.addCell(refundTotalCell);
			 * 
			 * doc.add(tableRefund); } } } } } }
			 */

		}
	}
	
	
	
}