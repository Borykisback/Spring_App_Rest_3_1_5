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
public class RoleController {
    private final RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String adminFormList(Model model, @AuthenticationPrincipal User user, @ModelAttribute("rolePage") User newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userName", user.getLogin());
        model.addAttribute("roleUser", user.getRoleByString());
        model.addAttribute("RoleAll", roleService.getRolesList());

        return "main/admin";
    }
}
