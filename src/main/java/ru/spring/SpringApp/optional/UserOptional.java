package ru.spring.SpringApp.optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spring.SpringApp.model.User;

import java.util.Optional;

@Repository
public interface UserOptional extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

}
