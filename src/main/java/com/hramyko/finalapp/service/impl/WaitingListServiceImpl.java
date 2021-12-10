package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.dao.WaitingListDao;
import com.hramyko.finalapp.entity.WaitingList;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.WaitingListService;
import com.hramyko.finalapp.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitingListServiceImpl implements WaitingListService {

    private final WaitingListDao waitingListDao;
    private final UserValidator userValidator;
    private final UserService userService;

    @Autowired
    public WaitingListServiceImpl(WaitingListDao waitingListDao, UserValidator userValidator,
                                  UserService userService) {
        this.waitingListDao = waitingListDao;
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @Override
    public int getUserId(int userId) {
        userValidator.validateId(userId);
        return waitingListDao.getUserId(userId);
    }

    @Override
    public void deleteUserForm(int userId) {
        userValidator.validateId(userId);
        waitingListDao.deleteUserForm(userId);
    }

    @Override
    public void addUserForm() {
        int idUser = userService.currentUser().getId();
        userValidator.validateId(idUser);
        waitingListDao.addUserForm(idUser);
    }

    @Override
    public List<WaitingList> findAll() {
        return waitingListDao.findAll();
    }
}
