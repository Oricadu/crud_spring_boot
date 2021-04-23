package com.oricadu.springboot.crud_spring_boot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
@Data//ломбок аннотация: генерирует геттеры, сеттеры, equals, hashCode методы
@NoArgsConstructor//ломбок аннотация: конструктор без аргуметов
@AllArgsConstructor //ломбок аннотация: конструктор cо всеми аргументами
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    public String getName() {
        return roleName.replaceAll("ROLE_", "");
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + roleName + '\'' +
                '}';
    }
}
