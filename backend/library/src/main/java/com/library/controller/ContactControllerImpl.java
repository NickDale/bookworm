package com.library.controller;

import com.library.controller.docs.ContactController;
import com.library.controller.dto.ContactMailRequest;
import com.library.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactControllerImpl implements ContactController {

    private final MailService mailService;

    @Override
    public boolean sendMailToUs(ContactMailRequest mailRequest) {
        return mailService.sendMailToUs(mailRequest);
    }
}
