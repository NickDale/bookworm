package com.library.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserTypes {

    ADMIN(1), LIBRARIAN(2), USER(3);

    private final String value;
    private final Long id;

    UserTypes(Integer id) {
        this.id = id.longValue();
        this.value = this.name();
    }

    @JsonIgnore
    public boolean isLibrarian() {
        return this.equals(LIBRARIAN);
    }

    @JsonIgnore
    public boolean isUser() {
        return this.equals(USER);
    }

    @JsonIgnore
    public boolean isAdmin() {
        return this.equals(ADMIN);
    }
}
