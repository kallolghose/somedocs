package com.automation.cato;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;

import com.automation.utilities.Utilities;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

public class CheckSomeThing 
{
	public static void main(String[] args) throws MalformedURLException 
	{
//		DesktopOptions options = new DesktopOptions();
//		options.setDebugConnectToRunningApp(true);
//		options.setApplicationPath("C:\\Windows\\System32\\notepad.exe");
//		WiniumDriver winDriver = new WiniumDriver(new URL("http://localhost:9999"), options);
//		//winDriver.findElement(By.xpath("//*[contains(text(),'FireEyes II')]")).click();
//		WebElement notepad = winDriver.findElement(By.className("Notepad"));
//		notepad.findElement(By.className("Edit")).sendKeys("SomeThing");
		Utilities.getCorrectionScript("<input class='gsfi' id='lst-ib' maxlength='2048' name='q' autocomplete='off' title='Search' value='' aria-label='Search' aria-haspopup='false' role='combobox' aria-autocomplete='both' style='border: medium none; padding: 0px; margin: 0px; height: auto; width: 100%; background: transparent url(&quot;data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw%3D%3D&quot;) repeat scroll 0% 0%; position: absolute; z-index: 6; left: 0px; outline: medium none;' dir='ltr' spellcheck='false' type='text'/>");
	}
}
