package com.frw.htmlreporting;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.frw.base.Base;
import com.frw.util.CaptureScreenUtil;
import com.frw.util.DateUtil;
import com.frw.util.FileUtil;


public class HTMLReporting_2015_bacUp extends Base{

	//BufferedWriter main_bw=null;
	//String screenShotLoc="D:\\HTMLReporting\\Screenshots\\";
	DateUtil dateObj;
	FileUtil fileObj;
	private static HTMLReporting_2015_bacUp HTMLReportingObj;
	private static CaptureScreenUtil captureObj;

	boolean isRemoteDriver=true;
	private static String replace_totalScenarioExecuted="Replace_Total Scenarios_Executed";
	private static String replace_totalTestsExecuted="Replace_Total_Tests_Executed";
	private static String replace_testsPassed="Replace_Tests_Passed";
	private static String replace_testsFailed="Replace_Tests_Failed";
	private static String replace_graph_testsPassed="Replace_graph_Tests_Passed";
	private static String replace_graph_testsFailed="Replace_graph_Tests_Failed";
	private static String replace_duration="Replace_Duration";
	private static String testRepo="";
	private static String Prv_scenario="";
	private static String htmlVersionFolder="HTMLReportingFiles_1.1";

	Date suite_srt;
	Date suite_end;
	private static String screenShotLoc="";
	String screenExt=".jpg";
	static int scenarioCount=0;
	private static int stepNo=1;
	public static int passCount=0;
	public static int failCount=0;


	String initiazlized_styleSheet="csssheets/res.css";
	//********************* CSS ID's and Classes ***********************************************
	String id_topheader="topheader";
	String id_topheadersuite="topheader_suite";
	String id_testresultsummary="testresultsummary";
	String id_testsummary="testsummary";
	String class_menu="menudiv";
	String class_head="grad2";
	String class_fail="alt";
	String style_width1="width:1%";
	String style_width3="width:3%";
	String style_widthAuto="width:auto";

	String graph_script="<script type=\"text/javascript\" src=\"csssheets/jsapi.js\"></script> "+
			"<script type=\"text/javascript\" src=\"jquery-latest.js\"></script>"+
			"<script type=\"text/javascript\" src=\"csssheets/jquery-1.11.3.min.js\"></script>"+
			"<script type=\"text/javascript\">"+
			" google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"+
			" google.setOnLoadCallback(drawChart);"+	
			" function drawChart() {"+
			" var data = google.visualization.arrayToDataTable(["+
			" ['Tests', 'Executed'],"+
			" ['Pass',     "+replace_graph_testsPassed+"],"+
			" ['Fail',     "+replace_graph_testsFailed+"]"+           
			" ]);"+

      					" var options = { "+
      					" title: 'Test Execution Percentage',"+
      					" is3D: true,"+
      					" };"+

      					" var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));"+
      					" chart.draw(data, options);"+
      					" } "+
      					" </script> "+
      					"<script> "+
      					"$(document).ready(function(){ "+
      					"	$('.filters').on( 'click', 'input', function() { "+
      					"		var val1 = $('input[name=Option1]:checked').val(); "+

						"			if (val1 == \"Show Only Pass\") { "+
						"				$(\".nalt\").show(); "+
						"		        $(\".alt\").hide(); "+
						"           } "+
						"			else if (val1 ==\"Show Only Fail\"){ "+
						"					$(\".alt\").show(); "+
						"					$(\".nalt\").hide(); "+
						"			   } "+
						"			else{ "+
						"				$(\".alt\").show(); "+
						"				$(\".nalt\").show(); "+
						"			} "+

						"        }); "+
						"   }) "+
						"			</script> "+
						"			<script> "+
						" $(document).ready(function(){ "+
						"   $(\"#flip\").click(function(){ "+
						"      $(\"#panel\").slideToggle(\"slow\"); "+
						"    }); "+
						" }); "+
						"			</script> "	;


	private HTMLReporting_2015_bacUp(){};

