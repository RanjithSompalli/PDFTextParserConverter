package com.ittc.ipkb.textParsingUtility.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.ittc.ipkb.textParsingUtility.beans.GeneraBean;
import com.ittc.ipkb.textParsingUtility.beans.TaxonomyBean;

/**
 * 
 * @author rsompalli
 * Utility Class to perform all the string parsing functionalities
 *
 */
public class StringUtility
{
	//List of taxonomy names.
	private static final String[] TAXONOMY_NAMES = {"Phylum","Subphylum","Class","Subclass","Order","Suborder","Superfamily","Family","Subfamily"};
	private static List<String> taxanomyNames = Arrays.asList(TAXONOMY_NAMES);
	
	//List of all time periods.
	private static final String[] TIME_PERIODS = 
		{
			"Aalenian","Actonian","Aeronian","Aksayan","Albian","Alexandrian","Amgaian","Amgan","Anisian","Aptian","Aquitanian","Arenig","Artinskian","Ashgill","Asselian","Atdabanian","Atlantic",	"Ayusokkanian",
			"Bajocian","Barremian","Bartonian","Bashkirian","Bathonian","Batyrbayan","Berriasian","Black River","Blancan","Boomerangian","Boreal","Botomian","Burdigalian",
			"Calabrian","Callovian","Cambrian", "Campanian","Capitanian","Caradoc","Carboniferous","Carnian","Cassinian","Castleman","Cautleyan","Cayugan","Cenomanian","Cenozoic","Changhsingian","Changlangpuan","Changshanian","Chattian","Chazy","chrons","Cisuralian","Coniacian","Costonian","Cretaceous",
			"Danian","Dapingian","Darriwilian","Datsonian","Delamaran","Deming","Devonian","Dolgellian","Dyeran","Eden","Eemian","Eifelian","Emsian","Eocene",
			"Famennian","Fengshanian","Ffestiniogian","Floian","Florian","Franconian","Frasnian","Furongian","Gaconadian","Gamach","Gelasian","Givetian","Gorstian","Guadalupian","Gzhelian","Harnagian","Hauterivian","Hettangian","Hirnantian","Holocene","Homerian",
			"Ibexian","Idamean","Induan","Ionian","Iverian","Jefferson","Jurassic","Kasimovian","Kazanian","Kimmeridgian","Kirkfield","Kungurian",
			"Ladinian","Langhian","Lenian","Llandeilo","Llandovery","Llanvirn","Lochkovian","Lockportian","Longvillian","Longwangmioan","Lopingian","Ludfordian","Ludlow","Lutetian",
			"Maastrichtian","Maentwrogian","Maozhangian","Marjuman","Marshbrookian","Mayaian","Mayan","Maysville","Meishuchuan","Mesozoic","Messinian","Mindyallan","Miocene","Mississippian","Montezuman","Moscovian",
			"Neogene","Norian","Olenekian","Oligocene","Onnian","Ontarian","Ordian","Ordovician","Oxfordian",
			"Paibian","Paleocene","Paleogene","Paleozoic","Payntonian","Pennsylvanian","Permian","Phanerozoic","Piacenzian","Pleistocene","Pliensbachian","Pliocene","Pragian","Preboreal","Priabonian","Pridoli","Pusgillian",
			"Quaternary","Qungzusian","Rawtheyan","Rhaetian","Rhuddanian","Richmond","Roadian","Rockland","Rupelian",
			"Sakian","Sakmarian","Sangamonian","Santonian","Selandian","Serpukhovian","Serravallian","Sheinwoodian","Sherman","Silurian","Sinemurian","Soundleyan","Stage","Steptoan","Subatlantic","Subboreal","Sunwaptan",
			"Tarantian","Telychian","Templetonian","Thanetian","Tithonian","Toarcian","Tommotian","Tonawandan","Tortonian","Tournaisian","Toyonian","Tremadoc","Trempealeauan","Trenton","Triassic","Turonian","Tyrrhenian",
			"Ufimian","Undillian","Valanginian","Vis¨¦an","Waucoban","Wenlock","Whiterock","Wordian","Wuchiapingian","Ypresian","Zanclean","Zhungxian","Zuzhuangian","Rec."
	};
	private static List<String> timePeriods = Arrays.asList(TIME_PERIODS);
	
