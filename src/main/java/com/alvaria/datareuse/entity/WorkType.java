package com.alvaria.datareuse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkType {
    private Integer id;
    private String workTypeName;
    private String type;
}
