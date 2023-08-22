package com.alvaria.datareuse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonReuseWorkType {
    private Integer id;
    private String workTypeName;
    private String type;
    private String comment;

    public NonReuseWorkType(String workTypeName, String type) {
        this.workTypeName = workTypeName;
        this.type = type;
    }
}
