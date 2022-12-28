package com.security.springbootnewsecurity.service;

import com.security.springbootnewsecurity.dto.request.UserSaveRequest;
import com.security.springbootnewsecurity.enums.ERole;
import com.security.springbootnewsecurity.model.Role;
import com.security.springbootnewsecurity.model.User;
import com.security.springbootnewsecurity.repository.RoleRepository;
import com.security.springbootnewsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

    }

    @Override
    public User addRole(int userId, ERole eRole) {
        User user = findUserById(userId);
        Role role = roleRepository.findByName(eRole);

        user.getRoles().add(role);
        user.getRoles().forEach(r -> r.setUsers(Arrays.asList(user)));

        return userRepository.save(user);

    }

    @Override
    public User removeRole(int userId, ERole eRole) {
        User user = findUserById(userId);
        Role role = roleRepository.findByName(eRole);


        user.getRoles().remove(role);
        user.getRoles().forEach(r -> r.setUsers(Arrays.asList(user)));

        return userRepository.save(user);
    }

    @Override
    public User saveUser(UserSaveRequest userSaveRequest) {
        User user = User.builder()
                .username(userSaveRequest.getUsername())
                .password(userSaveRequest.getPassword()).build();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>());
        return userRepository.save(user);

    }

    @Override
    public List<User> findByNameContaining(String name) {
        return userRepository.findByUsernameContainingIgnoreCase(name);

    }

    @Override
    public User deleteUserById(int userId) {
        User user = findUserById(userId);
        return userRepository.save(user);

    }
}
