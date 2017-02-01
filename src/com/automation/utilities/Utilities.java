package com.automation.utilities;

import java.util.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Utilities 
{
	public static void main(String[] args) {
		processCSV("C:\\Users\\KG00360770\\Downloads\\FireEyesFile_Copy.csv");
	}
	
	public static void processCSV(String csvPath)
	{
		try
		{
			String csvFile = "C:\\Users\\KG00360770\\Downloads\\FireEyesFile..csv";
			Scanner readFile = new Scanner(new File(csvFile));
			String newCsv = "";
			while(readFile.hasNext())
			{
				newCsv = newCsv + getRequiredColumns(CSVUtils.parseLine(readFile.nextLine()),0,1,2,5,6,7) + "\n";
			}
			readFile.close();
			PrintWriter writer =  new PrintWriter("D:\\newFile.csv");
			writer.println(newCsv);
			writer.close();
		}
		catch(Exception e)
		{
			System.out.println("");
			e.printStackTrace();
		}
	}
	
	public static String getRequiredColumns(List<String> str, int ... indexes)
	{
		try
		{
			String retStr = "";
			for(int i=0;i<indexes.length;i++)
			{
				retStr = retStr + str.get(indexes[i]) + ",";
			}
			retStr = retStr.substring(0, retStr.length()-1);
			return retStr;
		}
		catch(Exception e)
		{
			System.out.println("");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	public static void processCSV(String csvPath)
	{
		try
		{
			File file = new File(csvPath);
			//BufferedReader br = new BufferedReader(new FileReader(file));
			Scanner br = new Scanner(file);
			String newData = "", temp;
			//while((temp = br.readLine())!=null)
			while(br.hasNext())
			{
				temp = br.nextLine();
				newData = newData + getrequiredDataFromColumns(temp,0,1,5,6,7) + "\n";
				//break;
			}
			br.close();
			
			//Write the contents to file again
			PrintWriter writer = new PrintWriter(new File("D:\\newFile.csv"));
			writer.print(newData);
			writer.close();
		}
		catch(Exception e)
		{
			System.out.println("[Utilites - processCSV()] - Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static String getrequiredDataFromColumns(String line, int ... indexes)
	{
		try
		{
			System.out.println(line + "\n");
			String [] csvdata = line.split(",");
			String newStr = "";
			for(int i=0;i<indexes.length;i++)
			{
				newStr = newStr + csvdata[indexes[i]] + ",";
			}
			newStr = newStr.substring(0,newStr.length()-1);
			return newStr;
		}
		catch(Exception e)
		{
			System.out.println("[Utilties - getrequiredColumns()] - Exception : " + e.getMessage());
			e.printStackTrace();
		}
 		return null;
	}
	*/
	
	public static void injectJSToElement(WebDriver driver, String injectCode)
	{
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(injectCode);
		}
		catch(Exception e)
		{
			System.out.println("[Utilites : injectJSToElement] : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static String getCorrectionScript(String inputelement)
	{
		try
		{
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(inputelement)));
			
			NodeList nodeList = doc.getChildNodes();
			Node node = nodeList.item(0);
			Element elt = (Element)node;
			String tagName = elt.getTagName();
			if(tagName.equalsIgnoreCase("input"))
			{
				tagName = tagName + "[type=" + elt.getAttribute("type") + "]";
			}
			System.out.println(tagName);
		}
		catch(Exception e)
		{
			System.out.println("[Utilites : getCorrectionScript()] : " + e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}
