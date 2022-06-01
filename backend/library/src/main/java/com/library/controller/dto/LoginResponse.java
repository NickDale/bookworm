package com.library.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.model.entities.User;
import com.library.model.entities.UserTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    @JsonProperty(value = "user_id")
    private Long userId;

    private String firstname;
    @JsonProperty(value = "middle_name")
    private String middleName;

    private String lastname;
    @JsonProperty(value = "full_name")
    private String fullName;

    private String email;
    private String phone;
    private String username;
    @JsonProperty(value = "user_type")
    private UserTypes userType;

    public LoginResponse(User user) {
        this.email = user.getEmail();
        this.userId = user.getId();
        this.firstname = user.getFirstname();
        this.middleName = user.getMiddleName();
        this.lastname = user.getLastname();
        this.phone = user.getPhoneNumber();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.userType = user.getUserType().getUserType();
    }

}
