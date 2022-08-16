package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    UserInfo findByUsernameAndPassword(String username, String password);
    UserInfo findUserInfoById(Integer id);
}