	public static HTMLReporting_2015_bacUp getHTMLReportingObject(){
		if(HTMLReportingObj==null){
			HTMLReportingObj=new HTMLReporting_2015_bacUp();
		}

		return HTMLReportingObj;
	}
	/**
	 * Fetches/creates the html file to be generated
	 * @author khshaik
	 * @date Oct 8 2014 
	 * @param filefullPath
	 */

	private BufferedWriter fetchFile(String filefullPath){	
		BufferedWriter bw=null;
		try{
			File file =new File(filefullPath);
			FileWriter fw=new FileWriter(file);
			bw=new BufferedWriter(fw);
		}catch(Throwable t){
			t.printStackTrace();
			logsObj.logError("fetchFile:-Unable to fetch/create the HTML file"+filefullPath+" due to error-",t);
		}			

		return bw;
	}
	/**
	 * Closing tags for the main html page
	 * @author khshaik
	 * @date Oct 9 2014
	 * @throws IOException
	 */
	public void closingTags(BufferedWriter bw) {
		try{
			bw.append("</table>");
			bw.append("<div align=\"right\"><font size=\"3\" color=\"green\">Designed By : Shaik K.A.</font></div>");
			bw.append("</section>");
			bw.append("</div>");
			bw.append("</body >");
			bw.append("</html>");
			bw.close();
		}catch(Throwable t){
			t.printStackTrace();
			logsObj.logError("closingTags:-Unable to close the tags of test summary results page due to error-",t);
		}

	}

	/**
	 * Initialize html opening tags of the main page
	 * @author khshaik
	 * @date Oct 9 2014
	 * @param filefullPath
	 * @param stylesheetPath
	 * @return
	 */
	private BufferedWriter htmldoctype(String filefullPath,String stylesheetPath){
		BufferedWriter bw=null;
		try{
			bw=fetchFile(filefullPath);
			bw.append("<!DOCTYPE html> \n");
			bw.append("<html> \n");

			//"<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\""



		}catch (Throwable t){
			t.printStackTrace();
			logsObj.logError("htmldoctype:-Unable to map document type and stylesheet for the "+filefullPath+" due to error",t);
		}

		return bw;
	}

	/**
	 * Initialize style sheet location, graph script and Header of the main page
	 * @author khshaik
	 * @Date Oct 9 2014
	 * @param filefullPath
	 * @param stylesheetPath
	 * @return
	 */
	private BufferedWriter htmlstylesheet(String filefullPath){
		BufferedWriter bw=null;
		try{
			bw=htmldoctype(filefullPath, initiazlized_styleSheet);
			bw.append("<head> \n");
			String ss1="<link rel=\"stylesheet\" type=\"text/css\"";
			String ss2="href=\""+initiazlized_styleSheet+"\">\n";
			bw.append(ss1+ss2);
			bw.append(graph_script);
			bw.append("</head>\n");
			bw.append("<body style=\"overflow: auto\">\n");

		}catch(Throwable t){
			t.printStackTrace();
			logsObj.logError("htmlstylesheet:-Unable to stylesheet "+initiazlized_styleSheet+"for the "+filefullPath+" due to error",t);
		}

		return bw;
	}

