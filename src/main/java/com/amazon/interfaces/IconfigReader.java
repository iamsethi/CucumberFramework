/**
 * rsr 
 *
 *Aug 6, 2016
 */
package com.amazon.interfaces;

import org.openqa.selenium.remote.BrowserType;

public interface IconfigReader {
	public String getUserName();

	public String getPassword();

	public String getWebsite();

	public int getPageLoadTimeOut();

	public int getImplicitWait();

	public int getExplicitWait();

	public BrowserType getBrowser();
}
