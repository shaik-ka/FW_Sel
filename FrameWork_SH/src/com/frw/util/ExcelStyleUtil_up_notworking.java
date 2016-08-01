package com.frw.util;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.record.CFRuleBase.ComparisonOperator;
import org.apache.poi.hssf.usermodel.HSSFCell;
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
import org.apache.poi.ss.util.CellUtil;

public class ExcelStyleUtil_up_notworking {
	static HSSFCellStyle pass_style;
	static HSSFCellStyle fail_style;
	static HSSFCellStyle warning_style;
	static HSSFCellStyle info_style;
	static HSSFCellStyle linkCellStyle;
	static HSSFCell pass_cell;
	static Font mLinkFont;
	
	
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
		HSSFCellStyle linkStyle;
		
		/*if(linkCellStyle==null){
			linkStyle = aWorkbook.createCellStyle();
			linkCellStyle=linkStyle;
			mLinkFont = aWorkbook.createFont();
			mLinkFont.setFontName("Ariel");
			mLinkFont.setUnderline(Font.U_SINGLE);
			// mLinkFont.setColor(IndexedColors.ORANGE.getIndex());
			mLinkFont.setColor(colorIndex);
		}	else{
			linkStyle = aWorkbook.createCellStyle();
			linkStyle.cloneStyleFrom(linkCellStyle);
			mLinkFont = aWorkbook.createFont();
			mLinkFont.setFontName("Ariel");
			mLinkFont.setUnderline(Font.U_SINGLE);
			mLinkFont.setColor(colorIndex);
		}*/
		
		linkStyle = aWorkbook.createCellStyle();
		linkCellStyle=linkStyle;
		mLinkFont = aWorkbook.createFont();
		mLinkFont.setFontName("Ariel");
		mLinkFont.setUnderline(Font.U_SINGLE);
		// mLinkFont.setColor(IndexedColors.ORANGE.getIndex());
		mLinkFont.setColor(colorIndex);
		linkStyle.setFont(mLinkFont);
		return linkCellStyle;
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


	public static void setStatusStyle(HSSFCellStyle wb_style,HSSFCell cell,HSSFWorkbook wb,String status){
		/*if(wb_style==null){
			CellStyle newStyle = wb.createCellStyle();
			newStyle.cloneStyleFrom(pass_style);
			newStyle.setFillForegroundColor(HSSFColor.LIME.index);
			newStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}*/
		if(status.equalsIgnoreCase("PASS")){
			/*pass_style=passStyle(wb_style);
			cell.setCellStyle(pass_style);*/
			setStatusStyle_1(cell, wb, status);
		}
			
			/*if(pass_style==null){
				pass_cell=cell;
				pass_style=passStyle(wb_style);
				System.out.println("Style -First"+pass_style.getFillForegroundColor());
				cell.setCellStyle(pass_style);
				System.out.println("Style-Cell -First"+pass_style.getFillForegroundColor());
			}else{
				CellStyle newStyle = wb.createCellStyle();
				newStyle.cloneStyleFrom(pass_style);
				newStyle.setFillForegroundColor(HSSFColor.LIME.index);
				newStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				
				CellStyle newStyle =clone_passStyle(wb_style, wb);
				cell.setCellStyle(newStyle);		
				pass_style=passStyle(wb_style);
				cell.setCellStyle(pass_style);
				pass_style=cell.getCellStyle();
				HSSFCellStyle newStyle = wb.createCellStyle();
				newStyle=copyCellStyle(pass_cell, newStyle);
				System.out.println("New Style -Reuse"+newStyle.getFillForegroundColor());
				cell.setCellStyle(newStyle);
				System.out.println("New Style-Cell -Reuse"+newStyle.getFillForegroundColor());
			}						
		}*/
		else if(status.equalsIgnoreCase("FAIL")){
			/*fail_style=failStyle(wb_style);
			cell.setCellStyle(fail_style);*/
			setStatusStyle_1(cell, wb, status);
		}
		else if (status.equalsIgnoreCase("WARNING")){
			/*warning_style=failStyle(wb_style);
			cell.setCellStyle(warning_style);*/
			setStatusStyle_1(cell, wb, status);
		}
		else if (status.equalsIgnoreCase("INFO")){
			/*info_style=failStyle(wb_style);
			cell.setCellStyle(info_style);*/
			setStatusStyle_1(cell, wb, status);
		}
	}
	
