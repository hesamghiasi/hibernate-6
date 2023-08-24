package ir.hesamghiasi.softwareengineering.java.jug;

import jakarta.persistence.EntityManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
    }


    static List<Book> findBooksByTitleWithPagination(EntityManager entityManager,
                                                     String titlePattern) {
        return entityManager
                .createQuery("from Book where title like ?1 order by title", Book.class)
                .setParameter(1, titlePattern)
                .getResultList();
    }


}