package com.library.controller.docs;

import com.library.controller.dto.AddUser;
import com.library.controller.dto.ModifyUser;
import com.library.controller.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.NotSupportedException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/librarians/")
@Api(tags = {"Admin funkciók"})
public interface AdminController {

    @GetMapping(value = "/")
    @ApiOperation(value = "Könyvtárosok listázása filterrel")
    List<UserDto> listLibrarians(@RequestParam(value = "search", defaultValue = "") String search,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "30") int size,
                                 @RequestParam(defaultValue = "id,asc") String[] sort);

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Új könyvtáros létrehozása")
    UserDto addLibrarian(@Validated @RequestBody AddUser addUser);

    @PutMapping(value = "/")
    @ApiOperation(value = "Adott könyvtáros módosítása")
    UserDto updateLibrarian(@Validated @RequestBody ModifyUser modifyUser) throws NotSupportedException;

}
