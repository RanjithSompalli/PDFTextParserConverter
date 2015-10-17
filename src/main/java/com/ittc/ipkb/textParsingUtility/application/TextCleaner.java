/**
 * 
 */
package com.ittc.ipkb.textParsingUtility.application;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.ittc.ipkb.textParsingUtility.util.FileUtilityEx;

/**
 * @author yangtian
 * @description Recognize and remove page headers, sub labels in images 
 * and fig titles from the text.
 *
 */
public class TextCleaner {
	
	private static final Pattern PageHeadingParttern1 = Pattern.compile("([A-Z]{0,1}\\d{1,3})([ \t]*)([a-zA-Z—]+)");
	private static final Pattern PageHeadingParttern2 = Pattern.compile("([a-zA-Z—]+)([ \t]*)([A-Z]{0,1}\\d{1,3})");
	private static final Pattern SubLabelsParttern = Pattern.compile("(\\d*[a-zA-Z]*[ \t]*){1,3}");
	private static final Pattern FigTitleParttern = Pattern.compile("^(FIG)\\.[ \t]*\\d{1,3}\\..*$");
	
	private int[] markFlags;
	private String infilePath;
	private String outfilePath;
	
	private String[] arrayofLines;
	private int lineNumber;
	
	public TextCleaner(String inpath,String outpath){
		this.infilePath = inpath;
		this.outfilePath = outpath;
	}
	
	private void markHeadandPageNo(){
		
		//System.out.println("how many lines: "+lineNumber);
		System.out.println("Mark Page Headings and Numbers...");
		for(int i = 0;i < lineNumber;i++){
			if(PageHeadingParttern1.matcher(arrayofLines[i]).matches()
			   ||PageHeadingParttern2.matcher(arrayofLines[i]).matches()){
				System.out.println(arrayofLines[i]);
				markFlags[i] = 1;
			}
		}
	}
	
	private void markFigTitles(){
		boolean FigStart = false;
		System.out.println("Mark Fig Titles...");
		for(int i = 0;i < lineNumber;i++){
			if(FigStart == false){
				if(FigTitleParttern.matcher(arrayofLines[i]).matches()){
					System.out.println(arrayofLines[i]);
					markFlags[i] = 1;
					FigStart = true;
				}
			}
			else{
				
				if(arrayofLines[i-1].endsWith(").")){
					FigStart = false;
				}
				else{
					System.out.println(arrayofLines[i]);
					markFlags[i] = 1;
				}
			}
			
		}
	}
	
	private void markSublabels(){
		System.out.println("Mark Sub Labels...");
		for(int i = 0;i < lineNumber;i++){
			if(arrayofLines[i].startsWith("Phylum")
			 ||arrayofLines[i].startsWith("Class")
			 ||arrayofLines[i].startsWith("Order")
			 ||arrayofLines[i].startsWith("Family")
			 ||arrayofLines[i].startsWith("Subfamily")
			 ||arrayofLines[i].startsWith("Suborder")
					){
				
			}
			else{
				if(SubLabelsParttern.matcher(arrayofLines[i]).matches()){
					System.out.println(arrayofLines[i]);
					markFlags[i] = 1;
				}
			}
		}
	}
	
	/**
	 * This method removes all unnecessary items in the text file to make text parsing 
	 * much easier in the next step.
	 */
	public void removeDumpyItems(){
		try {
			arrayofLines = FileUtilityEx.getWholeTextFileAsAString(infilePath);
			lineNumber = arrayofLines.length;
			markFlags = new int[lineNumber];
			markHeadandPageNo();
			markSublabels();
			markFigTitles();
			for(int i = 0;i < lineNumber;i++){
				if(markFlags[i] == 1){
					arrayofLines[i] = "";
				}
			}
			FileUtilityEx.writetoFileLinebyLine(outfilePath, arrayofLines, lineNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args){
		
		//read the input file name.
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the txt file name to be cleaned:");
		String fileName = scanner.next();
		System.out.println("Enter the txt output file name:");
    	String outFilePath = scanner.next();
    	
    	TextCleaner myTextCleaner = new TextCleaner(fileName,outFilePath);
    	
    	myTextCleaner.removeDumpyItems();
    	System.out.println("Done.");
	}
	
	
}
