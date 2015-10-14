package com.ittc.ipkb.textParsingUtility.application;

public class SampleTestClass {

	public static void main(String[] args) 
	{
		String str = "Austra-lia.——FIG.";
		System.out.println(str.contains("——"));
		if(str.contains("——"))
		{
			String[] splittedString = str.split("——");
			for(String string : splittedString)
				System.out.println(string);
		}
	}
}
