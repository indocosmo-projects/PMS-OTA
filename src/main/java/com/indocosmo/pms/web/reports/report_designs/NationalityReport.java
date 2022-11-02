package com.indocosmo.pms.web.reports.report_designs;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;
import com.itextpdf.text.pdf.PdfWriter;

public class NationalityReport extends AbstractPdfViewReports{
	public static final String FONT = "resources/fonts/FreeSans.ttf";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		Date date = commonSettings.hotelDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");

		List<com.indocosmo.pms.web.reports.model.NationalityReport> nationalityReportlist = 
				reportTmpl.getNationalityReportList();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		final String DEST = "results/tables/table_border_outer_only.pdf";
		File file = new File(DEST);
        file.getParentFile().mkdirs();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();
		PdfPTable table = new PdfPTable(8);
        table.setTableEvent(new BorderEvent());
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {4.0f, 4.0f,4.0f, 2.0f, 2.5f, 3.0f, 3.0f, 3.0f});
		
		if(nationalityReportlist.size()!=0){
			
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell.setPhrase(new Phrase("Country", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("State", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Rooms", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Rooms %", font));
			table.addCell(cell);

		
			cell.setPhrase(new Phrase("Persons", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Persons %", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(year+" Rooms", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(year+" Persons", font));
			table.addCell(cell);
			Collections.sort(nationalityReportlist);
			int room=0;
			float room_perc=0.0f;
			int person=0;
			float person_perc=0.0f;	
			int yearly_room = 0;
			int yearly_person = 0;

			for( int i=0;i<nationalityReportlist.size();i++){
				com.indocosmo.pms.web.reports.model.NationalityReport nationalityReport=
						nationalityReportlist.get(i);
				if(i==0 || !nationalityReport.getCountry_name().equals(nationalityReportlist.get(i-1).getCountry_name())){
				table.addCell(String.valueOf((nationalityReport.getCountry_name())));
				}else {
					table.addCell("");
				}
				if(null !=nationalityReport.getState_name()){
					table.addCell(String.valueOf((nationalityReport.getState_name())));
				}else{
					table.addCell("");
				}
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase(String.valueOf(nationalityReport.getRoom())));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(nationalityReport.getRoom_percentage())));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(nationalityReport.getPersons())));
				table.addCell(cell);
				cell.setPhrase(new Phrase(nationalityReport.getPerson_percentage()));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(nationalityReport.getYearly_room())));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(nationalityReport.getYearly_person())));
				table.addCell(cell);
				room += nationalityReport.getRoom();
				person += nationalityReport.getPersons();
				room_perc += Float.valueOf(nationalityReport.getRoom_percentage().replace("%", "").trim());
				person_perc += Float.valueOf(nationalityReport.getPerson_percentage().replace("%", "").trim());
				yearly_room += nationalityReport.getYearly_room();
				yearly_person += nationalityReport.getYearly_person();
				
				if((nationalityReport.getCountry_name()==null && nationalityReportlist.get(i+1).getCountry_name()!=null)||
						(nationalityReportlist.size()==i+1 || !nationalityReport.getCountry_name().equals(nationalityReportlist.get(i+1).getCountry_name()))){
					table.addCell("");
					table.addCell("");
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(String.valueOf(room),font));
					table.addCell(cell);
					cell.setPhrase(new Phrase(String.format ("%.2f",(room_perc))+"%",font));
					table.addCell(cell);
					cell.setPhrase(new Phrase(String.valueOf(person),font));
					table.addCell(cell);
					cell.setPhrase(new Phrase(String.format ("%.2f",(person_perc)) +"%",font));
					table.addCell(cell);
					cell.setPhrase(new Phrase(String.valueOf(yearly_room),font));
					table.addCell(cell);
					cell.setPhrase(new Phrase(String.valueOf(yearly_person),font));
					table.addCell(cell);
				    room=0;
					room_perc=0.0f;
					person=0;
					person_perc=0.0f;	
					yearly_person=0;
					yearly_room=0;
				}
				

			}
			
		}else{
			PdfPCell cellspan=new PdfPCell();
			
			cellspan.setColspan(10);
			
			cellspan.setPadding(5);
			cellspan.setBorderWidthBottom(.5f);
			cellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
			table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		doc.add(table);
		
	}
	public class BorderEvent implements PdfPTableEvent {
        public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases) {
            float width[] = widths[0];
            float x1 = width[0];
            float x2 = width[width.length - 1];
            float y1 = heights[0];
            float y2 = heights[heights.length - 1];
            PdfContentByte cb = canvases[PdfPTable.LINECANVAS];
            cb.rectangle(x1, y1, x2 - x1, y2 - y1);
            cb.stroke();
            cb.resetRGBColorStroke();
        }
    }
}