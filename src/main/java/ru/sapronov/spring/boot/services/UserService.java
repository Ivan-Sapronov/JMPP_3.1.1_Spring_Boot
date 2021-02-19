package ru.sapronov.spring.boot.services;

import ru.sapronov.spring.boot.models.User;

import java.util.List;

/**
 * @author Ivan Sapronov on 16.02.2021
 * @project spring-boot
 */
public interface UserService {
    List<User> index();

    User show(long id);

    User getUserByUsername(String username);

    void save(User user);

    void update(User user);

    void delete(long id);
}
