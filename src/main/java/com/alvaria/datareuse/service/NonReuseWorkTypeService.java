package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.NonReuseWorkTypeMapper;
import com.alvaria.datareuse.dao.WorkTypeMapper;
import com.alvaria.datareuse.entity.*;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NonReuseWorkTypeService {
    public static List<String> WTTypeList = Arrays.asList("InboundVoiceWTs", "InboundSMSWTs", "ChatWTs", "InboundEmailWTs", "OutreachVoiceWTs", "OutreachSMSWTs");

    @Autowired
    private NonReuseWorkTypeMapper workTypeMapper;
    @Autowired
    private WorkTypeStatusService workTypeStatusService;

    public int saveWorkType(NonReuseWorkType workType) {
        if (workType.getId() == null) {
            return workTypeMapper.insertWorkType(workType);
        } else {
            return workTypeMapper.update(workType);
        }
    }
    @Transactional
    public int insertWorkTypes(List<NonReuseWorkType> list) {
        return workTypeMapper.insertWorkTypes(list);
    }

    public Map<String, Object> selectPage(Integer pageNum, Integer pageSize, String keyWord, String sort) {
        List<NonReuseWorkType> workTypes = workTypeMapper.findAllByKey(keyWord);
        String[] sortProp = sort.split(",");
        switch (sortProp[0]){
            case "workTypeName" :
                if(sortProp[1].equals("ASC")){
                    workTypes = workTypes.stream().sorted(Comparator.comparing(NonReuseWorkType::getWorkTypeName)).collect(Collectors.toList());
                }else {
                    workTypes = workTypes.stream().sorted(Comparator.comparing(NonReuseWorkType::getWorkTypeName).reversed()).collect(Collectors.toList());
                }

                break;
            default:
        }

        int wtNum = workTypes.size();
        int start = (pageNum - 1) * pageSize;
        while (start >= wtNum && wtNum!= 0) {
            pageNum--;
            start = (pageNum - 1) * pageSize;
        }

        int end = pageNum * pageSize > wtNum ? wtNum : pageNum * pageSize;
        List<NonReuseWorkType> pageWorkTypes = workTypes.subList(start, end);
        Map<String, Object> res = new HashMap<>();
        res.put("data", pageWorkTypes);
        res.put("total", workTypes.size());
        return res;
    }

    public Integer selectTotal() {
        return workTypeMapper.selectTotal();
    }

    public List<NonReuseWorkType> getAll() {
        return workTypeMapper.findAll();
    }

    public int deleteWorkTypes(String[] ids) {
        return workTypeMapper.removeWorkTypeByIds(ids);
    }

    public int saveWorkTypes(List<NonReuseWorkType> workTypes) {
        return workTypeMapper.insertWorkTypes(workTypes);
    }
}
