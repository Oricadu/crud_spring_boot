package com.oricadu.springboot.crud_spring_boot.repository;

import com.oricadu.springboot.crud_spring_boot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
