package com.alvaria.datareuse.entity;

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
}
