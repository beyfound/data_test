package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.UserStatusMapper;
import com.alvaria.datareuse.entity.User;
import com.alvaria.datareuse.entity.UserStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserStatusService {

    @Autowired
    private UserStatusMapper userStatusMapper;

    @Autowired
    private StationService stationService;

    public List<Integer> findInUsingStatusByOrg(String org) {
        return userStatusMapper.findInUsingStatusByOrg(org);
    }

    public int applyUserIfNotExist(UserStatus userStatus) {
        return userStatusMapper.applyUserIfNotExist(userStatus);
    }

    public UserStatus getStatusByUserIdAndOrg(Integer userId, String org) {
        UserStatus status = userStatusMapper.findStatusByUserIdAndOrg(userId, org);
        return status;
    }

    public List<UserStatus> getStatusByUserId(Integer userId) {
        return userStatusMapper.findStatusByUserId(userId);
    }

    public UserStatus getStatusByUUID(String uuid) {
        return userStatusMapper.getStatusByUUID(uuid);
    }

    public List<UserStatus> getAllStatus() {
        return userStatusMapper.findAllStatus();
    }

    public int releaseUserByUUID(String uuid) {
        return userStatusMapper.removeStatusByUUID(uuid);
    }

    public int releaseUserByTestCase(String testCase) {
        return userStatusMapper.removeStatusByTestCase(testCase);
    }

    public int updateStation(UserStatus userStatus) {
        return userStatusMapper.updateStation(userStatus);
    }

    public int releaseUserList(List<String> uuids) {
        return userStatusMapper.removeStatusByUUIDList(uuids);
    }


    public List<UserStatus> selectInusePage(Integer pageNum, Integer pageSize) {
        return userStatusMapper.selectInUsePage(pageNum, pageSize);
    }

    public Integer selectTotal() {
        return userStatusMapper.selectTotal();
    }
}
