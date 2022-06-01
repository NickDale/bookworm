package com.library.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.model.entities.Author;
import com.library.model.entities.Book;
import com.library.model.entities.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    @JsonProperty(value = "book_id")
    private Long bookId;
    @NotBlank
    private String title;
    private String isbn;

    @JsonProperty(value = "img_link")
    private String imgLink;

    @JsonProperty(value = "short_description")
    private String shortDescription;
    private boolean loanable;
    @JsonProperty(value = "max_day")
    private Integer maxLoanDays;
    @NotNull
    @JsonProperty(value = "category_id")
    private Long categoryId;

    private BookCategory category;
    private List<String> authors;
    @JsonProperty(value = "authors_string")
    private String authorsString;
    private String barcode;

    public BookDto(Book book) {
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.isbn = book.getIsbn();
        this.barcode = book.getBarcode();
        this.imgLink = book.getImgLink();
        this.shortDescription = book.getShortDescription();
        this.loanable = book.isLoanable();
        this.maxLoanDays = book.getMaxLoanDays();
        this.category = book.getCategory();
        this.authorsString = book.getAuthors()
                .stream()
                .map(Author::getName)
                .collect(Collectors.joining(", "));
    }
}
