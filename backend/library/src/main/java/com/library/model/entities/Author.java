package com.library.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "author")
@AllArgsConstructor
//@NoArgsConstructor
@Setter
@Getter
public class Author extends AbstractUnitEntity {

    public Author(String authorName) {
        this.name = authorName;
    }

//    @ManyToMany
////    @LazyCollection(LazyCollectionOption.FALSE)
//    @JoinTable(name = "book_author",
//            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
//    private List<Book> books;
}
