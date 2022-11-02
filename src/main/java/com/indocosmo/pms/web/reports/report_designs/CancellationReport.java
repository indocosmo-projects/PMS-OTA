package com.indocosmo.pms.web.reports.report_designs;

import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.pms.web.reports.AbstractPdfViewReports;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.indocosmo.pms.web.reservation_test.model.ResvHdr;
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

public class CancellationReport extends AbstractPdfViewReports{

	public static final String FONT = "resources/fonts/FreeSans.ttf";
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		String dateFormat;
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");

		List<ResvHdr> resvHdrList = reportTmpl.getResvHdrList();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);

		PdfPTable table = new PdfPTable(9);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f,2.0f,3.0f,3.0f,3.0f, 3.0f,3.25f,3.0f,3.0f});
		table.setSpacingBefore(10);
		if(resvHdrList.size()!=0){

		PdfPCell cell = new PdfPCell();
        
		cell.setPadding(5);
		PdfPCell cellspan=new PdfPCell();
		cellspan.setColspan(10);
		cellspan.setPadding(5);
		
		cell.setPhrase(new Phrase("#", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Resv No", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Resv Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Exp Arrival Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Exp Depart Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Resvd By", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Phone", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Resvd For", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("#Rooms", font));
		table.addCell(cell);
		
		int count=1;
		int i;
		for( i=0;i<resvHdrList.size();i++){
			
			Font f = FontFactory.getFont(FONT, "Cp1250", true);
			f.setSize(10);
			cell.setPadding(3.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
			
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setPadding(5.0f);
			ResvHdr resvhdr=resvHdrList.get(i);
			dateFormat = resvHdrList.get(i).getDateFormat();
			DateFormat simpleDateFormatHotelDate1 = new SimpleDateFormat(dateFormat);
			
			table.addCell(String.valueOf(count));
			table.addCell(String.valueOf(resvhdr.getResvNo()));
			table.addCell(String.valueOf(simpleDateFormatHotelDate1.format(resvhdr.getResvDate())));
			table.addCell(String.valueOf(simpleDateFormatHotelDate1.format(resvhdr.getMinArrDate())));
			table.addCell(String.valueOf(simpleDateFormatHotelDate1.format(resvhdr.getMaxDepartDate())));
			table.addCell(String.valueOf(resvhdr.getResvByFirstName()).concat(" ").concat(String.valueOf(resvhdr.getResvByLastName())));
			table.addCell(String.valueOf(resvhdr.getResvByPhone()));
			table.addCell(String.valueOf(resvhdr.getResvFor()));
			table.addCell(String.valueOf(resvhdr.getNumRooms()));
			count=count+1;

		}
		}else{
			PdfPCell noDataCellspan=new PdfPCell();
			
			noDataCellspan.setColspan(10);
			
			noDataCellspan.setPadding(5);
			noDataCellspan.setBorderWidthBottom(.5f);
			noDataCellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
			table.addCell(noDataCellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		doc.add(table);
	}
}