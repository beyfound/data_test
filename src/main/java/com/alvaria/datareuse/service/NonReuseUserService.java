package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.NonReuseUserMapper;
import com.alvaria.datareuse.dao.UserMapper;
import com.alvaria.datareuse.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class NonReuseUserService {

    @Autowired
    private NonReuseUserMapper userMapper;

    public List<NonReuseUser> getAll() {
        return userMapper.findAll();
    }

    public List<NonReuseUser> getAllByKey(String keyWord) {
        return userMapper.findAllByKey(keyWord);
    }

    public int saveUser(NonReuseUser user) {
        if (user.getId() == null) {
            return userMapper.insertUser(user);
        } else {
            return userMapper.update(user);
        }
    }

    public NonReuseUser getUserById(Integer id) {

        return userMapper.getUserById(id);
    }

    public NonReuseUser getUserByEmail(String emailPrefix) {
        return userMapper.getUserByEmail(emailPrefix);
    }

    public int deleteUserById(Integer id) {
        return userMapper.deleteById(id);
    }

    public int deleteUserByEmail(String emailPrefix) {
        return userMapper.deleteByEmail(emailPrefix);
    }

    public int deleteUsers(Integer[] ids) {
        return userMapper.deleteByIds(ids);
    }

    @Transactional
    public int insertUsers(List<NonReuseUser> list) {

        return userMapper.insertUsers(list);
    }

    public List<NonReuseUser> findUserIdsByRole(String role) {
        return userMapper.findAllByRole(role);
    }

    public Map<String, Object> selectPage(Integer pageNum, Integer pageSize, String keyWord, String sort) {
        Map<String, Object> res = new HashMap<>();
        List<NonReuseUser> users = userMapper.findAllByKey(keyWord);
        String[] sortProp = sort.split(",");
        switch (sortProp[0]){
            case "email" :
                if(sortProp[1].equals("ASC")){
                    users = users.stream().sorted(Comparator.comparing(NonReuseUser::getEmail)).collect(Collectors.toList());
                }else {
                    users = users.stream().sorted(Comparator.comparing(NonReuseUser::getEmail).reversed()).collect(Collectors.toList());
                }

                break;
            case "role":
                if(sortProp[1].equals("ASC")){
                    users = users.stream().sorted(Comparator.comparing(NonReuseUser::getRole)).collect(Collectors.toList());
                }else {
                    users = users.stream().sorted(Comparator.comparing(NonReuseUser::getRole).reversed()).collect(Collectors.toList());
                }
                break;
            default:
        }

        int userNum = users.size();
        int start = (pageNum - 1) * pageSize;
        while (start >= userNum && userNum!= 0) {
            pageNum--;
            start = (pageNum - 1) * pageSize;
        }

        int end = pageNum * pageSize > userNum ? userNum : pageNum * pageSize;
        List<NonReuseUser> pageUsers = users.subList(start, end);

        res.put("data", pageUsers);
        res.put("total", users.size());
        return res;
    }

    public Integer selectTotal() {
        return userMapper.selectTotal();
    }

    public int saveUsers(List<NonReuseUser> users) {
        return userMapper.insertUsers(users);
    }

}
