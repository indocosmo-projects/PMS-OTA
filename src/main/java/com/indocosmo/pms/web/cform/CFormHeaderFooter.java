package com.indocosmo.pms.web.cform;

import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class CFormHeaderFooter extends PdfPageEventHelper {

	InvoiceTemplate hdft;
	static final int NUM_IMAGE_COLUMS = 10;
	static final int NUM_HEADER_COLUMS = 30;
	static final int NUM_ADDRESS_COLUMS = 20;
	static final int NUM_TOTAL_COLUMS = NUM_IMAGE_COLUMS + NUM_HEADER_COLUMS + NUM_ADDRESS_COLUMS;

	/**
	 * @param hf
	 */
	public CFormHeaderFooter(InvoiceTemplate hf) {
		hdft = hf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(com.itextpdf.text.pdf.
	 * PdfWriter, com.itextpdf.text.Document)
	 */
	public void onStartPage(PdfWriter writer, Document document) {
		PdfPTable header = new PdfPTable(NUM_TOTAL_COLUMS);
		try {
			header.setWidthPercentage(100.0f);

			PdfPCell pCell = new PdfPCell();
			pCell.setColspan(NUM_IMAGE_COLUMS);
			pCell.setBorderColorBottom(BaseColor.RED);
			pCell.setFixedHeight(70.0f);
			pCell.setBorder(Rectangle.NO_BORDER);
			Image logo = Image.getInstance(hdft.getHdrLogoUrl());
			pCell.addElement(logo);
			header.addCell(pCell).setHorizontalAlignment(Element.ALIGN_LEFT);

			subTableHeadr(header);
			address(header);
			document.add(header);
		} catch (Exception ex) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(com.itextpdf.text.pdf.
	 * PdfWriter, com.itextpdf.text.Document)
	 */
	@Override
	public void onEndPage(PdfWriter writer, Document document) {

		PdfContentByte canvas = writer.getDirectContent();
		canvas.moveTo(25f, 730f);
		canvas.lineTo(559f, 730f);
		float llx = 25;
		float lly = 36;
		float urx = 559;
		float ury = 806;
		Rectangle rect = new Rectangle(llx, lly, urx, ury);
		rect.setBorder(Rectangle.BOX); // left, right, top, bottom border
		rect.setBorderWidth(1); // a width of 5 user units
		rect.setBorderColor(BaseColor.BLACK); // a red border
		rect.setUseVariableBorders(true); // the full width will be visible
		canvas.rectangle(rect);
	}

	/**
	 * @param header
	 * @throws Exception
	 */
	private void address(PdfPTable header) throws Exception {

		PdfPCell pCellAddress = new PdfPCell();
		pCellAddress.setColspan(NUM_ADDRESS_COLUMS);
		pCellAddress.setBorder(Rectangle.NO_BORDER);
		pCellAddress.setFixedHeight(25.0f);

		PdfPTable tableAddress = new PdfPTable(1);
		tableAddress.setWidthPercentage(100.0f);
		tableAddress.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		if (hdft.getHdrLine1() != null) {
			tableAddress.addCell(new Phrase(hdft.getHdrLine1()));
		}
		if (hdft.getHdrLine2() != null) {
			tableAddress.addCell(new Phrase(hdft.getHdrLine2()));
		}
		if (hdft.getHdrLine3() != null) {
			tableAddress.addCell(new Phrase(hdft.getHdrLine3()));
		}
		if (hdft.getHdrLine4() != null) {
			tableAddress.addCell(new Phrase(hdft.getHdrLine4()));
		}
		pCellAddress.addElement(tableAddress);
		header.addCell(pCellAddress).setHorizontalAlignment(Element.ALIGN_LEFT);
	}

	/**
	 * @param header
	 * @throws Exception
	 */
	private void subTableHeadr(PdfPTable header) throws Exception {

		Font fontBottom = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
		PdfPCell pCellHeader = new PdfPCell();
		pCellHeader.setColspan(NUM_HEADER_COLUMS);
		pCellHeader.setBorderColorBottom(BaseColor.RED);
		pCellHeader.setBorder(Rectangle.NO_BORDER);
		pCellHeader.setFixedHeight(70.0f);
		pCellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPTable subTable = new PdfPTable(1);
		subTable.setWidthPercentage(100.0f);
		PdfPCell pCellAdress = new PdfPCell();
		pCellAdress.setColspan(1);
		pCellAdress.setBorder(Rectangle.NO_BORDER);
		pCellAdress.setFixedHeight(35.0f);
		fontBottom.setStyle(Font.UNDERLINE);
		pCellAdress.setPhrase(new Phrase("\"FORM\" C", fontBottom));
		pCellAdress.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCellAdress.setHorizontalAlignment(Element.ALIGN_CENTER);
		subTable.addCell(pCellAdress);

		PdfPCell pcellBusinesHtl = new PdfPCell();
		pcellBusinesHtl.setColspan(1);
		pcellBusinesHtl.setBorderColorBottom(BaseColor.RED);
		pcellBusinesHtl.setFixedHeight(35.0f);
		pcellBusinesHtl.setBorder(Rectangle.NO_BORDER);
		pcellBusinesHtl.setPhrase(new Phrase("A BUSINESS HOTEL"));
		pcellBusinesHtl.setHorizontalAlignment(Element.ALIGN_CENTER);

		subTable.addCell(pcellBusinesHtl);
		pCellHeader.addElement(subTable);
		header.addCell(pCellHeader).setHorizontalAlignment(Element.ALIGN_LEFT);
	}
}