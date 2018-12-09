package com.amazon.managers;

import org.openqa.selenium.WebDriver;

import com.amazon.pageObjects.CartPage;
import com.amazon.pageObjects.CheckoutPage;
import com.amazon.pageObjects.HomePage;
import com.amazon.pageObjects.ProductListingPage;
import com.amazon.pageObjects.SignInPage;

public class PageObjectManager {

	private WebDriver driver;
	private ProductListingPage productListingPage;
	private CartPage cartPage;
	private HomePage homePage;
	private CheckoutPage checkoutPage;
	private SignInPage signInPage;

	public PageObjectManager(WebDriver driver) {
		this.driver = driver;
	}

	public HomePage getHomePage() {
		return (homePage == null) ? homePage = new HomePage(driver) : homePage;
	}

	public ProductListingPage getProductListingPage() {
		return (productListingPage == null) ? productListingPage = new ProductListingPage(driver) : productListingPage;
	}

	public CartPage getCartPage() {
		return (cartPage == null) ? cartPage = new CartPage(driver) : cartPage;
	}

	public CheckoutPage getCheckoutPage() {
		return (checkoutPage == null) ? checkoutPage = new CheckoutPage(driver) : checkoutPage;
	}

	public SignInPage getSignInPage() {
		return (signInPage == null) ? signInPage = new SignInPage(driver) : signInPage;
	}

}