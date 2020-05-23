package com.github.nightcodelabs.pretzel.performance;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;
import com.github.nightcodelabs.pretzel.helpers.ConfigReader;
import com.github.nightcodelabs.pretzel.helpers.FileOperations;

public class LocustOperations {
	
	private static final String TASKPACKAGEPATH = "locusttask";
	private static final String NAMEOFREPORT = "performanceResults";
	private static final Logger logger = LoggerFactory.getLogger(LocustOperations.class);
	private static String masterFilePath = FileOperations.getInstance().initialiseLocustMasterFile();
	private static String csvReportFilePath = ConfigReader.getInstance().getCsvReportFolderPath();
    private static String operatingSystem = System.getProperty("os.name").toLowerCase();

	private String locustTask;
    private String master = "127.0.0.1";
    private int masterPort = 5557;
    private int maxUsers;
    private int usersLoadPerSecond;
    private long maxRPS;
    private int weight;
    private int testTime;

    private Process locustMasterProcess;
	private Locust locust = Locust.getInstance();	
	/*
	 * This method set the data defined in cucumber in the private variables
	 */
	public void setTestData(Integer maxUsers, Integer usersLoadPerSecond, Integer testTime, Integer maxRPS, Integer weight){
		this.maxUsers = maxUsers;
		this.usersLoadPerSecond = usersLoadPerSecond;
		this.testTime = testTime;
		this.maxRPS = maxRPS;
		this.weight = weight;
	}
	
	/* 
	 * This method setup the slave with the private variables that contains the information of cucumber
	 */
	public void setUpSlave(){
		 locust.setMaxRPS(maxRPS);
		 locust.setMasterHost(master);
	     locust.setMasterPort(masterPort);
	    }
	 
	/*
	 * This method execute the task specified in cucumber
	 */
	public void executeTask(String taskName) throws Exception {
       this.locustTask=TASKPACKAGEPATH+"." + taskName;
       Class<?> nameClass = Class.forName(locustTask);
       locust.run((AbstractTask) nameClass.getConstructor(Integer.class).newInstance(this.weight));
   }
	

	/*
	 * This method raise the master with the parameters of the test defined in cucumber
	 */
	public void executeMaster() {
		
        String command="-f "+ masterFilePath +" --master --no-web --csv="+ csvReportFilePath + NAMEOFREPORT +" --expect-slaves=1 -c "+ maxUsers +" -r "+ usersLoadPerSecond+" -t"+testTime+"m";
        if (operatingSystem.indexOf("win") >= 0) {
        	command = "cmd.exe /c start /MIN locust.exe " + command;
        } else {
        	command= "locust " + command;
        }
        try {
        	locustMasterProcess = Runtime.getRuntime().exec(command);
        } catch (IOException error) {
        	logger.error("Something went wrong executing the master");
        }
    }
    
	
    //This method clear all the values;
	private void clearValues() {
		this.locustTask = "";
		this.maxUsers = 0;
		this.usersLoadPerSecond = 0;
		this.maxRPS = 0;
		this.weight = 0;
		this.testTime=0;
	}
    
    /*
     * This method execute the sequence needed for run the test
     */
    public void executePerformanceTask(Integer maxUsers, Integer usersLoadPerSecond, Integer testTime, Integer maxRPS, Integer weight, String nameTask) throws Exception {
		this.setTestData(maxUsers, usersLoadPerSecond, testTime, maxRPS, weight);
		this.executeMaster();
		if (operatingSystem.indexOf("win") >= 0) {
			while (!checkWindowsLocustService()) {
				logger.info("Waiting to locust master service to start");
			}
		}
		this.setUpSlave();
		this.executeTask(nameTask);
		TimeUnit.MINUTES.sleep(this.testTime);
		this.locust.stop();
		this.clearValues();
		if (operatingSystem.indexOf("win") >= 0) {
			while (checkWindowsLocustService()) {
				logger.info("Waiting to locust master service to be stopped");
			}
		} else {
			locustMasterProcess.onExit().get();
		}
	}
    
    public Boolean checkWindowsLocustService() {
         try {               	 
        	 Process process = Runtime.getRuntime().exec("tasklist");
        	 Scanner reader = new Scanner(process.getInputStream(), "UTF-8");
        	 while(reader.hasNextLine()) {
                 if(reader.nextLine().contains("locust")) {
                	 reader.close();
                     return true;
                 }
         	}    
        	 reader.close(); 
         } catch (IOException error) {
         	logger.error("Something went wrong checking locust service in windows system");
         }
    	return false;
    }
    
    //It returns true or false if the Max response time is higher or not than the expected 
    public Boolean checkMaxResponseTime(Long expectedTime) {
    	Boolean higher = false;
    	List<String[]> data = FileOperations.getInstance().readCSV(ConfigReader.getInstance().getStatsReportPath());    	
    	try {
			if (this.getMaxResponseTime(data, (data.size()-1))>expectedTime){
				higher = true;
			}
		} catch (Exception e) {
			logger.error("Something went wrong cheking the MaxResponseTime");
		};
    	return higher;
    }  
	
    //It returns the Max Response Time of the report
    private Long getMaxResponseTime(List<String[]> data, int testResultsIteration) throws Exception {
    	int position = 0;
    	for (int i= 2; i<data.get(0).length; i++) {
    		if((data.get(0)[i]).equals("Max response time")) {
    			position = i;
    		}   		
    	}    	
    	if(position == 0) {
    		logger.warn("The Max response time can't be found");
    		throw new Exception ("The Max response time can't be found");
    	} else {
    		return Long.parseLong(data.get(testResultsIteration)[position]);
    	}
    }
    
    
    
	
}
