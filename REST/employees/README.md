# RESTful API - Payroll Service

Java application implemented with:
    - Spring Framework
        - Spring Boot
        - Spring Web
        - Spring JPA
        - Spring HATEOAS

Endpoint:
http://localhost:8080/

Queries:
GET:
curl -v localhost:8080/employees

POST:
curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "George Buh", "role": "president"}'

PUT:
curl -X PUT localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "George Buh", "role": "soldier"}'

DELETE:
curl -X DELETE localhost:8080/employees/3


Quickstart:

1. Clone repository.
2. Edit 'application.properties' if necessary.
3. Run './mvnw clean spring-boot:run' in 'employees' directory
