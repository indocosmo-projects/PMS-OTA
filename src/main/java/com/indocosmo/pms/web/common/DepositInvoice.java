package com.indocosmo.pms.web.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.enumerator.PaymentMode;
import com.indocosmo.pms.util.MoneyToWordsConvertor;
import com.indocosmo.pms.web.checkOut.model.Invoice;
import com.indocosmo.pms.web.checkOut.model.TaxSummary;
import com.indocosmo.pms.web.common.setttings.Rounding;
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
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DepositInvoice extends AbstractITextPdfView {

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
		//Transaction txn = (Transaction) model.get("deposit");
		NumberFormat formatter = new DecimalFormat("#,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Invoice listInvoice = (Invoice) model.get("listInvoice");

		InvoiceTemplate invTmp = listInvoice.getHeaderFooter();
		SystemSettings settingsSystem = listInvoice.getSystemseting();

		String dateform = settingsSystem.getDateFormat();
		DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateform);
		SimpleDateFormat tf = new SimpleDateFormat(settingsSystem.getTimeFormat());
		List<Transaction> txnList = (List<Transaction>) listInvoice.getTxnList();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
		font.setColor(BaseColor.BLACK);
		Font fontchk = FontFactory.getFont(FontFactory.HELVETICA, 9);
		fontchk.setColor(BaseColor.BLACK);
		Font fonttx = FontFactory.getFont(FontFactory.HELVETICA, 9);
		Font fonttxhdr = FontFactory.getFont(FontFactory.HELVETICA, 11);
		Font fontAmt = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
		Font fontFoodExpnse = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
		Font fontFoodExpnseHead = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);

		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
		font1.setColor(BaseColor.BLACK);
		
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
		
		if (txnList.size() >0) {
			
			for (Transaction txn : txnList) {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] { 2.0f, 2.0f });
			table.setSpacingBefore(10);
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			cell.setBorder(Rectangle.NO_BORDER);
			
			cell.setColspan(1);
			cell.setPaddingLeft(0);
			cell.setRightIndent(0);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setPhrase(new Phrase("Receipt No           : " + txn.getReceipt_no(), font1));
			table.addCell(cell);
			
			cell.setColspan(1);
			cell.setPaddingLeft(0);
			cell.setRightIndent(0);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			java.util.Date rDate = sdf.parse(txn.getTxn_date());
			java.sql.Date recpDate = new Date(rDate.getTime());
			cell.setPhrase(new Phrase("Receipt Date: " + simpleDateFormatHotelDate.format(recpDate), font));
			table.addCell(cell);
			
			
			
			cell.setColspan(2);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setRightIndent(27);
			cell.setPaddingLeft(0);
			cell.setPhrase(new Phrase("Receipt Time: "
					+ tf.format(new SimpleDateFormat("yy-MM-dd hh:mm:ss").parse(txn.getTxn_time())), font));
			table.addCell(cell);
			
			cell.setRightIndent(0);
			cell.setPaddingLeft(0);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setColspan(2);
			cell.setPhrase(new Phrase("Name of Guest     : " + txn.getFirstName() + " " + txn.getLastName(), font));
			table.addCell(cell);
			
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setColspan(1);
			cell.setRightIndent(0);
			cell.setPaddingLeft(0);
			
			if (txn.getRoomNumber() != null && txn.getRoomNumber() != "") {
				cell.setPhrase(new Phrase("Room No              : " + txn.getRoomNumber(), font));
			} else {
				cell.setPhrase(new Phrase("Room No :      Not Assigned", font));
			}
			table.addCell(cell);

			cell.setColspan(1);
			cell.setRightIndent(0);
			cell.setPaddingLeft(40);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setPhrase(new Phrase("Room Type          : " + txn.getRoom_type(), font));
			table.addCell(cell);
			
			cell.setColspan(2);
			cell.setPaddingLeft(0);
			cell.setRightIndent(0);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setPhrase(new Phrase("Description           : Room Advance", font));
			table.addCell(cell);
			
			cell.setColspan(1);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setPhrase(new Phrase("Amount Received : Rs." + formatter.format(txn.getNett_amount()), font));
			table.addCell(cell);
			
			cell.setColspan(1);
			cell.setPaddingLeft(40);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			if (txn.getPayment_mode() != 0) {
				cell.setPhrase(new Phrase(
						"Mode of Payment : " + PaymentMode.get(txn.getPayment_mode()).getPaymentMode(), font));
			}
			table.addCell(cell);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setColspan(2);
			cell.setPaddingLeft(0);
			cell.setPhrase(new Phrase(
					"In Words               : " + MoneyToWordsConvertor.toWords(String.valueOf((txn.getNett_amount()))),
					font));
			table.addCell(cell);
			
			
			cell.setColspan(2);
			cell.setPhrase(new Phrase(
					"Prepared by          : " + ((User) request.getSession().getAttribute("userForm")).getName(), font));
			table.addCell(cell);
			
			cell.setColspan(2);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setPhrase(new Phrase("Signature : ......................"));
			table.addCell(cell);
			
			
			
			doc.add(table);

			/*PdfPTable bottomtable = new PdfPTable(2);
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
					bottomtable.getTotalHeight() + doc.bottom(doc.bottomMargin()), writer.getDirectContent());*/
			}
		} 
	}
	
	
	
}