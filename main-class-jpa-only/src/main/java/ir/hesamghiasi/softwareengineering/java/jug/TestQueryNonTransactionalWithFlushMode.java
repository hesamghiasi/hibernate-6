package ir.hesamghiasi.softwareengineering.java.jug;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.FlushModeType;

import java.util.Map;
import java.util.function.Consumer;

import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.lang.System.out;
import static org.hibernate.cfg.AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION;
import static org.hibernate.tool.schema.Action.CREATE;

public class TestQueryNonTransactionalWithFlushMode {


    private static EntityManager entityManager;
    public static void persistAndQueryNonTransactionalInSession() {
        var factory = createEntityManagerFactory("example",
                // export the inferred database schema
                Map.of(JAKARTA_HBM2DDL_DATABASE_ACTION, CREATE));

        // persist an entity
        nonTransactionalInSession(factory, entityManager -> {
            entityManager.persist(new Book("9781932394153", "Hibernate in Action"));
        });

        // query data using HQL
        transactionalInSession(factory, entityManager -> {
            out.println("fetched book name data " + entityManager.createQuery("select isbn||': '||title from Book").getSingleResult());
        });

        // query data using criteria API
        /*        nonTransactionalInSession(factory, entityManager -> {
            var builder = factory.getCriteriaBuilder();
            var query = builder.createQuery(String.class);
            var book = query.from(Book.class);
            query.select(builder.concat(builder.concat(book.get(Book_.isbn), builder.literal(": ")),
                    book.get(Book_.title)));
            out.println(entityManager.createQuery(query).getSingleResult());
        });*/
    }
    static void nonTransactionalInSession(EntityManagerFactory factory, Consumer<EntityManager> work) {
        try  {
            if (entityManager == null) {
                entityManager = factory.createEntityManager();
            }
            work.accept(entityManager);
        }finally {

        }
    }

    static void transactionalInSession(EntityManagerFactory factory, Consumer<EntityManager> work) {
        EntityTransaction transaction = null;
        try  {
            transaction = entityManager.getTransaction();
            transaction.begin();
            work.accept(entityManager);
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }


    public static void main(String[] args) {
        persistAndQueryNonTransactionalInSession();

    }
}
