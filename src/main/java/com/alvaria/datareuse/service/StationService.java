package com.alvaria.datareuse.service;

import com.alvaria.datareuse.dao.StationMapper;
import com.alvaria.datareuse.dao.UserStatusMapper;
import com.alvaria.datareuse.entity.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private UserStatusMapper UserStatusMapper;

    public void releaseStation(String station, String org) {
        stationMapper.releaseStation(station, org);
    }

    public String applyStationByOrg(String org) {
        String station = getIdleStationIdByOrg(org);
        UserStatusMapper.applyStationOnly(org, station);
        return station;
    }

    public void releaseStationFromUserStatus(String org, String station) {
        UserStatusMapper.releaseStationFromUserStatus(org, station);
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

    public Map<String, Object> selectPage(Integer pageNum, Integer pageSize, String keyWord, String sort) throws Exception {
        List<Station> stations = stationMapper.findAllByKey(keyWord);
        String[] sortProp = sort.split(",");
        switch (sortProp[0]){
            case "station" :
                if(sortProp[1].equals("ASC")){
                    stations = stations.stream().sorted(Comparator.comparing(Station::getStation)).collect(Collectors.toList());
                }else {
                    stations = stations.stream().sorted(Comparator.comparing(Station::getStation).reversed()).collect(Collectors.toList());
                }

                break;
            case "org":
                if(sortProp[1].equals("ASC")){
                    stations = stations.stream().sorted(Comparator.comparing(Station::getOrg)).collect(Collectors.toList());
                }else {
                    stations = stations.stream().sorted(Comparator.comparing(Station::getOrg).reversed()).collect(Collectors.toList());
                }
                break;
            default:
        }

        int staNum = stations.size();
        int start = (pageNum - 1) * pageSize;
        while (start >= staNum  && staNum!= 0) {
            pageNum--;
            start = (pageNum - 1) * pageSize;
        }

        int end = pageNum * pageSize > staNum ? staNum : pageNum * pageSize;
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

    public int releaseStationsByIds(List<String> ids) {
        return stationMapper.releaseStationsByIds(ids);
    }


}
