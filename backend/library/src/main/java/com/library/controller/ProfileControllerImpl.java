package com.library.controller;

import com.library.controller.docs.ProfileController;
import com.library.controller.dto.PasswordChange;
import com.library.controller.dto.UserDto;
import com.library.controller.dto.UserUpdateRequest;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ProfileControllerImpl implements ProfileController {

    private final UserService userService;

    @Override
    public void changePassword(@RequestBody PasswordChange passwordChange) {
        userService.changePassword(passwordChange);
    }

    @Override
    public UserDto userDetails() {
        return userService.getDetailsOfLoggedInUser();
    }

    @Override
    public void uploadImg(MultipartFile multipartFile) throws IOException {
        userService.uploadProfileImg(multipartFile.getBytes());
    }

    @Override
    public void updateProfile(UserUpdateRequest updateRequest) {
        userService.updateProfile(updateRequest);
    }

    @Override
    public boolean isEmailAddressOk(String email) {
        return userService.checkEmailAddress(email);
    }
}
