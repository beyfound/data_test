package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.User;
import com.alvaria.datareuse.entity.WorkType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WorkTypeMapper {

    int insertWorkTypes(List<WorkType> list);

    int insertWorkType(@Param("wt") WorkType workType);

    int update(WorkType workType);

    @Select("select count(*) from work_type")
    Integer selectTotal();

    @Select("select * from work_type limit #{pageNum}, #{pageSize}")
    List<WorkType> selectPage(Integer pageNum, Integer pageSize);


    @Select("select * from work_type where type = #{type} and mt_data=#{mtData} and id not in (select work_type_id from work_type_status where org =#{org})")
    List<WorkType> filterAvailableWorkType(String type, String org, boolean mtData);

    @Select("select * from work_type")
    List<WorkType> findAll();

    List<WorkType> findAllByKey(String keyWord);

    int removeWorkTypeByIds(@Param("list") String[] ids);
}
