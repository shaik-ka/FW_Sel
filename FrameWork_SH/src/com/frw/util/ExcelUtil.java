package com.frw.util;

import java.util.Hashtable;
import com.frw.base.Base;
import com.frw.util.Xls_Reader;

public class ExcelUtil extends Base {
	
	//steps to perform
		// 1. get the no of rows
		// 2. get the no of cols
		// 3. extract the data
	
	public static Object[][] fetchSingleSheetData(String sheetName,Xls_Reader xls){
		
		if (!xls.isSheetExist(sheetName)){
			xls=null;
			Object data[][]=new Object[1][1];
			data[0][0]="No parameters Test";
			//return new Object[1][0];
			return data;
		}
		
		
		int rows=xls.getRowCount(sheetName);
		int cols=xls.getColumnCount(sheetName);
		
		
		// extract data
		//********************************************************************************
		Object data[][]=new Object[rows-1][1];
		Hashtable<String,String>table=null;
		int index=0;
		
		
		for (int rowNum=2;rowNum<=rows;rowNum++){
			
			table=new Hashtable<String,String>();
			
			for (int colNum=0;colNum<cols;colNum++){
			
				String key=xls.getCellData(sheetName, colNum, 1);
				String value=xls.getCellData(sheetName, colNum, rowNum);
				table.put(key, value);
				
				//System.out.print(value+"----");
			}
				data[index][0]=table;
				index++;
		}
		
		
		
		return data;
	}
	
	
	
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public static Hashtable<String,String> getParameters(Xls_Reader xls,String sheetName,String keycolName,String dataColName){
		int rows=xls.getRowCount(sheetName);
		@SuppressWarnings("unused")
		int cols=xls.getColumnCount(sheetName);
		
		
		// extract data
		//********************************************************************************
		//Object data[][]=new Object[rows-1][1];
		//int index=0;
		Hashtable<String,String>table=null;
		table=new Hashtable<String,String>();
		
		for (int rowNum=2;rowNum<=rows;rowNum++){
			
				String key=xls.getCellData(sheetName, keycolName, rowNum);
				String value=xls.getCellData(sheetName, dataColName, rowNum);
				
				table.put(key, value);
				
				//System.out.print(value+"----");
			
		}
		
		
		
		return table;
	}
	
// Fetch data from  the excel sheet row wise and store it into Hash table	
@SuppressWarnings("unchecked")
public static Hashtable<String,String> getRowdataFromExcel(Xls_Reader xls, String sheetName,int row){
		
		int generalSheetRow=row;
		Hashtable<String,String> generalSheetData=new Hashtable<String,String> ();
		Object[][] obj_generalSheet=ExcelUtil.fetchSingleSheetData(sheetName, xls);
			
			if (generalSheetRow < obj_generalSheet.length){			
				generalSheetData=(Hashtable<String, String>) obj_generalSheet[generalSheetRow][0];				
			
			}
	
		
		return generalSheetData;
	}
	
	

}
