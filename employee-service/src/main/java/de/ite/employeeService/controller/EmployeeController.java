package de.ite.employeeService.controller;

import de.ite.employeeService.db.EmployeeService;
import de.ite.employeeService.entities.Employee;
import org.springframework.web.bind.annotation.*;

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
    public List<Employee> all() {
        return employeeService.findAllEmployees();
    }

    /*
     * Find an employee by ID
     */
    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.findEmployee(id);
    }

    /*
     * Find problematic records
     */
    @GetMapping("/employees/problems")
    List<Employee> getProblematicEmployees() {
        return employeeService.getProblematicEmployees();
    }

    /*
     * Create a new employee, looking up their tax id
     */
    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return employeeService.saveNewEmployee(newEmployee);
    }

    /*
     * Update employee
     */
    @PostMapping("/employees/{id}")
    Employee updateEmployee(@PathVariable Long id, @RequestBody Employee newEmployee) {
        return employeeService.updateEmployee(id, newEmployee);
    }

    /*
     * Update tax id for all employees
     */
    @GetMapping("/employees/update/tax")
    List<Employee> updateTaxIds() {
        return employeeService.updateEmployeeTaxIDs();
    }
}