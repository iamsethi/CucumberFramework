package com.amazon.database;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.amazon.interfaces.ILog;

public class TletIdrDao {
	private static Logger Log = ILog.getLogger(TletIdrDao.class);

	private NamedParameterJdbcTemplate jdbcTemplate;
	private DataSource dataSources;

	/** The insert scenario id. */
	@Value("${select.insertByScenarioId.sql}")
	private String insertByScenarioId;

	/** The select by scenario id. */
	@Value("${select.selectByScenarioId.sql}")
	private String selectByScenarioId;

	public void setDataSource(DataSource dataSource) {
		this.dataSources = dataSource;
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSources);
	}

	public void insertTradeLetter(String scenarioId) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("SCENARIO_ID", 1);
		jdbcTemplate.update(insertByScenarioId, paramMap);
		Log.debug("##########Inserted successfully#################");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(selectByScenarioId, paramMap);
		Log.debug("##########Retrieved successfully#################");
		System.out.println(list);
	}

}