package com.indocosmo.pms.web.cform.design_cform;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.cform.AbstractITectPdfCForm;
import com.indocosmo.pms.web.cform.model.CForm;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFBuilderCForm extends AbstractITectPdfCForm {

	static final int NUM_TITLE_COLUMS = 12;
	static final int NUM_DETAIL_COLUMS = 19;
	static final int NUM_TOTAL_COLUMS = NUM_TITLE_COLUMS + NUM_DETAIL_COLUMS;
	Font fonttext = FontFactory.getFont(FontFactory.HELVETICA, 9);
	Font fontBottom = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pms.web.cform.AbstractITectPdfCform#buildPdfDocument(java.util.
	 * Map, com.itextpdf.text.Document, com.itextpdf.text.pdf.PdfWriter,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		CForm listCform = (CForm) model.get("listCform");
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);

		PdfPTable table = new PdfPTable(NUM_TOTAL_COLUMS);
		table.setWidthPercentage(100.0f);
		createBlankSpace(table);

		createTitle(table, "Name (As per Passport)", listCform.getName());
		createTitle(table, "Gender", listCform.getGender());
		createTitle(table, "Date of Birth", "");
		createTitle(table, "Nationality", listCform.getNationality());

		document.add(table);
		createPermanentAddress(model, document);
		createPassportDetails(model, document);
		createVisaDetails(document);
		CreateArrivalDetails(model, document);
		createOtherDetails(model, document);
		bottomDetails(document);
	}

	/**
	 * @param table
	 * @param title
	 * @param value
	 */
	private void createTitle(PdfPTable table, String title, String value) {

		PdfPCell cellTitle = new PdfPCell();
		cellTitle.setColspan(NUM_TITLE_COLUMS);
		cellTitle.setBorder(Rectangle.NO_BORDER);
		cellTitle.setPhrase(new Phrase(title, fonttext));
		table.addCell(cellTitle).setHorizontalAlignment(Element.ALIGN_LEFT);
		value = ((value == null || value.trim().isEmpty()) ? "-------------------------------------" : value);
		PdfPCell cellDetail = new PdfPCell();
		cellDetail.setColspan(NUM_DETAIL_COLUMS);
		cellDetail.setBorder(Rectangle.NO_BORDER);
		cellDetail.setPhrase(new Phrase(value));
		table.addCell(cellDetail).setHorizontalAlignment(Element.ALIGN_LEFT);
	}

	/**
	 * @param document
	 * @throws DocumentException
	 */

	private void createPermanentAddress(Map<String, Object> model, Document document) throws DocumentException {

		CForm listCform = (CForm) model.get("listCform");
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		PdfPTable paTable = new PdfPTable(NUM_TOTAL_COLUMS);
		paTable.setWidthPercentage(100.0f);
		PdfPCell cellAddress = new PdfPCell();
		cellAddress.setColspan(NUM_TOTAL_COLUMS);
		cellAddress.setBorderWidthBottom(.5f);
		cellAddress.setBorder(Rectangle.NO_BORDER);
		cellAddress.setPhrase(new Phrase("Permanent Address:-", font));
		paTable.addCell(cellAddress).setHorizontalAlignment(Element.ALIGN_LEFT);
		String addLine1 = listCform.getAddress();
		String addLine2 = "";
		if (listCform.getAddress().length() >= 18) {
			addLine1 = listCform.getAddress().substring(0, 18);
			addLine2 = listCform.getAddress().substring(18, listCform.getAddress().length());
		}
		createCellRow(paTable, "Address/Reference In India", addLine1, 18);
		createBlankSpace(paTable);
		createCellRow(paTable, " ", addLine2, 18);
		document.add(paTable);
	}

	/**
	 * @param document
	 * @throws DocumentException
	 * @throws ParseException
	 */
	private void createPassportDetails(Map<String, Object> model, Document document)
			throws DocumentException, ParseException {

		NumberFormat formatter = new DecimalFormat("#00");
		CForm listCform = (CForm) model.get("listCform");
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		PdfPTable pdTable = new PdfPTable(NUM_TOTAL_COLUMS);
		pdTable.setWidthPercentage(100.0f);
		PdfPCell cellPassport = new PdfPCell();
		cellPassport.setColspan(NUM_TOTAL_COLUMS);
		cellPassport.setBorderWidthBottom(.5f);
		cellPassport.setBorder(Rectangle.NO_BORDER);
		cellPassport.setPhrase(new Phrase("Passport Details:-", font));
		pdTable.addCell(cellPassport).setHorizontalAlignment(Element.ALIGN_LEFT);
		String dateOfExpiry = "";
		if (listCform.getPassport_expiry_on() != null && !listCform.getPassport_expiry_on().isEmpty()) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date parse = sdf.parse(listCform.getPassport_expiry_on());
			Calendar c = Calendar.getInstance();
			c.setTime(parse);
			String month = String.valueOf(formatter.format(c.get(Calendar.MONTH) + 1));
			String date = String.valueOf(formatter.format(c.get(Calendar.DAY_OF_MONTH)));
			String year = String.valueOf(c.get(Calendar.YEAR));
			dateOfExpiry = date.concat(month).concat(year);

		}

		createCellRow(pdTable, "Passport No", listCform.getPassportNo(), 18);
		createBlankSpace(pdTable);
		createCellRow(pdTable, "Place of Issue", " ", 18);
		createBlankSpace(pdTable);
		createCellRow(pdTable, "Date of Issue", " ", 8);
		createBlankSpace(pdTable);
		createCellRow(pdTable, "Date of Expiry", dateOfExpiry, 8);
		createBlankSpace(pdTable);
		document.add(pdTable);
	}

	/**
	 * @param table
	 * @param title
	 * @param value
	 * @param numColumns
	 */
	private void createCellRow(PdfPTable table, String title, String value, Integer numColumns) {

		PdfPCell cellRowCreate = new PdfPCell();
		cellRowCreate.setColspan(NUM_TITLE_COLUMS);
		cellRowCreate.setBorder(Rectangle.NO_BORDER);
		cellRowCreate.setPhrase(new Phrase(title, fonttext));
		cellRowCreate.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellRowCreate.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cellRowCreate);

		for (int colIndex = 0; colIndex < numColumns; colIndex++) { // individual cell

			PdfPCell cellRow = new PdfPCell();
			cellRow.setFixedHeight(15.0f);
			if (colIndex < value.length()) {
				cellRow.setPhrase(new Phrase(String.valueOf(value.charAt(colIndex)), fonttext));
			}
			table.addCell(cellRow).setHorizontalAlignment(Element.ALIGN_CENTER);
		}

		if (NUM_DETAIL_COLUMS - numColumns > 0) { // empty Row

			PdfPCell cellRowEmpty = new PdfPCell();
			cellRowEmpty.setColspan(NUM_DETAIL_COLUMS - numColumns);
			cellRowEmpty.setBorder(Rectangle.NO_BORDER);
			table.addCell(cellRowEmpty);
		}
	}

	/**
	 * @param document
	 * @throws DocumentException
	 */
	private void createVisaDetails(Document document) throws DocumentException {
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		PdfPTable vdTable = new PdfPTable(NUM_TOTAL_COLUMS);
		vdTable.setWidthPercentage(100.0f);

		PdfPCell visaDetail = new PdfPCell();
		visaDetail.setColspan(NUM_TOTAL_COLUMS);
		visaDetail.setBorderWidthBottom(.5f);
		visaDetail.setBorder(Rectangle.NO_BORDER);
		visaDetail.setPhrase(new Phrase("VISA Details:-", font));
		vdTable.addCell(visaDetail).setHorizontalAlignment(Element.ALIGN_LEFT);

		createCellRow(vdTable, "VISA No", "", 18);
		createBlankSpace(vdTable);
		createCellRow(vdTable, "Validity", "", 18);
		createBlankSpace(vdTable);
		createCellRow(vdTable, "Place of Issue", "", 18);
		createBlankSpace(vdTable);
		createCellRow(vdTable, "Date of Issue", "", 8);
		createBlankSpace(vdTable);
		createCellRow(vdTable, "Visa Type", "", 18);
		createBlankSpace(vdTable);
		document.add(vdTable);
	}

	/**
	 * @param document
	 * @throws DocumentException
	 * @throws ParseException
	 */
	private void CreateArrivalDetails(Map<String, Object> model, Document document)
			throws DocumentException, ParseException {

		NumberFormat formatter = new DecimalFormat("#00");
		CForm listCform = (CForm) model.get("listCform");
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		PdfPTable adTable = new PdfPTable(NUM_TOTAL_COLUMS);
		adTable.setWidthPercentage(100.0f);
		PdfPCell arrivalDetail = new PdfPCell();
		arrivalDetail.setColspan(NUM_TOTAL_COLUMS);
		arrivalDetail.setBorderWidthBottom(.5f);
		arrivalDetail.setBorder(Rectangle.NO_BORDER);
		arrivalDetail.setPhrase(new Phrase("Arrival Details:-", font));
		adTable.addCell(arrivalDetail).setHorizontalAlignment(Element.ALIGN_LEFT);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date parse = sdf.parse(listCform.getArr_date());
		Calendar c = Calendar.getInstance();
		c.setTime(parse);
		String month = String.valueOf(formatter.format(c.get(Calendar.MONTH) + 1));
		String date = String.valueOf(formatter.format(c.get(Calendar.DAY_OF_MONTH)));
		String year = String.valueOf(c.get(Calendar.YEAR));

		createCellRow(adTable, "Arrived From", "", 8);
		createBlankSpace(adTable);
		createCellRow(adTable, "Date of Arrival in India", "", 8);
		createBlankSpace(adTable);
		createCellRow(adTable, "Date of Arrival in Hotel", date.concat(month).concat(year), 8);
		createBlankSpace(adTable);
		createCellRow(adTable, "Time of Arrival in Hotel", listCform.getArr_time(), 8);
		createBlankSpace(adTable);
		createCellRow(adTable, "Intended duration of stay in Hotel", listCform.getNum_nights() + " nights", 18);
		createBlankSpace(adTable);
		createCellRow(adTable, "Whether Employed in India", "", 18);
		createBlankSpace(adTable);
		document.add(adTable);
	}

	/**
	 * @param document
	 * @throws DocumentException
	 */
	private void createOtherDetails(Map<String, Object> model, Document document) throws DocumentException {

		CForm listCform = (CForm) model.get("listCform");
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		PdfPTable odTable = new PdfPTable(NUM_TOTAL_COLUMS);
		odTable.setWidthPercentage(100.0f);

		PdfPCell otherDetail = new PdfPCell();
		otherDetail.setColspan(NUM_TOTAL_COLUMS);
		otherDetail.setBorderWidthBottom(.5f);
		otherDetail.setBorder(Rectangle.NO_BORDER);
		otherDetail.setPhrase(new Phrase("Other Details:-", font));
		odTable.addCell(otherDetail).setHorizontalAlignment(Element.ALIGN_LEFT);

		createCellRow(odTable, "Purpose of visit", "", 18);
		createBlankSpace(odTable);
		createCellRow(odTable, "Next Destination", "", 18);
		createBlankSpace(odTable);
		createCellRow(odTable, "Place", "", 18);
		createBlankSpace(odTable);
		createCellRow(odTable, "City", "", 18);
		createBlankSpace(odTable);
		createCellRow(odTable, "Country", listCform.getNationality(), 18);
		createBlankSpace(odTable);
		createCellRow(odTable, "Contact No.(in India)", "", 18);
		createBlankSpace(odTable);
		createCellRow(odTable, "Mobile No.(in India)", listCform.getPhone(), 18);
		createBlankSpace(odTable);
		createCellRow(odTable, "Contact No.(Permanently residing Country)", "", 18);
		createBlankSpace(odTable);
		createCellRow(odTable, "Mobile No.(Permanently residing Country)", "", 18);
		document.add(odTable);
	}

	/**
	 * @param table
	 * @throws DocumentException
	 */
	private void createBlankSpace(PdfPTable table) throws DocumentException {

		PdfPCell blankSpace = new PdfPCell();
		blankSpace.setColspan(NUM_TOTAL_COLUMS);
		blankSpace.setBorder(Rectangle.NO_BORDER);
		blankSpace.setFixedHeight(3f);
		table.addCell(blankSpace).setHorizontalAlignment(Element.ALIGN_LEFT);
	}

	/**
	 * @param document
	 * @throws Exception
	 */
	private void bottomDetails(Document document) throws Exception {

		Font fontBottomText = FontFactory.getFont(FontFactory.HELVETICA, 6);

		PdfPTable bdTable = new PdfPTable(NUM_TOTAL_COLUMS);
		bdTable.setWidthPercentage(100.0f);
		PdfPCell bdCell = new PdfPCell();
		bdCell.setColspan(NUM_TOTAL_COLUMS);
		bdCell.setBorderWidthBottom(.5f);
		bdCell.setFixedHeight(50.0f);
		bdCell.setBorderColorBottom(BaseColor.RED);
		bdCell.setPhrase(new Phrase(" "));
		bdCell.setBorder(Rectangle.NO_BORDER);
		bdTable.addCell(bdCell).setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell noteCell = new PdfPCell();
		noteCell.setColspan(2);
		noteCell.setBorderWidthBottom(.5f);
		noteCell.setFixedHeight(25.0f);
		noteCell.setBorder(Rectangle.NO_BORDER);
		noteCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		noteCell.setPhrase(new Phrase("Note:-* ", fontBottom));
		bdTable.addCell(noteCell).setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell noteDetailCell = new PdfPCell();
		noteDetailCell.setColspan(15);
		noteDetailCell.setBorderWidthBottom(.5f);
		noteDetailCell.setFixedHeight(25.0f);
		noteDetailCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		noteDetailCell.setBorder(Rectangle.NO_BORDER);
		noteDetailCell.setPhrase(new Phrase(
				"Please ensure that no column is left,blank/incomplete in case web camera/digital camera is not available photocopy of the photograph in passport may be enclosed ",
				fontBottomText));
		bdTable.addCell(noteDetailCell).setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell MangrSignCell = new PdfPCell();
		MangrSignCell.setColspan(5);
		MangrSignCell.setBorderWidthBottom(.5f);
		MangrSignCell.setFixedHeight(25.0f);
		MangrSignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		MangrSignCell.setBorder(Rectangle.NO_BORDER);
		MangrSignCell.setPhrase(new Phrase("Manager Signature ", fontBottomText));
		bdTable.addCell(MangrSignCell).setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell visitrSignCell = new PdfPCell();
		visitrSignCell.setColspan(NUM_TITLE_COLUMS);
		visitrSignCell.setBorderWidthBottom(.5f);
		visitrSignCell.setFixedHeight(25.0f);
		visitrSignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		visitrSignCell.setBorder(Rectangle.NO_BORDER);
		visitrSignCell.setPhrase(new Phrase("Visitor Signature ", fontBottomText));
		bdTable.addCell(visitrSignCell).setHorizontalAlignment(Element.ALIGN_CENTER);
		document.add(bdTable);
	}
}
