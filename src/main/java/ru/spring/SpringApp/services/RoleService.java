package ru.spring.SpringApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.SpringApp.dao.RoleDao;
import ru.spring.SpringApp.model.Role;

import java.util.List;

@Service
@Transactional
public class RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    public List<Role> getRolesList() {
        return roleDao.getRolesList();
    }

    @Transactional
    public Role getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }

    @Transactional
    public List<Role> getSetOfRoles(List<String> rolesId){
        return roleDao.getSetOfRoles(rolesId);
    }
}
