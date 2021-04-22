package com.oricadu.springboot.crud_spring_boot.service;

import com.oricadu.springboot.crud_spring_boot.model.Role;

import java.util.List;

public interface RoleService {
    public List<Role> getListRoles();
    public Role getById(long id);
    public Role getByName(String name);
}
