package com.payroll;

import com.payroll.commons.SimpleIdentifiableRepresentationModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class EmployeeRepresentationModelAssembler extends SimpleIdentifiableRepresentationModelAssembler<Employee> {

    EmployeeRepresentationModelAssembler() {
        super(EmployeeController.class);
    }

    /**
     * Define links to add to every {@link EntityModel}.
     *
     * @param resource
     */
    @Override
    public void addLinks(EntityModel<Employee> resource) {

        /**
         * Add some custom links to the default ones provided. NOTE: To replace default links, don't invoke
         * {@literal super.addLinks()}.
         */
        super.addLinks(resource);

        resource.getContent().getId() //
                .ifPresent(id -> { //
                    // Add additional links
                    resource.add(linkTo(methodOn(ManagerController.class).findManager(id)).withRel("manager"));
                    resource.add(linkTo(methodOn(EmployeeController.class).findDetailedEmployee(id)).withRel("detailed"));

                    // Maintain a legacy link to support older clients not yet adjusted to the switch from "supervisor" to
                    // "manager".
                    resource.add(linkTo(methodOn(SupervisorController.class).findOne(id)).withRel("supervisor"));
                });
    }

    /**
     * Define links to add to {@link CollectionModel} collection.
     *
     * @param resources
     */
    @Override
    public void addLinks(CollectionModel<EntityModel<Employee>> resources) {

        super.addLinks(resources);

        resources.add(linkTo(methodOn(EmployeeController.class).findAllDetailedEmployees()).withRel("detailedEmployees"));
        resources.add(linkTo(methodOn(ManagerController.class).findAll()).withRel("managers"));
        resources.add(linkTo(methodOn(RootController.class).root()).withRel("root"));
    }
}