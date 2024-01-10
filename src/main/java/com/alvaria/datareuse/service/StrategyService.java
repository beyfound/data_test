package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.StrategyMapper;
import com.alvaria.datareuse.dao.UserMapper;
import com.alvaria.datareuse.entity.ConditionModel;
import com.alvaria.datareuse.entity.Strategy;
import com.alvaria.datareuse.entity.StrategyStatus;
import com.alvaria.datareuse.entity.WorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StrategyService {
    @Autowired
    private StrategyMapper strategyMapper;

    public Strategy getIdleStrategy(String org){
        return strategyMapper.getIdleStrategy(org);
    }

    public synchronized StrategyStatus applyStrategy(ConditionModel conditionModel){
        Strategy strategy = getIdleStrategy(conditionModel.getOrg());
        if(strategy == null){
            return null;
        }
        String uuid = UUID.randomUUID().toString();
        StrategyStatus strategyStatus = new StrategyStatus(strategy.getId(), strategy.getName(), conditionModel.getTestCase(), uuid, conditionModel.getOrg());
        strategyMapper.applyStrategy(strategyStatus);
        return strategyStatus;
    }

    public int save(Strategy strategy) {
        if(strategy.getId() == null || strategy.getId() == 0){
            return strategyMapper.save(strategy);
        }else {
            return strategyMapper.update(strategy);
        }

    }

    public int deleteStrategies(Integer[] ids) { return strategyMapper.deleteByIds(ids);
    }

    public Map<String, Object> selectPage(Integer pageNum, Integer pageSize, String keyWord, String sort) {
        List<Strategy> strategies = strategyMapper.findAllByKey(keyWord);
        String[] sortProp = sort.split(",");
        switch (sortProp[0]){
            case "name" :
                if(sortProp[1].equals("ASC")){
                    strategies = strategies.stream().sorted(Comparator.comparing(Strategy::getName)).collect(Collectors.toList());
                }else {
                    strategies = strategies.stream().sorted(Comparator.comparing(Strategy::getName).reversed()).collect(Collectors.toList());
                }

                break;
            default:
        }

        int wtNum = strategies.size();
        int start = (pageNum - 1) * pageSize;
        while (start >= wtNum && wtNum!= 0) {
            pageNum--;
            start = (pageNum - 1) * pageSize;
        }

        int end = pageNum * pageSize > wtNum ? wtNum : pageNum * pageSize;
        List<Strategy> pageWorkTypes = strategies.subList(start, end);
        Map<String, Object> res = new HashMap<>();
        res.put("data", pageWorkTypes);
        res.put("total", strategies.size());
        return res;
    }

    public int insertStrategis(List<Strategy> strategies) {
        return strategyMapper.insertStrategies(strategies);
    }
}
