package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.ConditionModel;
import com.alvaria.datareuse.entity.ResponseResult;
import com.alvaria.datareuse.entity.Station;
import com.alvaria.datareuse.entity.WorkType;
import com.alvaria.datareuse.service.StationService;
import com.alvaria.datareuse.service.UploadCSVService;
import com.alvaria.datareuse.service.WorkTypeService;
import com.alvaria.datareuse.service.WorkTypeStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseResult findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<Station> data = stationService.selectPage(pageNum, pageSize);
        Integer total = stationService.selectTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return new ResponseResult(0,res,"");
    }

    @GetMapping("/status/page")
    public ResponseResult findInUsePage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<Station> data = stationService.selectInUsePage(pageNum, pageSize);
        Integer total = stationService.selectInUseTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return new ResponseResult(0,res,"");
    }
}
