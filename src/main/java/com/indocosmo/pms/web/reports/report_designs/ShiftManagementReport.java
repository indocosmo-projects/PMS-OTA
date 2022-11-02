package com.indocosmo.pms.web.reports.report_designs;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.shiftManagement.model.ShiftManagement;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ShiftManagementReport extends AbstractPdfViewReports {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<ShiftManagement> shiftManagementList = reportTmpl.getShiftManagementList();
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f });
		table.setSpacingBefore(10);
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(new BaseColor(189, 189, 189));
		cell.setPadding(5);

		cell.setPhrase(new Phrase("Cashier", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Shift", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("OPening Float", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("OPening Date", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("OPening Time", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Closing Date", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Closing Time", font));
		table.addCell(cell);
		for (ShiftManagement shiftmanagement : shiftManagementList) {
			table.getDefaultCell().setPadding(5.0f);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(String.valueOf(shiftmanagement.getLoginusers()));
			table.addCell(String.valueOf(shiftmanagement.getCodeShift()));
			table.addCell(String.valueOf(shiftmanagement.getOpeningFloat()));
			table.addCell(String.valueOf(shiftmanagement.getOpeningDate()));
			table.addCell(String.valueOf(shiftmanagement.getOpeningTime()));
			table.addCell(String.valueOf(shiftmanagement.getClosingDate()));
			table.addCell(String.valueOf(shiftmanagement.getClosingTime()));
		}
		document.add(table);

	}

}
