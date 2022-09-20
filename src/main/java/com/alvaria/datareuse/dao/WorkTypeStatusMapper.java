package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.Station;
import com.alvaria.datareuse.entity.UserStatus;
import com.alvaria.datareuse.entity.WorkTypeStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WorkTypeStatusMapper {

    @Insert("INSERT INTO work_type_status(work_type_id, org, `uuid`, test_case, `work_type_name`)\n" +
            "SELECT #{workTypeId}, #{org}, #{uuid}, #{testCase}, #{workTypeName} FROM DUAL \n" +
            "WHERE NOT EXISTS (SELECT * FROM work_type_status WHERE work_type_id=#{workTypeId} AND org=#{org});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int applyWorkTypeIfNotExist(WorkTypeStatus workTypeStatus);

    @Delete("delete from work_type_status where uuid=#{uuid}")
    int removeStatusByUUID(String uuid);

    int removeStatusByUUIDList(List<String> uuids);

    @Delete("delete from work_type_status where test_case=#{testCase}")
    int removeStatusByTestCase(String testCase);

    @Select("select * from work_type_status limit #{pageNum}, #{pageSize}")
    List<WorkTypeStatus> selectInUsePage(Integer pageNum, Integer pageSize);

    @Select("select count(*) from work_type_status")
    Integer selectTotal();

    int releaseWorkTypeByIds(List<String> ids);
}
