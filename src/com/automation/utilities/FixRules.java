package com.automation.utilities;

public class FixRules {
	
	public static String RULE_NOT_FOUND = "RULE NOT FOUND";
	public static String AUTO_FIXED_PERFORMED = "AUTO FIXED PERFORMED";
	public static String SKIPPED = "SKIPPED";
	
	private String ViolationDescription;
	private String Violation;
	private String Tag;
	private String Code_Change;
	private String Operation;
	private String File;
	
	public String getViolationDescription() {
		return ViolationDescription;
	}
	public void setViolationDescription(String violationDescription) {
		ViolationDescription = violationDescription;
	}
	public String getViolation() {
		return Violation;
	}
	public void setViolation(String violation) {
		Violation = violation;
	}
	public String getTag() {
		return Tag;
	}
	public void setTag(String tag) {
		Tag = tag;
	}
	public String getCode_Change() {
		return Code_Change;
	}
	public void setCode_Change(String code_Change) {
		Code_Change = code_Change;
	}
	public String getOperation() {
		return Operation;
	}
	public void setOperation(String operation) {
		Operation = operation;
	}
	public String getFile() {
		return File;
	}
	public void setFile(String file) {
		File = file;
	}
}
