package com.amazon.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {
	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.CSS, using = "#enterAddressFullName")
	public WebElement tbx_Name;

	@FindBy(how = How.CSS, using = "#billing_email")
	public WebElement tbx_Email;

	@FindBy(how = How.CSS, using = "#enterAddressPhoneNumber")
	public WebElement tbx_Phone;

	@FindBy(how = How.NAME, using = "shipToThisAddress")
	public WebElement btn_shipToThisAddress;

	@FindBy(how = How.CLASS_NAME, using = "a-button-inner")
	public WebElement btn_dlvrToThisAddress;

	@FindBy(how = How.CSS, using = "#enterAddressCountryCode")
	public WebElement drpdwn_CountryDropDownArrow;

	@FindAll(@FindBy(how = How.CSS, using = "#select2-drop ul li"))
	public List<WebElement> country_List;

	@FindBy(how = How.CSS, using = "#enterAddressCity")
	public WebElement tbx_City;

	@FindBy(how = How.CSS, using = "#enterAddressAddressLine1")
	public WebElement tbx_Address;

	@FindBy(how = How.CSS, using = "#enterAddressPostalCode")
	public WebElement tbx_PostCode;

	@FindBy(how = How.CSS, using = "#enterAddressStateOrRegion")
	public WebElement tbx_State;

	@FindBy(how = How.LINK_TEXT, using = "enter a new shipping address")
	public WebElement chkbx_ShipToDifferetAddress;

	@FindAll(@FindBy(how = How.CSS, using = "radio-col aok-float-left spacing-right-small"))
	public List<WebElement> paymentMethod_List;

	@FindBy(how = How.CSS, using = "#terms.input-checkbox")
	public WebElement chkbx_AcceptTermsAndCondition;

	@FindBy(how = How.CSS, using = "#place_order")
	public WebElement btn_PlaceOrder;

	public void enter_Name(String name) {
		tbx_Name.sendKeys(name);
	}

	public void enter_Email(String email) {
		tbx_Email.sendKeys(email);
	}

	public void enter_Phone(String phone) {
		tbx_Phone.sendKeys(phone);
	}

	public void enter_City(String city) {
		tbx_City.sendKeys(city);
	}

	public void enter_Address(String address) {
		tbx_Address.sendKeys(address);
	}

	public void enter_PostCode(String postCode) {
		tbx_PostCode.sendKeys(postCode);
	}

	public void enter_State(String state) {
		tbx_State.sendKeys(state);

	}

	public void check_ShipToDifferentAddress(boolean value) {
		if (!value)
			chkbx_ShipToDifferetAddress.click();
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

	}

	public void check_TermsAndCondition(boolean value) {
		if (value)
			chkbx_AcceptTermsAndCondition.click();
	}

	public void clickOn_PlaceOrder() {
		btn_PlaceOrder.submit();

	}

	public void fill_PersonalDetails() {
		get_data_for_page(this);
	}

}