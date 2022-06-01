package com.library.controller.docs;

import com.library.controller.dto.ContactMailRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/contacts/")
@Api(tags = {"Kapcsolat"})
public interface ContactController {

    @PostMapping(path = "/mail", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Email küldés az üzemeltetőknek", notes = "A felhasználó képes e-mailt küldeni nekünk. Ő is megkapja másolatként")
    boolean sendMailToUs(@Validated @RequestBody ContactMailRequest login);
}
