package com.amazon.managers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.amazon.enums.DriverType;
import com.amazon.enums.EnvironmentType;

/**
 * @author Ketan Sethi
 *
 *
 */
public class WebDriverManager {
	// local variables
	private static DriverType driverType;
	private static EnvironmentType environmentType;
	private static WebDriverManager instance = null;
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	private ThreadLocal<String> sessionId = new ThreadLocal<String>();
	private ThreadLocal<String> sessionBrowser = new ThreadLocal<String>();
	private ThreadLocal<String> sessionPlatform = new ThreadLocal<String>();
	private ThreadLocal<String> sessionVersion = new ThreadLocal<String>();

	// constructor
	private WebDriverManager() {
		driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
		environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
	}

	/**
	 * getInstance method to retrieve active driver instance
	 *
	 * @return WebDriverManager
	 */
	public static WebDriverManager getInstance() {
		if (instance == null) {
			instance = new WebDriverManager();
		}

		return instance;
	}

	/**
	 * setDriver method to create driver instance
	 *
	 * @param browser
	 * @param environment
	 * @param platform
	 * @param optPreferences
	 * @throws Exception
	 */

	@SafeVarargs
	public final void setDriver(Map<String, Object>... optPreferences) throws Exception {
		String remoteHubURL = "http://127.0.0.1:4444/wd/hub";
		DesiredCapabilities caps = null;
		switch (environmentType) {
		case LOCAL:
			switch (driverType) {
			case FIREFOX:
				caps = DesiredCapabilities.firefox();
				FirefoxProfile ffProfile = new FirefoxProfile();
				ffProfile.setPreference("browser.autofocus", true);
				caps.setCapability(FirefoxDriver.PROFILE, ffProfile);
				caps.setCapability("marionette", true);
				System.setProperty("webdriver.gecko.driver",
						FileReaderManager.getInstance().getConfigReader().getDriverPath());
				if (optPreferences.length > 0) {
					processFFProfile(ffProfile, optPreferences);
				}
				webDriver.set(new FirefoxDriver(caps));

				break;
			case CHROME:
				caps = DesiredCapabilities.chrome();
				ChromeOptions chOptions = new ChromeOptions();
				Map<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("credentials_enable_service", false);
				chOptions.setExperimentalOption("prefs", chromePrefs);
				chOptions.addArguments("--disable-plugins", "--disable-extensions", "--disable-popup-blocking");
				caps.setCapability(ChromeOptions.CAPABILITY, chOptions);
				caps.setCapability("applicationCacheEnabled", false);
				System.setProperty("webdriver.chrome.driver",
						FileReaderManager.getInstance().getConfigReader().getDriverPath());
				if (optPreferences.length > 0) {
					processCHOptions(chOptions, optPreferences);
				}
				webDriver.set(new ChromeDriver(caps));
				break;
			case INTERNETEXPLORER:
				caps = DesiredCapabilities.internetExplorer();
				InternetExplorerOptions ieOpts = new InternetExplorerOptions();
				ieOpts.requireWindowFocus();
				ieOpts.merge(caps);
				caps.setCapability("requireWindowFocus", true);
				System.setProperty("webdriver.ie.driver", "ie_driver_windows_path/IEDriverServer.exe");
				if (optPreferences.length > 0) {
					processDesiredCaps(caps, optPreferences);
				}
				webDriver.set(new InternetExplorerDriver(caps));
				break;
			}
			break;
		case REMOTE:
			webDriver.set(new RemoteWebDriver(new URL(remoteHubURL), caps));
			((RemoteWebDriver) webDriver.get()).setFileDetector(new LocalFileDetector());
			break;
		}

	}

	/**
	 * getDriver method to retrieve active driver
	 *
	 * @return WebDriver
	 */
	public WebDriver getDriver() {
		return webDriver.get();
	}

	/**
	 * closeDriver method to close active driver
	 *
	 */
	public void closeDriver() {
		try {
			getDriver().quit();
		}

		catch (Exception e) {
			// do something
		}
	}

	/**
	 * getSessionId method to retrieve active id
	 *
	 * @return String
	 * @throws Exception
	 */
	public String getSessionId() throws Exception {
		return sessionId.get();
	}

	/**
	 * getSessionBrowser method to retrieve active browser
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getSessionBrowser() throws Exception {
		return sessionBrowser.get();
	}

	/**
	 * getSessionVersion method to retrieve active version
	 *
	 * @return String
	 * @throws Exception
	 */
	public String getSessionVersion() throws Exception {
		return sessionVersion.get();
	}

	/**
	 * getSessionPlatform method to retrieve active platform
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getSessionPlatform() throws Exception {
		return sessionPlatform.get();
	}

	/**
	 * Process Desired Capabilities method to override default browser or mobile
	 * driver behavior
	 *
	 * @param caps
	 *            - the DesiredCapabilities object
	 * @param options
	 *            - the key: value pair map
	 * @throws Exception
	 */
	private void processDesiredCaps(DesiredCapabilities caps, Map<String, Object>[] options) throws Exception {
		for (int i = 0; i < options.length; i++) {
			Object[] keys = options[i].keySet().toArray();
			Object[] values = options[i].values().toArray();
			for (int j = 0; j < keys.length; j++) {
				if (values[j] instanceof Integer) {
					caps.setCapability(keys[j].toString(), (int) values[j]);
				} else if (values[j] instanceof Boolean) {
					caps.setCapability(keys[j].toString(), (boolean) values[j]);
				} else if (Boolean.parseBoolean(values[j].toString())) {
					caps.setCapability(keys[j].toString(), Boolean.valueOf(values[j].toString()));
				} else {
					caps.setCapability(keys[j].toString(), values[j].toString());
				}
			}
		}
	}

	/**
	 * Process Firefox Profile Preferences method to override default browser driver
	 * behavior
	 *
	 * @param caps
	 *            - the FirefoxProfile object
	 * @param options
	 *            - the key: value pair map
	 * @throws Exception
	 */
	private void processFFProfile(FirefoxProfile profile, Map<String, Object>[] options) throws Exception {
		for (int i = 0; i < options.length; i++) {
			Object[] keys = options[i].keySet().toArray();
			Object[] values = options[i].values().toArray();
			// same as Desired Caps except the following difference
			for (int j = 0; j < keys.length; j++) {

				if (values[j] instanceof Integer) {
					profile.setPreference(keys[j].toString(), (int) values[j]);
				}
				// etc...
			}
		}
	}

	/**
	 * Process Chrome Options method to override default browser driver behavior
	 *
	 * @param caps
	 *            - the ChromeOptions object
	 * @param options
	 *            - the key: value pair map
	 * @throws Exception
	 */
	private void processCHOptions(ChromeOptions chOptions, Map<String, Object>[] options) throws Exception {
		for (int i = 0; i < options.length; i++) {
			Object[] keys = options[i].keySet().toArray();
			Object[] values = options[i].values().toArray();
			// same as Desired Caps except the following difference
			for (int j = 0; j < keys.length; j++) {
				if (values[j] instanceof Integer) {
					values[j] = (int) values[j];
					chOptions.setExperimentalOption("prefs", options[i]);
				}
				// etc...
			}
		}
	}

}