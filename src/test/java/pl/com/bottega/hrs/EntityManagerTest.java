package pl.com.bottega.hrs;

import org.hibernate.LazyInitializationException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.Salary;
import pl.com.bottega.hrs.model.Title;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by user on 15.10.2017.
 */
public class EntityManagerTest {


    private static EntityManagerFactory emf;
    private static Collection<Salary> salaries = new LinkedList<>();


    @BeforeClass
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("HRS-TEST");
    }

    @After
    public void cleanUp() {
        executeInTransaction((em) -> {
            em.createNativeQuery("TRUNCATE employees").executeUpdate();
        });

    }

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

    @Test
    public void shouldAddNewSalary(){
        //given
        Employee employee = createEmployeeWithNoSalary();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.close();
        em.getTransaction().commit();

        //when
        em = emf.createEntityManager();
        em.getTransaction().begin();
        employee = em.find(Employee.class, 1);
        updateSalary(employee);
        em.close();
        em.getTransaction().commit();

        //then
        em = emf.createEntityManager();
        employee = em.find(Employee.class, 1);
        assertEquals(Optional.ofNullable(21000), employee.getCurrentSalary());
        em.close();
//        em.getEntityManagerFactory().close();
    }

    @Test
    public void shouldUpdateSalary(){
        //given
        Employee employee = createEmployeeWithSalary();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.close();
        em.getTransaction().commit();


//        //when
        em = emf.createEntityManager();
        em.getTransaction().begin();
        employee = em.find(Employee.class, 1);
        updateSalary(employee);
        em.close();
        em.getTransaction().commit();
//
//        //then
        em = emf.createEntityManager();
        employee = em.find(Employee.class, 1);
        assertEquals(Optional.ofNullable(21000), employee.getCurrentSalary());
//        em.getEntityManagerFactory().close();
        em.close();


//
    }

    @Test
    public void shouldReturnCurrentSalary(){
        //given
        Employee employee = createEmployeeWithSalary();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.close();
        em.getTransaction().commit();


        //then
        em = emf.createEntityManager();
        employee = em.find(Employee.class, 1);
        assertEquals(Optional.ofNullable(100000), employee.getCurrentSalary());
//        em.getEntityManagerFactory().close();
        em.close();
    }

    @Test
    public void shouldAddNewTitle(){
        //given
        Employee employee = createEmployeeWithNoTitle();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.close();
        em.getTransaction().commit();

        //when
        em = emf.createEntityManager();
        em.getTransaction().begin();
        employee = em.find(Employee.class, 1);
        updateTitle(employee);
        em.close();
        em.getTransaction().commit();

        //then
        em = emf.createEntityManager();
        employee = em.find(Employee.class, 1);
        assertEquals(Optional.ofNullable("specialist"), employee.getCurrentTitle());
        em.close();
//        em.getEntityManagerFactory().close();
    }

    @Test
    public void shouldReturnCurrentTitle(){
        //given
        Employee employee = createEmployeeWithTitle();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.close();
        em.getTransaction().commit();


        //then
        em = emf.createEntityManager();
        employee = em.find(Employee.class, 1);
        assertEquals(Optional.ofNullable("managier"), employee.getCurrentTitle());
        em.close();
    }

    @Test
    public void shouldUpdateTitle(){
        //given
        Employee employeeTitle = createEmployeeWithTitle();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(employeeTitle);
        em.close();
        em.getTransaction().commit();

//        //when
        em = emf.createEntityManager();
        em.getTransaction().begin();
        employeeTitle = em.find(Employee.class, 1);
        updateTitle(employeeTitle);
        em.close();
        em.getTransaction().commit();

//        //then
        em = emf.createEntityManager();
        employeeTitle = em.find(Employee.class, 1);
        assertEquals(Optional.ofNullable("specialist"), employeeTitle.getCurrentTitle());
//        em.getEntityManagerFactory().close();
        em.close();
    }

    private Employee createEmployeeWithNoTitle(){
        Address address = new Address("al. Warszawska 10", "Lublin");
        Title title = new Title();
        return new Employee(1,"Jan", "Nowak", LocalDate.now(), address, title);
    }


    private Employee createEmployeeWithNoSalary(){
        Address address = new Address("al. Warszawska 10", "Lublin");
        Salary salary = new Salary();
        return new Employee(1,"Jan", "Nowak", LocalDate.now(), address, salary);
    }

    private Employee createEmployeeWithSalary(){
        Address address = new Address("al. Warszawska 10", "Lublin");
        Salary.SalaryId salaryId = new Salary.SalaryId(1,LocalDate.parse("1990-01-01"));
        Salary salary = new Salary(salaryId, 100000, LocalDate.parse("9999-01-01"));
        Employee employee = new Employee(1,"Jan", "Nowak", LocalDate.now(), address, salary);
        employee.addSalary(salary);
        return employee;
    }

    private Employee createEmployeeWithTitle(){
        Title.TitleId titleId = new Title.TitleId(1);
        Title title = new Title(titleId,"managier", LocalDate.parse("1990-01-01"), LocalDate.parse("9999-01-01"));
        Address address = new Address("al. Warszawska 10", "Lublin");
        Employee employee = new Employee(1,"Jan", "Nowak", LocalDate.now(), address, title);
        employee.addTitle(title);
        return employee;
    }

    private Employee createEmployeeWithSalaryAndTitle(){
        Title.TitleId titleId = new Title.TitleId(1);
        Title title = new Title(titleId,"managier", LocalDate.parse("1990-01-01"), LocalDate.parse("9999-01-01"));
        Salary.SalaryId salaryId = new Salary.SalaryId(1,LocalDate.parse("1990-01-01"));
        Salary salary = new Salary(salaryId, 100000, LocalDate.parse("9999-01-01"));
        Address address = new Address("al. Warszawska 10", "Lublin");
        Employee employee = new Employee(1,"Jan", "Nowak", LocalDate.now(), address ,salary, title);
        employee.addTitle(title);
        employee.addSalary(salary);
        return employee;
    }

    private Employee createEmployee(String firstName) {
        Address address = new Address("al. Warszawska 10", "Lublin");
        return new Employee(1, firstName, "Nowak", LocalDate.now(), address);
    }

    private void updateFirstName(String newName, Employee employee) {
        employee.updateProfile(newName, "Nowak", LocalDate.now());
    }

    private void updateSalary(Employee employee){
        employee.changeSalary(21000);
    }

    private void updateTitle(Employee employee) {
        employee.changeTitle("specialist");
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        consumer.accept(em);
        em.close();
        em.getTransaction().commit();
    }
}

