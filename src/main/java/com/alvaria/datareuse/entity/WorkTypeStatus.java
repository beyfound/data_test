package com.alvaria.datareuse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeStatus {
    @JsonIgnore
    private Integer id;
    private String org;
    @JsonIgnore
    private Integer workTypeId;
    @JsonIgnore
    private Date startTime;
    private String uuid;
    private String testCase;
    private String workTypeName;

    public WorkTypeStatus(Integer workTypeId, String workTypeName, String org, String uuid, String testCase) {
        this.workTypeId = workTypeId;
        this.org = org;
        this.uuid = uuid;
        this.testCase = testCase;
        this.workTypeName = workTypeName;
    }
}
