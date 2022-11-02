package com.indocosmo.pms.web.reports.report_designs;

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

public class RequestReport extends AbstractPdfViewReports {
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");
		List<ReceptionReport> requestList = reportTmpl.getRequestList();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f,2.5f,3.0f,3.0f,4.0f,3.0f});
		table.setSpacingBefore(10);
		if(requestList.size()!=0){
					
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);

			cell.setPhrase(new Phrase("#", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Room #", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Guest Name", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Request", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Required Time", font));
			table.addCell(cell);
		
			cell.setPhrase(new Phrase("Phone", font));
			table.addCell(cell);
			int count=1;
			int i;
			for( i=0;i<requestList.size();i++){
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				table.getDefaultCell().setPadding(5.0f);
				ReceptionReport reception=requestList.get(i);
				table.addCell(String.valueOf(count));
				table.addCell(String.valueOf(reception.getRoomNumber()));
				table.addCell(String.valueOf(reception.getFirstName()).concat(" ").concat(String.valueOf(reception.getLastName())));
				table.addCell(String.valueOf(reception.getName()));
				table.addCell(String.valueOf(reception.getReqTime().substring(10,19)));
				table.addCell(String.valueOf(reception.getPhone()));
				count=count+1;
				
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

}
