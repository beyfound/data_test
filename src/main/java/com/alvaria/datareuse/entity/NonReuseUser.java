package com.alvaria.datareuse.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonReuseUser {
    private Integer id;
    private String email;
    private String role;
    private String userName;
    private String firstName;
    private String lastName;
    private String team;
    private String managerOfTeam;
    private String comment;

    public NonReuseUser(String email, String role, String team, String managerOfTeam) {
        this.email = email;
        this.role = role;
        this.team = team;
        this.managerOfTeam = managerOfTeam;
    }

    public String getDisplay() {
        return this.lastName + ", " + this.firstName;
    }

    @JsonProperty("sn")
    public void setSn(String sn){
        this.lastName = sn;
    }

    @JsonProperty("givenName")
    public void setGivenName(String givenName){
        this.firstName = givenName;
    }

    @JsonProperty("managerOf")
    public void setManagerOf(String managerOf){
        this.managerOfTeam = managerOf;
    }

}
