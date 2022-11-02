package com.indocosmo.pms.web.reports.report_designs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.pms.web.pettycash.model.PettyCash;
import com.indocosmo.pms.web.reports.model.ReportTemplate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;

public class CreditCardExportExcel extends AbstractExcelView {

	private static final String DATE_FORMAT="dd-MM-yyyy";
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dateFormat;
		ReportTemplate reportTmpl = (ReportTemplate) model.get("reportTemplate");
		List<PettyCash> pettyExpenseReport = reportTmpl.getPettyCashList();
		NumberFormat amountFormat = new DecimalFormat("#0.00");
		HSSFSheet sheet = workbook.createSheet("sheet");
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

		Font headNameFont = workbook.createFont();
		headNameFont.setFontName("Liberation Sans");
		headNameFont.setFontHeightInPoints((short) 13);
		headNameFont.setColor(IndexedColors.BLACK.getIndex());
		headNameFont.setBoldweight(headNameFont.BOLDWEIGHT_BOLD);

		CellStyle headNameCellStyle = workbook.createCellStyle();
		headNameCellStyle.setFont(headNameFont);
		headNameCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font detailFont = workbook.createFont();
		detailFont.setFontName("Liberation Sans");
		detailFont.setFontHeightInPoints((short) 9);
		detailFont.setColor(IndexedColors.BLACK.getIndex());
		detailFont.setBoldweight(detailFont.BOLDWEIGHT_BOLD);

		CellStyle detailFontCellStyle = workbook.createCellStyle();
		detailFontCellStyle.setFont(detailFont);
		detailFontCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font reprName = workbook.createFont();
		reprName.setFontName("Liberation Sans");

		reprName.setBoldweight((short) 15);
		reprName.setFontHeightInPoints((short) 16);
		reprName.setBoldweight(Font.BOLDWEIGHT_BOLD);
		reprName.setColor(IndexedColors.BLACK.getIndex());
		reprName.setUnderline(Font.U_SINGLE);
		CellStyle rptNameCellStyle = workbook.createCellStyle();
		rptNameCellStyle.setFont(reprName);
		rptNameCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font subheadFont = workbook.createFont();
		subheadFont.setFontName("Liberation Sans");

		subheadFont.setFontHeightInPoints((short) 12);
		subheadFont.setColor(IndexedColors.BLACK.getIndex());
		subheadFont.setBoldweight(subheadFont.BOLDWEIGHT_BOLD);

		CellStyle subheadCellStyle = workbook.createCellStyle();
		subheadCellStyle.setFont(subheadFont);
		subheadCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		
		CellStyle subheadDateStyle = workbook.createCellStyle();
		subheadDateStyle.setFont(subheadFont);
		subheadDateStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font noDataFont = workbook.createFont();
		noDataFont.setFontName("Liberation Sans");

		noDataFont.setFontHeightInPoints((short) 12);
		noDataFont.setColor(IndexedColors.BLACK.getIndex());
		noDataFont.setBoldweight(noDataFont.BOLDWEIGHT_BOLD);

		CellStyle noDataCellStyle = workbook.createCellStyle();
		noDataCellStyle.setFont(noDataFont);
		noDataCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font columnHeadFond = workbook.createFont();
		columnHeadFond.setFontName("Liberation Sans");

		columnHeadFond.setBoldweight(columnHeadFond.BOLDWEIGHT_BOLD);
		columnHeadFond.setFontHeightInPoints((short) 12);
		columnHeadFond.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headCellStyle = workbook.createCellStyle();
		headCellStyle.setFont(columnHeadFond);
		headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		headCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headCellStyle.setBorderRight(CellStyle.BORDER_THIN);

		Font contentFont = workbook.createFont();
		contentFont.setFontName("Liberation Sans");

