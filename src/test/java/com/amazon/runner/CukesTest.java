package com.amazon.runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.amazon.dataProviders.JsonDataReader;
import com.amazon.managers.FileReaderManager;
import com.amazon.managers.WebDriverManager;
import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * A sample test to demonstrate
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/regressionTests/", tags = { "@regression" }, glue = {
		"com.amazon.stepDefinitions" }, plugin = {
				"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" }, monochrome = true)

public class CukesTest {

	@BeforeClass
	public static void setUp() throws Exception {
		JsonDataReader.registerEnvironment("ITV1");
		JsonDataReader.initializeJSON();
		WebDriverManager.getInstance().setDriver();
	}

	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(new File(FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
	}
}