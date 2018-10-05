package com.amazon.stepDefinitions;

import com.amazon.cucumber.TestContext;
import com.amazon.pageObjects.CheckoutPage;

import cucumber.api.java.en.When;

public class CheckoutPageSteps {
	TestContext testContext;
	CheckoutPage checkoutPage;

	public CheckoutPageSteps(TestContext context) {
		testContext = context;
		checkoutPage = testContext.getPageObjectManager().getCheckoutPage();
	}

	@When("^user select new delivery address$")
	public void select_same_delivery_address() {
		checkoutPage.check_ShipToDifferentAddress(false);
	}

	@When("^enter \"(.*)\" personal details on checkout page$")
	public void enter_personal_details_on_checkout_page(String rowID) {
		try {
			checkoutPage.fill_PersonalDetails();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@When("^select payment method as \"([^\"]*)\" payment$")
	public void select_payment_method_as_payment(String arg1) {
		// checkoutPage.select_PaymentMethod("CheckPayment");
	}

	@When("^place the order$")
	public void place_the_order() {
		// checkoutPage.check_TermsAndCondition(true);
		// checkoutPage.clickOn_PlaceOrder();
	}

}