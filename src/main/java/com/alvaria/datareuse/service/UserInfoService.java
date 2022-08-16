package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.UserInfoMapper;
import com.alvaria.datareuse.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo findByUsernameAndPassword(String username, String password) {
        return userInfoMapper.findByUsernameAndPassword(username, password);
    }

    public UserInfo findUserInfoById(Integer id) {
        return userInfoMapper.findUserInfoById(id);
    }
}