	/**
	 * Creates an HTML file and map the style sheet
	 * @author khshaik
	 * @Date Oct 14 2014
	 * @param filefullPath
	 * @param stylesheetPath
	 * @param title
	 * @return
	 */
	private BufferedWriter createHTMLAndMapStyleSheet(String filefullPath){
		BufferedWriter bw=null;
		bw=htmlstylesheet(filefullPath);

		return bw;
	}
	/**
	 * Included list of suites as menus in the main page
	 * @author khshaik
	 * @Date Oct 9 2014
	 * @param filefullPath
	 * @param stylesheetPath
	 * @param suitesList
	 * @return
	 */
	private BufferedWriter includeSuitesMenu(BufferedWriter bw,LinkedHashMap<String,String>suitesList,String title){


		try{

			bw.append("<nav> \n");
			bw.append("<ul> \n");
			/* Enumeration<String>  suites=suitesList.keys();
			while(suites.hasMoreElements()){
				String suiteName=(String) suites.nextElement();
				bw.append("<li class=\""+class_menu+"\"><a href=\""+suiteName+".html\">"+suiteName+"</a></li>");
			}*/
			List<String> suites = new ArrayList <String>(suitesList.keySet());
			for(int suite=0;suite<suites.size();suite++){
				String suiteName=suites.get(suite);
				bw.append("<li class=\""+class_menu+"\"><a href=\""+suiteName+".html\">"+suiteName+"</a></li>");
			}
			bw.append("</ul>");
			bw.append("</nav>");

			bw.append("<div class=\"openNav\">");
			bw.append("  <div class=\"icon\">  ");
			bw.append("</div>");
			bw.append("</div>");
			bw.append("<script>");
			bw.append("$(\".openNav\").click(function() {");
			bw.append("$(\"body\").toggleClass(\"navOpen\");");
			bw.append("$(\"nav\").toggleClass(\"open\");");
			bw.append("$(\".wrapper\").toggleClass(\"open\");");
			bw.append("$(this).toggleClass(\"open\");");
			bw.append("});");
			bw.append("</script>");

			bw.append("<div class=\"wrapper\" >\n");
			bw.append("<section>\n");


			if(title.contains("Suite")){
				bw.append("<h1 id=\""+id_topheadersuite+"\">"+title+"</h1> \n");
			}else{
				bw.append("<h1 id=\""+id_topheader+"\">"+title+"</h1> \n");
			}


		}catch(Throwable t){
			t.printStackTrace();
			logsObj.logError("includeSuitesMenu:-Unable to include the suites in the summary page due to error-",t);
		}


		return bw;

	}
	/**
	 * Publish Test Result Summary Table and graph in the main page
	 * @author khshaik
	 * @Date Oct 10 2014
	 * @return
	 */
	private BufferedWriter TestResultSummaryTableAndGraph(BufferedWriter bw,String url){
		try{
			bw.append("<table id=\""+id_testresultsummary+"\" align=\"right\" width=\"64%\">");
			bw.append("<tr >");
			bw.append("<th class=\""+class_head+"\">AUT URL:</th>");
			bw.append("<td colspan=\"5\">"+url+"</td>");
			bw.append("</tr>");

			bw.append("<tr >");
			bw.append("<th class=\""+class_head+"\">Total Scenarios Executed #:</th>");
			bw.append("<th class=\""+class_head+"\">Total Tests Executed #:</th>");
			bw.append("<th class=\""+class_head+"\">Tests Passed #:</th>");
			bw.append("<th class=\""+class_head+"\">Tests Failed #:</th>");			

			bw.append("<th class=\""+class_head+"\">Test Suite Execution Date:</th>");
			bw.append("<th class=\""+class_head+"\">Total Duration:</th>");
			bw.append("</tr>");
			bw.append("<tr >");
			bw.append(" <td>"+replace_totalScenarioExecuted+"</td>");
			bw.append(" <td>"+replace_totalTestsExecuted+"</td>");
			bw.append(" <td>"+replace_testsPassed+"</td>");
			bw.append(" <td>"+replace_testsFailed+"</td>");

			bw.append("<td>"+dateObj.getFormattedDate()+"</td>");
			bw.append("<td>"+replace_duration+"</td>");
			bw.append("</tr>");
			bw.append("</table>");
			bw.append("<div id=\"piechart_3d\" style=\"width: 36%; height: 140px;\" align =\"left\" ></div>");
			bw.append("<div id=\"flip\">Click to Search</div>");
			bw.append("<div id=\"panel\">");
			bw.append("<div  class=\"filters\"  align=\"left\" id=\"rcorners2\">");
			bw.append("<legend style=\"color:black;font-weight:bold\",\"align=\"center\"></legend>");
			bw.append("<label><input type=\"radio\" name=\"Option1\" value=\"Show Only Pass\" > Pass</label>");
			bw.append("<label><input type=\"radio\" name=\"Option1\" value=\"Show Only Fail\" > Fail</label>");
			bw.append("<label><input type=\"radio\" name=\"Option1\" value=\"Show All\" checked>All</label>");
			bw.append("</div> ");
			bw.append("</div>");

		}catch(Throwable t){
			t.printStackTrace();
			logsObj.logError("TestResultSummaryTableAndGraph:-Unable to create Test Result Summary table due to error-",t);
		}

		return bw;
	}

