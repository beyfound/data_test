package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.WorkTypeMapper;
import com.alvaria.datareuse.entity.*;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkTypeService {
    public static List<String> WTTypeList = Arrays.asList("InboundVoiceWTs", "InboundSMSWTs", "ChatWTs", "InboundEmailWTs", "OutreachVoiceWTs", "OutreachSMSWTs");

    @Autowired
    private WorkTypeMapper workTypeMapper;
    @Autowired
    private WorkTypeStatusService workTypeStatusService;

    public int saveWorkType(WorkType workType) {
        if (workType.getId() == null) {
            return workTypeMapper.insertWorkType(workType);
        } else {
            return workTypeMapper.update(workType);
        }
    }

    public int insertWorkTypes(List<WorkType> list) {
        return workTypeMapper.insertWorkTypes(list);
    }

    public ResponseResult applyOneWorkType(ConditionModel conditionModel) {
        String type = conditionModel.getType();
        String org = conditionModel.getOrg();
        String testCase = conditionModel.getTestCase();
        String uuid = UUID.randomUUID().toString();
        boolean mtData = conditionModel.isMtData();

        if (!WTTypeList.contains(type)) {
            return new ResponseResult(-1, type, "type not in: [" + StringUtils.join(WTTypeList, ',') + "]");
        }

        List<WorkType> availableWorkType = new ArrayList<>();
        availableWorkType = workTypeMapper.filterAvailableWorkType(type, org, mtData);

        if (availableWorkType.isEmpty()) {
            return new ResponseResult(-1, "", "Didn't find available work type.");
        }

        boolean applySuccess = false;
        WorkType workTypeApplyTo = new WorkType();
        try {
            while (!applySuccess) {
                Random random = new Random();
                workTypeApplyTo = availableWorkType.get(random.nextInt(availableWorkType.size()));
                WorkTypeStatus workTypeStatus = new WorkTypeStatus(workTypeApplyTo.getId(), workTypeApplyTo.getWorkTypeName(), org, uuid, testCase);

                if (workTypeStatusService.applyWorkTypeIfNotExist(workTypeStatus) == 1) {
                    return new ResponseResult(0, workTypeStatus, "Apply work type successfully.");
                }

                availableWorkType.remove(workTypeApplyTo);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseResult(-1, "", "Apply work type failed.: All work types are occupied.");
        }

        return new ResponseResult(-1, "", "Apply work type failed.");
    }

}
