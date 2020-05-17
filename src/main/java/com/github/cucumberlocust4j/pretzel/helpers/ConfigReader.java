package com.github.cucumberlocust4j.pretzel.helpers;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
	
	private static final String STATSREPORTPATH = "target/csvlocustsresults/performanceResults_stats.csv";
	private static final String STATSHISTORYREPORTPATH = "target/csvlocustsresults/performanceResults_stats_history.csv";
	private static final String CHARTDIR = "locustcharts/";
	private static final String CHARTPATH = "target/cucumber-reports/" + CHARTDIR;
	private static final String LOCUSTMASTERPATH = "target/pretzel/locust-master.py";
	private static final String CSVREPORTFOLDERPATH = "target/csvlocustsresults/";
	private static final String EXTENTREPORTCONFIGPATH = "src/main/resources/configs/extent-config.xml";
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
			File configPropertiesFile = new File("/configs/config.properties");
			if(configPropertiesFile.exists()) properties.load(this.getClass().getResourceAsStream("/configs/config.properties"));
		} catch (IOException e) {
			logger.warn("Configuration properties file cannot be found");
			throw new RuntimeException("Configuration properties file cannot be found");
		}
	}
	
	public String getStatsReportPath() {
		if (properties.getProperty("statsReportPath") != null) return properties.getProperty("statsReportPath");
		else return STATSREPORTPATH;
	}
	
	public String getStatsHistoryReportPath() {
		if (properties.getProperty("statsHistoryReportPath") != null) return properties.getProperty("statsHistoryReportPath");
		else return STATSHISTORYREPORTPATH;
	}

	public String getChartPath() {
		if (properties.getProperty("chartPath") != null) return properties.getProperty("chartPath");
		else return CHARTPATH;
	}

	public String getChartDir() {
		if (properties.getProperty("chartDir") != null) return properties.getProperty("chartDir");
		else return CHARTDIR;
	}
	
	public String getLocustMasterFilePath() {
		if (properties.getProperty("locustMasterPath") != null) return properties.getProperty("locustMasterPath");
		else return LOCUSTMASTERPATH;
	}
	
	public String getCsvReportFolderPath() {
		if (properties.getProperty("csvReportFolderPath") != null) return properties.getProperty("csvReportFolderPath");
		else return CSVREPORTFOLDERPATH;
	}
	
	public String getExtentReportConfigPath() {
		if (properties.getProperty("extentReportConfigPath") != null) return properties.getProperty("extentReportConfigPath");
		else return EXTENTREPORTCONFIGPATH;
	}

}
