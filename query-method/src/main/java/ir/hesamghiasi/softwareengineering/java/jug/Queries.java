package ir.hesamghiasi.softwareengineering.java.jug;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQuery;

import java.util.List;

@NamedQuery(name="findBooksByTitle",
        query="from Book where title like :title order by title")
public class Queries {

    static List<Book> findBooksByTitleWithPagination(EntityManager entityManager,
                                                     String titlePattern) {
        return entityManager.createNamedQuery("findBooksByTitle", Book.class)
                .setParameter("title", titlePattern)
                .getResultList();
    }
}