	/**
	 *  Publish Test Summary Table Head in the main page
	 *  @author khshaik
	 *  @Date Oct 10 2014
	 * @return
	 */
	private BufferedWriter TestSummaryTableHead(BufferedWriter bw){
		try{
			bw.append("<table id=\""+id_testsummary+"\" align=\"top\">");
			bw.append("<tr>");
			bw.append("<th style=\""+style_width3+"\"></th>");
			bw.append("<th class=\""+class_head+"\">Scenario Name</th>");
			bw.append("<th class=\""+class_head+"\">TestCase Name/ID</th>");
			//bw.append("<th class=\""+class_head+"\">Status</th>");
			bw.append("<th style=\""+style_width1+"\">Status</th>");
			bw.append("<th class=\""+class_head+"\">Start Time</th>");
			bw.append("<th class=\""+class_head+"\">End Time</th>");
			//bw.append("<th class=\""+class_head+"\">Duration(HH:MM:SS)</th>");
			bw.append("<th style=\""+style_width1+"\">Duration(HH:MM:SS)</th>");
			bw.append("</tr>");


		}catch(Throwable t){
			t.printStackTrace();
			logsObj.logError("TestSummaryTableHead:-Unable to publish the Head of Test Summary table head due to error-",t);
		}
		return bw;
	}


	/**
	 * Logs the status of test under execution in the summary page
	 * @author khshaik
	 * @Date Oct 10 2014
	 * @param bw
	 * @param scenarioName
	 * @param testcaseName
	 * @param testStatus
	 * @param test_srtime
	 * @param test_endtime
	 * @param duration
	 * @return
	 */
	public BufferedWriter logTestStatus(BufferedWriter bw,String scenarioName,String testcaseName,String testStatus,String test_srtime,String test_endtime){
		try{
			if(testStatus.equalsIgnoreCase("Pass")){
				passCount++;
				bw.append("<tr class=\"nalt\">");
				bw.append("<td><div id=\"imgsuccess\"/></td>");

			}else{
				failCount++;
				bw.append("<tr class=\"alt\">");
				bw.append("<td><div id=\"imgfailure\"/></td>");
			}
			if(!scenarioName.equals(Prv_scenario)){
				scenarioCount++;
			}

			tdTestSummaryTemplate( bw,scenarioName, testcaseName, testStatus, test_srtime, test_endtime,  dateObj.duration(dateObj.getDateFromString(test_srtime), dateObj.getDateFromString(test_endtime)));

		}catch(Throwable t){
			t.printStackTrace();
			logsObj.logError("logTestStatus:-Unable to publish the td for test case-"+testcaseName+" due to error-",t);
		}

		return bw;
	}

	/**
	 * creates an td for the test summary
	 * @author khshaik
	 * @Date Oct 13 2014
	 * @param bw
	 * @param scenarioName
	 * @param testcaseName
	 * @param testStatus
	 * @param test_strtime
	 * @param test_endtime
	 * @param duration
	 * @return
	 * @throws Throwable
	 */
	private BufferedWriter tdTestSummaryTemplate(BufferedWriter bw,String scenarioName,String testcaseName,String testStatus,String test_strtime,String test_endtime,String duration) throws Throwable{

		bw.append("<td>"+scenarioName+"</td>");
		bw.append("<td>"+testcaseName+"</td>");
		bw.append("<td>"+testStatus+"</td>");
		bw.append("<td>"+test_strtime+"</td>");
		bw.append("<td>"+test_endtime+"</td>");
		bw.append("<td>"+duration+"</td>");
		bw.append("</tr>");

		return bw;
	}

