package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.*;
import com.alvaria.datareuse.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/non_reuse_user")
@CrossOrigin
@Api(tags = "Non Reuse User controller")
public class NonReuseUserController {
    @Autowired
    private NonReuseUserService nonReuseUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private TestBedDataService testBedDataService;

    @Autowired
    private UploadCSVService uploadCSVService;

    @PostMapping("upload")
    @ApiOperation(value = "import users into the database")
    public ResponseResult multipartFileTest(@RequestPart MultipartFile multipartFile) throws Exception {
        List<User> userInfoList = uploadCSVService.getUserListFromCSV(multipartFile);
        List<NonReuseUser> nonReuseUsers = new ArrayList<>();
        for (User us :userInfoList) {
            NonReuseUser nonReuseUser =  new NonReuseUser(us.getEmail(), us.getRole(), us.getTeam(), us.getManagerOfTeam());
            nonReuseUsers.add(nonReuseUser);
        }
        int a = nonReuseUserService.insertUsers(nonReuseUsers);
        return new ResponseResult(0, "Insert " + a + " record(s) successfully", "");
    }

    @GetMapping("/email")
    public ResponseResult getUerByEmail(String email) {
        return new ResponseResult(0, nonReuseUserService.getUserByEmail(email), "");
    }


    @GetMapping("/all")
    public ResponseResult getAllUser() {
        List<NonReuseUser> users = nonReuseUserService.getAll();
        return new ResponseResult(0, users, "");
    }

    @PostMapping("/save")
    public ResponseResult saveUser(@RequestBody NonReuseUser user) {
        int code = nonReuseUserService.saveUser(user);
        return new ResponseResult(code, user, code > 0 ? "Save successfully" : "Save failed");
    }

    @PostMapping("/saveUsers")
    public ResponseResult saveUsers(@RequestBody List<NonReuseUser> users) {
        int code = nonReuseUserService.saveUsers(users);
        return new ResponseResult(code, users, code > 0 ? "Save successfully" : "Save failed");
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteUsers(@RequestBody Integer[] ids) {
        int row = nonReuseUserService.deleteUsers(ids);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Delete successfully" : "Delete failed");
    }

    @DeleteMapping("/{userId}")
    public ResponseResult deleteUserById(@PathVariable Integer userId) {
        int row = nonReuseUserService.deleteUserById(userId);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Delete successfully" : "Delete failed");
    }

    @DeleteMapping("/{email}")
    public ResponseResult deleteUserByEmail(@PathVariable String email) {
        int row = nonReuseUserService.deleteUserByEmail(email);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Delete successfully" : "Delete failed");
    }

    //分页查询接口
    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) String sort) {

        return new ResponseResult(0, nonReuseUserService.selectPage(pageNum, pageSize, keyword, sort), "");
    }

    @GetMapping("/organizations/{org}/users")
    public ResponseResult getOrganizationUsers(@PathVariable String org, @RequestParam String keyWord) {
        List<User> testBedUsers = testBedDataService.getOrganizationUsers(org, keyWord);
        List<NonReuseUser> nonReuseUsersInDB = nonReuseUserService.getAll();
        List<User> usersInDB = userService.getAll();
        List<User> userNotInDB = testBedUsers.stream().parallel().filter(a -> usersInDB.stream().noneMatch(b -> a.getEmail().equals(b.getEmail())) && nonReuseUsersInDB.stream().noneMatch(c -> a.getEmail().equals(c.getEmail()))).collect(Collectors.toList());
        return new ResponseResult(0, userNotInDB, "");
    }
}
