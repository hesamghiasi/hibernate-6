package ir.hesamghiasi.softwareengineering.java.jug;


import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import static java.lang.Boolean.TRUE;
import static java.lang.System.out;
public class Main {
    public static void main(String[] args) {
        var sessionFactory = new Configuration()
                .addAnnotatedClass(Book.class)
                // use H2 in-memory database
                .setProperty(AvailableSettings.URL, "jdbc:h2:mem:db1")
                .setProperty(AvailableSettings.USER, "sa")
                .setProperty(AvailableSettings.PASS, "")
                // use Agroal connection pool
                .setProperty("hibernate.agroal.maxSize", "20")
                // display SQL in console
                .setProperty(AvailableSettings.SHOW_SQL, TRUE.toString())
                .setProperty(AvailableSettings.FORMAT_SQL, TRUE.toString())
                .setProperty(AvailableSettings.HIGHLIGHT_SQL, TRUE.toString())
                .buildSessionFactory();

        // export the inferred database schema
        sessionFactory.getSchemaManager().exportMappedObjects(true);

        // persist an entity
        sessionFactory.inTransaction(session -> {
            session.persist(new Book("9781932394153", "Hibernate in Action"));
        });

        // query data using HQL
        sessionFactory.inSession(session -> {
            out.println(session.createSelectionQuery("select isbn||': '||title from Book").getSingleResult());
        });

        // query data using criteria API
        sessionFactory.inSession(session -> {
            var builder = sessionFactory.getCriteriaBuilder();
            var query = builder.createQuery(String.class);
            var book = query.from(Book.class);
            query.select(builder.concat(builder.concat(book.get(Book_.isbn), builder.literal(": ")),
                    book.get(Book_.title)));
            out.println(session.createSelectionQuery(query).getSingleResult());
        });
    }
}