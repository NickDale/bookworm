package com.library.controller;

import com.library.controller.docs.AdminController;
import com.library.controller.dto.AddUser;
import com.library.controller.dto.ModifyUser;
import com.library.controller.dto.UserDto;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

    private final UserService userService;

    @Override
    public List<UserDto> listLibrarians(String search, int page, int size, String[] sort) {
        return userService.getAllLibrarian(search, page, size, sort);
    }

    @Override
    public UserDto addLibrarian(AddUser addUser) {
        return userService.upsertLibrarian(addUser);
    }

    @Override
    public UserDto updateLibrarian(ModifyUser modifyUser) {
        return userService.modifyLibrarian(modifyUser);
    }
}
