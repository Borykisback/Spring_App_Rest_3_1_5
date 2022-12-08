package ru.spring.SpringApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.SpringApp.dao.UsersDao;
import ru.spring.SpringApp.model.User;

import java.util.List;

@Service
public class UsersServiceImp implements UsersService {

    private final UsersDao usersDao;

    @Autowired
    public UsersServiceImp(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
      return usersDao.listUsers();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        usersDao.delete(id);
    }

    @Override
    @Transactional
    public void update(User user, Long id) {
        usersDao.update(user, id);
    }

    @Override
    @Transactional
    public User show(Long id) {
        return usersDao.show(id);
    }

}
