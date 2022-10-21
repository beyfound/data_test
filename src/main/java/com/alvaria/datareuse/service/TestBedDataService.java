package com.alvaria.datareuse.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alvaria.datareuse.Utils.HttpUtil;
import com.alvaria.datareuse.entity.OrgInfo;
import com.alvaria.datareuse.entity.User;
import com.alvaria.datareuse.entity.WorkType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TestBedDataService {
    private static String User_BASE_URL = "https://myaspect.test.id.aspect-cloud.net/janus_api/v2/organizations/";
    private static String Username = "sysowner@%s.com";
    private static String Password = "Forgerock1";
    private static String Scope = "provisioningapi telephoneNumber mail cmAccess MessageDistributor customerexperienceapi myaspect.users acl reportingapi myaspect.teams uid outreachapi ReportingQuery ConfigurationService sn email myaspect.users.write provisioner openid givenName cn engagementcenterapi messagingapi myaspect.teams.write postalAddress preferredlanguage status";
    private static String WorkTypes_Base_Url = "https://%s.brooklyn.aspect-cloud.net/via/v2/organizations/%s/provisioning/workTypes";
    private static Map<String, OrgInfo> Test_Environment = new HashMap<String, OrgInfo>();

    static {
        OrgInfo pens = new OrgInfo();
        pens.setApiKey("023101B696FF3C7DD1A35A9CB5FCEA015054550B7FDEC2D05E669D2BB9D91C8F");
        pens.setAuthorization("Basic UFJPVklTSU9OSU5HX0FVVE9NQVRJT05fU0VSVklDRTprMjhhWnJXWjFfODBxSDFzWWlsbE82aG84c3ErR1NjM2p3ZlFkRjJ3VFhrPQ==");
        pens.setMyAspectBaseUrl("https://auth.test.id.aspect-cloud.net");
        pens.setFullName("pens");
        Test_Environment.put("pens", pens);
    }

    public List<User> getOrganizationUsers(String org, String keyWord) {
        String url = User_BASE_URL + org + "/users";
        Map<String, String> params = new HashMap<>();
        params.put("sort", "email");
        params.put("size", "500");
        params.put("page", "0");
        int page = 0;
        int pageUsersCount = 1;
        List<User> totalUsers = new ArrayList<>();


        String token = getToken(org);
        if (token == null) {
            return null;
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        while (pageUsersCount != 0) {
            params.put("page", page + "");
            String response = HttpUtil.doGet(url, headers, params);
            JSONObject object = JSON.parseObject(response);
            Object usersCount = object.get("usersInPage");
            pageUsersCount = Integer.parseInt(usersCount.toString());

            if (pageUsersCount != 0) {
                page++;
                List<User> users = JSON.parseArray(object.get("users").toString(), User.class);
                totalUsers.addAll(users.stream().filter(user -> user.getUserName().toLowerCase().contains(keyWord.toLowerCase())).collect(Collectors.toList()));
            }
        }

        for (User user : totalUsers) {
            user.setEmail(user.getEmail().substring(0, user.getEmail().indexOf('@')));
            user.setPassword("Forgerock1");
            user.setUserName(null);
            user.setManagerOfTeam(user.getManagerOfTeam().replace('[',' ').replace(']',' ').trim());
        }

        return totalUsers;
    }

    public List<WorkType> getOrganizationWorkTypes(String org, String keyWord) {
        String url = String.format(WorkTypes_Base_Url, org, org);
        List<WorkType> workTypes = new ArrayList<>();
        String token = getToken(org);
        if (token == null) {
            return null;
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put("x-api-key", Test_Environment.get(org).getApiKey());
        Map<String, String> inboundTypeFilter = new HashMap<>();
        inboundTypeFilter.put("voice", "InboundVoiceWTs");
        inboundTypeFilter.put("sms", "InboundSMSWTs");
        inboundTypeFilter.put("chat", "ChatWTs");
        inboundTypeFilter.put("email", "InboundEmailWTs");

        Map<String, String> outreachTypeFilter = new HashMap<>();
        outreachTypeFilter.put("voice", "OutreachVoiceWTs");
        outreachTypeFilter.put("sms", "OutreachSMSWTs");

        Map<String, Map> workTypeFilter = new HashMap<>();
        workTypeFilter.put("outreach", outreachTypeFilter);
        workTypeFilter.put("inbound", inboundTypeFilter);

        for (Map.Entry m : workTypeFilter.entrySet()) {
            Map<String, String> params = new HashMap<>();
            params.put("direction", m.getKey().toString());
            Map<String, String> type = (Map<String, String>) m.getValue();
            for (Map.Entry t : type.entrySet()) {
                params.put("channelType", t.getKey().toString());

                String response = HttpUtil.doGet(url, headers, params);
                JSONObject object = JSON.parseObject(response);
                List<JSONObject> wts = JSON.parseArray(object.get("workTypes").toString());

                for (JSONObject o : wts) {
                    List<JSONObject> names = JSON.parseArray(o.get("friendlyName").toString());
                    String workTypeName = names.get(0).get("value").toString();
                    if (workTypeName.toLowerCase().contains(keyWord.toLowerCase())) {
                        WorkType workType = new WorkType();
                        workType.setWorkTypeName(workTypeName);
                        workType.setType(t.getValue().toString());
                        workTypes.add(workType);
                    }
                }
            }
        }

        return workTypes;
    }

    private String getToken(String org) {
        String token = null;
        int retry = 3;
        while (retry > 0) {
            String MyAspectUrl = String.format("%s/openam/oauth2/access_token?realm=%s", Test_Environment.get(org).myAspectBaseUrl, Test_Environment.get(org).fullName);
            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", "application/x-www-form-urlencoded");
            header.put("Authorization", Test_Environment.get(org).authorization);

            Map<String, String> content = new HashMap<>();
            content.put("grant_type", "password");
            content.put("scope", Scope);
            content.put("username", String.format(Username, org));
            content.put("password", Password);
            token = HttpUtil.sendPostForm(MyAspectUrl, header, content);
            if (token == null) {
                retry--;
            } else {
                break;
            }
        }
        return token;
    }
}
