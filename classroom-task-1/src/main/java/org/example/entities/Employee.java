package org.example.entities;

public class Employee {
    private String name;
    private double hourlyRate;
    private EmployeeType employeeType;
    private boolean allowToExceedMaxSalary;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, double hourlyRate) {
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public Employee(String name, double hourlyRate, EmployeeType employeeType) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.employeeType = employeeType;
    }

    public Employee(String name, double hourlyRate, EmployeeType employeeType, boolean canExceedMaxSalary) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.employeeType = employeeType;
        this.allowToExceedMaxSalary = canExceedMaxSalary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public boolean isAllowToExceedMaxSalary() {
        return allowToExceedMaxSalary;
    }

    public void setAllowToExceedMaxSalary(boolean allowToExceedMaxSalary) {
        this.allowToExceedMaxSalary = allowToExceedMaxSalary;
    }
}
