package com.cao.shoppingAppAuth.dao;

import com.cao.shoppingAppAuth.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    // should be stored in the database
    // user table,permission table
    private final List<User> users = Arrays.asList(
            new User("user", "user", Arrays.asList("read")),
            new User("rep", "rep", Arrays.asList("write")),
            new User("admin", "admin", Arrays.asList("delete", "read", "update", "write"))
    );

    public Optional<User> loadUserByUsername(String username){
        return users.stream().filter(user -> username.equals(user.getUsername())).findAny();
    }

}
