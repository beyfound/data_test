package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.ResponseResult;
import com.alvaria.datareuse.entity.Station;
import com.alvaria.datareuse.service.StationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/station")
@CrossOrigin
@Api(tags = "Station controller")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String keyWord, @RequestParam(required = false) String sort) throws Exception {
        return new ResponseResult(0, stationService.selectPage(pageNum, pageSize, keyWord, sort), "");
    }

    @GetMapping("/status/page")
    public ResponseResult findInUsePage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<Station> data = stationService.selectInUsePage(pageNum, pageSize);
        Integer total = stationService.selectInUseTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return new ResponseResult(0, res, "");
    }

    @GetMapping("/apply")
    public ResponseResult applyStationByOrg(@RequestParam String org) {
        String station = stationService.applyStationByOrg(org);
        return new ResponseResult(0, station, "");
    }

    @DeleteMapping("/release")
    public ResponseResult releaseStation(@RequestParam String station, @RequestParam String org) {
        stationService.releaseStationFromUserStatus(org, station);
        return new ResponseResult(0, "", "Release station successfully");
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteStations(@RequestBody String[] ids) {
        return new ResponseResult(0, "Delete " + stationService.deleteStations(ids) + " station(s) successfully", "");
    }

    @PostMapping("/save")
    public ResponseResult SaveOrUpdate(@RequestBody Station station) {
        int row = stationService.saveStation(station);
        int code = row > 0 ? 0 : -1;
        String message = row > 0 ? "Save successfully" : "Save failed";
        return new ResponseResult(code, message, "");
    }

    @DeleteMapping("/release/ids")
    public ResponseResult releaseUserByIds(@RequestBody List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ResponseResult(-1, ids, "parameter error");
        }

        return new ResponseResult(0, "effect " + stationService.releaseStationsByIds(ids) + " rows", "Release successfully");
    }
}
