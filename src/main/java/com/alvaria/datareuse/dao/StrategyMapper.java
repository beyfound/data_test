package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.Strategy;
import com.alvaria.datareuse.entity.StrategyStatus;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StrategyMapper {
    @Select("SELECT * FROM `strategy` WHERE id NOT IN (SELECT strategy_id FROM strategy_status WHERE org = #{org}) LIMIT 1")
    Strategy getIdleStrategy(String org);
    @Insert({"INSERT INTO strategy_status (strategy_id, strategy_name, org, test_case, uuid) values (#{strategyId}, #{strategyName}, #{org}, #{testCase}, #{uuid})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int applyStrategy(StrategyStatus strategyStatus);
}
