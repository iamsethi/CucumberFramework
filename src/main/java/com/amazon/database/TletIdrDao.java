package com.amazon.database;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import com.amazon.interfaces.ILog;

public class TletIdrDao {
	private static Logger Log = ILog.getLogger(TletIdrDao.class);

	private JdbcTemplate jdbcTemplate;
	private DataSource dataSources;

	/** The select party id by TL id. */
	@Value("${select.partyIdByTLId.sql}")
	private String selectPartyIdByTLId;

	public void setDataSource(DataSource dataSource) {
		this.dataSources = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSources);
	}

	public void deleteTradeLetter(String tradeLetterId) {
		Log.info(selectPartyIdByTLId);
		jdbcTemplate.execute("SELECT * FROM TRADE_LETTER");

	}

}