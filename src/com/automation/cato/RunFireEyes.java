package com.automation.cato;

import java.util.List;

import com.automation.utilities.ExcelUtility;
import com.automation.utilities.Fireeyes;
import com.automation.utilities.Utilities;
import com.automation.utilities.Violation_Fixation;

public class RunFireEyes
{
	private static String CSVPATH = "C:\\Users\\KG00360770\\Downloads\\Cato\\";
	public static void main(String[] args) 
	{
		ExcelUtility.readExcelToHM("");
		
		Fireeyes.CSV_FILENAME = "planFeatures_changeMyPlan_noAverageUsage_again";
		Fireeyes.openBrowserAndDevTools("http://myatt:D3v$57t!@portal5.stage.att.net/ds2/1611/#/planFeatures_changeMyPlan_noAverageUsage");
		Fireeyes.startFireeyes();
		//Fireeyes.closeBrowser();
		List<Violation_Fixation> fixList = Utilities.processCSV(Fireeyes.getWebDriver().getTitle(), CSVPATH + 
								"planFeatures_changeMyPlan_noAverageUsage_again.csv");
		System.out.println(fixList.size());
		//List<Violation_Fixation> fixList = Utilities.processCSV("Google", CSVPATH + "planFeatures_changeMyPlan_noAverageUsage.csv");
		for(int i=0;i<fixList.size();i++)
		{
			Violation_Fixation violation = Utilities.getCorrectionScript(fixList.get(i));
			fixList.set(i, violation);
		}
		
		Utilities.printCSVFromList("D:\\fixList.csv",fixList);
	}
}
