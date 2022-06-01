package com.library.util;


import lombok.Getter;

import java.util.Arrays;

@Getter
public enum EmailResourceBundle {

    EMAIL("email"),
    ;
    public static final String PREFIX = "i18n/";

    private final String name;

    EmailResourceBundle(String name) {
        this.name = name;
    }

    public static String[] getAllBundle() {
        return Arrays.stream(EmailResourceBundle.values())
                .map(rb -> PREFIX.concat(rb.getName())).toArray(String[]::new);
    }

}
