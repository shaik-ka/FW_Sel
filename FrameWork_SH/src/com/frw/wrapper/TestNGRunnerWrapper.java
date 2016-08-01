package com.frw.wrapper;


public class TestNGRunnerWrapper {

	
	/**
	 * Fetches the Test suites which are marked as Y
	 * @author sahamed
	 * @Date Jun 12 2014
	 * @param xls
	 * @return Lists of executable suites - Suite Name[TSID] with its Package Name
	 *//*
	
	public static LinkedHashMap<String,String> fetch_SuitesRunnable(Xls_Reader xls){
		
		LinkedHashMap<String,String> executableSuites=new LinkedHashMap<String,String>();
		
		for (int i=2;i<=xls.getRowCount(Constants_FRMWRK.SUITE_SHEET_NAME);i++){
			String suite=xls.getCellData(Constants_FRMWRK.SUITE_SHEET_NAME, Constants_FRMWRK.SUITE_SHEET_TSID_COLUMN, i);
			String runmode=xls.getCellData(Constants_FRMWRK.SUITE_SHEET_NAME, Constants_FRMWRK.SUITE_SHEET_RUNMODE_COLUMN, i);
			
			
				if (runmode.equalsIgnoreCase(Constants_FRMWRK.RUNMODE_YES_FLAG)){
						executableSuites.put(suite, xls.getCellData(Constants_FRMWRK.SUITE_SHEET_NAME, Constants_FRMWRK.SUITE_SHEET_PKG_COLUMN, i));				
				}
			}			
		
		return executableSuites;
	}
	
	*//**
	 * Fetches the Test suites which are marked as Y
	 * @param xls
	 * @return List of executable tests - Test Name[TCID] with its Run Mode
	 *//*
	public static LinkedHashMap<String,String> fetch_SuiteTestsRunnable(Xls_Reader xls){
		LinkedHashMap<String,String> executableTests=new LinkedHashMap<String,String>();
		
		for(int i=2;i<=xls.getRowCount(Constants_FRMWRK.TC_SHEET_NAME);i++){
			
			String tcid=xls.getCellData(Constants_FRMWRK.TC_SHEET_NAME, Constants_FRMWRK.TC_SHEET_TCID_COLUMN, i);
			String runmode=xls.getCellData(Constants_FRMWRK.TC_SHEET_NAME, Constants_FRMWRK.TC_SHEET_RUNMODE_COLUMN, i);
			
				if(runmode.equalsIgnoreCase(Constants_FRMWRK.RUNMODE_YES_FLAG)){
					executableTests.put(tcid, runmode);
				}
			
		}		
		System.out.println("Total tests"+executableTests);
		return executableTests;
	}	*/
}