		contentFont.setFontHeightInPoints((short) 9);
		contentFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setFont(contentFont);
		contentCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle numericCellStyle = workbook.createCellStyle();
		numericCellStyle.setFont(contentFont);
		numericCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		numericCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numericCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		
		CellStyle numericCellStyleTotal = workbook.createCellStyle();
		numericCellStyleTotal.setFont(columnHeadFond);
		numericCellStyleTotal.setAlignment(CellStyle.ALIGN_RIGHT);
		numericCellStyleTotal.setBorderTop(CellStyle.BORDER_THIN);
		numericCellStyleTotal.setBorderBottom(CellStyle.BORDER_THIN);
		numericCellStyleTotal.setBorderLeft(CellStyle.BORDER_THIN);
		numericCellStyleTotal.setBorderRight(CellStyle.BORDER_THIN);

		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 8200);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 8200);
		sheet.setColumnWidth(5, 4500);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		
		

		HSSFRow headnameRow = sheet.createRow(0);
		headnameRow.setHeightInPoints(15);
		HSSFCell companyCellname = headnameRow.createCell(0);
		companyCellname.setCellValue(reportTmpl.getCompanyname());
		companyCellname.setCellStyle(headNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

		HSSFRow buildingnameRow = sheet.createRow(1);
		buildingnameRow.setHeightInPoints(15);
		HSSFCell cellbuildingname = buildingnameRow.createCell(0);
		cellbuildingname.setCellValue(reportTmpl.getBuilding());
		cellbuildingname.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

		HSSFRow DetailnameRow = sheet.createRow(2);
		DetailnameRow.setHeightInPoints(15);
		HSSFCell DetailnameCell = DetailnameRow.createCell(0);
		DetailnameCell.setCellValue(reportTmpl.getStreet() + "," + reportTmpl.getCity());
		DetailnameCell.setCellStyle(detailFontCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));

		HSSFRow nameRow = sheet.createRow(3);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(reportTmpl.getReportname());
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));

		HSSFRow subheaderRow = sheet.createRow(4);
		subheaderRow.setHeightInPoints(25);
		HSSFCell subheadercell = subheaderRow.createCell(0);
		subheadercell.setCellValue(reportTmpl.getDateFilter());
		subheadercell.setCellStyle(subheadDateStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 6));

		if (pettyExpenseReport.size() != 0) {

			HSSFRow tableheading = sheet.createRow(5);
			tableheading.setHeightInPoints(25);

			HSSFCell cellSiNo = tableheading.createCell(0);
			cellSiNo.setCellStyle(headCellStyle);
			cellSiNo.setCellValue("Date");

			HSSFCell cellRoomNo = tableheading.createCell(1);
			cellRoomNo.setCellStyle(headCellStyle);
			cellRoomNo.setCellValue(" ");

			HSSFCell cellRoomType = tableheading.createCell(2);
			cellRoomType.setCellStyle(headCellStyle);
			cellRoomType.setCellValue("Particulars");

			HSSFCell cellGustName = tableheading.createCell(3);
			cellGustName.setCellStyle(headCellStyle);
			cellGustName.setCellValue("Voucher Type");
			
			HSSFCell cellNarration = tableheading.createCell(4);
			cellNarration.setCellStyle(headCellStyle);
			cellNarration.setCellValue("Narration");

			HSSFCell cellNationality = tableheading.createCell(5);
			cellNationality.setCellStyle(headCellStyle);
			cellNationality.setCellValue("Voucher No");

			HSSFCell cellArrivalDate = tableheading.createCell(6);
			cellArrivalDate.setCellStyle(headCellStyle);
			cellArrivalDate.setCellValue("Debit");
			
			HSSFCell cellRoomCharge = tableheading.createCell(7);
			cellRoomCharge.setCellStyle(headCellStyle);
			cellRoomCharge.setCellValue("Credit");

			int count = 1;
			Double debitTotal = 0.00;
			Double creditTotal = 0.00;
			Double closinBlnce = 0.00;
			int rowCnt = 6;
			for (int i = 0; i < pettyExpenseReport.size(); i++) {

				PettyCash pettyCash=pettyExpenseReport.get(i);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
				
				HSSFRow detailRow = sheet.createRow(6 + i);
				detailRow.setHeightInPoints(25);

				if (i == 0
						|| (!pettyCash.getEntryDate().equals(pettyExpenseReport.get(i - 1).getEntryDate()))) {

				HSSFCell cellSiNoData = detailRow.createCell(0);
				cellSiNoData.setCellStyle(contentCellStyle);
				cellSiNoData.setCellValue(String.valueOf(simpleDateFormat.format(pettyCash.getEntryDate())));
				}
				else {
					
					HSSFCell cellSiNoData = detailRow.createCell(0);
					cellSiNoData.setCellStyle(contentCellStyle);
					cellSiNoData.setCellValue("");
					
				}
				
					HSSFCell cellDate = detailRow.createCell(1);
					cellDate.setCellStyle(contentCellStyle);
					cellDate.setCellValue("By");
				
				

				HSSFCell cellCategory = detailRow.createCell(2);
				cellCategory.setCellStyle(contentCellStyle);
				cellCategory.setCellValue(String.valueOf(pettyCash.getCategoryName()));

				HSSFCell cellVoucherType = detailRow.createCell(3);
				cellVoucherType.setCellStyle(contentCellStyle);
				cellVoucherType.setCellValue(String.valueOf(pettyCash.getVoucherType()));
				
				HSSFCell cellNarrati= detailRow.createCell(4);
				cellNarrati.setCellStyle(contentCellStyle);
				cellNarrati.setCellValue(String.valueOf(pettyCash.getNarration()));
				
				 if (pettyCash.getVoucherNo() == 0) {
					 HSSFCell cellVocuherNO = detailRow.createCell(5);
					 cellVocuherNO.setCellStyle(contentCellStyle);
					 cellVocuherNO.setCellValue("");
					 
				 }
				 else {
					 HSSFCell cellVocuherNO = detailRow.createCell(5);
					 cellVocuherNO.setCellStyle(contentCellStyle);
					 cellVocuherNO.setCellValue(String.valueOf(pettyCash.getVoucherNo()));
					 
				 }

					
					HSSFCell cellAmount = detailRow.createCell(6);
					cellAmount.setCellStyle(numericCellStyle);
					cellAmount.setCellValue("");
					

					HSSFCell cellAmount1 = detailRow.createCell(7);
					cellAmount1.setCellStyle(numericCellStyle);
					cellAmount1.setCellValue(String.valueOf(pettyCash.getAmount()));
					
					
					 creditTotal = creditTotal + pettyCash.getAmount();
					
				
				count++;
				
				rowCnt = 6+i;
			}
			closinBlnce = creditTotal;	
			
			for(int j=1;j<=3;j++){
				
				HSSFRow detailRow1 = sheet.createRow(rowCnt+ j);
				detailRow1.setHeightInPoints(25);
				
				HSSFCell cellSiNo1 = detailRow1.createCell(0);
				cellSiNo1.setCellStyle(contentCellStyle);
				cellSiNo1.setCellValue("");
				
				HSSFCell cellSiNo2 = detailRow1.createCell(1);
				cellSiNo2.setCellStyle(contentCellStyle);
				
				HSSFCell cellSiNo3 = detailRow1.createCell(2);
				cellSiNo3.setCellStyle(contentCellStyle);
				
				HSSFCell cellSiNo4 = detailRow1.createCell(3);
				cellSiNo4.setCellStyle(contentCellStyle);
				
				HSSFCell cellSiNo5 = detailRow1.createCell(4);
				cellSiNo5.setCellStyle(contentCellStyle);
				
				HSSFCell cellSiNo6 = detailRow1.createCell(5);
				cellSiNo6.setCellStyle(contentCellStyle);
				
				HSSFCell cellSiNo7 = detailRow1.createCell(6);
				cellSiNo7.setCellStyle(numericCellStyleTotal);
				
				HSSFCell cellSiNo8 = detailRow1.createCell(7);
				cellSiNo8.setCellStyle(numericCellStyleTotal);
				
			if(j == 2) {
				
				cellSiNo2.setCellValue("To");
			}
			else {
				cellSiNo2.setCellValue("");
			}
			if(j == 2) {
				cellSiNo3.setCellValue("Closing Balance");
			}
			else {
				cellSiNo3.setCellValue(" ");
			}
			
			cellSiNo4.setCellValue(" ");
			cellSiNo5.setCellValue(" ");
			cellSiNo6.setCellValue(" ");
				
				if(j == 1 ) {
					
					cellSiNo7.setCellValue("");
				}
				else if( j == 2){
					cellSiNo7.setCellValue(String.valueOf(creditTotal));
				}
				else {
					
					cellSiNo7.setCellValue(String.valueOf(creditTotal));
				}
				
				
				
				if(j == 1 ) {
					
					cellSiNo8.setCellValue(String.valueOf(creditTotal));
					
				}
				else if(j == 2){
					
					cellSiNo8.setCellValue("");
					
				}
				else {
					cellSiNo8.setCellValue(String.valueOf(creditTotal));
					
					
				}
			
		}
			

		}else {
			HSSFRow tableData = sheet.createRow(5);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(noDataCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 7));
		}
		

		Date today1 = new Date();
		SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday3 = sdf3.format(today1);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat(reportTmpl.getReportname().toLowerCase().replaceAll("\\s", "") + stringToday3 + ".xls");
		response.addHeader("Content-disposition", headerResponse);
		
	}


}
