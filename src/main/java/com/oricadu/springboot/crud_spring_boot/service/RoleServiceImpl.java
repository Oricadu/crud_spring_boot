package com.oricadu.springboot.crud_spring_boot.service;


import com.oricadu.springboot.crud_spring_boot.dao.RoleDao;
import com.oricadu.springboot.crud_spring_boot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;

    public RoleServiceImpl() {
    }

    @Override
    public List<Role> getListRoles() {
        return roleDao.getListRoles();
    }

    @Override
    public Role getById(long id) {
        return roleDao.getById(id);
    }

    @Override
    public Role getByName(String name) {
        return roleDao.getByName(name);
    }
}
