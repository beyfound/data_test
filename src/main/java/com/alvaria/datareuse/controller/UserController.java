package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.*;
import com.alvaria.datareuse.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/user")
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

    @Autowired
    private TestBedDataService testBedDataService;

    @PostMapping("upload")
    @ApiOperation(value = "import users into the database")
    public ResponseResult multipartFileTest(@RequestPart MultipartFile multipartFile, @RequestParam(required = false, defaultValue = "true") boolean isReuseData, @RequestParam(required = false, defaultValue = "true") boolean isMTData) throws Exception {
        List<User> userInfoList = uploadCSVService.getUserListFromCSV(multipartFile);
        int a = userService.insertUsers(userInfoList, isReuseData, isMTData);
        return new ResponseResult(0, "Insert " + a + " record(s) successfully", "" );
    }


    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new ResponseResult(0, user, "");
    }

    @GetMapping("/email")
    public ResponseResult getUerByEmail(String email) {
        return new ResponseResult(0, userService.getUserByEmail(email), "");
    }

    @GetMapping("/idty")
    public ResponseResult getUerByIdentity(@RequestParam String idty) {
        return new ResponseResult(0, userService.getUserByIdentify(idty), "");
    }

    @GetMapping("/all")
    public ResponseResult getAllUser() {
        List<User> users = userService.getAll();
        return new ResponseResult(0, users, "");
    }

    @PostMapping("/save")
    public ResponseResult saveUser(@RequestBody User user) {
        int code = userService.saveUser(user);
        return new ResponseResult(code, user, code > 0 ? "Save successfully" : "Save failed");
    }

    @PostMapping("/saveUsers")
    public ResponseResult saveUsers(@RequestBody List<User> users) {
        int code = userService.saveUsers(users);
        return new ResponseResult(code, users, code > 0 ? "Save successfully" : "Save failed");
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteUsers(@RequestBody Integer[] ids) {
        int row = userService.deleteUsers(ids);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Delete successfully" : "Delete failed");
    }

    @DeleteMapping("/{userId}")
    public ResponseResult deleteUserById(@PathVariable Integer userId) {
        int row = userService.deleteUserById(userId);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Delete successfully" : "Delete failed");
    }

    @DeleteMapping("/{email}")
    public ResponseResult deleteUserByEmail(@PathVariable String email) {
        int row = userService.deleteUserByEmail(email);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Delete successfully" : "Delete failed");
    }

    @PostMapping("/apply")
    public ResponseResult applyOneUser(@RequestBody ConditionModel conditionModel) throws Exception {
        return userService.applyOneUser(conditionModel);
    }

    @DeleteMapping("/release/{uuid}")
    public ResponseResult releaseOneUser(@PathVariable String uuid) {
        int row = userStatusService.releaseUserByUUID(uuid);
        return new ResponseResult(0, "", "Release work type successfully, effect" + row + "row(s)");
    }

    @DeleteMapping("/release/{testCase}")
    public ResponseResult releaseUserByTestCase(@PathVariable String testCase) {
        int row = userStatusService.releaseUserByTestCase(testCase);
        return new ResponseResult(0, "", "Release work type successfully, effect" + row + "row(s)");
    }

    @DeleteMapping("/release/uuids")
    public ResponseResult releaseUserList(@RequestBody List<String> uuids) {
        if (uuids == null || uuids.isEmpty()) {
            return new ResponseResult(-1, uuids, "parameter error");
        }

        return new ResponseResult(0, "effect " + userStatusService.releaseUserList(uuids) + " rows", "Release successfully");
    }

    @DeleteMapping("/release/ids")
    public ResponseResult releaseUserByIds(@RequestBody List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ResponseResult(-1, ids, "parameter error");
        }

        return new ResponseResult(0, "effect " + userStatusService.releaseUsersByIds(ids) + " rows", "Release successfully");
    }

    @GetMapping("/{userId}/tag")
    public ResponseResult getUserTagsById(@PathVariable Integer userId) {
        return new ResponseResult(0, userTagService.getUserTagsById(userId), "");
    }

    @GetMapping("/tag")
    public ResponseResult getUserByTags(@RequestParam List<String> tags) {
        return new ResponseResult(0, userTagService.getUserByTags(new String['r'], false), "");
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
    public ResponseResult findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String keyword) {

        return new ResponseResult(0, userService.selectPage(pageNum, pageSize, keyword), "");
    }

    @GetMapping("/status/page")
    public ResponseResult findInUsePage(@RequestParam Integer pageNum, @RequestParam() Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<UserStatus> data = userStatusService.selectInusePage(pageNum, pageSize);
        Integer total = userStatusService.selectTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return new ResponseResult(0, res, "");
    }

    @GetMapping("/organizations/{org}/users")
    public ResponseResult getOrganizationUsers(@PathVariable String org, @RequestParam String keyWord){
        return new ResponseResult(0, testBedDataService.getOrganizationUsers(org, keyWord), "");
    }
}
