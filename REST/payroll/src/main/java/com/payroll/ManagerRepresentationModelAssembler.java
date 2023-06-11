package com.payroll;

import com.payroll.commons.SimpleIdentifiableRepresentationModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class ManagerRepresentationModelAssembler extends SimpleIdentifiableRepresentationModelAssembler<Manager> {

    ManagerRepresentationModelAssembler() {
        super(ManagerController.class);
    }

    /**
     * Retain default links provided by {@link SimpleIdentifiableRepresentationModelAssembler}, but add extra ones to each
     * {@link Manager}.
     *
     * @param resource
     */
    @Override
    public void addLinks(EntityModel<Manager> resource) {
        /**
         * Retain default links.
         */
        super.addLinks(resource);

        resource.getContent().getId() //
                .ifPresent(id -> { //
                    // Add custom link to find all managed employees
                    resource.add(linkTo(methodOn(EmployeeController.class).findEmployees(id)).withRel("employees"));
                });
    }

    /**
     * Retain default links for the entire collection, but add extra custom links for the {@link Manager} collection.
     *
     * @param resources
     */
    @Override
    public void addLinks(CollectionModel<EntityModel<Manager>> resources) {

        super.addLinks(resources);

        resources.add(linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees"));
        resources.add(linkTo(methodOn(EmployeeController.class).findAllDetailedEmployees()).withRel("detailedEmployees"));
        resources.add(linkTo(methodOn(RootController.class).root()).withRel("root"));
    }
}