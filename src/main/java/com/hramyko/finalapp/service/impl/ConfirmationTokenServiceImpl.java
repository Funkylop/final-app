package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.dao.ConfirmationTokenDao;
import com.hramyko.finalapp.entity.ConfirmationToken;
import com.hramyko.finalapp.entity.TokenType;
import com.hramyko.finalapp.entity.User;
import com.hramyko.finalapp.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    ConfirmationTokenDao confirmationTokenDao;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenDao confirmationTokenDao) {
        this.confirmationTokenDao = confirmationTokenDao;
    }

    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        return confirmationTokenDao.findByConfirmationToken(confirmationToken);
    }

    @Override
    public void deleteConfirmationToken(User user, TokenType type) {
        confirmationTokenDao.deleteConfirmationToken(user, type);
    }

    @Override
    public void createConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenDao.createConfirmationToken(confirmationToken);
    }
}
