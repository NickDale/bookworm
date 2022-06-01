package com.library.controller.docs;

import com.library.controller.dto.Login;
import com.library.controller.dto.LoginResponse;
import com.library.controller.dto.RegistrationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/")
@Api(tags = {"Bejelentkezés"})
public interface LoginController {

    @PostMapping(path = "/login", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Bejelentkezés", notes = "JWT-t ad vissza a response AUTHORIZATION headerben")
    ResponseEntity<LoginResponse> login(@Validated @RequestBody Login login, HttpServletRequest request);

    @PostMapping(path = "/registration", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Regiosztráció", notes = "Weboldali regisztráció")
    @ResponseStatus(HttpStatus.CREATED)
    void registration(@Validated @RequestBody RegistrationRequest registrationRequest);

    @PostMapping(path = "/password/reset")
    @ApiOperation(value = "Elfelejtett jelszó", notes = "Újraküldi a felhasználónak beállított e-mail címre az új ideiglenes jelszót")
    @ResponseStatus(HttpStatus.OK)
    void resetPassword(@RequestBody Login login);

    @GetMapping(path = "/validate")
    @ApiOperation(value = "JWT validáció", notes = "Ellenőzi a token érvényességét")
    boolean validateToken(HttpServletRequest request);
}
