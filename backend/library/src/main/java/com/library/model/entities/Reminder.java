package com.library.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminder")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Reminder extends AbstractEntity {

    @NotNull
    @Column(name = "loan_item_id", nullable = false, updatable = false)
    private Long loanId;

    @CreatedDate
    @Column(name = "notification_date", nullable = false, updatable = false)
    private LocalDateTime notificationDate;
}
