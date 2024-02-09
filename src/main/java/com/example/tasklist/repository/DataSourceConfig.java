package com.example.tasklist.repository;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

@Configuration
public class DataSourceConfig {
	
	private final DataSource dataSource;

	public DataSourceConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}
}
