package com.hramyko.finalapp.web.controllers;

import com.hramyko.finalapp.entity.ConfirmationToken;
import com.hramyko.finalapp.entity.Status;
import com.hramyko.finalapp.entity.User;
import com.hramyko.finalapp.service.ConfirmationTokenService;
import com.hramyko.finalapp.service.impl.EmailSenderServiceImpl;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.parser.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("api")
public class AuthController {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderServiceImpl emailSenderServiceImpl;

    @Autowired
    public AuthController(UserService userService, ConfirmationTokenService confirmationTokenService,
                          EmailSenderServiceImpl emailSenderServiceImpl) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderServiceImpl = emailSenderServiceImpl;
    }

    @PostMapping(value = "/registration")
    public String saveUser(@RequestBody String jsonString) {
        User user = (User) JsonParser.getObjectFromJson(jsonString, User.class.getName());
        User existingUser;
        if (user != null) {
            try {
                existingUser = userService.findUserByEmail(user.getEmail());
            } catch (EmptyResultDataAccessException e) {
                existingUser = null;
            }
            if (existingUser == null) {
                userService.saveUser(user);
                emailSenderServiceImpl.registrationConfirmationMessage(user.getEmail());
            } else if (existingUser.getStatus().equals(Status.BANNED)) {
                emailSenderServiceImpl.registrationConfirmationMessage(user.getEmail());
            } else {
                return "Such user already exists";
            }
            return "To register successfully redirect to url, that we have send to your email";
        } else {
            return "Error";
        }
    }

    @PostMapping("/auth/forgot_password")
    public String forgotPassword(@RequestBody String json) {
        String email = JsonParser.getInfoFromJson(json, "email");
        userService.findUserByEmail(email);
        emailSenderServiceImpl.resetPasswordMessage(email);
        return "Please check your email and reset your password!";
    }

    @PatchMapping("/auth/reset/{token}")
    public String reset(@PathVariable("token") String token, @RequestBody String json) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByConfirmationToken(token);
        String password = JsonParser.getInfoFromJson(json, "password");
        if (confirmationToken != null) {
            User user = userService.findUserByEmail(confirmationToken.getEmail());
            user.setPassword(password);
            confirmationTokenService.deleteConfirmationToken(user, confirmationToken.getTokenType());
            userService.updateUser(user.getId(), user);
        }
        return "You changed your password successfully";
    }

    @PostMapping("/registration/confirm/{token}")
    public void confirmAccount(@PathVariable("token") String token, HttpServletResponse response) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByConfirmationToken(token);
        if (confirmationToken != null) {
            User user = userService.findUserByEmail(confirmationToken.getEmail());
            confirmationTokenService.deleteConfirmationToken(user, confirmationToken.getTokenType());
            userService.updateUserStatus(user.getId(), Status.ACTIVE.toString());
        }
        try {
            response.sendRedirect("/users/my_account");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("my/logout")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "You successfully logged out";
    }
}