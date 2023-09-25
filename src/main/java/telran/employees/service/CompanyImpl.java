package telran.employees.service;

import telran.employees.dto.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class CompanyImpl implements Company {

    private final Map<Long, Employee> employees = new HashMap<>();

    @Override
    public boolean addEmployee(Employee empl) {
        if (employees.containsKey(empl.getId())) {
            return false;
        }
        employees.put(empl.getId(), empl);
        return true;
    }

    @Override
    public Employee removeEmployee(long id) {
        return employees.remove(id);
    }

    @Override
    public Employee getEmployee(long id) {
        return employees.get(id);
    }

    @Override
    public List<Employee> getEmployees() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public List<DepartmentSalary> getDepartmentSalaryDistribution() {
        return employees.values().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.summingInt(Employee::getSalary)))
                .entrySet().stream()
                .map(entry -> new DepartmentSalary(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SalaryDistribution> getSalaryDistribution(int interval) {
        return employees.values().stream()
                .collect(Collectors.groupingBy(emp -> emp.getSalary()/ interval))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new SalaryDistribution(entry.getKey() * interval, (entry.getKey() + 1) * interval - 1, entry.getValue().size()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return employees.values().stream()
                .filter(emp -> emp.getDepartment().equals(department))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
        return employees.values().stream()
                .filter(emp -> emp.getSalary() >= salaryFrom && emp.getSalary() <= salaryTo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
        LocalDate now = LocalDate.now();
        return employees.values().stream()
                .filter(emp -> Period.between(emp.getBirthDate(), now).getYears() >= ageFrom && Period.between(emp.getBirthDate(), now).getYears() <= ageTo)
                .collect(Collectors.toList());
    }

    @Override
    public Employee updateSalary(long id, int newSalary) {
        Employee emp = employees.get(id);
        if (emp == null) return null;

        Employee updatedEmployee = new Employee(emp.getId(), emp.getName(), emp.getDepartment(), newSalary, emp.getBirthDate());
        employees.put(id, updatedEmployee);
        return updatedEmployee;
    }

    @Override
    public Employee updateDepartment(long id, String department) {
        Employee emp = employees.get(id);
        if (emp == null) return null;

        Employee updatedEmployee = new Employee(emp.getId(), emp.getName(), department, emp.getSalary(), emp.getBirthDate());
        employees.put(id, updatedEmployee);
        return updatedEmployee;
    }
}
