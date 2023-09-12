# Smart Home API
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/gregorydossantos/projeto-sds3/blob/main/LICENSE)
<br/>This is a project from my graduate studies in architecture and Java software development at FIAP.

## About smart-home 
This project consists of creating a rest api, using the main technologies available on the market.

## Technologies:
- Java 17
- Spring-boot 3.1.0
- Database H2
- Postgres
- Docker

## Libraries:
- Lombok
- ModelMapper 3.1.1
- JUnit
- Mockito

### API Documentation
#### DDD
![Web 1](https://github.com/gregorydossantos/smart-home/blob/develop/assets/ddd-smart-home.png)

#### Database Model
![Web 1](https://github.com/gregorydossantos/smart-home/blob/develop/assets/entity-relationship.png)

#### Sequence Diagram - API People
![Web 1](https://github.com/gregorydossantos/smart-home/blob/develop/assets/people-post.png)
<br />
![Web 1](https://github.com/gregorydossantos/smart-home/blob/develop/assets/people-get.png)
<br />
![Web 1](https://github.com/gregorydossantos/smart-home/blob/develop/assets/people-put.png)
<br />
![Web 1](https://github.com/gregorydossantos/smart-home/blob/develop/assets/people-delete.png)

#### Notes:
To create the database and upload it locally, first confirm that you have docker installed on your machine, after that follow these steps:
<br/> - cd smart-home/docker
<br/> - sudo docker-compose up -d (to start database)
<br/> - sudo docker-compose down -d (to finish database)
### Endpoints (Swagger):
After running the project, we can access the API documentation through Swagger: <br/>
Link: http://localhost:8080/swagger-ui/index.html#/
  
### Observation
Project is still under development ... 