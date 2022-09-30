package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    User getUserById(@Param("user_id") Integer id);

    int insertReuseUsers(List<User> list);

    int insertUsers(List<User> list);

    int insertUser(@Param("user") User user);

    List<User> findAll();

    @Select("select * from user where role = #{role}")
    List<User> findAllByRole(String role);

    int update(User user);

    @Delete("delete from `user` where id = #{id}")
    int deleteById(Integer id);

    @Delete("delete from `user` where email = #{email}")
    int deleteByEmail(String email);

    int deleteByIds(@Param("ids") Integer[] ids);

    @Select("select * from user where identity = #{identify}")
    User getUserByIdentity(String identify);

    List<User> filterAvailableUser(String role, String org, boolean mtData, @Param("inIDs") List<Integer> inIDs);

    @Select("select * from user where email = #{email}")
    User getUserByEmail(String email);


    List<User> selectPage(Integer pageNum, Integer pageSize, String keyWord);

    @Select("select count(*) from user")
    Integer selectTotal();

    User getOneAvailableUser(String role, String org, String team, String identity, @Param("array") String[] tags, boolean mtData, @Param("IDs") List<Integer> exclusionIDs);

    List<User> findAllByKey(String keyWord);
}
