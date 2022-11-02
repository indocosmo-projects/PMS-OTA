package com.indocosmo.pms.web.reports.report_designs;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.pms.web.corporate.model.Corporate;

public class CorporateList extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<Corporate> corporateList = (List<Corporate>) model.get("reportTemplate");
		
		Font reprName = workbook.createFont();
		reprName.setFontName("Liberation Sans");
		
		reprName.setBoldweight((short) 12);
		reprName.setFontHeightInPoints((short) 16);
		reprName.setBoldweight(Font.BOLDWEIGHT_BOLD);
		reprName.setColor(IndexedColors.BLACK.getIndex());
		reprName.setUnderline(Font.U_SINGLE);
		
		CellStyle rptNameCellStyle = workbook.createCellStyle();
		rptNameCellStyle.setFont(reprName);
		rptNameCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font contentFont = workbook.createFont();
		contentFont.setFontName("Liberation Sans");
		
		contentFont.setFontHeightInPoints((short) 9);
		contentFont.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setFont(contentFont);
		contentCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		contentCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderRight(CellStyle.BORDER_THIN); 
		
		Font tblHeadFont = workbook.createFont();
		tblHeadFont.setFontName("Liberation Sans");
		
		tblHeadFont.setFontHeightInPoints((short) 9);
		tblHeadFont.setColor(IndexedColors.BLACK.getIndex());
		tblHeadFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		CellStyle tblHeadCellStyle = workbook.createCellStyle();
		tblHeadCellStyle.setFont(tblHeadFont);
		tblHeadCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		tblHeadCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		tblHeadCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		tblHeadCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		tblHeadCellStyle.setBorderRight(CellStyle.BORDER_THIN); 
		
		HSSFSheet sheet = workbook.createSheet("sheet");
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 10000);
		
		HSSFRow nameRow = sheet.createRow(0);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue("Corporate List");
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		
		HSSFRow tblheadRow = sheet.createRow(2);
		tblheadRow.setHeightInPoints(15);
		
		HSSFCell cellHeadCode = tblheadRow.createCell(0);
		cellHeadCode.setCellStyle(tblHeadCellStyle);
		cellHeadCode.setCellValue("Code");
		
		HSSFCell cellHeadName = tblheadRow.createCell(1);
		cellHeadName.setCellStyle(tblHeadCellStyle);
		cellHeadName.setCellValue("Name");
		
		if(corporateList.size()!=0){
			for(int i=0; i<corporateList.size(); i++) {
				
				HSSFRow subheaderRow = sheet.createRow(3+i);
				subheaderRow.setHeightInPoints(15);
				
				HSSFCell cellCode = subheaderRow.createCell(0);
				cellCode.setCellStyle(contentCellStyle);
				cellCode.setCellValue(corporateList.get(i).getCode());
				
				HSSFCell cellName = subheaderRow.createCell(1);
				cellName.setCellStyle(contentCellStyle);
				cellName.setCellValue(corporateList.get(i).getName());
				
			}
		}

	}
	
}
