package com.library.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.model.entities.Loan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {

    @JsonProperty(value = "loan_id")
    private Long loanId;
    @JsonProperty(value = "user_id")
    private Long userId;

    private BookDto book;

    @JsonProperty(value = "estimate_end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate estimateEndDate;

    @JsonProperty(value = "extended_end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedEndDate;

    @JsonProperty(value = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate realEndDate;

    @JsonProperty(value = "creation_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;

    public LoanDto(Loan loan) {
        this.book = new BookDto(loan.getBook());
        this.estimateEndDate = loan.getEstimateEndDate();
        this.realEndDate = loan.getEndDate();
        this.creationDate = loan.getStartDateLogStamp();
        this.extendedEndDate = loan.getExtendedEndDate();
        this.loanId = loan.getId();
        this.userId = loan.getUserId();
    }
}
