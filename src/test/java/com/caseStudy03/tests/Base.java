package com.caseStudy03.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.caseStudy03.utils.ConfigFileReader;
import com.caseStudy03.utils.ExcelFileReader;

import io.restassured.RestAssured;

public class Base {
	protected String baseUrl;
	protected ConfigFileReader configReader;
	protected ExcelFileReader excelReader;
	
	@BeforeTest
	public void setup() {
		configReader = new ConfigFileReader();
		excelReader = new ExcelFileReader();
		RestAssured.baseURI=configReader.getUrl();
	}
	
	@DataProvider(name = "data")
	public Object[][] getData(){
			return ExcelFileReader.readExcel(configReader.getExcepFilePath());
	}
}
