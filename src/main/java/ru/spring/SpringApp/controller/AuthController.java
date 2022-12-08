package ru.spring.SpringApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spring.SpringApp.model.User;
import ru.spring.SpringApp.services.RegisterService;
import ru.spring.SpringApp.util.UsersValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegisterService registerService;
    private final UsersValidator usersValidator;

    @Autowired
    public AuthController(RegisterService registerService, UsersValidator usersValidator) {
        this.registerService = registerService;
        this.usersValidator = usersValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("rolePage") User user) {
        return "/auth/register";
    }

    @PostMapping("/register")
    public String performRegister(@ModelAttribute("rolePage") @Valid User user, BindingResult bindingResult) {
        usersValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/register";

        registerService.register(user);
        return "redirect:/auth/login";
    }
}
