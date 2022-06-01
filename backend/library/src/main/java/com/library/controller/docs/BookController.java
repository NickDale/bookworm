package com.library.controller.docs;

import com.library.controller.dto.BarcodeImage;
import com.library.controller.dto.BookDto;
import com.library.model.entities.Book;
import com.library.model.entities.BookCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = {"Könyvek"})
@Validated
@RequestMapping(value = "/books/", produces = APPLICATION_JSON_VALUE)
public interface BookController {

    @GetMapping(value = "/")
    @ApiOperation(value = "Könyvek listázása filterrel")
    List<BookDto> listBooksByFilter(@RequestParam(value = "search", required = false) String search,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "30") int size,
                                    @RequestParam(defaultValue = "id,asc") String[] sort);

    @GetMapping(value = "/{bookId}")
    @ApiOperation(value = "Könyv By id")
    BookDto findBookById(@PathVariable Long bookId);

    @GetMapping(value = "/barcode/{bookId}")
    @ApiOperation(value = "Visszaadja a vonalkódot a könyvid alapján")
    BarcodeImage getBarcodeImageByBookId(@PathVariable @Min(1) long bookId,
                                         @RequestParam(required = false) Integer width,
                                         @RequestParam(required = false) Integer height);

    @GetMapping(path = "/count")
    @ApiOperation(value = "Könyvtárban lévő könyvek száma")
    Long countAllBook();

    @GetMapping(value = "/categories")
    @ApiOperation(value = "Könyv kategóriák listázása filterrel")
    List<BookCategory> listCategoriesByFilter(@RequestParam(value = "search", required = false) String search,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "30") int size,
                                              @RequestParam(defaultValue = "id,asc") String[] sort);

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN')")
    @ApiOperation(value = "Új Könyv hozzáadása")
    Book addNewBook(@Validated @RequestBody BookDto bookDto);

}
