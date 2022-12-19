package ru.spring.SpringApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.SpringApp.model.Role;
import ru.spring.SpringApp.model.User;
import ru.spring.SpringApp.optional.UserOptional;

import javax.persistence.EntityManager;

@Service
public class RegisterService {
    private final UserOptional usersRepository;
    private final RoleService roleService;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserOptional usersRepository, RoleService roleService, EntityManager entityManager, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.roleService = roleService;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) {
        if (roleService.getRolesList().isEmpty()) { // Настройка ролей (если таблица пустая добавить роли)
            entityManager.persist(new Role(1L, "ROLE_ADMIN"));
            entityManager.persist(new Role(2L, "ROLE_USER"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Transactional
    public void updateUser(User user, Long id) {
        user.setId_user(id);
        user.setPassword(usersRepository.getById(id).getPassword());
        entityManager.merge(user);
    }
}
