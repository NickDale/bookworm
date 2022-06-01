package com.library.controller.dto;

import com.library.model.entities.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUser {

    private String title;
    @Length(min = 3)
    @NotBlank
    private String firstname;
    private String middleName;
    @Length(min = 3)
    @NotBlank
    private String lastname;

    private LocalDate birthDate;

    @NotBlank
    private String email;
    @NotBlank
    private String postcode;
    @Length(min = 1, max = 30)
    private String city;
    @Length(min = 1, max = 30)
    private String street;
    @Length(min = 1, max = 10)
    private String streetNumber;
    private Language language = Language.HU;
}
