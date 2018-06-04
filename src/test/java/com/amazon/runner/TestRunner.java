package com.amazon.runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.amazon.managers.FileReaderManager;
import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * A sample test to demonstrate
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/functionalTests", glue = { "com.amazon.stepDefinitions" }, plugin = {
		"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" }, monochrome = true)

public class TestRunner {
	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(new File(FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
	}
}