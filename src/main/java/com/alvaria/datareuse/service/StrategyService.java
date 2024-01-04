package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.StrategyMapper;
import com.alvaria.datareuse.dao.UserMapper;
import com.alvaria.datareuse.entity.ConditionModel;
import com.alvaria.datareuse.entity.Strategy;
import com.alvaria.datareuse.entity.StrategyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StrategyService {
    @Autowired
    private StrategyMapper strategyMapper;

    public Strategy getIdleStrategy(String org){
        return strategyMapper.getIdleStrategy(org);
    }

    public StrategyStatus applyStrategy(ConditionModel conditionModel){
        Strategy strategy = getIdleStrategy(conditionModel.getOrg());
        if(strategy == null){
            return null;
        }
        String uuid = UUID.randomUUID().toString();
        StrategyStatus strategyStatus = new StrategyStatus(strategy.getId(), strategy.getName(), conditionModel.getTestCase(), uuid, conditionModel.getOrg());
        strategyMapper.applyStrategy(strategyStatus);
        return strategyStatus;
    }
}
