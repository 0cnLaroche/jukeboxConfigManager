# Jukebox Configuration Manager

This API is delivered to Touchtunes by Samuel Laroche as part of the Java Backend developer hiring process

### Installation

Requirements : 
- JRE >= 1.8
- Maven
- _(optional)_ Docker

#### Run

```shell
mvn spring-boot:run
```
Default port is 8080. After running the app, services can be accessed at :
```
http://localhost:8080
```

#### Build

````shell
mvn clean install
````

#### Build Docker Container

````shell
mvn clean package
docker build -t touchtunes/jukebox-config-manager .
````

#### Run docker image

````shell
docker run -p 8080:8080 touchtunes/jukebox-config-manager
````

### Services

#### Find Jukebox for setting

Finds jukeboxes supporting the given setting. Jukebox must have all required components for the provided setting. See API docs or Swagger for optional query parameters documentation.

**GET**
```
/jukebox/{settingId}?model={name}&limit={pageSize}&offset={page}
```


### API documentation

While running the server, documentation can be accessed to the following URIs.

#### OpenAPI V3
````
/v3/api-docs
````

#### Swagger
````
/swagger-ui.html
````
