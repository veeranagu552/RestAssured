package com.caseStudy03.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
	private Properties properties;
	private final String propertiesFilePath = "src/main/resources/Configuration.properties";

	public ConfigFileReader() {
		File file = new File(propertiesFilePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("Config file not found");
		}
		properties = new Properties();
		try {
			properties.load(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUrl() {
		String url = properties.getProperty("url");
		if (url != null)
			return url;
		else
			throw new RuntimeException("url is not specified in properties file");
	}
	
	public String getBasePath() {
		String getBasePath = properties.getProperty("getBasePath");
		if (getBasePath != null)
			return getBasePath;
		else
			throw new RuntimeException("getBasePath is not specified in properties file");
	}
	
	public String postBasePath() {
		String postBasePath = properties.getProperty("postBasePath");
		if (postBasePath != null)
			return postBasePath;
		else
			throw new RuntimeException("postBasePath is not specified in properties file");
	}
	
	public String putBasePath() {
		String putBasePath = properties.getProperty("putBasePath");
		if (putBasePath != null)
			return putBasePath;
		else
			throw new RuntimeException("putBasePath is not specified in properties file");
	}
	
	public String deleteBasePath() {
		String deleteBasePath = properties.getProperty("deleteBasePath");
		if (deleteBasePath != null)
			return deleteBasePath;
		else
			throw new RuntimeException("deleteBasePath is not specified in properties file");
	}
	
	public String getExcepFilePath() {
		String excelFilePath = properties.getProperty("excelFilePath");
		if (excelFilePath != null)
			return excelFilePath;
		else
			throw new RuntimeException("excelFilePath is not specified in properties file");
	}
}
