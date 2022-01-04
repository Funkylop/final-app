package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.entity.CommonUser;
import com.hramyko.finalapp.entity.User;
import com.hramyko.finalapp.repository.CommonUserRepository;
import com.hramyko.finalapp.service.CommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonUserServiceImpl implements CommonUserService {

    private final CommonUserRepository commonUserRepository;

    @Autowired
    CommonUserServiceImpl(CommonUserRepository commonUserRepository) {
        this.commonUserRepository = commonUserRepository;
    }

    @Override
    public List<CommonUser> findAll() {
        return commonUserRepository.findAll();
    }

    @Override
    public CommonUser save(CommonUser commonUser) {
        return commonUserRepository.save(commonUser);
    }
}
