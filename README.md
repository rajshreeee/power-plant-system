# Virtual Power Plant

A spring boot sample project that implements POST and GET APIs.

### API request/response

-----------------------

#### API to save a list of batteries
```
POST: localhost:8080/api/batteries
```

Example Request:
```
[  
    {
        "name": "batteryA",
        "postCode": 10,
        "wattCapacity": 3
    },
      {
        "name": "batteryB",
        "postCode": 10,
        "wattCapacity": 3
    },  
        {
        "name": "batteryD",
        "postCode": 15,
        "wattCapacity": 3
    },
        {
        "name": "batteryC",
        "postCode": null,
        "wattCapacity": 3
    }
]
```

Example Response
```
{
    "data": [
        {
            "id": "63c4ea3501d6a047953448a5",
            "name": "batteryA",
            "postCode": 10,
            "wattCapacity": 3
        },
        {
            "id": "63c4ea3501d6a047953448a6",
            "name": "batteryB",
            "postCode": 10,
            "wattCapacity": 3
        },
        {
            "id": "63c4ea3501d6a047953448a7",
            "name": "batteryD",
            "postCode": 15,
            "wattCapacity": 3
        },
        {
            "id": "63c4ea3501d6a047953448a8",
            "name": "batteryC",
            "postCode": null,
            "wattCapacity": 3
        }
    ],
    "metadata": null
}
```

#### API to fetch batteries
```
GET: localhost:8080/api/batteries?sort=name&includeMetaData=true&minPostCode=10&maxPostCode=15
```

Example Response
```
{
    "data": [
        {
            "id": "63c4ea3501d6a047953448a5",
            "name": "batteryA",
            "postCode": 10,
            "wattCapacity": 3
        },
        {
            "id": "63c4ea3501d6a047953448a6",
            "name": "batteryB",
            "postCode": 10,
            "wattCapacity": 3
        },
        {
            "id": "63c4ea3501d6a047953448a7",
            "name": "batteryD",
            "postCode": 15,
            "wattCapacity": 3
        }
    ],
    "metadata": {
        "avgWattCapacity": 3.0,
        "totalWattCapacity": 9
    }
}
```

### Tech stack

---------------
- Java 17
- MongoDB
- Maven
- Spring Boot
- Docker

### Features

-------------

- Logging
- Exception Handling
- Validation
- Unit/Integration tests
- Dockerized application