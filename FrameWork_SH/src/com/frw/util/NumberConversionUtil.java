package com.frw.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.frw.Constants.Constants_FRMWRK;
import com.frw.base.Base;

public class NumberConversionUtil extends Base{

	/**
	 * Method converts the given input into required decimals and if the input is related money not only converts into required decimals also also delimits with , after it across hundreds;
	 *   user numberofDecimals as SINGLEDECIMAL for single decimal out put,
	 * numberofDecimals=SINGLEDECIMAL -ouput date format=#.0,
	 * numberofDecimals=DOUBLEDECIMAL -ouput date format=#.00,
	 * numberofDecimals=MONEY -ouput date format= ###,##.00 ,
	 * @param numberofDecimals
	 * @param input
	 * @return
	 */
	
public static String convertToDecimals(final String numberofDecimals,final String input){
		
		String flag=Constants_FRMWRK.False;
		
		
		try{
			
			//double d=Double.parseDouble(input); -- commented on Nov 26 and included below line
			BigDecimal d=new BigDecimal(input);
			
			if(numberofDecimals.equalsIgnoreCase("SINGLEDECIMAL")){
			
				flag= new DecimalFormat("#.0").format(d);
				
				
			}else if(numberofDecimals.equalsIgnoreCase("MONEY")){
				
				flag= new DecimalFormat("#,###.00").format(d);
				
			}else if(numberofDecimals.equalsIgnoreCase("DOUBLEDECIMAL")){
				
				flag= new DecimalFormat("#.00").format(d);
			}
			else if(numberofDecimals.equalsIgnoreCase("NEARESTROUND")){
				
				flag= new DecimalFormat("#.##").format(d);
			}
			else {				
				
			}
			
			
		}catch(Throwable t){
			logsObj.logError("Unable to convert the input value:-"+input+" in the require decimals"+numberofDecimals+" due to error->",t);
		}	
		
		return flag;
	}

/**
 * Converts the given three input values into the maximum precision among three(maximum decimals supported is two decimal precision)
 * @author sahamed
 * @Date Nov 27 2013
 * @param db
 * @param appVal
 * @param dispVal
 * @return
 * Three input values with maximum precision
 */

public static String convertToSameDecimalPrecision(String appVal,String dbVal,String dispVal){
	BigDecimal dbBig=new BigDecimal(dbVal);
	BigDecimal appBig=new BigDecimal(appVal);
	BigDecimal dispBig=new BigDecimal(dispVal);
	
	
	
	int scale,dbscale,appscale,dispscale ;
	@SuppressWarnings("unused")
	int dbprecision,appprecision,dispprecision;
	BigDecimal big,big2,big3;
	//BigDecimal noZero = b.stripTrailingZeros();
	
	// ***************   DB **********************************
	BigDecimal bigVal = dbBig;
	dbscale = bigVal.scale();
	dbprecision = bigVal.precision();    
	
	if (dbscale < 0) { // Adjust for negative scale
	    dbprecision -= dbscale;
	    dbscale = 0;        
	}
	logsObj.logInfo("DB scale-"+dbscale);
	
	// ***************   Excel **********************************
	
	BigDecimal bigValapp = appBig;
	appscale = bigValapp.scale();
	appprecision = bigValapp.precision();    
	
	if (appscale < 0) { // Adjust for negative scale
	    appprecision -= appscale;
	    appscale = 0;        
	}
	
	logsObj.logInfo("App scale-"+appscale);
	
	
	// ***************   Display **********************************
	BigDecimal bigValdisp = dispBig;
	dispscale = bigValdisp.scale();
	dispprecision = bigValdisp.precision();    
	
	if (dispscale < 0) { // Adjust for negative scale
	    dispprecision -= dispscale;
	    dispscale = 0;        
	}
	
	logsObj.logInfo("Display scale-"+dispscale);
	
	int x=appscale;
	int y=dbscale;
	int z=dispscale;
	
	if ( x > y && x > z ){
         	logsObj.log("First input value is largest.");
			scale=appscale;
			big=new BigDecimal(appVal);
			big2=dbBig;
			big3=dispBig;
			
	}		
      else if ( y>z ){
    	  	logsObj.log("Second input value is largest.");
			scale=dbscale;
			big=appBig;
			big2=new BigDecimal(dbVal);
			big3=dispBig;						
      }
      else {
         logsObj.log("Third input value is largest.");
         scale=dispscale;
		 big=appBig;
		 big2=dbBig;
		 big3=new BigDecimal(dispVal);
      }
     

	logsObj.log("The largest input value Scale is :-"+scale);
	String flag,flag2,flag3;
		if(scale==2){
			flag= new DecimalFormat("#.00").format(big);
			flag2= new DecimalFormat("#.00").format(big2);
			flag3= new DecimalFormat("#.00").format(big3);
			
		}else if (scale==1){
			flag= new DecimalFormat("#.0").format(big);
			flag2= new DecimalFormat("#.0").format(big2);
			flag3= new DecimalFormat("#.0").format(big3);
		}else{
			//flag=big;
			flag=appVal;
			flag2=dbVal;
			flag3=dispVal;
		}
		
		return flag+"-"+flag2+"-"+flag3;
		
	
}
/**
 * checks the number of decimal points available in the string
 * @author SAHAMED
 * @date Feb 04 2014
 * @param input
 * @return number of decimal points available in the string
 */
public static int retrieveStringDecimalCount(String input){
	int scale ;
	BigDecimal bigVal=new BigDecimal(input);	
	scale = bigVal.scale();	
	return scale;
	
}

}
