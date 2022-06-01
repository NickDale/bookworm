package com.library.controller;

import com.library.controller.docs.BookController;
import com.library.controller.dto.BarcodeImage;
import com.library.controller.dto.BookDto;
import com.library.model.entities.Book;
import com.library.model.entities.BookCategory;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {

    private final BookService bookService;

    @Override
    public List<BookDto> listBooksByFilter(String search, int page, int size, String[] sort) {
        return bookService.listBooksByFilter(search, page, size, sort);
    }

    @Override
    public BookDto findBookById(Long bookId) {
        return bookService.findBookById(bookId);
    }

    @Override
    public BarcodeImage getBarcodeImageByBookId(long bookId, Integer width, Integer height) {
        return bookService.getBarcodeImageByBookId(bookId, width, height);
    }

    @Override
    public Long countAllBook() {
        return bookService.countAllBook();
    }

    @Override
    public List<BookCategory> listCategoriesByFilter(String search, int page, int size, String[] sort) {
        return bookService.listCategoriesByFilter(search, page, size, sort);
    }

    @Override
    public Book addNewBook(BookDto bookDto) {
        return bookService.addNewBook(bookDto);
    }

}
