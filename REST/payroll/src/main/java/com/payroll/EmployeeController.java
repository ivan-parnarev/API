package com.payroll;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeRepresentationModelAssembler assembler;
    private final EmployeeWithManagerResourceAssembler employeeWithManagerResourceAssembler;

    EmployeeController(EmployeeRepository repository, EmployeeRepresentationModelAssembler assembler,
                       EmployeeWithManagerResourceAssembler employeeWithManagerResourceAssembler) {

        this.repository = repository;
        this.assembler = assembler;
        this.employeeWithManagerResourceAssembler = employeeWithManagerResourceAssembler;
    }

    /**
     * Look up all employees, and transform them into a REST collection resource using
     * {@link EmployeeRepresentationModelAssembler#toCollectionModel(Iterable)}. Then return them through Spring Web's
     * {@link ResponseEntity} fluent API.
     */
    @GetMapping("/employees")
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> findAll() {

        return ResponseEntity.ok(assembler.toCollectionModel(repository.findAll()));

    }

    /**
     * Look up a single {@link Employee} and transform it into a REST resource using
     * {@link EmployeeRepresentationModelAssembler#toModel(Object)}. Then return it through Spring Web's
     * {@link ResponseEntity} fluent API.
     *
     * @param id
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<EntityModel<Employee>> findOne(@PathVariable long id) {

        return repository.findById(id) //
                .map(assembler::toModel) //
                .map(ResponseEntity::ok) //
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Find an {@link Employee}'s {@link Manager} based upon employee id. Turn it into a context-based link.
     *
     * @param id
     * @return
     */
    @GetMapping("/managers/{id}/employees")
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> findEmployees(@PathVariable long id) {

        CollectionModel<EntityModel<Employee>> collectionModel = assembler
                .toCollectionModel(repository.findByManagerId(id));

        Links newLinks = collectionModel.getLinks().merge(Links.MergeMode.REPLACE_BY_REL,
                linkTo(methodOn(EmployeeController.class).findEmployees(id)).withSelfRel());

        return ResponseEntity.ok(CollectionModel.of(collectionModel.getContent(), newLinks));
    }

    @GetMapping("/employees/detailed")
    public ResponseEntity<CollectionModel<EntityModel<EmployeeWithManager>>> findAllDetailedEmployees() {

        return ResponseEntity.ok( //
                employeeWithManagerResourceAssembler.toCollectionModel( //
                        StreamSupport.stream(repository.findAll().spliterator(), false) //
                                .map(EmployeeWithManager::new) //
                                .collect(Collectors.toList())));
    }

    @GetMapping("/employees/{id}/detailed")
    public ResponseEntity<EntityModel<EmployeeWithManager>> findDetailedEmployee(@PathVariable Long id) {

        return repository.findById(id) //
                .map(EmployeeWithManager::new) //
                .map(employeeWithManagerResourceAssembler::toModel) //
                .map(ResponseEntity::ok) //
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}