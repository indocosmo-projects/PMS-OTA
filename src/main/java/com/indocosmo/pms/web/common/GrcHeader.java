package com.indocosmo.pms.web.common;

import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class GrcHeader extends PdfPageEventHelper {

	InvoiceTemplate hdft;
	PdfTemplate total;

	public GrcHeader(InvoiceTemplate hf) {
		hdft = hf;

	}

	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		total = writer.getDirectContent().createTemplate(30, 16);
	}

	public void onStartPage(PdfWriter writer, Document document) {
		PdfPTable header = new PdfPTable(3);
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 8);
		try {
			header.setWidths(new float[] { 4.0f, 3.0f, 4.0f });
			header.setTotalWidth(525);
			header.setLockedWidth(true);
			header.getDefaultCell().setFixedHeight(60);
			header.getDefaultCell().setBorder(Rectangle.BOTTOM);
			header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
			Image logo = Image.getInstance(hdft.getHdrLogoUrl());
			header.addCell(logo);

			header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			header.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
			header.getDefaultCell().setPaddingBottom(5.0f);
			header.addCell(new Phrase(" "));

			header.getDefaultCell().setFixedHeight(65);
			header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			PdfPTable tblhdr = new PdfPTable(1);
			tblhdr.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tblhdr.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			if (hdft.getHdrLine1() != null) {
				tblhdr.addCell(new Phrase(hdft.getHdrLine1(), font));
			}
			if (hdft.getHdrLine2() != null) {
				tblhdr.addCell(new Phrase(hdft.getHdrLine2(), font2));
			}
			if (hdft.getHdrLine3() != null) {
				tblhdr.addCell(new Phrase(hdft.getHdrLine3(), font2));
			}
			if (hdft.getHdrLine4() != null) {
				tblhdr.addCell(new Phrase(hdft.getHdrLine4(), font2));
			}

			header.addCell(tblhdr);
			header.writeSelectedRows(0, -1, 34, 830, writer.getDirectContent());
		} catch (Exception de) {
		}

	}

	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {

		ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber())), 2, 2,
				0);
	}

}
