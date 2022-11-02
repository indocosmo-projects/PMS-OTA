package com.indocosmo.pms.web.reports.report_designs;

import java.util.List;
import java.util.Map;
import java.sql.Date;
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

public class ShiftWiseTxnTrferReport extends AbstractPdfViewReports {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String dateFormat;
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<Transaction> transactionTransfrList = reportTmpl.getTransactionTransfrList();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);

		PdfPTable table = new PdfPTable(12);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 1.0f, 1.75f, 1.75f, 1.75f, 1.75f, 3.0f, 3.0f, 3.0f, 2.0f, 2.0f, 3.25f, 3.0f });
		table.setSpacingBefore(10);
		if (transactionTransfrList.size() != 0) {

			PdfPCell cell = new PdfPCell();

			cell.setPadding(5);
			PdfPCell cellspan = new PdfPCell();
			cellspan.setColspan(12);
			cellspan.setPadding(5);

			cell.setPhrase(new Phrase("#", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("FROM", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TO", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("FROM", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TO", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TXN ", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TAX CODE", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("NETT", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("SHIFT", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("USER", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("TXN", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("REMARKS", font));
			table.addCell(cell);

			int count = 1;
			int i;
			for (i = 0; i < transactionTransfrList.size(); i++) {
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				table.getDefaultCell().setPadding(5.0f);
				Transaction transaction = transactionTransfrList.get(i);
				dateFormat = transactionTransfrList.get(i).getDateFormat();
				DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				java.util.Date date = sdf.parse(transaction.getTransferedDate());
				java.sql.Date sqlDate = new Date(date.getTime());

				if (i == 0 || (!transactionTransfrList.get(i).getTransferedDate()
						.equals(transactionTransfrList.get(i - 1).getTransferedDate()))) {

					cellspan.setPhrase(new Phrase("Transferred:" + simpleDateFormatHotelDate.format(sqlDate), font));
					table.addCell(cellspan);
					if (i > 0) {
						count = 1;
						table.addCell(String.valueOf(count));

						table.addCell(String.valueOf(transaction.getSourceRoom()));
					}
				}
				if (i == 0) {
					table.addCell(String.valueOf(count));

					table.addCell(String.valueOf(transaction.getSourceRoom()));
				} else if (Integer.parseInt(transactionTransfrList.get(i).getSourceRoom()) == Integer
						.parseInt(transactionTransfrList.get(i - 1).getSourceRoom())
						&& (transactionTransfrList.get(i).getTransferedDate()
								.equals(transactionTransfrList.get(i - 1).getTransferedDate()))) {
					count = count - 1;
					table.addCell("");
					table.addCell("");
				}

				else if (Integer.parseInt(transactionTransfrList.get(i).getSourceRoom()) != Integer
						.parseInt(transactionTransfrList.get(i - 1).getSourceRoom())
						&& (transactionTransfrList.get(i).getTransferedDate()
								.equals(transactionTransfrList.get(i - 1).getTransferedDate()))) {
					table.addCell(String.valueOf(count));

					table.addCell(String.valueOf(transaction.getSourceRoom()));
				}

				table.addCell(String.valueOf(transaction.getDestinationRoom()));
				table.addCell(String.valueOf(transaction.getSourceName()));
				table.addCell(String.valueOf(transaction.getDestinationName()));
				table.addCell(String.valueOf(transaction.getAcc_mst_code()));

				table.addCell(String.valueOf(transaction.getTax_code()));

				table.addCell(String.valueOf(transaction.getNett_amount()));
				table.addCell(String.valueOf(transaction.getShiftCode()));
				table.addCell(String.valueOf(transaction.getUsername()));
				table.addCell(String.valueOf(transaction.getTxn_date()));

				table.addCell(String.valueOf(transaction.getRemarks()));

				count = count + 1;
			}
		} else {
			PdfPCell cellspan = new PdfPCell();

			cellspan.setColspan(10);

			cellspan.setPadding(5);
			cellspan.setBorderWidthBottom(.5f);
			cellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
			table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		doc.add(table);
	}
}