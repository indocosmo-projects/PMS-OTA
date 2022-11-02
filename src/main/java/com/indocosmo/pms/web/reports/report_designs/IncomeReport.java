package com.indocosmo.pms.web.reports.report_designs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class IncomeReport extends AbstractPdfViewReports{

	public static final String FONT = "resources/fonts/FreeSans.ttf";
	
	@Override
	public Document newDocument() {
		return new Document(PageSize.A4,36, 36, 110, 30);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ReportTemplate reportTemplate=(ReportTemplate) model.get("reportTemplate");
		List<com.indocosmo.pms.web.reports.model.IncomeReport> incomeReportList=reportTemplate.getIncomeReport();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);

		PdfPTable pdfTable=new PdfPTable(5);
		pdfTable.setWidthPercentage(100.0f);
		pdfTable.setWidths(new float[] {1.f,3.0f,3.0f,3.0f,3.0f});
		pdfTable.setSpacingBefore(10);

		if(incomeReportList.size()!=0){
			
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			PdfPCell cellspan=new PdfPCell();
			cellspan.setColspan(10);
			cellspan.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("#", font));
			pdfTable.addCell(cell);
			
			cell.setPhrase(new Phrase("Transaction Date", font));
			pdfTable.addCell(cell);

			cell.setPhrase(new Phrase("Receipt/Invoice No", font));
			pdfTable.addCell(cell);
			
			cell.setPhrase(new Phrase("Mode of Payment", font));
			pdfTable.addCell(cell);

			cell.setPhrase(new Phrase("Amount", font));
			pdfTable.addCell(cell);

			int count=0;
			float total =0.0f;
			float grandTotal =0.0f;
			
			Date txnDate=null;
			for(com.indocosmo.pms.web.reports.model.IncomeReport incomeReport :incomeReportList) {
						txnDate=incomeReport.getDate();
						if(count ==0 || incomeReport.getDate().equals(incomeReportList.get(count-1).getDate())){
							total += incomeReport.getAmount();
							if(count >0)
							{
								txnDate=null;
							}
						}else{
							pdfTable.getDefaultCell().setColspan(4);
							Paragraph paragraph = new Paragraph("Total");
							paragraph.getFont().setStyle(Font.BOLD);
							pdfTable.addCell(paragraph);
							pdfTable.getDefaultCell().setColspan(1);
							Paragraph paragraph1 = new Paragraph(String.format("%.2f",total));
							paragraph1.getFont().setStyle(Font.BOLD);
							pdfTable.addCell(paragraph1);
							
							total = incomeReport.getAmount();
						}
						
						String dateFormat = incomeReport.getDateFormat();
						DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);	
						pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
						pdfTable.addCell(String.valueOf(count+1));
						if(txnDate != null)
						{
							pdfTable.addCell(simpleDateFormatHotelDate1.format(txnDate));
						}
						else
						{
							pdfTable.addCell("");
						}						
						
						if(incomeReport.getReceiptNo()!=null){
							pdfTable.addCell(incomeReport.getReceiptNo());
						}else{
							pdfTable.addCell(incomeReport.getInvoiceNo());
						}
						pdfTable.addCell(incomeReport.getMode());
						
						pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
						pdfTable.addCell(String.format("%.2f", incomeReport.getAmount()));
						grandTotal += incomeReport.getAmount();
						count=count+1;
			}
			pdfTable.getDefaultCell().setColspan(4);
			Paragraph paragraph = new Paragraph("Total");
			paragraph.getFont().setStyle(Font.BOLD);
			pdfTable.addCell(paragraph);
			pdfTable.getDefaultCell().setColspan(1);
			Paragraph paragraphTemp = new Paragraph(String.format("%.2f",total));
			pdfTable.addCell(paragraphTemp);
			
			pdfTable.getDefaultCell().setColspan(4);
			Paragraph paragraphGrand = new Paragraph(" Grand Total");
			paragraphGrand.getFont().setStyle(Font.BOLD);
			pdfTable.addCell(paragraphGrand);
			pdfTable.getDefaultCell().setColspan(1);
			Paragraph paraTotal = new Paragraph(String.format("%.2f",grandTotal));
			paraTotal.getFont().setStyle(Font.BOLD);
			pdfTable.addCell(paraTotal);
			

		}else{

			PdfPCell noDataCellspan = new PdfPCell();

			noDataCellspan.setColspan(5);

			noDataCellspan.setPadding(5);
			noDataCellspan.setBorderWidthBottom(.5f);
			noDataCellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
			pdfTable.addCell(noDataCellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		document.add(pdfTable);
	}

}
