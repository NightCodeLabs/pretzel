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

/**
 * This class defines the operations needed to execute a task
 */
public class LocustOperations {
	
	private static final String TASKPACKAGEPATH = ConfigReader.getInstance().getPretzelTaskPackage();
	private static final String NAMEOFREPORT = "performanceResults";
	private static final Logger logger = LoggerFactory.getLogger(LocustOperations.class);
	private static String masterFilePath = FileOperations.getInstance().getGeneratedLocustMasterFilePathIfNotSpecfified();
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
	
	
	
	
	/**
	 * Executes given performance test
     * @param maxUsers	Number of total users to simulate
     * @param usersLoadPerSecond Number of total users spawned per second
     * @param testTime	Duration of the test execution
     * @param maxRPS Max number of requests per seconds (We recommend to initialise this with the same value as maxUsers for simplicity)
     * @param weight The user weight of the task executed (This is used when multiple test are executed concurrently at the same time)
     * @param nameTask The name of the performance task to be executed in the test.
	 * @throws Exception an exception
	 */
    public void doPretzel(Integer maxUsers, Integer usersLoadPerSecond, Integer testTime, Integer maxRPS, Integer weight, String nameTask) throws Exception {
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
    
	
    /**
     * Check if the response time of the test requests are higher than the expected time 
     * @param expectedTime The time in miliseconds
     * @return True if it's higher or false if it's lower.
     */
    public Boolean checkMaxResponseTimeAboveExpected(Long expectedTime) {
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
	
	
	/**
	 * Set the data defined at test level
     * @param maxUsers	Number of total users to simulate
     * @param usersLoadPerSecond Number of total users spawned per second
     * @param testTime	Duration of the test execution
     * @param maxRPS Max number of requests per seconds (We recommend to initialise this with the same value as maxUsers for simplicity)
     * @param weight The user weight of the task executed (This is used when multiple test are executed concurrently at the same time)
	 */
	private void setTestData(Integer maxUsers, Integer usersLoadPerSecond, Integer testTime, Integer maxRPS, Integer weight){
		this.maxUsers = maxUsers;
		this.usersLoadPerSecond = usersLoadPerSecond;
		this.testTime = testTime;
		this.maxRPS = maxRPS;
		this.weight = weight;
	}
	
	
	/**
	 *  This method setup the slave with the private variables that contains the information of test level
	 */
	private void setUpSlave(){
		 locust.setMaxRPS(maxRPS);
		 locust.setMasterHost(master);
	     locust.setMasterPort(masterPort);
	    }
	 
	
	/**
	 * Finds the task defined in test level and execute it
	 * @param taskName The name of the performance task to be executed in the test.
	 * @throws Exception an exception
	 */
	private void executeTask(String taskName) throws Exception {
       this.locustTask=TASKPACKAGEPATH+"." + taskName;
       Class<?> nameClass = Class.forName(locustTask);
       locust.run((AbstractTask) nameClass.getConstructor(Integer.class).newInstance(this.weight));
   }
	

	/**
	 * This method launches the locust master with the parameters defined at test level for different OS
	 */
	private void executeMaster() {
		
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
    
	/**
	 * This method clears all the private values of the test to be reused in the next.
	 */
	private void clearValues() {
		this.locustTask = "";
		this.maxUsers = 0;
		this.usersLoadPerSecond = 0;
		this.maxRPS = 0;
		this.weight = 0;
		this.testTime=0;
	}

    /**
     * Check if Locust service is already running in windows
     * @return true if already running and false if not
     */
    private Boolean checkWindowsLocustService() {
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
	

    /**
     * Analyzes a list of values in order to return the max response value
     * @param data The data list to analyze
     * @param testResultsIteration The line position inside the data to read
     * @return the max response value
     * @throws Exception an exception
     */
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
