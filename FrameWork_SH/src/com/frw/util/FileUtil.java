package com.frw.util;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	private static FileUtil fileUtilObj;


	private FileUtil(){}
	/**
	 * fetches the object reference of FileUtil class
	 * @author khshaik
	 * @date Oct 16 2014
	 * @return
	 */
	public static FileUtil getFileUtilObject(){

		if(fileUtilObj==null){
			fileUtilObj=new FileUtil();
		}

		return fileUtilObj;
	}


	private static FileWriter txtwriter;
	/**
	 * Creates an text file in the specified location
	 * @author sahamed
	 * @Date Jun 13 2014
	 * @param filePath
	 */
	public static void createTextFile(String filePath){


		try {

			File txtfile =new File(filePath);
			/*if(txtfile.exists()){
				txtfile.delete();
			}*/
			txtwriter=new FileWriter(txtfile);		

		} catch (IOException e) {
			System.out.println("Unable to create a text file-"+filePath);
		}
	}

	/**
	 * Writes data into the text file
	 * @author sahamed
	 * @Date Jun 13 2014
	 * @param data
	 * @throws IOException
	 */

	public static void writeTextFile(String data) throws IOException{
		//System.out.println("TxtWriter Data-"+data);
		txtwriter.write(data+",");
		//System.out.println("TxtWriter Done..");
	}

	/**
	 * Closes the text file
	 * @author sahamed
	 * @Date Jun 13 2014
	 * @throws IOException
	 */
	public static void closeTextFile() throws IOException{
		txtwriter.close();
	}

	/**
	 * Searches a specific type of file under a directory
	 * @author sahamed
	 * @Date Jun 15 2014
	 * @param directory
	 * @param searchFileName
	 * @param extFileName
	 * @return full path of file searched for success and false for search fail
	 */
	public static String getfilePathUnderSearch(String directory,final String searchFileName,final String extFileName){
		String flag="false";
		File f = new File(directory);
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(searchFileName) && name.endsWith(extFileName);
			}
		});

		if(matchingFiles.length==0){

		}else{
			flag=matchingFiles[0].getAbsolutePath();
			flag=flag.replace("/", "//");
		}
		return flag;
	}
	/**
	 * Deletes the files available in the specified folder
	 * @author khshaik
	 * @date Jan 17 2014
	 * @param filePath
	 */

	public static void deleteFilesinFolder(String directory){
		try {
			File f = new File(directory);
			if(f.exists()){
				FileUtils.cleanDirectory(f);
			}

		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	/**
	 * Copies the files available in source folder to destnation folder
	 * @author khshaik
	 * @date Oct 16 2014 
	 * @param src
	 * @param dst
	 */
	public static void copyFolders(String src,String dst){


		try {
			File srcFile=new File(src);		
			File dstFile=new File(dst);
			//if(!dstFile.exists()){
			FileUtils.copyDirectory(srcFile, dstFile);
			//}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	/**
	 * Copy the file from source file to destination file
	 * @author khshaik
	 * @date Mar 03 2015
	 * @param sourceFile
	 * @param destFile
	 */
	public static String copyFile(String sourceFile,String destFile){
		String flag="False";
		try{

			File source= new File(sourceFile);
			File dest=new File(destFile);
			FileUtils.copyFile(source, dest);
			flag="True";
		}catch (Exception ex){
			//System.out.println("Unable to copy the sourceFile-"+sourceFile+" to destination "+destFile+" due to error-"+ex);
		}

		return flag;
	}
	/**
	 * Deletes the file available in the specified location
	 * @author khshaik
	 * @date Oct 17 2014
	 * @param filePath
	 */

	public void DeletefileExists(String filePath){
		try{
			File file=new File(filePath);

			if(file.exists()){
				file.delete();
				System.out.println("File exists and deleted..");
			}else{
				System.out.println("File doesnot exists..");
			}
		}catch(Throwable t){

		}	

	}
	/**
	 * creates a folder in a specified path
	 * @author Khaleel
	 * @date Apr 7 2016
	 * @param folderPath
	 */
	public static void createFolder(String folderPath){
		File file = new File(folderPath);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory "+folderPath+" is created!");
			} else {
				System.out.println("Failed to create directory! "+folderPath);
			}
		}else{
			System.out.println("Directory "+folderPath+" is already exists!");
		}
	}
	/**
	 * creates a folder structure in a specified path
	 * @author Khaleel
	 * @date Apr 7 2016
	 * @param folderPath
	 */
	public static void createFolderStructure(String folderPath){
		File file = new File(folderPath);
		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Directory "+folderPath+" is created!");
			} else {
				System.out.println("Failed to create directory! "+folderPath);
			}
		}else{
			System.out.println("Directory "+folderPath+" is already exists!");
		}
	}

}
