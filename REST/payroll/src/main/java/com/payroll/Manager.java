package com.payroll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    /**
     * To break the recursive, bi-directional interface, don't serialize {@literal employees}.
     */
    @JsonIgnore //
    @OneToMany(mappedBy = "manager") //
    private List<Employee> employees = new ArrayList<>();

    Manager(String name) {
        this.name = name;
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}