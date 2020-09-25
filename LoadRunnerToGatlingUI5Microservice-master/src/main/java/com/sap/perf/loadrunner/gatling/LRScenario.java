package com.sap.perf.loadrunner.gatling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRScenario {

	private String name;

	private Map<String, LRUsrCase> usrCaseMap = new HashMap<String, LRUsrCase>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, LRUsrCase> getUsrCaseMap() {
		return usrCaseMap;
	}
	
	public LRUsrCase getLRUsrCase(String uiName) {
		return usrCaseMap.get(uiName);
	}

	public void addUsrCaseMap(String uiName, LRUsrCase lrUsrCase) {
		this.usrCaseMap.put(uiName, lrUsrCase);
	}

}
