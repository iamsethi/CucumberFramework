package com.amazon.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.amazon.interfaces.ILog;
import com.amazon.managers.FileReaderManager;

public class HomePage extends BasePage{
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.NAME, using = "field-keywords")
	public WebElement txtbx_Srch;

	@FindBy(how = How.XPATH, using = "//input[@type='submit']")
	public WebElement txtbx_Go;

	public void perform_Search(String search) {
		ILog.info("#######Perform Search#######");
		txtbx_Srch.sendKeys(search);
		txtbx_Go.click();
	}

	public void navigateTo_HomePage() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	}

}