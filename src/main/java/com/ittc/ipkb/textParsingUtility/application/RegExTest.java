package com.ittc.ipkb.textParsingUtility.application;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class RegExTest {

	public static void main(String[] args) 
	{
		//Pattern for checking taxonomy name : Should contain 1 or more capital letters and no other character.
		/*Pattern capitalLettersPattern = Pattern.compile("[A-Z]+"); //Success
		System.out.println(capitalLettersPattern.matcher("PRESCORA").matches());
		
		
		//Pattern for checking the genus name : Should start with a capital letter and should contain one or more small letters only.
		Pattern capitalLetterFollowedBySmallLetter =Pattern.compile("[A-Z][a-z]+"); //Success
		System.out.println(capitalLetterFollowedBySmallLetter.matcher("Xsdd").matches());
		
		//Pattern for checking year number while looking for genus pattern : Should start with four digits (a year number) 
		//and can have 0 or 1 alphabets and end with ,
		Pattern fourDigitNumberFollowedByalphabetnComma = Pattern.compile("[0-9]{4}[a-z]?,$"); //Success
		if(fourDigitNumberFollowedByalphabetnComma.matcher("1974b,").matches())
			System.out.println(true);
		else
			System.out.println(false);*/
		
		//Pattern for checking author name : Can start with 0 or 1 special characters eg : ( 
		//followed by All capital letters and may end with 0 or more special characters 
		Pattern allCapitalLettersFollowedByDotOrComma = Pattern.compile("[\\W]?[A-Z]+[\\W]*"); //Success
		System.out.println(allCapitalLettersFollowedByDotOrComma.matcher("MÄDLER,").matches());
		
		
		final String input = "(Soulié-Märsche, 1989, pl. XLIII,2,4,7).——FIG. 78,1f.";
		System.out.println( Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
		
		
	}
}
