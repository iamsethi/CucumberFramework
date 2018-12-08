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
		if (checkoutPage == null) {
			this.checkoutPage = new CheckoutPage(driver);
			System.out.println("if" + "-->" + this.checkoutPage);
			return this.checkoutPage;
		} else {
			System.out.println("else" + "-->" + checkoutPage);
			return checkoutPage;
		}
	}

	public SignInPage getSignInPage() {
		return (signInPage == null) ? signInPage = new SignInPage(driver) : signInPage;
	}

}