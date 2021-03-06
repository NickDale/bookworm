package com.library.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @Email
    @NotBlank
    private String email;

}
