package com.sap.perf.loadrunner.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.sap.micro.service.ui5.scenario.SystemConfig;
import com.sap.perf.loadrunner.gatling.LRToGatlingConverter;
import com.sap.perf.loadrunner.gatling.Utils;

public class TestUtilsMain {

	private static Logger logger = LogManager.getLogger(TestUtilsMain.class);

	public static void main(String[] args) throws Exception {
		System.out.println("This is the start of test utils main program");

		String absPath = Utils.getDataFileFullPath(
				"C:\\github\\wdf\\PreRelease_Scripts_refresh\\Scripts\\EC\\AHold_Emergency_Contact\\ec.prm",
				"..\\..\\Data\\CompanyIDs.dat");

		List<String> companyArray = Utils.filterCSVColumn(absPath, "AHOLD");
		for (String company : companyArray) {
			System.out.println("Company got:" + company);
		}

		// Utils.createFolder("C:\\github\\wdf\\PreRelease_Scripts_refresh\\gatling\\scala\\transactions");
		// Utils.writeToCSV(companyArray, "companyId",
		// "C:\\github\\wdf\\PreRelease_Scripts_refresh\\gatling\\scala\\transactions\\companyID.dat");
		//lrConvertToGatling("PreReleaseLoad");
		System.out.println(" Get name " + Utils.extractFileNameOnly("Preload.zip"));
		System.out.println(" Get name " + Utils.extractFileNameOnly("Preload"));
		callHttpRestPost();
	}
	
	public static void callHttpRestPost() {
		String targetURL = "http://10.169.77.97:8080/job/TestMavenSimple/build?token=LpJHnhUhlkksek";
	      try {
	            URL targetUrl = new URL(targetURL);

	            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
	            httpConnection.setDoOutput(true);
	            httpConnection.setRequestMethod("POST");
	            httpConnection.setRequestProperty("Content-Type", "application/json");

	           String input = "{\"id\":1,\"no\":\"no0003\",\"name\":\"jack\"}";

	            OutputStream outputStream = httpConnection.getOutputStream();
	            //outputStream.write(input.getBytes());
	            outputStream.flush();

	            if (httpConnection.getResponseCode() != 200) {
	                throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
	            }

	            BufferedReader responseBuffer = new BufferedReader(
	                    new InputStreamReader((httpConnection.getInputStream())));

	            String output;
	            System.out.println("Output from Server:\n");
	            while ((output = responseBuffer.readLine()) != null) {
	                System.out.println(output);
	            }

	            httpConnection.disconnect();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	

	public static String lrConvertToGatling(String zipFileName) {
		try {
			logger.info("Start to convert to Gatling background process asynchronously.");
			// 1.Unzip the zip file
			String fileLocation = SystemConfig.getProperty("file.upload-dir");
			logger.info("zip File Location " + fileLocation + " zipFile " + zipFileName);
			if (fileLocation == null) {
				fileLocation = "C:" + File.separator + "temp" + File.separator;
			}
			String lrDest = fileLocation + "lr" + File.separator;
			Utils.unzipFile(fileLocation + zipFileName + ".zip", lrDest);

			// Get usr file path
			Collection<File> listFiles = FileUtils.listFiles(
					new File(lrDest + zipFileName + File.separator + "Scenarios"),
					FileFilterUtils.suffixFileFilter(".lrs"), null);
			String scenarioFileName = null;
			for (File file : listFiles) {
				System.out.println(file.getName());
				scenarioFileName = file.getAbsolutePath();
			}

			// 2.Convert to Gatling
			LRToGatlingConverter lrToGatlingConverter = new LRToGatlingConverter();
			logger.info("Base folder " + fileLocation + zipFileName);
			logger.info("scenarioFileName " + scenarioFileName);
			String outputFolder = fileLocation + "gatling" + File.separator + zipFileName + File.separator;
			FileUtils.forceMkdir(new File(outputFolder));
			lrToGatlingConverter.fromLRtoGatling(lrDest + zipFileName + File.separator, scenarioFileName, outputFolder);

			// 3.Upload the gatling files to github
			GitHub github = GitHub.connectUsingPassword("shuyin_80@126.com", "P@ssword1");
			String orgName = "patricwan";
			GHRepository repo = null;
			try {
				repo = github.getRepository(orgName + "/" + zipFileName);
				logger.info("Try to get Repo obtained " + repo);
				repo.delete();
				logger.info("Remove it " + repo);
			} catch (Exception e) {
				logger.info(" Get repository failed " + e.getMessage());
			}
			Utils.createRepository(github, zipFileName);
			repo = github.getRepository(orgName + "/" + zipFileName);
			
			repo.createContent("<xml>content</xml>", "Pom Build File ", "pom.xml");
			Utils.pushFiles(new File(outputFolder), "Auto generated Gatling scala source code", "patricwan",
					github, repo, "master");
			
		} catch (Exception e) {
			logger.error("Error happend " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

}
