package telran.employees.service;

import telran.employees.dto.*;
import java.util.List;
import java.io.*;

public interface Company {
    boolean addEmployee(Employee empl);
    Employee removeEmployee(long id);
    Employee getEmployee(long id);
    List<Employee> getEmployees();

    default void restore(String dataFile) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(dataFile))) {
            List<Employee> employees = (List<Employee>) input.readObject();
            employees.forEach(this::addEmployee);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    default void save(String dataFile) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            List<Employee> employees = getEmployees();
            output.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<DepartmentSalary> getDepartmentSalaryDistribution();
    List<SalaryDistribution> getSalaryDistribution(int interval);
    List<Employee> getEmployeesByDepartment(String department);
    List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo);
    List<Employee> getEmployeesByAge(int ageFrom, int ageTo);
    Employee updateSalary(long id, int newSalary);
    Employee updateDepartment(long id, String department);
}
