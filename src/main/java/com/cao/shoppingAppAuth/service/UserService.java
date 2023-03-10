package com.cao.shoppingAppAuth.service;

import com.cao.shoppingAppAuth.dao.UserDao;
import com.cao.shoppingAppAuth.domain.User;
import com.cao.shoppingAppAuth.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userDao.loadUserByUsername(username);

        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException("Username does not exist");
        }

        User user = userOptional.get(); // database user

        // dealing with manually added User which password is not encrypted
        String encodedPassword = user.getPassword();
        Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        if (!BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
            encodedPassword = new BCryptPasswordEncoder().encode(encodedPassword);
        }

        return AuthUserDetail.builder() // spring security's userDetail
                .username(user.getUsername())
                .password(encodedPassword)
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        if (user.getIs_admin()) {
            userAuthorities.add(new SimpleGrantedAuthority("Admin_Permission"));
        } else {
            userAuthorities.add(new SimpleGrantedAuthority("User_Permission"));
        }

        return userAuthorities;
    }
}
