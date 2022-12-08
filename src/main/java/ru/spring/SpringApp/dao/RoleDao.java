package ru.spring.SpringApp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.SpringApp.model.Role;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDao {

    private final EntityManager entityManager;

    @Autowired
    public RoleDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Role> getRolesList() {
        return entityManager.createQuery("select role from Role role").getResultList();
    }

    @Transactional
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Transactional
    public List<Role> getSetOfRoles(List<String> rolesId) {
        List<Role> roleSet = new ArrayList<>();
        for (String id: rolesId) {
            roleSet.add(getRoleById(Long.parseLong(id)));
        }
        return roleSet;
    }
}
