package com.hramyko.finalapp.dao;

import com.hramyko.finalapp.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    List<User> findAll();
    User findUserById(int id);

    User updateUser(int id, User user);

    User saveUser(User user);
    void destroyUser(int id);
    User findUserByEmail(String email);

    Map<User, Double> getTopTraders();

    void updateUserStatus(int id, String status);
    void updateUserRole(int id, String role);
    List<User> findAllTraders();
}
