package com.oricadu.springboot.crud_spring_boot.dao;

import com.oricadu.springboot.crud_spring_boot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext(unitName = "entityManagerFactory")
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Role> getListRoles() {
        return entityManager.createQuery("select role from Role role").getResultList();
    }

    @Override
    public Role getById(long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getByName(String name) {
        Query query = entityManager.createQuery("select role from Role role where role.roleName = :name", Role.class);
        query.setParameter("name", name);
        Role role = (Role) query.getSingleResult();
        return role;
    }
}
