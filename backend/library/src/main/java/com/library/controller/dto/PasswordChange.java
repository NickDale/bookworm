package com.library.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChange {

    @NotNull
    @JsonProperty(required = true,value = "user_id")
    private Long userId;
    @NotBlank
    @JsonProperty(required = true,value = "old_password")
    private String oldPassword;
    @NotBlank
    @JsonProperty(required = true,value = "new_password")
    private String newPassword;
}
