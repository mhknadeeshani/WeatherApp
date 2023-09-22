## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.vanguard.weather.WeatherApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

API keys stored in ApiKeyRepository where you can use when sending requests.

```shell
mvn spring-boot:run
```

## Sending Request
* Application port : 8080
* Input parameters will be countryName and cityName. 
* Api key will be sent as a header parameter.

Example request: [http://localhost:8080/weather?countryName=Sri Lanka&city=colombo]()
<br /> Header parameter : apiKey = 821ea665e94bc5a0ee2456c41de5f3ea

## Assumptions
* With in one-hour weather condition will not change. 
* Therefore, data will be retrieved from database for the repeated calls for the  same country and city with in one hour. 
 

