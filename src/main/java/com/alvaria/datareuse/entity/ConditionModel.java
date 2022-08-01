package com.alvaria.datareuse.entity;

import lombok.Data;

@Data
public class ConditionModel {
    private String org;
    private String role;
    private String identity;
    private String[] tags;
    private String testCase;
    private String team;
    private boolean needStation;
}
