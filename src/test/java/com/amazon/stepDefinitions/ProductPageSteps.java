package com.amazon.stepDefinitions;

import com.amazon.cucumber.TestContext;
import com.amazon.pageObjects.ProductListingPage;

import cucumber.api.java.en.When;

public class ProductPageSteps {

	TestContext testContext;
	ProductListingPage productListingPage;

	public ProductPageSteps(TestContext context) {
		testContext = context;
		productListingPage = testContext.getPageObjectManager().getProductListingPage();
	}

	@When("^choose to buy the first item$")
	public void choose_to_buy_the_first_item() {
		productListingPage.select_Product(0);
		productListingPage.clickOn_AddToCart();
	}
}