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
	private static final String[] TAXONOMY_NAMES = {"Class","Subclass","Order","Suborder","Superfamily","Family","Subfamily"};
	private static List<String> taxanomyNames = Arrays.asList(TAXONOMY_NAMES);
	
	//List of all time periods.
	private static final String[] TIME_PERIODS = {"Aalenian","Actonian","Aeronian","Aksayan","Albian","Alexandrian","Amgaian","Amgan","Anisian","Aptian","Aquitanian","Arenig","Artinskian","Ashgill","Asselian","Atdabanian",
			"Atlantic",	"Ayusokkanian",	"Bajocian","Barremian","Bartonian","Bashkirian","Bathonian","Batyrbayan","Berriasian","Black River","Blancan","Boomerangian","Boreal","Botomian","Burdigalian","Calabrian","Callovian",
			"Cambrian", "Campanian","Capitanian","Caradoc","Carboniferous","Carnian","Cassinian","Castleman","Cautleyan","Cayugan","Cenomanian","Cenozoic","Changhsingian","Changlangpuan","Changshanian","Chattian","Chazy",
			"chrons","Cisuralian","Coniacian","Costonian","Cretaceous","Danian","Dapingian","Darriwilian","Datsonian","Delamaran","Deming","Devonian","Dolgellian","Dyeran","Early Cambrian","Early Cretaceous","Early Devonian",
			"Early Jurassic","Early Mississippian","Early Ordovician","Early Pennsylvanian","Early Triassic","Eden","Eemian","Eifelian","Emsian","Eocene","Famennian","Fengshanian","Ffestiniogian","Floian","Florian","Franconian",
			"Frasnian","Furongian","Gaconadian","Gamach","Gelasian","Givetian","Gorstian","Guadalupian","Gzhelian","Harnagian","Hauterivian","Hettangian","Hirnantian","Holocene","Homerian","Ibexian","Idamean","Induan","Ionian",
			"Iverian","Jefferson","Jurassic","Kasimovian","Kazanian","Kimmeridgian","Kirkfield","Kungurian","Ladinian","Langhian","Late Cambrian","Late Cretaceous","Late Devonian","Late Jurassic","Late Mississippian","Late Ordovician",
			"Late Pennsylvanian","Late Triassic","Lenian","Llandeilo","Llandovery","Llanvirn","Lochkovian","Lockportian","Longvillian","Longwangmioan","Lopingian","Lower Cambrian","Lower Cretaceous","Lower Devonian","Lower Jurassic",
			"Lower Mississippian","Lower Ordovician","Lower Pennsylvanian","Lower Triassic","Ludfordian","Ludlow","Lutetian","Maastrichtian","Maentwrogian","Maozhangian",
			"Marjuman",
			"Marshbrookian",
			"Mayaian",
			"Mayan",
			"Maysville",
			"Meishuchuan",
			"Mesozoic",
			"Messinian",
			"Middle Cambrian",
			"Middle Devonian",
			"Middle Jurassic",
			"Middle Mississippian",
			"Middle Ordovician",
			"Middle Pennsylvanian",
			"Middle Triassic",
			"Mindyallan",
			"Miocene",
			"Mississippian",
			"Montezuman",
			"Moscovian",
			"Neogene",
			"Norian",
			"Olenekian",
			"Oligocene",
			"Onnian",
			"Ontarian",
			"Ordian",
			"Ordovician",
			"Oxfordian",
			"Paibian",
			"Paleocene",
			"Paleogene",
			"Paleozoic",
			"Payntonian",
			"Pennsylvanian",
			"Permian",
			"Phanerozoic",
			"Piacenzian",
			"Pleistocene",
			"Pliensbachian",
			"Pliocene",
			"Pragian",
			"Preboreal",
			"Priabonian",
			"Pridoli",
			"Pusgillian",
			"Quaternary",
			"Qungzusian",
			"Rawtheyan",
			"Rhaetian",
			"Rhuddanian",
			"Richmond",
			"Roadian",
			"Rockland",
			"Rupelian",
			"Sakian",
			"Sakmarian",
			"Sangamonian",
			"Santonian",
			"Selandian",
			"Serpukhovian",
			"Serravallian",
			"Sheinwoodian",
			"Sherman",
			"Silurian",
			"Sinemurian",
			"Soundleyan",
			"Stage",
			"Steptoan",
			"Subatlantic",
			"Subboreal",
			"Sunwaptan",
			"Tarantian",
			"Telychian",
			"Templetonian",
			"Thanetian",
			"Tithonian",
			"Toarcian",
			"Tommotian",
			"Tonawandan",
			"Tortonian",
			"Tournaisian",
			"Toyonian",
			"Tremadoc",
			"Trempealeauan",
			"Trenton",
			"Triassic",
			"Turonian",
			"Tyrrhenian",
			"Ufimian",
			"Undillian",
			"Upper Cambrian",
			"Upper Cretaceous",
			"Upper Devonian",
			"Upper Jurassic",
			"Upper Mississippian",
			"Upper Ordovician",
			"Upper Pennsylvanian",
			"Upper Triassic",
			"Valanginian",
			"Vis¨¦an",
			"Waucoban",
			"Wenlock",
			"Whiterock",
			"Wordian",
			"Wuchiapingian",
			"Ypresian",
			"Zanclean",
			"Zhungxian",
			"Zuzhuangian"
	};
	
	private static List<String> timePeriods = Arrays.asList(TIME_PERIODS);
	
	
	//Pattern to check if all the characters in a string are capital letters
	private static Pattern capitalLettersPattern = Pattern.compile("[A-Z]*");
	private static Pattern capitalLetterFollowedBySmallLetter =Pattern.compile("[A-Z][a-z]*");
	private static Pattern allCapitalLettersFollowedByDotOrComma = Pattern.compile("[A-Z]*[.|,]");
	//TODO: This needs to be modified to check for the pattern 1992b. as of now works only for 1992.
	private static Pattern fourDigitNumber = Pattern.compile("\\d{4} *.");
	
	//Final results are populated into these data strucutres
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
	private static String currentClassName = "UNKNOWN";
	private static String currentSubclassName = "UNKNOWN";
	private static String currentOrderName = "UNKNOWN";
	private static String currentSuborderName = "UNKNOWN";
	private static String currentSuperfamilyName = "UNKNOWN";
	private static String currentFamilyName = "UNKNOWN";
	private static String currentSubfamilyName = "UNKNOWN";
	
	//An indicator to indicate if the last taxonomy found is family or sub family in some cases.
	//Used to indicate the starting position of genera information.
	private static String lastTaxonomyTypeFound = "Phylum";
	
	/**
	 * This method first parses the input array of strings for taxonomy information and after finding the 
	 * Family or Sub Family, it then looks for the genera information until next higher taxa is found and then again 
	 * repeats the same process when Higher taxa are found.
	 * 
	 * Used to extract both taxonomy and genera information.
	 * 
	 * @param textString - the array of strings that has the remaining words that are yet to be parsed.
	 */
	public static void parseRemainingString(String[] splittedWords)
	{
		
		//get the index position the string array that has a taxonomy word.
		int taxonomyStartingPosition = getTheStartingIndexofTaxonomy(splittedWords);
		
		//If the starting position is -1, no more taxanomies are found in the remaining text.
		if(taxonomyStartingPosition!=-1)
		{
			//If last found taxonomy is family, then look for next taxonomy and if the next taxonomy is sub family go ahead 
			// and parse the sub family taxonomy information else look for the genera information, until another high level taxa 
			// is found.
			if(lastTaxonomyTypeFound.equals("Family"))//TODO: Update this logic to check if last taxonomy name and current taxonomy name are different.	
			{
				String currentTaxonomyTypeFound = splittedWords[taxonomyStartingPosition];
				if(currentTaxonomyTypeFound.equals("Subfamily"))
				{
					//Ignore the text before the taxonomy information and build an array that starts with taxonomy info till the end of the remaing text
					String[] remainingString = new String[splittedWords.length-taxonomyStartingPosition];
					for(int i=taxonomyStartingPosition,j=0;i<splittedWords.length;i++,j++)
						remainingString[j]=splittedWords[i];
					extractTaxonomyInfo(remainingString);
				}
				else
					parseTextForGeneraInfo(splittedWords);
			}
			//If last found taxonomy is sub family, look for the genera information until next taxonomy keyword is found.
			else if(lastTaxonomyTypeFound.equals("Subfamily"))
			{
				parseTextForGeneraInfo(splittedWords);
			}
			//Look only for higher taxonomy information until the family or sub family taxas are found.
			else
			{
				//Ignore the text before the taxonomy information and build an array that starts with taxonomy info till the end of the remaing text
				String[] remainingString = new String[splittedWords.length-taxonomyStartingPosition];
				for(int i=taxonomyStartingPosition,j=0;i<splittedWords.length;i++,j++)
					remainingString[j]=splittedWords[i];
				extractTaxonomyInfo(remainingString);
			}
		}
		//check if there exists any genera information in the remaining text.
		else
		{
			parseTextForGeneraInfo(splittedWords);
		}
	}


	/**
	 * returns the index position in the string array where a taxonomy description starts
	 * @param splittedWords
	 * @return int : index of the taxonomy details starting position, -1 if the taxonomy is not found in the inout string
	 */
	private static int getTheStartingIndexofTaxonomy(String[] splittedWords) 
	{
		for(int wordCount=0;wordCount<splittedWords.length;wordCount++)
		{
			//if the word is a taxonomy word and is followed by a String of all capital letters then it is a taxonomy. Eg: Class CHAROPHYCEAE
			if(taxanomyNames.contains(splittedWords[wordCount]) && capitalLettersPattern.matcher(splittedWords[wordCount+1]).matches())
			{
				return wordCount;
			}
		}
		return -1;
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
		taxonomy.setHyperLinkInfo(hyperLinkInfo.toString());
		wordCount++;
		
		//extract the description.
		StringBuilder description = new StringBuilder("");
		while(!timePeriods.contains(splittedString[wordCount]))
		{
			description.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		taxonomy.setDescription(description.toString());
		
		//extract the time period info
		//TODO: this section needs to be modified to give appropriate pattern to end text.
		StringBuilder timePeriod = new StringBuilder("");
		while(!splittedString[wordCount].endsWith("."))
		{
			timePeriod.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		timePeriod.append(splittedString[wordCount]);
		taxonomy.setTimePeriod(timePeriod.toString());
		wordCount++;
		/*System.out.println("Taxonomy Type:"+taxonomy.getTaxonomyType()+", Name:"+taxonomy.getTaxonomyName()+", Author Info:"
		+taxonomy.getAuthorInfo()+",  Hyperlink Info:"+taxonomy.getHyperLinkInfo()+", Description:"+taxonomy.getDescription()+",  Time Period:"+taxonomy.getTimePeriod());*/
		
		//set the taxonomy into the final map.
		taxonomyDetailsMap.get(taxonomy.getTaxonomyType()).add(taxonomy);
		
		//parse the remaining string
		String[] remainingString = new String[splittedString.length-wordCount];
		for(int i=wordCount,j=0;i<splittedString.length;i++,j++)
			remainingString[j] = splittedString[i];
		
			
		//update the last taxonomy type found to the latest taxonomy type parsed and continue the process of parsing for the remaining string
	    lastTaxonomyTypeFound = taxonomy.getTaxonomyType();
		//call this method to parse the remaining String
	    parseRemainingString(remainingString);
	}
	
	/***
	 * This method parses the given array of strings and extracts the genera information.
	 * @param splittedString
	 */
	private static void parseTextForGeneraInfo(String[] splittedString)
	{
		int generaStartingPosition = getStartingIndexOfGenera(splittedString);
		System.out.println("Genera FOund at::"+generaStartingPosition);
		
		if(generaStartingPosition!=-1)
		{
			String[] remainingString = new String[splittedString.length-generaStartingPosition];
			for(int i=generaStartingPosition,j=0;i<splittedString.length;i++,j++)
				remainingString[j]=splittedString[i];
			extractGeneraInfo(remainingString);
		}
		
	}
	

	/**
	 * This method checks the regular patterns identified for the genera information.
	 * Steps include check for a String that starts with a capital letter followed by a set of samll letters
	 * and then check for a string that have all caiptal letters and may end with . or , and then find for the year number among the near by strings and after year number followed by p.
	 * @param splittedString
	 * @return the position in the string array from where the genera info starts, return -1 if no taxonomies are found.
	 */
	private static int getStartingIndexOfGenera(String[] splittedString)
	{
		int wordIndex = 0;
		
		while(wordIndex<splittedString.length)
		{
			//Look for this pattern as example: Moellerina ULRICH, 1886, p.
			//If a word has a capital letter followed by all small letters
			if(capitalLetterFollowedBySmallLetter.matcher(splittedString[wordIndex]).matches())
			{
				//And if the next word is a word with all the capital letters and ended by a , or . eg: ULRICH, or YANG & ZHOU,
				if(capitalLettersPattern.matcher(splittedString[wordIndex+1]).matches() || allCapitalLettersFollowedByDotOrComma.matcher(splittedString[wordIndex+1]).matches())
				{
					//There can be multiple author names before the year starts, estimating the number of author names would be a max of 10 before year ifno starts
					for(int i=wordIndex+1;i<wordIndex+10;i++)
					{
						//And the followed by a year number and followed by a string "p." eg: 1886, p.
						if(fourDigitNumber.matcher(splittedString[i]).matches() && splittedString[i+1].equals("p."))
						{
							return wordIndex;
						}
					}
				}
			}
			wordIndex++;		
		}
		
		return -1;
	}
	
	
	/**
	 * Contains logic to extract the Genera information
	 * @param string : Array of Strings that starts with the genera name until the end of the string
	 */
	private static void extractGeneraInfo(String[] splittedString) 
	{
		GeneraBean genera = new GeneraBean();
		
		//have to set the name, higher taxa names, author info, hyperlink info, description, time period and figure index by parsing the text word by word.
		
		//set the name and higher taxa names
		genera.setGeneraName(splittedString[0]);
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
		int wordCount=2;
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
		genera.setHyperLinkInfo(hyperLinkInfo.toString());
		wordCount++;
		
		//extract the description.
		StringBuilder description = new StringBuilder("");
		while(!timePeriods.contains(splittedString[wordCount]))
		{
			description.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		genera.setDescription(description.toString());
		
		//extract the time period info
		StringBuilder timePeriod = new StringBuilder("");
		while(!splittedString[wordCount].contains("——"))
		{
			timePeriod.append(splittedString[wordCount]+" ");
			wordCount++;
		}
		String[] timePeriodFigureSplitter = splittedString[wordCount].split("——");
		timePeriod.append(timePeriodFigureSplitter[0]);
		genera.setTimePeriod(timePeriod.toString());
		wordCount++;
		
		//extract the figure indexes
		//TODO: have to write logic to extract figure index.
		StringBuilder figureIndexes = new StringBuilder(timePeriodFigureSplitter[1]+" ");
		
		System.out.println("Genera Name:"+genera.getGeneraName()+"Class name:"+genera.getClassName()+"Sub CLass Name::"+genera.getSubClassName()
		+"order name:"+genera.getOrderName()+"sub order name:"+genera.getSubOrderName()+"Super family:"+genera.getSuperFamilyName()+
		"Sub Family name:"+genera.getSubFamilyName()+"Author:"+genera.getAuthorInfo()+"HyperLink info:"+genera.getHyperLinkInfo()+
		"Description:"+genera.getDescription()+"time period:"+genera.getTimePeriod());
		
		//set the taxonomy into the final map.
		generaList.add(genera);
		
		//parse the remaining string
		String[] remainingString = new String[splittedString.length-wordCount];
		for(int i=wordCount,j=0;i<splittedString.length;i++,j++)
			remainingString[j] = splittedString[i];
		
		//call this method to parse the remaining String
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
}
