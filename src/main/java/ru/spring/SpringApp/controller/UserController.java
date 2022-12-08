package ru.spring.SpringApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spring.SpringApp.services.UsersService;


@Controller
@RequestMapping(value = "/main")
public class UserController {


    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("userList", usersService.listUsers());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userName", authentication);
        return "/main/userPage";
    }

}
