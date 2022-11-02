package com.indocosmo.pms.web.common;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.indocosmo.pms.web.checkOut.model.Invoice;
import com.indocosmo.pms.web.checkOut.service.CheckOutServiceImp;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.transaction.model.Transaction;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This class is a work around for working with iText 5.x in Spring. The code
 * here is almost identical to the AbstractPdfView class.
 *
 */
public abstract class AbstractITextPdfView extends AbstractView {

	public AbstractITextPdfView() {
		setContentType("application/pdf");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CheckOutServiceImp checkoutService=new CheckOutServiceImp();
		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		// Apply preferences and build metadata.
		Document document = newDocument();
		PdfWriter writer = newWriter(document, baos);
		Invoice listInvoice = (Invoice) model.get("listInvoice");
		Transaction txn = (Transaction) model.get("deposit");
		SystemSettings settingsSystem = listInvoice.getSystemseting();
		
		if (listInvoice != null) {
			if (listInvoice.getPrintMode() == 1) {
				if (listInvoice.getHeaderFooter().isHFIncl()) {
					InvoiceFooter event = new InvoiceFooter(listInvoice.getHeaderFooter());
					writer.setPageEvent(event);
				}
			} else {
				InvoiceFooterPrePrinted event = new InvoiceFooterPrePrinted(listInvoice.getHeaderFooter());
				writer.setPageEvent(event);
			}
		} else if (txn != null) {
			if (txn.getHeaderFooter().isHFIncl()) {
				InvoiceFooter event = new InvoiceFooter(txn.getHeaderFooter());
				writer.setPageEvent(event);
			}
		}
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);

		// Build PDF document.
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();

		// Flush to HTTP response.
		writeToResponse(response, baos);
		if(listInvoice.getMailType()==1) {
			checkoutService.sendInvoiceMail(listInvoice.getFolioNo(),baos,settingsSystem);
		}
	}

	protected Document newDocument() {
		return new Document(PageSize.A4, 36, 36, 160, 60);
	}

	protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
		return PdfWriter.getInstance(document, os);
	}

	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
			throws DocumentException {

		writer.setViewerPreferences(getViewerPreferences());
	}

	protected int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}

	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
	}

	protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

}