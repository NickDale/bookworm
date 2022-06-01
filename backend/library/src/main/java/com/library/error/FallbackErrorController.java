package com.library.error;

import com.library.error.exception.UnAuthorizedException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/error")
public class FallbackErrorController implements ErrorController {

    @RequestMapping(method = {POST, GET, PUT})
    public void genericErrorHandler(HttpServletRequest request) {
        Object attribute = request.getAttribute("javax.servlet.error.exception");
        if (attribute != null) {
            if (attribute instanceof BadCredentialsException) {
                throw (BadCredentialsException) attribute;
            } else if (attribute instanceof UnAuthorizedException) {
                throw (UnAuthorizedException) attribute;
            }
            throw new RuntimeException("uncaught /error ex");
        }
    }
}
