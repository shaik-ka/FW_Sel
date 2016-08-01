package com.frw.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Hashtable;

import com.frw.log.Logs;


public class OracleDBUtil {
	
	private static OracleDBUtil DBOracleUtilObj;
	private static Logs logsObj;
	public static boolean isDBParametersInitialized=false;	

	private static String INI_DB_PROVIDER="";
	private static String INI_DB_DATASOURCE="";
	private static String INI_DB_DATASOURCEPORT="";
	private static String INI_DB_DATABASE="";
	private static String INI_DB_USERID="";
	private static String INI_DB_PSWD="";

	private OracleDBUtil(){}

	public static OracleDBUtil getDBUtilObj(){
		if(DBOracleUtilObj==null){
			DBOracleUtilObj=new OracleDBUtil();
		}
		return DBOracleUtilObj;
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

		//String connectorString = Provider+DataSource+DataSourcePort+";databaseName="+DataBase+";selectMethod=cursor;username="+DB_UserID+";password="+DB_PSW;
		String connectorString=Provider+DataSource+DataSourcePort+"/"+DataBase;
		
		//establish connection
		try
		{
			logsObj.logInfo("Start connection established to the datase "+DataBase);
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			con = DriverManager.getConnection(connectorString,DB_UserID,DB_PSW);
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

	public Object[][] fetchOracleData(String query){

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
		//	preStmt = conObj.prepareStatement(query);
			
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
						logsObj.logError("fetchOracleData-unable to fetch the data from the row-->"+row, t);

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
			logsObj.logError("fetchOracleData-unable to fetch from due to -->", t);
		}

		try {
			if(conObj!=null && !conObj.isClosed())
			{
				rs.close();
				conObj.close();
			}
		} catch (SQLException e) {
			//System.out.println("fetchSQLData-unable to close the connection");
			logsObj.logError("fetchOracleData-unable to close the connection due to -->", e);

		}
		return data;
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
	public Hashtable<String,String> getdataFromObject(Object[][] obj, int fetchRowNumber){
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
	
	/**
	 * executes the specified sql query and fetches the result set
	 * @author sahamed
	 * @Date 13 Sep 2013
	 * @param query
	 * @param ResultsetRow
	 * @return
	 */
	public  Hashtable<String,String> fetchOracleData(String query,int ResultsetRow){
		// *******************  Nuview Related ********************************************************** 
		/*if(query.contains("RegulatorVariable")){
			log("fetchSQLData:SQL query has regulator variable,hence need to replace");
			query=commonFunctions.replaceString("RegulatorVariable", query, Constants.Regulator);
		}*/
		
		Object[][] resultset=fetchOracleData(query);
		Hashtable<String,String> resultsetHash=getdataFromObject(resultset, ResultsetRow);
		return resultsetHash;
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

}
