package com.frw.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import com.frw.log.Logs;




public class SQLDBUtil {
	private static SQLDBUtil DBUtilObj;
	private static Logs logsObj;
	public static boolean isDBParametersInitialized=false;	

	private static String INI_DB_PROVIDER="";
	private static String INI_DB_DATASOURCE="";
	private static String INI_DB_DATASOURCEPORT="";
	private static String INI_DB_DATABASE="";
	private static String INI_DB_USERID="";
	private static String INI_DB_PSWD="";

	private SQLDBUtil(){}

	public static SQLDBUtil getDateUtilObj(){
		if(DBUtilObj==null){
			DBUtilObj=new SQLDBUtil();
		}
		return DBUtilObj;
	}

	public void initializeDBParameters(String DB_PROVIDER,String DB_DATASOURCE,String DB_DATASOURCEPORT,String DB_DATABASE,String DB_USERID,String DB_PSWD){
		if (isDBParametersInitialized==false){
			INI_DB_PROVIDER=DB_PROVIDER;
			INI_DB_DATASOURCE=DB_DATASOURCE;	
			INI_DB_DATASOURCEPORT=DB_DATASOURCEPORT;
			INI_DB_DATABASE=DB_DATABASE;
			INI_DB_USERID=DB_USERID;
			INI_DB_PSWD=DB_PSWD;
			logsObj=Logs.getLogsObjAndInitialize("devpinoyLogger");
			logsObj.log("initializeDBParameters- All required DB Parameters are initialized");
			isDBParametersInitialized=true;
		}else{
			logsObj.log("initializeDBParameters- All required DB Parameters are already initialized");
		}
	}

	private Connection DBConnectionObject(){

		Connection con = null;

		String Provider=INI_DB_PROVIDER;
		String DataSource=INI_DB_DATASOURCE+":";
		String DataSourcePort=INI_DB_DATASOURCEPORT;
		String DataBase=INI_DB_DATABASE;
		String DB_UserID=INI_DB_USERID;
		String DB_PSW=INI_DB_PSWD;

		String connectorString = Provider+DataSource+DataSourcePort+";databaseName="+DataBase+";selectMethod=cursor;username="+DB_UserID+";password="+DB_PSW;

		//establish connection
		try
		{
			logsObj.logInfo("Start connection established to the datase "+DataBase);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			con = DriverManager.getConnection(connectorString);
			//System.out.println("********************** connection**********");
			logsObj.log("connection established to the datase "+DataBase +"now has to fetch the require data...");

		}
		catch(Throwable t)
		{
			//System.out.println("DBConnectionObject-Unable to establish the connection to the datase "+DataBase +" hence cannot fetch the require data"+" error is"+t);
			logsObj.logError("DBConnectionObject-Unable to establish the connection to the datase "+DataBase +" hence cannot fetch the require data"+" error is", t);
		}

		return con;
	}

	/**
	 * Fetch the data from SQL DB and store the Hash table result in Object[][] 
	 * @author sahamed
	 * @param query
	 * @return
	 */

	public Object[][] fetchSQLData(String query){

		Connection conObj = null;

		PreparedStatement preStmt=null;
		ResultSetMetaData rsmd=null;
		ResultSet rs=null;
		int row=0;
		Object[][] data = new Object[row][1];
		Hashtable<String, String> table  = new Hashtable<String, String>();
		int col= 0;
		int index=0;
		try

		{
			conObj=DBConnectionObject();
			preStmt = conObj.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			logsObj.log("Prepared Statement after connection for query-"+query);
			rs= preStmt.executeQuery();
			rsmd = rs.getMetaData();
			col = rsmd.getColumnCount();
			int rows= getResultSetRowCount(rs);
			data = new Object[rows][1];

			for(int i=1;i<col;i++)
			{
				@SuppressWarnings("unused")
				String col_Name= rsmd.getColumnName(i);						
			}
			while(rs.next())
			{
				row++;
				table  = new Hashtable<String, String>();
				String temp=null;
				for(int j=1;j<=col;j++)
				{
					try
					{

						String dataType= rsmd.getColumnTypeName(j);
						if(dataType.equalsIgnoreCase("MONEY")){
							temp= rs.getString(j);	
							temp=NumberConversionUtil.convertToDecimals("MONEY", temp);									 
						}else{
							temp= rs.getString(j);										
						}


					}
					catch(Throwable t)
					{
						logsObj.logError("fetchSQLData-unable to fetch the data from the row-->"+row, t);

					}
					if(temp== null)
						temp= new String("");

					table.put(rsmd.getColumnName(j), temp);	 

				}

				data[index][0]=table;
				index++;
			}

		}
		catch(Throwable t)
		{
			//System.out.println("fetchSQLData-unable to fetch from due to -->"+t.printStackTrace().);
			logsObj.logError("fetchSQLData-unable to fetch from due to -->", t);
		}

		try {
			if(conObj!=null && !conObj.isClosed())
			{
				rs.close();
				conObj.close();
			}
		} catch (SQLException e) {
			//System.out.println("fetchSQLData-unable to close the connection");
			logsObj.logError("fetchSQLData-unable to close the connection due to -->", e);

		}
		return data;
	}
	
