package com.security.springbootnewsecurity.repository;

import com.security.springbootnewsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    User findByUsername(String username);
    List<User> findByUsernameContainingIgnoreCase(String username);

}
