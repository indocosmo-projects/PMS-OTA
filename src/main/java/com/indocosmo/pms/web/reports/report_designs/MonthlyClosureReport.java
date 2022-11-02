package com.indocosmo.pms.web.reports.report_designs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class MonthlyClosureReport extends AbstractPdfViewReports{
	public static final String FONT = "resources/fonts/FreeSans.ttf";

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		double baseTotal=0.00;
		double tax1Total=0.00;
		double tax2Total=0.00;
		double tax3Total=0.00;
		double totalAmount=0.00;
		int paxValue=0;
		int paxTotal=0;
		int nightValue=0;
		int nightTotal=0;
		String dateFormat;
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");
		List<Transaction> monthlyClosureReport = reportTmpl.getMonthlyClosureReport();
		NumberFormat amountFormat = new DecimalFormat("#,##0.00");
		PdfPTable table = new PdfPTable(10);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f,1.5f,1.0f,2.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f});
		table.setSpacingAfter(10);
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);

		if(monthlyClosureReport.size()!=0){
			double baseAmount = 0.0;
			double tax1 = 0.0;
			double tax2 = 0.0;
			//double tax3 = 0.0;
			double total = 0.0;

			PdfPCell cell = new  PdfPCell();

			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5);
			cell.setPhrase(new Phrase("Date",font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Invoice",font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Room",font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Guest",font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Num Nights",font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Pax",font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Base Amount",font));
			table.addCell(cell);

			cell.setPhrase(new Phrase((String) request.getSession().getAttribute("tax1Name"),font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase((String) request.getSession().getAttribute("tax2Name"),font));
			table.addCell(cell);
			
			//cell.setPhrase(new Phrase((String) request.getSession().getAttribute("tax3Name"),font));
			//table.addCell(cell);

			cell.setPhrase(new Phrase("Total",font));
			table.addCell(cell);


			for(int i=0;i<monthlyClosureReport.size();i++){
				Font font2 = FontFactory.getFont(FONT, "Cp1250", true);
				font2.setSize(10);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
				dateFormat = monthlyClosureReport.get(i).getDateFormat();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

				cell.setPhrase(new Phrase(String.valueOf(simpleDateFormat.format(monthlyClosureReport.get(i).getInvoiceDate())),font2));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(monthlyClosureReport.get(i).getInvoiceNo()),font2));
				table.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(monthlyClosureReport.get(i).getRoomNumber()),font2));
				table.addCell(cell);
				
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase(monthlyClosureReport.get(i).getFirstName(),font2));
				table.addCell(cell);
				
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase(String.valueOf(monthlyClosureReport.get(i).getNumNights()),font2));
				nightValue=Integer.parseInt(monthlyClosureReport.get(i).getNumNights());
				table.addCell(cell);
				
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase(String.valueOf(monthlyClosureReport.get(i).getPax()),font2));
				paxValue=monthlyClosureReport.get(i).getPax();
				table.addCell(cell);
				
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(monthlyClosureReport.get(i).getBase_amount())),font2));
				table.addCell(cell);
				cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(monthlyClosureReport.get(i).getTax1_amount())),font2));
				table.addCell(cell);
				cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(monthlyClosureReport.get(i).getTax2_amount())),font2));
				table.addCell(cell);
				//cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(monthlyClosureReport.get(i).getTax3_amount())),font2));
				//table.addCell(cell);
				cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(monthlyClosureReport.get(i).getAmount())),font2));
				table.addCell(cell);
				baseAmount += monthlyClosureReport.get(i).getBase_amount();
				baseTotal= Math.round(baseAmount * 100.0) / 100.0;

				tax1 += monthlyClosureReport.get(i).getTax1_amount();
				tax2 += monthlyClosureReport.get(i).getTax2_amount();
				//tax3 += monthlyClosureReport.get(i).getTax3_amount();
				tax1Total=Math.round(tax1 * 100.0) / 100.0;
				tax2Total=Math.round(tax2 * 100.0) / 100.0;
				//tax3Total=Math.round(tax3 * 100.0) / 100.0;

				total += monthlyClosureReport.get(i).getAmount();
				totalAmount=Math.round(total * 100.0) / 100.0;
				
				paxTotal+=paxValue;
				nightTotal+=nightValue;
			}


			cell.setColspan(4);
			cell.setPhrase(new Phrase("TOTAL",font));
			table.addCell(cell);

			cell.setColspan(1);
			cell.setPhrase(new Phrase(String.valueOf(nightTotal),font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(String.valueOf(paxTotal),font));
			table.addCell(cell);

			
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(baseTotal)),font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax1Total)),font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax2Total)),font));
			table.addCell(cell);
			
			//cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax3Total)),font));
			//table.addCell(cell);

			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(totalAmount)),font));
			table.addCell(cell);


		}else{
			PdfPCell colspan = new PdfPCell();
			colspan.setColspan(8);
			colspan.setPadding(5);
			colspan.setHorizontalAlignment(Element.ALIGN_CENTER);
			colspan.setPhrase(new Phrase("NO DATA AVAILABLE",font));
			table.addCell(colspan);
		}
		document.add(table);
	}

}