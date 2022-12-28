package com.security.springbootnewsecurity.controller;


import com.security.springbootnewsecurity.dto.request.UserSaveRequest;
import com.security.springbootnewsecurity.enums.ERole;
import com.security.springbootnewsecurity.model.User;
import com.security.springbootnewsecurity.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/versions/1")
@SecurityRequirement(name = "basic-auth")
public class UserController {

    private final UserService userService;


    @GetMapping(value = "/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public User findAllUsers(@PathVariable int userId) throws NotFoundException {
        return userService.findUserById(userId);
    }
    @PostMapping(value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public User saveUser(@RequestBody @Valid UserSaveRequest userSaveRequest){
        return userService.saveUser(userSaveRequest);
    }

    @PutMapping(value = "/user/{userId}/role/add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public User addRole(@PathVariable int userId, @RequestParam ERole eRole) throws NotFoundException {
        return userService.addRole(userId, eRole);
    }

    @DeleteMapping(value = "/user/{userId}/role/remove")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public User removeRole(@PathVariable int userId, @RequestParam ERole eRole) throws NotFoundException {
        return userService.removeRole(userId, eRole);
    }

    @GetMapping(value = "/user/search")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public List<User> findUserByNameContaining(@RequestParam String name){
        return userService.findByNameContaining(name);
    }

    @DeleteMapping(value = "/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public User deleteUserById(@PathVariable int userId) throws NotFoundException {
        return userService.deleteUserById(userId);
    }

}
