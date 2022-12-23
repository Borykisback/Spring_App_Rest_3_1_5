package ru.spring.SpringApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spring.SpringApp.model.User;
import ru.spring.SpringApp.services.RoleService;


@Controller
@RequestMapping(value = "/main")
public class UserController {

    @GetMapping()
    public String userList(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("authUser", user);
        model.addAttribute("roleUser", user.getRoleByString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userName", authentication);
        return "/main/userPage";
    }

}
