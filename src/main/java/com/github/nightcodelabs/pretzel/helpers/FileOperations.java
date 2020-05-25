package com.github.nightcodelabs.pretzel.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;

/**
 * Contains different methods that manage operations with files
 */
public class FileOperations {

	private static final String LOCUSTMASTERFILEPATH = ConfigReader.LOCUSTMASTERFILEPATH;
	private static final String LOCUSTMASTERCOMPLETEPATH = ConfigReader.getInstance().getLocustMasterFilePath();
	private static final Logger logger = LoggerFactory.getLogger(FileOperations.class);
	private static FileOperations _instance = new FileOperations();
	
	private FileOperations() {}
	
	public static FileOperations getInstance() {
		return _instance;
	}
    
	/**
	 * Read a CSV file and return a list with the values
	 * @param pathFile Path of the CSV file.
	 * @return The List with the contents of the CSV file
	 */
    public List<String[]> readCSV(String pathFile){
    	List<String[]> list = new ArrayList<>();
	    String[] fila = null;
    	CSVReader csvReader;
		try {
			csvReader = new CSVReader(new FileReader(pathFile));
			while((fila = csvReader.readNext()) != null) {    		
				   list.add(fila);
		}
		csvReader.close();
		}catch (FileNotFoundException e) {
			logger.error("Something went wrong reading the CSV Report file");
		} catch (IOException e) {
			logger.error("Something went wrong reading the CSV Report file");
		}
		
    	return list;
    } 
    
    /**
     * Delete directory if exist and recreates it
     * @param path Path of the folder
     */
    public void initialiseFolder(String path) {
  	  try {
  		  FileUtils.deleteDirectory(new File(path));
  		  FileUtils.forceMkdir(new File(path));
  	  } catch (IOException e) {
  		  logger.error("Something went wrong initialising the "+ path +" directory");
  	  }			
     }
     
    /**
     * Creates the locust master file is not specified by the user
     * @return Path of the locust master file 
     */
    public String getGeneratedLocustMasterFilePathIfNotSpecfified() {
  	  String locustFilePath = ConfigReader.getInstance().getLocustMasterFilePath();
  	  if(locustFilePath.contains(LOCUSTMASTERFILEPATH)) {
  		  this.initialiseFolder(LOCUSTMASTERFILEPATH);
  		  this.createLocustMasterFile();
  	  }
  	  return locustFilePath;
    }
   
    /**
     * Creates the locust Master .py file with the dummy server
     */
   private void createLocustMasterFile() {
	   String[] locustMasterFileLines = { "from locust import Locust, TaskSet, task",
			   "class DummyTask(TaskSet):",
			   "\t@task(1)",
			   "\tdef dummy(self):",
			   "\t\tpass",
			   "class Dummy(Locust):", 
			   "\ttask_set = DummyTask"
	   	};

	   FileWriter file = null;
	   try {
		   file = new FileWriter(LOCUSTMASTERCOMPLETEPATH);

		   for (String locustMasterFileLine : locustMasterFileLines) {
			   file.write(locustMasterFileLine + "\n");
		   }
		   file.close();
	   } catch (Exception e) {
		   logger.error("Something went wrong creating the locust-master.py. Info: "+ e.getMessage());
	   }
   }
   
    
	
}
