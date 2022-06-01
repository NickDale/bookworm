package com.library.model.repositories;

import com.library.model.entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends AbstractJpaRepository<Book> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT b FROM Book b WHERE b.barcode =:barcode AND b.id NOT IN (SELECT l.bookId FROM Loan l WHERE l.endDate IS NULL)")
    Optional<Book> findAvailableBookByBarcode(@NotBlank String barcode);

    @Transactional(readOnly = true)
    @Query(value = "SELECT b FROM Book b WHERE b.id IN (:ids) " +
            "AND b.id NOT IN (" +
            "   SELECT l.bookId FROM Loan l " +
            "   WHERE l.endDate IS NULL AND l.bookId IN (:ids)" +
            ")")
    List<Book> findAvailableBooksByIds(@Param("ids") Collection<Long> ids);

}
