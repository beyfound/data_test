package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.StrategyStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyStatusService {
    @Autowired
    StrategyStatusMapper strategyStatusMapper;
    public int releaseStrategyByName(String name, String org) {
        return strategyStatusMapper.releaseStrategyByName(name, org);
    }

    public int releaseStrategyByTestCase(String testCase) {
        return strategyStatusMapper.releaseStrategyByTestCase(testCase);
    }

    public int releaseStrategyList(List<String> uuids) { return strategyStatusMapper.removeStatusByUUIDList(uuids);
    }
}
