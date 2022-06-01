package com.library.service.mail.templates;

import lombok.Getter;

@Getter
public enum MailType {

    REGISTRATION("registration", "registration.subject"),
    ACCOUNT_ACTIVATION("(de)activation", "activation.subject"),
    ACCOUNT_DEACTIVATION("(de)activation", "deactivation.subject"),
    REMINDER("reminder", "reminder.subject"),
    FORGOTTEN_PASSWORD("forgotten_password", "forgotten.password.subject"),
    ;

    private final String path;
    private final String subjectKey;

    MailType(String path, String subjectKey) {
        this.path = path;
        this.subjectKey = subjectKey;
    }
}
