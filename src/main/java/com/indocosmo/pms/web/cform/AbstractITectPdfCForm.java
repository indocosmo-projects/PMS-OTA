package com.indocosmo.pms.web.cform;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.indocosmo.pms.web.cform.model.CForm;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author maria
 *
 */
public abstract class AbstractITectPdfCForm extends AbstractView {
	public AbstractITectPdfCForm() {
		setContentType("application/pdf");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.view.AbstractView#generatesDownloadContent()
	 */
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(
	 * java.util.Map, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ByteArrayOutputStream baos = createTemporaryOutputStream();
		Document document = newDocument();
		PdfWriter writer = newWriter(document, baos);
		CForm listCform = (CForm) model.get("listCform");

		if (listCform.getHeaderFooter().isHFIncl()) {
			CFormHeaderFooter event = new CFormHeaderFooter(listCform.getHeaderFooter());
			writer.setPageEvent(event);
		}
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);

		// Build PDF document.
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();

		// Flush to HTTP response.
		writeToResponse(response, baos);

	}

	/**
	 * @return
	 */
	protected Document newDocument() {
		return new Document(PageSize.A4, 36, 36, 40, 10);
	}

	/**
	 * @param document
	 * @param os
	 * @return
	 * @throws DocumentException
	 */
	protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
		return PdfWriter.getInstance(document, os);
	}

	/**
	 * @param model
	 * @param writer
	 * @param request
	 * @throws DocumentException
	 */
	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
			throws DocumentException {

		writer.setViewerPreferences(getViewerPreferences());
	}

	/**
	 * @return
	 */
	protected int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}

	/**
	 * @param model
	 * @param document
	 * @param request
	 */
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
	}

	/**
	 * @param model
	 * @param document
	 * @param writer
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

}