	/**
	 * Create Test Summary HTML with required tables and Include suites under execution
	 * @author khshaik
	 * @date Oct 16 2014
	 * @param testSummaryFilePath
	 * @param styleSheetPath
	 * @param title
	 * @param suites
	 * @param AUT_Url
	 * @return
	 */
	public BufferedWriter createHTMLForTestSummary(String testSummaryFilePath,String resultsFolder,Boolean isRemoteDriverExecution,String testRepositoryURL, String title,LinkedHashMap<String, String> suites,String AUT_Url){
		isRemoteDriver=isRemoteDriverExecution;
		testRepo=testRepositoryURL;
		fileObj=FileUtil.getFileUtilObject();
		fileObj.deleteFilesinFolder(resultsFolder);
		copyReqFolder(resultsFolder);		
		dateObj=DateUtil.getDateUtilObject();
		suite_srt=dateObj.getDate();

		BufferedWriter summary_bfw=createHTMLAndMapStyleSheet(testSummaryFilePath);
		includeSuitesMenu(summary_bfw,suites,title);
		TestResultSummaryTableAndGraph(summary_bfw,AUT_Url);
		TestSummaryTableHead(summary_bfw);
		return summary_bfw;
	}

	/**
	 * Create Test Summary HTML with required tables
	 * @author khshaik
	 * @date Oct 16 2014
	 * @param suiteFilePath
	 * @param title
	 * @param screensFolderPath
	 * @return
	 * @throws IOException 
	 */
	public BufferedWriter createHTMLForSuiteExecution(String suiteFilePath,String title,String screensFolderPath) throws IOException{

		BufferedWriter suite_bfw=createHTMLAndMapStyleSheet(suiteFilePath);
		suite_bfw.append("<h1 id=\""+id_topheadersuite+"\">"+title+"</h1> \n");
		SuiteSummaryTableHead(suite_bfw,screensFolderPath);

		return suite_bfw;
	}

