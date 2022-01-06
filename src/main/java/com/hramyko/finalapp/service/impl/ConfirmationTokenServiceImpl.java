package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.entity.ConfirmationToken;
import com.hramyko.finalapp.entity.TokenType;
import com.hramyko.finalapp.entity.User;
import com.hramyko.finalapp.repository.ConfirmationTokenRepository;
import com.hramyko.finalapp.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.findConfirmationTokenByConfirmationToken(confirmationToken);
    }

    @Override
    public void deleteConfirmationToken(User user, TokenType type) {
        confirmationTokenRepository.deleteConfirmationTokenByEmailAndTokenType(user.getEmail(), type.toString());
    }

    @Override
    public void createConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }
}
