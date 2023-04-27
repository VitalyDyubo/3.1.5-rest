package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final Validator validator;

    public AdminController(UserService userService, RoleService roleService, @Qualifier("userValidator") Validator validator) {
        this.userService = userService;
        this.roleService = roleService;
        this.validator = validator;
    }


    @GetMapping()
    public String adminInfo(Model model) {
        model.addAttribute("users", userService.index());
        return "admin/admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.findRoles());
        return "admin/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findRoles());
            return "admin/new";
        }
        userService.save(user);
        return "redirect:/admin";
    }


    @GetMapping("/userinfo/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin/show";
    }


    @GetMapping("/userinfo/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute(userService.show(id));
        model.addAttribute("roles", roleService.findRoles());
        return "admin/edit";
    }

    @PatchMapping("/userinfo/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") Long id, Model model) {
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findRoles());
            return "admin/edit";
        }
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/userinfo/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
