# la-vie-spring

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
- [ ] Minimum test coverage of 50%
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

Localhost installation steps can be quite tricky, so later I'll be working towards containerization with Docker, and after that, a remote deploy.
While it doesn't happen, this application with run if you have Postgres installed, and manually create a Database called "psych-clinic" within your postgre local connection.

---

## Swagger documentation

Access `http://{context-path}:{port}/api/swagger-ui` after SpringBoot initialization.
On local machines, its `http://localhost:8080/api/swagger-ui/`