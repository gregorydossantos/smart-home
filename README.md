# Smart Home API
This is a project from my graduate studies in architecture and Java software development at FIAP.

## About smart-home 
This project consists of creating a rest api, using the main technologies available on the market.

## Technologies:
- Java 17
- Spring-boot 3.1.0
- Database H2 

## Libraries:
- Lombok
- ModelMapper 3.1.1
- JUnit
- Mockito

### API Documentation
- Endpoints:
  - /address-register:<br/>
  Entry (request) <br/>
  {<br/>
    "street" : "String",<br/>
    "number" : "String",<br/>
    "district" : "String",<br/>
    "city" : "String",<br/>
    "state" : "String(2)"<br/>
  }<br/>
  Output (response)<br/>
  Status Code: 201 (CREATED)<br/>
  Status Code: 400 (BAD_REQUEST)<br/>
  {<br/>
    "timestamp": "2023-07-03T13:21:34.909056875",<br/>
    "status": 400,<br/>
    "error": "Erro no contexto da requisição!",<br/>
    "path": "/address-register"<br/>
  }<br/>
  Status Code: 400 (BAD_REQUEST)<br/>
    {<br/>
    "timestamp": "2023-07-03T13:21:34.909056875",<br/>
    "status": 400,<br/>
    "error": "Endereço já cadastrado!",<br/>
    "path": "/address-register"<br/>
    }<br/>
  - /home-appliance:<br/>
    Entry (request) <br/>
    {<br/>
    "name" : "String",<br/>
    "model" : "String",<br/>
    "brand" : "String",<br/>
    "voltage" : "String"<br/>
    }<br/>
    Output (response)<br/>
    Status Code: 201 (CREATED)<br/>
    Status Code: 400 (BAD_REQUEST)<br/>
    {<br/>
    "timestamp": "2023-07-03T13:21:34.909056875",<br/>
    "status": 400,<br/>
    "error": "Erro no contexto da requisição!",<br/>
    "path": "/address-register"<br/>
    }<br/>
    Status Code: 400 (BAD_REQUEST)<br/>
    {<br/>
    "timestamp": "2023-07-03T13:21:34.909056875",<br/>
    "status": 400,<br/>
    "error": "Eletrodoméstico já cadastrado!",<br/>
    "path": "/home-appliance"<br/>
    }<br/>
  - /people:<br/>
    Entry (request) <br/>
    {<br/>
    "name" : "String",<br/>
    "birthday" : "String",<br/>
    "gender" : "String(1)",<br/>
    "parentage" : "String",<br/>
    "ativo" : "String"<br/>
    }<br/>
    Output (response)<br/>
    Status Code: 201 (CREATED)<br/>
    Status Code: 400 (BAD_REQUEST)<br/>
    {<br/>
    "timestamp": "2023-07-03T13:21:34.909056875",<br/>
    "status": 400,<br/>
    "error": "Erro no contexto da requisição!",<br/>
    "path": "/people"<br/>
    }<br/>
    Status Code: 400 (BAD_REQUEST)<br/>
    {<br/>
    "timestamp": "2023-07-03T13:21:34.909056875",<br/>
    "status": 400,<br/>
    "error": "Pessoa já cadastrada!",<br/>
    "path": "/people"<br/>
    }<br/>
  
### Observation
Project is still under development ... 