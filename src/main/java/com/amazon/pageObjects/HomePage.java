package com.amazon.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.amazon.managers.FileReaderManager;

public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.NAME, using = "field-keywords")
	private WebElement txtbx_Srch;

	@FindBy(how = How.XPATH, using = "//input[@type='submit']")
	private WebElement txtbx_Go;

	public void perform_Search(String search) {
		txtbx_Srch.sendKeys(search);
		txtbx_Go.click();
	}

	public void navigateTo_HomePage() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	}

}