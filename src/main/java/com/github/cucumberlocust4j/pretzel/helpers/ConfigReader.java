package com.github.cucumberlocust4j.pretzel.helpers;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {

	private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
	private static ConfigReader _instance = new ConfigReader();
	
	private Properties properties;
	
	private ConfigReader() {
		properties = new Properties();
		this.loadData();
	}
	
	public static ConfigReader getInstance() {
		return _instance;
	}	
	
	private void loadData(){
		try {
			properties.load(this.getClass().getResourceAsStream("/configs/config.properties"));
		} catch (IOException e) {
			logger.warn("Configuration properties file cannot be found");
			throw new RuntimeException("Configuration properties file cannot be found");
		}
	}
	
	public String getStatsReportPath() {
		if (properties.getProperty("statsReportPath") != null) return properties.getProperty("statsReportPath");
		else return Constants.STATSREPORTPATH;
	}
	
	public String getStatsHistoryReportPath() {
		if (properties.getProperty("statsHistoryReportPath") != null) return properties.getProperty("statsHistoryReportPath");
		else return Constants.STATSHISTORYREPORTPATH;
	}

	public String getChartPath() {
		if (properties.getProperty("chartPath") != null) return properties.getProperty("chartPath");
		else return Constants.CHARTPATH;
	}
	
	public String getLocustMasterFilePath() {
		if (properties.getProperty("locustMasterPath") != null) return properties.getProperty("locustMasterPath");
		else return Constants.LOCUSTMASTERCOMPLETEPATH;
	}
	
	public String getCsvReportFolderPath() {
		if (properties.getProperty("csvReportFolderPath") != null) return properties.getProperty("csvReportFolderPath");
		else return Constants.CSVREPORTFOLDERPATH;
	}
	
	public String getExtentReportConfigPath() {
		if (properties.getProperty("extentReportConfigPath") != null) return properties.getProperty("extentReportConfigPath");
		else return Constants.EXTENTREPORTCONFIGPATH;
	}

}
