package com.frw.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Hashtable;

import com.frw.base.Base;


public class DBUtil extends Base{
	private static DBUtil DBUtilObj;
	public static boolean isDBParametersInitialized=false;	

	private static String INI_DB_PROVIDER="";
	private static String INI_DB_DATASOURCE="";
	private static String INI_DB_DATASOURCEPORT="";
	private static String INI_DB_DATABASE="";
	private static String INI_DB_USERID="";
	private static String INI_DB_PSWD="";
	
	private DBUtil(){}
	
	public static DBUtil getDateUtilObj(){
		if(DBUtilObj==null){
			DBUtilObj=new DBUtil();
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
			
			isDBParametersInitialized=true;
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
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			con = DriverManager.getConnection(connectorString);
			//System.out.println("********************** connection**********");
			//log("connection established to the datase "+DataBase +"now has to fetch the require data...");
			logsObj.log("connection established to the datase "+DataBase +"now has to fetch the require data...");

		}
		catch(Throwable t)
		{
			//log("Fail-Unable to establish the connection to the datase "+DataBase +" hence cannot fetch the require data"+" error is");
			logsObj.logError("Unable to establish the connection to the datase "+DataBase +" hence cannot fetch the require data"+" error is", t);
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
		@SuppressWarnings("unused")
		int rowIndex=0;

		conObj=DBConnectionObject();

		// access data 

		int index=0;

		//System.out.println("******************************************");
		try
		{
			preStmt = conObj.prepareStatement(query,rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
			//log("Prepared Statement after connection for query-"+query);
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
						//ErrorUtil.addVerificationFailure(t);
						//log("unable to fect the data from the row-->"+row);
						logsObj.logError("unable to fect the data from the row-->"+row+" due to error-",t);
						//t.printStackTrace();
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
			t.printStackTrace();
			logsObj.logError("unable to fect from due to-->",t);
		}

		try {
			if(conObj!=null && !conObj.isClosed())
			{
				rs.close();
				conObj.close();
			}
		} catch (SQLException e) {
			//ErrorUtil.addVerificationFailure(e);
			logsObj.logError("unable to close the connection",e);
			e.printStackTrace();
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
	public Hashtable<String,String> fetchSQLData(String query,int ResultsetRow){
				
		Object[][] resultset=fetchSQLData(query);
		Hashtable<String,String> resultsetHash=getdataFromObject(resultset, ResultsetRow);
		WaitUtil.pause(2);
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
	
	/**
	 * Fetch the Hash contents from the Object[][]
	 * @author khshaik
	 * @date Jul 03 2013
	 * @param obj
	 * @param fetchRowNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Hashtable<String,String> getdataFromObject(Object[][] obj, int fetchRowNumber){
		
		Hashtable<String,String> generalSheetData=new Hashtable<String,String>();
		try{
				if (obj.length!=0 || fetchRowNumber < obj.length ){								
					generalSheetData=(Hashtable<String, String>) obj[fetchRowNumber][0];
				}						
				return generalSheetData;
		}catch(Exception e){
			logsObj.logError(testcaseName+"Unable to fetch the data due to error-->",e);
			return generalSheetData;
		}
		
}


}
