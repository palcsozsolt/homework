# Backend Developer Test

### Task description

Calculate any possible land route from one country to another. 
The objective is to take a list of country data in JSON format and calculate the route by utilizing individual countries border information.


### API

The application exposes REST endpoint /routing/{origin}/{destination}
Example: 
 - Request GET /routing/CZE/ITA
 - Response 200 OK { "route": ["CZE", "AUT", "ITA"] }


### Starting the application

It requires JRE 11 or later.
Download as zip or clone the project.
Go to the project folder, 
 - build `mvnw clean install`
 - run `java -jar target/homework-0.0.1-SNAPSHOT.jar`
 
Use a HTTP client and ping the following url:
`http://localhost:8080/routing/PRT/MNG`
