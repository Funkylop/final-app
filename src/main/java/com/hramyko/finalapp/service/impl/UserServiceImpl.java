package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.entity.Status;
import com.hramyko.finalapp.entity.Trader;
import com.hramyko.finalapp.entity.User;
import com.hramyko.finalapp.repository.TraderRepository;
import com.hramyko.finalapp.repository.UserRepository;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.parser.JsonParser;
import com.hramyko.finalapp.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final TraderRepository traderRepository;

    @Autowired
    public UserServiceImpl(UserValidator userValidator, TraderRepository traderRepository,
                           UserRepository userRepository) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.traderRepository = traderRepository;
    }

    @Override
    @Transactional
    public String findAll() {
        return userRepository.findAll().toString();
    }

    @Override
    @Transactional
    public String findAllTraders() {
        List<Trader> traders = traderRepository.findAll();
        return traders.get(0).getGameObjects().toString();
    }

    @Override
    public User findUserById(int id) {
        userValidator.validateId(id);
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        userValidator.validate(user);
        user = traderRepository.save(new Trader(user));
        return user;
    }

    @Override
    public String updateUserPassword(int idUser, User user) {
        userValidator.validateId(idUser);
        if (user.getPassword() != null) {
            userValidator.validatePassword(user.getPassword());
        }
        User targetUser = userRepository.findById(idUser).get();
        targetUser.setPassword(user.getPassword());
        return userRepository.save(targetUser).toString();
    }

    @Override
    public String updateUser(User user) {
        User currentUser = currentUser();
        if (user.getPassword() != null) {
            userValidator.validatePassword(user.getPassword());
            currentUser.setPassword(user.getPassword());
        }
        if (user.getFirstName() != null) {
            userValidator.validateName(user.getFirstName());
            currentUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            userValidator.validateName(user.getLastName());
            currentUser.setLastName(user.getLastName());
        }
        return userRepository.save(currentUser).toString();
    }

    @Override
    public void destroyUser(int id) {
        userValidator.validateId(id);
        userRepository.deleteById(id);
    }

    @Override
    public String updateUserStatus(String email, String status) {
        status = status.toUpperCase();
        userValidator.validateEmail(email);
        userValidator.validateStatus(status);
        User user = userRepository.findUserByEmail(email);
        user.setStatus(Status.valueOf(status));
        return userRepository.save(user).toString();
    }

    @Override
    public String updateUserRole(int id, String jsonString) {
        String role = JsonParser.getInfoFromJson(jsonString, "role");
        role = role.toUpperCase();
        userValidator.validateId(id);
        userValidator.validateRole(role);
        User user = userRepository.findById(id).get();
        switch (user.getRole().toString()) {
            case "TRADER" : traderRepository.deleteById(id);
            case "USER" : ;
            case "ADMIN" : ;
        }
        user.setRole(role);
        switch (role) {
            case "TRADER" : {
                traderRepository.save(new Trader(user));
            }
        }
        return "Successfully";
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
        return userRepository.findUserByEmail(username);
    }

    @Override
    public User findUserByEmail(String email) {
        userValidator.validateEmail(email);
        return userRepository.findUserByEmail(email);
    }
//
//    @Override
//    public double getUserRating(int id) {
//        double rating = 0;
//        List<Comment> comments = commentDao.findAllUserComments(id);
//        if (comments.size() > 0) {
//            for (Comment comment : comments) {
//                rating += comment.getMark();
//            }
//            return rating / comments.size();
//        } else {
//            return 0;
//        }
//    }
//
//    @Override
//    public Map<User, Double> getTopTraders() {
//        Map<User, Double> traderRating = userDao.getTopTraders();
//        traderRating = traderRating.entrySet()
//                .stream()
//                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//                .collect(Collectors
//                        .toMap(Map.Entry::getKey,
//                                Map.Entry::getValue,
//                                (e1, e2) -> e1,
//                                LinkedHashMap::new));
//        return traderRating;
//    }
}
