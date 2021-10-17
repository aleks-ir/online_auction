# Online Auction test project

This is sample 'Online Auction' web application.

## Requirements

* JDK 11
* Apache Maven

## Build application:
```
mvn clean install
```

## Rest server

### Local tests with H2 in-memory

This starts Tomcat and serves up your rest-app project on http://localhost:8088:
```
java -jar rest-app/target/rest-app-1.0-SNAPSHOT.jar
```

This starts Tomcat and serves up your web-app project on http://localhost:8080.
```
java -jar web-app/target/web-app-1.0-SNAPSHOT.jar 
```