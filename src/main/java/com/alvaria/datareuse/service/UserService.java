package com.alvaria.datareuse.service;

import com.alibaba.fastjson.JSON;
import com.alvaria.datareuse.dao.UserMapper;
import com.alvaria.datareuse.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    public List<User> getAll() {
        return userMapper.findAll();
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
        boolean specificTeam = team != null && team.trim().length() != 0 ? true : false;
        boolean mtData = conditionModel.isMtData();

        if (role == null || role.trim().length() == 0) {
            throw new Exception("Search condition error, please pass right 'role' parameter");
        }

        if (org == null || org.trim().length() == 0) {
            throw new Exception("Search condition error, please pass right 'org' parameter");
        }


//        List<User> filteredUserList = new ArrayList<>();
//
//        //If 'identity' string exists in condition model, ignore the tags.
//        boolean isFilterUser = true;
//        if (identity != null && identity.trim().length() != 0) {
//            User user = getUserByIdentify(identity);
//            if (user != null)
//                filteredUserList.add(user);
//        } else if ((tags != null && tags.length != 0) || specificTeam) {
//            List<User> tagsUsers = null;
//            List<User> teamUsers = null;
//            if (tags != null && tags.length != 0) {
//                tagsUsers = userTagService.getUserByTags(tags, mtData);
//            }
//
//            if (specificTeam) {
//                teamUsers = userTagService.getUserByTeam(team, mtData);
//            }
//
//            if (tagsUsers != null && teamUsers == null) {
//                filteredUserList = tagsUsers;
//            } else if (tagsUsers == null && teamUsers != null) {
//                filteredUserList = teamUsers;
//            } else if (tagsUsers != null && teamUsers != null) {
//                List<User> finalTeamUsers = teamUsers;
//                filteredUserList = tagsUsers.stream().filter(user -> finalTeamUsers.contains(user)).collect(Collectors.toList());
//            }
//
//        } else {
//            isFilterUser = false;
//        }
//
//        if (isFilterUser && filteredUserList.isEmpty()) {
//            return new ResponseResult(-1, conditionModel, "No users are filtered out according to the tags or identity");
//        }
//
//        if (!filteredUserList.isEmpty()) {
//            List<Integer> finalUserIdsByTagOrIdentity = filteredUserList.stream().map(user -> user.getId()).collect(Collectors.toList());
//            availableUsers = userMapper.filterAvailableUser(role, org, mtData, finalUserIdsByTagOrIdentity);
//
//            if (availableUsers.isEmpty())
//                return new ResponseResult(-1, filteredUserList, "The filtered users are in use or their role don't match");
//        } else {
//            availableUsers = userMapper.filterAvailableUser(role, org, mtData, null);
//        }


        boolean applySuccess = false;
        String stationId = null;
        List<Integer> exclusionIds = new ArrayList<>();
        while (!applySuccess) {
            User userApplyTo = userMapper.getOneAvailableUser(role, org, team, identity, tags, mtData, exclusionIds);
            if (userApplyTo == null) {
                return new ResponseResult(-1, conditionModel, "Didn't find available users, please adjust search condition");
            }

            UserStatus userStatus = new UserStatus(org, userApplyTo.getId(), userApplyTo.getEmail(), null, uuid, testCase);
            if (userStatusService.applyUserIfNotExist(userStatus) == 1) {
                if (needStation) {
                    stationId = stationService.getIdleStationIdByOrg(org);
                    if (stationId == null)
                        return new ResponseResult(-1, "", "Didn't find available station on org: " + org);
                    userApplyTo.setStation(stationId);
                    userStatus.setStation(stationId);
                    userStatusService.updateStation(userStatus);
                }

                userStatus.setUser(userApplyTo);
                return new ResponseResult(0, userStatus, "Apply user successfully.");
            }

            exclusionIds.add(userApplyTo.getId());
        }

        return new ResponseResult(-1, "", "Didn't find available users, please adjust search condition");
    }

    @Transactional
    public int insertUsers(List<User> list) {
        return userMapper.insertUsers(list);
    }

    public List<User> findUserIdsByRole(String role) {
        return userMapper.findAllByRole(role);
    }

    public Map<String, Object> selectPage(Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<User> users = userMapper.selectPage(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("data", users);
        res.put("total", userMapper.selectTotal());
        return res;
    }

    public Integer selectTotal() {
        return userMapper.selectTotal();
    }


}
