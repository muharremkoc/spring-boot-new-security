package com.security.springbootnewsecurity.loader;


import com.security.springbootnewsecurity.enums.ERole;
import com.security.springbootnewsecurity.model.Role;
import com.security.springbootnewsecurity.model.User;
import com.security.springbootnewsecurity.repository.RoleRepository;
import com.security.springbootnewsecurity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DbLoader implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleRepository.count() == 0)
        Arrays.stream(ERole.values()).map(eRole -> Role.builder().name(eRole).build()).forEach(role -> roleRepository.save(role));

        if (userRepository.count() == 0) {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            userRepository.save(User.builder().username("root").password(passwordEncoder.encode("root")).roles(List.of(roleRepository.findByName(ERole.ROLE_USER), roleRepository.findByName(ERole.ROLE_ADMIN), roleRepository.findByName(ERole.ROLE_MANAGER))).build());
            userRepository.save(User.builder().username("user").password(passwordEncoder.encode("user")).roles(List.of(roleRepository.findByName(ERole.ROLE_USER))).build());
            userRepository.save(User.builder().username("admin").password(passwordEncoder.encode("admin")).roles(List.of(roleRepository.findByName(ERole.ROLE_ADMIN))).build());

        }


    }
}
