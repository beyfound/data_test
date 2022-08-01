package com.alvaria.datareuse.dao;

import com.alvaria.datareuse.entity.Station;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StationMapper {
    @Select("select * from station where idle = 1 and org=#{org} limit 1")
    Station getIdleStationIdByOrg(String org);

    @Update ("Update station set idle = 0 where id=#{st.id} and #{st.idle}  !=0 ")
    int applyStationId(@Param("st") Station station);

    @Update ("Update station set idle = 1 where station=#{station}")
    int releaseStation(String station);
}
