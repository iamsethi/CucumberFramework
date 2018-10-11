package com.amazon.api;

import static io.restassured.RestAssured.given;

import org.apache.log4j.Logger;

import com.amazon.constants.EndPoints;
import com.amazon.constants.Path;
import com.amazon.helper.RestUtilities;
import com.amazon.interfaces.ILog;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TwitterStepDefinition {
	private Logger Log = ILog.getLogger(TwitterStepDefinition.class);
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String tweetId = "";

	@Given("^a user exists with OATH$")
	public void a_user_exists_with_OATH_with_below_details() {
		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.STATUSES);
		resSpec = RestUtilities.getResponseSpecification();
	}

	@When("^a user post the tweet$")
	public void a_user_post_the_tweet(String tweetMessage) {
		// https://api.twitter.com/1.1/statuses/update.json?status=Hi There!!		
		Response response =
				given()
					.spec(RestUtilities.createQueryParam(reqSpec, "status", tweetMessage))
				.when()
					.post(EndPoints.STATUSES_TWEET_POST)
				.then()
					.spec(resSpec)
					.extract()
					.response();
		JsonPath jsPath = RestUtilities.getJsonPath(response);
		tweetId = jsPath.get("id_str");
		Log.info("The Tweet ID:" + tweetId);

	}

	@When("^user read the tweet$")
	public void user_read_the_tweet()  {
		RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_READ_SINGLE);
		Response res = RestUtilities.getResponse(
				RestUtilities.createQueryParam(reqSpec, "id", tweetId), "get");
		String text = res.path("text");
		System.out.println("The tweet text is: " + text);
	    
	}

	@When("^User delete the tweet$")
	public void user_delete_the_tweet()  {
	given()
		.spec(RestUtilities.createPathParam(reqSpec, "id", tweetId))
	.when()
		.post(EndPoints.STATUSES_TWEET_DESTROY)
	.then()
		.spec(resSpec);
	   
	}

	@Then("^twitter response status code should be \"([^\"]*)\"$")
	public void twitter_response_status_code_should_be(String arg1) {

	}

}