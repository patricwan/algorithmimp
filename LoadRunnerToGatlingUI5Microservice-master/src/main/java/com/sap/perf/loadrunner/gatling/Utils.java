package com.sap.perf.loadrunner.gatling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRef;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTree;
import org.kohsuke.github.GHTreeBuilder;
import org.kohsuke.github.GitHub;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Utils {

	public static Logger logger = LogManager.getLogger(Utils.class);

	// From file full path without suffix
	// C:\github\wdf\PreRelease_Scripts_refresh-master\Scripts\EC\AHold_Job_Info\login.c,
	// get the file name login.
	public static String getFileNameFromFullPath(String fileFullPath) {
		if (fileFullPath == null && fileFullPath.length() < 1) {
			return "";
		}
		int lastBackSlash = fileFullPath.lastIndexOf(File.separator);
		int lastDotSep = fileFullPath.lastIndexOf(".");
		if (lastBackSlash < lastDotSep) {
			return fileFullPath.substring(lastBackSlash + 1, lastDotSep);
		} else {
			return "";
		}

	}

	// Action file string as input, but we want to trim the header part and
	// footer part to make it more clean.
	public static String trimHeaderAndFooter(String actionFileStr, String actionName) {
		// trim " Action(){ " this part in the header. \\s* matches space or
		// none; other \\ means escape
		String headerTextPattern = actionName + "\\(\\s*\\)\\s*\\{\\s*";
		actionFileStr = actionFileStr.replaceAll(headerTextPattern, "");

		// trim " return 0; } " this part in the footer. Added many \\s* means
		// spaces could be everywhere.
		String footerTextPattern = "\\s*return\\s*\\d{1}\\s*;\\s*\\}\\s*";
		actionFileStr = actionFileStr.replaceAll(footerTextPattern, "");
		return actionFileStr;
	}

	/***
	 * Trim the block comments like /****** Comments Here \\* is to escape *
	 * (.+?) means a non-greedy way to match the string
	 * 
	 */
	public static String trimBlockContents(String actionFileStr) {
		String commentBlockPattern = "/\\*(.+?)\\*/";
		actionFileStr = actionFileStr.replaceAll(commentBlockPattern, "");

		return actionFileStr;
	}

	/***
	 * Capture matched string list from a search string. For groupNum, -1 means
	 * no group just the whole matching, 1 means getting 1st
	 * 
	 * @param searchStr
	 * @param regExp
	 * @param groupNum
	 * @return
	 */
	public static List<String> captureMatchedStringList(String searchStr, String regExp, int groupNum) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(searchStr);
		List<String> strList = new ArrayList<String>();

		while (matcher.find()) {
			if (groupNum > 0) {
				strList.add(matcher.group(groupNum));
			} else {
				strList.add(matcher.group());
			}
		}

		return strList;
	}

	/**
	 * Convert from a relative path ../../../company.bat to absolute path
	 * C:/documents/disk/Scripts/data/company.bat
	 * 
	 * @param fileFullPath
	 * @return
	 */
	public static String getDataFileFullPath(String prmFilePath, String dataPath) {
		if (dataPath == null || prmFilePath == null) {
			return "";
		}
		// If it is absolute path like C: D:
		if (dataPath.indexOf(":") > 0) {
			return dataPath;
		}
		// Convert relative path to absolute path.
		Path basePath = FileSystems.getDefault().getPath(prmFilePath);
		// use getParent() if basePath is a file (not a directory)
		Path resolvedPath = basePath.getParent().resolve(dataPath);
		Path abolutePath = resolvedPath.normalize();
		// String path = resolvedPath.toString();

		return abolutePath.toString();
	}

	/**
	 * 
	 * @param pathFull
	 * @param pathBase
	 * @return
	 */
	public static String getRelativePath(String pathFull, String pathBase) {
		Path full = FileSystems.getDefault().getPath(pathFull);
		Path base = FileSystems.getDefault().getPath(pathBase);
		return full.relativize(base).toString();
	}

	/**
	 * Find a column with specific name from csv
	 * 
	 * @param csvPath
	 * @param columnName
	 * @return
	 */
	public static List<String> filterCSVColumn(String csvPath, String columnName) {
		List<String> filteredStringList = new ArrayList<String>();
		try {
			Reader reader = Files.newBufferedReader(Paths.get(csvPath));
			List<CSVRecord> list = new CSVParser(reader, CSVFormat.DEFAULT).getRecords();
			CSVRecord firstLineCSV = list.get(0);
			// Found the match value from the first line of record, namely the
			// header line.
			int columnIndex = -1;
			for (int i = 0; i < firstLineCSV.size(); i++) {
				String value = firstLineCSV.get(i);
				if (columnName.equalsIgnoreCase(value)) {
					columnIndex = i;
					break;
				}
			}
			// Find the matched columnIndex of other rows and add to list.
			for (int j = 1; j < list.size(); j++) {
				CSVRecord otherLineCSV = list.get(j);
				if (otherLineCSV == null) {
					continue;
				}
				if (otherLineCSV.size() < columnIndex || columnIndex < 0) {
					/// ToDO:
					logger.error(" columnIndex " + columnIndex);
					continue;
				}

				logger.info(" columnIndex " + columnIndex);
				logger.info(" otherLineCSV.size()  " + otherLineCSV.size());
				logger.info(" otherLineCSV  " + otherLineCSV);
				String newValue = otherLineCSV.get(columnIndex);
				filteredStringList.add(newValue);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return filteredStringList;
	}

	/**
	 * Write to csv This method is not so generic as we know that usually we
	 * will use it for companyId csv writing, only one column is needed.
	 * 
	 * @param valueList
	 * @param header
	 * @param path
	 * @throws Exception
	 */
	public static void writeToCSV(List<String> valueList, String header, String filePath) {
		CSVFormat formator = CSVFormat.DEFAULT;
		FileWriter fileWriter = null;
		CSVPrinter printer = null;
		try {
			fileWriter = new FileWriter(filePath);
			printer = new CSVPrinter(fileWriter, formator);
			printer.printRecord(header);
			if (valueList != null) {
				for (String lineData : valueList) {
					printer.printRecord(lineData);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.close();
				}
				if (printer != null) {
					printer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Copy the csv or dat to destination path, may change the name.
	 * 
	 * @param sourcePath
	 * @param destPath
	 */
	public static void copyFileToDest(String sourcePath, String destPath) {
		try {
			FileUtils.copyFile(new File(sourcePath), new File(destPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * If the folder not exist, would create it. It might be a few folders
	 * 
	 * @param folder
	 */
	public static void createFolder(String folder) {
		try {
			Files.createDirectories(Paths.get(folder));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * If fullPath provided, cut the last file name, only keep the path.
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String cutFileName(String fullPath) {
		return Paths.get(fullPath).getParent().toString();
	}
	
	/**
	 * If fullPath provided, cut the last file name, only keep the path.
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String extractFileNameOnly(String fileFullName) {
		if (fileFullName.lastIndexOf(".") > 0)  {
			return fileFullName.substring(0, fileFullName.lastIndexOf("."));
		} else {
			return fileFullName;
		}
	}

	/**
	 * 
	 * @param userFile
	 * @param actionFileName
	 * @return
	 */
	public static String getActionFullPath(String userFile, String actionFileName) {
		return userFile.substring(0, userFile.lastIndexOf(File.separator)) + File.separator + actionFileName;
	}

	/**
	 * Get full name of a file, no suffix.
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String getFilePureNameNoSuffix(String fullPath) {
		if (fullPath == null) {
			return "";
		}
		int dotPos = fullPath.lastIndexOf(".");
		int lastSepPos = fullPath.lastIndexOf("\\");
		if (dotPos > 0 && lastSepPos > 0) {
			return fullPath.substring(lastSepPos + 1, dotPos);
		}
		return "";
	}

	/**
	 * 
	 * @param fileContent
	 * @param filePath
	 */
	public static void writeStringToFile(String fileContent, String filePath) {
		// If file directory not exist, create it.
		String folder = cutFileName(filePath);
		File folderFile = new File(folder);
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filePath, false));
			writer.write(fileContent);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Unzip file quick method
	 * 
	 * @param fileLocation
	 * @param targetFolder
	 * @return
	 */
	public static boolean unzipFile(String fileLocation, String targetFolder) {
		try {
			ZipFile zipFile = new ZipFile(fileLocation);
			if (zipFile.isValidZipFile()) {
				logger.info("Valid Zip File " + zipFile.getFile().getAbsolutePath());
				logger.info("Will extract to " + targetFolder);
				zipFile.extractAll(targetFolder);
				return true;
			} else {
				logger.error("Invalid zip file " + zipFile.getFile().getAbsolutePath());
				return false;
			}
		} catch (ZipException e) {
			logger.error("Unzip got errors " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	//Below github operations
	
	

	public static void createRepository(GitHub github, String newRepoName) throws Exception {
		GHCreateRepositoryBuilder builder = github.createRepository(newRepoName);
		builder.private_(false).homepage("").issues(false).downloads(false).wiki(false);
		builder.create();
	}

	static public String pushFiles(File baseDirectory, String message, String orgName, GitHub github, GHRepository repo,
			String branchName) throws IOException {
		// get current branch
		GHRef ref = repo.getRef("heads/" + branchName);
		GHCommit latestCommit = repo.getCommit(ref.getObject().getSha());
		GHTreeBuilder treeBuilder = repo.createTree().baseTree(latestCommit.getTree().getSha());
		addFilesToTree(treeBuilder, baseDirectory, baseDirectory);
		GHTree tree = treeBuilder.create();
		GHCommit commit = repo.createCommit().parent(latestCommit.getSHA1()).tree(tree.getSha()).message(message)
				.create();
		ref.updateTo(commit.getSHA1());

		System.out.println("Commit created with on branch " + branchName + " and SHA " + commit.getSHA1() + " and URL "
				+ commit.getHtmlUrl());
		return commit.getSHA1();
	}

	/**
	 * 
	 * @param treeBuilder
	 * @param baseDirectory
	 * @param currentDirectory
	 * @throws IOException
	 */
	private static void addFilesToTree(GHTreeBuilder treeBuilder, File baseDirectory, File currentDirectory)
			throws IOException {

		for (File file : currentDirectory.listFiles()) {
			String relativePath = baseDirectory.toURI().relativize(file.toURI()).getPath();
			if (file.isFile()) {
				treeBuilder.textEntry(relativePath, FileUtils.readFileToString(file), false);
			} else {
				addFilesToTree(treeBuilder, baseDirectory, file);
			}
		}
	}
}
