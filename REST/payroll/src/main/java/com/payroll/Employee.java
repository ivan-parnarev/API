package com.payroll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;

    /**
     * To break the recursive, bi-directional relationship, don't serialize {@literal manager}.
     */
    @JsonIgnore
    @ManyToOne
    private Manager manager;

    Employee(String name, String role, Manager manager) {

        this.name = name;
        this.role = role;
        this.manager = manager;
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}