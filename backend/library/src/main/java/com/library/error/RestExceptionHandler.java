package com.library.error;

import com.library.error.exception.*;
import com.library.error.helper.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorResponse apiError, HttpServletRequest request) {
        return ResponseEntity.status(apiError.getStatusCode())
                .body(apiError);
    }

    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<?> exceptionHandler(Throwable ex, HttpServletRequest request) {
        log.error("SERVER ERROR: {0}", ex);
        return buildErrorResponse(new ErrorResponse(INTERNAL_SERVER_ERROR, ex), request);
    }

    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    protected ResponseEntity<?> handler(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append("Media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildErrorResponse(new ErrorResponse(UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex), request);
    }

    @ExceptionHandler(value = {
            BadCredentialsException.class,
            BadCredentials.class,
            HttpMessageConversionException.class,
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            ConversionNotSupportedException.class,
            BindException.class
    })
    protected ResponseEntity<?> handler(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(new ErrorResponse(BAD_REQUEST, ex), request);
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    protected ResponseEntity<?> handler(UnAuthorizedException ex, HttpServletRequest request) {
        return buildErrorResponse(new ErrorResponse(UNAUTHORIZED, ex), request);
    }

    @ExceptionHandler(value = {
            ForbiddenException.class,
            AccessDeniedException.class,
            UserBlockedException.class
    })
    protected ResponseEntity<?> forbiddenHandler(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(new ErrorResponse(FORBIDDEN, ex), request);
    }

    @ExceptionHandler(value = PreConditionalException.class)
    protected ResponseEntity<?> handler(PreConditionalException ex, HttpServletRequest request) {
        return buildErrorResponse(new ErrorResponse(PRECONDITION_FAILED, ex), request);
    }

    @ExceptionHandler(value = {
            HttpMediaTypeNotAcceptableException.class,
            NotAcceptable.class
    })
    protected ResponseEntity<?> notAcceptHandler(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(new ErrorResponse(NOT_ACCEPTABLE, ex), request);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            HttpServerErrorException.InternalServerError.class,
            HttpMessageNotWritableException.class
    })
    protected ResponseEntity<?> errorHandler(Exception ex, HttpServletRequest request) {
        log.trace("InternalServerException {0}", ex);
        return buildErrorResponse(new ErrorResponse(INTERNAL_SERVER_ERROR, ex), request);
    }

    @ExceptionHandler(value = {
            EntityNotFoundException.class
    })
    protected ResponseEntity<?> conflictHandler(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(new ErrorResponse(CONFLICT, ex), request);
    }

    @ExceptionHandler(value = {
            NoHandlerFoundException.class
    })
    protected ResponseEntity<?> notFoundHandler(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(new ErrorResponse(NOT_FOUND, ex), request);
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class
    })
    protected ResponseEntity<?> methodNotAllowedHandler(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(new ErrorResponse(METHOD_NOT_ALLOWED, ex), request);
    }

}