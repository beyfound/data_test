package com.alvaria.datareuse.entity;

import lombok.Data;

@Data
public class Strategy {
    private Integer id;
    private String name;

    public Strategy(String name) {
        this.name = name;
    }

    public Strategy(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Strategy() {
    }
}
