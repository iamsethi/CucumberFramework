package com.amazon.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ProductListingPage extends BasePage{
	WebDriver driver;

	public ProductListingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.NAME, using = "submit.add-to-cart")
	private WebElement btn_AddToCart;

	@FindBy(how = How.XPATH, using = "(//a[@class='a-link-normal a-text-normal'])[2]")
	private WebElement prd_List;

	public void clickOn_AddToCart() {
		btn_AddToCart.click();
	}

	public void select_Product(int productNumber) {
		prd_List.click();
	}

}