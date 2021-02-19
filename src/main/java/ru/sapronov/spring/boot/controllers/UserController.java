package ru.sapronov.spring.boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.sapronov.spring.boot.models.Role;
import ru.sapronov.spring.boot.models.User;
import ru.sapronov.spring.boot.services.RoleService;
import ru.sapronov.spring.boot.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Ivan Sapronov on 16.02.2021
 * @project spring-boot
 */
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("user")
    public String userPage(Authentication authentication, ModelMap model) {
        User user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin/index")
    public String adminPage(ModelMap model) {
        List<User> users = userService.index();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping( "/admin/create")
    public String createUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "create";
    }

    @PostMapping( "/admin/create")
    public String createdUser(@ModelAttribute("newUser") User user,
                              @RequestParam(value = "adminRole", defaultValue = "") String adminRole,
                              @RequestParam(value = "userRole", defaultValue = "") String userRole) {

//этот вариант бросает (при этом сохраняя всё правильно в БД)
//javax.persistence.PersistenceException: org.hibernate.PersistentObjectException: detached entity passed to persist: ru.sapronov.springsecurity.models.Role
//		user.setRoles(getRoles(adminRole, userRole));
//		userService.save(user);

        userService.save(user);
        user.setRoles(getRoles(adminRole, userRole));
        userService.update(user);
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/edit")
    public String editUser(@RequestParam(name = "id", defaultValue = "0") long id,
                           ModelMap model) {
        model.addAttribute("user", userService.show(id));
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String updatedUser(@ModelAttribute("user") User user,
                              @RequestParam(value = "adminRole", defaultValue = "") String adminRole,
                              @RequestParam(value = "userRole", defaultValue = "") String userRole) {

        user.setRoles(getRoles(adminRole, userRole));
        userService.update(user);
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam(name = "id", defaultValue = "0") long id) {
        userService.delete(id);
        return "redirect:/admin/index";
    }

    public Set<Role> getRoles(String adminRole, String userRole) {
        Set<Role> roles = new HashSet<>();
        if (!adminRole.isEmpty()) {
            roles.add(roleService.getRoleByName(adminRole));
        }
        if (!userRole.isEmpty()) {
            roles.add(roleService.getRoleByName(userRole));
        }
        return roles;
    }
}
