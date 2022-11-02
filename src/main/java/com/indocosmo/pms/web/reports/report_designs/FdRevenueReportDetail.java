package com.indocosmo.pms.web.reports.report_designs;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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

public class FdRevenueReportDetail extends AbstractPdfViewReports{
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
		List<Transaction> revenueDetailReport = reportTmpl.getRevenueReportDetail();
		NumberFormat amountFormat = new DecimalFormat("#0.00");
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
		
		PdfPTable table1 = new PdfPTable(9);
		table1.setWidthPercentage(100.0f);
		table1.setWidths(new float[] {5.0f,3.0f,5.0f, 5.0f,2.5f,3.0f,5.0f,5.0f,3.0f});
		table1.setSpacingBefore(10);
		
		PdfPTable table = new PdfPTable(11);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {5.0f,3.0f,5.0f, 5.0f,2.5f,3.0f,2.0f,3.0f,2.0f,3.0f,3.0f});
		
		
		
		
		if(revenueDetailReport.size()!=0){
             
			if(headerCheck) {
				reportHeaderName(table1);
				
				
				
				PdfPCell cell1 = new PdfPCell();
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setPadding(4);
				cell1.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
				cell1.setPhrase(new Phrase("", SUBFONT));
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase("", SUBFONT));
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase("", SUBFONT));
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase("", SUBFONT));
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase("", SUBFONT));
				table1.addCell(cell1);
				
				cell1.setBorder(Rectangle.TOP  | Rectangle.LEFT | Rectangle.RIGHT);
				cell1.setPhrase(new Phrase("Base ", SUBFONT));
				table1.addCell(cell1);
				
				cell1.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
				cell1.setPhrase(new Phrase((String) request.getSession().getAttribute("tax1Name"),SUBFONT));
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase((String) request.getSession().getAttribute("tax2Name"),SUBFONT));
				table1.addCell(cell1);
				
				
				
				
				cell1.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
				cell1.setPhrase(new Phrase("Total", SUBFONT));
				table1.addCell(cell1);
				document.add(table1);
				
				PdfPCell cell = new PdfPCell();
				cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(4);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				

				cell.setPhrase(new Phrase("Particulars",SUBFONT));
				table.addCell(cell);

				cell.setPhrase(new Phrase("Room",SUBFONT));
				table.addCell(cell);
				
				cell.setPhrase(new Phrase("Invoice No.",SUBFONT));
				table.addCell(cell);

				cell.setPhrase(new Phrase("Guest",SUBFONT));
				table.addCell(cell);

				cell.setPhrase(new Phrase("Pax",SUBFONT));
				table.addCell(cell);

				cell.setPhrase(new Phrase("Amount ",SUBFONT));
				table.addCell(cell);
				
				cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				cell.setPhrase(new Phrase("% ",SUBFONT));
				table.addCell(cell);
				
				cell.setPhrase(new Phrase("Amount ",SUBFONT));
				table.addCell(cell);
				
				cell.setPhrase(new Phrase("% ",SUBFONT));
				table.addCell(cell);
				
				cell.setPhrase(new Phrase("Amount ",SUBFONT));
				table.addCell(cell);

				cell.setBorder(Rectangle.BOTTOM| Rectangle.LEFT | Rectangle.RIGHT);
				cell.setPhrase(new Phrase("Amount",SUBFONT));
				table.addCell(cell);
			
				
				headerCheck=false;
			}
			int count=1;
			Double baseTotal=0.00;
			Double tax1Total=0.00;
			Double tax2Total=0.00;
			Double tax3Total=0.00;
			Double TotalAmount=0.00;
			
			Double grandBaseTotal=0.00;
			Double grandTax1Total=0.00;
			Double grandTax2Total=0.00;
			Double grandTax3Total=0.00;
			Double grandTotalAmount=0.00;
			
			String txnDatePrev = "";
			String txnDateCurr = "";
			
			int paxTotal = 0;
			int paxGrandTotal = 0;
			String invoiceNoPrev = "";
			
			for(int i=0;i<revenueDetailReport.size();i++){
				Transaction dailyRevenue=revenueDetailReport.get(i);
			//for date display	
				String txnDate = dailyRevenue.getTxn_date();
				
				String invoiceNo = dailyRevenue.getInvoiceNo();
				
				if(i != 0 && !txnDatePrev.equals(txnDate)) {
					 
					 grandBaseTotal = grandBaseTotal + baseTotal;
					 grandTax1Total = grandTax1Total + tax1Total;
					 grandTax2Total = grandTax2Total + tax2Total;
					 grandTax3Total = grandTax3Total + tax3Total;
					 grandTotalAmount = grandTotalAmount + TotalAmount;
					 paxGrandTotal = paxGrandTotal +paxTotal;
					
						
					 
					 
						//for total ddispplay
						PdfPCell cellspanTotal=new PdfPCell();
						cellspanTotal.setPadding(4);
						cellspanTotal.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.BOTTOM);
						cellspanTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cellspanTotal.setColspan(5);
						cellspanTotal.setPhrase(new Phrase("TOTAL",font));
						table.addCell(cellspanTotal); 

						PdfPCell  cellBase=new PdfPCell();
						cellBase.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
						cellBase.setPhrase(new Phrase(amountFormat.format(Double.valueOf(baseTotal)),font));

						

						cellBase.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cellBase);
						
						PdfPCell  cellTax1=new PdfPCell();
						cellTax1.setColspan(2);
						cellTax1.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
						cellTax1.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax1Total)),font));
						
						cellTax1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cellTax1);
						
						PdfPCell  cellTax2=new PdfPCell();
						cellTax2.setColspan(2);
						cellTax2.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
						cellTax2.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax2Total)),font));
						
						cellTax2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cellTax2);
						
						PdfPCell  TotalAmountC=new PdfPCell();
						TotalAmountC.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
						TotalAmountC.setPhrase(new Phrase(amountFormat.format(Double.valueOf(Math.abs(TotalAmount))),font));
						
						
						TotalAmountC.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(TotalAmountC);
						
						 baseTotal=0.00;
						 tax1Total=0.00;
						 tax2Total=0.00;
						 tax3Total=0.00;
						 TotalAmount=0.00;
				
						 paxTotal = 0;
						
					 }
					 
					
				
				
				if(!txnDatePrev.equals(txnDate))
				{
					
					txnDatePrev = txnDate;
					PdfPCell cellspan=new PdfPCell();
					cellspan.setColspan(11);
					cellspan.setPadding(4);
					String dateFormat=reportTmpl.getDateFormat();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = sdf.parse(txnDate);
					DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);
					cellspan.setPhrase(new Phrase("Date: ".concat(String.valueOf(simpleDateFormatHotelDate.format(date))),font));
					cellspan.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.BOTTOM |Rectangle.RIGHT);
					table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_LEFT);
					
				
					
				}
			
					
				//for data display
				PdfPCell cellContent = new PdfPCell();
				cellContent.setPadding(5);
				
				
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase(dailyRevenue.getAccMstName(), contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellContent.setPhrase(new Phrase(dailyRevenue.getRoomNumber(), contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellContent.setPhrase(new Phrase(dailyRevenue.getInvoiceNo(), contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase(dailyRevenue.getFirstName() + " "+ dailyRevenue.getLastName(), contentFont));
					table.addCell(cellContent);

					if(!invoiceNoPrev.equals(invoiceNo))
					{
						cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cellContent.setPhrase(new Phrase(String.valueOf(dailyRevenue.getPax()), contentFont));
						table.addCell(cellContent);
						paxTotal = paxTotal + dailyRevenue.getPax() ;
						
					}
					else {
						
						cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cellContent.setPhrase(new Phrase(" ", contentFont));
						table.addCell(cellContent);
						
						
					}
					

					cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(dailyRevenue.getBase_amount())), contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(dailyRevenue.getTax1_pc())), contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(dailyRevenue.getTax1_amount())), contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(dailyRevenue.getTax2_pc())), contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(dailyRevenue.getTax2_amount())), contentFont));
					table.addCell(cellContent);
					
				
					cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(dailyRevenue.getAmount())), contentFont));
					table.addCell(cellContent);
				
				
				baseTotal= baseTotal + dailyRevenue.getBase_amount();
				tax1Total= tax1Total + dailyRevenue.getTax1_amount();
				tax2Total= tax2Total + dailyRevenue.getTax2_amount();
				TotalAmount= TotalAmount + dailyRevenue.getAmount();
			
				invoiceNoPrev = invoiceNo;
				
			
				
			}
			
			//for last total
			
			 grandBaseTotal = grandBaseTotal + baseTotal;
			 grandTax1Total = grandTax1Total + tax1Total;
			 grandTax2Total = grandTax2Total + tax2Total;
			 grandTax3Total = grandTax3Total + tax3Total;
			 grandTotalAmount = grandTotalAmount + TotalAmount;
			 
			 paxGrandTotal = paxGrandTotal +paxTotal;
				
			 
			 
				//for total ddispplay
				PdfPCell cellspanTotal=new PdfPCell();
				cellspanTotal.setPadding(4);
				cellspanTotal.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.BOTTOM);
				cellspanTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellspanTotal.setColspan(4);
				cellspanTotal.setPhrase(new Phrase("TOTAL",font));
				table.addCell(cellspanTotal); 
				
				PdfPCell  cellPaxT=new PdfPCell();
				cellPaxT.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
				cellPaxT.setPhrase(new Phrase(String.valueOf(paxTotal),font));
				cellPaxT.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellPaxT);

				PdfPCell  cellBase=new PdfPCell();
				cellBase.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
				cellBase.setPhrase(new Phrase(amountFormat.format(Double.valueOf(baseTotal)),font));

				

				cellBase.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellBase);
				
				PdfPCell  cellTax1=new PdfPCell();
				cellTax1.setColspan(2);
				cellTax1.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
				cellTax1.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax1Total)),font));
				
				cellTax1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellTax1);
				
				PdfPCell  cellTax2=new PdfPCell();
				cellTax2.setColspan(2);
				cellTax2.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
				cellTax2.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax2Total)),font));
				
				cellTax2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellTax2);
				
				PdfPCell  cellnetAmt=new PdfPCell();
				cellnetAmt.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
				cellnetAmt.setPhrase(new Phrase(amountFormat.format(Double.valueOf(Math.abs(TotalAmount))),font));
				
				
				cellnetAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellnetAmt);
				
				
             //for grand total
			PdfPCell cellTotalg=new PdfPCell();
			cellTotalg.setPadding(4);
			cellTotalg.setColspan(4);
			cellTotalg.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			cellTotalg.setPhrase(new Phrase("GRAND TOTAL",font));
			cellTotalg.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellTotalg);
			
			PdfPCell  cellGPaxT=new PdfPCell();
			cellGPaxT.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			cellGPaxT.setPhrase(new Phrase(String.valueOf(paxGrandTotal),font));
			cellGPaxT.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellGPaxT);
			
			PdfPCell cellBaseg=new PdfPCell();
			cellBaseg.setPhrase(new Phrase(amountFormat.format(Double.valueOf(grandBaseTotal)),font));
			cellBaseg.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellBaseg.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellBaseg);
			
			PdfPCell cellTax1g=new PdfPCell();
			cellTax1g.setColspan(2);
			cellTax1g.setPhrase(new Phrase(amountFormat.format(Double.valueOf(grandTax1Total)),font));
			cellTax1g.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTax1g.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellTax1g);
			
			PdfPCell cellTax2g=new PdfPCell();
			cellTax2g.setColspan(2);
			cellTax2g.setPhrase(new Phrase(amountFormat.format(Double.valueOf(grandTax2Total)),font));
			cellTax2g.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTax2g.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellTax2g);

		
			
			PdfPCell cellAmountg=new PdfPCell();
			cellAmountg.setPhrase(new Phrase(amountFormat.format(Double.valueOf(Math.abs(grandTotalAmount))),font));
			cellAmountg.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellAmountg.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellAmountg);
			
		
			
			document.add(table);

		}else{

			noDataAvailable(document);
		}
	}
	public void reportHeaderName(PdfPTable table){
		PdfPCell reportHeader = new PdfPCell();
		SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 12,    Font.BOLD);
		reportHeader.setColspan(9);
		reportHeader.setPadding(4);
		reportHeader.setPhrase(new Phrase("DETAIL FOOD REVENUE REPORT",SUBFONT));
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
