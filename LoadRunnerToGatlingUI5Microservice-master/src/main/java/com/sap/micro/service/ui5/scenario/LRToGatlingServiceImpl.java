package com.sap.micro.service.ui5.scenario;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sap.perf.loadrunner.gatling.LRToGatlingConverter;
import com.sap.perf.loadrunner.gatling.Utils;

@Service
public class LRToGatlingServiceImpl implements LRToGatlingService {

	public static Logger logger = LogManager.getLogger(LRToGatlingServiceImpl.class);

	@Async
	@Override
	public String lrConvertToGatling(String zipFileFullName) {
		try {
			logger.info("Start to convert to Gatling background process asynchronously.");
			// Extract Zip file
			String zipFileName = Utils.extractFileNameOnly(zipFileFullName);
			// 1.Unzip the zip file
			String fileLocation = SystemConfig.getProperty("file.upload-dir");
			if (fileLocation == null) {
				fileLocation = "C:" + File.separator + "temp" + File.separator;
			}
			logger.info("zip File Location " + fileLocation + " zipFile " + zipFileName);
			String lrDest = fileLocation + "lr" + File.separator;
			Utils.unzipFile(fileLocation + zipFileName + ".zip", lrDest);
			logger.info("Unzip to " + lrDest);

			String scePath = lrDest + zipFileName + File.separator + "Scenarios";
			// Get usr file path
			Collection<File> listFiles = FileUtils.listFiles(new File(scePath),
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
			Utils.pushFiles(new File(outputFolder), "Auto generated Gatling scala source code", "patricwan", github,
					repo, "master");

			logger.info("Pushed to git correctly");

		} catch (Exception e) {
			logger.error("Error happend " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}
}
