package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.UserStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserStatusMapper {
    @Select("select user_id from user_status where org = #{org}")
    List<Integer> findInUsingStatusByOrg(String org);

    @Insert("INSERT INTO user_status(org,user_id,test_case,`uuid`,station)\n" +
            "SELECT #{org}, #{userId}, #{testCase}, #{uuid}, #{station} FROM DUAL \n" +
            "WHERE NOT EXISTS (SELECT * FROM user_status WHERE user_id=#{userId} AND org=#{org});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int applyUserIfNotExist(UserStatus userStatus);

    UserStatus findStatusByUserIdAndOrg(Integer userId, String org);

    @Delete("delete from user_status where uuid=#{uuid}")
    int removeStatusByUUID(String uuid);

    List<UserStatus> findStatusByUserId(Integer userId);

    List<UserStatus> findAllStatus();

    int updateStation(UserStatus userStatus);

    @Select("select station from user_status where uuid = #{uuid}")
    String getStatusStation(String uuid);
}
