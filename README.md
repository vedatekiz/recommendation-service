# recommendation-service
This is a microservice that suggests games recommendations for a given customer username. 

Requirements
------------
* [Java Platform (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.x](http://maven.apache.org/)

Model
------------
* `customer` entity fields:
  `username` 
   
* `recommendation` entity fields:
   `text`

 Usage 
-----------
There are two endpoints:   <br>
* `/customers/upload`: upload csv file to create customer and recommendations
* `/customers/{username}/games/recommendations?count`: list `count` amount of recommendations for a given customer 

Both endpoints returns `ApiResponse` json object <br>
`ApiResponse` structure is as follows:
* `responseDescription`: Human readable description of api response
* `responseObject`: Actual data to retrieve  <br>
Sample api response:
```json{
{  
   "responseDescription":"Recommendation List Returned",
   "responseObject":[  
      {  
         "text":"bingo"
      },
      {  
         "text":"cashwheel"
      },
      {  
         "text":"sudoku"
      }
   ]
}
````

Restrictions
-----------
* Couple of customer and recommendations are inserted at the startup via CommandLineRunner 
* There is a unique constraint on `username` field on `Customer` entity

Build & Run
-----------
* `mvn install dockerfile:build`to build a docker image
* `docker push vedatekiz/recommendation` to push the image to the public `vedatekiz` repository on docker hub
* `kubectl create -f "deployment-recommendation.yaml"` to deploy the service in local minikube cluster


