package telran.employees.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CopmanyImpl implements Company {

    HashMap<Long,Employee> employees = new HashMap<>();

    @Override
    public boolean addEmployee(Employee eml) {
        boolean flag = false;
        if (!employees.containsKey(eml.id())) {
            employees.put(eml.id(), eml);
            flag = true; }
        return flag;
    }

    @Override
    public Employee removeEmployee(long id) {

        if (!employees.containsKey(id)) {
            throw new IllegalArgumentException("Employee with ID " + id + " not found");
        }

        return employees.remove(id);
    }

    @Override
    public Employee getEmployee(long id) {

        Employee employee = employees.get(id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with ID " + id + " not found");
        }

        return employee;
    }

    @Override
    public List<Employee> getEmployees() {
        List<Employee> ourEmployees = new ArrayList<>();
        for (HashMap.Entry<Long,Employee> entry: employees.entrySet())
        {
            Employee myEmployee = entry.getValue();
            ourEmployees.add(myEmployee);
        }
        return ourEmployees;
    }
}

