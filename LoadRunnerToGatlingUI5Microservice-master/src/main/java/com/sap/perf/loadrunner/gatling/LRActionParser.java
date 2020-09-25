package com.sap.perf.loadrunner.gatling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LRActionParser {

	private static Logger logger = LogManager.getLogger(LRActionParser.class);

	String filePath = null;

	public LRActionParser(String actionPath) {
		super();
		this.filePath = actionPath;
	}

	public LRAction parseActionFile() {
		LRAction lrAction = new LRAction();
		logger.debug("File Name " + Utils.getFileNameFromFullPath(this.filePath));
		String fileStr = readFile(this.filePath);
		logger.debug("Orginal fileStr " + fileStr);
		String cleanActionStr = Utils.trimHeaderAndFooter(fileStr, Utils.getFileNameFromFullPath(this.filePath));

		// Remove comments like /* comments */ non-greedy model
		cleanActionStr = Utils.trimBlockContents(cleanActionStr);
		logger.debug("File Str after trimHeader Footer and comments " + cleanActionStr);

		// Split by lr_end_transaction. eg:
		// lr_end_transaction("ECT_MSS_BP01_JobInfo_09_SaveJobInformationPortlet",LR_AUTO);
		// no-greedy model
		String endTranReg = "lr_end_transaction\\s*\\(\\s*(.+?)\\)\\s*;";
		String[] strArr = cleanActionStr.split(endTranReg);
		List<LRTransaction> lrTransactionList = new ArrayList<LRTransaction>();
		for (String sTr : strArr) {
			logger.debug("split transaction string: " + sTr);
			// skip the first part, action beginning to first transaction.
			if (!sTr.contains("lr_start_transaction")) {
				continue;
			}

			String patternSurfix = "(lr|web)_(.*?)\\)\\s*;";
			List<String> strLRs = Utils.captureMatchedStringList(sTr, patternSurfix, 0);

			// Each transaction object.
			LRTransaction lrTransaction = convertToTransaction(strLRs);
			lrTransactionList.add(lrTransaction);
		}

		lrAction.setLrTransctions(lrTransactionList);
		
		return lrAction;
	}

	private void toRegFind(LRRequest lrRequest, String regFindStr) {

		List<String> reqProperties = Utils.captureMatchedStringList(regFindStr, "\"(.+?)\"", 1);
		HashMap regFindMap = new HashMap();
		for (String propStr : reqProperties) {
			//propStr = propStr.replaceAll("\"", "").trim();
			propStr.substring(1, propStr.length() -1);

			/*String[] propArray = propStr.split("=");
			if (propArray.length == 2) {
				lrRequest.addRegFind(propArray[0], propArray[1]);
			}*/
			int firstEqual = propStr.indexOf("=");
			if (firstEqual > 1) {
				//lrRequest.addRegFind(propStr.substring(0, firstEqual), propStr.substring(firstEqual+1));
				regFindMap.put(propStr.substring(0, firstEqual), propStr.substring(firstEqual+1));
			}
		}
		lrRequest.addRegFindMap(regFindMap);
	}

	private void toRegParam(LRRequest lrRequest, String regParamStr) {
		LRRegParam lrRegParam = new LRRegParam();

		List<String> reqProperties = Utils.captureMatchedStringList(regParamStr, "\"(.+?)\",", 1);
		int iIndex = 0;
		for (String propStr : reqProperties) {
			logger.debug("parse web_save_param " + propStr);
			//propStr = propStr.replaceAll("\"", "").trim();   //Here sometimes there is \" in the str, so we can't replaceAll
			propStr.substring(1, propStr.length() -1);
			if (iIndex == 0) {
				lrRegParam.setParamName(propStr);
			} else {
				int firstEqual = propStr.indexOf("=");
				if (firstEqual > 1) {
					lrRegParam.addNewAttribute(propStr.substring(0, firstEqual), propStr.substring(firstEqual+1));
				}
			}
			iIndex++;
		}

		lrRequest.addRegParam(lrRegParam);
	}

	private void toLRRequestSubmitData(LRRequest lrRequest, String submitRequest, String itemText) {
		if (lrRequest == null) {
			lrRequest = new LRRequest();
		}

		// -1 means no group just the whole matching, 1 means getting 1st
		// group()
		List<String> attributesList = Utils.captureMatchedStringList(submitRequest, "\\s*\\((.+?),\\s*" + itemText, 1);
		if (attributesList.size() > 0) {
			String attributeStr = attributesList.get(0);
			logger.debug("parse web_submit_data attribute:" + attributeStr + " " + itemText);
			int iIndex = 0;
			for (String propStr : attributeStr.split(",")) {
				propStr = propStr.replaceAll("\"", "").trim();
				//propStr.substring(1, propStr.length() -1);
				logger.debug("web submit_data_properties " + propStr);
				if (iIndex == 0) {
					lrRequest.setLrRequestName(propStr);
				} else {
					//String[] propArray = propStr.split("=");
					//There is a = in the value like: Action={TestURL}/login?_s.crb={scrb3_URL}, we can't use plit("=") simply for it.
					int firstEqual = propStr.indexOf("=");
					/*if (propArray.length == 2) {
						logger.debug("Insert web_submit_data property " + propArray[0] + " " + propArray[1]);
						lrRequest.getAdditionaProperties().put(propArray[0], propArray[1]);
					} */
					if (firstEqual > 1) {
						lrRequest.getProperties().put(propStr.substring(0, firstEqual), propStr.substring(firstEqual+1));
					}
				}
				iIndex++;
			}
		}

		List<String> itemDataList = Utils.captureMatchedStringList(submitRequest, itemText + "\\s*,(.+?),\\s*LAST", 1);
		if (itemDataList.size() > 0) {
			String itemDataStr = itemDataList.get(0);
			logger.debug("parse item_data :" + itemText + " " + itemDataStr);
			String[] items = itemDataStr.split(",(\\s*ENDITEM\\s*),\\s*");
			for (String item : items) {
				logger.debug("parse item_data item :" + item);
				if (itemText.trim().equals("ITEMDATA")) {
					List<String> itemNames = Utils.captureMatchedStringList(item, "\"Name=(.+?)\"", 1);
					List<String> itemValues = Utils.captureMatchedStringList(item, "\"Value=(.+?)\"", 1);
					if (itemNames.size() > 0) {
						String value = null;
						if (itemValues.size() > 0) {
							value =  itemValues.get(0);
						} 
						lrRequest.addItemData(itemNames.get(0), value);
					}
				} else if (itemText.trim().equals("EXTRARES")) {
					logger.debug("Got extrares item :" + item);
					List<String> itemList = Utils.captureMatchedStringList(item, "\"(.+?)\"", 1);
					if (itemList.size() > 0) {
						lrRequest.addExtraItem(itemList.get(0));
					}
				}
			}
		}
	}

	private void toLRRequest(LRRequest lrRequest, String webRequestStr) {
		if (lrRequest == null) {
			lrRequest = new LRRequest();
		}

		if (webRequestStr.contains("EXTRARES")) {
			logger.debug("Will parse request with EXTRAES");
			toLRRequestSubmitData(lrRequest, webRequestStr, "EXTRARES");
		} else {
			List<String> reqProperties = Utils.captureMatchedStringList(webRequestStr, "\"(.+?)\"", 1);
			int iIndex = 0;
			for (String propStr : reqProperties) {
				if (iIndex == 0) {
					lrRequest.setLrRequestName(propStr);
				} else {
					/*String[] propArray = propStr.split("=");
					if (propArray.length == 2) {
						lrRequest.getProperties().put(propArray[0], propArray[1]);
					}*/
					int firstEqual = propStr.indexOf("=");
					if (firstEqual > 1) {
						lrRequest.getProperties().put(propStr.substring(0, firstEqual), propStr.substring(firstEqual+1));
					}
				}
				iIndex++;
			}
		}
	}

	private LRTransaction convertToTransaction(List<String> strLRs) {
		LRTransaction lrTransaction = new LRTransaction();
		boolean isNewReqExist = false;
		LRRequest lrRequest = null;

		for (String sLR : strLRs) {
			logger.debug("sLR or Web:" + sLR);
			if (sLR.startsWith("lr_think_time")) {
				List<String> strThinkTimeList = Utils.captureMatchedStringList(sLR, "lr_think_time\\((.*?)\\);", 1);
				lrTransaction.setThinkTime(strThinkTimeList.get(0));
			} else if (sLR.startsWith("lr_start_transaction")) {
				List<String> strTransactionNameList = Utils.captureMatchedStringList(sLR,
						"lr_start_transaction\\(\"(.*?)\"\\);", 1);
				if (strTransactionNameList.size() > 0) {
					lrTransaction.setTransactionName(strTransactionNameList.get(0));
				} else {
					logger.error("No transaction Name found for this File:  " + this.filePath);
				}
			} else if (sLR.startsWith("web_add_header")) { // Here may need to   start a new  LRRequest
				HttpHeader headerObj = headerToMap(sLR);
				if (!isNewReqExist) {
					lrRequest = new LRRequest();
					isNewReqExist = true;
					lrTransaction.addLrRequest(lrRequest);
				}
				lrRequest.addHeader(headerObj.getKeyName(), headerObj.getValue());
			} else if (sLR.startsWith("web_add_auto_header")) { // Here may need to   start a new  LRRequest
				HttpHeader headerObj = headerToMap(sLR);
				if (!isNewReqExist) {
					lrRequest = new LRRequest();
					isNewReqExist = true;
					lrTransaction.addLrRequest(lrRequest);
				}
				lrRequest.addAutoHeader(headerObj.getKeyName(), headerObj.getValue());
			}  else if (sLR.startsWith("web_reg_find")) {
				if (!isNewReqExist) {
					lrRequest = new LRRequest();
					isNewReqExist = true;
					lrTransaction.addLrRequest(lrRequest);
				}
				toRegFind(lrRequest, sLR);

			} else if (sLR.startsWith("web_reg_save_param")) {
				if (!isNewReqExist) {
					lrRequest = new LRRequest();
					isNewReqExist = true;
					lrTransaction.addLrRequest(lrRequest);
				}
				toRegParam(lrRequest, sLR);

			} else if (sLR.startsWith("web_url")) {
				if (!isNewReqExist) {
					lrRequest = new LRRequest();
					isNewReqExist = true;
					lrTransaction.addLrRequest(lrRequest);
				}
				toLRRequest(lrRequest, sLR);
				lrRequest.setLrRequestType("web_url");
				// Here should end the LRRequest
				isNewReqExist = false;
			} else if (sLR.startsWith("web_custom_request")) {
				if (!isNewReqExist) {
					lrRequest = new LRRequest();
					isNewReqExist = true;
					lrTransaction.addLrRequest(lrRequest);
				}
				toLRRequest(lrRequest, sLR);
				lrRequest.setLrRequestType("web_custom_request");
				// Here should end the LRRequest
				isNewReqExist = false;
			} else if (sLR.startsWith("web_submit_data")) {
				if (!isNewReqExist) {
					lrRequest = new LRRequest();
					isNewReqExist = true;
					lrTransaction.addLrRequest(lrRequest);
				}
				lrRequest.setLrRequestType("web_submit_data");
				toLRRequestSubmitData(lrRequest, sLR, "ITEMDATA");
				// Here should end the LRRequest
				isNewReqExist = false;
			}
		}

		return lrTransaction;
	}

	private HttpHeader headerToMap(String header) {
		HttpHeader headerObj = new HttpHeader();

		List<String> valueList = Utils.captureMatchedStringList(header, "\"(.+?)\"", 1);
		headerObj.setKeyName(valueList.get(0));
		headerObj.setValue(valueList.get(1));
		return headerObj;
	}

	private String readFile(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuffer retStr = new StringBuffer();

		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				if (!tempString.trim().startsWith("//")) {
					retStr.append(tempString.trim());
					line++;
				} else {
					logger.debug("Found a comment and trim it:" + tempString);
				}
			}
			reader.close();
		} catch (IOException e) {
			logger.error("Exception while reading Action file " + e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error("Exception while reading Action file " + e1.getMessage());
				}
			}
		}
		return retStr.toString();
	}
}
