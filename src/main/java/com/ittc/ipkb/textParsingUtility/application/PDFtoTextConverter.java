package com.ittc.ipkb.textParsingUtility.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;


/**
 * Class to convert the given PDF file into text file.
 *
 */
public class PDFtoTextConverter 
{
	private PDFParser parser = null;
	
	public PDFtoTextConverter(String fileName)
	{
		File file = new File(fileName);
		if(!file.isFile())
			System.err.println("File "+fileName+" not found.");
		try
		{
			parser = new PDFParser(new FileInputStream(file));
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
    public static void main( String[] args )
    {
    	System.out.println("Enter the PDF file path:");
    	Scanner scanner = new Scanner(System.in);
    	String filePath = scanner.next();
    	System.out.println("Enter starting page number:");
    	int startPage = scanner.nextInt();
    	System.out.println("Enter ending page number:");
    	int endPage = scanner.nextInt();
    	System.out.println("ENter output file name:");
    	String outFilePath = scanner.next();
        PDFtoTextConverter converter = new PDFtoTextConverter(filePath);
        String pdfContentInTextFormat = converter.getParsedText(startPage,endPage);
        converter.writeOutputToFile(pdfContentInTextFormat,outFilePath);
        scanner.close();
    }

	private String getParsedText(int startPage, int endPage) 
	{
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		String parsedText = null;
		
		try
		{
			PDFTextStripper stripper = new PDFTextStripper();
			parser.parse();
			cosDoc = parser.getDocument();
			pdDoc = new PDDocument(cosDoc);		    
			stripper.setStartPage(startPage);
			stripper.setEndPage(endPage);
			parsedText = stripper.getText(pdDoc);
		}
		catch(IOException e)
		{
			System.err.println("Error occured while parsing PDF file::"+e.getMessage());
		}
		finally
		{
			try
			{
				if(cosDoc!=null)
					cosDoc.close(); 
				if(pdDoc!=null)
					pdDoc.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return parsedText;
	}
	
	
	private void writeOutputToFile(String pdfContentInTextFormat, String outputFilePath) 
	{
		Writer writer = null;
		try 
		{
			System.out.println("OutputFIlePath:::"+outputFilePath);
			File file = new File(outputFilePath);
			if(!file.exists())
				file.createNewFile();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			writer.write(pdfContentInTextFormat);
			
			System.out.println("Successfully Copied to output...");
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();

		} 
		finally 
		{
			try {if(writer!=null)writer.close();} catch (IOException e) {e.printStackTrace();}
		}	
	}
}
