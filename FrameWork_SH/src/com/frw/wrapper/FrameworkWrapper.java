package com.frw.wrapper;

import java.util.ArrayList;

import com.frw.base.Base;
import com.frw.util.Xls_Reader;

public class FrameworkWrapper extends Base{
	
	/**
	 * Fetches the list of sheet instances available in the given Xls_Reader
	 * @param excelReader
	 * @return
	 */
	public static ArrayList<String> fetchDataSheetsOfExcel(Xls_Reader excelReader,ArrayList<String> datasheets){
		
		if(datasheets==null){		
			logsObj.logInfo("Xls Reader sheets are fetching");
			datasheets=excelReader.getsheets();
		}else{
			logsObj.logInfo("Xls Reader sheets are already fetched");
		}
		
		return datasheets;
	}

}
