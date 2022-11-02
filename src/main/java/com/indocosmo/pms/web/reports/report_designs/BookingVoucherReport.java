package com.indocosmo.pms.web.reports.report_designs;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.common.AbstractITextPdfView;
import com.indocosmo.pms.web.common.GrcHeader;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class BookingVoucherReport extends AbstractITextPdfView {

	protected Document newDocument() {
		return new Document(PageSize.A4, 30, 30, 78, 60);
	}

	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		Document document = newDocument();
		PdfWriter writer = newWriter(document, baos);

		String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
				request.getServerPort());
		InvoiceTemplate invTmpl = new InvoiceTemplate();
		JsonObject grcData = (JsonObject) model.get("grcData");
		JsonObject companyData = grcData.getAsJsonObject("companyData");
		
		
		String  companyN = companyData.get("companyN").getAsString();
		invTmpl.setHdrLogoUrl(baseUrl + companyData.get("image").getAsString()+ "/resources/common/images/logos_"+companyN+"/niko_logo.png");
		invTmpl.setHdrLine1(companyData.get("companyName").getAsString()); 
		invTmpl.setHdrLine2(companyData.get("streetName").getAsString() + ", " + companyData.get("cityName").getAsString());
		invTmpl.setHdrLine3("Ph: "+companyData.get("phone").getAsString());
		invTmpl.setHdrLine4("E-mail:"+companyData.get("email").getAsString()+", URL:"+companyData.get("url").getAsString());
		
		
		  
		/*GrcHeader event = new GrcHeader(invTmpl);
		writer.setPageEvent(event);

		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);*/

		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();

		writeToResponse(response, baos);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		JsonObject grcData = (JsonObject) model.get("grcData");
		JsonArray resvnJson = grcData.getAsJsonArray("resvnJson");
		JsonObject companyData = grcData.getAsJsonObject("companyData");
		Font zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 20);
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Font titleFont1 = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
		Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Font contentFonthead = FontFactory.getFont(FontFactory.HELVETICA, 10,Font.BOLD);
		Font detailFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
		Font declarationFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC);
		Font signFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLDITALIC);
		Font rulesHeadFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
		Font rulesSubHeadFont = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD);
		Font rulesContentFont = FontFactory.getFont(FontFactory.HELVETICA, 8);

		NumberFormat amountFormat = new DecimalFormat("#0.00");
		

		String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
				request.getServerPort());
		String  companyN = companyData.get("companyN").getAsString();
		String imagePath = baseUrl + companyData.get("image").getAsString()+ "/resources/common/images/logos_"+companyN+"/niko_logo.png";
		
		String dateform = companyData.get("dateform").getAsString();
		DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateform);
		SimpleDateFormat tf = new SimpleDateFormat(companyData.get("timeform").getAsString());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh.mm aa");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 8);
		
		InvoiceTemplate hdft = new InvoiceTemplate();
		hdft.setHdrLogoUrl(baseUrl + companyData.get("image").getAsString()+ "/resources/common/images/logos_"+companyN+"/niko_logo.png");
		hdft.setHdrLine1(companyData.get("companyName").getAsString()); 
		hdft.setHdrLine2(companyData.get("streetName").getAsString() + ", " + companyData.get("cityName").getAsString());
		hdft.setHdrLine3("Ph: "+companyData.get("phone").getAsString());
		hdft.setHdrLine4("E-mail:"+companyData.get("email").getAsString()+", URL:"+companyData.get("url").getAsString());
		
		
		PdfPTable header = new PdfPTable(3);
		header.setWidths(new float[] { 4.0f, 3.0f, 4.0f });
		header.setTotalWidth(525);
		header.setLockedWidth(true);
		header.getDefaultCell().setFixedHeight(60);
		header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		//header.getDefaultCell().setBorder(Rectangle.BOTTOM);
		//header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
		Image logo = Image.getInstance(hdft.getHdrLogoUrl());
		header.addCell(logo);

		header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		header.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
		header.getDefaultCell().setPaddingBottom(5.0f);
		header.addCell(new Phrase(" "));

		header.getDefaultCell().setFixedHeight(65);
		header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPTable tblhdr = new PdfPTable(1);
		tblhdr.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		tblhdr.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		if (hdft.getHdrLine1() != null) {
			tblhdr.addCell(new Phrase(hdft.getHdrLine1(), font));
		}
		if (hdft.getHdrLine2() != null) {
			tblhdr.addCell(new Phrase(hdft.getHdrLine2(), font2));
		}
		if (hdft.getHdrLine3() != null) {
			tblhdr.addCell(new Phrase(hdft.getHdrLine3(), font2));
		}
		if (hdft.getHdrLine4() != null) {
			tblhdr.addCell(new Phrase(hdft.getHdrLine4(), font2));
		}

		header.addCell(tblhdr);
		header.writeSelectedRows(0, -1, 34, 830, writer.getDirectContent());
		
		
		JsonObject rsvnObj = resvnJson.get(0).getAsJsonObject();
			
			int linecount=0;
			
			PdfPTable titleTable = new PdfPTable(4);
			titleTable.setWidths(new float[] { .5f,5f, 3f ,.5f});
			titleTable.setWidthPercentage(100f);
			//titleTable.setSpacingAfter(10f);

			
			
			
			PdfPCell titleCell = new PdfPCell();
			titleCell.setBorder(Rectangle.NO_BORDER);
			titleCell.setPadding(5);
			
			titleCell.setBorder(Rectangle.LEFT|Rectangle.TOP);
			titleCell.setBorderColor(BaseColor.LIGHT_GRAY);
			titleCell.setColspan(1);
			titleCell.setPhrase(new Phrase("", titleFont));
			titleTable.addCell(titleCell);
			
			titleCell.setColspan(2);
			titleCell.setBorder(Rectangle.TOP);
			titleCell.setBorderColor(BaseColor.LIGHT_GRAY);
			titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			titleCell.setPhrase(new Phrase("BOOKING CONFIRMATION VOUCHER", titleFont1));
			titleTable.addCell(titleCell);
			
			titleCell.setBorder(Rectangle.RIGHT|Rectangle.TOP);
			titleCell.setBorderColor(BaseColor.LIGHT_GRAY);
			titleCell.setColspan(1);
			titleCell.setPhrase(new Phrase("", titleFont));
			titleTable.addCell(titleCell);
			
			linecount++;
			
			titleCell.setBorder(Rectangle.LEFT);
			titleCell.setBorderColor(BaseColor.LIGHT_GRAY);
			titleCell.setColspan(1);
			titleCell.setPhrase(new Phrase("", titleFont));
			titleTable.addCell(titleCell);
			
			titleCell.setBorder(Rectangle.NO_BORDER);
			titleCell.setColspan(2);
			titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			titleCell.setPhrase(new Phrase("Greetings from "+companyData.get("companyName").getAsString()+"", titleFont1));
			titleTable.addCell(titleCell);
			
			titleCell.setBorder(Rectangle.RIGHT);
			titleCell.setBorderColor(BaseColor.LIGHT_GRAY);
			titleCell.setColspan(1);
			titleCell.setPhrase(new Phrase("", titleFont));
			titleTable.addCell(titleCell);
			
			linecount++;
			
			titleCell.setBorder(Rectangle.LEFT);
			titleCell.setBorderColor(BaseColor.LIGHT_GRAY);
			titleCell.setColspan(1);
			titleCell.setPhrase(new Phrase("", titleFont));
			titleTable.addCell(titleCell);
			
			titleCell.setBorder(Rectangle.NO_BORDER);
			titleCell.setColspan(2);
			titleCell.setPhrase(new Phrase("We are delighted to confirm your Room Booking as requested by you", titleFont));
			titleTable.addCell(titleCell);
			
			titleCell.setBorder(Rectangle.RIGHT);
			titleCell.setBorderColor(BaseColor.LIGHT_GRAY);
			titleCell.setColspan(1);
			titleCell.setPhrase(new Phrase("", titleFont));
			titleTable.addCell(titleCell);
			
			linecount++;
			
			titleCell.setBorder(Rectangle.LEFT);
			titleCell.setBorderColor(BaseColor.LIGHT_GRAY);
			titleCell.setColspan(1);
			titleCell.setPhrase(new Phrase("", titleFont));
			titleTable.addCell(titleCell);
			
			titleCell.setBorder(Rectangle.NO_BORDER);
			titleCell.setColspan(1);
			java.util.Date dateconfirm = sdf.parse(rsvnObj.get("conf_date").getAsString());
			DateFormat Date = DateFormat.getDateInstance();
			String currentDate = Date.format(dateconfirm.getTime());
			/* Calendar calendar = Calendar.getInstance();
			 calendar.set(Calendar.MONTH, Calendar.JANUARY);
		     calendar.set(Calendar.DAY_OF_MONTH, 9);
		     calendar.set(Calendar.YEAR, 2015);*/
			titleCell.setPhrase(new Phrase(currentDate, titleFont));
			titleTable.addCell(titleCell);
			
			
			titleCell.setColspan(1);
			titleCell.setPhrase(new Phrase("Confirmation No: "+rsvnObj.get("resv_no").getAsString(),titleFont1));
			titleCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT|Rectangle.BOTTOM);
			titleCell.setBorderColor(BaseColor.BLACK);
			titleTable.addCell(titleCell);
			
			
			titleCell.setBorder(Rectangle.RIGHT);
			titleCell.setBorderColor(BaseColor.LIGHT_GRAY);
			titleCell.setColspan(1);
			titleCell.setPhrase(new Phrase("", titleFont));
			titleTable.addCell(titleCell);
			
			
			linecount++;
			document.add(titleTable);
			
			//header.addCell(titleTable);
			
			
			PdfPTable contentTable1 = new PdfPTable(4);
			contentTable1.setWidths(new float[] {0.5f,2f,6f,0.5f });
			contentTable1.setWidthPercentage(100f);
		//	contentTable1.setSpacingAfter(10f);

			
			PdfPCell contentcell = new PdfPCell();
			contentcell.setBorder(Rectangle.NO_BORDER);
			contentcell.setPadding(5);
			//contentcell.setFixedHeight(30);
			contentcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			PdfPCell contentcelVal= new PdfPCell();
			contentcelVal.setBorder(Rectangle.NO_BORDER);
			contentcelVal.setPadding(5);
			//contentcelVal.setFixedHeight(30);
			contentcelVal.setHorizontalAlignment(Element.ALIGN_LEFT);
			contentcelVal.setBorder(Rectangle.BOTTOM);
			contentcelVal.setBorderColor(BaseColor.BLACK);
			
			PdfPCell leftCell= new PdfPCell();
			leftCell.setBorder(Rectangle.LEFT);
			leftCell.setBorderColor(BaseColor.LIGHT_GRAY);
			leftCell.setColspan(1);
			leftCell.setPhrase(new Phrase(""));
			
			
			PdfPCell rightCell= new PdfPCell();
			rightCell.setBorder(Rectangle.RIGHT);
			rightCell.setBorderColor(BaseColor.LIGHT_GRAY);
			rightCell.setColspan(1);
			rightCell.setPhrase(new Phrase(""));
			
			linecount++;
			
			contentTable1.addCell(leftCell);
			
			
			
			contentcell.setPhrase(new Phrase("Reserved For", titleFont));
			contentTable1.addCell(contentcell);
			contentcelVal.setPhrase(new Phrase(rsvnObj.get("reserved_for").getAsString(), titleFont));
			contentTable1.addCell(contentcelVal);
			contentTable1.addCell(rightCell);
			
			linecount++;
			
			contentTable1.addCell(leftCell);
			
			contentcell.setPhrase(new Phrase("CompanyName", titleFont));
			contentTable1.addCell(contentcell);
		
			if (rsvnObj.get("company").isJsonNull()) {
				
				contentcelVal.setPhrase(new Phrase("", detailFont));
				contentTable1.addCell(contentcelVal);
			} else {
			
				contentcelVal.setPhrase(new Phrase(rsvnObj.get("company").getAsString(), detailFont));
				contentTable1.addCell(contentcelVal);
			}
		
			
			
			contentTable1.addCell(rightCell);
			
			linecount++;
			
			contentTable1.addCell(leftCell);
			
			contentcell.setPhrase(new Phrase("Arrival Date/Time", titleFont));
			contentTable1.addCell(contentcell);
			
			//java.util.Date datearr = sdf.parse(rsvnObj.get("min_arr_date").getAsString());
			String formattedarrDate = "";
			java.sql.Timestamp tstmpArrDate = java.sql.Timestamp
					.valueOf(rsvnObj.get("min_arr_date").getAsString() + " " + (rsvnObj.get("min_arr_time").getAsString()));
			formattedarrDate = dateFormat.format(tstmpArrDate).toString();
			
			//contentcelVal.setPhrase(new Phrase(rsvnObj.get("min_arr_date").getAsString()+" "+rsvnObj.get("min_arr_time").getAsString(), titleFont));
			contentcelVal.setPhrase(new Phrase(formattedarrDate, titleFont));
			contentTable1.addCell(contentcelVal);
			
			contentTable1.addCell(rightCell);
			linecount++;
			
			contentTable1.addCell(leftCell);
			
			contentcell.setPhrase(new Phrase("Pick Up Details", titleFont));
			contentTable1.addCell(contentcell);
			
			if(rsvnObj.get("pickup_needed").getAsInt() == 0) {
				contentcelVal.setPhrase(new Phrase(" ", titleFont));
				contentTable1.addCell(contentcelVal);
				
			}else {
				contentcelVal.setPhrase(new Phrase("Pick Up Location: "+rsvnObj.get("pickupLocation").getAsString()+",Pick Up Time:"+rsvnObj.get("pickup_date").getAsString()+" "+rsvnObj.get("pickupTime").getAsString(), titleFont));
				contentTable1.addCell(contentcelVal);
				
			}
			
			
			contentTable1.addCell(rightCell);
			
			linecount++;
			
			
			contentTable1.addCell(leftCell);
			
			contentcell.setPhrase(new Phrase("Departure Date/Time", titleFont));
			contentTable1.addCell(contentcell);
			
			String formatteddepartDate = "";
			java.sql.Timestamp tstmpdptDate = java.sql.Timestamp
					.valueOf(rsvnObj.get("max_depart_date").getAsString() + " " + (rsvnObj.get("max_depart_time").getAsString()));
			formatteddepartDate = dateFormat.format(tstmpdptDate).toString();
			
			contentcelVal.setPhrase(new Phrase(formatteddepartDate, titleFont));
			contentTable1.addCell(contentcelVal);
			
			contentTable1.addCell(rightCell);
			linecount++;
			
			contentTable1.addCell(leftCell);
			
			contentcell.setPhrase(new Phrase("Mode Of Payment", titleFont));
			contentTable1.addCell(contentcell);
			
			if (rsvnObj.get("payment_mode").isJsonNull()) {
				
				contentcelVal.setPhrase(new Phrase("", detailFont));
				contentTable1.addCell(contentcelVal);
			} else {
			
				contentcelVal.setPhrase(new Phrase(rsvnObj.get("payment_mode").getAsString(), titleFont));
				contentTable1.addCell(contentcelVal);
			}
			
			
			
			contentTable1.addCell(rightCell);
			
			linecount++;
			
			
			contentTable1.addCell(leftCell);
			
			contentcell.setPhrase(new Phrase("Plan", titleFont));
			contentTable1.addCell(contentcell);
			
			contentcelVal.setPhrase(new Phrase(rsvnObj.get("meal_plan").getAsString(), titleFont));
			contentTable1.addCell(contentcelVal);
			
			contentTable1.addCell(rightCell);
			
			linecount++;
			
			
			contentTable1.addCell(leftCell);
			
			contentcell.setPhrase(new Phrase("Credit Card No", titleFont));
			contentTable1.addCell(contentcell);
			
			contentcelVal.setPhrase(new Phrase("", titleFont));
			contentTable1.addCell(contentcelVal);
			
			contentTable1.addCell(rightCell);
			
			linecount++;
			
			
			contentTable1.addCell(leftCell);
			
			contentcell.setPhrase(new Phrase("Expiry Date", titleFont));
			contentTable1.addCell(contentcell);
		
			contentcelVal.setPhrase(new Phrase("", titleFont));
			contentTable1.addCell(contentcelVal);
			
			contentTable1.addCell(rightCell);
			
			linecount++;
			
			
			contentTable1.addCell(leftCell);
			
			contentcell.setPhrase(new Phrase("Special Remarks", titleFont));
			contentTable1.addCell(contentcell);
		
			contentcelVal.setPhrase(new Phrase("", titleFont));
			contentTable1.addCell(contentcelVal);
			
			contentTable1.addCell(rightCell);
			
			linecount++;
			
			
			document.add(contentTable1);
			
			//header.addCell(contentTable1);
			
			
			PdfPTable contentTable2 = new PdfPTable(6);
			contentTable2.setWidths(new float[] {0.5f,2f,2f,2f,2f,0.5f});
			contentTable2.setWidthPercentage(100f);
			//contentTable2.setSpacingAfter(10f);
			
			PdfPCell cell1 = new PdfPCell();
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(4);
			cell1.setBorder(Rectangle.TOP | Rectangle.BOTTOM| Rectangle.LEFT | Rectangle.RIGHT);
			cell1.setBorderColor(BaseColor.BLACK);
			
			//blank row
			PdfPCell cell2 = new PdfPCell(new Phrase(" "));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setPadding(4);
			cell2.setColspan(4);
			cell2.setBorder(Rectangle.NO_BORDER);
			
			//blank row
			PdfPCell cellblank = new PdfPCell(new Phrase(" "));
			cellblank.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellblank.setPadding(4);
			cellblank.setColspan(6);
			cellblank.setBorder(Rectangle.BOTTOM| Rectangle.LEFT | Rectangle.RIGHT);
			cellblank.setBorderColor(BaseColor.LIGHT_GRAY);
			//blank row
			PdfPCell cellblank1 = new PdfPCell(new Phrase(" "));
			cellblank1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellblank1.setPadding(4);
			cellblank1.setColspan(6);
			cellblank1.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			cellblank1.setBorderColor(BaseColor.LIGHT_GRAY);
			
			contentTable2.addCell(leftCell);
			contentTable2.addCell(cell2);
			contentTable2.addCell(rightCell);
			
			linecount++;
			
			
			contentTable2.addCell(leftCell);
			cell1.setPhrase(new Phrase("Room Type", contentFonthead));
			contentTable2.addCell(cell1);
			
			
			
			cell1.setPhrase(new Phrase("No of Rooms", contentFonthead));
			contentTable2.addCell(cell1);
			
			
			
			cell1.setPhrase(new Phrase("Total Pax", contentFonthead));
			contentTable2.addCell(cell1);
			
			
			
			cell1.setPhrase(new Phrase("Rate", contentFonthead));
			contentTable2.addCell(cell1);
			contentTable2.addCell(rightCell);
			
			linecount++;
			
			for(int i=0;i< resvnJson.size();i++){
				JsonObject rsvnOb = resvnJson.get(i).getAsJsonObject();
				
				PdfPCell cellContent = new PdfPCell();
				cellContent.setPadding(5);
				cellContent.setBorder(Rectangle.TOP | Rectangle.BOTTOM| Rectangle.LEFT | Rectangle.RIGHT);
				cellContent.setBorderColor(BaseColor.BLACK);
				cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				contentTable2.addCell(leftCell);
				cellContent.setPhrase(new Phrase(rsvnOb.get("room_type_code").getAsString(), contentFont));
				contentTable2.addCell(cellContent);
				
				
				cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellContent.setPhrase(new Phrase(rsvnOb.get("num_rooms").getAsString(), contentFont));
				contentTable2.addCell(cellContent);
				
				
				cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellContent.setPhrase(new Phrase(rsvnOb.get("pax").getAsString(), contentFont));
				contentTable2.addCell(cellContent);
				
				cellContent.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellContent.setPhrase(new Phrase(amountFormat.format(rsvnOb.get("Rate").getAsDouble()), contentFont));
				contentTable2.addCell(cellContent);
				
				contentTable2.addCell(rightCell);
				linecount++;
				
			}
			
			//blank row
			
			contentTable2.addCell(leftCell);
			contentTable2.addCell(cell2);
			contentTable2.addCell(rightCell);
			
			linecount++;
			
			
			document.add(contentTable2);
			
			//header.addCell(contentTable2);
			
			
			PdfPTable contentTable3 = new PdfPTable(4);
			contentTable3.setWidths(new float[] {0.5f,0.5f,7.5f,0.5f });
			contentTable3.setWidthPercentage(100f);
			contentTable3.setSpacingAfter(10f);
			
			
			PdfPCell contentcell3 = new PdfPCell();
			contentcell3.setBorder(Rectangle.NO_BORDER);
			contentcell3.setPadding(5);
			contentcell3.setColspan(2);
			contentcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			PdfPCell contentcelVal3= new PdfPCell();
			contentcelVal3.setBorder(Rectangle.NO_BORDER);
			contentcelVal3.setPadding(5);
			contentcelVal3.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell contentcelVal3num= new PdfPCell();
			contentcelVal3num.setBorder(Rectangle.NO_BORDER);
			contentcelVal3num.setPadding(5);
			contentcelVal3num.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			
			contentTable3.addCell(leftCell);
			contentcell3.setPhrase(new Phrase("Trust the above is in order", contentFont));
			contentTable3.addCell(contentcell3);
			contentTable3.addCell(rightCell);
			linecount++;
			
			contentTable3.addCell(leftCell);
			contentcell3.setPhrase(new Phrase("Yours sincerely", contentFont));
			contentTable3.addCell(contentcell3);
			contentTable3.addCell(rightCell);
			linecount++;
			
			contentTable3.addCell(leftCell);
			contentcell3.setPhrase(new Phrase("Please do not hesitate to contact us for any further assistance", contentFont));
			contentTable3.addCell(contentcell3);
			contentTable3.addCell(rightCell);
			linecount++;
			
			contentTable3.addCell(leftCell);
			contentcell3.setPhrase(new Phrase("Note:", contentFont));
			contentTable3.addCell(contentcell3);
			contentTable3.addCell(rightCell);
			linecount++;
			
			contentTable3.addCell(leftCell);
			contentcelVal3num.setPhrase(new Phrase("1.", contentFont));
			contentTable3.addCell(contentcelVal3num);
			contentcelVal3.setPhrase(new Phrase(companyData.get("resv_confirm_text1").getAsString(), contentFont));
			contentTable3.addCell(contentcelVal3);
			contentTable3.addCell(rightCell);
			linecount++;
			
			contentTable3.addCell(leftCell);
			contentcelVal3num.setPhrase(new Phrase("2.", contentFont));
			contentTable3.addCell(contentcelVal3num);
			contentcelVal3.setPhrase(new Phrase(companyData.get("resv_confirm_text2").getAsString(), contentFont));
			contentTable3.addCell(contentcelVal3);
			contentTable3.addCell(rightCell);
			linecount++;
			
			contentTable3.addCell(leftCell);
			contentcelVal3num.setPhrase(new Phrase("3.", contentFont));
			contentTable3.addCell(contentcelVal3num);
			contentcelVal3.setPhrase(new Phrase(companyData.get("resv_confirm_text3").getAsString(), contentFont));
			contentTable3.addCell(contentcelVal3);
			contentTable3.addCell(rightCell);
			linecount++;
			
			contentTable3.addCell(leftCell);
			contentcelVal3num.setPhrase(new Phrase("4.", contentFont));
			contentTable3.addCell(contentcelVal3num);
			contentcelVal3.setPhrase(new Phrase(companyData.get("resv_confirm_text4").getAsString(), contentFont));
			contentTable3.addCell(contentcelVal3);
			contentTable3.addCell(rightCell);
			linecount++;
			
			contentTable3.addCell(leftCell);
			contentcelVal3num.setPhrase(new Phrase("5.", contentFont));
			contentTable3.addCell(contentcelVal3num);
			contentcelVal3.setPhrase(new Phrase(companyData.get("resv_confirm_text5").getAsString(), contentFont));
			contentTable3.addCell(contentcelVal3);
			contentTable3.addCell(rightCell);
			linecount++;
			
			contentTable3.addCell(leftCell);
			contentcelVal3.setPhrase(new Phrase(" ", contentFont));
			contentTable3.addCell(contentcelVal3);
			contentcelVal3.setPhrase(new Phrase("****************************************************************************************************************", contentFont));
			contentTable3.addCell(contentcelVal3);
			contentTable3.addCell(rightCell);
			linecount++;
			
			while(linecount<34) {
				
				//blank row
				contentTable3.addCell(cellblank1);
				linecount++;
			}
			
			contentTable3.addCell(cellblank);
			linecount++;
			
			
			document.add(contentTable3);
			
			System.out.println(linecount);
			
		/*	PdfPCell address1 =new PdfPCell(logo, false);
			address1.setBorder(Rectangle.NO_BORDER);
			address1.setPaddingTop(10);
			address1.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			address1.setBorderColor(BaseColor.LIGHT_GRAY);
			//address1.setFixedHeight(70);
			address1.setHorizontalAlignment(Element.ALIGN_CENTER);
			if (companyData.get("companyName").getAsString() != null) {
				address1.setPhrase(new Phrase(companyData.get("companyName").getAsString(), contentFont));
				header.addCell(address1);
			}
			if (companyData.get("streetName").getAsString() != null) {
				address1.setPhrase(new Phrase(companyData.get("streetName").getAsString(), contentFont));
				header.addCell(address1);
			}
			if (companyData.get("cityName").getAsString()!= null) {
				address1.setPhrase(new Phrase(companyData.get("cityName").getAsString(), contentFont));
				header.addCell(address1);
			}
			
			address1.setBorder(Rectangle.LEFT | Rectangle.RIGHT|Rectangle.BOTTOM);
			address1.setBorderColor(BaseColor.LIGHT_GRAY);
			address1.setPhrase(new Phrase("", contentFont));
			header.addCell(address1);*/
			
			/*PdfPTable tblfooterAddress = new PdfPTable(1);
			
			PdfPCell addresscell = new PdfPCell();
			addresscell.setPadding(5);
			addresscell.setBorder(Rectangle.NO_BORDER);
			addresscell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
		
			
			if (companyData.get("companyName").getAsString() != null) {
				addresscell.setPhrase(new Phrase(companyData.get("companyName").getAsString(), contentFont));
				tblfooterAddress.addCell(addresscell);
			}
			if (companyData.get("streetName").getAsString() != null) {
				addresscell.setPhrase(new Phrase(companyData.get("streetName").getAsString(), contentFont));
				tblfooterAddress.addCell(addresscell);
			}
			if (companyData.get("cityName").getAsString()!= null) {
				addresscell.setBorder(Rectangle.BOTTOM);
				addresscell.setBorderColor(BaseColor.LIGHT_GRAY);
				addresscell.setPhrase(new Phrase(companyData.get("cityName").getAsString(), contentFont));
				tblfooterAddress.addCell(addresscell);
			}
			
			
			header.addCell(tblfooterAddress);*/
			//header.writeSelectedRows(0, -1, 34, 830, writer.getDirectContent());

		//	document.add(titleTable);
		/*	for (int i = 0; i < resvnJson.size(); i++) {
				JsonObject rsvnObj = resvnJson.get(i).getAsJsonObject();
		}*/
	}

}
