package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.NonReuseUser;
import com.alvaria.datareuse.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NonReuseUserMapper {


    int insertUsers(List<NonReuseUser> list);

    int insertUser(@Param("user") NonReuseUser user);

    List<NonReuseUser> findAll();

    NonReuseUser getUserById(@Param("user_id") Integer id);
    @Select("select * from `user_non_reuse` where role = #{role}")
    List<NonReuseUser> findAllByRole(String role);

    int update(NonReuseUser user);

    @Delete("delete from `user_non_reuse` where id = #{id}")
    int deleteById(Integer id);

    @Delete("delete from `user_non_reuse` where email = #{email}")
    int deleteByEmail(String email);

    int deleteByIds(@Param("ids") Integer[] ids);


    @Select("select * from `user_non_reuse` where email = #{email}")
    NonReuseUser getUserByEmail(String email);

    List<NonReuseUser> selectPage(Integer pageNum, Integer pageSize, String keyWord);

    @Select("select count(*) from `user_non_reuse`")
    Integer selectTotal();

    List<NonReuseUser> findAllByKey(String keyWord);
}
