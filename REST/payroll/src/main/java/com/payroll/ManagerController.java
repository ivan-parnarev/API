package com.payroll;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ManagerController {

    private final ManagerRepository repository;
    private final ManagerRepresentationModelAssembler assembler;

    ManagerController(ManagerRepository repository, ManagerRepresentationModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Look up all managers, and transform them into a REST collection resource using
     * {@link ManagerRepresentationModelAssembler#toCollectionModel(Iterable)}. Then return them through Spring Web's
     * {@link ResponseEntity} fluent API.
     */
    @GetMapping("/managers")
    ResponseEntity<CollectionModel<EntityModel<Manager>>> findAll() {

        return ResponseEntity.ok( //
                assembler.toCollectionModel(repository.findAll()));

    }

    /**
     * Look up a single {@link Manager} and transform it into a REST resource using
     * {@link ManagerRepresentationModelAssembler#toModel(Object)}. Then return it through Spring Web's
     * {@link ResponseEntity} fluent API.
     *
     * @param id
     */
    @GetMapping("/managers/{id}")
    ResponseEntity<EntityModel<Manager>> findOne(@PathVariable long id) {

        return repository.findById(id) //
                .map(assembler::toModel) //
                .map(ResponseEntity::ok) //
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Find an {@link Employee}'s {@link Manager} based upon employee id. Turn it into a context-based link.
     *
     * @param id
     */
    @GetMapping("/employees/{id}/manager")
    ResponseEntity<EntityModel<Manager>> findManager(@PathVariable long id) {

        return ResponseEntity.ok( //
                assembler.toModel(repository.findByEmployeesId(id)));
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}