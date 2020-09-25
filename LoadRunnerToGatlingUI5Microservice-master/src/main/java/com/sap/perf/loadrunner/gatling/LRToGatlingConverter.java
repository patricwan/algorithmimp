package com.sap.perf.loadrunner.gatling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LRToGatlingConverter {
	
	static List<File> fileList = new ArrayList<File>();
	// A hashMap to store: usrFile Name and all its corresponding actions for
	// this usrFile.
	static Map folderTransactionsMap = new HashMap();
	private static Logger logger = LogManager.getLogger(LRToGatlingConverter.class);
	
	public String fromLRtoGatling(String lrBaseFolder, String scenarioFile, String gatlingOutputFolder) throws Exception {
		
		String scenarioOutputPath = gatlingOutputFolder + "scala" + File.separator + "scenarios";
		// Walk through the folder and found all action files
		//String folder = lrBaseFolder + "Scripts" + File.separator + "EC";
		String outputPath = gatlingOutputFolder + "scala" + File.separator + "transactions";
		ObjectMapper mapper = new ObjectMapper();

		// walkThrough(folder, ".usr");
		// Convert to LRScenario Object for a scenario file
		LRScenario lrScenario = parseLRScenarioFile(scenarioFile);
		mapper.writeValue(new File(
				lrBaseFolder + "Scenarios" + File.separator + Utils.getFileNameFromFullPath(scenarioFile) + ".json"),
				lrScenario);

		// Temp solution: Collect usr files and write to fileList
		walkThrough(lrScenario, scenarioFile);
		// Read usr Files and store Actions list to a Collection, store
		// Transactions List in another Collection. Both to hashmap.
		Map folderActionFilesMap = collectActionFiles();

		// Read prm files and store parameter data file information in a HashMap
		Map paramsMap = collectParamsMap();
		// Convert object to JSON string and save into a file directly

		// Either copy or filter/create .dat files to corresponding dest folder
		// as data files.
		prepareDataFiles(paramsMap, lrBaseFolder, gatlingOutputFolder);

		// Then actually we can write a Gatling scenario file
		String gatlingScenario = convertToGatlingScenario(lrScenario, folderTransactionsMap, paramsMap);
		Utils.writeStringToFile(gatlingScenario,
				scenarioOutputPath + File.separator + Utils.getFileNameFromFullPath(scenarioFile) + ".scala");

		mapper.writeValue(new File(lrBaseFolder + "Scenarios" + File.separator + "folderActions.json"),
				folderActionFilesMap);
		mapper.writeValue(new File(lrBaseFolder + "Scenarios" + File.separator + "allParams.json"), paramsMap);
		mapper.writeValue(new File(lrBaseFolder + "Scenarios" + File.separator + "folderTransactions.json"),
				folderTransactionsMap);

		Iterator iter = folderActionFilesMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Collection> entry = (Map.Entry<String, Collection>) iter.next();
			String userFile = entry.getKey();
			Collection valCollection = entry.getValue();
			logger.debug("Script Folder  " + userFile);
			// Convert one script folder to scala and output
			String scalaTranStr = convertToScala(userFile, valCollection, outputPath);
			Utils.writeStringToFile(scalaTranStr, outputPath + File.separator + getScalaName(userFile) + ".scala");
		}
		
		return gatlingOutputFolder;
	}
	
	private  String convertToGatlingScenario(LRScenario lrScenario, Map folderTransactionsMap, Map paramsMap) {
		Map strMaps = prepareAllStrings(lrScenario, folderTransactionsMap, paramsMap);
		
		StringBuilder output = new StringBuilder();
		output.append("package " + "scenario" + ";\r\n");
		output.append("\r\n");
		// Append imported packages
		output.append("import com.typesafe.config._;\r\n");
		output.append("import io.gatling.core.Predef._;\r\n");
		output.append("import io.gatling.http.Predef._;\r\n");
		output.append("import org.joda.time.DateTime;\r\n");
		output.append("import transactions._;\r\n");
		output.append("import scala.concurrent.duration._;\r\n");
		output.append("import scala.util.Random;\r\n");

		// Append some blank rows
		output.append("\r\n");

		// Append class name definition line
		output.append("class " + lrScenario.getName() + " extends Simulation { " + "\r\n");
		output.append("\r\n");
		
		output.append("    val conf = ConfigFactory.load(); \r\n");
		output.append("    val baseUrl = conf.getString(\"cfPerfloadBaseURL\");\r\n");
		output.append("   val BASE_URL:String = System.getProperty(\"url\", baseUrl);\r\n");
		output.append("\r\n");
		
		output.append("    var nUsers: Int = System.getProperty(\"peakTPS\",\"100\").toInt;\r\n");
		output.append("    var currentDate: String = DateTime.now().toLocalDate.toString(\"YYYY-MM-dd\");\r\n");
		output.append("    var nDurationRamp: Double = System.getProperty(\"rampUpTime\", \"5\").toDouble'\r\n");
		output.append("    var nDuration: Double = System.getProperty(\"steadyStateTime\", \"10\").toDouble;\r\n");
		output.append("    var hostAddress:String= BASE_URL.drop(8);\r\n");
		output.append("    var sessionID = Iterator.continually(Map(\"sessionID\" -> ( Random.alphanumeric.take(35).mkString )));\r\n");
		output.append("    val logger = org.slf4j.LoggerFactory.getLogger(\"login\");\r\n");
		output.append("\r\n");

		//Add data definiton part 
		output.append((String)strMaps.get("dataDef"));
		output.append("\r\n");
		output.append("   val startTime = System.currentTimeMillis;");
		
		//Add http protocol definition part
		output.append("\r\n");
		output.append("    val httpProtocol = http.baseURL(BASE_URL)\r\n");
		output.append("          .inferHtmlResources(BlackList(), WhiteList())\r\n");
		output.append("          .acceptHeader(\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\")\r\n");
		output.append("          .acceptEncodingHeader(\"gzip, deflate, sdch, br\")\r\n");
		output.append("          .acceptLanguageHeader(\"en-US,en;q=0.8,ta;q=0.6\")\r\n");
		output.append("          .connectionHeader(\"keep-alive\")\r\n");
		output.append("         .header(\"Host\",hostAddress)\r\n");
		output.append("         .header(\"Upgrade-Insecure-Requests\",\"1\")\r\n");
		output.append("         .userAgentHeader(\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36\")\r\n");
		output.append("         .disableWarmUp;\r\n");
		output.append("\r\n");
		output.append("\r\n");		
		
		//Add scenario definiton part 
		output.append((String)strMaps.get("usrScenDef"));
		output.append("\r\n");		
		
		//Add inject definiton part 
		output.append((String)strMaps.get("injectDef"));
		output.append("\r\n");		
		
		output.append(" }" + "\r\n");
		
		return output.toString();
	}

	//1. Prepare .dat definition file position: 
	 // val uiName_userDataFile = System.getProperty("userData", "./AHold_Job_Info/UserNameESS_20Manager.dat");
	//val uiName_userData = csv(uiName_userDataFile).circular;
	
	
	//2. Prepare Each UsrCase Definition  with   feed .feed(uiName_userData)
	/**
	 * val AHoldCase = scenario("AHold_Job_Info") .feed(userData)
	 * .feed(sessionID). asLongAs(session => (System.currentTimeMillis -
	 * startTime) < 3600) { exec(session => (session.set("baseUrl", BASE_URL)))
	 * .exec(session => (session.set("currentDate", currentDate))) .exec(session
	 * => (session.set("currentTimeStampinMs", System.currentTimeMillis())))
	 * .exec(session => (session.set("sfHostName", hostAddress)))
	 * .group("AHold_Job_Info") {
	 * exec(AHold_Job_Info.ECT_MSS_BP01_JobInfo_01_EnterURL)
	 */

	// 3. Prepare Scenario: 
	//Inject AHoldCase.inject(rampUsers(2) over (10  seconds)).protocols(httpProtocol)
	private Map prepareAllStrings(LRScenario lrScenario, Map folderTransactionsMap, Map paramsMap) {
		Map<String, String> allStrs = new HashMap<String, String>();
		
		StringBuilder strDataDef = new StringBuilder();
		StringBuilder strUsrScenarioDef = new StringBuilder();
		StringBuilder strInjectDef = new StringBuilder();
		
		strInjectDef.append("            	 setUp(\r\n");
		
		Map usrCaseMap = lrScenario.getUsrCaseMap();
		Iterator iter = usrCaseMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, LRUsrCase> entry = (Map.Entry<String, LRUsrCase>) iter.next();
			String uiName = entry.getKey();
			LRUsrCase lrUsrCase = entry.getValue();
			String lrFullPath = lrUsrCase.getUsrFileAbsPath();
			
			List<String> transactions = (List<String>) folderTransactionsMap.get(lrFullPath);
		   logger.debug(" paramsMap get " + paramsMap);
		   logger.debug(" lrFullPath " + lrFullPath);
			Map paramsAttr = (Map)paramsMap.get(lrFullPath);
			
			if (paramsAttr==null) {
				logger.error("Not able to find corresponding paramInfo for " + lrFullPath);
				continue;
			}
			
			//usr Scenario definition
			String sceName = uiName+"_sce";
			strUsrScenarioDef.append("    val ").append(sceName).append("=scenario(\"").append(sceName).append("\");\r\n");		
			
			//dataDef string builder
			Iterator iterP = paramsAttr.entrySet().iterator();
			while (iterP.hasNext()) {
				Map.Entry<String, Map> parEntry = (Map.Entry<String, Map>) iterP.next();
				String paramName = parEntry.getKey();
				Map parAttr = parEntry.getValue();
				//No need to iterate this time..
				String datPath = (String)parAttr.get("newDataPath");
				
				String paramVal = uiName + "_" + paramName;
				strDataDef.append("    val ").append(paramVal).append("File = System.getProperty(\"");
				strDataDef.append(paramVal).append("\", \"").append(datPath).append("\");");
				strDataDef.append("\r\n");
				strDataDef.append("    val ").append(paramVal).append("= csv(").append(paramVal).append("File).circular;\r\n");
				
				//Feed all params   ====== .feed(userData)
				strUsrScenarioDef.append("                    .feed(").append(paramVal).append(")\r\n");
			}
		
			strUsrScenarioDef.append("            asLongAs(session => (System.currentTimeMillis - startTime) < 3600) {  \r\n");
			strUsrScenarioDef.append("         exec(session => (session.set(\"baseUrl\", BASE_URL))) \r\n");
			strUsrScenarioDef.append("         .exec(session => (session.set(\"currentDate\", currentDate))) \r\n");
			strUsrScenarioDef.append("        .exec(session => (session.set(\"currentTimeStampinMs\", System.currentTimeMillis()))) \r\n");
			strUsrScenarioDef.append("        .exec(session => (session.set(\"sfHostName\", hostAddress)))\r\n");			
			strUsrScenarioDef.append("            .group(\"").append(sceName).append("\") {  \r\n");
			
			//Loop over the transaction list and append them. 
			for(String transStr : transactions) {
				strUsrScenarioDef.append("                  exec(").append(uiName).append(".").append(transStr).append(")\r\n");
				strUsrScenarioDef.append("        .pause(3 seconds).\r\n");
			}
			strUsrScenarioDef.append("                     }\r\n");
			strUsrScenarioDef.append("                   .exitHereIfFailed;\r\n");
			strUsrScenarioDef.append("	 };\r\n");
			strUsrScenarioDef.append("\r\n");
			
			
			strInjectDef.append("		       ").append(sceName).append(".inject(rampUsers(10) over (10 seconds)).protocols(httpProtocol);\r\n");
		}
		
		strInjectDef.append("   );\r\n");
		
		allStrs.put("dataDef", strDataDef.toString());
		allStrs.put("usrScenDef", strUsrScenarioDef.toString());
		allStrs.put("injectDef", strInjectDef.toString());
		
		return allStrs;
	}

	/**
	 * 
	 * @param lrUsrCase
	 * @param folderTransactionsMap
	 * @param paramsMap
	 * @return
	 */
	private String generateCaseDefString(LRUsrCase lrUsrCase, Map folderTransactionsMap, Map paramsMap) {
		StringBuilder transDef = new StringBuilder();

		return transDef.toString();
	}

	/**
	 * Iterate the parameters Map and prepare corresponding data files in output
	 * folder
	 * 
	 * @param paramsMap
	 * @param lrBaseFolder
	 * @param gatlingOutputFolder
	 */
	private void prepareDataFiles(Map paramsMap, String lrBaseFolder, String gatlingOutputFolder) {
		Iterator iter = paramsMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Map> entry = (Map.Entry<String, Map>) iter.next();
			String paramFilePath = entry.getKey();
			Map params = (Map) entry.getValue();
			logger.debug("Param File Path:" + paramFilePath);
			Iterator iterParam = params.entrySet().iterator();

			while (iterParam.hasNext()) {
				Map.Entry<String, Map> entryParam = (Map.Entry<String, Map>) iterParam.next();
				String param = entryParam.getKey();
				logger.debug("Mapped Param :" + param);
				Map eachParamMap = (Map) entryParam.getValue();
				String tableStr = (String) eachParamMap.get("Table");
				String columnName = (String) eachParamMap.get("ColumnName");
				String paramName = (String) eachParamMap.get("ParamName");
				logger.debug("param currently processed:" + paramName + " column Name:" + columnName);
				if (columnName == null || tableStr == null) {
					logger.error(
							"By pass param  " + paramFilePath + " data File " + tableStr + " param Name " + paramName);
					continue;
				}
				// Original dataFile full path
				String dataFile = Utils.getDataFileFullPath(paramFilePath, tableStr);
				String destFile = getDestFilePath(paramName, tableStr, lrBaseFolder, gatlingOutputFolder,
						paramFilePath);

				logger.debug("prepare path  source: " + dataFile);
				logger.debug("prepare path      dest:" + destFile);

				// Take this opportunity to update the Map's relative path new
				// copied destFile path to be used in generating files.
				String outBaseFolder = gatlingOutputFolder + "resources" + File.separator;
				String newDatRelativePath = Utils.getRelativePath(outBaseFolder, destFile);
				eachParamMap.put("newDataPath", newDatRelativePath);
				logger.debug("Put new data path to map:  outBaseFolder " + outBaseFolder + " new:"
						+ eachParamMap.get("newDataPath"));

				// Copy data file to corresponding folder
				// If it is URL related, we don't need it.
				if (paramName.toUpperCase().indexOf("URL") > 0) {
					logger.error("By pass param with URL " + paramFilePath + " data File " + tableStr + " param Name "
							+ paramName);
					continue;
				}
				if (columnName.equalsIgnoreCase(paramName) || columnName.startsWith("Col")) {
					logger.debug("Will do copy for " + paramName);
					Utils.copyFileToDest(dataFile, destFile);
				} else {
					logger.debug("Will do create for " + paramName);
					// Filter the selected column and create data file.
					createDataFile(dataFile, destFile, columnName, paramName);
				}
			}
		}
	}

	/**
	 * 
	 * @param paramName
	 * @param tableStr
	 * @param lrBaseFolder
	 * @param gatlingOutputFolder
	 * @param paramFilePath
	 */
	private String getDestFilePath(String paramName, String tableStr, String lrBaseFolder,
			String gatlingOutputFolder, String paramFilePath) {
		// destination path
		String destPath = gatlingOutputFolder + "resources" + File.separator + "data" + File.separator;
		// Find out the relative path of the dest folder.
		String firstHalf = lrBaseFolder + "Scripts" + File.separator;
		String relativePath = Utils.getRelativePath(firstHalf, Utils.cutFileName(paramFilePath));

		destPath = destPath + relativePath;
		String destFile = destPath + File.separator + paramName + ".dat";

		return destFile;
	}

	/**
	 * 
	 * @param dataFile
	 * @param destFile
	 * @param columnName
	 * @param paramName
	 */
	private void createDataFile(String dataFile, String destFile, String columnName, String paramName) {
		List<String> filteredVallues = Utils.filterCSVColumn(dataFile, columnName);
		Utils.writeToCSV(filteredVallues, paramName, destFile);
	}

	private String getScalaName(String userFile) {
		String[] strArray = userFile.split("\\" + File.separator);
		if (strArray.length > 2) {
			String scalaName = strArray[strArray.length - 2];
			return scalaName;
		}
		return "";
	}

	public String convertToScala(String userFile, Collection actionFiles, String outputPath) throws Exception {
		// String actionPath = lrFile.getAbsolutePath();
		LRAction lrAction = new LRAction();
		for (Object objVal : actionFiles) {
			String actionFileFullPath = Utils.getActionFullPath(userFile, (String) objVal);
			System.out.println("Action File Full Path " + actionFileFullPath);
			LRActionParser lrParser = new LRActionParser(actionFileFullPath);
			LRAction lrActionNew = lrParser.parseActionFile();
			System.out.println("Parsed Transactions " + lrActionNew.getLrTransctions().size());
			lrAction.combine(lrActionNew);
		}

		String samePath = userFile.substring(0, userFile.lastIndexOf(File.separator));
		String scalaName = getScalaName(userFile);

		GTTransactions gtTransactions = new GTTransactions(scalaName, "com.sap", lrAction);
		String output = gtTransactions.generateStringForOutput();

		ObjectMapper mapper = new ObjectMapper();
		// Convert object to JSON string and save into a file directly
		mapper.writeValue(new File(samePath + File.separator + scalaName + ".json"), lrAction);

		return output;
	}

	/**
	 * 
	 * @return
	 */
	private Map collectParamsMap() {
		Map paramsMap = new HashMap();
		try {
			for (File cFile : fileList) {
				logger.debug("Going to read user File to collectParams " + cFile.getAbsolutePath());
				String prmFileName = cFile.getAbsolutePath().replaceAll("\\.usr", "\\.prm");
				logger.debug("Would handle prm File " + prmFileName);
				File prmFile = new File(prmFileName);
				if (!prmFile.exists()) {
					logger.error("Prm file " + prmFileName + " not exist. Please find it according to usr file ");
					continue;
				}

				BufferedReader reader = new BufferedReader(new FileReader(prmFile));
				String tempString = null;
				boolean isParamDefStart = false;
				Map newParamMapFile = null;
				String tempParamName = null;

				Map newParamMapEach = null;
				while ((tempString = reader.readLine()) != null) {
					// If it is the parameter tag starts.
					if (tempString.trim().startsWith("[parameter:")) {
						// It is not the first time of the [Parameterxxx] tag,
						// but also need to new a paramMapEach HashMap.
						if (newParamMapFile != null) {
							newParamMapFile.put(tempParamName, newParamMapEach);
							newParamMapEach = new HashMap();
						} else { // It is the first time to start it from File
									// level.
							newParamMapFile = new HashMap();
							newParamMapEach = new HashMap();
						}
						logger.debug("Read line of parameter " + tempString.trim());
						List<String> paramNameList = Utils.captureMatchedStringList(tempString.trim(),
								"\\[parameter:(.+?)\\]", 1);
						tempParamName = paramNameList.get(0);
					} else {
						String[] lineArray = tempString.split("=");
						if (lineArray.length == 2) {
							String valueStr = lineArray[1].substring(1, lineArray[1].length() - 1);
							newParamMapEach.put(lineArray[0], valueStr);
						}
					}
					newParamMapFile.put(tempParamName, newParamMapEach);
				}
				paramsMap.put(cFile.getAbsolutePath(), newParamMapFile);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return paramsMap;
	}

	// Parse the user file and get a collection of Action Files for each folder.
	private Map collectActionFiles() {
		Map actionMap = new HashMap();
		for (File cFile : fileList) {
			logger.debug("Going to read user File " + cFile);
			Collection actionFiles = readFileByLines(cFile);
			actionMap.put(cFile.getAbsolutePath(), actionFiles);
		}
		return actionMap;
	}

	public Collection readFileByLines(File file) {
		BufferedReader reader = null;
		Collection actionFileList = new ArrayList();
		Collection transactionsList = new ArrayList();

		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			boolean isActionFileContent = false;
			boolean isTransactionContent = false;
			while ((tempString = reader.readLine()) != null) {
				if (tempString.trim().equalsIgnoreCase("[Actions]")) {
					isActionFileContent = true;
				} else if (tempString.trim().matches("\\[.+\\]")) {
					isActionFileContent = false;
				} else if (isActionFileContent) {
					String[] lineArray = tempString.split("=");
					if (lineArray.length == 2) {
						actionFileList.add(lineArray[1]);
					}
				}

				if (tempString.trim().equalsIgnoreCase("[Transactions]")) {
					isTransactionContent = true;
				} else if (tempString.trim().matches("\\[.+\\]")) {
					// Until it meets next [ ] for next section.
					isTransactionContent = false;
				} else if (isTransactionContent) {
					logger.debug("Going to put item to transaction List:" + tempString);
					if (tempString.indexOf("=") > 1) {
						transactionsList.add(tempString.substring(0, tempString.indexOf("=")));
					}
				}
				line++;
			}
			logger.debug("Going to put transaction List to map ");
			folderTransactionsMap.put(file.getAbsolutePath(), transactionsList);
			reader.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			return actionFileList;
		}
	}

	/**
	 * 
	 * @param path
	 * @param suffix
	 */
	public void walkThrough(LRScenario lrScenario, String scenarioFilePath) {
		Map userCaseMap = lrScenario.getUsrCaseMap();
		Iterator usrCase = userCaseMap.entrySet().iterator();

		while (usrCase.hasNext()) {
			Map.Entry<String, LRUsrCase> entryParam = (Map.Entry<String, LRUsrCase>) usrCase.next();
			String uiName = entryParam.getKey();
			LRUsrCase lrUsrCase = entryParam.getValue();
			String usrFullPath = Utils.getDataFileFullPath(scenarioFilePath, lrUsrCase.getUsrFilePath());
			fileList.add(new File(usrFullPath));
		}
	}

	/**
	 * Read scenario file (.xxx/xxx/scenario.lrs) then parse necessary
	 * information into LRScenario this object
	 * 
	 * @param scenarioFilePath
	 * @return
	 */
	private LRScenario parseLRScenarioFile(String scenarioFilePath) {
		LRScenario lrScenario = new LRScenario();

		lrScenario.setName(Utils.getFileNameFromFullPath(scenarioFilePath));
		BufferedReader reader = null;
		try {
			File scenarioFile = new File(scenarioFilePath);
			reader = new BufferedReader(new FileReader(scenarioFile));
			String tempString = null;

			logger.debug("===Start to parse Scenario File " + scenarioFilePath);

			boolean isTestChief = false;
			boolean isTestChiefSection = false;

			boolean isScenarioGroupsData = false;
			boolean isScenarioGroupsSection = false;

			LRUsrCase lrUsrCase = null;
			String uiName = null;
			int rowCount = 0;
			while ((tempString = reader.readLine()) != null) {
				// {TestChief
				// {bp03_future_account_balances_1_1
				// UiName=bp03_future_account_balances_1_1
				// Path=..\Scripts\EC\BP03_Future_Account_Balances\BP03_Future_Account_Balances.usr
				rowCount++;
				if (tempString.trim().startsWith("{TestChief")) {
					isTestChief = true;
					logger.debug("===Start to parse TestChief ====");
					// Find first {, then it is the start of a new section.
				} else if (isTestChief && tempString.trim().startsWith("{")) {
					isTestChiefSection = true;
					lrUsrCase = new LRUsrCase();
				} else if (isTestChief && isTestChiefSection && tempString.trim().startsWith("Path=")) {
					// Get the path info
					String path = tempString.trim().substring(5, tempString.trim().length());
					logger.debug("==Parse TestChief got path " + path);
					if (lrUsrCase != null) {
						lrUsrCase.setUsrFilePath(path);
						lrUsrCase.setUsrFileAbsPath(Utils.getDataFileFullPath(scenarioFilePath, path));
					}
				} else if (isTestChief && isTestChiefSection && tempString.trim().startsWith("UiName=")) {
					// Get the UniName info
					uiName = tempString.trim().substring(7, tempString.trim().length());
					if (lrUsrCase != null) {
						lrUsrCase.setUiName(uiName);
					}
				} else if (isTestChief && isTestChiefSection && tempString.trim().startsWith("}")) {
					// First } then close this TestChief section.
					lrScenario.addUsrCaseMap(uiName, lrUsrCase);
					isTestChiefSection = false;
				} else if (isTestChief && !isTestChiefSection && tempString.trim().startsWith("}")) {
					// second }, would close the TestChief.
					isTestChief = false;
					lrUsrCase = null;
				}

				// {ScenarioGroupsData
				// SCHED_GROUP_NAME=admin_viewuserpermission.1_1_1
				// TEST_PERCENT_DISTRIBUTION=0.28
				if (tempString.trim().startsWith("{ScenarioGroupsData")) {
					isScenarioGroupsData = true;
					logger.debug("===Start to parse ScenarioGroupsData ====");
					// Find first {, then it is the start of a new section.
				} else if (isScenarioGroupsData && tempString.trim().startsWith("{")) {
					isScenarioGroupsSection = true;
					lrUsrCase = null;
				} else if (isScenarioGroupsData && isScenarioGroupsSection
						&& tempString.trim().startsWith("TEST_PERCENT_DISTRIBUTION=")) {
					// Get the path info
					String percent = tempString.trim().substring(26, tempString.trim().length());
					logger.debug("Meet TEST_PERCENT_DISTRIBUTION " + percent + " from GroupName " + uiName);
					if (lrUsrCase != null) {
						lrUsrCase.setPerNumUsers(Float.parseFloat(percent));
						logger.debug("Set Percentage Distribution " + percent + " for " + uiName);
					}
				} else if (isScenarioGroupsData && isScenarioGroupsSection
						&& tempString.trim().startsWith("SCHED_GROUP_NAME=")) {
					// Get the UniName info
					uiName = tempString.trim().substring(17, tempString.trim().length());
					lrUsrCase = lrScenario.getLRUsrCase(uiName);
					logger.debug("Meet UiName " + uiName + " found corresponding LRUsrCase " + lrUsrCase.toString());
				} else if (isScenarioGroupsData && isScenarioGroupsSection && tempString.trim().startsWith("}")) {
					// First } then close this TestChief section.
					isScenarioGroupsSection = false;
				} else if (isScenarioGroupsData && !isScenarioGroupsSection && tempString.trim().startsWith("}")) {
					// second }, would close the TestChief.
					isScenarioGroupsData = false;
				}
			}
			logger.debug("==Read File RowCount " + rowCount);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lrScenario;
	}
}