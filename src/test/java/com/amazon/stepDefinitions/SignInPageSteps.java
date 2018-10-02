package com.amazon.stepDefinitions;

import com.amazon.cucumber.TestContext;
import com.amazon.dataProviders.JsonDataReader;
import com.amazon.pageObjects.SignInPage;

import cucumber.api.java.en.When;

public class SignInPageSteps {

	TestContext testContext;
	SignInPage signInPage;

	public SignInPageSteps(TestContext context) {
		testContext = context;
		signInPage = testContext.getPageObjectManager().getSignInPage();

	}

	@When("^I sign in on application$")
	public void i_sign_in_on_application() {
		try {
			JsonDataReader.get_data_for_page("UWSOrderSummaryEditPage");
			signInPage.perform_SignIn();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}