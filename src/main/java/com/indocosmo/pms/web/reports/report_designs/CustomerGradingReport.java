package com.indocosmo.pms.web.reports.report_designs;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.CustomerGrading;
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

public class CustomerGradingReport extends AbstractPdfViewReports{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");
		List<CustomerGrading> customerGradingList = reportTmpl.getCustomerGradingList();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		
		Font fontContent = FontFactory.getFont(FontFactory.HELVETICA, 12);
		if(customerGradingList.size()!=0){
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100f);
			table.setWidths(new float[] {2f, 2f, 2.5f, 1f, 1f});
			
			PdfPCell cell = new PdfPCell();
			cell.setPadding(8f);
			cell.setPhrase(new Phrase("Guest Name", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Email", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Address", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Phone", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Total Amount", font));
			table.addCell(cell);
			
			PdfPCell cellContent = new PdfPCell();
			cellContent.setPadding(3);
			
			for(CustomerGrading obj : customerGradingList) {
				cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellContent.setPhrase(new Phrase(obj.getGuestName(), fontContent));
				table.addCell(cellContent);
				cellContent.setPhrase(new Phrase(obj.getEmail(), fontContent));
				table.addCell(cellContent);
				cellContent.setPhrase(new Phrase(obj.getAddress(), fontContent));
				table.addCell(cellContent);
				cellContent.setPhrase(new Phrase(obj.getPhone().toString(), fontContent));
				table.addCell(cellContent);
				cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellContent.setPhrase(new Phrase(obj.getTotalAmount().toString(), fontContent));
				table.addCell(cellContent);
			}
			
			document.add(table);
			
		}else{
			PdfPTable tableNoData = new PdfPTable(1);
			tableNoData.setWidthPercentage(100f);
			
			PdfPCell noDatacellspan=new PdfPCell();
			noDatacellspan.setColspan(11);
			noDatacellspan.setPadding(5);
			noDatacellspan.setBorderWidthBottom(.5f);
			noDatacellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
			tableNoData.addCell(noDatacellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
			
			document.add(tableNoData);
		}
		
	}

}
