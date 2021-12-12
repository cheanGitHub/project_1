package com.cc.user.service.impl;

import com.cc.user.service.UserService;
import com.cc.user.dao.UserMapper;
import com.cc.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
//        return new User();
        return userMapper.getUserById(id);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
}
