package com.alvaria.datareuse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrgInfo {
    public String fullName;
    public String authorization;
    public String myAspectBaseUrl;
    public String apiUrl;
    public String apiKey;
}
