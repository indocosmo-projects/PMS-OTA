package com.indocosmo.pms.web.reports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.indocosmo.pms.web.reports.model.TemplateReport;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportHeaderFooter extends PdfPageEventHelper {
	TemplateReport hdft;
	private int ReportType,pageCount=0;
	PdfTemplate total;
	private int page;
	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		total=writer.getDirectContent().createTemplate(30,16);
	}
	public ReportHeaderFooter(TemplateReport hf) {
		hdft =hf; 
		ReportType=hdft.getReportType();
	}

	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		
		final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 13,    Font.BOLD);
		final Font FONT1 = new Font(Font.getFamily("TIMES_ROMAN"), 9);
		final Font FONT2 = new Font(Font.getFamily("TIMES_ROMAN"), 14, Font.BOLD|Font.UNDERLINE);
		if(pageCount==0){
			pageCount+=1;
			

			
		PdfPTable headerTable = new PdfPTable(1);
		try {
			if(ReportType==1){
				headerTable.setTotalWidth(800);
			}else if(ReportType==0){
				headerTable.setTotalWidth(542);
			}else if(ReportType==2){
				headerTable.setTotalWidth(360);}	
			headerTable.setWidths(new int []{12});
			headerTable.setWidthPercentage(100.0f);
			headerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			PdfPCell cellHeader =new PdfPCell();
			
			cellHeader.setPhrase(new Phrase(hdft.getName(),SUBFONT));
			cellHeader.setBorder(Rectangle.NO_BORDER);
			cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerTable.addCell(cellHeader);
			
			cellHeader.setPhrase(new Phrase(hdft.getBuilding(),FONT1));
			cellHeader.setBorder(Rectangle.NO_BORDER);
			cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerTable.addCell(cellHeader);
		
			cellHeader.setPhrase(new Phrase(hdft.getStreet()+", "+hdft.getCity(),FONT1));
			cellHeader.setBorder(Rectangle.NO_BORDER);
			cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerTable.addCell(cellHeader);
			
			cellHeader.setPhrase(new Phrase(hdft.getHeader(),FONT2));
			cellHeader.setBorder(Rectangle.NO_BORDER);
			cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerTable.addCell(cellHeader); 
			
			cellHeader.setPhrase(new Phrase(hdft.getFilterDetails(),FONT1));
			cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			headerTable.addCell(cellHeader);
			
			headerTable.writeSelectedRows(0, -1, document.rightMargin()-10,document.top()+document.topMargin(), writer.getDirectContent());
		} 
		
		catch (DocumentException e) {
			e.printStackTrace();
		}
		}
		
	}

	@Override
	public void onEndPage(PdfWriter writer,Document document) {
		
		document.setMargins(20, 20,hdft.getHeaderHeight(), 30);
		PdfPTable table =new PdfPTable(5);
		try{table.setWidths(new int []{12,12,12,8,2});
		if(ReportType==1){
			table.setTotalWidth(800);
		}else if(ReportType==0){
			table.setTotalWidth(542);
		}else if(ReportType==2){
			table.setTotalWidth(360);}	
		table.setWidthPercentage(100.0f);
		table.setLockedWidth(true);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		
		PdfPCell cellFooter =new PdfPCell();
		cellFooter.setPhrase(new Phrase(hdft.getHotelName()));
		cellFooter.setBorder(Rectangle.NO_BORDER);
		cellFooter.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cellFooter);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		cellFooter.setPhrase(new Phrase("Printed On: "+dateFormat.format(hdft.getPrintDate())));
		cellFooter.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellFooter.setBorder(Rectangle.NO_BORDER);
		table.addCell(cellFooter);
		cellFooter.setPhrase(new Phrase("By "+hdft.getPrintBy())); 
		cellFooter.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellFooter.setBorder(Rectangle.NO_BORDER);
		table.addCell(cellFooter);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(String.format("Page %d of", writer.getPageNumber()));
		page=writer.getPageNumber();
		PdfPCell cell =new PdfPCell(Image.getInstance(total));
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
		

		if(ReportType==1){
			table.writeSelectedRows(0, -1, document.rightMargin()-5, document.bottom()+5, writer.getDirectContent());
		}else if(ReportType==0 || ReportType==2 ){
			table.writeSelectedRows(0, -1, document.rightMargin()-5, document.bottom()+5, writer.getDirectContent());
		}
		}catch(DocumentException de){
			throw new ExceptionConverter(de);
		}
		 PdfContentByte canvas = writer.getDirectContent();
	        Rectangle rect = document.getPageSize();
	        rect.setLeft(-10);
	        
	        rect.setBorder(Rectangle.NO_BORDER); // left, right, top, bottom border
	      
	}
	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		ColumnText.showTextAligned(total,
				Element.ALIGN_LEFT, new Phrase(String.valueOf(page)),
				2,2,0);
		
	}
}
