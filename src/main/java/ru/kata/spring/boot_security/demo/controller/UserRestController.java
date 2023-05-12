package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ModelAndView user(Model model, Principal principal) {
        User authentificatedUser = userService.findByUserName(principal.getName());
        model.addAttribute("authenticatedUserRoles", authentificatedUser.getRoles());
        return new ModelAndView("user/user");
    }

    @GetMapping("/user")
    public ResponseEntity<User> getAuthenticatedUser(Principal principal) {
        User user = userService.findByUserName(principal.getName());
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
