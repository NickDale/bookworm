package com.library.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.oned.Code128Writer;
import com.library.controller.dto.BarcodeImage;
import com.library.controller.dto.BookDto;
import com.library.controller.dto.LoanDto;
import com.library.controller.dto.LoanRequest;
import com.library.error.exception.EntityNotFoundException;
import com.library.error.exception.ForbiddenException;
import com.library.error.exception.NotAcceptable;
import com.library.error.exception.PreConditionalException;
import com.library.error.exception.UserBlockedException;
import com.library.model.entities.Author;
import com.library.model.entities.Book;
import com.library.model.entities.BookCategory;
import com.library.model.entities.Loan;
import com.library.model.entities.User;
import com.library.model.repositories.AuthorRepository;
import com.library.model.repositories.BookRepository;
import com.library.model.repositories.LoanRepository;
import com.library.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.library.util.Constant.BOOK_ALREADY_BACK;
import static com.library.util.Constant.BOOK_NOT_FOUND_BY_BARCODE;
import static com.library.util.Constant.BOOK_NOT_LOANABLE;
import static com.library.util.Constant.LOAN_ALREADY_EXPIRED_OR_BACK;
import static com.library.util.Constant.ONLY_ONCE_EXTENDED_ENABLED;
import static com.library.util.Constant.OWN_RENTALS_VISIBLE;
import static com.library.util.Constant.USER_NOT_FOUND_BY_ID;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final String PATTERN = "0000000000";
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final UserService userService;

    @Value("${extended.day.number}")
    private Long extendedDayNumber;

    public LoanDto getLoanItemDetailsByItemId(final long loanId) {
        return new LoanDto(loanRepository.getById(loanId));
    }

    @Transactional
    public void extendLoanByLoanId(final long loanId) {
        var loan = loanRepository.getById(loanId);
        if (nonNull(loan.getEndDate()) || LocalDate.now().isAfter(loan.getEstimateEndDate())) {
            throw new NotAcceptable(LOAN_ALREADY_EXPIRED_OR_BACK);
        }
        if (nonNull(loan.getExtendedEndDate())) {
            throw new PreConditionalException(ONLY_ONCE_EXTENDED_ENABLED);
        }
        loan.setExtendedEndDate(loan.getEstimateEndDate().plusDays(extendedDayNumber));
    }

    public List<BookDto> listBooksByFilter(String search, final int page, final int size, String[] sorts) {
        return bookRepository.findAll(Book.class, search, page, size, sorts)
                .stream()
                .map(BookDto::new)
                .collect(toList());
    }

    public List<BookCategory> listCategoriesByFilter(String search, final int page, final int size, String[] sorts) {
        return bookRepository.findAll(BookCategory.class, search, page, size, sorts);
    }

    @Transactional
    public Book addNewBook(final BookDto bookDto) {
        var book = new Book(bookDto);
        book.setBarcode(generateBarcode());
        for (var authorName : bookDto.getAuthors()) {
            Author author;
            var optionalAuthor = authorRepository.findAuthorByNameIgnoreCase(authorName);
            if (optionalAuthor.isEmpty()) {
                author = authorRepository.save(new Author(authorName));
            } else {
                author = optionalAuthor.get();
            }
            book.addAuthor(author);
        }
        return bookRepository.save(book);
    }

    public String generateBarcode() throws HibernateException {
        return new DecimalFormat(PATTERN).format(bookRepository.count() + 1);
    }

    @Transactional
    public List<Book> createLoan(@NonNull final String username, @NonNull final LoanRequest loanRequest) {
        var user = findUserByName(username);
        List<Book> availableBooks = bookRepository.findAvailableBooksByIds(loanRequest.getBookIds());

        var loanItems = availableBooks
                .stream()
                .map(book -> createLoanItem(user.getId(), book))
                .collect(Collectors.toSet());

        bookRepository.saveAllEntity(loanItems);
        return availableBooks;
    }

    public void retrievalLoan(@NonNull final String barcode) {
        var loanItem = loanRepository.findLoanItemByBook_BarcodeAndEndDateIsNull(barcode)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND_BY_BARCODE, barcode));
        if (nonNull(loanItem.getEndDate())) {
            throw new PreConditionalException(BOOK_ALREADY_BACK);
        }

        loanItem.setEndDate(LocalDate.now());
        loanRepository.saveEntity(loanItem);
    }

    @Transactional
    public Book createLoanByBarcode(@NonNull final String username, @NonNull final String barcode) {
        var user = findUserByName(username);
        var book = bookRepository.findAvailableBookByBarcode(barcode)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND_BY_BARCODE, barcode));
        if (!book.isLoanable()) {
            throw new NotAcceptableStatusException(BOOK_NOT_LOANABLE);
        }
        loanRepository.save(createLoanItem(user.getId(), book));
        return book;
    }

    private Loan createLoanItem(final @NonNull long userId, @NonNull final Book book) {
        return Loan.builder()
                .bookId(book.getId())
                .userId(userId)
                .estimateEndDate(LocalDate.now().plusDays(book.getMaxLoanDays()))
                .build();
    }

    private User findUserByName(@NonNull final String username) {
        var user = userRepository.findUserByEmailIgnoreCaseOrUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_BY_ID, username));
        if (!user.isActive()) {
            throw new UserBlockedException();
        }
        return user;
    }

    public Long countAllBook() {
        return bookRepository.count();
    }

    public BookDto findBookById(final long bookId) {
        return new BookDto(bookRepository.getById(bookId));
    }

    public BarcodeImage getBarcodeImageByBookId(final long bookId, final Integer width, final Integer height) {
        var book = bookRepository.getById(bookId);
        return new BarcodeImage(generateBarcodeImage(book.getBarcode(), width, height));
    }

    private String generateBarcodeImage(final String barcodeText, final Integer width, final Integer height) {
        var barcodeWriter = new Code128Writer();
        var img = MatrixToImageWriter.toBufferedImage(
                barcodeWriter.encode(
                        barcodeText,
                        BarcodeFormat.CODE_128,
                        ofNullable(width).orElse(400),
                        ofNullable(height).orElse(100)
                )
        );

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(img, "png", baos);
            return Base64.encodeBase64String(baos.toByteArray());
        } catch (IOException ex) {
            throw new RuntimeException("invalid file\n " + ex.getMessage());
        }
    }

    public List<LoanDto> listUserLoanItems(final long userId, final boolean onlyHistory, final boolean onlyActive,
                                           final String bookTitle, final int page, final int size) {
        User loggedInUser = userService.findUserLoggedInUser();
        boolean sameUser = loggedInUser.getId().equals(userId);
        if (!sameUser && !loggedInUser.getUserType().getUserType().isLibrarian()) {
            throw new ForbiddenException(OWN_RENTALS_VISIBLE);
        }
        List<LoanDto> loanDtos = findUserById(userId).getLoans()
                .stream()
                .filter(filterLoans(onlyHistory, onlyActive, bookTitle))
                .map(LoanDto::new)
                .collect(toList());
        return createPageList(loanDtos, page, size);
    }

    private Predicate<Loan> filterLoans(final boolean onlyHistory, final boolean onlyActive, final String bookTitle) {
        return loan -> isNotBlank(bookTitle) ? loan.getBook().getTitle().equalsIgnoreCase(bookTitle) : TRUE
                && (onlyHistory ? nonNull(loan.getEndDate()) : TRUE)
                && (onlyActive ? isNull(loan.getEndDate()) : TRUE);
    }

    private <T> List<T> createPageList(List<T> list, final int page, final int size) {
        PagedListHolder<T> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);
        return pagedListHolder.getPageList();
    }

    public User findUserById(final long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_BY_ID, id));
    }

}
