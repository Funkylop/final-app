package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.dao.CommentDao;
import com.hramyko.finalapp.dao.UserDao;
import com.hramyko.finalapp.entity.Comment;
import com.hramyko.finalapp.entity.User;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserValidator userValidator;
    private final CommentDao commentDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserValidator userValidator,
                           CommentDao commentService) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.commentDao = commentService;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findUserById(int id) {
        userValidator.validateId(id);
        return userDao.findUserById(id);
    }

    @Override
    public User saveUser(User user) {
        userValidator.validate(user);
        user = userDao.saveUser(user);
        return user;
    }

    @Override
    public User updateUser(int idUser, User user) {
        if (user.getPassword() != null) {
            userValidator.validatePassword(user.getPassword());
        }
        return userDao.updateUser(idUser, user);
    }

    @Override
    public User updateUser(User user) {
        int id = currentUser().getId();
        userValidator.validateId(id);
        if (user.getPassword() != null) {
            userValidator.validatePassword(user.getPassword());
        }
        if (user.getFirstName() != null) {
            userValidator.validateName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            userValidator.validateName(user.getLastName());
        }
        return userDao.updateUser(id, user);
    }

    @Override
    public void destroyUser(int id) {
        userValidator.validateId(id);
        userDao.destroyUser(id);
    }

    @Override
    public void updateUserStatus(int id, String status) {
        status = status.toUpperCase();
        userValidator.validateId(id);
        userValidator.validateStatus(status);
        userDao.updateUserStatus(id, status);
    }

    @Override
    public void updateUserRole(int id, String role) {
        role = role.toUpperCase();
        userValidator.validateId(id);
        userValidator.validateRole(role);
        userDao.updateUserRole(id, role);
    }

    @Override
    public List<User> findAllTraders() {
        return userDao.findAllTraders();
    }

    @Override
    public User currentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userDao.findUserByEmail(username);
    }

    @Override
    public User findUserByEmail(String email) {
        userValidator.validateEmail(email);
        return userDao.findUserByEmail(email);
    }

    @Override
    public double getUserRating(int id) {
        double rating = 0;
        List<Comment> comments = commentDao.findAllUserComments(id);
        if (comments.size() > 0) {
            for (Comment comment : comments) {
                rating += comment.getMark();
            }
            return rating / comments.size();
        } else {
            return 0;
        }
    }

    @Override
    public Map<User, Double> getTopTraders() {
        Map<User, Double> traderRating = userDao.getTopTraders();
        traderRating = traderRating.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new));
        return traderRating;
    }
}
