package com.library.controller.docs;

import com.library.controller.dto.PasswordChange;
import com.library.controller.dto.UserDto;
import com.library.controller.dto.UserUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping(value = "/profiles/")
@Api(tags = {"Könyvtárt használó felhasználók érik el"})
public interface ProfileController {

    @PostMapping(value = "/password/change")
    @ApiOperation(value = "Jelszó módosítás", notes = "Saját felhasználó jelszavának módosítása")
    @ResponseStatus(HttpStatus.OK)
    void changePassword(@RequestBody PasswordChange passwordChange);

    @GetMapping(value = "/details")
    @ApiOperation(value = "Felhasználó adatok", notes = "Visszaadja a bejelentkezett felhasználó adatait")
    UserDto userDetails();

    @PostMapping("/image")
    @ApiOperation(value = "Felhasználó profilképének feltöltése")
    void uploadImg(@RequestParam("file") MultipartFile multipartFile) throws IOException;

    @PatchMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Felhasználó egyes adatainak módosítása")
    void updateProfile(@RequestBody UserUpdateRequest updateRequest);

    @GetMapping("/check/{email}")
    @ApiOperation(value = "Megmondja, hogy az adott e-mail cím használható vagy sem (true = igen, false = nem)")
    boolean isEmailAddressOk(@PathVariable String email);
}
