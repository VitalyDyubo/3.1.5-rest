package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> showAllUsers();

    User show(long id);

    void save(User user);

    void update(User updateUser);

    void delete(Long id);

    User findByUserName(String username);

    User findByUserId(Long id);


}
