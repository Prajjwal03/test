# Epix treasury app

## Deploying in Jboss

* Use below maven command to create war in target folder
```
mvn clean package
```
* Copy the _EpixTreasuryApp.war_ file in target folder and deploy it in jboss.

## Running local

To run locally,

* Use below maven command and run/debug the application
```
spring-boot:run -Pdevelopment
```

* Hit the below endpoint, to fetch list of applications
```
http://localhost:8080/table/applications
```

>NOTE: You might see exceptions as NGLogger will not work in local.

## Yet to be done
- Handle logging while running locally
- Remove unused dependencies (https://www.baeldung.com/maven-unused-dependencies)
- Add Actuator endpoints (Should be accessible only in local)
