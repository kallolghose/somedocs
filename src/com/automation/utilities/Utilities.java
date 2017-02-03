package com.automation.utilities;

import java.util.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
			String eltXml = vFix.getViolationElement();
			//Fix Element
			if(!eltXml.contains("</")) //Check if end tag does not exists
			{
				if(!eltXml.contains("/>")) //Check if element does not end in the same line
				{
					eltXml = eltXml.replace(">", "/>");
				}
			}
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(eltXml)));
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
				vFix = parseRule(Fireeyes.getWebDriver(), vFix, elt, rule, rule.getOperation());
			}
			else
			{
				vFix.setFixComments(FixRules.RULE_NOT_FOUND);
			}
			return vFix;
		}
		catch(Exception e)
		{
			System.out.println("[Utilites : getCorrectionScript()] : " + e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static Violation_Fixation parseRule(WebDriver driver,Violation_Fixation vFix,Element node,FixRules fixRule,String nextRule)
	{
		try
		{
			String rules[] = nextRule.split(" ");
			if(rules[0].equalsIgnoreCase("ADD_ATTRIBUTE"))
			{
				String code_change = fixRule.getCode_Change();
				String attr[] = code_change.split("=");
				node.setAttribute(attr[0], attr[1]); //Enhance the following
				String xmlNode = getStringFromNode(node);
				vFix.setFixElement(xmlNode);
				vFix.setFixRule(FixRules.AUTO_FIXED_PERFORMED);
			}
			if(rules[0].equalsIgnoreCase("SKIP"))
			{
				vFix.setFixRule(FixRules.SKIPPED);
			}
			
			if(rules[0].equalsIgnoreCase("CHECK_HEADER_FOOTER"))
			{
				String ancestor_header_xpath = "//*[ancestor::header]";
				
				WebElement errorElt = driver.findElement(By.id(node.getAttribute("id")));
				if(errorElt.findElements(By.xpath(ancestor_header_xpath)).size()!=0)
				{
					vFix = parseRule(driver, vFix, node, fixRule, rules[1]);
				}
				String ancestor_footer_xpath = "//*[ancestor::footer]";
				if(errorElt.findElements(By.xpath(ancestor_footer_xpath)).size()!=0)
				{
					vFix = parseRule(driver, vFix, node, fixRule, rules[1]);
				}
			}
			
			return vFix;
		}
		catch(Exception e)
		{
			System.out.println("[Utilities - parseRule] : Exception  : " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getStringFromNode(Element node)
	{
		try
		{
			StringWriter writer = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(node), new StreamResult(writer));
			String xml = writer.toString();
			return xml;
		}
		catch(Exception e)
		{
			System.out.println("");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void printCSVFromList(String fileName,List<Violation_Fixation> fix)
	{
		try
		{
			File file = new File(fileName);
			PrintWriter writer = new PrintWriter(file);
			writer.println("PageName, ID, Description, Date, Element, FixRule, FixedElement, FixComments, FixDate");
			Iterator<Violation_Fixation> itr = fix.iterator();
			while(itr.hasNext())
			{
				System.out.println(itr.next().toString());
				writer.println(itr.next().toString());
			}
			writer.close();
		}
		catch(Exception e)
		{
			System.out.println("[Utilities : printCSVFromList] : Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
