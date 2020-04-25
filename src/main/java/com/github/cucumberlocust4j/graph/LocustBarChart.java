package com.github.cucumberlocust4j.graph;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.cucumberlocust4j.helpers.ConfigReader;
import com.github.cucumberlocust4j.helpers.FileOperations;


public class LocustBarChart {
		
	private static final Logger logger = LoggerFactory.getLogger(LocustBarChart.class);
	private static final String TITLE = "Performance Execution Results";
    private static final String CATEGORYAXISLABELTITLE = "Load Distribution";
    private static final String VALUEAXISLABELTITLE = "Requests";
    private static final int CHARTWIDTH= 1400;
    private static final int CHARTHEIGHT=800;
    private static final String ERROR = "ERROR CREATING THE GRAPH";
    
    private String fileName;

    public String getFileName() {
    	return this.fileName;
    }    
    
    /**
     * Create the chart according to the csv perfromance results
     * @param testResultsIteration the index of the csv results in case of the csv contains more than one line results
     */
	public void createChart() {
		this.fileName = "performanceChart"+System.currentTimeMillis()+".png";
		File file = new File(ConfigReader.getInstance().getChartPath()+"/"+this.fileName);
        JFreeChart chart = ChartFactory.createBarChart(TITLE,CATEGORYAXISLABELTITLE,VALUEAXISLABELTITLE, createDataset(ConfigReader.getInstance().getStatsReportPath()), PlotOrientation.VERTICAL,false,true,false);
        chart.addSubtitle(0, new TextTitle(" "));
        chart.addSubtitle(1, new TextTitle(this.createRequestResults(ConfigReader.getInstance().getStatsReportPath())));
        chart.addSubtitle(2, new TextTitle(" "));
        try {
            ChartUtils.saveChartAsPNG(file, chart, CHARTWIDTH, CHARTHEIGHT);
        } catch (IOException error) {
            logger.error(ERROR);
        }
    }
    
    private CategoryDataset createDataset(String path){
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	List<String[]> datasetData = FileOperations.getInstance().readCSV(path);
    	for (int i= 11; i<datasetData.get((datasetData.size()-1)).length; i++) {
    		dataset.setValue(Integer.parseInt(datasetData.get((datasetData.size()-1))[i]), "Requests", datasetData.get(0)[i]);    		
    	}
    	return dataset;
    }
    
    private String createRequestResults(String path){
    	List<String[]> requestData = FileOperations.getInstance().readCSV(path);
    	String requestResults = "";
    	for (int i=2; i<(requestData.get((requestData.size()-1)).length-12); i++) {
    		requestResults += requestData.get(0)[i]+": "+requestData.get((requestData.size()-1))[i]+"  |  ";
    		
    	}
    	return requestResults;
    }   
  

}
