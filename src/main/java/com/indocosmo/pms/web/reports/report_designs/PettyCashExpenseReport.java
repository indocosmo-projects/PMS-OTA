package com.indocosmo.pms.web.reports.report_designs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.indocosmo.pms.web.pettycash.model.PettyCash;
import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PettyCashExpenseReport extends AbstractPdfViewReports{
	public static final String FONT = "resources/fonts/FreeSans.ttf";
	private static final String DATE_FORMAT="dd-MM-yyyy";
	
	/*@Override
	public Document newDocument() {
		return new Document(PageSize.A4, 36, 36, 120, 80);
	}*/
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dateFormat;
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<PettyCash> pettyExpenseReport = reportTmpl.getPettyCashList();
		NumberFormat amountFormat = new DecimalFormat("#0.00");
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(80.0f);
		//table.setWidths(new float[] {0.5f,0.5f,1.0f, 1.0f,0.5f,0.5f,0.5f,0.5f});
		table.setWidths(new float[] {0.5f,0.5f,1.0f, 1.0f,0.5f,0.5f,0.5f,0.5f});
		table.setSpacingBefore(10);
		
		if(pettyExpenseReport.size()!=0){
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell.setPhrase(new Phrase("#", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Date", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Head", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Voucher Type", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Voucher No", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Credit", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Debit", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Narration", font));
			table.addCell(cell);
			
			int count=1;
			for(int i=0;i<pettyExpenseReport.size();i++){
				
				cell.setPadding(2.5f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setPadding(2.0f);
				cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				Font f = FontFactory.getFont(FONT, "Cp1250", true);
				f.setSize(10);
				cell.setPhrase(new Phrase(String.valueOf(count),f));
				table.addCell(cell);
				PettyCash pettyCash=pettyExpenseReport.get(i);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
				cell.setPhrase(new Phrase(String.valueOf(simpleDateFormat.format(pettyCash.getEntryDate())),f));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(pettyCash.getCategoryName()),f));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(pettyCash.getVoucherType()),f));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(pettyCash.getVoucherNo()),f));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				
				if(pettyCash.getVoucherType().equals("PAYMENT")) {
					cell.setPhrase(new Phrase(String.valueOf(pettyCash.getAmount()),f));
					table.addCell(cell);
				}else {
					cell.setPhrase(new Phrase(String.valueOf(""),f));
					table.addCell(cell);
				}
				if(pettyCash.getVoucherType().equals("CONTRA")) {
					cell.setPhrase(new Phrase(String.valueOf(pettyCash.getAmount()),f));
					table.addCell(cell);
				}else {
					cell.setPhrase(new Phrase(String.valueOf(""),f));
					table.addCell(cell);
				}
				
				cell.setPhrase(new Phrase(String.valueOf(pettyCash.getNarration()),f));
				table.addCell(cell);
			
				count++;
			}
		}else{
			PdfPCell cellspan=new PdfPCell();
			cellspan.setColspan(10);
			cellspan.setPadding(5);
			cellspan.setPhrase(new Phrase("No Data Available",font));
			table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		document.add(table);
	}

}
