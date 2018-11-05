package com.amazon.stepDefinitions;

import com.amazon.cucumber.TestContext;
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

		signInPage.perform_SignIn();

	}

}