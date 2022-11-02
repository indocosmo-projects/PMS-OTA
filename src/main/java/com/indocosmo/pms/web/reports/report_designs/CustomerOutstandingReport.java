
package com.indocosmo.pms.web.reports.report_designs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.indocosmo.pms.web.agingAR.model.AgingAR;
import com.indocosmo.pms.web.common.AbstractITextPdfView;
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

public class CustomerOutstandingReport extends AbstractITextPdfView {

	public static final String FONT_CURRENCY = "../../../../../../../resources/common/fonts/PlayfairDisplay-Regular.ttf";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		double sum=0;
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");
		List<AgingAR> customerOutstandingList= reportTmpl.getCustomerOutstandingList();
		NumberFormat formatter = new DecimalFormat("#,##0.00"); 
		
		String customerName = "";
		
		Font header = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
		header.setColor(BaseColor.BLACK);
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
		font.setColor(BaseColor.BLACK);
		PdfPTable tableHeader = new PdfPTable(1);
		tableHeader.setWidthPercentage(100f);
		tableHeader.setWidths(new float[] {1.0f});
		PdfPCell cell = new PdfPCell();
		
		tableHeader.setSpacingBefore(5);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPhrase((new Phrase("CUSTOMER OUTSTANDING REPORT", header)));;
		tableHeader.addCell(cell);
		doc.add(tableHeader);
		
		if(customerOutstandingList.size() != 0) {
			for(int i=0; i<customerOutstandingList.size(); i++) {
				Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
				subFont.setColor(BaseColor.BLACK);
				PdfPTable tableCust = new PdfPTable(1); 
				tableCust.setWidths(new float[] {10.0f});
				tableCust.setWidthPercentage(100f);
				PdfPCell cellCust = new PdfPCell();

				if(!customerName.equals(customerOutstandingList.get(i).getCorporate_name().toUpperCase())) {
					
					if(i>0)
					{
						Font totalSubFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
						totalSubFont.setColor(BaseColor.BLACK);
						
						PdfPTable tableTotal = new PdfPTable(4);
						tableTotal.setWidthPercentage(100f);
						tableTotal.setWidths(new float[] {2.0f,2.0f,1.0f,3.0f});
						PdfPCell cellTotal = new PdfPCell();
		
						cellTotal.setBackgroundColor(new BaseColor(255, 255, 255));
						cellTotal.setPadding(3);
						
						cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cellTotal.setColspan(3);
						cellTotal.setPhrase(new Phrase("GRAND TOTAL",totalSubFont));
						tableTotal.addCell(cellTotal);
						cellTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
						cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cellTotal.setPhrase(new Phrase(String.valueOf(formatter.format(sum)),totalSubFont));
						tableTotal.addCell(cellTotal);
						
						
						doc.add(tableTotal);	
						sum = 0;
					}
					cellCust.setPhrase(new Phrase(customerOutstandingList.get(i).getCorporate_name(),font));
					tableCust.addCell(cellCust);
					doc.add(tableCust);
					
					PdfPTable tableNoData = new PdfPTable(4);
					tableNoData.setWidthPercentage(100f);
					tableNoData.setWidths(new float[] {2.0f,2.0f,1.0f,3.0f});
		
					PdfPCell cellNoData = new PdfPCell();
					cellNoData.setBackgroundColor(new BaseColor(255, 255, 255));
					cellNoData.setPadding(3);
				
					cellNoData.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellNoData.setPhrase(new Phrase("Invoice Date", font));
					tableNoData.addCell(cellNoData);
					cellNoData.setPhrase(new Phrase("Invoice number", font));
					tableNoData.addCell(cellNoData);
					cellNoData.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellNoData.setPhrase(new Phrase("Age", font));
					tableNoData.addCell(cellNoData);
					cellNoData.setPhrase(new Phrase("Invoice Amount", font));
					tableNoData.addCell(cellNoData);
					cellNoData.setColspan(3);
					cellNoData.setPhrase(new Phrase("Opening Balance", subFont));
					tableNoData.addCell(cellNoData);
					cellNoData.setPhrase(new Phrase(String.valueOf(formatter.format(customerOutstandingList.get(i).getOpening_amount())),subFont));
					tableNoData.addCell(cellNoData);
				    doc.add(tableNoData);			    			    			    
				}
				
				PdfPTable tableContent = new PdfPTable(4);
				tableContent.setWidthPercentage(100f);
				tableContent.setWidths(new float[] {2.0f,2.0f,1.0f,3.0f});
				
				
				PdfPCell cellContent = new PdfPCell();
				
				cellContent.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
				
				

				cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellContent.setPhrase(new Phrase((customerOutstandingList.get(i).getInvoice_date()),subFont));
				tableContent.addCell(cellContent);
				cellContent.setPhrase(new Phrase((customerOutstandingList.get(i).getInvoice_no()),subFont));
				tableContent.addCell(cellContent);
				cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellContent.setPhrase(new Phrase(String.valueOf(customerOutstandingList.get(i).getDays()),subFont));
				tableContent.addCell(cellContent);
				cellContent.setPhrase(new Phrase(String.valueOf(formatter.format(customerOutstandingList.get(i).getAmount())),subFont));
				tableContent.addCell(cellContent);
				tableContent.setWidthPercentage(100f);
				
				sum= sum + customerOutstandingList.get(i).getAmount();
				
				doc.add(tableContent);
				
				customerName =  customerOutstandingList.get(i).getCorporate_name();
																				
			}
						
			Font subFontTotal = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
			subFontTotal.setColor(BaseColor.BLACK);
			
			PdfPTable tableTotal = new PdfPTable(4);
			tableTotal.setWidthPercentage(100f);
			tableTotal.setWidths(new float[] {2.0f,2.0f,1.0f,3.0f});
			PdfPCell cellTotal = new PdfPCell();

			cellTotal.setBackgroundColor(new BaseColor(255, 255, 255));
			cellTotal.setPadding(3);
			
			cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTotal.setColspan(3);
			cellTotal.setPhrase(new Phrase("GRAND TOTAL",subFontTotal));
			tableTotal.addCell(cellTotal);
			cellTotal.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
			cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTotal.setPhrase(new Phrase(String.valueOf(formatter.format(sum)),subFontTotal));
			tableTotal.addCell(cellTotal);
			
			
			doc.add(tableTotal);	
			sum = 0;
		}else {
			PdfPTable tableNoData = new PdfPTable(1);
			tableNoData.setWidthPercentage(100f);
			tableNoData.setSpacingBefore(10f);
			
			PdfPCell noDataCellspan=new PdfPCell();
			noDataCellspan.setColspan(11);
			noDataCellspan.setPadding(5);
			noDataCellspan.setBorderWidthBottom(.5f);
			noDataCellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
			tableNoData.addCell(noDataCellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
			
			doc.add(tableNoData);
		}
											
	}

}

