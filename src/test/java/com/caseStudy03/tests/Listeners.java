package com.caseStudy03.tests;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.caseStudy03.utils.ExtentReportClass;

public class Listeners implements ITestListener{
	ExtentTest test;
	ExtentReports extent = ExtentReportClass.getExtentReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	public void onTestSuccess(ITestResult result) {
		String testMethodName = result.getMethod().getMethodName();
		extentTest.get().log(Status.PASS, testMethodName);
	}

	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable());
		String testMethodName = result.getMethod().getMethodName();
		System.out.println("Failed :"+testMethodName);
		extentTest.get().log(Status.FAIL, testMethodName);
	}

	public void onTestSkipped(ITestResult result) {
		String testMethodName = result.getMethod().getMethodName();
		extentTest.get().log(Status.SKIP, testMethodName);
	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}

}
