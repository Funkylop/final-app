package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.entity.CommonUser;
import com.hramyko.finalapp.entity.User;
import com.hramyko.finalapp.repository.CommonUserRepository;
import com.hramyko.finalapp.service.CommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonUserServiceImpl implements CommonUserService {

    private final CommonUserRepository commonUserRepository;


    @Autowired
    CommonUserServiceImpl(CommonUserRepository commonUserRepository) {
        this.commonUserRepository = commonUserRepository;
    }

    @Transactional
    @Override
    public List<CommonUser> findAll() {
        return commonUserRepository.findAll();
    }

    @Transactional
    @Override
    public CommonUser save(CommonUser commonUser) {
        String encodedPassword = getEncodedPassword(commonUser.getPassword());
        commonUser.setPassword(encodedPassword);
        return commonUserRepository.save(commonUser);
    }

    private String getEncodedPassword(String password)
    {
        return new BCryptPasswordEncoder(12).encode(password);
    }
}
