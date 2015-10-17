/**
 * 
 */
package com.ittc.ipkb.textParsingUtility.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author yangtian
 * @Description  Utility class to perform file reading in other way.
 *
 */
public class FileUtilityEx {
	/**
	 * Read the input text file line by line and store them as an array of strings.
	 * @param input text file path
	 * @return String[] : the content of the file
	 * @throws IOException
	 */
	public static String[] getWholeTextFileAsAString(String fileName) throws IOException{
		//Read the content of the file
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		String content = "";
		//FileWriter fileWriter = null;
		StringBuilder stringBuilder = new StringBuilder("");
		try 
		{
			fstream = new FileInputStream(fileName);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));

			//read line by line and build a single string
			String textLine;
			while((textLine = br.readLine())!=null)
			{
				stringBuilder.append(textLine);
				stringBuilder.append("\n");
			}
			content = stringBuilder.toString();
			
            //File newTextFile = new File("H:\\workspace\\pdfFiles\\test.txt");
            //fileWriter = new FileWriter(newTextFile);
            //fileWriter.write(content);
            //fileWriter.close();
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(br!=null)
					br.close();
				if(in!=null)
					in.close();
				if(fstream!=null)
					fstream.close();
				//if(fileWriter!=null)
				//	fileWriter.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return stringBuilder.toString().split("\n");
	}
	
	public static void writetoFileLinebyLine (String filename, String[] lines,int len) throws IOException{
		  BufferedWriter outputWriter = null;
		  outputWriter = new BufferedWriter(new FileWriter(filename));
		  for (int i = 0; i < len; i++) {
			  if(!lines[i].equals("")){
				  outputWriter.write(lines[i]);
				  outputWriter.newLine();
			  }		    
		  }
		  outputWriter.flush();  
		  outputWriter.close();  
	}

	
}
