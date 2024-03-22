package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


@Controller
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("admin", userService.findAll());
        return "admin";
    }

    @GetMapping("/admin/new")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllUser());
        return "creat";
    }

    @PostMapping("/admin/new")
    public String getAddUser(@ModelAttribute("user") User user) {
        user.setPassword(user.getPassword());
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/update")
    public String getWhatUpdateUser(@ModelAttribute("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", roleService.getAllUser());
        return "update";
    }

    @PostMapping("/admin/{id}/update")
    public String getUpdateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        user.setPassword(user.getPassword());
        userService.updateUser(id, user);
        return "redirect:/admin/";
    }


    @PostMapping("/admin/{id}")
    public String deleteUser(@ModelAttribute("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }


}
