package de.ite.employeeService.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "EMPLOYEE")
public class Employee {
    @Id
    private Long id;
    private String name;
    private String taxId;
    private String role;

    Employee(String name, String taxId, String role) {
        this.name = name;
        this.taxId = taxId;
        this.role = role;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}