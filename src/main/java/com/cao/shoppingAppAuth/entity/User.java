package com.cao.shoppingAppAuth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class User {

    // Database table
    private String username;
    private String password;
    // @OneToMany
    private List<String> permissions;
}
