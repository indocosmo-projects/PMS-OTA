package com.indocosmo.pms.web.reports.report_designs;

import java.time.LocalDate;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.indocosmo.pms.util.MoneyToWordsConvertor;
import com.indocosmo.pms.web.common.AbstractITextPdfView;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreditorsReceiptReport extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		JsonObject jobj = (JsonObject) model.get("receiptData");

		Font font = FontFactory.getFont(FontFactory.HELVETICA, 9);
		
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 10f });
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell("RECEIPT");
		document.add(table);

		PdfPTable contentTable = new PdfPTable(2);
		contentTable.setWidthPercentage(100f);
		contentTable.setWidths(new float[] { 5f, 3f });
		contentTable.setSpacingBefore(30f);

		PdfPCell contentCell = new PdfPCell();
		contentCell.setBorder(Rectangle.NO_BORDER);
		contentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		contentCell.setPhrase(new Phrase("Received From: "+jobj.get("corporate_name").getAsString(), font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("Date: "+jobj.get("payment_date").getAsString(), font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("Voucher: "+jobj.get("voucher_no").getAsString(), font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("Amount: "+jobj.get("payment_amount").getAsString(), font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("In words: "+ MoneyToWordsConvertor.toWords(jobj.get("payment_amount").getAsDouble()), font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase(""));
		contentTable.addCell(contentCell);
		
		document.add(contentTable);

		PdfPTable bottomtable = new PdfPTable(2);
		bottomtable.setWidthPercentage(100.0f);
		bottomtable.setWidths(new float[] { 2.0f, 2.0f });
		bottomtable.setTotalWidth(document.right(document.rightMargin()) - document.left(document.leftMargin()));
		contentCell.setColspan(1);
		contentCell.setPhrase(new Phrase(
				"Print Date: " + LocalDate.now().toString(), font));
		bottomtable.addCell(contentCell);
		contentCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		contentCell.setPhrase(new Phrase("Signatory:..............................", font));
		bottomtable.addCell(contentCell);
		bottomtable.writeSelectedRows(0, -1, document.left(document.leftMargin()),
				bottomtable.getTotalHeight() + document.bottom(document.bottomMargin()), writer.getDirectContent());
	}

}
