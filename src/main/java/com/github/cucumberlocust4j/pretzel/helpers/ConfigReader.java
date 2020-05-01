package com.github.cucumberlocust4j.pretzel.helpers;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
	private static ConfigReader _instance = new ConfigReader();
	private Properties properties;
	private static final String STATSREPORTPATH = "target/csvlocustsresults/performanceResults_stats.csv";
	private static final String STATSHISTORYREPORTPATH = "target/csvlocustsresults/performanceResults_stats_history.csv";
	private static final String CHARTPATH = "target/cucumber-reports/locustcharts/";
	private static final String LOCUSTMASTERPATH = "src/main/resources/performance/locust-master.py";
	private static final String CSVREPORTFOLDERPATH = "target/csvlocustsresults/";
	private static final String EXTENTREPORTCONFIGPATH = "src/main/resources/configs/extent-config.xml";
	
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
		if (properties.getProperty("STATSREPORTPATH") != null) return properties.getProperty("STATSREPORTPATH");
		else return STATSREPORTPATH;
	}
	
	public String getStatsHistoryReportPath() {
		if (properties.getProperty("STATSHISTORYREPORTPATH") != null) return properties.getProperty("STATSHISTORYREPORTPATH");
		else return STATSHISTORYREPORTPATH;
	}

	public String getChartPath() {
		if (properties.getProperty("CHARTPATH") != null) return properties.getProperty("CHARTPATH");
		else return CHARTPATH;
	}
	
	public String getLocustMasterFilePath() {
		if (properties.getProperty("LOCUSTMASTERPATH") != null) return properties.getProperty("LOCUSTMASTERPATH");
		else return LOCUSTMASTERPATH;
	}
	
	public String getCsvReportFolderPath() {
		if (properties.getProperty("CSVREPORTFOLDERPATH") != null) return properties.getProperty("CSVREPORTFOLDERPATH");
		else return CSVREPORTFOLDERPATH;
	}
	
	public String getExtentReportConfigPath() {
		if (properties.getProperty("EXTENTREPORTCONFIGPATH") != null) return properties.getProperty("EXTENTREPORTCONFIGPATH");
		else return EXTENTREPORTCONFIGPATH;
	}

}
