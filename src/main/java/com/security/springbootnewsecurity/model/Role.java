package com.security.springbootnewsecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.springbootnewsecurity.enums.ERole;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private ERole name;


    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> users;


    public Role() {
    }

    public Role(int id, ERole name) {
        this.id = id;
        this.name = name;
    }

    public Role(int id, ERole name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