	//************************************** Suite level *********************************************************
	/**
	 * Creates the Head for Suite
	 * @author khshaik
	 * @Date Oct 14 2014
	 * @param bw
	 * @return
	 */
	private BufferedWriter SuiteSummaryTableHead(BufferedWriter bw,String loc_screenShot){
		screenShotLoc=loc_screenShot;
		try{
			bw.append("<table id=\""+id_testsummary+"\" align=\"left\" ><tr >");

			bw.append("<table id=\""+id_testsummary+"\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"<tr >");
			//bw.append("<th class=\""+class_head+"\">Stp.No</th>"); 
			bw.append("<th style=\""+style_width1+"\">Stp.No</th>");
			bw.append("<th class=\""+class_head+"\">Suite/Module Name</th>");
			bw.append("<th class=\""+class_head+"\">ScenarioName/ID</th>");
			bw.append("<th class=\""+class_head+"\">Test RefID</th>");			
			bw.append("<th class=\""+class_head+"\">Testcase Name</th>");
			bw.append("<th class=\""+class_head+"\">Browser Name</th>");
			bw.append("<th class=\""+class_head+"\">Browser Version</th>");
			bw.append("<th class=\""+class_head+"\">Environment</th>");
			//bw.append("<th style=\""+style_width3+"\">Step</th>");
			bw.append("<th class=\""+class_head+"\">Step/Action Details</th>");
			bw.append("<th style=\""+style_width3+"\">Step Description</th>");
			//bw.append("<th class=\""+class_head+"\">Step Description</th>");			
			bw.append("<th class=\""+class_head+"\">Status</th>");
			//	bw.append("<th class=\""+class_head+"\">Screenshot</th>");
			bw.append("<th class=\""+class_head+"\">Executed on</th>");
			//bw.append("<th class=\""+class_head+"\">Screenshot</th>");
			bw.append("<th style=\""+style_width1+"\">Screenshot</th>");
			bw.append("</tr>");

		}catch (Throwable t){
			t.printStackTrace();
			logsObj.logError("SuiteSummaryTableHead:-Unable to create the Suite Summary Table due to error-",t);
		}

		return null;		
	}

	/**
	 * Creates an td for suite test step
	 * @author khshaik
	 * @date Oct 14 2014
	 * @param bw
	 * @param stpNo
	 * @param suiteName
	 * @param testcaseName
	 * @param browserName
	 * @param browserVersion
	 * @param environment
	 * @param step
	 * @param stepDescription
	 * @param status
	 * @param screenShotPath
	 * @return
	 * @throws Throwable
	 */
	private BufferedWriter tdTestStepTemplate(WebDriver driver,BufferedWriter bw,String suiteName,String scenarioName,String TestcaseRefID ,String testcaseName,String browserName,String browserVersion,String environment,String step,String stepDescription,String status) throws Throwable{

		bw.append("<td>"+stepNo+"</td>");
		bw.append("<td>"+suiteName+"</td>");
		bw.append("<td>"+scenarioName+"</td>");

		//bw.append("<td>"+TestcaseRefID+"</td>");
		if(!testRepo.equalsIgnoreCase("")){
			bw.append("<td> <a href=\""+testRepo+TestcaseRefID+"\">"+TestcaseRefID+"</a> </td>");
		}else{
			bw.append("<td>"+TestcaseRefID+"</td>");
		}

		bw.append("<td>"+testcaseName+"</td>");
		bw.append("<td>"+browserName+"</td>");
		bw.append("<td>"+browserVersion+"</td>");
		bw.append("<td>"+environment+"</td>");
		bw.append("<td>"+step+"</td>");
		bw.append("<td>"+stepDescription+"</td>");
		bw.append("<td>"+status+"</td>");		
		bw.append("<td>"+dateObj.getFormattedDate()+"</td>");
		if(status.equalsIgnoreCase("Fail")){

			String RESULT_FOLDER_LOCATION=screenShotLoc;
			String failureImageFileName=testcaseName+"_"+stepNo+"_" + new SimpleDateFormat("MM-dd-yyyy_HH-ss"). 
					format(new GregorianCalendar().getTime())+ ".png"; 

			String failureImageFileNamePath = RESULT_FOLDER_LOCATION+failureImageFileName;
			captureObj=CaptureScreenUtil.getCaptureScreenUtil();
			//captureObj=captureObj.getCaptureScreenUtil();
			if(isRemoteDriver==false){
				//commonFunctions.TakeScreenshotLink(failureImageFileNamePath,resultStep);
				captureObj.TakeScreenshotLink(driver, failureImageFileNamePath, stepNo);
			}else{
				//commonFunctions.TakeScreenshotLinkforRemoteDriver(failureImageFileNamePath,resultStep);
				captureObj.TakeScreenshotLinkforRemoteDriver(driver, failureImageFileNamePath, stepNo);
			}

			//bw.append("<td><a href=\"D:\\\\HTMLReporting\\Screenshots\\07.jpg\"><img alt=\"07.jpg\" src=\"D:\\\\HTMLReporting\\Screenshots\\0.jpg\" width=\"80\" height=\"40\" /></a> </td>");
			//bw.append("<td><a href=\""+screenShotLoc+screenName+screenExt+"\"><img alt=\""+screenName+"\" src=\""+screenShotLoc+screenName+screenExt+"\" width=\"80\" height=\"40\" /></a> </td>");
			bw.append("<td><a href=\""+failureImageFileNamePath+"\"><img alt=\""+failureImageFileName+"\" src=\""+failureImageFileNamePath+"\" width=\"80\" height=\"40\" /></a> </td>");
		}else{
			bw.append("<td></td>");
		}
		//bw.append("<td><a href=\""+screenShotPath+"\">"+screenShotPath+"</a></td>");

		bw.append("</tr>");
		stepNo++;
		return bw;
	}

	/**
	 * Logs the test step details of a suite
	 * @param bw
	 * @param stpNo
	 * @param suiteName
	 * @param testcaseName
	 * @param browserName
	 * @param browserVersion
	 * @param environment
	 * @param step
	 * @param stepDescription
	 * @param stepStatus
	 * @param screenShotPath
	 * @return
	 */
	public BufferedWriter logTestStepDetails(WebDriver driver,BufferedWriter bw,String suiteName,String scenarioName,String TestcaseRefID,String testcaseName,String browserName,String browserVersion,String environment,String step,String stepDescription,String stepStatus){
		try{
			if(stepStatus.equalsIgnoreCase("Pass")){
				bw.append("<tr>");

			}else{
				bw.append("<tr class=\"alt\">");
			}

			//Nov 3
			//tdTestStepTemplate(driver, bw,suiteName,scenarioName,TestcaseRefID,testcaseName,browserName,browserVersion,environment,step,stepDescription,stepStatus,screenShotPath);
			tdTestStepTemplate(driver, bw,suiteName,scenarioName,TestcaseRefID,testcaseName,browserName,browserVersion,environment,step,stepDescription,stepStatus);

		}catch(Throwable t){
			t.printStackTrace();
			logsObj.logError("logTestStepDetails:-Unable to publish the td for test case-"+testcaseName+" due to error-",t);
		}

		return bw;
	}


	/**
	 * Read complete HTML report and write values which are required
	 * @author khshaik
	 * @date Oct 15 2014
	 * @param TotalValue
	 * @param TotalExecuted
	 * @param Passed
	 * @param Failed
	 * @return get values from the test summary result count and write in the test summary count
	 * @throws IOException
	 */

	public  String UpdateHTMLTestSummary(String filePath) {
		suite_end=dateObj.getDate();
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}

			int cnt1 = contentBuilder.indexOf(replace_totalScenarioExecuted);
			contentBuilder.replace(cnt1,cnt1+replace_totalScenarioExecuted.length(),String.valueOf(scenarioCount));			   
			int  TotalExecuted=passCount+failCount;

			int cnt2 = contentBuilder.indexOf(replace_totalTestsExecuted);
			contentBuilder.replace(cnt2,cnt2+replace_totalTestsExecuted.length(),String.valueOf(TotalExecuted));

			int cnt3 = contentBuilder.indexOf(replace_testsPassed);
			contentBuilder.replace(cnt3,cnt3+replace_testsPassed.length(),String.valueOf(passCount));

			int cnt4 = contentBuilder.indexOf(replace_testsFailed);
			contentBuilder.replace(cnt4,cnt4+replace_testsFailed.length(),String.valueOf(failCount));

			int cnt5 = contentBuilder.indexOf(replace_duration);
			contentBuilder.replace(cnt5,cnt5+replace_duration.length(),dateObj.duration(suite_srt, suite_end));

			int cnt6 = contentBuilder.indexOf(replace_graph_testsPassed);
			contentBuilder.replace(cnt6,cnt6+replace_graph_testsPassed.length(),String.valueOf(passCount));

			int cnt7 = contentBuilder.indexOf(replace_graph_testsFailed);
			contentBuilder.replace(cnt7,cnt7+replace_graph_testsFailed.length(),String.valueOf(failCount));


			in.close();
		} catch (IOException e) {
			logsObj.logError("UpdateHTMLTestSummary:- Unable to update the Execution summary details in Test Summary page due to error-",e);
		}

