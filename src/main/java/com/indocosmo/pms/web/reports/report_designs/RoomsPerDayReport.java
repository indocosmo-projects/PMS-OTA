package com.indocosmo.pms.web.reports.report_designs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.ReceptionReport;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class RoomsPerDayReport extends AbstractPdfViewReports{
	public static final String FONT = "resources/fonts/FreeSans.ttf";
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");
		List<ReceptionReport> roomPerDay = reportTmpl.getRoomsPerDayList();
		
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {0.5f,1.0f,1.0f, 1.0f});
		table.setSpacingBefore(10);
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		if(roomPerDay.size()!=0){
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell.setPhrase(new Phrase("#", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Date", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Rooms", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Pax", font));
			table.addCell(cell);
			
			int count=1;
			for(int i=0;i<roomPerDay.size();i++){
				Font f = FontFactory.getFont(FONT, "Cp1250", true);
				f.setSize(10);
				String dateFormat=reportTmpl.getDateFormat();
				DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);
				cell.setPhrase(new Phrase(String.valueOf(count),f));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(simpleDateFormatHotelDate1.format(roomPerDay.get(i).getNightDate())),f));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(roomPerDay.get(i).getTotalRooms()),f));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(roomPerDay.get(i).getPax()),f));
				table.addCell(cell);
				count=count+1;
			}
		}else{
			PdfPCell cellSpan=new PdfPCell();
			Font noDatafont=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			cellSpan.setColspan(4);
			cellSpan.setPadding(5);
			cellSpan.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellSpan.setPhrase(new Phrase("NO DATA AVILABLE",noDatafont));
			table.addCell(cellSpan);
		}
		document.add(table);
	}
	
}