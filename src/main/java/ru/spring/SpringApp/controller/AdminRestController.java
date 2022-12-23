package ru.spring.SpringApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spring.SpringApp.model.Role;
import ru.spring.SpringApp.model.User;
import ru.spring.SpringApp.services.RoleService;
import ru.spring.SpringApp.services.UserService;
import ru.spring.SpringApp.util.UsersValidator;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;
    private final UsersValidator usersValidator;

    public AdminRestController(UserService userService, RoleService roleService, UsersValidator usersValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.usersValidator = usersValidator;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.POST)
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/updateUser", method = RequestMethod.POST)
    public void updateUser(@RequestParam Long id, String firstName, String lastName, int age, String email, String password, @RequestParam("addRole") List<String> roles) {
        List<Role> lists = roleService.getSetOfRoles(roles);
        userService.updateUser(new User(firstName, lastName, age, email, password, lists), id);
    }

    @RequestMapping(value = "/admin/addNewUser", method = RequestMethod.POST)
    public void addNewUser(@RequestParam String firstName, String password, String lastName, int age, String email,
                           @RequestParam("addRole") List<String> roles) {
        List<Role> lists = roleService.getSetOfRoles(roles);
        userService.register(new User(firstName, lastName, age, email, password, lists));
    }

    @RequestMapping(value = "/admin/deleteUser", method = RequestMethod.POST)
    public void deleteUser(@RequestParam Long id) {
        userService.deleteById(id);
    }
}
