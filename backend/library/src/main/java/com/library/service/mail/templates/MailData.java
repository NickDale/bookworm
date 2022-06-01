package com.library.service.mail.templates;

import com.library.model.entities.Language;
import com.library.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailData {

    private Language language;
    private String name;
    private String generatedPassword;
    private Boolean activation;

    public MailData(User user, String generatePassword) {
        this.generatedPassword = generatePassword;
        this.language = user.getLanguage();
        this.name = user.getFullName();
    }
}
