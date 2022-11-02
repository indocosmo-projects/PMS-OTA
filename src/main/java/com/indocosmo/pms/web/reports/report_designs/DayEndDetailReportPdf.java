package com.indocosmo.pms.web.reports.report_designs;

import java.sql.Date;
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
import com.indocosmo.pms.web.reports.model.DayEndRport;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
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

public class DayEndDetailReportPdf extends AbstractPdfViewReports{
	private float heightHeader=40.0f;
	private String onDate = "";
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
		List<DayEndRport> dayEndReport = reportTmpl.getDayEndReport();
		NumberFormat amountFormat = new DecimalFormat("#0.00");
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		
		
		PdfPTable table = new PdfPTable(13);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.5f,4.0f,4.0f, 3.0f,3.0f,3.0f,1.5f,4.5f,3.0f,3.0f,3.0f,3.0f,3.2f});
		


		Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
		
		
		
		
		
		if(dayEndReport.size()!=0){
			SimpleDateFormat sdfyy = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date =  dayEndReport.get(0).getRptDate();
			String dateFormatrt = reportTmpl.getDateFormat();
			DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormatrt);
			onDate = String.valueOf(simpleDateFormatHotelDate.format(date));
			if(headerCheck) {
				reportHeaderName(table);
				
				PdfPCell cell1 = new PdfPCell();
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setPadding(4);
				cell1.setBorder(Rectangle.TOP  | Rectangle.BOTTOM| Rectangle.LEFT | Rectangle.RIGHT);
				cell1.setPhrase(new Phrase("Sl No", SUBFONT));
				table.addCell(cell1);
				cell1.setPhrase(new Phrase("Invoice No", SUBFONT));
				table.addCell(cell1);
				cell1.setPhrase(new Phrase("Guest Name", SUBFONT));
				table.addCell(cell1);
				cell1.setPhrase(new Phrase("Room", SUBFONT));
				table.addCell(cell1);
				/*cell1.setPhrase(new Phrase("Booking Methord", SUBFONT));
				table.addCell(cell1);*/
				cell1.setPhrase(new Phrase("Check In", SUBFONT));
				table.addCell(cell1);
			//	cell1.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
				cell1.setPhrase(new Phrase("Check Out", SUBFONT));
				table.addCell(cell1);
				cell1.setPhrase(new Phrase("Pax", SUBFONT));
				table.addCell(cell1);
				cell1.setPhrase(new Phrase("Particulars", SUBFONT));
				table.addCell(cell1);
				cell1.setPhrase(new Phrase("Payable Amount", SUBFONT));
				table.addCell(cell1);
				cell1.setPhrase(new Phrase("Amount Paid", SUBFONT));
				table.addCell(cell1);
				cell1.setPhrase(new Phrase("Refund", SUBFONT));
				table.addCell(cell1);
				cell1.setPhrase(new Phrase("Balace", SUBFONT));
				table.addCell(cell1);
				cell1.setPhrase(new Phrase("Payment Mode", SUBFONT));
				table.addCell(cell1);
				
				headerCheck=false;
			}
			int count=1;
			
			Double payableAmt=0.00;
			Double amountPaid=0.00;
			Double refundAmt=0.00;
			Double balance=0.00;
			
			Double grandAmountPaid=0.00;
			Double grandPayableAmt=0.00;
			Double grandBalance=0.00;
			Double grandRefundAmt=0.00;
			
			
			String invoiceNoPrev = "";
			String folioNoPrev = "";
			String formattedcheckin = "";
			String formattedcheckout = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh.mm aa");
			
			for(int i=0;i<dayEndReport.size();i++){
				DayEndRport dayEndRpt=dayEndReport.get(i);
			//for date display	
				String invoiceNo = dayEndRpt.getInvoiceNo();
				
				String folioNo = String.valueOf(dayEndRpt.getFolioNo());
				
				if(i != 0 && !folioNoPrev.equals(folioNo)) {
					
					count = count + 1;
					
				  }
				
				
				if(count > 1 && !folioNoPrev.equals(folioNo)) {
					
					
					
					
					
					grandAmountPaid=grandAmountPaid + amountPaid ;
					grandPayableAmt=grandPayableAmt + payableAmt;
					grandRefundAmt = grandRefundAmt +refundAmt;
					
						//for total ddispplay
						PdfPCell cellspanTotal=new PdfPCell();
						cellspanTotal.setPadding(4);
						cellspanTotal.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.BOTTOM);
						cellspanTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cellspanTotal.setColspan(8);
						cellspanTotal.setPhrase(new Phrase("TOTAL",font));
						table.addCell(cellspanTotal); 
						
						PdfPCell  cellPaxT=new PdfPCell();
						cellPaxT.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
						cellPaxT.setPhrase(new Phrase(amountFormat.format(Double.valueOf(payableAmt)),font));
						cellPaxT.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cellPaxT);

						PdfPCell  cellBase=new PdfPCell();
						cellBase.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
						cellBase.setPhrase(new Phrase(amountFormat.format(Double.valueOf(amountPaid)),font));
						cellBase.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cellBase);
						
						PdfPCell  cellBaseRefund=new PdfPCell();
						cellBase.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
						cellBase.setPhrase(new Phrase(amountFormat.format(Double.valueOf(refundAmt)),font));
						cellBase.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cellBase);
						
						PdfPCell  cellTax1=new PdfPCell();
						cellTax1.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
						cellTax1.setPhrase(new Phrase(amountFormat.format(Double.valueOf(payableAmt-(amountPaid-refundAmt))),font));
						cellTax1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cellTax1);
						
						
						PdfPCell  cellTax2=new PdfPCell();
						cellTax2.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
						cellTax2.setPhrase(new Phrase("",font));
						cellTax2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cellTax2);
						
						
						amountPaid=0.00;
						payableAmt=0.00;
						balance=0.00;
						refundAmt = 0.00;
						
					 }
					 		
			

					
				//for data display
				
				
				PdfPCell cellContent = new PdfPCell();
				cellContent.setPadding(5);
				
				
				if(!folioNoPrev.equals(folioNo))
				{
				//cellContent.setBorder(Rectangle.TOP | Rectangle.RIGHT  | Rectangle.LEFT);	
					cellContent.setBorder(Rectangle.TOP | Rectangle.RIGHT  | Rectangle.LEFT | Rectangle.BOTTOM);		
				cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellContent.setPhrase(new Phrase(String.valueOf(count), contentFont));
				table.addCell(cellContent);
				
				cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellContent.setPhrase(new Phrase(dayEndRpt.getInvoiceNo(), contentFont));
				table.addCell(cellContent);
				
				cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellContent.setPhrase(new Phrase(dayEndRpt.getFirstName()+" "+dayEndRpt.getLastName(), contentFont));
				table.addCell(cellContent);
				
				
				
				cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellContent.setPhrase(new Phrase(dayEndRpt.getRoomNumber() +" "+dayEndRpt.getRoom_tpye(), contentFont));
				table.addCell(cellContent);
				
				
				
				java.util.Date dateCheckin = sdf.parse(dayEndRpt.getCheckinDate());
				java.sql.Date sqlDateCheckin = new Date(dateCheckin.getTime());
				java.sql.Timestamp tstmpDeptDate = java.sql.Timestamp
						.valueOf(sqlDateCheckin + " " + (dayEndRpt.getCheckinTime()));
				formattedcheckin = dateFormat.format(tstmpDeptDate).toString();
				
				cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellContent.setPhrase(new Phrase(formattedcheckin, contentFont));
				table.addCell(cellContent);
				
				if(!(dayEndRpt.getCheckOutDate() == null))
				{
				java.util.Date dateCheckout = sdf.parse(dayEndRpt.getCheckOutDate());
				java.sql.Date sqlDateCheckout = new Date(dateCheckout.getTime());
				java.sql.Timestamp tstmpcheckout = java.sql.Timestamp
						.valueOf(sqlDateCheckout + " " + (dayEndRpt.getCheckOutTime()));
				formattedcheckout = dateFormat.format(tstmpcheckout).toString();
				
				cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellContent.setPhrase(new Phrase(formattedcheckout, contentFont));
				table.addCell(cellContent);
				}
				else {

					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
				}
				
				cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellContent.setPhrase(new Phrase(String.valueOf(dayEndRpt.getPax()), contentFont));
				table.addCell(cellContent);
				
				
				
				}
				else {
					
					//cellContent.setBorder( Rectangle.RIGHT  | Rectangle.LEFT);
					cellContent.setBorder( Rectangle.RIGHT  | Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					
				
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					
					
					
				}
			//	cellContent.setBorder(Rectangle.TOP | Rectangle.RIGHT |Rectangle.LEFT | Rectangle.LEFT);
				cellContent.setBorder(Rectangle.TOP | Rectangle.RIGHT |Rectangle.LEFT | Rectangle.BOTTOM);
				cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellContent.setPhrase(new Phrase(dayEndRpt.getPerticulars()+"("+String.valueOf(simpleDateFormatHotelDate.format(dayEndRpt.getTxn_date()))+")", contentFont));
				table.addCell(cellContent);
				
				if(dayEndRpt.getNettAmount()<0 && !dayEndRpt.getAccMstCode().equals("REFUND")) {
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(dayEndRpt.getAmount())), contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					 payableAmt=payableAmt + dayEndRpt.getAmount();
					
					
				}
				else if(dayEndRpt.getAccMstCode().equals("REFUND")) {
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(dayEndRpt.getAmount())), contentFont));
					table.addCell(cellContent);
					
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase(dayEndRpt.getPaymentMode(), contentFont));
					table.addCell(cellContent);
					refundAmt=refundAmt + dayEndRpt.getAmount();
					
					 
				}
				else {
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(dayEndRpt.getAmount())), contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase("", contentFont));
					table.addCell(cellContent);
					
					cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellContent.setPhrase(new Phrase(dayEndRpt.getPaymentMode(), contentFont));
					table.addCell(cellContent);
					amountPaid=amountPaid + dayEndRpt.getAmount();
					
				}
				
				//balance = payableAmt-amountPaid;
				
				
				//invoiceNoPrev = invoiceNo;
				folioNoPrev = folioNo;
			}
			
			

			
			//for last total
			
			grandAmountPaid=grandAmountPaid + amountPaid ;
			grandPayableAmt=grandPayableAmt + payableAmt;
			grandRefundAmt = grandRefundAmt +refundAmt;
			
			//for total ddispplay
			PdfPCell cellspanTotal=new PdfPCell();
			cellspanTotal.setPadding(4);
			cellspanTotal.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.BOTTOM);
			cellspanTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellspanTotal.setColspan(8);
			cellspanTotal.setPhrase(new Phrase("TOTAL",font));
			table.addCell(cellspanTotal); 
			
			PdfPCell  cellPaxT=new PdfPCell();
			cellPaxT.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			cellPaxT.setPhrase(new Phrase(String.valueOf(payableAmt),font));
			cellPaxT.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellPaxT);

			PdfPCell  cellBase=new PdfPCell();
			cellBase.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			cellBase.setPhrase(new Phrase(amountFormat.format(Double.valueOf(amountPaid)),font));
			cellBase.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellBase);
			
			PdfPCell  cellBaseR=new PdfPCell();
			cellBaseR.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			cellBaseR.setPhrase(new Phrase(amountFormat.format(Double.valueOf(refundAmt)),font));
			cellBaseR.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellBaseR);
			
			PdfPCell  cellTax1=new PdfPCell();
			cellTax1.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			cellTax1.setPhrase(new Phrase(amountFormat.format(Double.valueOf(payableAmt-(amountPaid-refundAmt))),font));
			cellTax1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellTax1);
			
			
			PdfPCell  cellTax2=new PdfPCell();
			cellTax2.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			cellTax2.setPhrase(new Phrase("",font));
			cellTax2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellTax2);
				
             //for grand total
			PdfPCell cellTotalg=new PdfPCell();
			cellTotalg.setPadding(4);
			cellTotalg.setColspan(8);
			cellTotalg.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			cellTotalg.setPhrase(new Phrase("GRAND TOTAL",font));
			cellTotalg.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellTotalg);
			
			PdfPCell cellPax=new PdfPCell();
			cellPax.setPhrase(new Phrase(amountFormat.format(Double.valueOf(grandPayableAmt)),font));
			cellPax.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellPax.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellPax);
			
			PdfPCell cellBaseg=new PdfPCell();
			cellBaseg.setPhrase(new Phrase(amountFormat.format(Double.valueOf(grandAmountPaid)),font));
			cellBaseg.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellBaseg.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellBaseg);
			
			PdfPCell cellBasegR=new PdfPCell();
			cellBasegR.setPhrase(new Phrase(amountFormat.format(Double.valueOf(grandRefundAmt)),font));
			cellBasegR.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellBasegR.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellBasegR);
			
			PdfPCell cellTax1g=new PdfPCell();
			cellTax1g.setPhrase(new Phrase(amountFormat.format(Double.valueOf(grandPayableAmt-(grandAmountPaid-grandRefundAmt))),font));
			cellTax1g.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTax1g.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellTax1g);
			
			PdfPCell cellTax2g=new PdfPCell();
			cellTax2g.setPhrase(new Phrase("",font));
			cellTax2g.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTax2g.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellTax2g);

			
			
			
			document.add(table);
			
			
			
			
			
			PdfPTable summaryTable  = new PdfPTable(2);
			summaryTable.setWidthPercentage(40.0f);
			summaryTable.setWidths(new float[] {3.0f,3.0f});
			summaryTable.setSpacingBefore(10);
			summaryTable.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell();

			cell.setColspan(2);
			cell.setPadding(4);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("SUMMARY",font));
			cell.setBorder(Rectangle.NO_BORDER);
			summaryTable.addCell(cell);
			cell.setBorder(Rectangle.BOX);
			cell.setColspan(1);


			cell.setPadding(2);
			cell.setPhrase(new Phrase("Total Cash Payment ",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getCashPaymentTotal())),font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Total Non Cash Payment",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getNonCashPaymentTotal())),font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Booking Cash Payment",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getBookingCashPaymentTotal())),font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Booking Non-Cash Payment",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getBookingNonCashPaymentTotal())),font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Expense Total",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getPettyExpenseTotal())),font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Cash Opening Balance",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getOpeningBal())),font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Cash to Office ",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getCashToOfficeTotal())),font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Total of Advance",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getDepositTotal())),font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Total Food Cost",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getFoodCostTotal())),font));
			summaryTable.addCell(cell);
			
			/*cell.setPhrase(new Phrase("Total Refund Amount",font));
			summaryTable.addCell(cell);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getRefundTotal())),font));
			summaryTable.addCell(cell);*/
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Total Complementary Amount",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getComplementaryTotal())),font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Petty Contra Amount",font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(reportTmpl.getContraTotal())),font));
			summaryTable.addCell(cell);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("Balance cash in hand",font));
			summaryTable.addCell(cell);
			//current date opening balance + cashPayment + bookingPaymentinCash+petty closing Balance
			Double balanceinhand = reportTmpl.getOpeningBal() + reportTmpl.getCashPaymentTotal()+reportTmpl.getBookingCashPaymentTotal()+reportTmpl.getPettyclosingAmount();
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPhrase(new Phrase(amountFormat.format(Double.valueOf(balanceinhand)),font));
			summaryTable.addCell(cell);
			
			
			document.add(summaryTable);
			
			

		}else{

			noDataAvailable(document);
		}
	}
	public void reportHeaderName(PdfPTable table){
		PdfPCell reportHeader = new PdfPCell();
		SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 12,    Font.BOLD);
		reportHeader.setColspan(13);
		//reportHeader.setPadding(4);
		reportHeader.setPhrase(new Phrase("DAY END REPORT(On Date : "+onDate+")",SUBFONT));
		reportHeader.setBorder(Rectangle.NO_BORDER);
		table.addCell(reportHeader).setHorizontalAlignment(Element.ALIGN_CENTER);

	}


	public void noDataAvailable(Document document) throws DocumentException{
		PdfPCell cell = new PdfPCell();
		PdfPTable table = new  PdfPTable(12);
		reportHeaderName(table);
		cell.setColspan(12);
		cell.setPhrase(new Phrase(" "));
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);

		PdfPCell noDataCell = new PdfPCell();
		noDataCell.setPhrase(new Phrase("NO DATA AVAILABLE"));
		noDataCell.setPadding(5);
		noDataCell.setColspan(12);
		noDataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(noDataCell);
		document.add(table);
	}

}
