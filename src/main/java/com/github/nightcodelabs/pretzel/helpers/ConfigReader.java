package com.github.nightcodelabs.pretzel.helpers;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to gather and manage the information from a properties file
 */
public class ConfigReader {
	
	public static final String PRETZELTASKPACKAGE = "pretzel";
	public static final String PRETZELRESULTSPATH = "target/pretzel/";
	public static final String PRETEZELINTERNALEXECUTIONFILEPATH = PRETZELRESULTSPATH + "internal/";
	public static final String PRETEZELREPORTFILEPATH = PRETZELRESULTSPATH + "report/";
    public static final String LOCUSTMASTERFILEPATH = PRETEZELINTERNALEXECUTIONFILEPATH + "locustMasterFile/";
	public static final String LOCUSTMASTERFILENAME = "locust-master.py";
	public static final String LOCUSTMASTERCOMPLETEPATH = LOCUSTMASTERFILEPATH + LOCUSTMASTERFILENAME;
	public static final String CSVREPORTFOLDERPATH = PRETEZELINTERNALEXECUTIONFILEPATH + "/locustCsvReport/";
	public static final String STATSREPORTPATH = CSVREPORTFOLDERPATH + "performanceResults_stats.csv";
    public static final String STATSHISTORYREPORTPATH = CSVREPORTFOLDERPATH + "performanceResults_stats_history.csv";
	public static final String CHARTDIR = "charts/";
    public static final String CHARTPATH = PRETEZELREPORTFILEPATH + CHARTDIR;
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
	
	/**
	 * Load data from config.properties file
	 */
	private void loadData(){
		try {
			File configPropertiesFile = new File("/configs/config.properties");
			if(configPropertiesFile.exists()) properties.load(this.getClass().getResourceAsStream("/configs/config.properties"));
		} catch (IOException e) {
			logger.warn("Configuration properties file cannot be found");
			throw new RuntimeException("Configuration properties file cannot be found");
		}
	}
	
	/**
	 * @return Path where the Results Stats csv file of locust will be generated
	 */
	public String getStatsReportPath() {
		if (properties.getProperty("statsReportPath") != null) return properties.getProperty("statsReportPath");
		else return STATSREPORTPATH;
	}
	

	/**
	 * @return Path where the History Results csv file of locust will be generated
	 */
	public String getStatsHistoryReportPath() {
		if (properties.getProperty("statsHistoryReportPath") != null) return properties.getProperty("statsHistoryReportPath");
		else return STATSHISTORYREPORTPATH;
	}

	/**
	 * @return Path where the Bar Chart png file will be generated
	 */
	public String getChartPath() {
		if (properties.getProperty("chartPath") != null) return properties.getProperty("chartPath");
		else return CHARTPATH;
	}

	/**
	 * @return Path of the Bar Chart directory where the png files are going to be generated 
	 */
	public String getChartDir() {
		if (properties.getProperty("chartDir") != null) return properties.getProperty("chartDir");
		else return CHARTDIR;
	}
	
	/**
	 * @return Path where the .py locust master file is stored (or will be generated)
	 */
	public String getLocustMasterFilePath() {
		if (properties.getProperty("locustMasterPath") != null) return properties.getProperty("locustMasterPath");
		else return LOCUSTMASTERCOMPLETEPATH;
	}
	
	/**
	 * @return Directory path where the csv file is going to be stored
	 */
	public String getCsvReportFolderPath() {
		if (properties.getProperty("csvReportFolderPath") != null) return properties.getProperty("csvReportFolderPath");
		else return CSVREPORTFOLDERPATH;
	}
	
	/**
	 * @return The name of the package where the tasks are stored
	 */
	public String getPretzelTaskPackage() {
		if (properties.getProperty("pretzelTaskPackage") != null) return properties.getProperty("pretzelTaskPackage");
		else return PRETZELTASKPACKAGE;
	}

}
