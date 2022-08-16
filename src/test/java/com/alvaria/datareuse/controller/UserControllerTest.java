package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.ConditionModel;
import com.alvaria.datareuse.entity.ResponseResult;
import com.alvaria.datareuse.entity.UserStatus;
import com.alvaria.datareuse.entity.WorkTypeStatus;
import com.alvaria.datareuse.service.UserService;
import com.alvaria.datareuse.service.UserStatusService;
import com.alvaria.datareuse.service.WorkTypeService;
import com.alvaria.datareuse.service.WorkTypeStatusService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    public static String USER_UUID;
    public static String WT_UUID;
    @Resource
    private UserService userService;

    @Resource
    private UserStatusService userStatusService;

    @Resource
    private WorkTypeService workTypeService;

    @Resource
    private WorkTypeStatusService workTypeStatusService;

    @Test
    public void applyOneUser() throws Exception {
        ConditionModel conditionModel = new ConditionModel();
        conditionModel.setNeedStation(true);
        conditionModel.setOrg("pats");
        conditionModel.setRole("agent");
        conditionModel.setTestCase("Springboot unit tes");
        ResponseResult responseResult = userService.applyOneUser(conditionModel);
        Assert.assertNotEquals(responseResult.getCode(),-1 );
        UserStatus userStatus = (UserStatus) responseResult.getData();
        USER_UUID = userStatus.getUuid();
        userStatusService.getStatusByUUID(USER_UUID);
    }

    @Test
    public void releaseUser() throws Exception {
        List<String> uuids = new ArrayList<>();
        uuids.add(USER_UUID);
        userStatusService.releaseUserList(uuids);
    }

    @Test
    public void applyWorkType() throws Exception {
        ConditionModel conditionModel = new ConditionModel();
        conditionModel.setOrg("pats");
        conditionModel.setType("InboundSMSWTs");
        conditionModel.setTestCase("Springboot unit tes");
        ResponseResult responseResult = workTypeService.applyOneWorkType(conditionModel);
        Assert.assertNotEquals(responseResult.getCode(),-1 );
        WorkTypeStatus workTypeStatus = (WorkTypeStatus) responseResult.getData();
        WT_UUID = workTypeStatus.getUuid();
    }

   // @Test
    public void releaseWorkType() throws Exception {
        List<String> uuids = new ArrayList<>();
        uuids.add(WT_UUID);
        workTypeStatusService.releaseWorkTypeList(uuids);
    }
}