package com.library.model.repositories;

import com.library.model.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends AbstractJpaRepository<User> {

    @Transactional(readOnly = true)
    Optional<User> findUserByQrCodeIgnoreCase(@NotBlank String qrCode);

    @Transactional(readOnly = true)
    Optional<User> findUserByEmailIgnoreCase(@NotBlank String email);

    @Transactional(readOnly = true)
    Optional<User> findUserByUsername(@NotBlank String userName);

    @Transactional(readOnly = true)
    @Query(value = "SELECT u FROM User u where upper(u.email) = upper(:username) OR u.username = :username")
    Optional<User> findUserByEmailIgnoreCaseOrUsername(@NotBlank String username);

    @Transactional(readOnly = true)
    boolean existsUserByEmailIgnoreCase(@NotBlank String email);

    @Transactional(readOnly = true)
    int countByUsernameLike(@NotBlank String userName);

    @Transactional(readOnly = true)
    @Query(value = "SELECT u FROM User u "
            + "INNER JOIN Loan l ON l.userId = u.id AND l.id NOT IN (SELECT r.loanId FROM Reminder r )"
            + "WHERE l.endDate IS NULL AND l.estimateEndDate <= :date")
    List<User> findAllUserWhoHasActiveLoanWithoutReminder(@NonNull LocalDateTime date);

    @Transactional(readOnly = true)
    @Query(value = "SELECT u FROM User u " +
            "INNER JOIN Loan l ON l.userId = u.id " +
            "WHERE l.endDate IS NULL AND l.estimateEndDate <= :date")
    List<User> findAllUserWhoHasExpiredLoan(@NonNull LocalDateTime date);
}
