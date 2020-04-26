package runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.cucumberlocust4j.pretzel.helpers.ConfigReader;
import com.github.cucumberlocust4j.pretzel.helpers.FileOperations;
import com.vimalselvam.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue={"steps"},
     	tags = {"~@ignore"},
        plugin = {"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
        monochrome = true
)

public class TestRunner {

		private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

		@BeforeClass
		public static void cleanLocustChartsDirectory() {
			//Delete and create the locustcharts folder and csv folder in order to ensure that exists in every execution
			FileOperations.getInstance().folderInitialisation(ConfigReader.getInstance().getChartPath(), ConfigReader.getInstance().getCsvReportFolderPath());
		}

	    @AfterClass
	    public static void writeExtentReport() {
	        try {
	            Reporter.loadXMLConfig(new File(ConfigReader.getInstance().getExtentReportConfigPath()));
	        } catch (NoSuchMethodError ex){
	            logger.error("Something went wrong writting in the Extent Report");
	        }

	    }


}
