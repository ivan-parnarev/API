package com.payroll;

import org.springframework.data.repository.CrudRepository;

interface ManagerRepository extends CrudRepository<Manager, Long> {

    /**
     * Navigate through the JPA relationship to find a {@link Manager} based on an {@link Employee}'s {@literal id}.
     *
     * @param id
     * @return Manager
     */
    Manager findByEmployeesId(Long id);
}