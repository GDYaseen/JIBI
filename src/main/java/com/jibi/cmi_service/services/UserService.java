package com.jibi.cmi_service.services;

import com.jibi.cmi_service.models.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    void deleteUserById(Long id);

    User getUserById(Long userId);

    List<User> getAllUsers();
}
