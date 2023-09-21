package telran.io.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import telran.employees.service.Employee;
import telran.employees.service.Company;
import telran.employees.service.CopmanyImpl;
import org.junit.jupiter.api.Order;
import telran.employees.service.Employee;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class Company_Test {

    final static String TEST_FILE_NAME = "test.data";
    Employee empl1 = new Employee(66666, "Lolita", "Back End", 20000, LocalDate.of(1999,2,17));
    Employee empl2 = new Employee(99999, "Masik","Back End", 18000, LocalDate.of(1990, 3, 14));
    Employee empl3 = new Employee(1488, "Daniel", "Design", 14000, LocalDate.of(1991,5,17));
    Employee empl4 = new Employee(12489, "Yosef", "Design", 15000, LocalDate.of(1994,8,27));
    Employee empl5 = new Employee(77777, "Gosha", "Front end", 17000, LocalDate.of(1985,12,7));
    Employee[] employees = {empl1, empl2, empl3, empl4, empl5};
    Company company;

    @BeforeEach
    void setUp() {
        company = new CopmanyImpl();
        for(Employee empl: employees) {
            company.addEmployee(empl);
        }
    }

    @Test
    void testAddEmployee() {
        assertTrue(company.addEmployee(new Employee(3423423, "Alexey", "Front end", 17000, LocalDate.of(1977,4,6))));
        assertFalse(company.addEmployee(empl1));
    }

    @Test
    void testRemoveEmployee() {
        assertNotNull(company.removeEmployee(66666));
        assertNull(company.removeEmployee(111222));
    }

    @Test
    void testGetEmployee() {
        assertEquals(empl1, company.getEmployee(66666));
        assertThrows(IllegalArgumentException.class, () -> company.getEmployee(111000));
    }

    @Test
    void testGetEmployees() {
        List<Employee> expectedEmployees = Arrays.asList(employees);
        List<Employee> actualEmployees = company.getEmployees();
        actualEmployees.sort(Comparator.comparingLong(Employee::id));
        assertEquals(expectedEmployees, actualEmployees);
    }


    @Test
    @Order(2)
    void testRestore() throws Exception {
        Company restoreCompany =  new CopmanyImpl();
        restoreCompany.restore(TEST_FILE_NAME);
        assertEquals(empl1, restoreCompany.getEmployee(66666));
        assertEquals(empl2, restoreCompany.getEmployee(99999));
        List<Employee> expectedEmployees = Arrays.asList(employees);
        List<Employee> actualEmployees = restoreCompany.getEmployees();
        actualEmployees.sort(Comparator.comparingLong(Employee::id));
        assertEquals(expectedEmployees.size(), actualEmployees.size());
        for (int i = 0; i < expectedEmployees.size(); i++) {
            assertEquals(expectedEmployees.get(i), actualEmployees.get(i));
        }
    }
    @Test
    @Order(1)
    void testSave() {
        company.save(TEST_FILE_NAME);
    }

}