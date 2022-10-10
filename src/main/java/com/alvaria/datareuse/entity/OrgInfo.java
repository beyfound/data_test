package com.alvaria.datareuse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrgInfo {
    public String fullName;
    public String authorization;
    public String myAspectBaseUrl;
}
