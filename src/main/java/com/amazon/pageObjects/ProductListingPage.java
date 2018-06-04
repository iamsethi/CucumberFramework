package com.amazon.pageObjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ProductListingPage {
	WebDriver driver;

	public ProductListingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.NAME, using = "submit.add-to-cart")
	private WebElement btn_AddToCart;

	@FindAll(@FindBy(how = How.XPATH, using = "//h2"))
	private List<WebElement> prd_List;

	public void clickOn_AddToCart() {
		btn_AddToCart.click();
	}

	public void select_Product(int productNumber) {
		prd_List.get(productNumber).click();
	}

}