package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.NonReuseWorkType;
import com.alvaria.datareuse.entity.WorkType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NonReuseWorkTypeMapper {

    int insertWorkTypes(List<NonReuseWorkType> list);

    int insertWorkType(@Param("wt") NonReuseWorkType workType);

    int update(NonReuseWorkType workType);

    @Select("select count(*) from `work_type_non_reuse`")
    Integer selectTotal();

    @Select("select * from `work_type_non_reuse` limit #{pageNum}, #{pageSize}")
    List<NonReuseWorkType> selectPage(Integer pageNum, Integer pageSize);

    @Select("select * from `work_type_non_reuse`")
    List<NonReuseWorkType> findAll();

    List<NonReuseWorkType> findAllByKey(String keyWord);

    int removeWorkTypeByIds(@Param("list") String[] ids);
}
