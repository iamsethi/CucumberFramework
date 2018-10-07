package com.amazon.cucumber;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.amazon.managers.FileReaderManager;
import com.amazon.managers.PageObjectManager;
import com.amazon.managers.WebDriverManager;

public class TestContext {
	private WebDriverManager webDriverManager;
	private FileReaderManager fileReaderManager;
	private PageObjectManager pageObjectManager;
	private JdbcTemplate jdbcTemplateObject;
	private static final String confFile = "/spring-context/applicationContext-smoke-suite.xml";
	private static ConfigurableApplicationContext context;

	public TestContext() {
		webDriverManager = WebDriverManager.getInstance();
		fileReaderManager = FileReaderManager.getInstance();
		pageObjectManager = new PageObjectManager(webDriverManager.getDriver());
		context = new ClassPathXmlApplicationContext(confFile);
		jdbcTemplateObject = context.getBean(JdbcTemplate.class);
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

	public JdbcTemplate getTletIdrDao() {
		return jdbcTemplateObject;
	}

}