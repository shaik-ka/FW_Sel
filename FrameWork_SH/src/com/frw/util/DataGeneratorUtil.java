package com.frw.util;



import org.apache.commons.lang3.RandomStringUtils;

public class DataGeneratorUtil {
	
	/**
	 * Get the required length String using characters/numbers
	 * @author khshaik
	 * @date Apr 02 2015
	 * @param length
	 * @param useChars
	 * @param useNumbers
	 * @return
	 */
	
	public static String getRandomString(int length,boolean useChars,boolean useNumbers ){
		 String generatedString = RandomStringUtils.random(length, useChars, useNumbers);
		 return generatedString;
	}
	
	
	

}
