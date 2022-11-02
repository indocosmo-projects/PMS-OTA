package com.indocosmo.pms.web.reports.report_designs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.CashRegistersClosureReportTemplate;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.itextpdf.text.BaseColor;
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

public class B2CReport extends AbstractPdfViewReports{
	private float heightHeader=40.0f;
	CashRegistersClosureReportTemplate reportTmple;
	Font font;
	Font SUBFONT;
	public static final String FONT_CURRENCY = "../../../../../../../../resources/common/fonts/PlayfairDisplay-Regular.ttf";
	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(),20, 20, heightHeader, 30);
	}

	public static final String FONT = "resources/fonts/FreeSans.ttf";
	NumberFormat amountFormat = new DecimalFormat("#,##0.00");
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean headerCheck=true;
		
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");
		List<Transaction> b2cReport = reportTmpl.getB2CReport();
		NumberFormat amountFormat = new DecimalFormat("#0.00");
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		
		
		PdfPTable table1 = new PdfPTable(4);
		table1.setWidthPercentage(100.0f);
		table1.setWidths(new float[] {5.0f,5.0f,4.0f, 4.0f});
		table1.setSpacingBefore(10);
		
		Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
		
		if(b2cReport.size()!=0){
             
			if(headerCheck) {
				reportHeaderName(table1);
				
				Transaction b2cData0=b2cReport.get(0);
				
				PdfPCell cell1 = new PdfPCell();
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setPadding(4);
				
				cell1.setBorder(Rectangle.TOP | Rectangle.BOTTOM| Rectangle.LEFT | Rectangle.RIGHT);
				cell1.setPhrase(new Phrase("", SUBFONT));
				table1.addCell(cell1);
				
				cell1.setPhrase(new Phrase("", SUBFONT));
				table1.addCell(cell1);
				
				cell1.setPhrase(new Phrase("", SUBFONT));
				table1.addCell(cell1);
				
				cell1.setPhrase(new Phrase("Total Taxable  Value", SUBFONT));
				table1.addCell(cell1);
				
				
				
				cell1.setPhrase(new Phrase("", contentFont));
				table1.addCell(cell1);
				
				cell1.setPhrase(new Phrase("", contentFont));
				table1.addCell(cell1);
				
				cell1.setPhrase(new Phrase("", contentFont));
				table1.addCell(cell1);
				
				cell1.setPhrase(new Phrase(amountFormat.format(Double.valueOf(b2cData0.getTotalTaxableVal())), contentFont));
				table1.addCell(cell1);
				
				cell1.setPhrase(new Phrase("Type", SUBFONT));
				table1.addCell(cell1);
				
				cell1.setPhrase(new Phrase("Place Of Supply", SUBFONT));
				table1.addCell(cell1);
				
				cell1.setPhrase(new Phrase("Rate", SUBFONT));
				table1.addCell(cell1);
				
				cell1.setPhrase(new Phrase("Taxable Value", SUBFONT));
				table1.addCell(cell1);
				
				headerCheck=false;
			}
			
		
			
			for(int i=0;i<b2cReport.size();i++){
				Transaction b2cData=b2cReport.get(i);
				
				
				//for data display
				
				
				PdfPCell cellContent = new PdfPCell();
				cellContent.setPadding(5);
				
				cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellContent.setPhrase(new Phrase("OE", contentFont));
				table1.addCell(cellContent);
				
				cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellContent.setPhrase(new Phrase("32-Kerala", contentFont));
				table1.addCell(cellContent);
				
				cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(b2cData.getTotalTaxPc())), contentFont));
				table1.addCell(cellContent);
				
				cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(b2cData.getInvoiceBaseAmount())), contentFont));
				table1.addCell(cellContent);
			
				
			}
			
			document.add(table1);

		}else{

			noDataAvailable(document);
		}
	}
	public void reportHeaderName(PdfPTable table){
		PdfPCell reportHeader = new PdfPCell();
		SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 12,    Font.BOLD);
		reportHeader.setColspan(4);
		//reportHeader.setPadding(4);
		reportHeader.setPhrase(new Phrase("B2C REPORT",SUBFONT));
		reportHeader.setBorder(Rectangle.NO_BORDER);
		table.addCell(reportHeader).setHorizontalAlignment(Element.ALIGN_CENTER);

	}


	public void noDataAvailable(Document document) throws DocumentException{
		PdfPCell cell = new PdfPCell();
		PdfPTable table = new  PdfPTable(6);
		reportHeaderName(table);
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
