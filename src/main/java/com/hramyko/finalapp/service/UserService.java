package com.hramyko.finalapp.service;

import com.hramyko.finalapp.entity.Trader;
import com.hramyko.finalapp.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    String findAll();
//    User findUserById(int id);
//    User saveUser(User user);
//    User updateUser(User user);
//    void destroyUser(int id);
    User findUserByEmail(String email);

    String findAllTraders();

    //    void updateUserStatus(int id, String status);
//    void updateUserRole(int id, String role);
//    List<User> findAllTraders();
    User currentUser();
//    User updateUser(int idUser, User user);
//    double getUserRating(int id);
//    Map<User, Double> getTopTraders();
}