	//Reserved pronouns for time periods (eg: upper, lower, middle, early etc.,)
	/*private static final String[] TIME_PERIOD_ADD_ON_WORDS= {"Early","Late","Lower","Middle","Upper"};
	private static List<String> timePeriodAddOnWords = Arrays.asList(TIME_PERIOD_ADD_ON_WORDS);*/
	
	
	///Pattern for checking taxonomy name : Should contain 1 or more capital letters and no other character. (Eg: CHOVANELLACEAE)
	private static Pattern allCapitalLettersPattern = Pattern.compile("[A-Z]+");
	//Pattern for checking the genus name : Should start with a capital letter and should contain one or more small letters only. (Eg: Ampullichara)
	private static Pattern capitalLetterFollowedBySmallLetter =Pattern.compile("[A-Z][a-z]+");
	//Pattern for checking author name : Can start with 0 or 1 special characters
	//followed by All capital letters and may end with 0 or more special characters (Eg: CROFT or (CROFT) or C. or CROFT, )
	private static Pattern allCapitalLettersFollowedByDotOrComma = Pattern.compile("[\\W]?[A-Z]+[\\W]*");
	//Pattern for checking year number while looking for genus pattern : Should start with four digits (a year number) 
	//and can have 0 or 1 alphabets and end with , (Eg: 1974, 1974b, etc.,)
	private static Pattern fourDigitNumber =Pattern.compile("[0-9]{4}[a-z]?,+$");
	
	//Final results are populated into these data structures
	public static Map<String,List<TaxonomyBean>> taxonomyDetailsMap = new HashMap<String,List<TaxonomyBean>>();
	public static List<GeneraBean> generaList = new ArrayList<GeneraBean>();
	
	
	/**
	 * creates a default map that has all the taxonomy names as the keywords and an empty list of TaxonomyBeans which will 
	 * later have the populated values.
	 */
	static
	{
		for(String taxonomy : taxanomyNames)
		{
			taxonomyDetailsMap.put(taxonomy, new ArrayList<TaxonomyBean>());
		}
	}
	
	//Default Taxonomy Names for a Genera.Will be updated while parsing the text
	private static String currentPhylumName = "UNKNOWN";
	private static String currentSubPhylumName = "UNKNOWN";
	private static String currentClassName = "UNKNOWN";
	private static String currentSubclassName = "UNKNOWN";
	private static String currentOrderName = "UNKNOWN";
	private static String currentSuborderName = "UNKNOWN";
	private static String currentSuperfamilyName = "UNKNOWN";
	private static String currentFamilyName = "UNKNOWN";
	private static String currentSubfamilyName = "UNKNOWN";
	
	
	/**
	 * This method is the initial method that is called with the whole text or called ecery time for the remaining string
	 * after obtaining certain useful info
	 * 
	 * This method looks for next occurring pattern that is whether genus comes first or taxonomy comes first in the remaining text
	 * and calls appropriate method to get the relative info. 
	 * Used to extract both taxonomy and genus information.
	 * 
	 * @param textString - the array of strings that has the remaining words that are yet to be parsed.
	 */
	public static void parseRemainingString(String[] remainingString)
	{
		//In the remaining string check if genus occurs first or taxonomy occurs first.
	    //If genus comes first call parseRemainingStringForGeneraInfo to extract genus info
		//else call parseRemainingStringForTaxonomyInfo to extract taxonomy info
		int nextTaxonomyStartingPosition = getStartingIndexofTaxonomyInTheRemainingText(remainingString);
	    int nextGeneraStartingPosition = getStartingIndexOfGeneraInTheRemainingText(remainingString);
	    
	    int nextPositionToParse = determineWhetherGeneraOrTaxonomyComesNext(nextGeneraStartingPosition,nextTaxonomyStartingPosition);
	    if(nextPositionToParse!=-1)
	    {
	    	if(nextPositionToParse==nextGeneraStartingPosition)
	    		parseRemainingStringForGeneraInfo(remainingString);
	    	else if(nextPositionToParse==nextTaxonomyStartingPosition)
	    		parseRemainingStringForTaxonomyInfo(remainingString);
	    }
	}
	
	/**
	 * returns the index position in the string array where a taxonomy description starts
	 * @param splittedWords
	 * @return integer : index of the taxonomy details starting position, -1 if the taxonomy is not found in the input string
	 */
	private static int getStartingIndexofTaxonomyInTheRemainingText(String[] splittedWords) 
	{
		for(int wordCount=0;wordCount<splittedWords.length;wordCount++)
		{
			//if the word is a taxonomy word and is followed by a String of all capital letters then it is a taxonomy. Eg: Class CHAROPHYCEAE
			if(taxanomyNames.contains(splittedWords[wordCount]) && allCapitalLettersPattern.matcher(splittedWords[wordCount+1]).matches())
			{
				return wordCount;
			}
		}
		return -1;
	}
	
