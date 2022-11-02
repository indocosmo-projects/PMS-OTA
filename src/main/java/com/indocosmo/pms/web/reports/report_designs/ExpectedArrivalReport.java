package com.indocosmo.pms.web.reports.report_designs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.indocosmo.pms.enumerator.ReservationStatus;
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

public class ExpectedArrivalReport extends AbstractPdfViewReports{
	
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		String dateFormat;
		ReportTemplate reportTmpl=(ReportTemplate) model.get("reportTemplate");
		List<ResvHdr> resvHdrList = reportTmpl.getResvHdrList();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
	    PdfPTable table = new PdfPTable(11);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f,1.5f,3.0f,3.0f, 3.0f,3.75f,2.5f,3.0f,3.75f,2.25f,2.0f});
		table.setSpacingBefore(10);
		if(resvHdrList.size()!=0){
		PdfPCell cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPadding(5);
		PdfPCell dateCellspan=new PdfPCell();
		dateCellspan.setBorder(Rectangle.NO_BORDER);
		dateCellspan.setColspan(11);
		dateCellspan.setPadding(5);
		PdfPCell cellspan=new PdfPCell();
		cellspan.setBorder(Rectangle.NO_BORDER);
		cellspan.setColspan(3);
		cellspan.setPadding(5);
		cell.setPhrase(new Phrase("#", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Resv#", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Resv Date", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Exp Dept", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Packs", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Phone", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Resv For", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("# Rooms", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Status", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Deposit", font));
		table.addCell(cell).setHorizontalAlignment(Element.ALIGN_RIGHT);;
		cell.setPhrase(new Phrase("Mode", font));
		table.addCell(cell);
        int count=1;
		int id= resvHdrList.get(0).getResvNo();
		int i;
		
		for( i=0;i<resvHdrList.size();i++){
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setPadding(5.0f);
			table.getDefaultCell().setBorder(0);
			ResvHdr resvhdr=resvHdrList.get(i);
			dateFormat = resvHdrList.get(i).getDateFormat();
			DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);
			
			if(resvhdr.getResvNo()!=id){
				ResvHdr resvHdr = resvHdrList.get(i-1);
					
			  if(resvHdr.isPickupNeeded()==true ){
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				cellspan.setPhrase(new Phrase("Req: Pickup"));
				table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
				cellspan.setPhrase(new Phrase("Date:"+resvHdr.getPickupDate()+""));
				table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);;
				cellspan.setPhrase(new Phrase("Time:"+resvHdr.getPickupTime()+""));
				table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);;
				cellspan.setPhrase(new Phrase("Location:"+resvHdr.getPickupLocation()+""));
				table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_LEFT);;
				}
			}
			if(i==0||(!resvHdrList.get(i).getMinArrDate().equals(resvHdrList.get(i-1).getMinArrDate()))){
				dateCellspan.setPhrase(new Phrase(simpleDateFormatHotelDate.format((resvhdr.getMinArrDate())), font));
				table.addCell(dateCellspan);
				count=1;
			}
			if(resvhdr.getResvNo()!=id || i==0){
				table.addCell(String.valueOf(count));
				table.addCell(String.valueOf(resvhdr.getResvNo()));
				table.addCell(String.valueOf(simpleDateFormatHotelDate.format(resvhdr.getResvDate())));
				table.addCell(String.valueOf(simpleDateFormatHotelDate.format(resvhdr.getMaxDepartDate())));
				table.addCell(String.valueOf(resvhdr.getPacks()));
				table.addCell(String.valueOf(resvhdr.getResvByPhone()));
				table.addCell(String.valueOf(resvhdr.getResvFor()));
				table.addCell(String.valueOf(resvhdr.getNumRooms()).concat(" ").concat(String.valueOf(resvhdr.getRoomTypeCode())));
				table.addCell(ReservationStatus.get(resvhdr.getStatus()).name());
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);;
				table.addCell(String.valueOf(resvhdr.getAmount()));
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell("system");
				count=count+1;
			}else{
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell("");
				table.addCell("");
				table.addCell("");
				table.addCell("");
				table.addCell("");
				table.addCell("");
				table.addCell("");
				table.addCell(String.valueOf(resvhdr.getNumRooms()).concat(" ").concat(String.valueOf(resvhdr.getRoomTypeCode())));
				table.addCell("");
				table.addCell("");
				table.addCell("");
			}
			id=resvhdr.getResvNo();
		}
		if(resvHdrList.get(i-1).isPickupNeeded()==true ){
		ResvHdr resvHdr=resvHdrList.get(i-1);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		cellspan.setPhrase(new Phrase("Req:Pickup"));
		table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
		cellspan.setPhrase(new Phrase("Date:"+resvHdr.getPickupDate()+""));
		table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);;
		cellspan.setPhrase(new Phrase("Time:"+resvHdr.getPickupTime()+""));
		table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_CENTER);;
		cellspan.setPhrase(new Phrase("Location:"+resvHdr.getPickupLocation()+""));
		table.addCell(cellspan).setHorizontalAlignment(Element.ALIGN_LEFT);;
		}
		}else{
			PdfPCell noDataCellspan = new PdfPCell();
			noDataCellspan.setColspan(11);
			noDataCellspan.setPadding(5);
			noDataCellspan.setBorderWidthBottom(.5f);
			noDataCellspan.setPhrase(new Phrase("NO DATA AVAILABLE", font));
			table.addCell(noDataCellspan).setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		doc.add(table);
}
}