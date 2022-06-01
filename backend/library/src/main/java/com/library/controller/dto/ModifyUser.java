package com.library.controller.dto;

import com.library.model.entities.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyUser {

    @NotNull
    private Long userId;
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
    private boolean active;
    private Language language;

    private String postcode;
    @Length(max = 30)
    private String city;
    @Length(max = 30)
    private String street;
    @Length(max = 10)
    private String streetNumber;
}
