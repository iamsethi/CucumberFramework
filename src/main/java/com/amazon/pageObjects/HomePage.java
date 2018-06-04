package com.amazon.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.amazon.managers.FileReaderManager;

public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void perform_Search(String search) {
		driver.navigate().to(FileReaderManager.getInstance().getConfigReader().getApplicationUrl() + "/?s=" + search
				+ "&post_type=product");
	}

	public void navigateTo_HomePage() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	}

}