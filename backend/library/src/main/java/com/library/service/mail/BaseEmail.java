package com.library.service.mail;

import com.library.config.ApplicationContextProvider;
import com.library.util.I18NBundle;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

import static com.library.util.EmailResourceBundle.EMAIL;

public abstract class BaseEmail implements I18NBundle {

    private final SpringTemplateEngine templateEngine;

    public BaseEmail() {
        this.templateEngine = ApplicationContextProvider.getBean(SpringTemplateEngine.class);
    }

    protected abstract String getEmailSubject();

    protected abstract String getEmailTemplatePath();

    protected abstract Context getEmailContext();

    protected abstract Map<String, String> getInlinedFiles();

    protected String getHTMLEmail() {
        return process(getEmailTemplatePath(), getEmailContext());
    }

    protected String process(String templatePath, Context context) {
        return templateEngine.process(templatePath, context);
    }

    @Override
    public String getBundleName() {
        return EMAIL.getName();
    }
}