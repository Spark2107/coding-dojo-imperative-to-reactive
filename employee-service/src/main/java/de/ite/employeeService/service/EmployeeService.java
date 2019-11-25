package de.ite.employeeService.service;

import de.ite.employeeService.db.EmployeeRepository;
import de.ite.employeeService.entities.Employee;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final WebClient webClient;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.webClient = WebClient.builder().build();
        this.employeeRepository = employeeRepository;
    }


    public Flux<Employee> findAllEmployees() {
        return Flux.fromIterable(employeeRepository.findAll());
    }

    public Mono<Employee> findEmployee(int id) {
        return Mono.just(employeeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Could not find employee " + id)));
    }

    public Flux<Employee> getProblematicEmployees() {
        return Flux.fromIterable(employeeRepository.findAll())
                .filter(this::isProblemData);
    }

    private boolean isProblemData(Employee employee) {
        return (employee.getName().isEmpty()) ||
                employee.getTaxId().isEmpty() ||
                employee.getRole().isEmpty() ||
                Character.isLowerCase(employee.getName().charAt(0));
    }

    private Mono<Employee> save(Employee newEmployee) {
        return Mono.just(employeeRepository.save(newEmployee));
    }

    public Mono<Employee> saveNewEmployee(Employee newEmployee) {
        return taxLookup(newEmployee.getName()).map(it -> {
            newEmployee.setTaxId(it);
            return newEmployee;
        }).flatMap(this::save);
    }

    private Mono<String> taxLookup(String name) {
        return webClient.method(HttpMethod.GET)
                .uri(String.format("http://localhost:8082/%s", name))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<Employee> updateEmployee(int id, Employee newEmployee) {
        return findEmployee(id).map(e -> {
            if (newEmployee.getName() != null && !newEmployee.getName().equals("")) e.setName(newEmployee.getName());
            if (newEmployee.getRole() != null && !newEmployee.getRole().equals("")) e.setRole(newEmployee.getRole());
            if (newEmployee.getTaxId() != null && !newEmployee.getTaxId().equals(""))
                e.setTaxId(newEmployee.getTaxId());
            return e;
        }).flatMap(this::save);
    }

    public Flux<Employee> updateEmployeeTaxIDs() {
        return findAllEmployees()
                .flatMap(e -> taxLookup(e.getName())
                        .map(taxId -> {
                            System.out.println("Thread: " + Thread.currentThread().getName());
                            e.setTaxId(taxId);
                            return e;
                        }).flatMap(this::save));
    }
}
