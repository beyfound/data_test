package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.StationMapper;
import com.alvaria.datareuse.dao.UserTagMapper;
import com.alvaria.datareuse.entity.Station;
import com.alvaria.datareuse.entity.User;
import com.alvaria.datareuse.entity.WorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StationService {

    @Autowired
    private StationMapper stationMapper;

    public void releaseStation(String station) {
        stationMapper.releaseStation(station);
    }

    public String getIdleStationIdByOrg(String org) {
        int time = 0;
        while (time++ < 10) {
            Station station = stationMapper.getIdleStationIdByOrg(org);
            if (stationMapper.applyStationId(station) == 1)
                return station.getStation();
        }
        return null;
    }

    public List<Station> selectPage(Integer pageNum, Integer pageSize) {
        return stationMapper.selectPage(pageNum, pageSize);
    }

    public Integer selectTotal() {
        return stationMapper.selectTotal();
    }

    public Integer selectInUseTotal() {
        return stationMapper.selectInUseTotal();
    }

    public List<Station> selectInUsePage(Integer pageNum, Integer pageSize) {
        return stationMapper.selectInUsePage(pageNum, pageSize);
    }
}
