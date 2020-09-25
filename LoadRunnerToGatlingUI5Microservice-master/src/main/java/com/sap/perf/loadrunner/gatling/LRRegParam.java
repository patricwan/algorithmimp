package com.sap.perf.loadrunner.gatling;

import java.util.HashMap;

public class LRRegParam {
	
	private String paramName;

	private HashMap attributes = new HashMap();
	
	
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public HashMap getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap attributes) {
		this.attributes = attributes;
	}

   public void addNewAttribute(String key, String value) {
	   this.attributes.put(key, value);
   }
	

}
