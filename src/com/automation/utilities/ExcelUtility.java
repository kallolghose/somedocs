package com.automation.utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExcelUtility 
{
	private static HashMap<String,FixRules> codeHM = new HashMap<String,FixRules>();
	public static String EXCEL_PATH = "D:\\KG00360770\\TECHM\\TechmRnd\\CATO_Rnd\\rules.xlsx";
	
	public static void readExcelToHM(String excelPath)
	{
		try
		{
			excelPath = excelPath.equalsIgnoreCase("") ? EXCEL_PATH : excelPath;
			FileInputStream file = new FileInputStream(new File(excelPath));
			XSSFWorkbook workbook = new XSSFWorkbook(file); 
			XSSFSheet sheet = workbook.getSheet("Mapping");
			Iterator<Row> itr = sheet.iterator();
			while(itr.hasNext())
			{
				Row row = itr.next();
				if(row.getCell(0)!=null)
				{
					FixRules data = new FixRules();
					data.setTag(row.getCell(0).getStringCellValue());
					data.setViolationDescription(row.getCell(1).getStringCellValue());
					data.setViolation(row.getCell(2).getStringCellValue());
					data.setCode_Change(row.getCell(3).getStringCellValue());
					data.setOperation(row.getCell(4).getStringCellValue());
					data.setFile(row.getCell(5).getStringCellValue());
					String key = data.getTag()+"__"+data.getViolation();
					codeHM.put(key, data);
				}
			}
			workbook.close();
		}
		catch(Exception e)
		{
			System.out.println("[ExcelUtility  - readExcel()] : Exception - " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static FixRules getRuleForElement(String elt)
	{
		try
		{
			return codeHM.get(elt);
		}
		catch(Exception e)
		{
			System.out.println("[Excelutility : getRuleForElement()] - Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