	/**
	 * executes the specified sql query and fetches the result set
	 * @author sahamed
	 * @Date 13 Sep 2013
	 * @param query
	 * @param ResultsetRow
	 * @return
	 */
	public  Hashtable<String,String> fetchSQLData(String query,int ResultsetRow){
		// *******************  Nuview Related ********************************************************** 
		/*if(query.contains("RegulatorVariable")){
			log("fetchSQLData:SQL query has regulator variable,hence need to replace");
			query=commonFunctions.replaceString("RegulatorVariable", query, Constants.Regulator);
		}*/
		
		Object[][] resultset=fetchSQLData(query);
		Hashtable<String,String> resultsetHash=getdataFromObject(resultset, ResultsetRow);
		return resultsetHash;
	}

	/**
	 * Fetch the data from SQL DB and store the Hash table result in String [][] 
	 * @author sahamed
	 * @param inputQuery
	 * @return
	 */
	public String [][] fetch_SQLData(String query) 
	{
		Connection conObj = null;
		ResultSet resultSet =null;
		int rowCount = 0;
		int columnCount = 0;
		String myData [][] = null;

		try
		{

			conObj=DBConnectionObject();

			Statement statement = conObj.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet =statement.executeQuery(query);

			// Get Column count
			ResultSetMetaData resultSet_metaData= resultSet.getMetaData();
			columnCount = resultSet_metaData.getColumnCount();

			// Get Row Count
			while( resultSet.next() ) 
				rowCount++;

			//Initialize data structure
			myData = new String [rowCount][columnCount];

			resultSet.beforeFirst();


			//populate data structure
			for(int row=0; row<rowCount; row++)
			{
				resultSet.next();

				for(int col=1; col<=columnCount; col++)
					myData[row][col-1] = resultSet.getString(col); 
			}

			statement.close();
			conObj.close();

		}

		catch (Exception e)
		{
			logsObj.logError("fetch_SQLData-unable to fetch data due to -->", e);
		}
		
		
		try {
			if(conObj!=null && !conObj.isClosed())
			{
				//resultSet.close();
				conObj.close();
			}
		} catch (SQLException e) {
			//System.out.println("fetchSQLData-unable to close the connection");
			logsObj.logError("fetchSQLData-unable to close the connection due to -->", e);

		}
		

		return myData;


	}

	/**
	 * Execute the required query 
	 * @author sahamed
	 * @param query
	 * @return True for successful execution of query otherwise Fail
	 */
	public String executeQuery(String query){

		Connection conObj = null;		
		Statement Stmt=null;	
		String flag="False";
		try	{
			conObj=DBConnectionObject();
			Stmt = conObj.createStatement();
			Stmt.executeUpdate(query);
			flag="True";
			logsObj.log("executeQuery:-Successfully executed the Query-"+query);

		}
		catch(Throwable t)
		{
			logsObj.logError("executeQuery:-unable to execute the Query-"+query+"  due to-->",t);
		}

		try {
			if(conObj!=null && !conObj.isClosed())
			{

				conObj.close();
			}
		} catch (SQLException e) {
			logsObj.logError("executeQuery:-unable to close the connection",e);
			e.printStackTrace();
		}
		return flag;
	}


	/**
	 * Get row count of resultset
	 * @author sahamed
	 * @param resultSet
	 * @return
	 */
	private static int getResultSetRowCount(ResultSet resultSet) {
		if (resultSet == null) {
			return 0;
		}
		try {
			resultSet.last();
			return resultSet.getRow();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			try {
				resultSet.beforeFirst();
			} catch (SQLException exp) {
				exp.printStackTrace();
			}
		}
		return 0;
	}
	
	
	 
	/**
	 * fetch the data from Object[][] and store in HashTable
	 * @author khshaik
	 * @date Jul 03 2014
	 * @param obj
	 * @param fetchRowNumber
	 * @return
	 */

	
	@SuppressWarnings("unchecked")
	public static Hashtable<String,String> getdataFromObject(Object[][] obj, int fetchRowNumber){
		fetchRowNumber=fetchRowNumber-1;
				Hashtable<String,String> generalSheetData=new Hashtable<String,String>();
				try{
						if (obj.length!=0 || fetchRowNumber < obj.length ){								
							generalSheetData=(Hashtable<String, String>) obj[fetchRowNumber][0];
						}	
						Thread.sleep(1000L);						
				}catch(Exception e){
						logsObj.logError("getdataFromObject:-Unable to fetch the data due to error-->",e);					
				}
				
				return generalSheetData;
		}


}
