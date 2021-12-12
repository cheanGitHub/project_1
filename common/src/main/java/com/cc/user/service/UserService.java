package com.cc.user.service;

import com.cc.user.domain.User;

public interface UserService {

    User getUserById(Long id);

    void insertUser(User user);
}
