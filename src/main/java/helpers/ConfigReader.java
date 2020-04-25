package helpers;

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
		return properties.getProperty("statsReportLocation");
	}
	
	public String getStatsHistoryReportPath() {
		return properties.getProperty("statsHistoryReportLocation");
	}

	public String getChartPath() {
		return properties.getProperty("chartLocation");
	}
	
	public String getLocustMasterFilePath() {
		return properties.getProperty("locustMasterFile");
	}
	
	public String getCsvReportFolderPath() {
		return properties.getProperty("csvReportFolderLocation");
	}
	
	public String getExtentReportConfigPath() {
		return properties.getProperty("extentReportConfigLocation");
	}

}
