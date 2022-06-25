# Spring boot test

A Springboot API that exposes two services

- One that allows to register a user
- One that displays the details of a registered user 


# REST API

## Register user

### Request

http://localhost:8080/register

    {
        "username" : "jeanmichel",
        "birthDate" : "1995-12-01",
        "countryOfResidence" : "FR",
        "phoneNumber": 33145453245,
        "gender": "M"
    }

`username`, `birthDate` and `countryOfResidence` are required <br>`phoneNumber` and `gender` are optional <br>`birthDate` must be in the format "yyyy-MM-dd" and correspond to and adult user <br>`countryOfResidence` must be the ISO code of France

### Response

    Status : 201 CREATED

    {
        "id": 1,
        "username": "jeanmichel",
        "birthDate": "1995-12-01T00:00:00.000+0000",
        "countryOfResidence": "FR",
        "phoneNumber": 33145453245,
        "gender": "M"
    }


## Get a registered user

### Request

http://localhost:8080/user?username=jeanmichel

### Response

    Status: 200 OK
    {
        "id": 1,
        "username": "jeanmichel",
        "birthDate": "1995-12-01T00:00:00.000+0000",
        "countryOfResidence": "FR",
        "phoneNumber": 33145453245,
        "gender": "M"
    }
