package com.library.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_type")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserType extends AbstractEntityLogStamp {

    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true, updatable = false)
    protected UserTypes userType;
}