package com.security.springbootnewsecurity.repository;

import com.security.springbootnewsecurity.enums.ERole;
import com.security.springbootnewsecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(ERole eRole);

}
