package com.library.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "book_loan")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Loan extends AbstractEntityLogStamp {

    @NotNull
    @Column(name = "estimate_end_date", nullable = false, updatable = false)
    private LocalDate estimateEndDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "extended_date")
    private LocalDate extendedEndDate;

    @NotNull
    @Column(name = "book_id", nullable = false, updatable = false)
    private Long bookId;

    @NotNull
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Book book;
}
