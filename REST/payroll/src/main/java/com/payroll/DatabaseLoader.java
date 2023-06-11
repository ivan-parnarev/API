package com.payroll;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, ManagerRepository managerRepository) {
        return args -> {

            /*
             * Gather Elon's team
             */
            Manager manager = managerRepository.save(new Manager("Elon Musk"));

            Employee emp1 = employeeRepository.save(new Employee("Ivan", "CTO", manager));
//            Employee emp2 = employeeRepository.save(new Employee("Elena", "CEO", manager));

            manager.setEmployees(Arrays.asList(emp1));
            managerRepository.save(manager);

            /*
             * Put together Jeff's team
             */
            Manager manager1 = managerRepository.save(new Manager("Jeff"));

            Employee emp3 = employeeRepository.save(new Employee("Yasen", "COO", manager1));

            manager1.setEmployees(Arrays.asList(emp3));
            managerRepository.save(manager1);
        };
    }
}