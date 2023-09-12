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
### Endpoints:
#### /address-register:
    Entry (request)
    {
      "street" : "String",
      "number" : "String",
      "district" : "String",
      "city" : "String",
      "state" : "String(2)"
    }
    Output (response)
    Status Code: 201 (CREATED)
    Status Code: 400 (BAD_REQUEST)
    {
      "timestamp": "2023-07-03T13:21:34.909056875",
      "status": 400,
      "error": "Erro no contexto da requisição!",
      "path": "/address-register"
    }
    Status Code: 400 (BAD_REQUEST)
      {
        "timestamp": "2023-07-03T13:21:34.909056875",
        "status": 400,
        "error": "Endereço já cadastrado!",
        "path": "/address-register"
      }
#### /home-appliance:
    Entry (request)
    {
      "name" : "String",
      "model" : "String",
      "brand" : "String",
      "voltage" : "String"
    }
    Output (response)
    Status Code: 201 (CREATED)
    Status Code: 400 (BAD_REQUEST)
    {
      "timestamp": "2023-07-03T13:21:34.909056875",
      "status": 400,
      "error": "Erro no contexto da requisição!",
      "path": "/address-register"
    }
    Status Code: 400 (BAD_REQUEST)
    {
      "timestamp": "2023-07-03T13:21:34.909056875",
      "status": 400,
      "error": "Eletrodoméstico já cadastrado!",
      "path": "/home-appliance"
    }
#### /people:
    Entry (request)
    {
      "name" : "String",
      "birthday" : "String",
      "gender" : "String(1)",
      "parentage" : "String",
      "ativo" : "String"
    }
    Output (response)
    Status Code: 201 (CREATED)
    Status Code: 400 (BAD_REQUEST)
    {
      "timestamp": "2023-07-03T13:21:34.909056875",
      "status": 400,
      "error": "Erro no contexto da requisição!",
      "path": "/people"
    }
    Status Code: 400 (BAD_REQUEST)
    {
      "timestamp": "2023-07-03T13:21:34.909056875",
      "status": 400,
      "error": "Pessoa já cadastrada!",
      "path": "/people"
    }
  
### Observation
Project is still under development ... 