package com.amazon.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
	WebDriver driver;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.ID, using = "hlb-ptc-btn-native")
	private WebElement btn_ContinueToCheckout;

	public void clickOn_ContinueToCheckout() {
		btn_ContinueToCheckout.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
	}

}