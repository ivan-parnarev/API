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
curl -v -X POST localhost:8080/employees -H 'Content-Type:application/json' -d '{"firstName": "George", "lastName": "Buh", "role": "president"}'

PUT:
curl -X PUT localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "George Buh", "role": "soldier"}'
curl -v -X PUT localhost:8080/employees/3 -H 'Content-Type:application/json' -d '{"firstName": "George", "lastName": "Popo", "role": "soldier"}'

DELETE:
curl -X DELETE localhost:8080/employees/3

Quickstart:

1. Clone repository.
2. Edit 'application.properties' if necessary.
3. Run './mvnw clean spring-boot:run' in 'employees' directory.


Spring HATEOAS
- EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links.

- linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel() asks that Spring HATEOAS build a link to the EmployeeController 's one() method, and flag it as a self link.

- linkTo(methodOn(EmployeeController.class).all()).withRel("employees") asks Spring HATEOAS to build a link to the aggregate root, all(), and call it "employees".

- What do we mean by "build a link"? One of Spring HATEOAS’s core types is Link. It includes a URI and a rel (relation). Links are what empower the web. 
- Before the World Wide Web, other document systems would render information or links, but it was the linking of documents WITH this kind of relationship metadata that stitched the web together.

- Spring HATEOAS’s abstract base class for all models is RepresentationModel. But for simplicity, EntityModel<T> as a mechanism easily wraps all POJOs as models.

Tips:
curl -v localhost:8080/employees/1 | json_pp
