package com.library.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactMailRequest {

    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 500)
    private String message;
}
