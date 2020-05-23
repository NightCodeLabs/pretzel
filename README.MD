# Pretzel  
<img src="pretzel.png" width="250" align="center">
 
# Purpose
Pretzel is a tool that helps you to reuse existing Functional Tests in popular frameworks like RestAssured to do Load and Performance Tests with Java.  

# Background  
Teams use BDD to drive development based on the user behaviour, but stories never go beyond a single user, defects are found very late within development or even in production and there are no service level agreements, only actual user behaviour!  
We need an easy, reusable way to tackle this, which would enable our development teams to drive load and performance testing into their day-to-day activities: from requirements gathering, to test automation as well as having part of the delivery pipeline, reusing the existing Java, RestAssured and Cucumber tests.  
We have build Pretzel to help us with all of the above.  
  
  
# How to use  
### Add the Dependency in your pom.xml
``` 
    <dependency>  
    	<groupId>com.github.nightcodelabs</groupId>  
    	<artifactId>pretzel</artifactId>  
    	<version>0.0.1-SNAPSHOT</version>   
    </dependency>  
```  

### Start using Pretzel in your project
- Create a class that will be used to execute a load and performance test in ```src/main/java/pretzel``` extending ```com.github.nightcodelabs.pretzel.performance.Task```  
- In the ```execute``` method, reference the piece of code you want to reuse from your functional tests  
- In your favorite test framework, call pretzel.doPretzel with the needed parameters  
- Execute the test, you will find the generated reports in target/pretzel  
- Initialize the reporting in the ```@before``` of the test execution  
- Process the report images in the ```@after``` of your test execution  

Since we've built Pretzel with the idea to use it with Cucumber in BDD fashion, we've built this [project example](https://github.com/NightCodeLabs/pretzel-example) to guide you.
  
### Installing Dependencies  
#### Windows  
- Install latest JDK  
- Install python   
- Install Visual Studio Code / Build Tools for C++ (Yeah, locustio doesn't really work without these build tools because a weird dependency)      
- In command line run:  
 ```  
  $ pip install locustio==0.13.5  
 ```  
- Add locustio to your env. path  
- Restart cmd  
- Install maven
- In command line run:   
 ```  
  $ mvn install  
 ``` 
 
#### Linux  
- Install JDK  
- Install Python:  
 ```  
  $ sudo apt install pip 
 ```  
- Install Locustio:  
 ```  
  $ pip install locustio==0.13.5   
 ```    
- Add Locustio to env path:  
```  
  $ vi  ~/.bash_rc  
  $ export PATH=$PATH:~/.local/bin
   ```  
- restart terminal  
- Install maven:  
```  
  $ sudo apt install maven
   ```  
- In command line run:   
 ```  
  $ mvn install  
 ```   

### Configurable Properties  
We offer the possibility to personalize some default configurations.     
You only need to create a config.properties file into ```src/main/resources/config```.  
The name of the personalizable variables can be found [here](src/main/java/com/github/nightcodelabs/pretzel/helpers/ConfigReader.java).  

Make sure you know what you're doing, otherwise this might break the execution.  

# How to contribute
We're super open to contributors. If you have an idea, open an issue, and we'll discuss it.  
If you have a solution, fork our repo and submit a PR.  