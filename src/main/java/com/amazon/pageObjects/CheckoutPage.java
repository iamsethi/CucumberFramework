package com.amazon.pageObjects;

import java.util.List;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.amazon.selenium.BrowserUtils;

public class CheckoutPage {
	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.CSS, using = "#enterAddressFullName")
	private WebElement txtbx_Name;

	@FindBy(how = How.CSS, using = "#billing_email")
	private WebElement txtbx_Email;

	@FindBy(how = How.CSS, using = "#enterAddressPhoneNumber")
	private WebElement txtbx_Phone;

	@FindBy(how = How.NAME, using = "shipToThisAddress")
	private WebElement btn_shipToThisAddress;

	@FindBy(how = How.CLASS_NAME, using = "a-button-inner")
	private WebElement btn_dlvrToThisAddress;

	@FindBy(how = How.CSS, using = "#enterAddressCountryCode")
	private WebElement drpdwn_CountryDropDownArrow;

	@FindAll(@FindBy(how = How.CSS, using = "#select2-drop ul li"))
	private List<WebElement> country_List;

	@FindBy(how = How.CSS, using = "#enterAddressCity")
	private WebElement txtbx_City;

	@FindBy(how = How.CSS, using = "#enterAddressAddressLine1")
	private WebElement txtbx_Address;

	@FindBy(how = How.CSS, using = "#enterAddressPostalCode")
	private WebElement txtbx_PostCode;

	@FindBy(how = How.CSS, using = "#enterAddressStateOrRegion")
	private WebElement txtbx_State;

	@FindBy(how = How.LINK_TEXT, using = "enter a new shipping address")
	private WebElement chkbx_ShipToDifferetAddress;

	@FindAll(@FindBy(how = How.CSS, using = "radio-col aok-float-left spacing-right-small"))
	private List<WebElement> paymentMethod_List;

	@FindBy(how = How.CSS, using = "#terms.input-checkbox")
	private WebElement chkbx_AcceptTermsAndCondition;

	@FindBy(how = How.CSS, using = "#place_order")
	private WebElement btn_PlaceOrder;

	public void enter_Name(String name) {
		txtbx_Name.sendKeys(name);
	}

	public void enter_Email(String email) {
		txtbx_Email.sendKeys(email);
	}

	public void enter_Phone(String phone) {
		txtbx_Phone.sendKeys(phone);
	}

	public void enter_City(String city) {
		txtbx_City.sendKeys(city);
	}

	public void enter_Address(String address) {
		txtbx_Address.sendKeys(address);
	}

	public void enter_PostCode(String postCode) {
		txtbx_PostCode.sendKeys(postCode);
	}

	public void enter_State(String state) {
		txtbx_State.sendKeys(state);

	}

	public void check_ShipToDifferentAddress(boolean value) {
		if (!value)
			chkbx_ShipToDifferetAddress.click();
		BrowserUtils.untilJqueryIsDone(driver);
	}

	public void select_Country(String countryName) {

		drpdwn_CountryDropDownArrow.click();
		Select country = new Select(drpdwn_CountryDropDownArrow);
		country.selectByVisibleText(countryName);

	}

	public void select_PaymentMethod(String paymentMethod) {
		if (paymentMethod.equals("CheckPayment")) {
			paymentMethod_List.get(0).click();
		} else if (paymentMethod.equals("Cash")) {
			paymentMethod_List.get(1).click();
		} else {
			new Exception("Payment Method not recognised : " + paymentMethod);
		}
		BrowserUtils.untilJqueryIsDone(driver);

	}

	public void check_TermsAndCondition(boolean value) {
		if (value)
			chkbx_AcceptTermsAndCondition.click();
	}

	public void clickOn_PlaceOrder() {
		btn_PlaceOrder.submit();
		BrowserUtils.untilPageLoadComplete(driver);
	}

	public void fill_PersonalDetails(JSONObject customer) {
		enter_Name(customer.get("firstName").toString());
		enter_Address(customer.get("streetAddress").toString());
		enter_City(customer.get("city").toString());
		enter_State(customer.get("state").toString());
		enter_PostCode(customer.get("postCode").toString());
		select_Country(customer.get("country").toString());
		enter_Phone(customer.get("mob").toString());
		btn_shipToThisAddress.click();
		btn_dlvrToThisAddress.click();

	}

}