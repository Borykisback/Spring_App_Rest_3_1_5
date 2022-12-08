package ru.spring.SpringApp.services;


import ru.spring.SpringApp.model.User;

import java.util.List;

public interface UsersService {
    List<User> listUsers();
    void delete(Long id);
    void update(User user, Long id);
    User show(Long id);
}
