package ru.spring.SpringApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.SpringApp.model.Role;
import ru.spring.SpringApp.model.User;
import ru.spring.SpringApp.optional.UsersOptional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterService {
    private final UsersOptional usersRepository;
    private final RoleService roleService;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final UsersService usersService;

    @Autowired
    public RegisterService(UsersOptional usersRepository, RoleService roleService, EntityManager entityManager, PasswordEncoder passwordEncoder, UsersService usersService) {
        this.usersRepository = usersRepository;
        this.roleService = roleService;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
        this.usersService = usersService;
    }

    @Transactional
    public void register(User user) {
        if (roleService.getRolesList().isEmpty()) { // Настройка ролей (если таблица пустая добавить роли)
            entityManager.persist(new Role(1L, "ROLE_ADMIN"));
            entityManager.persist(new Role(2L, "ROLE_USER"));
        }
        List<Role> lists = new ArrayList<>(); //Выдача роли (нужно придумать что бы первому пользователю выдавалась сразу админка)
        if (usersService.listUsers().isEmpty()) {
            lists.add(roleService.getRoleById(1L));
        } else {
            lists.add(roleService.getRoleById(2L));
        }
        user.setRolesFull(lists);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }



}
