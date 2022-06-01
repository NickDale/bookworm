package com.library.service.mail;

import com.library.controller.dto.ContactMailRequest;
import com.library.model.entities.User;
import com.library.service.mail.templates.LibraryMail;
import com.library.service.mail.templates.MailData;
import com.library.service.mail.templates.MailType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import java.io.File;
import java.util.Collection;
import java.util.Collections;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final String CONTACT_SUBJECT = "KAPCSOLATFELVÉTEL";
    private final MailBuilder mailBuilder;
    @Value("${spring.mail.username}")
    private String bookwormMailAddress;

    public boolean sendMailToUs(ContactMailRequest mailRequest) {
        String messageNumber = mailBuilder
                .subject(String.join(" - ",CONTACT_SUBJECT, mailRequest.getName()))
                .recipients(bookwormMailAddress)
                .recipients(Message.RecipientType.CC, mailRequest.getEmail())
                .plainBody(mailRequest.getMessage())
                .build();
        return isNotBlank(messageNumber);
    }

    public void sendRegistrationMail(@NonNull final User user, @NonNull String generatePassword) {
        LibraryMail template = new LibraryMail(MailType.REGISTRATION, user, generatePassword);
        sendMail(template, user.getEmail());
    }

    public void sendForgottenPasswordMail(@NonNull final User user, @NonNull String generatePassword) {
        LibraryMail template = new LibraryMail(MailType.FORGOTTEN_PASSWORD, user, generatePassword);
        sendMail(template, user.getEmail());
    }

    public void sendActivationMail(@NonNull final User user, boolean isActive) {
        var data = MailData.builder()
                .activation(isActive)
                .name(user.getFullName())
                .language(user.getLanguage())
                .build();
        LibraryMail template = new LibraryMail(isActive ? MailType.ACCOUNT_ACTIVATION : MailType.ACCOUNT_DEACTIVATION, data);
        sendMail(template, user.getEmail());
    }


    public void sendMail(final BaseEmail template, final String to) {
        sendMail(template, to, Collections.emptyList());
    }

    public void sendMail(final BaseEmail template, final String to, final Collection<File> files) {
        sendMail(template, Message.RecipientType.TO, to, files);
    }

    private void sendMail(@NonNull final BaseEmail template, @NonNull final Message.RecipientType recipientType,
                          @NonNull final String to, @NonNull final Collection<File> files) {
        mailBuilder
                .recipients(recipientType, to)
                .addFiles(files)
                .addTemplate(template)
                .build();
    }

}
