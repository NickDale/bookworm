package com.library.model.entities;

import com.library.controller.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Book extends AbstractEntityLogStamp {

    @Size(max = 200)
    @NotBlank
    @Column(name = "title", nullable = false, updatable = false, length = 200)
    private String title;

    private String isbn;

    @Column(name = "img_link")
    private String imgLink;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(columnDefinition = "BIT", nullable = false, length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean loanable;

    @Column(name = "max_loan_days", nullable = false)
    private Integer maxLoanDays;

    @NotNull
    @Column(name = "category_id", updatable = false, nullable = false)
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", updatable = false, insertable = false)
    private BookCategory category;

    @Size(max = 50)
    @NotBlank
    @Column(name = "barcode", unique = true, updatable = false, length = 50)
    private String barcode;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false, updatable = false))
    private List<Author> authors;

    public Book(BookDto bookDto) {
        this.isbn = bookDto.getIsbn();
        this.imgLink = bookDto.getImgLink();
        this.loanable = bookDto.isLoanable();
        this.title = bookDto.getTitle();
        this.maxLoanDays = bookDto.getMaxLoanDays();
        this.shortDescription = bookDto.getShortDescription();
        this.categoryId = bookDto.getCategoryId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return loanable == book.loanable && Objects.equals(maxLoanDays, book.maxLoanDays)
                && Objects.equals(title, book.title)
                && Objects.equals(isbn, book.isbn)
                && Objects.equals(imgLink, book.imgLink)
                && Objects.equals(shortDescription, book.shortDescription)
                && Objects.equals(categoryId, book.categoryId)
                && Objects.equals(category, book.category)
                && Objects.equals(barcode, book.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, isbn, imgLink, shortDescription, loanable,
                maxLoanDays, categoryId, category, barcode);
    }

    public void addAuthor(Author author) {
        if (isEmpty(this.authors)) {
            this.authors = new ArrayList<>();
        }
        this.authors.add(author);
    }
}
