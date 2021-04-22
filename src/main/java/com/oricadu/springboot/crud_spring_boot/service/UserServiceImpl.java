//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.oricadu.springboot.crud_spring_boot.service;


import com.oricadu.springboot.crud_spring_boot.dao.UserDao;
import com.oricadu.springboot.crud_spring_boot.model.Role;
import com.oricadu.springboot.crud_spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    public UserServiceImpl() {
    }
    @Override
    @Transactional
    public void add(User user) {
        this.userDao.add(user);
    }

    @Override
    @Transactional
    public User get(long id) {
        return this.userDao.get(id);
    }

    @Override
    @Transactional
    public User remove(long id) {
        return this.userDao.remove(id);
    }

    @Override
    @Transactional
    public User update(long id, User user) {
        return this.userDao.update(id, user);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return this.userDao.getUsers();
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getUserByUsername(s);
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
