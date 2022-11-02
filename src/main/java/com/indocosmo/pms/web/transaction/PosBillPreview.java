package com.indocosmo.pms.web.transaction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.common.AbstractITextPdfView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PosBillPreview extends AbstractITextPdfView {

	public static final String FONT_CURRENCY = "../../../../../../../resources/common/fonts/PlayfairDisplay-Regular.ttf";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Font font = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 9);
		Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);

		DecimalFormat numberFormat = new DecimalFormat("#0.00");

		// JsonArray orderData = (JsonArray) model.get("orderData");
		Map<String, Object> orderData = (Map<String, Object>) model.get("orderData");
		if (orderData != null) {
			Object billDetails = orderData.get("bill_details");
			JsonParser parser = new JsonParser();
			JsonObject orderHdrsArrayObj = parser.parse(billDetails.toString()).getAsJsonObject();
			JsonArray orderHdrsArray = orderHdrsArrayObj.get("order_hdrs").getAsJsonArray();

			JsonObject orderHdrsObject = orderHdrsArray.get(0).getAsJsonObject();
			JsonArray orderDtlArray = orderHdrsObject.get("order_dtls").getAsJsonArray();

			PdfPTable tabletop = new PdfPTable(2);
			tabletop.setWidths(new float[] { 2.0f, 3.0f });
			tabletop.setPaddingTop(30.0f);
			tabletop.getDefaultCell().setPaddingBottom(30f);

			PdfPHeaderCell cellspan = new PdfPHeaderCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(6);
			cellspan.setBorder(Rectangle.NO_BORDER);
			cellspan.setPhrase(new Phrase("RESTUARANT", font1));
			cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
			tabletop.addCell(cellspan);

			PdfPHeaderCell cellspan1 = new PdfPHeaderCell();
			cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan1.setPadding(6);
			cellspan1.setBorder(Rectangle.NO_BORDER);
			cellspan1.setPhrase(new Phrase("BILL", font));
			cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
			tabletop.addCell(cellspan1);

			doc.add(tabletop);

			PdfPTable tableHdr1 = new PdfPTable(2);
			tableHdr1.setPaddingTop(30.0f);
			tableHdr1.setWidths(new float[] { 2.0f, 2.0f });

			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(new BaseColor(255, 255, 255));
			cell.setPadding(3);
			tableHdr1.getDefaultCell().setPaddingBottom(10.0f);
			tableHdr1.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			tableHdr1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tableHdr1.getDefaultCell().setBorderWidthBottom(2.0f);

			String[] orderTime = (orderHdrsObject.get("order_time").toString()).split(" ");
			cell.setPhrase(new Phrase(
					"Order Date: " + ((orderHdrsObject.get("order_date")) + orderTime[1]).replace('"', ' '), font3));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHdr1.addCell(cell);

			cell.setPhrase(
					new Phrase("Order Id: " + (orderHdrsObject.get("order_id").toString()).replace('"', ' '), font3));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHdr1.addCell(cell);

			doc.add(tableHdr1);

			PdfPTable tableHdr2 = new PdfPTable(3);
			tableHdr2.setWidths(new float[] { 2.0f, 1.0f, 1.0f });

			PdfPCell cellHdr2 = new PdfPCell();
			cellHdr2.setBackgroundColor(new BaseColor(255, 255, 255));
			cellHdr2.setPadding(3);
			tableHdr2.getDefaultCell().setPaddingBottom(10.0f);
			tableHdr2.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			tableHdr2.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableHdr2.getDefaultCell().setBorderWidthBottom(2.0f);

			cellHdr2.setPhrase(
					new Phrase("Bill No: " + (orderHdrsObject.get("invoice_no").toString()).replace('"', ' '), font3));
			cellHdr2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHdr2.addCell(cellHdr2);

			cellHdr2.setPhrase(
					new Phrase("Room No: " + (orderHdrsArrayObj.get("room_no").toString()).replace('"', ' '), font3));
			tableHdr2.addCell(cellHdr2);

			cellHdr2.setPhrase(
					new Phrase("Shop Code: " + (orderHdrsObject.get("shop_code").toString()).replace('"', ' '), font2));
			tableHdr2.addCell(cellHdr2);

			doc.add(tableHdr2);

			PdfPTable table = new PdfPTable(5);
			table.setWidths(new float[] { .8f, 1.0f, 2.5f, 1.5f, 1.5f });

			PdfPCell cell1 = new PdfPCell();
			cell1.setBackgroundColor(new BaseColor(255, 255, 255));
			cell1.setPadding(5);
			table.getDefaultCell().setPaddingBottom(10.0f);
			table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			table.getDefaultCell().setBorder(Rectangle.BOTTOM);
			table.getDefaultCell().setBorderWidthBottom(2.0f);
			cell1.setPhrase(new Phrase("SI#", font1));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell1);
			cell1.setPhrase(new Phrase("QTY", font1));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell1);
			cell1.setPhrase(new Phrase("ITEM", font1));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell1);
			cell1.setPhrase(new Phrase("RATE", font1));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell1);
			cell1.setPhrase(new Phrase("TOTAL", font1));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell1);

			int count = 0;
			for (int i = 0; i < orderDtlArray.size(); i++) {
				count++;
				String sino = "" + count;

				JsonObject obj = (JsonObject) orderDtlArray.get(i);

				PdfPCell cell3 = new PdfPCell();
				cell3.setBackgroundColor(new BaseColor(255, 255, 255));
				cell3.setPadding(5);
				table.getDefaultCell().setPaddingBottom(10.0f);
				table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
				table.getDefaultCell().setBorder(Rectangle.BOTTOM);
				table.getDefaultCell().setBorderWidthBottom(2.0f);

				cell3.setPhrase(new Phrase(sino, font2));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell3);

				cell3.setPhrase(new Phrase(numberFormat.format(obj.get("qty").getAsDouble()), font2));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell3);

				cell3.setPhrase(new Phrase(obj.get("name").getAsString(), font2));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell3);

				cell3.setPhrase(new Phrase(numberFormat.format(obj.get("fixed_price").getAsDouble()), font2));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell3);

				cell3.setPhrase(new Phrase(numberFormat.format(obj.get("item_total").getAsDouble()), font2));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell3);

			}

			doc.add(table);

			PdfPTable totalTable = new PdfPTable(2);
			totalTable.setWidths(new float[] { 3.87f, 1f });

			PdfPCell cellTotal = new PdfPCell();
			cellTotal.setBackgroundColor(new BaseColor(255, 255, 255));
			cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTotal.setPadding(5);

			cellTotal.setPhrase(new Phrase("SUB TOTAL ", font2));
			totalTable.addCell(cellTotal);

			cellTotal.setPhrase(
					new Phrase(numberFormat.format(orderHdrsObject.get("detail_total").getAsDouble()), font2));
			totalTable.addCell(cellTotal);

			cellTotal.setPhrase(new Phrase("DISCOUNT AMOUNT ", font2));
			totalTable.addCell(cellTotal);

			cellTotal.setPhrase(
					new Phrase(numberFormat.format(orderHdrsObject.get("bill_discount_amount").getAsDouble()), font2));
			totalTable.addCell(cellTotal);

			/*
			 * if(orderHdrsObject.get("refund_amount").getAsDouble() != 0 ){
			 * 
			 * cellTotal.setPhrase(new Phrase("REFUND ",font2));
			 * totalTable.addCell(cellTotal);
			 * 
			 * cellTotal.setPhrase(new
			 * Phrase(numberFormat.format(orderHdrsObject.get("refund_amount").getAsDouble()
			 * ),font2)); totalTable.addCell(cellTotal);
			 * 
			 * cellTotal.setPhrase(new Phrase("NET AMOUNT ",font2));
			 * cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * totalTable.addCell(cellTotal);
			 * 
			 * cellTotal.setPhrase(new
			 * Phrase(numberFormat.format(orderHdrsObject.get("detail_total").getAsDouble()
			 * - orderHdrsObject.get("refund_amount").getAsDouble()),font2));
			 * cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * totalTable.addCell(cellTotal);
			 * 
			 * }
			 */

			List<Object> refundHeaderList = new ArrayList<>();
			refundHeaderList = (List<Object>) orderData.get("refund_details");

			double totalrefundamount = 0.0;
			if (!refundHeaderList.isEmpty()) {
				// List<Object> refundHeaderList = new ArrayList<>();
				Object refundDetailObject = null;
				// refundHeaderList = (List<Object>) orderData.get("refund_details");
				for (int i = 0; i < refundHeaderList.size(); i++) {
					refundDetailObject = refundHeaderList.get(i);
					JsonObject refundDetailJsonObject = parser.parse(refundDetailObject.toString()).getAsJsonObject();
					JsonArray refundHdrsJsonArray = refundDetailJsonObject.get("order_hdrs").getAsJsonArray();
					JsonObject refundHdrsJsonObject = refundHdrsJsonArray.get(0).getAsJsonObject();
					JsonArray refundDetailJsonArray = refundHdrsJsonObject.get("order_refund").getAsJsonArray();

					for (int j = 0; j < refundDetailJsonArray.size(); j++) {
						JsonObject obj = (JsonObject) refundDetailJsonArray.get(j);
						totalrefundamount += obj.get("refund_amount").getAsDouble();
					}
				}

				cellTotal.setPhrase(new Phrase("REFUND ", font2));
				totalTable.addCell(cellTotal);

				cellTotal.setPhrase(new Phrase(numberFormat.format(totalrefundamount), font2));
				totalTable.addCell(cellTotal);
			}
			// else {
			cellTotal.setPhrase(new Phrase("NET AMOUNT ", font2));
			cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			totalTable.addCell(cellTotal);

			double totalAmount = 0.0;
			double billDiscount = 0.0;
			double finalRoundAmount = 0.0;
			double net = 0.0;

			totalAmount = orderHdrsObject.get("total_amount").getAsDouble();
			billDiscount = orderHdrsObject.get("bill_discount_amount").getAsDouble();
			finalRoundAmount = orderHdrsObject.get("final_round_amount").getAsDouble();

			net = totalAmount - billDiscount + finalRoundAmount;
			cellTotal.setPhrase(new Phrase(numberFormat.format(net - totalrefundamount), font2));
			cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			totalTable.addCell(cellTotal);

			cellTotal.setBorder(Rectangle.NO_BORDER);
			cellTotal.setPhrase(new Phrase("* Includes tax of Rs.", font2));
			totalTable.addCell(cellTotal);

			Double taxTotal = orderHdrsObject.get("total_tax1").getAsDouble()
					+ orderHdrsObject.get("total_tax2").getAsDouble() + orderHdrsObject.get("total_tax3").getAsDouble()
					+ orderHdrsObject.get("total_gst").getAsDouble() + orderHdrsObject.get("total_sc").getAsDouble();

			cellTotal.setPhrase(new Phrase(numberFormat.format(taxTotal), font2));
			cellTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
			totalTable.addCell(cellTotal);
			// }

			doc.add(totalTable);

			double totalRefundTaxAmount = 0.0;

			if (!refundHeaderList.isEmpty()) {
				Object refundDetailObject = null;

				PdfPTable tableRefundHead = new PdfPTable(1);
				tableRefundHead.setWidths(new float[] { 3.0f });
				tableRefundHead.setPaddingTop(30.0f);
				tableRefundHead.setSpacingBefore(30f);
				tableRefundHead.getDefaultCell().setPaddingBottom(30f);

				PdfPHeaderCell cellspanRefundHead = new PdfPHeaderCell();
				cellspanRefundHead.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspanRefundHead.setPadding(6);
				// cellspanRefundHead.setColspan(5);
				cellspanRefundHead.setBorder(Rectangle.NO_BORDER);
				cellspanRefundHead.setPhrase(new Phrase("REFUND", font));
				cellspanRefundHead.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableRefundHead.addCell(cellspanRefundHead);

				doc.add(tableRefundHead);

				for (int i = 0; i < refundHeaderList.size(); i++) {
					refundDetailObject = refundHeaderList.get(i);
					JsonObject refundDetailJsonObject = parser.parse(refundDetailObject.toString()).getAsJsonObject();
					JsonArray refundHdrsJsonArray = refundDetailJsonObject.get("order_hdrs").getAsJsonArray();
					JsonObject refundHdrsJsonObject = refundHdrsJsonArray.get(0).getAsJsonObject();
					JsonArray refundDetailJsonArray = refundHdrsJsonObject.get("order_refund").getAsJsonArray();

					PdfPTable tableRefundHdr = new PdfPTable(2);
					tableRefundHdr.setPaddingTop(30.0f);
					tableRefundHdr.setWidths(new float[] { 2.0f, 2.0f });

					PdfPCell cellRefundHdr = new PdfPCell();
					cellRefundHdr.setBackgroundColor(new BaseColor(255, 255, 255));
					cellRefundHdr.setPadding(3);
					tableRefundHdr.getDefaultCell().setPaddingBottom(10.0f);
					tableRefundHdr.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
					tableRefundHdr.getDefaultCell().setBorder(Rectangle.NO_BORDER);
					tableRefundHdr.getDefaultCell().setBorderWidthBottom(2.0f);

					cellRefundHdr.setPhrase(new Phrase(
							"Refund Date: " + (refundHdrsJsonObject.get("refund_date").toString()).replace('"', ' '),
							font2));
					cellRefundHdr.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableRefundHdr.addCell(cellRefundHdr);

					String[] orderRefundTime = (refundHdrsJsonObject.get("refund_time").toString()).split(" ");
					cellRefundHdr.setPhrase(
							new Phrase("Refund Time: " + (orderRefundTime[1].toString()).replace('"', ' '), font2));
					cellRefundHdr.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableRefundHdr.addCell(cellRefundHdr);

					doc.add(tableRefundHdr);

					PdfPTable tableRefund = new PdfPTable(4);
					tableRefund.setWidths(new float[] { .8f, 1.0f, 3.5f, 1.5f, });
					// tableRefund.setSpacingAfter(30f);

					PdfPCell cellRefund = new PdfPCell();
					cellRefund.setBackgroundColor(new BaseColor(255, 255, 255));
					cellRefund.setPadding(5);
					tableRefund.getDefaultCell().setPaddingBottom(10.0f);
					tableRefund.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
					tableRefund.getDefaultCell().setBorder(Rectangle.BOTTOM);
					tableRefund.getDefaultCell().setBorderWidthBottom(2.0f);
					cellRefund.setPhrase(new Phrase("SI#", font1));
					cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableRefund.addCell(cellRefund);
					cellRefund.setPhrase(new Phrase("QTY", font1));
					cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableRefund.addCell(cellRefund);
					cellRefund.setPhrase(new Phrase("ITEM", font1));
					cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableRefund.addCell(cellRefund);
					/*
					 * cellRefund.setPhrase(new Phrase("RATE", font1));
					 * cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * tableRefund.addCell(cellRefund);
					 */
					cellRefund.setPhrase(new Phrase("TOTAL", font1));
					cellRefund.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableRefund.addCell(cellRefund);

					int number = 0;
					double refundItemTotal = 0.0;
					for (int j = 0; j < refundDetailJsonArray.size(); j++) {

						number++;
						String sino = "" + number;

						JsonObject obj = (JsonObject) refundDetailJsonArray.get(j);

						PdfPCell cellRefundDtl = new PdfPCell();
						cellRefundDtl.setBackgroundColor(new BaseColor(255, 255, 255));
						cellRefundDtl.setPadding(5);
						tableRefund.getDefaultCell().setPaddingBottom(10.0f);
						tableRefund.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
						tableRefund.getDefaultCell().setBorder(Rectangle.BOTTOM);
						tableRefund.getDefaultCell().setBorderWidthBottom(2.0f);

						cellRefundDtl.setPhrase(new Phrase(sino, font2));
						cellRefundDtl.setHorizontalAlignment(Element.ALIGN_CENTER);
						tableRefund.addCell(cellRefundDtl);

						cellRefundDtl.setPhrase(new Phrase(numberFormat.format(obj.get("qty").getAsDouble()), font2));
						cellRefundDtl.setHorizontalAlignment(Element.ALIGN_LEFT);
						tableRefund.addCell(cellRefundDtl);

						cellRefundDtl.setPhrase(new Phrase(obj.get("name").getAsString(), font2));
						cellRefundDtl.setHorizontalAlignment(Element.ALIGN_LEFT);
						tableRefund.addCell(cellRefundDtl);

						/*
						 * cellRefundDtl.setPhrase(new
						 * Phrase(numberFormat.format(obj.get("fixed_price").getAsDouble()),font2));
						 * cellRefundDtl.setHorizontalAlignment(Element.ALIGN_RIGHT);
						 * tableRefund.addCell(cellRefundDtl);
						 */

						cellRefundDtl.setPhrase(
								new Phrase(numberFormat.format(obj.get("refund_amount").getAsDouble()), font2));
						cellRefundDtl.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableRefund.addCell(cellRefundDtl);

						refundItemTotal += obj.get("refund_amount").getAsDouble();
						totalRefundTaxAmount += obj.get("tax1_amount").getAsDouble()
								+ obj.get("tax2_amount").getAsDouble() + obj.get("tax3_amount").getAsDouble();
					}
					doc.add(tableRefund);

					PdfPTable refundTotalTable = new PdfPTable(2);
					refundTotalTable.setWidths(new float[] { 3.52f, 1f });
					refundTotalTable.setSpacingAfter(20f);

					PdfPCell refundTotalCell = new PdfPCell();
					refundTotalCell.setBackgroundColor(new BaseColor(255, 255, 255));
					refundTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					refundTotalCell.setPadding(5);

					refundTotalCell.setPhrase(new Phrase("REFUND TOTAL", font2));
					refundTotalTable.addCell(refundTotalCell);

					refundTotalCell.setPhrase(new Phrase(numberFormat.format(refundItemTotal), font2));
					refundTotalTable.addCell(refundTotalCell);

					refundTotalCell.setBorder(Rectangle.NO_BORDER);
					refundTotalCell.setPhrase(new Phrase("* Refund ncludes tax of Rs.", font2));
					refundTotalTable.addCell(refundTotalCell);

					refundTotalCell.setPhrase(new Phrase(numberFormat.format(totalRefundTaxAmount), font2));
					refundTotalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					refundTotalTable.addCell(refundTotalCell);

					doc.add(refundTotalTable);
				}
			}

		} else {

			PdfPTable tableNoData = new PdfPTable(1);
			tableNoData.setWidthPercentage(100.0f);
			tableNoData.setSpacingBefore(10);

			PdfPCell cellNoData = new PdfPCell();

			cellNoData.setBackgroundColor(new BaseColor(255, 255, 255));
			cellNoData.setPadding(5);
			tableNoData.getDefaultCell().setPaddingBottom(10.0f);
			tableNoData.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			tableNoData.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableNoData.getDefaultCell().setBorderWidthBottom(2.0f);
			cellNoData.setPhrase(new Phrase("No Data Available ", font));
			cellNoData.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableNoData.addCell(cellNoData);
			doc.add(tableNoData);

		}
	}

}
