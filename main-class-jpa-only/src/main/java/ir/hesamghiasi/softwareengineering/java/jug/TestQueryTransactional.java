package ir.hesamghiasi.softwareengineering.java.jug;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.Map;
import java.util.function.Consumer;

import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.lang.System.out;
import static org.hibernate.cfg.AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION;
import static org.hibernate.tool.schema.Action.CREATE;

public class TestQueryTransactional {

    public static void persistAndQueryWithTransactionalInSession() {
        var factory = createEntityManagerFactory("example",
                Map.of(JAKARTA_HBM2DDL_DATABASE_ACTION, CREATE));
        transactionalInSession(factory, entityManager -> {
            entityManager.persist(new Book("9781932394153", "Hibernate in Action"));
        });
        transactionalInSession(factory, entityManager -> {
            out.println("fetched book data is " + entityManager.createQuery("select * from Book").getSingleResult());
        });
/*        transactionalInSession(factory, entityManager -> {
            var builder = factory.getCriteriaBuilder();
            var query = builder.createQuery(String.class);
            var book = query.from(Book.class);
            query.select(builder.concat(builder.concat(book.get(Book_.isbn), builder.literal(": ")),
                    book.get(Book_.title)));
            out.println(entityManager.createQuery(query).getSingleResult());
        });*/
    }
    static void transactionalInSession(EntityManagerFactory factory, Consumer<EntityManager> work) {
        var entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;
        try (entityManager) {
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
        persistAndQueryWithTransactionalInSession();
    }
}
