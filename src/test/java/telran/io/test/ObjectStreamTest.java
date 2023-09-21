package telran.io.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ObjectStreamTest {
    static final String FILE_NAME= "person.data";
    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    @Order(1)
    void writePersonTest() throws Exception {
        Person person = new Person("Vasya", 123445);
        person.person = person;
        assertEquals(person.id, person.person.person.person.id);
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(FILE_NAME)))
        {
            output.writeObject(person);
        }

    }
    @Test
    @Order(2)
    void readPersonTest() throws Exception {
        Person person = null;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILE_NAME)))
        {
            person = (Person) input.readObject();
        }
        assertEquals("Vasya", person.person.person.person.name);
    }

}

class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    String name;
    Integer id;
    Person person;

    public Person(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
}