package com.hramyko.finalapp.service;

import com.hramyko.finalapp.entity.Trader;
import com.hramyko.finalapp.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    String findAll();
    User findUserById(int id);
    User saveUser(User user);
    String updateUser(User user);
    void destroyUser(int id);
    User findUserByEmail(String email);

    String findAllTraders();

    String updateUserStatus(String email, String status);
    String updateUserRole(int id, String role);
    User currentUser();
    String updateUserPassword(int idUser, User user);
}
