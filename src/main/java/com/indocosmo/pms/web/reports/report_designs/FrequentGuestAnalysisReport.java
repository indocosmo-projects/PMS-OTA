package com.indocosmo.pms.web.reports.report_designs;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.ReportHeaderFooter;
import com.indocosmo.pms.web.reports.dao.ReportsDaoImpl;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.reports.model.TemplateReport;
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

public class FrequentGuestAnalysisReport extends AbstractPdfViewReports{

	
	protected Document newDocument() {
		return new Document(PageSize.A4 ,10, 20, 70, 60);
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		// Apply preferences and build metadata.
		Document document = newDocument();
		PdfWriter writer = newWriter(document, baos);
		
		HttpSession session = request.getSession();
		ReportsDaoImpl reportDao = new ReportsDaoImpl();
		TemplateReport tr = new TemplateReport();
		List<TemplateReport> reportTemplateList = reportDao.getCompanyDetails();
		tr.setName(reportTemplateList.get(0).getName());
		tr.setBuilding(reportTemplateList.get(0).getBuilding());
		tr.setStreet(reportTemplateList.get(0).getStreet());
		tr.setCity(reportTemplateList.get(0).getCity());
		tr.setState(reportTemplateList.get(0).getState());
		tr.setCountry(reportTemplateList.get(0).getCountry());
		tr.setGst(reportTemplateList.get(0).getGst());
		tr.setPrintBy(((User)session.getAttribute("userForm")).getName());
		tr.setPrintDate(new Date(commonSettings.getHotelDate().getTime()));
		
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportHeaderFooter(tr);
		ReportHeaderFooter event = new ReportHeaderFooter(reportTemplate.getReportHeaderFooter());
		writer.setPageEvent(event);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);

		// Build PDF document.
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();

		// Flush to HTTP response.
		writeToResponse(response, baos);
	}
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		JsonArray bookingDataList =  (JsonArray) model.get("bookingData");
		JsonObject data = bookingDataList.get(0).getAsJsonObject();
		JsonObject dateRange = data.get("dateRange").getAsJsonObject();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
		Font subHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
		Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 10f ,10f});

		PdfPCell headerCell = new PdfPCell();
		headerCell.setBorder(Rectangle.NO_BORDER);
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setPadding(5);
		headerCell.setPhrase(new Phrase("FREQUENT GUEST ANALYSIS REPORT", headerFont));
		table.addCell(headerCell);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setPhrase(new Phrase("Between "+dateRange.get("fromDate").getAsString()+" And "+dateRange.get("toDate").getAsString(), subHeaderFont));
		table.addCell(headerCell);
		document.add(table);
		
		PdfPTable contentTable = new PdfPTable(9);
		contentTable.setWidthPercentage(100f);
		contentTable.setWidths(new float[] { 0.5f, 3.3f, 1.2f, 2.2f, 0.9f,1f, 1f, 1.2f, 1f });
		

		PdfPCell contentCell = new PdfPCell();
		contentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		contentCell.setPadding(5);
		contentCell.setPhrase(new Phrase("#", font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("Company Name", font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("Phone", font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("Email", font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("Nights", font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("Freq.", font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("Country", font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("State", font));
		contentTable.addCell(contentCell);
		contentCell.setPhrase(new Phrase("Amount", font));
		contentTable.addCell(contentCell);
		int count = 0;
	
		if(bookingDataList.size() > 0) {
			JsonArray bookingData = data.get("bookingData").getAsJsonArray();
			if(bookingData.size() > 0) {
				for(Object obj : bookingData) {
					count++;
					JsonObject bookingObj = (JsonObject) obj;
					
					contentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					contentCell.setPadding(3);
					contentCell.setPhrase(new Phrase(String.valueOf(count), contentFont));
					contentTable.addCell(contentCell);
					contentCell.setPhrase(new Phrase(bookingObj.get("company").getAsString(), contentFont));
					contentTable.addCell(contentCell);
					contentCell.setPhrase(new Phrase(bookingObj.get("phone").getAsString(), contentFont));
					contentTable.addCell(contentCell);
					contentCell.setPhrase(new Phrase(bookingObj.get("email").getAsString(), contentFont));
					contentTable.addCell(contentCell);
					contentCell.setPhrase(new Phrase(bookingObj.get("noOfNights").getAsString(), contentFont));
					contentTable.addCell(contentCell);
					contentCell.setPhrase(new Phrase(bookingObj.get("frequency").getAsString(), contentFont));
					contentTable.addCell(contentCell);
					contentCell.setPhrase(new Phrase(bookingObj.get("nationality").getAsString(), contentFont));
					contentTable.addCell(contentCell);
					contentCell.setPhrase(new Phrase(bookingObj.get("state").getAsString(), contentFont));
					contentTable.addCell(contentCell);
					contentCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					contentCell.setPhrase(new Phrase(bookingObj.get("amount").getAsString(), contentFont));
					contentTable.addCell(contentCell);
					
				}
				
				document.add(contentTable);
			}else {
				
				PdfPTable tableNoData = new  PdfPTable(6);
				PdfPCell cell = new PdfPCell();
				cell.setColspan(6);
				cell.setPhrase(new Phrase(" "));
				cell.setBorder(Rectangle.NO_BORDER);
				tableNoData.addCell(cell);

				PdfPCell noDatacell = new PdfPCell();
				noDatacell.setPhrase(new Phrase("NO DATA AVAILABLE"));
				noDatacell.setPadding(5);
				noDatacell.setColspan(6);
				noDatacell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableNoData.addCell(noDatacell);
				document.add(tableNoData);
			}
		}
	}

}
