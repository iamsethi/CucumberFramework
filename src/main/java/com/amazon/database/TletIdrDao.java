package com.amazon.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:application-sql.properties")
public class TletIdrDao {

	private static final String confFile = "/spring-context/applicationContext-smoke-suite.xml";
	private ConfigurableApplicationContext context;
	private JdbcTemplate jdbcTemplateObject;

	/** The select party id by TL id. */
	@Value("${select.partyIdByTLId.sql}")
	private String selectPartyIdByTLId;

	public TletIdrDao() {
		context = new ClassPathXmlApplicationContext(confFile);
		jdbcTemplateObject = context.getBean(JdbcTemplate.class);
	}

	public void deleteTradeLetter(String tradeLetterId) {
		System.out.println(selectPartyIdByTLId);
		jdbcTemplateObject.execute("SELECT * FROM TRADE_LETTER");

	}

}