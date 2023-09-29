package ir.hesamghiasi.softwareengineering.java.jug;

import jakarta.persistence.EntityManager;

import java.util.Map;
import java.util.function.Consumer;

import static ir.hesamghiasi.softwareengineering.java.jug.TestQueryTransactional.persistAndQueryWithTransactionalInSession;
import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.lang.System.out;
import static org.hibernate.cfg.AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION;
import static org.hibernate.tool.schema.Action.CREATE;

public class TestFindNonTransactionalOneEntityManager {

    private static EntityManager entityManager;

    public static void persistAndFindNonTransactionalOneEntityManager() {
        nonTransactionalOneEntityManagerInSession(entityManager -> {
            entityManager.persist(new Book("9781932394153", "Hibernate in Action"));
        });
        nonTransactionalOneEntityManagerInSession(entityManager -> {
            out.println("fetched book name is " + entityManager.find(Book.class, "9781932394153").title);
        });
    }
    static void nonTransactionalOneEntityManagerInSession(Consumer<EntityManager> work) {
        try {
            if (entityManager == null) {
                var factory = createEntityManagerFactory("example",
                        Map.of(JAKARTA_HBM2DDL_DATABASE_ACTION, CREATE));
                entityManager = factory.createEntityManager();
            }
            work.accept(entityManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        persistAndFindNonTransactionalOneEntityManager();
    }
}
