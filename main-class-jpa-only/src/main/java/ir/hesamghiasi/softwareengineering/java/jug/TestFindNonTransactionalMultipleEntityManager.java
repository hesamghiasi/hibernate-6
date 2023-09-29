package ir.hesamghiasi.softwareengineering.java.jug;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Map;
import java.util.function.Consumer;

import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.lang.System.out;
import static org.hibernate.cfg.AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION;
import static org.hibernate.tool.schema.Action.CREATE;

public class TestFindNonTransactionalMultipleEntityManager {

    public static void persistAndFindNonTransactionalMultipleEntityManager() {
        var factory = createEntityManagerFactory("example",
                Map.of(JAKARTA_HBM2DDL_DATABASE_ACTION, CREATE));
        nonTransactionalMultipleEntityManagerInSession(factory, entityManager -> {
            entityManager.persist(new Book("9781932394153", "Hibernate in Action"));
        });
        nonTransactionalMultipleEntityManagerInSession(factory, entityManager -> {
            out.println("fetched book name is " + entityManager.find(Book.class, "9781932394153").title);
        });
    }
    static void nonTransactionalMultipleEntityManagerInSession(EntityManagerFactory factory, Consumer<EntityManager> work) {
        var entityManager = factory.createEntityManager();
        try (entityManager) {
            work.accept(entityManager);
        } catch (Exception e) {
            throw e;
        }
    }


    public static void main(String[] args) {
        persistAndFindNonTransactionalMultipleEntityManager();
    }
}
