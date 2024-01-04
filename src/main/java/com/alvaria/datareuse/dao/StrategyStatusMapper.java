package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.Strategy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StrategyStatusMapper {
    @Delete("DELETE FROM strategy_status WHERE testCase=#{testCase}")
    int releaseStrategyByTestCase(String testCase);
    @Delete("DELETE FROM strategy_status WHERE strategy_name=#{name} and org=#{org}")
    int releaseStrategyByName(String name, String org);
    int removeStatusByUUIDList(List<String> ids);
}
