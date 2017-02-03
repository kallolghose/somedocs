package com.automation.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Violation_Fixation 
{
	private String violationPagename;
	private String violationID;
	private String violationDescription;
	private String violationDate;
	private String violationElement;
	
	private String fixRule;
	private String fixElement;
	private String fixComments; //AUTO-FIXED, RULE NOT PRESENT, SKIPPED
	private String fixDate;
	
	public Violation_Fixation()
	{
		violationPagename = "";
		violationID = "";
		violationDescription = "";
		violationDate = "";
		violationElement = "";
		
		fixRule = "";
		fixElement = "";
		fixComments = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		fixDate = dateFormat.format(date);
	}
	
	public String getViolationPagename() {
		return violationPagename;
	}
	public void setViolationPagename(String violationPagename) {
		this.violationPagename = violationPagename;
	}
	public String getViolationID() {
		return violationID;
	}
	public void setViolationID(String violationID) {
		this.violationID = violationID;
	}
	public String getViolationDescription() {
		return violationDescription;
	}
	public void setViolationDescription(String violationDescription) {
		this.violationDescription = violationDescription;
	}
	public String getViolationDate() {
		return violationDate;
	}
	public void setViolationDate(String violationDate) {
		this.violationDate = violationDate;
	}
	public String getViolationElement() {
		return violationElement;
	}
	public void setViolationElement(String violationElement) {
		this.violationElement = violationElement;
	}
	public String getFixRule() {
		return fixRule;
	}
	public void setFixRule(String fixRule) {
		this.fixRule = fixRule;
	}
	public String getFixElement() {
		return fixElement;
	}
	public void setFixElement(String fixElement) {
		this.fixElement = fixElement;
	}
	public String getFixComments() {
		return fixComments;
	}
	public void setFixComments(String fixComments) {
		this.fixComments = fixComments;
	}
	public String getFixDate() {
		return fixDate;
	}
	public void setFixDate(String fixDate) {
		this.fixDate = fixDate;
	}
	
	public String toString()
	{
		try
		{
			return violationPagename+", " + violationID + ", " + violationDescription + ", " + violationElement + ", " + violationDate
					+ ", " + fixRule + ", " + fixElement + ", " + fixComments + ", " + fixDate;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
}
