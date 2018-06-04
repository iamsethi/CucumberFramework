package com.amazon.stepDefinitions;

import com.amazon.cucumber.TestContext;
import com.amazon.managers.FileReaderManager;
import com.amazon.pageObjects.CheckoutPage;
import com.amazon.testDataTypes.Customer;

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
	public void enter_personal_details_on_checkout_page(String customerName) {
		Customer customer = FileReaderManager.getInstance().getJsonReader().getCustomerByName(customerName);
		checkoutPage.fill_PersonalDetails(customer);
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