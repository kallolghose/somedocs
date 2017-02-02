package com.automation.utilities;

import java.util.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import au.com.bytecode.opencsv.CSVReader;

public class Utilities 
{
	
	public static void main(String[] args) {
		processCSV("","C:\\Users\\KG00360770\\Downloads\\FireEyesFile.csv");
	}
	
	public static List<Violation_Fixation> processCSV(String pageName, String csvPath)
	{
		try
		{
			List<Violation_Fixation> fixList = new ArrayList<Violation_Fixation>();
			CSVReader reader = new CSVReader(new FileReader(csvPath),',','"',1);
			List<String[]> allRows = reader.readAll();
			for(String[] row : allRows)
			{
				Violation_Fixation temp = new Violation_Fixation();
				temp.setViolationPagename(pageName);
				temp.setViolationDescription(row[3]);
				temp.setViolationID(row[1]);
				temp.setViolationElement(row[7]);
				temp.setViolationDate(row[6]);
				fixList.add(temp);
			}
			reader.close();
			return fixList;
		}
		catch(Exception e)
		{
			System.out.println("[Utilites : processCSV()] - Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getRequiredColumns(String [] str, int ... indexes)
	{
		try
		{
			String retStr = "";
			for(int i=0;i<indexes.length;i++)
			{
				retStr = retStr + str[indexes[i]] + ",";
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
	
	public static Violation_Fixation getCorrectionScript(Violation_Fixation vFix)
	{
		try
		{
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(vFix.getViolationElement())));
			NodeList nodeList = doc.getChildNodes();
			Node node = nodeList.item(0);
			Element elt = (Element)node;
			String tagName = elt.getTagName();
			if(tagName.equalsIgnoreCase("input"))
			{
				tagName = tagName + "[type=" + elt.getAttribute("type") + "]";
			}
			String key = tagName + "__" + vFix.getViolationID();
			FixRules rule = ExcelUtility.getRuleForElement(key);
			if(rule!=null)
			{
				String operation = rule.getOperation();
				if(operation.equalsIgnoreCase("ADD_ATTRIBUTE"))
				{
					String code_change = rule.getCode_Change();
					String attr[] = code_change.split("=");
					elt.setAttribute(attr[0], attr[1]);
				}
			}
			else
			{
				vFix.setFixComments(FixRules.RULE_NOT_FOUND);
			}
		}
		catch(Exception e)
		{
			System.out.println("[Utilites : getCorrectionScript()] : " + e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}
