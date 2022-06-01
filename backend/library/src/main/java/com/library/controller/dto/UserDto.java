package com.library.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.model.entities.Language;
import com.library.model.entities.User;
import com.library.model.entities.UserType;
import com.library.model.entities.UserTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty(value = "user_id")
    private Long userId;
    private String title;

    @Length(min = 3)
    @NotBlank
    private String firstname;
    @JsonProperty(value = "middle_name")
    private String middleName;

    @Length(min = 3)
    @NotBlank
    private String lastname;

    @JsonProperty(value = "birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank
    private String email;
    private String username;
    private boolean active;

    private String postcode;
    @Max(30)
    private String city;
    @Max(30)
    private String street;
    @Max(10)
    @JsonProperty(value = "street_number")
    private String streetNumber;

    @JsonProperty(value = "user_type")
    private UserTypes userTypes;
    @JsonProperty(value = "qr_code")
    private String qrCode;
    private Language language;

    @JsonProperty(value = "full_name")
    private String fullName;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "img")
    private String img;

    public UserDto(User user) {
        try {
            BeanUtils.copyProperties(this, user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        this.language = user.getLanguage();
        this.userId = user.getId();
        this.userTypes = user.getUserType().getUserType();
        this.img = Base64.encodeBase64String(user.getProfileImage());
    }
}
