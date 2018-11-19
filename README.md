# hellofresh_api
Test groupkt webservice with restAssured

## Contents 
- Get all countries and validate that US, DE and GB were returned in the response
- Get each country (US, DE and GB) individually and validate the response
- Try to get information for inexistent countries and validate the response
- This API has not a POST method at the moment, but it is being developed. Write a test that would validate new country addition using POST(it will not work now, but no worries).
Example of json body for POST is below:
  {
     name: "Test Country",
     alpha2_code: "TC",
     alpha3_code: "TCY"
  }
  
## requirements
jdk 1.8 
maven 3.1 

## How to run 

clone repository, run 

``` mvn clean build ``` 

``` mvn test ```

