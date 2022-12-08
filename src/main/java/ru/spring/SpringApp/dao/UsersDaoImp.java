package ru.spring.SpringApp.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.SpringApp.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UsersDaoImp implements UsersDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<User> listUsers() {
        return entityManager.createQuery("select us FROM User us").getResultList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User us = entityManager.find(User.class, id);
        entityManager.remove(us);

    }

    @Override
    @Transactional
    public void update(User user, Long id) {
        user.setId_user(id);
        user.setLogin(show(id).getLogin());
        user.setPassword(show(id).getPassword());
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public User show(Long id) {
        return entityManager.find(User.class, id);
    }

}
