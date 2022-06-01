package com.library.util;

import com.library.model.entities.Language;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public interface I18NBundle extends Serializable {

    private String getBundleNameWithPrefix() {
        return EmailResourceBundle.PREFIX + getBundleName();
    }

    default String getString(String key) {
        return ResourceBundle.getBundle(getBundleNameWithPrefix()).getString(normalizeName(key));
    }

    default String getString(Language language, String key) {
        return ResourceBundle.getBundle(getBundleNameWithPrefix(), new Locale(language.name())).getString(normalizeName(key));
    }

    default String getString(Locale local, String key) {
        return ResourceBundle.getBundle(getBundleNameWithPrefix(), local).getString(normalizeName(key));
    }

    default String getString(Locale local, Object... args) {
        return MessageFormat.format(getString(local), args);
    }

    default String normalizeName(String key) {
        return key.replaceAll("_", ".").toLowerCase();
    }

    String getBundleName();
}
