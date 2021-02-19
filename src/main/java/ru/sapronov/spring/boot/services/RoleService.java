package ru.sapronov.spring.boot.services;

import ru.sapronov.spring.boot.models.Role;

/**
 * @author Ivan Sapronov on 16.02.2021
 * @project spring-boot
 */
public interface RoleService {

    void deleteRole(long id);

    Role getRoleByName(String role);
}
