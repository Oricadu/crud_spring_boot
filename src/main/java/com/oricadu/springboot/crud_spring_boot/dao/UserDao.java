//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.oricadu.springboot.crud_spring_boot.dao;


import com.oricadu.springboot.crud_spring_boot.model.User;

import java.util.List;


public interface UserDao {
    void add(User var1);

    User get(long var1);

    User getUserByUsername(String username);

    User remove(long var1);

    User update(long var1, User var3);

    List<User> getUsers();
}
