package com.indocosmo.pms.web.reports.report_designs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class OccupancyReportList extends AbstractPdfViewReports{
	public static final String FONT = "resources/fonts/FreeSans.ttf";
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dateFormat;
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");
		List<ReceptionReport> occupancyReport = reportTmpl.getOccupancyReportList();
		NumberFormat amountFormat = new DecimalFormat("#0.00");
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f,2.0f,2.5f, 5.0f,3.0f,2.5f,2.5f,2.5f});
		table.setSpacingBefore(10);
		
		if(occupancyReport.size()!=0){
			
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell.setPhrase(new Phrase("#", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Room No", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Room Type", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Guest Name", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Nationality", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Room Charge", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Arrival Date", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Depart Date", font));	
			table.addCell(cell);

			int count=1;
			
			for(int i=0;i<occupancyReport.size();i++){
				cell.setPadding(2.5f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setPadding(2.0f);
				cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				Font f = FontFactory.getFont(FONT, "Cp1250", true);
				f.setSize(10);
				cell.setPhrase(new Phrase(String.valueOf(count),f));
				table.addCell(cell);
				ReceptionReport receptionReport = occupancyReport.get(i);
				dateFormat=receptionReport.getDateFormat();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
				cell.setPhrase(new Phrase(String.valueOf(receptionReport.getRoomNumber()),f));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(receptionReport.getRoomTypeCode()),f));
				table.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase((receptionReport.getFirstName()).concat(" ").concat(String.valueOf(receptionReport.getLastName())),f));
				table.addCell(cell);
				if((receptionReport.getNationality()=="") || receptionReport.getNationality()==null){
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPhrase(new Phrase(" ",f));
					table.addCell(cell);
				}else{
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPhrase(new Phrase(String.valueOf(receptionReport.getNationality()),f));
					table.addCell(cell);
				}
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase(String.valueOf(amountFormat.format(receptionReport.getRoomCharge())),f));
				table.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPhrase(new Phrase(String.valueOf(simpleDateFormat.format(receptionReport.getArrDate())),f));
				table.addCell(cell);
				if( receptionReport.getActDepartDate()==null){
					cell.setPhrase(new Phrase(" ",f));
					table.addCell(cell);
				}else {
					cell.setPhrase(new Phrase(String.valueOf(simpleDateFormat.format(receptionReport.getActDepartDate())),f));
					table.addCell(cell);
				}
				count=count+1;
			}
		}else{
			PdfPCell cellspan=new PdfPCell();
			cellspan.setColspan(10);
			cellspan.setPadding(5);
			cellspan.setPhrase(new Phrase("No Data Available",font));
			table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		document.add(table);
	}

	
}