	/**
	 * This method checks the regular patterns identified for the genera information.
	 * Steps include check for a String that starts with a capital letter followed by a set of samll letters
	 * and then check for a string that have all caiptal letters and may end with . or , and then find for the year number among the near by strings and after year number followed by p.
	 * @param splittedString
	 * @return the position in the string array from where the genera info starts, return -1 if no taxonomies are found.
	 */
	private static int getStartingIndexOfGeneraInTheRemainingText(String[] splittedString)
	{
		int wordIndex = 0;
		
		while(wordIndex<splittedString.length)
		{
			//Look for this pattern as example: Moellerina ULRICH, 1886, p.
			//If a word has a capital letter followed by all small letters, it can be a genus name based on next words.
			if(capitalLetterFollowedBySmallLetter.matcher(splittedString[wordIndex]).matches() && !taxanomyNames.contains(splittedString[wordIndex]))
			{
				//And if the next word is a word with all the capital letters and ended by a , or . eg: ULRICH, or YANG & ZHOU,
				int tempWordIndex = wordIndex+1; 
				while(allCapitalLettersFollowedByDotOrComma.matcher(splittedString[tempWordIndex]).matches() || splittedString[tempWordIndex].equals("&"))
				{
					tempWordIndex++;
				}
				//And the followed by a year number and followed by a string "p." eg: 1886, p.
				if(fourDigitNumber.matcher(splittedString[tempWordIndex]).matches() && splittedString[tempWordIndex+1].equals("p."))
				{
					return wordIndex;
				}
				
			}
			wordIndex++;		
		}
		
		return -1;
	}
	
	
	
	/**
	 * This method first parses the input array of strings for taxonomy information and after finding the 
	 * Family or Sub Family, it then looks for the genera information until next higher taxa is found and then again 
	 * repeats the same process when Higher taxa are found.
	 * 
	 * Used to extract both taxonomy and genera information.
	 * 
	 * @param textString - the array of strings that has the remaining words that are yet to be parsed.
	 */
	public static void parseRemainingStringForTaxonomyInfo(String[] splittedWords)
	{
		
		//get the index position the string array that has a taxonomy word.
		int taxonomyStartingPosition = getStartingIndexofTaxonomyInTheRemainingText(splittedWords);
		
		//If the starting position is -1, no more taxanomies are found in the remaining text.
		if(taxonomyStartingPosition!=-1)
		{
			//Ignore the text before the taxonomy information and build an array that starts with taxonomy info till the end of the remaing text
			String[] remainingString = new String[splittedWords.length-taxonomyStartingPosition];
			for(int i=taxonomyStartingPosition,j=0;i<splittedWords.length;i++,j++)
				remainingString[j]=splittedWords[i];
			extractTaxonomyInfo(remainingString);
		}
	}


	
	
	/***
	 * This method parses the given array of strings and extracts the genera information.
	 * @param splittedString
	 */
	private static void parseRemainingStringForGeneraInfo(String[] splittedString)
	{
		int generaStartingPosition = getStartingIndexOfGeneraInTheRemainingText(splittedString);
		
		if(generaStartingPosition!=-1)
		{
			String[] remainingString = new String[splittedString.length-generaStartingPosition];
			for(int i=generaStartingPosition,j=0;i<splittedString.length;i++,j++)
				remainingString[j]=splittedString[i];
			extractGeneraInfo(remainingString);
		}
	}
	

