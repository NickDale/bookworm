package com.library.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"startDateLogStamp", "createdBy", "lastModifiedUser", "lastModifiedDate"})
public abstract class AbstractEntityLogStamp extends AbstractEntity {

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    @JsonIgnore
    private LocalDateTime startDateLogStamp;

    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    @Column(name = "last_mod_user")
    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedUser;

    @Column(name = "last_mod_date")
    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntityLogStamp that = (AbstractEntityLogStamp) o;
        return Objects.equals(startDateLogStamp, that.startDateLogStamp)
                && Objects.equals(createdBy, that.createdBy)
                && Objects.equals(lastModifiedUser, that.lastModifiedUser)
                && Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateLogStamp, createdBy, lastModifiedUser, lastModifiedDate);
    }
}

