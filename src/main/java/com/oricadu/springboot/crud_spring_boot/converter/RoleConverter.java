package com.oricadu.springboot.crud_spring_boot.converter;


import com.oricadu.springboot.crud_spring_boot.dao.RoleDao;
import com.oricadu.springboot.crud_spring_boot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role convert(String id) {
        Role role = null;
        if (roleDao != null) {
            role = roleDao.getById(Integer.parseInt(id));
        }
        return role;
    }
}
