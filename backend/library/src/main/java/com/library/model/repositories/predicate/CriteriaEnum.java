package com.library.model.repositories.predicate;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CriteriaEnum {

    EQUAL(":"),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    LIKE("~"),
    NEGATE("!"),
    ;

    private final String value;

    CriteriaEnum(String value) {
        this.value = value;
    }

    public static CriteriaEnum findByValue(String operation) {
        return Arrays.stream(CriteriaEnum.values())
                .filter(c -> c.getValue().equalsIgnoreCase(operation))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
