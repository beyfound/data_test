package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.Station;
import com.alvaria.datareuse.entity.UserStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserStatusMapper {
    @Select("select user_id from user_status where org = #{org}")
    List<Integer> findInUsingStatusByOrg(String org);

    @Insert("INSERT INTO user_status(org,user_id,email,test_case,`uuid`,station)\n" +
            "SELECT #{org}, #{userId},#{email}, #{testCase}, #{uuid}, #{station} FROM DUAL \n" +
            "WHERE NOT EXISTS (SELECT * FROM user_status WHERE user_id=#{userId} AND org=#{org});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int applyUserIfNotExist(UserStatus userStatus);

    UserStatus findStatusByUserIdAndOrg(Integer userId, String org);

    @Delete("delete from user_status where uuid=#{uuid}")
    int removeStatusByUUID(String uuid);

    @Select("select * from user_status where uuid=#{uuid}")
    UserStatus getStatusByUUID(String uuid);

    List<UserStatus> findStatusByUserId(Integer userId);

    List<UserStatus> findAllStatus();

    int updateStation(UserStatus userStatus);

    @Select("select station from user_status where uuid = #{uuid}")
    String getStatusStation(String uuid);

    int removeStatusByUUIDList(List<String> uuids);

    @Delete("delete from user_status where test_case=#{testCase}")
    int removeStatusByTestCase(String testCase);

    @Select("select * from user_status limit #{pageNum}, #{pageSize}")
    List<UserStatus> selectInUsePage(Integer pageNum, Integer pageSize);

    @Select("select count(*) from user_status")
    Integer selectTotal();
}
