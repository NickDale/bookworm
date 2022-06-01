package com.library.service.mail.templates;

import com.library.model.entities.User;
import com.library.service.mail.BaseEmail;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LibraryMail extends BaseEmail {

    private static final String BOOK_WORM_LOGO = "static/images/logo.png";
    private final MailType mailType;
    private final MailData mailData;

    public LibraryMail(MailType mailType, User user) {
        this(mailType, user, null);
    }

    public LibraryMail(MailType mailType, User user, String generatePassword) {
        this.mailType = mailType;
        this.mailData = new MailData(user, generatePassword);
    }

    public LibraryMail(MailType mailType, MailData data) {
        this.mailType = mailType;
        this.mailData = data;
    }

    @Override
    protected String getEmailSubject() {
        return getString(mailData.getLanguage(), mailType.getSubjectKey());
    }

    @Override
    protected String getEmailTemplatePath() {
        return mailType.getPath();
    }

    @Override
    protected Context getEmailContext() {
        var context = new Context();
        context.setLocale(new Locale(mailData.getLanguage().name()));
        context.setVariable("data", mailData);
        return context;
    }

    @Override
    protected Map<String, String> getInlinedFiles() {
        Map<String, String> files = new HashMap<>();
        files.put("logo", BOOK_WORM_LOGO);

        return files;
    }
}
