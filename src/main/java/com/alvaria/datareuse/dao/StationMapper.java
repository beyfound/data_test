package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.Station;
import com.alvaria.datareuse.entity.WorkType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StationMapper {
    @Select("select * from station where idle = 1 and org=#{org} limit 1")
    Station getIdleStationIdByOrg(String org);

    @Update ("Update station set idle = 0 where id=#{st.id} and #{st.idle}  !=0 ")
    int applyStationId(@Param("st") Station station);

    @Update ("Update station set idle = 1 where station=#{station}")
    int releaseStation(String station);

    @Select("select count(*) from station")
    Integer selectTotal();

    @Select("select count(*) from station where idle = 0")
    Integer selectInUseTotal();

    @Select("select * from station limit #{pageNum}, #{pageSize}")
    List<Station> selectPage(Integer pageNum, Integer pageSize);

    @Select("select * from station where idle = 0 limit #{pageNum}, #{pageSize}")
    List<Station> selectInUsePage(Integer pageNum, Integer pageSize);

    List<Station> findAllByKey(String keyWord);

    int deleteStations(@Param("list") String[] ids);

    int insertStation(@Param("sta") Station station);

    int update(Station station);

    int releaseStaionsByIds(List<String> ids);
}
