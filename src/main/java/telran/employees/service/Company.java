package telran.employees.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import telran.employees.service.Employee;


public interface Company {
    boolean addEmployee(Employee eml);
    Employee removeEmployee(long id);
    Employee getEmployee(long id);
    List<Employee> getEmployees();
    default void restore(String dataFile) throws ClassNotFoundException
    {

        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(dataFile)))
        {
            List <Employee> employees = (List <Employee>) input.readObject();
            employees.forEach(this::addEmployee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void save(String dataFile)
    {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(dataFile)))
        {
            List <Employee> employees = getEmployees();
            output.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}