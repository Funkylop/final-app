package com.hramyko.finalapp.dao;

import com.hramyko.finalapp.entity.User;
import com.hramyko.finalapp.entity.WaitingList;

import java.util.List;

public interface WaitingListDao {
    int getUserId(int formId);
    void deleteUserForm(int formId);
    void addUserForm(int userId);
    List<WaitingList> findAll();
}
