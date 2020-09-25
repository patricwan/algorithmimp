package com.sap.perf.loadrunner.gatling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LRRequest {
	
	private String lrRequestType;
	
	public String getLrRequestType() {
		return lrRequestType;
	}

	public void setLrRequestType(String lrRequestType) {
		this.lrRequestType = lrRequestType;
	}
	
	private String lrRequestName;
	
	//web_add_header
	private HashMap headerMap = new HashMap();
	
	//web_add_header
	private HashMap autoHeaderMap = new HashMap();

	public HashMap getAutoHeaderMap() {
		return autoHeaderMap;
	}

	public void setAutoHeaderMap(HashMap autoHeaderMap) {
		this.autoHeaderMap = autoHeaderMap;
	}
	
	public void addAutoHeader(String key, String value) {
		this.autoHeaderMap.put(key, value);
	}

	//request properties
	private HashMap properties = new HashMap();
	
	//ItemData
	private HashMap itemDataMap = new HashMap();
	
	private List<String> extraItemList = new ArrayList<String>();

	public List<String> getExtraItemList() {
		return extraItemList;
	}

	public void setExtraItemList(List<String> extraItemList) {
		this.extraItemList = extraItemList;
	}
	
	public  void addExtraItem(String extraItem) {
		this.extraItemList.add(extraItem);
	}

	public HashMap getItemDataMap() {
		return itemDataMap;
	}

	public void setItemDataMap(HashMap itemDataMap) {
		this.itemDataMap = itemDataMap;
	}

	public void addItemData(String key, String value) {
		this.itemDataMap.put(key, value);
	}
	
	//web_reg_find 
	private List<HashMap> regFindList = new ArrayList<HashMap>();
	
	public List getRegFindList() {
		return regFindList;
	}

	public void setRegFindMap(List<HashMap> regFindList) {
		this.regFindList = regFindList;
	}
	
	public void addRegFindMap(HashMap regFindMap) {
		this.regFindList.add(regFindMap);
	}
   
	//web_reg_save_param
	private List<LRRegParam> listRegParams = new ArrayList<LRRegParam>();
	
	public List<LRRegParam> getListRegParams() {
		return listRegParams;
	}

	public void setListRegParams(List<LRRegParam> listRegParams) {
		this.listRegParams = listRegParams;
	}
	
	public void addRegParam(LRRegParam lrRegParam) {
		this.listRegParams.add(lrRegParam); 
	}

	public HashMap getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(HashMap headerMap) {
		this.headerMap = headerMap;
	}
	
	public void addHeader(String key, String value) {
		this.headerMap.put(key, value);
	}
	
	public String getLrRequestName() {
		return lrRequestName;
	}

	public void setLrRequestName(String lrRequestName) {
		this.lrRequestName = lrRequestName;
	}

	public HashMap getProperties() {
		return properties;
	}

	public void setProperties(HashMap properties) {
		this.properties = properties;
	}
	
	public void addProperty(String key, String value) {
		this.properties.put(key, value);
		
	}
}
