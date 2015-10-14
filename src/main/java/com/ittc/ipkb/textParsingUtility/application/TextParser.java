package com.ittc.ipkb.textParsingUtility.application;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.ittc.ipkb.textParsingUtility.util.FileUtility;
import com.ittc.ipkb.textParsingUtility.util.StringUtility;
import com.ittc.ipkb.textParsingUtility.beans.TaxonomyBean;


/***
 * 
 * @author rsompalli
 * Starting class for the text parsing application. 
 *
 */
public class TextParser 
{
	/**
	 * Main method. Starting point of the application. Takes file name as the input from the user.
	 * 
	 * Design is as follows: 
	 * 1. Take a text file as input.(provide the complete path of the file when prompted)
	 * 2. Convert the entire text of the file as a single string seperated by spaces in order to avoid parising line by line.
	 * 3. Split the string into array of strings.
	 * 4. Call the method parseRemainingString and pass the array of strings as input.
	 * 5. This method will parse the string word by word and extracts the taxonomy information and genera information accordingly.
	 * 6. The design for parsing and extracting information is explained at corresponding methods.
	 * 7. After parisng the entire text, the results are populated to StringUtility.taxonomyDetailsMap and StringUtility.GeneraList.
	 * 8. Write these results to output XML files.
	 * 
	 */
	
	public static void main(String[] args) 
	{
		//read the input file name.
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the file name to be parsed:::");
		String fileName = scanner.next();
		
		try 
		{
			//read the entire content of file as a single string to make it easier to parse
			String textContentAsString =  FileUtility.getWholeTextFileAsAString(fileName);
			
			//split the strings based on white space
			String[] textContentStringArray = textContentAsString.split("\\s+");
			
			//parse the string word by word to find the regular patterns identified and read the content in structured format.
			StringUtility.parseRemainingString(textContentStringArray);
			
			//Write the final results to XML files.
			Map<String,List<TaxonomyBean>> finalTaxonomyInformation = StringUtility.taxonomyDetailsMap;
			FileUtility.writeTaxonomyInformationToXML(finalTaxonomyInformation);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			scanner.close();
		}
	}

}
