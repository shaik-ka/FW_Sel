package com.frw.util;

import org.apache.poi.hssf.record.CFRuleBase.ComparisonOperator;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSheetConditionalFormatting;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelStyleUtil {
	
	public static HSSFCellStyle summarySheetHeaderCellStyle(
			   final HSSFWorkbook aWorkbook )
			{
				
				/*HSSFCellStyle style = wb.createCellStyle();
				HSSFFont font = wb.createFont();*/
				
			   final HSSFCellStyle cellStyle = aWorkbook.createCellStyle();
			   
			  /* cellStyle.setAlignment( HSSFCellStyle.ALIGN_CENTER );
			   cellStyle.setFillPattern( HSSFCellStyle.SPARSE_DOTS );
			   cellStyle.setBorderBottom( HSSFCellStyle.BORDER_DOUBLE );
			   cellStyle.setBorderLeft( HSSFCellStyle.BORDER_DOUBLE );
			   cellStyle.setBorderRight( HSSFCellStyle.BORDER_DOUBLE );
			   cellStyle.setBorderTop( HSSFCellStyle.BORDER_DOUBLE );*/
			   cellStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
			   cellStyle.setBorderBottom(HSSFCellStyle.BORDER_SLANTED_DASH_DOT);
			   cellStyle.setBorderTop(HSSFCellStyle.BORDER_SLANTED_DASH_DOT);
			   cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			   cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			   return cellStyle;
			}
	
	
	/*public static HSSFCellStyle resSummarySheetHeaderCellStyle(
			   final HSSFWorkbook aWorkbook )
			{
				
								
			   final HSSFCellStyle cellStyle = aWorkbook.createCellStyle();
			  			   
			   cellStyle.setBorderBottom(HSSFCellStyle.BRICKS);
			   cellStyle.setBorderTop(HSSFCellStyle.BRICKS);
			   cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			   cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			   return cellStyle;
			}*/
	
	
	public static HSSFFont createFont(HSSFWorkbook aWorkbook){
		final HSSFFont font = aWorkbook.createFont();
		return font;
	}
	
	public static HSSFCellStyle linkCellStyle(HSSFWorkbook aWorkbook,short colorIndex){
		 final HSSFCellStyle cellStyle = aWorkbook.createCellStyle();
		
		final  Font mLinkFont = aWorkbook.createFont();
		  mLinkFont.setFontName("Ariel");
		  mLinkFont.setUnderline(Font.U_SINGLE);
		 // mLinkFont.setColor(IndexedColors.ORANGE.getIndex());
		  mLinkFont.setColor(colorIndex);
		  cellStyle.setFont(mLinkFont);
		return cellStyle;
	}
	
	public static HSSFCellStyle hearderCellStyle(HSSFWorkbook aWorkbook,short colorIndex){
		 final HSSFCellStyle cellStyle = aWorkbook.createCellStyle();
		
		 cellStyle.setFillForegroundColor(colorIndex);
		 cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		 cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		 cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		 cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		return cellStyle;
	}

	public static void createConditionalFormattingStatus(HSSFSheet sheet,String sheetType){
		 String conditionalFormattingRange;
		 HSSFSheetConditionalFormatting my_cond_format_layer = sheet.getSheetConditionalFormatting();
       if(sheetType.equalsIgnoreCase("TestSummary")){
      	 conditionalFormattingRange="C1:C60000";
       }else{
      	 conditionalFormattingRange="K1:K60000";
       }
       /* Create a Rule */
       ConditionalFormattingRule rule1 = my_cond_format_layer.createConditionalFormattingRule(ComparisonOperator.EQUAL, "\"Pass\"");
       PatternFormatting fill1 = rule1.createPatternFormatting();
       fill1.setFillBackgroundColor(IndexedColors.LIME.index);
       fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
       CellRangeAddress[] regions = {CellRangeAddress.valueOf(conditionalFormattingRange)};
       my_cond_format_layer.addConditionalFormatting(regions, rule1);
       
       ConditionalFormattingRule rule2 = my_cond_format_layer.createConditionalFormattingRule(ComparisonOperator.EQUAL, "\"Fail\"");
       PatternFormatting fill2 = rule2.createPatternFormatting();
       fill2.setFillBackgroundColor(IndexedColors.RED.index);
       fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);         
       my_cond_format_layer.addConditionalFormatting(regions, rule2);
       
       ConditionalFormattingRule rule3 = my_cond_format_layer.createConditionalFormattingRule(ComparisonOperator.EQUAL, "\"Warning\"");
       PatternFormatting fill3 = rule3.createPatternFormatting();
       fill3.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.index);
       fill3.setFillPattern(PatternFormatting.SOLID_FOREGROUND);         
       my_cond_format_layer.addConditionalFormatting(regions, rule3);
       
       ConditionalFormattingRule rule4 = my_cond_format_layer.createConditionalFormattingRule(ComparisonOperator.EQUAL, "\"Info\"");
       PatternFormatting fill4 = rule4.createPatternFormatting();
       fill4.setFillBackgroundColor(IndexedColors.AQUA.index);
       fill4.setFillPattern(PatternFormatting.SOLID_FOREGROUND);         
       my_cond_format_layer.addConditionalFormatting(regions, rule4);
       
       
       
	}
	
	@SuppressWarnings("unused")
	private CellStyle cloneStyle(HSSFSheet sheet,Cell cell) {
		   CellStyle newStyle = sheet.getWorkbook().createCellStyle();
		   newStyle.cloneStyleFrom(cell.getCellStyle());
		   System.out.println("Clonned Cell Style");
		   return newStyle;
		}
	@SuppressWarnings("unused")
	private Font cloneFont(CellStyle cellstyle,HSSFSheet sheet) {
		   Font newFont = sheet.getWorkbook().createFont();
		   Font originalFont = sheet.getWorkbook().getFontAt(cellstyle.getFontIndex());
		   if (originalFont != null) {
		       newFont.setBold(originalFont.getBold());
		       newFont.setItalic(originalFont.getItalic());
		       newFont.setFontHeight(originalFont.getFontHeight());
		       newFont.setUnderline(originalFont.getUnderline());
		       newFont.setStrikeout(originalFont.getStrikeout());
		       // This cast can only be done when using .xlsx files
		       HSSFFont originalXFont = (HSSFFont) originalFont;
		       HSSFFont newXFont = (HSSFFont) newFont;
		       newXFont.setColor(originalXFont.getColor());
		   }
		   return newFont;
		}

}
