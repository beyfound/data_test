package com.alvaria.datareuse.service;

import com.alibaba.fastjson.JSON;
import com.alvaria.datareuse.dao.UserMapper;
import com.alvaria.datareuse.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private StationService stationService;

    private final Lock lock = new ReentrantLock();

    public List<User> getAll() {
        return userMapper.findAll();
    }

    public List<User> getAllByKey(String keyWord) {
        return userMapper.findAllByKey(keyWord);
    }

    public int saveUser(User user) {
        if (user.getId() == null) {
            return userMapper.insertUser(user);
        } else {
            return userMapper.update(user);
        }
    }

    public User getUserById(Integer id) {

        return userMapper.getUserById(id);
    }

    public User getUserByEmail(String emailPrefix) {
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

    public User getUserByIdentify(String identify) {
        return userMapper.getUserByIdentity(identify);
    }

    @Transactional
    public ResponseResult applyOneUser(ConditionModel conditionModel) throws Exception {
        String role = conditionModel.getRole();
        String org = conditionModel.getOrg();
        String[] tags = conditionModel.getTags();
        String testCase = conditionModel.getTestCase();
        String uuid = UUID.randomUUID().toString();
        String identity = conditionModel.getIdentity();
        String team = conditionModel.getTeam();
        boolean needStation = conditionModel.isNeedStation();
        boolean mtData = conditionModel.isMtData();

        if (role == null || role.trim().length() == 0) {
            throw new Exception("Search condition error, please pass right 'role' parameter");
        }

        if (org == null || org.trim().length() == 0) {
            throw new Exception("Search condition error, please pass right 'org' parameter");
        }

        String stationId = null;
        if (needStation) {
            stationId = stationService.getIdleStationIdByOrg(org);
            if (stationId == null)
                return new ResponseResult(-1, "", "Didn't find available station on org: " + org);
        }

        List<Integer> exclusionIds = new ArrayList<>();
        int retryTime = 0;
        while (retryTime < 5) {
            User userApplyTo = userMapper.getOneAvailableUser(role, org, team, identity, tags, mtData, exclusionIds);
            if (userApplyTo == null) {
                stationService.releaseStation(stationId);
                return new ResponseResult(-1, conditionModel, "Didn't find available users, please adjust search condition");
            }

            UserStatus userStatus = new UserStatus(org, userApplyTo.getId(), userApplyTo.getEmail(), null, uuid, testCase, stationId);
            int statusCode = userStatusService.applyUserIfNotExist(userStatus);
            if (statusCode == 1) {
                userApplyTo.setStation(stationId);
                userStatus.setUser(userApplyTo);
                return new ResponseResult(0, userStatus, "Apply user successfully.");
            } else if (statusCode == -1) {
                retryTime += 1;
                Thread.sleep(3000);
                continue;
            }

            exclusionIds.add(userApplyTo.getId());
        }

        stationService.releaseStation(stationId);
        return new ResponseResult(-1, "", "Didn't find available users, please adjust search condition");
    }

    @Transactional
    public int insertUsers(List<User> list, boolean isReuseData, boolean isMTData) {
        if (isReuseData) {
            for (User user : list) {
                user.setReuseData(true);
            }
        }

        if (isMTData) {
            for (User user : list) {
                user.setMtData(true);
            }
        }

        return userMapper.insertUsers(list);
    }

    public List<User> findUserIdsByRole(String role) {
        return userMapper.findAllByRole(role);
    }

    public Map<String, Object> selectPage(Integer pageNum, Integer pageSize, String keyWord) {
        List<User> users = userMapper.findAllByKey(keyWord);
        int userNum = users.size();
        int start = (pageNum - 1) * pageSize;
        while (start >= userNum) {
            pageNum--;
            start = (pageNum - 1) * pageSize;
        }

        int end = pageNum * pageSize > userNum ? userNum : pageNum * pageSize;
        List<User> pageUsers = users.subList(start, end);
        Map<String, Object> res = new HashMap<>();
        res.put("data", pageUsers);
        res.put("total", users.size());
        return res;
    }

    public Integer selectTotal() {
        return userMapper.selectTotal();
    }

    public int saveUsers(List<User> users) {
        return userMapper.insertUsers(users);
    }

}
