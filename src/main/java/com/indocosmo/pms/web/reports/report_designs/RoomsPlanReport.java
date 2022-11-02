package com.indocosmo.pms.web.reports.report_designs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.ReceptionReport;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class RoomsPlanReport extends AbstractPdfViewReports{
	private float heightHeader=40.0f;
	
	Font SUBFONT;
	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(),20, 20, heightHeader, 30);
	}
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		ReportTemplate reportTemplate = (ReportTemplate) model.get("reportTemplate");
		List<ReceptionReport> roomPlanList=  reportTemplate.getRoomPlanListReport();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		if(roomPlanList.size()!=0){
				
				int count=1;
				PdfPTable table =null;
				for(int i=0;i<roomPlanList.size();i++){
					
				if(i==0 || (!roomPlanList.get(i).getNightDate().equals(roomPlanList.get(i-1).getNightDate()))){
					table = new PdfPTable(4);
					table.setWidthPercentage(100);
					table.setWidths(new float[] {0.5f,1.0f,1.0f, 3.0f});
					table.setSpacingBefore(10);
					tableHeader(table);
					PdfPCell cell=new PdfPCell();
					PdfPCell cellspan=new PdfPCell();
					cellspan.setColspan(6);
					cellspan.setPadding(5);
					String dateFormat=reportTemplate.getDateFormat();
					DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);
					cellspan.setPhrase(new Phrase("Date: ".concat(String.valueOf(simpleDateFormatHotelDate.format(roomPlanList.get(i).getNightDate()))),font));
					cellspan.setBorder(Rectangle.NO_BORDER);
					table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
					
					cell.setPadding(5);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPhrase(new Phrase("Sl.No",font));
					table.addCell(cell);
					
					cell.setPhrase(new Phrase("Room Number",font));
					table.addCell(cell);
					
					cell.setPhrase(new Phrase("Pax",font));
					table.addCell(cell);
					
					cell.setPhrase(new Phrase("Plan Description",font));
					table.addCell(cell);
				}
		
						PdfPCell cell = new PdfPCell();
						Font countFont=FontFactory.getFont(FontFactory.HELVETICA);
						countFont.setSize(10);
						cell.setPadding(5);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setPhrase(new Phrase(String.valueOf(count),countFont));
						table.addCell(cell);
						
						cell.setPhrase(new Phrase(String.valueOf(roomPlanList.get(i).getRoomNumber()),countFont));
						table.addCell(cell);
						
						cell.setPhrase(new Phrase(String.valueOf(roomPlanList.get(i).getPax()+roomPlanList.get(i).getChildren()+roomPlanList.get(i).getInfants()),countFont));
						table.addCell(cell);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPhrase(new Phrase(roomPlanList.get(i).getRateDescription(),countFont));
						table.addCell(cell);
						count=count+1;

						if((roomPlanList.get(roomPlanList.size()-1)==roomPlanList.get(i) || (!roomPlanList.get(i).getNightDate().equals(roomPlanList.get(i+1).getNightDate())))){
							document.add(table);
							document.setMargins(20, 20, 0, 30);
							document.newPage();//to  get new date report on new page
							
						}
				}
					
			}else{
				noDataAvailable(document);
			}

		}
	
	public void tableHeader(PdfPTable table){
		PdfPCell reportHeader = new PdfPCell();
		SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 16,    Font.BOLD);
		reportHeader.setPaddingTop(0);
		reportHeader.setColspan(6);
		reportHeader.setPadding(5);
		reportHeader.setPhrase(new Phrase("ROOM AND RATE PLAN",SUBFONT));
		reportHeader.setBorder(Rectangle.NO_BORDER);
		table.addCell(reportHeader).setHorizontalAlignment(Element.ALIGN_CENTER);
	}
	
	public void noDataAvailable(Document document) throws DocumentException{
		PdfPCell cell = new PdfPCell();
		PdfPTable table = new  PdfPTable(6);
		tableHeader(table);
		cell.setColspan(6);
		cell.setPhrase(new Phrase(" "));
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
		
		PdfPCell noDataCell = new PdfPCell();
		noDataCell.setPhrase(new Phrase("NO DATA AVAILABLE"));
		noDataCell.setPadding(5);
		noDataCell.setColspan(6);
		noDataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(noDataCell);
		document.add(table);
}
}