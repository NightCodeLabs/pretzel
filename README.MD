# Locust Api Test  
[![Build Status](https://travis-ci.com/cucumber-locust4j/locustapitest.svg?branch=master)](https://travis-ci.com/cucumber-locust4j/locustapitest)
# Purpose  
Teams use BDD to drive development based on the user behaviour, but stories never go beyond a single user, defects are found very late within development or even in production and there are no service level agreements, only actual user behaviour!  
We need an easy, reusable way to tackle this, which would enable our development teams to drive load and performance testing into their day-to-day activities: from requirements gathering, to test automation as well as having part of the delivery pipeline, reusing the existing Java, RestAssured and Cucumber tests.
  
## Installation guide  
### Containerized Execution  
- Install docker in your machine
- Run
 ```  
  $ docker-compose up --build -d
 ``` 
- Copy Report  
 ```  
  $ docker cp locustapitest:/locustapitest/report.zip .
 ``` 

### Local execution  
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

#### Running specific tests
- To run a test with specific tags
 ```  
  $ mvn test -Dcucumber.options="--tags @Functional"
 ```