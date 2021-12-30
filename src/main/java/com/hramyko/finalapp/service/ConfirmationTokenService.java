package com.hramyko.finalapp.service;

import com.hramyko.finalapp.entity.ConfirmationToken;
import com.hramyko.finalapp.entity.TokenType;
import com.hramyko.finalapp.entity.User;

public interface ConfirmationTokenService {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    void deleteConfirmationToken(User user, TokenType type);
    void createConfirmationToken(ConfirmationToken confirmationToken);
}
