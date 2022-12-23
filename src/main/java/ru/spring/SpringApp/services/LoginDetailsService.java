package ru.spring.SpringApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spring.SpringApp.model.User;
import ru.spring.SpringApp.repository.UserRepository;

import java.util.Optional;

@Service
public class LoginDetailsService implements UserDetailsService {

    private final UserRepository usersDao;

    @Autowired
    public LoginDetailsService(UserRepository usersRepository) {
        this.usersDao = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = usersDao.findByLogin(userName);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Error User!!!");
        }
        return user.get();
    }
}
