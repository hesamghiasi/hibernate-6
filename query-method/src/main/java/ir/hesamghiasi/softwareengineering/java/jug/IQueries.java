package ir.hesamghiasi.softwareengineering.java.jug;

import java.util.List;

public interface IQueries {
//    @HQL("from Book where title like :title order by title")
    List<Book> findBooksByTitleWithPagination(String title);
}


