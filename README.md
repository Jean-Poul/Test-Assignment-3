# Assignment 3  
  
This assignment has been solved with **Java** and has been done with the use of Unit and Integration test combined with frameworks such as:  
- Faker
- Flyway 
- JaCoCo
- JUnit 5  
- Maven
- Mockito
- PITest
- PMD

Furthermore, Docker has been used to spin up a MySQL docker container to be able to test against the data stored in this container.  
    
## 1.0 Booking system :computer: 
*I have been given the task to implement and test a booking system that involves Booking, Customer and Employee services*  

### 1.1 In-depth explanation
*Further explanation for this assignment can be found at the following link: :point_right:*  
[Assignment 3](https://www.example.com)  
  
**Results: **  
![Green test](https://github.com/Jean-Poul/Test-Assignment-3/blob/main/pictures/results.PNG)  
  
### 1.2 Instructions

Use the following command to run a MySQL docker container:  
`docker run -d --rm --name mysql-test-db -e MYSQL_ROOT_PASSWORD=testuser123 -p 3307:3306 mysql`  
  
To make a clean build use the following command:  
`mvn clean install`  
  
To run all tests use the following command:  
`mvn test`    
  
To make a JaCoCo report use the following command:  
`mvn jacoco:report`  

To get a PMD report use the following command:  
`mvn compile site`
    
To run mutation test use the following command:  
`mvn test-compile org.pitest:pitest-maven:mutationCoverage`
  
**All generated reports can be found in the target/site/ folder.**    
  
High five :raised_hands:
  
---  
    
#### 2.0 TODO    
- Refactor code logic
- Refactor and split up tests
- Refactoring to kill more mutators  
- Test for not null and if an exception has been thrown  
- Make negative tests
- Refactor sms "system" code
- Clean the docker MySQL database before test (better solution would be to use an in-memory database like H2 for testing)
