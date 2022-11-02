package com.indocosmo.pms.web.reports.report_designs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.CustomerReport;
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


public class CustomerHistoryReport extends AbstractPdfViewReports{

	public static final String FONT = "resources/fonts/FreeSans.ttf";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ReportTemplate reportTemplate=(ReportTemplate) model.get("reportTemplate");
		List<CustomerReport> customerReport=reportTemplate.getCustomerReport();
		Map<String, List<CustomerReport>> mapValues = customerReport.parallelStream().
				collect(Collectors.groupingBy(pl -> pl.getFirst_name()+" "+pl.getLast_name()));
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);

		PdfPTable pdfTable=new PdfPTable(9);
		pdfTable.setWidthPercentage(100.0f);
		pdfTable.setWidths(new float[] {1.0f,3.5f,3.5f,3.5f,3.5f,3.0f,3.0f,3.0f,3.5f});
		pdfTable.setSpacingBefore(10);

		if(mapValues.size()!=0) {

			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			PdfPCell cellspan=new PdfPCell();
			cellspan.setColspan(10);
			cellspan.setPadding(5);

			cell.setPhrase(new Phrase("#", font));
			pdfTable.addCell(cell);

			cell.setPhrase(new Phrase("Customer Name", font));
			pdfTable.addCell(cell);

			cell.setPhrase(new Phrase("Phone", font));
			pdfTable.addCell(cell);

			cell.setPhrase(new Phrase("Email", font));
			pdfTable.addCell(cell);

			cell.setPhrase(new Phrase("Arrival Date", font));
			pdfTable.addCell(cell);

			cell.setPhrase(new Phrase("Room Type", font));
			pdfTable.addCell(cell);

			cell.setPhrase(new Phrase("Room Number", font));
			pdfTable.addCell(cell);

			cell.setPhrase(new Phrase("Nights", font));
			pdfTable.addCell(cell);

			cell.setPhrase(new Phrase("Departure Date", font));
			pdfTable.addCell(cell);

			int count=1;

			for(Map.Entry<String, List<CustomerReport>> entry:mapValues.entrySet()) {

				String customerKey=entry.getKey();	
				List<CustomerReport> customerData=entry.getValue();

				Font f = FontFactory.getFont(FONT, "Cp1250", true);
				f.setSize(10);
				cell.setPadding(3.0f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);

				pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				pdfTable.getDefaultCell().setPadding(5.0f);

				
				String ckey = "";
				for(CustomerReport customerListNew:customerData) {

					if(ckey!=customerKey) {	 

						pdfTable.addCell(String.valueOf(count));

						pdfTable.addCell(customerKey);

						pdfTable.addCell(String.valueOf(customerListNew.getPhone()));

						pdfTable.addCell(String.valueOf(customerListNew.getEmail()));

						ckey = customerKey;
						
						count=count+1;

					}else {

						pdfTable.addCell("");
						pdfTable.addCell("");
						pdfTable.addCell("");
						pdfTable.addCell("");

					}

					pdfTable.addCell(String.valueOf(customerListNew.getArr_date()));
					pdfTable.addCell(String.valueOf(customerListNew.getRoom_type_code()));
					pdfTable.addCell(String.valueOf(customerListNew.getRoom_number()));
					pdfTable.addCell(String.valueOf(customerListNew.getNum_nights()));
					if(customerListNew.getAct_depart_date() !=null){
						pdfTable.addCell(String.valueOf(customerListNew.getAct_depart_date()));
					}else{
						pdfTable.addCell("");
					}
					pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				}
			}

		}else {

			
			PdfPCell noDataCellspan = new PdfPCell();

			noDataCellspan.setColspan(10);

			noDataCellspan.setPadding(5);
			noDataCellspan.setBorderWidthBottom(.5f);
			noDataCellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
			pdfTable.addCell(noDataCellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		document.add(pdfTable);
	}

}
