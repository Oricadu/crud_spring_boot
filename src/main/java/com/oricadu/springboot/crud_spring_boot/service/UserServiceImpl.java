//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.oricadu.springboot.crud_spring_boot.service;


import com.oricadu.springboot.crud_spring_boot.dao.UserDao;
import com.oricadu.springboot.crud_spring_boot.model.Role;
import com.oricadu.springboot.crud_spring_boot.model.User;
import com.oricadu.springboot.crud_spring_boot.repository.UserRepository;
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
    UserRepository userRepository;

    public UserServiceImpl() {
    }

    @Override
    @Transactional
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User get(long id) {
        return userRepository.findById(id).get();

    }

    @Override
    @Transactional
    public User remove(long id) {
        User user = get(id);
        userRepository.delete(user);
        return user;
    }

    @Override
    @Transactional
    public User update(long id, User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return userRepository.findUserByEmail(username);
    }

}
