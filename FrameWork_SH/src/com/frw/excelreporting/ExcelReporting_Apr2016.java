package com.frw.excelreporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.WebDriver;

import com.frw.base.Base;
import com.frw.util.CaptureScreenUtil;
import com.frw.util.DateUtil;
import com.frw.util.ExcelStyleUtil;
import com.frw.util.WaitUtil;


public class ExcelReporting_Apr2016 extends Base{
/*
	private static int summaryrowNumber = 0;
	private static int resultStep=1;

	private static String testSummaryFilePath="";
	private static String testSummarySheetName="";
	private static String testRepo_url="";
	private static String screens_locPath="";
	private static boolean isRemoteDriver=false;
	
	
	private static HSSFCellStyle style;
	private static HSSFCellStyle style2;
	private static HSSFCellStyle linkStyle;
	private static HSSFCellStyle step_style;
	private static HSSFFont font;

	private static ExcelReporting_Apr2016 excelReportingObj;
	private static DateUtil dateUtilObj;
	private static CaptureScreenUtil captureObj;
	
	
	private ExcelReporting_Apr2016(){}
	
	public static ExcelReporting_Apr2016 getExcelReportingObject(){
		if(excelReportingObj==null){
			excelReportingObj= new ExcelReporting_Apr2016();
		}
		
		return excelReportingObj;
	}
	*//**
	 * Creates an Excel File with current date stamp in the specified location
	 * @author khshaik
	 * @date Oct 17 2014
	 * @param resultsFile
	 *//*
	@SuppressWarnings("resource")
	public String CreateResultFile(String resultsFile)	{

		try {

			File file=new File(resultsFile);
			boolean exists = file.exists();
			if (!exists) {
				// It returns false if File or directory does not exist
				new File(resultsFile).mkdir();
				//System.out.println("Directory created : " + Constants.Results_File_Path);
			}else{
				// System.out.println("Directory you are searching already exist : " + Constants.Results_File_Path);

			}

			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			resultsFile = resultsFile+"\\Sel-Results" + "_" + dateFormat.format(cal.getTime()) +".xls";

			HSSFWorkbook wb = new HSSFWorkbook();

			FileOutputStream fileOut =  new FileOutputStream(resultsFile);

			wb.write(fileOut);

			fileOut.flush();

			fileOut.close();

		} catch (Exception e) {
			logsObj.logError("CreateResultFile:-Unable to create the Results File"+resultsFile+" due to error-", e);			

		}

		return resultsFile;

	}

	*//**
	 * creates an test summary template sheet in the results file created
	 * @author khshaik
	 * @date Oct 17 2014
	 * @param resultsFile
	 * @param sheetName
	 * @param url
	 * @return
	 *//*
	public  String createTestSummarySheet(String resultsFile,String sheetName,Boolean isRemoteDriverExecution,String testRepository_url,String test_url){

		try{
			isRemoteDriver=isRemoteDriverExecution;
			testRepo_url=testRepository_url;
			dateUtilObj= DateUtil.getDateUtilObject();
			File file=new File(resultsFile);

			if (!file.exists()) {
				CreateResultFile(resultsFile);
				//log("New file Created : "+ resultsFile);

			} else {
				//log("Result File Found : "+ resultsFile);

			}
			testSummaryFilePath=resultsFile;
			InputStream myXls = new FileInputStream(resultsFile);

			HSSFWorkbook wb = new HSSFWorkbook(myXls);		

			HSSFSheet summarySheet = wb.createSheet(sheetName);
			testSummarySheetName=sheetName;
			ExcelStyleUtil.createConditionalFormattingStatus(summarySheet,"TestSummary");
			// By applying style for cells we can see the total text in the cell for specified width
			// *************** cell style for summary sheet ****************************
			//style2 = ExcelStyleUtil.hearderCellStyle(wb,HSSFColor.DARK_TEAL.index);
			style2 = ExcelStyleUtil.hearderCellStyle(wb,HSSFColor.LAVENDER.index);


			summarySheet.createRow(summaryrowNumber).createCell(0).setCellValue("Total Test Scenarios Executed #:");


			summarySheet.getRow(summaryrowNumber).createCell(1).setCellType(Cell.CELL_TYPE_FORMULA);
			summarySheet.getRow(summaryrowNumber).getCell(1).setCellFormula("SUMPRODUCT((A5:A30000<>\"\")/COUNTIF(A5:A30000,A5:A30000&\"\"))");		

			summarySheet.getRow(summaryrowNumber).createCell(2).setCellValue("");
			summarySheet.getRow(summaryrowNumber).createCell(3).setCellValue("Total Test Cases Executed #:");


			summarySheet.getRow(summaryrowNumber).createCell(4).setCellType(Cell.CELL_TYPE_FORMULA);
			summarySheet.getRow(summaryrowNumber).getCell(4).setCellFormula("SUM(E2:E3)");

			summarySheet.setColumnWidth(0, 8000);
			summarySheet.setColumnWidth(1, 8000);
			summarySheet.setColumnWidth(2, 3000);
			summarySheet.setColumnWidth(3, 7000);
			summarySheet.setColumnWidth(4, 5000);

			summarySheet.getRow(summaryrowNumber).getCell(0).setCellStyle(style2);	
			summarySheet.getRow(summaryrowNumber).getCell(1).setCellStyle(style2);	
			summarySheet.getRow(summaryrowNumber).getCell(2).setCellStyle(style2);	
			summarySheet.getRow(summaryrowNumber).getCell(3).setCellStyle(style2);	
			summarySheet.getRow(summaryrowNumber).getCell(4).setCellStyle(style2);


			summarySheet.createRow(summaryrowNumber+1).createCell(0).setCellValue("AUT URL:");

			summarySheet.getRow(summaryrowNumber+1).createCell(1).setCellValue(test_url);			
			summarySheet.getRow(summaryrowNumber+1).createCell(2).setCellValue("");			
			summarySheet.getRow(summaryrowNumber+1).createCell(3).setCellValue("Test Cases Passed #:");			
			summarySheet.getRow(summaryrowNumber+1).createCell(4).setCellType(Cell.CELL_TYPE_FORMULA);
			summarySheet.getRow(summaryrowNumber+1).getCell(4).setCellFormula("COUNTIF(C5:C10000,\"Pass\")");


			summarySheet.getRow(summaryrowNumber+1).getCell(0).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+1).getCell(1).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+1).getCell(2).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+1).getCell(3).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+1).getCell(4).setCellStyle(style2);

			//row 2
			summarySheet.createRow(summaryrowNumber+2).createCell(0).setCellValue("Test Suite Execution Date:");

			String srtDate=dateUtilObj.getFormattedDate();
			summarySheet.getRow(summaryrowNumber+2).createCell(1).setCellValue(srtDate);			
			summarySheet.getRow(summaryrowNumber+2).createCell(2).setCellValue("");			
			summarySheet.getRow(summaryrowNumber+2).createCell(3).setCellValue("Test Cases Failed #:");
			summarySheet.getRow(summaryrowNumber+2).createCell(4).setCellType(Cell.CELL_TYPE_FORMULA);
			summarySheet.getRow(summaryrowNumber+2).getCell(4).setCellFormula("COUNTIF(C5:C10000,\"Fail\")");


			style2.setWrapText(true);	

			summarySheet.getRow(summaryrowNumber+2).getCell(0).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+2).getCell(1).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+2).getCell(2).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+2).getCell(3).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+2).getCell(4).setCellStyle(style2);


			//row 3
			//style2 = ExcelStyleUtil.hearderCellStyle(wb,HSSFColor.GOLD.index);
			style2 = ExcelStyleUtil.hearderCellStyle(wb,HSSFColor.LIGHT_ORANGE.index);

			summarySheet.createRow(summaryrowNumber+3).createCell(0).setCellValue("Scenario Name");

			summarySheet.getRow(summaryrowNumber+3).createCell(1).setCellValue("TestCase Name/ID");
			summarySheet.getRow(summaryrowNumber+3).createCell(2).setCellValue("Status");
			summarySheet.getRow(summaryrowNumber+3).createCell(3).setCellValue("Start Time");
			summarySheet.getRow(summaryrowNumber+3).createCell(4).setCellValue("End Time");	
			summarySheet.getRow(summaryrowNumber+3).createCell(5).setCellValue("Duration(HH:MM:SS)");	
			summarySheet.setColumnWidth(5, 6000);			

			summarySheet.getRow(summaryrowNumber+3).getCell(0).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+3).getCell(1).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+3).getCell(2).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+3).getCell(3).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+3).getCell(4).setCellStyle(style2);
			summarySheet.getRow(summaryrowNumber+3).getCell(5).setCellStyle(style2);



			FileOutputStream fileOut =  new FileOutputStream(resultsFile);

			wb.write(fileOut);

			fileOut.flush();

			fileOut.close();

		} catch (Exception rea) {
			logsObj.logError("createTestSummarySheet:-Unable to create the Test Summary Sheet  in the location-"+resultsFile+" due to error", rea);
			//log("createTestSummarySheet:-Unable to create the Test Summary Sheet due to error-"+rea+" in the location-"+resultsFile);

		}

		return sheetName;
	}


	*//**
	 * Creates an suite template excel sheet in the results file created
	 * @author khshaik
	 * @date Oct 17 2014
	 * @param resultsFile
	 * @param sheetName
	 *//*
	@SuppressWarnings("deprecation")
	public void createSuiteSummarySheet(String resultsFile,String sheetName,String screenshotLocPath){
		summaryrowNumber=0;
		try{
			screens_locPath=screenshotLocPath;
			File file=new File(resultsFile);

			if (!file.exists()) {
				CreateResultFile(resultsFile);
				logsObj.log("New Excel File created is"+resultsFile);

			} else {
				logsObj.log("Result File Already Exists : "+ resultsFile);

			}

			InputStream myXls = new FileInputStream(resultsFile);

			HSSFWorkbook wb = new HSSFWorkbook(myXls);

			HSSFSheet tSheet = wb.createSheet(sheetName);
			ExcelStyleUtil.createConditionalFormattingStatus(tSheet,"SuiteSummary");
			logsObj.log("Summary Sheet : "+ sheetName+" is created");
			HSSFRow rowhead = tSheet.createRow((short)summaryrowNumber);


			HSSFRow rowhead_summary = tSheet.createRow((short)summaryrowNumber);
			rowhead.setHeight((short) 400);

			tSheet.setColumnWidth(0, 1500);
			tSheet.setColumnWidth(1, 4000);
			tSheet.setColumnWidth(2, 4500);
			tSheet.setColumnWidth(3, 2500);
			tSheet.setColumnWidth(4, 4500);
			tSheet.setColumnWidth(5, 2500);
			tSheet.setColumnWidth(6, 2000);
			tSheet.setColumnWidth(7, 2500);
			tSheet.setColumnWidth(8, 8500);
			tSheet.setColumnWidth(9, 14500);
			tSheet.setColumnWidth(10, 2500);
			tSheet.setColumnWidth(11, 3500);
			tSheet.setColumnWidth(12, 10500);
		


			style2 = ExcelStyleUtil.hearderCellStyle(wb,HSSFColor.GOLD.index);

			rowhead_summary.setHeight((short)400);


			rowhead_summary.createCell((short) 0).setCellValue("Step");

			rowhead_summary.createCell((short) 1).setCellValue("Suite/Module Name");

			rowhead_summary.createCell((short) 2).setCellValue("ScenarioName/ID");

			rowhead_summary.createCell((short) 3).setCellValue("Test RefID");

			rowhead_summary.createCell((short) 4).setCellValue("TestcaseName/ID");

			rowhead_summary.createCell((short) 5).setCellValue("Browser Name");	
			rowhead_summary.createCell((short) 6).setCellValue("Browser version");	
			rowhead_summary.createCell((short) 7).setCellValue("Environment");
		
			rowhead_summary.createCell((short) 8).setCellValue("Step/Action Details");

			rowhead_summary.createCell((short) 9).setCellValue("Step Description");

			rowhead_summary.createCell((short) 10).setCellValue("Status");

			rowhead_summary.createCell((short) 11).setCellValue("Executed on");

			rowhead_summary.createCell((short) 12).setCellValue("ScreenShot File Path (For failed steps)");

			rowhead_summary.getCell((short) 0).setCellStyle(style2);
			rowhead_summary.getCell((short) 1).setCellStyle(style2);
			rowhead_summary.getCell((short) 2).setCellStyle(style2);
			rowhead_summary.getCell((short) 3).setCellStyle(style2);
			rowhead_summary.getCell((short) 4).setCellStyle(style2);
			rowhead_summary.getCell((short) 5).setCellStyle(style2);
			rowhead_summary.getCell((short) 6).setCellStyle(style2);
			rowhead_summary.getCell((short) 7).setCellStyle(style2);
			rowhead_summary.getCell((short) 8).setCellStyle(style2);
			rowhead_summary.getCell((short) 9).setCellStyle(style2);
			rowhead_summary.getCell((short)10).setCellStyle(style2);
			rowhead_summary.getCell((short)11).setCellStyle(style2);
			rowhead_summary.getCell((short)12).setCellStyle(style2);



			FileOutputStream fileOut =  new FileOutputStream(resultsFile);

			wb.write(fileOut);

			fileOut.flush();

			fileOut.close();

		}catch(Throwable t){
			//log("createSuiteSummarySheet:-Unable to create the Test Suite Summary Sheet"+sheetName+" due to error-"+t+" in the location-"+resultsFile);
			logsObj.logError("createSuiteSummarySheet:-Unable to create the Test Suite Summary Sheet "+sheetName+" in the location"+resultsFile+" due to error",t);
		}	


	}

	*//**
	 * Logs the Test status in the test summary sheet of results file
	 * @author khshaik
	 * @date Oct 20 2014
	 * @param scenarioName
	 * @param testcaseName
	 * @param Result
	 * @param testStartDateTime
	 * @param testEndDateTime
	 *//*
	
	public void ReporterTestSummary(String scenarioName,String testcaseName,Boolean Result,String testStartDateTime,String testEndDateTime)
	{

		try{
			File file=new File(testSummaryFilePath);
			if (!file.exists()) {
				CreateResultFile(testSummaryFilePath);
				//System.out.println("New file Created : "+ Constants.Results_File_Path);
			} 

			InputStream myXls = new FileInputStream(testSummaryFilePath);
			HSSFWorkbook wb = new HSSFWorkbook(myXls);
			HSSFSheet summarySheet = wb.getSheetAt((wb.getSheetIndex(testSummarySheetName)));
			font =ExcelStyleUtil.createFont(wb);
			style = ExcelStyleUtil.summarySheetHeaderCellStyle(wb);


			summaryrowNumber = summarySheet.getLastRowNum();
			summaryrowNumber=summaryrowNumber+1;
			summarySheet.createRow(summaryrowNumber).createCell(0).setCellValue(scenarioName);
			summarySheet.getRow(summaryrowNumber).createCell(1).setCellValue(testcaseName);

			short color = 0;
			String txt = null; 
			if (Result==true) {
				style.setFillForegroundColor(HSSFColor.LIME.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				color = HSSFColor.BLACK.index;
				txt = "PASS";                     
			} else if (Result==false) {
				style.setFillForegroundColor(HSSFColor.RED.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				txt = "FAIL";
			}

			summarySheet.getRow(summaryrowNumber).createCell(2).setCellValue(txt);
			summarySheet.getRow(summaryrowNumber).getCell(2).setCellStyle(style);	

			summarySheet.getRow(summaryrowNumber).createCell(3).setCellValue(testStartDateTime);
			summarySheet.getRow(summaryrowNumber).createCell(4).setCellValue(testEndDateTime);


			String duration=dateUtilObj.duration(testStartDateTime, testEndDateTime);

			summarySheet.getRow(summaryrowNumber).createCell(5).setCellValue(duration);

			font.setColor(color);                
			style.setFont(font);

			FileOutputStream fileOut = new FileOutputStream(testSummaryFilePath);
			wb.write(fileOut);
			fileOut.close();

		}catch(Throwable t){
			//log("ReporterTestSummary:Unable to log the test summary details due to error-"+t);
			logsObj.logError("ReporterTestSummary:Unable to log the test summary details due to error-", t);
		}
	}
	
	
	*//**
	 * Logs the step action details into the relevant suite execution sheet
	 * @author khshaik
	 * @date Oct 22 2014
	 * @param driver
	 * @param sheetName
	 * @param moduleName
	 * @param scenarioName
	 * @param testcase_RefID
	 * @param testcaseName
	 * @param browserName
	 * @param browserVersion
	 * @param environment
	 * @param Step
	 * @param StepDescription
	 * @param Result
	 *//*
	
	@SuppressWarnings({ "unused", "deprecation" })
	public void Reporter(WebDriver driver,String sheetName,String moduleName,String scenarioName,String testcase_RefID,String testcaseName,String browserName,String browserVersion,String environment, String Step,String StepDescription, String Result)
	{
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		try{
			File file=new File(testSummaryFilePath);
			if (!file.exists()) {
				CreateResultFile(testSummaryFilePath);
				//System.out.println("New file Created : "+ Constants.Results_File_Path);
			} 
			
			int count = 1; // Sleep 1 second
			while(!file.canWrite() && count <20){
				logsObj.log("ExcelReporting file is write protected hence wait ..");
			    // Cannot write to file, windows still working on it
			    WaitUtil.pause(100L);
			  count++;
			}
			if(count==20){
				logsObj.log("ExcelReporting file is still in  write protected after wait ..");
			}
			

			InputStream myXls = new FileInputStream(testSummaryFilePath);
			HSSFWorkbook wb = new HSSFWorkbook(myXls);
			HSSFSheet tSheet = wb.getSheetAt((wb.getSheetIndex(sheetName)));
					
			style=ExcelStyleUtil.summarySheetHeaderCellStyle(wb);
			if(step_style==null){
				step_style=wb.createCellStyle();
			}
			step_style=wb.createCellStyle();

			resultStep = tSheet.getLastRowNum()+1;

			Row rowhead = tSheet.createRow((short)resultStep);
			
			rowhead.createCell((short) 0).setCellValue(resultStep);
			rowhead.createCell((short) 1).setCellValue(moduleName);
			rowhead.createCell((short) 2).setCellValue(scenarioName);			
			
			// linkStyle=ExcelStyleUtil.linkCellStyle(wb,IndexedColors.ORANGE.getIndex());
			
			 if(!testRepo_url.equalsIgnoreCase("")){
				  getTestcaseReference(rowhead, testcase_RefID);
			 }
			

			rowhead.createCell((short) 4).setCellValue(testcaseName);
			rowhead.createCell((short) 5).setCellValue(browserName);
			rowhead.createCell((short) 6).setCellValue(browserVersion);
			rowhead.createCell((short) 7).setCellValue(environment);
			rowhead.createCell((short) 8).setCellValue(Step);
			rowhead.createCell((short) 9).setCellValue(StepDescription);
			HSSFCell cellStatus=(HSSFCell) rowhead.createCell((short) 10);
			short color = 0;
			String txt = null; 
			if (Result.equalsIgnoreCase("Pass")) {
				style.setFillForegroundColor(HSSFColor.LIME.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				ExcelStyleUtil.setStatusStyle(step_style,cellStatus, wb, Result);
				txt = "PASS";                     
			} else if (Result.equalsIgnoreCase("Fail")) {
				style.setFillForegroundColor(HSSFColor.RED.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				ExcelStyleUtil.setStatusStyle(step_style,cellStatus, wb, Result);
				txt = "FAIL";

				String RESULT_FOLDER_LOCATION=screens_locPath;
				String failureImageFileName=testcaseName+"_"+resultStep+"_" + new SimpleDateFormat("MM-dd-yyyy_HH-ss"). 
						format(new GregorianCalendar().getTime())+ ".png"; 

				String failureImageFileNamePath = RESULT_FOLDER_LOCATION+failureImageFileName; 
				
				captureObj=CaptureScreenUtil.getCaptureScreenUtil();
				if(isRemoteDriver==false){
					//commonFunctions.TakeScreenshotLink(failureImageFileNamePath,resultStep);
					captureObj.TakeScreenshotLink(driver, failureImageFileNamePath, resultStep);
				}else{
					//commonFunctions.TakeScreenshotLinkforRemoteDriver(failureImageFileNamePath,resultStep);
					captureObj.TakeScreenshotLinkforRemoteDriver(driver, failureImageFileNamePath, resultStep);
				}

				linkStyle=ExcelStyleUtil.linkCellStyle(wb,IndexedColors.BLUE.getIndex());
				HSSFHyperlink file_link=new HSSFHyperlink(HSSFHyperlink.LINK_FILE);

				file_link.setAddress("file:///"+failureImageFileNamePath);
				rowhead.createCell((short) 12).setCellValue(failureImageFileName);
				rowhead.getCell((short) 12).setHyperlink(file_link);
				rowhead.getCell((short) 12).setCellStyle(linkStyle);

			} 
			
			else if (Result.equalsIgnoreCase("Warning")) {
				style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				ExcelStyleUtil.setStatusStyle(step_style,cellStatus, wb, Result);
				txt = "WARNING";

			}
			
			else if (Result.equalsIgnoreCase("Info")) {
				style.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				
//styleFont.setColor(IndexedColors.WHITE.getIndex());
				color = HSSFColor.WHITE.index;
		//		font.setColor(IndexedColors.WHITE.getIndex());
				ExcelStyleUtil.setStatusStyle(step_style,cellStatus, wb, Result);
				txt = "INFO";
			}
			
			rowhead.createCell((short) 10).setCellValue(txt);
			HSSFCell cell = tSheet.getRow((short)resultStep).getCell((short)10);      
			cell.setCellStyle(step_style);
			rowhead.createCell((short) 11).setCellValue(dateFormat.format(cal.getTime()));




			FileOutputStream fileOut = new FileOutputStream(testSummaryFilePath);
		
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception rea) {
			//log("Reporter:Unable to log the test step details due to error-"+rea);
			logsObj.logError("Reporter:Unable to log the test step details due to error-", rea);
			
		}
	}
	
	*//**
	 * Logs the step action details into the relevant suite execution sheet
	 * @author khshaik
	 * @date Jan 17 2016
	 * @param driver
	 * @param sheetName
	 * @param moduleName
	 * @param scenarioName
	 * @param testcase_RefID
	 * @param testcaseName
	 * @param browserName
	 * @param browserVersion
	 * @param environment
	 * @param Step
	 * @param StepDescription
	 * @param Result
	 *//*
	
	@SuppressWarnings({ "unused"})
	public void Reporter(String sheetName,String moduleName,String scenarioName,String testcase_RefID,String testcaseName,String browserName,String browserVersion,String environment, String Step,String StepDescription, String Result)
	{
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		try{
			File file=new File(testSummaryFilePath);
			if (!file.exists()) {
				CreateResultFile(testSummaryFilePath);
				//System.out.println("New file Created : "+ Constants.Results_File_Path);
			} 
			
			int count = 1; // Sleep 1 second
			while(!file.canWrite() && count <20){
				logsObj.log("ExcelReporting file is write protected hence wait ..");
			    // Cannot write to file, windows still working on it
			    WaitUtil.pause(100L);
			  count++;
			}
			if(count==20){
				logsObj.log("ExcelReporting file is still in  write protected after wait ..");
			}
			

			InputStream myXls = new FileInputStream(testSummaryFilePath);
			HSSFWorkbook wb = new HSSFWorkbook(myXls);
			HSSFSheet tSheet = wb.getSheetAt((wb.getSheetIndex(sheetName)));
					
			style=ExcelStyleUtil.summarySheetHeaderCellStyle(wb);
			HSSFCellStyle detailsHead_style=wb.createCellStyle();
			if(step_style==null){
				step_style=wb.createCellStyle();
			}


			resultStep = tSheet.getLastRowNum()+1;

			Row rowhead = tSheet.createRow((short)resultStep);
			
			rowhead.createCell((short) 0).setCellValue(resultStep);
			rowhead.createCell((short) 1).setCellValue(moduleName);
			rowhead.createCell((short) 2).setCellValue(scenarioName);			
			
			 linkStyle=ExcelStyleUtil.linkCellStyle(wb,IndexedColors.ORANGE.getIndex());
			
			 if(!testRepo_url.equalsIgnoreCase("")){
				  getTestcaseReference(rowhead, testcase_RefID);
			 }
			

			rowhead.createCell((short) 4).setCellValue(testcaseName);
			rowhead.createCell((short) 5).setCellValue(browserName);
			rowhead.createCell((short) 6).setCellValue(browserVersion);
			rowhead.createCell((short) 7).setCellValue(environment);
			rowhead.createCell((short) 8).setCellValue(Step);
			rowhead.createCell((short) 9).setCellValue(StepDescription);
			HSSFCell cellStatus=(HSSFCell) rowhead.createCell((short) 10);
			short color = 0;
			String txt = null; 
			if (Result.equalsIgnoreCase("Pass")) {
				
				style.setFillForegroundColor(HSSFColor.LIME.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				
				ExcelStyleUtil.setStatusStyle(step_style,cellStatus, wb, Result);
				txt = "PASS";                     
			} else if (Result.equalsIgnoreCase("Fail")) {
				style.setFillForegroundColor(HSSFColor.RED.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				ExcelStyleUtil.setStatusStyle(step_style,cellStatus, wb, Result);
				txt = "FAIL";

		//		String RESULT_FOLDER_LOCATION=screens_locPath;
				String failureImageFileName=testcaseName+"_"+resultStep+"_" + new SimpleDateFormat("MM-dd-yyyy_HH-ss"). 
						format(new GregorianCalendar().getTime())+ ".png"; 

				String failureImageFileNamePath = RESULT_FOLDER_LOCATION+failureImageFileName; 
				
				captureObj=CaptureScreenUtil.getCaptureScreenUtil();
				if(isRemoteDriver==false){
					//commonFunctions.TakeScreenshotLink(failureImageFileNamePath,resultStep);
					captureObj.TakeScreenshotLink(driver, failureImageFileNamePath, resultStep);
				}else{
					//commonFunctions.TakeScreenshotLinkforRemoteDriver(failureImageFileNamePath,resultStep);
					captureObj.TakeScreenshotLinkforRemoteDriver(driver, failureImageFileNamePath, resultStep);
				}

				linkStyle=ExcelStyleUtil.linkCellStyle(wb,IndexedColors.BLUE.getIndex());
				HSSFHyperlink file_link=new HSSFHyperlink(HSSFHyperlink.LINK_FILE);

		//		file_link.setAddress("file:///"+failureImageFileNamePath);
				rowhead.createCell((short) 12).setCellValue(failureImageFileName);
				rowhead.getCell((short) 12).setHyperlink(file_link);
				rowhead.getCell((short) 12).setCellStyle(linkStyle);

			} 
			
			else if (Result.equalsIgnoreCase("Warning")) {
				style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				ExcelStyleUtil.setStatusStyle(step_style,cellStatus, wb, Result);
				txt = "WARNING";

			}
			
			else if (Result.equalsIgnoreCase("Info")) {
				style.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

				color = HSSFColor.WHITE.index;
		
				ExcelStyleUtil.setStatusStyle(step_style,cellStatus, wb, Result);
				txt = "INFO";
			}
			
			cellStatus.setCellValue(txt);
//////
			
			rowhead.createCell((short) 10).setCellValue(txt);
			HSSFCell cell = tSheet.getRow((short)resultStep).getCell((short)10);      
			cell.setCellStyle(style);
			
////		
			
			
			rowhead.createCell((short) 11).setCellValue(dateFormat.format(cal.getTime()));



			
			FileOutputStream fileOut = new FileOutputStream(testSummaryFilePath);
		
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception rea) {			
			logsObj.logError("Reporter:Unable to log the test step details due to error-", rea);
			
		}
	}
	
	*//**
	 * fetches the testcaseID and set as hyperlink
	 * @author khshaik
	 * @date Oct 17 2014
	 * @param rowhead
	 * @param testcaseID
	 *//*
	private void getTestcaseReference(Row rowhead,String testcaseID){
		HSSFHyperlink mat_link=new HSSFHyperlink(HSSFHyperlink.LINK_FILE);

		mat_link.setAddress(testRepo_url+testcaseID);
		rowhead.createCell((short) 3).setCellValue(testcaseID);
		rowhead.getCell((short) 3).setHyperlink(mat_link);
		rowhead.getCell((short) 3).setCellStyle(linkStyle);
	}

*/

}
