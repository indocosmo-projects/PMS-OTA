package com.indocosmo.pms.web.reports.report_designs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.indocosmo.pms.web.agingAR.model.AgingAR;
import com.indocosmo.pms.web.common.AbstractITextPdfView;
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

public class AgingReport extends AbstractITextPdfView {

	public static final String FONT_CURRENCY = "../../../../../../../resources/common/fonts/PlayfairDisplay-Regular.ttf";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {


		NumberFormat formatter = new DecimalFormat("#,##0.00"); 	
		List<AgingAR> summaryreportdata = (List<AgingAR>) model.get("summaryreportdata");
		

		float sum=0,sum1=0,sum2=0, sum3=0, sum4=0;
		
		Font header = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
		header.setColor(BaseColor.BLACK);
		PdfPTable tableHeader = new PdfPTable(1);
		tableHeader.setWidthPercentage(100.0f);		
		PdfPCell cell = new PdfPCell();		
		tableHeader.setSpacingBefore(8);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		cell.setPhrase((new Phrase("AGING AR REPORT SUMMARY", header)));;
		tableHeader.addCell(cell);
		doc.add(tableHeader);
		
		PdfPTable tableData = new PdfPTable(6);
		tableData.setWidthPercentage(100.0f);
		tableData.setWidths(new float[] {8.0f,6.0f,5.0f,5.0f,5.0f,5.0f});
		tableData.setSpacingBefore(10);

		PdfPCell cellData = new PdfPCell();

		cellData.setBackgroundColor(new BaseColor(255, 255, 255));
		cellData.setPadding(5);
		
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
		font.setColor(BaseColor.BLACK);
		cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellData.setPhrase((new Phrase("Customer Name", font)));
		tableData.addCell(cellData);
		cellData.setPhrase(new Phrase("Outstanding", font));
		tableData.addCell(cellData);
		cellData.setPhrase(new Phrase("0-30 Days", font));
		tableData.addCell(cellData);
		cellData.setPhrase(new Phrase("30-60 Days", font));
		tableData.addCell(cellData);
		cellData.setPhrase(new Phrase("60-90 Days ", font));
		tableData.addCell(cellData);
		cellData.setPhrase(new Phrase(">90 Days", font));
		tableData.addCell(cellData);
		
		
		for(int i=0; i<summaryreportdata.size(); i++) {
			Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
			subFont.setColor(BaseColor.BLACK);
			cellData.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellData.setPhrase(new Phrase(summaryreportdata.get(i).getCorporate_name(),subFont));
			tableData.addCell(cellData);
			cellData.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
			cellData.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellData.setPhrase(new Phrase(String.valueOf(formatter.format(summaryreportdata.get(i).getBalance())),subFont));
			tableData.addCell(cellData);
			cellData.setPhrase(new Phrase(String.valueOf(formatter.format(summaryreportdata.get(i).getBalance30())),subFont));
			tableData.addCell(cellData);
			cellData.setPhrase(new Phrase(String.valueOf(formatter.format(summaryreportdata.get(i).getBalance60())),subFont));
			tableData.addCell(cellData);
			cellData.setPhrase(new Phrase(String.valueOf(formatter.format(summaryreportdata.get(i).getBalance90())),subFont));
			tableData.addCell(cellData);
			cellData.setPhrase(new Phrase(String.valueOf(formatter.format(summaryreportdata.get(i).getBalance120())),subFont));
			tableData.addCell(cellData);
			
			sum= sum + summaryreportdata.get(i).getBalance();
			sum1= sum1 + summaryreportdata.get(i).getBalance30();
			sum2= sum2 + summaryreportdata.get(i).getBalance60();
			sum3= sum3 + summaryreportdata.get(i).getBalance90();
			sum4= sum4 + summaryreportdata.get(i).getBalance120();
		}
		
		Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
		subFont.setColor(BaseColor.BLACK);
		cellData.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellData.setPhrase(new Phrase("GRAND TOTAL",subFont));
		tableData.addCell(cellData);
		cellData.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);
		cellData.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellData.setPhrase(new Phrase(String.valueOf(formatter.format(sum)),subFont));
		tableData.addCell(cellData);
		cellData.setPhrase(new Phrase(String.valueOf(formatter.format(sum1)),subFont));
		tableData.addCell(cellData);
		cellData.setPhrase(new Phrase(String.valueOf(formatter.format(sum2)),subFont));
		tableData.addCell(cellData);
		cellData.setPhrase(new Phrase(String.valueOf(formatter.format(sum3)),subFont));
		tableData.addCell(cellData);
		cellData.setPhrase(new Phrase(String.valueOf(formatter.format(sum4)),subFont));
		tableData.addCell(cellData);
		doc.add(tableData);
	
	}

}
