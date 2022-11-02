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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class TransactionTransferReport extends AbstractPdfViewReports {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String dateFormat;
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<Transaction> transactionList = reportTmpl.getTransactionList();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);

		PdfPTable table = new PdfPTable(11);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 1.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f });
		table.setSpacingBefore(10);
		if (transactionList.size() != 0) {

			PdfPCell cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);

			cell.setPadding(5);

			PdfPCell cellspan = new PdfPCell();
			cellspan.setBorder(Rectangle.NO_BORDER);
			cellspan.setColspan(11);
			cellspan.setPadding(5);

			cell.setPhrase(new Phrase("#", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("SRC ROOM", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("DEST ROOM", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TXN CODE", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("AMOUNT", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TAX CODE", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TAX", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("SERVICE CHARGE", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("NETT", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("TXN", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("REMARKS", font));
			table.addCell(cell);
			int count = 1;
			int i;
			for (i = 0; i < transactionList.size(); i++) {
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				table.getDefaultCell().setPadding(5.0f);
				table.getDefaultCell().setBorder(0);
				Transaction transaction = transactionList.get(i);
				dateFormat = transactionList.get(i).getDateFormat();
				DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				java.util.Date date = sdf.parse(transaction.getTransferedDate());
				java.sql.Date sqlDate = new Date(date.getTime());

				if (i == 0 || (!transactionList.get(i).getTransferedDate()
						.equals(transactionList.get(i - 1).getTransferedDate()))) {

					cellspan.setPhrase(new Phrase("Transferred : " + simpleDateFormatHotelDate.format(sqlDate), font));
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
				} else if (transactionList.get(i).getSourceRoom().equals(transactionList.get(i - 1).getSourceRoom())
						&& (transactionList.get(i).getTransferedDate()
								.equals(transactionList.get(i - 1).getTransferedDate()))) {
					count = count - 1;
					table.addCell("");
					table.addCell("");
				}

				else if (!(transactionList.get(i).getSourceRoom().equals(transactionList.get(i - 1).getSourceRoom()))
						&& (transactionList.get(i).getTransferedDate()
								.equals(transactionList.get(i - 1).getTransferedDate()))) {
					table.addCell(String.valueOf(count));

					table.addCell(String.valueOf(transaction.getSourceRoom()));
				}

				table.addCell(String.valueOf(transaction.getDestinationRoom()));
				table.addCell(String.valueOf(transaction.getAcc_mst_code()));
				table.addCell(String.valueOf(transaction.getBase_amount()));

				table.addCell(String.valueOf(transaction.getTax_code()));
				table.addCell(String.valueOf(transaction.getTax()));
				table.addCell(String.valueOf(transaction.getServiceCharge()));
				table.addCell(String.valueOf(transaction.getNett_amount()));
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