		try{
			FileWriter fstream = new FileWriter(filePath);
			BufferedWriter outobj = new BufferedWriter(fstream);
			outobj.write(contentBuilder.toString());
			outobj.close();

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		String content = contentBuilder.toString();


		return content;
	}

	/** 
	 * copies the required folders for html reporting from project location to results location
	 * @author khshaik
	 * @date Oct 17 2014
	 * @param resultsFolder
	 */
	void copyReqFolder(String resultsFolder){
		String sourceFile_styles=System.getProperty("user.dir")+"//"+htmlVersionFolder+"//csssheets";
		String destFile_styles=resultsFolder+"//csssheets";

		String sourceFile_images=System.getProperty("user.dir")+"//"+htmlVersionFolder+"//images";
		String destFile_images=resultsFolder+"//images";

		fileObj.copyFolders(sourceFile_styles, destFile_styles);
		fileObj.copyFolders(sourceFile_images, destFile_images);
	}
	/**
	 * Creates an td for suite test step
	 * @author khshaik
	 * @date Jan 17 2016
	 * @param bw
	 * @param stpNo
	 * @param suiteName
	 * @param testcaseName
	 * @param browserName
	 * @param browserVersion
	 * @param environment
	 * @param step
	 * @param stepDescription
	 * @param status
	 * @param screenShotPath
	 * @return
	 * @throws Throwable
	 */

