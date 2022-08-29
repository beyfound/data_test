package com.alvaria.datareuse.service;

import com.alibaba.fastjson.JSONArray;
import com.alvaria.datareuse.dao.UserTagMapper;
import com.alvaria.datareuse.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTagService {

    @Autowired
    private UserTagMapper userTagMapper;

    public List<User> getUserByTags(String[] tags, boolean mtData) {
        if (tags == null || tags.length == 0) {
            return new ArrayList<User>();
        }
        return userTagMapper.findUserListByTags(tags, mtData);
    }

    public String getUserTagsById(Integer userId) {
        return userTagMapper.findUserTagsById(userId);
    }

    public List<User> getUserByTeam(String team, boolean mtData) {
        return userTagMapper.findUserByTeam(team, mtData);
    }
}
