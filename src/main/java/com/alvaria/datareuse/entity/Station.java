package com.alvaria.datareuse.entity;

import lombok.Data;

@Data
public class Station {
    private Integer id;
    private String station;
    private String org;
    private boolean idle;
}