	private BufferedWriter tdTestStepTemplate(BufferedWriter bw,String suiteName,String scenarioName,String TestcaseRefID ,String testcaseName,String browserName,String browserVersion,String environment,String step,String stepDescription,String status) throws Throwable{

		bw.append("<td>"+stepNo+"</td>");
		bw.append("<td>"+suiteName+"</td>");
		bw.append("<td>"+scenarioName+"</td>");

		//bw.append("<td>"+TestcaseRefID+"</td>");
		if(!testRepo.equalsIgnoreCase("")){
			bw.append("<td> <a href=\""+testRepo+TestcaseRefID+"\">"+TestcaseRefID+"</a> </td>");
		}else{
			bw.append("<td>"+TestcaseRefID+"</td>");
		}

		bw.append("<td>"+testcaseName+"</td>");
		bw.append("<td>"+browserName+"</td>");
		bw.append("<td>"+browserVersion+"</td>");
		bw.append("<td>"+environment+"</td>");
		bw.append("<td>"+step+"</td>");
		bw.append("<td>"+stepDescription+"</td>");
		bw.append("<td>"+status+"</td>");		
		bw.append("<td>"+dateObj.getFormattedDate()+"</td>");
		if(status.equalsIgnoreCase("Fail")){

			/*		String RESULT_FOLDER_LOCATION=screenShotLoc;
				String failureImageFileName=testcaseName+"_"+stepNo+"_" + new SimpleDateFormat("MM-dd-yyyy_HH-ss"). 
						format(new GregorianCalendar().getTime())+ ".png"; 

				String failureImageFileNamePath = RESULT_FOLDER_LOCATION+failureImageFileName;
				captureObj=CaptureScreenUtil.getCaptureScreenUtil();
				//captureObj=captureObj.getCaptureScreenUtil();
				if(isRemoteDriver==false){
					//commonFunctions.TakeScreenshotLink(failureImageFileNamePath,resultStep);
					captureObj.TakeScreenshotLink(driver, failureImageFileNamePath, stepNo);
				}else{
					//commonFunctions.TakeScreenshotLinkforRemoteDriver(failureImageFileNamePath,resultStep);
					captureObj.TakeScreenshotLinkforRemoteDriver(driver, failureImageFileNamePath, stepNo);
				}				
				bw.append("<td><a href=\""+failureImageFileNamePath+"\"><img alt=\""+failureImageFileName+"\" src=\""+failureImageFileNamePath+"\" width=\"80\" height=\"40\" /></a> </td>");
			 */
		}else{
			bw.append("<td></td>");
		}
		//bw.append("<td><a href=\""+screenShotPath+"\">"+screenShotPath+"</a></td>");

		bw.append("</tr>");
		stepNo++;
		return bw;
	}

	/**
	 * Logs the test step details of a suite
	 * @author Shaik
	 * @date Jan 17 2016
	 * @param bw
	 * @param stpNo
	 * @param suiteName
	 * @param testcaseName
	 * @param browserName
	 * @param browserVersion
	 * @param environment
	 * @param step
	 * @param stepDescription
	 * @param stepStatus
	 * @param screenShotPath
	 * @return
	 */
	public BufferedWriter logTestStepDetails(BufferedWriter bw,String suiteName,String scenarioName,String TestcaseRefID,String testcaseName,String browserName,String browserVersion,String environment,String step,String stepDescription,String stepStatus){
		try{				
			if(stepStatus.equalsIgnoreCase("Fail")){
				bw.append("<tr class=\"alt\">");

			}else{
				bw.append("<tr>");
			}
			tdTestStepTemplate(bw,suiteName,scenarioName,TestcaseRefID,testcaseName,browserName,browserVersion,environment,step,stepDescription,stepStatus);

		}catch(Throwable t){
			t.printStackTrace();
			//log("logTestStepDetails:-Unable to publish the td for test case-"+testcaseName+" due to error-"+t);
			logsObj.logError("logTestStepDetails:-Unable to publish the td for test case-"+testcaseName+" due to error-",t);

		}

		return bw;
	}


}
