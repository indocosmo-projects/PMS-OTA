package com.indocosmo.pms.web.reports.report_designs;


import java.io.File;
import java.io.FileOutputStream;
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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneralReport extends AbstractPdfViewReports{
	public static final String FONT = "resources/fonts/FreeSans.ttf";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");

		List<com.indocosmo.pms.web.reports.model.GeneralReport> generalReportList = 
				reportTmpl.getGeneralReportList();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		final String DEST = "results/tables/table_border_outer_only.pdf";
		File file = new File(DEST);
        file.getParentFile().mkdirs();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();
		PdfPTable table = new PdfPTable(10);
        table.setTableEvent(new BorderEvent());
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f, 4.0f, 2.0f, 2.5f, 3.0f, 2.5f, 3.5f,3.5f,2.5f,2.5f});
		
		if(generalReportList.size()!=0){
			
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell.setPhrase(new Phrase("#", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Gust Name", font));
			table.addCell(cell);
			

			cell.setPhrase(new Phrase("Pax", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Room No", font));
			table.addCell(cell);

		
			cell.setPhrase(new Phrase("Room Type", font));
			table.addCell(cell);
			

			cell.setPhrase(new Phrase("Meal Plan", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Checkin Date", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Checkout Date", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Tariff", font));
			table.addCell(cell);
			if(com.indocosmo.pms.web.reports.model.GeneralReport.getType()==2 ||
					com.indocosmo.pms.web.reports.model.GeneralReport.getType()==4 ){
				cell.setPhrase(new Phrase("Folio Balance", font));
			}else{
				cell.setPhrase(new Phrase("Deposit", font));
			}
			table.addCell(cell);
					
			int resv_no=0;

			for( int i=0;i<generalReportList.size();i++){
			
				Font f = FontFactory.getFont(FONT, "Cp1250", true);
				f.setSize(10);
				cell.setPadding(3.0f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				com.indocosmo.pms.web.reports.model.GeneralReport generalReport=
							generalReportList.get(i);
				cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
				cell.setPhrase(new Phrase(String.valueOf(i+1), f));
				table.addCell(cell);
				if(resv_no != generalReport.getReport_no()){
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(generalReport.getGust_name(), f));					
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
					table.addCell(cell);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPhrase(new Phrase(generalReport.getPax_resv(), f));
					table.addCell(cell);
					
				}else {
					cell.setPhrase(new Phrase(""));
					cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
					table.addCell(cell);
					table.addCell(cell);
					
				}
				cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
				if(generalReport.getRoom_no()=="" || generalReport.getRoom_no() == null ){
					table.addCell("");
				}else {
					cell.setPhrase(new Phrase(String.valueOf(generalReport.getRoom_no()), f));
					table.addCell(cell);
				}
				cell.setPhrase(new Phrase(generalReport.getRoom_type(), f));
				table.addCell(cell);
				cell.setPhrase(new Phrase(generalReport.getMeal_plan(), f));
				table.addCell(cell);
				cell.setPhrase(new Phrase(generalReport.getCheckin_date(), f));
				table.addCell(cell);
				
				if(null != generalReport.getCheckout_date()) {
					cell.setPhrase(new Phrase(generalReport.getCheckout_date(), f));
					table.addCell(cell);
				}else {
					cell.setPhrase(new Phrase(generalReport.getExpCheckout_date(), f));
					table.addCell(cell);
				}
				cell.setPhrase(new Phrase(generalReport.getTarif(), f));		
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);			
				if(resv_no != generalReport.getReport_no()){
					cell.setPhrase(new Phrase(generalReport.getDeposit(), f));
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
					table.addCell(cell);
				}else {
					cell = new PdfPCell(new Phrase(""));
					cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
					table.addCell(cell);
				}
				
				if(resv_no != generalReport.getReport_no()){
					resv_no = generalReport.getReport_no();
				}

			}
			
		}else{
			PdfPCell noDataCellspan = new PdfPCell();
			
			noDataCellspan.setColspan(10);
			
			noDataCellspan.setPadding(5);
			noDataCellspan.setBorderWidthBottom(.5f);
			noDataCellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
			table.addCell(noDataCellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
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