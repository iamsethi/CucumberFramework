package com.amazon.stepDefinitions;

import org.json.simple.JSONObject;

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

	@When("^a '(.*)' message is sent to the green box with the properties$")
	public void sign_in_on_application(String rowID) {
		// signInPage.perform_SignIn(testData);
	}

}