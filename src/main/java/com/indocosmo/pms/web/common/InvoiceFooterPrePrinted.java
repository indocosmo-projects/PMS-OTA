package com.indocosmo.pms.web.common;

import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class InvoiceFooterPrePrinted extends PdfPageEventHelper {

	InvoiceTemplate hdft;
	PdfTemplate total;

	public InvoiceFooterPrePrinted(InvoiceTemplate hf) {
		hdft = hf;

	}

	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		total = writer.getDirectContent().createTemplate(30, 16);
	}

	public void onEndPage(PdfWriter writer, Document document) {

		final Font pageNumberFont = FontFactory.getFont(FontFactory.HELVETICA, 5);

		//Rectangle rect = document.getPageSize();
		PdfPTable footer = new PdfPTable(1);
		footer.setTotalWidth(535);
		footer.setLockedWidth(true);
		footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		footer.getDefaultCell().setPaddingBottom(5.0f);

		footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		footer.addCell(String.format("Page %d", writer.getPageNumber(), pageNumberFont));

		footer.writeSelectedRows(0, -1, document.rightMargin() - 10, 20, writer.getDirectContent());
	}

	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {

		ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber())), 2, 2,
				0);
	}

}