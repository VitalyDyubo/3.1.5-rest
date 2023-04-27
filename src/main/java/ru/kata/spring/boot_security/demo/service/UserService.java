package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {
    public List<User> index();
    public User show(long id);
    public void save(User user);
    public void update(User updateUser);
    public void delete(Long id);
    public User findByUserName(String username);
    public User findByUserId(Long id);

}
