package com.frw.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.frw.Constants.Constants_FRMWRK;
import com.frw.base.Base;



public class DateUtil extends Base {

	private static DateUtil dateUtilObject;


	private DateUtil(){}

	/**
	 * get the object reference of the DateUtil class
	 * @author khshaik
	 * @date Oct 15 2014
	 * @return
	 */
	public static DateUtil getDateUtilObject(){

		if(dateUtilObject==null){
			dateUtilObject=new DateUtil();
		}

		return dateUtilObject;
	}

	/**
	 * return the current date in a required format
	 * @param input
	 * @return
	 */

	public static String getCurrentDateInRequiredDateFormat(String dateFormat){
		String timeStamp =null;

		try{
			timeStamp = new SimpleDateFormat(dateFormat).format(Calendar.getInstance().getTime());
		}catch(Throwable t){
			// log("unable to format the given date format:"+dateFormat+" of the current date");
			logsObj.logError("unable to format the given date format:"+dateFormat+" of the current date due to error", t);
		}		 							 

		return timeStamp;
	}


	/**
	 * Increments the given date(US Format) by given increment counter
	 * @author khshaik
	 * @date Aug 16 2013
	 * @param dateToIncrement
	 * @param incrementer
	 * @return
	 */

	public static String dateIncremterInUSFormat(String dateToIncrement,int incrementer){
		String incrementedDate="";   
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");   
		Calendar cal = Calendar.getInstance();   
		try { 
			cal.setTime(df.parse(dateToIncrement)); 
		} catch (ParseException e)   
		{ 
			//log("Unable to increment the Date:"+dateToIncrement+"Due to Error-->"+e);
			logsObj.logError("Unable to increment the Date:"+dateToIncrement+" due to Error-->",e);
		}   

		cal.add(Calendar.DAY_OF_MONTH, incrementer); // number of days to add   
		incrementedDate = df.format(cal.getTime()); 
		return incrementedDate;
	}
	/**
	 * Increments the given date(Non US Format) by given increment counter
	 * @author Khaleel
	 * @param dateToIncrement
	 * @param incrementer
	 * @return
	 */
	public static String dateIncremterInNonUSFormat(String dateToIncrement,int incrementer){
		String incrementedDate="";   
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");   
		Calendar cal = Calendar.getInstance();   
		try { 
			cal.setTime(df.parse(dateToIncrement)); 
		} catch (ParseException e)   
		{ 
			//log("Unable to increment the Date:"+dateToIncrement+"Due to Error-->"+e);
			logsObj.logError("Unable to increment the Date:"+dateToIncrement+" due to Error-->",e);
		}   

		cal.add(Calendar.DAY_OF_MONTH, incrementer); // number of days to add   
		incrementedDate = df.format(cal.getTime()); 
		return incrementedDate;
	}


	/**
	 * Format into required date format of input date string	
	 * @param inputDateFormat
	 * @param expectedDateFormat
	 * @param inputDate
	 * @return formated date of input string for success and error message for failure
	 */
	public static String converttoRequiredDateFormat(String inputDateFormat,String expectedDateFormat, String inputDate){
		String flag=Constants_FRMWRK.True;

		try{
			SimpleDateFormat formatter = new SimpleDateFormat(inputDateFormat);
			Date date=(Date) formatter.parse(inputDate);
			//logInfo("DateUtil:Before conversion Date:-"+inputDate);
			logsObj.logInfo("DateUtil:Before conversion Date:-"+inputDate);

			SimpleDateFormat formatter2 = new SimpleDateFormat(expectedDateFormat);

			inputDate=formatter2.format(date);
			flag=inputDate;

		}catch (Throwable t){

			//log("Error-unable to convert to require date format-"+expectedDateFormat+"due to error->"+t);
			logsObj.logError("Error-unable to convert to require date format-"+expectedDateFormat+"due to error->", t);
			flag="Error-unable to convert to require date format-"+expectedDateFormat+"due to error->"+t;
		}


		return flag;

	}


	/**
	 * fetch the date difference between two date values in HH:MM:SS 
	 * @author SAHAMED
	 * @date Dec 16 2013
	 * @param strDate
	 * @param checkDate
	 * @return value in HH:MM:SS for success and error details for failure
	 */


	public static  String getTimeDifference(String strDate,String checkDate)

