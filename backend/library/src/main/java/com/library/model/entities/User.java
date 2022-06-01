package com.library.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.controller.dto.AddUser;
import com.library.controller.dto.ModifyUser;
import com.library.controller.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.lang.Boolean.TRUE;
import static java.util.Optional.ofNullable;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonIgnoreProperties(value = {"password", "salt"})
public class User extends AbstractEntityLogStamp {

    private String title;

    @NotBlank
    private String firstname;

    @Column(name = "middlename")
    private String middleName;

    @NotBlank
    private String lastname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Language language;

    @Size(max = 100)
    @NotBlank
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Size(max = 15)
    @NotBlank
    @Column(name = "username", unique = true, nullable = false, length = 15)
    private String username;

    @Column(name = "encrypted_password", length = 500)
    private String password;

    @Column(name = "salt", length = 500)
    private String salt;

    @Column(columnDefinition = "BIT", nullable = false, length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;

    @Column(name = "deactivation_date")
    private LocalDateTime deactivationDate;

    @Size(max = 15)
    @Column(name = "postcode", length = 15)
    private String postcode;

    @Size(max = 30)
    @Column(name = "city", length = 30)
    private String city;

    @Size(max = 30)
    @Column(name = "street", length = 30)
    private String street;

    @Size(max = 10)
    @Column(name = "street_number", length = 10)
    private String streetNumber;

    @NotNull
    @Column(name = "user_type_id", nullable = false, updatable = false)
    private Long userTypeId;

    @ManyToOne
    @JoinColumn(name = "user_type_id", referencedColumnName = "id", updatable = false, insertable = false)
    private UserType userType;

    @Size(max = 100)
    @Column(name = "QR_code", unique = true, nullable = false, length = 100)
    private String qrCode;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false, insertable = false)
    private List<Loan> loans;

    @Lob
    @Column(name = "user_img")
    private byte[] profileImage;

    public User(UserDto userDto) {
        this.firstname = userDto.getFirstname();
        this.middleName = userDto.getMiddleName();
        this.lastname = userDto.getLastname();
        this.title = userDto.getTitle();
        this.email = userDto.getEmail();
        this.active = TRUE;
        this.postcode = userDto.getPostcode();
        this.city = userDto.getCity();
        this.street = userDto.getStreet();
        this.streetNumber = userDto.getStreetNumber();
    }

    public User(AddUser addUser) {
        this.firstname = addUser.getFirstname();
        this.middleName = addUser.getMiddleName();
        this.lastname = addUser.getLastname();
        this.title = addUser.getTitle();
        this.email = addUser.getEmail();
        this.active = TRUE;
        this.postcode = addUser.getPostcode();
        this.city = addUser.getCity();
        this.street = addUser.getStreet();
        this.streetNumber = addUser.getStreetNumber();
        this.birthDate = addUser.getBirthDate();
        this.language = addUser.getLanguage();
    }

    public String getFullName() {
        var name = ofNullable(this.title).filter(StringUtils::isNotBlank).orElse("");
        return language.isHungarian()
                ? String.join(" ", name, lastname, firstname)
                : String.join(" ", name, firstname, lastname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return active == user.active && Objects.equals(title, user.title) && Objects.equals(firstname, user.firstname)
                && Objects.equals(middleName, user.middleName) && Objects.equals(lastname, user.lastname)
                && Objects.equals(email, user.email) && Objects.equals(username, user.username)
                && Objects.equals(password, user.password) && Objects.equals(salt, user.salt)
                && Objects.equals(userTypeId, user.userTypeId) && Objects.equals(userType, user.userType)
                && Objects.equals(qrCode, user.qrCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, firstname, middleName, lastname, email,
                username, password, salt, active, userTypeId, userType, qrCode);
    }

    public void setModifications(ModifyUser modifyUser) {
        this.title = modifyUser.getTitle();
        this.firstname = modifyUser.getFirstname();
        this.middleName = modifyUser.getMiddleName();
        this.lastname = modifyUser.getLastname();
        this.birthDate = modifyUser.getBirthDate();
        this.email = modifyUser.getEmail();
        this.language = modifyUser.getLanguage();
        this.postcode = modifyUser.getPostcode();
        this.city = modifyUser.getCity();
        this.street = modifyUser.getStreet();
        this.streetNumber = modifyUser.getStreetNumber();
        this.active = modifyUser.isActive();
    }
}
