package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.WorkTypeStatusMapper;
import com.alvaria.datareuse.entity.WorkTypeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkTypeStatusService {
    @Autowired
    private WorkTypeStatusMapper workTypeStatusMapper;

    public int applyWorkTypeIfNotExist(WorkTypeStatus workTypeStatus) {
        return workTypeStatusMapper.applyWorkTypeIfNotExist(workTypeStatus);
    }

    public int releaseWorkTypeList(List<String> uuids) {
        return workTypeStatusMapper.removeStatusByUUIDList(uuids);
    }

    public int releaseWorkType(String uuid) {
        return workTypeStatusMapper.removeStatusByUUID(uuid);
    }

    public int releaseWorkTypeByTestCase(String testCase) {
        return workTypeStatusMapper.removeStatusByTestCase(testCase);
    }

    public List<WorkTypeStatus> selectInUsePage(Integer pageNum, Integer pageSize) {
        return workTypeStatusMapper.selectInUsePage(pageNum, pageSize);
    }

    public Integer selectTotal() {
        return workTypeStatusMapper.selectTotal();
    }
}
