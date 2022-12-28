package com.security.springbootnewsecurity.service;

import com.security.springbootnewsecurity.dto.request.UserSaveRequest;
import com.security.springbootnewsecurity.enums.ERole;
import com.security.springbootnewsecurity.model.User;

import java.util.List;

public interface UserService {

    User findUserById(int id);

    User addRole(int userId, ERole eRole);

    User removeRole(int userId,ERole eRole);

    User saveUser(UserSaveRequest userSaveRequest);

    List<User> findByNameContaining(String name);

    User deleteUserById(int userId);



}
