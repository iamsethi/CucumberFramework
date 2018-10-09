package com.amazon.cucumber;

import com.amazon.database.TletIdrDao;
import com.amazon.managers.FileReaderManager;
import com.amazon.managers.PageObjectManager;
import com.amazon.managers.WebDriverManager;

public class TestContext {
	private WebDriverManager webDriverManager;
	private FileReaderManager fileReaderManager;
	private PageObjectManager pageObjectManager;
	private TletIdrDao tletIdrDao;

	public TestContext() {
		webDriverManager = WebDriverManager.getInstance();
		fileReaderManager = FileReaderManager.getInstance();
		pageObjectManager = new PageObjectManager(webDriverManager.getDriver());
		tletIdrDao = new TletIdrDao();

	}

	public WebDriverManager getWebDriverManager() {
		return webDriverManager;
	}

	public PageObjectManager getPageObjectManager() {
		return pageObjectManager;
	}

	public FileReaderManager getFileReaderManager() {
		return fileReaderManager;
	}

	public TletIdrDao getTletIdrDao() {
		return tletIdrDao;
	}

}