	/**
	 * Contains logic to extract the taxonomy information.
	 * Parses the given array of Strings word by word looking for identiifed patterns and then builds the TaxonomyBean and appends it to output data structure.
	 * After one Taxonomy information, removes the content related to that txonomy and calls the parseRemainingString method back to parse the remaning string.
	 * @param string -- Array of Strings that starts with the taxonomy information.
	 */
	private static void extractTaxonomyInfo(String[] splittedString) 
	{
		TaxonomyBean taxonomy = new TaxonomyBean();
		
		//update the current values of higher taxonomy when a new level of taxonomy is found
		updateCurrentTaxonomyName(splittedString[0],splittedString[1] );
		
		//have to set the type, name, author info, hyper link info, description, time period, figure index by parsing the string
		//set the name and type of the taxonomy
		taxonomy.setTaxonomyType(splittedString[0]);
		taxonomy.setTaxonomyName(splittedString[1]);
		
		//extract the author info. the strings that are listed until "[" char is found is the author info 
		//eg: Smith, 1938 [Charophyceae SMITH, 1938, p. 127] [=Charophycophyta PAPENFUSS, 1946, p. 218] - Author info is  Smith, 1938 
		StringBuilder authorInfo = new StringBuilder("");
		int wordCount=2;
		while(!splittedString[wordCount].startsWith("["))
		{
			authorInfo.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		taxonomy.setAuthorInfo(authorInfo.toString());
		
		//extract the hyper link info
		StringBuilder hyperLinkInfo = new StringBuilder("");
		while(!splittedString[wordCount].endsWith("]") || splittedString[wordCount+1].startsWith("["))
		{
			hyperLinkInfo.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		hyperLinkInfo.append(splittedString[wordCount]+" ");
		taxonomy.setReferenceInfo(hyperLinkInfo.toString());
		wordCount++;
		
		//extract the description and time period
		StringBuilder description = new StringBuilder("");
		StringBuilder timePeriod = new StringBuilder("");
		while(!checkIfWordContainsTimePeriod(splittedString[wordCount]))
		{
			description.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		taxonomy.setDescription(description.toString());
		
		//extract the time period info
		//TODO: this section needs to be modified to give appropriate pattern to end text.
		while(!splittedString[wordCount].endsWith("."))
		{
			timePeriod.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		timePeriod.append(splittedString[wordCount]);
		taxonomy.setTimePeriod(timePeriod.toString());
		wordCount++;
		
		//Write the taxonomy details extracted to the corresponding XML file
		FileUtility.writeTaxonomyInformationToXML(taxonomy);
		
		//build the remaining string
		String[] remainingString = new String[splittedString.length-wordCount];
		for(int i=wordCount,j=0;i<splittedString.length;i++,j++)
			remainingString[j] = splittedString[i];
		
		//after extracting one taxonomy info call parseRemainingString to parse the remaining text
		parseRemainingString(remainingString);
	    
	}
	
	
	
	/**
	 * Contains logic to extract the Genus information
	 * @param string : Array of Strings that starts with the genus name until the end of the string
	 */
	private static void extractGeneraInfo(String[] splittedString) 
	{
		GeneraBean genera = new GeneraBean();
		
		//have to set the name, higher taxonomy names, author info, hyper link info, description, time period and figure index by parsing the text word by word.
		
		//set the name and higher taxonomy names
		genera.setGeneraName(splittedString[0]);
		genera.setPhylumName(currentPhylumName);
		genera.setSubPhylumName(currentSubPhylumName);
		genera.setClassName(currentClassName);
		genera.setSubClassName(currentSubclassName);
		genera.setOrderName(currentOrderName);
		genera.setSubOrderName(currentSuborderName);
		genera.setSuperFamilyName(currentSuperfamilyName);
		genera.setFamilyName(currentFamilyName);
		genera.setSubFamilyName(currentSubfamilyName);
		
		
		//extract the author info. the strings that are listed until "[" char is found is the author info 
		//eg: Z. WANG, 1984, p. 55 [*G. sinensis Z. WANG, 1984, p. 55, pl. I,1–7; OD]. - Author info is  Z. WANG, 1984, p. 55 
		StringBuilder authorInfo = new StringBuilder("");
		int wordCount=1;
		while(!splittedString[wordCount].startsWith("["))
		{
			authorInfo.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		genera.setAuthorInfo(authorInfo.toString());
		
		//extract the hyper link info
		StringBuilder hyperLinkInfo = new StringBuilder("");
		while(!splittedString[wordCount].endsWith("].") || splittedString[wordCount+1].startsWith("["))
		{
			hyperLinkInfo.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		hyperLinkInfo.append(splittedString[wordCount]+" ");
		genera.setReferenceInfo(hyperLinkInfo.toString());
		wordCount++;
		
		//extract the description.
		StringBuilder description = new StringBuilder("");
		while(!checkIfWordContainsTimePeriod(splittedString[wordCount]))
		{
			description.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		genera.setDescription(description.toString());
		
		//extract the time period info
		StringBuilder timePeriod = new StringBuilder("");
		while(!splittedString[wordCount].contains("FIG."))
		{
			timePeriod.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		String[] timePeriodFigureSplitter = splittedString[wordCount].split("FIG.");
		if(timePeriodFigureSplitter!=null && timePeriodFigureSplitter.length>0)
			timePeriod.append(timePeriodFigureSplitter[0]);
		genera.setTimePeriod(timePeriod.toString());
		wordCount++;
		
		//extract the figure indexes
		StringBuilder figureIndexes = new StringBuilder("FIG. ");
		if(timePeriodFigureSplitter!=null && timePeriodFigureSplitter.length>1)
			figureIndexes.append(timePeriodFigureSplitter[1]+" ");
		String figureIndex = retrieveFigureIndex(splittedString,wordCount);
		figureIndexes.append(figureIndex);
		genera.setFigureIndex(figureIndexes.toString());
		
		//write the extracted genus info to the XML file
		FileUtility.writeGeneraInformationToXML(genera);
		
		//set the taxonomy into the final genus list.
		generaList.add(genera);
		
		//parse the remaining string
		String[] remainingString = new String[splittedString.length-wordCount];
		for(int i=wordCount,j=0;i<splittedString.length;i++,j++)
			remainingString[j] = splittedString[i];
		
		//after extracting one taxonomy info call parseRemainingString to parse the remaining text
		parseRemainingString(remainingString);
	}


	/**
	 * Method to update the current higher taxonomy names for the purpose of adding to genera object
	 * @param taxonomyType
	 * @param taxonomyName
	 */
	private static void updateCurrentTaxonomyName(String taxonomyType, String taxonomyName) 
	{
		switch(taxonomyType)
		{
			case "Phylum": currentPhylumName = taxonomyName;
			break;
			case "Subphylym": currentSubPhylumName = taxonomyName;
			break;
			case "Class": currentClassName = taxonomyName;
			break;
			case "Subclass": currentSubclassName = taxonomyName;
			break;
			case "Order": currentOrderName = taxonomyName;
			break;
			case "Suborder": currentSuborderName = taxonomyName;
			break;
			case "Superfamily": currentSuperfamilyName = taxonomyName;
			break;
			case "Family": currentFamilyName = taxonomyName;
			break;
			case "Subfamily": currentSubfamilyName = taxonomyName;
			break;
		}	
	}
	
	/**
	 * This method checks if a given word has any reserved time period name in it.
	 * @param word
	 * @return boolean : true if time period name exists else false.
	 */
	private static boolean checkIfWordContainsTimePeriod(String word)
	{
		boolean timePeriodFound = false;
		for(String str : timePeriods)
		{
			if(word.contains(str))
			{
				timePeriodFound = true;
				break;
			}
		}
		return timePeriodFound;
	}
	
	/**
	 * 
	 * method to determine which section i.e genus or taxonomy section comes first in the remaining text.
	 * @param int,int starting indexes of genus and taxonomy in the remaining text
	 * @return int which is the best position to start parsing for useful info.
	 */
	private static int determineWhetherGeneraOrTaxonomyComesNext(int generaStartingIndex, int taxonomyStartingIndex)
	{
		
		if(generaStartingIndex ==-1 && taxonomyStartingIndex ==-1)
			return -1;
		else if(generaStartingIndex==-1 && taxonomyStartingIndex !=-1)
			return taxonomyStartingIndex;
		else if(generaStartingIndex!=-1 && taxonomyStartingIndex ==-1)
			return generaStartingIndex;
		else
		{
			if(generaStartingIndex<taxonomyStartingIndex)
				return generaStartingIndex;
			else
				return taxonomyStartingIndex;
		}
	}
	
	/**
	 * Logic to extract the figure index. Gets the text from starting position till next genus or taxonomy is found.
	 * @param splittedString
	 * @param startingIndex
	 * @return String : figure index
	 */
	private static String retrieveFigureIndex(String[] splittedString, int startingIndex)
	{
		StringBuilder figureIndex = new StringBuilder();
		
		//parse the remaining string
		String[] remainingString = new String[splittedString.length-startingIndex];
		for(int i=startingIndex,j=0;i<splittedString.length;i++,j++)
			remainingString[j] = splittedString[i];
		//parse until next genus or higher taxonomy are found and that text will be figure index.
		int nextGeneraStartingPosition = getStartingIndexOfGeneraInTheRemainingText(remainingString);
		int nextTaxonomyStartingPosition = getStartingIndexofTaxonomyInTheRemainingText(remainingString);
		int nextPositionToParse = determineWhetherGeneraOrTaxonomyComesNext(nextGeneraStartingPosition,nextTaxonomyStartingPosition);
		for(int i=0;i<nextPositionToParse;i++)
			figureIndex.append( remainingString[i]+" ");

		return figureIndex.toString();
	}
}
