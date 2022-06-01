package com.library.util;

public interface Constant {
    String USER_NOT_FOUND = "User not found ";
    String USER_NOT_FOUND_BY_ID = USER_NOT_FOUND + "with the given {} id";
    String BOOK_NOT_FOUND_BY_BARCODE = "Book not found with this barcode: {}";
    String CAN_CHANGE_ONLY_THE_OWN_PASS = "You can change only your own password";
    String USER_IS_BLOCKED = "User is blocked";
    String ADMIN_CAN_ONLY_MODIFY_LIBRARIANS = "Admin can only modify librarians";
    String EMAIL_ALREADY_EXIST = "This email address already exist";
    String USER_ALREADY = "This user is already {}";
    String BOOK_NOT_LOANABLE = "Book is not loanable";
    String BOOK_ALREADY_BACK = "The book has already been brought back to the library";

    String USERNAME_REQUIRED = "Username is required";
    String OWN_RENTALS_VISIBLE = "Only your own rentals are visible";
    String LOAN_DOES_NOT_EXIST = "Didn't find anything with the given id";
    String LOAN_ALREADY_EXPIRED_OR_BACK = "This is already expired or war brought back";
    String ONLY_ONCE_EXTENDED_ENABLED = "This book has already been extended";
}
