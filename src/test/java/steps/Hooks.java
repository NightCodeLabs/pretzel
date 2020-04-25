package steps;

import java.io.File;
import java.io.IOException;

import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import graph.LocustBarChart;
import helpers.ConfigReader;

public class Hooks {
	
	 @Before
	 public void BeforeSteps() throws IOException {
		 	//Setup the Report basic information
	        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
	        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
	        Reporter.setSystemInfo("Machine", System.getProperty("os.name"));
	 }

	@After(order = 0)
	public void AfterSteps(Scenario scenario) throws IOException {
			if (scenario.getSourceTagNames().contains("@Performance")) {
			//Create the chart after Scenario iteration
			LocustBarChart locustBarChart = new LocustBarChart();
			locustBarChart.createChart();
			File destinationPath = new File(ConfigReader.getInstance().getChartPath() + locustBarChart.getFileName());
			//Add the image to the Report
			Reporter.addScreenCaptureFromPath(destinationPath.toString(), "Performance Results");
		}

	}

}
