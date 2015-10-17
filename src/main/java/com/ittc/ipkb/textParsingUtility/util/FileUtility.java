package com.ittc.ipkb.textParsingUtility.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ittc.ipkb.textParsingUtility.application.TextParser;
import com.ittc.ipkb.textParsingUtility.beans.GeneraBean;
import com.ittc.ipkb.textParsingUtility.beans.TaxonomyBean;

/****
 * 
 * @author rsompalli
 * Utility class to perform file reading and writing operations
 *
 */
public class FileUtility 
{
	/**
	 * Method that reads the input text file line by line and returns the entire content as a single string
	 * @param input text file path
	 * @return String : the content of the file
	 * @throws IOException
	 */
	public static String getWholeTextFileAsAString(String fileName) throws IOException
	{
		//Read the content of the file
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		
		StringBuilder stringBuilder = new StringBuilder(" ");
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
				stringBuilder.append(" ");
			}
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
			try{if(br!=null)br.close();if(in!=null)in.close();if(fstream!=null)fstream.close();}
			catch(IOException e){e.printStackTrace();}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * This method will check if output folder path exists and creates the folder structure if not exists.
	 * @param path
	 */
	public static void createOutputFolder(String path)
	{
		File outputFile = new File(path);
		if(!outputFile.exists())
			outputFile.mkdirs();
		else
		{
			File[] outputFiles = outputFile.listFiles();
			for(File file : outputFiles)
				file.delete();
		}
			
	}
	
