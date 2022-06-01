package com.library.controller.docs;

import com.library.controller.dto.AddUser;
import com.library.controller.dto.UserDto;
import com.library.model.entities.UserType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/users/", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Felhasználók kezelése"})
public interface UserController {

    @GetMapping(value = "/")
    @ApiOperation(value = "Felhasználók listázása filterrel")
    List<UserDto> listUsers(@RequestParam(value = "search", defaultValue = "") String search,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "30") int size,
                            @RequestParam(defaultValue = "id,asc") String[] sorts);

    @GetMapping(value = "/{userId}")
    @ApiOperation(value = "Felhasználó adatainak lekérdezése", notes = "Addott felhasználó adatainak listázása ID alapján")
    UserDto getUserById(@PathVariable Long userId);

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Új felhasználó hozzáadása")
    @ResponseStatus(HttpStatus.CREATED)
    UserDto addNewUser(@Validated @RequestBody AddUser addUser);

    @GetMapping(value = "/deactivate/{userId}")
    @ApiOperation(value = "Felhasználó inaktiválása Id alapján")
    @ResponseStatus(HttpStatus.OK)
    void deactivateUserByUserId(@PathVariable Long userId);

    @GetMapping(value = "/activate/{userId}")
    @ApiOperation(value = "Felhasználó aktiválása Id alapján")
    @ResponseStatus(HttpStatus.OK)
    void activateUserByUserId(@PathVariable Long userId);

    @GetMapping(value = "/types")
    @ApiOperation(value = "Felhasználó típusok listázása")
    List<UserType> listVisibleUserTypes();

}
