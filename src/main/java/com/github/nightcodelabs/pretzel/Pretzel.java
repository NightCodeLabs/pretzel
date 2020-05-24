package com.github.nightcodelabs.pretzel;

import com.github.nightcodelabs.pretzel.graph.BarChart;
import com.github.nightcodelabs.pretzel.helpers.ConfigReader;
import com.github.nightcodelabs.pretzel.helpers.FileOperations;
import com.github.nightcodelabs.pretzel.performance.LocustOperations;

import java.io.File;


/** 
 * Entry point class for the different functionalities of pretzel
 * Collection of main methods to be used by external projects
 */
public class Pretzel {
    LocustOperations locustOperations = new LocustOperations();

    /**
     * Prepare the reports folders in order to ensure that exists in every execution
     */
    public void initiateReportDirectory() {
        FileOperations.getInstance().initialiseFolder(ConfigReader.getInstance().getChartPath());
        FileOperations.getInstance().initialiseFolder(ConfigReader.getInstance().getCsvReportFolderPath());
    }

    /**
     * Main method that launch the performance test
     * @param maxUsers	Number of total users to simulate
     * @param usersLoadPerSecond Number of total users spawned per second
     * @param testTime	Duration of the test execution
     * @param maxRPS Max number of requests per seconds (We recommend to initialise this with the same value as maxUsers for simplicity)
     * @param weight The user weight of the task executed (This is used when multiple test are executed concurrently at the same time)
     * @param nameTask The name of the performance task to be executed in the test.
     * @throws Exception an exception
     */
    public void doPretzel(Integer maxUsers, Integer usersLoadPerSecond, Integer testTime, Integer maxRPS, Integer weight, String nameTask) throws Exception {
    locustOperations.doPretzel(maxUsers,usersLoadPerSecond, testTime, maxRPS, weight, nameTask);
   }

    /**
     * Check if the response time of the test requests are higher than the expected time 
     * @param expectedTime The time in miliseconds
     * @return True if it's higher or false if it's lower.
     */
    public Boolean checkMaxResponseTimeAboveExpected(Long expectedTime) {
       return locustOperations.checkMaxResponseTimeAboveExpected(expectedTime);
   }

    /**
     * Generate the graphic chart with the test results and it returns the location path
     * @return The location path of the generated png file with the performance test results
     */
    public String getGeneratedChartFilePath () {
        BarChart locustBarChart = new BarChart();
        locustBarChart.createChart();
        File destinationPath = new File(ConfigReader.getInstance().getChartDir() + locustBarChart.getFileName());
        return destinationPath.toString();
    }

}
