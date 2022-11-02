package com.indocosmo.pms.web.reports.report_designs;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

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

public class ShiftWiseTransactionReport extends AbstractPdfViewReports {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String dateFormat;
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<Transaction> shiftWiseTransactionList = reportTmpl.getShiftWiseTransactionList();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);

		PdfPTable table = new PdfPTable(11);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 0.75f, 2.0f, 2.5f, 2.0f, 2.5f, 2.5f, 2.5f, 2.0f, 2.0f, 2.0f, 3.0f });
		table.setSpacingBefore(10);
		if (shiftWiseTransactionList.size() != 0) {

			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);

			PdfPCell cellspan = new PdfPCell();
			cellspan.setColspan(11);
			cellspan.setPadding(5);

			cell.setPhrase(new Phrase("#", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("ROOM #", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TXN CODE", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("AMOUNT", font));
			table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

			cell.setPhrase(new Phrase("TAX CODE", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TAX", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("SERVICE CHARGE", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TOTAL", font));
			table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPhrase(new Phrase("SHIFT", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("USER", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("REMARKS", font));
			table.addCell(cell);
			int count = 1;
			int i;
			for (i = 0; i < shiftWiseTransactionList.size(); i++) {
				DecimalFormat df = new DecimalFormat("#0.00");
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				table.getDefaultCell().setPadding(5.0f);
				Transaction transaction = shiftWiseTransactionList.get(i);
				dateFormat = shiftWiseTransactionList.get(i).getDateFormat();
				DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = sdf.parse(transaction.getTxn_date());
				java.sql.Date sqlDate = new Date(date.getTime());

				if (i == 0 || (!shiftWiseTransactionList.get(i).getTxn_date()
						.equals(shiftWiseTransactionList.get(i - 1).getTxn_date()))) {

					cellspan.setPhrase(new Phrase(simpleDateFormatHotelDate1.format(sqlDate), font));
					table.addCell(cellspan);
					if (i > 0) {
						count = 1;
						table.addCell(String.valueOf(count));

						table.addCell(String.valueOf(transaction.getSourceRoom()));
					}
				}

				if (transaction.getSourceRoom() == null) {
					table.addCell(String.valueOf(count));
					table.addCell("");
				} else {

					if (i == 0) {
						table.addCell(String.valueOf(count));

						table.addCell(String.valueOf(transaction.getSourceRoom()));
					}

					else if (Integer.parseInt(shiftWiseTransactionList.get(i).getSourceRoom()) == Integer
							.parseInt(shiftWiseTransactionList.get(i - 1).getSourceRoom())
							&& (shiftWiseTransactionList.get(i).getTxn_date()
									.equals(shiftWiseTransactionList.get(i - 1).getTxn_date()))) {
						count = count - 1;
						table.addCell("");
						table.addCell("");
					}

					else if (Integer.parseInt(shiftWiseTransactionList.get(i).getSourceRoom()) != Integer
							.parseInt(shiftWiseTransactionList.get(i - 1).getSourceRoom())
							&& (shiftWiseTransactionList.get(i).getTxn_date()
									.equals(shiftWiseTransactionList.get(i - 1).getTxn_date()))) {
						table.addCell(String.valueOf(count));

						table.addCell(String.valueOf(transaction.getSourceRoom()));
					}
				}
				table.addCell(String.valueOf(transaction.getAcc_mst_code()));
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(String.valueOf(df.format(transaction.getBase_amount())));
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(String.valueOf(transaction.getTax_code()));
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				if (transaction.getTax() == 0.00) {
					table.addCell("");

				} else {
					if (transaction.isInclude_tax() == true) {
						table.addCell(String.valueOf(df.format(transaction.getTax())).concat("(Incl.)"));
					} else {
						table.addCell(String.valueOf(df.format(transaction.getTax())).concat("(Excl.)"));
					}
				}
				table.addCell(String.valueOf(transaction.getServiceCharge()));
				if (transaction.getNett_amount() < 0) {
					Double nettamunt = transaction.getNett_amount() * (-1);
					table.addCell(String.valueOf(df.format(nettamunt)));
				} else {

					table.addCell(String.valueOf(df.format(transaction.getNett_amount())));
				}
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				;

				table.addCell(String.valueOf(transaction.getShiftCode()));
				table.addCell(String.valueOf(transaction.getUsername()));
				if (transaction.getRemarks() == null) {
					table.addCell("");
				} else {
					table.addCell(String.valueOf(transaction.getRemarks()));
				}
				count = count + 1;
			}

		} else {
			PdfPCell cellspan = new PdfPCell();

			cellspan.setColspan(11);

			cellspan.setPadding(5);
			cellspan.setBorderWidthBottom(.5f);
			cellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
			table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		document.add(table);
	}
}