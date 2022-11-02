 package com.indocosmo.pms.web.reports.report_designs;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.pms.web.common.AbstractITextPdfView;
import com.indocosmo.pms.web.common.GrcHeader;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.itextpdf.text.Chunk;
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

public class ReceptionGrcReport extends AbstractITextPdfView{

	protected Document newDocument() {
		return new Document(PageSize.A4, 30, 30, 78, 60);
	}

	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ByteArrayOutputStream byteOutPutStream = createTemporaryOutputStream();

		Document document = newDocument();
		PdfWriter writer = newWriter(document, byteOutPutStream);

		String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
				request.getServerPort());
		InvoiceTemplate invTmpl = new InvoiceTemplate();
		
		JsonObject grcData = (JsonObject) model.get("jsonGrcFormData");
		JsonObject companyData = grcData.getAsJsonObject("companyDetails");

		String  companyN = companyData.get("companyN").getAsString();
		invTmpl.setHdrLogoUrl(baseUrl + companyData.get("image").getAsString()+ "/resources/common/images/logos_"+companyN+"/niko_logo.png");

		

		invTmpl.setHdrLine1(companyData.get("companyName").getAsString());
		invTmpl.setHdrLine2(
				companyData.get("streetName").getAsString() + ", " + companyData.get("cityName").getAsString());
		invTmpl.setHdrLine3("Ph: "+companyData.get("phone").getAsString());
		invTmpl.setHdrLine4("E-mail:"+companyData.get("email").getAsString()+", URL:"+companyData.get("url").getAsString());
		GrcHeader event = new GrcHeader(invTmpl);
		writer.setPageEvent(event);

		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);

		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();

		writeToResponse(response, byteOutPutStream);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		JsonObject grcData = (JsonObject) model.get("jsonGrcFormData");
		JsonArray grcFormData = grcData.getAsJsonArray("grcDetails");
		
		JsonObject companyData = grcData.getAsJsonObject("companyDetails");


		
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE);
		Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		Font detailFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
		Font declarationFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC);
		Font signFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLDITALIC);
		Font rulesHeadFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
		Font rulesSubHeadFont = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD);
		Font rulesContentFont = FontFactory.getFont(FontFactory.HELVETICA, 8);

		for (int i = 0; i < grcFormData.size(); i++) {

			JsonObject jsonGrcFormData=grcFormData.get(i).getAsJsonObject();

			PdfPTable titleTable = new PdfPTable(2);
			titleTable.setWidths(new float[] { 5f, 2f });
			titleTable.setWidthPercentage(100f);
			titleTable.setSpacingAfter(10f);

			PdfPCell titleCell = new PdfPCell();
			titleCell.setBorder(Rectangle.NO_BORDER);
			titleCell.setPadding(2);
			titleCell.setPhrase(new Phrase("GUEST REGISTRATION FORM", titleFont));
			titleTable.addCell(titleCell);
			titleCell.setPhrase(new Phrase("No:"+jsonGrcFormData.get("checkin_no").getAsString()));
			titleTable.addCell(titleCell);
			
			document.add(titleTable);

			PdfPTable contentTable = new PdfPTable(5);
			contentTable.setWidths(new float[] { 2f, 3f, 1.5f, 3f, 1.5f });
			contentTable.setWidthPercentage(100f);

			PdfPCell contentCell = new PdfPCell();
			contentCell.setPadding(3);

			contentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			contentCell.setPhrase(new Phrase("Name: ", contentFont));
			contentTable.addCell(contentCell);
			contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
			contentCell.setPhrase(new Phrase(jsonGrcFormData.get("salutation").getAsString()+"\t"+jsonGrcFormData.get("first_name").getAsString()+" "+jsonGrcFormData.get("last_name").getAsString(), detailFont));
			contentTable.addCell(contentCell);
			contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
			contentCell.setPhrase(new Phrase("Date Of Birth: ", contentFont));
			contentTable.addCell(contentCell);
			contentCell.setPhrase(new Phrase("", detailFont));
			contentTable.addCell(contentCell);
			contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			contentCell.setPhrase(new Phrase(" ", contentFont));
			contentTable.addCell(contentCell);

			contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			contentCell.setPhrase(new Phrase("Address: ", contentFont));
			contentTable.addCell(contentCell);
			contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
			contentCell.setPhrase(new Phrase(jsonGrcFormData.get("address").getAsString(), detailFont));
			contentTable.addCell(contentCell);

			if (!jsonGrcFormData.get("passport_no").isJsonNull()) {
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				contentCell.setPhrase(new Phrase("Passport/ID No: ", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setPhrase(new Phrase(jsonGrcFormData.get("passport_no").getAsString(), contentFont));
				contentTable.addCell(contentCell);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			}else {
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				contentCell.setPhrase(new Phrase("Passport/ID No: ", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setPhrase(new Phrase("", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			}

			contentCell.setPhrase(new Phrase(" ", contentFont));
			contentTable.addCell(contentCell);

			if (!jsonGrcFormData.get("nationality").isJsonNull()) {
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				contentCell.setPhrase(new Phrase("Nationality: ", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				contentCell.setPhrase(new Phrase(jsonGrcFormData.get("nationality").getAsString(), contentFont));
				contentTable.addCell(contentCell);
			}else {
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				contentCell.setPhrase(new Phrase("Nationality: ", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				contentCell.setPhrase(new Phrase("", contentFont));
				contentTable.addCell(contentCell);
			}
			
			contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
			contentCell.setPhrase(new Phrase("Date Of Issue: ", contentFont));
			contentTable.addCell(contentCell);
			contentCell.setPhrase(new Phrase("", detailFont));
			contentTable.addCell(contentCell);
			contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			contentCell.setPhrase(new Phrase(" ", contentFont));
			contentTable.addCell(contentCell);
			
			contentCell.setColspan(1);
			contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			contentCell.setPhrase(new Phrase("Date Of Expiry:", contentFont));
			contentTable.addCell(contentCell);
			contentCell.setColspan(4);
			contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			contentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			contentCell.setPhrase(new Phrase("", detailFont));
			contentTable.addCell(contentCell);

			if (!jsonGrcFormData.get("email").isJsonNull()) {
				contentCell.setColspan(1);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				contentCell.setPhrase(new Phrase("Email: ", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				contentCell.setPhrase(new Phrase(jsonGrcFormData.get("email").getAsString(), detailFont));
				contentTable.addCell(contentCell);
				contentCell.setColspan(3);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				contentCell.setPhrase(new Phrase(" ", contentFont));
				contentTable.addCell(contentCell);
			} else {
				contentCell.setColspan(1);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				contentCell.setPhrase(new Phrase("Email: ", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setColspan(4);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				contentCell.setPhrase(new Phrase(" ", contentFont));
				contentTable.addCell(contentCell);
			}
			
		
			contentCell.setColspan(1);
			
			if (!jsonGrcFormData.get("phone").isJsonNull()) {
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				contentCell.setPhrase(new Phrase("Contact No: ", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				contentCell.setPhrase(new Phrase(jsonGrcFormData.get("phone").getAsString(), detailFont));
				contentTable.addCell(contentCell);
			}else {
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				contentCell.setPhrase(new Phrase("Contact No: ", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				contentCell.setPhrase(new Phrase("", detailFont));
				contentTable.addCell(contentCell);
			}
			
			if (!jsonGrcFormData.get("state").isJsonNull()) {
				contentCell.setPhrase(new Phrase("State: ", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setPhrase(new Phrase(jsonGrcFormData.get("state").getAsString(), detailFont));
				contentTable.addCell(contentCell);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				contentCell.setPhrase(new Phrase(" ", contentFont));
				contentTable.addCell(contentCell);
			}else {
				contentCell.setPhrase(new Phrase("State: ", contentFont));
				contentTable.addCell(contentCell);
				contentCell.setPhrase(new Phrase("", detailFont));
				contentTable.addCell(contentCell);
				contentCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				contentCell.setPhrase(new Phrase(" ", contentFont));
				contentTable.addCell(contentCell);
			}
			
			contentCell.setPadding(2);
			contentCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
			contentCell.setColspan(5);
			
			contentCell.setPhrase(new Phrase(
					"1. I have read and understood the rules and regulations of "+companyData.get("companyName").getAsString()+" printed at the back of the Registration Card and agree to"
							+ " abide by the same.",
					declarationFont));
			contentTable.addCell(contentCell);
			contentCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			contentCell.setPhrase(new Phrase(
					"2. I am accepting the management's advice to keep all the valuables in the safe deposit locker provided by the hotel and any loss of"
							+ " money or valuable items from the room or hotel premises will be entirely on my risk and the hotel is not responsible for the same.",
					declarationFont));
			contentTable.addCell(contentCell);
			contentCell.setPhrase(new Phrase(
					"3. If fail to check out at the specified time, I authorize the management to remove my belongings to the "
							+ '"' + "Left Luggage Room" + '"' + ".",
					declarationFont));
			contentTable.addCell(contentCell);
			contentCell.setPhrase(
					new Phrase("4. I also agree to pay all the bills on presentation either by Cash or Credit Card. ",
							declarationFont));
			contentTable.addCell(contentCell);
			contentCell.setPhrase(new Phrase(
					"5. By providing your phone / email address you agree to receive sms and/or email communication from us.",
					declarationFont));
			contentTable.addCell(contentCell);
			contentCell.setPadding(4);
			contentCell.setPhrase(new Phrase(" ", contentFont));
			contentTable.addCell(contentCell);
			contentCell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.RIGHT);
			contentCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			contentCell.setPhrase(
					new Phrase("Guest's Signature                                      FOM Signature      ", signFont));
			contentTable.addCell(contentCell);
			document.add(contentTable);

			PdfPTable detailsTable = new PdfPTable(8);
			detailsTable.setWidthPercentage(100f);
			detailsTable.setWidths(new float[] { 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f});

			PdfPCell detailsCell = new PdfPCell();
			detailsCell.setPadding(4);

			detailsCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			detailsCell.setPhrase(new Phrase("Room No.", contentFont));
			detailsTable.addCell(detailsCell);
			detailsCell.setPhrase(new Phrase("Arrival Date & Time", contentFont));
			detailsTable.addCell(detailsCell);
			detailsCell.setPhrase(new Phrase("Departure Date & Time", contentFont));
			detailsTable.addCell(detailsCell);
			detailsCell.setPhrase(new Phrase("Room Type", contentFont));
			detailsTable.addCell(detailsCell);
			detailsCell.setPhrase(new Phrase("Rate", contentFont));
			detailsTable.addCell(detailsCell);
			detailsCell.setPhrase(new Phrase("Resv No", contentFont));
			detailsTable.addCell(detailsCell);
			detailsCell.setPhrase(new Phrase("Room Nights", contentFont));
			detailsTable.addCell(detailsCell);
			
			
			detailsCell.setPhrase(new Phrase("Advance Paid", contentFont));
			detailsTable.addCell(detailsCell);
		
			if(!jsonGrcFormData.get("room_number").isJsonNull()) {
				detailsCell.setPhrase(new Phrase(jsonGrcFormData.get("room_number").getAsString(), contentFont));
				detailsTable.addCell(detailsCell);
			}else {
				detailsCell.setPhrase(new Phrase(""));
				detailsTable.addCell(detailsCell);
			}
			if(!(jsonGrcFormData.get("arr_date").isJsonNull())) {
				detailsCell.setPhrase(new Phrase(jsonGrcFormData.get("arr_date").getAsString()+"\n"+jsonGrcFormData.get("arr_time").getAsString(), contentFont));
				detailsTable.addCell(detailsCell);
			}else {
				detailsCell.setPhrase(new Phrase(""));
				detailsTable.addCell(detailsCell);
			}
			if(!(jsonGrcFormData.get("exp_depart_date").isJsonNull())) {
				detailsCell.setPhrase(new Phrase(jsonGrcFormData.get("exp_depart_date").getAsString()+"\n"+jsonGrcFormData.get("exp_depart_time").getAsString(), contentFont));
				detailsTable.addCell(detailsCell);
			}else {
				detailsCell.setPhrase(new Phrase(""));
				detailsTable.addCell(detailsCell);
			}
			if(!(jsonGrcFormData.get("room_type_code").isJsonNull())) {
				detailsCell.setPhrase(new Phrase(jsonGrcFormData.get("room_type_code").getAsString(), contentFont));
				detailsTable.addCell(detailsCell);
			}else {
				detailsCell.setPhrase(new Phrase(""));
				detailsTable.addCell(detailsCell);
			}
			detailsCell.setPhrase(new Phrase("", contentFont));
			detailsTable.addCell(detailsCell);
			detailsCell.setPhrase(new Phrase(" ", contentFont));
			detailsTable.addCell(detailsCell);
			if(!(jsonGrcFormData.get("num_nights").isJsonNull())) {
				detailsCell.setPhrase(new Phrase(jsonGrcFormData.get("num_nights").getAsString(), contentFont));
				detailsTable.addCell(detailsCell);
			}else {
				detailsCell.setPhrase(new Phrase(""));
				detailsTable.addCell(detailsCell);
			}
			if(!(jsonGrcFormData.has("totalAdvance"))) {
				detailsCell.setPhrase(new Phrase(""));
				detailsTable.addCell(detailsCell);
			}else {
			if(!(jsonGrcFormData.get("totalAdvance").isJsonNull()) && jsonGrcFormData.get("acc_mst_code").getAsString().equals("DEPOSIT")) {
				detailsCell.setPhrase(new Phrase(jsonGrcFormData.get("totalAdvance").getAsString(), contentFont));
				detailsTable.addCell(detailsCell);
			}else {
				detailsCell.setPhrase(new Phrase(""));
				detailsTable.addCell(detailsCell);
			}
			}
			
		//	detailsCell.setPhrase(new Phrase(jsonGrcFormData.get("num_nights").getAsString(), contentFont));
		//	detailsTable.addCell(detailsCell);
			
			detailsCell.setPhrase(new Phrase(" ", contentFont));
			detailsTable.addCell(detailsCell);

			document.add(detailsTable);

			PdfPTable rulesTable = new PdfPTable(3);
			rulesTable.setSpacingBefore(6f);
			rulesTable.setWidthPercentage(100f);
			rulesTable.setWidths(new float[] { 1f, 1f, 1f });

			PdfPCell rulesCell = new PdfPCell();

			rulesCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			rulesCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.BOTTOM);
			Phrase p = new Phrase("Rules and Regulations", rulesHeadFont);
			p.add(new Chunk("\n"));
			p.add(new Chunk(
					"\nThe following are the terms and conditions of the agreement"
							+ " between the guest(s) and "+companyData.get("companyName").getAsString()+" (hereafter called the management)"
							+ " regarding the services provided to the guest and which will be binding upon them.",
					rulesContentFont));
			p.add(new Chunk("\n"));
			p.add(new Chunk("\n1. RIGHTS OF THE MANAGEMENT", rulesSubHeadFont));
			p.add(new Chunk("\nIt is agreed that the guest will conduct him/herself in"
					+ " a respectable manner and not cause any disturbance"
					+ " within the hotel premises. The management reserves"
					+ " to itself absolute right of admission to any person in the hotel premises."
					+ " The management has the right to change the room allocated to the guest any time."
					+ " Without assigning any reason thereof and without any previous notice, or to shift the guest to"
					+ " any suitable position, the management claims absolute control of the whole of the hotel premises.",
					rulesContentFont));
			p.add(new Chunk("\n"));
			p.add(new Chunk("\n2. GUEST BELONGINGS", rulesSubHeadFont));
			p.add(new Chunk(" "));
			p.add(new Chunk(
					"\nGuests are particularly requested to secure the bolts of the doors and windows of their rooms when"
							+ " going out or when going to bed and not to leave the key in the key hole. For the convenience of the guests,"
							+ " private lockers are available in rooms. The management will in no way whatsoever, be responsible for  any"
							+ " loss and / or damage to the guests cash belongings or any causes whatsoever including theft or pilferage.",
					rulesContentFont));
			p.add(new Chunk("\n"));
			p.add(new Chunk("\n3. RELATION BETWEEN THE MANAGEMENT AND GUESTS", rulesSubHeadFont));
			p.add(new Chunk(
					"\nThe hotel management shall always be deemed to be in possession and control of the whole of the hotel"
							+ " premises and every part thereof.",
					rulesContentFont));
			p.add(new Chunk("\n"));
			p.add(new Chunk("\n4. ADMISSION AND TARIFF", rulesSubHeadFont));
			p.add(new Chunk("\nGuest should fill in and sign the guest registration card at the reception and obtain a "
					+ '"' + "Key Card" + '"' + " with Tariff Card and key of the room.", rulesContentFont));
			rulesTable.addCell(p);
			rulesCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
			Phrase p1 = new Phrase("5. GUEST'S VISITORS", rulesSubHeadFont);
			p1.add(new Chunk(
					"\nThe visitors or servants of guest are not allowed in the room at any time during the day or night."
							+ " They may be entertained in the lounge.",
					rulesContentFont));
			p1.add(new Chunk("\n"));
			p1.add(new Chunk("\n6. FOOD & BEVERAGE", rulesSubHeadFont));
			p1.add(new Chunk(
					"\nOrdinarily food or liquor of any kind is not allowed to be brought into the hotel. In case food or"
							+ " beverages are brought in and consumed in the hotel, the hotel is entitled to make an extra charge.",
					rulesContentFont));
			p1.add(new Chunk("\n"));
			p1.add(new Chunk("\n7. SETTLEMENTS IN BILL", rulesSubHeadFont));
			p1.add(new Chunk("\nBills must be settled on presentation by cash or Credit Card. Cheque are not accepted.",
					rulesContentFont));
			p1.add(new Chunk("\n"));
			p1.add(new Chunk("\n8. MANAGEMENT'S LIEN IN GUEST LUGGAUGE AND BELONGINGS.", rulesSubHeadFont));
			p1.add(new Chunk(
					"\nIn the case of default in payment of dues by a guest, the management shall have a lien on the luggage and belongings, and"
							+ " the entitled to detain the same or to sell or auction such property at any time without reference to the guests and appropriate the"
							+ " net sale proceeds towards the amount due by the guest without prejudice to the management's rights to adopt such further recovery"
							+ " proceedings as may be required.",
					rulesContentFont));
			p1.add(new Chunk("\n"));
			p1.add(new Chunk("\n9. CHECK IN/CHECK OUT TIME IS 12 NOON", rulesSubHeadFont));
			p1.add(new Chunk(
					"\nThe guest shall vacate the room allotted to him on expiry of the period of occupation granted to the"
							+ " guest. Should the guest fail to vacate the room on expiry of the period the management has the right to remove"
							+ " the guest and his/her belongings from the room occupied by the guest.",
					rulesContentFont));
			p1.add(new Chunk("\n"));
			p1.add(new Chunk("\n10. GAMBLING", rulesSubHeadFont));
			p1.add(new Chunk(
					"\nGambling or any sort - which is not allowed as per government rules-is prohibited in the hotel premises and the"
							+ " management will not be responsible for the consequence arising thereof.",
					rulesContentFont));
			p1.add(new Chunk("\n"));
			p1.add(new Chunk("\n11. HAZARDOUS GOODS", rulesSubHeadFont));
			p1.add(new Chunk(
					"\nBringing into and / or sorting of raw or exposed cinema films or any other articles of combustible or"
							+ " hazardous nature and / or prohibited goods and / or",
					rulesContentFont));
			rulesTable.addCell(p1);
			rulesCell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
			Phrase p2 = new Phrase(
					"goods of objectionable nature, is prohibited. The guest shall be solely liable and responsible to the management."
							+ " Its other guests, invitees, visitors, agents and servants for all loss, financial or other, damage that may be caused by"
							+ " such articles or as a result of the guest's own negligence and non-observance of any and all instructions.",
					rulesContentFont);
			p2.add(new Chunk("\n"));
			p2.add(new Chunk("\n12. USE OF HOTEL FACILITIES", rulesSubHeadFont));
			p2.add(new Chunk(
					"\nThe guests shall use all the facilities and the services available at the hotel with care and caution"
							+ " and entirely, at the guest's risk. The guest agrees to abide by and follow all instructions placed by the"
							+ " management at various places in the hotel premises. The management shall not be responsible for any injury"
							+ " to the guest or damage to the guest's goods that may be caused as result of use of the facilities or services for"
							+ " any reason whatsoever.",
					rulesContentFont));
			p2.add(new Chunk("\n"));
			p2.add(new Chunk("\n13. ELECTRICITY AND WATER", rulesSubHeadFont));
			p2.add(new Chunk(
					"\nGuests are specially requested to switch of the lights, fan, A/c units etc. Whenever not in use."
							+ " This is not only to economies the use of electricity and water but also to ensure safety of equipment's and to avoid"
							+ " inconvenience to other residents. No heaters will be allowed inside the room.",
					rulesContentFont));
			p2.add(new Chunk("\n"));
			p2.add(new Chunk("\n14. DAMAGE OF PROPERTY", rulesSubHeadFont));
			p2.add(new Chunk(
					"\nThe guest will be held responsible for any loss or damage or prejudice to the hotel property caused by"
							+ " themselves, their friends and any person for whom they are responsible.",
					rulesContentFont));
			p2.add(new Chunk("\n"));
			p2.add(new Chunk("\n15. APPLICATION OF LAW, GOVERNMENT RULES AND REGULATIONS", rulesSubHeadFont));
			p2.add(new Chunk(
					"\nThe guest is requested to observe, abide by conform to and be bound to all applicable acts and laws"
							+ " and the government rules and regulations in force from time to time.",
					rulesContentFont));
			p2.add(new Chunk("\n"));
			p2.add(new Chunk("\n16. AMENDMENT OF RULES", rulesSubHeadFont));
			p2.add(new Chunk(
					"\nThe management reserves to itself the right to add to, or alter or amend any of the above terms,"
							+ " conditions and rules at any time without notice.",
					rulesContentFont));
			rulesTable.addCell(p2);

			document.add(rulesTable);
		}

	}
}
