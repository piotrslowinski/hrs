package pl.com.bottega.hrs;

import pl.com.bottega.hrs.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Created by user on 15.10.2017.
 */
public class App3 {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Podaj nazwisko: ");
        String lastName = scanner.nextLine();
        System.out.println("Podaj datę urodzenia od:");
        LocalDate dateFrom = LocalDate.parse(scanner.next());
        System.out.println("Podaj datę urodzenia do:");
        LocalDate dateTo = LocalDate.parse(scanner.next());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HRS");

        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.lastName LIKE :lastName " +
                "AND e.birthDate >= :dateFrom AND e.birthDate <= :dateTo")
                .setParameter("lastName", lastName + "%")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo);

        List queryResults = query.getResultList();
        if (queryResults.size() == 0)
            System.out.println("---No matching employees---");
        queryResults.stream().forEach(System.out::println);

    }
}
