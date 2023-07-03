# smart-home
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