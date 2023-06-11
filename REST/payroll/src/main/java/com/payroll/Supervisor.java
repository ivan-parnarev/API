package com.payroll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Legacy representation. Contains older format of data. Fewer links because hypermedia at the time was an after
 * thought.
 */
@Value
@JsonPropertyOrder({"id", "name", "employees"})
class Supervisor {

    @JsonIgnore
    Manager manager;

    public Long getId() {

        return this.manager.getId() //
                .orElseThrow(() -> new RuntimeException("Couldn't find anything"));
    }

    public String getName() {
        return this.manager.getName();
    }

    public List<String> getEmployees() {

        return manager.getEmployees().stream() //
                .map(employee -> employee.getName() + "::" + employee.getRole()) //
                .collect(Collectors.toList());
    }
}
