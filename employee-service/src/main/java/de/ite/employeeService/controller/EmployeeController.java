package de.ite.employeeService.controller;

import de.ite.employeeService.service.EmployeeService;
import de.ite.employeeService.entities.Employee;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;


    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /*
     * List all employees
     */
    @GetMapping("/employees")
    public Flux<Employee> all() {
        return employeeService.findAllEmployees();
    }

    /*
     * Find an employee by ID
     */
    @GetMapping("/employees/{id}")
    public Mono<Employee> getEmployee(@PathVariable int id) {
        return employeeService.findEmployee(id);
    }

    /*
     * Find problematic records
     */
    @GetMapping("/employees/problems")
    Flux<Employee> getProblematicEmployees() {
        return employeeService.getProblematicEmployees();
    }

    /*
     * Create a new employee, looking up their tax id
     */
    @PostMapping("/employees")
    Mono<Employee> newEmployee(@RequestBody Employee newEmployee) {
        return employeeService.saveNewEmployee(newEmployee);
    }

    /*
     * Update employee
     */
    @PostMapping("/employees/{id}")
    Mono<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee newEmployee) {
        return employeeService.updateEmployee(id, newEmployee);
    }

    /*
     * Update tax id for all employees
     */
    @GetMapping("/employees/update/tax")
    Flux<Employee> updateTaxIds() {
        return employeeService.updateEmployeeTaxIDs();
    }
}