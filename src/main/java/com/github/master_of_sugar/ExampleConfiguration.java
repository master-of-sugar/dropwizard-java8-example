package com.github.master_of_sugar;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class ExampleConfiguration extends Configuration{
	
	private static Logger logger = LoggerFactory.getLogger(ExampleConfiguration.class);
	
	@Valid
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory database = new DataSourceFactory();

	public DataSourceFactory getDataSourceFactory() {
		String databaseUrl = System.getenv("DATABASE_URL");
		if(databaseUrl == null){
			logger.info("Standard DataSourceFactory url=" + database.getUrl());
			return database;
		}
		DataSourceFactory dsf = HerokuDataSourceFactory.get(databaseUrl);
		logger.info("Heroku DataSourceFactory url=" + dsf.getUrl());
		return dsf;
	}

}
