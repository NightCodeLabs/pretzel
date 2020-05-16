package com.github.cucumberlocust4j.pretzel;

import com.github.cucumberlocust4j.pretzel.graph.LocustBarChart;
import com.github.cucumberlocust4j.pretzel.helpers.ConfigReader;
import com.github.cucumberlocust4j.pretzel.helpers.FileOperations;
import com.github.cucumberlocust4j.pretzel.performance.LocustOperations;

import java.io.File;


// Entrypoint class for the different functionalities of pretzel
public class Pretzel {
    LocustOperations locustOperations = new LocustOperations();

    public void cleanChartsDirectory() {
        //Delete and create the locustcharts folder and csv folder in order to ensure that exists in every execution
        FileOperations.getInstance().folderInitialisation(ConfigReader.getInstance().getChartPath(), ConfigReader.getInstance().getCsvReportFolderPath());
    }

    public void executePerformanceTask(Integer maxUsers, Integer usersLoadPerSecond, Integer testTime, Integer maxRPS, Integer weight, String nameTask) throws Exception {
    locustOperations.executePerformanceTask(maxUsers,usersLoadPerSecond, testTime, maxRPS, weight, nameTask);
   }

    public Boolean checkMaxResponseTime(Long expectedTime) {
       return locustOperations.checkMaxResponseTime(expectedTime);
   }

    public String generateReportFilePath () {
        LocustBarChart locustBarChart = new LocustBarChart();
        locustBarChart.createChart();
        File destinationPath = new File(ConfigReader.getInstance().getChartDir() + locustBarChart.getFileName());
        return destinationPath.toString();
    }

}
