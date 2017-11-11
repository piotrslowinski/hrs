package pl.com.bottega.hrs.infrastructure;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import pl.com.bottega.hrs.model.*;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by user on 15.10.2017.
 */
public class EntityManagerTest extends InfrastructureTest {



    @Test
    public void tracksChangesToEntities() {
        //given
//        Employee employee = new Employee(1, "Jan", "Nowak", LocalDate.parse("1980-02-01"));
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(employee);
//        em.close();
//        em.getTransaction().commit();
//
//
//        //when
//        em = emf.createEntityManager();
//        em.getTransaction().begin();
//        employee = em.find(Employee.class, 1);
//        employee.updateProfile("Janusz", "Nowak", LocalDate.now());
//        em.close();
//        em.getTransaction().commit();
//
//        //then
//        em = emf.createEntityManager();
//        employee = em.find(Employee.class, 1);
//        assertEquals("Janusz", employee.getFirstName());
//

        //given

        executeInTransaction(em -> {
            Employee employee = createEmployee("Jan");
            em.persist(employee);
        });

        //when
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            updateFirstName("Janusz", employee);
        });

        //then
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            assertEquals("Janusz", employee.getFirstName());
        });

    }

    @Test
    public void mergesEntities() {
        ///given
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Jan");
            em.persist(employee);
        });

        //when
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Janusz");
            Employee employeeCopy = em.merge(employee);
            updateFirstName("Eustachy", employeeCopy);
            updateFirstName("Stefan", employee);
        });

        //then
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            assertEquals("Eustachy", employee.getFirstName());
        });
    }

    @Test
    public void removesEntities(){
        //given
        executeInTransaction(em -> {
            Employee employee = createEmployee("Jan");
            em.persist(employee);
        });

        //when
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            em.remove(employee);
        });

        //when
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            assertNull(employee);
        });
    }

    @Test
    public void cascadesOperations(){
        //given
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Janek");
            em.persist(employee);
        });

        //then
        executeInTransaction((em) -> {
            Address address = em.find(Address.class, 1);
            assertNotNull(address);
        });
    }

    private Employee tmpEmployee;

    @Test(expected = LazyInitializationException.class)
    public void throwsLazyInitException(){
        // givn
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Janek");
            em.persist(employee);
        });

        executeInTransaction((em) -> {
           tmpEmployee = em.find(Employee.class, 1);
        });

        tmpEmployee.getSalaries().size();

    }




    private Employee createEmployee(String firstName) {
        Address address = new Address("al. Warszawska 10", "Lublin");
        return new Employee(1, firstName, "Nowak", LocalDate.now(), address, new StandardTimeProvider());
    }

    private void updateFirstName(String newName, Employee employee) {
        employee.updateProfile(newName, "Nowak", LocalDate.now());
    }




}