	public static void setStatusStyle_1(HSSFCell cell,HSSFWorkbook wb,String status){
		if(status.equalsIgnoreCase("PASS")){
			CellUtil.setCellStyleProperty(cell, wb, CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
			CellUtil.setCellStyleProperty(cell, wb, CellUtil.FILL_FOREGROUND_COLOR, HSSFColor.LIME.index);			
		}
		else if (status.equalsIgnoreCase("FAIL")){
			CellUtil.setCellStyleProperty(cell, wb, CellUtil.FILL_FOREGROUND_COLOR, HSSFColor.RED.index);
			CellUtil.setCellStyleProperty(cell, wb, CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
		}
		else if (status.equalsIgnoreCase("WARNING")){
			CellUtil.setCellStyleProperty(cell, wb, CellUtil.FILL_FOREGROUND_COLOR, HSSFColor.LIGHT_ORANGE.index);
			CellUtil.setCellStyleProperty(cell, wb, CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
		}
		else if (status.equalsIgnoreCase("INFO")){
			CellUtil.setCellStyleProperty(cell, wb, CellUtil.FILL_FOREGROUND_COLOR, HSSFColor.DARK_TEAL.index);
			CellUtil.setCellStyleProperty(cell, wb, CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
		}
	}

	
	private static HSSFCellStyle passStyle(HSSFCellStyle style){
		style.setFillForegroundColor(HSSFColor.LIME.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		pass_style=style;
		return pass_style;
	}
	private static HSSFCellStyle failStyle(HSSFCellStyle style){
		style.setFillForegroundColor(HSSFColor.RED.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		fail_style=style;
		return fail_style;
	}
	private static HSSFCellStyle warningStyle(HSSFCellStyle style){
		style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		warning_style=style;
		return warning_style;
	}
	private static HSSFCellStyle infoStyle(HSSFCellStyle style){
		style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		info_style=style;
		return info_style;
	}
	private static HSSFCellStyle clone_passStyle(HSSFCellStyle style,HSSFWorkbook wb){
		HSSFCellStyle newStyle = wb.createCellStyle();
		newStyle.cloneStyleFrom(style);		
		newStyle.setFillForegroundColor(IndexedColors.LIME.index);
		newStyle.setFillBackgroundColor(IndexedColors.LIME.index);
		newStyle.setFillPattern(CellStyle.ALIGN_FILL);
		newStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		return newStyle;
	}
	
	
	private static HSSFCellStyle copyCellStyle(HSSFCell cell, HSSFCellStyle newCellStyle) {
	    newCellStyle.setAlignment(cell.getCellStyle().getAlignment());
	    newCellStyle.setBorderBottom(cell.getCellStyle().getBorderBottom());
	    newCellStyle.setBorderLeft(cell.getCellStyle().getBorderLeft());
	    newCellStyle.setBorderRight(cell.getCellStyle().getBorderRight());
	    newCellStyle.setBorderTop(cell.getCellStyle().getBorderTop());
	    newCellStyle.setBottomBorderColor(cell.getCellStyle().getBottomBorderColor());
	    newCellStyle.setDataFormat(cell.getCellStyle().getDataFormat());
	    newCellStyle.setFillBackgroundColor(cell.getCellStyle().getFillBackgroundColor());
	    newCellStyle.setFillForegroundColor(cell.getCellStyle().getFillForegroundColor());
	    newCellStyle.setFillPattern(cell.getCellStyle().getFillPattern());
	    newCellStyle.setFont(cell.getCellStyle().getFont(cell.getSheet().getWorkbook()));
	    newCellStyle.setHidden(cell.getCellStyle().getHidden());
	    newCellStyle.setIndention(cell.getCellStyle().getIndention());
	    newCellStyle.setLeftBorderColor(cell.getCellStyle().getLeftBorderColor());
	    newCellStyle.setLocked(cell.getCellStyle().getLocked());
	    newCellStyle.setRightBorderColor(cell.getCellStyle().getRightBorderColor());
	    newCellStyle.setRotation(cell.getCellStyle().getRotation());
	    newCellStyle.setShrinkToFit(cell.getCellStyle().getShrinkToFit());
	    newCellStyle.setTopBorderColor(cell.getCellStyle().getTopBorderColor());
	    // newCellStyle.setUserStyleName(cell.getCellStyle().getUserStyleName()); -> ignore
	    newCellStyle.setVerticalAlignment(cell.getCellStyle().getVerticalAlignment());
	    newCellStyle.setWrapText(cell.getCellStyle().getWrapText());
	    return newCellStyle;
	}
	
	 private static CellStyle getSameCellStyle(Cell oldCell, Cell newCell, List<CellStyle> styleList) {
	        CellStyle styleToFind = oldCell.getCellStyle();
	        CellStyle currentCellStyle = null;
	        CellStyle returnCellStyle = null;
	        Iterator<CellStyle> iterator = styleList.iterator();
	        Font oldFont = null;
	        Font newFont = null;
	        while (iterator.hasNext() && returnCellStyle == null) {
	            currentCellStyle = iterator.next();
	 
	            if (currentCellStyle.getAlignment() != styleToFind.getAlignment()) {
	                continue;
	            }
	            if (currentCellStyle.getHidden() != styleToFind.getHidden()) {
	                continue;
	            }
	            if (currentCellStyle.getLocked() != styleToFind.getLocked()) {
	                continue;
	            }
	            if (currentCellStyle.getWrapText() != styleToFind.getWrapText()) {
	                continue;
	            }
	            if (currentCellStyle.getBorderBottom() != styleToFind.getBorderBottom()) {
	                continue;
	            }
	            if (currentCellStyle.getBorderLeft() != styleToFind.getBorderLeft()) {
	                continue;
	            }
	            if (currentCellStyle.getBorderRight() != styleToFind.getBorderRight()) {
	                continue;
	            }
	            if (currentCellStyle.getBorderTop() != styleToFind.getBorderTop()) {
	                continue;
	            }
	            if (currentCellStyle.getBottomBorderColor() != styleToFind.getBottomBorderColor()) {
	                continue;
	            }
	            if (currentCellStyle.getFillBackgroundColor() != styleToFind.getFillBackgroundColor()) {
	                continue;
	            }
	            if (currentCellStyle.getFillForegroundColor() != styleToFind.getFillForegroundColor()) {
	                continue;
	            }
	            if (currentCellStyle.getFillPattern() != styleToFind.getFillPattern()) {
	                continue;
	            }
	            if (currentCellStyle.getIndention() != styleToFind.getIndention()) {
	                continue;
	            }
	            if (currentCellStyle.getLeftBorderColor() != styleToFind.getLeftBorderColor()) {
	                continue;
	            }
	            if (currentCellStyle.getRightBorderColor() != styleToFind.getRightBorderColor()) {
	                continue;
	            }
	            if (currentCellStyle.getRotation() != styleToFind.getRotation()) {
	                continue;
	            }
	            if (currentCellStyle.getTopBorderColor() != styleToFind.getTopBorderColor()) {
	                continue;
	            }
	            if (currentCellStyle.getVerticalAlignment() != styleToFind.getVerticalAlignment()) {
	                continue;
	            }
	 
	            oldFont = oldCell.getSheet().getWorkbook().getFontAt(oldCell.getCellStyle().getFontIndex());
	            newFont = newCell.getSheet().getWorkbook().getFontAt(currentCellStyle.getFontIndex());
	 
	            if (newFont.getBoldweight() == oldFont.getBoldweight()) {
	                continue;
	            }
	            if (newFont.getColor() == oldFont.getColor()) {
	                continue;
	            }
	            if (newFont.getFontHeight() == oldFont.getFontHeight()) {
	                continue;
	            }
	            if (newFont.getFontName() == oldFont.getFontName()) {
	                continue;
	            }
	            if (newFont.getItalic() == oldFont.getItalic()) {
	                continue;
	            }
	            if (newFont.getStrikeout() == oldFont.getStrikeout()) {
	                continue;
	            }
	            if (newFont.getTypeOffset() == oldFont.getTypeOffset()) {
	                continue;
	            }
	            if (newFont.getUnderline() == oldFont.getUnderline()) {
	                continue;
	            }
	            if (newFont.getCharSet() == oldFont.getCharSet()) {
	                continue;
	            }
	            if (oldCell.getCellStyle().getDataFormatString().equals(currentCellStyle.getDataFormatString())) {
	                continue;
	            }
	 
	            returnCellStyle = currentCellStyle;
	        }
	        return returnCellStyle;
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
}
