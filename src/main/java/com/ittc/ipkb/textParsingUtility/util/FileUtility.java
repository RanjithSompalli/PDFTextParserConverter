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
			try
			{
				if(br!=null)
					br.close();
				if(in!=null)
					in.close();
				if(fstream!=null)
					fstream.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return stringBuilder.toString();
	}

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
	 * TODO: Write the logic for this method.
	 * 
	 * This method writes the Genera information to XML files. Eg: Genera.xml
	 * @param finalGeneraInformation
	 */
	public static void writeGenerateInformationToXML(List<GeneraBean> finalGeneraInformation)
	{
		
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
        	   hyperLinkInfoTag.appendChild(doc.createTextNode(taxonomy.getHyperLinkInfo()));
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
