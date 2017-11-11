package pl.com.bottega.hrs.infrastructure;

import org.junit.After;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Consumer;

/**
 * Created by user on 30.10.2017.
 */
public abstract class InfrastructureTest {

    private static EntityManagerFactory emf;

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

    protected void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        consumer.accept(em);
        em.close();
        em.getTransaction().commit();
    }

    protected EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
}
