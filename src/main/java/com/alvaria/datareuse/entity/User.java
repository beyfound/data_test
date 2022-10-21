package com.alvaria.datareuse.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String email;
    private String role;
    private String identity;
    private String tags;
    private String[] tagsArray;

    public String[] getTagsArray() {
        if (tags != null) {
            JSONArray array = JSON.parseArray(this.tags);
            String[] temp = new String[array.size()];
            for (int i = 0; i < array.size(); i++) {
                temp[i] = array.get(i).toString();
            }
            
            tagsArray = temp;
        }
        return tagsArray;
    }

    public void setTagsArray(String[] tagsArray) {
        this.tagsArray = tagsArray;
    }

    private String password;
    private String cPassword;
    private String station;
    private String display;

    public String getDisplay() {
        return this.lastName + ", " + this.firstName;
    }

    private String userName;
    private String firstName;
    private String lastName;
    private String mgrTeamList;
    private String team;
    private String DID;
    @JsonProperty("personaType")
    private String personalType;
    private String name;
    private String tZone;
    private String language;
    private String managerOfTeam;
    private String telephoneNumber;
    private boolean mtData;
    private boolean reuseData;

    @JsonProperty("sn")
    public void setSn(String sn){
        this.lastName = sn;
    }

    @JsonProperty("givenName")
    public void setGivenName(String givenName){
        this.firstName = givenName;
    }

    @JsonProperty("displayName")
    public void setDisplayName(String givenName){
        this.firstName = givenName;
    }

    @JsonProperty("managerOf")
    public void setManagerOf(String managerOf){
        this.managerOfTeam = managerOf;
    }

//    @JsonProperty("username")
//    public void setUsername(String username){
//        this.userName = username;
//    }
}
