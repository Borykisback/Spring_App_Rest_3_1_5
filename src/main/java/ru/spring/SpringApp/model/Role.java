package ru.spring.SpringApp.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "userrole")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id_role")
    private Long id_role;

    @Column(name = "role")
    private String role;


    @Override
    public String getAuthority() {
        return role;
    }

    public Role() {}

    public Role(Long id_role, String role) {
        this.id_role = id_role;
        this.role = role;
    }

    public Long getId_role() {
        return id_role;
    }

    public void setId_role(Long id_role) {
        this.id_role = id_role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id_role=" + id_role +
                ", role='" + role + '\'' +
                '}';
    }
}
