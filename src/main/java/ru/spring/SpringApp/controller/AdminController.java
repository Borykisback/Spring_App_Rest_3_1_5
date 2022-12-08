package ru.spring.SpringApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spring.SpringApp.model.Role;
import ru.spring.SpringApp.model.User;
import ru.spring.SpringApp.services.RoleService;
import ru.spring.SpringApp.services.UsersService;

import java.util.List;

@Controller
@RequestMapping(value = "/main")
public class AdminController {

    private final UsersService usersService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UsersService usersService, RoleService roleService) {
        this.usersService = usersService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String adminFormList(Model model) {
        model.addAttribute("userListAll", usersService.listUsers());
        return "main/admin";
    }

    //******************* editUser

    @GetMapping(value = "/{id}/editUser")
    public String formEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userId", usersService.show(id));
        model.addAttribute("RoleAll", roleService.getRolesList());
        return "main/editUser";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("userId") User user, @PathVariable("id") Long id, @RequestParam("rolesThyme") List<String> values) {
        List<Role> lists = roleService.getSetOfRoles(values);
        user.setRolesFull(lists);
        usersService.update(user, id);
        return "redirect:/main/admin";
    }


    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        usersService.delete(id);
        return "redirect:/main/admin";
    }
}
