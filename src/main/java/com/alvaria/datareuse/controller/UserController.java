package com.alvaria.datareuse.controller;

import com.alibaba.fastjson2.JSONObject;
import com.alvaria.datareuse.entity.*;
import com.alvaria.datareuse.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "User controller")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UploadCSVService uploadCSVService;

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private StationService stationService;


    @PostMapping("upload")
    @ApiOperation(value = "import users into the database")
    public String multipartFileTest(@RequestPart MultipartFile multipartFile) throws Exception {
        List<User> userInfoList = uploadCSVService.getUserListFromCSV(multipartFile);
        int a = userService.insertUsers(userInfoList);
        return "insert " + a + " records";
    }


    @GetMapping("/user/{id}")
    public ResponseResult getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new ResponseResult(0, user, "");
    }

    @GetMapping("/user/email")
    public ResponseResult getUerByEmail(String email) {
        return new ResponseResult(0, userService.getUserByEmail(email), "");
    }

    @GetMapping("/user/idty")
    public ResponseResult getUerByIdentity(@RequestParam String idty) {
        return new ResponseResult(0, userService.getUserByIdentify(idty), "");
    }

    @GetMapping("/user/all")
    public ResponseResult getAllUser() {
        List<User> users = userService.getAll();
        return new ResponseResult(0, users, "");
    }

    @PostMapping("/user")
    public ResponseResult saveUser(@RequestBody User user) {
        int code = userService.saveUser(user);
        return new ResponseResult(code, user, code > 0 ? "Save successfully" : "Save failed");
    }

    @DeleteMapping("/user")
    public ResponseResult deleteUsers(@RequestParam(value = "ids[]") Integer[] ids) {
        int row = userService.deleteUsers(ids);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Delete successfully" : "Delete failed");
    }

    @DeleteMapping("/user/{userId}")
    public ResponseResult deleteUserById(@PathVariable Integer userId) {
        int row = userService.deleteUserById(userId);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Delete successfully" : "Delete failed");
    }

    @DeleteMapping("/user/{email}")
    public ResponseResult deleteUserByEmail(@PathVariable String email) {
        int row = userService.deleteUserByEmail(email);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Delete successfully" : "Delete failed");
    }

    @PostMapping("/user/apply")
    public ResponseResult applyOneUser(@RequestBody ConditionModel conditionModel) throws Exception {
        return userService.applyOneUser(conditionModel);
    }

    @DeleteMapping("/user/release/{uuid}")
    public ResponseResult releaseOneUser(@PathVariable String uuid) {
        int row = userStatusService.releaseUserByUUID(uuid);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Release user successfully" : "Release user failed");
    }

    @GetMapping("/{userId}/tag")
    public ResponseResult getUserTagsById(@PathVariable Integer userId) {
        return new ResponseResult(0, userTagService.getUserTagsById(userId), "");
    }

    @GetMapping("/user/tag")
    public ResponseResult getUserByTags(@RequestParam List<String> tags) {
        return new ResponseResult(0, userTagService.getUserByTags(new String['r']), "");
    }

    @GetMapping("/status/{userId}")
    public ResponseResult getStatusByUserId(@PathVariable Integer userId) {
        List<UserStatus> statusList = userStatusService.getStatusByUserId(userId);
        return new ResponseResult(0, statusList, "");
    }

    @GetMapping("/status/{userId}/{org}")
    public ResponseResult getStatusByOrgAndUserId(@PathVariable Integer userId, @PathVariable String org) {
        UserStatus status = userStatusService.getStatusByUserIdAndOrg(userId, org);
        return new ResponseResult(0, status, "");
    }

    @GetMapping("/status/all")
    public ResponseResult getAllStatus() {
        List<UserStatus> statusList = userStatusService.getAllStatus();
        return new ResponseResult(0, statusList, "");
    }

    //分页查询接口
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<User> data = userService.selectPage(pageNum, pageSize);
        Integer total = userService.selectTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }

    @PostMapping("/login")
    public boolean login(@RequestBody Login login) {
        JSONObject json = new JSONObject();
        return true;
    }

    @GetMapping("/user/station")
    public String getStation(String org) {
        return  stationService.getIdleStationIdByOrg(org);
    }
}
