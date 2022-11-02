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

public class InvoiceFooter extends PdfPageEventHelper {

	InvoiceTemplate hdft;
	PdfTemplate total;

	public InvoiceFooter(InvoiceTemplate hf) {
		hdft = hf;

	}

	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		total = writer.getDirectContent().createTemplate(30, 16);
	}

	public void onStartPage(PdfWriter writer, Document document) {
		PdfPTable header = new PdfPTable(3);

		Font fontHead = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15, Font.UNDERLINE);
		try {
			header.setWidths(new float[] { 4.0f, 4.0f, 4.0f });
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
			if (hdft.getTitle() == null) {
				header.addCell(new Phrase("INVOICE", fontHead));
			} else {
				header.addCell(new Phrase(hdft.getTitle(), fontHead));
			}

			header.getDefaultCell().setFixedHeight(100);
			header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			PdfPTable tblhdr = new PdfPTable(1);
			tblhdr.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tblhdr.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			if (hdft.getHdrLine1() != null) {
				tblhdr.addCell(new Phrase(hdft.getHdrLine1()));
			}
			if (hdft.getHdrLine2() != null) {
				tblhdr.addCell(new Phrase(hdft.getHdrLine2()));
			}
			if (hdft.getHdrLine3() != null) {
				tblhdr.addCell(new Phrase(hdft.getHdrLine3()));
			}
			if (hdft.getHdrLine4() != null) {
				tblhdr.addCell(new Phrase(hdft.getHdrLine4()));
			}

			header.addCell(tblhdr);
			header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
		} catch (Exception de) {

		}

	}

	public void onEndPage(PdfWriter writer, Document document) {

		final Font pageNumberFont = FontFactory.getFont(FontFactory.HELVETICA, 5);

//		Rectangle rect = document.getPageSize(); // writer.getBoxSize("document");
		PdfPTable footer = new PdfPTable(3);

		footer.setTotalWidth(535);
		footer.setLockedWidth(true);
		footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		footer.getDefaultCell().setPaddingBottom(5.0f);
		PdfPTable footercol1 = new PdfPTable(1);
		footercol1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		if (hdft.getFtrCol1Line1() != null) {
			footercol1.addCell(new Phrase(hdft.getFtrCol1Line1()));
		}
		if (hdft.getFtrCol1Line2() != null) {
			footercol1.addCell(new Phrase(hdft.getFtrCol1Line2()));
		}
		if (hdft.getFtrCol1Line3() != null) {
			footercol1.addCell(new Phrase(hdft.getFtrCol1Line3()));
		}
		if (hdft.getFtrCol1Line4() != null) {
			footercol1.addCell(new Phrase(hdft.getFtrCol1Line4()));
		}
		footer.addCell(footercol1);
		PdfPTable footercol2 = new PdfPTable(1);
		footercol2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		footercol2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		if (hdft.getFtrCol2Line1() != null) {
			footercol2.addCell(new Phrase(hdft.getFtrCol2Line1()));
		}
		if (hdft.getFtrCol2Line2() != null) {
			footercol2.addCell(new Phrase(hdft.getFtrCol2Line2()));
		}
		if (hdft.getFtrCol2Line3() != null) {
			footercol2.addCell(new Phrase(hdft.getFtrCol2Line3()));
		}
		if (hdft.getFtrCol2Line4() != null) {
			footercol2.addCell(new Phrase(hdft.getFtrCol2Line4()));
		}
		footer.addCell(footercol2);

		PdfPTable footercol3 = new PdfPTable(1);
		footercol3.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		footercol3.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		if (hdft.getFtrCol3Line1() != null) {
			footercol3.addCell(new Phrase(hdft.getFtrCol3Line1()));
		}
		if (hdft.getFtrCol3Line2() != null) {
			footercol3.addCell(new Phrase(hdft.getFtrCol3Line2()));
		}
		if (hdft.getFtrCol3Line3() != null) {
			footercol3.addCell(new Phrase(hdft.getFtrCol3Line3()));
		}
		if (hdft.getFtrCol3Line4() != null) {
			footercol3.addCell(new Phrase(hdft.getFtrCol3Line4()));
		}

		footercol3.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		footercol3.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		footercol3.addCell(String.format("Page %d", writer.getPageNumber(), pageNumberFont));

		footer.addCell(footercol3);
		footer.writeSelectedRows(0, -1, document.rightMargin() - 10, 20, writer.getDirectContent());
	}

	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {

		ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber())), 2, 2,
				0);
	}

}