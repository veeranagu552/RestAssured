package com.caseStudy03.utils;

import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportClass {
	static ExtentReports extent;

	public static ExtentReports getExtentReportObject() {
		Date today = new Date();
		@SuppressWarnings("deprecation")
		String path = "Reports/caseStudy03Report" + today.getDate() + "" + today.getMonth() + "" + today.getYear() + ""
				+ today.getHours() + today.getMinutes() + ".html";
		System.out.println(path);
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Case Study 03 Report");
		reporter.config().setDocumentTitle("Case Study 03");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Testing Engineer", "Veernag");
		return extent;
	}
}
