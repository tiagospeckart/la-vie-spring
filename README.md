# la-vie-spring

![Java build CI with Maven](https://github.com/tiagospeckart/la-vie-spring/actions/workflows/build.yml/badge.svg)
![Maven Tests passed](https://github.com/tiagospeckart/la-vie-spring/actions/workflows/test.yml/badge.svg)

Backend server MVP that manages a REST API for a psychotherapy clinic.

This project is a rebuild of my first API, originally developed in JavaScript: [la-vie-backend](https://github.com/tiagospeckart/la-vie-backend).

Created as part of the SpringBoot module in the +Devs2Blu program, 2023.

---
## Specification

- [x] Backend software developed using SpringBoot
- [x] Data persistence through Postgresql
- [x] UML Class Diagram
- [x] Insomnia file with comprehensive API call examples
- [x] API documentation using Swagger
- [x] Requisite List
  - [x] Functional Requirements
  - [x] Non-Functional Requirements
  - [x] Business Rules
- [x] Minimum test coverage of 50% (Currently 54%)
- [x] Database migrations using Flyway
- [x] Build automation through Maven
- [x] Adherence to clean code principles
- [x] Data normalization process
---

Documentation chapters will be organized as Wiki this [Repository Wiki](https://github.com/tiagospeckart/la-vie-spring/wiki) articles:

1. [The Story](https://github.com/tiagospeckart/la-vie-spring/wiki/The-Story)
2. [Functional Requirements](https://github.com/tiagospeckart/la-vie-spring/wiki/Functional-Requirements)
3. [Non-functional Requirements](https://github.com/tiagospeckart/la-vie-spring/wiki/Non%E2%80%90functional-Requirements)
4. [Business Rules](https://github.com/tiagospeckart/la-vie-spring/wiki/Business-Rules)
5. [UML Class Diagram](https://github.com/tiagospeckart/la-vie-spring/wiki/UML-Class-Diagram)

---

## Installation

### Docker

#### Prerequisites

Make sure you have Docker and Docker Compose installed on your system.

#### Steps

1. Clone the repo

```bash
git clone https://github.com/tiagospeckart/la-vie-spring.git
cd la-vie-spring
```

2. Build the Spring Boot Application
```bash
docker-compose build
```
3. Start the containers

```bash
docker-compose up -d
```

### Local

You can use the `dev` profile in `application.yml` to start with a **H2** virtual database, or use Postgres if you have it. 
Manually create a `psych-clinic` database

---

## Swagger documentation

Access `http://{context-path}:{port}/api/swagger-ui/index.html` after SpringBoot initialization.
On local machines, its `http://localhost:8080/api/swagger-ui/index.html`
