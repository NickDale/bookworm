package com.library.error.helper;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Data
@AllArgsConstructor
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private List<ApiValidationError> errors;
    private String message;
    private String statusName;
    private Integer statusCode;
    private String debugMessage;

    public ErrorResponse(HttpStatus status, Throwable ex) {
        this(status, null, ex);
    }

    public ErrorResponse(HttpStatus status, String message, Throwable ex) {
        this.statusCode = status.value();
        this.statusName = status.name();
        this.message = ofNullable(message).filter(StringUtils::isNotBlank).orElse(ex.getMessage());
        this.debugMessage = ex.getLocalizedMessage();
        setErrors(ex);
    }

    private void setErrors(Throwable ex) {
        if (ex instanceof ConstraintViolationException) {
            addValidationErrors(((ConstraintViolationException) ex).getConstraintViolations());
        } else if (ex instanceof MethodArgumentNotValidException) {
            var validException = (MethodArgumentNotValidException) ex;
            addValidationErrors(validException.getBindingResult().getFieldErrors());
            addValidationError(validException.getBindingResult().getGlobalErrors());
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            var exception = (MethodArgumentTypeMismatchException) ex;
            this.message = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                    exception.getName(), exception.getValue(), ofNullable(exception.getRequiredType()).map(Class::getSimpleName).orElse(" - "));
        }
    }

    private void addSubError(ApiValidationError subError) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

}

