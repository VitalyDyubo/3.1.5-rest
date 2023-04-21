package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
@RequestMapping()
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("/user")
    public String userInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userService.findByUserName(userDetails.getUsername()));
        return "user";
    }

    @GetMapping("/admin")
    public String adminInfo(Model model) {
        model.addAttribute("users", userService.index());
        return "admin";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        List<Role> roles = userService.findRoles();
        model.addAttribute("roles", roles);
        return "new";
    }

    @PostMapping("/admin/new")
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }


    @GetMapping("/admin/userinfo/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "show";
    }



    @GetMapping("/admin/userinfo/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute(userService.show(id));
        List<Role> roles = userService.findRoles();
        model.addAttribute("roles", roles);
        return "edit";
    }

    @PatchMapping("/admin/userinfo/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/userinfo/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
