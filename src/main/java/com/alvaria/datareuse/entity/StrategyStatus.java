package com.alvaria.datareuse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrategyStatus {
    private Integer id;
    private Integer strategyId;
    private String strategyName;
    private String testCase;
    private String uuid;
    private String org;

    public StrategyStatus(Integer strategyId, String strategyName, String testCase, String uuid, String org) {
        this.strategyId = strategyId;
        this.strategyName = strategyName;
        this.testCase = testCase;
        this.uuid = uuid;
        this.org = org;
    }
}
