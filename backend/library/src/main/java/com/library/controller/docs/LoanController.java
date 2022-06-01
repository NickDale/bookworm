package com.library.controller.docs;

import com.library.controller.dto.LoanDto;
import com.library.controller.dto.LoanRequest;
import com.library.model.entities.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = {"Kölcsönzések"})
@Validated
@RequestMapping(value = "/loans/", produces = APPLICATION_JSON_VALUE)
public interface LoanController {

    @GetMapping(value = "/")
    @ApiOperation(value = "Az adott felhasználó kölcsönzött könyveinek listázása")
    List<LoanDto> listUserLoansItemsByFilter(@RequestParam(value = "userId") @Positive long userId,
                                             @RequestParam(defaultValue = "false", required = false) boolean onlyHistory,
                                             @RequestParam(defaultValue = "true", required = false) boolean onlyActive,
                                             @RequestParam(value = "book_title", required = false) String bookTitle,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "30") int size);

    @GetMapping(value = "/{loanId}")
    @ApiOperation(value = "Az kölcsönzés részleteit adja vissza Id alapján")
    LoanDto getLoanDetailsById(@PathVariable @Positive long loanId);

    @PutMapping(value = "/extend/{loanId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Meghosszabítja az adott könyv kölcsönzését (alapértelmezetten 1 héttel)")
    void extendLoanByLoanId(@PathVariable @Positive long loanId);

    @PostMapping(value = "/{username}", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Kölcsönzés")
    @ApiResponse(code = 201, message = "Visszaadja a sikeresen kikölcsönzött könyveket")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN')")
    List<Book> createLoan(@PathVariable String username,
                          @Validated @RequestBody LoanRequest loanRequest);

    @PostMapping(value = "/{username}/{barcode}")
    @ApiOperation(value = "Kölcsönzés", notes = "Vonalkód alapján")
    @ApiResponse(code = 201, message = "Visszaadja a vonalkód alapján sikeresen kikölcsönzött könyvet")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN')")
    Book createLoanByBarCode(@PathVariable String username,
                             @PathVariable String barcode);

    @PutMapping(value = "/retrieval/{barcode}")
    @ApiOperation(value = "Könyv visszahozása")
    @ApiResponse(code = 200, message = "Sikeresen visszahozták a könyvet")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN')")
    void loanRetrieval(@PathVariable String barcode);

}
