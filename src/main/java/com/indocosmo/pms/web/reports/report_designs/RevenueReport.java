package com.indocosmo.pms.web.reports.report_designs;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.indocosmo.pms.enumerator.PaymentMode;
import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.CashRegistersClosureReportTemplate;
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

public class RevenueReport extends AbstractPdfViewReports{
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
		double baseTaxTotal=0.00;
		double tax1AmountTotal=0.00;
		double tax2AmountTotal=0.00;
		double tax3AmountTotal=0.00;
		double totalTaxAmount=0.00;	
		double baseAmount=0.00;
		double tax1MoneyTotal=0.00;
		double tax2MoneyTotal=0.00;
		double tax3MoneyTotal=0.00;
		double totalRevenue=0.00;
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");
		String xmlReport = reportTmpl.getRevenueReport();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document documentxml = builder.parse(new InputSource(new StringReader(xmlReport)));
		documentxml.getDocumentElement().normalize();
		org.w3c.dom.NodeList txnDateNodes = documentxml.getDocumentElement().getChildNodes();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		Map<String,List<String>> summaryMap=new LinkedHashMap<String,List<String>>();
		
		PdfPTable table = new PdfPTable(9);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {3.0f,3.0f,5.0f, 2.5f,3.0f,3.0f,3.0f,3.0f,3.0f});
		table.setSpacingBefore(10);
		
		if(txnDateNodes.getLength()!=0){
             
			if(headerCheck) {
				reportHeaderName(table);
				printHeaderCol(table,request);
				headerCheck=false;
			}
			
			for(int i=0;i<txnDateNodes.getLength();i++){
				
				org.w3c.dom.Node txnDateNode = txnDateNodes.item(i);
				String txnDate = txnDateNode.getAttributes().getNamedItem("date").getNodeValue();
				String txnDateTotal = txnDateNode.getAttributes().getNamedItem("total").getNodeValue();
				String baseTotal=txnDateNode.getAttributes().getNamedItem("basetotal").getNodeValue();
				String tax1Total=txnDateNode.getAttributes().getNamedItem("tax1total").getNodeValue();
				String tax2Total=txnDateNode.getAttributes().getNamedItem("tax2total").getNodeValue();
				String tax3Total=txnDateNode.getAttributes().getNamedItem("tax3total").getNodeValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = sdf.parse(txnDate);

				
				PdfPCell cellspan=new PdfPCell();
				cellspan.setColspan(9);
				cellspan.setPadding(4);
				String dateFormat=reportTmpl.getDateFormat();
				DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);
				cellspan.setPhrase(new Phrase("Date: ".concat(String.valueOf(simpleDateFormatHotelDate.format(date))),font));
				cellspan.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.BOTTOM |Rectangle.RIGHT);
				table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_LEFT);

				txnDetails(table,txnDateNode.getFirstChild());

				PdfPCell cellspanTotal=new PdfPCell();
				cellspanTotal.setPadding(4);
				cellspanTotal.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.BOTTOM);
				cellspanTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellspanTotal.setColspan(4);
				cellspanTotal.setPhrase(new Phrase("TOTAL",font));
				table.addCell(cellspanTotal); 

				PdfPCell  cellBase=new PdfPCell();
				cellBase.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
				cellBase.setPhrase(new Phrase(amountFormat.format(Double.valueOf(baseTotal)),font));

				List<String> values=new ArrayList<String>();
				values.add(baseTotal);

				cellBase.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellBase);
				
				PdfPCell  cellTax1=new PdfPCell();
				cellTax1.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
				cellTax1.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax1Total)),font));
				values.add(tax1Total);
				cellTax1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellTax1);
				
				PdfPCell  cellTax2=new PdfPCell();
				cellTax2.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
				cellTax2.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax2Total)),font));
				values.add(tax2Total);
				cellTax2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellTax2);
				
				PdfPCell  cellTax3=new PdfPCell();
				cellTax3.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
				cellTax3.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax3Total)),font));
				values.add(tax2Total);
				cellTax3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellTax3);

				PdfPCell cellspanAmt = new PdfPCell();
				cellspanAmt.setColspan(4);
				cellspanAmt.setPaddingTop(4);
				cellspanAmt.setPaddingBottom(4);
				cellspanAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellspanAmt.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
				cellspanAmt.setPhrase(new Phrase(amountFormat.format(Double.valueOf(txnDateTotal)),font));
				table.addCell(cellspanAmt);
				values.add(txnDateTotal);
				summaryMap.put(String.valueOf(simpleDateFormatHotelDate.format(date)), values);
				
				baseTaxTotal+=Double.valueOf(baseTotal);
				baseAmount=Math.round(baseTaxTotal * 100.00) / 100.00;
				
				tax1AmountTotal+=Double.valueOf(tax1Total);
				tax1MoneyTotal=Math.round(tax1AmountTotal * 100.00) / 100.00;
				
				tax2AmountTotal+=Double.valueOf(tax2Total);
				tax2MoneyTotal=Math.round(tax2AmountTotal * 100.00) / 100.00;
				
				tax3AmountTotal+=Double.valueOf(tax3Total);
				tax3MoneyTotal=Math.round(tax3AmountTotal * 100.00) / 100.00;
				
				totalTaxAmount+=Double.valueOf(txnDateTotal);
				totalRevenue=Math.round(totalTaxAmount * 100.00) / 100.00;
			}
             
			PdfPCell cellTotal=new PdfPCell();
			cellTotal.setPadding(4);
			cellTotal.setColspan(4);
			cellTotal.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			cellTotal.setPhrase(new Phrase("GRAND TOTAL",font));
			cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellTotal);
			
			PdfPCell cellBase=new PdfPCell();
			cellBase.setPhrase(new Phrase(amountFormat.format(Double.valueOf(baseAmount)),font));
			cellBase.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellBase.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellBase);
			
			PdfPCell cellTax1=new PdfPCell();
			cellTax1.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax1MoneyTotal)),font));
			cellTax1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTax1.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellTax1);
			
			PdfPCell cellTax2=new PdfPCell();
			cellTax2.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax2MoneyTotal)),font));
			cellTax2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTax2.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellTax2);

			PdfPCell cellTax3=new PdfPCell();
			cellTax3.setPhrase(new Phrase(amountFormat.format(Double.valueOf(tax3MoneyTotal)),font));
			cellTax3.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTax3.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellTax3);
			
			PdfPCell cellAmount=new PdfPCell();
			cellAmount.setPhrase(new Phrase(amountFormat.format(Double.valueOf(totalRevenue)),font));
			cellAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellAmount.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cellAmount);
			
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
			cell.setPhrase(new Phrase("MODE OF PAYMENT",font));
			summaryTable.addCell(cell);
			

			cell.setPhrase(new Phrase("AMOUNT",font));
			summaryTable.addCell(cell);

			

			
			for(int i=0;i<txnDateNodes.getLength();i++){
				org.w3c.dom.Node txnDateNode = txnDateNodes.item(i);
				String txnDate = txnDateNode.getAttributes().getNamedItem("date").getNodeValue();
				 Double txnDateTotal = 0.00;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = sdf.parse(txnDate);
				PdfPCell cellspan=new PdfPCell();
				cellspan.setColspan(2);
				cellspan.setPadding(4);
				String dateFormat=reportTmpl.getDateFormat();
				DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);
				cellspan.setPhrase(new Phrase("Date: ".concat(String.valueOf(simpleDateFormatHotelDate1.format(date))),font));
				cellspan.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.BOTTOM |Rectangle.RIGHT);
				summaryTable.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_LEFT);
				
				cellspan.setColspan(1);
				
				for(int txnPaymentModeIndex=0;txnPaymentModeIndex<txnDateNode.getLastChild().getChildNodes().getLength();txnPaymentModeIndex++){
					org.w3c.dom.Node txnPaymentNode =txnDateNode.getLastChild().getChildNodes().item(txnPaymentModeIndex);
					cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellspan.setPhrase(new Phrase(PaymentMode.get(Integer.valueOf(txnPaymentNode.getAttributes().getNamedItem("payment_mode").getNodeValue())).getPaymentMode(), contentFont));
					summaryTable.addCell(cellspan);
					cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellspan.setPhrase(new Phrase(txnPaymentNode.getAttributes().getNamedItem("nettamount").getNodeValue(), contentFont));
					summaryTable.addCell(cellspan);
					txnDateTotal += Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("nettamount").getNodeValue());
				}
				
				
				
				cell.setPhrase(new Phrase("TOTAL",font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				summaryTable.addCell(cell);
				cell.setPhrase(new Phrase(amountFormat.format(txnDateTotal),font));
				summaryTable.addCell(cell);
			}
			
			document.add(table);
			document.add(summaryTable);
		}else{

			noDataAvailable(document);
		}
	}
	public void reportHeaderName(PdfPTable table){
		PdfPCell reportHeader = new PdfPCell();
		SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 12,    Font.BOLD);
		reportHeader.setColspan(9);
		reportHeader.setPadding(4);
		reportHeader.setPhrase(new Phrase("DAILY REVENUE REPORT",SUBFONT));
		reportHeader.setBorder(Rectangle.NO_BORDER);
		table.addCell(reportHeader).setHorizontalAlignment(Element.ALIGN_CENTER);

	}

	public void printHeaderCol(PdfPTable table,HttpServletRequest request){

		PdfPCell cell = new PdfPCell();
		cell.setPadding(4);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		cell.setPhrase(new Phrase("Particulars",SUBFONT));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Room",SUBFONT));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Guest",SUBFONT));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Pax",SUBFONT));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Base Amount",SUBFONT));
		table.addCell(cell);

		cell.setPhrase(new Phrase((String) request.getSession().getAttribute("tax1Name"),SUBFONT));
		table.addCell(cell);
		cell.setPhrase(new Phrase((String) request.getSession().getAttribute("tax2Name"),SUBFONT));
		table.addCell(cell);
		
		if(request.getSession().getAttribute("tax3Name") != null) {
			cell.setPhrase(new Phrase((String) request.getSession().getAttribute("tax3Name"),SUBFONT));
			table.addCell(cell);	
		}else {
			cell.setPhrase(new Phrase(""));
			table.addCell(cell);	
		}

		cell.setPhrase(new Phrase("Amount",SUBFONT));
		table.addCell(cell);
	}

	Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
	
	public void txnDetails(PdfPTable table,Node txnDateNode){
		
		PdfPCell cellContent = new PdfPCell();
		cellContent.setPadding(5);
		
		for(int txnPaymentIndex=0;txnPaymentIndex<txnDateNode.getChildNodes().getLength();txnPaymentIndex++){
			org.w3c.dom.Node txnPaymentNode =txnDateNode.getChildNodes().item(txnPaymentIndex);
			cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellContent.setPhrase(new Phrase(txnPaymentNode.getAttributes().getNamedItem("type").getNodeValue(), contentFont));
			table.addCell(cellContent);
			cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellContent.setPhrase(new Phrase(txnPaymentNode.getAttributes().getNamedItem("room").getNodeValue(), contentFont));
			table.addCell(cellContent);
			cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellContent.setPhrase(new Phrase(txnPaymentNode.getAttributes().getNamedItem("guest").getNodeValue(), contentFont));
			table.addCell(cellContent);

			cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellContent.setPhrase(new Phrase(txnPaymentNode.getAttributes().getNamedItem("source").getNodeValue(), contentFont));
			table.addCell(cellContent);

			cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("baseamount").getNodeValue())), contentFont));
			table.addCell(cellContent);

			cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("tax1amount").getNodeValue())), contentFont));
			table.addCell(cellContent);
			cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("tax2amount").getNodeValue())), contentFont));
			table.addCell(cellContent);
			
			if(txnPaymentNode.getAttributes().getNamedItem("tax3amount").getNodeValue() != null) {
				cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("tax3amount").getNodeValue())), contentFont));
				table.addCell(cellContent);
			}else {
				cellContent.setPhrase(new Phrase(""));
				table.addCell(cellContent);
			}

			cellContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellContent.setPhrase(new Phrase(amountFormat.format(Double.valueOf(txnPaymentNode.getAttributes().getNamedItem("amount").getNodeValue())), contentFont));
			table.addCell(cellContent);
		}
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
