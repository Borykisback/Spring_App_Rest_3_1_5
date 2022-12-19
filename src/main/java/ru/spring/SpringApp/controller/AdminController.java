package ru.spring.SpringApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spring.SpringApp.model.Role;
import ru.spring.SpringApp.model.User;
import ru.spring.SpringApp.optional.UserOptional;
import ru.spring.SpringApp.services.RegisterService;
import ru.spring.SpringApp.services.RoleService;
import ru.spring.SpringApp.util.UsersValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/main")
public class AdminController {
    private final RoleService roleService;
    private final UsersValidator usersValidator;
    private final RegisterService registerService;
    private final UserOptional userOptional;
    @Autowired
    public AdminController( RoleService roleService, UsersValidator usersValidator, RegisterService registerService, UserOptional userOptional) {
        this.roleService = roleService;
        this.usersValidator = usersValidator;
        this.registerService = registerService;
        this.userOptional = userOptional;
    }


    @GetMapping("/admin")
    public String adminFormList(Model model, @AuthenticationPrincipal User user, @ModelAttribute("rolePage") User newUser) {
        model.addAttribute("users", userOptional.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userName", user.getLogin());
        model.addAttribute("roleUser", user.getRoleByString());
        model.addAttribute("RoleAll", roleService.getRolesList());
        model.addAttribute("userUp", userOptional.findById(user.getId_user()));


        return "main/admin";
    }

    //******************* editUser
    @GetMapping(value = "/{id}")
    public String formEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userId", userOptional.findById(id));
        model.addAttribute("RoleAll", roleService.getRolesList());
        return "main/admin";
    }

    @PostMapping("/admin/{id}")
    public String updateUser(@ModelAttribute("userUp") User user, @PathVariable("id") Long id, @RequestParam("addRole") List<String> values) {
        List<Role> lists = roleService.getSetOfRoles(values);
        user.setRolesFull(lists);
        registerService.updateUser(user, id);
        return "redirect:/main/admin";
    }

    //******************* DeleteUser
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userOptional.deleteById(id);
        return "redirect:/main/admin";
    }

    //******************* CreateUser
    @PostMapping("/newUser")
    public String performRegister(@ModelAttribute("rolePage") @Valid User user, @RequestParam("addRole") List<String> values, BindingResult bindingResult) {
        usersValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "redirect:/main/admin";

        List<Role> lists = roleService.getSetOfRoles(values);
        user.setRolesFull(lists);
        registerService.register(user);
        return "redirect:/main/admin";
    }
}
