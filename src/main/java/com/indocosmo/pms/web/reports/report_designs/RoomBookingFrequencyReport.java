package com.indocosmo.pms.web.reports.report_designs;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.reports.model.RoomBookingReport;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class RoomBookingFrequencyReport extends AbstractPdfViewReports {

	public static final String FONT = "resources/fonts/FreeSans.ttf";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String roomNumber = "";
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<RoomBookingReport> roomFrequency = reportTmpl.getRoomBookingFrequency();
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 0.5f, 3.0f, 3.5f, 2.0f, 3.0f, 3.0f });
		table.setSpacingBefore(10);

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		int i;
		int count = 1;

		if (roomFrequency.size() != 0) {

			for (i = 0; i < roomFrequency.size(); i++) {

				roomNumber = roomFrequency.get(i).getRoomNumber();

				if ((i == 0) || !roomNumber.equals(roomFrequency.get(i - 1).getRoomNumber())) {

					count = 1;

					table.getDefaultCell().setColspan(6);
					table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
					table.addCell("    ");

					PdfPCell cellRoomNo = new PdfPCell();
					cellRoomNo.setBorder(Rectangle.BOTTOM);
					cellRoomNo.setColspan(6);
					cellRoomNo.setPhrase(new Phrase(roomNumber, font));
					table.addCell(cellRoomNo);

					PdfPCell cell = new PdfPCell();
					cell.setBorder(Rectangle.TOP);
					cell.setPhrase(new Phrase("#", font));
					table.addCell(cell);

					cell.setPhrase(new Phrase("Arrival Date", font));
					table.addCell(cell);

					cell.setPhrase(new Phrase("Guest Name", font));
					table.addCell(cell);

					cell.setPhrase(new Phrase("Adults", font));
					table.addCell(cell);

					cell.setPhrase(new Phrase("Children", font));
					table.addCell(cell);

					cell.setPhrase(new Phrase("Infants", font));
					table.addCell(cell);
				}

				table.getDefaultCell().setColspan(1);

				table.addCell(String.valueOf(count));

				table.addCell(String.valueOf(String.valueOf(roomFrequency.get(i).getArrDate())));
				table.addCell(String.valueOf(roomFrequency.get(i).getFirstName()));
				table.addCell(String.valueOf(roomFrequency.get(i).getAdults()));
				table.addCell(String.valueOf(roomFrequency.get(i).getChildren()));
				table.addCell(String.valueOf(roomFrequency.get(i).getInfants()));

				count = count + 1;

			}

		}
		document.add(table);
	}

}
