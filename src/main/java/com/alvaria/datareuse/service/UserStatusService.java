package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.UserStatusMapper;
import com.alvaria.datareuse.entity.UserStatus;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

    public List<UserStatus> getAllStatus() {
        return userStatusMapper.findAllStatus();
    }

    public int releaseUserByUUID(String uuid) {
        String station = userStatusMapper.getStatusStation(uuid);
        if (station != null) {
            stationService.releaseStation(station);
        }

        return userStatusMapper.removeStatusByUUID(uuid);
    }

    public int updateStation(UserStatus userStatus) {
        return userStatusMapper.updateStation(userStatus);
    }
}
