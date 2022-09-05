package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.Utils.JwtUtil;
import com.alvaria.datareuse.entity.ResponseResult;
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
    public ResponseResult login(@RequestBody UserInfo userInfo) {
        UserInfo user = new UserInfo();
        Map<String, String> tokenMap = new HashMap<>();
        if (user != null) {
            Map<String, String> payload = new HashMap<>();
            payload.put("userId", "2");
            payload.put("username", "admin");
            String token = JwtUtil.generateToken(payload);
            tokenMap.put("token", token);
            tokenMap.put("role", "admin");
            return new ResponseResult(0,tokenMap,"Login successful");
        }

        return new ResponseResult(-1,null,"Username or password incorrect");
    }
}
