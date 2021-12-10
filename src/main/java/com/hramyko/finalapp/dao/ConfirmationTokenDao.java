package com.hramyko.finalapp.dao;

import com.hramyko.finalapp.entity.ConfirmationToken;
import com.hramyko.finalapp.entity.TokenType;
import com.hramyko.finalapp.entity.User;

public interface ConfirmationTokenDao {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    void deleteConfirmationToken(User user, TokenType type);
    void createConfirmationToken(ConfirmationToken confirmationToken);
}