	/***
	 * This method is called from the StringUtility.extractTaxonomyInfo () method every time a taxonomy information
	 * is extracted from the input text file.
	 * 
	 * @param taxonomy : TaxonomyBean instance that holds the extracted taxonomy information.
	 */
	public static void writeTaxonomyInformationToXML(TaxonomyBean taxonomy)
	{
		String xmlFilePath = TextParser.outputFolderPath+"\\"+taxonomy.getTaxonomyType()+".xml";
		
		try 
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc;
			Element rootElement;
			
			File xmlFile = new File(xmlFilePath);
			//If file does not already exist create a new doc with root element
			if(!xmlFile.exists())
			{
				// root elements
				doc = docBuilder.newDocument();
				rootElement = doc.createElement(taxonomy.getTaxonomyType()+"s");
				doc.appendChild(rootElement);
				
			}
			else //extract the existing element and append text to it
			{
				doc = docBuilder.parse(xmlFile);
				rootElement = (Element)doc.getFirstChild();
			}

			Element taxonomyTag = doc.createElement(taxonomy.getTaxonomyType());
			Attr nameAttribute = doc.createAttribute("name");
			nameAttribute.setValue(taxonomy.getTaxonomyName());
			taxonomyTag.setAttributeNode(nameAttribute);

			Element authorInfoTag = doc.createElement("authorInfo");
			authorInfoTag.appendChild(doc.createTextNode(taxonomy.getAuthorInfo()));
			taxonomyTag.appendChild(authorInfoTag);

			Element hyperLinkInfoTag = doc.createElement("hyperLinkInfo");
			hyperLinkInfoTag.appendChild(doc.createTextNode(taxonomy.getReferenceInfo()));
			taxonomyTag.appendChild(hyperLinkInfoTag);

			Element descriptionTag = doc.createElement("description");
			descriptionTag.appendChild(doc.createTextNode(taxonomy.getDescription()));
			taxonomyTag.appendChild(descriptionTag);

			Element timePeriodTag = doc.createElement("timePeriod");
			timePeriodTag.appendChild(doc.createTextNode(taxonomy.getTimePeriod()));
			taxonomyTag.appendChild(timePeriodTag);

			rootElement.appendChild(taxonomyTag);


			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);
		} 
		catch (Exception e)
		{
            e.printStackTrace();
        }
	}
	
	
	/***
	 * This method is called from the StringUtility.extractGeneraInfo () method every time a genus information
	 * is extracted from the input text file.
	 * 
	 * @param generaInfo : GeneraBean instance that holds the extracted genus information.
	 */
	public static void writeGeneraInformationToXML(GeneraBean generaInfo)
	{
		String xmlFilePath = TextParser.outputFolderPath+"\\genera.xml";
		
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc;
			Element rootElement;

			File xmlFile = new File(xmlFilePath);
			if(!xmlFile.exists())
			{
				doc = docBuilder.newDocument();
				rootElement = doc.createElement("Genera");
				doc.appendChild(rootElement);
			}
			else
			{
				doc = docBuilder.parse(xmlFile);
				rootElement = (Element)doc.getFirstChild();
			}


			Element genusTag = doc.createElement("Genus");
			Attr nameAttribute = doc.createAttribute("name");
			nameAttribute.setValue(generaInfo.getGeneraName());
			genusTag.setAttributeNode(nameAttribute);

			Element phylumTag = doc.createElement("phylum");
			phylumTag.appendChild(doc.createTextNode(generaInfo.getPhylumName()));
			genusTag.appendChild(phylumTag);

			Element subPhylumTag = doc.createElement("subPhylum");
			subPhylumTag.appendChild(doc.createTextNode(generaInfo.getSubPhylumName()));
			genusTag.appendChild(subPhylumTag);

			Element classTag = doc.createElement("class");
			classTag.appendChild(doc.createTextNode(generaInfo.getClassName()));
			genusTag.appendChild(classTag);

			Element subClassTag = doc.createElement("subClass");
			subClassTag.appendChild(doc.createTextNode(generaInfo.getSubClassName()));
			genusTag.appendChild(subClassTag);

			Element orderTag = doc.createElement("order");
			orderTag.appendChild(doc.createTextNode(generaInfo.getOrderName()));
			genusTag.appendChild(orderTag);

			Element subOrderTag = doc.createElement("subOrder");
			subOrderTag.appendChild(doc.createTextNode(generaInfo.getSubOrderName()));
			genusTag.appendChild(subOrderTag);

			Element superFamilyTag = doc.createElement("superFamily");
			superFamilyTag.appendChild(doc.createTextNode(generaInfo.getSuperFamilyName()));
			genusTag.appendChild(superFamilyTag);

			Element familyTag = doc.createElement("family");
			familyTag.appendChild(doc.createTextNode(generaInfo.getFamilyName()));
			genusTag.appendChild(familyTag);

			Element subFamilyTag = doc.createElement("subFamily");
			subFamilyTag.appendChild(doc.createTextNode(generaInfo.getSubFamilyName()));
			genusTag.appendChild(subFamilyTag);

			Element authorInfoTag = doc.createElement("authorInfo");
			authorInfoTag.appendChild(doc.createTextNode(generaInfo.getAuthorInfo()));
			genusTag.appendChild(authorInfoTag);

			Element hyperLinkInfoTag = doc.createElement("hyperLinkInfo");
			hyperLinkInfoTag.appendChild(doc.createTextNode(generaInfo.getReferenceInfo()));
			genusTag.appendChild(hyperLinkInfoTag);

			Element descriptionTag = doc.createElement("description");
			descriptionTag.appendChild(doc.createTextNode(generaInfo.getDescription()));
			genusTag.appendChild(descriptionTag);

			Element timePeriodTag = doc.createElement("timePeriod");
			timePeriodTag.appendChild(doc.createTextNode(generaInfo.getTimePeriod()));
			genusTag.appendChild(timePeriodTag);

			Element figureIndexTag = doc.createElement("figureIndex");
			figureIndexTag.appendChild(doc.createTextNode(generaInfo.getFigureIndex()));
			genusTag.appendChild(figureIndexTag);

			rootElement.appendChild(genusTag);


			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	//This part is deprecated as of now. None of the below methods are in Use.
	
	/**
	 * 
	 * This method writes the Taxonomy information to XML files. Each level of Taxonomy will have a seperate file eg: Class.xml, Subclass.xml etc.,
	 * @param finalTaxonomyInformation
	 */
	public static void writeTaxonomyInformationToXML(Map<String, List<TaxonomyBean>> finalTaxonomyInformation) 
	{
		for(Map.Entry<String, List<TaxonomyBean>> taxonomyInformation : finalTaxonomyInformation.entrySet())
		{
			if(taxonomyInformation.getValue()!=null && !taxonomyInformation.getValue().isEmpty())
			{
				generateXMLFile(taxonomyInformation.getKey(),taxonomyInformation.getValue());
			}
		}
	}
	
	
	/**
	 * This method writes the actual information retrieved to an XML file with name as fileName
	 * @param fileName
	 * @param dataToBeWritten
	 */
	public static void generateXMLFile(String fileName,List<TaxonomyBean> dataToBeWritten)
	{
		try 
		{

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(fileName+"s");
			doc.appendChild(rootElement);
 
           for(TaxonomyBean taxonomy : dataToBeWritten)
           {
        	   Element taxonomyTag = doc.createElement(fileName);
        	   Attr nameAttribute = doc.createAttribute("name");
        	   nameAttribute.setValue(taxonomy.getTaxonomyName());
        	   taxonomyTag.setAttributeNode(nameAttribute);
        	   
        	   Element authorInfoTag = doc.createElement("authorInfo");
        	   authorInfoTag.appendChild(doc.createTextNode(taxonomy.getAuthorInfo()));
        	   taxonomyTag.appendChild(authorInfoTag);
        	   
        	   Element hyperLinkInfoTag = doc.createElement("hyperLinkInfo");
        	   hyperLinkInfoTag.appendChild(doc.createTextNode(taxonomy.getReferenceInfo()));
        	   taxonomyTag.appendChild(hyperLinkInfoTag);
        	   
        	   Element descriptionTag = doc.createElement("description");
        	   descriptionTag.appendChild(doc.createTextNode(taxonomy.getDescription()));
        	   taxonomyTag.appendChild(descriptionTag);
        	   
        	   Element timePeriodTag = doc.createElement("timePeriod");
        	   timePeriodTag.appendChild(doc.createTextNode(taxonomy.getTimePeriod()));
        	   taxonomyTag.appendChild(timePeriodTag);
        	   
        	   rootElement.appendChild(taxonomyTag);
           }
 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\output\\"+fileName+".xml"));
			transformer.transform(source, result);

			System.out.println("File saved!");
 
        } 
		catch (Exception e)
		{
            e.printStackTrace();
        }
	}
	
	

}
