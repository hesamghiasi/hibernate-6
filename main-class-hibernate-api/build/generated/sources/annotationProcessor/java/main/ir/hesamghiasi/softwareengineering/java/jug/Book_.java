package ir.hesamghiasi.softwareengineering.java.jug;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {

	public static volatile SingularAttribute<Book, String> isbn;
	public static volatile SingularAttribute<Book, String> title;

	public static final String ISBN = "isbn";
	public static final String TITLE = "title";

}

