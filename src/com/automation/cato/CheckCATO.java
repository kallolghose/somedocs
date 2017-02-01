package com.automation.cato;

import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumOptions;

import com.automation.utilities.Utilities;
import com.sun.glass.events.KeyEvent;

public class CheckCATO 
{
	private static String sSeleniumJar = "selenium-server-standalone-3.0.1.jar";
	private static String sPackageFldr = System.getProperty("user.dir") + "/Packages/";
	private static String EXTENSION_PATH = "C:\\Users\\KG00360770\\Downloads\\FireEyesII_extension.xpi";
	private static String GECKODRIVERPATH = "D:\\KG00360770\\ATT\\Automation\\Automation_Suite\\IO\\Utilities\\geckodriver.exe";
	private static String FFBINPATH = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	private static String CSVPATH = "C:\\Users\\KG00360770\\Downloads\\";
	
	public static void main(String[] args) throws Exception 
	{
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		File extension = new File(EXTENSION_PATH);
		firefoxProfile.addExtension(extension);
		
		gridStartFirefox();
		System.setProperty("webdriver.gecko.driver",GECKODRIVERPATH);
		DesiredCapabilities oCap = DesiredCapabilities.firefox(); 
		oCap.setBrowserName("firefox");
		oCap.setCapability("firefox_binary", FFBINPATH);
		oCap.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
		URL sURL = new URL("http://localhost:5555/wd/hub");
		WebDriver driver = new RemoteWebDriver(sURL, oCap);
		driver.get("https://www.google.co.in/?gfe_rd=cr&ei=lFKQWKPENOfI8AfFmaaQAw");
		String openInspect = Keys.chord(Keys.CONTROL, Keys.SHIFT, "i");
		driver.findElement(By.tagName("html")).sendKeys(openInspect);
		
		DesktopOptions options = new DesktopOptions();
		options.setDebugConnectToRunningApp(true);
		options.setApplicationPath(FFBINPATH);
		WiniumDriver winDriver = new WiniumDriver(new URL("http://localhost:9999"), options);
		WebElement mozilla = winDriver.findElement(By.className("MozillaWindowClass"));
		//mozilla.findElement(By.xpath("//*[contains(text(),'FireEyes II')]")).click();
		List<WebElement> elts = mozilla.findElements(By.className(""));	
		for(WebElement elt : elts)
		{
			String name = elt.getAttribute("Name");
			if(name!=null)
			{
				if(elt.getAttribute("Name").equalsIgnoreCase("FireEyes II"))
				{
					if(elt.getAttribute("ControlType").equals("ControlType.Text"))
					{
						elt.click();
					}
				}
			}
			
		}
		
		//Click on analyse using tab
		performTabEnter(2);
		
		waitForUsingString(mozilla, 1 , 2, "violations");
		
		//Click on export using tab
		performTabEnter(3);
		
		Thread.sleep(2*1000);
		WebElement saveDialog = winDriver.findElement(By.className("#32770"));
		WebElement filename = saveDialog.findElement(By.className("Edit"));
		filename.sendKeys("FireEyesFile"); //THe CSV filename
		WebElement saveBtn = getElementUsingName(saveDialog, "Button", "Save");
		if(saveBtn!=null)
		{
			saveBtn.click();
			//Check if save as dialog comes up
			WebElement dialogElement = getElementUsingName(saveDialog, "#32770", "Confirm Save As");
			if(dialogElement!=null)
			{
				WebElement yesBtn = getElementUsingName(dialogElement, "CCPushButton", "Yes");
				if(yesBtn!=null)
					yesBtn.click();
			}
		}
		
		//Utilities.processCSV(CSVPATH + "FireEyesFile.csv");
	}
	
	
	private static boolean waitForUsingString(WebElement parent,int maxTimeinMin, int intervalinsec, String exitString)
	{
		try
		{
			int cluster = ((maxTimeinMin*60)/intervalinsec);
			while(cluster-->0)
			{
				Thread.sleep(intervalinsec*1000);
				List<WebElement> elts = parent.findElements(By.className(""));	
				for(WebElement elt : elts)
				{
					String name = elt.getAttribute("Name");
					if(name!=null)
					{
						if(elt.getAttribute("Name").contains(exitString))
						{
							return true;
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in waitForUsingString : " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	//Perform tab operation n times and then press enter
	private static void performTabEnter(int n)
	{
		try
		{
			Robot robot = new Robot();
			for(int i=0;i<n;i++)
			{
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
			}
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}
		catch(Exception e)
		{
			System.out.println("performTabEnter : Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static WebElement getElementUsingName(WebElement parent,String className, String name)
	{
		try
		{
			List<WebElement> list = parent.findElements(By.className(className));
			for(WebElement elt:list)
			{
				if(elt.getAttribute("Name").equals(name))
				{
					return elt;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in getBtn : " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private static void gridStartFirefox() throws Exception {
		if (getHTTPResponseStatusCode("http://localhost:5555/wd/hub/status") == 404 ) {
			List<String> cmd= Arrays.asList("cmd.exe", "/k","cd \"" + sPackageFldr + "\" && " + sPackageFldr.split("\\\\")[0] + " && "
					+ "java -jar " + sSeleniumJar 
					+ " -role node -hub http://localhost:4444/grid/register -browser browserName=firefox,maxInstances=5 -port 5555");
			String sResp = executeRunCommand(cmd, true);
			
		} else
			System.out.println("Open");
	}
	
	private static int getHTTPResponseStatusCode(String u) throws IOException {
		
		try {
			URL url = new URL(u);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			return http.getResponseCode();
		} catch (Exception e) {
			return 404;
		}
    }
	public static String executeRunCommand(List<String> sCmds, boolean isSession) throws IOException, InterruptedException {
		
		String sTotalResp = "", sLineResp = null; int iCtr=1;
			
		try {
			ProcessBuilder builder = new ProcessBuilder(sCmds);
			builder.redirectErrorStream(true);
			Process p = builder.start(); 
			if (isSession) {
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				Thread.sleep(500);
				while (br.ready()) {
					sLineResp = br.readLine();
					sTotalResp += "\n"+ sLineResp;
					System.out.println(sLineResp);
					Thread.sleep(500);
				}
				br.close();
			} else {
				Thread.sleep(5000);
				p.destroyForcibly();
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((sLineResp = br.readLine()) != null) {
					sTotalResp += sLineResp;
				}
				br.close();
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return sTotalResp;
	}
}
