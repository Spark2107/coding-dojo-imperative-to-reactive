package de.ite.employeeService.db;

import de.ite.employeeService.entities.Employee;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private RestTemplate restTemplate;

    public EmployeeService(EmployeeRepository employeeRepository, RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.employeeRepository = employeeRepository;
    }


    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployee(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "Could not find employee " + id));
    }

    public List<Employee> getProblematicEmployees() {
        List<Employee> problematicEmployees = new ArrayList<>();
        List<Employee> employees = findAllEmployees();
        for (Employee e : employees) {
            if (isProblemData(e)) {
                problematicEmployees.add(e);
            }
        }
        return problematicEmployees;
    }

    private boolean isProblemData(Employee employee) {
        return (employee.getName().isEmpty()) ||
                employee.getTaxId().isEmpty() ||
                employee.getRole().isEmpty() ||
                Character.isLowerCase(employee.getName().charAt(0));
    }

    private Employee save(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public Employee saveNewEmployee(Employee newEmployee) {
        String name = newEmployee.getName();
        String taxId = taxLookup(name);
        newEmployee.setTaxId(taxId);
        return save(newEmployee);
    }

    private String taxLookup(String name) {
        return restTemplate.getForObject(
                String.format("http://localhost:8082/%s", name),
                String.class
        );
    }

    public List<Employee> updateEmployeeTaxIDs() {
        List<Employee> employees = findAllEmployees();
        for (Employee e : employees) {
            System.out.println("Thread: " + Thread.currentThread().getName());
            String name = e.getName();
            String taxId = taxLookup(name);
            e.setTaxId(taxId);
        }
        return employeeRepository.saveAll(employees);
    }
}
