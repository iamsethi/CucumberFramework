package com.amazon.database;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.amazon.dataProviders.JsonDataReader;
import com.amazon.interfaces.ILog;

/**
 * @author sethiK
 *
 */
@ContextConfiguration(locations = { "classpath*:/spring-context/applicationContext-smoke-suite.xml" })
public class TletIdrDao {

	private static Logger Log = ILog.getLogger(JsonDataReader.class);

	private JdbcTemplate jdbcTemplate;

	private DataSource dataSources;
	protected String PARTY_ID;

	/** The select party id by TL id. */
	@Value("${select.partyIdByTLId.sql}")
	private String selectPartyIdByTLId;

	public void setDataSource(DataSource dataSource) {
		this.dataSources = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSources);

	}

	public void deleteTradeLetter(String tradeLetterId) {

		Log.info("####" + selectPartyIdByTLId);
		jdbcTemplate.execute(selectPartyIdByTLId);

	}

}
