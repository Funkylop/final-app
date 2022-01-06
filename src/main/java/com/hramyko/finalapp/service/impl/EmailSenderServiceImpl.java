package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.entity.ConfirmationToken;
import com.hramyko.finalapp.entity.TokenType;
import com.hramyko.finalapp.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender, ConfirmationTokenService confirmationTokenService) {
        this.javaMailSender = javaMailSender;
        this.confirmationTokenService = confirmationTokenService;
    }

    public void registrationConfirmationMessage(String email) {
        String confirmationUrl = "http://localhost:8080/registration/confirm/";
        String message = "Hello! To register on our website, please, redirect to the following url: ";
        SimpleMailMessage mail = generateMail(email, "Registration Confirmation",
                confirmationUrl, message, "REGISTRATION_TOKEN");
        javaMailSender.send(mail);
    }

    public void resetPasswordMessage(String email) {
        String confirmationUrl = "http://localhost:8080/auth/reset/";
        String message = "Hello! To reset your password, please, redirect to the following url: ";
        SimpleMailMessage mail = generateMail(email, "Reset Password",
                confirmationUrl, message, "RESET_PASSWORD_TOKEN");
        javaMailSender.send(mail);
    }

    public void notifyMessage(String email) {
        String message = "Hello! You has been updated to trader!";
        SimpleMailMessage mail = generateMail(email, "Account role",
                "", message, null);
        javaMailSender.send(mail);
    }

    private SimpleMailMessage generateMail(String recipientAddress, String subject,
                                           String confirmationUrl, String message, String tokenType) {

        if (tokenType != null) {
            ConfirmationToken confirmationToken = new ConfirmationToken(recipientAddress, TokenType.valueOf(tokenType));
            confirmationTokenService.createConfirmationToken(confirmationToken);
            confirmationUrl = confirmationUrl + confirmationToken.getConfirmationToken();
        } else {
            confirmationUrl = "";
        }
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + confirmationUrl);

        return email;
    }
}