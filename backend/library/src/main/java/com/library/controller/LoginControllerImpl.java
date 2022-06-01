package com.library.controller;

import com.library.config.jwt.TokenProvider;
import com.library.controller.docs.LoginController;
import com.library.controller.dto.Login;
import com.library.controller.dto.LoginResponse;
import com.library.controller.dto.RegistrationRequest;
import com.library.error.exception.UnAuthorizedException;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

import static com.library.util.Constant.USERNAME_REQUIRED;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class LoginControllerImpl implements LoginController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Override
    public ResponseEntity<LoginResponse> login(Login login, HttpServletRequest request) {
        var data = userService.login(login);
        if ("mobile".equalsIgnoreCase(request.getHeader("Application")) && !data.getUserType().isUser()) {
            throw new UnAuthorizedException();
        }
        return ResponseEntity.ok()
                .header(AUTHORIZATION, tokenProvider.generateToken(data, request))
                .body(data);
    }

    @Override
    public void registration(RegistrationRequest registrationRequest) {
        userService.registerNewUser(registrationRequest);
    }

    @Override
    public void resetPassword(Login login) {
        if (isBlank(login.getUsername())) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, USERNAME_REQUIRED);
        userService.resetPassword(login.getUsername());
    }

    @Override
    public boolean validateToken(HttpServletRequest request) {
        return tokenProvider.validateToken(request.getHeader(AUTHORIZATION), request);
    }
}
