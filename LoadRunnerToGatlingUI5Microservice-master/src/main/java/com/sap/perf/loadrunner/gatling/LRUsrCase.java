package com.sap.perf.loadrunner.gatling;

public class LRUsrCase {
	
	private String uiName;
	
	private String usrFilePath;
	
	private String usrFileAbsPath;

	private int numUsers;
	
	private float perNumUsers;

	public String getUsrFileAbsPath() {
		return usrFileAbsPath;
	}

	public void setUsrFileAbsPath(String usrFileAbsPath) {
		this.usrFileAbsPath = usrFileAbsPath;
	}
	
	public String getUiName() {
		return uiName;
	}

	public void setUiName(String uiName) {
		this.uiName = uiName;
	}

	public String getUsrFilePath() {
		return usrFilePath;
	}

	public void setUsrFilePath(String usrFilePath) {
		this.usrFilePath = usrFilePath;
	}

	public int getNumUsers() {
		return numUsers;
	}

	public void setNumUsers(int numUsers) {
		this.numUsers = numUsers;
	}

	public float getPerNumUsers() {
		return perNumUsers;
	}

	public void setPerNumUsers(float perNumUsers) {
		this.perNumUsers = perNumUsers;
	}
}
