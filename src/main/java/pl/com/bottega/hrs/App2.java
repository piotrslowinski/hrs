package pl.com.bottega.hrs;

import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.Salary;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

/**
 * Created by user on 14.10.2017.
 */
public class App2 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "HRS" );

//        Salary salary = new Salary();
        Address address = new Address("al.Warszawska", "Lublin");
        Employee employee = new Employee(500005, "Krzysztof","Jerzyna",
                LocalDate.parse("1960-08-12"),address);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.flush();

        em.getTransaction().commit();
        emf.close();
    }
}
