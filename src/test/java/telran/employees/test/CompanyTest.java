package telran.employees.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.*;

import telran.employees.dto.*;
import telran.employees.service.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyTests {

    private static final long ID_NOT_EXIST = 10000000;
    private static final String TEST_DATA = "test.data";

    Employee empl1 = new Employee(123, "Veronica", "Engineering", 20000, LocalDate.of(1990, 2, 15));
    Employee empl2 = new Employee(124, "Anatoly", "Design", 50000, LocalDate.of(1985, 4, 12));
    Employee empl3 = new Employee(125, "Maxim", "Engineering", 50000, LocalDate.of(1992, 6, 22));
    Employee empl4 = new Employee(126, "Daniel", "Design", 30000, LocalDate.of(1994, 10, 10));
    Employee empl5 = new Employee(127, "yuri", "HR", 60000, LocalDate.of(1988, 8, 8));

    Employee[] employees = {empl1, empl2, empl3, empl4, empl5};
    Company company;

    final static String TEST_FILE_NAME = "test.data";

    @BeforeEach
    void setUp() throws Exception {
        company = new CompanyImpl(){
        };
        for(Employee empl: employees) {
            company.addEmployee(empl);
        }
    }

    @Test
    void testAddEmployee() {
        assertFalse(company.addEmployee(empl1));
        assertTrue(company.addEmployee(new Employee(ID_NOT_EXIST, "name", "IT", 15000, LocalDate.of(1991,1,1))));
    }

    @Test
    void testRemoveEmployee() {
        assertNull(company.removeEmployee(ID_NOT_EXIST));
        assertEquals(empl1, company.removeEmployee(empl1.getId()));
        Employee[] expected = {empl2, empl3, empl4, empl5};
        Employee[] actual = company.getEmployees().toArray(Employee[]::new);
        Arrays.sort(actual, Comparator.comparingLong(Employee::getId));
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetEmployee() {
        assertEquals(empl1, company.getEmployee(empl1.getId()));
        assertNull(company.getEmployee(ID_NOT_EXIST));
    }

    @Test
    void testGetEmployees() {
        Employee[] actual = company.getEmployees().toArray(Employee[]::new);
        Arrays.sort(actual, Comparator.comparingLong(Employee::getId));
        assertArrayEquals(employees, actual);
    }

    @Test
    @Order(2)
    void testRestore() {
        Company newCompany = new CompanyImpl();
        newCompany.restore(TEST_FILE_NAME);
        Employee[] actual = newCompany.getEmployees().toArray(Employee[]::new);
        Arrays.sort(actual, Comparator.comparingLong(Employee::getId));
        assertArrayEquals(employees, actual);
    }

    @Test
    @Order(1)
    void testSave() {
        company.save(TEST_FILE_NAME);
    }

    @Test
    void testGetDepartmentSalaryDistribution() {
        DepartmentSalary ds1 = new DepartmentSalary("Engineering", 70000);
        DepartmentSalary ds2 = new DepartmentSalary("Design", 80000);
        DepartmentSalary ds3 = new DepartmentSalary("HR", 60000);
        List<DepartmentSalary> expected = List.of(ds1, ds2, ds3);
        List<DepartmentSalary> actual = company.getDepartmentSalaryDistribution();
        assertIterableEquals(expected, actual);
    }

    @Test
    void testGetEmployeesByDepartment() {
        List<Employee> expected = List.of(empl1, empl3);
        List<Employee> actual = company.getEmployeesByDepartment("Engineering");
        actual.sort(Comparator.comparingLong(Employee::getId));
        assertIterableEquals(expected, actual);
    }

    @Test
    void testGetEmployeesBySalary(){
        List<Employee> expected = List.of(empl3, empl4);
        List<Employee> actual = company.getEmployeesBySalary(25000, 51000);
        actual.sort(Comparator.comparingLong(Employee::getId));
        assertIterableEquals(expected, actual);
    }

    @Test
    void testGetEmployeesByAge(){
        List<Employee> expected = List.of(empl4);
        List<Employee> actual = company.getEmployeesByAge(25, 30);
        assertIterableEquals(expected, actual);
    }

    @Test
    void testUpdateSalary() {
        int newSalary = 21000;
        Employee updatedEmployee = company.updateSalary(empl1.getId(), newSalary);
        assertTrue(updatedEmployee.getSalary() == newSalary);
    }

    @Test
    void testUpdateDepartment() {
        String newDepartment = "IT";
        Employee updatedEmployee = company.updateDepartment(empl1.getId(), newDepartment);
        assertTrue(updatedEmployee.getDepartment().equals(newDepartment));
    }
}