	{
		String flag=Constants_FRMWRK.False;
		try 
		{

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm:ss aa");
			Date date1=(Date) formatter.parse(strDate);
			Date date2=(Date) formatter.parse(checkDate);

			//in milliseconds
			long diff = date2.getTime() - date1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			//long diffDays = diff / (24 * 60 * 60 * 1000);
			flag=Long.toString(diffHours)+":"+Long.toString(diffMinutes)+":"+Long.toString(diffSeconds);
		}
		catch (Throwable t) 
		{
			//log(Constants_FRMWRK.Error+"Unable to convert the date difference of given value due to error-->"+t);
			logsObj.logError(Constants_FRMWRK.Error+"Unable to convert the date difference of given value due to error-->",t);
			flag=Constants_FRMWRK.Error+"Unable to convert the date difference of given value due to error-->"+t;
		}

		return flag;
	}


	/**
	 * Retrieves the current date
	 * @author khshaik
	 * @date Oct 15 2014
	 * @return
	 */
	public Date getDate(){		
		Date d=new Date();		
		return d;
	}

	/**
	 * returns a formated date in "MM/dd/yyyy hh:mm:ss a" format
	 * @author khshaik
	 * @date Oct 15 2014
	 * @param date
	 * @return
	 */
	public String getFormattedDate(Date date){
		DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		String formateddate=dateFormat.format(date).toString();
		return formateddate;
	}

	/**
	 * returns a formated date in "MM/dd/yyyy hh:mm:ss a" format
	 * @author khshaik
	 * @date Oct 15 2014
	 * @return
	 */
	public  String getFormattedDate(){
		Date date=getDate();
		DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		String formateddate=dateFormat.format(date).toString();
		return formateddate;
	}

	/**
	 * returns a date from a String
	 * @author khshaik
	 * @date Oct 16 2014
	 * @param date
	 * @return
	 */
	public Date getDateFromString(String date){
		Date dater=null;

		try{
			SimpleDateFormat df =new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
			dater = df.parse(date);
		}catch(Throwable t){

		}

		return dater;
	}

	/**
	 * calculates the duration between given two dates
	 * @author khshaik
	 * @date Oct 15 2014
	 * @param dat1
	 * @param dat2
	 * @return
	 */
	public String duration(Date dat1,Date dat2){

		/*DecimalFormat decFor=new DecimalFormat("00");		
		long diffDuration=dat2.getTime()-dat1.getTime();*/
		long diff = dat2.getTime() - dat1.getTime();

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		//long diffDays = diff / (24 * 60 * 60 * 1000);
		String duration=Long.toString(diffHours)+":"+Long.toString(diffMinutes)+":"+Long.toString(diffSeconds);

		/*long diffDays=TimeUnit.MILLISECONDS.toDays(diffDuration);
		long diffHours=TimeUnit.MILLISECONDS.toHours(diffDuration);
		long diffMins=TimeUnit.MILLISECONDS.toMinutes(diffDuration);
		long diddSecs=TimeUnit.MILLISECONDS.toSeconds(diffDuration);


		String duration=decFor.format(diffHours)+":"+decFor.format(diffMins)+":"+decFor.format(diddSecs);*/

		return duration;


	}

	public String duration(String dat1,String dat2){
		/*Date date1=getDateFromString(dat1);
		Date date2=getDateFromString(dat2);

		DecimalFormat decFor=new DecimalFormat("00");		
		long diffDuration=date2.getTime()-date1.getTime();

		//long diffDays=TimeUnit.MILLISECONDS.toDays(diffDuration);
		long diffHours=TimeUnit.MILLISECONDS.toHours(diffDuration);
		long diffMins=TimeUnit.MILLISECONDS.toMinutes(diffDuration);
		long diddSecs=TimeUnit.MILLISECONDS.toSeconds(diffDuration);

		String duration=decFor.format(diffHours)+":"+decFor.format(diffMins)+":"+decFor.format(diddSecs);*/
		String duration =getTimeDifference(dat1, dat2);
		return duration;


	}

	public static String dateIncremterInRequiredFormat(String dateFormat,String dateToIncrement,int incrementer){
		String incrementedDate="";   
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);   
		Calendar cal = Calendar.getInstance();   
		try { 
			cal.setTime(df.parse(dateToIncrement)); 
		} catch (ParseException e)   
		{ 
			//log("Unable to increment the Date:"+dateToIncrement+"Due to Error-->"+e);
			//logsObj.logError("Unable to increment the Date:"+dateToIncrement+" due to Error-->",e);
		}   

		cal.add(Calendar.DAY_OF_MONTH, incrementer); // number of days to add   
		incrementedDate = df.format(cal.getTime()); 
		return incrementedDate;
	}



}
