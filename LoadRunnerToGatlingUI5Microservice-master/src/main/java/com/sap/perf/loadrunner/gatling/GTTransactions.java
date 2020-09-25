package com.sap.perf.loadrunner.gatling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GTTransactions {

	private String fileName;

	private String packageName;

	private String imported;

	private String objectName;

	private String varsDefinitions;

	private List<String> transactionStrList = new ArrayList<String>();

	private List<HashMap> autoHeaderList = new ArrayList<HashMap>();

	private LRAction lrAction = null;

	private static Logger logger = LogManager.getLogger(GTTransactions.class);

	public GTTransactions(String fileName, String packageName, LRAction lrAction) {
		super();
		this.fileName = fileName;
		this.packageName = packageName;
		this.lrAction = lrAction;
	}

	public String getFileName() {
		return fileName;
	}

	public String getPackageName() {
		return packageName;
	}

	public String generateStringForOutput() {
		logger.debug("========================Going to generate output string for transaction " + this.getFileName()
				+ "============");

		StringBuffer output = new StringBuffer();
		output.append("package " + packageName + ";\r\n");

		// Append imported packages

		// Append some blank rows
		output.append("\r\n");
		output.append("\r\n");
		output.append("\r\n");

		// Append class name definition line
		output.append("object " + this.getFileName() + " { " + "\r\n");

		output.append("\r\n");

		List<LRTransaction> lrTransactions = lrAction.getLrTransctions();
		for (LRTransaction lrEach : lrTransactions) {
			output.append(generateEachTranStr(lrEach));
		}

		// append closing bracket
		output.append("}" + "\r\n");

		return output.toString();
	}

	private String generateEachTranStr(LRTransaction lrTransaction) {
		StringBuffer eachTrans = new StringBuffer();
		// add definition
		eachTrans.append("  val ").append(lrTransaction.getTransactionName()).append("=").append("\r\n");

		// Append request string
		if (lrTransaction.getLrRequests() != null) {
			for (LRRequest lrRequest : lrTransaction.getLrRequests()) {
				logger.debug("Going to generate LRRequest String for " + lrRequest.getLrRequestName());
				String method = (String) lrRequest.getProperties().get("Method");
				if (method == null) {
					method = "get";
				}
				logger.debug("Request method is " + method);
				eachTrans.append(generateRequestString(lrRequest));
				eachTrans.append("\r\n");

				eachTrans.append(generateHeadersString(lrRequest));
				eachTrans.append(generateRegFindString(lrRequest));
				eachTrans.append(generateRegParamString(lrRequest));
				eachTrans.append(generateFormDataString(lrRequest, method ));
				
				eachTrans.append(")");
			}
			eachTrans.append(";");
			eachTrans.append("\r\n");
		}
		return eachTrans.toString();
	}

	// Generate general http url, method..
	private String generateRequestString(LRRequest lrRequest) {
		StringBuffer eachRequest = new StringBuffer();
		eachRequest.append("	      exec(http(\"" + lrRequest.getLrRequestName() + "\")").append("\r\n");
		eachRequest.append("         .");
		// get or post. Depends on the method.
		if ("POST".equals(lrRequest.getProperties().get("METHOD"))) {
			eachRequest.append("post");
		} else {
			eachRequest.append("get");
		}
		eachRequest.append("(\"");
		String url = (String) ((lrRequest.getProperties().get("Action") != null)
				? lrRequest.getProperties().get("Action") : lrRequest.getProperties().get("URL"));
		eachRequest.append(url);
		eachRequest.append("\")");

		/*
		 * eachTrans.append("	      exec(http(\"" +
		 * lrRequest.getLrRequestName() + "\")").append("\r\n");
		 * eachTrans.append("            .").append("get").append("\"").append(
		 * "\"\r\n"); HashMap headerMap = lrRequest.getHeaderMap(); // Loop the
		 * header hashMap Iterator it = headerMap.entrySet().iterator(); while
		 * (it.hasNext()) { Map.Entry pair = (Map.Entry) it.next();
		 * System.out.println(pair.getKey() + " = " + pair.getValue());
		 * eachTrans.append("      .header(\"").append(pair.getKey()).append(
		 * "\",\"").append(pair.getValue()) .append("\")\r\n"); }
		 * eachTrans.append("      .check(status.in(302, 200))\r\n"); eachTrans.
		 * append("      .check(responseTimeInMillis.saveAs(\"execLatency\")))\r\n"
		 * ); eachTrans.append("            .exec(session => { ");
		 * eachTrans.append("            })\r\n");
		 */
		return eachRequest.toString();
	}

	private String generateHeadersString(LRRequest lrRequest) {
		StringBuilder headersString = new StringBuilder();
		HashMap autoHeaderMap = lrRequest.getAutoHeaderMap();
		if (autoHeaderMap != null) {
			autoHeaderList.add(autoHeaderMap);
		}
		//For autoHeader, add to class level variable HashMap list, then in the following request, it would always add this to request header.
		for (HashMap eachAutoheader : autoHeaderList) {
			headersString.append(generateHeaderMapString(eachAutoheader));
		}

		HashMap headerMap = lrRequest.getHeaderMap();
		headersString.append(generateHeaderMapString(headerMap));
		
		return headersString.toString();
	}
	
	private String generateHeaderMapString(HashMap headerMap) {
		StringBuilder headerStr = new StringBuilder();
		Iterator it = headerMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			headerStr.append("         .header(\"");
			headerStr.append(pair.getKey());
			headerStr.append("\",\"");
			if (pair.getValue() != null) {
				headerStr.append(pair.getValue());
			}
			headerStr.append("\")");
			headerStr.append("\r\n");
		}
		
		return headerStr.toString();
	}

	private String generateFormDataString(LRRequest lrRequest, String method) {
		StringBuilder formDataStr = new StringBuilder();
		HashMap formDataMap = lrRequest.getItemDataMap();
		Iterator it = formDataMap.entrySet().iterator();
		String paramStr = "";
		if ("Post".equalsIgnoreCase(method)) {
			paramStr = "formParam";
		} else if ("Get".equalsIgnoreCase("Post"))  {
			paramStr = "queryParam";
		}
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			formDataStr.append("         ." + paramStr + "(\"");
			formDataStr.append(pair.getKey());
			formDataStr.append("\"");
			formDataStr.append(",\"");
			formDataStr.append(pair.getValue());
			formDataStr.append("\")");
			formDataStr.append("\r\n");
		}
		
		return formDataStr.toString();
	}

	private String generateRegFindString(LRRequest lrRequest) {
		StringBuilder regFindStr = new StringBuilder();
		List<HashMap>  regFindList = lrRequest.getRegFindList();
		for (HashMap regFindMap : regFindList) {
			//TODO: To add web_reg_find conversion logic. 
			
		}
		
		return regFindStr.toString();
	}

	private String generateRegParamString(LRRequest lrRequest) {
		StringBuilder regParamsStr = new StringBuilder();
		List<LRRegParam>  regParamList = lrRequest.getListRegParams();
		for (LRRegParam regParam : regParamList) {
			String paramName = regParam.getParamName();
			HashMap attributeMap = regParam.getAttributes();
			
			String lbStr = (String)attributeMap.get("LB");
			String rbStr = (String)attributeMap.get("RB");
			regParamsStr.append("         .check(regex(\"");
			regParamsStr.append(lbStr);		
			regParamsStr.append("(.*?)");
			regParamsStr.append(rbStr);
			regParamsStr.append("\").saveAs(\"");
			regParamsStr.append(paramName);
			regParamsStr.append("\")");
			regParamsStr.append("\r\n");
		}
		return regParamsStr.toString();
	}
}
