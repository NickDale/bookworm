package com.library.model.repositories;

import com.library.model.entities.Loan;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface LoanRepository extends AbstractJpaRepository<Loan> {

    Optional<Loan> findLoanItemByBook_BarcodeAndEndDateIsNull(@NotBlank String barcode);

}
