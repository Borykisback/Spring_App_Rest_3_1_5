package ru.spring.SpringApp.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "userrole")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id_role")
    private Long idRole;

    @Column(name = "role")
    private String role;


    @Override
    public String getAuthority() {
        return role;
    }

    public Role() {}

    public Role(Long idRole, String role) {
        this.idRole = idRole;
        this.role = role;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
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
                "id_role=" + idRole +
                ", role='" + role + '\'' +
                '}';
    }
}
