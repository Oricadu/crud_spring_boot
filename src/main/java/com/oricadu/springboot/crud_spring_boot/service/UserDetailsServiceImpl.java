package com.oricadu.springboot.crud_spring_boot.service;

import com.oricadu.springboot.crud_spring_boot.model.Role;
import com.oricadu.springboot.crud_spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("user %s not found", s));
        } else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPass(), mapToAuthorities(user.getRoles()));
        }

    }

    private Collection<? extends GrantedAuthority> mapToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }
}
