package com.amazon.stepDefinitions;

import com.amazon.cucumber.TestContext;
import com.amazon.pageObjects.CartPage;

import cucumber.api.java.en.When;

public class CartPageSteps {

	TestContext testContext;
	CartPage cartPage;

	public CartPageSteps(TestContext context) {
		testContext = context;
		cartPage = testContext.getPageObjectManager().getCartPage();
	}

	@When("^moves to checkout from mini cart$")
	public void moves_to_checkout_from_mini_cart() {
		cartPage.clickOn_ContinueToCheckout();
	}

}