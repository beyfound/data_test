package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.Tag;
import com.alvaria.datareuse.entity.UserStatus;
import com.alvaria.datareuse.entity.UserTag;
import com.alvaria.datareuse.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UserTagMapper {
    List<User> findUserListByTags(@Param("array") String[] tags, boolean mtData);

    @Select("select tags from `user` where id = #{userId}")
    String findUserTagsById(Integer userId);

    @Select("select * from `user` where team = #{team} and mt_data=#{mtData}")
    List<User>  findUserByTeam(String team, boolean mtData);
}
