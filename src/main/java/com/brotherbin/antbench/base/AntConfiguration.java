package com.brotherbin.antbench.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AntConfiguration {

	private final String propertiesFile = "antbench.properties";
	
	private int concurrency;

	private int iterations;
	
	private int initDataRows;
	
	private int initBatchSize;
	
	public static final AntConfiguration INSTANCE = new AntConfiguration();
	
	private AntConfiguration() {
		loadProperties();
	}
	
	private void loadProperties(){
		InputStream inputStrem = this.getClass().getClassLoader().getResourceAsStream(propertiesFile);
		Properties prop = new Properties();
		try {
			prop.load(inputStrem);
			concurrency = Integer.parseInt(prop.getProperty("concurrency"));
			iterations = Integer.parseInt(prop.getProperty("iterations"));
			initDataRows = Integer.parseInt(prop.getProperty("initDataRows"));
			initBatchSize = Integer.parseInt(prop.getProperty("initBatchSize"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getConcurrency() {
		return concurrency;
	}

	public int getIterations() {
		return iterations;
	}

	public int getInitDataRows() {
		return initDataRows;
	}
	
	public int getInitBatchSize() {
		return initBatchSize;
	}
}
