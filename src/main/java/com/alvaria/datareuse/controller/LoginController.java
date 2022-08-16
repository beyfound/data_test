package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.Utils.JwtUtil;
import com.alvaria.datareuse.entity.UserInfo;
import com.alvaria.datareuse.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserInfo userInfo) {
        UserInfo user = userInfoService.findByUsernameAndPassword(userInfo.getUsername(), userInfo.getPassword());
        Map<String, Object> data = new HashMap<>();
        Map<String, String> tokenMap = new HashMap<>();
        Map<String, String> msgMap = new HashMap<>();
        Map<String, Integer> statusMap = new HashMap<>();
        if (user != null) {
            Map<String, String> payload = new HashMap<>();
            payload.put("userId", user.getId() + "");
            payload.put("username", user.getUsername());
            String token = JwtUtil.generateToken(payload);
            tokenMap.put("token", token);
            msgMap.put("msg", "Login successful");
            statusMap.put("status", 200);
            data.put("data", tokenMap);
            data.put("meta", statusMap);
            return data;
        }

        statusMap.put("status", 400);
        msgMap.put("Username or password incorrect", "400");
        return data;
    }
}
