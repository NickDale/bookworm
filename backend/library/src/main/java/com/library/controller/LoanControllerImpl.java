package com.library.controller;

import com.library.controller.docs.LoanController;
import com.library.controller.dto.LoanDto;
import com.library.controller.dto.LoanRequest;
import com.library.model.entities.Book;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanControllerImpl implements LoanController {

    private final BookService bookService;

    @Override
    public LoanDto getLoanDetailsById(final long loanId) {
        return bookService.getLoanItemDetailsByItemId(loanId);
    }

    @Override
    public void extendLoanByLoanId(final long loanId) {
        bookService.extendLoanByLoanId(loanId);
    }

    @Override
    public List<LoanDto> listUserLoansItemsByFilter(final long userId, boolean onlyHistory,
                                                    boolean onlyActive, String bookTitle, int page, int size) {
        return bookService.listUserLoanItems(userId, onlyHistory, onlyActive, bookTitle, page, size);
    }

    @Override
    public List<Book> createLoan(final String username, LoanRequest loanRequest) {
        return bookService.createLoan(username, loanRequest);
    }

    @Override
    public Book createLoanByBarCode(String username, String barcode) {
        return bookService.createLoanByBarcode(username, barcode);
    }

    @Override
    public void loanRetrieval(String barcode) {
        bookService.retrievalLoan(barcode);
    }

}
