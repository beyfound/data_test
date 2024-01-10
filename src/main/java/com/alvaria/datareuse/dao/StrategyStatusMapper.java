package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.Strategy;
import com.alvaria.datareuse.entity.StrategyStatus;
import com.alvaria.datareuse.entity.UserStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StrategyStatusMapper {
    @Delete("DELETE FROM strategy_status WHERE test_case=#{testCase}")
    int releaseStrategyByTestCase(String testCase);
    @Delete("DELETE FROM strategy_status WHERE strategy_name=#{name} and org=#{org}")
    int releaseStrategyByName(String name, String org);
    int removeStatusByUUIDList(List<String> ids);

    @Select("select * from strategy_status limit #{pageNum}, #{pageSize}")
    List<StrategyStatus> selectInUsePage(Integer pageNum, Integer pageSize);

    @Select("select count(*) from strategy_status")
    Integer selectTotal();

}
