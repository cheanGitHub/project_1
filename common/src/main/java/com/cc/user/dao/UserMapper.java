package com.cc.user.dao;

import com.cc.user.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User getUserById(@Param("id") Long id);

    void insertUser(User user);
}
