package com.library.service.mail;

import com.library.model.entities.User;
import com.library.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class Scheduler {

    private final MailService mailService;
    private final UserRepository userRepository;

    @Value("${reminder.in.days}")
    private int reminderBeforeExpireDay;

    public void sendReminders() {
        List<User> allUserWhoHasActiveLoan = userRepository.findAllUserWhoHasActiveLoanWithoutReminder(
                LocalDateTime.now().minusDays(reminderBeforeExpireDay)
        );
        if (isNotEmpty(allUserWhoHasActiveLoan)) {
            //TODO: send reminder
        }

        List<User> allUserWhoHasExpiredLoan = userRepository.findAllUserWhoHasExpiredLoan(LocalDateTime.now());
        if (isNotEmpty(allUserWhoHasExpiredLoan)) {
            //TODO send expried
        }
    }
}
