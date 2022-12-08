package ru.spring.SpringApp.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.spring.SpringApp.model.Role;
import ru.spring.SpringApp.model.User;
import ru.spring.SpringApp.services.LoginDetailsService;

@Component
public class UsersValidator implements Validator {

    private final LoginDetailsService loginDetailsService;

    @Autowired
    public UsersValidator(LoginDetailsService loginDetailsService) {
        this.loginDetailsService = loginDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Role.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       User user = (User) target;

        try {
            loginDetailsService.loadUserByUsername(user.getLogin());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
            errors.rejectValue("login", "", "Уже существует");
    }
}
