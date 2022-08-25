package com.alvaria.datareuse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus {
    @JsonIgnore
    private int id;
    private String org;
    @JsonIgnore
    private int userId;
    private String email;
    private Date startTime;
    private String uuid;
    private String testCase;
    private User user;
    private String station;

    public UserStatus(String org, int userId, String email, Date startTime, String uuid, String testCase) {
        this.org = org;
        this.userId = userId;
        this.email = email;
        this.startTime = startTime;
        this.uuid = uuid;
        this.testCase = testCase;
    }
}
