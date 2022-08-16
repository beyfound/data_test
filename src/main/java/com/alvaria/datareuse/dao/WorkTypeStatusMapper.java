package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.UserStatus;
import com.alvaria.datareuse.entity.WorkTypeStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WorkTypeStatusMapper {
//    @Select("select user_id from user_status where org = #{org}")
//    List<Integer> findInUsingStatusByOrg(String org);

    @Insert("INSERT INTO work_type_status(work_type_id, org, `uuid`, test_case, `work_type_name`)\n" +
            "SELECT #{workTypeId}, #{org}, #{uuid}, #{testCase}, #{workTypeName} FROM DUAL \n" +
            "WHERE NOT EXISTS (SELECT * FROM wt_inuse WHERE work_type_id=#{workTypeId} AND org=#{org});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int applyWorkTypeIfNotExist(WorkTypeStatus workTypeStatus);

    //    UserStatus findStatusByUserIdAndOrg(Integer userId, String org);
//
    @Delete("update work_type_status set del_flag = 1 where where uuid=#{uuid}")
    int removeStatusByUUID(String uuid);

    int removeStatusByUUIDList(List<String> uuids);
//
//    List<UserStatus> findStatusByUserId(Integer userId);
//
//    List<UserStatus> findAllStatus();
//
//    int updateStation(UserStatus userStatus);

}
