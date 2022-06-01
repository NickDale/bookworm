package com.library.controller;

import com.library.controller.docs.UserController;
import com.library.controller.dto.AddUser;
import com.library.controller.dto.UserDto;
import com.library.model.entities.UserType;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public List<UserDto> listUsers(String search, int page, int size, String[] sorts) {
        return userService.listAllSimpleUserByFilter(search, page, size, sorts);
    }

    @Override
    public UserDto getUserById(Long userId) {
        return userService.findUserDtoById(userId);
    }

    @Override
    public UserDto addNewUser(AddUser addUser) {
        return userService.saveUser(addUser);
    }

    @Override
    public void deactivateUserByUserId(Long userId) {
        userService.deactivateUserById(userId);
    }

    @Override
    public void activateUserByUserId(Long userId) {
        userService.activateUserByUserId(userId);
    }

    @Override
    public List<UserType> listVisibleUserTypes() {
        return userService.listVisibleUserTypes();
    }

}
