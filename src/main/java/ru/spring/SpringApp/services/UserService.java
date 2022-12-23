package ru.spring.SpringApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.SpringApp.model.Role;
import ru.spring.SpringApp.model.User;
import ru.spring.SpringApp.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository usersRepository, RoleService roleService, EntityManager entityManager, PasswordEncoder passwordEncoder) {
        this.userRepository = usersRepository;
        this.roleService = roleService;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) { // РЕГИСТРАЦИЯ
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOneUser(Long id) {
        Optional<User> findUser = userRepository.findById(id);
        return findUser.orElse(null);
    }

    @Transactional
    public void updateUser(User user, Long id) {
        user.setIdUser(id);
        user.setPassword(userRepository.getById(id).getPassword());
        entityManager.merge(user);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void newAdminAndUser() {
        if (roleService.getRolesList().isEmpty()) { // Настройка ролей (если таблица пустая добавить роли)
            entityManager.persist(new Role(1L, "ROLE_ADMIN"));
            entityManager.persist(new Role(2L, "ROLE_USER"));
        }
        if (userRepository.findByLogin("admin@mail.ru").isEmpty()) {
            List<String> allRoles = new ArrayList<>();
            allRoles.add("2");
            List<Role> user = roleService.getSetOfRoles(allRoles);
            register(new User("USER", "USER", 25, "user@mail.ru", "123", user));
            allRoles.add("1");
            List<Role> admin = roleService.getSetOfRoles(allRoles);
            register(new User("ADMIN", "ADMIN", 25, "admin@mail.ru", "123", admin));
        }
    }

}
