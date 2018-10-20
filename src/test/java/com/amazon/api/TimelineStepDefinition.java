package com.amazon.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;

import com.amazon.constants.EndPoints;
import com.amazon.constants.Path;
import com.amazon.interfaces.ILog;

import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TimelineStepDefinition {
	private Logger Log = ILog.getLogger(TimelineStepDefinition.class);
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String tweetId = "";
	
	@BeforeClass
	public void setup() {
		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.queryParam("user_id", "apiautomation");
		reqSpec.basePath(Path.STATUSES);
		
		resSpec = RestUtilities.getResponseSpecification();
	}


	@When("^user read the tweet$")
	public void user_read_the_tweet()  {
	given()
		.spec(RestUtilities.createQueryParam(reqSpec, "count", "1"))
	.when()
		.get(EndPoints.STATUSES_USER_TIMELINE)
	.then()
		//.log().all()
		.spec(resSpec)
		.body("user.screen_name", hasItem("iam_sethi"));
		
		RestUtilities.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);
		Response res = RestUtilities.getResponse(
				RestUtilities.createQueryParam(reqSpec, "count", "2"), "get");
		ArrayList<String> screenNameList = res.path("user.screen_name");
		System.out.println("Read Tweets 2 Method");
		Assert.assertTrue(screenNameList.contains("apiautomation"));
	    
	}
	

}