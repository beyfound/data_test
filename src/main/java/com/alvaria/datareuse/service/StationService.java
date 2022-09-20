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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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

    public Map<String, Object> selectPage(Integer pageNum, Integer pageSize, String keyWord) {
        int start = (pageNum - 1) * pageSize;
        List<Station> stations = stationMapper.findAllByKey(keyWord);
        int end = pageNum * pageSize > stations.size() ? stations.size() : pageNum * pageSize;
        List<Station> pageStations = stations.subList(start, end);
        Map<String, Object> res = new HashMap<>();
        res.put("data", pageStations);
        res.put("total", stations.size());
        return res;
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

    public int deleteStations(String[] ids) {
        int row = stationMapper.deleteStations(ids);
        return row;
    }

    public int saveStation(Station station) {
        if (station.getId() == null) {
            return stationMapper.insertStation(station);
        } else {
            return stationMapper.update(station);
        }
    }
}
