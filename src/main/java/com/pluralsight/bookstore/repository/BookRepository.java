package com.pluralsight.bookstore.repository;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.util.NumberGenerator;
import com.pluralsight.bookstore.util.TextUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

//Ensures it will not invoke more than one transaction
//so no conflict between 2 writing methods at the same time
@Transactional(SUPPORTS)
public class BookRepository {
	//Injection points
	//Add the JPA Entity Manager and persistence unit
    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager em;
    
    @Inject
    private TextUtil textUtil;
    @Inject
    private NumberGenerator generator;

    //Business methods
    //CRUD operations
    //read only
    public Book find(@NotNull Long id) {
        return em.find(Book.class, id);
    }
    
    //JQPL Queries
    public List<Book> findAll() {
		//takes a JPQL query and returns a TypedQuery interface
	    TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ORDER BY b.title DESC", Book.class);
	    return query.getResultList();
	}
	
	public Long countAll() {
	    TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Book b", Long.class);
	    return query.getSingleResult();
	}

	//write only
	//required = making transactions mandatory
    @Transactional(REQUIRED)
    public Book create(@NotNull Book book) {
    		book.setTitle(textUtil.sanitize(book.getTitle()));
    		book.setIsbn(generator.generateNumber());
        em.persist(book);
        return book;
    }

    @Transactional(REQUIRED)
    public void delete(@NotNull Long id) {
        em.remove(em.getReference(Book.class, id));
    }
}
