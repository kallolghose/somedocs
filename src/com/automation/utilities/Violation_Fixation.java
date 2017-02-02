package com.automation.utilities;

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
	private Date fixDate;
	
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
	public Date getFixDate() {
		return fixDate;
	}
	public void setFixDate(Date fixDate) {
		this.fixDate = fixDate;
	}
}
