package com.library.service.mail;

import com.library.util.EmailResourceBundle;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class MailConfig {

    private static final String EMAIL_TEMPLATE_BASE_PATH = "templates/";
    private static final String EXTENSION = ".html";

    @Bean
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public MailBuilder mailBuilder(JavaMailSender javaMailSender) {
        return new MailBuilder(javaMailSender);
    }

    @Bean("emailTemplateEngine")
    public SpringTemplateEngine templateEngine(MessageSource messageSource,
                                               ClassLoaderTemplateResolver templateResolver) {
        var templateEngine = new SpringTemplateEngine();
        templateEngine.setMessageSource(messageSource);
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    @Bean
    ClassLoaderTemplateResolver templateResolver() {
        var templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(EMAIL_TEMPLATE_BASE_PATH);
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(EXTENSION);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8.name());
        return templateResolver;
    }

    @Bean
    MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(UTF_8.name());
        messageSource.setBasenames(EmailResourceBundle.getAllBundle());
        return messageSource;
    }

}
